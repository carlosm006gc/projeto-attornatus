package com.dev.testeAttornatus.services;

import com.dev.testeAttornatus.domain.Pessoa;
import com.dev.testeAttornatus.repositories.PessoaRepository;
import com.dev.testeAttornatus.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    public Pessoa criarPessoa(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    public Pessoa editarPessoa(UUID id, Pessoa pessoa) {
        Pessoa pessoaExistente = pessoaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada com id " + id));
        pessoaExistente.setNome(pessoa.getNome());
        pessoaExistente.setDataNascimento(pessoa.getDataNascimento());
        return pessoaRepository.save(pessoaExistente);
    }

    public Pessoa consultarPessoa(UUID id) {
        return pessoaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada com id " + id));
    }

    public List<Pessoa> listarPessoas() {
        return pessoaRepository.findAll();
    }
}