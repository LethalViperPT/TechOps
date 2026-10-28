import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * Interface gráfica (Swing) do Sistema de Gestão de Tarefas.
 * <p>
 * Não implementa nenhuma lógica de negócio própria — reutiliza exatamente as
 * mesmas classes {@link GestorTarefas} e {@link PersistenciaTarefas} usadas
 * pela versão em modo consola ({@link TaskManagerApp}), garantindo que as
 * duas interfaces partilham as mesmas regras e os mesmos dados.
 */
public class TaskManagerGUI extends JFrame {

    private static final String FICHEIRO_DADOS = "tarefas.txt";
    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static final Color COR_FUNDO = new Color(0xF4, 0xF6, 0xFB);
    private static final Color COR_PRIMARIA = new Color(0x1E, 0x27, 0x61);
    private static final Color COR_ALTA = new Color(0xC0, 0x39, 0x2B);
    private static final Color COR_MEDIA = new Color(0xB2, 0x8A, 0x00);
    private static final Color COR_BAIXA = new Color(0x1E, 0x7A, 0x34);
    private static final Color COR_CONCLUIDA = new Color(0x6B, 0x72, 0x80);

    private final PersistenciaTarefas persistencia = new PersistenciaTarefas(FICHEIRO_DADOS);
    private final GestorTarefas gestor;

    private final TarefaTableModel modeloTabela = new TarefaTableModel();
    private final JTable tabela = new JTable(modeloTabela);

    private final JTextField campoTitulo = new JTextField();
    private final JTextField campoDescricao = new JTextField();
    private final JTextField campoData = new JTextField();
    private final JComboBox<Prioridade> comboPrioridade = new JComboBox<>(Prioridade.values());

    private final JComboBox<String> filtroPrioridade = new JComboBox<>(new String[]{"Todas", "BAIXA", "MEDIA", "ALTA"});
    private final JComboBox<String> filtroEstado = new JComboBox<>(new String[]{"Todas", "Pendentes", "Concluídas"});
    private final JComboBox<String> comboOrdenar = new JComboBox<>(new String[]{"Sem ordenação", "Data", "Prioridade", "Título"});

    private final JLabel labelEstado = new JLabel(" ");
    private final JButton btnGuardar = new JButton("Criar Tarefa");

    private Integer idEmEdicao = null; // null = a criar tarefa nova; caso contrário, ID da tarefa a editar

    public TaskManagerGUI() {
        super("Sistema de Gestão de Tarefas");
        this.gestor = new GestorTarefas(persistencia.carregar());

        configurarJanela();
        add(construirPainelFormulario(), BorderLayout.NORTH);
        add(construirPainelTabela(), BorderLayout.CENTER);
        add(construirPainelAcoes(), BorderLayout.SOUTH);

        atualizarTabela();
    }

