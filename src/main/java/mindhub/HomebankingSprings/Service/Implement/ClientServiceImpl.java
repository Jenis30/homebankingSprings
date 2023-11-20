package mindhub.HomebankingSprings.Service.Implement;

import mindhub.HomebankingSprings.Service.ClientService;
import mindhub.HomebankingSprings.dtos.ClientDTO;
import mindhub.HomebankingSprings.models.Client;
import mindhub.HomebankingSprings.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Set<ClientDTO> getAllClient() {
        return clientRepository.findAll().stream().map(client -> new ClientDTO(client)).collect(Collectors.toSet());
    }

    @Override
    public ClientDTO findClientById(Long id) {
        return clientRepository.findById(id).map(client -> new ClientDTO(client)).orElse(null);
    }

    @Override
    public Client findClientByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    @Override
    public void saveClient(Client client) {
        clientRepository.save(client);
    }
}
