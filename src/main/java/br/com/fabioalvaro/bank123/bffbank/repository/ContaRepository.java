package br.com.fabioalvaro.bank123.bffbank.repository;

import br.com.fabioalvaro.bank123.bffbank.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Integer> {
    Optional<Conta> findByEmail(String email);
    Optional<Conta> findByIdUserFirebase(String idUserFirebase);
}