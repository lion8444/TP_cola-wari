package jp.co.sss.management.controller.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jp.co.sss.management.repository.UserRepository;
import jp.co.sss.management.service.agenda.AgendaService;
import jp.co.sss.management.service.schedule.ScheduleService;
import jp.co.sss.management.service.user.UserService;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
@RequestMapping("/schedule")
public class ScheduleShowController {

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
    
}
