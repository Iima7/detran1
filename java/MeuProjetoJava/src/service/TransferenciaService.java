package service;

import model.Transferencia;
import model.Veiculo;
import model.Proprietario;
import model.CPFValidator;
import dao.TransferenciaDAO;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

/**
 * Classe de serviço responsável pela lógica de negócio relacionada às transferências.
 * Implementa todas as operações necessárias para gerenciar transferências de propriedade.
 */
public class TransferenciaService {
    /** Lista em memória das transferências realizadas */
    private List<Transferencia> transferencias;
    
    /** Serviço de veículos para operações relacionadas */
    private VeiculoService veiculoService;
    
    /** DAO responsável pela persistência das transferências */
    private TransferenciaDAO transferenciaDAO;
    
    /**
     * Construtor que inicializa o serviço e carrega os dados do arquivo
     * @param veiculoService Serviço de veículos para operações relacionadas
     */
    public TransferenciaService(VeiculoService veiculoService) {
        this.veiculoService = veiculoService;
        this.transferenciaDAO = new TransferenciaDAO(new dao.VeiculoDAO());
        this.transferencias = new ArrayList<>(transferenciaDAO.listarTodos());
    }
    
    /**
     * Realiza uma transferência de propriedade de veículo
     * @param placa Placa do veículo
     * @param novoProprietario Novo proprietário do veículo
     * @return true se a transferência foi bem sucedida, false caso contrário
     */
    public boolean realizarTransferencia(String placa, Proprietario novoProprietario) {
        // Busca o veículo
        Veiculo veiculo = veiculoService.buscarVeiculoPorPlaca(placa);
        if (veiculo == null) {
            return false; // Veículo não encontrado
        }
        
        // Valida o CPF do novo proprietário
        if (!CPFValidator.isValid(novoProprietario.getCpf())) {
            return false;
        }
        
        // Verifica se o novo proprietário é diferente do atual
        if (veiculo.getProprietario().getCpf().equals(novoProprietario.getCpf())) {
            return false; // Mesmo proprietário
        }
        
        // Cria a transferência
        Transferencia transferencia = new Transferencia(veiculo, novoProprietario);
        
        // Atualiza o proprietário do veículo
        veiculo.setProprietario(novoProprietario);
        
        // Registra a transferência
        transferencias.add(transferencia);
        transferenciaDAO.salvar(transferencia);
        
        return true;
    }
    
    /**
     * Busca todas as transferências de um veículo específico
     * @param placa Placa do veículo
     * @return Lista de transferências do veículo
     */
    public List<Transferencia> buscarTransferenciasPorVeiculo(String placa) {
        return transferencias.stream()
                .filter(t -> t.getVeiculo().getPlaca().equalsIgnoreCase(placa))
                .toList();
    }
    
    /**
     * Retorna lista de todas as transferências realizadas
     * @return Lista de transferências
     */
    public List<Transferencia> listarTransferencias() {
        return new ArrayList<>(transferencias);
    }
    
    /**
     * Verifica se um veículo possui histórico de transferências
     * @param placa Placa do veículo
     * @return true se o veículo tem transferências, false caso contrário
     */
    public boolean veiculoTemTransferencias(String placa) {
        return !buscarTransferenciasPorVeiculo(placa).isEmpty();
    }

    /**
     * Exibe o histórico de transferências de um veículo
     * @param placa Placa do veículo
     */
    public void exibirHistoricoTransferencias(String placa) {
        List<Transferencia> transferencias = buscarTransferenciasPorVeiculo(placa);
        
        if (transferencias.isEmpty()) {
            System.out.println("\nNenhuma transferência encontrada para este veículo.");
            return;
        }

        System.out.println("\nHistórico de Transferências:");
        System.out.println("============================");
        
        for (Transferencia t : transferencias) {
            System.out.println("\nData da Transferência: " + t.getDataTransferencia());
            System.out.println("Novo Proprietário:");
            System.out.println("  Nome: " + t.getNovoProprietario().getNome());
            System.out.println("  CPF: " + t.getNovoProprietario().getCpf());
            System.out.println("------------------------");
        }
    }

    /**
     * Gera relatório de transferências realizadas em um período
     * @param dataInicio Data inicial do período
     * @param dataFim Data final do período
     */
    public void gerarRelatorioTransferenciasPorPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim) {
        List<Transferencia> transferenciasNoPeriodo = transferencias.stream()
                .filter(t -> !t.getDataTransferencia().isBefore(dataInicio) && 
                           !t.getDataTransferencia().isAfter(dataFim))
                .toList();

        if (transferenciasNoPeriodo.isEmpty()) {
            System.out.println("\nNenhuma transferência encontrada no período especificado.");
            return;
        }

        System.out.println("\n=== TRANSFERÊNCIAS NO PERÍODO ===");
        System.out.println("Período: " + dataInicio.toLocalDate() + " até " + dataFim.toLocalDate());
        System.out.println("=================================");
        
        for (Transferencia t : transferenciasNoPeriodo) {
            System.out.println("\nData: " + t.getDataTransferencia());
            System.out.println("Veículo: " + t.getVeiculo().getPlaca());
            System.out.println("  Marca: " + t.getVeiculo().getMarca());
            System.out.println("  Modelo: " + t.getVeiculo().getModelo());
            System.out.println("Novo Proprietário: " + t.getNovoProprietario().getNome());
            System.out.println("------------------------");
        }
    }
} 