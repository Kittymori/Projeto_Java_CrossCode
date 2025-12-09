package com.ProjetoExtensao.Projeto.view;

import com.ProjetoExtensao.Projeto.infra.Cores;
import com.ProjetoExtensao.Projeto.models.EventoSentinela;
import com.ProjetoExtensao.Projeto.servicos.EventoSentinelaService;
import com.ProjetoExtensao.Projeto.servicos.NavigationService;
import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Component
@NoArgsConstructor
public class TelaConsultaEventoSentinela extends JFrame {

    @Autowired
    private EventoSentinelaService eventoSentinelaService;
    @Lazy
    @Autowired
    private NavigationService navigationService;
    private JTextField txtCpfFiltro;
    private JTextField txtDataFiltro;
    private JComboBox<String> comboEvento;
    private JTable tabelaEventos;
    private DefaultTableModel tableModel;

    // Lista de Eventos
    private static final String[] EVENTOS_SENTINELAS_LIST = {
            "Todos", "Tentativa de Suicídio", "Desnutrição",
            "Quedas", "Óbito", "Diarreia", "Pressão arterial",
            "Escabiose", "Glicemia", "Desidratação", "Temperatura",
            "Úlcera por pressão"
    };

    @PostConstruct
    public void initUI() {
        setTitle("Tela 3 - Consultar Eventos Sentinelas");
        setSize(1200, 700);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(createHeaderPanel(), BorderLayout.NORTH);
        getContentPane().setBackground(Cores.COR_FUNDO_CLARO);

        // Painel Central
        JPanel panelCenter = new JPanel(new BorderLayout(0, 10));
        panelCenter.setOpaque(false);
        panelCenter.setBorder(new EmptyBorder(20, 40, 20, 40));

        // Header da Tela de Consulta
        JPanel panelHeader = new JPanel(new BorderLayout());
        panelHeader.setOpaque(false);

        JLabel titleLabel = new JLabel("Consultar Eventos");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Cores.COR_LETRA_PAINEL);

        JButton btnRegistrar = createButton("Registrar Evento");
        btnRegistrar.addActionListener(e -> {
            navigationService.abrirTelaRegistroEventoSentinela();
            dispose();
        });

        panelHeader.add(titleLabel, BorderLayout.WEST);
        panelHeader.add(btnRegistrar, BorderLayout.EAST);

        panelCenter.add(panelHeader, BorderLayout.NORTH);

        //Painel de Filtros
        JPanel panelFiltros = new JPanel();
        panelFiltros.setLayout(new GridBagLayout());
        panelFiltros.setBackground(Cores.COR_FUNDO_CINZA);
        panelFiltros.setBorder(new EmptyBorder(15, 15, 15, 15));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font fonteLabel = new Font("Segoe UI", Font.BOLD, 14);
        Font fonteCampo = new Font("Segoe UI", Font.PLAIN, 13);

        // 1. CPF
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.35;
        panelFiltros.add(createLabel("CPF do Paciente", fonteLabel), gbc);

        gbc.gridy = 1;
        try {
            MaskFormatter cpfFormatter = new MaskFormatter("###.###.###-##");
            cpfFormatter.setPlaceholderCharacter('_');
            txtCpfFiltro = new JFormattedTextField(cpfFormatter);
            txtCpfFiltro.setColumns(15);
        } catch (java.text.ParseException e) {
            txtCpfFiltro = new JFormattedTextField(5);
        }
        txtCpfFiltro.setFont(fonteCampo);
        panelFiltros.add(txtCpfFiltro, gbc);


        // 2. Data
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.35;
        panelFiltros.add(createLabel("Data (DD/MM/AAAA)", fonteLabel), gbc);

        gbc.gridy = 1;
        try {
            MaskFormatter formatter = new MaskFormatter("##/##/####");
            txtDataFiltro = new JFormattedTextField(formatter);
            txtDataFiltro.setColumns(10);
        } catch (java.text.ParseException e) {
            txtDataFiltro = new JTextField(5);
        }
        txtDataFiltro.setFont(fonteCampo);
        panelFiltros.add(txtDataFiltro, gbc);


        // 3. Evento
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        panelFiltros.add(createLabel("Evento", fonteLabel), gbc);

        gbc.gridy = 1;
        comboEvento = new JComboBox<>(EVENTOS_SENTINELAS_LIST);
        comboEvento.setFont(fonteCampo);
        panelFiltros.add(comboEvento, gbc);

        // 4. Botão Pesquisar
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.weightx = 0.1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        JButton btnPesquisar = createButton("Pesquisar");
        btnPesquisar.addActionListener(e -> carregarDadosTabela());
        panelFiltros.add(btnPesquisar, gbc);

        panelCenter.add(panelFiltros, BorderLayout.CENTER);


