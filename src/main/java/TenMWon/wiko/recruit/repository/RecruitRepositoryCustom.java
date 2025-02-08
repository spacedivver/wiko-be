package TenMWon.wiko.recruit.repository;

import TenMWon.wiko.recruit.entity.Recruit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RecruitRepositoryCustom {

    Page<Recruit> findRecruitWithFilters(String industryType, String startAddress, String endAddress, Long minSalary, Long maxSalary, Pageable pageable);
}
