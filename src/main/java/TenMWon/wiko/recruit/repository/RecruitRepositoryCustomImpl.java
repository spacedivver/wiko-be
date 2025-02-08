package TenMWon.wiko.recruit.repository;

import TenMWon.wiko.recruit.entity.IndustryType;
import TenMWon.wiko.recruit.entity.QRecruit;
import TenMWon.wiko.recruit.entity.Recruit;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RecruitRepositoryCustomImpl implements RecruitRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Recruit> findRecruitWithFilters(String industryType, String startAddress, String endAddress, Long minSalary, Long maxSalary, Pageable pageable) {
        QRecruit recruit = QRecruit.recruit;

        BooleanExpression predicate = buildPredicate(industryType, startAddress, endAddress, minSalary, maxSalary, recruit);

        List<Recruit> content = jpaQueryFactory
                .selectFrom(recruit)
                .where(predicate)
                .orderBy(recruit.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        long total = jpaQueryFactory
                .selectFrom(recruit)
                .where(predicate)
                .fetchCount();
        return new PageImpl<>(content, pageable, total);
    }

    // 필터링 조건(업종, 지역, 급여)
    private BooleanExpression buildPredicate(String industryType, String startAddress, String endAddress, Long minSalary, Long maxSalary, QRecruit recruit) {
        BooleanExpression predicate = recruit.isNotNull();
        // 업종
        if (industryType != null) {
            predicate = predicate.and(recruit.industryType.eq(IndustryType.valueOf(industryType)));
        }
        // 지역
        if (startAddress != null && endAddress != null) {
            predicate = predicate.and(recruit.companyAddress.like(startAddress + "%"));
            predicate = predicate.and(recruit.companyAddress.like("%" + endAddress + "%"));
        } else if (startAddress != null) {
            predicate = predicate.and(recruit.companyAddress.like(startAddress + "%"));
        }
        // 급여
        if (minSalary != null) {
            predicate = predicate.and(recruit.salary.goe(minSalary));
        }
        if (maxSalary != null) {
            predicate = predicate.and(recruit.salary.loe(maxSalary));
        }
        return predicate;
    }
}
