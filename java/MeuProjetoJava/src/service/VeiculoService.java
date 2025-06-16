package service;

import dao.VeiculoDAO;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import model.CPFValidator;
import model.Veiculo;

/**
 * Classe de serviço responsável pela lógica de negócio relacionada aos veículos.
 * Implementa todas as operações necessárias para gerenciar veículos no sistema.
 */
public class VeiculoService {
    /** Lista em memória dos veículos cadastrados */
    private List<Veiculo> veiculos;
    
    /** DAO responsável pela persistência dos veículos */
    private VeiculoDAO veiculoDAO;
    
    /**
     * Construtor que inicializa o serviço e carrega os dados do arquivo
     */
    public VeiculoService() {
        this.veiculoDAO = new VeiculoDAO();
        this.veiculos = new ArrayList<>(veiculoDAO.listarTodos());
    }
    
    /**
     * Cadastra um novo veículo no sistema
     * @param veiculo Veículo a ser cadastrado
     * @return true se o cadastro foi bem sucedido, false caso contrário
     */
    public boolean cadastrarVeiculo(Veiculo veiculo) {
        if (!validarPlaca(veiculo.getPlaca())) {
            return false;
        }
        
        if (!CPFValidator.isValid(veiculo.getProprietario().getCpf())) {
            return false;
        }
        
        if (buscarVeiculoPorPlaca(veiculo.getPlaca()) != null) {
            return false; // Veículo já cadastrado
        }
        
        veiculos.add(veiculo);
        veiculoDAO.salvar(veiculo);
        return true;
    }
    
    /**
     * Busca um veículo pela placa
     * @param placa Placa do veículo
     * @return Veículo encontrado ou null se não existir
     */
    public Veiculo buscarVeiculoPorPlaca(String placa) {
        return veiculos.stream()
                .filter(v -> v.getPlaca().equalsIgnoreCase(placa))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Valida se uma placa está no formato correto (antigo ou Mercosul)
     * @param placa Placa a ser validada
     * @return true se a placa for válida, false caso contrário
     */
    public boolean validarPlaca(String placa) {
        // Remove espaços e converte para maiúsculas
        placa = placa.replaceAll("\\s", "").toUpperCase();
        
        // Padrão para placas antigas (ABC1234)
        Pattern padraoAntigo = Pattern.compile("^[A-Z]{3}[0-9]{4}$");
        
        // Padrão para placas Mercosul (ABC1D23)
        Pattern padraoMercosul = Pattern.compile("^[A-Z]{3}[0-9][A-Z][0-9]{2}$");
        
        return padraoAntigo.matcher(placa).matches() || 
               padraoMercosul.matcher(placa).matches();
    }
    
    /**
     * Gera uma nova placa no formato Mercosul
     * @return Nova placa gerada
     */
    public String gerarPlacaMercosul() {
        StringBuilder placa = new StringBuilder();
        String letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String numeros = "0123456789";
        
        // Gera 3 letras aleatórias
        for (int i = 0; i < 3; i++) {
            placa.append(letras.charAt((int) (Math.random() * letras.length())));
        }
        
        // Gera 1 número
        placa.append(numeros.charAt((int) (Math.random() * numeros.length())));
        
        // Gera 1 letra
        placa.append(letras.charAt((int) (Math.random() * letras.length())));
        
        // Gera 2 números
        for (int i = 0; i < 2; i++) {
            placa.append(numeros.charAt((int) (Math.random() * numeros.length())));
        }
        
        return placa.toString();
    }
    
    /**
     * Retorna lista de todos os veículos cadastrados
     * @return Lista de veículos
     */
    public List<Veiculo> listarVeiculos() {
        return new ArrayList<>(veiculos);
    }

    /**
     * Remove um veículo do sistema
     * @param placa Placa do veículo a ser removido
     * @param transferenciaService Serviço de transferências para verificar histórico
     * @return true se o veículo foi removido, false caso contrário
     */
    public boolean removerVeiculo(String placa, TransferenciaService transferenciaService) {
        // Verifica se o veículo existe
        Veiculo veiculo = buscarVeiculoPorPlaca(placa);
        if (veiculo == null) {
            return false;
        }

        // Verifica se o veículo tem histórico de transferências
        if (transferenciaService.veiculoTemTransferencias(placa)) {
            return false;
        }

        // Remove o veículo
        boolean removido = veiculos.removeIf(v -> v.getPlaca().equalsIgnoreCase(placa));
        if (removido) {
            veiculoDAO.remover(placa);
        }
        return removido;
    }

    /**
     * Busca veículos por CPF do proprietário
     * @param cpf CPF do proprietário
     * @return Lista de veículos do proprietário
     */
    public List<Veiculo> buscarVeiculosPorProprietario(String cpf) {
        return veiculos.stream()
                .filter(v -> v.getProprietario().getCpf().equals(cpf))
                .toList();
    }

    /**
     * Exibe os detalhes de um veículo no console
     * @param veiculo Veículo a ser exibido
     */
    public void exibirDetalhesVeiculo(Veiculo veiculo) {
        if (veiculo == null) {
            System.out.println("Veículo não encontrado.");
            return;
        }

        System.out.println("\nDetalhes do Veículo:");
        System.out.println("Placa: " + veiculo.getPlaca());
        System.out.println("Marca: " + veiculo.getMarca());
        System.out.println("Modelo: " + veiculo.getModelo());
        System.out.println("Ano: " + veiculo.getAno());
        System.out.println("Cor: " + veiculo.getCor());
        System.out.println("\nProprietário:");
        System.out.println("Nome: " + veiculo.getProprietario().getNome());
        System.out.println("CPF: " + veiculo.getProprietario().getCpf());
    }

    /**
     * Gera relatório de quantidade de veículos por marca
     */
    public void gerarRelatorioVeiculosPorMarca() {
        Map<String, Long> veiculosPorMarca = veiculos.stream()
                .collect(Collectors.groupingBy(
                    Veiculo::getMarca,
                    Collectors.counting()
                ));

        if (veiculosPorMarca.isEmpty()) {
            System.out.println("\nNenhum veículo cadastrado no sistema.");
            return;
        }

        System.out.println("\n=== RELATÓRIO DE VEÍCULOS POR MARCA ===");
        System.out.println("=======================================");
        
        veiculosPorMarca.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .forEach(entry -> {
                    System.out.printf("\nMarca: %s", entry.getKey());
                    System.out.printf("\nQuantidade: %d", entry.getValue());
                    System.out.println("\n------------------------");
                });
    }

    /**
     * Gera relatório de veículos com placa antiga
     */
    public void gerarRelatorioVeiculosPlacaAntiga() {
        List<Veiculo> veiculosPlacaAntiga = veiculos.stream()
                .filter(v -> v.getPlaca().matches("^[A-Z]{3}[0-9]{4}$"))
                .toList();

        if (veiculosPlacaAntiga.isEmpty()) {
            System.out.println("\nNenhum veículo com placa antiga encontrado.");
            return;
        }

        System.out.println("\n=== VEÍCULOS COM PLACA ANTIGA ===");
        System.out.println("=================================");
        
        for (Veiculo v : veiculosPlacaAntiga) {
            System.out.println("\nPlaca: " + v.getPlaca());
            System.out.println("Marca: " + v.getMarca());
            System.out.println("Modelo: " + v.getModelo());
            System.out.println("Proprietário: " + v.getProprietario().getNome());
            System.out.println("------------------------");
        }
    }
} 