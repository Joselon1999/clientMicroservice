package everis.bootcamp.clientMicroservice.Repository;

import everis.bootcamp.clientMicroservice.Document.Client;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends ReactiveMongoRepository<Client, String> {

}
