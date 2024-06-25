package jp.co.sss.management.controller.agenda;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.sss.management.bean.UserBean;
import jp.co.sss.management.bean.UserCheckedBean;
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
import jp.co.sss.management.service.UserBeanTools;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
@RequestMapping("/agenda")
public class AgendaUpdateController {

	@Autowired
	UserBeanTools userBeanTools;
	
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
    	List<UserBean> userBeans = userBeanTools.copyEntityListToBeanList(userRepository.findByStatus(0));

		List<UserCheckedBean> userCheckedBeans = new ArrayList<>();

		for (UserBean userBean : userBeans) {
			UserCheckedBean userCheckedBean = new UserCheckedBean();
			userCheckedBean.setUserBean(userBean);
            for (AgendaEntry agendaEntry : agendaEntries) {
                if (userBean.getUserId().equals(agendaEntry.getUser().getUserId())) {
                    userCheckedBean.setChecked(1);
                    break;
                }
            }
			userCheckedBeans.add(userCheckedBean);
		}

		for (UserCheckedBean userCheckedBean : userCheckedBeans) {
			log.debug("usercheckedBean check : userId : {}, userName : {}, checked : {}",userCheckedBean.getUserBean().getUserId(), userCheckedBean.getUserBean().getUserName(), userCheckedBean.getChecked());
		}

    	model.addAttribute("agenda", agenda);
    	model.addAttribute("agendaName", agendaEntries.get(0).getAgent().getAgentName());
    	model.addAttribute("users", userCheckedBeans);
    	model.addAttribute("agents", agentRepository.findByDeleteFlag(0));
    	model.addAttribute("comps", companyRepository.findAll());
        return "agenda/update_input";
    }
    
    @PostMapping("/update/check")
    public String agendaUpdateCheck(Model model,AgendaForm agendaForm, @RequestParam(required = false) List<Integer> userId,CompanyForm comFomr,AgentForm agentForm) {
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
