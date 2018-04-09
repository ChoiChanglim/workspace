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

import my.instagram.service.PdfcrowdService;
import my.random.api.annotation.TilesOn;
import my.random.api.constant.LottoReader;
import my.random.api.constant.SessionScopeBean;
import my.random.api.interceptor.CookieInterceptor;
import my.random.api.util.RequestUtil;
import my.random.bean.Luckylog;
import my.random.service.DestinyService;

import org.json.JSONArray;
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

    @Inject
    Provider<SessionScopeBean> sessionScopebeanFactory;
    
    @Autowired
    PdfcrowdService pdfcrowdService;
    
    @Value("#{propinfo['TEMP_IMG_PATH']}")
    private String TEMP_IMG_PATH;

    @TilesOn
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest req, HttpServletResponse res,
    		@RequestParam(value = "ukey", required = false, defaultValue = "") String ukey
    		) {
        ModelAndView modelAndView = new ModelAndView();
        if("".equals(ukey)){
        	ukey = CookieInterceptor.GetUkey(req, ukey);
        }
        modelAndView.addObject("nick", CookieInterceptor.GetNick(req));
        modelAndView.addObject("lastResult", destinyService.getLastDestinyInfo());
        
        List<Luckylog> mylist = destinyService.getMyList(ukey, LottoReader.getGnum(new Date()));
        modelAndView.addObject("mylist", mylist);
        modelAndView.addObject("myLuckyList", destinyService.getMyLuckyList(ukey));
        modelAndView.addObject("ukey", ukey);
        
        
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
    
    @RequestMapping(value = "/policy", method = RequestMethod.GET)
    public ModelAndView policy(HttpServletRequest req, HttpServletResponse res) {
        ModelAndView modelAndView = new ModelAndView();
        return modelAndView;
    }
    
    @RequestMapping(value = "/makeimg", method = RequestMethod.GET)
    public String makeimg(HttpServletRequest req, HttpServletResponse res,
    		RedirectAttributes redirectAttributes,
    		@RequestParam(value = "ukey", required = false, defaultValue = "") String ukey
    		) {
        ModelAndView modelAndView = new ModelAndView();
        boolean isMake = pdfcrowdService.makePng(ukey);
        modelAndView.addObject("isMake", isMake);
        //System.err.println("isMake:"+isMake);
        if(isMake == true){
        	redirectAttributes.addAttribute("ukey", ukey);
        }
        return "redirect:/toinstagram";
    }
    @RequestMapping(value = "/toinstagram", method = RequestMethod.GET)
    public ModelAndView toinstagram(HttpServletRequest req, HttpServletResponse res,
    		@RequestParam(value = "ukey", required = false, defaultValue = "") String ukey
    		) {
        ModelAndView modelAndView = new ModelAndView();
        String img_url = "/temp/"+ukey+".png";
        modelAndView.addObject("url", img_url);
        modelAndView.addObject("ukey", ukey);
        
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
    
}
