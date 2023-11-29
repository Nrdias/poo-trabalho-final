package telas;

import dados.Aplicacao;
import dados.Atendimento;
import dados.EstadoAtendimento;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AlterarAtendimento extends JPanel {
    private Aplicacao app;
    private JComboBox<String> atendimentos;
    private JTextArea atendimentoPreview;
    private JPanel buttonsPanel;
    private JButton pendente;
    private JButton executando;
    private JButton finalizado;
    private JButton cancelado;


    public AlterarAtendimento(Aplicacao app) {
        this.app = app;

        this.setLayout(new GridBagLayout());

        ArrayList<Atendimento> attlist = app.getAtendimentos();

        this.atendimentos = new JComboBox<>();
        for (Atendimento atendimento : attlist) {
            this.atendimentos.addItem(String.valueOf(atendimento.getCod()));
        }
        this.atendimentoPreview = new JTextArea();
        this.atendimentoPreview.setEditable(false);
        this.atendimentoPreview.setText(this.app.atendimentosToString());

        this.buttonsPanel = new JPanel();
        this.buttonsPanel.setLayout(new GridLayout(1, 4));

        this.pendente = new JButton("Pendente");
        this.executando = new JButton("Executando");
        this.finalizado = new JButton("Finalizado");
        this.cancelado = new JButton("Cancelado");

        this.pendente.addActionListener(e -> {
            this.handlePendente();
        });

        this.executando.addActionListener(e -> {
            this.handleExecutando();
        });

        this.finalizado.addActionListener(e -> {
            this.handleFinalizado();
        });

        this.cancelado.addActionListener(e -> {
            this.handleCancelado();
        });

        this.buttonsPanel.add(this.pendente);
        this.buttonsPanel.add(this.executando);
        this.buttonsPanel.add(this.finalizado);
        this.buttonsPanel.add(this.cancelado);


        GridBagConstraints gc = new GridBagConstraints();

        gc.gridx = 0;
        gc.gridy = 0;
        gc.weightx = 1;
        gc.weighty = 0.1;
        this.add(new JLabel("Selecione um atendimento"), gc);

        gc.gridy++;
        this.add(this.atendimentos, gc);

        gc.gridy++;
        gc.weighty = 1;
        this.add(new JScrollPane(this.atendimentoPreview), gc);

        gc.gridy++;
        gc.weighty = 0.1;
        this.add(this.buttonsPanel, gc);
    }

    private void handlePendente() {
        String id = (String) this.atendimentos.getSelectedItem();
        if (id == null) return;

        Atendimento atendimento = this.app.getAtendimentoById(Integer.parseInt(id));

        if (atendimento == null) return;
        if (atendimento.getStatus() == EstadoAtendimento.FINALIZADO) return;

        atendimento.setStatus(EstadoAtendimento.PENDENTE);
        atendimento.setEquipe(null);
        this.atendimentoPreview.setText(this.app.getAtendimentoById(Integer.parseInt(id)).toString());
    }

    private void handleExecutando() {
        String id = (String) this.atendimentos.getSelectedItem();
        if (id == null) return;

        Atendimento atendimento = this.app.getAtendimentoById(Integer.parseInt(id));

        if (atendimento == null) return;
        if (atendimento.getStatus() == EstadoAtendimento.FINALIZADO) return;

        atendimento.setStatus(EstadoAtendimento.EXECUTANDO);
        this.atendimentoPreview.setText(this.app.getAtendimentoById(Integer.parseInt(id)).toString());
    }

    private void handleFinalizado() {
        String id = (String) this.atendimentos.getSelectedItem();
        if (id == null) return;

        Atendimento atendimento = this.app.getAtendimentoById(Integer.parseInt(id));

        if (atendimento == null) return;
        if (atendimento.getStatus() == EstadoAtendimento.FINALIZADO) return;

        atendimento.setStatus(EstadoAtendimento.FINALIZADO);
        this.atendimentoPreview.setText(this.app.getAtendimentoById(Integer.parseInt(id)).toString());
    }

    private void handleCancelado() {
        String id = (String) this.atendimentos.getSelectedItem();
        if (id == null) return;

        Atendimento atendimento = this.app.getAtendimentoById(Integer.parseInt(id));

        if (atendimento == null) return;
        if (atendimento.getStatus() == EstadoAtendimento.FINALIZADO) return;

        atendimento.setStatus(EstadoAtendimento.CANCELADO);
        this.atendimentoPreview.setText(this.app.getAtendimentoById(Integer.parseInt(id)).toString());
    }
}
