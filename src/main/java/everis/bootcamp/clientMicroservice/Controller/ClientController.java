package everis.bootcamp.clientMicroservice.Controller;

import everis.bootcamp.clientMicroservice.Document.Client;
import everis.bootcamp.clientMicroservice.Service.ClientService;
import everis.bootcamp.clientMicroservice.ServiceDTO.Request.ClientRequest;
import everis.bootcamp.clientMicroservice.ServiceDTO.Request.UpdateClientRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/clients")
public class ClientController {

    private final ClientService clientService;

    //CREATE
    @PostMapping(value = "/newClient")
    public Mono<Client> createClient(@RequestBody Client client){
        return clientService.create(client);
    }
    //UPDATE

    @PutMapping(value = "/update/{id}")
    public Mono<ResponseEntity<Client>> updateClient(@PathVariable("id") String id,@RequestBody ClientRequest clientRequest) {
        return clientService.update(id,clientRequest)
                .map(client -> ResponseEntity.created(URI.create("/clients".concat(client.getId())))
                        .contentType(MediaType.APPLICATION_JSON).body(client))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    //READ
    @GetMapping(value = "/selectAll")
    public ResponseEntity<Flux<Client>> listClient(){
        return ResponseEntity.ok().body(clientService.readAll());
    }
    //DELETE
    @DeleteMapping(value = "/delete/{clientId}")
    public Mono<Client> deleteClient(@PathVariable(value = "clientId") String clientId){
        return clientService.delete(clientId);
    }
    //FIND ONE
    @GetMapping(value = "/find/{clientId}")
    public Mono<Client> findOneClient(@PathVariable(value = "clientId") String clientId){
        return clientService.getOne(clientId);
    }
    //EXIST
    @GetMapping(value = "/exist/{clientId}")
    public Mono<Boolean> existClient(@PathVariable(value = "clientId") String clientId){
        return clientService.isPresent(clientId);
    }

}
