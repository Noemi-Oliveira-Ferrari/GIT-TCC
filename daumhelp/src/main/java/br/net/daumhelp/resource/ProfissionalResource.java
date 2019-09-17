package br.net.daumhelp.resource;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.net.daumhelp.model.Profissional;
import br.net.daumhelp.model.ProfissionalDTO;
import br.net.daumhelp.repository.ProfissionalDTORepository;
import br.net.daumhelp.repository.ProfissionalRepository;
import br.net.daumhelp.utils.HandleDates;

@CrossOrigin(origins = "http://localhost")
@RestController
@RequestMapping("/profissionais")
public class ProfissionalResource {

	@Autowired
	private ProfissionalDTORepository proDTORepository;
	@Autowired
	private ProfissionalRepository proRepository;
	
	@GetMapping
	public List<ProfissionalDTO> getPros(){
		return proDTORepository.findAll();
	}

	@GetMapping("/id/{id}")
	public Optional<ProfissionalDTO> getProById(@PathVariable Long id) {
		return proDTORepository.findById(id);
	}

	@GetMapping("/categoria/{idCategoria}")
	public List<ProfissionalDTO> getProByCategoria(@PathVariable Long idCategoria) {
		return proDTORepository.findByCategoria(idCategoria);
	}


	@GetMapping("/subcategoria/{idSubategoria}")
	public List<ProfissionalDTO> getProBySubcategoria(@PathVariable Long idSubategoria) {
		return proDTORepository.findBySubcategoria(idSubategoria);
	}

	@GetMapping("/cpf/{cpf}")
	public ProfissionalDTO getProByCpf(@PathVariable String cpf) {
		return proDTORepository.findByCpf(cpf);
	}

	@GetMapping("/cnpj/{cnpj}")
	public ProfissionalDTO getProByCnpj(@PathVariable String cnpj) {
		return proDTORepository.findByCnpj(cnpj);
	}

	@GetMapping("/endereco/{idEndereco}")
	public ProfissionalDTO getProByIdEndereco(@PathVariable Long idEndereco) {
		return proDTORepository.findByIdEndereco(idEndereco);
	}

	@GetMapping("/cep/{cep}")
	public List<ProfissionalDTO> getProByIdEndereco(@PathVariable String cep) {
		return proDTORepository.findByCep(cep);
	}

	@GetMapping("/cidade/{idCidade}")
	public List<ProfissionalDTO> getProByCidade(@PathVariable Long idCidade) {
		return proDTORepository.findByCidade(idCidade);
	}

	@GetMapping("/microrregiao/{idMicro}")
	public List<ProfissionalDTO> getProByMicro(@PathVariable Long idMicro) {
		return proDTORepository.findByMicrorregiao(idMicro);
	}

	@GetMapping("/uf/{uf}")
	public List<ProfissionalDTO> getProByUf(@PathVariable String uf) {
		return proDTORepository.findByUf(uf);
	}

	@GetMapping("/idUf/{idUf}")
	public List<ProfissionalDTO> getProByIdUf(@PathVariable Long idUf) {
		return proDTORepository.findByIdUf(idUf);
	}
	
	@PostMapping
	public ResponseEntity<Profissional> gravarPro(
			@RequestBody Profissional profissional,
			HttpServletResponse response){

		
		profissional.setCriadoEm(HandleDates.dataHoraAtual());
		profissional.setAtualizadoEm(HandleDates.dataHoraAtual());
		
		 Profissional proSalvo = proRepository.save(profissional);
		 
		 URI uri = ServletUriComponentsBuilder
				 .fromCurrentRequestUri()
				 .buildAndExpand(profissional.getIdProfissional())
				 .toUri();
		 response.addHeader("Location", uri.toASCIIString());
		 
		 return ResponseEntity.created(uri).body(profissional);
	}
	
	@PutMapping("/id/{idPro}")
	public ResponseEntity<Profissional> atualizarPro(
			@RequestBody Profissional profissional,
			@PathVariable Long idPro){
		
		Profissional proSalvo = proRepository.findById(idPro).get();
		
		profissional.setAtualizadoEm(HandleDates.dataHoraAtual());
		profissional.setCriadoEm(proSalvo.getCriadoEm());
		
		BeanUtils.copyProperties(profissional, proSalvo, "idProfissional");

		proRepository.save(profissional);
		return ResponseEntity.ok(proSalvo);
	}
	
	
}