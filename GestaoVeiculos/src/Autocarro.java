/**
 * Representa um autocarro. Acrescenta a capacidade de passageiros aos
 * dados comuns definidos em Veiculo.
 */
public class Autocarro extends Veiculo {

    private int capacidadePassageiros;

    public Autocarro(String matricula, String marca, String modelo, int ano, String cor, double preco,
                      int capacidadePassageiros) {
        super(matricula, marca, modelo, ano, cor, preco);
        this.capacidadePassageiros = capacidadePassageiros;
    }

    public int getCapacidadePassageiros() {
        return capacidadePassageiros;
    }

    @Override
    public String getTipo() {
        return "Autocarro";
    }

    @Override
    public String getInfoEspecifica() {
        return "Capacidade: " + capacidadePassageiros + " passageiros";
    }

    @Override
    public String paraLinhaFicheiro() {
        return "Autocarro;" + camposComunsParaFicheiro() + ";" + capacidadePassageiros;
    }
}
