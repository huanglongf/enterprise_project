<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
<meta charset="UTF-8">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<title>LMIS</title>
<meta name="description" content="overview &amp; stats" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%@ include file="/lmis/yuriy.jsp"%>
<style type="text/css">
label {
	margin-right: 15px;
	font-size: 14px;
}
</style>
</head>
<body>
	<input id="lastPage" name="lastPage" style='display: none'
		value='${queryParam.maxResult}'>
	<input id="master_id" name="master_id" style='display: none'
		value='${queryParam.master_id}'>
	<input id="lastTotalCount" name="lastTotalCount" style='display: none'
		value='${totalSize}'>
	
	<div class= "title_div" id= "sc_title" style='height:80px'>
		<form id="diff">
			<table class= "table table-striped" style= "table-layout: fixed;overflow-x:show;" >
		   			<thead align="center">
						<tr class= "table_head_line"  >
						<td >
						<input type="checkbox" id="checkAll" onclick="inverseCkb('ckb')"/>
							<td class="td_cs"  >序号</td>
							<!-- <td class="td_cs"  >核销周期</td> -->
							<td class="td_cs"  >月结账号</td>
							<td class="td_cs"  >发货时间</td>
							<td class="td_cs"  >运单号</td>
							<td class="td_cs"  >发货重量（kg）</td>
							<td class="td_cs"  >体积(长*宽*高）</td>
							<td class="td_cs"  >始发地（省）</td>
							<td class="td_cs"  >始发地（市）</td>
							<td class="td_cs"  >始发地（区）</td>
							<td class="td_cs"  >目的地(省)</td>
							<td class="td_cs"  >目的地(市)</td>
							<td class="td_cs"  >目的地(区)</td>
							<td class="td_cs"  >计费重量（kg）</td>
							<td class="td_cs"  >供应商名称</td>
							<td class="td_cs"  >产品类型</td>
							<td class="td_cs"  >保值</td>
							<td class="td_cs"  >运费</td>
							<td class="td_cs"  >其他增值服务费</td>
							<td class="td_cs"  >合计费用</td>
							<td class="td_cs"  >发货仓</td>
							<td class="td_cs"  >所属店铺</td>
							<td class="td_cs"  >成本中心代码</td>
							<td class="td_cs"  >前置单据号</td>
							<td class="td_cs"  >平台订单号</td>
							<td class="td_cs"  >耗材sku编码</td>
							<td class="td_cs"  >长（mm）</td>
							<td class="td_cs"  >宽（mm)</td>
							<td class="td_cs"  >高(mm)</td>
							<td class="td_cs"  >体积(mm3)</td>
							<td class="td_cs"  >发货时间</td>
							<td class="td_cs"  >始发地（省）</td>
							<td class="td_cs"  >始发地（市）</td>
							<td class="td_cs"  >目的地（省）</td>
							<td class="td_cs"  >目的地（市）</td>
							<td class="td_cs"  >发货重量</td>
							<td class="td_cs"  >物流商名称</td>
							<td class="td_cs"  >产品类型</td>
							<td class="td_cs"  >保价货值</td>
							<td class="td_cs"  >体积计费重量（mm3）</td>
							<td class="td_cs"  >计费重量（kg)</td>
							<td class="td_cs"  >首重</td>
							<td class="td_cs"  >续重</td>
							<td class="td_cs"  >折扣</td>
							<td class="td_cs"  >标准运费</td>
							<td class="td_cs"  >折扣运费</td>
							<td class="td_cs"  >保价费</td>
							<td class="td_cs"  >附加费&服务费</td>
							<td class="td_cs"  >最终费用</td>
							<td class="td_cs"  >是否核销</td>
							<td class="td_cs"  >未核销原因备注</td>
							<td class="td_cs"  >备注</td>
							<td class="td_cs"  >是否生成账单</td>
						</tr>
						<!--  --><tr>
						<td style='width: 50px'>
							<td class="td_ch"  ></td>
						<!-- <td class="td_cs"  ><input onkeyup="showMsg(this)" name="billingCycle" /></td> -->
						<form id='pageTwoQuery'>
						<td class="td_cs"  ><input  id='month_account' onkeyup="showMsg(this)" name="month_account_test" style='display:none' />
						 </td> 
						<td class="td_cs"  ><input onkeyup="showMsg(this)" name="transport_time" value='<fmt:formatDate value="${queryParam.transport_time}" pattern="yyyy-MM-dd HH:mm:ss" />'></td>
						<td class="td_cs"  ><input onkeyup="showMsg(this)" name="waybill" value='${queryParam.waybill}'/></td>
						<td class="td_cs"  ><input onkeyup="showMsg(this)" name="transport_weight" value='${queryParam.transport_weight}'/></td>
						<td class="td_cs"  ><input onkeyup="showMsg(this)" name="transport_volumn" value='${queryParam.transport_volumn}' /></td>
						<td class="td_cs"  ><input onkeyup="showMsg(this)" name="origin_province" value='${queryParam.origin_province}'/></td>
						<td class="td_cs"  ><input onkeyup="showMsg(this)" name="origin_city" value='${queryParam.origin_city}'/></td>
						<td class="td_cs"  ><input onkeyup="showMsg(this)" name="origin_state" value='${queryParam.origin_state}'/></td>
						<td class="td_cs"  ><input onkeyup="showMsg(this)" name="dest_province" value='${queryParam.dest_province}'/></td>
						<td class="td_cs"  ><input onkeyup="showMsg(this)" name="dest_city" value='${queryParam.dest_city}'/></td>
						<td class="td_cs"  ><input onkeyup="showMsg(this)" name="dest_state" value='${queryParam.dest_state}'/></td>
						<td class="td_cs"  ><input onkeyup="showMsg(this)" name="charged_weight" value='${queryParam.charged_weight}'/></td>
						<td class="td_cs"  ><input onkeyup="showMsg(this)" name="express_name" value='${queryParam.express_name}'/></td>
						<td class="td_cs"  ><input onkeyup="showMsg(this)" name="producttype_name" value='${queryParam.producttype_name}'/></td>
						<td class="td_cs"  ><input onkeyup="showMsg(this)" name="insurance" value='${queryParam.insurance}'/></td>
						<td class="td_cs"  ><input onkeyup="showMsg(this)" name="freight" value='${queryParam.freight}' /></td>
						<td class="td_cs"  ><input onkeyup="showMsg(this)" name="other_value_added_service_charges"  value='${queryParam.other_value_added_service_charges}'/></td>
						<td class="td_cs"  ><input onkeyup="showMsg(this)" name="total_charge" value='${queryParam.total_charge}'/></td>
						<td class="td_cs"  ><input onkeyup="showMsg(this)" name="transport_warehouse" value='${queryParam.transport_warehouse}'/></td>
						<td class="td_cs"  ><input onkeyup="showMsg(this)" name="store" value='${queryParam.store}' /></td>
						<td class="td_cs"  ><input onkeyup="showMsg(this)" name="cost_center" value='${queryParam.cost_center}'/></td>
						<td class="td_cs"  ><input onkeyup="showMsg(this)" name="epistatic_order" value='${queryParam.epistatic_order}'/></td>
						<td class="td_cs"  ><input onkeyup="showMsg(this)" name="platform_no" value='${queryParam.platform_no}'/></td>
						<td class="td_cs"  ><input onkeyup="showMsg(this)" name="sku_number" value='${queryParam.sku_number}'/></td>
						<td class="td_cs"  ><input onkeyup="showMsg(this)" name="length" value='${queryParam.length}'/></td>
						<td class="td_cs"  ><input onkeyup="showMsg(this)" name="width" value='${queryParam.width}'/></td>
						<td class="td_cs"  ><input onkeyup="showMsg(this)" name="height" value='${queryParam.height}'/></td>
						<td class="td_cs"  ><input onkeyup="showMsg(this)" name="volumn" value='${queryParam.volumn}'/></td>
						<td class="td_cs"  ><input onkeyup="showMsg(this)" name="transport_time1" value='<fmt:formatDate value="${queryParam.transport_time1}" pattern="yyyy-MM-dd HH:mm:ss" />'/></td>
						<td class="td_cs"  ><input onkeyup="showMsg(this)" name="origin_province1" value='${queryParam.origin_province1}'/></td>
						<td class="td_cs"  ><input onkeyup="showMsg(this)" name="origin_city1" value='${queryParam.origin_city1}'/></td>
						<td class="td_cs"  ><input onkeyup="showMsg(this)" name="dest_province1" value='${queryParam.dest_province1}'/></td>
						<td class="td_cs"  ><input onkeyup="showMsg(this)" name="dest_city1" value='${queryParam.dest_city1}'/></td>
						<td class="td_cs"  ><input onkeyup="showMsg(this)" name="transport_weight1" value='${queryParam.transport_weight1}'/></td>
						<td class="td_cs"  ><input onkeyup="showMsg(this)" name="express_code1" value='${queryParam.express_code1}'/></td>
						<td class="td_cs"  ><input onkeyup="showMsg(this)" name="producttype_code1" value='${queryParam.producttype_code1}'/></td>
						<td class="td_cs"  ><input onkeyup="showMsg(this)" name="insurance1" value='${queryParam.insurance1}'/></td>
						<td class="td_cs"  ><input onkeyup="showMsg(this)" name="volumn_charged_weight" value='${queryParam.volumn_charged_weight}'/></td>
						<td class="td_cs"  ><input onkeyup="showMsg(this)" name="charged_weight1" value='${queryParam.charged_weight1}'/></td>
						<td class="td_cs"  ><input onkeyup="showMsg(this)" name="firstWeight" value='${queryParam.firstWeight}'/></td>
						<td class="td_cs"  ><input onkeyup="showMsg(this)" name="addedWeight" value='${queryParam.addedWeight}'/></td>
						<td class="td_cs"  ><input onkeyup="showMsg(this)" name="discount" value='${queryParam.discount}'/></td>
						<td class="td_cs"  ><input onkeyup="showMsg(this)" name="standard_freight" value='${queryParam.standard_freight}'/></td>
						<td class="td_cs"  ><input onkeyup="showMsg(this)" name="afterdiscount_freight" value='${queryParam.afterdiscount_freight}'/></td>
						<td class="td_cs"  ><input onkeyup="showMsg(this)" name="insurance_fee1" value='${queryParam.insurance_fee1}'/></td>
						<td class="td_cs"  ><input onkeyup="showMsg(this)" name="additional_fee" value='${queryParam.additional_fee}'/></td>
						<td class="td_cs"  ><input onkeyup="showMsg(this)" name="last_fee" value='${queryParam.last_fee}'/></td>
						<td class="td_cs"  ><input id='is_verification' name='test' onkeyup="showMsg(this)"  value='${queryParam.is_verification}' style='display:none'/></td>
						<td class="td_cs"  ><input onkeyup="showMsg(this)" name="reason" value='${queryParam.reason}'/></td>
						<td class="td_cs"  ><input onkeyup="showMsg(this)" name="remarks" value='${queryParam.remarks}'/></td>
						<td class="td_cs"  ><input onkeyup="showMsg(this)" name="test2" value='${queryParam.remarks}' style='display:none'/></td>
						<!-- <td class="td_cs"  ><input onkeyup="showMsg(this)" name="total_discount" /></td>
						<td class="td_cs"  ><input onkeyup="showMsg(this)" name="physical_accounting" /></td>
						<td class="td_cs"  ><input onkeyup="showMsg(this)" name="jp_num" /></td>
						<td class="td_cs"  ><input onkeyup="showMsg(this)" name="volumn_weight" /></td>
						<td class="td_cs"  ><input onkeyup="showMsg(this)" name="volumn_account_weight" /></td>
						<td class="td_cs"  ><input onkeyup="showMsg(this)" name="jf_weight" /></td>
						<td class="td_cs"  ><input onkeyup="showMsg(this)" name="first_weight" /></td>
						<td class="td_cs"  ><input onkeyup="showMsg(this)" name="first_weight_price" /></td>
						<td class="td_cs"  ><input onkeyup="showMsg(this)" name="added_weight" /></td>
						<td class="td_cs"  ><input onkeyup="showMsg(this)" name="added_weight_price" /></td>
						<td class="td_cs"  ><input onkeyup="showMsg(this)" name="account_id" /></td>
						<td class="td_cs"  ><input onkeyup="showMsg(this)" name="close_account" /></td> -->
						</form>
						</tr>
					</thead>
			</table>
			</form>
		</div>
		<div class= "tree_div" ></div>
		<div style= "height: 400px; overflow: auto; overflow: scroll; border: solid #F2F2F2 1px;" id= "sc_content" onscroll= "init_td('sc_title', 'sc_content')" >
			<table id= "table_content" class= "table table-hover table-striped" style= "table-layout: fixed;overflow-x:show;" >
		  		<tbody align= "center">
			  			<c:forEach items= "${pageView.records }" var= "records" varStatus='status'>
				  		<tr ondblclick='toUpdate("${records.id}")'>
					  		<td class="td_ch" ><input id="ckb" name="ckb" type="checkbox" value="${records.id}"></td>
							<td class="td_cs"  title="${status.count }">${status.count }</td>
							<!-- <td class="td_cs"  title="${records.billingCycle }">${records.billingCycle }</td> -->
							<td class="td_cs"  title="${records.month_account }">${records.month_account }</td>
							<td class="td_cs" title="${records.transport_time }">
							<fmt:formatDate value="${records.transport_time}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
							<td class="td_cs"  title="${records.waybill }">${records.waybill }</td>
							<td class="td_cs"  title="${records.transport_weight }">${records.transport_weight }</td>
							<td class="td_cs"  title="${records.transport_volumn }">${records.transport_volumn }</td>
							<td class="td_cs"  title="${records.origin_province }">${records.origin_province }</td>
							<td class="td_cs"  title="${records.origin_city }">${records.origin_city }</td>
							<td class="td_cs"  title="${records.origin_state }">${records.origin_state }</td>
							<td class="td_cs"  title="${records.dest_province }">${records.dest_province }</td>
							<td class="td_cs"  title="${records.dest_city }">${records.dest_city }</td>
							<td class="td_cs"  title="${records.dest_state }">${records.dest_state }</td>
							<td class="td_cs"  title="${records.charged_weight }">${records.charged_weight }</td>
							<td class="td_cs"   title="${records.express_name }">${records.express_name}</td>
							<td class="td_cs"   title="${records.producttype_name }">${records.producttype_name }</td>
							<td class="td_cs"   title="${records.insurance }">${records.insurance }</td>
							<td class="td_cs"   title="${records.freight }">${records.freight }</td>
							<td class="td_cs"   title="${records.other_value_added_service_charges }">${records.other_value_added_service_charges }</td>
							<td class="td_cs"   title="${records.total_charge }">${records.total_charge }</td>
							<td class="td_cs"   title="${records.transport_warehouse }">${records.transport_warehouse }</td>
							<td class="td_cs"   title="${records.store }">${records.store }</td>
							<td class="td_cs"   title="${records.cost_center }">${records.cost_center }</td>
							<td class="td_cs"   title="${records.epistatic_order }">${records.epistatic_order }</td>
							<td class="td_cs"   title="${records.platform_no }">${records.platform_no }</td>
							<td class="td_cs"   title="${records.sku_number }">${records.sku_number }</td>
							<td class="td_cs"   title="${records.length }">${records.length }</td>
							<td class="td_cs"   title="${records.width }">${records.width }</td>
							<td class="td_cs"   title="${records.height }">${records.height }</td>
							<td class="td_cs"   title="${records.volumn }">${records.volumn }</td>
							<td class="td_cs"   title="${records.transport_time1 }">
							<fmt:formatDate value="${records.transport_time1}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
							<td class="td_cs"   title="${records.origin_province1 }">${records.origin_province1 }</td>
							<td class="td_cs"   title="${records.origin_city1 }">${records.origin_city1 }</td>
							<td class="td_cs"   title="${records.dest_province1 }">${records.dest_province1 }</td>
							<td class="td_cs"   title="${records.dest_city1 }">${records.dest_city1 }</td>
							<td class="td_cs"   title="${records.transport_weight1 }">${records.transport_weight1 }</td>
							<td class="td_cs"   title="${records.express_code1 }">${records.express_code1 }</td>
							<td class="td_cs"   title="${records.producttype_code1 }">${records.producttype_code1 }</td>
							<td class="td_cs"  title="${records.insurance1 }">${records.insurance1 }</td>
							<td class="td_cs"  title="${records.volumn_charged_weight }">${records.volumn_charged_weight }</td>
							<td class="td_cs"  title="${records.charged_weight1 }">${records.charged_weight1 }</td>
							<td class="td_cs"  title="${records.firstWeight }">${records.firstWeight }</td>
							<td class="td_cs"  title="${records.addedWeight }">${records.addedWeight }</td>
							<td class="td_cs"  title="${records.discount }">${records.discount }</td>
							<td class="td_cs"  title="${records.standard_freight }">${records.standard_freight }</td>
							<td class="td_cs"  title="${records.afterdiscount_freight }">${records.afterdiscount_freight }</td>
							<td class="td_cs"  title="${records.insurance_fee1 }">${records.insurance_fee1 }</td>
							<td class="td_cs"  title="${records.additional_fee }">${records.additional_fee }</td>
							<td class="td_cs"  title="${records.last_fee }">${records.last_fee }</td>
							<td class="td_cs" title="${records.is_verification }">
							<c:if test="${records.is_verification=='1'}">已核销</c:if>
		   					<c:if test="${records.is_verification=='0'}">未核销</c:if>
							</td>
							<td class="td_cs"  title="${records.reason}">${records.reason}</td>
							<td class="td_cs"  title="${records.remarks }">${records.remarks }</td>
							<td class="td_cs"  ><c:if test="${empty records.account_id}">未生成账单</c:if>
							<c:if test="${not empty records.account_id}">已成账单</c:if>
							</td>
						</tr>
		  			</c:forEach>
		  		</tbody>
			</table>
		</div>
	<!-- 分页添加 -->
	<div style="margin-right: 5%; margin-top: 20px; margin-bottom: 10%;">${pageView.pageView}</div>
	
	
	<!-- 分页添加 -->
