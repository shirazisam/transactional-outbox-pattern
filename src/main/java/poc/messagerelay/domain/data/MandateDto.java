package poc.messagerelay.domain.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MandateDto {
    private Long id;
    private String debtType;
    private String debtReference;
    private String persIdf;
    private String mandateReference;
    private Integer duration;
}
