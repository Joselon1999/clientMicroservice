package everis.bootcamp.clientMicroservice.ServiceDTO.Response;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientResponse {
    private String id;
    private String name;
    private String clientTypeName;
    private String status;
}
