package jp.co.sss.management.controller.schedule;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.sss.management.bean.AgendaBean;
import jp.co.sss.management.bean.RestScheduleBean;
import jp.co.sss.management.form.ScheduleForm;
import jp.co.sss.management.service.agenda.AgendaService;
import jp.co.sss.management.service.schedule.ScheduleService;
import jp.co.sss.management.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@Slf4j
@RestController
public class RestScheduleController {
    
    @Autowired
    ScheduleService scheduleService;

    @Autowired
    AgendaService agendaService;

    @Autowired
    UserService userService;

    @PostMapping("/schedule/calendar")
    public List<RestScheduleBean> showSchedule(String data) {
        List<RestScheduleBean> restScheduleBeans = scheduleService.showRestScheduleBeans();
        for (RestScheduleBean restScheduleBean : restScheduleBeans) {
            log.debug("schedule test : {}", restScheduleBean.getTitle());
        }
        return restScheduleBeans;
    }

    @PostMapping("/schedule/agendaList")
    public List<AgendaBean> viewAgendas(@RequestParam List<Integer> userIdList) {

        log.debug("RestScheduleController.viewAgendas userIdList size : {}", userIdList.size());
        for (Integer integer : userIdList) {
            log.debug("RestScheduleController.viewAgendas userIdList : {}", integer);
        }
        List<AgendaBean> agendaBeans = agendaService.showAgendaBeansWithUsers(userService.searchUserBeansByUserId(userIdList));
        if (!agendaBeans.isEmpty()) {
            log.debug("RestScheduleController.viewAgendas userIdList : {}", agendaBeans.get(0).toString());
        }else {
            for (AgendaBean agendaBean : agendaBeans) {
                log.debug("RestSchedulecontroller.viewAgendas return AgendaBeanList Check size : {}, value : {}", agendaBeans.size(), agendaBean.toString());
            }
        }
        return agendaBeans;
    }

    @PostMapping("/schedule/resist/insert")
    public int insertSchedule(ScheduleForm scheduleForm) {
        
        log.debug("RestScheduleController.insertSchedule parameter check scheduleForm : {}", scheduleForm.toString());

        if(scheduleService.insertSchedule(scheduleForm) == 0) {
            return 0;
        }
        return 1;
    }
    
}
