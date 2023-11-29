package telas;

import dados.Aplicacao;

import javax.swing.*;
import java.awt.*;

public class Relatorio extends JPanel {
    Aplicacao app;

    JLabel equipamentosLabel, equipesLabel, eventosLabel;
    JTextArea equipamentos, equipes, eventos;

    public Relatorio(Aplicacao app) {
        super();
        this.app = app;
        this.setSize(800, 600);
        this.setLayout(new GridLayout(5, 2));

        this.equipamentosLabel = new JLabel("Equipamentos");
        this.equipamentosLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.equipamentos = new JTextArea(5, 30);
        this.equipamentos.setEditable(false);

        this.equipesLabel = new JLabel("Equipes");
        this.equipes = new JTextArea(5, 30);
        this.equipes.setEditable(false);

        this.eventosLabel = new JLabel("Eventos");
        this.eventos = new JTextArea(5, 30);
        this.eventos.setEditable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        var gc = new GridBagConstraints();

        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.gridx = 0;
        gc.gridy = 0;
        gc.weighty = 0.1;
        panel.add(equipamentosLabel, gc);
        gc.gridy++;
        gc.weighty = 0.9;
        panel.add(new JScrollPane(equipamentos), gc);

        gc.gridy++;
        gc.weighty = 0.1;
        panel.add(equipesLabel, gc);
        gc.gridy++;
        gc.weighty = 0.9;
        panel.add(new JScrollPane(equipes), gc);

        gc.gridy++;
        gc.weighty = 0.1;
        panel.add(eventosLabel, gc);
        gc.gridy++;
        gc.weighty = 0.9;
        panel.add(new JScrollPane(eventos), gc);

        this.setLayout(new BorderLayout());

        this.add(new JScrollPane(panel), BorderLayout.CENTER);

        handleRelatorio();
    }

    public void handleRelatorio() {
        handleRelatorioEquipamentos();
        handleRelatorioEquipes();
        handleRelatorioEventos();
    }

    public void handleRelatorioEquipamentos() {
        if (app.hasEquipamentos()) {
            equipamentos.setText(app.equipamentosToString());
        } else {
            equipamentos.setText("Não há equipamentos para gerar relatório");
        }
    }

    public void handleRelatorioEquipes() {
        if (app.hasEquipes()) {
            equipes.setText(app.equipesToString());
        } else {
            equipes.setText("Não há equipes para gerar relatório");
        }
    }

    public void handleRelatorioEventos() {
        if (app.hasEventos()) {
            eventos.setText(app.eventosToString());
        } else {
            eventos.setText("Não há eventos para gerar relatório");
        }
    }
}
