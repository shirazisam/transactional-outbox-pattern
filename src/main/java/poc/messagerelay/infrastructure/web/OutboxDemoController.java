package poc.messagerelay.infrastructure.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import poc.messagerelay.domain.data.MandateDto;
import poc.messagerelay.domain.port.in.MandateServicePort;

@RestController
@RequestMapping("/api/v1/mandates")
public class OutboxDemoController {

    private final MandateServicePort mandateServicePort;

    public OutboxDemoController(MandateServicePort mandateServicePort) {
        this.mandateServicePort = mandateServicePort;
    }


    @PostMapping
    public MandateDto addMandate(@RequestBody MandateDto mandateDto) {
        return mandateServicePort.addMandate(mandateDto);
    }

}
