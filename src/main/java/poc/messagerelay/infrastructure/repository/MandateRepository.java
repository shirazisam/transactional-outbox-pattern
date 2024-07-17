package poc.messagerelay.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import poc.messagerelay.infrastructure.entity.Mandate;

@Repository
public interface MandateRepository extends JpaRepository<Mandate, Long> {
}
