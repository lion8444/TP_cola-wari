package jp.co.sss.management.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jp.co.sss.management.bean.UserBean;
import jp.co.sss.management.entity.ComCategory;
import jp.co.sss.management.form.CategoryForm;
import jp.co.sss.management.repository.CategoryRepository;

@Controller
public class CategoryInput {
	/**
	 * セッション情報
	 */
	@Autowired
	HttpSession session;

	/**
	 * 会員情報　リポジトリ
	 */
	@Autowired
	CategoryRepository categoryRepository;

	/**
	 * カテゴリ一覧画面表示
	 */
	@RequestMapping(path = "/admin/category", method = { RequestMethod.GET, RequestMethod.POST })
	public String showCategory(Model model) {
		//ログインされていない場合と管理者権限を持っていない場合はログイン画面へ
		UserBean userBean = (UserBean) session.getAttribute("user");
		if (userBean == null || userBean.getAuth() == 0) {
			// 対象が無い場合、ログイン画面へ
			return "redirect:/";
		}

		//カテゴリー全件検索
		List<ComCategory> cc = categoryRepository.findAllByOrderByCateIdAsc();
		model.addAttribute("category", cc);

		//入力用フォーム
		ComCategory cate = new ComCategory();
		model.addAttribute("categoryForm", cate);

		// セッションからエラー情報を取得
		BindingResult result = (BindingResult) session.getAttribute("result");
		if (result != null) {
			// エラー情報をモデルに追加し、セッションから削除
			model.addAttribute("org.springframework.validation.BindingResult.categoryForm", result);
			session.removeAttribute("result");
		}

		return "mypage/category/resist";
	}

	/**
	 * カテゴリDB更新
	 */
	@RequestMapping(path = "/admin/category/complete_r", method = { RequestMethod.GET, RequestMethod.POST })
	public String resistCategory_r(@Valid @ModelAttribute CategoryForm categoryForm, BindingResult result) {
		// 入力値にエラーがあった場合、入力画面に戻る
		if (result.hasErrors()) {

			session.setAttribute("result", result);

			//変更入力画面　表示処理
			return "redirect:/admin/category";
		}

		//フォームをコピー
		ComCategory cc = new ComCategory();
		cc.setCateName(categoryForm.getCateName());

		//データベース更新
		categoryRepository.save(cc);
		System.out.println("DB更新");
		return "redirect:/admin/category/complete";
	}

	/**
	 * 完了画面表示
	 */
	@GetMapping("/admin/category/complete")
	public String resistCategory() {
		//ログインされていない場合と管理者権限を持っていない場合はログイン画面へ
		UserBean userBean = (UserBean) session.getAttribute("user");
		if (userBean == null || userBean.getAuth() == 0) {
			// 対象が無い場合、ログイン画面へ
			return "redirect:/";
		}

		return "mypage/category/resist_complete";
	}
}
