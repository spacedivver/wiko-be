package TenMWon.wiko.recruit.repository;

import TenMWon.wiko.recruit.entity.Recruit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RecruitRepositoryCustom {

    Page<Recruit> findRecruitWithFilters(List<String> industryTypeList, String startAddress, String endAddress, Long minPay, Long maxPay, String keyword, Pageable pageable);
}
