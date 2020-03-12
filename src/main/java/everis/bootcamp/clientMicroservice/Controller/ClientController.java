package everis.bootcamp.clientMicroservice.Controller;

import everis.bootcamp.clientMicroservice.Document.Client;
import everis.bootcamp.clientMicroservice.Service.ClientService;
import everis.bootcamp.clientMicroservice.ServiceDTO.Request.CreateClientRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/clients")
public class ClientController {

    private final ClientService clientService;

    //CREATE
    @PostMapping(produces = {MediaType.APPLICATION_STREAM_JSON_VALUE})
    public Mono<Client> createClient(@RequestBody CreateClientRequest createClientRequest){
        return clientService.create(createClientRequest);
    }
    //UPDATE

    //Loading...

    //READ
    @GetMapping(produces = {MediaType.APPLICATION_STREAM_JSON_VALUE})
    public Flux<Client> readAllClients(){
        return clientService.readAll();
    }
    //DELETE
    @DeleteMapping(value = "/{clientId}",produces = {MediaType.APPLICATION_STREAM_JSON_VALUE})
    public Mono<Client> deleteClient(@PathVariable(value = "clientId") String clientId){
        return clientService.delete(clientId);
    }
}
