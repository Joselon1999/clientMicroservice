package everis.bootcamp.clientMicroservice.Repository;

import everis.bootcamp.clientMicroservice.Document.ClientType;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ClientTypeRepository extends ReactiveMongoRepository<ClientType, String> {

    Mono<ClientType> findByName(String name);
}
