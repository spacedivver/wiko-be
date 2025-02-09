package TenMWon.wiko.resume.repository;

import TenMWon.wiko.resume.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeRepository extends JpaRepository<Resume, Long> {

    //    Resume findByUserId(Long userId);

}
