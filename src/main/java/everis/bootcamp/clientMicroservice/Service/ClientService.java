package everis.bootcamp.clientMicroservice.Service;

import everis.bootcamp.clientMicroservice.ServiceImpl.Request.CreateClientRequest;
import reactor.core.publisher.Mono;

public interface ClientService {
    Mono<String> create(CreateClientRequest createClientRequest);

}
