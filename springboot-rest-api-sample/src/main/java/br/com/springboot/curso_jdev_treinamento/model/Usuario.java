package br.com.springboot.curso_jdev_treinamento.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

/**
 * classe de persistencia, tabela de banco, de usuario.
 * @Entity do pacote javax.persistence para virar tabela no banco.
 * Sempre se implementa o  Serializable
 * é primordial criar uma sequencia
 * @SequenceGenerator(name = "Seq_usuario", sequenceName = "Seq_usuario", allocationSize = 1, initialValue = 1 )
 * Cria uma sequencia para ser usada no id.
 * @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Seq_usuario") -> usando a seq. criada
 * @author hphoe
 *
 */

@Entity
@SequenceGenerator(name = "Seq_usuario", sequenceName = "Seq_usuario", allocationSize = 1, initialValue = 1 )
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;
	
	//atributos
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Seq_usuario")
	private Long id;
	
	private String nome;
	
	private int idade;
	
	//get e set

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getIdade() {
		return idade;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}

	
}
