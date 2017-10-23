function getJson(url, method, param, callback){
	$.ajax({
		  url: url,
		  method:method,
		  data:param,
		  dataType : "json",
		  success:callback
		});
}

