<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../../common/meta.jsp"%>
<title>配货批次分配</title>
<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/pickinglist-plan.js' includeParams='none' encode='false'/>"></script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
<!-- 这里是页面内容区 -->
	<div id="div-sta-list">
		<form id="form_query">
			<table width="80%" id="filterTable">
				<tr>
					<td class="label" width="8%">创建时间</td>
					<td width="18%">											  
						<input loxiaType="date" trim="true" name="biginCreateDate" showTime="true" id='biginCreateDate'/>
					</td>
					<td class="label" style="text-align:center"><s:text name="label.warehouse.pl.to"/></td>
					<td width="18%"><input loxiaType="date" trim="true" name="endCreateDate" showTime="true" id='endCreateDate'/></td>
					<td class="label" width="8%">分配时间</td>
					<td width="18%">											  
						<input loxiaType="date" trim="true" name="biginPickDate" showTime="true" id='executionTime'/>
					</td>
					<td class="label" style="text-align:center"><s:text name="label.warehouse.pl.to"/></td>
					<td width="20%">
						<input loxiaType="date" trim="true" name="endPickDate" showTime="true" id='endExecutionTime'/>
				   </td>
				</tr>
				<tr>
					<td class="label" width="8%">配货批次号</td>
					<td width="18%"><input loxiaType="input" trim="true" name="pickingListCommand.code" id='code'/></td>
					<td class="label" width="10%" style="text-align:center">登录账号</td>
					<td width="18%"><input loxiaType="input" trim="true" name="pickingListCommand.jobNumber" id='jubNumber'/></td>
					<td class="label" width="8%">配货状态：</td>
				   <td width="18%">
				   		<select loxiaType="select" name="pickingListCommand.statusInt" id="select1">
				   		    <option value="">--请选择--</option>
							<option value="2">未开始配货</option>
							<option value="8">部分完成</option>
							<option value="10">全部完成</option>
						</select>
				   </td>
				   <td class="label" width="8%">拣货状态：</td>
				   <td width="20%">
				   		<select loxiaType="select" name="pickingListCommand.nodeType" id="select2">
				   		    <option value="">--请选择--</option>
							<option value="1">未开始拣货</option>
							<option value="13">正在拣货</option>
							<option value="14">拣货完成</option>
						</select>
				   </td>
				</tr>

			</table>
		</form>
	</div>
		<div class="buttonlist">
			<button loxiaType="button" class="confirm" id="search"><s:text name="button.search"/></button>
			<button loxiaType="button" id="reset"><s:text name="button.reset"/></button>
			<span style="color: red;font-size: 14px; margin-left: 20px;" class="label">默认查询近三个月的记录，否则请加上时间条件！</span>
		</div>
		<table id="tbl-orderList"></table>
		<div id="pager"></div>
   <div class="buttonlist"></div>
	<table id="filterTable1" width ="1200px" style="margin-top: 10px;" > 
	    <tr class="label"><td>配货批分配</td></tr>
	    <tr><td><br/></td></tr>
		<tr>
			<td class="label" width = "8%">登录账号：</td>
			<td width = "15%">
				<input loxiaType="input" id = "jubNumber1" name="jubNumber1" trim="true"/>
			</td>
			<td class="label" width = "8%">拣货状态扫描：</td>
			<td width = "15%">
				<input loxiaType="input" id = "batchStatus" name="jubNumber1" trim="true"/>
			</td>
			<td colspan="2" width = "40%"> <span style="color: red;font-size: 14px; margin-left: 20px;" class="label"> &nbsp;&nbsp;1 代表开始拣货，2 代表拣货完成</span></td>
		</tr>
		<tr>
			<td class="label" width = "8%">批次号：</td>
			<td >
				<input loxiaType="input" id = "batchCode" name="batchCode" trim="true"/>
			</td>
			<td class="label" width = "8%">确认OK条码：</td>
			<td >
				<input loxiaType="input" id = "isSure" name="isSure" trim="true"/>
			</td>
			<td class="label" width = "8%">拣货状态：</td>
			<td width = "15%">
				<select loxiaType="select" name="pickType" id = "pickType">
					<option value="13">开始拣货</option>
					<option value="14">拣货完成</option>
				</select>
			</td>
			<td></td>
		</tr>
	</table>
	<div class="buttonlist">
		<button type="button" id="sure" loxiaType="button" class="confirm">确定</button>
		<button loxiaType="button" id="reset1"><s:text name="button.reset"/></button>
	</div>
</body>
</html>