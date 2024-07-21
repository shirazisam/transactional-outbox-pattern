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

import java.util.Calendar;
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

    private final MandateMapper mandateMapper = MandateMapper.INSTANCE;

    @Override
    @Transactional
    public MandateDto addMandate(MandateDto mandateDto) {
        String state;
        Optional<Mandate> maybePresent = Optional.empty();
        if (mandateDto.getId() != null) {
            maybePresent = mandateRepository.findById(mandateDto.getId());
        }
        state = maybePresent.isPresent() ? "UPDATED" : "CREATED";
        Mandate savedMandate = mandateRepository.save(mandateMapper.mandateDtoToMandate(mandateDto));
        /* save the action in the Outbox table, in the same transaction */
        outboxRepository.save(Outbox.builder().mandateId(savedMandate.getId()).operation(state).timestamp(Calendar.getInstance()).build());
        log.info("Mandate {} is {}", savedMandate.getId(), state);
        return mandateMapper.mandateToMandateDto(savedMandate);
    }

    @Override
    public List<MandateDto> getMandates() {
        return mandateRepository.findAll().stream().map(mandateMapper::mandateToMandateDto).collect(Collectors.toList());
    }

    @Override
    public MandateDto getMandateById(Long id) {
        return mandateRepository.findById(id).map(mandateMapper::mandateToMandateDto).orElse(null);
    }

    @Override
    @Transactional
    public void deleteMandate(Long id) {
        Optional<Mandate> mandate = mandateRepository.findById(id);
        if (mandate.isPresent()) {
            Mandate entity = mandate.get();
            mandateRepository.delete(entity);
            outboxRepository.save(new Outbox(null, entity.getId(), "DELETED", Calendar.getInstance()));
        }
    }

}
