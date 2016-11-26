package cn.kanejin.olla.sample.web;

import cn.kanejin.olla.response.ServiceResult;
import cn.kanejin.olla.sample.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Kane Jin
 */
@Controller
public class HelloController {
    @Autowired
    private HelloService helloService;

    @RequestMapping({"/", "/hello"})
    public ModelAndView sayHello() {
        ModelAndView mv = new ModelAndView("hello");

        ServiceResult<String> result = helloService.sayHello("Kane");

        mv.addObject("message", result.getData());

        return mv;
    }
}
