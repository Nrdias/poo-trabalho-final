package telas;

import dados.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.function.Function;

public class CadastroAtendimento extends JPanel {
    private Aplicacao app;
    private Evento evento;
    private PainelDeEventos eventos;
    private JButton confirmar;

    public CadastroAtendimento(Aplicacao app) {
        super();

        this.app = app;

        this.eventos = new PainelDeEventos((evento) -> {
            this.evento = evento;
            System.out.println("Evento mudou para " + evento.getCodigo());
            return null;
        }, app.getEventos());
        this.confirmar = new JButton("Confirmar");

        this.setLayout(new BorderLayout());

        this.draw();
    }

    private void draw() {
        this.removeAll();

        this.add(new JLabel("Selecione um evento:" ), BorderLayout.NORTH);

        this.add(eventos, BorderLayout.CENTER);


        this.revalidate();
        this.repaint();
    }
}

// não é o jeito mais bonito, mas serve
class PainelDeEventos extends JPanel {
    private Function<Evento, Void> onEventoChange;
    private ArrayList<Evento> eventos;

    private JPanel eventosContainer;

    public PainelDeEventos(Function<Evento, Void> onEventoChange, ArrayList<Evento> eventos) {
        super();

        this.onEventoChange = onEventoChange;
        this.eventos = eventos;

        this.eventosContainer = new JPanel();
        this.eventosContainer.setLayout(new BoxLayout(this.eventosContainer, BoxLayout.Y_AXIS));

        this.draw();
    }

    private void draw() {
        this.removeAll();

        this.eventos.forEach((evento) -> {
            EventoCard card = new EventoCard(evento, this.onEventoChange);
            this.eventosContainer.add(card);
        });

        // TODO: this is not working
        JScrollPane scroll = new JScrollPane(this.eventosContainer);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setMaximumSize(new Dimension(400, 400));

        this.add(scroll);

        this.revalidate();
        this.repaint();
    }
}


class EventoCard extends JPanel {
    private Evento evento;
    private Function<Evento, Void> onEventoChange;

    public EventoCard(Evento evento, Function<Evento, Void> onEventoChange) {
        super();

        this.evento = evento;
        this.onEventoChange = onEventoChange;

        this.draw();
    }

    private void draw() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        gc.anchor = GridBagConstraints.LINE_START;

        gc.gridx = 0;
        gc.gridy = 0;

        String tipoDeEvento = "";
        if (evento instanceof Ciclone) {
            tipoDeEvento = "Ciclone";
        } else if (evento instanceof Terremoto) {
            tipoDeEvento = "Terremoto";
        } else if (evento instanceof Seca) {
            tipoDeEvento = "Seca";
        }

        gc.gridwidth = 2;
        this.add(new JLabel(tipoDeEvento), gc);

        gc.gridwidth = 1;
        gc.gridy++;
        this.add(new JLabel("Código: "), gc);
        gc.gridx++;
        this.add(new JLabel(evento.getCodigo()), gc);

        gc.gridx = 0;
        gc.gridy++;
        this.add(new JLabel("Data: "), gc);
        gc.gridx++;
        this.add(new JLabel(evento.getData()), gc);

        gc.gridx = 0;
        gc.gridy++;
        this.add(new JLabel("Latitude: "), gc);
        gc.gridx++;
        this.add(new JLabel(String.valueOf(evento.getLatitude())), gc);

        gc.gridx = 0;
        gc.gridy++;
        this.add(new JLabel("Longitude: "), gc);
        gc.gridx++;
        this.add(new JLabel(String.valueOf(evento.getLongitude())), gc);

        switch (tipoDeEvento) {
            case "Ciclone":
                Ciclone ciclone = (Ciclone) evento;
                gc.gridx = 0;
                gc.gridy++;
                this.add(new JLabel("Velocidade: "), gc);
                gc.gridx++;
                this.add(new JLabel(String.valueOf(ciclone.getVelocidade())), gc);

                gc.gridx = 0;
                gc.gridy++;
                this.add(new JLabel("Precipitação: "), gc);
                gc.gridx++;
                this.add(new JLabel(String.valueOf(ciclone.getPrecipitacao())), gc);
                break;

            case "Terremoto":
                Terremoto terremoto = (Terremoto) evento;
                gc.gridx = 0;
                gc.gridy++;
                this.add(new JLabel("Magnitude: "), gc);
                gc.gridx++;
                this.add(new JLabel(String.valueOf(terremoto.getMagnitude())), gc);
                break;

            case "Seca":
                Seca seca = (Seca) evento;
                gc.gridx = 0;
                gc.gridy++;
                this.add(new JLabel("Estiagem: "), gc);
                gc.gridx++;
                this.add(new JLabel(String.valueOf(seca.getEstiagem())), gc);
                break;
        }

        gc.gridx = 0;
        gc.gridy++;
        gc.gridwidth = 2;
        JButton selecionar = new JButton("Selecionar");
        selecionar.addActionListener(e -> {
            this.onEventoChange.apply(evento);
        });

        this.add(selecionar, gc);
    }
}
