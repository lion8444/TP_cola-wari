package jp.co.sss.management.controller.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.sss.management.service.schedule.ScheduleService;



@Controller
@RequestMapping("/schedule")
public class ScheduleShowController {

    @Autowired
    ScheduleService scheduleService;
    
    @GetMapping("list")
    public String showSchedule() {
        return "schedule/schedules";
    }

    @GetMapping("detail/{scheduleId}")
    public String getMethodName(@PathVariable int scheduleId, Model model) {

        model.addAttribute("schedule", scheduleService.showScheduleDetail(scheduleId));
        return "schedule/schedule";
    }
    
    
    
}
