package jp.co.sss.management.controller.schedule;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jp.co.sss.management.bean.ScheduleEntryBean;
import jp.co.sss.management.bean.UserBean;
import jp.co.sss.management.bean.UserCheckedBean;
import jp.co.sss.management.form.ScheduleForm;
import jp.co.sss.management.service.agenda.AgendaService;
import jp.co.sss.management.service.schedule.ScheduleService;
import jp.co.sss.management.service.user.UserService;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    UserService userService;

    @Autowired
    AgendaService agendaService;
    
    @GetMapping("list")
    public String goToAllSchedule(Model model) {
        log.debug("scheduleController users : {}", userService.showUserBeansByStatus().get(0).toString());
        log.debug("agenda : {}", agendaService.showAgendaBeansWithUsers(userService.showUserBeansByStatus()));
        model.addAttribute("users", userService.showUserBeansByStatus());
        model.addAttribute("agendas", agendaService.showAgendaBeansWithUsers(userService.showUserBeansByStatus()));
        return "schedule/schedules";
    }

    @GetMapping("detail/{scheduleId}")
    public String goToScheduleDetail(@PathVariable int scheduleId, Model model) {
        model.addAttribute("schedule", scheduleService.showScheduleDetail(scheduleId));
        return "schedule/schedule";
    }

    @GetMapping("update")
    public String goToUpdate(@RequestParam Integer scheduleId, Model model) {
        ScheduleEntryBean scheduleEntryBean = scheduleService.showScheduleDetail(scheduleId);
        List<UserBean> userBeans = userService.showUserBeansByStatus();
        List<UserBean> userBeans2 = scheduleEntryBean.getUserBeans();
        List<UserCheckedBean> checked = new ArrayList<>();

        for (UserBean userBean : userBeans) {
            UserCheckedBean userCheckedBean = new UserCheckedBean();
            userCheckedBean.setUserBean(userBean);
            for (UserBean userBean2 : userBeans2) {
                if (userBean.getUserId().equals(userBean2.getUserId())) {
                    userCheckedBean.setChecked(1);
                    break;
                }
            }
            checked.add(userCheckedBean);
        }
        for (UserCheckedBean userCheckedBean : checked) {
            log.debug("UserCheckedBean check UserName : {}, checked : {}", userCheckedBean.getUserBean().getUserName(), userCheckedBean.getChecked());
        }

        model.addAttribute("schedule", scheduleService.showScheduleDetail(scheduleId));
        // model.addAttribute("users", userService.showUserBeansByStatus());
        model.addAttribute("users", checked);
        return "schedule/schedule_update";
    }
    
    @PostMapping("update")
    public String updateSchedule(ScheduleForm scheduleForm, Model model) {
        if (scheduleService.insertSchedule(scheduleForm) != 1) {
            log.debug("ScheduleUpdateController.updateSchedule update FAIL!!!!!");
            return "redirect:/";
        }
        model.addAttribute("complete", 0);
        model.addAttribute("scheduleId", scheduleForm.getScheduleId());
        return "schedule/schedule_complete";
    }

    @PostMapping("delete")
    public String deleteSchedule(Integer scheduleId, Model model) {
        scheduleService.deleteSchedule(scheduleId);
            
        model.addAttribute("complete", 1);
        
        return "schedule/schedule_complete";
    }
    
}
