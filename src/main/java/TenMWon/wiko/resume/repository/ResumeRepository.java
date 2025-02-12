package TenMWon.wiko.resume.repository;

import TenMWon.wiko.resume.entity.Resume;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResumeRepository extends JpaRepository<Resume, Long> {

//        Optional<Resume> findByUserUserId(Long userId);
        Page<Resume> findByUserUserId(Long userId, Pageable pageable);
}
