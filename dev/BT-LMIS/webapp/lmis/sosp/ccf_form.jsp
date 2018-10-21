<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<div id="ccf_form" class="moudle_dashed_border_show">
	<div>
		<div>
			<div class="div_margin">---已维护公式---</div>
		</div>
		<input id="ssc_cb_id" name="ssc_cb_id" type="hidden" value="${cb.id}" />
		<CCF_MAIN></CCF_MAIN>
	</div>
	<div class="add_button">
		<a data-toggle="tab" href="#tab_add">
			<button class="btn btn-xs btn-yellow" onclick="changeShow('CCFDIV')"><i class="icon-hdd"></i>新增</button>
		</a>
	</div>
	<!-- 仓储费 -->
	<div class="moudle_dashed_border_width_90_red" id="CCFDIV">
		<div class="form-group">
			<div class="controls" style="width: 300px;">
				<div class="div_margin">
					<div class="input-prepend input-group">
						存储时间:<span class="add-on input-group-addon">
							<i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
						</span>
						<input type="text" readonly  style="width: 100%" name="reservation2" id="reservation2" class="form-control" value="${sdate} - ${edate}" />
					</div>
				</div>	
				<div class="div_margin">
					仓库:<select id="ssc_whs_id" name="ssc_whs_id" style="width: 80%;" onchange="showArea();" data-edit-select="1">
							<option>请选择</option>
							<c:forEach items="${wareHouseList}" var="wareHouseList">
								<option value="${wareHouseList.id}">${wareHouseList.warehouse_name}</option>
							</c:forEach>
						</select>
				</div>
				<div class="div_margin">
				<input id="ssc_area_ids" name="ssc_area_ids" type="hidden" />
					区域:<select id="ssc_area_id" name="ssc_area_id" style="width: 80%;" onchange="cl();">
							<option>请选择</option>
						</select>
			 	</div>
				 <div class="div_margin">
					类型:<select id="ssc_item_type" name="ssc_item_type" style="width: 80%;" onchange="cl();">
							<option>请选择</option>
							<c:forEach items="${itemTypeList}" var="itemTypeList">
								<option value="${itemTypeList.id}">${itemTypeList.itemtype_name}</option>
							</c:forEach>
						</select>
				</div>			
			</div>
			<div class="div_margin">计算公式 : 
				<select style="width: 280px;" id="CCF" name="CCF" onchange="showCCF();">
					<option value="-1">请选择计费方式</option>
					<option value="0">固定费用</option>
					<option value="1">按面积结算</option>
					<option value="2">按体积结算</option>
					<option value="3">按商品的体积推算</option>
					<option value="4">按件数结算</option>
					<option value="5">按件数反推所占面积</option>
					<option value="6">按托盘结算</option>
					<option value="7">按单个托盘的存放数量推算</option>
				</select>
			</div>
		</div>	
	<!-- 仓储费固定费用 -->
	<div id="CCF0" style="display: none;" >
		<div>
			<span style="color: red">计算公式 : 仓储费 = 固定费用 * 月份</span>
		</div>
		<br />
		<div>
			<span>固定费用:<input id="ssc_fixedcost" name="ssc_fixedcost" type="text" value="" /></span>
			<select id="ssc_fixedcost_unit" name="ssc_fixedcost_unit" style="height: 28px; width: 100px;">
				<option value="0">/元</option>
			</select>
		</div>
		<div class="div_margin">
			<a data-toggle="tab" href="#tab_add">
				<button class="btn btn-xs btn-yellow" onclick="SAVE_GDFY();"><i class="icon-hdd"></i>保存</button>
			</a>	
		</div>
		<script type="text/javascript">
			//保存-固定费用
		    function SAVE_GDFY(){
				//固定信息
		    	var ssc_cb_id = $("#ssc_cb_id").val();
		    	var reservation2 = $("#reservation2").val();
		    	var ssc_whs_id = $("#ssc_whs_id").val();
		    	var ssc_area_id = $("#ssc_area_id").val();
		    	var ssc_item_type = $("#ssc_item_type").val();
		    	var CCF = $("#CCF").val();
		    	//固定费用
		    	var ssc_fixedcost = $("#ssc_fixedcost").val();
		    	var ssc_fixedcost_unit = $("#ssc_fixedcost_unit").val();
		    	if(ssc_cb_id==""||reservation2==""||ssc_whs_id==""||ssc_area_id==""||ssc_item_type==""){
					alert("请先添加合同基础信息!");
		    		return;
		    	}
				$.ajax({
		            url: "${root}control/sospController/saveGDFY.do?ssc_cb_id="+ssc_cb_id+"&ssc_fixedcost="+ssc_fixedcost+
		            		"&ssc_fixedcost_unit="+ ssc_fixedcost_unit+"&CCF="+CCF+"&reservation2="+reservation2+"&ssc_whs_id="+ssc_whs_id+
		            		"&ssc_area_id="+ssc_area_id+"&ssc_item_type="+ssc_item_type,
		            type: "POST",
		            data: null,
		            dataType: "json",
		            success: function(data){
		            	alert(data.info);
		            	getCCFMainTB(ssc_cb_id);
		            }
		        });
		    }
		</script>
	</div>
	<!-- 商品实际占用面积 -->
	<div id="CCF1" style="display: none;"  class="moudle_dashed_border">
		<div class="div_margin">
			<span style="color: red">计算公式 : 仓储费 = 所占平方数 * 面积单价 </span>
		</div>
		<br />
		<div class="form-group" style="margin: 10px;">
			<select onchange="showMJ();" id="MJ" name="MJ" style="width: 30%;">
				<option value="0">请选择</option>
				<option value="1">无阶梯</option>
				<option value="2">超过部分面积阶梯</option>
				<option value="3">总占用面积阶梯</option>
			</select>
		</div>
		<div id="MJ1" class="moudle_dashed_border_width_90">
			<div class="div_margin">
				<span>面积单价:<input id="ssc_area_price" name="ssc_area_price" type="text" value="" /></span>
				<select id="ssc_area_price_unit" name="ssc_area_price_unit" style="height: 28px; width: 100px;">
					<option value="0">/元/㎡/天</option>
				</select>
				<div class="div_margin">
					<a data-toggle="tab" href="#tab_add">
						<button class="btn btn-xs btn-yellow" onclick="SAVE_MJJS_WJT();">
							<i class="icon-hdd"></i>保存
						</button>
					</a>	
				</div>
			</div>
			<script type="text/javascript">
				//保存-商品实际占用面积-无阶梯
			    function SAVE_MJJS_WJT(){
					//固定信息
			    	var ssc_cb_id = $("#ssc_cb_id").val();
			    	var reservation2 = $("#reservation2").val();
			    	var ssc_whs_id = $("#ssc_whs_id").val();
			    	var ssc_area_id = $("#ssc_area_id").val();
			    	var ssc_item_type = $("#ssc_item_type").val();
			    	var CCF = $("#CCF").val();
			    	var MJ = $("#MJ").val();
			    	//无阶梯
			    	var ssc_area_price = $("#ssc_area_price").val();
			    	var ssc_area_price_unit = $("#ssc_area_price_unit").val();
			    	if(ssc_cb_id==""||reservation2==""||ssc_whs_id==""||ssc_area_id==""||ssc_item_type==""){
						alert("请先添加合同基础信息!");
			    		return;
			    	}
					$.ajax({
			            url: "${root}control/sospController/saveMJJSWJT.do?ssc_cb_id="+ssc_cb_id+"&CCF="+CCF+"&reservation2="+reservation2+"&ssc_whs_id="+ssc_whs_id+"&ssc_area_id="+ssc_area_id+"&ssc_item_type="+ssc_item_type
			            		+"&ssc_area_price="+ssc_area_price+"&ssc_area_price_unit="+ssc_area_price_unit+"&MJ="+MJ,
			            type: "POST",
			            data: null,
			            dataType: "json",
			            success: function(data){
			            	alert(data.info);
			            	getCCFMainTB(ssc_cb_id);
			            }
			        });
			    }
			</script>
		</div>
		<div id="MJ2" style="display: none;">
			<br />
			<div>
				<span style="color: red"> 阶梯价计算逻辑：IF（面积 in 条件范围）｛条件面积 * 面积单价｝+IF（面积 in 条件范围）｛条件面积 * 面积单价｝+ ...</span>
			</div>
			<div>
				<span>已维护的阶梯</span>
			</div>
			<div>
				<MJJS_CGBF></MJJS_CGBF>
			</div>
			<div>
			   <div class="add_button">
				<a data-toggle="tab" href="#tab_add">
					<button class="btn btn-xs btn-yellow" onclick="changeShow('addDiv_op1')">
						<i class="icon-hdd"></i>新增
					</button>
				</a>
				</div>
				<div id="addDiv_op1">
					<span style="color: red">阶梯价计算逻辑：(实际存储面积－维护条件面积)*面积单价</span>
					<span style="color: red">公式说明：阶梯区间，需要在下方设置</span>
					<div>
						<span>阶梯设置：</span>
					</div>
					<div  class="div_margin">
					 条件:
						<select id="amjjs_cgbf_tj1" name="amjjs_cgbf_tj1">
							<option value=">">></option>
							<option value=">=">>=</option>
						</select> 
						存储面积<input type="text" id="amjjs_cgbf_cs1" name="amjjs_cgbf_cs1" />
						<select><option>㎡</option></select>
					</div>
					<div class="div_margin">
						组合方式:
						<select id="amjjs_cgbf_tj2" name="amjjs_cgbf_tj2">
							<option value="1">并且</option>
							<!-- <option value="1">或者</option> -->
						</select>
					</div>
					<div class="div_margin">
						条件:<select id="amjjs_cgbf_tj3" name="amjjs_cgbf_tj3">
							<option value="<">&lt;</option>
							<option value="<=">&lt;=</option>
						</select> 
						存储面积<input type="text" id="amjjs_cgbf_cs2" name="amjjs_cgbf_cs2"/>
						<select><option>㎡</option></select>
					</div>
					<div class="div_margin">
						单价:<input type="text" id="sta_price" name="sta_price" />
						<select id="sta_price_unit" name="sta_price_unit"><option value="0">/元/㎡/天</option></select>
					</div>
					<div class="div_margin">
						<a data-toggle="tab" href="#tab_add">
							<button class="btn btn-xs btn-yellow" onclick="SAVE_MJJS_CGBF();">
								<i class="icon-hdd"></i>保存
							</button>
						</a>	
					</div>
					<script type="text/javascript">
						//保存-商品实际占用面积-超过部分阶梯
					    function SAVE_MJJS_CGBF(){
							//固定信息
					    	var ssc_cb_id = $("#ssc_cb_id").val();
					    	var reservation2 = $("#reservation2").val();
					    	var ssc_whs_id = $("#ssc_whs_id").val();
					    	var ssc_area_id = $("#ssc_area_id").val();
					    	var ssc_item_type = $("#ssc_item_type").val();
					    	var CCF = $("#CCF").val();
					    	var MJ = $("#MJ").val();
					    	//超过部分阶梯
					    	var amjjs_cgbf_tj1 = $("#amjjs_cgbf_tj1").val();
					    	var amjjs_cgbf_tj2 = $("#amjjs_cgbf_tj2").val();
					    	var amjjs_cgbf_tj3 = $("#amjjs_cgbf_tj2").val();
					    	var amjjs_cgbf_cs1 = $("#amjjs_cgbf_cs1").val();
					    	var amjjs_cgbf_cs2 = $("#amjjs_cgbf_cs2").val();
					    	var sta_price = $("#sta_price").val();
					    	var sta_price_unit = $("#sta_price_unit").val();
							var sta_section = getSection("amjjs_cgbf_tj1", "amjjs_cgbf_tj2", "amjjs_cgbf_tj3", "amjjs_cgbf_cs1", "amjjs_cgbf_cs2");
					    	if(ssc_cb_id==""||reservation2==""||ssc_whs_id==""||ssc_area_id==""||ssc_item_type==""){
								alert("请先添加合同基础信息!");
								return;
							}
							if(sta_section!=false){
								$.ajax({
						            url: "${root}control/sospController/saveMJJSCGBF.do?ssc_cb_id="+ssc_cb_id+"&CCF="+CCF+"&reservation2="+reservation2+"&ssc_whs_id="+ssc_whs_id+"&ssc_area_id="+ssc_area_id+"&ssc_item_type="+ssc_item_type
						            		+"&sta_section="+sta_section+"&sta_price="+sta_price+"&sta_price_unit="+sta_price_unit+"&MJ="+MJ,
						            type: "POST",
						            data: null,
						            dataType: "json",
						            success: function(data){
						            	alert(data.info);
						            	getCCFMainTB(ssc_cb_id);
						            	getMJJS_CGBF_tab(ssc_cb_id);
						            }
						        });
							}
					    };
					    function getMJJS_CGBF_tab(id){
							var ssc_whs_id = $("#ssc_whs_id").val();
							var ssc_area_id = $("#ssc_area_id").val();
							if(null==$("#ssc_area_id").val()){
								var ssc_area_id = $("#ssc_area_ids").val();
							}
							var ssc_item_type = $("#ssc_item_type").val();
							var CCF = $("#CCF").val();
					    	var url=root+"/control/sospController/getMJJS_CGBF_tab.do?ssc_cb_id="+id+"&id2="+ssc_whs_id+"&id3="+ssc_area_id+"&id4="+ssc_item_type+"&id5="+CCF;
					    	var htm_td="";
					    	   $.ajax({
					    			type : "POST",
					    			url: url,  
					    			data:"",
					    			dataType: "json",  
					    			success : function(data) {
					    				if(data.status=='y'){
						    				var text="<table class='with-border' border='1'>"
						    				       +"<tr class='title'>"
						    				       +"<td>序号</td><td>面积区间</td><td>单价</td>"
						    				       +"<td>操作</td></tr>";
						    				for(i=0;i<data.taList.length;i++){
						    					htm_td=htm_td+"<tr><td>"+(i+1)+"</td><td>"+data.taList[i].sta_section+"</td><td>"+data.taList[i].sta_price+"/元/㎡/天</td><td><a onclick='deletea("+data.taList[i].sta_id+");'>删除</a></td></tr>";
						    				}
						    				$("MJJS_CGBF").html(text+htm_td+"</table>");
					    				}else{
					    					var text="<table class='with-border' border='1'>"
						    				       +"<tr class='title'>"
						    				       +"<td>序号</td><td>面积区间</td><td>单价</td>"
						    				       +"<td>操作</td></tr>";
					    					$("MJJS_CGBF").html(text+"</table>");
					    				}
					    			}
					    		});
					    }
					</script>
				</div>
			</div>
		</div>
		<div id="MJ3" style="display: none;">
			<br />
			<div >
				<span style="color: red"> 阶梯价计算逻辑：实际存储面积*面积单价 </span>
			</div>
			<div>
				<span>已维护的阶梯</span>
			</div>
			<div>
				<MJJS_ALL></MJJS_ALL>
			</div>
			<div>
			   <div class="add_button">
					<a data-toggle="tab" href="#tab_add">
						<button class="btn btn-xs btn-yellow" onclick="changeShow('addDiv_op2');">
							<i class="icon-hdd"></i>新增
						</button>
					</a>
				</div>
				<div id="addDiv_op2" class="moudle_dashed_border_width_90">
					<span style="color: red">阶梯价计算逻辑：实际存储面积*面积单价</span><br/>
					<span style="color: red">公式说明：阶梯区间，需要在下方设置</span>
					<div class="div_margin">
						<span>阶梯设置：</span>
					</div>
					<div class="div_margin">
				        条件:
						<select id="amjjs_all_tj1" name="amjjs_all_tj1">
							<option value=">">></option>
							<option value=">=">>=</option>
						</select> 
						实际存储面积<input type="text" id="amjjs_all_cs1" name="amjjs_all_cs1"/>
						<select><option>㎡</option></select>
					</div>
					<div class="div_margin">
						组合方式:
						<select  id="amjjs_all_tj2" name="amjjs_all_tj2">
							<!-- <option>或者</option> -->
							<option value="1">并且</option>
						</select>
					</div>
					<div class="div_margin">
						条件:<select  id="amjjs_all_tj3" name="amjjs_all_tj3">
							<option value="<"><</option>
							<option value="<="><=</option>
						</select> 
						实际存储面积<input type="text"  id="amjjs_all_cs2" name="amjjs_all_cs2"/>
						<select><option>㎡</option></select>
					</div>
					<div>
						单价:<input type="text" id="saa_price" name="saa_price" /><select id="saa_price_unit" name="saa_price_unit"><option value="0">/元/㎡/天</option></select>
					</div>
					<div class="div_margin">
						<a data-toggle="tab" href="#tab_add">
							<button class="btn btn-xs btn-yellow" onclick="SAVE_MJJS_ALL();">
								<i class="icon-hdd"></i>保存
							</button>
						</a>	
					</div>		
					<script type="text/javascript">
						//保存-商品实际占用面积-实际存储面积
					    function SAVE_MJJS_ALL(){
							//固定信息
					    	var ssc_cb_id = $("#ssc_cb_id").val();
					    	var reservation2 = $("#reservation2").val();
					    	var ssc_whs_id = $("#ssc_whs_id").val();
					    	var ssc_area_id = $("#ssc_area_id").val();
					    	var ssc_item_type = $("#ssc_item_type").val();
					    	var CCF = $("#CCF").val();
					    	var MJ = $("#MJ").val();
					    	//超过部分阶梯
					    	var amjjs_all_tj1 = $("#amjjs_all_tj1").val();
					    	var amjjs_all_tj2 = $("#amjjs_all_tj2").val();
					    	var amjjs_all_tj3 = $("#amjjs_all_tj2").val();
					    	var amjjs_all_cs1 = $("#amjjs_all_cs1").val();
					    	var amjjs_all_cs2 = $("#amjjs_all_cs2").val();
					    	var saa_price = $("#saa_price").val();
					    	var saa_price_unit = $("#saa_price_unit").val();
							var saa_section = getSection("amjjs_all_tj1", "amjjs_all_tj1", "amjjs_all_tj3", "amjjs_all_cs1", "amjjs_all_cs2");
					    	if(ssc_cb_id==""||reservation2==""||ssc_whs_id==""||ssc_area_id==""||ssc_item_type==""){
								alert("请先添加合同基础信息!");
								return;
							}
							if(saa_section!=false){
								$.ajax({
						            url: "${root}control/sospController/saveMJJSALL.do?ssc_cb_id="+ssc_cb_id+"&CCF="+CCF+"&reservation2="+reservation2+"&ssc_whs_id="+ssc_whs_id+"&ssc_area_id="+ssc_area_id+"&ssc_item_type="+ssc_item_type
						            		+"&saa_section="+saa_section+"&saa_price="+saa_price+"&saa_price_unit="+saa_price_unit+"&MJ="+MJ,
						            type: "POST",
						            data: null,
						            dataType: "json",
						            success: function(data){
						            	alert(data.info);
						            	getCCFMainTB(ssc_cb_id);
						            	getMJJS_ALL_tab(ssc_cb_id);
						            }
						        });
							}
					    };
					    function getMJJS_ALL_tab(id){
							var ssc_whs_id = $("#ssc_whs_id").val();
							var ssc_area_id = $("#ssc_area_id").val();
							if(null==$("#ssc_area_id").val()){
								var ssc_area_id = $("#ssc_area_ids").val();
							}
							var ssc_item_type = $("#ssc_item_type").val();
							var CCF = $("#CCF").val();
					    	var url=root+"/control/sospController/getMJJS_ALL_tab.do?ssc_cb_id="+id+"&id2="+ssc_whs_id+"&id3="+ssc_area_id+"&id4="+ssc_item_type+"&id5="+CCF;
					    	var htm_td="";
					    	   $.ajax({
					    			type : "POST",
					    			url: url,  
					    			data:"",
					    			dataType: "json",  
					    			success : function(data) {
				    					var text="<table class='with-border' border='1'>"+
				    					"<tr class='title'>"+
										"<td>序号</td>"+
										"<td>实际存储面积区间</td>"+
										"<td>单价</td>"+
										"<td>操作</td>"+
										"</tr>";
					    				if(data.status=='y'){
						    				for(i=0;i<data.list.length;i++){
						    					htm_td=htm_td+"<tr><td>"+(i+1)+"</td><td>"+data.list[i].saa_section+"</td><td>"+data.list[i].saa_price+"/元/㎡/天</td><td><a onclick='deleteb("+data.list[i].saa_id+");'>删除</a></td></tr>";
						    				}
						    				$("MJJS_ALL").html(text+htm_td+"</table>");
					    				}else{
						    				$("MJJS_ALL").html(text+"</table>");
					    				}
					    			}
					    		});
					    }
					</script>							
				</div>
			</div>
		</div>
	</div>
		<br />
		<!-- 仓储费按体积结算 -->
		<div id="CCF2" style="display: none;">
			<div class="div_margin">
				<span style="color: red">计算公式 : 所占立方数 * 立方单价</span>
			</div>
			<div class="form-group">
				<select onchange="showTJ();" id="TJ" name="TJ" style="width: 30%;">
					<option value="0">请选择</option>
					<option value="1">无阶梯</option>
					<option value="2">超过部分体积阶梯</option>
					<option value="3">总占用体积阶梯</option>
				</select>
			</div>
			
			<div id="TJ1" class="moudle_dashed_border_width_90">
				<div  class="div_margin">
					<span>立方单价:<input id="ssc_volume_price" name="ssc_volume_price" type="text" value="" /></span>
					<select style="height: 28px; width: 100px;" id="ssc_volume_price_unit" name="ssc_volume_price_unit">
						<option value="0">/元/m³/天</option>
					</select>
				</div>
				<div class="div_margin">
					<a data-toggle="tab" href="#tab_add">
						<button class="btn btn-xs btn-yellow" onclick="saveCCF2();">
							<i class="icon-hdd"></i>保存
						</button>
					</a>	
				</div>
				<script type="text/javascript">
					function saveCCF2(){
						//固定信息
				    	var ssc_cb_id = $("#ssc_cb_id").val();
				    	var reservation2 = $("#reservation2").val();
				    	var ssc_whs_id = $("#ssc_whs_id").val();
				    	var ssc_area_id = $("#ssc_area_id").val();
				    	var ssc_item_type = $("#ssc_item_type").val();
				    	var CCF = $("#CCF").val();
				    	var TJ = $("#TJ").val();
				    	
				    	//无阶梯
				    	var ssc_volume_price = $("#ssc_volume_price").val();
				    	var ssc_volume_price_unit = $("#ssc_volume_price_unit").val();
				    	if(ssc_cb_id==""||reservation2==""||ssc_whs_id==""||ssc_area_id==""||ssc_item_type==""){
							alert("请先添加合同基础信息!");
							return;
						}
						$.ajax({
				            url: "${root}control/sospController/saveCCF2.do?ssc_cb_id="+ssc_cb_id+"&CCF="+CCF+"&reservation2="+reservation2+"&ssc_whs_id="+ssc_whs_id+"&ssc_area_id="+ssc_area_id+"&ssc_item_type="+ssc_item_type
				            		+"&ssc_volume_price="+ssc_volume_price+"&ssc_volume_price_unit="+ssc_volume_price_unit+"&TJ="+TJ,
				            type: "POST",
				            data: null,
				            dataType: "json",
				            success: function(data){
				            	alert(data.info);
				            	getCCFMainTB(ssc_cb_id);
				            }
				        });
					};
				</script>		
			</div>
			
			<div id="TJ2" style="display: none;" align="center">
				<div>
					<span style="color: red"> 阶梯价计算逻辑：（实际存储立方-维护的条件立方）*单价</span>
				</div>
				<div>
					<span>---已维护的阶梯---</span>
				</div>
				<div class="div_margin">
					<TJJT_CGBF></TJJT_CGBF>
				</div>
				<div class="div_margin">
				    <div class="add_button">
					<a data-toggle="tab" href="#tab_add">
						<button class="btn btn-xs btn-yellow" onclick="changeShow('TjjtCGBF');">
							<i class="icon-hdd"></i>新增
						</button>
					</a>
					</div>
					<div id="TjjtCGBF"  class="moudle_dashed_border_width_90">
						<div class="">
							<span style="color: red">超过部分阶梯价： 阶梯价计算逻辑：（实际存储立方-维护的条件立方）*单价</span><br />
							<span style="color: red">公式说明：阶梯区间，需要在下方设置</span><br />
							<div class="div_margin">
								<span>阶梯设置：</span>
							</div>
							<div class="div_margin">
							条件:
								<select id="atjjs_cgbf_tj1" name="atjjs_cgbf_tj1">
									<option value=">">></option>
									<option value=">=">>=</option>
								</select> 
								存储体积<input type="text"  id="atjjs_cgbf_cs1" name="atjjs_cgbf_cs1"/>
								<select><option>m³</option></select>
							</div>
							<div class="div_margin">
								组合方式:
								<select  id="atjjs_cgbf_tj2" name="atjjs_cgbf_tj2">
									<option value="1">并且</option>
								</select>
							</div>
							<div class="div_margin">
								条件:<select  id="atjjs_cgbf_tj3" name="atjjs_cgbf_tj3">
									<option value="<"><</option>
									<option value="<="><=</option>
								</select> 
								存储体积<input type="text"  id="atjjs_cgbf_cs2" name="atjjs_cgbf_cs2"/>
								<select><option value="0">m³</option></select>
							</div>
							<div>
								单价:<input type="text"   id="ctv_price" name="ctv_price" />
								<select id="ctv_price_unit" name="ctv_price_unit"><option value="0">／元／m³／天</option></select>
							</div>
						</div>
						<div class="div_margin">
							<a data-toggle="tab" href="#tab_add">
								<button class="btn btn-xs btn-yellow" onclick="saveTjjtCGBF();">
									<i class="icon-hdd"></i>保存
								</button>
							</a>
						</div>
						<script type="text/javascript">
							function saveTjjtCGBF(){
								//固定信息
						    	var ssc_cb_id = $("#ssc_cb_id").val();
						    	var reservation2 = $("#reservation2").val();
						    	var ssc_whs_id = $("#ssc_whs_id").val();
						    	var ssc_area_id = $("#ssc_area_id").val();
						    	var ssc_item_type = $("#ssc_item_type").val();
						    	var CCF = $("#CCF").val();
						    	var TJ = $("#TJ").val();
						    	//超过部分阶梯
						    	var atjjs_cgbf_tj1 = $("#atjjs_cgbf_tj1").val();
						    	var atjjs_cgbf_tj2 = $("#atjjs_cgbf_tj2").val();
						    	var atjjs_cgbf_tj3 = $("#atjjs_cgbf_tj3").val();
						    	var atjjs_cgbf_cs1 = $("#atjjs_cgbf_cs1").val();
						    	var atjjs_cgbf_cs2 = $("#atjjs_cgbf_cs2").val();
						    	var ctv_price = $("#ctv_price").val();
						    	var ctv_price_unit = $("#ctv_price_unit").val();
								var ctv_section = getSection("atjjs_cgbf_tj1", "atjjs_cgbf_tj2", "atjjs_cgbf_tj3", "atjjs_cgbf_cs1", "atjjs_cgbf_cs2");
						    	if(ssc_cb_id==""||reservation2==""||ssc_whs_id==""||ssc_area_id==""||ssc_item_type==""){
									alert("请先添加合同基础信息!");
									return;
								}
								if(ctv_section!=false){
									$.ajax({
							            url: "${root}control/sospController/saveTjjtCGBF.do?ssc_cb_id="+ssc_cb_id+"&CCF="+CCF+"&reservation2="+reservation2+"&ssc_whs_id="+ssc_whs_id+"&ssc_area_id="+ssc_area_id+"&ssc_item_type="+ssc_item_type
							           		+"&TJ="+TJ+"&ctv_price="+ctv_price+"&ctv_price_unit="+ctv_price_unit+"&ctv_section="+ctv_section,
							            type: "POST",
							            data: null,
							            dataType: "json",
							            success: function(data){
							            	alert(data.info);
							            	getCCFMainTB(ssc_cb_id);
							            	showTjjtCGBF(ssc_cb_id);
							            }
							        });
								}
							};
							function showTjjtCGBF(id){
								var ssc_whs_id = $("#ssc_whs_id").val();
								var ssc_area_id = $("#ssc_area_id").val();
								if(null==$("#ssc_area_id").val()){
									var ssc_area_id = $("#ssc_area_ids").val();
								}
								var ssc_item_type = $("#ssc_item_type").val();
								var CCF = $("#CCF").val();
						    	var url=root+"/control/sospController/showTjjtCGBF.do?ssc_cb_id="+id+"&id2="+ssc_whs_id+"&id3="+ssc_area_id+"&id4="+ssc_item_type+"&id5="+CCF;
						    	var htm_td="";
						    	   $.ajax({
						    			type : "POST",
						    			url: url,  
						    			data:"",
						    			dataType: "json",  
						    			success : function(data) {
						    				var text="<table class='with-border' border='1'>"+
					    					"<tr class='title'>"+
											"<td>序号</td>"+
											"<td>实际存储体积区间</td>"+
											"<td>单价</td>"+
											"<td>操作</td>"+
											"</tr>";
						    				if(data.status=='y'){
							    				for(i=0;i<data.list.length;i++){
							    					htm_td=htm_td+"<tr><td>"+(i+1)+"</td><td>"+data.list[i].ctv_section+"</td><td>"+data.list[i].ctv_price+"/元/㎡/天</td><td><a onclick='deletec("+data.list[i].ctv_id+")'>删除</a></td></tr>";
							    				}
							    				$("TJJT_CGBF").html(text+htm_td+"</table>");
						    				}else{
							    				$("TJJT_CGBF").html(text+"</table>");
						    				}
						    			}
						    		});
								
							};
						</script>
					</div>
				</div>
			</div>
			<div id="TJ3" style="display: none;">
				<br />
				<div>
					<span style="color: red"> 阶梯价计算逻辑：实际存储体积*体积单价 </span>
				</div>
				<div>
					<span>---已维护的阶梯---</span>
				</div>
				<div>
					<TJJT_ALL></TJJT_ALL>
				</div>
				<div class="div_margin">
				  <div class="add_button">
					<a data-toggle="tab" href="#tab_add">
						<button class="btn btn-xs btn-yellow" onclick="changeShow('TjjtAll')">
							<i class="icon-hdd"></i>新增
						</button>
					</a>
				</div>
					<div id="TjjtAll" class="moudle_dashed_border_width_90">
						<span style="color: red">阶梯价计算逻辑：实际存储体积*体积单价</span><br />
						<span style="color: red">公式说明：阶梯区间，需要在下方设置</span><br />
						<div>
							<span>阶梯设置：</span>
						</div>
						<div class="div_margin">
						条件:
							<select id="atjjs_all_tj1" name="atjjs_all_tj1">
								<option value=">">></option>
								<option value=">=">>=</option>
							</select> 
							实际存储体积<input type="text" id="atjjs_all_cs1" name="atjjs_all_cs1"/>
							<select><option>m³</option></select>
						</div>
						<div class="div_margin">
							组合方式:
							<select  id="atjjs_all_tj2" name="atjjs_all_tj2">
								<option value="1">并且</option>
							</select>
						</div>
						<div class="div_margin">
							条件:<select  id="atjjs_all_tj3" name="atjjs_all_tj3">
								<option value="<">&lt;</option>
								<option value="<=">&lt;=</option>
							</select> 
							实际存储体积<input type="text"   id="atjjs_all_cs2" name="atjjs_all_cs2"/>
							<select><option>m³</option></select>
						</div>
						<div class="div_margin">
							单价:<input type="text" id="sav_price" name="sav_price"/>
							<select id="sav_price_unit" name="sav_price_unit"><option value="0">／元／m³／天</option></select>
						</div>
						<div class="div_margin">
							<a data-toggle="tab" href="#tab_add">
								<button class="btn btn-xs btn-yellow" onclick="saveTJJTALL();">
									<i class="icon-hdd"></i>保存
								</button>
							</a>	
						</div>
						<script type="text/javascript">
							function saveTJJTALL(){
								//固定信息
						    	var ssc_cb_id = $("#ssc_cb_id").val();
						    	var reservation2 = $("#reservation2").val();
						    	var ssc_whs_id = $("#ssc_whs_id").val();
						    	var ssc_area_id = $("#ssc_area_id").val();
						    	var ssc_item_type = $("#ssc_item_type").val();
						    	var CCF = $("#CCF").val();
						    	var TJ = $("#TJ").val();
						    	//超过部分阶梯
						    	var atjjs_all_tj1 = $("#atjjs_all_tj1").val();
						    	var atjjs_all_tj2 = $("#atjjs_all_tj2").val();
						    	var atjjs_all_tj3 = $("#atjjs_all_tj3").val();
						    	var atjjs_all_cs1 = $("#atjjs_all_cs1").val();
						    	var atjjs_all_cs2 = $("#atjjs_all_cs2").val();
						    	var sav_price = $("#sav_price").val();
						    	var sav_price_unit = $("#sav_price_unit").val();
								var sav_section = getSection("atjjs_all_tj1", "atjjs_all_tj2", "atjjs_all_tj3", "atjjs_all_cs1", "atjjs_all_cs2");
						    	if(ssc_cb_id==""||reservation2==""||ssc_whs_id==""||ssc_area_id==""||ssc_item_type==""){
									alert("请先添加合同基础信息!");
									return;
								}
								if(sav_section!=false){
									$.ajax({
							            url: "${root}control/sospController/saveTjjtALL.do?ssc_cb_id="+ssc_cb_id+"&CCF="+CCF+"&reservation2="+reservation2+"&ssc_whs_id="+ssc_whs_id+"&ssc_area_id="+ssc_area_id+"&ssc_item_type="+ssc_item_type
							           		+"&TJ="+TJ+"&sav_price="+sav_price+"&sav_price_unit="+sav_price_unit+"&sav_section="+sav_section,
							            type: "POST",
							            data: null,
							            dataType: "json",
							            success: function(data){
							            	alert(data.info);
							            	getCCFMainTB(ssc_cb_id);
							            	showTJJTALL(ssc_cb_id);
							            }
							        });
								}
							};
							function showTJJTALL(id){
								var ssc_whs_id = $("#ssc_whs_id").val();
								var ssc_area_id = $("#ssc_area_id").val();
								if(null==$("#ssc_area_id").val()){
									var ssc_area_id = $("#ssc_area_ids").val();
								}
								var ssc_item_type = $("#ssc_item_type").val();
								var CCF = $("#CCF").val();
								var url=root+"control/sospController/showTJJTALL.do?ssc_cb_id="+id+"&id2="+ssc_whs_id+"&id3="+ssc_area_id+"&id4="+ssc_item_type+"&id5="+CCF;
						    	var htm_td="";
					    	   	$.ajax({
					    			type : "POST",
					    			url: url,  
					    			data:null,
					    			dataType: "json",  
					    			success : function(data) {
					    				var text="<table class='with-border' border='1'>"+
							    				"<tr class='title'>"+
												"<td>序号</td>"+
												"<td>实际体积区间</td>"+
												"<td>单价</td>"+
												"<td>操作</td>"+
												"</tr>";
					    				if(data.status=='y'){
						    				for(i=0;i<data.list.length;i++){
						    					htm_td=htm_td+"<tr><td>"+(i+1)+"</td><td>"+data.list[i].sav_section+"</td><td>"+data.list[i].sav_price+"/元/㎡/天</td><td><a onclick='deleted("+data.list[i].sav_id+")'>删除</a></td></tr>";
						    				}
						    				$("TJJT_ALL").html(text+htm_td+"</table>");
					    				}else{
						    				$("TJJT_ALL").html(text+"</table>");
					    				}
					    			}
					    		});
							};
						</script>						
					</div>
				</div>
			</div>
		</div>
		<!-- 仓储费按商品的体积推算 -->
		<div id="CCF3" style="display: none;">
			<div class="div_margin">
				<span style="color: red">计算公式 : 商品立方数 * 件数 * 立方单价</span>
			</div>
			<div class="div_margin">
				<select onchange="showSPTJ();" id="SPTJ" name="SPTJ" style="width: 30%;">
					<option value="0">请选择</option>
					<option value="1">无阶梯</option>
					<option value="2">超过部分体积阶梯</option>
					<option value="3">总占用体积阶梯</option>
				</select>
			</div>
			<div id="SPTJ1" class="moudle_dashed_border_width_90">
				<div class="div_margin">
					<span>立方单价:<input id="ssc_volume_calculation_price" name="ssc_volume_calculation_price" type="text" value="" /></span>
					<select style="height: 28px; width: 100px;" id="ssc_volume_calculation_price_unit" name="ssc_volume_calculation_price_unit" >
						<option value="0">/元/m³/天</option>
					</select>
				</div>
				<div class="div_margin">
					<a data-toggle="tab" href="#tab_add">
						<button class="btn btn-xs btn-yellow" onclick="saveCCF3();">
							<i class="icon-hdd"></i>保存
						</button>
					</a>	
				</div>		
				<script type="text/javascript">
					function saveCCF3(){
						//固定信息
				    	var ssc_cb_id = $("#ssc_cb_id").val();
				    	var reservation2 = $("#reservation2").val();
				    	var ssc_whs_id = $("#ssc_whs_id").val();
				    	var ssc_area_id = $("#ssc_area_id").val();
				    	var ssc_item_type = $("#ssc_item_type").val();
				    	var CCF = $("#CCF").val();
				    	var SPTJ = $("#SPTJ").val();
				    	//无阶梯
				    	var ssc_volume_calculation_price = $("#ssc_volume_calculation_price").val();
				    	var ssc_volume_calculation_price_unit = $("#ssc_volume_calculation_price_unit").val();
				    	if(ssc_cb_id==""||reservation2==""||ssc_whs_id==""||ssc_area_id==""||ssc_item_type==""){
							alert("请先添加合同基础信息!");
				    		return;
				    	}
						$.ajax({
				            url: "${root}control/sospController/saveCCF3.do?ssc_cb_id="+ssc_cb_id+"&CCF="+CCF+"&reservation2="+reservation2+"&ssc_whs_id="+ssc_whs_id+"&ssc_area_id="+ssc_area_id+"&ssc_item_type="+ssc_item_type
				            		+"&ssc_volume_calculation_price="+ssc_volume_calculation_price+"&ssc_volume_calculation_price_unit="+ssc_volume_calculation_price_unit,
				            type: "POST",
				            data: null,
				            dataType: "json",
				            success: function(data){
				            	alert(data.info);
				            	getCCFMainTB(ssc_cb_id);
				            }
				        });
					};
				</script>		
			</div>
			<div id="SPTJ2" style="display: none;">
				<br />
				<div>
					<span style="color: red"> 阶梯价计算逻辑：（实际存储立方-维护的条件立方）*单价</span>
				</div>
				<div>
					<span>已维护的阶梯</span>
				</div>
				<div>
					<SpjtCGBF></SpjtCGBF>
				</div>
				<div>
				 <div class="add_button">
					<a data-toggle="tab" href="#tab_add">
						<button class="btn btn-xs btn-yellow" onclick="changeShow('SpjtCGBF')">
							<i class="icon-hdd"></i>新增
						</button>
					</a>
					</div>
					<div id="SpjtCGBF" class="moudle_dashed_border_width_90">
						<span style="color: red">阶梯价计算逻辑：(实际存储立方－维护条件立方)*体积单价</span><br />
						<span style="color: red">公式说明：阶梯区间，需要在下方设置</span><br />
						<div align="center">
							<span>阶梯设置：</span>
						</div>
						<div class="div_margin">
						条件:
							<select id="asptjts_cgbf_tj1" name="asptjts_cgbf_tj1">
								<option value=">">></option>
								<option value=">=">>=</option>
							</select> 
							存储体积<input type="text"  id="asptjts_cgbf_cs1" name="asptjts_cgbf_cs1"/>
							<select><option>m³</option></select>
						</div>
						<div class="div_margin">
							组合方式:
							<select id="asptjts_cgbf_tj2" name="asptjts_cgbf_tj2">
								<option value="1">并且</option>
							</select>
						</div>
						<div class="div_margin">
							条件:<select  id="asptjts_cgbf_tj3" name="asptjts_cgbf_tj3">
								<option value="<">&lt;</option>
								<option value="<=">&lt;=</option>
							</select> 
							存储体积<input type="text" id="asptjts_cgbf_cs2" name="asptjts_cgbf_cs2"/>
							<select><option>m³</option></select>
						</div>
						<br />
						<div>
							单价:<input type="text" id="stvc_price" name="stvc_price"/>
							<select id="stvc_price_unit" name="stvc_price_unit"><option value="0">／元／m³／天</option></select>
						</div>
						<div class="div_margin">
							<a data-toggle="tab" href="#tab_add">
								<button class="btn btn-xs btn-yellow" onclick="saveSpjtCGBF();">
									<i class="icon-hdd"></i>保存
								</button>
							</a>	
						</div>
						<script type="text/javascript">
							function saveSpjtCGBF(){
								//固定信息
						    	var ssc_cb_id = $("#ssc_cb_id").val();
						    	var reservation2 = $("#reservation2").val();
						    	var ssc_whs_id = $("#ssc_whs_id").val();
						    	var ssc_area_id = $("#ssc_area_id").val();
						    	var ssc_item_type = $("#ssc_item_type").val();
						    	var CCF = $("#CCF").val();
						    	var SPTJ = $("#SPTJ").val();
						    	//超过部分阶梯
						    	var asptjts_cgbf_tj1 = $("#asptjts_cgbf_tj1").val();
						    	var asptjts_cgbf_tj2 = $("#asptjts_cgbf_tj2").val();
						    	var asptjts_cgbf_tj3 = $("#asptjts_cgbf_tj3").val();
						    	var asptjts_cgbf_cs1 = $("#asptjts_cgbf_cs1").val();
						    	var asptjts_cgbf_cs2 = $("#asptjts_cgbf_cs2").val();
						    	var stvc_price = $("#stvc_price").val();
						    	var stvc_price_unit = $("#stvc_price_unit").val();
								var stvc_section = getSection("asptjts_cgbf_tj1", "asptjts_cgbf_tj2", "asptjts_cgbf_tj3", "asptjts_cgbf_cs1", "asptjts_cgbf_cs2");
						    	if(ssc_cb_id==""||reservation2==""||ssc_whs_id==""||ssc_area_id==""||ssc_item_type==""){
									alert("请先添加合同基础信息!");
									return;
								}
								if(stvc_section!=false){
									$.ajax({
							            url: "${root}control/sospController/saveSpjtCGBF.do?ssc_cb_id="+ssc_cb_id+"&CCF="+CCF+"&reservation2="+reservation2+"&ssc_whs_id="+ssc_whs_id+"&ssc_area_id="+ssc_area_id+"&ssc_item_type="+ssc_item_type
							           		+"&SPTJ="+SPTJ+"&stvc_section="+stvc_section+"&stvc_price="+stvc_price+"&stvc_price_unit="+stvc_price_unit,
							            type: "POST",
							            data: null,
							            dataType: "json",
							            success: function(data){
							            	alert(data.info);
							            	getCCFMainTB(ssc_cb_id);
							            	showSpjtCGBF(ssc_cb_id);
							            }
							        });
								}
							};
							function showSpjtCGBF(id){
								var ssc_whs_id = $("#ssc_whs_id").val();
								var ssc_area_id = $("#ssc_area_id").val();
								if(null==$("#ssc_area_id").val()){
									var ssc_area_id = $("#ssc_area_ids").val();
								}
								var ssc_item_type = $("#ssc_item_type").val();
								var CCF = $("#CCF").val();
								var url=root+"control/sospController/showSpjtCGBF.do?ssc_cb_id="+id+"&id2="+ssc_whs_id+"&id3="+ssc_area_id+"&id4="+ssc_item_type+"&id5="+CCF;;
						    	var htm_td="";
					    	   	$.ajax({
					    			type : "POST",
					    			url: url,  
					    			data:null,
					    			dataType: "json",  
					    			success : function(data) {
					    				var text="<table class='with-border' border='1'>"+
							    				"<tr class='title'>"+
												"<td>序号</td>"+
												"<td>体积区间</td>"+
												"<td>单价</td>"+
												"<td>操作</td>"+
												"</tr>";
					    				if(data.status=='y'){
						    				for(i=0;i<data.list.length;i++){
						    					htm_td=htm_td+"<tr><td>"+(i+1)+"</td><td>"+data.list[i].stvc_section+"</td><td>"+data.list[i].stvc_price+"/元/㎡/天</td><td><a onclick='deletee("+data.list[i].stvc_id+")'>删除</a></td></tr>";
						    				}
						    				$("SpjtCGBF").html(text+htm_td+"</table>");
					    				}else{
						    				$("SpjtCGBF").html(text+"</table>");
					    				}
					    			}
					    		});
							};
						</script>
					</div>
				</div>
			</div>
			<div id="SPTJ3" style="display: none;">
				<br />
				<div>
					<span style="color: red"> 阶梯价计算逻辑：实际存储体积*体积单价 </span>
				</div>
				<div>
					<span>---已维护的阶梯---</span>
				</div>
				<div>
					<SpjtALL></SpjtALL>
				</div>
				<div>
				<div class="add_button">
					<a data-toggle="tab" href="#tab_add">
						<button class="btn btn-xs btn-yellow" onclick="changeShow('SpjtAll')">
							<i class="icon-hdd"></i>新增
						</button>
					</a>
					</div>
					<div id="SpjtAll" class="moudle_dashed_border_width_90">
						<span style="color: red">阶梯价计算逻辑：实际存储体积*体积单价</span><br />
						<span style="color: red">公式说明：阶梯区间，需要在下方设置</span><br />
						<div align="center">
							<span>阶梯设置：</span>
						</div>
						<div class="div_margin">
						条件:
							<select id="asptjts_all_tj1" name="asptjts_all_tj1">
								<option value=">">></option>
								<option value=">=">>=</option>
							</select> 
							存储体积<input type="text"  id="asptjts_all_cs1" name="asptjts_all_cs1"/>
							<select><option>m³</option></select>
						</div>
						<div class="div_margin">
							组合方式:
							<select  id="asptjts_all_tj2" name="asptjts_all_tj2">
								<option>或者</option>
								<option>并且</option>
							</select>
						</div>
						<div class="div_margin">
							条件:<select  id="asptjts_all_tj3" name="asptjts_all_tj3">
								<option value="<">&lt;</option>
								<option value="<=">&lt;=</option>
							</select> 
							存储体积<input type="text"  id="asptjts_all_cs2" name="asptjts_all_cs2"/>
							<select><option>m³</option></select>
						</div>
						<div>
							单价:<input type="text" id="savc_price" name="savc_price"/>
							<select  id="savc_price_unit" name="savc_price_unit"><option value="0">／元／m³／天</option></select>
						</div>
						<div class="div_margin">
							<a data-toggle="tab" href="#tab_add">
								<button class="btn btn-xs btn-yellow" onclick="saveSpjtALL();">
									<i class="icon-hdd"></i>保存
								</button>
							</a>	
						</div>
						<script type="text/javascript">
							function saveSpjtALL(){
								//固定信息
						    	var ssc_cb_id = $("#ssc_cb_id").val();
						    	var reservation2 = $("#reservation2").val();
						    	var ssc_whs_id = $("#ssc_whs_id").val();
						    	var ssc_area_id = $("#ssc_area_id").val();
						    	var ssc_item_type = $("#ssc_item_type").val();
						    	var CCF = $("#CCF").val();
						    	var SPTJ = $("#SPTJ").val();
						    	//超过部分阶梯
						    	var asptjts_all_tj1 = $("#asptjts_all_tj1").val();
						    	var asptjts_all_tj2 = $("#asptjts_all_tj2").val();
						    	var asptjts_all_tj3 = $("#asptjts_all_tj3").val();
						    	var asptjts_all_cs1 = $("#asptjts_all_cs1").val();
						    	var asptjts_all_cs2 = $("#asptjts_all_cs2").val();
						    	var savc_price = $("#stvc_price").val();
						    	var savc_price_unit = $("#stvc_price_unit").val();
								var savc_section = getSection("asptjts_all_tj1", "asptjts_all_tj2", "asptjts_all_tj3", "asptjts_all_cs1", "asptjts_all_cs2");
		    					if(ssc_cb_id==""||reservation2==""||ssc_whs_id==""||ssc_area_id==""||ssc_item_type==""){
									alert("请先添加合同基础信息!");
									return;
								}
								if(savc_section!=false){
									$.ajax({
							            url: "${root}control/sospController/saveSpjtALL.do?ssc_cb_id="+ssc_cb_id+"&CCF="+CCF+"&reservation2="+reservation2+"&ssc_whs_id="+ssc_whs_id+"&ssc_area_id="+ssc_area_id+"&ssc_item_type="+ssc_item_type
							           		+"&SPTJ="+SPTJ+"&savc_section="+savc_section+"&savc_price="+savc_price+"&savc_price_unit="+savc_price_unit,
							            type: "POST",
							            data: null,
							            dataType: "json",
							            success: function(data){
							            	alert(data.info);
							            	getCCFMainTB(ssc_cb_id);
							            	showSpjtALL(ssc_cb_id);
							            }
							        });
								}
							};
							function showSpjtALL(id){
								var ssc_whs_id = $("#ssc_whs_id").val();
								var ssc_area_id = $("#ssc_area_id").val();
								if(null==$("#ssc_area_id").val()){
									var ssc_area_id = $("#ssc_area_ids").val();
								}
								var ssc_item_type = $("#ssc_item_type").val();
								var CCF = $("#CCF").val();
								var url=root+"control/sospController/showSpjtALL.do?ssc_cb_id="+id+"&id2="+ssc_whs_id+"&id3="+ssc_area_id+"&id4="+ssc_item_type+"&id5="+CCF;
						    	var htm_td="";
					    	   	$.ajax({
					    			type : "POST",
					    			url: url,  
					    			data:null,
					    			dataType: "json",  
					    			success : function(data) {
					    				var text="<table class='with-border' border='1'>"+
							    				"<tr class='title'>"+
												"<td>序号</td>"+
												"<td>体积区间</td>"+
												"<td>单价</td>"+
												"<td>操作</td>"+
												"</tr>";
					    				if(data.status=='y'){
						    				for(i=0;i<data.list.length;i++){
						    					htm_td=htm_td+"<tr><td>"+(i+1)+"</td><td>"+data.list[i].savc_section+"</td><td>"+data.list[i].savc_price+"/元/㎡/天</td><td><a onclick='deletef("+data.list[i].savc_id+")'>删除</a></td></tr>";
						    				}
						    				$("SpjtALL").html(text+htm_td+"</table>");
					    				}else{
						    				$("SpjtALL").html(text+"</table>");
					    				}
					    			}
					    		});
							};
						</script>
					</div>
				</div>
			</div>
		</div>
		<!-- 仓储费按件数结算 -->
		<div id="CCF4" class="moudle_dashed_border_width_90">
			<div>
				<span style="color: red">计算公式 : 每日库存余数 * 件数单价</span>
			</div>
			<div class="div_margin">
				<span>件数单价:<input id="ssc_number_price" name="ssc_number_price" type="text" value="" /></span>
				<select style="height: 28px; width: 100px;"  id="ssc_number_price_unit" name="ssc_number_price_unit">
					<option value="0">/元/件/天</option>
				</select>
			</div>
			<div class="div_margin">
				<a data-toggle="tab" href="#tab_add">
					<button class="btn btn-xs btn-yellow" onclick="saveCCF4();">
						<i class="icon-hdd"></i>保存
					</button>
				</a>	
			</div>
			<script type="text/javascript">
					function saveCCF4(){
						//固定信息
				    	var ssc_cb_id = $("#ssc_cb_id").val();
				    	var reservation2 = $("#reservation2").val();
				    	var ssc_whs_id = $("#ssc_whs_id").val();
				    	var ssc_area_id = $("#ssc_area_id").val();
				    	var ssc_item_type = $("#ssc_item_type").val();
				    	var CCF = $("#CCF").val();
				    	//超过部分阶梯
				    	var ssc_number_price = $("#ssc_number_price").val();
				    	var ssc_number_price_unit = $("#ssc_number_price_unit").val();
		    			if(ssc_cb_id==""||reservation2==""||ssc_whs_id==""||ssc_area_id==""||ssc_item_type==""){
							alert("请先添加合同基础信息!");
							return;
						}
						$.ajax({
				            url: "${root}control/sospController/saveCCF4.do?ssc_cb_id="+ssc_cb_id+"&CCF="+CCF+"&reservation2="+reservation2+"&ssc_whs_id="+ssc_whs_id+"&ssc_area_id="+ssc_area_id+"&ssc_item_type="+ssc_item_type
				           		+"&ssc_number_price="+ssc_number_price+"&ssc_number_price_unit="+ssc_number_price_unit,
				            type: "POST",
				            data: null,
				            dataType: "json",
				            success: function(data){
				            	alert(data.info);
				            	getCCFMainTB(ssc_cb_id);
				            }
				        });
					};
			</script>
		</div>
		<!-- 仓储费按件数反推所占面积 -->
		<div id="CCF5" class="moudle_dashed_border_width_90">
			<div>
				<span style="color: red">计算公式 : 系统根据每日库存结余来推算出所占平方数</span>
			</div>
			<div class="div_margin">
			  <div class="div_margin">
				<span>单平方所容纳件数：<input id="ssc_square_hold_the_number" name="ssc_square_hold_the_number" type="text" value="" /></span>
				<select style="height: 28px; width: 100px;" id="ssc_square_hold_the_number_unit" name="ssc_square_hold_the_number_unit">
					<option value="0">/件/㎡</option>
				</select>			  
			  </div>
			  <div class="div_margin">
				<span>&nbsp;&nbsp;&nbsp;&nbsp;平&nbsp;方&nbsp;单&nbsp;价:<input id="ssc_square_price" name="ssc_square_price" type="text" value="" /></span>
				<select style="height: 28px; width: 100px;" id="ssc_square_price_unit" name="ssc_square_price_unit">
					<option value="0">/元/㎡/天</option>
				</select>			  
			  </div>
			</div>
			<div class="div_margin">
				<a data-toggle="tab" href="#tab_add">
					<button class="btn btn-xs btn-yellow" onclick="saveCCF5();">
						<i class="icon-hdd"></i>保存
					</button>
				</a>	
			</div>	
			<script type="text/javascript">
					function saveCCF5(){
						//固定信息
				    	var ssc_cb_id = $("#ssc_cb_id").val();
				    	var reservation2 = $("#reservation2").val();
				    	var ssc_whs_id = $("#ssc_whs_id").val();
				    	var ssc_area_id = $("#ssc_area_id").val();
				    	var ssc_item_type = $("#ssc_item_type").val();
				    	var CCF = $("#CCF").val();
				    	//
				    	var ssc_square_hold_the_number = $("#ssc_square_hold_the_number").val();
				    	var ssc_square_hold_the_number_unit = $("#ssc_square_hold_the_number_unit").val();
				    	var ssc_square_price = $("#ssc_square_price").val();
				    	var ssc_square_price_unit = $("#ssc_square_price_unit").val();
				    	if(ssc_cb_id==""||reservation2==""||ssc_whs_id==""||ssc_area_id==""||ssc_item_type==""){
							alert("请先添加合同基础信息!");
							return;
						}
						$.ajax({
				            url: "${root}control/sospController/saveCCF5.do?ssc_cb_id="+ssc_cb_id+"&CCF="+CCF+"&reservation2="+reservation2+"&ssc_whs_id="+ssc_whs_id+"&ssc_area_id="+ssc_area_id+"&ssc_item_type="+ssc_item_type
				           		+"&ssc_square_hold_the_number="+ssc_square_hold_the_number+"&ssc_square_hold_the_number_unit="+ssc_square_hold_the_number_unit
				           		+"&ssc_square_price="+ssc_square_price+"&ssc_square_price_unit="+ssc_square_price_unit,
				            type: "POST",
				            data: null,
				            dataType: "json",
				            success: function(data){
				            	alert(data.info);
				            	getCCFMainTB(ssc_cb_id);
				            }
				        });
					};
			</script>		
		</div>
		<!-- 仓储费按托盘结算 -->
		<div id="CCF6"  style="display: none;">
			<div>
				<span style="color: red">计算公式 : 实际使用托盘数 * 托盘单价</span>
			</div>
			<br />
			<div class="form-group" style="margin: 10px;">
				<select onchange="showTP();" id="TP" name="TP" style="width: 30%;">
					<option value="0">请选择</option>
					<option value="1">无阶梯</option>
					<option value="2">超过部分托盘数阶梯</option>
					<option value="3">总占用托盘数阶梯</option>
				</select>
			</div>
			<div id="TP1" class="moudle_dashed_border_width_90">
				<span>托盘单价:<input id="ssc_tray_price" name="ssc_tray_price" type="text" value="" /></span>
				<select style="height: 28px; width: 100px;" id="ssc_tray_price_unit" name="ssc_tray_price_unit">
					<option value="0">/元/个/天</option>
				</select>
				<div class="div_margin">
					<a data-toggle="tab" href="#tab_add">
						<button class="btn btn-xs btn-yellow" onclick="saveTPJSWJT();">
							<i class="icon-hdd"></i>保存
						</button>
					</a>	
				</div>
				<script type="text/javascript">
					function saveTPJSWJT(){
						//固定信息
				    	var ssc_cb_id = $("#ssc_cb_id").val();
				    	var reservation2 = $("#reservation2").val();
				    	var ssc_whs_id = $("#ssc_whs_id").val();
				    	var ssc_area_id = $("#ssc_area_id").val();
				    	var ssc_item_type = $("#ssc_item_type").val();
				    	var CCF = $("#CCF").val();
				    	var TP = $("#TP").val();
				    	//超过部分阶梯
				    	var ssc_tray_price = $("#ssc_tray_price").val();
				    	var ssc_tray_price_unit = $("#ssc_tray_price_unit").val();
		    			if(ssc_cb_id==""||reservation2==""||ssc_whs_id==""||ssc_area_id==""||ssc_item_type==""){
							alert("请先添加合同基础信息!");
							return;
						}
						$.ajax({
				            url: "${root}control/sospController/saveTPJSWJT.do?ssc_cb_id="+ssc_cb_id+"&CCF="+CCF+"&reservation2="+reservation2+"&ssc_whs_id="+ssc_whs_id+"&ssc_area_id="+ssc_area_id+"&ssc_item_type="+ssc_item_type
				           		+"&TP="+TP+"&ssc_tray_price="+ssc_tray_price+"&ssc_tray_price_unit="+ssc_tray_price_unit,
				            type: "POST",
				            data: null,
				            dataType: "json",
				            success: function(data){
				            	alert(data.info);
				            	getCCFMainTB(ssc_cb_id);
				            }
				        });
					};
				</script>
			</div>
			<div id="TP2" style="display: none;">
				<div class="div_margin"> 
					<span>已维护的阶梯</span>
				</div>
				<div>
					<TPCGBF></TPCGBF>
				</div>
				<div>
				   <div class="add_button">
					<a data-toggle="tab" href="#tab_add">
						<button class="btn btn-xs btn-yellow" onclick="changeShow('TpCGBF')">
							<i class="icon-hdd"></i>新增
						</button>
					</a>
					</div>
					<div id="TpCGBF" class="moudle_dashed_border_width_90">
						<span style="color: red">阶梯价计算逻辑：（实际使用托盘数-维护条件托盘数）*单价</span><br />
						<span style="color: red">公式说明：阶梯区间，需要在下方设置</span><br />
						<div align="left">
							<span>阶梯设置：</span>
						</div>
						<div class="div_margin">
						条件:
							<select id="atpjs_cgbf_tj1" name="atpjs_cgbf_tj1">
								<option value=">">></option>
								<option value=">=">>=</option>
							</select> 
							实际使用托盘数<input type="text" id="atpjs_cgbf_cs1" name="atpjs_cgbf_cs1" />
							<select><option>/个</option></select>
						</div>
						<div class="div_margin">
							组合方式:
							<select  id="atpjs_cgbf_tj2" name="atpjs_cgbf_tj2">
								<option value="1">并且</option>
							</select>
						</div>
						<div class="div_margin">
							条件:<select  id="atpjs_cgbf_tj3" name="atpjs_cgbf_tj3">
								<option value="<">&lt;</option>
								<option value="<=">&lt;=</option>
							</select> 
							实际使用托盘数<input type="text"  id="atpjs_cgbf_cs2" name="atpjs_cgbf_cs2"/>
							<select><option>/个</option></select>
						</div>
						<div class="div_margin">
							单价:<input type="text" id="stt_price" name="stt_price"/>
							<select id="stt_price_unit" name="stt_price_unit"><option value="0">/元/个/天</option></select>
						</div>
						<div class="div_margin">
							<a data-toggle="tab" href="#tab_add">
								<button class="btn btn-xs btn-yellow" onclick="saveTPJS_CGBF();">
									<i class="icon-hdd"></i>保存
								</button>
							</a>	
						</div>	
						<script type="text/javascript">
							function saveTPJS_CGBF(){
								//固定信息
						    	var ssc_cb_id = $("#ssc_cb_id").val();
						    	var reservation2 = $("#reservation2").val();
						    	var ssc_whs_id = $("#ssc_whs_id").val();
						    	var ssc_area_id = $("#ssc_area_id").val();
						    	var ssc_item_type = $("#ssc_item_type").val();
						    	var CCF = $("#CCF").val();
						    	var TP = $("#TP").val();
						    	//超过部分阶梯
						    	var ssc_tray_price = $("#ssc_tray_price").val();
						    	var ssc_tray_price_unit = $("#ssc_tray_price_unit").val();
						    	var atpjs_cgbf_tj1 = $("#atpjs_cgbf_tj1").val();
						    	var atpjs_cgbf_tj2 = $("#atpjs_cgbf_tj2").val();
						    	var atpjs_cgbf_tj3 = $("#atpjs_cgbf_tj3").val();
						    	var atpjs_cgbf_cs1 = $("#atpjs_cgbf_cs1").val();
						    	var atpjs_cgbf_cs2 = $("#atpjs_cgbf_cs2").val();
						    	var stt_price = $("#stt_price").val();
						    	var stt_price_unit = $("#stt_price_unit").val();
								var stt_section = getSection("atpjs_cgbf_tj1", "atpjs_cgbf_tj2", "atpjs_cgbf_tj3", "atpjs_cgbf_cs1", "atpjs_cgbf_cs2");
		    					if(ssc_cb_id==""||reservation2==""||ssc_whs_id==""||ssc_area_id==""||ssc_item_type==""){
									alert("请先添加合同基础信息!");
									return;
								}
								if(stt_section!=false){
									$.ajax({
							            url: "${root}control/sospController/saveTPJS_CGBF.do?ssc_cb_id="+ssc_cb_id+"&CCF="+CCF+"&reservation2="+reservation2+"&ssc_whs_id="+ssc_whs_id+"&ssc_area_id="+ssc_area_id+"&ssc_item_type="+ssc_item_type
							           		+"&TP="+TP+"&stt_price="+stt_price+"&stt_price_unit="+stt_price_unit+"&stt_section="+stt_section,
							            type: "POST",
							            data: null,
							            dataType: "json",
							            success: function(data){
							            	alert(data.info);
							            	getCCFMainTB(ssc_cb_id);
							            	showTPJS_CGBF(ssc_cb_id);
							            }
							        });
								}
							};
							function showTPJS_CGBF(id){
								var ssc_whs_id = $("#ssc_whs_id").val();
								var ssc_area_id = $("#ssc_area_id").val();
								if(null==$("#ssc_area_id").val()){
									var ssc_area_id = $("#ssc_area_ids").val();
								}
								var ssc_item_type = $("#ssc_item_type").val();
								var CCF = $("#CCF").val();
								var url=root+"control/sospController/showTPJS_CGBF.do?ssc_cb_id="+id+"&id2="+ssc_whs_id+"&id3="+ssc_area_id+"&id4="+ssc_item_type+"&id5="+CCF;
						    	var htm_td="";
					    	   	$.ajax({
					    			type : "POST",
					    			url: url,  
					    			data:null,
					    			dataType: "json",  
					    			success : function(data) {
					    				var text="<table class='with-border' border='1'>"+
							    				"<tr class='title'>"+
												"<td>序号</td>"+
												"<td>托盘数区间</td>"+
												"<td>单价</td>"+
												"<td>操作</td>"+
												"</tr>";
					    				if(data.status=='y'){
						    				for(i=0;i<data.list.length;i++){
						    					htm_td=htm_td+"<tr><td>"+(i+1)+"</td><td>"+data.list[i].stt_section+"</td><td>"+data.list[i].stt_price+"/元/㎡/天</td><td><a onclick='deletett("+data.list[i].stt_id+")'>删除</a></td></tr>";
						    				}
						    				$("TPCGBF").html(text+htm_td+"</table>");
					    				}else{
						    				$("TPCGBF").html(text+"</table>");
					    				}
					    			}
					    		});
							};
						</script>					
					</div>
				</div>
			</div>
			<div id="TP3" style="display: none;">
				<div class="div_margin">
					<span>---已维护的阶梯---</span>
				</div>
				<div>
					<TPALL></TPALL>
				</div>
				<div>
				    <div class="add_button">
					<a data-toggle="tab" href="#tab_add">
						<button class="btn btn-xs btn-yellow" onclick="changeShow('TpAll')">
							<i class="icon-hdd"></i>新增
						</button>
					</a>
					</div>
					<div id="TpAll" class="moudle_dashed_border_width_90">
						<span style="color: red">阶梯价计算逻辑：实际使用托盘数*单价</span><br />
						<span style="color: red">公式说明：阶梯区间，需要在下方设置</span><br />
						<div align="center">
							<span>阶梯设置：</span>
						</div>
						<div class="div_margin">
						条件:
							<select id="atpjs_all_tj1" name="atpjs_all_tj1">
								<option value=">">></option>
								<option value=">=">>=</option>
							</select> 
							实际使用托盘数<input type="text" id="atpjs_all_cs1" name="atpjs_all_cs1"/>
							<select><option>/个</option></select>
						</div>
						<div class="div_margin">
							组合方式:
							<select  id="atpjs_all_tj2" name="atpjs_all_tj2">
								<option value="1">并且</option>
							</select>
						</div>
						<div class="div_margin">
							<select  id="atpjs_all_tj3" name="atpjs_all_tj3">
								<option value="<">&lt;</option>
								<option value="<=">&lt;=</option>
							</select> 
							实际使用托盘数<input type="text" id="atpjs_all_cs2" name="atpjs_all_cs2"/>
							<select><option>/个</option></select>
						</div>
						<div>
							单价:<input type="text" id="sat_price" name="sat_price"/>
							<select id="sat_price_unit" name="sat_price_unit">
							<option value="0">/元/个/天</option></select>
						</div>
						<div class="div_margin">
							<a data-toggle="tab" href="#tab_add">
								<button class="btn btn-xs btn-yellow" onclick="saveTPJS_ALL();">
									<i class="icon-hdd"></i>保存
								</button>
							</a>	
						</div>
						<script type="text/javascript">
							function saveTPJS_ALL(){
								//固定信息
						    	var ssc_cb_id = $("#ssc_cb_id").val();
						    	var reservation2 = $("#reservation2").val();
						    	var ssc_whs_id = $("#ssc_whs_id").val();
						    	var ssc_area_id = $("#ssc_area_id").val();
						    	var ssc_item_type = $("#ssc_item_type").val();
						    	var CCF = $("#CCF").val();
						    	var TP = $("#TP").val();
						    	//超过部分阶梯
						    	var ssc_tray_price = $("#ssc_tray_price").val();
						    	var ssc_tray_price_unit = $("#ssc_tray_price_unit").val();

						    	var atpjs_all_tj1 = $("#atpjs_all_tj1").val();
						    	var atpjs_all_tj2 = $("#atpjs_all_tj2").val();
						    	var atpjs_all_tj3 = $("#atpjs_all_tj3").val();
						    	var atpjs_all_cs1 = $("#atpjs_all_cs1").val();
						    	var atpjs_all_cs2 = $("#atpjs_all_cs2").val();
						    	var sat_price = $("#sat_price").val();
						    	var sat_price_unit = $("#sat_price_unit").val();
								var sat_section = getSection("atpjs_all_tj1", "atpjs_all_tj2", "atpjs_all_tj3", "atpjs_all_cs1", "atpjs_all_cs2");
								if(ssc_cb_id==""||reservation2==""||ssc_whs_id==""||ssc_area_id==""||ssc_item_type==""){
									alert("请先添加合同基础信息!");
									return;
								}
								if(sat_section!=false){
									$.ajax({
							            url: "${root}control/sospController/saveTPJS_ALL.do?ssc_cb_id="+ssc_cb_id+"&CCF="+CCF+"&reservation2="+reservation2+"&ssc_whs_id="+ssc_whs_id+"&ssc_area_id="+ssc_area_id+"&ssc_item_type="+ssc_item_type
							           		+"&TP="+TP+"&sat_price="+sat_price+"&sat_price_unit="+sat_price_unit+"&sat_section="+sat_section,
							            type: "POST",
							            data: null,
							            dataType: "json",
							            success: function(data){
							            	alert(data.info);
							            	getCCFMainTB(ssc_cb_id);
							            	showTPJS_ALL(ssc_cb_id);
							            }
							        });
								}
							};
							function showTPJS_ALL(id){
								var ssc_whs_id = $("#ssc_whs_id").val();
								var ssc_area_id = $("#ssc_area_id").val();
								if(null==$("#ssc_area_id").val()){
									var ssc_area_id = $("#ssc_area_ids").val();
								}
								var ssc_item_type = $("#ssc_item_type").val();
								var CCF = $("#CCF").val();
								var url=root+"control/sospController/showTPJS_ALL.do?ssc_cb_id="+id+"&id2="+ssc_whs_id+"&id3="+ssc_area_id+"&id4="+ssc_item_type+"&id5="+CCF;
						    	var htm_td="";
					    	   	$.ajax({
					    			type : "POST",
					    			url: url,  
					    			data:null,
					    			dataType: "json",  
					    			success : function(data) {
					    				var text="<table class='with-border' border='1'>"+
							    				"<tr class='title'>"+
												"<td>序号</td>"+
												"<td>托盘数区间</td>"+
												"<td>单价</td>"+
												"<td>操作</td>"+
												"</tr>";
					    				if(data.status=='y'){
						    				for(i=0;i<data.list.length;i++){
						    					htm_td=htm_td+"<tr><td>"+(i+1)+"</td><td>"+data.list[i].sat_section+"</td><td>"+data.list[i].sat_price+"/元/㎡/天</td><td><a onclick='deletej("+data.list[i].sat_id+")'>删除</a></td></tr>";
						    				}
						    				$("TPALL").html(text+htm_td+"</table>");
					    				}else{
						    				$("TPALL").html(text+"</table>");
					    				}
					    			}
					    		});
							};
						</script>	
					</div>
				</div>
			</div>
		</div>
		<!-- 仓储费按托盘存放数反推 -->
		<div id="CCF7" style="display: none;">
			<div class="div_margin">
				<span style="color: red">计算公式 : 商品每日库存结余 ÷ 单个托盘可放商品数 * 托盘单价</span>
			</div>
			<div class="form-group" class="div_margin">
				<select onchange="showTPFT();" id="TPFT" name="TPFT" style="width: 30%;">
					<option value="0">请选择</option>
					<option value="1">无阶梯</option>
					<option value="2">超过部分托盘数阶梯</option>
					<option value="3">总占用托盘数阶梯</option>
				</select>
			</div>
			<div id="TPFT1" class="moudle_dashed_border">
			  	<div class="div_margin">
					<span>单个托盘可放商品数:<input id="ssc_single_tray_number" name="ssc_single_tray_number" type="text" value="" /></span>
					<select style="height: 28px; width: 100px;" id="ssc_single_tray_number_unit" name="ssc_single_tray_number_unit">
						<option value="0">/件</option>
					</select>			  
				  </div>
				  <div class="div_margin">
					<span>&nbsp;&nbsp;&nbsp;&nbsp;托&nbsp;&nbsp;盘&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;单&nbsp;&nbsp;价:&nbsp;&nbsp;&nbsp;&nbsp;
					<input id="ssc_singlt_tray_price" name="ssc_singlt_tray_price" type="text" value="" /></span>
					<select style="height: 28px; width: 100px;" id="ssc_singlt_tray_price_unit" name="ssc_singlt_tray_price_unit">
						<option value="0">/元/个/天</option>
					</select>			  
				  </div>
				  <div class="div_margin">
					<a data-toggle="tab" href="#tab_add">
						<button class="btn btn-xs btn-yellow" onclick="saveTPFT_WJT();">
							<i class="icon-hdd"></i>保存
						</button>
					</a>	
				</div>
				<script type="text/javascript">
					function saveTPFT_WJT(){
						//固定信息
				    	var ssc_cb_id = $("#ssc_cb_id").val();
				    	var reservation2 = $("#reservation2").val();
				    	var ssc_whs_id = $("#ssc_whs_id").val();
				    	var ssc_area_id = $("#ssc_area_id").val();
				    	var ssc_item_type = $("#ssc_item_type").val();
				    	var CCF = $("#CCF").val();
				    	var TPFT = $("#TPFT").val();
				    	//无阶梯
				    	var ssc_single_tray_number = $("#ssc_single_tray_number").val();
				    	var ssc_single_tray_number_unit = $("#ssc_single_tray_number_unit").val();
				    	var ssc_singlt_tray_price = $("#ssc_singlt_tray_price").val();
				    	var ssc_singlt_tray_price_unit = $("#ssc_singlt_tray_price_unit").val();
				    	if(ssc_cb_id==""||reservation2==""||ssc_whs_id==""||ssc_area_id==""||ssc_item_type==""){
							alert("请先添加合同基础信息!");
							return;
						}
						$.ajax({
				            url: "${root}control/sospController/saveTPFT_WJT.do?ssc_cb_id="+ssc_cb_id+"&CCF="+CCF+"&reservation2="+reservation2+"&ssc_whs_id="+ssc_whs_id+"&ssc_area_id="+ssc_area_id+"&ssc_item_type="+ssc_item_type
				           		+"&TPFT="+TPFT
				           		+"&ssc_single_tray_number="+ssc_single_tray_number+"&ssc_single_tray_number_unit="+ssc_single_tray_number_unit
				           		+"&ssc_singlt_tray_price="+ssc_singlt_tray_price+"&ssc_singlt_tray_price_unit="+ssc_singlt_tray_price_unit,
				            type: "POST",
				            data: null,
				            dataType: "json",
				            success: function(data){
				            	alert(data.info);
				            	getCCFMainTB(ssc_cb_id);
				            }
				        });
					};
				</script>
			</div>
			<div id="TPFT2" style="display: none;">
				<div class="div_margin">
					<span>---已维护的阶梯---</span>
				</div>
				<div>
					<ATPFTCGBF></ATPFTCGBF>
				</div>
				<div class="add_button">
					<a data-toggle="tab" href="#tab_add">
						<button class="btn btn-xs btn-yellow" onclick="changeShow('TpftCGBF')">
							<i class="icon-hdd"></i>新增
						</button>
					</a>
				</div>
				<div class="div_margin">
					<div id="TpftCGBF" class="moudle_dashed_border_width_90">
					
						<span style="color: red">单个托盘可放商品数</span>
						<br />
						<span>单个托盘可放商品数<input id="ssc_single_tray_number_cgbf" name="ssc_single_tray_number_cgbf" type="text" value="" /></span>
						<select style="height: 28px; width: 100px;" id="ssc_single_tray_number_unit_cgbf" name="ssc_single_tray_number_unit_cgbf">
							<option>/件</option>
						</select>
						<br>
						<span style="color: red">阶梯价计算逻辑：（实际使用托盘数-维护条件托盘数）*托盘单价</span><br />
						<span style="color: red">公式说明：阶梯区间，需要在下方设置</span><br />
						<div align="center">
							<span>阶梯设置：</span>
						</div>
						<div class="div_margin">
						条件:
							<select id="atpft_cgbf_tj1" name="atpft_cgbf_tj1">
								<option value=">">></option>
								<option value=">=">>=</option>
							</select> 
							实际使用托盘数<input type="text" id="atpft_cgbf_cs1" name="atpft_cgbf_cs1"/>
							<select><option>/个</option></select>
						</div>
						<div class="div_margin">
							组合方式:
							<select id="atpft_cgbf_tj2" name="atpft_cgbf_tj2">
								<option>或者</option>
								<option>并且</option>
							</select>
						</div>
						<div class="div_margin">
							条件:<select id="atpft_cgbf_tj3" name="atpft_cgbf_tj3">
								<option value="<">&lt;</option>
								<option value="<=">&lt;=</option>
							</select> 
							实际使用托盘数<input type="text" id="atpft_cgbf_cs2" name="atpft_cgbf_cs2" />
							<select><option>/个</option></select>
						</div>
						<div class="div_margin">
							单价:<input type="text" id="stat_price" name="stat_price" />
							<select id="stat_price_unit" name="stat_price_unit" >
								<option value="0">/元/个/天</option>
							</select>
						</div>
						<div class="div_margin">
							<a data-toggle="tab" href="#tab_add">
								<button class="btn btn-xs btn-yellow" onclick="saveTPFT_CGBF();">
									<i class="icon-hdd"></i>保存
								</button>
							</a>	
						</div>
						<script type="text/javascript">
							function saveTPFT_CGBF(){
								//固定信息
						    	var ssc_cb_id = $("#ssc_cb_id").val();
						    	var reservation2 = $("#reservation2").val();
						    	var ssc_whs_id = $("#ssc_whs_id").val();
						    	var ssc_area_id = $("#ssc_area_id").val();
						    	var ssc_item_type = $("#ssc_item_type").val();
						    	var CCF = $("#CCF").val();
						    	var TPFT = $("#TPFT").val();
						    	//超过部分阶梯
						    	var stat_price = $("#stat_price").val();
						    	var stat_price_unit = $("#stat_price_unit").val();
						    	var ssc_single_tray_number = $("#ssc_single_tray_number_cgbf").val();
						    	var ssc_single_tray_number_unit = $("#ssc_single_tray_number_unit_cgbf").val();
						    	var atpft_cgbf_tj1 = $("#atpft_cgbf_tj1").val();
						    	var atpft_cgbf_tj2 = $("#atpft_cgbf_tj2").val();
						    	var atpft_cgbf_tj3 = $("#atpft_cgbf_tj3").val();
						    	var atpft_cgbf_cs1 = $("#atpft_cgbf_cs1").val();
						    	var atpft_cgbf_cs2 = $("#atpft_cgbf_cs2").val();
								var stat_section = getSection("atpft_cgbf_tj1", "atpft_cgbf_tj2", "atpft_cgbf_tj3", "atpft_cgbf_cs1", "atpft_cgbf_cs2");
		    					if(ssc_cb_id==""||reservation2==""||ssc_whs_id==""||ssc_area_id==""||ssc_item_type==""){
									alert("请先添加合同基础信息!");
									return;
								}
								if(stat_section!=false){
									$.ajax({
							            url: "${root}control/sospController/saveTPFT_CGBF.do?ssc_cb_id="+ssc_cb_id+"&CCF="+CCF+"&reservation2="+reservation2+"&ssc_whs_id="+ssc_whs_id+"&ssc_area_id="+ssc_area_id+"&ssc_item_type="+ssc_item_type
							           		+"&TPFT="+TPFT+"&stat_price="+stat_price+"&stat_price_unit="+stat_price_unit+"&stat_section="+stat_section+"&ssc_single_tray_number="+ssc_single_tray_number+"&ssc_single_tray_number_unit="+ssc_single_tray_number_unit,
							            type: "POST",
							            data: null,
							            dataType: "json",
							            success: function(data){
							            	alert(data.info);
							            	getCCFMainTB(ssc_cb_id);
							            	showTPFT_CGBF(ssc_cb_id);
							            }
							        });
								}
							};
							function showTPFT_CGBF(id){
								var ssc_whs_id = $("#ssc_whs_id").val();
								var ssc_area_id = $("#ssc_area_id").val();
								if(null==$("#ssc_area_id").val()){
									var ssc_area_id = $("#ssc_area_ids").val();
								}
								var ssc_item_type = $("#ssc_item_type").val();
								var CCF = $("#CCF").val();
								var url=root+"control/sospController/showTPFT_CGBF.do?ssc_cb_id="+id+"&id2="+ssc_whs_id+"&id3="+ssc_area_id+"&id4="+ssc_item_type+"&id5="+CCF;
						    	var htm_td="";
					    	   	$.ajax({
					    			type : "POST",
					    			url: url,  
					    			data:null,
					    			dataType: "json",  
					    			success : function(data) {
					    				var text="<table class='with-border' border='1'>"+
							    				"<tr class='title'>"+
												"<td>序号</td>"+
												"<td>托盘数区间</td>"+
												"<td>单价</td>"+
												"<td>操作</td>"+
												"</tr>";
					    				if(data.status=='y'){
						    				for(i=0;i<data.list.length;i++){
						    					htm_td=htm_td+"<tr><td>"+(i+1)+"</td><td>"+data.list[i].stat_section+"</td><td>"+data.list[i].stat_price+"/元/㎡/天</td><td><a onclick='deleteh("+data.list[i].stst_id+")'>删除</a></td></tr>";
						    				}
						    				$("ATPFTCGBF").html(text+htm_td+"</table>");
					    				}else{
						    				$("ATPFTCGBF").html(text+"</table>");
					    				}
					    			}
					    		});
							};
						</script>		
					</div>
				</div>
			</div>
			<div id="TPFT3" style="display: none;">
				<div class="div_margin">
					<span>---已维护的阶梯---</span>
				</div>
				<div>
					<ATPFTALL></ATPFTALL>
				</div>
					<div class="add_button">
					<a data-toggle="tab" href="#tab_add">
						<button class="btn btn-xs btn-yellow" onclick="changeShow('TpftAll')">
							<i class="icon-hdd"></i>新增
						</button>
					</a>
					</div>				
				<div>
					<div id="TpftAll" class="moudle_dashed_border_width_90">
						<span style="color: red">单个托盘可放商品数</span>
						<br />
						<span>单个托盘可放商品数<input id="ssc_single_tray_number_all" name="ssc_single_tray_number_all" type="text" value="" /></span>
						<select style="height: 28px; width: 100px;" id="ssc_single_tray_number_unit_all" name="ssc_single_tray_number_unit_all">
							<option>/件</option>
						</select>
						<br>
						<span style="color: red">阶梯价计算逻辑：实际使用托盘数*托盘单价</span><br />
						<span style="color: red">公式说明：阶梯区间，需要在下方设置</span><br />
						<div align="center">
							<span>阶梯设置：</span>
						</div>
						<div class="div_margin">
						条件:
							<select id="atpft_all_tj1" name="atpft_all_tj1">
								<option value=">">></option>
								<option value=">=">>=</option>
							</select> 
							实际使用托盘数<input type="text" id="atpft_all_cs1" name="atpft_all_cs1"/>
							<select><option>/个</option></select>
						</div>
						<div class="div_margin">
							组合方式:
							<select  id="atpft_all_tj2" name="atpft_all_tj2">
								<option value="1">并且</option>
							</select>
						</div>
						<div class="div_margin">
							条件:<select id="atpft_all_tj3" name="atpft_all_tj3">
								<option value="<">&lt;</option>
								<option value="<=">&lt;=</option>
							</select> 
							实际使用托盘数<input type="text" id="atpft_all_cs2" name="atpft_all_cs2"/>
							<select ><option value="0">/个</option></select>
						</div>
						<div>
							单价:<input id="sast_price" name="sast_price" type="text" />
							<select id="sast_price_unit" name="sast_price_unit" ><option value="0">/元/个/天</option></select>
						</div>

					</div>
					<div class="div_margin">
						<a data-toggle="tab" href="#tab_add">
							<button class="btn btn-xs btn-yellow" onclick="saveTPFT_ALL();">
								<i class="icon-hdd"></i>保存
							</button>
						</a>	
					</div>
					<script type="text/javascript">
						function saveTPFT_ALL(){
							//固定信息
					    	var ssc_cb_id = $("#ssc_cb_id").val();
					    	var reservation2 = $("#reservation2").val();
					    	var ssc_whs_id = $("#ssc_whs_id").val();
					    	var ssc_area_id = $("#ssc_area_id").val();
					    	var ssc_item_type = $("#ssc_item_type").val();
					    	var CCF = $("#CCF").val();
					    	var TPFT = $("#TPFT").val();
					    	//超过部分阶梯
					    	var sast_price = $("#sast_price").val();
					    	var sast_price_unit = $("#sast_price_unit").val();
					    	var ssc_single_tray_number = $("#ssc_single_tray_number_all").val();
					    	var ssc_single_tray_number_unit = $("#ssc_single_tray_number_unit_all").val();
					    	var atpft_all_tj1 = $("#atpft_all_tj1").val();
					    	var atpft_all_tj2 = $("#atpft_all_tj2").val();
					    	var atpft_all_tj3 = $("#atpft_all_tj3").val();
					    	var atpft_all_cs1 = $("#atpft_all_cs1").val();
					    	var atpft_all_cs2 = $("#atpft_all_cs2").val();
							var sast_section = getSection("atpft_all_tj1", "atpft_all_tj2", "atpft_all_tj3", "atpft_all_cs1", "atpft_all_cs2");
		    				if(ssc_cb_id==""||reservation2==""||ssc_whs_id==""||ssc_area_id==""||ssc_item_type==""){
								alert("请先添加合同基础信息!");
								return;
							}
							if(sast_section!=false){
								$.ajax({
						            url: "${root}control/sospController/saveTPFT_ALL.do?ssc_cb_id="+ssc_cb_id+"&CCF="+CCF+"&reservation2="+reservation2+"&ssc_whs_id="+ssc_whs_id+"&ssc_area_id="+ssc_area_id+"&ssc_item_type="+ssc_item_type
						           		+"&TPFT="+TPFT+"&sast_price="+sast_price+"&sast_price_unit="+sast_price_unit+"&sast_section="+sast_section+"&ssc_single_tray_number="+ssc_single_tray_number+"&ssc_single_tray_number_unit="+ssc_single_tray_number_unit,
						            type: "POST",
						            data: null,
						            dataType: "json",
						            success: function(data){
						            	alert(data.info);
						            	getCCFMainTB(ssc_cb_id);
						            	showTPFT_ALL(ssc_cb_id);
						            }
						        });
							}
						};
						function showTPFT_ALL(id){
							var ssc_whs_id = $("#ssc_whs_id").val();
							var ssc_area_id = $("#ssc_area_id").val();
							if(null==$("#ssc_area_id").val()){
								var ssc_area_id = $("#ssc_area_ids").val();
							}
							var ssc_item_type = $("#ssc_item_type").val();
							var CCF = $("#CCF").val();
							var url=root+"control/sospController/showTPFT_ALL.do?ssc_cb_id="+id+"&id2="+ssc_whs_id+"&id3="+ssc_area_id+"&id4="+ssc_item_type+"&id5="+CCF;
					    	var htm_td="";
				    	   	$.ajax({
				    			type : "POST",
				    			url: url,  
				    			data:null,
				    			dataType: "json",  
				    			success : function(data) {
				    				var text="<table class='with-border' border='1'>"+
						    				"<tr class='title'>"+
											"<td>序号</td>"+
											"<td>托盘数区间</td>"+
											"<td>单价</td>"+
											"<td>操作</td>"+
											"</tr>";
				    				if(data.status=='y'){
					    				for(i=0;i<data.list.length;i++){
					    					htm_td=htm_td+"<tr><td>"+(i+1)+"</td><td>"+data.list[i].sast_section+"</td><td>"+data.list[i].sast_price+"/元/㎡/天</td><td><a onclick='deletei("+data.list[i].sast_id+")'>删除</a></td></tr>";
					    				}
					    				$("ATPFTALL").html(text+htm_td+"</table>");
				    				}else{
					    				$("ATPFTALL").html(text+"</table>");
				    				}
				    			}
				    		});
						};
					</script>					
				</div>
			</div>
		</div>
	</div>
	
	<div>
		<div class="div_margin">
			<label class="control-label blue">
				管理费
			</label>
			<input 
			id="cb_managementFee_other_ccf" 
			type="checkbox" 
			class="ace ace-switch ace-switch-5" ${ccfFeeFlag == true ? 'checked="checked"' : '' } 
			onchange="managementOtherShow('ccf');"/>
			<span class="lbl"></span>
		</div>
		<!-- 管理费 -->
		<div id="managementFeeOther_ccf" class="moudle_dashed_border" style="width:100%;border:0px;${ccfFeeFlag == true ? 'display:block;' : '' } ">
			<iframe id="iframe_ccf" class="with-border" style="overflow: visible; border:0px;margin:0px;padding:0px;background: rgba(0,0,0,0);width: 100%;" src="${root }control/expressContractController/toManagementFeeOther.do?management_fee_type=ccf&cb_id=${cb.id }"></iframe>
		</div>
	</div>
