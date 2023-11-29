package dados;

import utils.Coordinate;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
     * <p>
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

    public void lerArquivoEquipes(String arquivo) {
        Path path = Paths.get(arquivo);
        try (BufferedReader br = Files.newBufferedReader(path, Charset.defaultCharset())) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(";");
                this.addEquipe(new Equipe(data[0], Integer.parseInt(data[1]), Double.parseDouble(data[2]), Double.parseDouble(data[3])));
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo");
        } catch (NumberFormatException e) {
            System.out.println("Erro ao formatar String para Número");
        }
    }

    public void lerArquivoEventos(String arquivo) {
        Path path = Paths.get(arquivo);
        try (BufferedReader br = Files.newBufferedReader(path, Charset.defaultCharset())) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
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
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo");
        } catch (NumberFormatException e) {
            System.out.println("Erro ao formatar String para Número");
        }
    }

    public void lerArquivoAtendimentos(String arquivo) {
        Path path = Paths.get(arquivo);
        try (BufferedReader br = Files.newBufferedReader(path, Charset.defaultCharset())) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(";");
                Evento evento = this.getEventos().stream().filter((e) -> (e.getCodigo().equals(data[4]))).findFirst().orElse(null);
                if (evento == null) System.out.println("Evento não encontrado");
                else
                    this.addAtendimento(new Atendimento(Integer.parseInt(data[0]), evento.getData(), Integer.parseInt(data[2]), evento));
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo");
        } catch (NumberFormatException e) {
            System.out.println("Erro ao formatar String para Número");
        }
    }

    public void lerArquivosEquipamentos(String arquivo) {
        Path path = Paths.get(arquivo);
        try (BufferedReader br = Files.newBufferedReader(path, Charset.defaultCharset())) {
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
                        this.addEquipamento(new CaminhaoTanque(Integer.parseInt(data[0]), data[1], Double.parseDouble(data[2]), Integer.parseInt(data[5])));
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
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo");
        } catch (NumberFormatException e) {
            System.out.println("Erro ao formatar String para Número");
        }
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
