package everis.bootcamp.clientMicroservice.ServiceDTO.Request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CreateClientRequest {
    private String name;
    private String status;

}