    private void configurarJanela() {
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                persistencia.guardar(gestor.listarTarefas());
                dispose();
                System.exit(0);
            }
        });
        setSize(1000, 640);
        setMinimumSize(new Dimension(820, 520));
        setLocationRelativeTo(null);
        getContentPane().setBackground(COR_FUNDO);
        try {
            // Usa o tema Nimbus em vez do tema do sistema operativo.
            // Em Linux, o tema do sistema (GTK) tem um bug antigo e conhecido
            // do Swing em que o clique do rato nas opções de uma JComboBox
            // (como o campo "Prioridade") não regista — só as setas do
            // teclado funcionam. O Nimbus não tem esse problema e mantém um
            // aspeto visual cuidado em qualquer sistema operativo.
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception ignorada) {
            // se o Nimbus não estiver disponível, mantém-se o tema por omissão do Swing
        }
    }

    // ---------- Painel do formulário (criar / editar) ----------

    private JPanel construirPainelFormulario() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBorder(BorderFactory.createEmptyBorder(14, 16, 10, 16));
        painel.setBackground(COR_FUNDO);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 6, 4, 6);
        c.fill = GridBagConstraints.HORIZONTAL;

        JLabel titulo = new JLabel("Sistema de Gestão de Tarefas");
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 20f));
        titulo.setForeground(COR_PRIMARIA);
        c.gridx = 0; c.gridy = 0; c.gridwidth = 6; c.insets = new Insets(0, 6, 12, 6);
        painel.add(titulo, c);

        c.gridwidth = 1; c.insets = new Insets(4, 6, 4, 6);

        c.gridx = 0; c.gridy = 1; c.weightx = 0;
        painel.add(new JLabel("Título:"), c);
        c.gridx = 1; c.weightx = 1;
        painel.add(campoTitulo, c);

        c.gridx = 2; c.weightx = 0;
        painel.add(new JLabel("Prioridade:"), c);
        c.gridx = 3; c.weightx = 0.4;
        painel.add(comboPrioridade, c);

        c.gridx = 4; c.weightx = 0;
        painel.add(new JLabel("Prazo (dd/MM/yyyy):"), c);
        c.gridx = 5; c.weightx = 0.5;
        painel.add(campoData, c);

        c.gridx = 0; c.gridy = 2; c.weightx = 0;
        painel.add(new JLabel("Descrição:"), c);
        c.gridx = 1; c.gridwidth = 3; c.weightx = 1;
        painel.add(campoDescricao, c);

        c.gridx = 4; c.gridwidth = 1; c.weightx = 0;
        JButton btnLimpar = new JButton("Novo / Limpar");
        btnLimpar.addActionListener(e -> limparFormulario());
        painel.add(btnLimpar, c);

        c.gridx = 5;
        estilizarBotaoPrimario(btnGuardar);
        btnGuardar.addActionListener(e -> guardarFormulario());
        painel.add(btnGuardar, c);

        return painel;
    }

    private void estilizarBotaoPrimario(JButton botao) {
        botao.setBackground(COR_PRIMARIA);
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setOpaque(true);
        botao.setBorderPainted(false);
    }

    // ---------- Painel central (tabela) ----------

    private JPanel construirPainelTabela() {
        tabela.setRowHeight(26);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) carregarSelecaoNoFormulario();
        });
        tabela.setDefaultRenderer(Object.class, new RendererTarefas());

        JTableHeader cabecalho = tabela.getTableHeader();
        cabecalho.setFont(cabecalho.getFont().deriveFont(Font.BOLD));
        cabecalho.setBackground(COR_PRIMARIA);
        cabecalho.setForeground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBorder(BorderFactory.createEmptyBorder(4, 16, 4, 16));

        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(COR_FUNDO);
        painel.add(scroll, BorderLayout.CENTER);
        return painel;
    }

    /** Colore as células da tabela consoante a prioridade e o estado da tarefa. */
    private class RendererTarefas extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                         boolean hasFocus, int row, int column) {
            Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            setForeground(Color.BLACK);
            setFont(getFont().deriveFont(Font.PLAIN));

            Tarefa t = modeloTabela.getTarefaNaLinha(row);
            if (t != null) {
                if (column == 2) { // Prioridade
                    setForeground(switch (t.getPrioridade()) {
                        case ALTA -> COR_ALTA;
                        case MEDIA -> COR_MEDIA;
                        case BAIXA -> COR_BAIXA;
                    });
                    setFont(getFont().deriveFont(Font.BOLD));
                } else if (column == 4) { // Estado
                    setForeground(t.isConcluida() ? COR_BAIXA : COR_CONCLUIDA);
                } else if (t.isConcluida()) {
                    setForeground(COR_CONCLUIDA);
                }
            }
            if (!isSelected) {
                setBackground(row % 2 == 0 ? Color.WHITE : new Color(0xF7, 0xF9, 0xFD));
            }
            return comp;
        }
    }

    // ---------- Painel inferior (ações, filtros, estado) ----------

    private JPanel construirPainelAcoes() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(COR_FUNDO);
        painel.setBorder(BorderFactory.createEmptyBorder(6, 16, 12, 16));

        JPanel linhaAcoes = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        linhaAcoes.setBackground(COR_FUNDO);

        JButton btnConcluir = new JButton("✔ Marcar como Concluída");
        btnConcluir.addActionListener(e -> marcarConcluida());

        JButton btnEliminar = new JButton("✘ Eliminar");
        btnEliminar.addActionListener(e -> eliminarSelecionada());

        linhaAcoes.add(btnConcluir);
        linhaAcoes.add(btnEliminar);
        linhaAcoes.add(Box.createHorizontalStrut(20));
        linhaAcoes.add(new JLabel("Prioridade:"));
        linhaAcoes.add(filtroPrioridade);
        linhaAcoes.add(new JLabel("Estado:"));
        linhaAcoes.add(filtroEstado);
        linhaAcoes.add(new JLabel("Ordenar por:"));
        linhaAcoes.add(comboOrdenar);

        filtroPrioridade.addActionListener(e -> atualizarTabela());
        filtroEstado.addActionListener(e -> atualizarTabela());
        comboOrdenar.addActionListener(e -> atualizarTabela());

        labelEstado.setForeground(COR_PRIMARIA);
        labelEstado.setFont(labelEstado.getFont().deriveFont(Font.PLAIN, 12f));

        painel.add(linhaAcoes, BorderLayout.NORTH);
        painel.add(labelEstado, BorderLayout.SOUTH);
        return painel;
    }

    // ---------- Ações ----------

    private void guardarFormulario() {
        String titulo = campoTitulo.getText().trim();
        String descricao = campoDescricao.getText().trim();
        String dataTexto = campoData.getText().trim();

        if (titulo.isBlank()) {
            mostrarAviso("O título não pode ficar vazio.");
            return;
        }

        LocalDate data;
        try {
            data = LocalDate.parse(dataTexto, FORMATO_DATA);
        } catch (DateTimeParseException ex) {
            mostrarAviso("Data inválida. Usa o formato dd/MM/yyyy (ex: 31/12/2026).");
            return;
        }

        Prioridade prioridade = (Prioridade) comboPrioridade.getSelectedItem();

        if (idEmEdicao == null) {
            Tarefa nova = gestor.adicionarTarefa(titulo, descricao, data, prioridade);
            mostrarEstado("✔ Tarefa #" + nova.getId() + " criada com sucesso.", COR_BAIXA);
        } else {
            gestor.editarTarefa(idEmEdicao, titulo, descricao, data, prioridade);
            mostrarEstado("✔ Tarefa #" + idEmEdicao + " atualizada com sucesso.", COR_BAIXA);
        }

        persistencia.guardar(gestor.listarTarefas());
        limparFormulario();
        atualizarTabela();
    }

    private void marcarConcluida() {
        Tarefa selecionada = obterTarefaSelecionada();
        if (selecionada == null) {
            mostrarAviso("Seleciona primeiro uma tarefa na tabela.");
            return;
        }
        gestor.marcarConcluida(selecionada.getId());
        persistencia.guardar(gestor.listarTarefas());
        mostrarEstado("✔ Tarefa #" + selecionada.getId() + " marcada como concluída.", COR_BAIXA);
        atualizarTabela();
    }

    private void eliminarSelecionada() {
        Tarefa selecionada = obterTarefaSelecionada();
        if (selecionada == null) {
            mostrarAviso("Seleciona primeiro uma tarefa na tabela.");
            return;
        }
        int confirmacao = JOptionPane.showConfirmDialog(this,
                "Eliminar a tarefa \"" + selecionada.getTitulo() + "\"?",
                "Confirmar eliminação", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirmacao != JOptionPane.YES_OPTION) return;

        gestor.removerTarefa(selecionada.getId());
        persistencia.guardar(gestor.listarTarefas());
        mostrarEstado("✔ Tarefa #" + selecionada.getId() + " eliminada.", COR_BAIXA);
        limparFormulario();
        atualizarTabela();
    }

    private Tarefa obterTarefaSelecionada() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) return null;
        return modeloTabela.getTarefaNaLinha(linha);
    }

    private void carregarSelecaoNoFormulario() {
        Tarefa t = obterTarefaSelecionada();
        if (t == null) return;
        idEmEdicao = t.getId();
        campoTitulo.setText(t.getTitulo());
        campoDescricao.setText(t.getDescricao());
        campoData.setText(t.getDataLimite().format(FORMATO_DATA));
        comboPrioridade.setSelectedItem(t.getPrioridade());
        btnGuardar.setText("Guardar Alterações");
    }

    private void limparFormulario() {
        idEmEdicao = null;
        campoTitulo.setText("");
        campoDescricao.setText("");
        campoData.setText("");
        comboPrioridade.setSelectedIndex(0);
        btnGuardar.setText("Criar Tarefa");
        tabela.clearSelection();
    }

    private void atualizarTabela() {
        Prioridade prioridade = switch ((String) filtroPrioridade.getSelectedItem()) {
            case "BAIXA" -> Prioridade.BAIXA;
            case "MEDIA" -> Prioridade.MEDIA;
            case "ALTA" -> Prioridade.ALTA;
            default -> null;
        };
        Boolean concluida = switch ((String) filtroEstado.getSelectedItem()) {
            case "Pendentes" -> Boolean.FALSE;
            case "Concluídas" -> Boolean.TRUE;
            default -> null;
        };

        List<Tarefa> lista = gestor.filtrarTarefas(prioridade, concluida);

        String criterio = (String) comboOrdenar.getSelectedItem();
        if (criterio != null && !criterio.equals("Sem ordenação")) {
            lista = gestor.ordenarLista(lista, criterio.equals("Título") ? "titulo" : criterio.toLowerCase());
        }

        modeloTabela.atualizar(lista);

        long total = gestor.listarTarefas().size();
        long concluidas = gestor.listarTarefas().stream().filter(Tarefa::isConcluida).count();
        labelEstado.setText(String.format(
                "A mostrar %d de %d tarefa(s)  ·  %d concluída(s) no total  ·  %d pendente(s) no total",
                lista.size(), total, concluidas, total - concluidas));
    }

    private void mostrarAviso(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Aviso", JOptionPane.WARNING_MESSAGE);
    }

    private void mostrarEstado(String mensagem, Color cor) {
        labelEstado.setForeground(cor);
        labelEstado.setText(mensagem);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TaskManagerGUI().setVisible(true));
    }
}
