package my.random.controller;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import my.random.api.annotation.TilesOn;
import my.random.api.constant.LottoReader;
import my.random.api.constant.SessionScopeBean;
import my.random.api.interceptor.CookieInterceptor;
import my.random.api.util.RequestUtil;
import my.random.bean.Luckylog;
import my.random.service.DestinyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DestinyController {

    @Autowired
    DestinyService destinyService;

    @Inject
    Provider<SessionScopeBean> sessionScopebeanFactory;

    @TilesOn
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest req, HttpServletResponse res) {
        ModelAndView modelAndView = new ModelAndView();
        String ukey = CookieInterceptor.GetUkey(req);
        modelAndView.addObject("nick", CookieInterceptor.GetNick(req));
        modelAndView.addObject("lastResult", destinyService.getLastDestinyInfo());
        
        List<Luckylog> mylist = destinyService.getMyList(ukey, LottoReader.getGnum(new Date()));
        modelAndView.addObject("mylist", mylist);
        modelAndView.addObject("myLuckyList", destinyService.getMyLuckyList(ukey));
        
        if(mylist.size() > 2) {
        	modelAndView.addObject("second", mylist.get(1));
        }
        
        

        return modelAndView;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView create(HttpServletRequest req, HttpServletResponse res,
            @RequestParam(value = "nick", required = false, defaultValue = "") String nick,
            @RequestParam(value = "count", required = true) int count
            ) {

        CookieInterceptor.SetNick(req, res, nick);
        String ukey = CookieInterceptor.GetUkey(req);
        ModelAndView modelAndView = new ModelAndView();
        List<Integer[]> numsList =  destinyService.create(ukey, count);
        

        modelAndView.addObject("numsList", numsList);
        modelAndView.addObject("nextGno", LottoReader.getGnum(new Date()));
        RequestUtil.setCookie(res, "gcnt", String.valueOf(count), 300);

        return modelAndView;
    }

}
