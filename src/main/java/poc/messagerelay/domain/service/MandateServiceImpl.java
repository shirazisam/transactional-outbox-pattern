package poc.messagerelay.domain.service;

import poc.messagerelay.domain.data.MandateDto;
import poc.messagerelay.domain.port.in.MandateServicePort;
import poc.messagerelay.domain.port.out.MandatePersistencePort;

public class MandateServiceImpl implements MandateServicePort {

    private final MandatePersistencePort mandatePersistencePort;

    public MandateServiceImpl(MandatePersistencePort mandatePersistencePort) {
        this.mandatePersistencePort = mandatePersistencePort;
    }

    @Override
    public MandateDto addMandate(MandateDto mandateDto) {
        return mandatePersistencePort.addMandate(mandateDto);
    }
}
