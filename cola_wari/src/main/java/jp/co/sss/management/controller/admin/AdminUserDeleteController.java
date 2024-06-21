package jp.co.sss.management.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import jp.co.sss.management.bean.UserBean;
import jp.co.sss.management.entity.User;
import jp.co.sss.management.repository.UserRepository;

@Controller
public class AdminUserDeleteController {
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
	 * 削除確認画面表示
	 */
	@RequestMapping(path = "/mypage/employee/delete/check", method = { RequestMethod.GET, RequestMethod.POST })
	public String userDeleteCheck(@RequestParam(value = "selectedUser", required = false) String selectedUserId) {
		//ログインされていない場合と管理者権限を持っていない場合はログイン画面へ
		UserBean userBean = (UserBean) session.getAttribute("user");
		if (userBean == null || userBean.getAuth() != 1) {
			//部員登録・変更・削除用のセッションスコープを初期化
			session.removeAttribute("userForm");
			// 対象が無い場合、ログイン画面へ
			return "redirect:/";
		}

		if (selectedUserId == null) {
			//部員登録・変更・削除用のセッションスコープを初期化
			session.removeAttribute("userForm");
			// selectedUserId が null の場合、/adminへ遷移
			return "redirect:/admin";
		}

		//チェックぼくっすにチェックを入れられたユーザIdで検索し、DBから持ってくる。
		int userId = Integer.parseInt(selectedUserId);
		User userDelete = userRepository.findByUserIdAndStatus(userId, 0);

		//リクエストスコープに保存
		session.setAttribute("userForm", userDelete);

		return "mypage/admin/delete_check";
	}

	/**
	 * データベース更新
	 */
	@RequestMapping(path = "/mypage/employee/delete/complete_r", method = { RequestMethod.GET, RequestMethod.POST })
	public String userDeleteComplete_r() {
		//セッションスコープから削除するデータ抽出
		User userDelete = (User) session.getAttribute("userForm");

		//データの削除（デリートフラグを変更）
		userDelete.setStatus(1);

		//データベース更新
		userRepository.save(userDelete);

		//セッション情報の削除
		session.removeAttribute("userForm");

		return "redirect:/mypage/employee/delete/complete";
	}

	/**
	 * 完了画面表示
	 */
	@GetMapping("/mypage/employee/delete/complete")
	public String userDeleteComplete() {
		return "mypage/admin/delete_complete";
	}

}
