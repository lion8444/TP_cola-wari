package jp.co.sss.management.controller.login;

import java.util.ArrayList;
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
import jp.co.sss.management.bean.AgendaBean;
import jp.co.sss.management.bean.ScheduleBean;
import jp.co.sss.management.bean.UserBean;
import jp.co.sss.management.entity.Agenda;
import jp.co.sss.management.entity.Schedule;
import jp.co.sss.management.entity.User;
import jp.co.sss.management.form.LoginForm;
import jp.co.sss.management.repository.AgendaEntryRepository;
import jp.co.sss.management.repository.ScheduleEntryRepository;
import jp.co.sss.management.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class LoginController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	HttpSession session;

	@Autowired
	AgendaEntryRepository agendaEntryRepository;

	@Autowired
	ScheduleEntryRepository scheduleEntryRepository;

	@RequestMapping(path = "/", method = { RequestMethod.GET, RequestMethod.POST })
	public String index(Model model) {

		//ユーザの情報が無かったらログイン画面へ
		if (session.getAttribute("user") == null) {
			return "login/login";
		}

		//登録されているユーザのBeanを取得
		UserBean userBean = ((UserBean) session.getAttribute("user"));
		//ビーンからユーザIdを取得
		int userId = userBean.getUserId();
		System.out.println(userId);
		//空のユーザを作る
		User user = new User();
		//空っぽのユーザにIDのみ情報を入れる
		user.setUserId(userId);

		//ユーザIdが一致する案件情報を中間テーブルを通して検索
		List<Agenda> agendas = agendaEntryRepository.findByUserAgendas(user);
		//AgendaBean agendaBean = new AgendaBean();
		List<AgendaBean> agendaBeans = new ArrayList<>();

		List<Schedule> schedules = scheduleEntryRepository.findByUserSchedules(user);
		List<ScheduleBean> scheduleBeans = new ArrayList<>();

		for (Agenda agenda : agendas) {
			log.debug("案件データ確認 : {}", agenda.getTitle());

			// 各Agendaごとに新しいAgendaBeanを作成して情報をセットする
			AgendaBean agendaBean = new AgendaBean();
			agendaBean.setTitle(agenda.getTitle());

			// AgendaBeanをリストに追加
			agendaBeans.add(agendaBean);
		}

		for (Schedule schedule : schedules) {
			log.debug("スケジュールデータ確認 : {}", schedule.getTitle());

			ScheduleBean scheduleBean = new ScheduleBean();
			scheduleBean.setTitle(schedule.getTitle());

			scheduleBeans.add(scheduleBean);

		}

		//リクエストスコープ（仮）に、取得した案件情報を登録
		model.addAttribute("nowAgendas", agendaBeans);

		model.addAttribute("nowTasks", scheduleBeans);

		//管理者権限の情報をセッションスコープに保存
		session.setAttribute("auth", userBean.getAuth());

		//不要なセッションスコープ削除
		session.removeAttribute("messeage1");
		session.removeAttribute("messeage2");

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
	@RequestMapping(path = "/login", method = { RequestMethod.GET, RequestMethod.POST })
	public String login(@Valid @ModelAttribute LoginForm loginForm, BindingResult result, Model model) {
		String returnStr = "login";

		//入力値に誤りがあった場合
		if (result.hasErrors()) {
			session.setAttribute("result", result);
			model.addAttribute("org.springframework.validation.BindingResult.userForm", result);
			returnStr = "login/login";
		} else {
			returnStr = "redirect:/";
		}

		return returnStr;
	}

	/**
	 * ログアウト処理
	 * @return
	 */
	@GetMapping("/logout")
	public String logout() {
		session.invalidate();
		return "redirect:/";
	}

	//なんか知らんけどいる。消したら動かん。
	@ModelAttribute("loginForm")
	public LoginForm getLoginForm() {
		return new LoginForm();
	}
}
