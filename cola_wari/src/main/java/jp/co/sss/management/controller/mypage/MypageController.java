package jp.co.sss.management.controller.mypage;

import java.util.List;

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
import jp.co.sss.management.entity.AgendaEntry;
import jp.co.sss.management.entity.User;
import jp.co.sss.management.form.PasswordForm;
import jp.co.sss.management.repository.AgendaEntryRepository;
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

	@Autowired
	AgendaEntryRepository agendaEntryRepository;

	/**
	 * マイページ表示用コントローラ（一般会員）
	 */
	@RequestMapping(path = "/mypage", method = { RequestMethod.GET, RequestMethod.POST })
	public String showMypage(Model model) {
		//ログインされていない場合ログイン画面へ
		UserBean userBean = (UserBean) session.getAttribute("user");
		if (userBean == null) {
			// 対象が無い場合、ログイン画面へ
			return "redirect:/";
		}
		Integer id = userBean.getUserId();
		User user = userRepository.getReferenceById(id);
		model.addAttribute("user",user);
		List<AgendaEntry> progressEntry = agendaEntryRepository.findByUserId(id,0);
		model.addAttribute("progresses",progressEntry);
		List<AgendaEntry> endEntry = agendaEntryRepository.findByUserId(id,1);
		model.addAttribute("ends",endEntry);

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

		BindingResult result = (BindingResult) session.getAttribute("result");
		if (result != null) {
			//セッションにエラー情報がある場合、エラー情報を画面表示設定
			model.addAttribute("org.springframework.validation.BindingResult.passwordForm", result);
			session.removeAttribute("result");
		}

		return "mypage/user/update_input";
	}

	/**
	 * パスワード変更（データベース更新）
	 * @return
	 */
	@RequestMapping(path = "/mypage/password/update/complete_r", method = RequestMethod.POST)
	public String updatePassword(@Valid @ModelAttribute PasswordForm passwordForm, BindingResult result) {
		//エラーがあるか否かを調べるやつ
		boolean error = false;
		//userという名前のセッションスコープからユーザIDの取得
		UserBean userBean = (UserBean) session.getAttribute("user");
		int userId = userBean.getUserId();
		// 変更対象の情報を取得
		User user = userRepository.findByUserIdAndStatus(userId, 0);

		if (user == null) {
			// 対象が無い場合、エラー
			return "redirect:/";
		}

		// 入力値にエラーがあった場合、入力画面に戻る
		if (result.hasErrors()) {
			//入力された前回のパスワードとDBのパスワードが一致しないとき
			if (!(user.getPassword().equals(passwordForm.getOldPassword()))) {
				String messeage1 = "・前回のパスワードが一致しません。";
				session.setAttribute("messeage1", messeage1);
			}
			//新しいパスワードと確認用パスワードが一致しないとき
			if (!(passwordForm.getNewPassword().equals(passwordForm.getCheckPassword()))) {
				String messeage2 = "・新しいパスワードと確認用パスワードが一致しません。";
				session.setAttribute("messeage2", messeage2);
			}

			session.setAttribute("result", result);

			//変更入力画面　表示処理
			return "redirect:/mypage/password/update";
		}

		//入力された前回のパスワードとDBのパスワードが一致しないとき
		if (!(user.getPassword().equals(passwordForm.getOldPassword()))) {
			String messeage1 = "前回のパスワードと一致しません。";
			session.setAttribute("messeage1", messeage1);
			error = true;
		}
		//新しいパスワードと確認用パスワードが一致しないとき
		if (!(passwordForm.getNewPassword().equals(passwordForm.getCheckPassword()))) {
			String messeage2 = "新しいパスワードと確認用パスワードが一致しません。";
			session.setAttribute("messeage2", messeage2);
			error = true;
		}

		//エラーがない場合
		if (error == false) {
			//フォームの新しいパスワードを変更しエンティティを更新。
			user.setPassword(passwordForm.getNewPassword());

			// 情報を保存
			userRepository.save(user);

			//変更完了画面　表示処理
			return "redirect:/mypage/password/update/complete";
		} else {
			//エラーがある場合、入力画面へリダイレクト
			return "redirect:/mypage/password/update";
		}

	}

	/**
	 * パスワード変更完了画面表示(リダイレクト後)
	 */
	@RequestMapping(path = "/mypage/password/update/complete", method = RequestMethod.GET)
	public String showCompletePassword() {
		session.removeAttribute("messeage1");
		session.removeAttribute("messeage2");

		//ログインされていない場合と管理者権限を持っていない場合はログイン画面へ
		UserBean userBean = (UserBean) session.getAttribute("user");
		if (userBean == null) {
			// 対象が無い場合、ログイン画面へ
			return "redirect:/";
		}

		return "mypage/user/update_complete";
	}

	//消すと動かない。なんでかは知らん。
	@ModelAttribute("passwordForm")
	public PasswordForm getPasswordForm() {
		return new PasswordForm();
	}
}
