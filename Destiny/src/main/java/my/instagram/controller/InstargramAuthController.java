package my.instagram.controller;


import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import my.instagram.constant.Const;
import my.instagram.constant.Const.Client;
import my.instagram.service.AuthService;
import my.random.api.exception.CustomException;
import my.random.api.util.RequestUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@Controller
@RequestMapping("/instagram/auth")
public class InstargramAuthController {
	private static Logger LOG = LoggerFactory.getLogger(InstargramAuthController.class);
	
	@Autowired
	AuthService authService;
	
    @RequestMapping(value = "/callback", method = RequestMethod.GET)
    public String index(HttpServletRequest req, HttpServletResponse res,
    		@RequestParam(value = "code", required = false, defaultValue = "") String code
    		) {
    	//System.err.println("### /callback");
        ModelAndView modelAndView = new ModelAndView();
        if("".equals(code.trim())){
        	LOG.error("code is null!");
        	throw CustomException.NOT_ALLOWED_USER;
        }
        String username=authService.setAccessToken(code);
        modelAndView.addObject("code", code);
        modelAndView.addObject("username", username);
        modelAndView.setView(new MappingJackson2JsonView());
        
        String redirect_url = "https://www.instagram.com/"+username; 
        return "redirect:"+redirect_url;
    }
    
    @RequestMapping(value = "/getAuth", method = RequestMethod.GET)
    public String getAuth(HttpServletRequest req, HttpServletResponse res) {
    	//System.err.println("### /getAuth");
    	HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("client_id", Client.ID.getValue());
        param.put("redirect_uri", Client.REDIRECT_URI.getValue());
        param.put("response_type", "code");
        
        String url = Const.GET_AUTH_CODE+"?"+RequestUtil.getQstr(param);
        return "redirect:"+url;
    }
    
}
