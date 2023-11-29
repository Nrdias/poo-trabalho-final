package telas;

import dados.Aplicacao;

import javax.swing.*;
import java.awt.*;

public class Programa extends JFrame {
    JPanel mainPanel;
    Aplicacao app;

    public Programa(Aplicacao app) {
        super("Programa");

        this.app = app;

        initMainPanel();

        this.add(mainPanel);

        this.setVisible(true);
        this.setSize(500, 500);


        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
//        this.pack();
    }


    /**
     * Inicializa o painel principal da aplicação
     */
    private void initMainPanel() {
        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new GridLayout(10, 1));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel title = new JLabel("ACME Resgate :)");
        title.setFont(title.getFont().deriveFont(24.0F));
        topPanel.add(title);

        JButton carregarDados = new JButton("Carregar Dados");
        JButton salvarDados = new JButton("Salvar Dados");

        carregarDados.addActionListener(e -> {
            this.handleCarregarDados();
        });
        salvarDados.addActionListener(e -> {
            this.handleSalvarDados();
        });

        topPanel.add(carregarDados);
        topPanel.add(salvarDados);

        this.mainPanel.add(topPanel);

        JButton cadastrarEvento = new JButton("Cadastrar Evento");
        cadastrarEvento.addActionListener(e -> {
            this.changePanel(new CadastroEvento(app));
        });
        this.mainPanel.add(cadastrarEvento);

        JButton cadastrarEquipe = new JButton("Cadastrar Equipe");
        cadastrarEquipe.addActionListener(e -> {
            this.changePanel(new CadastroEquipe(app));
        });
        this.mainPanel.add(cadastrarEquipe);

        JButton cadastrarEquipamento = new JButton("Cadastrar Equipamento");
        cadastrarEquipamento.addActionListener(e -> {
            this.changePanel(new CadastroEquipamento(app));
        });
        this.mainPanel.add(cadastrarEquipamento);

        JButton cadastrarAtendimento = new JButton("Cadastrar Atendimento");
        cadastrarAtendimento.addActionListener(e -> {
            this.changePanel(new CadastroAtendimento(app));
        });
        this.mainPanel.add(cadastrarAtendimento);

        JButton mostrarRelatorio = new JButton("Mostrar Relatório");
        mostrarRelatorio.addActionListener(e -> {
            this.changePanel(new Relatorio(app));
        });
        this.mainPanel.add(mostrarRelatorio);

        JButton mostrarAtendimentos = new JButton("Mostrar Atendimentos");
        mostrarAtendimentos.addActionListener(e -> {
            this.changePanel(new MostrarAtendimentos(app));
        });
        this.mainPanel.add(mostrarAtendimentos);

        JButton alocarAtendimentos = new JButton("Alocar Atendimentos");
        alocarAtendimentos.addActionListener(e -> {
            this.changePanel(new AlocarAtendimentos(app));
        });
        this.mainPanel.add(alocarAtendimentos);

        JButton vincularEquipamentoEquipe = new JButton("Vincular um equipamento a uma equipe");
        vincularEquipamentoEquipe.addActionListener(e -> {
            this.changePanel(new VincularEquipamentoEquipe(app));
        });
        this.mainPanel.add(vincularEquipamentoEquipe);


        JButton sair = new JButton("Sair");
        sair.addActionListener(e -> {
            // TODO: mensagem de confirmação?
            int confirmed = JOptionPane.showConfirmDialog(null, "Você realmente quer sair?", ":(", JOptionPane.YES_NO_OPTION);
            if (confirmed == JOptionPane.YES_OPTION) {
                System.exit(0);
            }

        });
        this.mainPanel.add(sair);
    }

    /**
     * Lida com o evento de carregar os dados
     */
    private void handleCarregarDados() {
        String[] options = {"Eventos", "Equipes", "Equipamentos", "Atendimentos"};
        int selected = JOptionPane.showOptionDialog(this, "Selecione qual entidade deseja ler", "Ler Dados", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, "Equipes");

        if (selected == JOptionPane.CLOSED_OPTION) {
            return;
        }

        JFileChooser fileChooser = new JFileChooser("src/files");
        int result = fileChooser.showOpenDialog(null);

        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }

        String path = fileChooser.getSelectedFile().getAbsolutePath();

        switch (selected) {
            case 0:
                app.lerArquivoEventos(path);
                break;
            case 1:
                app.lerArquivoEquipes(path);
                break;
            case 2:
                app.lerArquivosEquipamentos(path);
                break;
            case 3:
                app.lerArquivoAtendimentos(path);
                break;
        }
    }

    /**
     * Lida com o evento de salvar os dados
     */
    private void handleSalvarDados() {
        String[] options = {"Eventos", "Equipes", "Equipamentos", "Atendimentos", "Todos"};
        int selected = JOptionPane.showOptionDialog(this, "Selecione qual entidade deseja salvar", "Salvar Dados", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, "Equipes");

        if (selected == JOptionPane.CLOSED_OPTION) {
            return;
        }

        JFileChooser fileChooser = new JFileChooser("src/files");
        int result = fileChooser.showSaveDialog(null);

        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }

        String arquivo = fileChooser.getSelectedFile().getName();
        System.out.println(arquivo);

        switch (selected) {
            case 0 -> app.gravarArquivoEventos(arquivo);
            case 1 -> app.gravarArquivoEquipes(arquivo);
            case 2 -> app.gravarArquivoEquipamentos(arquivo);
            case 3 -> app.gravarArquivoAtendimentos(arquivo);
            default -> {
                app.gravarArquivoEventos("eventos");
                app.gravarArquivoEquipes("equipes");
                app.gravarArquivoEquipamentos("equipamentos");
                app.gravarArquivoAtendimentos("atendimentos");
            }
        }
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
