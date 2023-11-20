package mindhub.HomebankingSprings.Service;

import mindhub.HomebankingSprings.dtos.CardDTO;
import mindhub.HomebankingSprings.models.Card;
import mindhub.HomebankingSprings.models.Client;

import java.util.Set;

public interface CardService {
    void saveCard(Card card);

    Set<CardDTO> findAllCard();

    Card findCardById(Long id);

    Card findCardIdAndClient(Long id, Client client);

}
