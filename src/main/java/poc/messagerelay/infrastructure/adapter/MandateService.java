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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MandateService implements MandatePersistencePort {

    @Autowired
    private MandateRepository mandateRepository;

    @Autowired
    private OutboxRepository outboxRepository;

    MandateMapper mandateMapper = MandateMapper.INSTANCE;

    @Override
    @Transactional
    public MandateDto addMandate(MandateDto mandateDto) {
        Optional<Mandate> maybePresent = mandateRepository.findById(mandateDto.getId());
        String state = maybePresent.isPresent() ? "UPDATED" : "CREATED";
        Mandate saved = mandateRepository.save(mandateMapper.mandateDtoToMandate(mandateDto));
        outboxRepository.save(Outbox.builder().mandateId(saved.getId()).operation(state).build());
        log.info("Mandate {} created", saved.getId());
        return mandateMapper.mandateToMandateDto(saved);
    }

    @Override
    public List<MandateDto> getMandates() {
        return mandateRepository.findAll().stream().map(mandateMapper::mandateToMandateDto).collect(Collectors.toList());
    }
}
