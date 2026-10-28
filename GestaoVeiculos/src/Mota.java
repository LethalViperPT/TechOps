/**
 * Representa uma mota. Acrescenta a cilindrada aos dados comuns
 * definidos em Veiculo.
 */
public class Mota extends Veiculo {

    private int cilindrada;

    public Mota(String matricula, String marca, String modelo, int ano, String cor, double preco,
                int cilindrada) {
        super(matricula, marca, modelo, ano, cor, preco);
        this.cilindrada = cilindrada;
    }

    public int getCilindrada() {
        return cilindrada;
    }

    @Override
    public String getTipo() {
        return "Mota";
    }

    @Override
    public String getInfoEspecifica() {
        return "Cilindrada: " + cilindrada + " cc";
    }

    @Override
    public String paraLinhaFicheiro() {
        return "Mota;" + camposComunsParaFicheiro() + ";" + cilindrada;
    }
}
