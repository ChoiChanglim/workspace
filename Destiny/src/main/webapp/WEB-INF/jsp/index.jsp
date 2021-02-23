<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script>
jQuery(document).ready(function(){
	$('#lucky_form').ajaxForm({
        dataType: 'json',
        async: true,
        beforeSubmit: function() {
            
        },
        complete: function(data) {
            var numsList = data.responseJSON.numsList;
            //console.log(data.responseJSON);
            var nextGno = data.responseJSON.nextGno;
            
            for(var i=0;i<numsList.length;i++){
            	var row =   "<tr>";
                row = row +     "<td>"+nextGno+"</td>"
                row = row +     "<td>"+numsList[i]+"</td>"
                row = row + "</tr>"
                //console.log(row);
                jQuery("#yournums tbody").append(row);
            }
            location.reload(true);
            //console.log(data.responseText);
        }
    });
	/* $('#btn_upload').click(function(e){
		e.preventDefault();
		var url = "/KakaoUploadTargetUserFile.json";
		//var param="ukey=${ukey}";
		window.open(url);
        getJson(url, "post", param, function(data){
        	console.log(data);   
        });
	}); */
	Kakao.Channel.createAddChannelButton({
	    container: '#kakao-add-channel-button',
	    channelPublicId: '_gXmsC' // 채널 홈 URL에 명시된 id로 설정합니다.
	});
	/* Kakao.Channel.createChatButton({
	    container: '#kakao-talk-channel-chat-button',
	    channelPublicId: '_gXmsC' // 카카오톡 채널 홈 URL에 명시된 id로 설정합니다.
	}); */
	
	
	// 당첨자 슬라이드
    settings = {
		slide: 'ul',     //슬라이드 되어야 할 태그 ex) div, li 
        infinite : true,	
        slidesToShow: 1,
        slidesToScroll: 1,
        autoplay: true,
        autoplaySpeed: 4500,
        speed: 1200,
        draggable : true,
        /* responsive: [
            {
                breakpoint: 768,
                settings: 'unslick'
            }
        ] */
    }
    //$(".winner_zone_slide").slick(settings);
    $("#slide_ul").slick(settings);
	
});


</script>
<style>
.last_tr{
    background-color: #efefef;
}
.winner_zone_slide{
    /* height:55px;
    overflow:hidden; */
    
}
#slide_ul .li{
    line-height:20px;
}
.kakao_logo{
    background:url("/resources/img/kakaolink_btn_small.png") no-repeat center;
    background-size:15px 15px;
    width:15px;
    height:15px;
    margin:0;
    padding:0;
    position: relative;
    top: 2px;
    margin-right:2px;
}

