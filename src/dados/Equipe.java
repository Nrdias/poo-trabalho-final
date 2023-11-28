package dados;

import utils.Coordinate;

public class Equipe {
	private String codinome;
	private int quantidade;
	private double latitude;
	private double longitude;
	private Equipamento equipamento;
	private Atendimento atendimento;

	public Equipe(String codinome, int quantidade, double latitude, double longitude) {
		this.codinome = codinome;
		this.quantidade = quantidade;
		this.latitude = latitude;
		this.longitude = longitude;
		this.equipamento = null;
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

	public Equipamento getEquipamento() {
		return equipamento;
	}

	public void setEquipamento(Equipamento equipamento) {
		this.equipamento = equipamento;
	}

	public Atendimento getAtendimento() {
		return atendimento;
	}

	public void setAtendimento(Atendimento atendimento) {
		this.atendimento = atendimento;
	}

	public Coordinate getCoordinates() {
		return new Coordinate(latitude, longitude);
	}

	public String toString() {
		if(equipamento != null) {
			return "codinome=" + codinome + ", quantidade=" + quantidade + ", latitude=" + latitude + ", longitude="
					+ longitude + ", equipamento=" + equipamento.getNome();
		}
		return "codinome=" + codinome + ", quantidade=" + quantidade + ", latitude=" + latitude + ", longitude="
				+ longitude;
	}
}
