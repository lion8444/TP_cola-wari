package jp.co.sss.management.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jp.co.sss.management.bean.UserBean;
import jp.co.sss.management.entity.User;
import jp.co.sss.management.form.UserForm;
import jp.co.sss.management.form.UserUpdateForm;
import jp.co.sss.management.repository.UserRepository;

@Controller
public class AdminUserUpdateController {
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
	@RequestMapping(path = "/admin/employee/update_r", method = { RequestMethod.GET, RequestMethod.POST })
	public String showUserUpdate_r(@RequestParam(value = "selectedUser", required = false) String selectedUserId) {
		//メッセージ表示用(変更削除する人を選択してください。)
		Integer select = 0;
		if (selectedUserId == null) {
			//部員登録・変更・削除用のセッションスコープを初期化
			session.removeAttribute("userUpdateForm");
			//メッセージ表示用(変更削除する人を選択してください。)
			select = 1;
			session.setAttribute("select", select);
			// selectedUserId が null の場合、/adminへ遷移
			return "redirect:/admin";
		}

		//セッションスコープより入力情報を取り出す
		UserUpdateForm userForm = (UserUpdateForm) session.getAttribute("userUpdateForm");
		//userFormに何も入っていない場合、初めての遷移と判断
		if (userForm == null) {
			//チェックぼくっすにチェックを入れられたユーザIdで検索し、DBから持ってくる。
			int userId = Integer.parseInt(selectedUserId);
			User userUpdate = userRepository.findByUserIdAndStatus(userId, 0);
			//変更前の情報をセッションスコープに保存
			session.setAttribute("userUpdate", userUpdate);

			// 初期表示用フォーム情報の生成
			userForm = new UserUpdateForm();

			userForm.setUserName(userUpdate.getUserName());
			userForm.setTeam(userUpdate.getTeam());
			userForm.setEmail(userUpdate.getEmail());
			userForm.setTel(userUpdate.getTel());
			userForm.setPosition(userUpdate.getPosition());
			userForm.setAuth(userUpdate.getAuth());
			userForm.setUserId(userId);

			//			//userFormにコピー
			//			BeanUtils.copyProperties(userUpdate, userForm);

			//変更入力フォームをセッションに保持
			session.setAttribute("userUpdateForm", userForm);

			return "redirect:/admin/employee/update";
		}

		//変更入力フォームをセッションに保持
		session.setAttribute("userUpdateForm", userForm);

		return "redirect:/admin/employee/update";
	}

	@RequestMapping(path = "/admin/employee/update", method = RequestMethod.GET)
	public String showUserUpdate(Model model) {
		//ログインされていない場合と管理者権限を持っていない場合はログイン画面へ
		UserBean userBean = (UserBean) session.getAttribute("user");
		if (userBean == null || userBean.getAuth() != 1) {
			// 対象が無い場合、ログイン画面へ
			return "redirect:/";
		}

		//セッションから入力フォーム取得
		UserUpdateForm userUpdateForm = (UserUpdateForm) session.getAttribute("userUpdateForm");

		// 入力フォーム情報を画面表示設定
		model.addAttribute("userUpdateForm", userUpdateForm);

		BindingResult result = (BindingResult) session.getAttribute("result");
		if (result != null) {
			//セッションにエラー情報がある場合、エラー情報を画面表示設定
			model.addAttribute("org.springframework.validation.BindingResult.userUpdateForm", result);
			session.removeAttribute("result");
		}

		return "mypage/admin/update_input";
	}

	/**
	 * 入力後の入力チェックなど
	 * @param userForm
	 * @param result
	 * @return
	 */
	@RequestMapping(path = "/admin/employee/update/check_r", method = { RequestMethod.GET, RequestMethod.POST })
	public String inputUpdateUser_r(@Valid @ModelAttribute UserUpdateForm userUpdateForm, BindingResult result) {
		// 入力値にエラーがあった場合、入力画面に戻る
		if (result.hasErrors()) {
			session.setAttribute("result", result);
			//変更入力画面　表示処理
			return "redirect:/admin/employee/update";
		}

		//入力された内容をセッションスコープに保存
		session.setAttribute("userUpdateForm", userUpdateForm);

		return "redirect:/admin/employee/update/check";
	}

	/**
	 * 登録確認画面表示
	 * @return
	 */
	@GetMapping("/admin/employee/update/check")
	public String inputUpdateUser() {
		//ログインされていない場合と管理者権限を持っていない場合はログイン画面へ
		UserBean userBean = (UserBean) session.getAttribute("user");
		if (userBean == null || userBean.getAuth() != 1) {
			// 対象が無い場合、ログイン画面へ
			return "redirect:/";
		}

		return "mypage/admin/update_check";
	}

	/**
	 * 確認後データベース更新
	 * @param userForm
	 * @param result
	 * @return
	 */
	@RequestMapping(path = "/admin/employee/update/complete_r", method = { RequestMethod.GET, RequestMethod.POST })
	public String inputUpdateUserCheck_r() {
		//セッションスコープの内容を取り出す。
		UserUpdateForm userUpdateForm = (UserUpdateForm) session.getAttribute("userUpdateForm");
		User userUpdate = (User) session.getAttribute("userUpdate");

		int userId = userUpdate.getUserId();
		String password = userUpdate.getPassword();

		//エンティティのオブジェクト作成
		User user = new User();

		//変更した内容をエンティティに代入
		user.setUserName(userUpdateForm.getUserName());
		user.setTeam(userUpdateForm.getTeam());
		user.setEmail(userUpdateForm.getEmail());
		user.setTel(userUpdateForm.getTel());
		user.setPosition(userUpdateForm.getPosition());
		user.setAuth(userUpdateForm.getAuth());
		user.setUserId(userId);
		user.setPassword(password);

		System.out.println(user.getUserName());
		System.out.println(user.getTeam());
		System.out.println(user.getUserId());
		System.out.println(user.getPassword());
		System.out.println(user.getEmail());

		//データベース更新
		userRepository.save(user);

		return "redirect:/admin/employee/update/complete";
	}

	/**
	 * 登録確認画面表示
	 * @return
	 */
	@GetMapping("/admin/employee/update/complete")
	public String inputUpdateUserComplete() {
		//ログインされていない場合と管理者権限を持っていない場合はログイン画面へ
		UserBean userBean = (UserBean) session.getAttribute("user");
		if (userBean == null || userBean.getAuth() != 1) {
			// 対象が無い場合、ログイン画面へ
			return "redirect:/";
		}

		//登録用のセッションスコープ削除
		session.removeAttribute("userForm");

		return "mypage/admin/update_complete";
	}

	//消すと動かない。なんでかは知らん。
	@ModelAttribute("userForm")
	public UserForm getUserForm() {
		return new UserForm();
	}
}