package telas;

import dados.*;

import javax.swing.*;
import java.awt.*;

public class AlocarAtendimentos extends JPanel {
    public Aplicacao app;

    public AlocarAtendimentos(Aplicacao app) {
        super();

        this.app = app;

        this.setLayout(new BorderLayout());
        this.add(new JLabel("Alocar Atendimentos"), BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());

        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;

        int pendentes = this.app.getAtendimentosPendentesSize();

        if (pendentes == 0) {
            centerPanel.add(new JLabel("Não há atendimentos pendentes! :)"), gc);
        } else {
            centerPanel.add(new JLabel("Quantidade de atendimentos pendentes: " + pendentes), gc);
            gc.gridy++;
            JPanel proximoAtendimento = this.renderAtendimento(this.app.peekAtendimento());
            centerPanel.add(proximoAtendimento, gc);
            gc.gridy++;
            JButton alocar = new JButton("Alocar");
            centerPanel.add(alocar, gc);
        }

        this.add(centerPanel);

    }

    private JPanel renderAtendimento(Atendimento atendimento) {
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
}
