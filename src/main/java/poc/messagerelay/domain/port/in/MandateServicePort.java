package poc.messagerelay.domain.port.in;

import poc.messagerelay.domain.data.MandateDto;

public interface MandateServicePort {

    MandateDto addMandate(MandateDto mandateDto);

}
