import javax.swing.*;
import java.awt.*;

public class Programa extends JFrame {
    JPanel panel;
    JButton button;
    JLabel label;
    JTextArea textArea;
    JTextField textField;

    public Programa(){
        super("Programa");
        this.add(setMenu());
        this.setVisible(true);
        this.setSize(500, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }



    public JPanel setMenu() {
        panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1));
        button = new JButton("Cadastrar Evento");
        button.addActionListener(e -> {
            this.setContentPane(this.setCadastrarEvento());
            this.setVisible(true);
        });
        panel.add(button);
        button = new JButton("Cadastrar Equipe");
        button.addActionListener(e -> {
            this.setContentPane(this.setCadastrarEquipe());
            this.setVisible(true);
        });
        panel.add(button);
        button = new JButton("Cadastrar Equipamento");
        button.addActionListener(e -> {
            this.setContentPane(this.setCadastrarEquipamento());
            this.setVisible(true);
        });
        panel.add(button);
        button = new JButton("Cadastrar Atendimento");
        button.addActionListener(e -> {
            this.setContentPane(this.setCadastrarAtendimento());
            this.setVisible(true);
        });
        panel.add(button);
        button = new JButton("Mostrar Relatório");
        button.addActionListener(e -> {
            this.setContentPane(this.setMostrarRelatorio());
            this.setVisible(true);
        });
        panel.add(button);
        button = new JButton("Sair");
        button.addActionListener(e -> {
            System.exit(0);
        });
        panel.add(button);
        return panel;
    }

    public JPanel setCadastrarEvento() {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        label = new JLabel("Nome do Evento");
        panel.add(label);
        textField = new JTextField(20);
        panel.add(textField);

        return panel;
    }
    private JPanel setMostrarRelatorio() {
        panel = new JPanel();
        return panel;
    }

    private JPanel setCadastrarAtendimento() {
        panel = new JPanel();
        return panel;
    }

    private JPanel setCadastrarEquipamento() {
        panel = new JPanel();
        return panel;
    }

    private JPanel setCadastrarEquipe() {
        panel = new JPanel();
        return panel;
    }
}
