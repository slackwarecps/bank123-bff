package br.com.fabioalvaro.bank123.bffbank.service;

import br.com.fabioalvaro.bank123.bffbank.dto.PerfilResponse;
import br.com.fabioalvaro.bank123.bffbank.model.Cliente;
import br.com.fabioalvaro.bank123.bffbank.model.Conta;
import br.com.fabioalvaro.bank123.bffbank.repository.ClienteRepository;
import br.com.fabioalvaro.bank123.bffbank.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public PerfilResponse getPerfil(String email) {
        
        // 1. Busca a conta pelo e-mail
        Optional<Conta> contaOptional = contaRepository.findByEmail(email);

        if (contaOptional.isEmpty()) {
            throw new RuntimeException("Conta não encontrada para o e-mail: " + email);
        }

        Conta conta = contaOptional.get();

        // 2. Tenta buscar o cliente associado à conta
        Optional<Cliente> clienteOptional = clienteRepository.findByNumeroConta(conta.getNumeroConta());

        String nomeCompleto = null;
        String endereco = null;

        if (clienteOptional.isPresent()) {
            Cliente cliente = clienteOptional.get();
            nomeCompleto = cliente.getNomeCompleto();
            endereco = String.format("%s, %s - %s, %s - %s, %s",
                    cliente.getEnderecoLogradouro(),
                    cliente.getEnderecoNumero(),
                    cliente.getEnderecoBairro(),
                    cliente.getEnderecoCidade(),
                    cliente.getEnderecoEstado(),
                    cliente.getEnderecoCep());
        } else {
            // Caso onde a conta existe (via onboarding) mas o cliente ainda não completou o cadastro
            nomeCompleto = conta.getEmail(); // Fallback para o email ou outro identificador
            endereco = "Cadastro incompleto";
        }

        // 3. Monta a resposta com dados da Conta e do Cliente (se existir)
        return new PerfilResponse(
                nomeCompleto,
                endereco,
                "0001", // Agência fixa por enquanto
                conta.getNumeroConta(),
                conta.getIdUserFirebase(),
                conta.getStatus()
        );
    }
}
