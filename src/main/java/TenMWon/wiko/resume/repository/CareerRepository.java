package TenMWon.wiko.resume.repository;

import TenMWon.wiko.resume.entity.Career;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CareerRepository extends JpaRepository<Career, Long> {

    Optional<Career> findByResumeResumeId(Long resumeId);
}
