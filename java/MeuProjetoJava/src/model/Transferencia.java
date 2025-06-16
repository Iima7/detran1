package model;

import java.time.LocalDateTime;

/**
 * Classe que representa uma transferência de propriedade de veículo no sistema DETRAN.
 * Esta classe registra todas as informações relevantes de uma transferência,
 * incluindo o veículo, o novo proprietário e a data da transferência.
 */
public class Transferencia {
    /** Veículo que está sendo transferido */
    private Veiculo veiculo;
    
    /** Novo proprietário do veículo */
    private Proprietario novoProprietario;
    
    /** Data e hora em que a transferência foi realizada */
    private LocalDateTime dataTransferencia;

    /**
     * Construtor que cria uma nova transferência
     * @param veiculo Veículo que está sendo transferido
     * @param novoProprietario Novo proprietário do veículo
     */
    public Transferencia(Veiculo veiculo, Proprietario novoProprietario) {
        this.veiculo = veiculo;
        this.novoProprietario = novoProprietario;
        this.dataTransferencia = LocalDateTime.now(); // Registra a data/hora atual
    }

    /**
     * Retorna o veículo que foi transferido
     * @return Referência ao veículo
     */
    public Veiculo getVeiculo() {
        return veiculo;
    }

    /**
     * Define ou atualiza o veículo da transferência
     * @param veiculo Novo veículo
     */
    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    /**
     * Retorna o novo proprietário do veículo
     * @return Referência ao novo proprietário
     */
    public Proprietario getNovoProprietario() {
        return novoProprietario;
    }

    /**
     * Define ou atualiza o novo proprietário
     * @param novoProprietario Novo proprietário
     */
    public void setNovoProprietario(Proprietario novoProprietario) {
        this.novoProprietario = novoProprietario;
    }

    /**
     * Retorna a data e hora da transferência
     * @return Data e hora da transferência
     */
    public LocalDateTime getDataTransferencia() {
        return dataTransferencia;
    }

    /**
     * Define ou atualiza a data da transferência
     * @param dataTransferencia Nova data da transferência
     */
    public void setDataTransferencia(LocalDateTime dataTransferencia) {
        this.dataTransferencia = dataTransferencia;
    }

    /**
     * Retorna uma representação em texto da transferência
     * Útil para logging e depuração
     */
    @Override
    public String toString() {
        return "Transferencia{" +
                "veiculo=" + veiculo +
                ", novoProprietario=" + novoProprietario +
                ", dataTransferencia=" + dataTransferencia +
                '}';
    }
} 