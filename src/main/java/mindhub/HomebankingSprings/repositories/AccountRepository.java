package mindhub.HomebankingSprings.repositories;

import mindhub.HomebankingSprings.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource // expone los datos a rest
public interface AccountRepository extends JpaRepository<Account,Long> {

    public Account findByNumber(String number);

     public Boolean  existsByNumber(String number);

}
