package telas;

import dados.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.function.Function;

public class CadastroAtendimento extends JPanel {
    private Aplicacao app;
    private Evento evento;
    private PainelDeEventos eventos;

    private JTextField codigo, dataInicio, duracao;
    private JButton cadastrar, listar, limpar;
    private JTextArea messages;

    public CadastroAtendimento(Aplicacao app) {
        super();

        this.app = app;

        this.eventos = new PainelDeEventos((evento) -> {
            this.evento = evento;
            System.out.println("Evento mudou para " + evento.getCodigo());
            this.drawAtendimentoForm();
            return null;
        }, app.getEventos());


        codigo = new JTextField(10);
        dataInicio = new JTextField(10);
        duracao = new JTextField(10);

        cadastrar = new JButton("Confirmar cadastro");
        listar = new JButton("Listar atendimentos");
        limpar = new JButton("Limpar");

        messages = new JTextArea(5, 30);

        cadastrar.addActionListener(e -> {
            this.handleCadastro();
        });

        listar.addActionListener(e -> {
            this.handleListAtendimentos();
        });

        limpar.addActionListener(e -> {
            this.handleClean();
        });


        this.drawEventSelection();
    }

    private void drawEventSelection() {
        this.removeAll();

        this.setLayout(new BorderLayout());

        this.add(new JLabel("Selecione um evento:"), BorderLayout.NORTH);

        this.add(eventos, BorderLayout.CENTER);

        this.revalidate();
        this.repaint();
    }

    private void drawAtendimentoForm() {
        this.removeAll();

        this.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.BOTH;

        gc.gridy = 0;
        gc.gridx = 0;
        this.add(new JLabel("Código do evento: "), gc);
        gc.gridx++;
        this.add(new JLabel(evento.getCodigo()), gc);
        gc.gridx++;
        JButton trocaEvento = new JButton("Trocar evento");
        trocaEvento.addActionListener(e -> {
            this.evento = null;
            this.drawEventSelection();
        });
        this.add(trocaEvento);

        gc.gridy++;
        gc.gridx = 0;
        gc.gridwidth = 3;

        Insets prev = gc.insets;
        gc.insets = new Insets(10, 0, 10, 0);
        this.add(new JSeparator(), gc);
        gc.insets = prev;


        // add labels
        gc.gridx = 0;
        gc.gridy++;
        int y = gc.gridy;
        this.add(new JLabel("Código do atendimento: "), gc);
        gc.gridy++;
        this.add(new JLabel("Data de início: "), gc);
        gc.gridy++;
        this.add(new JLabel("Duração: "), gc);


        // add text fields
        gc.gridwidth = 2;
        gc.gridx = 1;
        gc.gridy = y;
        this.add(codigo, gc);
        gc.gridy++;
        this.add(dataInicio, gc);
        gc.gridy++;
        this.add(duracao, gc);

        // add buttons
        gc.gridx = 0;
        gc.gridy++;
        gc.gridwidth = 1;
        this.add(cadastrar, gc);
        gc.gridx++;
        this.add(listar, gc);
        gc.gridx++;
        this.add(limpar, gc);

        gc.gridwidth = 3;
        gc.gridx = 0;
        gc.gridy++;
        this.add(messages, gc);


        this.revalidate();
        this.repaint();
    }

    private void handleCadastro() {
        String codigo = this.codigo.getText();
        String dataInicio = this.dataInicio.getText();
        String duracao = this.duracao.getText();

        Integer parsedCod = null;
        Date parsedDataInicio = null;
        Integer parsedDuracao = null;

        boolean hasErrors = false;

        this.messages.setText("");

        if (codigo.isEmpty()) {
            this.messages.append("Insira um código para o atendimento.\n");
            hasErrors = true;
        } else {
            try {
                parsedCod = Integer.parseInt(codigo);
            } catch (NumberFormatException e) {
                this.messages.append("O código deve ser um número.\n");
                hasErrors = true;
            }
        }


        if (dataInicio.isEmpty()) {
            this.messages.append("Insira uma data.\n");
            hasErrors = true;
        } else {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                parsedDataInicio = sdf.parse(dataInicio);
                // todo: check if date is after the event date
            } catch (IllegalArgumentException | ParseException e) {
                this.messages.append("Data inválida. Deve ter o formato dd/mm/aaaa.\n");
                hasErrors = true;
            }
        }

        if (duracao.isEmpty()) {
            this.messages.append("Insira uma duração.\n");
            hasErrors = true;
        } else {
            try {
                parsedDuracao = Integer.parseInt(duracao);
            } catch (NumberFormatException e) {
                this.messages.append("A duração deve ser um número.\n");
                hasErrors = true;
            }
        }

        if (hasErrors) {
            return;
        }

        Atendimento atendimento = new Atendimento(parsedCod, parsedDataInicio.toString(), parsedDuracao, this.evento);

        boolean added = this.app.addAtendimento(atendimento);
        if (!added) {
            this.messages.setText("Já existe um atendimento com esse código ou o evento \njá possui um atendimento relacionado com ele.\n");
        } else {
            this.messages.setText("Atendimento cadastrado com sucesso.\n");
        }
    }

    private void handleListAtendimentos() {
        this.messages.setText(this.app.atendimentosToString());
    }

    private void handleClean() {
        this.codigo.setText("");
        this.dataInicio.setText("");
        this.duracao.setText("");
        this.messages.setText("");
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
