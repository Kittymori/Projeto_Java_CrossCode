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
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
@NoArgsConstructor
public class TelaConsultaEventosSentinelas extends JFrame {

    @Autowired
    private EventoSentinelaService eventoSentinelaService; 
    @Lazy
    @Autowired
    private NavigationService navigationService;
    private JComboBox<String> comboEvento, comboMes, comboAno;
    private JTable tabelaEventos;
    private DefaultTableModel tableModel;

    // Lista dos eventos sentinelas para o filtro
    private static final String[] EVENTOS_SENTINELAS_LIST = {
            "Todos", "Tentativa de Suicídio", "Desnutrição",
            "Quedas", "Óbito", "Diarreia", "Pressão arterial",
            "Escabiose", "Glicemia", "Desidratação", "Temperatura",
            "Úlcera por pressão"
    };

    // Lista completa dos meses
    private static final String[] MESES_LIST = {
            "Todos", "Janeiro", "Fevereiro", "Março", "Abril",
            "Maio", "Junho", "Julho", "Agosto", "Setembro",
            "Outubro", "Novembro", "Dezembro"
    };

    @PostConstruct
    public void initUI() {
        setTitle("Tela 3 - Consultar Eventos Sentinelas");
        setSize(1200, 700);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Adicionar o Painel de Cabeçalho
        add(createHeaderPanel(), BorderLayout.NORTH);

        getContentPane().setBackground(Cores.COR_FUNDO_CLARO);

        //Painel Central
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
            navigationService.abrirTelaRegistroEventosSentinelas();
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

        // 1. Evento
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.35;
        panelFiltros.add(createLabel("Evento", fonteLabel), gbc);

        gbc.gridy = 1;
        comboEvento = new JComboBox<>(EVENTOS_SENTINELAS_LIST);
        comboEvento.setFont(fonteCampo);
        panelFiltros.add(comboEvento, gbc);

        // 2. Mês
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        panelFiltros.add(createLabel("Mês", fonteLabel), gbc);

        gbc.gridy = 1;
        comboMes = new JComboBox<>(MESES_LIST);
        comboMes.setFont(fonteCampo);
        panelFiltros.add(comboMes, gbc);

        // 3. Ano
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0.25;
        panelFiltros.add(createLabel("Ano", fonteLabel), gbc);

        gbc.gridy = 1;
        String[] anos = {"Todos", "2024", "2025", "2026"};
        comboAno = new JComboBox<>(anos);
        comboAno.setFont(fonteCampo);
        panelFiltros.add(comboAno, gbc);

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


        //Painel da Tabela

        String[] colunas = {"Eventos", "Data", "Ocorrências"};
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

    // MÉTODOS DE CABEÇALHO

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Cores.COR_FUNDO_CLARO);
        headerPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        // Lado Esquerdo: Nome e Subtítulo
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        logoPanel.setOpaque(false);

        JLabel nameLabel = new JLabel("<html>RECANTO DO SAGRADO CORAÇÃO<br><small>ASSISTÊNCIA SOCIAL CATARINA LABOURÉ</small></html>");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        nameLabel.setForeground(Cores.COR_RODAPE);
        logoPanel.add(nameLabel);

        headerPanel.add(logoPanel, BorderLayout.WEST);

        // Lado Direito: Botões Admin, Sair e Atualizar
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        buttonPanel.setOpaque(false);

        Color btnBackground = Cores.COR_RODAPE;
        Color btnForeground = Cores.COR_FUNDO_CLARO;

        // 1. Administrador Painel
        JButton btnAdmin = createHeaderButton("Administrador Painel", btnBackground, btnForeground, "admin.png");
        btnAdmin.addActionListener(e -> {
            navigationService.abrirTelaGeral();
            dispose();
        });
        buttonPanel.add(btnAdmin);

        // 2. Sair
        JButton btnSair = createHeaderButton("Sair", btnBackground, btnForeground, "exit.png");
        btnSair.addActionListener(e -> {
            dispose();
        });
        buttonPanel.add(btnSair);

        // 3. Atualizar
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
            ImageIcon icon = new ImageIcon(getClass().getResource("/images/" + iconPath));
            Image scaledImage = icon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
            btn.setIcon(new ImageIcon(scaledImage));
            if (!text.isEmpty()) {
                btn.setHorizontalTextPosition(SwingConstants.RIGHT);
            } else {
                btn.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            }
        } catch (Exception e) {
        }

        btn.setBackground(background);
        btn.setForeground(foreground);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        return btn;
    }

    // Métodos Auxiliares Comuns

    private JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        return label;
    }

    private JTextField createTextField(Font font, int columns) {
        JTextField txt = new JTextField(columns);
        txt.setFont(font);
        return txt;
    }

    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(Cores.COR_RODAPE);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        return btn;
    }

    //Lógica de Consulta e Filtragem com o Backend

    public void carregarDadosTabela() {
        tableModel.setRowCount(0);

        // 1. CHAMA O MÉTODO DO SERVICE
        List<EventoSentinela> eventos = eventoSentinelaService.buscarTodosEventosNaAPI();

        String eventoSelecionado = (String) comboEvento.getSelectedItem();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // 2. FILTRAGEM E ADIÇÃO À TABELA
        for (EventoSentinela evento : eventos) {
            String tipoEvento = evento.getTipoEvento().toString(); 
            String dataStr = evento.getDataOcorrido().format(formatter); 
            String ocorrencia = String.valueOf(evento.getOcorrencia()); 

            boolean matchEvento = "Todos".equals(eventoSelecionado) || tipoEvento.equals(eventoSelecionado);
            
            if (matchEvento) {
                 tableModel.addRow(new Object[]{tipoEvento, dataStr, ocorrencia});
            }
        }
        
        if (tableModel.getRowCount() == 0 && !eventos.isEmpty()) {
             JOptionPane.showMessageDialog(this, "Nenhum evento encontrado para o filtro selecionado.", "Consulta Vazia", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