</body>

</html>
<script type="text/javascript">
function hx(){
	if($("input[name='ckb']:checked").length==0){
		var result=  	confirm('是否要以查询栏的条件进行核销？'); 
		if(result){
			loadingStyle();
		 	 $.post("${root}/control/lmis/diffBilldeatilsController/hx.do?"+$('#diff').serialize(),function(data){
				if(data.code==1){
					alert('操作成功');
					cancelLoadingStyle();
					DiffBilldeatils();
				}else{
					alert('操作失败！');
					cancelLoadingStyle();
				}
			}); 	
		}
	}else{
		//根据id来核销
		var id=$("input[name='ckb']:checked");
		var idsStr="";
		$.each(id,function(index,item){
			idsStr=idsStr+";"+this.value;
		});
		$.post('${root}/control/lmis/diffBilldeatilsController/hxids.do?ids='+idsStr+"&master_id=${master.id}",
	   		function(data){
			if(data.code==1){
				alert('操作成功');
				DiffBilldeatils();
			}else{
				alert('操作失败！');
				
			}
			
		});
	} 

	}



function uploadDetail(){
		if($("input[name='ckb1234']:checked").length==0){
	   
	   $.post('${root}/control/lmis/diffBilldeatilsController/upload.do?'+$('#diff').serialize(),function(data){
		 if(data.code==1){
			 alert("操作成功！！");
			 window.open(root + "/DownloadFile/hxupload/"+data.url);
		 }  else{
			 alert("操作失败");
		 }
		    
		   
	   });
		}else{
			
			$.post('${root}/control/lmis/diffBilldeatilsController/uploadIds.do?'+$('#diff').serialize(),function(data){
				 if(data.code==1){
					 alert("操作成功！！");
					 window.open(root + "/DownloadFile/hxupload/"+data.url);
				 }  else{
					 alert("操作失败");
				 }
				    
				   
			   });	
			
			
		}

}



