package mindhub.HomebankingSprings.Service.Implement;

import mindhub.HomebankingSprings.Service.ClientLoanService;
import mindhub.HomebankingSprings.models.ClientLoan;
import mindhub.HomebankingSprings.repositories.ClientLoanRepository;
import mindhub.HomebankingSprings.repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientLoanServiceImpl implements ClientLoanService {
    @Autowired
    private ClientLoanRepository clientLoanRepository;
    @Override
    public void saveClientLoan(ClientLoan clientLoan){
        clientLoanRepository.save(clientLoan);
    }
}
