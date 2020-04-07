package everis.bootcamp.clientMicroservice;

import everis.bootcamp.clientMicroservice.Document.Client;
import everis.bootcamp.clientMicroservice.Document.ClientType;
import everis.bootcamp.clientMicroservice.Repository.ClientRepository;
import everis.bootcamp.clientMicroservice.Service.ClientService;
import everis.bootcamp.clientMicroservice.Service.ClientServiceImpl;
import everis.bootcamp.clientMicroservice.ServiceDTO.Request.ClientRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class ClientServiceTest {

    @MockBean
    private ClientRepository repository;

    private ClientService clientService;

    private final ClientType clientType1 = ClientType.builder().name("TYPE 1").build();
    private final ClientType clientType2 = ClientType.builder().name("TYPE 2").build();
    private final Client client1 = Client.builder().id("1").name("USUARIO 1").clientType(clientType1).bankId("1").status("EXIST").build();
    private final Client client2 = Client.builder().id("2").name("USUARIO 2").clientType(clientType2).bankId("1").status("EXIST").build();
    private final Client client3 = Client.builder().name("USUARIO 3").clientType(clientType2).bankId("1").status("EXIST").build();
    private final ClientRequest request1 = ClientRequest.builder().name("USUARIO 1").idClientType(clientType1.getId()).bankId("1").status("EXIST").build();
    private final ClientRequest request2 = ClientRequest.builder().name("USUARIO 2").idClientType(clientType2.getId()).bankId("1").status("EXIST").build();
    private final ClientRequest request3 = ClientRequest.builder().name("USUARIO 3").idClientType(clientType2.getId()).bankId("1").status("EXIST").build();

    @BeforeEach
    void setUp() {
        clientService = new ClientServiceImpl(repository) {
        };
    }
        @Test
        void readAll(){
            when(repository.findAll()).thenReturn(Flux.just(client1,client2,client3));
            Flux<Client> actual = clientService.readAll();
            assertResults(actual, client1,client2,client3);
        }
    @Test
    void getOne(){
        final String id ="1";
        when(repository.findById(id)).thenReturn(Mono.just(client1));
        Mono<Client> actual = clientService.getOne(id);
        assertResults(actual, client1);
    }
    @Test
    void create() {
        when(repository.save(client3)).thenReturn(Mono.just(client3));
        Mono<Client> actual = clientService.create(request3);
        assertResults(actual, client3);
    }

    @Test
    void update() {
        when(repository.findById(client3.getId())).thenReturn(Mono.just(client3));
        Mono<Client> actual = clientService.update(client3.getId(), request3);
        assertResults(actual);
    }

    @Test
    void delete() {
        when(repository.findById(client1.getId())).thenReturn(Mono.just(client1));
        when(repository.delete(client1)).thenReturn(Mono.empty());
        Mono<Client> actual = clientService.delete(client1.getId());
        assertResults(actual, client1);
    }

    private void assertResults(Publisher<Client> publisher, Client... expectedClient) {
        StepVerifier
                .create(publisher)
                .expectNext(expectedClient)
                .verifyComplete();
    }

    private void assertResults(Mono<Boolean> actual, boolean b) {
        StepVerifier
                .create(actual)
                .expectNext(b)
                .verifyComplete();
    }

    private void assertResults(Mono<ClientType> actual, ClientType clientType) {
        StepVerifier
                .create(actual)
                .expectNext(clientType)
                .verifyComplete();
    }


}
