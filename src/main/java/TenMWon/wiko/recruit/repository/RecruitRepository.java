package TenMWon.wiko.recruit.repository;

import TenMWon.wiko.recruit.entity.Recruit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface RecruitRepository extends JpaRepository<Recruit, Long> {
}
