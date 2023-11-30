package telas;

import dados.Aplicacao;
import dados.Atendimento;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MostrarAtendimentos extends JPanel {
    Aplicacao app;
    
    public MostrarAtendimentos(Aplicacao app) {
        super();
        this.app = app;
        
        this.setLayout(new BorderLayout());
        
        this.add(new JLabel("Atendimentos"), BorderLayout.NORTH);
        

        JTextArea atendimentos = new JTextArea(5, 30);
        atendimentos.setEditable(false);

        atendimentos.setText(this.formatAtendimentos());

        this.add(new JScrollPane(atendimentos),  BorderLayout.CENTER);
    }

    // does not look good at all but gets the job done
    private String formatAtendimentos() {
        ArrayList<Atendimento> atendimentos = this.app.getAtendimentos();

        StringBuilder sb = new StringBuilder();

        for (Atendimento atendimento : atendimentos) {
            sb.append(atendimento.toString());
            sb.append("Custo: R$").append(atendimento.calculaCusto()).append("\n");
            sb.append(atendimento.getEvento().toString());
            if (atendimento.getEquipe() != null) {
                sb.append(atendimento.getEquipe().toString());
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
