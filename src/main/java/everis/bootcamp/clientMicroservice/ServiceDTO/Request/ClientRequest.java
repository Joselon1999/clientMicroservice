package everis.bootcamp.clientMicroservice.ServiceDTO.Request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientRequest {
    private String name;
    private String idClientType;
    private String bankId;
    private String status;

}
