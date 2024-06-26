package jp.co.sss.management.controller.company;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.ServletContext;
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
import jp.co.sss.management.service.BeanTools;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/company")
public class ComRegistController {
	@Autowired
	ServletContext context;

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
	BeanTools beanTools;

	/**
	 * 入力画面　表示処理(GET)
	 * 
	 * @param model Viewとの値受渡し
	 * @return "company/regist_input" 入力画面　表示
	 */
	@GetMapping("/regist/input")
	public String registInput(Model model) {
		CompanyForm companyForm = new CompanyForm();
		AgentForm agentForm = new AgentForm();

		List<ComCategory> categories = comCategoryRepository.findAll();

		BindingResult result1 = (BindingResult) session.getAttribute("result1");
		BindingResult result2 = (BindingResult) session.getAttribute("result2");

		if (result1 != null) {
			//セッションにエラー情報がある場合、エラー情報を画面表示設定
			companyForm = (CompanyForm) session.getAttribute("companyForm");
		}
		if (result2 != null) {
			agentForm = (AgentForm) session.getAttribute("agentForm");
		}

		model.addAttribute("companyForm", companyForm);
		model.addAttribute("agentForm", agentForm);
		model.addAttribute("categories", categories);

		return "company/regist_input";
	}

	/**
	 * 登録入力確認処理
	 *
	 * @param form 入力フォーム
	 * @param result 入力値チェックの結果
	 * @return 
	 * 	入力値エラーあり："redirect:regist/input" 入力録画面　表示処理
	 * 	入力値エラーなし："redirect:regist/check" 登録確認画面　表示処理
	 */
	@PostMapping("/regist/input")
	public String registInputCheck(@Valid CompanyForm companyForm, BindingResult result1, AgentForm agentForm,
			BindingResult result2) {
		session.removeAttribute("result1");
		session.removeAttribute("result2");
		//エラーがあるか否か
		boolean error = false;
		// 入力値にエラーがあった場合、入力画面に戻る
		if (result1.hasErrors()) {
			session.setAttribute("result1", result1);
			error = true;
		}
		if (result2.hasErrors()) {
			session.setAttribute("result1", result2);
			error = true;
		}

		if (error == true) {
			//変更入力画面　表示処理
			session.setAttribute("companyForm", companyForm);
			session.setAttribute("agentForm", agentForm);
			return "redirect:/company/regist/input";
		}

		companyForm.setCateName(comCategoryRepository.getReferenceById(companyForm.getCateId()).getCateName());

		log.debug("ComRegistController.registInputCheck CompanyForm : {}, AgentForm : {}", companyForm.toString(),
				agentForm.toString());

		// 入力フォームをセッションに保持
		session.setAttribute("companyForm", companyForm);
		session.setAttribute("agentForm", agentForm);

		// 登録確認画面　表示処理
		return "company/regist_check";
	}

	/**
	 * 登録確認画面　表示処理
	 *
	 * @param model Viewとの値受渡し
	 * @return "admin/item/regist_check" 登録確認画面表示
	 */
	@GetMapping("regist/check")
	public String registCheck(Model model) {
		//セッションから入力フォーム情報取得
		CompanyForm companyForm = (CompanyForm) session.getAttribute("companyForm");
		AgentForm agentForm = (AgentForm) session.getAttribute("agentForm");

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
		model.addAttribute("agentForms", agentForm.getAgentForms());

		//System.out.println(companyForm.toString());
		//System.out.println(agentForm.toString());

		//登録確認画面　表示処理
		return "company/regist_check";

	}

	/**
	 * 情報登録処理
	 *
	 * @return "redirect:/regist/complete" 登録完了画面　表示処理
	 */
	@PostMapping("regist/check")
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

		for (AgentForm agentForm2 : agentForm.getAgentForms()) {
			Agent agentEntity = new Agent();
			BeanUtils.copyProperties(agentForm2, agentEntity);
			if (agentForm2.getAgentId() != null) {
				agentEntity.setAgentId(agentForm2.getAgentId());
			}
			agentEntity.setCompany(companyEntity);
			agentRepository.save(agentEntity);
		}

		//セッション情報の削除
		session.removeAttribute("companyForm");
		session.removeAttribute("agentForm");

		//登録完了画面　表示処理
		//二重送信対策のためリダイレクトを行う
		return "redirect:/company/regist/complete";
	}

	/**
	 * 登録完了画面　表示処理
	 *
	 * @return "admin/item/regist_complete" 登録完了画面　表示
	 */
	@GetMapping("regist/complete")
	public String registCompleteFinish() {

		//登録完了画面　表示
		return "company/regist_complete";
	}
}
