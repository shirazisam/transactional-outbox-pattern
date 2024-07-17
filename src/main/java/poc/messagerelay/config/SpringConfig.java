package poc.messagerelay.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import poc.messagerelay.domain.port.in.MandateServicePort;
import poc.messagerelay.domain.port.out.MandatePersistencePort;
import poc.messagerelay.domain.service.MandateServiceImpl;
import poc.messagerelay.infrastructure.adapter.MandateService;

@Configuration
public class SpringConfig {

    @Bean
    public MandateServicePort mandateAdapter(){
        return new MandateServiceImpl(mandatePersistence());
    }

    @Bean
    public MandatePersistencePort mandatePersistence(){
        return new MandateService();
    }
}
