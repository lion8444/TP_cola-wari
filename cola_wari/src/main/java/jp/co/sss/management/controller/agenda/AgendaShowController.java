package jp.co.sss.management.controller.agenda;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequestMapping("/agenda")
public class AgendaShowController {
    
    @GetMapping("list")
    public String showAgendas(Model model) {
        return "agenda/agendas";
    }
    
}
