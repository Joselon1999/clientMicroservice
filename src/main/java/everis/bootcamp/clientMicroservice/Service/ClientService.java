package everis.bootcamp.clientMicroservice.Service;

import everis.bootcamp.clientMicroservice.Document.Client;
import everis.bootcamp.clientMicroservice.Document.ClientType;
import everis.bootcamp.clientMicroservice.ServiceDTO.Request.ClientRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClientService {
    Mono<Client> create(ClientRequest clientRequest);
    Mono<Client> update(String id,ClientRequest clientRequest);
    Flux<Client> readAll();
    Mono<Client> delete(String id);
    Mono<Client> getOne(String id);
    Mono<Boolean> isPresent(String id);
    Mono<String> getType(String id);
}
