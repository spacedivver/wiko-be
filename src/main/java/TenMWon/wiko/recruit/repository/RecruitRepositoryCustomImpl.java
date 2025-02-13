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
    public Page<Recruit> findRecruitWithFilters(List<String> industryTypeList, String startAddress, String endAddress, Long minPay, Long maxPay, String keyword, String lang, Pageable pageable) {

        QRecruit recruit = QRecruit.recruit;

        BooleanExpression predicate = buildPredicate(industryTypeList, startAddress, endAddress, minPay, maxPay, keyword, lang, recruit);

        List<Recruit> content = jpaQueryFactory
                .selectFrom(recruit)
                .where(predicate)
                .orderBy(recruit.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = jpaQueryFactory
                .select(recruit.count())
                .from(recruit)
                .where(predicate)
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<Recruit> findRecruitWithFiltersLang(String lang, Pageable pageable) {
        QRecruit recruit = QRecruit.recruit;

        BooleanExpression predicate = buildLangPredicate(lang, recruit);

        List<Recruit> content = jpaQueryFactory
                .selectFrom(recruit)
                .where(predicate)
                .orderBy(recruit.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = jpaQueryFactory
                .select(recruit.count())
                .from(recruit)
                .where(predicate)
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression buildLangPredicate(String lang, QRecruit recruit) {
        BooleanExpression predicate = recruit.isNotNull();

        // 언어
        if (lang != null && !lang.isEmpty()) {
            predicate = predicate.and(recruit.lang.eq(lang)); // recruit DB -> lang == RequestParam -> lang
        }

        return predicate;
    }

    private BooleanExpression buildPredicate(List<String> industryTypeList, String startAddress, String endAddress, Long minPay, Long maxPay, String keyword, String lang, QRecruit recruit) {
        BooleanExpression predicate = recruit.isNotNull();

        // 검색 (title을 사용한 검색을 진행)
        if (keyword != null && !keyword.isEmpty()) {
            predicate = predicate.and(recruit.title.containsIgnoreCase(keyword));
        }

        // 언어
        if (lang != null && !lang.isEmpty()) {
            predicate = predicate.and(recruit.lang.eq(lang)); // recruit DB -> lang == RequestParam -> lang
        }

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

        // 급여
        if (minPay != null) {
            predicate = predicate.and(recruit.pay.goe(minPay));
        }
        if (maxPay != null) {
            predicate = predicate.and(recruit.pay.loe(maxPay));
        }
        return predicate;
    }
}
