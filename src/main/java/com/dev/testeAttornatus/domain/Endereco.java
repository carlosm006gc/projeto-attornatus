package com.dev.testeAttornatus.domain;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String logradouro;

    @Column(nullable = false)
    private String cep;

    @Column(nullable = false)
    private Integer numero;
    @Column(nullable = false)
    private boolean principal;
    @Column(nullable = false)
    private String cidade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;

    public Endereco() {
    }

    public Endereco(String logradouro, String cep, Integer numero, boolean principal, String cidade) {
        this.logradouro = logradouro;
        this.cep = cep;
        this.numero = numero;
        this.principal = principal;
        this.cidade = cidade;
    }
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }
    public void setPrincipal(boolean principal) {
        this.principal = principal;
    }

    public boolean isPrincipal() {
        return principal;
    }
    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
}
