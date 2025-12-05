package com.ProjetoExtensao.Projeto.view;

import com.ProjetoExtensao.Projeto.infra.Cores;
import com.ProjetoExtensao.Projeto.servicos.EventoSentinelaService; 
import com.ProjetoExtensao.Projeto.servicos.NavigationService;
import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.time.LocalDate;

@Component
@NoArgsConstructor
public class TelaRegistroEventoSentinela extends JFrame {

    @Autowired
    private EventoSentinelaService eventoSentinelaService; 
    @Lazy
    @Autowired 
    private NavigationService navigationService; 

    private JTextField txtNome;
    private JTextField txtData;
    private JSpinner spinnerOcorrencias;
    private JTextArea txtObservacoes;
    private ButtonGroup grupoEventos; 

    @PostConstruct
    public void initUI() {
        setTitle("Tela 7 - Registro de Evento Sentinela");
        setSize(1000, 700);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        add(createHeaderPanel(), BorderLayout.NORTH);

        JPanel panelCenter = new JPanel(new BorderLayout());
        panelCenter.setBackground(Cores.COR_FUNDO_CLARO);
        panelCenter.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        
        //Título Principal e Botão Consultar
        JPanel panelHeader = new JPanel(new BorderLayout());
        panelHeader.setOpaque(false);
        
        JLabel titleLabel = new JLabel("Registro de Eventos");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Cores.COR_LETRA_PAINEL); 
        
        JButton btnConsultar = new JButton("Consultar Eventos →");
        btnConsultar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnConsultar.setForeground(new Color(0x3333FF)); 
        btnConsultar.setBorderPainted(false);
        btnConsultar.setFocusPainted(false);
        btnConsultar.setContentAreaFilled(false);
        btnConsultar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnConsultar.addActionListener(e -> {
            navigationService.abrirTelaConsultaEventosSentinelas();
            dispose();
        });

        panelHeader.add(titleLabel, BorderLayout.WEST);
        panelHeader.add(btnConsultar, BorderLayout.EAST);
        
        panelCenter.add(panelHeader, BorderLayout.NORTH);


        // Painel de Conteúdo Principal
        JPanel panelContent = new JPanel(new GridBagLayout());
        panelContent.setOpaque(false);
        panelContent.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        
        GridBagConstraints grid = new GridBagConstraints();
        grid.insets = new Insets(10, 10, 10, 10);
        grid.fill = GridBagConstraints.HORIZONTAL;

        Font fonteLabel = new Font("Segoe UI", Font.BOLD, 14);
        Font fonteCampo = new Font("Segoe UI", Font.PLAIN, 13);
        
        // 1. LINHA DO RESIDENTE/PROFISSIONAL
        grid.gridx = 0;
        grid.gridy = 0;
        grid.gridwidth = 2;
        grid.weightx = 1.0;
        

        panelContent.add(createLabel("Nome do Residente/Profissional", fonteLabel), grid); 
        
        grid.gridy++;
        txtNome = createTextField(fonteCampo, 25);
        panelContent.add(txtNome, grid);
        
        // 2. LINHA DA DATA E OCORRÊNCIAS=
        grid.gridy++;
        grid.gridwidth = 1;
        
        // Coluna 1: Data
        grid.gridx = 0;
        grid.weightx = 0.5;
        
        JPanel panelData = new JPanel(new BorderLayout(0, 5));
        panelData.setOpaque(false);
        panelData.add(createLabel("Data (DD/MM/AAAA)", fonteLabel), BorderLayout.NORTH); 
        txtData = createTextField(fonteCampo, 15);
        panelData.add(txtData, BorderLayout.CENTER);
        panelContent.add(panelData, grid);
        
        // Coluna 2: Ocorrências
        grid.gridx = 1;
        grid.weightx = 0.5;
        
        JPanel panelOcorrencias = new JPanel(new BorderLayout(0, 5));
        panelOcorrencias.setOpaque(false);
        panelOcorrencias.add(createLabel("Ocorrências (Quantidade)", fonteLabel), BorderLayout.NORTH);
        
