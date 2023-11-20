package mindhub.HomebankingSprings.Service;

import mindhub.HomebankingSprings.dtos.LoanDTO;
import mindhub.HomebankingSprings.models.Loan;

import javax.persistence.SecondaryTable;
import java.util.Set;

public interface LoanService {
    Loan findLoanById(Long id);

    void saveLoan(Loan loan);

    Set<LoanDTO> findAllLoan();

    Boolean existsByName(String name);
}
