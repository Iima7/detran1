package dao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import model.Proprietario;
import model.Veiculo;

/**
 * Classe responsável pela persistência dos dados de veículos.
 * Implementa operações de leitura e escrita em arquivo para armazenar
 * e recuperar informações dos veículos cadastrados.
 */
public class VeiculoDAO {
    /** Nome do arquivo onde os dados são persistidos */
    private static final String ARQUIVO = "veiculos.txt";
    
    /**
     * Salva um veículo no arquivo de persistência
     * @param veiculo Veículo a ser salvo
     */
    public void salvar(Veiculo veiculo) {
        try (FileWriter fw = new FileWriter(ARQUIVO, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            
            out.println(veiculo.getPlaca() + "," + 
                       veiculo.getMarca() + "," + 
                       veiculo.getModelo() + "," + 
                       veiculo.getAno() + "," + 
                       veiculo.getProprietario().getNome() + "," + 
                       veiculo.getProprietario().getCpf());
            
        } catch (IOException e) {
            System.err.println("Erro ao salvar veículo: " + e.getMessage());
        }
    }
    
    /**
     * Lista todos os veículos cadastrados no arquivo
     * @return Lista de veículos encontrados
     */
    public List<Veiculo> listarTodos() {
        List<Veiculo> veiculos = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados.length == 6) {
                    Proprietario proprietario = new Proprietario(dados[4], dados[5]);
                    Veiculo veiculo = new Veiculo(
                        dados[0], // placa
                        dados[1], // marca
                        dados[2], // modelo
                        Integer.parseInt(dados[3]), // ano
                        dados[3], // cor
                        proprietario
                    );
                    veiculos.add(veiculo);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler veículos: " + e.getMessage());
        }
        
        return veiculos;
    }
    
    /**
     * Remove um veículo do arquivo de persistência
     * @param placa Placa do veículo a ser removido
     * @return true se o veículo foi removido com sucesso, false caso contrário
     */
    public boolean remover(String placa) {
        List<Veiculo> veiculos = listarTodos();
        boolean removido = veiculos.removeIf(v -> v.getPlaca().equalsIgnoreCase(placa));
        
        if (removido) {
            try (PrintWriter out = new PrintWriter(new FileWriter(ARQUIVO))) {
                for (Veiculo v : veiculos) {
                    out.println(v.getPlaca() + "," + 
                              v.getMarca() + "," + 
                              v.getModelo() + "," + 
                              v.getAno() + "," + 
                              v.getProprietario().getNome() + "," + 
                              v.getProprietario().getCpf());
                }
            } catch (IOException e) {
                System.err.println("Erro ao remover veículo: " + e.getMessage());
                return false;
            }
        }
        
        return removido;
    }
} 