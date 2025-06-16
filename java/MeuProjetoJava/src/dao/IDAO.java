package dao;

import java.util.List;

/**
 * Interface genérica para operações de persistência
 * @param <T> Tipo da entidade a ser persistida
 */
public interface IDAO<T> {
    void salvar(T entidade);
    void atualizar(T entidade);
    void deletar(String id);
    T buscarPorId(String id);
    List<T> listarTodos();
} 