package mindhub.HomebankingSprings.repositories;

import mindhub.HomebankingSprings.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource  //queremos exponer los datos cuando queramos
public interface ClientRepository extends JpaRepository<Client,Long> {
}
