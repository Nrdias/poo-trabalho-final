package telas;

import dados.Aplicacao;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Programa extends JFrame {
    JPanel mainPanel;
    Aplicacao app;

    public Programa(Aplicacao app) {
        super("Programa");

        this.app = app;

        initMainPanel();

        this.add(mainPanel);

        this.setVisible(true);
        this.setSize(650, 700);


        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
//        this.pack();
    }


    /**
     * Inicializa o painel principal da aplicação
     */
    private void initMainPanel() {
        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new GridLayout(11, 1));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel title = new JLabel("ACME Resgate :)");
        title.setFont(title.getFont().deriveFont(24.0F));
        topPanel.add(title);

        JButton carregarDados = new JButton("Carregar Dados");
        JButton carregarDadosIniciais = new JButton("Carregar Dados Iniciais");
        JButton salvarDados = new JButton("Salvar Dados");

        carregarDados.addActionListener(e -> {
            this.handleCarregarDados();
        });
        carregarDadosIniciais.addActionListener(e -> {
            this.handleCarregarDadosInicias();
        });
        salvarDados.addActionListener(e -> {
            this.handleSalvarDados();
        });

        topPanel.add(carregarDados);
        topPanel.add(carregarDadosIniciais);
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

        JButton alterarAtendimentos = new JButton("Alterar Atendimento");
        alterarAtendimentos.addActionListener(e -> {
            this.changePanel(new AlterarAtendimento(app));
        });
        this.mainPanel.add(alterarAtendimentos);

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

        boolean carregou = switch (selected) {
            case 0 -> app.lerArquivoEventos(path);
            case 1 -> app.lerArquivoEquipes(path);
            case 2 -> app.lerArquivosEquipamentos(path);
            case 3 -> app.lerArquivoAtendimentos(path);
            default -> false;
        };

        if (carregou) {
            JOptionPane.showMessageDialog(null, "Dados carregados com sucesso!");
        } else {
            JOptionPane.showMessageDialog(null, "Não foi possível carregar os dados");
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

        String arquivo = fileChooser.getSelectedFile().getAbsolutePath();

        if (selected == 4) {
            // remove the file name
            arquivo = arquivo.substring(0, arquivo.lastIndexOf(File.separator) + 1);
        }

        System.out.println(arquivo);

        boolean gravou = switch (selected) {
            case 0 -> app.gravarArquivoEventos(arquivo);
            case 1 -> app.gravarArquivoEquipes(arquivo);
            case 2 -> app.gravarArquivoEquipamentos(arquivo);
            case 3 -> app.gravarArquivoAtendimentos(arquivo);
            case 4 -> app.gravarArquivoEventos(arquivo + "eventos") &&
                    app.gravarArquivoEquipes(arquivo + "equipes") &&
                    app.gravarArquivoEquipamentos(arquivo + "equipamentos") &&
                    app.gravarArquivoAtendimentos(arquivo + "atendimentos");
            default -> false;
        };

        if (gravou) {
            JOptionPane.showMessageDialog(null, "Dados salvos com sucesso!");
        } else {
            JOptionPane.showMessageDialog(null, "Não foi possível salvar os dados");
        }
    }


    public void handleCarregarDadosInicias() {
        int opcao = JOptionPane.showConfirmDialog(null, "Deseja carregar os dados iniciais?", "Dados iniciais", JOptionPane.YES_NO_OPTION);
        if (opcao != JOptionPane.YES_OPTION) return;

        boolean carregou = this.app.carregarDadosIniciais();
        if (carregou) {
            JOptionPane.showMessageDialog(null, "Dados carregados com sucesso!");
        } else {
            JOptionPane.showMessageDialog(null, "Não foi possível carregar os dados iniciais");
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
