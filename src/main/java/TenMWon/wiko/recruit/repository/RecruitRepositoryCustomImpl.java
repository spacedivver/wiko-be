package TenMWon.wiko.recruit.repository;

import TenMWon.wiko.common.entity.BaseResponseStatus;
import TenMWon.wiko.common.exception.BaseException;
import TenMWon.wiko.recruit.entity.IndustryType;
import TenMWon.wiko.recruit.entity.QRecruit;
import TenMWon.wiko.recruit.entity.Recruit;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class RecruitRepositoryCustomImpl implements RecruitRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Recruit> findRecruitWithFilters(List<String> industryTypeList, String startAddress, String endAddress, Long minPay, Long maxPay, Pageable pageable) {

        QRecruit recruit = QRecruit.recruit;

        BooleanExpression predicate = buildPredicate(industryTypeList, startAddress, endAddress, minPay, maxPay, recruit);

        List<Recruit> content = jpaQueryFactory
                .selectFrom(recruit)
                .where(predicate)
                .orderBy(recruit.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        if (content.isEmpty()) {
            throw new BaseException(BaseResponseStatus.NO_EXIST_RECRUIT);
        }

        long total = jpaQueryFactory
                .select(recruit.count())
                .from(recruit)
                .where(predicate)
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }

    // 필터링 조건(업종, 지역, 급여)
    private BooleanExpression buildPredicate(List<String> industryTypeList, String startAddress, String endAddress, Long minPay, Long maxPay, QRecruit recruit) {
        BooleanExpression predicate = recruit.isNotNull();
        // 업종
        if (industryTypeList != null && !industryTypeList.isEmpty()) {
            List<IndustryType> formattedTypes = industryTypeList.stream()
                    .map(type -> IndustryType.valueOf(type.replace("-", "_")))
                    .collect(Collectors.toList());
            predicate = predicate.and(recruit.industryType.in(formattedTypes));
        }
        // 지역
        if (startAddress != null && endAddress != null) {
            predicate = predicate.and(recruit.location.like(startAddress + "%"));
            predicate = predicate.and(recruit.location.like("%" + endAddress + "%"));
        } else if (startAddress != null) {
            predicate = predicate.and(recruit.location.like(startAddress + "%"));
        }

        if (minPay != null || maxPay != null) {
            predicate = predicate.and(convertPayToLong(recruit.pay, minPay, maxPay));
        }
        return predicate;
    }

    private BooleanExpression convertPayToLong(StringPath pay, Long minPay, Long maxPay) {
        // 쉼표, '만원', '원', '연봉', '월급', '시급' 등 제거하고 숫자만 추출
        StringTemplate payWithoutSymbols = Expressions.stringTemplate("replace(replace(replace(replace(replace({0}, ',', ''), '만원', ''), '원', ''), '연봉', ''), '월급', '')", pay);

        // 숫자만 추출한 값으로 Long 타입으로 변환
        NumberTemplate<Long> payValue = Expressions.numberTemplate(Long.class, "{0}", payWithoutSymbols);

        // 급여 조건 초기화
        BooleanExpression condition = null;

        // minPay 조건 추가
        if (minPay != null) {
            condition = payValue.goe(minPay);  // minPay보다 크거나 같은 급여만 필터링
        }

        // maxPay 조건 추가
        if (maxPay != null) {
            condition = condition == null ? payValue.loe(maxPay) : condition.and(payValue.loe(maxPay));  // maxPay보다 작거나 같은 급여만 필터링
        }

        return condition;
    }
}