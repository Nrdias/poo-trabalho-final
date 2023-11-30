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

    public void setStatus(EstadoAtendimento status) {
        this.status = status;
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
        if (this.equipe == null) return 0;

        double distancia = this.evento.getCoordinates().getDistanceTo(this.equipe.getCoordinates());

        double custoEquipe = this.duracao * this.equipe.getQuantidade() * 250;
        double custoEquipamentos = this.equipe.getCustoDiarioEquipamentos() * this.duracao;
        double custoDeslocamento = distancia / 1000 * (100 * this.equipe.getQuantidade() + 0.1 * custoEquipamentos);

        System.out.println("Custo equipe: " + custoEquipe);
        System.out.println("Custo equipamentos: " + custoEquipamentos);
        System.out.println("Custo deslocamento: " + custoDeslocamento);

        return custoEquipe + custoEquipamentos + custoDeslocamento;
    }


    @Override
    public String toString() {
        String s = "Atendimento\n" + "Código: " + cod + "\nData de inicio:" + dataInicio + "\nDuração:" + duracao + " dias\nStatus:" + status + "\n";

        if (this.evento instanceof Terremoto) {
            s += "Evento: Terremoto, código: " + evento.getCodigo() + "\n";
        } else if (this.evento instanceof Ciclone) {
            s += "Evento: Ciclone, código: " + evento.getCodigo() + "\n";
        } else {
            s += "Evento: Seca, código: " + evento.getCodigo() + "\n";
        }

        if (this.equipe != null) {
            s += "Equipe: " + equipe.getCodinome() + "\n";
        } else {
            s += "Equipe: Não atribuída\n";
        }

        return s;
    }
}
