package br.net.daumhelp.resource;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.net.daumhelp.cep.GetCep;
import br.net.daumhelp.model.Cidade;
import br.net.daumhelp.model.Endereco;
import br.net.daumhelp.model.EnderecoCep;
import br.net.daumhelp.repository.CidadeRepository;
import br.net.daumhelp.repository.EnderecoRepository;
import br.net.daumhelp.utils.HandleJsonInJava;

@CrossOrigin(origins = "http://localhost")
@RestController
@RequestMapping("/enderecos")
public class EnderecoResource {

	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;

	@GetMapping
	public List<Endereco> getEnderecos(){
		return enderecoRepository.findAll();
	}
	
	@GetMapping("/cep/{cep}")
	public Endereco getEnderecosViacep
	(@PathVariable String cep){
		String jsonEndereco = GetCep.trazerCep(cep);
		EnderecoCep enderecoCep = HandleJsonInJava.convertEnderecoJsonToJavaObject(jsonEndereco);
		
		Endereco endereco = new Endereco();
		endereco.setLogradouro(enderecoCep.getLogradouro());
		endereco.setCep(enderecoCep.getCep());
		endereco.setBairro(enderecoCep.getBairro());
		
		Long idCidade = Long.parseLong(enderecoCep.getIbge());
		Cidade cidade = cidadeRepository.findByOtherId(idCidade);
		
		endereco.setCidade(cidade);
		return endereco;
	}
	
	@PostMapping
	public ResponseEntity<Endereco> saveEndereco
	(@RequestBody Endereco endereco, HttpServletResponse response) {
		Endereco enderecoSalvo = enderecoRepository.save(endereco);
		
		URI uri = ServletUriComponentsBuilder	
				.fromCurrentRequestUri()
				.buildAndExpand(endereco.getIdEndereco())
				.toUri();
		response.addHeader("Location", uri.toASCIIString());
		
		return ResponseEntity.created(uri).body(endereco);
	}
	
	
}
