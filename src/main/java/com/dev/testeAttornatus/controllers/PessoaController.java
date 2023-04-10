package com.dev.testeAttornatus.controllers;

import com.dev.testeAttornatus.domain.Endereco;
import com.dev.testeAttornatus.domain.Pessoa;
import com.dev.testeAttornatus.services.EnderecoService;
import com.dev.testeAttornatus.services.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {
    @Autowired
    private PessoaService pessoaService;
    @Autowired
    private EnderecoService enderecoService;

    @GetMapping
    public List<Pessoa> listarPessoas() {
        return pessoaService.listarPessoas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> consultarPessoa(@PathVariable UUID id) {
        Pessoa pessoa = pessoaService.consultarPessoa(id);
        if (pessoa != null) {
            return ResponseEntity.ok(pessoa);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pessoa criarPessoa(@RequestBody Pessoa pessoa) {
        return pessoaService.criarPessoa(pessoa);
    }

    @PutMapping("/{id}")
    public Pessoa editarPessoa(@PathVariable UUID id, @RequestBody Pessoa pessoa) {
        return pessoaService.editarPessoa(id, pessoa);
    }

    @PostMapping("/{id}/enderecos")
    @ResponseStatus(HttpStatus.CREATED)
    public Endereco criarEndereco(@PathVariable UUID id, @RequestBody Endereco endereco) {
        return enderecoService.criarEndereco(id, endereco);
    }

    @GetMapping("/{id}/enderecos")
    public List<Endereco> listarEnderecos(@PathVariable UUID id) {
        return enderecoService.listarEnderecos(id);
    }

    @PutMapping("/{id}/enderecos/{enderecoId}/principal")
    public Endereco definirEnderecoPrincipal(@PathVariable UUID id, @PathVariable UUID enderecoId) {
        return enderecoService.definirEnderecoPrincipal(id, enderecoId);
    }
}
