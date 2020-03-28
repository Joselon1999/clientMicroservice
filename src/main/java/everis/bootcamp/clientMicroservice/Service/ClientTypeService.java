package everis.bootcamp.clientMicroservice.Service;

import everis.bootcamp.clientMicroservice.Document.ClientType;
import everis.bootcamp.clientMicroservice.ServiceDTO.Request.ClientRequest;
import everis.bootcamp.clientMicroservice.ServiceDTO.Request.ClientTypeRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClientTypeService {
    Mono<ClientType> create(ClientType clientType);
    Mono<ClientType> update(String id,ClientTypeRequest clientTypeRequest);
    Flux<ClientType> readAll();
    Mono<ClientType> delete(String id);
    Mono<ClientType> getOne(String id);

}
