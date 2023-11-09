public class Atendimento {

	private int cod;

	private String dataInicio;

	private int duracao;

	private String status;

	public double calculaCusto() {
		return 0;
	}

	public Atendimento(int cod, String dataInicio, int duracao, String status) {
		this.cod = cod;
		this.dataInicio = dataInicio;
		this.duracao = duracao;
		this.status = status;
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

	public String getStatus() {
		return status;
	}

}
