package poc.messagerelay.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import poc.messagerelay.infrastructure.entity.Outbox;

@Repository
public interface OutboxRepository extends JpaRepository<Outbox, Long> {
}
