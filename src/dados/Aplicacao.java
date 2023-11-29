package dados;

import utils.Coordinate;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

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
        dadosIniciais();
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

    public void lerArquivoEquipes(String arquivo){
        BufferedReader br;
        Path path = Paths.get(arquivo);
        try{
            br = Files.newBufferedReader(path, Charset.defaultCharset());
            br.readLine();
            String line;
            while ((line = br.readLine()) != null){
                String[] data = line.split(";");
                this.addEquipe(new Equipe(data[0], Integer.parseInt(data[1]), Double.parseDouble(data[2]), Double.parseDouble(data[3]))) ;
            }
        }catch (Exception e){
            if(e instanceof NumberFormatException) System.out.println("Erro ao formatar String para Número");
            else System.out.println("Erro ao ler o arquivo");
        }
    }

    public void lerArquivoEventos(String arquivo){
        BufferedReader br;
        Path path = Paths.get(arquivo);
        try{
            br = Files.newBufferedReader(path, Charset.defaultCharset());
            br.readLine();
            String line;
            while ((line = br.readLine()) != null){
                String[] data = line.split(";");
                switch (Integer.parseInt(data[4])) {
                    case 1 ->
                            this.addEvento(new Ciclone(data[0], data[1], Double.parseDouble(data[2]), Double.parseDouble(data[3]), Double.parseDouble(data[5]), Double.parseDouble(data[6])));
                    case 2 ->
                            this.addEvento(new Terremoto(data[0], data[1], Double.parseDouble(data[2]), Double.parseDouble(data[3]), Double.parseDouble(data[5])));
                    case 3 ->
                            this.addEvento(new Seca(data[0], data[1], Double.parseDouble(data[2]), Double.parseDouble(data[3]), Integer.parseInt(data[5])));
                }
            }
        }catch (Exception e){
            if(e instanceof NumberFormatException) System.out.println("Erro ao formatar String para Número");
            else System.out.println("Erro ao ler o arquivo");
        }
    }

    public void lerArquivoAtendimentos(String arquivo){
        BufferedReader br;
        Path path = Paths.get(arquivo);
        try{
            br = Files.newBufferedReader(path, Charset.defaultCharset());
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(";");
                Evento evento = this.getEventos().stream().filter((e) -> (e.getCodigo().equals(data[4]))).findFirst().orElse(null);
                if(evento == null) System.out.println("Evento não encontrado");
                else this.addAtendimento(new Atendimento(Integer.parseInt(data[0]), evento.getData(), Integer.parseInt(data[2]), evento));
            }
        }catch (Exception e){
            if(e instanceof NumberFormatException) System.out.println("Erro ao formatar String para Número");
            else System.out.println("Erro ao ler o arquivo");
        }
    }

    public void lerArquivosEquipamentos(String arquivo){
        BufferedReader br;
        Path path = Paths.get(arquivo);
        try{
            br = Files.newBufferedReader(path, Charset.defaultCharset());
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(";");
                Equipe e;
                switch (Integer.parseInt(data[4])) {
                    case 1 -> {
                        this.addEquipamento(new Barco(Integer.parseInt(data[0]), data[1], Double.parseDouble(data[2]), Integer.parseInt(data[5])));
                        e = this.getEquipes().stream().filter((equipe) -> (equipe.getCodinome().equals(data[3]))).findFirst().orElse(null);
                        if (e == null) System.out.println("Equipe não encontrada");
                        else this.vincularEquipamentoEquipe(this.getEquipamentoById(Integer.parseInt(data[0])), e);
                    }
                    case 2 -> {
                        this.addEquipamento(new CaminhaoTanque(Integer.parseInt(data[0]), data[1], Double.parseDouble(data[2]), Double.parseDouble(data[5])));
                        e = this.getEquipes().stream().filter((equipe) -> (equipe.getCodinome().equals(data[3]))).findFirst().orElse(null);
                        if (e == null) System.out.println("Equipe não encontrada");
                        else this.vincularEquipamentoEquipe(this.getEquipamentoById(Integer.parseInt(data[0])), e);
                    }
                    case 3 -> {
                        this.addEquipamento(new Escavadeira(Integer.parseInt(data[0]), data[1], Double.parseDouble(data[2]), data[5], Double.parseDouble(data[6])));
                        e = this.getEquipes().stream().filter((equipe) -> (equipe.getCodinome().equals(data[3]))).findFirst().orElse(null);
                        if (e == null) System.out.println("Equipe não encontrada");
                        else this.vincularEquipamentoEquipe(this.getEquipamentoById(Integer.parseInt(data[0])), e);
                    }
                }
            }
        }catch (Exception e){
            if(e instanceof NumberFormatException) System.out.println("Erro ao formatar String para Número");
            else System.out.println("Erro ao ler o arquivo");
        }
    }

    public void gravarArquivoEquipes(String arquivo){
        String path = "src/files/" + arquivo + ".csv";
        try{
            FileOutputStream fos = new FileOutputStream(path);
            PrintStream writer = new PrintStream(fos);
            writer.println("Codinome;Quantidade de Membros;Latitude;Longitude");
            this.getEquipes().forEach((equipe) -> {
                writer.println(equipe.getCodinome() + ";" + equipe.getQuantidade() + ";" + equipe.getLatitude() + ";" + equipe.getLongitude());
            });
            writer.close();
            JOptionPane.showMessageDialog(null, "Arquivo gravado com sucesso");
        }catch (Exception e){
            System.out.println("Erro ao gravar o arquivo");
        }
    }

    public void gravarArquivoEventos(String arquivo){
        String path = "src/files/" + arquivo + ".csv";
        try{
            FileOutputStream fos = new FileOutputStream(path);
            PrintStream writer = new PrintStream(fos);
            writer.println("codigo;data;latitude;longitude;tipo;velocidade_magnitude_estiagem;precipitacao");
            this.getEventos().forEach((evento) -> {
                if(evento instanceof Ciclone){
                    writer.println(evento.getCodigo() + ";" + evento.getData() + ";" + evento.getLatitude() + ";" + evento.getLongitude() + ";" + 1 + ";" + ((Ciclone) evento).getVelocidade() + ";" + ((Ciclone) evento).getPrecipitacao());
                }else if(evento instanceof Terremoto){
                    writer.println(evento.getCodigo() + ";" + evento.getData() + ";" + evento.getLatitude() + ";" + evento.getLongitude() + ";" + 2 + ";" + ((Terremoto) evento).getMagnitude());
                }else if(evento instanceof Seca){
                    writer.println(evento.getCodigo() + ";" + evento.getData() + ";" + evento.getLatitude() + ";" + evento.getLongitude() + ";" + 3 + ";" + ((Seca) evento).getEstiagem());
                }
            });
            writer.close();
            JOptionPane.showMessageDialog(null, "Arquivo gravado com sucesso");
        }catch (Exception e){
            System.out.println("Erro ao gravar o arquivo");
        }
    }

    public void gravarArquivoAtendimentos(String arquivo){
        String path = "src/files/" + arquivo + ".csv";
        try{
            FileOutputStream fos = new FileOutputStream(path);
            PrintStream writer = new PrintStream(fos);
            writer.println("cod;dataInicio;duracao;status;codigo");
            this.getAtendimentos().forEach((atendimento) -> {
                writer.println(atendimento.getCod() + ";" + atendimento.getDataInicio() + ";" + atendimento.getDuracao() + ";" + atendimento.getStatus() + ";" + atendimento.getEvento().getCodigo());
            });
            writer.close();
            JOptionPane.showMessageDialog(null, "Arquivo gravado com sucesso");
        }catch (Exception e){
            System.out.println("Erro ao gravar o arquivo");
        }
    }

    public void gravarArquivoEquipamentos(String arquivo){
        String path = "src/files/" + arquivo + ".csv";
        try{
            FileOutputStream fos = new FileOutputStream(path);
            PrintStream writer = new PrintStream(fos);
            writer.println("identificador;nome;custodiario;codinome;tipo;capacidade_combustivel;carga");
            this.getEquipamentos().forEach((equipamento) -> {
                if(equipamento instanceof Barco){
                    writer.println(equipamento.getId() + ";" + equipamento.getNome() + ";" + equipamento.getCustoDia() + ";" + equipamento.getEquipe().getCodinome() + ";" + 1 + ";" + ((Barco) equipamento).getCapacidade());
                }else if(equipamento instanceof CaminhaoTanque){
                    writer.println(equipamento.getId() + ";" + equipamento.getNome() + ";" + equipamento.getCustoDia() + ";" + equipamento.getEquipe().getCodinome() + ";" + 2 + ";" + ((CaminhaoTanque) equipamento).getCapacidade());
                }else if(equipamento instanceof Escavadeira){
                    writer.println(equipamento.getId() + ";" + equipamento.getNome() + ";" + equipamento.getCustoDia() + ";" + equipamento.getEquipe().getCodinome() + ";" + 3 + ";" + ((Escavadeira) equipamento).getCombustivel() + ";" + ((Escavadeira) equipamento).getCarga());
                }
            });
            writer.close();
            JOptionPane.showMessageDialog(null, "Arquivo gravado com sucesso");
        }catch (Exception e){
            System.out.println("Erro ao gravar o arquivo");
        }
    }

    public void dadosIniciais() {
        JOptionPane dialog = new JOptionPane();
        int opcao = JOptionPane.showConfirmDialog(null, "Deseja carregar os dados iniciais?", "Dados iniciais", JOptionPane.YES_NO_OPTION);
        if (opcao != JOptionPane.YES_OPTION) return;

        String equipes = "./src/files/" + JOptionPane.showInputDialog("Digite o nome do arquivo de equipes") + ".csv";
        String eventos = "./src/files/" + JOptionPane.showInputDialog("Digite o nome do arquivo de eventos") + ".csv";
        String atendimentos = "./src/files/" + JOptionPane.showInputDialog("Digite o nome do arquivo de atendimentos") + ".csv";
        String equipamentos = "./src/files/" + JOptionPane.showInputDialog("Digite o nome do arquivo de equipamentos") + ".csv";
        this.lerArquivoEquipes(equipes);
        JOptionPane.showMessageDialog(null, "Equipes carregadas com sucesso");
        this.lerArquivoEventos(eventos);
        JOptionPane.showMessageDialog(null, "Eventos carregados com sucesso");
        this.lerArquivoAtendimentos(atendimentos);
        JOptionPane.showMessageDialog(null, "Atendimentos carregados com sucesso");
        this.lerArquivosEquipamentos(equipamentos);
        JOptionPane.showMessageDialog(null, "Equipamentos carregados com sucesso");

        JOptionPane.showMessageDialog(null, "Dados carregados com sucesso");
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
