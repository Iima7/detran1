package model;

/**
 * Classe utilitária para validação de CPF.
 * Implementa o algoritmo oficial de validação de CPF da Receita Federal.
 */
public class CPFValidator {
    
    /**
     * Valida se um CPF é válido de acordo com as regras da Receita Federal
     * @param cpf CPF a ser validado (pode estar com ou sem formatação)
     * @return true se o CPF for válido, false caso contrário
     */
    public static boolean isValid(String cpf) {
        // Remove caracteres não numéricos
        cpf = cpf.replaceAll("[^0-9]", "");
        
        // Verifica se o CPF tem 11 dígitos
        if (cpf.length() != 11) {
            return false;
        }
        
        // Verifica se todos os dígitos são iguais
        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }
        
        // Calcula o primeiro dígito verificador
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += (cpf.charAt(i) - '0') * (10 - i);
        }
        int firstDigit = 11 - (sum % 11);
        if (firstDigit > 9) {
            firstDigit = 0;
        }
        
        // Calcula o segundo dígito verificador
        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += (cpf.charAt(i) - '0') * (11 - i);
        }
        int secondDigit = 11 - (sum % 11);
        if (secondDigit > 9) {
            secondDigit = 0;
        }
        
        // Verifica se os dígitos calculados correspondem aos do CPF
        return (cpf.charAt(9) - '0' == firstDigit) && (cpf.charAt(10) - '0' == secondDigit);
    }
} 