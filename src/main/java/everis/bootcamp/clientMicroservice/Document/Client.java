package everis.bootcamp.clientMicroservice.Document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Document
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    @Id
    private String id;
    @NotBlank(message = "'Names' can't be blank")
    private String name;
    @NotNull(message = "'Type' can't be blank")
    private ClientType clientType;
    @NotBlank(message = "'Status' can't be blank")
    private String status;
}
