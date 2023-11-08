package com.pedrohlimadev.gestao_vagas.modules.company.useCases;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.pedrohlimadev.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import com.pedrohlimadev.gestao_vagas.modules.company.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;

@Service
public class AuthCompanyUseCase {

    @Value("${security.token.secret}")
    private String secretKey;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String execute(AuthCompanyDTO authCompanyDTO) throws AuthenticationException {
        var comapny = this.companyRepository.findByUsername(authCompanyDTO.getUsername()).orElseThrow(() -> {
            throw new UsernameNotFoundException("Comapny not found");
        });

        //VERFIFICAR SE AS SENHAS SÃO IGUAIS
        var passwordMatchs = this.passwordEncoder.matches(authCompanyDTO.getPassword(), comapny.getPassword());

        if(!passwordMatchs) {
            throw new AuthenticationException();
        }

        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        var token = JWT.create().withIssuer("javagas")
                .withSubject(comapny.getId().toString())
                .sign(algorithm);

        return token;
    }
}
