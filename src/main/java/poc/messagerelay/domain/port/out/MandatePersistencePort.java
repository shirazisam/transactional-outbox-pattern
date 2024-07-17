package poc.messagerelay.domain.port.out;

import poc.messagerelay.domain.data.MandateDto;

public interface MandatePersistencePort {

    MandateDto addMandate(MandateDto mandateDto);
}
