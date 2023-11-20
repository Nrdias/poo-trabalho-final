package telas;

import dados.*;

import javax.swing.*;
import java.awt.*;


public class CadastroEquipamento extends JPanel {
    private final Aplicacao app;

    private final JTextField id;
    private final JTextField name;
    private final JTextField dailyCost;
    private final JComboBox<String> type;

    /* equipment specific fields here: */
    private JLabel fuelLabel;
    private JTextField fuel;
    private JLabel loadLabel;
    private JTextField load; // load has different names in different equipment, but it's the same thing

    private final JTextArea messages;

    private final JButton confirm;
    private final JButton clear;
    private final JButton showValues;
    private final JButton showAllEquipamentos;
    private final JButton closeApp;


    public CadastroEquipamento(Aplicacao app) {
        super();

        // set the app
        this.app = app;

        // create text fields
        this.id = new JTextField(10);
        this.name = new JTextField(10);
        this.dailyCost = new JTextField(10);
        this.type = new JComboBox<>();

        this.type.addItem("Barco");
        this.type.addItem("Escavadeira");
        this.type.addItem("Caminhão Tanque");
        this.type.setSelectedItem(null);

        // create buttons
        this.confirm = new JButton("Confirmar");
        this.clear = new JButton("Limpar");
        this.showValues = new JButton("Mostrar valores");
        this.showAllEquipamentos = new JButton("Ver equipamentos");
        this.closeApp = new JButton("Fechar aplicação");

        // create messages area
        this.messages = new JTextArea(10, 30);
        this.messages.setEditable(false);

        // event listeners
        this.confirm.addActionListener(e -> handleConfirmation());
        this.clear.addActionListener(e -> handleClear());
        this.showValues.addActionListener(e -> handleShowValues());
        this.showAllEquipamentos.addActionListener(e -> handleShowEquipamentos());
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
        this.add(new JLabel("ID:"), gc);
        gc.gridy++;
        this.add(new JLabel("Nome:"), gc);
        gc.gridy++;
        this.add(new JLabel("Custo diário:"), gc);
        gc.gridy++;
        this.add(new JLabel("Tipo:"), gc);

        // equipment specific labels
        if (this.fuelLabel != null) {
            gc.gridy++;
            this.add(fuelLabel, gc);
        }
        if (this.loadLabel != null) {
            gc.gridy++;
            this.add(loadLabel, gc);
        }

        gc.gridwidth = 2;
        gc.gridy = 0;
        gc.gridx = 1;
        this.add(id, gc);
        gc.gridy++;
        this.add(name, gc);
        gc.gridy++;
        this.add(dailyCost, gc);
        gc.gridy++;
        this.add(type, gc);

        // equipment specific fields
        if (this.fuel != null) {
            gc.gridy++;
            this.add(fuel, gc);
        }
        if (this.load != null) {
            gc.gridy++;
            this.add(load, gc);
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
        this.add(showAllEquipamentos, gc);
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
        this.add(messages, gc);


        this.revalidate();
        this.repaint();
    }


    private void handleConfirmation() {
        String id = this.id.getText();
        String name = this.name.getText();
        String dailyCost = this.dailyCost.getText();
        Double parsedDailyCost = null;
        String type = (String) this.type.getSelectedItem();

        String fuel = this.fuel != null ? this.fuel.getText() : "";
        String load = this.load != null ? this.load.getText() : "";

        boolean hasErrors = false;

        this.messages.setText("");

        if (id.isEmpty()) {
            this.messages.append("Insira um ID.\n");
            hasErrors = true;
        } else {
            try {
                Integer.parseInt(id);
            } catch (NumberFormatException e) {
                this.messages.append("ID inválido. Deve ser do tipo Integer.\n");
                hasErrors = true;
            }
        }

        if (name.isEmpty()) {
            this.messages.append("Insira um nome.\n");
            hasErrors = true;
        }

        if (dailyCost.isEmpty()) {
            this.messages.append("Insira um custo diário.\n");
            hasErrors = true;
        } else {
            try {
                parsedDailyCost = Double.parseDouble(dailyCost);
            } catch (NumberFormatException e) {
                this.messages.append("Custo diário inválido. Deve ser do tipo Double. \n");
                hasErrors = true;
            }
        }

        if (type == null) {
            this.messages.append("Selecione um tipo de equipamento.\n");
            hasErrors = true;
        } else {
            switch (type) {
                case "Barco":
                    if (load.isEmpty()) {
                        this.messages.append("Insira uma capacidade.\n");
                        hasErrors = true;
                    } else {
                        try {
                            Integer.parseInt(load);
                        } catch (NumberFormatException e) {
                            this.messages.append("Capacidade inválida. Deve ser do tipo Integer.\n");
                            hasErrors = true;
                        }
                    }
                    break;
                case "Escavadeira":
                    if (fuel.isEmpty()) {
                        this.messages.append("Insira um combustível.\n");
                        hasErrors = true;
                    }
                    if (load.isEmpty()) {
                        this.messages.append("Insira uma carga.\n");
                        hasErrors = true;
                    } else {
                        try {
                            Double.parseDouble(load);
                        } catch (NumberFormatException e) {
                            this.messages.append("Carga inválida. Deve ser do tipo Double.\n");
                            hasErrors = true;
                        }
                    }
                    break;
                case "Caminhão Tanque":
                    if (load.isEmpty()) {
                        this.messages.append("Insira uma capacidade.\n");
                        hasErrors = true;
                    } else {
                        try {
                            Double.parseDouble(load);
                        } catch (NumberFormatException e) {
                            this.messages.append("Capacidade inválida. Deve ser do tipo Double.\n");
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

        Equipamento e = switch (type) {
            case "Barco" -> new Barco(Integer.parseInt(id), name, parsedDailyCost, (int) Double.parseDouble(load));
            case "Escavadeira" ->
                    new Escavadeira(Integer.parseInt(id), name, parsedDailyCost, fuel, Double.parseDouble(load));
            case "Caminhão Tanque" ->
                    new CaminhaoTanque(Integer.parseInt(id), name, parsedDailyCost, Double.parseDouble(load));
            default -> throw new IllegalStateException("Unexpected value: " + type); // não deve ocorrer
        };

        if (this.app.addEquipamento(e)) {
            this.messages.append("Equipamento adicionado com sucesso!");
        } else {
            this.messages.append("ERRO! ID do equipamento já existe!");
        }
    }


    private void handleClear() {
        this.id.setText("");
        this.name.setText("");
        this.dailyCost.setText("");
        this.type.setSelectedItem(null);
        this.messages.setText("");
        this.fuel = null;
        this.fuelLabel = null;
        this.load = null;
        this.loadLabel = null;
        this.draw();
    }

    private void handleShowValues() {
        String id = this.id.getText();
        String name = this.name.getText();
        String dailyCost = this.dailyCost.getText();
        String type = (String) this.type.getSelectedItem();

        String fuel = this.fuel != null ? this.fuel.getText() : "";
        String load = this.load != null ? this.load.getText() : "";

        this.messages.setText("");

        if (!id.isEmpty()) {
            this.messages.append("ID: " + id + "\n");
        }
        if (!name.isEmpty()) {
            this.messages.append("Nome: " + name + "\n");
        }
        if (!dailyCost.isEmpty()) {
            this.messages.append("Custo diário: " + dailyCost + "\n");
        }
        if (type != null) {
            this.messages.append("Tipo: " + type + "\n");
        }
        if (!fuel.isEmpty()) {
            this.messages.append("Combustível: " + fuel + "\n");
        }
        if (!load.isEmpty()) {
            this.messages.append("Capacidade: " + load + "\n");
        }
    }

    private void handleShowEquipamentos() {
        this.messages.setText(this.app.toString());
    }


    private void handleTypeChange() {
        String selected = (String) this.type.getSelectedItem();

        if (selected == null) {
            this.messages.setText("");
            return;
        }

        switch (selected) {
            case "Barco":
            case "Caminhão Tanque":
                this.fuelLabel = null;
                this.fuel = null;
                this.loadLabel = new JLabel("Capacidade");
                this.load = new JTextField(10);

                break;
            case "Escavadeira":
                this.loadLabel = new JLabel("Carga");
                this.load = new JTextField(10);
                this.fuelLabel = new JLabel("Combustível");
                this.fuel = new JTextField(10);
                break;
        }

        this.draw();

    }
}
