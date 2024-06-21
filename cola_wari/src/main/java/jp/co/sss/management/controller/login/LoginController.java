package jp.co.sss.management.controller.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class LoginController {
    
    @Autowired
    HttpSession session;

    @GetMapping({"", "/"})
    public String index() {
//        if (session.getAttribute("user") == null) {
//            return "login/login";
//        }
        return "index";
    }

    @PostMapping("/login")
    public String login() {
        
        return "index";
    }
    
    
}
