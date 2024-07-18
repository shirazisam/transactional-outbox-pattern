package poc.messagerelay.domain.port.out;

import poc.messagerelay.domain.data.MandateDto;

import java.util.List;

public interface MandatePersistencePort {

    MandateDto addMandate(MandateDto mandateDto);

    List<MandateDto> getMandates();
}
