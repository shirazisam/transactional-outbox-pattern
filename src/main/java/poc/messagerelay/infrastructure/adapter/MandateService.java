package poc.messagerelay.infrastructure.adapter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poc.messagerelay.domain.data.MandateDto;
import poc.messagerelay.domain.port.out.MandatePersistencePort;
import poc.messagerelay.infrastructure.entity.Mandate;
import poc.messagerelay.infrastructure.entity.Outbox;
import poc.messagerelay.infrastructure.mapper.MandateMapper;
import poc.messagerelay.infrastructure.repository.MandateRepository;
import poc.messagerelay.infrastructure.repository.OutboxRepository;

@Slf4j
@Service
public class MandateService implements MandatePersistencePort {

    @Autowired
    private MandateRepository mandateRepository;

    @Autowired
    private OutboxRepository outboxRepository;

    @Override
    @Transactional
    public MandateDto addMandate(MandateDto mandateDto) {
        MandateMapper mandateMapper = MandateMapper.INSTANCE;
        Mandate saved = mandateRepository.save(mandateMapper.mandateDtoToMandate(mandateDto));
        outboxRepository.save(Outbox.builder().mandateId(saved.getId()).operation(saved.getDebtType()).build());
        log.info("Mandate {} created", saved.getId());
        return mandateMapper.mandateToMandateDto(saved);
    }
}
