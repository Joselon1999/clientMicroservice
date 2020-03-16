package everis.bootcamp.clientMicroservice;
import static org.mockito.Mockito.times;
import everis.bootcamp.clientMicroservice.Document.Client;
import everis.bootcamp.clientMicroservice.Repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

@SpringBootTest
class ClientMicroserviceApplicationTests {

	@MockBean
	ClientRepository repository;

	@Autowired
	private WebTestClient webTestClient;

	@Test
	void testCreateEmployee() {
		Client client = new Client();
		client.setName("Test");
		client.setType("Type 1");
		client.setStatus("Status 1");

		Mockito.when(repository.save(client)).thenReturn(Mono.just(client));

		webClient.post()
				.uri("/api/clients/newClient")
				.contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromObject(client))
				.exchange()
				.expectStatus().isCreated();

		Mockito.verify(repository, times(1)).save(client);
	}

}
