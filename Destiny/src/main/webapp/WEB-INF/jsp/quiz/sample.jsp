<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0">
<title>테스트</title>
<link rel="shortcut icon" href="#">
<link rel="stylesheet" type="text/css" href="/resources/css/slick.css"/>
<link rel="stylesheet" type="text/css" href="/resources/css/slick-theme.css"/>
<script src="/resources/js/jquery-1.11.3.min.js"></script>
<script src="/resources/js/slick.min.js"></script>


<style>
</style>
<script>
jQuery(document).ready(function(){
    jQuery("#startQuiz").click(function(){
        jQuery(".quiz_zone").show();
        jQuery(".dots").show();
        startQuiz();
    });
    var startQuiz = function(){
        $(".quiz").slick({
            slide: 'div',       
            infinite : false,
            slidesToShow: 1,
            dots: false,
            autoplay:false,
            draggable : false,
            touchMove : false,  
            swipe : false,
            appendDots: $('.dots'),
            /* customPaging : function(slider, i) {
                console.log(slider);
                return '<a class="pager_item"> O </a>';
            } */
        });
        
        /* $(".slick-dots li a").on('click', function(e){
            e.preventDefault();
            e.stopPropagation(); // use this
            return false;
            console.log(e);
            
        }); */
    }
    
    
    
    //slick 별도의 페이징
    var slickToGo = function(index){
        $('.quiz').slick("slickGoTo", index);
    }
    
    
    //답변 버 튼
    jQuery(".answer_btn").click(function(e){
        var selected_choice = jQuery(this).attr("data");
        var quiz_no = toNumber(jQuery(this).attr("no"));
        var max_quiz_no = toNumber("${quizAndChoiceList.size() }");
        
        var next_quiz_no = quiz_no+1;
        var next_quiz_index = quiz_no;
        
        
        if(next_quiz_no > max_quiz_no){
            //퀴즈 끝
            alert("퀴즈끝");
        }else{
            slickToGo(next_quiz_index);
        }
        jQuery(".dots_"+quiz_no).css("color", "red");
    });
    
    
});

function toNumber(str){
    var testing = parseInt(str) + 0;
    if(isNaN(testing)){
        return 0;
    }
    return testing;
}
</script>
<style>
.quiz_zone{width:500px;height:200px;border:1px solid #000; display:none}
.dots{display:none;width:500px; text-align: center;}
.slick-active:focus, slick-slide:focus, slick-current:focus, slick-track:focus, .slick-slide {
    outline: none;
}
.slick-active:drag {
    pointer-events: none
}
.slick-dots {
    pointer-events: none
}
</style>
</head>
<body>
<button id="startQuiz">START</button>
<section id="introduction">

    <div class="quiz_zone">
        <div class="quiz">
            <c:forEach items="${quizAndChoiceList }" var="list">
                <!-- 퀴즈 한개 -->
                <div>
                    <p>${list.quizEnum.quiz }</p>
                    <!-- 퀴즈의 보기  -->
                    <ul>
                        <c:forEach items="${list.choiceList }" var="choiceList">
                            <li><button class="answer_btn" data="${choiceList }" no="${list.quizEnum.no }">${choiceList.choice }</button></li>
                        </c:forEach>
                    </ul>
                </div>
            </c:forEach>
        </div>
    </div>
    
    <!-- 하단 퀴즈 네비게이션 -->
    <div class="dots">
        <c:forEach items="${quizAndChoiceList }" var="list">
            <span class="dots_${list.quizEnum.no} }">O</span>
        </c:forEach>
    </div>
</section>
</body>


</html>



