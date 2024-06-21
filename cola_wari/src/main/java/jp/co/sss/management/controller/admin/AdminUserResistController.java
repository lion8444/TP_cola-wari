package jp.co.sss.management.controller.admin;

import org.springframework.beans.BeanUtils;
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
import jp.co.sss.management.entity.User;
import jp.co.sss.management.form.UserForm;
import jp.co.sss.management.repository.UserRepository;

@Controller
public class AdminUserResistController {
	/**
	 * セッション情報
	 */
	@Autowired
	HttpSession session;

	/**
	 * 会員情報　リポジトリ
	 */
	@Autowired
	UserRepository userRepository;

	/**
	 * 登録画面表示コントローラ
	 */
	@RequestMapping(path = "/mypage/employee/input", method = { RequestMethod.GET, RequestMethod.POST })
	public String showUserResist(Model model) {
		//ログインされていない場合と管理者権限を持っていない場合はログイン画面へ
		UserBean userBean = (UserBean) session.getAttribute("user");
		if (userBean == null || userBean.getAuth() != 1) {
			// 対象が無い場合、ログイン画面へ
			return "redirect:/";
		}

		// セッションからエラー情報を取得
		BindingResult result = (BindingResult) session.getAttribute("result");
		if (result != null) {
			// エラー情報をモデルに追加し、セッションから削除
			model.addAttribute("org.springframework.validation.BindingResult.userForm", result);
			session.removeAttribute("result");
		}

		// セッションからユーザー登録情報を取得
		UserForm userForm = (UserForm) session.getAttribute("userForm");
		if (userForm == null) {
			userForm = new UserForm();
			//初期パスワード追加
			userForm.setPassword("Passw0rd");
			// 入力フォーム情報を画面表示設定
			model.addAttribute("userForm", userForm);

			// セッションにユーザー登録情報を設定
			session.setAttribute("userForm", userForm);

			return "mypage/admin/resist_input";
		}

		// 入力フォーム情報を画面表示設定
		model.addAttribute("userForm", userForm);

		return "mypage/admin/resist_input";
	}

	/**
	 * 入力後の入力チェックなど
	 * @param userForm
	 * @param result
	 * @return
	 */
	@RequestMapping(path = "/mypage/employee/input/check_r", method = { RequestMethod.GET, RequestMethod.POST })
	public String inputResistUser_r(@Valid @ModelAttribute UserForm userForm, BindingResult result) {

		// 入力値にエラーがあった場合、入力画面に戻る
		if (result.hasErrors()) {

			session.setAttribute("result", result);

			//変更入力画面　表示処理
			return "redirect:/mypage/employee/input";
		}

		//ユーザビーンを作成。
		UserBean userBean = new UserBean();
		//フォームから入力された値をビーンに追加
		BeanUtils.copyProperties(userForm, userBean);

		//入力された内容をセッションスコープに保存
		session.setAttribute("userBean", userBean);

		return "redirect:/mypage/employee/input/check";
	}

	/**
	 * 登録確認画面表示
	 * @return
	 */
	@GetMapping("/mypage/employee/input/check")
	public String inputResistUser() {
		//ログインされていない場合と管理者権限を持っていない場合はログイン画面へ
		UserBean userBean = (UserBean) session.getAttribute("user");
		if (userBean == null || userBean.getAuth() != 1) {
			// 対象が無い場合、ログイン画面へ
			return "redirect:/";
		}

		return "mypage/admin/resist_check";
	}

	/**
	 * 確認後データベース更新
	 * @param userForm
	 * @param result
	 * @return
	 */
	@RequestMapping(path = "/mypage/employee/input/complete_r", method = { RequestMethod.GET, RequestMethod.POST })
	public String inputResistUserCheck_r() {
		//セッションスコープの内容を取り出す。
		UserBean userBean = (UserBean) session.getAttribute("userBean");

		//エンティティのオブジェクト作成
		User user = new User();

		//id以外をエンティティにコピー
		BeanUtils.copyProperties(userBean, user, "userId");

		//データベース更新
		userRepository.save(user);

		return "redirect:/mypage/employee/input/complete";
	}

	/**
	 * 登録確認画面表示
	 * @return
	 */
	@GetMapping("/mypage/employee/input/complete")
	public String inputResistUserComplete() {
		//ログインされていない場合と管理者権限を持っていない場合はログイン画面へ
		UserBean userBean = (UserBean) session.getAttribute("user");
		if (userBean == null || userBean.getAuth() != 1) {
			// 対象が無い場合、ログイン画面へ
			return "redirect:/";
		}

		//登録用のセッションスコープ削除
		session.removeAttribute("userBean");

		return "mypage/admin/resist_complete";
	}

	//消すと動かない。なんでかは知らん。
	@ModelAttribute("userForm")
	public UserForm getUserForm() {
		return new UserForm();
	}
}
