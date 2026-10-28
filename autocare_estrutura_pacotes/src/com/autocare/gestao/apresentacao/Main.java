package com.autocare.gestao.apresentacao;

import com.autocare.gestao.excecoes.EstadoInvalidoException;
import com.autocare.gestao.excecoes.PersistenciaException;
import com.autocare.gestao.excecoes.RegistoNaoEncontradoException;
import com.autocare.gestao.excecoes.ValidacaoException;
import com.autocare.gestao.modelo.Cliente;
import com.autocare.gestao.modelo.EstadoMarcacao;
import com.autocare.gestao.modelo.Intervencao;
import com.autocare.gestao.modelo.Marcacao;
import com.autocare.gestao.modelo.Mecanico;
import com.autocare.gestao.modelo.Viatura;
import com.autocare.gestao.repositorio.ClienteRepositorio;
import com.autocare.gestao.repositorio.IntervencaoRepositorio;
import com.autocare.gestao.repositorio.MarcacaoRepositorio;
import com.autocare.gestao.repositorio.MecanicoRepositorio;
import com.autocare.gestao.repositorio.ViaturaRepositorio;
import com.autocare.gestao.servico.ClienteServico;
import com.autocare.gestao.servico.IntervencaoServico;
import com.autocare.gestao.servico.MarcacaoServico;
import com.autocare.gestao.servico.MecanicoServico;
import com.autocare.gestao.servico.ViaturaServico;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Ponto de entrada da aplicação AutoCare.
 *
 * A partir da Aula 8 (Tarefa 8.1), a persistência é feita através de JDBC
 * numa base de dados MySQL, em vez de ficheiro. IMPORTANTE: a camada
 * servico (ClienteServico, ViaturaServico, MarcacaoServico, IntervencaoServico)
 * não sofreu qualquer alteração face à Aula 7 — só mudou a forma como os
 * repositórios são construídos aqui, o que confirma que a arquitetura em
 * camadas da Aula 4 estava bem desenhada.
 *
 * NOTA: como o NIF do cliente é UNIQUE na base de dados, os dados de teste
 * são gerados com base na hora atual, para que possas correr este programa
 * repetidamente sem erros de duplicação.
 */
public class Main {

    private static final String URL_BASE_DADOS = "jdbc:mysql://localhost:3306/autocare";
    private static final String UTILIZADOR = "autocare_app";
    private static final String PALAVRA_PASSE = "autocare123";

    public static void main(String[] args) {
        try (Connection ligacao = DriverManager.getConnection(URL_BASE_DADOS, UTILIZADOR, PALAVRA_PASSE)) {

            ClienteRepositorio clienteRepositorio = new ClienteRepositorio(ligacao);
            ViaturaRepositorio viaturaRepositorio = new ViaturaRepositorio(ligacao);
            MecanicoRepositorio mecanicoRepositorio = new MecanicoRepositorio(ligacao);
            MarcacaoRepositorio marcacaoRepositorio = new MarcacaoRepositorio(ligacao);
            IntervencaoRepositorio intervencaoRepositorio = new IntervencaoRepositorio(ligacao);

            ClienteServico clienteServico = new ClienteServico(clienteRepositorio);
            ViaturaServico viaturaServico = new ViaturaServico(viaturaRepositorio);
            MecanicoServico mecanicoServico = new MecanicoServico(mecanicoRepositorio);
            MarcacaoServico marcacaoServico = new MarcacaoServico(marcacaoRepositorio);
            IntervencaoServico intervencaoServico = new IntervencaoServico(intervencaoRepositorio);

            long semente = System.currentTimeMillis();

            testarClientes(clienteServico, semente);
            testarFluxoCompleto(clienteServico, viaturaServico, mecanicoServico, marcacaoServico, intervencaoServico, semente);

        } catch (SQLException erro) {
            System.err.println("Não foi possível ligar à base de dados: " + erro.getMessage());
        } catch (PersistenciaException erro) {
            System.err.println("Erro de persistência: " + erro.getMessage());
            if (erro.getCause() != null) {
                System.err.println("Causa real (MySQL): " + erro.getCause().getMessage());
            }
        }
    }