function startAccount(){
  if($("input[name='ckb']:checked").length!=0){
  		 var result=  	confirm('是否要生成账单！'); 
  		if(result){
  			loadingStyle();
			var id=$("input[name='ckb']:checked");
			var idsStr="";
			$.each(id,function(index,item){
				idsStr+=this.value+";";
			});
		
			$.post('${root}/control/lmis/diffBilldeatilsController/genAccount.do?ids='+idsStr+"&master_id="+$("#master_id").val(),
	        		function(data){
				      cancelLoadingStyle();
	        	            if(data.code==1){
	        	            	alert("操作成功！");
	        	            }else if(data.code==0){
	        	            	alert("操作失败！");
	        	             }else if(data.code==2){
	        	            	 alert('有部分明细数据已生成过账单，不能重复操作！！！');
	        	             }
	        	            $(window).scrollTop(0);
        					loadingStyle();
        					DiffBilldeatils_Is_verification_page('');
	                        }
	  		      ); 
  		}	
	}else{
		
		var result1=  	confirm('是否要根据搜索栏的条件来生成账单？！'); 
		if(result1)
		qureyAccount();
	}   
}

function qureyAccount(){
	loadingStyle();
	$.post("${root}/control/lmis/diffBilldeatilsController/genAccountSe.do?"+$('#diff').serialize()+"&master_id="+$("#master_id").val()+"&month_accountq="+$('#example-getting-started1').val()
			,function(data){
		
		cancelLoadingStyle();
		if(data.code==1){
			alert('操作成功');
			DiffBilldeatils_Is_verification_page('')
		}else if(data.code==0){
			alert('操作失败！');
		}else if(data.code==2){
       	 alert('有部分明细数据已生成过账单，不能重复操作！！！');
		}
	});
	
}


