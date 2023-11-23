package dados;

import java.util.ArrayList;
import java.util.Comparator;

public class Aplicacao {
    private ArrayList<Equipamento> equipamentos;
    private ArrayList<Equipe> equipes;

    public Aplicacao() {
        this.equipamentos = new ArrayList<>();
        this.equipes = new ArrayList<>();
    }

    public boolean addEquipamento(Equipamento e) {
        boolean idUsed = this.equipamentos.stream().anyMatch((equipamento) -> (equipamento.getId() == e.getId()));

        if (idUsed) {
            return false;
        }

        this.equipamentos.add(e);
        this.equipamentos.sort(Comparator.comparing(Equipamento::getId));
        return true;
    }

    public boolean addEquipe(Equipe e) {
        boolean codinomeUsed = this.equipes.stream().anyMatch((equipe) -> (equipe.getCodinome().equals(e.getCodinome())));

        if (codinomeUsed) {
            return false;
        }

        this.equipes.add(e);
        this.equipes.sort((o1, o2) -> o1.getCodinome().compareTo(o2.getCodinome()));
        return true;
    }

    public String equipesToString(){
        StringBuilder str = new StringBuilder("Equipes: \n");

        this.equipes.forEach((equipe) -> {
            str.append(equipe.toString()).append("\n");
        });

        return str.toString();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("Equipamentos: \n");

        this.equipamentos.forEach((equipamento) -> {
            str.append(equipamento.toString()).append("\n");
        });

        return str.toString();
    }
}
