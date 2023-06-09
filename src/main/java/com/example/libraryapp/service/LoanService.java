package com.example.libraryapp.service;

import com.example.libraryapp.exception.LoanNotFoundException;
import com.example.libraryapp.model.Loan;
import com.example.libraryapp.repository.LoanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LoanService {
    private final LoanRepository loanRepository;

    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public void updateLoan(Loan loan) {
        loanRepository.save(loan);
    }

    public Loan findLoanById(long id) {
        return loanRepository.findById(id)
                .orElseThrow(() -> new LoanNotFoundException("No loan found with the id: %d".formatted(id)));
    }

    public void deleteLoanById(long id) {
        loanRepository.deleteById(id);
    }
}
