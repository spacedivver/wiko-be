package TenMWon.wiko.recruit.repository;

import TenMWon.wiko.recruit.entity.Recruit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecruitRepository extends JpaRepository<Recruit, Long> {
    Page<Recruit> findByTitleContainingIgnoreCaseOrCompanyContainingIgnoreCase(String keyword, String keyword1, Pageable pageable);
}
