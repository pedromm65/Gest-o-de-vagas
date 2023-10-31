package com.pedrohlimadev.gestao_vagas.mdoules.candidate.useCases;

import com.pedrohlimadev.gestao_vagas.exceptions.UserFoundException;
import com.pedrohlimadev.gestao_vagas.mdoules.candidate.CandidateEntity;
import com.pedrohlimadev.gestao_vagas.mdoules.candidate.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    public CandidateEntity execute(CandidateEntity candidateEntity) {
        this.candidateRepository
                .findByUsernameOrEmail(candidateEntity.getUsername(), candidateEntity.getEmail())
                .ifPresent((user) -> {
                    throw new UserFoundException();
                });

        return this.candidateRepository.save(candidateEntity);
    }
}
