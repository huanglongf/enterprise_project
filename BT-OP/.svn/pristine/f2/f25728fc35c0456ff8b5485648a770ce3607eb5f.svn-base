<%@ page language= "java" import= "java.util.*" pageEncoding= "utf-8" %>
<%@ taglib prefix= "c" uri= "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri= "http://java.sun.com/jsp/jstl/fmt" prefix= "fmt" %>
<%@ taglib prefix= "fns" uri= "http://java.sun.com/jsp/jstl/functions" %>
<% String basePath= request.getScheme()+ "://"+ request.getServerName()+ ":"+ request.getServerPort()+ request.getContextPath()+ "/"; %>
<link rel= "alternate icon" type= "image/png" href= "<%=basePath %>/assets/i/favicon.ico" >
<!-- root -->
<script type= "text/javascript" >
	var root= "<%=basePath %>";
	// NGINX配置地址
	var FileUpload;
	var FileDown;
	function openDiv(url){$("#div_view").load(url); };
    function openDivs(url, dat){
	  	$.ajax({
			type: "POST",
           	url: url,
           	dataType: "text",
           	data: dat,
    		cache: false,
    		contentType: "application/x-www-form-urlencoded;charset=UTF-8",
           	success: function (data){$("#div_view").html(data); }
    		
	  	});
	  	
    };
</script>
<c:set var= "root" value= "<%=basePath %>" ></c:set>