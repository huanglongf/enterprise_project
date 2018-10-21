<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/yuriy.jsp"%>
<meta name="viewport" content="width=device-width, initial-scale=1">
</head>

<script type="text/javascript">
	var skus={"name":"傅红雪","age":"24","profession":"刺客"};
	function test(){
    	var ifsku = skus['name1'];
    	alert(ifsku);
	};
</script>
<body>
	<button onclick="test();">点我</button>
</body>
</html>
