package jp.co.sss.management.controller.company;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpSession;
import jp.co.sss.management.entity.Agent;
import jp.co.sss.management.entity.ComCategory;
import jp.co.sss.management.entity.Company;
import jp.co.sss.management.form.AgentForm;
import jp.co.sss.management.form.CompanyForm;
import jp.co.sss.management.repository.AgentRepository;
import jp.co.sss.management.repository.ComCategoryRepository;
import jp.co.sss.management.repository.CompanyRepository;
import jp.co.sss.management.service.CompanyBeanTools;

@Controller
@RequestMapping("/company")
public class ComUpdateController {
	/**
	 * 企業情報　リポジトリ
	 */
	@Autowired
	public CompanyRepository companyRepository;

	/**
	 * 担当者情報　リポジトリ
	 */
	@Autowired
	public AgentRepository agentRepository;
	
	/**
	 * 企業カテゴリ情報　リポジトリ
	 */
	@Autowired
	public ComCategoryRepository comCategoryRepository;

	/**
	 * セッション
	 */
	@Autowired
	HttpSession session;

	@Autowired
	CompanyBeanTools companyBeanTools;

	@Autowired
	EntityManager entityManager;

	@GetMapping("/update/input/{id}")
	public String updateInput(@PathVariable int id, Model model) {
		CompanyForm companyForm = new CompanyForm();
		AgentForm agentForm = new AgentForm();
		List<ComCategory> categories = comCategoryRepository.findAll();

		
		Company companyList = companyRepository.getReferenceById(id);
		List<Agent> agentList = agentRepository.findByCompany_ComId(id);
		
		model.addAttribute("companyForm", companyForm);
		model.addAttribute("agentForm", agentForm);
		model.addAttribute("categories", categories);
		
		companyForm = (CompanyForm) session.getAttribute("companyForm");
		agentForm = (AgentForm) session.getAttribute("agentForm");

		model.addAttribute("company", companyList);
		model.addAttribute("agent", agentList);
		return "company/update_input";
	}
}
