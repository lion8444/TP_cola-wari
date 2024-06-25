package jp.co.sss.management.controller.agenda;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sss.management.entity.Agenda;
import jp.co.sss.management.entity.AgendaEntry;
import jp.co.sss.management.repository.AgendaRepository;
import jp.co.sss.management.repository.AgentRepository;
import jp.co.sss.management.repository.CompanyRepository;


@Controller
@RequestMapping("/agenda")
public class AgendaShowController {
	
	@Autowired
	AgendaRepository agendaRepository;
	
	@Autowired
	CompanyRepository companyRepository;
	
	@Autowired
	AgentRepository agentRepository;
    
	/**
	 * 
	 * @param model
	 * @return
	 */
    @RequestMapping("/list")
    public String showAgendaList(Model model) {
    	List<Agenda> agendas = agendaRepository.findByStatus(0);
    	model.addAttribute("agendas", agendas);
    	
        return "agenda/list";
    }
    
    @RequestMapping("/detail/{agendaId}")
    public String showAgendaDetail(@PathVariable Integer agendaId, Model model) {
    	Agenda agenda = agendaRepository.getReferenceById(agendaId);
    	List<AgendaEntry> agendaEntries = agenda.getAgendaEntries();
    	
    	model.addAttribute("agenda", agenda);
    	model.addAttribute("agendaName", agendaEntries.get(0).getAgent().getAgentName());
        return "agenda/detail";
    }
    
    
    
}
