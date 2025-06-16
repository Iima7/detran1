package dao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstrata que implementa operações básicas de persistência em arquivo
 * @param <T> Tipo da entidade a ser persistida
 */
public abstract class ArquivoDAO<T> implements IDAO<T> {
    protected String nomeArquivo;
    protected List<T> cache;

    public ArquivoDAO(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
        this.cache = new ArrayList<>();
        carregarDados();
    }

    protected void carregarDados() {
        File arquivo = new File(nomeArquivo);
        if (!arquivo.exists()) {
            try {
                arquivo.createNewFile();
            } catch (IOException e) {
                System.err.println("Erro ao criar arquivo: " + e.getMessage());
            }
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                T entidade = converterLinhaParaObjeto(linha);
                if (entidade != null) {
                    cache.add(entidade);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo: " + e.getMessage());
        }
    }

    protected void salvarDados() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
            for (T entidade : cache) {
                String linha = converterObjetoParaLinha(entidade);
                writer.write(linha);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar no arquivo: " + e.getMessage());
        }
    }

    @Override
    public void salvar(T entidade) {
        cache.add(entidade);
        salvarDados();
    }

    @Override
    public void atualizar(T entidade) {
        String id = obterIdentificador(entidade);
        for (int i = 0; i < cache.size(); i++) {
            if (obterIdentificador(cache.get(i)).equals(id)) {
                cache.set(i, entidade);
                salvarDados();
                return;
            }
        }
    }

    @Override
    public void deletar(String id) {
        cache.removeIf(entidade -> obterIdentificador(entidade).equals(id));
        salvarDados();
    }

    @Override
    public T buscarPorId(String id) {
        return cache.stream()
                .filter(entidade -> obterIdentificador(entidade).equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<T> listarTodos() {
        return new ArrayList<>(cache);
    }

    // Métodos abstratos que devem ser implementados pelas classes filhas
    protected abstract T converterLinhaParaObjeto(String linha);
    protected abstract String converterObjetoParaLinha(T objeto);
    protected abstract String obterIdentificador(T objeto);
} 