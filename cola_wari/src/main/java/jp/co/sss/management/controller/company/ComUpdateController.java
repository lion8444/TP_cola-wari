package jp.co.sss.management.controller.company;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jp.co.sss.management.entity.Agent;
import jp.co.sss.management.entity.ComCategory;
import jp.co.sss.management.entity.Company;
import jp.co.sss.management.form.AgentForm;
import jp.co.sss.management.form.CompanyForm;
import jp.co.sss.management.repository.AgentRepository;
import jp.co.sss.management.repository.ComCategoryRepository;
import jp.co.sss.management.repository.CompanyRepository;
import jp.co.sss.management.service.CompanyBeanTools;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/company")
public class ComUpdateController {
	/**
	 * 企業情報　リポジトリ
	 */
	@Autowired
	CompanyRepository companyRepository;

	/**
	 * 担当者情報　リポジトリ
	 */
	@Autowired
	AgentRepository agentRepository;
	
	/**
	 * 企業カテゴリ情報　リポジトリ
	 */
	@Autowired
	ComCategoryRepository comCategoryRepository;

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
		List<ComCategory> categories = comCategoryRepository.findAll();

		
		Company company = companyRepository.getReferenceById(id);
		List<Agent> agentList = agentRepository.findByCompany_ComId(id);
		List<AgentForm> agentForms = new ArrayList<>();

		for (Agent agent : agentList) {
			AgentForm form = new AgentForm();
			BeanUtils.copyProperties(agent, form);

			form.setComId(agent.getCompany().getComId());

			agentForms.add(form);
		}

		AgentForm agentForm = new AgentForm();
		agentForm.setAgentForms(agentForms);

		BeanUtils.copyProperties(company, companyForm);

		companyForm.setCateId(company.getComCagetory().getCateId());
		companyForm.setCateName(company.getComCagetory().getCateName());
		
		model.addAttribute("companyForm", companyForm);
		model.addAttribute("agentForm", agentForm);
		model.addAttribute("categories", categories);
		
		companyForm = (CompanyForm) session.getAttribute("companyForm");
		agentForm = (AgentForm) session.getAttribute("agentForm");

		return "company/update_input";
	}

	@PostMapping("update/input")
	public String registInputCheck(@Valid @ModelAttribute CompanyForm companyForm, AgentForm agentForm) {

		log.debug("ComRegistController.registInputCheck CompanyForm : {}, AgentForm : {}", companyForm.toString(), agentForm.toString());
		companyForm.setCateName(comCategoryRepository.getReferenceById(companyForm.getCateId()).getCateName());

		log.debug("companyForm cateName check : {}", companyForm.getCateName());
		// 入力フォームをセッションに保持
		session.setAttribute("companyForm", companyForm);
		session.setAttribute("agentForm", agentForm);
		
		// 登録確認画面　表示処理
		return "redirect:/company/update/check";
	}
	
	/**
	 * 登録確認画面　表示処理
	 *
	 * @param model Viewとの値受渡し
	 * @return "admin/item/regist_check" 登録確認画面表示
	 */
	@GetMapping("update/check")
	public String registCheck(Model model) {
		//セッションから入力フォーム情報取得
		CompanyForm companyForm = (CompanyForm) session.getAttribute("companyForm");
		AgentForm agentForm = (AgentForm) session.getAttribute("agentForm");

		log.debug("companyForm cateName check : {}, {}", companyForm.getCateName(), agentForm.toString());
		
		/**
		if (companyForm == null) {
			// セッション情報がない場合、エラー
			return "redirect:/syserror";
		}
		
		if (agentForm == null) {
			// セッション情報がない場合、エラー
			return "redirect:/syserror";
		}
		*/
		//入力フォーム情報をスコープへ設定
		model.addAttribute("companyForm", companyForm);
		model.addAttribute("agentForm", agentForm);
		
		//System.out.println(companyForm.toString());
		//System.out.println(agentForm.toString());

		//登録確認画面　表示処理
		return "company/update_check";

	}

	/**
	 * 情報登録処理
	 *
	 * @return "redirect:/regist/complete" 登録完了画面　表示処理
	 */
	@Transactional
	@PostMapping("update/check")
	public String registComplete() {
		

		//セッション保持情報から入力値再取得
		CompanyForm companyForm = (CompanyForm) session.getAttribute("companyForm");
		AgentForm agentForm = (AgentForm) session.getAttribute("agentForm");

		// Formクラス内の各フィールドの値をエンティティにコピー
		Company companyEntity = new Company();

		BeanUtils.copyProperties(companyForm, companyEntity);
		

		if (companyForm.getComId() != null) {
			companyEntity.setComId(companyForm.getComId());
		}

		companyEntity.setUpdateDate(LocalDate.now());
		ComCategory category = comCategoryRepository.getReferenceById(companyForm.getCateId());
		companyEntity.setComCagetory(category);

		// 商品情報をDBに保存
		companyRepository.save(companyEntity);

		agentRepository.deleteAllByCompany(companyEntity);
	
		List<Agent> temps = agentRepository.findByCompany_ComId(companyEntity.getComId());
		if (!temps.isEmpty()) {
			log.debug("DeleteAll ERROR!!!!!!!!!!!!!!!!!!!!!!!");
		}

		for (AgentForm agentForm2 : agentForm.getAgentForms()) {
			Agent agentEntity = new Agent();
			BeanUtils.copyProperties(agentForm2, agentEntity);
			if (agentForm2.getAgentId() != null) {
				agentEntity.setAgentId(agentForm2.getAgentId());
			}
			agentEntity.setCompany(companyEntity);
			agentRepository.save(agentEntity);	
		}

		session.setAttribute("comId", companyEntity.getComId());
		//セッション情報の削除
		session.removeAttribute("companyForm");
		session.removeAttribute("agentForm");

		//登録完了画面　表示処理
		//二重送信対策のためリダイレクトを行う
		return "redirect:/company/update/complete";
	}
	
	/**
	 * 登録完了画面　表示処理
	 *
	 * @return "admin/item/regist_complete" 登録完了画面　表示
	 */
	@GetMapping("update/complete")
	public String registCompleteFinish(Model model) {
		model.addAttribute("comId", (Integer) session.getAttribute("comId"));

		session.removeAttribute("comId");
		//登録完了画面　表示
		return "company/update_complete";
	}
}
