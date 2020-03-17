package everis.bootcamp.clientMicroservice.Controller;

import everis.bootcamp.clientMicroservice.Document.Client;
import everis.bootcamp.clientMicroservice.Service.ClientService;
import everis.bootcamp.clientMicroservice.ServiceDTO.Request.ClientRequest;
import everis.bootcamp.clientMicroservice.ServiceDTO.Request.UpdateClientRequest;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class ClientController {

    private final ClientService clientService;

    //CREATE
    @ApiOperation(value = "Creates new clients")
    @PostMapping(value = "/clients")
    public Mono<Client> createClient(@Valid @RequestBody Client client){
        return clientService.create(client);
    }
    //UPDATE
    @ApiOperation(value = "Updates clients",
                    notes = "Requires the client ID and all Client Request Params - Which are same Client Params" +
                            "excluding the ID")
    @PutMapping(value = "/clients/{id}")
    public Mono<ResponseEntity<Client>> updateClient(@PathVariable("id") String id,@Valid @RequestBody ClientRequest clientRequest) {
        return clientService.update(id,clientRequest)
                .map(client -> ResponseEntity.created(URI.create("/clients".concat(client.getId())))
                        .contentType(MediaType.APPLICATION_JSON).body(client))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    //READ
    @ApiOperation(value = "List all clients")
    @GetMapping(value = "/clients")
    public ResponseEntity<Flux<Client>> listClient(){
        return ResponseEntity.ok().body(clientService.readAll());
    }
    //DELETE
    @ApiOperation(value = "Deletes a client",
            notes = "Requires the client ID")
    @DeleteMapping(value = "/clients/{clientId}")
    public Mono<Client> deleteClient(@PathVariable(value = "clientId") String clientId){
        return clientService.delete(clientId);
    }
    //FIND ONE
    @ApiOperation(value = "List one client",
            notes = "Requires the client ID")
    @GetMapping(value = "/client/{clientId}")
    public Mono<Client> findOneClient(@PathVariable(value = "clientId") String clientId){
        return clientService.getOne(clientId);
    }
    //EXIST
    @ApiOperation(value = "Validates client",
            notes = "Requires the client ID: Returns True if a client is found and false if it isn't")
    @GetMapping(value = "/exist/{clientId}")
    public Mono<Boolean> existClient(@PathVariable(value = "clientId") String clientId){
        return clientService.isPresent(clientId);
    }

}
