package jp.co.sss.management.controller.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;



@Controller
public class LoginController {
    
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
		
		
		for(Schedule schedule : schedules) {
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
        
        return "index";
    }
    
    
}
