package everis.bootcamp.clientMicroservice.Document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;


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
    @NotBlank(message = "'Type' can't be blank")
    private String type;
    @NotBlank(message = "'Status' can't be blank")
    private String status;
}
