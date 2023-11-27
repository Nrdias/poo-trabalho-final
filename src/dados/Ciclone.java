package dados;

public class Ciclone extends Evento {

    private double velocidade;

    private double precipitacao;

    public Ciclone(String codigo, String data, double latitude, double longitude, double velocidade, double precipitacao) {
        super(codigo, data, latitude, longitude);
        this.velocidade = velocidade;
        this.precipitacao = precipitacao;
    }

    public double getVelocidade() {
        return velocidade;
    }

    public double getPrecipitacao() {
        return precipitacao;
    }

    @Override
    public String toString() {
        return "Ciclone\n" + super.toString() + "Velocidade: " + velocidade + "\nPrecipitação: " + precipitacao + "\n";
    }
}
