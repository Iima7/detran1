# 📋 Etapas do Projeto - Sistema de Gerenciamento de Veículos (DETRAN)

## ✅ Configuração Inicial do Projeto
- [x] Configurar estrutura do projeto em Java (pacotes: model, dao, service, ui)
- [x] Garantir uso de Programação Orientada a Objetos (POO)
- [x] Configurar projeto para salvar dados de forma local (arquivo .txt)
- [x] Preciso que meu sistema tenha um menu das minhas funcionalidades

## 🧾 Modelagem de Dados
- [x] Criar entidade `Veiculo` (placa, marca, modelo, ano, cor, proprietário)
- [x] Criar entidade `Proprietario` (nome, CPF)
- [x] Criar entidade `Transferencia` (veículo, novo proprietário, data)
- [x] Implementar validador de CPF
- [x] Criar mapeamento do histórico de transferências

## 🛠 Funcionalidades Principais

### Cadastro de Veículos
- [x] Validar formato da placa (Antigo ou Mercosul)
- [x] Gerar placa Mercosul automaticamente para novos emplacamentos
- [x] Persistir veículo no banco de dados com CPF válido do proprietário

### Transferência de Propriedade
- [x] Validar novo CPF e garantir que seja diferente do atual
- [x] Converter placa antiga para Mercosul automaticamente (regra de conversão)
- [x] Registrar data da transferência
- [x] Registrar nova entrada no histórico de transferências

### Baixa de Veículos
- [x] Permitir exclusão apenas se não houver histórico de transferências

### Consultas
- [x] Consulta de veículo por placa
- [x] Consulta de veículos por proprietário (CPF)
- [x] Consulta do histórico de transferências por veículo

### Relatórios
- [x] Quantidade de veículos por marca
- [x] Veículos transferidos em um período informado
- [x] Veículos com placa antiga ainda não transferidos

## 💾 Persistência e Carga de Dados
- [x] Implementar DAO e classes de persistência
- [x] Garantir persistência dos dados entre execuções
- [x] Criar rotina de carga inicial de dados a partir de arquivos `.txt` (se banco estiver vazio)
