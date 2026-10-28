package com.autocare.gestao.apresentacao;

import com.autocare.gestao.excecoes.PersistenciaException;
import com.autocare.gestao.modelo.Cliente;
import com.autocare.gestao.modelo.EstadoMarcacao;
import com.autocare.gestao.modelo.Mecanico;
import com.autocare.gestao.modelo.Viatura;
import com.autocare.gestao.repositorio.ClienteRepositorio;
import com.autocare.gestao.repositorio.MarcacaoRepositorio;
import com.autocare.gestao.repositorio.MecanicoRepositorio;
import com.autocare.gestao.repositorio.ViaturaRepositorio;
import com.autocare.gestao.servico.MarcacaoServico;

import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Teste de desempenho (Tarefa 9.3): mede o tempo da pesquisa combinada
 * (Tarefa 7.1) com um número reduzido de registos e depois com um volume
 * maior, para comparar.
 *
 * Como criar 500 marcações implica também criar viaturas/mecânicos válidos
 * (por causa das chaves estrangeiras), este harness gera esses dados de
 * apoio automaticamente antes de cada medição.
 *
 * CORRE ESTA CLASSE DIRETAMENTE (tem o seu próprio main) — não faz parte
 * do fluxo normal da aplicação, é só para a medição desta tarefa.
 */
public class TesteDesempenho {

    private static final String URL_BASE_DADOS = "jdbc:mysql://localhost:3306/autocare";
    private static final String UTILIZADOR = "autocare_app";
    private static final String PALAVRA_PASSE = "autocare123";

    public static void main(String[] args) throws Exception {
        try (Connection ligacao = DriverManager.getConnection(URL_BASE_DADOS, UTILIZADOR, PALAVRA_PASSE)) {

            MarcacaoRepositorio marcacaoRepositorio = new MarcacaoRepositorio(ligacao);
            MarcacaoServico marcacaoServico = new MarcacaoServico(marcacaoRepositorio);

            medir(ligacao, marcacaoServico, 10);
            medir(ligacao, marcacaoServico, 500);
        }
    }

    private static void medir(Connection ligacao, MarcacaoServico marcacaoServico, int quantidade) throws Exception {
        gerarMarcacoesDeTeste(ligacao, quantidade);

        long inicio = System.nanoTime();
        int resultados = marcacaoServico.pesquisar(EstadoMarcacao.AGENDADA, null, null).size();
        long fim = System.nanoTime();

        double milissegundos = (fim - inicio) / 1_000_000.0;
        System.out.printf("Volume ~%d marcações -> pesquisa devolveu %d resultados em %.2f ms%n",
                quantidade, resultados, milissegundos);
    }

    private static void gerarMarcacoesDeTeste(Connection ligacao, int quantidade) throws PersistenciaException {
        ClienteRepositorio clienteRepositorio = new ClienteRepositorio(ligacao);
        ViaturaRepositorio viaturaRepositorio = new ViaturaRepositorio(ligacao);
        MecanicoRepositorio mecanicoRepositorio = new MecanicoRepositorio(ligacao);
        MarcacaoRepositorio marcacaoRepositorio = new MarcacaoRepositorio(ligacao);

        long semente = System.nanoTime();
        Cliente cliente = clienteRepositorio.inserir(new Cliente(0, "Cliente Desempenho", gerarContacto(semente), gerarNif(semente)));
        Viatura viatura = viaturaRepositorio.inserir(new Viatura(0, "PD-" + (semente % 1000), "Marca", "Modelo", 2020, cliente));
        Mecanico mecanico = mecanicoRepositorio.inserir(new Mecanico(0, "Mecânico Desempenho", "Geral"));

        for (int i = 0; i < quantidade; i++) {
            marcacaoRepositorio.inserir(new com.autocare.gestao.modelo.Marcacao(
                    0, LocalDate.of(2026, 9, 1).plusDays(i % 28), LocalTime.of(8 + (i % 10), 0), viatura, mecanico));
        }
    }

    private static String gerarNif(long semente) {
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

    private static String gerarContacto(long semente) {
        char[] prefixos = {'2', '3', '9'};
        char prefixo = prefixos[(int) Math.floorMod(semente, 3)];
        return prefixo + String.format("%08d", Math.floorMod(semente, 100000000L));
    }
}
