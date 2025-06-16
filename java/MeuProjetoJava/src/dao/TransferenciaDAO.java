package dao;

import model.Transferencia;
import model.Veiculo;
import model.Proprietario;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável pela persistência dos dados de transferências.
 * Implementa operações de leitura e escrita em arquivo para armazenar
 * e recuperar informações das transferências de propriedade realizadas.
 */
public class TransferenciaDAO {
    /** Nome do arquivo onde os dados são persistidos */
    private static final String ARQUIVO = "transferencias.txt";
    
    /** Formato de data utilizado para persistência */
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    /** DAO de veículos para recuperar informações dos veículos transferidos */
    private VeiculoDAO veiculoDAO;
    
    /**
     * Construtor que inicializa o DAO com a dependência do DAO de veículos
     * @param veiculoDAO DAO responsável pela persistência de veículos
     */
    public TransferenciaDAO(VeiculoDAO veiculoDAO) {
        this.veiculoDAO = veiculoDAO;
    }
    
    /**
     * Salva uma transferência no arquivo de persistência
     * @param transferencia Transferência a ser salva
     */
    public void salvar(Transferencia transferencia) {
        try (FileWriter fw = new FileWriter(ARQUIVO, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            
            out.println(transferencia.getVeiculo().getPlaca() + "," + 
                       transferencia.getNovoProprietario().getNome() + "," + 
                       transferencia.getNovoProprietario().getCpf() + "," + 
                       transferencia.getDataTransferencia().format(formatter));
            
        } catch (IOException e) {
            System.err.println("Erro ao salvar transferência: " + e.getMessage());
        }
    }
    
    /**
     * Lista todas as transferências cadastradas no arquivo
     * @return Lista de transferências encontradas
     */
    public List<Transferencia> listarTodos() {
        List<Transferencia> transferencias = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados.length == 4) {
                    String placa = dados[0];
                    Veiculo veiculo = veiculoDAO.listarTodos().stream()
                            .filter(v -> v.getPlaca().equals(placa))
                            .findFirst()
                            .orElse(null);
                            
                    if (veiculo != null) {
                        Proprietario novoProprietario = new Proprietario(dados[1], dados[2]);
                        LocalDateTime dataTransferencia = LocalDateTime.parse(dados[3], formatter);
                        
                        Transferencia transferencia = new Transferencia(veiculo, novoProprietario);
                        transferencia.setDataTransferencia(dataTransferencia);
                        transferencias.add(transferencia);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler transferências: " + e.getMessage());
        }
        
        return transferencias;
    }
} 