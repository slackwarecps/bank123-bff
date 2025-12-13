package br.com.fabioalvaro.bank123.bffbank.repository;

import br.com.fabioalvaro.bank123.bffbank.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    Optional<Cliente> findByNumeroConta(Integer numeroConta);
}
