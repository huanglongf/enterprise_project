<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script>
$(document).ready(function(){ 
	getTablData($("#id").val());
});
</script>
<div id="hcf_form" class="moudle_dashed_border_show">
	<div class="div_margin">
		<hctd></hctd>
	</div>
	<div class="add_button">
		<a data-toggle="tab" href="#tab_add">
			<button class="btn btn-xs btn-yellow" onclick="changeShow('HCFAdd')">
				<i class="icon-hdd"></i>新增
			</button>
		</a>
	</div>
	<!-- 仓储费 -->
	<div class="moudle_dashed_border_width_90_red" id="HCFAdd">
		<div class="div_margin">
			耗材编码：<input type="text" id="hc_no"/>
		</div>
		<div class="div_margin">
			耗材名称：<input type="text" id="hc_name" />
		</div>
		<div class="div_margin">
			耗材单位：<input type="text" id="hc_unit"/>
		</div>
		<div class="div_margin">
			耗材类型：
			<select style="width: 280px;" id="HC_TYPE" onchange="showHCF();">
				<option>请选择类型</option>
				<option value="1">主材</option>
				<option value="2">辅材</option>
			</select>
		</div>
		<div class="div_margin">
			结算规则：
			<select style="width: 280px;" id="HCF" onchange="showHCF();">
				<option>请选择结算方式</option>
				<option value="1">耗材费按实际使用量计算</option>
				<option value="2">耗材费按订单量计算</option>
				<option value="3">耗材费按实际采购量计算</option>
				<option value="4">耗材费按商品件数计算</option>
			</select>
		</div>
		<div id="HCF1" style="display: none;" class="div_margin">
			<div class="div_margin">
				<span style="color: red">耗材费按实际使用量计算：耗材实际使用量*耗材单价 </span>
			</div>
			<div class="div_margin">
				耗材单价：<input type="text"  id="hc_price"/>
				单位：
				<select id="price_unit">
					<option>元/KG</option>
					<option>元/米</option>
				</select>
			</div>
			<div class="div_margin">
				<a data-toggle="tab" href="#tab_add">
					<button class="btn btn-xs btn-yellow" onclick="save('HC1')">
						<i class="icon-hdd"></i>保存
					</button>
				</a>
			</div>
		</div>
		<div id="HCF2" style="display: none;" class="div_margin">
			<div class="div_margin">
				<span style="color: red">耗材费按订单量计算：B2C订单量*单价 </span>
			</div>
			<div class="div_margin">
				耗材单价：<input type="text" id="hc_price2"/>
				单位：
				<select id="price_unit2">
					<option>元/KG</option>
					<option>元/米</option>
				</select>
			</div>
			<div class="div_margin">
				订单量：<input type="text" id="bill_num"/>
				单位：
				<select>
					<option>单</option>
				</select>
			</div>
			<div class="div_margin">
				<span style="color: red">胶带按单量的计算逻辑：（销售出库单数/30）*单价 </span>
			</div>
			<div class="div_margin">
				<span style="color: red">气珠单片计算逻辑：销售订单数/200*10*单价 </span>
			</div>
			<div class="div_margin">
				<a data-toggle="tab" href="#tab_add">
					<button class="btn btn-xs btn-yellow" onclick="save('HC2')">
						<i class="icon-hdd"></i>保存
					</button>
				</a>
			</div>
		</div>
		<div id="HCF4" style="display: none;" class="div_margin">
			<div class="div_margin">
				<span style="color: red">耗材费按商品件数计算：B2C商品件数*单价 </span>
			</div>
			<div class="div_margin">
				耗材单价：<input type="text" id="hc_price4"/>
				单位：
				<select id="price_unit4">
					<option>元/KG</option>
					<option>元/米</option>
				</select>
			</div>
			<div class="div_margin">
				商品件数：<input type="text" id="article_num"/>
				单位：
				<select>
					<option>单</option>
				</select>
			</div>
			<div class="div_margin">
				
			</div>
			<div class="div_margin">
				
			</div>
			<div class="div_margin">
				<a data-toggle="tab" href="#tab_add">
					<button class="btn btn-xs btn-yellow" onclick="save('HC4')">
						<i class="icon-hdd"></i>保存
					</button>
				</a>
			</div>
		</div>
		<div id="HCF3" style="display: none;" class="div_margin">
			<div class="div_margin">
				<span style="color: red">耗材费按采购量计算：耗材实际采购量*耗材单价 </span>
			</div>
			<div class="div_margin">
				耗材单价：<input type="text" id="hc_price3"/>
				单位：
				<select id="price_unit3">
					<option>元/KG</option>
					<option>元/米</option>
				</select>
			</div>
			<div class="div_margin">
				<a data-toggle="tab" href="#tab_add">
					<button class="btn btn-xs btn-yellow" onclick="save('HC3')">
						<i class="icon-hdd"></i>保存
					</button>
				</a>
			</div>
		</div>
	</div>
		
	<div>
		<div class="div_margin">
			<label class="control-label blue">
				管理费
			</label>
			<input 
			id="cb_managementFee_other_hcf" 
			type="checkbox" 
			class="ace ace-switch ace-switch-5" ${hcfFeeFlag == true ? 'checked="checked"' : '' } 
			onchange="managementOtherShow('hcf');"/>
			<span class="lbl"></span>
		</div>
		<!-- 管理费 -->
		<div id="managementFeeOther_hcf" class="moudle_dashed_border" style="width:100%;border:0px;${hcfFeeFlag == true ? 'display:block;' : '' }">
			<iframe id="iframe_hcf" class="with-border" style="overflow: visible; border:0px;margin:0px;padding:0px;background: rgba(0,0,0,0);width: 100%;" src="${root }control/expressContractController/toManagementFeeOther.do?management_fee_type=hcf&cb_id=${cb.id }"></iframe>
		</div>
	</div>
