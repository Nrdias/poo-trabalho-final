package telas;

import dados.*;

import javax.swing.*;
import java.awt.*;

// TODO: após alocar um atendimento, sugerir o próximo atendimento
public class AlocarAtendimentos extends JPanel {
    private Aplicacao app;
    private Atendimento atendimento;
    private JButton alocar;
    private JTextArea messages;

    public AlocarAtendimentos(Aplicacao app) {
        super();

        this.app = app;

        this.setLayout(new BorderLayout());
        this.add(new JLabel("Alocar Atendimentos"), BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());

        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.BOTH;
        gc.gridx = 0;
        gc.gridy = 0;

        int pendentes = this.app.getAtendimentosPendentesSize();

        if (pendentes == 0) {
            centerPanel.add(new JLabel("Não há atendimentos pendentes! :)"), gc);
        } else {
            centerPanel.add(new JLabel("Quantidade de atendimentos pendentes: " + pendentes), gc);

            this.atendimento = this.app.peekAtendimento();

            gc.gridy++;
            centerPanel.add(this.renderAtendimento(), gc);
            gc.gridy++;
            alocar = new JButton("Alocar");
            alocar.addActionListener(e -> this.handleAlocar());
            centerPanel.add(alocar, gc);
            gc.gridy++;
            messages = new JTextArea(5, 20);
            messages.setEditable(false);
            centerPanel.add(messages, gc);
        }

        this.add(centerPanel);

    }

    private JPanel renderAtendimento() {
        JPanel atendimentoPanel = new JPanel();
        atendimentoPanel.setLayout(new GridLayout(3, 2));

        atendimentoPanel.add(new JLabel("Código do atendimento: " + atendimento.getCod()));
        atendimentoPanel.add(new JLabel("Data de inicio: " + atendimento.getDataInicio()));
        atendimentoPanel.add(new JLabel("Duração: " + atendimento.getDuracao() + " dias"));
        atendimentoPanel.add(new JLabel("Status: " + atendimento.getStatus()));
        Evento evento = atendimento.getEvento();
        atendimentoPanel.add(new JLabel("Evento: " + evento.getCodigo()));

        if (evento instanceof Terremoto) {
            atendimentoPanel.add(new JLabel("Tipo de evento: Terremoto"));
        } else if (evento instanceof Seca) {
            atendimentoPanel.add(new JLabel("Tipo de evento: Seca"));
        } else if (evento instanceof Ciclone) {
            atendimentoPanel.add(new JLabel("Tipo de evento: Ciclone"));
        } else {
            atendimentoPanel.add(new JLabel("Tipo de evento: Desconhecido"));
        }

        atendimentoPanel.setBackground(Color.LIGHT_GRAY);

        return atendimentoPanel;
    }

    private void handleAlocar() {
        String message = this.app.alocarAtendimento();

        if (message == null) {
            this.messages.setText("Atendimento alocado com sucesso!\n");
            this.messages.append("Equipe designada: " + atendimento.getEquipe());
            this.alocar.setEnabled(false);
        } else {
            this.messages.setText("Não foi possível alocar o atendimento.\nMotivo: " + message + ".");
            this.alocar.setEnabled(false);
        }
    }
}
