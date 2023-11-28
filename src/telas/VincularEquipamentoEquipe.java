package telas;

import dados.Aplicacao;
import dados.Equipamento;
import dados.Equipe;

import javax.swing.*;
import java.awt.*;

public class VincularEquipamentoEquipe extends JPanel {
    private Aplicacao app;
    private JPanel equipamentoSelection;
    private JPanel equipeSelection;

    private Equipamento selectedEquipamento;
    private Equipe selectedEquipe;

    private JTextArea equipamentoPreview;
    private JTextArea equipePreview;

    private JButton vincular;

    public VincularEquipamentoEquipe(Aplicacao app) {
        super();
        this.app = app;

        this.setLayout(new BorderLayout());

        this.add(new JLabel("Vincular Equipamento a Equipe"), BorderLayout.NORTH);

        this.initSelectionPanels();

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1, 2));
        mainPanel.add(equipamentoSelection);
        mainPanel.add(equipeSelection);
        this.add(mainPanel, BorderLayout.CENTER);

        this.vincular = new JButton("Vincular");
        this.vincular.addActionListener(e -> {
            if (this.selectedEquipamento == null || this.selectedEquipe == null) {
                JOptionPane.showMessageDialog(this, "Selecione um equipamento e uma equipe");
                return;
            }

            boolean vinculado = this.app.vincularEquipamentoEquipe(this.selectedEquipamento, this.selectedEquipe);
            if (!vinculado) {
                JOptionPane.showMessageDialog(this, "Equipamento já vinculado a equipe");
                return;
            }

            JOptionPane.showMessageDialog(this, "Equipamento vinculado com sucesso");
        });

        this.add(vincular, BorderLayout.SOUTH);
    }

    private void initSelectionPanels() {
        this.equipamentoSelection = new JPanel();
        this.equipeSelection = new JPanel();

        this.equipamentoSelection.setLayout(new GridBagLayout());
        this.equipeSelection.setLayout(new GridBagLayout());

        JComboBox<String> equipamentos = new JComboBox<>();
        JComboBox<String> equipes = new JComboBox<>();

        this.app.getEquipamentos().forEach(equipamento -> {
            equipamentos.addItem(equipamento.getId().toString());
        });
        equipamentos.setSelectedItem(null);
        equipamentos.addActionListener(e -> {
            String nome = (String) equipamentos.getSelectedItem();
            if (nome == null) {
                equipamentoPreview.setText("");
                return;
            }
            Equipamento equipamento = app.getEquipamentoById(Integer.parseInt(nome));
            this.selectedEquipamento = equipamento;
            if (equipamento == null) return;

            this.equipamentoPreview.setText(equipamento.toString());
        });

        this.app.getEquipes().forEach(equipe -> {
            equipes.addItem(equipe.getCodinome());
        });
        equipes.setSelectedItem(null);
        equipes.addActionListener(e -> {
            String nome = (String) equipes.getSelectedItem();
            if (nome == null) {
                equipePreview.setText("");
                return;
            }

            Equipe equipe = app.getEquipeByCodinome(nome);
            this.selectedEquipe = equipe;
            if (equipe == null) return;

            equipePreview.setText(equipe.toString());
        });

        this.equipamentoPreview = new JTextArea(10, 65);
        this.equipePreview = new JTextArea(10, 65);


        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.BOTH;

        gc.gridx = 0;
        gc.gridy = 0;
        gc.weighty = 0.1;
        this.equipamentoSelection.add(new JLabel("Equipamento"), gc);
        this.equipeSelection.add(new JLabel("Equipe"), gc);

        gc.gridy++;
        gc.weighty = 0.2;
        this.equipamentoSelection.add(equipamentos, gc);
        this.equipeSelection.add(equipes, gc);

        gc.gridy++;
        gc.weighty = 1;
        this.equipamentoSelection.add(equipamentoPreview, gc);
        this.equipeSelection.add(equipePreview, gc);
    }
}
