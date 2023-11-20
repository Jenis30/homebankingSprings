package mindhub.HomebankingSprings.dtos;

import mindhub.HomebankingSprings.models.Card;
import mindhub.HomebankingSprings.models.CardColor;
import mindhub.HomebankingSprings.models.CardType;

import java.time.LocalDate;

public class CardDTO {

    private long id;
    private String cardHolder;
    private String number;
    private String cvv;
    private LocalDate fromDate;
    private LocalDate thruDate;
    private CardType type;
    private CardColor color;
    private Boolean active;

    public CardDTO() {
    }

    public CardDTO(Card card) {
        this.id = card.getId();
        this.cardHolder= card.getCardHolder();
        this.number = card.getNumber();
        this.cvv = card.getCvv();
        this.fromDate = card.getFromDate();
        this.thruDate = card.getThruDate();
        this.type = card.getType();
        this.color = card.getColor();
        this.active = card.getActive();
    }

    public long getId() {
        return id;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public String getNumber() {
        return number;
    }

    public String getCvv() {
        return cvv;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public CardType getType() {
        return type;
    }

    public Boolean getActive() {return active;}

    public CardColor getColor() {
        return color;
    }
}

