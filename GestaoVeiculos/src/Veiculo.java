/**
 * Classe base abstrata para qualquer veículo da garagem.
 * As subclasses (Carro, Mota, Autocarro) acrescentam os campos próprios
 * de cada tipo e implementam getTipo() e getInfoEspecifica().
 */
public abstract class Veiculo {

    protected String matricula;
    protected String marca;
    protected String modelo;
    protected int ano;
    protected String cor;
    protected double preco;

    public Veiculo(String matricula, String marca, String modelo, int ano, String cor, double preco) {
        this.matricula = matricula;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.cor = cor;
        this.preco = preco;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public int getAno() {
        return ano;
    }

    public String getCor() {
        return cor;
    }

    public double getPreco() {
        return preco;
    }

    /** Ex: "Carro", "Mota", "Autocarro" */
    public abstract String getTipo();

    /** Informação própria de cada tipo de veículo (ex: numero de portas, cilindrada, etc). */
    public abstract String getInfoEspecifica();

    /**
     * Junta TODOS os campos relevantes do veículo (os comuns e os
     * específicos de cada subclasse) numa única String, para a pesquisa
     * poder procurar em qualquer um deles. Como chama getInfoEspecifica()
     * (que cada subclasse implementa à sua maneira), isto cobre
     * automaticamente os campos próprios de Carro, Mota e Autocarro, sem
     * ser preciso repetir nada nas subclasses.
     */
    public String getCamposPesquisaveis() {
        return matricula + " " + marca + " " + modelo + " " + ano + " " + cor + " " + preco
                + " " + getTipo() + " " + getInfoEspecifica();
    }

    /** Campos comuns, separados por ";", para gravação em ficheiro. */
    protected String camposComunsParaFicheiro() {
        return matricula + ";" + marca + ";" + modelo + ";" + ano + ";" + cor + ";" + preco;
    }

    /** Cada subclasse acrescenta o seu tipo e os seus campos próprios a isto. */
    public abstract String paraLinhaFicheiro();

    @Override
    public String toString() {
        return String.format("[%-9s] %s %s (%d) | Matricula: %-8s | Cor: %-10s | Preco: %8.2f EUR | %s",
                getTipo(), marca, modelo, ano, matricula, cor, preco, getInfoEspecifica());
    }
}
