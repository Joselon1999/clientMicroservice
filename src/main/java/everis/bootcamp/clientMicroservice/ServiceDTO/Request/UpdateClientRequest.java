package everis.bootcamp.clientMicroservice.ServiceDTO.Request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateClientRequest {
    private String id;
    private String name;
    private String type;
    private String status;
}
