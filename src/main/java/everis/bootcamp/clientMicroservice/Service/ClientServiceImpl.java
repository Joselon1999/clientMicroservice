package everis.bootcamp.clientMicroservice.Service;

import everis.bootcamp.clientMicroservice.Document.Client;
import everis.bootcamp.clientMicroservice.Repository.ClientRepository;
import everis.bootcamp.clientMicroservice.ServiceDTO.Request.CreateClientRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientRepository clientRepository;

//CREATE 100%
    @Override
    public Mono<Client> create(CreateClientRequest createClientRequest) {
        return addClientToRepository(createClientRequest);
    }

    private Mono<Client> addClientToRepository(CreateClientRequest createClientRequest) {
        return clientRepository.findByName(createClientRequest.getName())
                .switchIfEmpty(clientRepository.save(toClient(createClientRequest)));
    }

    private Client toClient(CreateClientRequest createClientRequest) {
        Client client = new Client();
        BeanUtils.copyProperties(createClientRequest,client);
        client.setId(UUID.randomUUID().toString());
        client.setName(createClientRequest.getName());
        client.setStatus(createClientRequest.getStatus());

        return client;
    }

    //UPDATE: 0% --- No tengo idea de como implementarlo, se hacerlo en rxjava
    @Override
    public Mono<Client> update() {
        return null;
    }
//READ 100%
    @Override
    public Flux<Client> readAll() {
        return clientRepository.findAll();
    }
//DELETE 100%
    @Override
    public Mono<Client> delete(String id) {
        return clientRepository.findById(id).switchIfEmpty(Mono.empty())
                .map(client -> changeClientStatus(client,"INACTIVE"));
    }

    private Client changeClientStatus(Client client, String status) {
        client.setStatus(status);
        return client;
    }
//Find One 100%
    @Override
    public Mono<Client> getOne(String id) {
        return clientRepository.findById(id);
    }
}
