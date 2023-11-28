package dados;

import java.util.*;

public class Aplicacao {
    private ArrayList<Equipamento> equipamentos;
    private ArrayList<Equipe> equipes;
    private ArrayList<Evento> eventos;
    private Queue<Atendimento> atendimentos;

    public Aplicacao() {
        this.equipamentos = new ArrayList<>();
        this.equipes = new ArrayList<>();
        this.eventos = new ArrayList<>();
        this.atendimentos = new ArrayDeque<>();
    }

    public boolean hasEquipamentos() {
        return !this.equipamentos.isEmpty();
    }
    public boolean hasEquipes() {
        return !this.equipes.isEmpty();
    }
    public boolean hasEventos() {
        return !this.eventos.isEmpty();
    }
    public boolean hasAtendimentos() {
        return !this.atendimentos.isEmpty();
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

    public ArrayList<Equipamento> getEquipamentos() {
        return this.equipamentos;
    }

    public Equipamento getEquipamentoById(int id) {
        return this.equipamentos.stream().filter((equipamento) -> (equipamento.getId() == id)).findFirst().orElse(null);
    }

    public boolean addEquipe(Equipe e) {
        boolean codinomeUsed = this.equipes.stream().anyMatch((equipe) -> (equipe.getCodinome().equals(e.getCodinome())));

        if (codinomeUsed) {
            return false;
        }

        this.equipes.add(e);
        this.equipes.sort(Comparator.comparing(Equipe::getCodinome));
        return true;
    }

    public ArrayList<Equipe> getEquipes() {
        return this.equipes;
    }

    public Equipe getEquipeByCodinome(String codinome) {
        return this.equipes.stream().filter((equipe) -> (equipe.getCodinome().equals(codinome))).findFirst().orElse(null);
    }

    public boolean addEvento(Evento e) {
        boolean idUsed = this.eventos.stream().anyMatch((evento) -> (evento.getCodigo().equals(e.getCodigo())));

        if (idUsed) {
            return false;
        }

        this.eventos.add(e);
        this.eventos.sort(Comparator.comparing(Evento::getCodigo));
        return true;
    }

    public ArrayList<Evento> getEventos() {
        return this.eventos;
    }

    public boolean addAtendimento(Atendimento a) {
        boolean idUsed = this.atendimentos.stream().anyMatch((atendimento) -> (atendimento.getCod() == a.getCod()));
        boolean eventoAtendido = this.atendimentos.stream().anyMatch((atendimento) -> (atendimento.getEvento().getCodigo().equals(a.getEvento().getCodigo())));

        if (idUsed || eventoAtendido) {
            return false;
        }

        return this.atendimentos.add(a);
    }

    public String equipamentosToString() {
        StringBuilder str = new StringBuilder("Equipamentos: \n");

        this.equipamentos.forEach((equipamento) -> {
            str.append(equipamento.toString()).append("\n");
        });

        return str.toString();
    }

    public String equipesToString() {
        StringBuilder str = new StringBuilder("Equipes: \n");

        this.equipes.forEach((equipe) -> {
            str.append(equipe.toString()).append("\n");
        });

        return str.toString();
    }

    public String eventosToString() {
        StringBuilder str = new StringBuilder("Eventos: \n");

        this.eventos.forEach((evento) -> {
            str.append(evento.toString()).append("\n");
        });

        return str.toString();
    }

    public String atendimentosToString() {
        StringBuilder str = new StringBuilder("Atendimentos: \n");

        this.atendimentos.forEach((atendimento) -> {
            str.append(atendimento.toString()).append("\n");
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
