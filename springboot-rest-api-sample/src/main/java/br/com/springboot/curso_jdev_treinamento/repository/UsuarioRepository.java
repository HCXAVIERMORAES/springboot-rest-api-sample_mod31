package br.com.springboot.curso_jdev_treinamento.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.springboot.curso_jdev_treinamento.model.Usuario;

/**
 * Para fazer os crudes é necessario o repositorio, logo tabela usuario.
 * Passe-se a classe persistente, que representa o BD e o tipo de ID.
 * 
 * @author hphoe
 *
 */

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	/**
	 * para fazer uma busca mais aprimorada, como buscar por nome, é necessario fazer a implementação
	 * pois o spring data fornece apenas o padrão basico, com salvar, deletar, etc.
	 * Uma busca por nome pode retornar nennhum ou mais de um, logo uma lista. Por ser uma interface so se faz
	 * a declaração da interface.
	 * sobre ele vai a instrução para se consultar no BD, ouseja, uma JPQL.
	 * trim(u.nome) -> retira espaço na pesquisa. upper() -> transforma em maiusculo.
	 * No UsuarioRepository vaiso isso, o resto vai no controler.
	 */
	
	@Query(value = "select u from Usuario u where upper(trim(u.nome)) like %?1%")
	List<Usuario> buscarPorNome (String name);
}
