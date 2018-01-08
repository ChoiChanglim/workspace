<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script>
function download(ukey){
	var url = "/download?ukey="+ukey;
	window.open(url);
}
</script>
<h2>이미지를 다운받고, 이미지를 클릭하여 인스타그램에 자랑해 보세요.</h2>
<button onclick="download('${ukey}')">이미지 다운로드</button>
<br>
<a href="${info.contextPath }/instagram/auth/getAuth" target="_blank"><img src="${url }" alt="인스타에 공유하세요"/></a><br>

