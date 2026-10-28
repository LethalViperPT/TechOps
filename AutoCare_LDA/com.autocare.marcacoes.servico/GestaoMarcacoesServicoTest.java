import com.autocare.marcacoes.modelo.*;
import com.autocare.marcacoes.servico.GestaoMarcacoesServico;
import com.autocare.marcacoes.repositorio.MarcacaoRepositorio;
import com.autocare.marcacoes.excecoes.AutoCareException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.*;

public class GestaoMarcacoesServicoTest {
    
    private GestaoMarcacoesServico servico;
    private MarcacaoRepositorio repositorioMock; // Simulação para isolar o teste

    @BeforeEach
    public void setup() {
        repositorioMock = new MarcacaoRepositorio();
        servico = new GestaoMarcacoesServico(repositorioMock);
    }

    @Test
    public void testCriarMarcacaoComSucesso() {
        Mecanico m1 = new Mecanico(1, "Rui", "Mecânica Geral");
        Viatura v1 = new Viatura(1, "AA-00-BB", "Ford", "Fiesta", 2017, null);
        Marcacao nova = new Marcacao(1, LocalDate.now().plusDays(2), LocalTime.of(10, 0), "PENDENTE", v1, m1);
        
        assertDoesNotThrow(() -> servico.criarMarcacao(nova));
    }

    @Test
    public void testImpedirSobreposicaoHorarios() {
        Mecanico m1 = new Mecanico(1, "Rui", "Mecânica Geral");
        Viatura v1 = new Viatura(1, "AA-00-BB", "Ford", "Fiesta", 2017, null);
        
        Marcacao mrc1 = new Marcacao(1, LocalDate.now().plusDays(2), LocalTime.of(10, 0), "PENDENTE", v1, m1);
        servico.criarMarcacao(mrc1);
        
        Marcacao mrc2 = new Marcacao(2, LocalDate.now().plusDays(2), LocalTime.of(10, 0), "PENDENTE", v1, m1);
        
        // Testa o caso de erro esperado[cite: 5]
        assertThrows(AutoCareException.class, () -> servico.criarMarcacao(mrc2));
    }

    @Test
    public void testAvancarEstadoValido() {
        Mecanico m1 = new Mecanico(1, "Rui", "Mecânica Geral");
        Marcacao mrc = new Marcacao(1, LocalDate.now(), LocalTime.now(), "PENDENTE", null, m1);
        
        mrc.avancarEstado(); // Regra de negócio implementada na aula 6[cite: 5]
        assertEquals("EM_CURSO", mrc.getEstado());
    }

    @Test
    public void testAvancarEstadoInvalido() {
        Mecanico m1 = new Mecanico(1, "Rui", "Mecânica Geral");
        Marcacao mrc = new Marcacao(1, LocalDate.now(), LocalTime.now(), "CONCLUIDA", null, m1);
        
        assertThrows(IllegalStateException.class, () -> mrc.avancarEstado());
    }
}