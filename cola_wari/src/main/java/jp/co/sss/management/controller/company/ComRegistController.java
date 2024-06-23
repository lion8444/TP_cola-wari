package jp.co.sss.management.controller.company;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;
import jp.co.sss.management.bean.ComCategoryBean;
import jp.co.sss.management.entity.Agent;
import jp.co.sss.management.entity.ComCategory;
import jp.co.sss.management.entity.Company;

import jp.co.sss.management.form.AgentForm;
import jp.co.sss.management.form.CompanyForm;

import jp.co.sss.management.repository.AgentRepository;
import jp.co.sss.management.repository.CompanyRepository;
import jp.co.sss.management.service.BeanTools;

@Controller
@RequestMapping("/company")
public class ComRegistController {
	@Autowired
	ServletContext context;

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
	 * セッション
	 */
	@Autowired
	HttpSession session;

	@Autowired
	BeanTools beanTools;

	/**
	 * 入力画面　表示処理(GET)
	 * 
	 * @param model Viewとの値受渡し
	 * @return "company/regist_input" 入力画面　表示
	 */
	@GetMapping("regist/input")
	public String registInput(Model model) {
		CompanyForm companyForm = new CompanyForm();
		AgentForm agentForm = new AgentForm();

		// ComCategoryBean comCategoryBean = 

		model.addAttribute("companyForm", companyForm);
		model.addAttribute("agentForm", agentForm);
		
		return "company/regist_input";
	}

	/**
	 * 登録確認画面　表示処理
	 *
	 * @param model Viewとの値受渡し
	 * @return "company/regist_check" 登録確認画面表示
	 */
	@GetMapping("regist/check")
	public String registInputCheck(Model companyModel, Model agentModel) {
		CompanyForm companyForm = (CompanyForm) session.getAttribute("companyForm");
		AgentForm agentForm = (AgentForm) session.getAttribute("agentForm");

		companyModel.addAttribute("companyForm", companyForm);
		agentModel.addAttribute("agentForm", agentForm);
		return "company/regist_check";
	}

	/**
	 * 情報登録処理
	 *
	 * @return "redirect:/regist/complete" 登録完了画面　表示処理
	 */
	@PostMapping("regist/complete")
	public String registComplete() {
		ComCategory category = new ComCategory();

		//セッション保持情報から入力値再取得
		CompanyForm companyForm = (CompanyForm) session.getAttribute("companyForm");
		AgentForm agentForm = (AgentForm) session.getAttribute("agentForm");

		// Formクラス内の各フィールドの値をエンティティにコピー
		Company companyEntity = new Company();
		Agent agentEntity = new Agent();

		BeanUtils.copyProperties(companyForm, companyEntity);
		BeanUtils.copyProperties(agentForm, agentEntity);

		if (companyForm.getComId() != null) {
			companyEntity.setComId(companyForm.getComId());
		}

		if (agentForm.getAgentId() != null) {
			agentEntity.setAgentId(agentForm.getAgentId());
		}
		category.setCateId(companyForm.getCateId());
		companyEntity.setComCagetory(category);

		// 商品情報をDBに保存
		companyRepository.save(companyEntity);
		agentRepository.save(agentEntity);

		//セッション情報の削除
		session.removeAttribute("companyForm");
		session.removeAttribute("agentForm");

		//登録完了画面　表示処理
		//二重送信対策のためリダイレクトを行う
		return "redirect:/regist/complete";
	}
}
