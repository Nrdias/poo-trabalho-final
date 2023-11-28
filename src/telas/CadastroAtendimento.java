package telas;

import dados.Aplicacao;
import dados.Evento;

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

        this.setLayout(new GridBagLayout());

        this.draw();
    }

    private void draw() {
        this.removeAll();

        GridBagConstraints gc = new GridBagConstraints();

        gc.fill = GridBagConstraints.BOTH;
        gc.gridx = 0;
        gc.gridy = 0;
        this.add(new JLabel("Cadastrar Atendimento"), gc);

        gc.gridy++;
        gc.weighty = 1;
        this.add(eventos, gc);

        gc.gridy++;
        gc.weighty = 0;
        this.add(confirmar, gc);

        this.revalidate();
        this.repaint();
    }
}

// não é o jeito mais bonito, mas serve
class PainelDeEventos extends JPanel {
    private Function<Evento, Void> onEventoChange;
    private ArrayList<Evento> eventos;

    public PainelDeEventos() {
        this(evento -> null, new ArrayList<>());
    }

    public PainelDeEventos(Function<Evento, Void> onEventoChange, ArrayList<Evento> eventos) {
        super();

        this.onEventoChange = onEventoChange;
        this.eventos = eventos;

        this.draw();
    }

    public void setOnEventoChange(Function<Evento, Void> onEventoChange) {
        this.onEventoChange = onEventoChange;
    }

    public void setEventos(ArrayList<Evento> eventos) {
        this.eventos = eventos;
    }

    private void draw() {
        this.removeAll();

        this.setLayout(new GridBagLayout());

        GridBagConstraints gc = new GridBagConstraints();

        gc.fill = GridBagConstraints.BOTH;
        gc.gridx = 0;
        gc.gridy = 0;
        this.add(new JLabel("Eventos"), gc);

        gc.gridy++;
        gc.weighty = 1;
        this.add(new JScrollPane(new ListaDeEventos(eventos, evento -> {
            onEventoChange.apply(evento);
            return null;
        })), gc);

        this.revalidate();
        this.repaint();
    }
}

class ListaDeEventos extends JPanel {
    private ArrayList<Evento> eventos;
    private Function<Evento, Void> onEventoChange;

    public ListaDeEventos(ArrayList<Evento> eventos, Function<Evento, Void> onEventoChange) {
        super();

        this.eventos = eventos;
        this.onEventoChange = onEventoChange;

        this.draw();
    }

    public void setEventos(ArrayList<Evento> eventos) {
        this.eventos = eventos;
    }

    public void setOnEventoChange(Function<Evento, Void> onEventoChange) {
        this.onEventoChange = onEventoChange;
    }

    private void draw() {
        this.removeAll();

        this.setLayout(new GridBagLayout());

        GridBagConstraints gc = new GridBagConstraints();

        gc.fill = GridBagConstraints.BOTH;
        gc.gridx = 0;
        gc.gridy = 0;

        for (Evento evento : eventos) {
            JButton button = new JButton(evento.getCodigo());
            button.addActionListener(e -> {
                onEventoChange.apply(evento);
            });
            this.add(button, gc);
            gc.gridy++;
        }

        this.revalidate();
        this.repaint();
    }
}
