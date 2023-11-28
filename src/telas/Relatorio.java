package telas;

import dados.Aplicacao;

import javax.swing.*;
import java.awt.*;

public class Relatorio extends JPanel {
    Aplicacao app;

    JLabel equipamentosLabel, equipesLabel, eventosLabel, atendimentosLabel;
    JTextArea equipamentos, equipes, eventos, atendimentos, messages;

    public Relatorio(Aplicacao app) {
        super();
        this.app = app;
        this.setSize(800, 600);
        this.setLayout(new GridLayout(5, 2));

        this.equipamentosLabel = new JLabel("Equipamentos");
        this.equipamentos = new JTextArea(5, 30);
        this.equipamentos.setEditable(false);

        this.equipesLabel = new JLabel("Equipes");
        this.equipes = new JTextArea(5, 30);
        this.equipes.setEditable(false);

        this.eventosLabel = new JLabel("Eventos");
        this.eventos = new JTextArea(5, 30);
        this.eventos.setEditable(false);

        this.atendimentosLabel = new JLabel("Atendimentos");
        this.atendimentos = new JTextArea(5, 30);
        this.atendimentos.setEditable(false);

        this.add(equipamentosLabel);
        this.add(equipamentos);

        this.add(equipesLabel);
        this.add(equipes);

        this.add(eventosLabel);
        this.add(eventos);

        this.add(atendimentosLabel);
        this.add(atendimentos);


        this.messages = new JTextArea(5, 30);
        this.messages.setEditable(false);
        this.messages.setLineWrap(true);
        this.messages.setWrapStyleWord(true);
        this.add(new JLabel("Mensagens"));
        this.add(messages);

        handleRelatorio();


    }

    public void handleRelatorio() {
        if(app.getAtendimentos().isEmpty() && app.getEquipamentos().isEmpty() && app.getEquipes().isEmpty() && app.getEventos().isEmpty()) {
            messages.setText("Não há dados para gerar relatório");
            return;
        }
        handleRelatorioEquipamentos();
        handleRelatorioEquipes();
        handleRelatorioEventos();
        handleRelatorioAtendimentos();
    }

    public void handleRelatorioEquipamentos() {
        if(app.getEquipamentos().isEmpty()) {
            messages.setText("Não há dados para gerar relatório");
        } else {
            for (int i = 0; i < app.getEquipamentos().size(); i++) {
                equipamentos.append(app.getEquipamentos().get(i).toString());
            }
        }
    }

    public void handleRelatorioEquipes() {
        if(app.getEquipes().isEmpty()) {
            messages.setText("Não há dados para gerar relatório");
        } else {
            for (int i = 0; i < app.getEquipes().size(); i++) {
                equipes.append(app.getEquipes().get(i).toString());
            }
        }
    }

    public void handleRelatorioEventos() {
        if(app.getEventos().isEmpty()) {
            messages.setText("Não há dados para gerar relatório");
        } else {
            for (int i = 0; i < app.getEventos().size(); i++) {
                eventos.append(app.getEventos().get(i).toString());
            }
        }
    }

    public void handleRelatorioAtendimentos() {
        if(app.getAtendimentos().isEmpty()) {
            messages.setText("Não há dados para gerar relatório");
        } else {
            for (int i = 0; i < app.getAtendimentos().size(); i++) {
                atendimentos.append(app.getAtendimentos().get(i).toString());
            }
        }
    }

}