</div>


<script type="text/javascript">
	//耗材费－[计算公式]展示
	function showHCF() {
		var divId = $("#HCF").val();
		var divObj = $("div[id^='HCF']");
		for (i = 0; i < divObj.length; i++) {
			if (i == divId) {
				$("#HCF" + i).css("display", "block");
			} else {
				$("#HCF" + i).css("display", "none");
			}
		}
	};
	
	function save(id){

		var url=root+"/control/hcController/saveData.do";
		var hc_no=$("#hc_no").val();
		var hc_name=$("#hc_name").val();
		var hc_unit=$("#hc_unit").val();
		var cat_type=$("#HCF").val();
		var contract_id=$("#id").val();
		var contract_id=$("#id").val();
		//Yuriy.Jiang 2016-07-29 添加耗材类型 主材 辅材
		var HC_TYPE = $("#HC_TYPE").val();
		if(contract_id==""){
			alert("请先维护合同主信息!");
			return;
		}		
		if(hc_no==""){
			alert("耗材编码不能为空!");
			return;
		}
		if(hc_name==""){
			alert("耗材名称不能为空!");
			return;
		}
		if(hc_unit==""){
			alert("耗材单位不能为空!");
			return;
		}	
		if(HC_TYPE==""){
			alert("耗材类型不能为空!");
			return;
		}		
		var data="hc_no="+hc_no+"&hc_name="+hc_name+"&hc_unit="+hc_unit+"&cat_type="+cat_type+"&contract_id="+contract_id+"&HC_TYPE="+HC_TYPE;
		if(id=="HC1"){
			var hc_price=$("#hc_price").val();
			if(hc_price==""){
				alert("耗材单价不能为空!");
				return;
			}				
			var price_unit=$("#price_unit").val();
			data=data+"&hc_price="+hc_price+"&price_unit="+price_unit+"&cat_type_name=耗材费按实际使用量计算";
		}
		if(id=="HC2"){
			var hc_price=$("#hc_price2").val();
			var price_unit=$("#price_unit2").val();
			var bill_num=$("#bill_num").val();
			if(hc_price==""){
				alert("耗材单价不能为空!");
				return;
			}
			if(bill_num==""){
				alert("订单量不能为空!");
				return;
			}			
			data=data+"&hc_price="+hc_price+"&price_unit="+price_unit+"&bill_num="+bill_num+"&cat_type_name=耗材费按订单量计算";
		}		
		if(id=="HC3"){
			var hc_price=$("#hc_price3").val();
			var price_unit=$("#price_unit3").val();
			if(hc_price==""){
				alert("耗材单价不能为空!");
				return;
			}			
			data=data+"&hc_price="+hc_price+"&price_unit="+price_unit+"&cat_type_name=耗材费按实际采购量计算";
		}
		if(id=="HC4"){
			var hc_price=$("#hc_price4").val();
			var price_unit=$("#price_unit4").val();
			var article_num=$("#article_num").val();
			if(hc_price==""){
				alert("耗材单价不能为空!");
				return;
			}
			if(article_num==""){
				alert("商品件数不能为空!");
				return;
			}			
			data=data+"&hc_price="+hc_price+"&price_unit="+price_unit+"&bill_num="+article_num+"&cat_type_name=耗材费按商品件数计算";
		}	
		
		
		save_data(data,url);

	}
	
	
	
	function  save_data(data,url){
		   $.ajax({
				type : "POST",
				url: url,  
				data:data,
				dataType: "json",  
				success : function(jsonStr) {
				alert(jsonStr.result_reason);
				if(jsonStr.result_flag==1){
				 getTablData($("#id").val());
				 }
				}
			});
	}
	
	function getTablData(contract_id){
		var url=root+"/control/hcController/getTable.do?contract_id="+contract_id;
		var htm_td="";
		   $.ajax({
				type : "POST",
				url: url,  
				data:"",
				dataType: "json",  
				success : function(jsonStr) {
					var text="<table class='with-border' border='1'>"
					       +"<tr class='title'>"
					       +"<td>耗材编码</td><td>耗材名称</td><td>耗材类型</td><td>单位</td>"
					       +"<td>单价</td><td>耗材结算规则</td><td>操作</td></tr>";
					for(i=0;i<jsonStr.length;i++){
						htm_td=htm_td+"<tr><td>"+jsonStr[i].hc_no+"</td><td>"+jsonStr[i].hc_name+"</td><td>"+jsonStr[i].hc_type+"</td><td>"+jsonStr[i].hc_unit+"</td><td>"+jsonStr[i].hc_price+jsonStr[i].price_unit+"</td><td>"+jsonStr[i].cat_type_name+"</td><td><a href='javascript:;' onclick='del_td(\""+jsonStr[i].id+"\")'>删除</a></td></tr>"
					}
					$("hctd").html(text+htm_td+"</table>");
				}
			});
	}
	
	function del_td(id){
		if(!confirm("是否删除以下所选数据?")){
		  	return;
		  	
		}
		var url=root+"/control/hcController/delTable.do?id="+id;
		   $.ajax({
				type : "POST",
				url: url,  
				data:"",
				dataType: "json",  
				success : function(jsonStr) {
					alert(jsonStr.result_reason);
					 if(jsonStr.result_flag==1){
						 getTablData($("#id").val());
					}
				}
			});
	}
</script>