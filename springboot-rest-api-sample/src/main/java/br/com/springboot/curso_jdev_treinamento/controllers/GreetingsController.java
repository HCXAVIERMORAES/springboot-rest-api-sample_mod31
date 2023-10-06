package br.com.springboot.curso_jdev_treinamento.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.springboot.curso_jdev_treinamento.model.Usuario;
import br.com.springboot.curso_jdev_treinamento.repository.UsuarioRepository;

/**
 *@RestController -> intercepta todas as requisições que se fizer o mapeamento, tbm tem paramentros
 * A sample greetings controller to return greeting text
 */
@RestController
public class GreetingsController {
	
	//usando o repositorio para que se fassa os crudes
	@Autowired /*popular IC/CD ou CDI - injeção de dependencia*/
	private UsuarioRepository usuarioRepository;
	
    /**
     *@RequestMapping(value = "/{name}", method = RequestMethod.GET) -> intercepta apos "/" o conteudo na url
     * @param name the name to greet ->diga o nome para cumprimentar
     * @return texto de saudação
     */
    @RequestMapping(value = "mostrarnome/{name}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String greetingText(@PathVariable String name) {
        return "Curso spring boot API: " + name + "!";
    }
    
    //criando outra url
    @RequestMapping(value = "/olamundo/{nome}", method = RequestMethod.GET) //mapeamento e metodo
    @ResponseStatus(HttpStatus.OK) //resposta
    public String retornaOlaMundo(@PathVariable String nome) {
    	
    	//teste de injeção de dependencia
    	Usuario usuario = new Usuario();
    	usuario.setNome(nome);
    	
    	usuarioRepository.save(usuario);//grava no BD
    	
		return "Olá Mundo! " + nome;
	}
    
    //API restorna um JASON ,buscar todos. Primeiro metodo, sava todos
    @GetMapping(value = "listatodos") //idem ao @RequestMappin porem mais enjuto
    @ResponseBody //retorna os dados para o corpo da pag. - retorna um Jason
    public ResponseEntity<List<Usuario>> listaUsuario() {
    	
    	List<Usuario> usuarios = usuarioRepository.findAll(); //findAll -> retorna uma lista de usuarios
    	
    	return new ResponseEntity<List<Usuario>>(usuarios,HttpStatus.OK); //retorna a lista em Json
    	
    }
    
    /**
     * criando END-POINT para salvar no banco
     * deve receber por POST - q envia por baixo dos panos a requisição
     *  @PostMapping(value = "salvar") -> mapeia a url como salvar
     *  @ResponseBody() -> fazer um retorno apos salvar.
     *  os dados seram recebido como parametros no metodo - ...salvar(parametros)
     *  @RequestBody -> recebe os dados passados e injeta na classe usuario
     *  ResponseEntity<Usuario>(user, HttpStatus.CREATED) -> usa-se passando o ResponseEntity passando o
     *  corpo da requisição, user, e retornando um status, podendo ser tbm um .OK. Nesse caso um .CREATED 
     *  	informando que foi criado.
     */
    
    @PostMapping(value = "salvar") //mapeia a url
    @ResponseBody() //descrição da resposta
    public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario) {//recebe os dados para salvar
    	
    	Usuario user = usuarioRepository.save(usuario); //pega o repositry e salva e retorna o usuario salvo
    	
    	//retorno do end-point
    	return  new ResponseEntity<Usuario>(user, HttpStatus.CREATED); 
    }
    
    /**
     * Criando end-point para delete do BD
     * idem ao salva, porem passando o Id
     */	
    @DeleteMapping(value = "delete") //mapeia a url
    @ResponseBody() //descrição da resposta
    public ResponseEntity<String> delete (@RequestParam Long iduser ){ //mapeia a url
    
    	 usuarioRepository.deleteById(iduser); //pega o repositry e deleta    	
    	 
    	//retorno do end-point
    	return new ResponseEntity<String> ("User deletado com sucesso", HttpStatus.OK);     
    }
    
    /**
     * Criar um end-point para buscar usuario por id
     * Metodo get
     * busca por id, url: http://localhost:8000/buscaruserid
     * OBS: as vezes so com o parametro não funciona, colocar: (@RequestParam(name = "variavel") Long variavel )
     */
    
    @GetMapping(value = "buscaruserid") //mapeia a url
    @ResponseBody() //descrição da resposta
    public ResponseEntity<Usuario> buscaruserid (@RequestParam(name = "iduser") Long iduser ){ //recebe os dados para consultar
    
    	Usuario usuario = usuarioRepository.findById(iduser).get(); //pega o repositry chama o metodo e retorna   em usuario   	
    	 
    	//retorno do end-point
    	return new ResponseEntity<Usuario> (usuario, HttpStatus.OK);     
    }
    
    /**
     * Metodo de atualizar
     * Oid é obrigatorio, logo qdo se passa ResponseEntity<?> uma "?" ele pode retorna qulquer coisa
     */
    @PutMapping(value = "atualizar")
    @ResponseBody() //descrição da resposta
    public ResponseEntity<?> atualizar(@RequestBody Usuario usuario) {
    	
    	if(usuario.getId() == null) {
    		return  new ResponseEntity<String>("ID não informado para aualização", HttpStatus.OK);
    	}
    	
    	Usuario user = usuarioRepository.saveAndFlush(usuario); //pega o repositry e salva, roda  direto no BD e retorna o usuario salvo
    	
    	//retorno do end-point
    	return  new ResponseEntity<Usuario>(user, HttpStatus.OK); 
    }
    
    /**
     * Criar um end-point para buscar por nome
     * Metodo get
     * busca por nome, url: http://localhost:8000/buscarPorNome
     * usa-se o List do java.util.Lis
     * .trim() é para retirar espaços. .toUpperCase() -> trranforma em maiusculo
     */
    
    @GetMapping(value = "buscarPorNome") //mapeia a url
    @ResponseBody() //descrição da resposta
    public ResponseEntity<List<Usuario>> buscarPorNome ( @RequestParam(name = "name") String name ){ //recebe os dados para consultar
    
    	List<Usuario> usuario = usuarioRepository.buscarPorNome(name.trim().toUpperCase()); //pega o repositry chama o metodo e retorna   em usuario   	
    	 
    	//retorno do end-point
    	return new ResponseEntity<List<Usuario>> (usuario, HttpStatus.OK);     
    }
    
}
