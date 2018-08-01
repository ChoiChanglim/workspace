package my.kakao.chatbot.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import my.kakao.chatbot.bean.Message;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@Controller
public class CharbotController {
	
	@RequestMapping(value = "/keyboard")
    public ModelAndView keyboard(HttpServletRequest req, HttpServletResponse res,
            @RequestParam(value="type", required = false, defaultValue="text") String type
            ) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView(new MappingJackson2JsonView());
        
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("type", type);
        modelAndView.addObject("type", "buttons");
        
        String[] buttons = {"홍마", "최창림", "개치범", "최광성빈"};
        modelAndView.addObject("buttons", buttons);
        return modelAndView;
    }
	
	@RequestMapping(value = "/message", method = RequestMethod.POST)
    public ModelAndView message(HttpServletRequest req, HttpServletResponse res,
            @RequestParam(value="user_key", required = false, defaultValue="") String user_key,
            @RequestParam(value="type", required = false, defaultValue="text") String type,
            @RequestParam(value="content", required = false, defaultValue="") String content,
            @RequestParam Map<String, String> param,
            @RequestBody Message message
            ) {
		
		System.err.println("/message call");
		System.err.println(message.getContent());
		Iterator<String> iter = param.keySet().iterator();
		while(iter.hasNext()){
			String key = iter.next();
			String val = (String)param.get(key);
			System.err.println(key+"="+val);
		}
		
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView(new MappingJackson2JsonView());
        HashMap<String, Object> map = new HashMap<String, Object>();
        if("".equals(message.getContent())){
        	map.put("text", "안녕");
                	
        }else if("홍마".equals(message.getContent())){
        	map.put("text", "홍마 안녕");
        	
        }else if("최창림".equals(message.getContent())){
        	map.put("text", "최창림님 안녕하십니까?");
        	
        }else if("개치범".equals(message.getContent())){
        	map.put("text", "개치범??");
        	
        }else if("최광성빈".equals(message.getContent())){
        	map.put("text", "최광성빈 오션월드가냐?");
        	
        }else if(message.getContent().contains("ㅋㅋ")){
        	map.put("text", "웃기냐? ㅋㅋ"); 
        	
        }else if(message.getContent().contains("헐")){
        	map.put("text", "헐헐 ㅎ"); 
        	
        }else{
        	map.put("text", "바쁘니까 말시키지마!"); 
        }
        
        
        modelAndView.addObject("message", map);
        return modelAndView;
    }
}
