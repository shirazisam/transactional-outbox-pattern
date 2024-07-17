package poc.messagerelay.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import poc.messagerelay.domain.data.MandateDto;
import poc.messagerelay.infrastructure.entity.Mandate;

@Mapper
public interface MandateMapper {

    MandateMapper INSTANCE = Mappers.getMapper(MandateMapper.class);

    MandateDto mandateToMandateDto(Mandate mandate);

    Mandate mandateDtoToMandate(MandateDto mandateDto);
}
