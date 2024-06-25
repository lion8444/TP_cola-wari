package jp.co.sss.management.controller.agenda;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.sss.management.entity.Agenda;
import jp.co.sss.management.entity.AgendaEntry;
import jp.co.sss.management.entity.Agent;
import jp.co.sss.management.entity.Company;
import jp.co.sss.management.entity.User;
import jp.co.sss.management.form.AgendaForm;
import jp.co.sss.management.form.AgentForm;
import jp.co.sss.management.form.CompanyForm;
import jp.co.sss.management.repository.AgendaEntryRepository;
import jp.co.sss.management.repository.AgendaRepository;
import jp.co.sss.management.repository.AgentRepository;
import jp.co.sss.management.repository.CompanyRepository;
import jp.co.sss.management.repository.UserRepository;

@Controller
@RequestMapping("/agenda")
public class AgendaUpdateController {
	
	@Autowired
	AgendaRepository agendaRepository;
    
	@Autowired
	AgentRepository agentRepository;
	
	@Autowired
	CompanyRepository companyRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AgendaEntryRepository agendaEntryRepository;
    
    @GetMapping("/update")
    public String agendaUpdate(Model model,Integer agendaId) {
    	Agenda agenda = agendaRepository.getReferenceById(agendaId);
    	List<AgendaEntry> agendaEntries = agenda.getAgendaEntries();
    	
    	model.addAttribute("agenda", agenda);
    	model.addAttribute("agendaName", agendaEntries.get(0).getAgent().getAgentName());
    	model.addAttribute("users", userRepository.findAll());
    	model.addAttribute("agents", agentRepository.findAll());
    	model.addAttribute("comps", companyRepository.findAll());
        return "agenda/update_input";
    }
    
    @PostMapping("/update/check")
    public String agendaUpdateCheck(Model model,AgendaForm agendaForm,@RequestParam(required = false) List<Integer> userId,CompanyForm comFomr,AgentForm agentForm) {
    	User user;
    	Company company = new Company();
    	company.setComId(comFomr.getComId());
    	Agent agent = agentRepository.findByAgentIdAndCompanyAndDeleteFlag(agentForm.getAgentId(), company, 0);
    		if(agent == null) {
    			model.addAttribute("message", "該当する企業担当者が存在しません。");
    			return "redirect:/update";
    		}
    	
    		List<User> userList = new ArrayList<>();
        	for(Integer userid : userId) {
        		user = userRepository.getReferenceById(userid);
        		userList.add(user);
        	}
        	
        	agent = agentRepository.getReferenceById(agentForm.getAgentId());
        	
        	
        	Company com = companyRepository.getReferenceById(comFomr.getComId());
        	
        	model.addAttribute("com",com);
        	model.addAttribute("agendaForm",agendaForm);
        	model.addAttribute("agent",agent);
        	model.addAttribute("userList",userList);
        	
    	
        return "agenda/update_check";
    }
    @Transactional
    @PostMapping("/update/complete")
    public String agendaUpdateComplete(Model model, AgendaForm agendaform,Integer comId,
    		@RequestParam("userId") List<Integer> userIds,Integer agentId) {  	
    	System.out.println("hello");
    	Agenda agenda = new Agenda();
    	agenda.setAgendaId(agendaform.getAgendaId());
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
    	
		agendaEntryRepository.deleteAllByAgenda(agenda);

    	Agent agent = new Agent();
    	
    	List<AgendaEntry> agendaEntries = new ArrayList<>();
    	for(Integer userId:userIds) {
    		User user = new User();
    		AgendaEntry agendaEntry = new AgendaEntry();

        	agent.setAgentId(agentId);

        	agendaEntry.setAgent(agent);
        	agendaEntry.setAgenda(agenda);

    		user.setUserId(userId);
    		agendaEntry.setUser(user);
    		
    		agendaEntries.add(agendaEntry);
    	}   	
    	
    	for(AgendaEntry agendaE:agendaEntries) {
    		agendaEntryRepository.save(agendaE);
    	}
        return "agenda/update_complete";
    }

	@PostMapping("/close")
	public String getMethodName(Integer agendaId) {
		Agenda agenda = agendaRepository.getReferenceById(agendaId);
		agenda.setStatus(1);
		agendaRepository.save(agenda);

		return "redirect:/agenda/list";
	}
	
}
