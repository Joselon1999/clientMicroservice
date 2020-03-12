package everis.bootcamp.clientMicroservice.Service;

import everis.bootcamp.clientMicroservice.Document.Client;
import everis.bootcamp.clientMicroservice.ServiceDTO.Request.CreateClientRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface ClientService {
    Mono<Client> create(CreateClientRequest createClientRequest);
    Mono<Client> update();
    Flux<Client> readAll();
    Mono<Client> delete(String id);
    Mono<Client> getOne(String id);
}
