package dados;

public class Atendimento {
    private int cod;
    private String dataInicio;
    private int duracao;
    private EstadoAtendimento status;
    private Evento evento;
    private Equipe equipe;

    public Atendimento(int cod, String dataInicio, int duracao, Evento evento) {
        this.cod = cod;
        this.dataInicio = dataInicio;
        this.duracao = duracao;
        this.status = EstadoAtendimento.PENDENTE;
        this.evento = evento;
    }

    public Atendimento(int cod, String dataInicio, int duracao, Evento evento, Equipe equipe) {
        this.cod = cod;
        this.dataInicio = dataInicio;
        this.duracao = duracao;
        this.status = EstadoAtendimento.EXECUTANDO;
        this.evento = evento;
        this.equipe = equipe;
    }

    public int getCod() {
        return cod;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public int getDuracao() {
        return duracao;
    }

    public EstadoAtendimento getStatus() {
        return status;
    }

    public Evento getEvento() {
        return evento;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
        this.status = EstadoAtendimento.EXECUTANDO;
    }

    public double calculaCusto() {
        return 0;
    }
}
