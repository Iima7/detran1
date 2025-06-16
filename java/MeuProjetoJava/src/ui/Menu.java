package ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import model.Proprietario;
import model.Veiculo;
import service.TransferenciaService;
import service.VeiculoService;

/**
 * Classe responsável por gerenciar o menu do sistema e interação com o usuário
 */
public class Menu {
    private Scanner scanner;
    private VeiculoService veiculoService;
    private TransferenciaService transferenciaService;

    public Menu() {
        this.scanner = new Scanner(System.in);
        this.veiculoService = new VeiculoService();
        this.transferenciaService = new TransferenciaService(veiculoService);
    }

    /**
     * Exibe o menu principal e processa a escolha do usuário
     */
    public void exibirMenuPrincipal() {
        int opcao;
        do {
            System.out.println("\n=== SISTEMA DE GERENCIAMENTO DE VEÍCULOS - DETRAN ===");
            System.out.println("1. Cadastro de Veículos");
            System.out.println("2. Transferência de Propriedade");
            System.out.println("3. Baixa de Veículos");
            System.out.println("4. Consultas");
            System.out.println("5. Relatórios");
            System.out.println("0. Sair");
            System.out.print("\nEscolha uma opção: ");

            opcao = lerOpcao();

            switch (opcao) {
                case 1:
                    menuCadastroVeiculos();
                    break;
                case 2:
                    menuTransferencia();
                    break;
                case 3:
                    menuBaixaVeiculos();
                    break;
                case 4:
                    menuConsultas();
                    break;
                case 5:
                    menuRelatorios();
                    break;
                case 0:
                    System.out.println("Saindo do sistema...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void menuCadastroVeiculos() {
        System.out.println("\n=== CADASTRO DE VEÍCULOS ===");
        
        System.out.print("Digite a placa do veículo: ");
        String placa = scanner.nextLine().toUpperCase();
        
        System.out.print("Digite o modelo do veículo: ");
        String modelo = scanner.nextLine();
        
        System.out.print("Digite a marca do veículo: ");
        String marca = scanner.nextLine();
        
        System.out.print("Digite o ano de fabricação: ");
        int anoFabricacao = Integer.parseInt(scanner.nextLine());
        
        System.out.print("Digite a cor do veículo: ");
        String cor = scanner.nextLine();
        
        System.out.print("Digite o CPF do proprietário: ");
        String cpfProprietario = scanner.nextLine();
        
        System.out.print("Digite o nome do proprietário: ");
        String nomeProprietario = scanner.nextLine();
        
        Proprietario proprietario = new Proprietario(cpfProprietario, nomeProprietario);
        Veiculo veiculo = new Veiculo(placa, marca, modelo, anoFabricacao, cor, proprietario);
        
        if (veiculoService.cadastrarVeiculo(veiculo)) {
            System.out.println("\nVeículo cadastrado com sucesso!");
        } else {
            System.out.println("\nErro ao cadastrar veículo. Verifique se a placa já está cadastrada.");
        }
        
        aguardarEnter();
    }

    private void menuTransferencia() {
        System.out.println("\n=== TRANSFERÊNCIA DE PROPRIEDADE ===");
        
        System.out.print("Digite a placa do veículo: ");
        String placa = scanner.nextLine().toUpperCase();
        
        System.out.print("Digite o CPF do novo proprietário: ");
        String cpfNovoProprietario = scanner.nextLine();
        
        System.out.print("Digite o nome do novo proprietário: ");
        String nomeNovoProprietario = scanner.nextLine();
        
        Proprietario novoProprietario = new Proprietario(cpfNovoProprietario, nomeNovoProprietario);
        
        if (transferenciaService.realizarTransferencia(placa, novoProprietario)) {
            System.out.println("\nTransferência realizada com sucesso!");
        } else {
            System.out.println("\nErro ao realizar transferência. Verifique se:");
            System.out.println("1. A placa está correta");
            System.out.println("2. O veículo existe no sistema");
            System.out.println("3. O novo proprietário é diferente do atual");
        }
        
        aguardarEnter();
    }

    private void menuBaixaVeiculos() {
        System.out.println("\n=== BAIXA DE VEÍCULOS ===");
        System.out.print("Digite a placa do veículo para baixa: ");
        String placa = scanner.nextLine().toUpperCase();

        if (veiculoService.removerVeiculo(placa, transferenciaService)) {
            System.out.println("Veículo removido com sucesso!");
        } else {
            System.out.println("Não foi possível remover o veículo. Verifique se:");
            System.out.println("1. A placa está correta");
            System.out.println("2. O veículo não possui histórico de transferências");
        }
        aguardarEnter();
    }

    private void menuConsultas() {
        int opcao;
        do {
            System.out.println("\n=== CONSULTAS ===");
            System.out.println("1. Consultar veículo por placa");
            System.out.println("2. Consultar veículos por proprietário (CPF)");
            System.out.println("3. Consultar histórico de transferências");
            System.out.println("0. Voltar");
            System.out.print("\nEscolha uma opção: ");
            
            opcao = lerOpcao();
            
            switch (opcao) {
                case 1:
                    consultarVeiculoPorPlaca();
                    break;
                case 2:
                    consultarVeiculosPorProprietario();
                    break;
                case 3:
                    consultarHistoricoTransferencias();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
            if (opcao != 0) {
                aguardarEnter();
            }
        } while (opcao != 0);
    }

    private void consultarVeiculoPorPlaca() {
        System.out.println("\n=== CONSULTA POR PLACA ===");
        System.out.print("Digite a placa do veículo: ");
        String placa = scanner.nextLine().toUpperCase();
        
        Veiculo veiculo = veiculoService.buscarVeiculoPorPlaca(placa);
        veiculoService.exibirDetalhesVeiculo(veiculo);
    }

    private void consultarVeiculosPorProprietario() {
        System.out.println("\n=== CONSULTA POR PROPRIETÁRIO ===");
        System.out.print("Digite o CPF do proprietário: ");
        String cpf = scanner.nextLine();
        
        List<Veiculo> veiculos = veiculoService.buscarVeiculosPorProprietario(cpf);
        
        if (veiculos.isEmpty()) {
            System.out.println("\nNenhum veículo encontrado para este proprietário.");
            return;
        }
        
        System.out.println("\nVeículos encontrados:");
        System.out.println("=====================");
        for (Veiculo v : veiculos) {
            veiculoService.exibirDetalhesVeiculo(v);
            System.out.println("------------------------");
        }
    }

    private void consultarHistoricoTransferencias() {
        System.out.println("\n=== CONSULTA DE HISTÓRICO DE TRANSFERÊNCIAS ===");
        System.out.print("Digite a placa do veículo: ");
        String placa = scanner.nextLine().toUpperCase();
        
        transferenciaService.exibirHistoricoTransferencias(placa);
    }

    private void menuRelatorios() {
        int opcao;
        do {
            System.out.println("\n=== RELATÓRIOS ===");
            System.out.println("1. Quantidade de veículos por marca");
            System.out.println("2. Veículos transferidos em um período");
            System.out.println("3. Veículos com placa antiga");
            System.out.println("0. Voltar");
            System.out.print("\nEscolha uma opção: ");
            
            opcao = lerOpcao();
            
            switch (opcao) {
                case 1:
                    relatorioVeiculosPorMarca();
                    break;
                case 2:
                    relatorioTransferenciasPorPeriodo();
                    break;
                case 3:
                    relatorioVeiculosPlacaAntiga();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
            if (opcao != 0) {
                aguardarEnter();
            }
        } while (opcao != 0);
    }

    private void relatorioVeiculosPorMarca() {
        veiculoService.gerarRelatorioVeiculosPorMarca();
    }

    private void relatorioTransferenciasPorPeriodo() {
        System.out.println("\n=== RELATÓRIO DE TRANSFERÊNCIAS POR PERÍODO ===");
        
        try {
            System.out.print("Digite a data inicial (DD/MM/AAAA HH:mm): ");
            String dataInicioStr = scanner.nextLine();
            LocalDateTime dataInicio = LocalDateTime.parse(dataInicioStr, 
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

            System.out.print("Digite a data final (DD/MM/AAAA HH:mm): ");
            String dataFimStr = scanner.nextLine();
            LocalDateTime dataFim = LocalDateTime.parse(dataFimStr, 
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

            transferenciaService.gerarRelatorioTransferenciasPorPeriodo(dataInicio, dataFim);
        } catch (DateTimeParseException e) {
            System.out.println("\nErro: Formato de data inválido. Use o formato DD/MM/AAAA HH:mm.");
        }
    }

    private void relatorioVeiculosPlacaAntiga() {
        veiculoService.gerarRelatorioVeiculosPlacaAntiga();
    }

    private int lerOpcao() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void aguardarEnter() {
        System.out.println("\nPressione ENTER para continuar...");
        scanner.nextLine();
    }

    /**
     * Fecha o scanner quando o programa for encerrado
     */
    public void fechar() {
        if (scanner != null) {
            scanner.close();
        }
    }
} 