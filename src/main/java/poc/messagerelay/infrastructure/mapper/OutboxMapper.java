package poc.messagerelay.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import poc.messagerelay.domain.data.OutboxDto;
import poc.messagerelay.infrastructure.entity.Outbox;

@Mapper
public interface OutboxMapper {

    OutboxMapper INSTANCE = Mappers.getMapper(OutboxMapper.class);

    OutboxDto outboxToDto(Outbox outbox);

    Outbox dtoToOutbox(OutboxDto outboxDto);
}
