package telas;

import javax.swing.*;
import java.awt.*;

public class Programa extends JFrame {
    JPanel mainPanel;

    JPanel panel;
    JButton button;

    public Programa(){
        super("Programa");

        initMainPanel();

        this.add(mainPanel);

        this.setVisible(true);
        this.setSize(500, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    /**
     * Inicializa o painel principal da aplicação
     */
    private void initMainPanel() {
        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new GridLayout(7, 1));

        JLabel title = new JLabel("ACME Resgate :)");
        this.mainPanel.add(title);

        JButton cadastrarEvento = new JButton("Cadastrar Evento");
        cadastrarEvento.addActionListener(e -> {
            // apenas cria o painel e muda para ele
            JPanel panel = new CadastroEvento();
            this.changePanel(panel);
        });

        this.mainPanel.add(cadastrarEvento);


        // TODO: criar uma classe para cada painel, alterar variável `button` para uma mais específica.

        button = new JButton("Cadastrar Equipe");
        button.addActionListener(e -> {
            this.setContentPane(this.setCadastrarEquipe());
            this.setVisible(true);
        });
        this.mainPanel.add(button);
        button = new JButton("Cadastrar Equipamento");
        button.addActionListener(e -> {
            this.setContentPane(this.setCadastrarEquipamento());
            this.setVisible(true);
        });
        this.mainPanel.add(button);
        button = new JButton("Cadastrar Atendimento");
        button.addActionListener(e -> {
            this.setContentPane(this.setCadastrarAtendimento());
            this.setVisible(true);
        });
        this.mainPanel.add(button);
        button = new JButton("Mostrar Relatório");
        button.addActionListener(e -> {
            this.setContentPane(this.setMostrarRelatorio());
            this.setVisible(true);
        });
        this.mainPanel.add(button);

        JButton sair = new JButton("Sair");
        sair.addActionListener(e -> {
            System.exit(0);
        });
        this.mainPanel.add(sair);
    }


    /**
     * Navega para outro painel, adicionando botões para voltar a tela inicial.
     *
     * @param panel painel de destino
     */
    private void changePanel(JPanel panel) {
        JPanel newPanel = new JPanel();
        BorderLayout layout = new BorderLayout(10, 10);

        newPanel.setLayout(layout);

        JButton goBack = new JButton("Voltar");
        goBack.addActionListener(e -> {
            this.setContentPane(mainPanel);
            this.setVisible(true);
        });
        newPanel.add(goBack, BorderLayout.NORTH);

        newPanel.add(panel, BorderLayout.CENTER);

        this.setContentPane(newPanel);
        this.setVisible(true);
    }

    // TODO: remover esses métodos e implementar uma classe por tela
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
