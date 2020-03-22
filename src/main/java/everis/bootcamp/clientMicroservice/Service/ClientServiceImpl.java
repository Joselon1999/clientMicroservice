package everis.bootcamp.clientMicroservice.Service;

import everis.bootcamp.clientMicroservice.Document.Client;
import everis.bootcamp.clientMicroservice.Document.ClientType;
import everis.bootcamp.clientMicroservice.Repository.ClientRepository;
import everis.bootcamp.clientMicroservice.Repository.ClientTypeRepository;
import everis.bootcamp.clientMicroservice.ServiceDTO.Request.ClientRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientRepository clientRepository;
    @Autowired
    ClientTypeRepository clientTypeRepository;

    //CREATE 100%
    @Override
    public Mono<Client> create(ClientRequest clientRequest) {
        Client client = new Client();
        client.setName(clientRequest.getName());
        client.setClientType(ClientType.builder().id(clientRequest.getIdClientType()).build());
        client.setStatus(clientRequest.getStatus());
        return clientRepository.save(client);
    }

    @Override
    public Mono<Client> update(String id, ClientRequest clientRequest) {
        return clientRepository.findById(id);
//                .flatMap(client -> {
//            Mono<ClientType> type = clientTypeRepository.findById(clientRequest.getIdClientType())
//                    .map(clientType ->
//                            client.setClientType(ClientType.builder()
//                                    .id(clientType.getId())
//                                    .name(clientType.getName())
//                                    .build()));
//            client.setName(clientRequest.getName());
//
//            client.setStatus(clientRequest.getStatus());
//            return clientRepository.save(client);
//        });

    }

    /*READ 100%*/
    @Override
    public Flux<Client> readAll() {
        return clientRepository.findAll();
    }

    //DELETE 100%
    @Override
    public Mono<Client> delete(String id) {
        return getOne(id).switchIfEmpty(Mono.empty()).filter(Objects::nonNull)
                .flatMap(client -> clientRepository.delete(client).then(Mono.just(client)));

    }

    //Find One 100%
    @Override
    public Mono<Client> getOne(String id) {
        return clientRepository.findById(id);
    }

//IsPresent

    @Override
    public Mono<Boolean> isPresent(String id) {
        return clientRepository.existsById(id);
    }

}
