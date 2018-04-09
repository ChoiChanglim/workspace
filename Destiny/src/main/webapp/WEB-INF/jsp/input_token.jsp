<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta property="fb:pages" content="1418218805139858" />
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet" href="${info.contextPath }/resources/css/bootstrap-tagsinput.css">

<script src="${info.contextPath }/resources/js/jquery-1.11.3.min.js"></script>
<script src="${info.contextPath }/resources/js/bootstrap-tagsinput.min.js"></script>

<title>INOUT TOKEN</title>

<script>
jQuery(document).ready(function(){
	
	jQuery("#submit").click(function(){
		var tags = jQuery("input[name='tags']").val();
		alert(tags);
	});
});
</script>
</head>
<body>
<p><a href="https://bootstrap-tagsinput.github.io/bootstrap-tagsinput/examples/" target="_blank">https://bootstrap-tagsinput.github.io/bootstrap-tagsinput/examples/</a></p>

<input id="tags" name="tags" type="text" data-role="tagsinput" value="Amsterdam,Washington,Sydney,Beijing,Cairo" />
<br/>
<button id="submit">확인</button>
</body>
</html>
