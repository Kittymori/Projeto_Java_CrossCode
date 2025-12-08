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
    // CORRIGIDO: Adicionado ';'
    private JFormattedTextField txtCpfFiltro;
    private JFormattedTextField txtDataFiltro;
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

        // Painel de Filtros
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
            txtCpfFiltro = new JFormattedTextField(15);
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
            txtDataFiltro = new JTextField(10);
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
        tabelaEventos.getTableHeader().setBackground(Cores.COR_FUNDO_CINZA);
        tabelaEventos.getTableHeader().setForeground(Cores.COR_LETRA_PAINEL);
        tabelaEventos.setBackground(Cores.COR_FUNDO_CINZA);
        
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
        nameLabel.setForeground(Cores.COR_ROD