function cancelhx(){
	if($("input[name='ckb']:checked").length==0){
		var result=  	confirm('是否要以查询栏的条件进行核销？'); 
		if(result){
		 	 $.post("${root}/control/lmis/diffBilldeatilsController/cancelhx.do?"+$('#diff').serialize()+'&master_id='+$('#master_id').val(),function(data){
				if(data.code==1){
					alert('操作成功');
					DiffBilldeatils_Is_verification_page('')
				}else{
					alert('操作失败！');
					
				}
			}); 	
		}
	}else{
		//根据id来核销
		var id=$("input[name='ckb']:checked");
		var idsStr="";
		$.each(id,function(index,item){
			idsStr=idsStr+";"+this.value;
		});
		$.post('${root}/control/lmis/diffBilldeatilsController/cancelhxids.do?ids='+idsStr+"&master_id="+$("#master_id").val(),
	   		function(data){
			if(data.code==1){
				alert('操作成功');
				DiffBilldeatils_Is_verification_page('')
			}else{
				alert('操作失败！');
				
			}
		});
	} 

	}

	
	
	function pageJump() {
		
		
		var $v2 = $($("#month_account")[0].parentNode.parentNode);
		var ff=$v2.children(".td_cs");
		var param='';
		$.each(ff,function(index,item){
			if(item.firstChild.value!='')
			param=param+item.firstChild.name+"="+item.firstChild.value+'&';
		}); 
		
	
		 loadingStyle();
			$.ajax({
				type: "POST",
	           	url:'${root}/control/lmis/diffBilldeatilsController/pageQuery.do?&'+param+'master_id='+$("#master_id").val()+'&is_verification='+$('#is_verification').val()+"&page="+$("#pageIndex").val()+"&pageSize="+$("#pageSize").val(),
	           	dataType: "text",
	           	data:'',
	    		cache:false,
	    		contentType:"application/x-www-form-urlencoded;charset=UTF-8",
	           	success: function (data){
	           		if($('#is_verification').val()==0){
	           			$("#diff_billdeatils_is_verification_page").html("");
	           			$("#diff_billdeatils_page").html(data);	
	           		}
	              
	           		if($('#is_verification').val()==1){
	           			$("#diff_billdeatils_page").html("");	
	           			$("#diff_billdeatils_is_verification_page").html(data);
	           			
	           		}
	              cancelLoadingStyle();
	           	}
		  	});   
		  	   }


</script>


<style>
.select {
	padding: 3px 4px;
	height: 30px;
	width: 160px;
	text-align: enter;
}

.table_head_line td {
	font-weight: bold;
	font-size: 16px
}

.modal-header {
	height: 50px;
}
</style>
