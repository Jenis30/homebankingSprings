package mindhub.HomebankingSprings.Service;

import mindhub.HomebankingSprings.dtos.ClientDTO;
import mindhub.HomebankingSprings.models.Client;

import java.util.Set;

public interface ClientService {
    Set<ClientDTO>getAllClient();

    ClientDTO findClientById (Long id);

    Client findClientByEmail(String email);
    void saveClient(Client client);
}
