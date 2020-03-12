package everis.bootcamp.clientMicroservice.ServiceDTO.Request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ClientRequest {
    private String name;
    private String type;
    private String status;

}
