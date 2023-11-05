package mindhub.HomebankingSprings.dtos;

public class LoanApplicationDTO {

    private Long id;
    private Double amount;
    private Integer payments;
    private String numberAccount;

    public LoanApplicationDTO (){

    }

    public Long getId() {
        return id;
    }

    public Double getAmount() {
        return amount;
    }

    public Integer getPayments() {
        return payments;
    }

    public String getNumberAccount() {
        return numberAccount;
    }
}
