package com.sboot.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.sboot.model.Pessoa;
import com.sboot.model.Telefone;
import com.sboot.relUtil.ReportUtil;
import com.sboot.repository.PessoaRepository;
import com.sboot.repository.ProfissaoRepository;
import com.sboot.repository.TelefoneRepository;

@Controller
public class PessoaController {

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private TelefoneRepository telefoneRepository;

	@Autowired
	private ReportUtil reportUtil;

	@Autowired
	private ProfissaoRepository profissaoRepository;

	@RequestMapping(method = RequestMethod.GET, value = "/cadpessoa")
	public ModelAndView init() {
		ModelAndView modelAndView = new ModelAndView("cadastro/cadpessoa");
		modelAndView.addObject("pessoaobj", new Pessoa());
		modelAndView.addObject("profissoes", profissaoRepository.findAll());
		modelAndView.addObject("pessoas", pessoaRepository.findAll(PageRequest.of(0, 5, Sort.by("nome"))));
		return modelAndView;
	}

	@GetMapping("/login")
	public ModelAndView telaLogin() {
		ModelAndView modelAndView = new ModelAndView("login");
		modelAndView.addObject("pessoaobj", new Pessoa());
		return modelAndView;

	}

	@RequestMapping(method = RequestMethod.POST, value = "**/salvarpessoa", consumes = { "multipart/form-data" })
	public ModelAndView salvar(@Valid Pessoa pessoa, BindingResult bindingResult, final MultipartFile file)
			throws IOException {

		// carrega os telefones para ver se essa pessoa tem telefone
		pessoa.setTelefones(telefoneRepository.getTelefones(pessoa.getId()));

		if (bindingResult.hasErrors()) {
			ModelAndView modelAndView = new ModelAndView("cadastro/cadpessoa");

			modelAndView.addObject("pessoas", pessoaRepository.findAll(PageRequest.of(0, 5, Sort.by("nome"))));
			modelAndView.addObject("pessoaobj", pessoa);
			List<String> msg = new ArrayList<String>();
			for (ObjectError errors : bindingResult.getAllErrors()) {
				msg.add(errors.getDefaultMessage()); // vem das anotações @NotEmpty e @NotNull
			}
			modelAndView.addObject("msg", msg);
			return modelAndView;
		}

		if (file.getSize() > 0) {
			if (pessoa.getId() != null && pessoa.getId() > 0) {

				/* Edição */
				Pessoa pessoaTemporaria = pessoaRepository.findById(pessoa.getId()).get();
				if (pessoaTemporaria.getFoto() == null) {

					pessoa.setFoto(file.getBytes());
					pessoa.setTipoArquivoFoto(file.getContentType());
					pessoa.setNomeArquivoFoto(file.getOriginalFilename());
				} else {
					pessoa.setFoto(file.getBytes());
					pessoa.setTipoArquivoFoto(file.getContentType());
					pessoa.setNomeArquivoFoto(file.getOriginalFilename());
				}

			} else {
				pessoa.setFoto(file.getBytes());
				pessoa.setTipoArquivoFoto(file.getContentType());
				pessoa.setNomeArquivoFoto(file.getOriginalFilename());
			}

		}

		/*
		 * verifica se há foto ou img para upload if (file.getSize() > 0) {
		 * if(pessoa.getId() != null && pessoa.getId() > 0) { // edição Pessoa
		 * fotoPessoaTempo = pessoaRepository.findById(pessoa.getId()).get();
		 * 
		 * if(fotoPessoaTempo.get)
		 * 
		 * 
		 * 
		 * pessoa.setFoto(fotoPessoaTempo.getFoto());
		 * pessoa.setTipoArquivoFoto(fotoPessoaTempo.getTipoArquivoFoto());
		 * pessoa.setNomeArquivoFoto(fotoPessoaTempo.getNomeArquivoFoto());
		 * 
		 * }else { pessoa.setFoto(file.getBytes());
		 * pessoa.setTipoArquivoFoto(file.getContentType());
		 * pessoa.setNomeArquivoFoto(file.getOriginalFilename()); } }
		 */

		ModelAndView andView = new ModelAndView("cadastro/cadpessoa");
		pessoaRepository.save(pessoa);
		andView.addObject("pessoas", pessoaRepository.findAll(PageRequest.of(0, 5, Sort.by("nome"))));
		andView.addObject("profissoes", profissaoRepository.findAll());
		andView.addObject("pessoaobj", new Pessoa());

		return andView;

	}

