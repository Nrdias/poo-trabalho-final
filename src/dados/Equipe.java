package dados;

import java.util.ArrayList;

public class Equipe {
    private String codinome;
    private int quantidade;
    private double latitude;
    private double longitude;

    private ArrayList<Equipamento> equipamentos;

    public Equipe(String codinome, int quantidade, double latitude, double longitude) {
        this.codinome = codinome;
        this.quantidade = quantidade;
        this.latitude = latitude;
        this.longitude = longitude;
        this.equipamentos = new ArrayList<>();
    }

    public String getCodinome() {
        return codinome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public boolean addEquipamento(Equipamento equipamento) {
        boolean idUsed = this.equipamentos.stream().anyMatch(e -> e.getId().equals(equipamento.getId()));

        if (idUsed) {
            return false;
        }

        return this.equipamentos.add(equipamento);
    }

    public String toString() {
        return "Codinome:" + codinome + "\nQuantidade:" + quantidade + "\nLatitude:" + latitude + "\nLongitude:" + longitude + "\nNúmero de equipamentos:" + equipamentos.size() + "\n";
    }
}
