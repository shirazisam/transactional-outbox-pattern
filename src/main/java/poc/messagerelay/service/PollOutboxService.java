package poc.messagerelay.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poc.messagerelay.infrastructure.entity.Outbox;
import poc.messagerelay.infrastructure.mapper.OutboxMapper;
import poc.messagerelay.infrastructure.publisher.MandatePublisher;
import poc.messagerelay.infrastructure.repository.OutboxRepository;

import java.time.LocalDateTime;

@Slf4j
@Service
public class PollOutboxService {

    @Autowired
    private MandatePublisher mandatePublisher;

    @Autowired
    private OutboxRepository outboxRepository;

    @Scheduled(cron = "${app.poll.cron}")
    @Transactional
    public void pollOutboxTable() {
        log.info("Polling for Outbox for new events {}", LocalDateTime.now());
        for (Outbox item : outboxRepository.findAll()) {
            processOutBoxTableItem(item);
            outboxRepository.delete(item);
        }
    }

    /**
     * Put the outbox item on a message queue
     * @param item to put on message queue
     */
    private void processOutBoxTableItem(Outbox item) {
        mandatePublisher.publishMessage(OutboxMapper.INSTANCE.outboxToOoutboxDto(item));
        log.info("published item = {}", item);
    }
}
