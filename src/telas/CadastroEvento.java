package telas;

import dados.*;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class CadastroEvento extends JPanel {
    private final Aplicacao app;

    private final JTextField codigo;
    private final JTextField data;
    private final JTextField latitude;
    private final JTextField longitude;
    private final JComboBox<String> type;

    /* equipment specific fields here: */
    private JLabel velocidadeLabel;
    private JTextField velocidade;
    private JLabel precipitacaoLabel;
    private JTextField precipitacao;
    private JLabel magnitudeLabel;
    private JTextField magnitude;
    private JLabel estiagemLabel;
    private JTextField estiagem;

    private final JTextArea messages;

    private final JButton confirm;
    private final JButton clear;
    private final JButton showValues;
    private final JButton showAllEventos;
    private final JButton closeApp;


    public CadastroEvento(Aplicacao app) {
        super();

        // set the app
        this.app = app;

        // create text fields
        this.codigo = new JTextField(10);
        this.data = new JTextField(10); // TODO: date picker?
        this.latitude = new JTextField(10);
        this.longitude = new JTextField(10);
        this.type = new JComboBox<>();

        this.type.addItem("Ciclone");
        this.type.addItem("Terremoto");
        this.type.addItem("Seca");
        this.type.setSelectedItem(null);

        // create buttons
        this.confirm = new JButton("Confirmar");
        this.clear = new JButton("Limpar");
        this.showValues = new JButton("Mostrar valores");
        this.showAllEventos = new JButton("Ver eventos");
        this.closeApp = new JButton("Fechar aplicação");

        // create messages area
        this.messages = new JTextArea(10, 30);
        this.messages.setEditable(false);

        // event listeners
        this.confirm.addActionListener(e -> handleConfirmation());
        this.clear.addActionListener(e -> handleClear());
        this.showValues.addActionListener(e -> handleShowValues());
        this.showAllEventos.addActionListener(e -> handleShowEventos());
        this.closeApp.addActionListener(e -> System.exit(0));

        this.type.addActionListener(e -> handleTypeChange());

        // set the layout of the panel
        this.setLayout(new GridBagLayout());
        this.draw();
    }

    private void draw() {
        this.removeAll();

        GridBagConstraints gc = new GridBagConstraints();

        gc.fill = GridBagConstraints.HORIZONTAL;

        // labels and inputs
        gc.gridx = 0;
        gc.gridy = 0;
        this.add(new JLabel("Código:"), gc);
        gc.gridy++;
        this.add(new JLabel("Data:"), gc);
        gc.gridy++;
        this.add(new JLabel("Latitude:"), gc);
        gc.gridy++;
        this.add(new JLabel("Longitude:"), gc);
        gc.gridy++;
        this.add(new JLabel("Tipo:"), gc);

        // equipment specific labels
        if (this.velocidadeLabel != null) {
            gc.gridy++;
            this.add(velocidadeLabel, gc);
        }
        if (this.precipitacaoLabel != null) {
            gc.gridy++;
            this.add(precipitacaoLabel, gc);
        }
        if (this.magnitudeLabel != null) {
            gc.gridy++;
            this.add(magnitudeLabel, gc);
        }
        if (this.estiagemLabel != null) {
            gc.gridy++;
            this.add(estiagemLabel, gc);
        }

        gc.gridwidth = 2;
        gc.gridy = 0;
        gc.gridx = 1;
        this.add(codigo, gc);
        gc.gridy++;
        this.add(data, gc);
        gc.gridy++;
        this.add(latitude, gc);
        gc.gridy++;
        this.add(longitude, gc);
        gc.gridy++;
        this.add(type, gc);

        // equipment specific fields
        if (this.velocidade != null) {
            gc.gridy++;
            this.add(velocidade, gc);
        }
        if (this.precipitacao != null) {
            gc.gridy++;
            this.add(precipitacao, gc);
        }
        if (this.magnitude != null) {
            gc.gridy++;
            this.add(magnitude, gc);
        }
        if (this.estiagem != null) {
            gc.gridy++;
            this.add(estiagem, gc);
        }


        // buttons
        gc.gridwidth = 1;

        gc.gridx = 0;
        gc.gridy++;
        this.add(confirm, gc);
        gc.gridx++;
        this.add(clear, gc);
        gc.gridx++;
        this.add(showValues, gc);
        gc.gridy++;
        gc.gridx = 0;
        this.add(showAllEventos, gc);
        gc.gridx++;
        this.add(closeApp, gc);

        // separator
        gc.gridwidth = 3;

        Insets prev = gc.insets;
        gc.insets = new Insets(10, 0, 10, 0);

        gc.gridx = 0;
        gc.gridy++;
        this.add(new JSeparator(), gc);

        gc.insets = prev;


        // messages area
        gc.gridx = 0;
        gc.gridy++;
        this.add(new JScrollPane(messages), gc);


        this.revalidate();
        this.repaint();
    }


    private void handleConfirmation() {
        String codigo = this.codigo.getText();
        String data = this.data.getText();
        String latitude = this.latitude.getText();
        String longitude = this.longitude.getText();
        Date parsedDate = null;
        Double parsedLatitude = null;
        Double parsedLongitude = null;
        String type = (String) this.type.getSelectedItem();

        Double parsedVelocidade = null;
        Double parsedPrecipitacao = null;
        Double parsedMagnitude = null;
        Integer parsedEstiagem = null;

        boolean hasErrors = false;

        this.messages.setText("");

        if (codigo.isEmpty()) {
            this.messages.append("Insira um código.\n");
            hasErrors = true;
        }

        if (data.isEmpty()) {
            this.messages.append("Insira uma data.\n");
            hasErrors = true;
        } else {
            try {
                parsedDate = new Date(data);
            } catch (IllegalArgumentException e) {
                this.messages.append("Data inválida. Deve ser do tipo Date.\n");
                hasErrors = true;
            }
        }

        if (latitude.isEmpty()) {
            this.messages.append("Insira uma latitude.\n");
            hasErrors = true;
        } else {
            try {
                parsedLatitude = Double.parseDouble(latitude);
            } catch (NumberFormatException e) {
                this.messages.append("Latitude inválida. Deve ser do tipo Double. \n");
                hasErrors = true;
            }
        }

        if (longitude.isEmpty()) {
            this.messages.append("Insira uma longitude.\n");
            hasErrors = true;
        } else {
            try {
                parsedLongitude = Double.parseDouble(longitude);
            } catch (NumberFormatException e) {
                this.messages.append("Longitude inválida. Deve ser do tipo Double. \n");
                hasErrors = true;
            }
        }

        if (type == null) {
            this.messages.append("Selecione um tipo de evento.\n");
            hasErrors = true;
        } else {
            switch (type) {
                case "Ciclone":
                    String velocidade = this.velocidade != null ? this.velocidade.getText() : "";
                    String precipitacao = this.precipitacao != null ? this.precipitacao.getText() : "";

                    if (velocidade.isEmpty()) {
                        this.messages.append("Insira uma velocidade.\n");
                        hasErrors = true;
                    } else {
                        try {
                            parsedVelocidade = Double.parseDouble(velocidade);
                        } catch (NumberFormatException e) {
                            this.messages.append("Velocidade inválida. Deve ser do tipo Double.\n");
                            hasErrors = true;
                        }
                    }
                    if (precipitacao.isEmpty()) {
                        this.messages.append("Insira uma precipitação.\n");
                        hasErrors = true;
                    } else {
                        try {
                            parsedPrecipitacao = Double.parseDouble(precipitacao);
                        } catch (NumberFormatException e) {
                            this.messages.append("Precipitação inválida. Deve ser do tipo Double.\n");
                            hasErrors = true;
                        }
                    }
                    break;
                case "Terremoto":
                    String magnitude = this.magnitude != null ? this.magnitude.getText() : "";

                    if (magnitude.isEmpty()) {
                        this.messages.append("Insira uma magnitude.\n");
                        hasErrors = true;
                    } else {
                        try {
                            parsedMagnitude = Double.parseDouble(magnitude);
                        } catch (NumberFormatException e) {
                            this.messages.append("Magnitude inválida. Deve ser do tipo Double.\n");
                            hasErrors = true;
                        }
                    }

                    break;
                case "Seca":
                    String estiagem = this.estiagem != null ? this.estiagem.getText() : "";

                    if (estiagem.isEmpty()) {
                        this.messages.append("Insira uma estiagem.\n");
                        hasErrors = true;
                    } else {
                        try {
                            parsedEstiagem = Integer.parseInt(estiagem);
                        } catch (NumberFormatException e) {
                            this.messages.append("Estiagem inválida. Deve ser do tipo Integer.\n");
                            hasErrors = true;
                        }
                    }

                    break;
                default:
                    this.messages.append("Tipo de equipamento inválido.\n");
                    hasErrors = true;
                    break;
            }
        }

        if (hasErrors) {
            return;
        }

        Evento e = switch (type) {
            case "Ciclone" ->
                    new Ciclone(codigo, parsedDate.toString(), parsedLatitude, parsedLongitude, parsedVelocidade, parsedPrecipitacao);
            case "Terremoto" ->
                    new Terremoto(codigo, parsedDate.toString(), parsedLatitude, parsedLongitude, parsedMagnitude);
            case "Seca" -> new Seca(codigo, parsedDate.toString(), parsedLatitude, parsedLongitude, parsedEstiagem);
            default -> throw new IllegalStateException("Unexpected value: " + type); // não deve ocorrer
        };

        if (this.app.addEvento(e)) {
            this.messages.append("Evento adicionado com sucesso!");
        } else {
            this.messages.append("ERRO! Código do evento já existe!");
        }
    }


    private void handleClear() {
        this.codigo.setText("");
        this.data.setText("");
        this.latitude.setText("");
        this.longitude.setText("");
        this.type.setSelectedItem(null);
        this.messages.setText("");

        this.velocidadeLabel = null;
        this.velocidade = null;
        this.precipitacaoLabel = null;
        this.precipitacao = null;
        this.magnitudeLabel = null;
        this.magnitude = null;
        this.estiagemLabel = null;
        this.estiagem = null;

        this.draw();
    }

    private void handleShowValues() {
        String codigo = this.codigo.getText();
        String data = this.data.getText();
        String latitude = this.latitude.getText();
        String longitude = this.longitude.getText();
        String type = (String) this.type.getSelectedItem();

        String velocidade = this.velocidade == null ? "" : this.velocidade.getText();
        String precipitacao = this.precipitacao == null ? "" : this.precipitacao.getText();
        String magnitude = this.magnitude == null ? "" : this.magnitude.getText();
        String estiagem = this.estiagem == null ? "" : this.estiagem.getText();

        this.messages.setText("");

        if (!codigo.isEmpty()) this.messages.append("Código: " + codigo + "\n");
        if (!data.isEmpty()) this.messages.append("Data: " + data + "\n");
        if (!latitude.isEmpty()) this.messages.append("Latitude: " + latitude + "\n");
        if (!longitude.isEmpty()) this.messages.append("Longitude: " + longitude + "\n");
        if (type != null) this.messages.append("Type: " + type + "\n");
        if (!velocidade.isEmpty()) this.messages.append("Velocidade: " + velocidade + "\n");
        if (!precipitacao.isEmpty()) this.messages.append("Precipitação: " + precipitacao + "\n");
        if (!magnitude.isEmpty()) this.messages.append("Magnitude: " + magnitude + "\n");
        if (!estiagem.isEmpty()) this.messages.append("Estiagem: " + estiagem + "\n");
    }

    private void handleShowEventos() {
        this.messages.setText(this.app.eventosToString());
    }

    private void handleTypeChange() {
        String selected = (String) this.type.getSelectedItem();

        if (selected == null) {
            this.messages.setText("");
            return;
        }

        switch (selected) {
            case "Ciclone":
                this.velocidadeLabel = new JLabel("Velocidade");
                this.velocidade = new JTextField(10);
                this.precipitacaoLabel = new JLabel("Precipitação");
                this.precipitacao = new JTextField(10);
                break;
            case "Terremoto":
                this.magnitudeLabel = new JLabel("Magnitude");
                this.magnitude = new JTextField(10);
                break;
            case "Seca":
                this.estiagemLabel = new JLabel("Estiagem");
                this.estiagem = new JTextField(10);
                break;
        }

        this.draw();
    }
}
