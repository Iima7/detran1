package model;

/**
 * Classe que representa um veículo no sistema DETRAN.
 * Esta classe armazena todas as informações relevantes de um veículo,
 * incluindo seus dados básicos e informações do proprietário.
 */
public class Veiculo {
    // Atributos básicos do veículo
    private String placa;        // Placa do veículo (formato antigo ou Mercosul)
    private String marca;        // Marca do veículo (ex: Fiat, Volkswagen)
    private String modelo;       // Modelo do veículo (ex: Uno, Gol)
    private int ano;            // Ano de fabricação do veículo
    private String cor;         // Cor do veículo
    private Proprietario proprietario;  // Referência ao proprietário atual

    /**
     * Construtor que cria um novo veículo com todos os seus dados
     * @param placa Placa do veículo
     * @param marca Marca do veículo
     * @param modelo Modelo do veículo
     * @param ano Ano de fabricação
     * @param cor Cor do veículo
     * @param proprietario Proprietário atual do veículo
     */
    public Veiculo(String placa, String marca, String modelo, int ano, String cor, Proprietario proprietario) {
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.cor = cor;
        this.proprietario = proprietario;
    }

    // Métodos Getters e Setters para todos os atributos
    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public Proprietario getProprietario() {
        return proprietario;
    }

    public void setProprietario(Proprietario proprietario) {
        this.proprietario = proprietario;
    }

    /**
     * Retorna uma representação em texto do veículo
     * Útil para logging e depuração
     */
    @Override
    public String toString() {
        return "Veiculo{" +
                "placa='" + placa + '\'' +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", ano=" + ano +
                ", cor='" + cor + '\'' +
                ", proprietario=" + proprietario +
                '}';
    }
} 