.naver_logo{
    background:url("/resources/img/naver_id_small.png") no-repeat right center;;
    background-size:15px 15px;
    width:15px;
    height:15px;
    margin:0;
    padding:0;
    position: relative;
    top: 2px;
    margin-right:2px;
}
</style>
<div class="container">
  <div class="page-header">
    <h3 style="display: inline-block;">[${lastResult.gno } 회차]</h3>&nbsp;<label><fmt:formatDate value="${lastResult.gdate }" pattern="yyyy.MM.dd"/></label> 
    <h3><strong>${lastResult.no1 }, ${lastResult.no2 }, ${lastResult.no3 }, ${lastResult.no4 }, ${lastResult.no5 }, ${lastResult.no6 }</strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${lastResult.bonus }</h3>
    <h5>${lastResult.firstCount }명&nbsp;&nbsp;&nbsp; ${lastResult.firstAmount }</h5>
    <div class="winner_zone_slide">
        <ul id="slide_ul">
            <c:forEach items="${allWinnerList }" var="winner">
                <li>
                    <c:choose>
                        <c:when test="${winner.udiv eq 'KAKAO' }">
                            <label class="kakao_logo"></label>
                        </c:when>
                        <c:when test="${winner.udiv eq 'NAVER' }">
                            <label class="naver_logo"></label>
                        </c:when>
                        <c:otherwise></c:otherwise>
                    </c:choose>
                    ${winner.maskUname }님 ${winner.winnerMessage }
                </li>
            </c:forEach>
        </ul>
    </div>
    
    <c:choose>
        <c:when test="${isSignin == true }">
            <c:choose>
	            <c:when test="${memberDivEnum == 'KAKAO' }">
	                <span><a href="/login/signin?udiv=NAVER"><img src="/resources/img/naver_id_login_green.png" style="height:30px;" /></a></span>
	            </c:when>
	            <c:when test="${memberDivEnum == 'NAVER' }">
	                <span><a href="/login/signin?udiv=KAKAO"><img src="/resources/img/kakao_login_small.png" /></a></span>
	            </c:when>
            </c:choose>
        </c:when>
        <c:when test="${isSignin == false }">
            <span><a href="/login/signin?udiv=KAKAO"><img src="/resources/img/kakao_login_small.png" /></a></span>
            <span><a href="/login/signin?udiv=NAVER"><img src="/resources/img/naver_id_login_green.png" style="height:30px;" /></a></span>
        </c:when>
    </c:choose>
    
    <span id="kakao-add-channel-button"></span>
    <!-- <span id="kakao-talk-channel-chat-button"></span> -->
  </div>
  <h4>로또번호는 아몰랑에서 생성해 주세요!</h4>
  <h4>매주 토요일 추첨이 완료 되면 당첨여부를 알려드립니다!</h4>
  <p></p>
  <form id="lucky_form" class="form-inline" method="post" action="/create.json">
  <div class="form-group">
    <label for="nick">이름</label>
    <input type="text" class="form-control" id="nick" name="nick" placeholder="Big Boss" value="${nick }" style="ime-mode:disabled;">
  </div>
  <div class="form-group">
      <label for="count">생성 개수</label>
      <select id="count" name="count" class="form-control">          
      	  <option <c:if test="${cookie.gcnt.value == 1}">selected</c:if>>1</option>
      	  <option <c:if test="${cookie.gcnt.value == 3}">selected</c:if>>3</option>
      	  <option <c:if test="${cookie.gcnt.value == 4}">selected</c:if>>4</option>
      	  <option <c:if test="${cookie.gcnt.value == 5}">selected</c:if><c:if test="${cookie.gcnt.value eq null}">selected</c:if>>5</option>
      	  <option <c:if test="${cookie.gcnt.value == 10}">selected</c:if>>10</option>
	  </select>	   
  </div>
  <div class="form-group">
    <input type="submit" value="생성" class="form-control btn-success">
  </div>
</form>
<div>
	<h5></h5> 
	<table id="yournums" class="table table-hover">
	    <thead>
		    <tr>
		        <th>No.</th>
		        <th>Lucky numbers</th>
		        <th>When</th>
		    </tr>
	    </thead>
	    <tbody>
	    	<!-- 지난주 당첨번호가 있으면 -->
	        	<c:forEach items="${myLuckylogList }" var="luckylogList" varStatus="status">
	        		<tr class="last_tr">
	        		    <td>${luckylogList.gno }</td>
	        			<td>
	        				<c:forEach items="${luckylogList.luckynums }" var="lno">
	        				    <c:choose>
	        					<c:when test="${lno ==  lastResult.no1 || lno == lastResult.no2 || lno == lastResult.no3 || lno == lastResult.no4 || lno == lastResult.no5 || lno == lastResult.no6}">
	        						<span style="padding-right: 5px;font-weight: bold;color:orange;">${lno }</span>
	        					</c:when>
	        					<c:otherwise>
	        						<span style="padding-right: 5px;">${lno }</span>
	        					</c:otherwise>
	        					</c:choose>
	        				</c:forEach>
	        			</td>
	        			<td><fmt:formatDate value="${luckylogList.regdate }" pattern="yyyy.MM.dd HH:mm:ss"/></td>
	        		</tr>
	        		<c:if test="${status.last }">
	        		    <tr><td colspan="3" style="height: 50px;"></td></tr>
	        		</c:if>
	        	</c:forEach>
	        
	        <!-- 다음주 기대 번호 -->
	    	<c:set var="next">${second.regdatetime}</c:set>
	        <c:forEach items="${mylist }" var="mylist" varStatus="status">
	        	
	        	<c:if test="${next - mylist.regdatetime > 3}">
	        		<tr style="height:3px;"><td colspan="3" style="background: green;padding:0px;"></td></tr>
	        	</c:if>
	        	
	        	<tr>
	        		<td>${mylist.gno }</td>
	        		<td>${mylist.no1 }, ${mylist.no2 }, ${mylist.no3 }, ${mylist.no4 }, ${mylist.no5 }, ${mylist.no6 }</td>
	        		<td><fmt:formatDate value="${mylist.regdate }" pattern="yyyy.MM.dd HH:mm:ss"/> </td>
	        	</tr>
	        	
	        	
	        	<c:set var="next">${mylist.regdatetime }</c:set>
	        </c:forEach>
	    </tbody>
	</table>
</div>
</div>

<style>
.instabtn{
    text-align: center;
	position: relative;
	top: -20px;
	margin-bottom: 40px;
}

</style>

