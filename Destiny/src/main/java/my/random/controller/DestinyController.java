package my.random.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import my.random.api.annotation.TilesOn;
import my.random.api.constant.LottoReader;
import my.random.api.constant.SessionScopeBean;
import my.random.api.constant.SupportMember.MemberDivEnum;
import my.random.api.exception.RedirectException;
import my.random.api.interceptor.CookieInterceptor;
import my.random.api.util.AesCipher;
import my.random.api.util.RequestUtil;
import my.random.api.util.StringUtil;
import my.random.bean.Luckylog;
import my.random.bean.Member;
import my.random.service.DestinyService;
import my.random.service.KakaoApiService;
import my.random.service.LoginService;
import my.random.service.LoginServiceImpl;
import my.random.service.MemberService;
import my.random.service.NaverApiService;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.AbstractView;

@Controller
public class DestinyController {

    @Autowired
    DestinyService destinyService;
    
    @Autowired
    LoginService loginService;
    
    @Autowired
    MemberService memberService;

    @Inject
    Provider<SessionScopeBean> sessionScopebeanFactory;
    
    
    @Value("#{propinfo['TEMP_IMG_PATH']}")
    private String TEMP_IMG_PATH;

    @TilesOn
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest req, HttpServletResponse res,
    		@RequestParam(value = "ukey", required = false, defaultValue = "") String ukey
    		) {
        ModelAndView modelAndView = new ModelAndView();
        SessionScopeBean sessionBean = this.sessionScopebeanFactory.get();
        String nick = "";
        if("".equals(ukey)){
        	ukey = CookieInterceptor.GetUkey(req, ukey);
        	nick = CookieInterceptor.GetNick(req);
        	
        }
        
        
        if(sessionBean.isSignIn()){
        	
        	MemberDivEnum memberDivEnum = MemberDivEnum.GetMemberDivEnum(sessionBean.getUdiv());
        	Member member = memberService.getUser(sessionBean.getUid(), memberDivEnum);
        	ukey = sessionBean.getUid();
        	nick = member.getUname();
        }

        modelAndView.addObject("nick", nick);
        modelAndView.addObject("lastResult", destinyService.getLastDestinyInfo());
        
        List<Luckylog> mylist = destinyService.getMyList(ukey, LottoReader.getGnum(new Date()));
        modelAndView.addObject("mylist", mylist);
        modelAndView.addObject("myLuckyList", destinyService.getMyLuckyList(ukey));
        modelAndView.addObject("ukey", ukey);
        modelAndView.addObject("isSignin", sessionBean.isSignIn());
        
        
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
    	SessionScopeBean sessionBean = this.sessionScopebeanFactory.get();
        
        String ukey = CookieInterceptor.GetUkey(req);
        if(sessionBean.isSignIn()){
        	MemberDivEnum memberDivEnum = MemberDivEnum.GetMemberDivEnum(sessionBean.getUdiv());
        	Member member = memberService.getUser(sessionBean.getUid(), memberDivEnum);
        	ukey = sessionBean.getUid();
        	nick = member.getUname();
        }
        CookieInterceptor.SetNick(req, res, nick);
        ModelAndView modelAndView = new ModelAndView();
        List<Integer[]> numsList =  destinyService.create(ukey, count);
        

        modelAndView.addObject("numsList", numsList);
        modelAndView.addObject("nextGno", LottoReader.getGnum(new Date()));
        RequestUtil.setCookie(res, "gcnt", String.valueOf(count), 300);

        return modelAndView;
    }
    
    @RequestMapping(value = "/policy", method = RequestMethod.GET)
    public ModelAndView policy(HttpServletRequest req, HttpServletResponse res) {
        ModelAndView modelAndView = new ModelAndView();
        return modelAndView;
    }
    
    
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void download(HttpServletRequest req, HttpServletResponse res,
    		@RequestParam(value = "ukey", required = false, defaultValue = "") String ukey
    		) throws IOException {
        File file = new File(TEMP_IMG_PATH+ukey+".png");
        if(!file.exists()){
            String errorMessage = "Sorry. The file you are looking for does not exist";
            System.out.println(errorMessage);
            OutputStream outputStream = res.getOutputStream();
            outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
            outputStream.close();
            return;
        }
         
        String mimeType= URLConnection.guessContentTypeFromName(file.getName());
        if(mimeType==null){
            System.out.println("mimetype is not detectable, will take default");
            mimeType = "application/octet-stream";
        }
         
        System.out.println("mimetype : "+mimeType);
        res.setContentType("application/download; charset=utf-8"); 
        //res.setContentType(mimeType);
         
        /* "Content-Disposition : inline" will show viewable types [like images/text/pdf/anything viewable by browser] right on browser 
            while others(zip e.g) will be directly downloaded [may provide save as popup, based on your browser setting.]*/
        res.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() +"\""));
 
         
        /* "Content-Disposition : attachment" will be directly download, may provide save as popup, based on your browser setting*/
        //response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
         
        res.setContentLength((int)file.length());
 
        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
 
        //Copy bytes from source to destination(outputstream in this example), closes both streams.
        FileCopyUtils.copy(inputStream, res.getOutputStream());
    }
    
    /**
     * slick pageing
     * @param req
     * @param res
     * @param ukey
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(HttpServletRequest req, HttpServletResponse res,
    		@RequestParam(value = "page", required = false, defaultValue = "1") int page
    		) {
        ModelAndView modelAndView = new ModelAndView();
        
        int limit  = 6;
        List<HashMap<String, Object>> list = new ArrayList<HashMap<String,Object>>();
        System.err.println("page:"+page);
        for(int i=0;i<limit;i++){
        	HashMap<String, Object> map = new HashMap<String, Object>();
        	int no = (i+1) + ((page-1) * limit);
        	//System.err.println(no);
        	map.put("key", no);
        	map.put("cont", "AB"+no);
        	list.add(map);
        }
        modelAndView.addObject("list", list);
        return modelAndView;
    }
    
    @RequestMapping(value = "/input_token", method = RequestMethod.GET)
    public ModelAndView input_token(HttpServletRequest req, HttpServletResponse res
    		) {
        ModelAndView modelAndView = new ModelAndView();
     
        return modelAndView;
    }
    
    
    @RequestMapping(value = "/login/signin")
    public ModelAndView signin_by_etc(HttpServletRequest req, HttpServletResponse res,
    		@RequestParam(value = "expt", required = false, defaultValue = "") String expt,
    		@RequestParam(value = "udiv", required = false, defaultValue = "") String udiv
    		){
		SessionScopeBean sessionBean = this.sessionScopebeanFactory.get();
		if(sessionBean.isSignIn()){
			//throw RedirectException.UNACCEPTABLE_PAGE_EXCEPTION;
		}
		
        ModelAndView modelAndView = new ModelAndView();
        String referer = req.getHeader("referer"); 
        if(StringUtil.isNull(referer)){
        	referer = "/";
        }
        
        AesCipher cipher  = new AesCipher(LoginServiceImpl.SIGNED_COOKIE_CIPHER_KEY);
        RequestUtil.setCookieByMinute(res, LoginServiceImpl.SIGNED_REFERER_COOKIE_NAME, cipher.encrypt(referer), 30);
        
        MemberDivEnum memberDivEnum = MemberDivEnum.GetMemberDivEnum(udiv);
        if(memberDivEnum == MemberDivEnum.NAVER){
        	modelAndView.setViewName("redirect:/api/naver/login?expt="+expt);
        	
        }else if(memberDivEnum == MemberDivEnum.KAKAO){
        	modelAndView.setViewName("redirect:/api/kakao/login?expt="+expt);
        	
        }else{
        	//throw RedirectException.LOGIN_EXCEPTION;
        	
        }
        return modelAndView;
    }
    
    
    @RequestMapping(value = "/logout")
    public ModelAndView logout(HttpServletRequest req, HttpServletResponse res,
    		@RequestParam(value = "referer", required = false, defaultValue = "") String referer,
    		@RequestParam(value = "rechk", required = false, defaultValue = "") String rechk
    		){
        ModelAndView modelAndView = new ModelAndView();
        if("".equals(referer)){
        	referer = req.getHeader("referer");
        	
        }
        if("".equals(rechk) == false){ //레퍼러체크가 기본값이 아니면 레퍼러 체크하지 말자.
        	referer = "/";
        }
        
        RequestUtil.setCookieByMinute(res, LoginServiceImpl.SIGNED_UID_COOKIE_NAME, "", -1);
		RequestUtil.setCookieByMinute(res, LoginServiceImpl.SIGNED_UDIV_COOKIE_NAME, "", -1);
		SessionScopeBean sessionBean = this.sessionScopebeanFactory.get();
		MemberDivEnum memberDivEnum = MemberDivEnum.GetMemberDivEnum(sessionBean.getUdiv());
		sessionBean.setUid(null);
		sessionBean.setUdiv(null);
		
		
		//플랫폼별 로그아웃 처리
		if(memberDivEnum == MemberDivEnum.NAVER){
			AesCipher cipher  = new AesCipher(NaverApiService.NaverServerCipherKey);
			String accessToken = NaverApiService.GetAccessToken(req, res, cipher);
	        NaverApiService.DeleteNaverAccessToken(accessToken);
	        RequestUtil.deleteCookie(res, NaverApiService.ACCESS_TOKEN_COOKIE_NAME);
	        RequestUtil.deleteCookie(res, NaverApiService.REFRESH_TOKEN_COOKIE_NAME);
	        
	        
		}else if(memberDivEnum == MemberDivEnum.KAKAO){
			RequestUtil.deleteCookie(res, KakaoApiService.KakaoAccessTokenCookieName);
	        RequestUtil.deleteCookie(res, KakaoApiService.KakaoRefreshTokenCookieName);
			
		}
		
		modelAndView.setViewName("redirect:/");
        return modelAndView;
    }
}