        SpinnerNumberModel model = new SpinnerNumberModel(1, 1, 999, 1);
        spinnerOcorrencias = new JSpinner(model);
        spinnerOcorrencias.setFont(fonteCampo);
        
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinnerOcorrencias, "0");
        editor.getTextField().setColumns(5);
        spinnerOcorrencias.setEditor(editor);
        
        JPanel spinnerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0)); 
        spinnerPanel.setOpaque(false);
        spinnerPanel.add(spinnerOcorrencias);
        
        panelOcorrencias.add(spinnerPanel, BorderLayout.WEST); 
        panelContent.add(panelOcorrencias, grid);
        
        // 3. LINHA DO EVENTO E OBSERVAÇÕES


        grid.gridy++;
        grid.gridwidth = 1;
        grid.fill = GridBagConstraints.BOTH; 
        
        // Coluna 1: Evento Sentinela
        grid.gridx = 0;
        grid.weighty = 1.0; 
        panelContent.add(createEventosSentinelaPanel(fonteLabel, fonteCampo), grid); 
        
        // Coluna 2: Observações
        grid.gridx = 1;
        
        JPanel panelObservacoes = new JPanel(new BorderLayout(0, 5));
        panelObservacoes.setOpaque(false);
        panelObservacoes.add(createLabel("Observações", fonteLabel), BorderLayout.NORTH);
        
        txtObservacoes = new JTextArea(5, 20);
        txtObservacoes.setFont(fonteCampo);
        txtObservacoes.setLineWrap(true);
        txtObservacoes.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(txtObservacoes);
        panelObservacoes.add(scrollPane, BorderLayout.CENTER);
        
        panelContent.add(panelObservacoes, grid);


        // Botões Salvar, Limpar, Cancelar
        grid.gridy++;
        grid.gridx = 0; 
        grid.gridwidth = 2; 
        grid.fill = GridBagConstraints.NONE; 
        grid.anchor = GridBagConstraints.EAST;

        JButton btnSalvar = createButton("Salvar");
        JButton btnLimpar = createButton("Limpar");
        JButton btnCancelar = createButton("Cancelar");

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        btnPanel.setOpaque(false);
        btnPanel.add(btnSalvar);
        btnPanel.add(btnLimpar);
        btnPanel.add(btnCancelar);

        panelContent.add(btnPanel, grid);
        
        
        panelCenter.add(panelContent, BorderLayout.CENTER);
        add(panelCenter, BorderLayout.CENTER);

        // Eventos dos botões
        btnSalvar.addActionListener(e -> registrarEvento());
        btnLimpar.addActionListener(e -> limparCampos());
        btnCancelar.addActionListener(e -> dispose());
    }
    
    
    //Lógica de Aplicação

    private String getSelectedRadioButtonText() {
        for (java.util.Enumeration<AbstractButton> buttons = grupoEventos.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                return button.getText();
            }
        }
        return null; 
    }

    private void registrarEvento() {
        try {
            // Coleta dos dados
            String nomeResidenteProfissional = txtNome.getText(); 
            String tipoEvento = getSelectedRadioButtonText();
            String dataStr = txtData.getText();
            Integer ocorrencias = (Integer) spinnerOcorrencias.getValue();
            String observacoes = txtObservacoes.getText();
            
            // Validação básica
            if (nomeResidenteProfissional.isEmpty() || tipoEvento == null || dataStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha o Nome, Data e selecione o Tipo de Evento.", 
                                                 "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // 3. CHAMADA AO CLIENTE REST (POST)
            
            String resposta = eventoSentinelaService.registrarEventoNaAPI(
                nomeResidenteProfissional,
                tipoEvento, 
                dataStr,
                ocorrencias,
                observacoes 
            ); 

            // 4. Tratar a Resposta
            if (resposta.startsWith("Erro")) {
                JOptionPane.showMessageDialog(this, resposta, "Erro no Registro", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Evento Sentinela registrado com sucesso!\n" + resposta);
                limparCampos();
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro inesperado ao registrar evento. Verifique se o Nome existe e se o formato da Data está correto (DD/MM/AAAA).", 
                                          "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void limparCampos() {
        txtNome.setText("");
        txtData.setText("");
        spinnerOcorrencias.setValue(1); 
        txtObservacoes.setText("");
        grupoEventos.clearSelection();
    }
    
    // MÉTODOS DE CABEÇALHO
    
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
            dispose();
            navigationService.abrirTelaRegistroEventosSentinelas(); 
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
}
