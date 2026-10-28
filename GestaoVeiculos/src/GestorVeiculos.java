import java.util.ArrayList;
import java.util.List;

/**
 * Gere a lista de veículos da garagem.
 * <p>
 * Em vez de ter uma variável List<Veiculo> lá dentro, esta classe usa
 * herança: GestorVeiculos ESTENDE ArrayList<Veiculo>, ou seja, um
 * GestorVeiculos é, ele próprio, um ArrayList de veículos. Isto significa
 * que já vem com add(), get(), remove(), size(), iterator(), etc.
 * "de fábrica", herdados do ArrayList, sem ser preciso escrevê-los.
 * <p>
 * Só são acrescentados os métodos que fazem falta e que o ArrayList não
 * tem: procurar por matrícula, listar por tipo, pesquisar e remover por
 * matrícula.
 */
public class GestorVeiculos extends ArrayList<Veiculo> {

    public List<Veiculo> listarTodos() {
        return new ArrayList<>(this);
    }

    public List<Veiculo> listarPorTipo(String tipo) {
        List<Veiculo> resultado = new ArrayList<>();
        for (Veiculo v : this) {
            if (v.getTipo().equalsIgnoreCase(tipo)) {
                resultado.add(v);
            }
        }
        return resultado;
    }

    public Veiculo procurarPorMatricula(String matricula) {
        for (Veiculo v : this) {
            if (v.getMatricula().equalsIgnoreCase(matricula)) {
                return v;
            }
        }
        return null;
    }

    public boolean removerPorMatricula(String matricula) {
        return removeIf(v -> v.getMatricula().equalsIgnoreCase(matricula));
    }

    /**
     * Pesquisa livre: procura o termo (sem distinguir maiúsculas/minúsculas)
     * em TODOS os campos relevantes do veículo — os comuns (matrícula,
     * marca, modelo, ano, cor, preço, tipo) e também os específicos de
     * cada subclasse (portas/combustível, cilindrada, capacidade), através
     * de getCamposPesquisaveis().
     */
    public List<Veiculo> pesquisar(String termo) {
        String termoPesquisado = termo.trim().toLowerCase();
        List<Veiculo> resultado = new ArrayList<>();
        for (Veiculo v : this) {
            if (v.getCamposPesquisaveis().toLowerCase().contains(termoPesquisado)) {
                resultado.add(v);
            }
        }
        return resultado;
    }
}
