package poc.messagerelay.domain.service;

import poc.messagerelay.domain.data.MandateDto;
import poc.messagerelay.domain.port.in.MandateServicePort;
import poc.messagerelay.domain.port.out.MandatePersistencePort;

import java.util.List;

public class MandateServiceImpl implements MandateServicePort {

    private final MandatePersistencePort mandatePersistencePort;

    public MandateServiceImpl(MandatePersistencePort mandatePersistencePort) {
        this.mandatePersistencePort = mandatePersistencePort;
    }

    @Override
    public MandateDto addMandate(MandateDto mandateDto) {
        return mandatePersistencePort.addMandate(mandateDto);
    }

    @Override
    public List<MandateDto> getMandates() {
        return mandatePersistencePort.getMandates();
    }
}
