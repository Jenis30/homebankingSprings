package mindhub.HomebankingSprings.repositories;

import mindhub.HomebankingSprings.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource  //  exponemos  los datos utilizando el servicio rest a traves de peticiones http
public interface ClientRepository extends JpaRepository<Client,Long> {

    Client findByEmail(String email);

}
