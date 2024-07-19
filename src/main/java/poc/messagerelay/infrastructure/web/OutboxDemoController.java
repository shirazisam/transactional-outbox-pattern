package poc.messagerelay.infrastructure.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import poc.messagerelay.domain.data.MandateDto;
import poc.messagerelay.domain.port.in.MandateServicePort;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<MandateDto>> getMandates() {
        return ResponseEntity.ok(mandateServicePort.getMandates());
    }

    @GetMapping(value = "/{id}", produces = { "application/json" })
    public ResponseEntity<MandateDto> getMandateById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(mandateServicePort.getMandateById(id));
    }

    @DeleteMapping(value = "/{id}")
    public void deleteMandate(@PathVariable("id") Long id) {
        mandateServicePort.deleteMandate(id);
    }
}