	@RequestMapping(method = RequestMethod.GET, value = "**/listapessoas")
	public ModelAndView pessoas() {
		ModelAndView andView = new ModelAndView("cadastro/cadpessoa");
		andView.addObject("pessoas", pessoaRepository.findAll(PageRequest.of(0, 5, Sort.by("nome"))));
		andView.addObject("pessoaobj", new Pessoa());
		andView.addObject("profissoes", profissaoRepository.findAll());

		return andView;
	}

	@GetMapping("/editarpessoa/{idpessoa}")
	public ModelAndView editar(@PathVariable("idpessoa") Long idpessoa) {

		Optional<Pessoa> pessoa = pessoaRepository.findById(idpessoa);

		ModelAndView modelAndView = new ModelAndView("cadastro/cadpessoa");
		modelAndView.addObject("pessoaobj", pessoa.get());
		modelAndView.addObject("pessoas", pessoaRepository.findAll(PageRequest.of(0, 5, Sort.by("nome"))));
		modelAndView.addObject("profissoes", profissaoRepository.findAll());
		return modelAndView;

	}

	@GetMapping("/excluirpessoa/{idpessoa}")
	public ModelAndView excluir(@PathVariable("idpessoa") Long idpessoa) {

		ModelAndView modelAndView = new ModelAndView("cadastro/cadpessoa");
		if (idpessoa > 0 && idpessoa != null) {
			pessoaRepository.deleteById(idpessoa);
			modelAndView.addObject("pessoas", pessoaRepository.findAll(PageRequest.of(0, 5, Sort.by("nome"))));
			modelAndView.addObject("pessoaobj", new Pessoa());
			modelAndView.addObject("profissoes", profissaoRepository.findAll());
		}
		return modelAndView;

	}

	@PostMapping("**/pesqnome")
	public ModelAndView pesquisar(@RequestParam("buscanome") String buscaNome,
			@RequestParam("pesquisasexo") String pesquisasexo,
			@PageableDefault(size = 5, sort = { "nome" }) Pageable pageable) {

		ModelAndView andView = new ModelAndView("cadastro/cadpessoa");

		Page<Pessoa> pessoas = null;

		if (pesquisasexo != null && !pesquisasexo.isEmpty()) {
			pessoas = pessoaRepository.buscaPessoaporPaginacaoSexo(buscaNome, pesquisasexo, pageable);
		} else if (buscaNome != null || !buscaNome.isEmpty()) {
			pessoas = pessoaRepository.buscaPessoaporPaginacao(buscaNome, pageable);
		} else {
			String msg = "Selecione o Sexo:";
			andView.addObject("msg", msg);
		}
		andView.addObject("pessoas", pessoas);
		andView.addObject("pessoaobj", new Pessoa());
		andView.addObject("profissoes", profissaoRepository.findAll());
		andView.addObject("buscanome", buscaNome);
		return andView;
	}

	@GetMapping("**/pesqnome")
	public void imprimePDF(@RequestParam("buscanome") String buscanome,
			@RequestParam("pesquisasexo") String pesquisasexo, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		List<Pessoa> pessoas = new ArrayList<>();
		if (buscanome != null && !buscanome.isEmpty() && pesquisasexo != null && !pesquisasexo.isEmpty()) {
			pessoas = pessoaRepository.findPessoaByNameSexo(buscanome, pesquisasexo);

		} else if (buscanome != null && !buscanome.isEmpty()) {
			pessoas = pessoaRepository.findPessoaByName(buscanome);
		} else if (pesquisasexo != null && !pesquisasexo.isEmpty()) {
			pessoas = pessoaRepository.findPessoaBySexo(pesquisasexo); // busca so por sexo
		} else {

			Iterable<Pessoa> iterable = pessoaRepository.findAll(PageRequest.of(0, 5, Sort.by("nome")));
			for (Pessoa pessoa : iterable) {
				pessoas.add(pessoa);

			}
		}

		/* Chame o serviço que fa a geração do relatorio */

		byte[] pdf = reportUtil.geraRelatorio(pessoas, "RelatorioPessoas", request.getServletContext());

		/* Tamnho da resposta para o navegador */
		response.setContentLength(pdf.length);

		/* Definir da resposta o tipo de arquivo */
		response.setContentType("application/octet-stream"); /* arquivos, pdfs, multimidia */

		/* Definir o cabeçalho da resposta */
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"", "relpessoas.pdf");
		response.setHeader(headerKey, headerValue);

