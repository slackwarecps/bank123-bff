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

    public PerfilResponse getPerfil(Integer xAccountId, String xCorrelationId, String authorization) {
        
        // Busca o cliente pelo numero da conta recebido no header x-account-id
        Optional<Cliente> clienteOptional = clienteRepository.findByNumeroConta(xAccountId);

        if (clienteOptional.isEmpty()) {
            // Se não encontrar o cliente, lança uma exceção.
            throw new RuntimeException("Cliente não encontrado para a conta: " + xAccountId);
        }

        Cliente cliente = clienteOptional.get();

        // Busca a conta pelo numero da conta do cliente
        Optional<Conta> contaOptional = contaRepository.findById(cliente.getNumeroConta());

        if (contaOptional.isEmpty()) {
            // Se não encontrar a conta, lança uma exceção.
            throw new RuntimeException("Conta não encontrada para o cliente: " + cliente.getNomeCompleto());
        }

        Conta conta = contaOptional.get();

        String endereco = String.format("%s, %s - %s, %s - %s, %s",
                cliente.getEnderecoLogradouro(),
                cliente.getEnderecoNumero(),
                cliente.getEnderecoBairro(),
                cliente.getEnderecoCidade(),
                cliente.getEnderecoEstado(),
                cliente.getEnderecoCep());


        // Mapeia os dados da entidade Conta para o DTO de resposta
        return new PerfilResponse(
                cliente.getNomeCompleto(),
                endereco,
                "Não aplicável",
                conta.getNumeroConta()
        );
    }
}
