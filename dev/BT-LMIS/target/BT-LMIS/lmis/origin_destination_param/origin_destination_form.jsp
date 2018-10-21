<%@ page language= "java" import= "java.util.*" pageEncoding= "utf-8" %>
<!DOCTYPE html>
<html>
	<head lang= "en" >
		<meta charset= "UTF-8" >
		<%@ include file= "../../lmis/yuriy.jsp" %>
		<title>LMIS</title>
		<meta name= "description" content= "overview &amp; stats" />
		<meta name= "viewport" content= "width=device-width, initial-scale=1.0" />
		<link type="text/css" href="<%=basePath %>validator/css/style.css" rel="stylesheet" />
		<link type="text/css" href="<%=basePath %>validator/css/demo.css" rel="stylesheet" />
		<link type="text/css" href="<%=basePath %>/css/table.css" rel="stylesheet" />
		<style type= "text/css" >
			.text-style{
				font-size: 17px;
		 		color: blue;
		 		
		 	}
		 	.iput_style{
		 		width: 25%;
		 		height: 28px;
		 		
		 	}
		</style>
		
		<script type="text/javascript">
			if("ontouchend" in document) document.write("<script src='<%=basePath %>assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>
		<script type="text/javascript">
			window.jQuery || document.write("<script src='<%=basePath %>assets/js/jquery-2.0.3.min.js'>"+"<"+"/script>");
		</script>
		<script type="text/javascript" src="<%=basePath %>js/common.js"></script>
		<script type="text/javascript" src="<%=basePath %>validator/js/Validform_v5.3.2_min.js"></script>
		<script type="text/javascript" src="<%=basePath %>jquery/jquery-2.0.3.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>lmis/trans_moudle/js/trans.js"></script>	
		<script type="text/javascript" src="<%=basePath %>lmis/origin_destination_param/js/origin_destination_form.js"></script>	
		<script type="text/javascript">
			$(function(){
				//首重续重
				if($("#szxz_switch").val()=='1'){
					$("#if_szxz").attr("checked","checked");
					changeShow('sx_div');
				}
			
				//零担
				if($("#ld_switch").val()=='1'){
					$("#if_ld").attr("checked","checked");
					changeShow('ld_div');
				}
			
				//商品类型
				if($("#it_switch").val()=='1'){
					$("#if_gt").attr("checked","checked");
					changeShow('gt_div');
				}
			
				//整车
				if($("#zc_switch").val()=='1'){
					$("#if_zc").attr("checked","checked");
					changeShow('zc_div');
				}
				init_table();
				$(".registerform").Validform({
					tiptype:2
					
	 			});
				
			})
		</script>
		<script type="text/javascript">
			function init_table() {
    			//续重
				var sx_id=$("#sx_tab_id").val();
				//公斤单价
				var gj_id=$("#gj_tab_id").val();
				//立方单价
				var lf_id=$("#lf_tab_id").val();
				//商品类型
				var gt_id=$("#gt_tab_id").val();
			    //中转费
			    var zf_id=$("#zf_tab_id").val();
			    //整车
			    var zc_id=$("#zc_tab_id").val();
			    get_tab(sx_id,'sx_tab',1);
			    get_tab(gj_id,'gj_tab',1);
			    get_tab(lf_id,'lf_tab',1);
			    get_tab(gt_id,'gt_tab',2);
			    get_tab(zf_id,'zf_tab',2);
			    get_tab(zc_id,'zc_tab',3);
				    
			}
			function get_tab(TabId,dtx, type) {
				var url= root+"/control/addressController/getTab.do?table_id="+ TabId+ "&tabType="+ type;
				var htm_td= "";
		   		$.ajax({
		   			type: "POST",
		   			url: url,  
					data: "",
					dataType: "json",  
					success: function(jsonStr) {
						if(type == 1){
							var text= "<table class= 'with-border' border= '1' >"
								+ "<tr class= 'title' >"
								+ "<td>序号</td><td>区间</td><td>区间单位</td>"
								+ "<td>单价</td><td>价格单位</td><td>操作</td></tr>";
							for(i= 0;i< jsonStr.length; i++) {
								htm_td= htm_td+ "<tr><td>"+ (i+ 1)+ "</td><td>"+ jsonStr[i].internal+ "</td><td>"+ jsonStr[i].internal_unit+ "</td><td>"+ jsonStr[i].price+ "</td><td>"+ jsonStr[i].price_unit+ "</td><td><a href='javaScript:;' onclick='update_td(\""+ jsonStr[i].id+ "\",\""+ jsonStr[i].mark_a+ "\",\""+ jsonStr[i].args1+ "\",\""+ jsonStr[i].mark_b+ "\",\""+ jsonStr[i].args2+ "\",\""+ dtx+ "\",\""+ jsonStr[i].price+ "\")'>编辑</a>/<a href='javaScript:;' onclick='del_tab_add(\""+ jsonStr[i].id+ "\",\""+ type+ "\",\""+ dtx+ "\",\""+ TabId+ "\")'>删除</a></td></tr>";
								
							}
							
						}
						if(type == 2) {
							var text= "<table class= 'with-border' border= '1' >"
						       	+ "<tr class= 'title' >"
						       	+ "<td>序号</td><td>商品类型</td><td>价格</td>"
						       	+ "<td>价格单位</td><td>操作</td></tr>";
							for(i= 0; i< jsonStr.length;i++) {
								htm_td= htm_td+ "<tr><td>"+ (i+ 1)+ "</td><td>"+ jsonStr[i].item_type+ "</td><td>"+ jsonStr[i].price+ "</td><td>"+ jsonStr[i].unit+ "</td><td><a href='javaScript:;' onclick='update_gtTab(\""+ jsonStr[i].id+ "\",\""+ jsonStr[i].item_type+ "\",\""+ jsonStr[i].price+ "\",\""+ dtx+ "\")'>编辑</a>/<a href='javaScript:;' onclick='del_tab(\""+ jsonStr[i].id+ "\",\""+ type+ "\",\""+ dtx+ "\",\""+ TabId+ "\")'>删除</a></td></tr>";
								
							}
							
						}
						if(type == 3) {
							var text= "<table class= 'with-border' border= '1' >"
						       	+ "<tr class= 'title' >"
						       	+ "<td>序号</td><td>车型代码</td><td>车型名称</td>"
						       	+ "<td>容量</td><td>容量单位</td><td>价格</td><td>价格单位</td><td>操作</td></tr>";
							for(i= 0;i< jsonStr.length; i++) {
								htm_td= htm_td+ "<tr><td>"+ (i+ 1)+ "</td><td>"+ jsonStr[i].bus_code+ "</td><td>"+ jsonStr[i].bus_name+ "</td><td>"+ jsonStr[i].bus_volumn+ "</td><td>"+ jsonStr[i].busvolumn_unit+ "</td><td>"+ jsonStr[i].price+ "</td><td>"+ jsonStr[i].unit+ "</td><td><a href='javaScript:;' onclick='update_BusTab(\""+ jsonStr[i].id+ "\",\""+ jsonStr[i].bus_code+"\",\""+ jsonStr[i].bus_name+ "\",\""+ jsonStr[i].bus_volumn+"\",\""+ jsonStr[i].price+"\",\""+ dtx+ "\")'>编辑</a>/<a href='javaScript:;' onclick='del_tab_add(\""+ jsonStr[i].id+ "\",\""+ type+ "\",\""+ dtx+ "\",\""+ TabId+ "\")'>删除</a></td></tr>";
								
							}
							
						}
						$(dtx).html(text+htm_td+ "</table>");
					
					}
			
				});
		   		
			}
		</script>
	</head>
	<body>
		<div class="page-header">
			<h1>地址信息编辑</h1>
		</div>
		<div class="clearfix form-actions" style="margin-bottom: 10px;">
			<div style="width: 100%;" align="center">
				<div class="moudle_dashed_border_show_90">
					<table style="width: 80%;" class="form_table">
						<tr>
							<td class="right" width="33%"><label class="no-padding-right blue" for="form-field-1">合同&nbsp;: </label></td>
							<td class="left" width="33%"><select style="width: 100%;" disabled="disabled"><option>${mian_data.contract_no}</option></select></td>
							<td class="left" width="33%"><div class="Validform_checktip"></div></td>
						</tr>				
						<tr>
							<td class="right" width="33%"><label class="no-padding-right blue" for="form-field-1">供应商&nbsp;: </label></td>
							<td class="left" width="33%"><input id="name" disabled="disabled" name="name" type="text" id="form-field-1" placeholder="" value="${mian_data.carrier_name}" datatype="s6-18" errormsg="信息至少6个字符,最多18个字符！" /></td>
							<td class="left" width="33%"><div class="Validform_checktip"></div></td>
						</tr>
						<tr>
							<td class="right"><label class="no-padding-right blue"
								for="form-field-1">产品类型&nbsp;: </label></td>
							<td nowrap="nowrap" class="left"><input id="name" name="name" disabled="disabled"
								type="text" id="form-field-1" placeholder="" value="${mian_data.itemtype_name }"
								datatype="s6-18" errormsg="信息至少6个字符,最多18个字符！" /></td>
							<td nowrap="nowrap" class="left"></td>
						</tr>
	
						<tr>
							<td nowrap="nowrap" class="right"><label class="no-padding-right blue" for="form-field-1">始发地址&nbsp;:</label></td>
							<td  nowrap="nowrap">
								<select style="width: 30%;" disabled="disabled">
									<option>${mian_data.country_destination }</option>
								</select>
								 <select style="width: 30%;" disabled="disabled">
								   <option>${mian_data.province_origin }</option>
								</select>
								<select style="width: 30%;" disabled="disabled">
								   <option>${mian_data.city_origin }</option>
								</select>
								<select style="width: 30%;" disabled="disabled">
								   <option>${mian_data.state_origin }</option>
								</select>
							</td>
							<td nowrap="nowrap" class="left"></td>
						</tr>
						<tr>
							<td nowrap="nowrap" class="right"><label class="no-padding-right blue" for="form-field-1"> 目的地址&nbsp;: </label></td>
							<td nowrap="nowrap" class="left">
								<select style="width: 30%;" disabled="disabled">
								   <option>${mian_data.country_destination }</option>
								</select>
								<select style="width: 30%;" disabled="disabled">
								   <option>${mian_data.province_destination }</option>
								</select>
								<select style="width: 30%;" disabled="disabled">
								   <option>${mian_data.city_destination }</option>
								</select>		
								<select style="width: 30%;" disabled="disabled">
								   <option>${mian_data.district_destination }</option>
								</select>														
							</td>
							<td nowrap="nowrap" class="left"></td>
						</tr>
						<tr>
							<td nowrap="nowrap" class="right"><label
								class="no-padding-right blue" for="form-field-1">
									计费公式&nbsp;:</label></td>
							<td nowrap="nowrap" class="left">
								<select class="select" disabled="disabled">
								   <option>${mian_data.formula_name}</option>
								</select>
							</td>
							<td nowrap="nowrap" class="left"></td>
						</tr>
					</table>
				</div>
				<div class="form-group">
					<span>首重续重:</span>
			     	<input type="hidden" id="szxz_switch" value="${mian_data.szxz_switch}"/>
					<input id="if_szxz" type="checkbox" class="ace ace-switch ace-switch-5" onchange="changeShow_add('sx_div')" />
			    	<span class="lbl"></span>
				</div>
				<div class="moudle_dashed_border_width_90" id="sx_div">		
				    <div class="div_margin"><span class="text-style"> &nbsp; &nbsp; &nbsp;  &nbsp;折&nbsp;扣：</span><input value="${mian_data.szxz_discount}" id="xz_discount" class="iput_style"/>&nbsp;<select style=" width: 7%; height: 28px;"><option>%</option></select></div>	   
				    <div class="div_margin"><span class="text-style"> &nbsp; &nbsp; &nbsp;  &nbsp;首&nbsp;重：</span><input value="${mian_data.szxz_sz}"  id="xz_sh_weight" class="iput_style"/>&nbsp;<select style=" width: 7%; height: 28px;"><option>KG</option></select></div>
				    <div class="div_margin"><span class="text-style">首重价格：</span><input value="${mian_data.szxz_price}" class="iput_style" id="sz_price"/>&nbsp;<select style=" width: 7%; height: 28px;"><option>元</option></select></div>
			        <div class="div_margin"><span class="text-style">&nbsp;计抛基数：</span><input  value="${mian_data.szxz_jpnum}"  class="iput_style" id="jp_js"/>&nbsp;<select style=" width: 7%; height: 28px;"><option>KG</option></select></div>
			        <div class="div_margin"><span class="text-style">&nbsp;计费重量：</span><input  value="${mian_data.jf_weight}"  class="iput_style" id="jf_wight"/>&nbsp;<select style=" width: 7%; height: 28px;"><option>KG</option></select></div>
					<div class="div_margin">
						<a data-toggle="tab" href="#tab_add">
							<button class="btn btn-xs btn-yellow" id="save_catDiv3" onclick="save_xz_main(${mian_data.id},'xz')">
								<i class="icon-hdd"></i><span id="but_text_3">保存</span>
							</button>
					  	</a>
					</div>
	    			<span>续重单价：</span>
	    			<div class="moudle_dashed_border_show">
	    				<input value="${mian_data.szxz_id}" type="hidden" id="sx_tab_id"/>
	    				<sx_tab></sx_tab>
<!-- 						<div class="add_button"> -->
<!-- 							<a data-toggle="tab" href="#tab_add"> -->
<!-- 								<button class="btn btn-xs btn-yellow" onclick="changeShow_add('xz_ad_td')"> -->
<!-- 								<i class="icon-hdd"></i>新增 -->
<!-- 							</button> -->
<!-- 							</a> -->
<!-- 						</div>		     -->
						<div style= "display: none" id= "xz_ad_td" >
							<div class= "div_margin" >
								<span>---阶梯设置---</span>
							</div>
							<div class= "div_margin" >
								条件 
								<select id="xz_mark1">
									<option value=">">&gt;</option>
									<option value=">=" >&gt;=</option>
								</select>
								<span id="">续重</span>
								<input type="text" id="xz_agrs1"/>
						       	<select><option>KG</option></select>
							</div>
							<div class="div_margin" >
								组合方式:
								<select id="xz_mark2">
									<!-- <option value="0">或者</option> -->
									<option value="1">并且</option>
							 	</select>
							</div>
							<div class="div_margin" >
								条件 
								<select id="xz_mark3">
									<option value="<">&lt;</option>
									<option value="<=">&lt;=</option>
								</select>
								<span id="">续重</span>
								<input type="text" id="xz_agrs2"/>
								<select><option>KG</option></select>
							</div>
							<div class="div_margin">
								 收费:<input type="text" id="xz_price" />&nbsp;<select><option>元</option></select>
							</div>
							<div class="div_margin">
							    <a data-toggle="tab" href="#tab_add">
									<button class="btn btn-xs btn-yellow" id="save_catDiv3" onclick="save_xz()">
										<i class="icon-hdd"></i><span id="but_text_3">保存</span>
									</button>
									<button class="btn btn-xs btn-yellow" id="save_catDiv3" onclick="changeShow('xz_ad_td')">
								 		<i class="icon-hdd"></i><span id="but_text_3">取消</span>
								  	</button>												  
							   	</a>
				       	 	</div>		
	                	</div>	 
                  	</div>
	          	</div>
				<div class="form-group">
					<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;零担:</span>
			    	<input type="hidden" id="ld_switch" value="${mian_data.ld_switch}"/>
					<input id="if_ld" type="checkbox" class="ace ace-switch ace-switch-5" onchange="changeShow_add('ld_div')" />
					<span class="lbl"></span>
				</div>
				<div class="moudle_dashed_border_width_90" id="ld_div">
					<div class="div_margin"><span class="text-style">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;最低一票：</span><input value="${mian_data.low_price}" id="ld_discount_price" class="iput_style"/></div>
					<div class="div_margin"><span class="text-style">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;最低起运量（KG）：</span><input value="${mian_data.low_weight}" id="ld_discount_price" class="iput_style"/></div>
					<div class="div_margin"><span class="text-style">最低起运量（立方米）：</span><input value="${mian_data.low_cubic}" id="ld_discount_price" class="iput_style"/></div>
					<div class="div_margin"><span class="text-style">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;折&nbsp;扣：</span><input value="${mian_data.ld_discount}" id="ld_discount_price" class="iput_style"/>&nbsp;<select style=" width: 7%; height: 28px;"><option>%</option></select></div>
					<div class="div_margin">
			 			<a data-toggle="tab" href="#tab_add">
							<button class="btn btn-xs btn-yellow" id="save_catDiv3" onclick="save_xz_main(${mian_data.id},'ld')">
								<i class="icon-hdd"></i><span id="but_text_3">保存</span>
							</button>
			  			</a>
			  		</div>		    
			    	<span>公斤单价：</span>
		    		<div class="moudle_dashed_border_show">
		    			<input id="gj_tab_id" type="hidden" value="${mian_data.weightprice_id}" />
		    			<gj_tab></gj_tab>
						<!-- <div class="add_button"> -->
						<!-- <a data-toggle="tab" href="#tab_add"> -->
						<!-- <button class="btn btn-xs btn-yellow" onclick="changeShow_add('ld_ad_td')"> -->
						<!-- <i class="icon-hdd"></i>新增 -->
						<!-- </button> -->
						<!-- </a> -->
						<!-- </div> -->
						<div style="display: none" id="ld_ad_td">
							<div class="div_margin" >
								<span>---阶梯设置---</span>
							</div>
							<div class="div_margin">
								条件
								<select id="gj_mark1">
									<option value=">">></option>
									<option value=">=" >>=</option>
								</select>
								<span id="">重量</span>
								<input type="text" id="gj_agrs1"/>
						       	<select><option>KG</option></select>
							</div>
							<div class="div_margin" >
								组合方式:
								<select id="gj_mark2">
					     			<option value="1">并且</option>
					 		   </select>
							</div>
							<div class="div_margin" >
								条件 
								<select id="gj_mark3">
									<option value="<"><</option>
									<option value="<="><=</option>
								</select>
								<span id="">重量</span>
								<input type="text" id="gj_agrs2"/>
								<select><option>KG</option></select>
							</div>
							<div class="div_margin">
								 公斤单价:<input type="text" id="gj_price" />&nbsp;<select><option>元</option></select>
							</div>
						 	<div class="div_margin">
						    	<a data-toggle="tab" href="#tab_add">
								  	<button class="btn btn-xs btn-yellow" id="save_catDiv3" onclick="save_gj()">
								 		<i class="icon-hdd"></i><span id="but_text_3">保存</span>
								  	</button>
								  	<button class="btn btn-xs btn-yellow" id="save_catDiv3" onclick="changeShow('ld_ad_td')">
								 		<i class="icon-hdd"></i><span id="but_text_3">取消</span>
								  	</button>										  
						   		</a>
					        </div>			
	                 	</div>	 
					</div>
					<span>立方单价:</span>
		    		<div class= "moudle_dashed_border_show" >
		    			<input id="lf_tab_id" type="hidden" value="${mian_data.volumprice_id}"/>
		    			<lf_tab></lf_tab>
						<!-- <div class="add_button"> -->
						<!-- <a data-toggle="tab" href="#tab_add"> -->
						<!-- <button class="btn btn-xs btn-yellow" onclick="changeShow_add('lf_ad_td')"> -->
						<!-- <i class="icon-hdd"></i>新增 -->
						<!-- </button> -->
						<!-- </a> -->
						<!-- </div>	-->
						<div style= "display: none" id= "lf_ad_td" >
							<div class="div_margin" >
								<span>---阶梯设置---</span>
							</div>
							<div class="div_margin">
								条件
						 		<select id="lf_mark1">
									<option value=">">></option>
									<option value=">=" >>=</option>
								</select>
								<span id="">零担重量</span>
								<input type="text" id="lf_agrs1"/>
						       	<select><option>KG</option></select>
							</div>
							<div class="div_margin" >
								组合方式:
								<select id="lf_mark2">
									<!-- <option value="0">或者</option> -->
							     	<option value="1">并且</option>
								</select>
							</div>
							<div class="div_margin" >
								条件 
								<select id="lf_mark3">
									<option value="<"><</option>
									<option value="<="><=</option>
								</select>
								<span id="">零担重量</span><input type="text" id="lf_agrs2"/>
								<select><option>KG</option></select>
							</div>
							<div class="div_margin">
								 收费比率:
								<input type="text" id="lf_price" />&nbsp;<select><option>元</option></select>
							</div>
							<div class="div_margin">
						    	<a data-toggle="tab" href="#tab_add">
									<button class="btn btn-xs btn-yellow" id="save_catDiv3" onclick="save_lf()">
								 		<i class="icon-hdd"></i><span id="but_text_3">保存</span>
								  	</button>
								  	<button class="btn btn-xs btn-yellow" id="save_catDiv3" onclick="changeShow('lf_ad_td')">
								 		<i class="icon-hdd"></i><span id="but_text_3">取消</span>
								  	</button>												  
							   	</a>
					        </div>
						</div>	                                    
					</div>
				</div>
				<div class= "form-group">
					<span>商品类型:</span>
					    <input type="hidden" id="it_switch" value="${mian_data.it_switch}"/>
						<input id="if_gt" type="checkbox" class="ace ace-switch ace-switch-5" onchange="changeShow_add('gt_div')" />
					<span class="lbl"></span>
				</div>
				<div class="moudle_dashed_border_width_90" id="gt_div">		
	    			<div class= "div_margin" >
				    	<span class= "text-style">&nbsp;折&nbsp;扣：</span>
				    	<input value= "${mian_data.good_type_discount }" id= "gt_discount_price" class= "iput_style" />&nbsp;
				    	<select style= "width: 7%; height: 28px;" >
				    		<option>%</option>
				    	</select>
	    			</div>
        			<div class= "div_margin" >
			        	<span class= "text-style" >&nbsp;派送费：</span>
			        	<input value= "${mian_data.ps_price }" id="gt_ps_peice" class="iput_style"/>&nbsp;
			        	<select id= "ps_unit" style= "width: 7%; height: 28px;" >
			        		<option ${mian_data.ps_unit == "元/件"? "selected= 'selected'": "" } >元/件</option>
			        		<option ${mian_data.ps_unit == "元/m³"? "selected= 'selected'": "" } >元/m³</option>
			        	</select>
        			</div>	 
					<div class= "div_margin" >
						<a data-toggle= "tab" href= "#tab_add" >
							<button class= "btn btn-xs btn-yellow" id= "save_catDiv3" onclick= "save_xz_main(${mian_data.id},'gt')" >
								<i class= "icon-hdd" ></i><span id= "but_text_3" >保存</span>
							</button>
					  	</a>
					</div>	   
	    			<div class= "moudle_dashed_border_show" >
	    				<span>运费：</span>
	     				<input id="gt_tab_id" type="hidden" value="${mian_data.it_id}"/>
         				<gt_tab></gt_tab>
						<!-- <div class="add_button"> -->
						<!-- <a data-toggle="tab" href="#tab_add"> -->
						<!-- <button class="btn btn-xs btn-yellow" onclick="changeShow_add('tp_ad_td')"> -->
						<!-- <i class="icon-hdd"></i>新增 -->
						<!-- </button> -->
						<!-- </a> -->
						<!-- </div>	-->
						<div class="div_margin" style="display:none" id="tp_ad_td">
							<span>商品类型：<input id="yf_gt_type" type="text"/></span>
						 	<span>价格：<input id="yf_gt_price" type="text"/></span>
						 	<select><option>元</option></select>		
							<div class="div_margin">
						    	<a data-toggle="tab" href="#tab_add">
							  		<button class="btn btn-xs btn-yellow" id="save_catDiv3" onclick="save_gt()">
								 		<i class="icon-hdd"></i><span id="but_text_3">保存</span>
								  	</button>
								  	<button class="btn btn-xs btn-yellow" id="save_catDiv3" onclick="changeShow('tp_ad_td')">
								 		<i class="icon-hdd"></i><span id="but_text_3">取消</span>
								  	</button>											  
							   	</a>
					        </div>	                        
                         </div>
					</div>
					<div class="form-group">
						<div class="moudle_dashed_border_show">
							<span>中转费：</span>
	     					<input id="zf_tab_id" type="hidden" value="${mian_data.zh_price_tab_id}"/>
							<zf_tab></zf_tab>			
							<!-- <div class="add_button"> -->
							<!-- <a data-toggle="tab" href="#tab_add"> -->
							<!-- <button class="btn btn-xs btn-yellow" onclick="changeShow_add('zf_ad_td')"> -->
							<!-- <i class="icon-hdd"></i>新增 -->
							<!-- </button> -->
							<!-- </a> -->
							<!-- </div>	-->
							<div class="div_margin" style="display:none" id="zf_ad_td">
								<span>商品类型：<input id="zf_gt_type" type="text"/></span>
								<span>价格：<input id="zf_gt_price" type="text"/></span><select><option>元</option></select>
	  							<div class="div_margin">
									<a data-toggle="tab" href="#tab_add">
										<button class="btn btn-xs btn-yellow" id="save_catDiv3" onclick="save_zf()">
									 		<i class="icon-hdd"></i><span id="but_text_3">保存</span>
									  	</button>
									  	<button class="btn btn-xs btn-yellow" id="save_catDiv3" onclick="changeShow('zf_ad_td')">
									 		<i class="icon-hdd"></i><span id="but_text_3">取消</span>
									  	</button>												  
								   	</a>
						        </div>
         					</div>	
      					</div>
	  				</div>		
		
					<!-- <div class="form-group"> -->
					<!-- <div class="form-group"> -->
					<%-- <span>派送费价格：<input  value="${mian_data.ps_price}" type="text"/></span>	<select><option>元/件</option></select>		 --%>
					<!-- </div> -->
					<!-- </div> -->
				</div>
				<div class="form-group">
					<div class="form-group">
						<span>整车:</span>
						<input type="hidden" id="zc_switch" value="${mian_data.zc_switch}"/>
					 	<input type="checkbox" id="if_zc" class="ace ace-switch ace-switch-5" onchange="changeShow_add('zc_div')"/> 
						<span class="lbl"></span>
					</div>
				</div>
				<div class="moudle_dashed_border_width_90" id="zc_div">		
    				<span>运费：</span>
   					<input id="zc_tab_id" type="hidden" value="${mian_data.bus_id}"/>
	    			<div class="moudle_dashed_border_show">
	    				<zc_tab></zc_tab>
						<!-- <div class="add_button"> -->
						<!-- <a data-toggle="tab" href="#tab_add"> -->
						<!-- <button class="btn btn-xs btn-yellow" onclick="changeShow_add('zc_ad_td')"> -->
						<!-- <i class="icon-hdd"></i>新增 -->
						<!-- </button> -->
						<!-- </a> -->
						<!-- </div> -->
						<div class="div_margin" style="display:none" id="zc_ad_td">
							<div class="div_margin">
								  <span>车型代码：<input id="car_code" type="text"/></span>
								  <span>价格：<input id="car_price" type="text"/></span>
								  <select><option>元</option></select>
							</div>
							<div class="div_margin">
                            	<span>车型名称：<input id="car_name" type="text"/></span>						 
							  	<span>容量：<input id="car_volum" type="text"/></span>
							  	<select><option>m³</option></select>	
				     		</div>
				     		<div class="div_margin">
						    	<a data-toggle="tab" href="#tab_add">
							  		<button class="btn btn-xs btn-yellow" id="save_catDiv3" onclick="save_zc()">
								 		<i class="icon-hdd"></i><span id="but_text_3">保存</span>
								  	</button>
								  	<button class="btn btn-xs btn-yellow" id="save_catDiv3" onclick="changeShow('zc_ad_td')">
									 	<i class="icon-hdd"></i><span id="but_text_3">取消</span>
								  	</button>											  
							   	</a>
				        	</div>
			        	</div>
		        	</div>
	        	</div>
        	</div>
       	</div>
       	<input id="xz_id_tab" value="" type="hidden"/>
     	<input id="gj_id_tab" value="" type="hidden"/>
     	<input id="lf_id_tab" value="" type="hidden"/>
     	<input id="gt_id_tab" value="" type="hidden"/>
     	<input id="zf_id_tab" value="" type="hidden"/>
      	<input id="zc_id_tab" value="" type="hidden"/>
      	<input id="mainId" value="${mian_data.id}" type="hidden"/>
	</body>
</html>
