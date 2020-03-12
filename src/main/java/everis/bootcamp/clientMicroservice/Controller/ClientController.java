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
    @PostMapping(value = "/newClient",produces = {MediaType.APPLICATION_STREAM_JSON_VALUE})
    public Mono<Client> createClient(@RequestBody ClientRequest clientRequest){
        return clientService.create(clientRequest);
    }
    //UPDATE

    @PutMapping(value = "/update/{id}",produces = {MediaType.APPLICATION_STREAM_JSON_VALUE})
    public Mono<ResponseEntity<Client>> updateClient(@PathVariable("id") String id,@RequestBody ClientRequest clientRequest) {
        return clientService.update(id,clientRequest)
                .map(client -> ResponseEntity.created(URI.create("/clients".concat(client.getId())))
                        .contentType(MediaType.APPLICATION_JSON).body(client))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    //READ
    @GetMapping(value = "/selectAll",produces = {MediaType.APPLICATION_STREAM_JSON_VALUE})
    public Mono<ResponseEntity<Flux<Client>>> list(){
        return Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(clientService.readAll()));
    }
    //DELETE
    @DeleteMapping(value = "/delete/{clientId}",produces = {MediaType.APPLICATION_STREAM_JSON_VALUE})
    public Mono<Client> deleteClient(@PathVariable(value = "clientId") String clientId){
        return clientService.delete(clientId);
    }
}