        // Painel da Tabela
        String[] colunas = {"CPF do Paciente", "Nome do Paciente", "Eventos", "Data", "Ocorrências"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaEventos = new JTable(tableModel);
        tabelaEventos.setFont(fonteCampo);
        tabelaEventos.setRowHeight(25);

        tabelaEventos.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabelaEventos.getTableHeader().setBackground(Cores.COR_FUNDO_CLARO);
        tabelaEventos.getTableHeader().setForeground(Cores.COR_LETRA_PAINEL);

        JScrollPane scrollPane = new JScrollPane(tabelaEventos);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        JPanel panelTabelaContainer = new JPanel(new BorderLayout());
        panelTabelaContainer.setOpaque(false);
        panelTabelaContainer.setBorder(new EmptyBorder(15, 0, 0, 0));
        panelTabelaContainer.add(scrollPane, BorderLayout.CENTER);

        panelCenter.add(panelTabelaContainer, BorderLayout.SOUTH);

        add(panelCenter, BorderLayout.CENTER);
        carregarDadosTabela();
    }

    //MÉTODOS DE LAYOUT

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Cores.COR_FUNDO_CLARO);
        headerPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        logoPanel.setOpaque(false);

        JLabel nameLabel = new JLabel("<html>RECANTO DO SAGRADO CORAÇÃO<br><small>ASSISTÊNCIA SOCIAL CATARINA LABOURÉ</small></html>");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        nameLabel.setForeground(Cores.COR_RODAPE);
        logoPanel.add(nameLabel);

        headerPanel.add(logoPanel, BorderLayout.WEST);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        buttonPanel.setOpaque(false);

        Color btnBackground = Cores.COR_RODAPE;
        Color btnForeground = Cores.COR_FUNDO_CLARO;

        JButton btnAdmin = createHeaderButton("Administrador Painel", btnBackground, btnForeground, "admin.png");
        btnAdmin.addActionListener(e -> {
            navigationService.abrirTelaGeral();
            dispose();
        });
        buttonPanel.add(btnAdmin);

        JButton btnSair = createHeaderButton("Sair", btnBackground, btnForeground, "exit.png");
        btnSair.addActionListener(e -> {
            dispose();
        });
        buttonPanel.add(btnSair);

        JButton btnAtualizar = createHeaderButton("", btnBackground, btnForeground, "refresh.png");
        btnAtualizar.addActionListener(e -> {
            carregarDadosTabela();
        });
        buttonPanel.add(btnAtualizar);

        headerPanel.add(buttonPanel, BorderLayout.EAST);

        return headerPanel;
    }

    private JButton createHeaderButton(String text, Color background, Color foreground, String iconPath) {
        JButton btn = new JButton(text);
        try {
            if (!text.isEmpty()) {
                btn.setHorizontalTextPosition(SwingConstants.RIGHT);
            } else {
                btn.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            }
        } catch (Exception e) { }
        btn.setBackground(background);
        btn.setForeground(foreground);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        return btn;
    }

    private JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        return label;
    }

    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(Cores.COR_RODAPE);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        return btn;
    }

    //LÓGICA DE CONSULTA E FILTRAGEM

    public void carregarDadosTabela() {
        tableModel.setRowCount(0);

        // Coleta os valores de filtro
        String cpfFiltro = txtCpfFiltro.getText().trim();
        String dataFiltroStr = txtDataFiltro.getText().trim();
        String eventoSelecionado = (String) comboEvento.getSelectedItem();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataFiltro = null;

        if (!dataFiltroStr.isEmpty()) {
            try {
                dataFiltro = LocalDate.parse(dataFiltroStr, formatter);
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(this, "Erro no formato da Data de Filtro. Use DD/MM/AAAA.",
                        "Erro de Formato", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        // CHAMA O MÉTODO DO SERVICE
        List<EventoSentinela> eventos = eventoSentinelaService.buscarTodosEventosNaAPI();

        // FILTRAGEM E ADIÇÃO À TABELA
        for (EventoSentinela evento : eventos) {

            String cpfPaciente;
            String nomePaciente;

            // 1. Acesso aos dados do Paciente
            if (evento.getPaciente() != null) {
                cpfPaciente = evento.getPaciente().getCpf();
                nomePaciente = evento.getPaciente().getNomeCompleto();
            } else {
                cpfPaciente = "[ERRO/NULO]";
                nomePaciente = "[ERRO/NULO]";
            }

            String tipoEvento = evento.getTipoEvento().toString();
            String dataStr = evento.getDataOcorrido().format(formatter);

            // 2. CONDIÇÕES DE FILTRAGEM

            // Filtro 1: Evento
            boolean matchEvento = "Todos".equals(eventoSelecionado) || tipoEvento.equals(eventoSelecionado);

            // Filtro 2: CPF
            boolean matchCpf = cpfFiltro.isEmpty() || cpfPaciente.equals(cpfFiltro);

            // Filtro 3: Data
            boolean matchData;
            if (dataFiltro == null) {
                matchData = true;
            } else {
                matchData = evento.getDataOcorrido().equals(dataFiltro);
            }


            if (matchEvento && matchCpf && matchData) {
                tableModel.addRow(new Object[]{
                        cpfPaciente,
                        nomePaciente,
                        tipoEvento,
                        dataStr,
                });
            }
        }

        if (tableModel.getRowCount() == 0 && !eventos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum evento encontrado para os filtros selecionados.", "Consulta Vazia", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}