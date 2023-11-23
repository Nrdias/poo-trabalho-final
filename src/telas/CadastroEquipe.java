package telas;

import dados.*;

import javax.swing.*;

public class CadastroEquipe extends JPanel {
    private Aplicacao app;
    private JLabel labelCodinome, labelIntegrantes, labelLatitude, labelLongitude;
    private JTextField codinome, integrantes, latitude, longitude;
    private JButton cadastrar, listar, limpar;
    private JTextArea messages;

    public CadastroEquipe(Aplicacao app) {
        super();
        this.app = app;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

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

        this.add(labelCodinome);
        this.add(codinome);
        this.add(labelIntegrantes);
        this.add(integrantes);
        this.add(labelLatitude);
        this.add(latitude);
        this.add(labelLongitude);
        this.add(longitude);
        this.add(cadastrar);
        this.add(listar);
        this.add(limpar);
        this.add(messages);

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
                this.messages.setText("Equipe cadastrada com sucesso!");
                this.handleClean();
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
