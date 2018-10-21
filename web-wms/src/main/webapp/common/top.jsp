<%@taglib prefix="s" uri="/struts-tags"%>
<%@page contentType="text/html;charset=UTF-8"%>
<style>
</style>
<script type="text/javascript" src="<s:url value='/scripts/common/top.js' includeParams='none' encode='false'/>"></script>
<div class="top-container">
	<div id="infobar" style="color: white;">
		<span  id="userinfo" lgname="<s:property value="user.loginName" />"  sysdate="<s:property value="sysdate" />" ouName ="<s:property value="currentOu.name" /> " >用户名：<s:property value="user.loginName" /> <a id="roleinfo" href="#" style="color: orange;"> 
			<input name="userRoleId" type="hidden" value="<s:property value="currentUserRole.id"/>" id="userRoleId" />
				<input name="roleId" type="hidden" value="<s:property value="currentRole.id"/>" id="roleId" /> <input name="ouId" type="hidden" value="<s:property value="currentUserRole.ou.id"/>" id="ouId" /> [角色名：<span id="rolename"><s:property
						value="currentRole.name"
					/> </span> 组织：<span id="orgname"><s:property value="currentOu.name" /> </span>]
		</a> <a id="copysession" href="#" style="color: white;">复制会话</a><a style="color: white;" id="exit" href="#">退出</a>
		</span>
	</div>
	<div id="navigation" class="ui-corner-all">
		<div class="welcome ui-corner-left">
		</div>
		<div class="topmenu" id="top-menu"></div>
		<div class="stop">
			<!--  -->
		</div>
<!-- 		<div id="functionbar" class="ui-corner-right">
			<input placeholder="请输入功能码/单据号/会员号/商品编码..." />
		</div> -->
		<div class="ui-corner-right">

		</div>
	</div>
	<div id="dialog-chgrole">
		<h3>用户信息</h3>
		<span class="label">用户名</span><span class="field1"><s:property value="user.loginName" /> </span> <span class="label">真实姓名</span><span class="field1"><s:property value="user.userName" /> </span>
		<h3 id="currentRole">
			<s:property value="currentRole.name" />
		</h3>
		<table id="tbl-rolelist"></table>
		<p></p>
		<div class="buttonlist">
			<button type="button" loxiaType="button" class="confirm">确定</button>
			<button type="button" loxiaType="button" class="cancel" onclick='$j("#dialog-chgrole").dialog("close");'>取消</button>
		</div>
	</div>
</div>