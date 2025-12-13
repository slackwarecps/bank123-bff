package br.com.fabioalvaro.bank123.bffbank.repository;

import br.com.fabioalvaro.bank123.bffbank.model.LivroCaixa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LivroCaixaRepository extends JpaRepository<LivroCaixa, Integer> {
    // Select * from livrocaixa where numeroconta = ?
    List<LivroCaixa> findByNumeroConta(Integer numeroConta);
}