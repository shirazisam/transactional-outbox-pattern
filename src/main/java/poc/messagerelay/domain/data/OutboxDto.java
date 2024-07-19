package poc.messagerelay.domain.data;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Calendar;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OutboxDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long mandateId;
    private String operation;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Calendar timestamp;
}
