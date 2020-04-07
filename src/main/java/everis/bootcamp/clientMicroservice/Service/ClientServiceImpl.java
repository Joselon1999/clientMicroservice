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
public class ClientServiceImpl implements ClientService {


    private final ClientRepository clientRepository;

    //private final ClientTypeRepository clientTypeRepository;
    @Autowired
    ClientTypeRepository clientTypeRepository;


    public ClientServiceImpl(ClientRepository repository) {
        this.clientRepository=repository;
    }
    //public ClientTypeServiceImpl(ClientTypeRepository repository) {
    //    this.clientTypeRepository=repository;
    //}

    //CREATE 100%
    @Override
    public Mono<Client> create(ClientRequest clientRequest) {
        Mono<ClientType> clientTypeMono = clientTypeRepository.findById(clientRequest.getIdClientType());
        return clientTypeMono.flatMap(type ->
                {
                    Client client = new Client();
                    client.setName(clientRequest.getName());
                    client.setBankId(clientRequest.getBankId());
                    client.setClientType(ClientType.builder()
                            .id(type.getId())
                            .name(type.getName())
                            .build());
                    client.setStatus(clientRequest.getStatus());
                    return clientRepository.save(client);
                }
        );
    }

    @Override
    public Mono<Client> update(String id, ClientRequest clientRequest) {
        Mono<ClientType> clientTypeMono = clientTypeRepository.findById(clientRequest.getIdClientType());
        return clientRepository.findById(id)
                .flatMap(thisClient -> {
                    Mono<Client> clientMono = clientTypeMono.flatMap(type ->
                            {
                                thisClient.setName(clientRequest.getName());
                                thisClient.setBankId(clientRequest.getBankId());
                                thisClient.setClientType(ClientType.builder()
                                        .id(type.getId())
                                        .name(type.getName())
                                        .build());
                                thisClient.setStatus(clientRequest.getStatus());
                                return clientRepository.save(thisClient);
                            }
                    );
                    return clientMono;
                }).switchIfEmpty(Mono.empty());
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

    //TODO Corregir esta asquerosidad
    @Override
    public Mono<String> getType(String id) {
        Mono<Client> client = clientRepository.findById(id);
        Mono<String> type = client.map(client1 -> {
            return client1.getClientType().getName();
        });
        return type;
    }
}