</div>	

<script type="text/javascript">
	$(document).ready(function(){
		//打开页面加载时间控件
		$("#reservation2").daterangepicker(null, function(start, end, label) {
	    	console.log(start.toISOString(), end.toISOString(), label);
	   	});
		//打开页面默认加载主列表
		getCCFMainTB($("#ssc_cb_id").val());
	});
	
	//编辑明细
	function getCCF(id){
		$.ajax({
			type : "POST",
			url: root+"/control/sospController/showStorageCharge.do?ssc_id="+id,  
			data:null,
			dataType: "json",  
			success : function(data) {
				if(data.status=='y'){
					$("#CCFDIV").css("display", "block");
				  	$("#ssc_whs_id").val(data.storageCharge.ssc_whs_id);
				  	$("#ssc_area_id option").remove();
					var whsid = $("#ssc_whs_id").val();
					$("#ssc_area_id option").remove();
					var whsid = $("#ssc_whs_id").val();
					$.ajax({
			            url: "${root}control/sospController/getArea.do?id="+whsid,
			            type: "POST",
			            data: null,
			            dataType: "json",
			            success: function(data){
			            	 $.each(data.whList,function(n,value) {
			            		 $("#ssc_area_id").append("<option value='"+value.id+"'>"+value.area_name+"</option>");
							});  
			            }
			        });
					$("#ssc_area_ids").val(data.storageCharge.ssc_area_id);
					$("#ssc_item_type").val(data.storageCharge.ssc_item_type);
					$("#CCF").val(data.storageCharge.ssc_sc_type);
					$("#ssc_fixedcost").val(data.storageCharge.ssc_fixedcost);
					alert(data.storageCharge.ssc_square_hold_the_number);
					$("#ssc_square_hold_the_number").val(data.storageCharge.ssc_square_hold_the_number);
					$("#ssc_square_price").val(data.storageCharge.ssc_square_price);
					$("#ssc_area_price").val(data.storageCharge.ssc_area_price);
					$("#ssc_volume_price").val(data.storageCharge.ssc_area_price);
					$("#ssc_volume_calculation_price").val(data.storageCharge.ssc_volume_calculation_price);
					$("#ssc_number_price").val(data.storageCharge.ssc_number_price);
					$("#ssc_single_tray_number").val(data.storageCharge.ssc_single_tray_number);
					$("#ssc_singlt_tray_price").val(data.storageCharge.ssc_singlt_tray_price);
					showCCF();
				}
			}
		});
	}
	function getWAS(){
		//合同ID
		var ssc_cb_id = $("#ssc_cb_id").val();
		//仓库ID
		var ssc_whs_id = $("#ssc_whs_id").val();
		//区域ID
		var ssc_area_id = $("#ssc_area_id").val();
		//商品类型ID
		var ssc_item_type = $("#ssc_item_type").val();
		//仓储费结算类型
		var CCF = $("#CCF").val();
		getMJJS_CGBF_tab(ssc_cb_id);
		getMJJS_ALL_tab(ssc_cb_id);
		showTjjtCGBF(ssc_cb_id);
		showTJJTALL(ssc_cb_id);
		showSpjtCGBF(ssc_cb_id);
		showSpjtALL(ssc_cb_id);
		showTPJS_CGBF(ssc_cb_id);
		showTPJS_ALL(ssc_cb_id);
		showTPFT_CGBF(ssc_cb_id);
		showTPFT_ALL(ssc_cb_id);
	}
	//主列表刷新加载
	function getCCFMainTB(id){
		var url=root+"/control/sospController/getCCFMainTB.do?ssc_cb_id="+id;
		var htm_td="";
	   	$.ajax({
			type : "POST",
			url: url,  
			data:"",
			dataType: "json",  
			success : function(data) {
				if(data.status=='y'){
					var text="<table class='with-border' border='1'><tr class='title'><td>序号</td><td>存储时间</td><td>仓库</td><td>区域</td><td>商品类型</td><td>仓储费</td><td>操作</td></tr>";
					for(i=0;i<data.list.length;i++){
						htm_td=htm_td+"<tr><td>"+(i+1)+"</td><td>"+data.list[i].ssc_starttime+" - "+data.list[i].ssc_endtime+"</td><td>"+data.list[i].ssc_whs_name+"</td><td>"+data.list[i].ssc_area_name+"</td><td>"+data.list[i].ssc_item_type_name+"</td>"
						if(data.list[i].ssc_sc_type==0){
							htm_td=htm_td+"<td><a href='javascript:void(0)' onclick='getCCF("+data.list[i].ssc_id+");');>固定费用</a></td>";
						}else if (data.list[i].ssc_sc_type==1) {
							htm_td=htm_td+"<td><a href='javascript:void(0)' onclick='getCCF("+data.list[i].ssc_id+");'>按面积结算</a></td>";
						}else if (data.list[i].ssc_sc_type==2) {
							htm_td=htm_td+"<td><a href='javascript:void(0)' onclick='getCCF("+data.list[i].ssc_id+");'>按体积结算</a></td>";
						}else if (data.list[i].ssc_sc_type==3) {
							htm_td=htm_td+"<td><a href='javascript:void(0)' onclick='getCCF("+data.list[i].ssc_id+");'>按商品的体积推算</a></td>";
						}else if (data.list[i].ssc_sc_type==4) {
							htm_td=htm_td=htm_td+"<td><a href='javascript:void(0)' onclick='getCCF("+data.list[i].ssc_id+");'>按件数结算</a></td>";
						}else if (data.list[i].ssc_sc_type==5) {
							htm_td=htm_td=htm_td+"<td><a href='javascript:void(0)' onclick='getCCF("+data.list[i].ssc_id+");'>按件数反推所占面积</a></td>";
						}else if (data.list[i].ssc_sc_type==6) {
							htm_td=htm_td=htm_td+"<td><a href='javascript:void(0)' onclick='getCCF("+data.list[i].ssc_id+");'>按托盘结算</a></td>";
						}else if (data.list[i].ssc_sc_type==7) {
							htm_td=htm_td=htm_td+"<td><a href='javascript:void(0)' onclick='getCCF("+data.list[i].ssc_id+");'>按单个托盘的存放数量推算</a></td>";
						}
						htm_td=htm_td+"<td><a onclick='deleteMain("+data.list[i].ssc_id+")'>删除</a></td></tr>";
					}
					$("CCF_MAIN").html(text+htm_td+"</table>");
				}else{
					var text="<table class='with-border' border='1'><tr class='title'><td>存储时间</td><td>仓库</td><td>区域</td><td>商品类型</td><td>仓储费</td><td>操作</td></tr>";
					$("CCF_MAIN").html(text+"</table>");
				}
			}
		});
	};
	//主列表删除
	function deleteMain(id){
   		$.ajax({
			type : "POST",
			url: root+"/control/sospController/deleteCCFMain.do?ssc_id="+id,  
			data:null,
			dataType: "json",  
			success : function(data) {
				if(data.status=='y'){
					alert(data.info);
					getCCFMainTB($("#ssc_cb_id").val());
					getMJJS_CGBF_tab($("#ssc_cb_id").val());
					getMJJS_ALL_tab($("#ssc_cb_id").val());
					showTjjtCGBF($("#ssc_cb_id").val());
					showTJJTALL($("#ssc_cb_id").val());
					showSpjtCGBF($("#ssc_cb_id").val());
					showSpjtALL($("#ssc_cb_id").val());
					showTPJS_CGBF($("#ssc_cb_id").val());
					showTPJS_ALL($("#ssc_cb_id").val());
					showTPFT_CGBF($("#ssc_cb_id").val());
					showTPFT_ALL($("#ssc_cb_id").val());
				}else{
					alert(data.info);
				}
			}
		});
	};
	//仓储费－[计算公式]展示
	function showCCF() {
		var ssc_cb_id = $("#ssc_cb_id").val();
		//仓库ID
		var ssc_whs_id = $("#ssc_whs_id").val();
		//区域ID
		var ssc_area_id = $("#ssc_area_id").val();
		//商品类型ID
		var ssc_item_type = $("#ssc_item_type").val();
		//仓储费结算类型
		var divId = $("#CCF").val();
		if(ssc_cb_id==""||ssc_whs_id=="请选择"||ssc_area_id=="请选择"||ssc_item_type=="请选择"){
			$("#CCF").val("-1"); 
		}else{
			getWAS();
			var divObj = $("div[id^='CCF']");
			for (i = 0; i < divObj.length; i++) {
				if (i == divId) {
					$("#CCF" + i).css("display", "block");
				} else {
					$("#CCF" + i).css("display", "none");
				}
			}
		}
		
	};
	function cl(){
		$("#CCF").val("-1"); 
		showCCF();
	}
	//商品实际占用面积[阶梯]展示
	function showMJ() {
		var divId = $("#MJ").val();
		var divObj2 = $("div[id^='MJ']");
		for (i = 1; i <= divObj2.length; i++) {
			if (i == divId) {
				$("#MJ" + i).css("display", "block");
			} else {
				$("#MJ" + i).css("display", "none");
			}
		}
	};
	//按体积结算[阶梯]展示
	function showTJ() {
		var divId = $("#TJ").val();
		var divObj2 = $("div[id^='TJ']");
		for (i = 1; i <= divObj2.length; i++) {
			if (i == divId) {
				$("#TJ" + i).css("display", "block");
			} else {
				$("#TJ" + i).css("display", "none");
			}
		}
	};
	//仓储费按商品的体积推算[阶梯]展示
	function showSPTJ() {
		var divId = $("#SPTJ").val();
		var divObj2 = $("div[id^='SPTJ']");
		for (i = 1; i <= divObj2.length; i++) {
			if (i == divId) {
				$("#SPTJ" + i).css("display", "block");
			} else {
				$("#SPTJ" + i).css("display", "none");
			}
		}
	};
	//仓储费按商品的体积推算[阶梯]展示
	function showTP() {
		var divId = $("#TP").val();
		var divObj2 = $("div[id^='TP']");
		for (i = 1; i <= divObj2.length; i++) {
			if (i == divId) {
				$("#TP" + i).css("display", "block");
			} else {
				$("#TP" + i).css("display", "none");
			}
		}
	};
	//仓储费按商品的体积推算[阶梯]展示
	function showTPFT() {
		var divId = $("#TPFT").val();
		var divObj2 = $("div[id^='TPFT']");
		for (i = 1; i <= divObj2.length; i++) {
			if (i == divId) {
				$("#TPFT" + i).css("display", "block");
			} else {
				$("#TPFT" + i).css("display", "none");
			}
		}
	};
	//根据仓库ID加载区域
	function showArea(){
		$("#ssc_area_id option").remove();
		var whsid = $("#ssc_whs_id").val();
		$.ajax({
            url: "${root}control/sospController/getArea.do?id="+whsid,
            type: "POST",
            data: null,
            dataType: "json",
            success: function(data){
            	 $.each(data.whList,function(n,value) {
            		 $("#ssc_area_id").append("<option value='"+value.id+"'>"+value.area_name+"</option>");
				});  
         		cl();
            }
        });
	}
	//根据Name删除Select框里的option
	function DeleteOptions(selectName){  
        var obj = document.getElementsByTagName(selectName)[0];  
        var selectOptions = obj.options;  
        var optionLength = selectOptions.length;  
        for(var i=0;i <optionLength;i++){  
            obj.removeChild(selectOptions[0]);  
        }  
    };
    function deletea(id){
    	$.ajax({
    		type : "POST",
    		url: root+"/control/sospController/deletea.do?id="+id,  
    		data:null,
    		dataType: "json",  
    		success : function(data) {
    			if(data.status=='y'){
    				alert(data.info);
    				getMJJS_CGBF_tab($("#ssc_cb_id").val());
    			}else{
    				alert(data.info);
    			}
    		}
    	});
    };
    function deleteb(id){
    	$.ajax({
    		type : "POST",
    		url: root+"/control/sospController/deleteb.do?id="+id,  
    		data:null,
    		dataType: "json",  
    		success : function(data) {
    			if(data.status=='y'){
    				alert(data.info);
    				getMJJS_ALL_tab($("#ssc_cb_id").val());
    			}else{
    				alert(data.info);
    			}
    		}
    	});
    };
    function deletec(id){
    	$.ajax({
    		type : "POST",
    		url: root+"/control/sospController/deletec.do?id="+id,  
    		data:null,
    		dataType: "json",  
    		success : function(data) {
    			if(data.status=='y'){
    				alert(data.info);
    				showTjjtCGBF($("#ssc_cb_id").val());
    			}else{
    				alert(data.info);
    			}
    		}
    	});
    };
    function deleted(id){
    	$.ajax({
    		type : "POST",
    		url: root+"/control/sospController/deleted.do?id="+id,  
    		data:null,
    		dataType: "json",  
    		success : function(data) {
    			if(data.status=='y'){
    				alert(data.info);
    				showTJJTALL($("#ssc_cb_id").val());
    			}else{
    				alert(data.info);
    			}
    		}
    	});
    };
    function deletee(id){
    	$.ajax({
    		type : "POST",
    		url: root+"/control/sospController/deletee.do?id="+id,  
    		data:null,
    		dataType: "json",  
    		success : function(data) {
    			if(data.status=='y'){
    				alert(data.info);
    				showSpjtCGBF($("#ssc_cb_id").val());
    			}else{
    				alert(data.info);
    			}
    		}
    	});
    };
    function deletef(id){
    	$.ajax({
    		type : "POST",
    		url: root+"/control/sospController/deletef.do?id="+id,  
    		data:null,
    		dataType: "json",  
    		success : function(data) {
    			if(data.status=='y'){
    				alert(data.info);
    				showSpjtALL($("#ssc_cb_id").val());
    			}else{
    				alert(data.info);
    			}
    		}
    	});
    };
    function deletett(id){
    	$.ajax({
    		type : "POST",
    		url: root+"/control/sospController/deletett.do?id="+id,  
    		data:null,
    		dataType: "json",  
    		success : function(data) {
    			if(data.status=='y'){
    				alert(data.info);
    				showTPJS_CGBF($("#ssc_cb_id").val());
    			}else{
    				alert(data.info);
    			}
    		}
    	});
    };
    function deletej(id){
    	$.ajax({
    		type : "POST",
    		url: root+"/control/sospController/deletej.do?id="+id,  
    		data:null,
    		dataType: "json",  
    		success : function(data) {
    			if(data.status=='y'){
    				alert(data.info);
    				showTPJS_ALL($("#ssc_cb_id").val());
    			}else{
    				alert(data.info);
    			}
    		}
    	});
    };
    function deleteh(id){
    	$.ajax({
    		type : "POST",
    		url: root+"/control/sospController/deleteh.do?id="+id,  
    		data:null,
    		dataType: "json",  
    		success : function(data) {
    			if(data.status=='y'){
    				alert(data.info);
    				showTPFT_CGBF($("#ssc_cb_id").val());
    			}else{
    				alert(data.info);
    			}
    		}
    	});
    };
    function deletei(id){
    	$.ajax({
    		type : "POST",
    		url: root+"/control/sospController/deletei.do?id="+id,  
    		data:null,
    		dataType: "json",  
    		success : function(data) {
    			if(data.status=='y'){
    				alert(data.info);
    				showTPFT_ALL($("#ssc_cb_id").val());
    			}else{
    				alert(data.info);
    			}
    		}
    	});
    };
	
</script>