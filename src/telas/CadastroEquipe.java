package telas;

import dados.*;

import javax.swing.*;
import java.awt.*;

public class CadastroEquipe extends JPanel {
    private Aplicacao app;
    private JLabel labelCodinome, labelIntegrantes, labelLatitude, labelLongitude;
    private JTextField codinome, integrantes, latitude, longitude;
    private JButton cadastrar, listar, limpar;
    private JTextArea messages;

    public CadastroEquipe(Aplicacao app) {
        super();
        this.app = app;
        this.setLayout(new GridBagLayout());

        labelCodinome = new JLabel("Codinome da Equipe");
        codinome = new JTextField(10);

        labelIntegrantes = new JLabel("Quantidade de Integrantes");
        integrantes = new JTextField(10);

        labelLatitude = new JLabel("Latitude");
        latitude = new JTextField(10);

        labelLongitude = new JLabel("Longitude");
        longitude = new JTextField(10);

        cadastrar = new JButton("Confirmar Cadastro");
        listar = new JButton("Listar Equipes");
        limpar = new JButton("Limpar");

        messages = new JTextArea(5, 30);

        cadastrar.addActionListener(e -> {
            this.handleCadastro();
        });

        listar.addActionListener(e -> {
            this.handleListEquipes();
        });

        limpar.addActionListener(e -> {
            this.handleClean();
        });

        var gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.BOTH;

        // add labels
        gc.gridx = 0;
        gc.gridy = 0;
        this.add(labelCodinome, gc);
        gc.gridy++;
        this.add(labelIntegrantes, gc);
        gc.gridy++;
        this.add(labelLatitude, gc);
        gc.gridy++;
        this.add(labelLongitude, gc);

        // add text fields
        gc.gridwidth = 2;
        gc.gridx = 1;
        gc.gridy = 0;
        this.add(codinome, gc);
        gc.gridy++;
        this.add(integrantes, gc);
        gc.gridy++;
        this.add(latitude, gc);
        gc.gridy++;
        this.add(longitude, gc);

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

    }

    private void handleCadastro() {
        String codinome = this.codinome.getText();
        String integrantes = this.integrantes.getText();
        String latitude = this.latitude.getText();
        String longitude = this.longitude.getText();

        if (codinome.isEmpty() || integrantes.isEmpty() || latitude.isEmpty() || longitude.isEmpty()) {
            this.messages.setText("Preencha todos os campos!");
        } else {
            try {
                int quantidade = Integer.parseInt(integrantes);
                double lat = Double.parseDouble(latitude);
                double lon = Double.parseDouble(longitude);
                Equipe equipe = new Equipe(codinome, quantidade, lat, lon);
                this.app.addEquipe(equipe);
                this.handleClean();
                this.messages.setText("Equipe cadastrada com sucesso!");
            } catch (NumberFormatException e) {
                this.messages.setText("Preencha os campos corretamente!");
            }
        }
    }

    private void handleListEquipes() {
        this.messages.setText(this.app.equipesToString());
    }

    private void handleClean() {
        this.codinome.setText("");
        this.integrantes.setText("");
        this.latitude.setText("");
        this.longitude.setText("");
        this.messages.setText("");
    }
}
