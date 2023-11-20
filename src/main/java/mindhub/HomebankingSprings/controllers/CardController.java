package mindhub.HomebankingSprings.controllers;

import mindhub.HomebankingSprings.Service.CardService;
import mindhub.HomebankingSprings.Service.ClientService;
import mindhub.HomebankingSprings.dtos.AccountDTO;
import mindhub.HomebankingSprings.dtos.CardDTO;
import mindhub.HomebankingSprings.models.Card;
import mindhub.HomebankingSprings.models.CardColor;
import mindhub.HomebankingSprings.models.CardType;
import mindhub.HomebankingSprings.models.Client;
import mindhub.HomebankingSprings.repositories.CardRepository;
import mindhub.HomebankingSprings.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    private CardService cardService;

    @Autowired
    private ClientService clientService;

    @PostMapping("/client/current/cards")
    public ResponseEntity<Object> createCard(Authentication authentication, @RequestParam CardType type, @RequestParam CardColor color) {
        Client client = clientService.findClientByEmail(authentication.getName());

        if (client == null) {
            throw new UsernameNotFoundException("unknow client" + authentication.getName());
        }
        if (client.getCards().stream().filter(card -> card.getType() == type && card.getActive()).count() < 3) {
            Card card = new Card((client.getFirstName() + client.getLastName()), (generateNumber(1, 10000) + " " + generateNumber(1, 10000) + " " + generateNumber(1, 10000) + " " + generateNumber(1, 10000)), generateCvv(1, 1000), LocalDate.now(), LocalDate.now().plusYears(5), type, color, true);
            cardService.saveCard(card);
            client.addCard(card);
            clientService.saveClient(client);
            return new ResponseEntity<>("Su tarjeta fue creada con exito", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Excedio limite de tarjetas", HttpStatus.FORBIDDEN);
        }
    }

    public String generateNumber(int min, int max) {
        Set<CardDTO> cards = cardService.findAllCard();
        Set<String> numberCard = cards.stream().map(card -> card.getNumber()).collect(Collectors.toSet());

        long number;
        String numberFormat;
        do {
            number = (int) ((Math.random() * (max - min)) + min);
            numberFormat = String.format("%04d", number);
        } while (numberCard.contains(numberFormat));
        return numberFormat;

    }

    public String generateCvv(int min, int max) {
        Set<CardDTO> cards = cardService.findAllCard();
        Set<String> numberCard = cards.stream().map(card -> card.getNumber()).collect(Collectors.toSet());

        String aux = "";
        long number;
        String numberComplete;
        do {
            number = (int) ((Math.random() * (max - min)) + min);
            String numberFormat = String.format("%03d", number);
            numberComplete = aux + number;
        } while (numberCard.contains(numberComplete));
        return numberComplete;
    }

    @PatchMapping("/cards/modify") //extra1
    public Object cardModify(@RequestParam Boolean active, @RequestParam Long id, Authentication authentication) {

        Client client = clientService.findClientByEmail(authentication.getName());
        Card modifyCard = cardService.findCardIdAndClient(id, client);

        if (modifyCard == null) {
            return new ResponseEntity<>("Card not found", HttpStatus.FORBIDDEN);
        }
        modifyCard.setActive(active);
        cardService.saveCard(modifyCard);
        return new CardDTO(modifyCard);
    }
}