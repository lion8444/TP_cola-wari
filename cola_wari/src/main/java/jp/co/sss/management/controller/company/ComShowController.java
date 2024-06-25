package jp.co.sss.management.controller.company;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.persistence.EntityManager;
import jp.co.sss.management.bean.CompanyBean;
import jp.co.sss.management.entity.Agent;
import jp.co.sss.management.entity.Company;
import jp.co.sss.management.repository.AgentRepository;
import jp.co.sss.management.repository.CompanyRepository;
import jp.co.sss.management.service.CompanyBeanTools;

@Controller
@RequestMapping("/company")
public class ComShowController {
	@Autowired
	CompanyRepository companyRepository;

	@Autowired
	AgentRepository agentRepository;

	@Autowired
	CompanyBeanTools companyBeanTools;

	@Autowired
	EntityManager entityManager;

	@RequestMapping(path = "list", method = { RequestMethod.GET, RequestMethod.POST })
	public String showCompanies(Model model) {

    	List<CompanyBean> companyBeans = companyBeanTools.copyEntityListToBeanList(companyRepository.findAll());
    	
    	model.addAttribute("companies", companyBeans);
        return "company/companies";
    }
    
    @GetMapping("detail/{id}")
    public String showCompanyDetail(@PathVariable int id, Model model) {
    	Company company = companyRepository.getReferenceById(id);
    	List<Agent> agentList = agentRepository.findByCompanyAndDeleteFlag(company, 0);
    	
    	model.addAttribute("company", company);
    	model.addAttribute("agent", agentList);
    	return "company/company";
    }
}
