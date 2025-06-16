import ui.Menu;

/**
 * Classe principal que inicia o sistema.
 * Responsável por criar a instância do menu e iniciar a interface com o usuário.
 */
public class Main {
    public static void main(String[] args) {
        // Cria uma nova instância do menu
        Menu menu = new Menu();
        // Exibe o menu principal e inicia a interação com o usuário
        menu.exibirMenuPrincipal();
        // Fecha o scanner quando o programa for encerrado
        menu.fechar();
    }
} 