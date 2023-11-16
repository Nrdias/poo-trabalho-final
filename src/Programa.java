import javax.swing.*;
import java.awt.*;

public class Programa extends JFrame {
    JPanel menu, cadastrarEvento, cadastrarEquipe, cadastrarEquipamento, cadastrarAtendimento, mostrarRelatório;
    JButton button;
    GridLayout gridLayout = new GridLayout(2, 1);

    public Programa(){
        super("Programa");
        menu = new JPanel();
        menu.setLayout(gridLayout);
        button = new JButton("Cadastrar Evento");
        menu.add(button);
        button = new JButton("Cadastrar Equipe");
        menu.add(button);
        button = new JButton("Cadastrar Equipamento");
        menu.add(button);
        button = new JButton("Cadastrar Atendimento");
        menu.add(button);
        button = new JButton("Mostrar Relatório");
        menu.add(button);
        add(menu);
        this.setVisible(true);
        this.setSize(500, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
