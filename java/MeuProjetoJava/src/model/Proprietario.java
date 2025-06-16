package model;

/**
 * Classe que representa um proprietário de veículo no sistema DETRAN.
 * Esta classe é responsável por armazenar e gerenciar as informações
 * pessoais dos proprietários de veículos, como nome e CPF.
 * 
 * Utilizada nas operações de:
 * - Cadastro de novos proprietários
 * - Transferência de propriedade de veículos
 * - Consulta de proprietários
 */
public class Proprietario {
    /** Nome completo do proprietário do veículo */
    private String nome;
    
    /** 
     * CPF do proprietário no formato XXX.XXX.XXX-XX
     * Este campo é usado como identificador único do proprietário
     */
    private String cpf;

    /**
     * Construtor que cria um novo proprietário com nome e CPF
     * @param nome Nome completo do proprietário
     * @param cpf CPF do proprietário no formato XXX.XXX.XXX-XX
     */
    public Proprietario(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }

    /**
     * Retorna o nome do proprietário
     * @return Nome completo do proprietário
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define ou atualiza o nome do proprietário
     * @param nome Novo nome do proprietário
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna o CPF do proprietário
     * @return CPF no formato XXX.XXX.XXX-XX
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * Define ou atualiza o CPF do proprietário
     * @param cpf Novo CPF no formato XXX.XXX.XXX-XX
     */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    /**
     * Retorna uma representação em texto do proprietário
     * Útil para logging e depuração
     * @return String no formato "Proprietario{nome='Nome', cpf='CPF'}"
     */
    @Override
    public String toString() {
        return "Proprietario{" +
                "nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                '}';
    }
} 