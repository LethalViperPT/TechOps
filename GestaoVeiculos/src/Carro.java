/**
 * Representa um carro. Acrescenta o número de portas e o tipo de combustível
 * aos dados comuns definidos em Veiculo.
 */
public class Carro extends Veiculo {

    private int numeroPortas;
    private String tipoCombustivel;

    public Carro(String matricula, String marca, String modelo, int ano, String cor, double preco,
                 int numeroPortas, String tipoCombustivel) {
        super(matricula, marca, modelo, ano, cor, preco);
        this.numeroPortas = numeroPortas;
        this.tipoCombustivel = tipoCombustivel;
    }

    public int getNumeroPortas() {
        return numeroPortas;
    }

    public String getTipoCombustivel() {
        return tipoCombustivel;
    }

    @Override
    public String getTipo() {
        return "Carro";
    }

    @Override
    public String getInfoEspecifica() {
        return numeroPortas + " portas | Combustivel: " + tipoCombustivel;
    }

    @Override
    public String paraLinhaFicheiro() {
        return "Carro;" + camposComunsParaFicheiro() + ";" + numeroPortas + ";" + tipoCombustivel;
    }
}
