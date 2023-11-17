package telas;

import javax.swing.*;
import java.awt.*;

public class Programa extends JFrame {
    JPanel mainPanel;

    public Programa() {
        super("Programa");

        initMainPanel();

        this.add(mainPanel);

        this.setVisible(true);
        this.setSize(500, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
    }


    /**
     * Inicializa o painel principal da aplicação
     */
    private void initMainPanel() {
        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new GridLayout(7, 1));

        JLabel title = new JLabel("ACME Resgate :)");
        title.setFont(title.getFont().deriveFont(24.0F));
        this.mainPanel.add(title);

        JButton cadastrarEvento = new JButton("Cadastrar Evento");
        cadastrarEvento.addActionListener(e -> {
            // apenas cria o painel e muda para ele
            this.changePanel(new CadastroEvento());
        });

        this.mainPanel.add(cadastrarEvento);

        JButton cadastrarEquipe = new JButton("Cadastrar Equipe");
        cadastrarEquipe.addActionListener(e -> {
            this.changePanel(new CadastroEquipe());
        });
        this.mainPanel.add(cadastrarEquipe);

        JButton cadastrarEquipamento = new JButton("Cadastrar Equipamento");
        cadastrarEquipamento.addActionListener(e -> {
            this.changePanel(new CadastroEquipamento());
        });
        this.mainPanel.add(cadastrarEquipamento);

        JButton cadastrarAtendimento = new JButton("Cadastrar Atendimento");
        cadastrarAtendimento.addActionListener(e -> {
            this.changePanel(new CadastroAtendimento());
        });
        this.mainPanel.add(cadastrarAtendimento);

        JButton mostrarRelatorio = new JButton("Mostrar Relatório");
        mostrarRelatorio.addActionListener(e -> {
            this.changePanel(new Relatorio());
        });
        this.mainPanel.add(mostrarRelatorio);

        JButton sair = new JButton("Sair");
        sair.addActionListener(e -> {
            // TODO: mensagem de confirmação?
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
}
