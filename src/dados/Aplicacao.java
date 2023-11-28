package dados;

import utils.Coordinate;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Aplicacao {
    private ArrayList<Equipamento> equipamentos;
    private ArrayList<Equipe> equipes;
    private ArrayList<Evento> eventos;
    private ArrayList<Atendimento> atendimentos;
    private Queue<Atendimento> atendimentosPendentes;

    public Aplicacao() {
        this.equipamentos = new ArrayList<>();
        this.equipes = new ArrayList<>();
        this.eventos = new ArrayList<>();
        this.atendimentos = new ArrayList<>();
        this.atendimentosPendentes = new ArrayDeque<>();
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

    public boolean hasAtendimentosPendentes() {
        return !this.atendimentosPendentes.isEmpty();
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

        if (a.getStatus() == EstadoAtendimento.PENDENTE) {
            this.atendimentosPendentes.add(a);
        }

        return this.atendimentos.add(a);
    }

    public ArrayList<Atendimento> getAtendimentos() {
        return new ArrayList<>(atendimentos);
    }

    public int getAtendimentosPendentesSize() {
        return this.atendimentosPendentes.size();
    }

    public Atendimento peekAtendimento() {
        return this.atendimentosPendentes.peek();
    }

    /**
     * Aloca o primeiro atendimento para uma equipe, com base na distância geografica entre eles.
     *
     * @return uma string contendo o motivo do atendimento não ter sido alocado.
     * os motivos podem ser:
     * - não há atendimentos pendentes
     * - não há equipes disponíveis próximas ao evento
     * - há equipes próximas ao evento, mas todas estão ocupadas
     *
     * caso o atendimento seja alocado, retorna null
     */
    public String alocarAtendimento() {
        final double DISTANCIA_MAXIMA = 5_000; // em km

        if (this.atendimentosPendentes.isEmpty()) {
            return "não há atendimentos pendentes";
        }

        Atendimento atendimento = this.atendimentosPendentes.remove();

        Coordinate coordenadasEvento = atendimento.getEvento().getCoordinates();

        ArrayList<Equipe> equipesProximas = this.equipes.stream().filter((e) -> {
            Coordinate coordenadasEquipe = e.getCoordinates();
            double distancia = coordenadasEquipe.getDistanceTo(coordenadasEvento);

            return distancia <= DISTANCIA_MAXIMA;
        }).collect(Collectors.toCollection(ArrayList::new));

        if (equipesProximas.isEmpty()) {
            atendimento.setStatus(EstadoAtendimento.CANCELADO);
            return "não há equipes próximas ao evento";
        }

        Equipe equipe = equipesProximas.stream().filter(e -> e.getAtendimento() == null).findFirst().orElse(null);

        if (equipe == null) {
            this.atendimentosPendentes.add(atendimento);
            return "todas as equipes próximas ao evento estão ocupadas";
        }

        equipe.setAtendimento(atendimento);
        atendimento.setEquipe(equipe);
        return null;
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


    public boolean vincularEquipamentoEquipe(Equipamento equipamento, Equipe equipe) {
        if (equipamento.getEquipe() != null) {
            return false;
        }

        equipamento.setEquipe(equipe);
        return equipe.addEquipamento(equipamento);
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
