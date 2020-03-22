package everis.bootcamp.clientMicroservice.Controller;

import everis.bootcamp.clientMicroservice.Document.ClientType;
import everis.bootcamp.clientMicroservice.Service.ClientTypeService;
import everis.bootcamp.clientMicroservice.ServiceDTO.Request.ClientTypeRequest;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/api")
public class ClientTypeController {

    @Autowired
    ClientTypeService clientTypeService;

    //CREATE
    @ApiOperation(value = "Creates new clientTypes")
    @PostMapping(value = "/clientTypes")
    public Mono<ClientType> createClientType(@Valid @RequestBody ClientType clientType){
        return clientTypeService.create(clientType);
    }
    //UPDATE
    @ApiOperation(value = "Updates clientTypes",
            notes = "Requires the clientType ID and all ClientType Request Params - Which are same ClientType Params" +
                    "excluding the ID")
    @PutMapping(value = "/clientTypes/{id}")
    public Mono<ResponseEntity<ClientType>> updateClientType(@PathVariable("id") String id, @Valid @RequestBody ClientTypeRequest clientTypeRequest) {
        return clientTypeService.update(id,clientTypeRequest)
                .map(clientType -> ResponseEntity.created(URI.create("/clientTypes".concat(clientType.getId())))
                        .contentType(MediaType.APPLICATION_JSON).body(clientType))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    //READ
    @ApiOperation(value = "List all clientTypes")
    @GetMapping(value = "/clientTypes")
    public ResponseEntity<Flux<ClientType>> listClientType(){
        return ResponseEntity.ok().body(clientTypeService.readAll());
    }
    //DELETE
    @ApiOperation(value = "Deletes a clientType",
            notes = "Requires the clientType ID")
    @DeleteMapping(value = "/clientTypes/{clientTypeId}")
    public Mono<ClientType> deleteClientType(@PathVariable(value = "clientTypeId") String clientTypeId){
        return clientTypeService.delete(clientTypeId);
    }
    //FIND ONE
    @ApiOperation(value = "List one clientType",
            notes = "Requires the clientType ID")
    @GetMapping(value = "/clientType/{clientTypeId}")
    public Mono<ClientType> findOneClientType(@PathVariable(value = "clientTypeId") String clientTypeId){
        return clientTypeService.getOne(clientTypeId);
    }
}
