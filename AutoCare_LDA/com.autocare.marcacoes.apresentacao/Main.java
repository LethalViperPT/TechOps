package com.autocare.marcacoes.apresentacao;

import com.autocare.marcacoes.excecoes.AutoCareException;
import com.autocare.marcacoes.modelo.*;
import com.autocare.marcacoes.repositorio.*;
import com.autocare.marcacoes.servico.GestaoMarcacoesServico;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Main {
    private static GestaoMarcacoesServico servico;
    private static MecanicoRepositorio repoMecanicos;
    private static ViaturaRepositorio repoViaturas;
    private static DefaultTableModel tableModel;
    private static JTable tabela;

    public static void main(String[] args) {
        MarcacaoRepositorio repoMarcacoes = new MarcacaoRepositorio();
        repoMecanicos = new MecanicoRepositorio();
        repoViaturas = new ViaturaRepositorio();
        servico = new GestaoMarcacoesServico(repoMarcacoes);

        JFrame frame = new JFrame("AutoCare - Gestão de Marcações");
        frame.setSize(950, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));

        JPanel painelTopo = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        painelTopo.setBackground(new Color(41, 128, 185));

        try {
            BufferedImage imgLogo = ImageIO.read(new File("logo.png"));
            Image scaledImg = imgLogo.getScaledInstance(100, 60, Image.SCALE_SMOOTH);
            painelTopo.add(new JLabel(new ImageIcon(scaledImg)));
        } catch (Exception e) {
            System.err.println("AVISO: O formato da imagem não é reconhecido ou ficheiro não encontrado.");
        }

        JLabel lblTitulo = new JLabel("AutoCare Lda");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setForeground(Color.WHITE);
        painelTopo.add(lblTitulo);
        frame.add(painelTopo, BorderLayout.NORTH);

        String[] colunas = {"ID", "Data", "Hora", "Estado", "Mecânico", "Viatura"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabela = new JTable(tableModel);
        tabela.setRowHeight(25);
        tabela.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        tabela.getColumnModel().getColumn(0).setMinWidth(0);
        tabela.getColumnModel().getColumn(0).setMaxWidth(0);
        frame.add(new JScrollPane(tabela), BorderLayout.CENTER);

        tabela.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { abrirDetalhes(frame); }
            }
        });

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));
        JButton btnNovoCliente = new JButton("👤 Registar Cliente");
        JButton btnNova = new JButton("📅 Nova Marcação");
        JButton btnDetalhes = new JButton("ℹ Ver Detalhes");
        JButton btnConcluir = new JButton("✔ Concluir");
        JButton btnEliminar = new JButton("✖ Eliminar");

        Font fontBotoes = new Font("SansSerif", Font.BOLD, 14);
        btnNovoCliente.setFont(fontBotoes); btnNova.setFont(fontBotoes); btnDetalhes.setFont(fontBotoes); btnConcluir.setFont(fontBotoes); btnEliminar.setFont(fontBotoes);

        painelBotoes.add(btnNovoCliente);
        painelBotoes.add(btnNova);
        painelBotoes.add(btnDetalhes);
        painelBotoes.add(btnConcluir);
        painelBotoes.add(btnEliminar);
        frame.add(painelBotoes, BorderLayout.SOUTH);

        btnNovoCliente.addActionListener(e -> abrirDialogoNovoCliente(frame));
        btnNova.addActionListener(e -> abrirDialogoNovaMarcacao(frame));
        btnDetalhes.addActionListener(e -> abrirDetalhes(frame));

        btnConcluir.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(frame, "Selecione uma marcação na tabela primeiro.");
                return;
            }
            long id = (Long) tableModel.getValueAt(linha, 0);
            try {
                servico.atualizarMarcacao(new Marcacao(id, LocalDate.now(), LocalTime.now(), "CONCLUIDA", null, null));
                JOptionPane.showMessageDialog(frame, "Marcação dada como concluída!");
                carregarDadosTabela();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnEliminar.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(frame, "Selecione uma marcação para eliminar.");
                return;
            }
            long id = (Long) tableModel.getValueAt(linha, 0);
            if (JOptionPane.showConfirmDialog(frame, "Tem a certeza que deseja eliminar?", "Aviso", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                servico.removerMarcacao(id);
                carregarDadosTabela();
            }
        });

        carregarDadosTabela();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void abrirDialogoNovoCliente(JFrame parent) {
        JDialog dialog = new JDialog(parent, "Registar Novo Cliente e Viatura", true);
        dialog.setLayout(new GridLayout(7, 2, 10, 10));
        dialog.setSize(450, 300);
        dialog.getContentPane().setBackground(new Color(240, 240, 240));

        JTextField txtNome = new JTextField();
        JTextField txtTelefone = new JTextField();
        JTextField txtNif = new JTextField();
        JTextField txtMarca = new JTextField();
        JTextField txtModelo = new JTextField();
        JTextField txtMatricula = new JTextField();

        dialog.add(new JLabel("  Nome do Cliente:")); dialog.add(txtNome);
        dialog.add(new JLabel("  Nº Telefone:")); dialog.add(txtTelefone);
        dialog.add(new JLabel("  NIF (9 dígitos):")); dialog.add(txtNif);
        dialog.add(new JLabel("  Marca do Veículo:")); dialog.add(txtMarca);
        dialog.add(new JLabel("  Modelo:")); dialog.add(txtModelo);
        dialog.add(new JLabel("  Matrícula (XX-XX-XX):")); dialog.add(txtMatricula);

        JButton btnGuardar = new JButton("Guardar Ficha");
        btnGuardar.setBackground(new Color(41, 128, 185));
        btnGuardar.setForeground(Color.WHITE);

        btnGuardar.addActionListener(e -> {
            String nifInserido = txtNif.getText().trim();
            ClienteRepositorio repoCli = new ClienteRepositorio();

            // VALIDAÇÃO: Se o NIF já existe, bloqueia e avisa!
            if (repoCli.existeNif(nifInserido)) {
                JOptionPane.showMessageDialog(dialog, "Erro: Este NIF já se encontra registado num cliente do sistema!", "NIF Duplicado", JOptionPane.WARNING_MESSAGE);
                return;
            }

            long novoIdCliente = repoCli.guardar(new Cliente(0, txtNome.getText(), txtTelefone.getText(), nifInserido));

            if(novoIdCliente != -1) {
                Cliente cliSalvo = new Cliente(novoIdCliente, txtNome.getText(), txtTelefone.getText(), nifInserido);
                repoViaturas.guardar(new Viatura(0, txtMatricula.getText(), txtMarca.getText(), txtModelo.getText(), LocalDate.now().getYear(), cliSalvo));

                JOptionPane.showMessageDialog(dialog, "Cliente e Viatura registados com sucesso na base de dados!");
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Erro ao gravar na Base de Dados.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.add(new JLabel("")); dialog.add(btnGuardar);
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
    }

    private static void abrirDetalhes(JFrame frame) {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(frame, "Selecione uma marcação para ver os detalhes.");
            return;
        }
        long idMarcacao = (Long) tableModel.getValueAt(linha, 0);
        Marcacao m = servico.listarTodas().stream().filter(marc -> marc.getIdMarcacao() == idMarcacao).findFirst().orElse(null);

        if (m != null) {
            String mensagem = "FICHA COMPLETA DA INTERVENÇÃO\n\n" +
                    "👤 DADOS DO CLIENTE\n" +
                    "Nome: " + m.getViatura().getCliente().getNome() + "\n" +
                    "Telefone: " + m.getViatura().getCliente().getContacto() + "\n" +
                    "NIF: " + m.getViatura().getCliente().getNif() + "\n\n" +
                    "🚗 DADOS DA VIATURA\n" +
                    "Marca/Modelo: " + m.getViatura().getMarca() + " " + m.getViatura().getModelo() + "\n" +
                    "Matrícula: " + m.getViatura().getMatricula() + "\n\n" +
                    "🔧 AGENDAMENTO\n" +
                    "Data/Hora: " + m.getData() + " às " + m.getHora() + "\n" +
                    "Mecânico: " + m.getMecanico().getNome() + "\n" +
                    "Estado Atual: " + m.getEstado();

            JOptionPane.showMessageDialog(frame, mensagem, "Ficha de Detalhes", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private static void abrirDialogoNovaMarcacao(JFrame parent) {
        JDialog dialog = new JDialog(parent, "Agendar Serviço", true);
        dialog.setLayout(new GridLayout(5, 2, 10, 10));
        dialog.setSize(450, 250);
        dialog.getContentPane().setBackground(new Color(240, 240, 240));

        List<Mecanico> mecanicos = repoMecanicos.listarTodos();
        List<Viatura> viaturas = repoViaturas.listarTodas();

        JComboBox<Viatura> comboViatura = new JComboBox<>(viaturas.toArray(new Viatura[0]));
        JComboBox<Mecanico> comboMecanico = new JComboBox<>(mecanicos.toArray(new Mecanico[0]));
        JTextField txtData = new JTextField(LocalDate.now().plusDays(1).toString());
        JTextField txtHora = new JTextField("10:00");

        dialog.add(new JLabel("  Viatura Cliente:")); dialog.add(comboViatura);
        dialog.add(new JLabel("  Mecânico Responsável:")); dialog.add(comboMecanico);
        dialog.add(new JLabel("  Data (YYYY-MM-DD):")); dialog.add(txtData);
        dialog.add(new JLabel("  Hora (HH:MM):")); dialog.add(txtHora);

        JButton btnGuardar = new JButton("Confirmar Marcação");
        btnGuardar.setBackground(new Color(46, 204, 113));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFocusPainted(false);

        btnGuardar.addActionListener(e -> {
            try {
                Marcacao nova = new Marcacao(0, LocalDate.parse(txtData.getText()), LocalTime.parse(txtHora.getText()), "PENDENTE",
                        (Viatura) comboViatura.getSelectedItem(), (Mecanico) comboMecanico.getSelectedItem());
                servico.criarMarcacao(nova);
                dialog.dispose();
                carregarDadosTabela();
                JOptionPane.showMessageDialog(parent, "Marcação guardada com sucesso no sistema!");
            } catch (AutoCareException ex) {
                JOptionPane.showMessageDialog(dialog, ex.getMessage(), "Erro de Agendamento", JOptionPane.WARNING_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Verifique o formato da data e hora.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.add(new JLabel("")); dialog.add(btnGuardar);
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
    }

    private static void carregarDadosTabela() {
        tableModel.setRowCount(0);
        for (Marcacao m : servico.listarTodas()) {
            tableModel.addRow(new Object[]{
                    m.getIdMarcacao(), m.getData().toString(), m.getHora().toString(),
                    m.getEstado(), m.getMecanico().getNome(), m.getViatura().getMatricula()
            });
        }
    }
}