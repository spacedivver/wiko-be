package TenMWon.wiko.recruit.repository;

import TenMWon.wiko.recruit.entity.Recruit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface RecruitRepository extends JpaRepository<Recruit, Long> {
    Page<Recruit> findByCreatedAtAfterAndLang(LocalDateTime startOfDay, String lang, Pageable pageable);
//    Page<Recruit> findRecruitWithLangAndCreatedAtAfter(LocalDateTime startOfDay, String lang, Pageable pageable);
    Page<Recruit> findByLocalIsTrue(Pageable pageable);
}
