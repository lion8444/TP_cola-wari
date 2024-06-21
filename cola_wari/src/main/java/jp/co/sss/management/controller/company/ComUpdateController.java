package jp.co.sss.management.controller.company;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/company")
public class ComUpdateController {
    @PostMapping("/update/input")
    public String updateInput() {
    	
    	return "company/update_input";
    }
}
