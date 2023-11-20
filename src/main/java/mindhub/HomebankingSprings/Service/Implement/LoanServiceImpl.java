package mindhub.HomebankingSprings.Service.Implement;

import mindhub.HomebankingSprings.Service.LoanService;
import mindhub.HomebankingSprings.dtos.LoanDTO;
import mindhub.HomebankingSprings.models.Loan;
import mindhub.HomebankingSprings.repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
@Service
public class LoanServiceImpl implements LoanService {
    @Autowired
    private LoanRepository loanRepository;
    @Override
    public Loan findLoanById(Long id) {
        return loanRepository.findById(id).orElse(null);
    }

    @Override
    public void saveLoan(Loan loan) {
        loanRepository.save(loan);
    }

    @Override
    public Set<LoanDTO> findAllLoan() {
        return loanRepository.findAll().stream().map(loandb -> new LoanDTO(loandb)).collect(Collectors.toSet());
    }

    @Override
    public Boolean existsByName(String name) {
        return loanRepository.existsByName(name);
    }
}
