package jp.co.sss.management.controller.schedule;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("schedule")
public class ScheduleResistController {

    @GetMapping("resist/insert")
    public String goToInsertSchedule() {
        return "schedule/schedule_insert";
    }
}
