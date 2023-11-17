package telas;

import javax.swing.*;

public class CadastroEvento extends JPanel {
    private JLabel label;
    private JTextField textField;
    private JButton button;

    public CadastroEvento() {
        super();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.label = new JLabel("Nome do Evento");
        this.textField = new JTextField(20);
        this.button = new JButton("Cadsatrar");

        this.add(label);
        this.add(textField);
        this.add(button);
    }
}
