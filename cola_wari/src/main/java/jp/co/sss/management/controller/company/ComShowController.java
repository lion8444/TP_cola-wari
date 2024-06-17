package jp.co.sss.management.controller.company;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/company")
public class ComShowController {
    @GetMapping("list")
    public String showCompanies(Model model) {
        return "company/companies";
    }
}
