package jp.co.sss.management.controller.mypage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jp.co.sss.management.bean.UserBean;
import jp.co.sss.management.entity.User;
import jp.co.sss.management.form.PasswordForm;
import jp.co.sss.management.repository.UserRepository;

@Controller
public class MypageController {

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
	 * マイページ表示用コントローラ（一般会員）
	 */
	@RequestMapping(path = "/mypage", method = { RequestMethod.GET, RequestMethod.POST })
	public String showMypage() {
		//ログインされていない場合ログイン画面へ
		UserBean userBean = (UserBean) session.getAttribute("user");
		if (userBean == null) {
			// 対象が無い場合、ログイン画面へ
			return "redirect:/";
		}

		return "mypage/menu_user";
	}

	/**
	 * パスワード変更画面表示
	 */
	@RequestMapping(path = "/mypage/password/update", method = RequestMethod.GET)
	public String showUpdatePassword(Model model) {
		//ログインされていない場合ログイン画面へ
		UserBean userBean = (UserBean) session.getAttribute("user");
		if (userBean == null) {
			// 対象が無い場合、ログイン画面へ
			return "redirect:/";
		}

		return "mypage/user/update_input";
	}

	/**
	 * パスワード変更（データベース更新）
	 * @return
	 */
	@RequestMapping(path = "/mypage/password/update/complete_r", method = RequestMethod.POST)
	public String updatePassword(@Valid @ModelAttribute PasswordForm passwordForm, BindingResult result) {
		// 入力値にエラーがあった場合、入力画面に戻る
		if (result.hasErrors()) {

			session.setAttribute("result", result);

			//変更入力画面　表示処理
			return "redirect:mypage/employee/update";
		}

		//userという名前のセッションスコープからユーザIDの取得
		UserBean userBean = (UserBean) session.getAttribute("user");
		int userId = userBean.getUserId();

		// 変更対象の情報を取得(一応、いらないなら消す)
		User user = userRepository.findByUserIdAndStatus(userId, 0);
		if (user == null) {
			// 対象が無い場合、エラー
			return "redirect:/";
		}

		//入力された前回のパスワードとDBのパスワードが一致しないとき
		if (!(user.getPassword().equals(passwordForm.getOldPassword()))) {
			return "redirect:/";
		} else {
			//新しいパスワードと確認用パスワードが一致しないとき
			if (!(passwordForm.getNewPassword().equals(passwordForm.getCheckPassword()))) {
				return "redirect:/";
			} else {
				//フォームの新しいパスワードを変更しエンティティを更新。
				user.setPassword(passwordForm.getNewPassword());

				// 情報を保存
				userRepository.save(user);
			}
		}

		//変更完了画面　表示処理
		return "redirect:/mypage/password/update/complete";
	}

	/**
	 * パスワード変更完了画面表示(リダイレクト後)
	 */
	@RequestMapping(path = "/mypage/password/update/complete", method = RequestMethod.GET)
	public String showCompletePassword() {
		return "mypage/user/update_complete";
	}

	//消すと動かない。なんでかは知らん。
	@ModelAttribute("passwordForm")
	public PasswordForm getPasswordForm() {
		return new PasswordForm();
	}
}
