package my.random.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/article")
public class FbInstantArticleController {
	
    @RequestMapping(value = "/**", method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest req, HttpServletResponse res) {
        ModelAndView modelAndView = new ModelAndView();

        return modelAndView;
    }
}
