package everis.bootcamp.clientMicroservice.Service;

import everis.bootcamp.clientMicroservice.Document.Client;
import everis.bootcamp.clientMicroservice.Repository.ClientRepository;
import everis.bootcamp.clientMicroservice.ServiceDTO.Request.ClientRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientRepository clientRepository;

//CREATE 100%
    @Override
    public Mono<Client> create(Client clientRequest) {
        return clientRepository.save(clientRequest);
    }

    @Override
    public Mono<Client> update(String id,ClientRequest clientRequest) {
      return clientRepository.findById(id).flatMap(client -> {
      client.setName(clientRequest.getName());
      client.setType(clientRequest.getType());
      client.setStatus(clientRequest.getStatus());
      return clientRepository.save(client);
    });

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
