package jp.co.sss.management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/util")
public class UtilController {
    
    @GetMapping({"","/buttons"})
    public String utilButton() {
        return "common/util/utilites/buttons";
    }

    @GetMapping("cards")
    public String utilCrad() {
        return "common/util/utilites/cards";
    }

    @GetMapping("charts")
    public String utilChart() {
        return "common/util/utilites/charts";
    }

    @GetMapping("animations")
    public String utilAnimation() {
        return "common/util/utilites/animation";
    }

    @GetMapping("borders")
    public String utilBorder() {
        return "common/util/utilites/border";
    }

    @GetMapping("colors")
    public String utilColor() {
        return "common/util/utilites/color";
    }

    @GetMapping("others")
    public String utilOuther() {
        return "common/util/utilites/other";
    }

    @GetMapping("tables")
    public String utilTable() {
        return "common/util/utilites/tables";
    }
    
}
