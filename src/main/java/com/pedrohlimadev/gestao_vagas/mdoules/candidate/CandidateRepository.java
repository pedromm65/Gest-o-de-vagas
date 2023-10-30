package com.pedrohlimadev.gestao_vagas.mdoules.candidate;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CandidateRepository  extends JpaRepository<CandidateEntity, UUID> {
}
