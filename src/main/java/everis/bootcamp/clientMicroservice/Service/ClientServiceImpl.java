package everis.bootcamp.clientMicroservice.Service;

import everis.bootcamp.clientMicroservice.Document.Client;
import everis.bootcamp.clientMicroservice.Repository.ClientRepository;
import everis.bootcamp.clientMicroservice.ServiceDTO.Request.ClientRequest;
import everis.bootcamp.clientMicroservice.ServiceDTO.Request.UpdateClientRequest;
import everis.bootcamp.clientMicroservice.ServiceDTO.Response.ClientResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientRepository clientRepository;

//CREATE 100%
    @Override
    public Mono<Client> create(ClientRequest clientRequest) {
        return addClientToRepository(clientRequest);
    }

    private Mono<Client> addClientToRepository(ClientRequest clientRequest) {
        return clientRepository.findByName(clientRequest.getName())
                .switchIfEmpty(clientRepository.save(toClient(clientRequest)));
    }

    private Client toClient(ClientRequest clientRequest) {
        Client client = new Client();
        BeanUtils.copyProperties(clientRequest,client);
        client.setId(UUID.randomUUID().toString());
        client.setName(clientRequest.getName());
        client.setStatus(clientRequest.getStatus());
        client.setType(clientRequest.getType());

        return client;
    }

    //UPDATE: 0% --- No tengo idea de como implementarlo, se hacerlo en rxjava
    @Override
    public Mono<Client> update(String id,ClientRequest clientRequest) {
/*
        Optional<Client> clientMono = clientRepository.findById(updateClientRequest.getId());

        if (clientMono == null ){
            System.out.println("aea");
            return Mono.empty();
        }else
            Client client = clientMono.get();
        return clientRepository.save();
*/

    return clientRepository.findById(id).flatMap(client -> {
      client.setName(clientRequest.getName());
      client.setType(clientRequest.getType());
      client.setStatus(clientRequest.getStatus());
      return clientRepository.save(client);
    });

    }
/*READ 0% --- Para reedicion*/
@Override
public Flux<Client> readAll() {
    return clientRepository.findAllByStatusIsContaining("ACTIVE").switchIfEmpty(Flux.empty());
}
    //DELETE 100%
    @Override
    public Mono<Client> delete(String id) {
    final Mono<Client> clientMono = getOne(id);
        return getOne(id).switchIfEmpty(Mono.empty()).filter(Objects::nonNull)
                .flatMap(client -> clientRepository.delete(client).then(Mono.just(client)));

    }

//Find One 100%
    @Override
    public Mono<Client> getOne(String id) {
        return clientRepository.findById(id);
    }
}
