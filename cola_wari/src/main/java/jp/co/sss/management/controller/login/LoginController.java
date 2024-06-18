package jp.co.sss.management.controller.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jp.co.sss.management.form.LoginForm;
import jp.co.sss.management.repository.UserRepository;

@Controller
public class LoginController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	HttpSession session;

	@RequestMapping(path = "/", method = { RequestMethod.GET, RequestMethod.POST })
	public String index() {
		//ユーザの情報が無かったらログイン画面へ
		if (session.getAttribute("user") == null) {
			return "login/login";
		}
		return "index";
	}

	/**
	 * ログイン処理
	 *
	 * @param form ログインフォーム
	 * @param result 入力チェック結果
	 * @return
			ログインが成功した場合 "redirect:/index" トップ画面表示処理
	 */
	@PostMapping("/login")
	public String login(@Valid @ModelAttribute LoginForm loginForm, BindingResult result) {

		String returnStr = "login";

		//入力値に誤りがあった場合
		if (result.hasErrors()) {
			// セッション情報を無効にして、ログイン画面再表示
			session.invalidate();
			returnStr = "login/login";
		} else {
			returnStr = "redirect:/";
		}

		return returnStr;
	}

	@ModelAttribute("loginForm")
	public LoginForm getLoginForm() {
		return new LoginForm();
	}

}
