package jp.co.sss.management.controller.agenda;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jp.co.sss.management.entity.Agenda;
import jp.co.sss.management.entity.AgendaEntry;
import jp.co.sss.management.entity.Agent;
import jp.co.sss.management.entity.Company;
import jp.co.sss.management.entity.User;
import jp.co.sss.management.form.AgendaForm;
import jp.co.sss.management.repository.AgendaEntryRepository;
import jp.co.sss.management.repository.AgendaRepository;
import jp.co.sss.management.repository.AgentRepository;
import jp.co.sss.management.repository.CompanyRepository;
import jp.co.sss.management.repository.UserRepository;

@Controller
@RequestMapping("/agenda")
public class AgendaResistController {

	@Autowired
	AgendaRepository agendaRepository;

	@Autowired
	AgentRepository agentRepository;

	@Autowired
	CompanyRepository companyRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	AgendaEntryRepository agentEntryRepository;

	@Autowired
	HttpSession session;

	@GetMapping("/input")
	public String registAgenda(Model model, AgendaForm agendaform) {
		model.addAttribute("users", userRepository.findByStatus(0));
		model.addAttribute("agents", agentRepository.findByDeleteFlag(0));
		model.addAttribute("comps", companyRepository.findAll());

		BindingResult result = (BindingResult) session.getAttribute("result");
		if (result != null) {
			model.addAttribute("org.springframework.validation.BindingResult.agendaForm", result);
			session.removeAttribute("result");
		}
		model.addAttribute("agendaForm", agendaform);
		return "agenda/regist_input";
	}

	@PostMapping("/input/check")
	public String registAgendaCheck(@Valid @ModelAttribute AgendaForm agendaForm, BindingResult result,
			@RequestParam("userId") List<Integer> userIds, Integer agentId, Integer comId, Model model) {
		if (result.hasErrors()) {
			session.setAttribute("result", result);

			// 登録入力画面 表示処理
			return "redirect:/agenda/input";

		}

		User user;
		Company company = new Company();
		company.setComId(comId);
		Agent agent = agentRepository.findByAgentIdAndCompanyAndDeleteFlag(agentId, company, 0);
		if (agent == null) {
			model.addAttribute("message", "該当する企業担当者が存在しません。");
			return "redirect:/agenda/input";
		}

		List<User> userList = new ArrayList<>();
		for (Integer userId : userIds) {
			user = userRepository.getReferenceById(userId);
			userList.add(user);
		}

		agent = agentRepository.getReferenceById(agentId);

		Company com = companyRepository.getReferenceById(comId);

		model.addAttribute("com", com);
		model.addAttribute("agendaForm", agendaForm);
		model.addAttribute("agent", agent);
		model.addAttribute("userList", userList);

		return "agenda/regist_check";
	}

	@PostMapping("input/complete")
	public String registAgendaComplete(Model model, AgendaForm agendaform, Integer comId,
			@RequestParam("userId") List<Integer> userIds, Integer agentId) {
		Agenda agenda = new Agenda();
		agenda.setTitle(agendaform.getTitle());
		agenda.setDescription(agendaform.getDescription());
		agenda.setStatus(agendaform.getStatus());
		agenda.setReport(agendaform.getReport());
		agenda.setStartDate(agendaform.getStartDate());
		agenda.setEndDate(agendaform.getEndDate());
		Company com = new Company();
		com.setComId(comId);
		agenda.setCompany(com);
		agendaRepository.save(agenda);

		Agent agent = new Agent();

		List<AgendaEntry> agendaEntries = new ArrayList<>();
		for (Integer userId : userIds) {
			User user = new User();
			AgendaEntry agendaEntry = new AgendaEntry();
			agent.setAgentId(agentId);
			agendaEntry.setAgent(agent);

			agenda = agendaRepository.findByMaxAgendaId();
			agendaEntry.setAgenda(agenda);

			user.setUserId(userId);
			agendaEntry.setUser(user);

			agendaEntries.add(agendaEntry);
		}

		for (AgendaEntry agendaE : agendaEntries) {
			agentEntryRepository.save(agendaE);
		}

		return "agenda/regist_complete";
	}

}
