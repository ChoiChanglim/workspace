package my.random.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import my.random.api.constant.QuizAndChoice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class QuizController {
	private static Logger LOG = LoggerFactory.getLogger(QuizController.class);
			
	@RequestMapping(value = "/quiz/sample")
    public ModelAndView test(HttpServletRequest req, HttpServletResponse res){
        ModelAndView modelAndView = new ModelAndView();
        List<QuizAndChoice> quizAndChoiceList = QuizAndChoice.GetQuestionAndChoiceList();
        modelAndView.addObject("quizAndChoiceList", quizAndChoiceList);
        LOG.info("TEST PAGE Cntroller");
        return modelAndView;
    }
}
