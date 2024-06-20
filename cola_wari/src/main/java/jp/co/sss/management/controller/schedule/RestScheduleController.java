package jp.co.sss.management.controller.schedule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.sss.management.bean.RestScheduleBean;
import jp.co.sss.management.service.schedule.ScheduleService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class RestScheduleController {
    
    @Autowired
    ScheduleService scheduleService;

    @PostMapping("/schedule/calendar")
    public List<RestScheduleBean> showSchedule(String data) {
        List<RestScheduleBean> restScheduleBeans = scheduleService.showRestScheduleBeans();
        for (RestScheduleBean restScheduleBean : restScheduleBeans) {
            log.debug("schedule test : {}", restScheduleBean.getTitle());
        }
        return restScheduleBeans;
    }
    
    
}
