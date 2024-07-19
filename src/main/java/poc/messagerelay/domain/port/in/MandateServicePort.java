package poc.messagerelay.domain.port.in;

import poc.messagerelay.domain.data.MandateDto;

import java.util.List;

public interface MandateServicePort {

    MandateDto addMandate(MandateDto mandateDto);

    List<MandateDto> getMandates();

    MandateDto getMandateById(Long id);

    void deleteMandate(Long id);
}
