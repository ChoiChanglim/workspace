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
});
</script>
<div class="container">
  <div class="page-header">
    <h3>[${lastResult.gno } 회차]</h3> 
    <h3><strong>${lastResult.no1 }, ${lastResult.no2 }, ${lastResult.no3 }, ${lastResult.no4 }, ${lastResult.no5 }, ${lastResult.no6 }</strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${lastResult.bonus }</h3>
  </div>
  
  <form id="lucky_form" class="form-inline" method="post" action="/create.json">
  <div class="form-group">
    <label for="nick">아이디</label>
    <input type="text" class="form-control" id="nick" name="nick" placeholder="Big Boss" value="${nick }" style="ime-mode:disabled;">
  </div>
  <div class="form-group">
      <label for="count">몇 게임?</label>
      <select id="count" name="count" class="form-control">
      	  <option>1</option>
      	  <option selected>3</option>
      	  <option>5</option>
	  </select>	   
  </div>
  <div class="form-group">
  <!-- <button type="submit" class="btn btn-default">생성</button> -->
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
	        	<c:forEach items="${myLuckyList }" var="luckyList">
	        		<tr>
	        			<td colspan="3" style="text-align: center;font-size: 12pt; ">
	        				<c:forEach items="${luckyList }" var="lno">
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
	        		</tr>
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



