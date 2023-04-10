package com.dev.testeAttornatus;

import com.dev.testeAttornatus.domain.Endereco;
import com.dev.testeAttornatus.domain.Pessoa;
import com.dev.testeAttornatus.repositories.EnderecoRepository;
import com.dev.testeAttornatus.repositories.PessoaRepository;
import com.dev.testeAttornatus.services.EnderecoService;
import com.dev.testeAttornatus.services.PessoaService;
import com.dev.testeAttornatus.services.exceptions.ResourceNotFoundException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class TesteAttornatusApplicationTests {

    @Autowired
    private PessoaService pessoaService;
    @Autowired
    private EnderecoService enderecoService;
    @MockBean
    private EnderecoRepository enderecoRepository;
    @MockBean
    private PessoaRepository pessoaRepository;

    @Test
    public void testCriarPessoa() {
        Pessoa pessoa = new Pessoa("João", LocalDate.of(2000, 1, 1));
        when(pessoaRepository.save(pessoa)).thenReturn(pessoa);
        Pessoa pessoaSalva = pessoaService.criarPessoa(pessoa);
        assertEquals(pessoa.getNome(), pessoaSalva.getNome());
        assertEquals(pessoa.getDataNascimento(), pessoaSalva.getDataNascimento());
    }

    @Test
    public void testEditarPessoa() {
        UUID id = UUID.randomUUID();
        Pessoa pessoaExistente = new Pessoa("João", LocalDate.of(2000, 1, 1));
        pessoaExistente.setId(id);
        Pessoa pessoaAtualizada = new Pessoa("Maria", LocalDate.of(2001, 2, 2));
        when(pessoaRepository.findById(id)).thenReturn(Optional.of(pessoaExistente));
        when(pessoaRepository.save(pessoaExistente)).thenReturn(pessoaExistente);
        Pessoa pessoaEditada = pessoaService.editarPessoa(id, pessoaAtualizada);
        assertEquals(pessoaAtualizada.getNome(), pessoaEditada.getNome());
        assertEquals(pessoaAtualizada.getDataNascimento(), pessoaEditada.getDataNascimento());
    }

    @Test
    public void testConsultarPessoa() {
        UUID id = UUID.randomUUID();
        Pessoa pessoa = new Pessoa("João", LocalDate.of(2000, 1, 1));
        pessoa.setId(id);
        when(pessoaRepository.findById(id)).thenReturn(Optional.of(pessoa));
        Pessoa pessoaEncontrada = pessoaService.consultarPessoa(id);
        assertEquals(pessoa.getNome(), pessoaEncontrada.getNome());
        assertEquals(pessoa.getDataNascimento(), pessoaEncontrada.getDataNascimento());
    }

    @Test
    public void testListarPessoas() {
        List<Pessoa> listaPessoas = Arrays.asList(new Pessoa("João", LocalDate.of(2000, 1, 1)), new Pessoa("Maria", LocalDate.of(2001, 2, 2)), new Pessoa("José", LocalDate.of(2002, 3, 3)));
        when(pessoaRepository.findAll()).thenReturn(listaPessoas);
        List<Pessoa> listaEncontrada = pessoaService.listarPessoas();
        assertEquals(listaPessoas.size(), listaEncontrada.size());
        for (int i = 0; i < listaPessoas.size(); i++) {
            Pessoa pessoa = listaPessoas.get(i);
            Pessoa pessoaEncontrada = listaEncontrada.get(i);
            assertEquals(pessoa.getNome(), pessoaEncontrada.getNome());
            assertEquals(pessoa.getDataNascimento(), pessoaEncontrada.getDataNascimento());
        }
    }

    @Test
    public void testCriarEndereco() {
        // Cria uma pessoa
        Pessoa pessoa = new Pessoa();
        pessoa.setId(UUID.randomUUID());
        pessoa.setNome("João");

        // Cria um endereço
        Endereco endereco = new Endereco();
        endereco.setId(UUID.randomUUID());
        endereco.setLogradouro("Rua Teste");
        endereco.setNumero(123);
        endereco.setCidade("Cidade Teste");

        // Configura o comportamento do mock do repositório de pessoas para retornar a pessoa criada acima
        Mockito.when(pessoaRepository.findById(pessoa.getId())).thenReturn(Optional.of(pessoa));

        // Configura o comportamento do mock do repositório de endereços para retornar o endereço criado acima
        Mockito.when(enderecoRepository.save(any(Endereco.class))).thenReturn(endereco);

        // Chama o método a ser testado passando os parâmetros corretos
        Endereco enderecoCriado = enderecoService.criarEndereco(pessoa.getId(), endereco);

        // Verifica se o endereço retornado é o mesmo que foi criado
        Assert.assertEquals(endereco, enderecoCriado);

        // Verifica se a pessoa tem o endereço criado adicionado à sua lista de endereços
        Assert.assertTrue(pessoa.getEnderecos().contains(enderecoCriado));
    }

    @Test
    public void testDefinirEnderecoPrincipal() {
        // Cria uma pessoa e dois endereços para ela
        Pessoa pessoa = new Pessoa();
        UUID pessoaId = UUID.randomUUID();
        pessoa.setId(pessoaId);

        Endereco endereco1 = new Endereco();
        UUID enderecoId1 = UUID.randomUUID();
        endereco1.setId(enderecoId1);
        endereco1.setLogradouro("Rua A");
        endereco1.setPessoa(pessoa);

        Endereco endereco2 = new Endereco();
        UUID enderecoId2 = UUID.randomUUID();
        endereco2.setId(enderecoId2);
        endereco2.setLogradouro("Rua B");
        endereco2.setPessoa(pessoa);

        List<Endereco> enderecos = Arrays.asList(endereco1, endereco2);
        pessoa.setEnderecos(enderecos);

        // Define o endereço 2 como principal
        when(pessoaRepository.findById(pessoaId)).thenReturn(Optional.of(pessoa));
        when(enderecoRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        Endereco enderecoPrincipal = enderecoService.definirEnderecoPrincipal(pessoaId, enderecoId2);

        // Verifica se o endereço 2 é o principal e se o endereço 1 não é mais principal
        assertThat(enderecoPrincipal.isPrincipal()).isTrue();
        assertThat(endereco1.isPrincipal()).isFalse();
    }

    @Test
    public void testDefinirEnderecoPrincipalPessoaNaoEncontrada() {
        // Tenta definir um endereço como principal para uma pessoa que não existe
        UUID pessoaId = UUID.randomUUID();
        UUID enderecoId = UUID.randomUUID();

        when(pessoaRepository.findById(pessoaId)).thenReturn(Optional.empty());

        // Verifica se a exceção é lançada
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            enderecoService.definirEnderecoPrincipal(pessoaId, enderecoId);
        });

        assertThat(exception.getMessage()).isEqualTo("Pessoa não encontrada com id " + pessoaId);
        verify(enderecoRepository, never()).save(any());
    }

    @Test
    public void testListarEnderecos() {
        // Cria uma pessoa e adiciona 2 endereços a ela
        Pessoa pessoa = new Pessoa();
        UUID pessoaId = UUID.randomUUID();
        pessoa.setId(pessoaId);

        Endereco endereco1 = new Endereco();
        UUID enderecoId1 = UUID.randomUUID();
        endereco1.setId(enderecoId1);
        endereco1.setLogradouro("Rua A");
        endereco1.setPessoa(pessoa);

        Endereco endereco2 = new Endereco();
        UUID enderecoId2 = UUID.randomUUID();
        endereco2.setId(enderecoId2);
        endereco2.setLogradouro("Rua B");
        endereco2.setPessoa(pessoa);

        List<Endereco> enderecos = Arrays.asList(endereco1, endereco2);
        pessoa.setEnderecos(enderecos);

        when(pessoaRepository.findById(pessoaId)).thenReturn(Optional.of(pessoa));

        // Chama o método de listar endereços e verifica se retorna os 2 endereços criados acima
        List<Endereco> enderecosEncontrados = enderecoService.listarEnderecos(pessoaId);
        assertEquals(2, enderecosEncontrados.size());
        assertTrue(enderecosEncontrados.contains(endereco1));
        assertTrue(enderecosEncontrados.contains(endereco2));
    }


    @Test
    public void testDefinirEnderecoPrincipalEnderecoNaoEncontrado() {
        // Cria uma pessoa e um endereço, mas tenta definir um endereço que não existe como principal
        Pessoa pessoa = new Pessoa();
        UUID pessoaId = UUID.randomUUID();
        pessoa.setId(pessoaId);

        Endereco endereco = new Endereco();
        UUID enderecoId = UUID.randomUUID();
        endereco.setId(enderecoId);
        endereco.setLogradouro("Rua A");
        endereco.setPessoa(pessoa);

        List<Endereco> enderecos = Arrays.asList(endereco);
        pessoa.setEnderecos(enderecos);

        UUID enderecoIdNaoExistente = UUID.randomUUID();

        when(pessoaRepository.findById(pessoaId)).thenReturn(Optional.of(pessoa));

        // Verifica se a exceção é lançada
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                enderecoService.definirEnderecoPrincipal(pessoa.getId(), enderecoIdNaoExistente));
        assertEquals("Endereço não encontrado com id " + enderecoIdNaoExistente, exception.getMessage());
    }
}
