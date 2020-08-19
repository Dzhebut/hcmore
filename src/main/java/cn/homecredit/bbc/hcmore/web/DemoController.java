package cn.homecredit.bbc.hcmore.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DemoController {

    @RequestMapping("/hello")
    @ResponseBody
    public String hello(){
        return "hello";
    }
}
