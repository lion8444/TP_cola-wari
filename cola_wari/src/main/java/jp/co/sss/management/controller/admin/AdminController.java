package jp.co.sss.management.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpSession;
import jp.co.sss.management.bean.UserBean;
import jp.co.sss.management.entity.User;
import jp.co.sss.management.repository.UserRepository;

@Controller
public class AdminController {

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
	 * 管理者画面表示
	 * @return
	 */
	@RequestMapping(path = "/admin", method = { RequestMethod.GET, RequestMethod.POST })
	public String showAdmin(Model model) {
		//ログインされていない場合と管理者権限を持っていない場合はログイン画面へ
		UserBean userBean = (UserBean) session.getAttribute("user");
		if (userBean == null || userBean.getAuth() == 0) {
			// 対象が無い場合、ログイン画面へ
			return "redirect:/";
		}

		//セッション情報の削除
		session.removeAttribute("userForm");

		//全てのユーザ情報をDBから取得
		List<User> userList = userRepository.findByStatusOrderByUserIdAsc(0);
		//userをスコープに保存
		model.addAttribute("userAll", userList);

		return "mypage/menu_admin";
	}

}
