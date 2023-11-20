package mindhub.HomebankingSprings.Service.Implement;

import mindhub.HomebankingSprings.Service.CardService;
import mindhub.HomebankingSprings.dtos.CardDTO;
import mindhub.HomebankingSprings.models.Card;
import mindhub.HomebankingSprings.models.Client;
import mindhub.HomebankingSprings.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CardServiceImpl implements CardService {
    @Autowired
    private CardRepository cardRepository;

    @Override
    public void saveCard(Card card) {
        cardRepository.save(card);
    }

    @Override
    public Set<CardDTO> findAllCard() {
        return cardRepository.findAll().stream().map(card -> new CardDTO(card)).collect(Collectors.toSet());
    }
    @Override
    public Card findCardById(Long id) {
        return cardRepository.findById(id).orElse(null);
    }

    @Override
    public Card findCardIdAndClient(Long id, Client client) {
        return cardRepository.findByIdAndClient(id,client);
    }
}