    private static void testarClientes(ClienteServico clienteServico, long semente) throws PersistenciaException {
        System.out.println("=== Teste: Clientes via JDBC (Tarefas 5.2, 6.2/6.3 e 8.1) ===");

        Cliente ana = clienteServico.registarCliente("Ana Ferreira", gerarContacto(semente), gerarNifValido(semente));
        System.out.println("Cliente criado na base de dados: " + ana.getNome() + " (id " + ana.getIdCliente() + ")");

        List<Cliente> todos = clienteServico.listarClientes();
        System.out.println("Total de clientes na base de dados: " + todos.size());

        try {
            clienteServico.registarCliente("Carlos Silva", "000000000", "228674374");
        } catch (ValidacaoException erro) {
            System.out.println("Validação (6.2) continua a funcionar sem alterações: " + erro.getMessage());
        }

        ana.setNome("Ana Ferreira Santos");
        clienteServico.atualizarCliente(ana);
        System.out.println("Cliente atualizado: " + clienteServico.consultarCliente(ana.getIdCliente()).getNome());

        try {
            clienteServico.removerCliente(999999L);
        } catch (RegistoNaoEncontradoException erro) {
            System.out.println("Remoção de id inexistente tratada corretamente: " + erro.getMessage());
        }
    }

    private static void testarFluxoCompleto(ClienteServico clienteServico, ViaturaServico viaturaServico,
                                            MecanicoServico mecanicoServico, MarcacaoServico marcacaoServico,
                                            IntervencaoServico intervencaoServico, long semente) throws PersistenciaException {
        System.out.println();
        System.out.println("=== Teste: Fluxo completo (RF02 a RF07, 7.1, 8.1 e 8.2) ===");

        // A alteração foi feita aqui: de semente + 1 para semente + 10
        Cliente cliente = clienteServico.registarCliente("Elsa Marques", gerarContacto(semente + 10), gerarNifValido(semente + 10));
        Viatura viatura = viaturaServico.registarViatura("11-BB-" + (semente % 100), "Peugeot", "208", 2020, cliente);
        System.out.println("Viatura registada (RF02): " + viatura.getMatricula() + " (id " + viatura.getIdViatura() + ")");

        Mecanico mecanico = mecanicoServico.registarMecanico("Sofia Lima", "Elétrica");
        System.out.println("Mecânico registado: " + mecanico.getNome() + " (id " + mecanico.getIdMecanico() + ")");

        LocalDate dataTeste = LocalDate.of(2026, 9, 1);
        LocalTime horaTeste = LocalTime.of(9, 30);

        Marcacao marcacao = marcacaoServico.criarMarcacao(viatura, mecanico, dataTeste, horaTeste);
        System.out.println("Marcação criada (RF03): id " + marcacao.getIdMarcacao() + ", estado " + marcacao.getEstado());

        try {
            marcacaoServico.criarMarcacao(viatura, mecanico, dataTeste, horaTeste);
        } catch (ValidacaoException erro) {
            System.out.println("RF04 continua a funcionar sem alterações: " + erro.getMessage());
        }

        List<Marcacao> marcacoesDoDia = marcacaoServico.listarMarcacoesDoDia(dataTeste);
        System.out.println("Marcações do dia " + dataTeste + " (RF07): " + marcacoesDoDia.size());

        List<Marcacao> pesquisaCombinada = marcacaoServico.pesquisar(EstadoMarcacao.AGENDADA, mecanico.getIdMecanico(), dataTeste);
        System.out.println("Pesquisa combinada estado+mecânico (7.1): " + pesquisaCombinada.size() + " resultado(s)");

        Intervencao intervencao = intervencaoServico.registarIntervencao(
                marcacao, "Substituição da bateria", "1x bateria 60Ah", 95.0, dataTeste);
        System.out.println("Intervenção registada (RF05): " + intervencao.getDescricao()
                + " — marcação passou a " + marcacao.getEstado());

        try {
            marcacaoServico.cancelarMarcacao(marcacao);
        } catch (EstadoInvalidoException erro) {
            System.out.println("Transição de estado inválida (6.1) continua a ser rejeitada: " + erro.getMessage());
        }

        List<Intervencao> historico = intervencaoServico.consultarHistoricoDaViatura(viatura.getIdViatura());
        System.out.println("Histórico da viatura (RF06): " + historico.size() + " intervenção(ões)");
    }

    /** Gera um NIF sintaticamente válido (9 dígitos + dígito de controlo correto) a partir de uma semente. */
    private static String gerarNifValido(long semente) {
        long numero = Math.floorMod(semente, 900000000L) + 100000000L;
        String primeiros8 = String.valueOf(numero).substring(0, 8);
        int soma = 0;
        for (int i = 0; i < 8; i++) {
            soma += Character.getNumericValue(primeiros8.charAt(i)) * (9 - i);
        }
        int resto = soma % 11;
        int controlo = (resto < 2) ? 0 : 11 - resto;
        return primeiros8 + controlo;
    }

    /** Gera um contacto sintaticamente válido (9 dígitos, a começar por 2, 3 ou 9) a partir de uma semente. */
    private static String gerarContacto(long semente) {
        char[] prefixos = {'2', '3', '9'};
        char prefixo = prefixos[(int) Math.floorMod(semente, 3)];
        long resto = Math.floorMod(semente, 100000000L);
        return prefixo + String.format("%08d", resto);
    }
}