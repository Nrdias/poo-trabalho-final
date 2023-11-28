package dados;

public class Equipamento {
    private int id;
    private String nome;
    private double custoDia;
    private Equipe equipe;

    public Equipamento(int id, String nome, double custoDia) {
        this.id = id;
        this.nome = nome;
        this.custoDia = custoDia;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getCustoDia() {
        return custoDia;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }

    @Override
    public String toString() {
        String str = "Id: " + id + "\nNome: " + nome + "\nCusto diário:" + custoDia + "\n";

        if (this.equipe != null) {
            str += "Equipe: " + this.equipe.getCodinome() + "\n";
        }

        return str;
    }
}
