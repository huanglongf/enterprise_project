<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
</head>
<body>
	<script type="text/javascript">
		function getJreVersion() {
			var result = document.jreTest.getJreVersion(1);
			document.getElementById("jreVersion").innerHTML = result;
			return result;
		}
	</script>
	<APPLET name="jreTest" CODE="com.jumbo.util.comm.JreTestApplet.class" ARCHIVE="<%=request.getContextPath()%>/common/jreTestApplet.jar" WIDTH="0" HEIGHT="0"> </APPLET>
	<div>
		当前java版本：<span id="jreVersion" style=""></span>
		<button onclick="getJreVersion();">测试</button>
	</div>
	<p>
		JRE 版本 ：1.7 ，请使用<a href="https://scm.baozun.com/scm" target="main">https://scm.baozun.com/scm</a>访问系统
	</p>
	<p>
		JRE 版本 ：1.6 ，请使用<a href="http://scm.baozun.cn/scm" target="main">http://scm.baozun.cn/scm</a>访问系统
	</p>
	<p>
		系统版本     : dd326909ce6aa61725325ae345d57fbd0f3f0e25 
    </p>
</body>