<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/timedtask-manager.js"' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/thread-manager.js"' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/priorityChannelOms-manager.js"' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/chooseOption-manager.js"' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/chooseOptionDataGrid-manager.js"' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/chooseOptionOcpDataGrid-manager.js"' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>
<body>
<div  id="tabs">
                   <ul>
					<li><a href="#tabs_1">定时任务配置查询及修改</a></li>
					<li><a href="#tabs_2">线程池查询及维护</a></li>
					<li><a href="#tabs_3">直连店铺过仓优先级维护</a></li>
					<!-- <li><a href="#tabs_4">过仓直连非直连维护</a></li> -->
					<li><a href="#tabs_5">设置利丰批次数</a></li>
					<li><a href="#tabs_6">占用库存维护</a></li>
					<li><a href="#tabs_7">利丰出库单导入</a></li>
				</ul>
					<div id="tabs_1">
					    <div>
							<form name="searchForm" id="searchForm" >
								<table>
									<tr>
										<td class="label"><s:text name="定时任务类名:"></s:text></td> 
										<td><input loxiaType="input" trim="true" id="beanName" name="ssTask.beanName"/></td> 
										<td class="label"><s:text name="定时任务方法名:"></s:text></td>
										<td><input loxiaType="input" trim="true" id="methodName" name="ssTask.methodName"/></td>
									</tr>
								</table>
							</form>
							<button  loxiaType="button" class="confirm" id="search"><s:text name="查询"></s:text> </button>
							<button  loxiaType="button" id="reset"><s:text name="重设"></s:text> </button>
							</br>
							</br>
							<table id="tbl-timedtaskList"></table>
							<div id="pager_query"></div>
							</div>
							</br>
							</br>
							<div>
							<form>
								<table>
									<tr>
										
										<td class="label"><input type="hidden"  id="id" name="id"/><s:text name="定时任务类名:"></s:text></td>
										<td><input loxiaType="input" trim="true" disabled="true" id="beanName_1" name="ssTask.beanName"/> </td>
										<td class="label"><s:text name="方法名:"></s:text></td>
										<td><input loxiaType="input" trim="true" disabled="true" id="methodName_1" name="ssTask.methodName"/></td>
										<%-- <td class="label"><s:text name="修改后的执行时间:"></s:text></td>
										<td><input loxiaType="input" trim="true" id="timeExp" name="ssTask.timeExp"/></td>
										<td style="color: red;font-weight: bold;" id="remind">*注意时间的格式</td> --%>
									</tr>
									<tr>
										
										<td class="label"><s:text name="修改后的执行时间:"></s:text></td>
										<td><input loxiaType="input" trim="true" id="timeExp" name="ssTask.timeExp"/></td>
										<td style="color: red;font-weight: bold;" id="remind">*注意时间的格式</td>
									</tr>
									<tr>
										
										<td class="label"><s:text name="是否启用:"></s:text></td>
										<td><input loxiaType="input" trim="true" id="node" name="ssTask.node"/></td>
										<td style="color: red;font-weight: bold;" id="remind">*请输入数字：0 或者 1 ；0代表禁用，1代表启用；</td>
									</tr>
								</table>
							</form>
							</br>
							<button  loxiaType="button" class="confirm" id="update"><s:text name="修改时间"></s:text> </button>
							</div>
					
					</div>
					<div id="tabs_2">
							<div>
									<%-- <form name="searchForm" id="searchForm">
										<table>
											<tr>
												<td class="label"><s:text name="线程编码:"></s:text></td> 
												<td><input loxiaType="input" trim="true" id="beanName" name="ssTask.beanName"/></td> 
											</tr>
										</table>
									</form>
									<button  loxiaType="button" class="confirm" id="search"><s:text name="查询"></s:text> </button>
									<button  loxiaType="button" id="reset"><s:text name="重设"></s:text> </button> 
									</br>
									</br>--%>
									<table id="tbl-threadList"></table>
									<div id="pager_query2"></div>
									</div>
									</br>
									</br>
									 <div>
									<form>
										<table>
											<tr>
												<td class="label"><s:text name="zkNode:"></s:text></td>
												<td><input loxiaType="input" trim="true"  value="/notice/logchange/wms/cfg/thread" id="path" name="path"/></td>
												<td class="label"><s:text name="线程池编码:"></s:text></td>
												<td><input loxiaType="input" trim="true" id="threadConfig_threadCode" name="threadConfig.threadCode"/></td>
											</tr>
											<tr>
												
												<td class="label"><s:text name="线程池大小:"></s:text></td>
												<td><input loxiaType="input" trim="true" id="threadConfig_threadCount" name="threadConfig.threadCount"/></td>
												<td class="label"><s:text name="系统编码:"></s:text></td>
												<td><input loxiaType="input" trim="true" id="threadConfig_sysKey" name="threadConfig.sysKey"/></td>
											</tr>
											<tr>
												<td  class="label"><s:text name="备注:"/></td>
				                                <td colspan="3"><textarea loxiaType="input" name="threadConfig.memo" max="255" id="threadConfig_memo" maxLength="255"></textarea></td>
											</tr>
										</table>
									</form>
									</br>
									<button  loxiaType="button" class="confirm" id="addupdate"><s:text name="新增"></s:text> </button>
									<button loxiaType="button" id="thread_reset"><s:text name="button.reset"/></button>
									</div> 
					
					</div>
					<div id="tabs_3">
							<div>
									<table id="tbl-PriorityChannelOmsList"></table>
									<div id="pager_query3"></div>
									</div>
									</br>
									</br>
									 <div>
									<form>
										<table>
											<tr>
												<td class="label"><s:text name="店铺:"></s:text></td>
												<td><input loxiaType="input" trim="true" id="priorityChannelOms_code" name="priorityChannelOms.code"/></td>
											</tr>
											<tr>
												<td class="label"><s:text name="百分比:"></s:text></td>
												<td><input loxiaType="input" trim="true" id="priorityChannelOms_qty" name="priorityChannelOms.qty"/></td>
											</tr>
										</table>
									</form>
									</br>
									<button  loxiaType="button" class="confirm" id="addupdate3"><s:text name="新增"></s:text> </button>
									<button loxiaType="button" id="thread_reset3"><s:text name="button.reset"/></button>
									</div> 
									<!-- 店铺优先级维护 -->
									 <div>
									<form>
										<table>
											<tr>
												<td class="label"><s:text name="直连创单配置优先级比例:"></s:text></td>
												<td><input loxiaType="input" trim="true" id="optionValue" name="optionValue"/></td>
												<td style="color: red;font-weight: bold;" id="remind">*格式必须为 ：*/* 形式,*为0-10的整数，并且两个*的值相加和为10</td>
											</tr>
										</table>
									</form>
									</br>
									<button  loxiaType="button" class="confirm" id="update32"><s:text name="修改"></s:text> </button>
									<button loxiaType="button" id="thread_reset32"><s:text name="button.reset"/></button>
									</div>
									<div>
									 <div>
									<form>
										<table>
											<tr>
												<td class="label"><s:text name="直连批次大小:"></s:text></td>
												<td><input loxiaType="input" trim="true" id="oms_optionValue" name="optionValue"/></td>
											</tr>
										</table>
									</form>
									</br>
									<button  loxiaType="button" class="confirm" id=oms_update4><s:text name="修改"></s:text> </button>
									</div> 
									<!-- 店铺优先级维护 -->
									 <div>
									<form>
										<table>
											<tr>
												<td class="label"><s:text name="非直连批次大小:"></s:text></td>
												<td><input loxiaType="input" trim="true" id="pac_optionValue" name="optionValue"/></td>
											</tr>
										</table>
									</form>
									</br>
									<button  loxiaType="button" class="confirm" id="pac_update4"><s:text name="修改"></s:text> </button>
									</div>
					
					</div>
					
					</div>
				<!-- 	<div id="tabs_4">
							
					</div> -->
					<div id="tabs_5">
							<div>
									<table id="tbl-ChooseOptionList"></table>
									<!-- <div id="pager_query5"></div> -->
									</div>
					
					</div>
					<div id="tabs_6">
							<div>
									<table id="tbl-ChooseOptionList6"></table>
									 <div id="pager_query6"></div> 
									</div>
					
					</div>

					<div id="tabs_7">
							<div>
								<table>
									<tr>
										<td>
										<form method="post" enctype="multipart/form-data" id="flTxtForm" name="flTxtForm" target="upload">
											<input type="file" loxiaType="input" id="flTxt" name="flTxt" style="width: 300px;"/>
										</form>
										</td>
										<td>
											<button loxiaType="button" class="confirm" id="importLfTxt"><s:text name="导入出库单信息"></s:text> </button>
										</td>
									</tr>
									<tr>
										<td><b style="color: red;">文件格式：.txt</b><br/>
												  数据格式：S200187498237	SF	767093959198	1.11		M36-25-16	TEST_H_H_H1001 <b style="color: red;">以tab键分割</b><br/>
												  数据解释：出库单号（必填） 运输商（必填） 运单号（必填） 重量（必填） CTN_TYPE（非必填） 数据唯一码（必填，数据库全局唯一）
									</tr>
								</table>
						</div>
						</div>
<iframe id="tbl-ChooseOptionList2-id" class="hidden" target="_blank"></iframe>
<input type="hidden" trim="true" value="/notice/logchange/wms/cfg/thread" id="path2" name="path"/>
					<div id="showType2" style="display:none;">
					<table>
								<tr>
												<td class="label"><s:text name="线程池大小:"></s:text></td>
												<td><input loxiaType="input" trim="true" id="optionValue2" name="threadConfig.threadCount"/></td>
											</tr>
							</table>
						<button type="button" loxiaType="button" id="updateType2" onclick="updateType2()">修改</button>
					</div>
<iframe id="tbl-PriorityChannelOmsList-id" class="hidden" target="_blank"></iframe>
					<div id="showType" style="display:none;">
					<input type="hidden" id="typeId"/>
					<table>
								<tr>
												<td class="label"><s:text name="店铺:"></s:text></td>
												<td><input loxiaType="input" trim="true" id="priorityChannelOms_code2" name="priorityChannelOms.code"/></td>
											</tr>
											<tr>
												<td class="label"><s:text name="百分比:"></s:text></td>
												<td><input loxiaType="input" trim="true" id="priorityChannelOms_qty2" name="priorityChannelOms.qty"/></td>
											</tr>
							</table>
						<button type="button" loxiaType="button" id="updateType" onclick="updateType()">修改</button>
						<button type="button" loxiaType="button" id="btnTypeClose" >取消</button>
					</div>
<iframe id="tbl-ChooseOptionList-id" class="hidden" target="_blank"></iframe>
<input type="hidden" id="tbl-ChooseOptionList-optionKey"/>
					<div id="showType5" style="display:none;">
					<table>
								<tr>
												<td class="label"><s:text name="value:"></s:text></td>
												<td><input loxiaType="input" trim="true" id="optionValue5" name="optionValue"/></td>
											</tr>
							</table>
						<button type="button" loxiaType="button" id="updateType5" onclick="updateType5()">修改</button>
						<button type="button" loxiaType="button" id="btnTypeClose5" >取消</button>
					</div>
<iframe id="tbl-ChooseOptionList6-id" class="hidden" target="_blank"></iframe>
					<div id="showType6" style="display:none;">
					<table>
								<tr>
												<td class="label"><s:text name="value:"></s:text></td>
												<td><input loxiaType="input" trim="true" id="optionValue6" name="optionValue"/></td>
											</tr>
							</table>
						<button type="button" loxiaType="button" id="updateType6" onclick="updateType6()">修改</button>
						<button type="button" loxiaType="button" id="btnTypeClose6" >取消</button>
					</div>	
</body>
</html>