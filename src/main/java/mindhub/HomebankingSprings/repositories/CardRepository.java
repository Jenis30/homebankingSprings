package mindhub.HomebankingSprings.repositories;

import mindhub.HomebankingSprings.models.Card;
import mindhub.HomebankingSprings.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CardRepository extends JpaRepository<Card,Long> {

    Card findByIdAndClient(Long id, Client client);
}
