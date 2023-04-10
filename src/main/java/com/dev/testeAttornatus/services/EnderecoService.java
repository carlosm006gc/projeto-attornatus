package com.dev.testeAttornatus.services;

import com.dev.testeAttornatus.domain.Endereco;
import com.dev.testeAttornatus.domain.Pessoa;
import com.dev.testeAttornatus.repositories.EnderecoRepository;
import com.dev.testeAttornatus.repositories.PessoaRepository;
import com.dev.testeAttornatus.services.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EnderecoService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    public Endereco criarEndereco(UUID pessoaId, Endereco endereco) {
        Pessoa pessoa = pessoaRepository.findById(pessoaId)
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada com id " + pessoaId));
        endereco.setPessoa(pessoa);
        pessoa.getEnderecos().add(endereco);
        enderecoRepository.save(endereco);
        pessoaRepository.save(pessoa);
        return endereco;
    }


    public List<Endereco> listarEnderecos(UUID pessoaId) {
        Pessoa pessoa = pessoaRepository.findById(pessoaId).orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada com id " + pessoaId));
        return pessoa.getEnderecos();
    }

    public Endereco definirEnderecoPrincipal(UUID pessoaId, UUID enderecoId) {
        Pessoa pessoa = pessoaRepository.findById(pessoaId).orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada com id " + pessoaId));
        Endereco endereco = pessoa.getEnderecos().stream()
                .filter(e -> e.getId().equals(enderecoId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Endereço não encontrado com id " + enderecoId));

        pessoa.getEnderecos().forEach(e -> e.setPrincipal(false));
        endereco.setPrincipal(true);
        pessoaRepository.save(pessoa);
        return endereco;
    }

}