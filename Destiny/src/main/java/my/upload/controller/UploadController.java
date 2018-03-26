package my.upload.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import my.upload.service.FileService;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;



@RequestMapping("/upload")
@Controller
public class UploadController {
    private static Logger LOG = LoggerFactory.getLogger(UploadController.class);
    
    @Autowired
    FileService fileService;



    @RequestMapping(value = "/save_image", method = RequestMethod.POST)
    public ModelAndView upload(HttpServletRequest req, HttpServletResponse res
            ){
        ModelAndView modelAndView = new ModelAndView();
        
        boolean isImageFiles = fileService.isImageFiles(req);
        if(isImageFiles == true){
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)req;
            List<HashMap<String, Object>> result = fileService.fileSave(multipartHttpServletRequest);
            System.err.println(new JSONArray(result));
            modelAndView.addObject("result", result);
        }


        return modelAndView;
    }
}
