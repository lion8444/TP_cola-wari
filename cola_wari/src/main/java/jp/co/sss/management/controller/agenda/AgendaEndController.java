package jp.co.sss.management.controller.agenda;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sss.management.repository.AgendaRepository;

@Controller
@RequestMapping("/agenda")
public class AgendaEndController {
	
	@Autowired
	AgendaRepository repository;
    
    @GetMapping("close")
    public String finishAgendaCheck(Model model) {
    	
        return "agenda/finish_check";
    }
    
    @GetMapping("close/complete")
    public String finishAgendaComplete(Model model) {
    	
        return "agenda/finish_complete";
    }
}