		/* Finaliza a respota no navegador */
		response.getOutputStream().write(pdf);

	}

	@GetMapping("**/download/{idpessoa}")
	public void baixarArquivo(@PathVariable("idpessoa") Long idpessoa, HttpServletResponse response)
			throws IOException {
		/* Consulta o objeto pessoa no banco de dados */
		Pessoa pessoa = pessoaRepository.findById(idpessoa).get();

		if (pessoa.getFoto() != null) {
			/* Setar o tamanho da resposta */
			response.setContentLength(pessoa.getFoto().length);

			/* tipo da resposta */
			response.setContentType(pessoa.getTipoArquivoFoto()); // tippo do arquivo || pode ser generecia
																	// application/octet-stream

			/* Defini o cabeçãlho da respota */
			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"", pessoa.getNomeArquivoFoto());
			response.setHeader(headerKey, headerValue);

			/* finaliza a resposta passando o arquivo */
			response.getOutputStream().write(pessoa.getFoto());

		}

	}

	@GetMapping("/telefonesuser/{idp}")
	public ModelAndView telefones(@PathVariable("idp") Long idp) {

		Optional<Pessoa> pessoa = pessoaRepository.findById(idp);

		ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
		modelAndView.addObject("pessoaobj", pessoa.get());
		modelAndView.addObject("telefones", telefoneRepository.getTelefones(pessoa.get().getId()));
		return modelAndView;

	}

	@PostMapping("/addfonepessoa/{pessoaid}")
	public ModelAndView addFonePessoa(Telefone telefone, @PathVariable("pessoaid") Long pessoaid) {

		Pessoa pessoa = pessoaRepository.findById(pessoaid).get();
		if (telefone != null && telefone.getNumero().isEmpty() || telefone.getTipo().isEmpty()) {
			ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
			modelAndView.addObject("pessoaobj", pessoa);
			modelAndView.addObject("telefones", telefoneRepository.getTelefones(pessoaid));

			List<String> msg = new ArrayList<>();
			if (telefone.getNumero().isEmpty()) {
				msg.add("O Número deve ser informado");
			}
			if (telefone.getTipo().isEmpty()) {
				msg.add("O Tipo do telefone deve ser informado");
			}
			modelAndView.addObject("msg", msg);

			return modelAndView;
		}

		ModelAndView andView = new ModelAndView("cadastro/telefones");
		telefone.setPessoa(pessoa);
		telefoneRepository.save(telefone);
		andView.addObject("pessoaobj", pessoa);
		andView.addObject("telefones", telefoneRepository.getTelefones(pessoaid));
		return andView;

	}

	@GetMapping("/excluirtelefone/{telefoneid}")
	public ModelAndView deleteFonePessoa(@PathVariable("telefoneid") Long telefoneid) {
		ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
		if (telefoneid > 0 && telefoneid != null) {
			Pessoa pessoa = telefoneRepository.findById(telefoneid).get().getPessoa();
			telefoneRepository.deleteById(telefoneid);
			modelAndView.addObject("telefones", telefoneRepository.getTelefones(pessoa.getId()));
			modelAndView.addObject("pessoaobj", pessoa);
		}
		return modelAndView;
	}

	@GetMapping("/pessoaspag")
	public ModelAndView carregaPessoaPorPaginacao(@PageableDefault(size = 5) Pageable pageable, ModelAndView model,
			@RequestParam("buscanome") String buscanome) {
		Page<Pessoa> pagPessoa = pessoaRepository.buscaPessoaporPaginacao(buscanome, pageable);
		model.addObject("pessoas", pagPessoa);
		model.addObject("pessoaobj", new Pessoa());
		model.addObject("buscanome", buscanome);
		model.setViewName("cadastro/cadpessoa");

		return model;
	}

}
