var INCLUDE_SEL_ID = "";
var INCLUDE_SEL_VAL_CODE = "";
function initShopQuery(selId,valCode){
	INCLUDE_SEL_ID = selId;
	INCLUDE_SEL_VAL_CODE = valCode;
	if(valCode == 'innerShopCode'){
		INCLUDE_SEL_VAL_CODE = 'code'
	}
}
$j(document).ready(function(){
	//光标移动
 $j("#centerType").keydown(function(evt) {//成本中心类型转部门/店铺
	if (evt.keyCode === 13) {
		$j("#departmentshop").focus();
	}
});
 $j("#departmentshop").keydown(function(evt) {//部门/店铺转寄件人
		if (evt.keyCode === 13) {
			$j("#sender").focus();
		}
	});
 $j("#sender").keydown(function(evt) {//寄件人转寄件人手机号码
		if (evt.keyCode === 13) {
			$j("#senderTel").focus();
		}
	});
 $j("#senderTel").keydown(function(evt) {//寄件人手机号码转固定电话
		if (evt.keyCode === 13) {
			$j("#senderTel2").focus();
		}
	});
 $j("#price").keydown(function(evt) {//保价金额跳转到包裹数量
			if (evt.keyCode === 13) {
				$j("#packageNum").focus();
			}
});
 $j("#packageNum").keydown(function(evt) {//包裹数量转收货人
		if (evt.keyCode === 13) {
			$j("#receiver").focus();
		}
});
 $j("#receiver").keydown(function(evt) {//收货人转收件人联系方式
		if (evt.keyCode === 13) {
			$j("#rTel").focus();
		}
});
 $j("#rTel").keydown(function(evt) {//收件人联系方式转收件人省份
		if (evt.keyCode === 13) {
			$j("#rTel2").focus();
		}
});
 
 $j("#rTel2").keydown(function(evt) {//收件人联系方式转收件人省份
		if (evt.keyCode === 13) {
			$j("#rProvince").focus();
		}
});
 
 $j("#rProvince").keydown(function(evt) {//收件人省份转收件人市
		if (evt.keyCode === 13) {
			$j("#rCity").focus();
		}
});
 $j("#rCity").keydown(function(evt) {//收件人市转收件人区 rAddress
		if (evt.keyCode === 13) { 
			$j("#rArea").focus();
		}
});
 $j("#rArea").keydown(function(evt) {//收件人区转收件人地址  
		if (evt.keyCode === 13) { 
			$j("#rAddress").focus();
		}
});
 
 $j("#selectLpCode").keydown(function(evt) {//服务物流商转时效类型或保价金额
		if (evt.keyCode === 13) { 
			var type=$j('#timeType').css('display');
			if(type=='none'){
				$j("#price").focus();
			}else{
				$j("#timeType").focus();
			}
		}
});
 $j("#timeType").keydown(function(evt) {//时效类型转保价金额
		if (evt.keyCode === 13) { 
			$j("#price").focus();
		}
});
	
	$j("#timeType").hide();
 	$j("#timeType2").hide(); 
	$j("#businessType").focus();
	//仓库
	var rsHouse = loxia.syncXhrPost($j("body").attr("contextpath") + "/json/getWarehouse.do");
	$j("#ouId").val(rsHouse["name"]);
	$j("#sAddress").val(rsHouse["name"]);
	//业务类型
	var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/json/getChooseOptionByCode.do",{"categoryCode":"businessType"});
	$j("#businessType").append("<option value='0'>请选择</option>");
	for(var i=0;i<result.length;i++){
		$j("#businessType").append("<option value="+result[i].optionKey+">"+result[i].optionValue+"</option>");
	}
	$j("#btnSearchShop").click(function(){//查询店铺
		var type=document.getElementById("centerType").value; 
		if(type==''){
			 jumbo.showMsg("请中心类型");
		}else if(type=='1'){//部门
			$j("#departQueryDialog").show();
			$j("#departQueryDialog").dialog("open");
		}else{//店铺
			$j("#shopQueryDialog").show();
			$j("#shopQueryDialog").dialog("open");
		}
	});
	//点击下一步2
	$j("#next2").click(function(){
		 var staTypes= new Array();
		 var staIds= new Array();
		 var type=document.getElementById("businessType").value;
		 var staType=document.getElementsByName("staType");
		 var staId=document.getElementsByName("staId");
		 if(staType.length>0 && staId.length>0){
			for(var i=0;i<staType.length;i++){
				staTypes.push(staType[i].value);
				}
			for(var i=0;i<staId.length;i++){
				staIds.push(staId[i].value);
				}
		}
		 if(type==0){//0:请选择 1：转店，2：样品领用，3:补发，4:零担物流,5:公司件，6:其他
			 jumbo.showMsg("请选择业务类型");return;
				 }else if(type==1 || type==2){
						if(staTypes.length==0){ 
							 jumbo.showMsg("请添加作业指令信息");return;
						}else{
							checkSta(staTypes,staIds,staTypes.length,null);//验证作业单
							}
				 }else if(type==4 || type==6){
						checkSta(staTypes,staIds,staTypes.length,null);//验证作业单
				 }else if(type==3){
					 checkSta(staTypes,staIds,staTypes.length,'3');//验证作业单
				 }
	});
	
	//点击下一步3
	$j("#next3").click(function(){
		 var centerType=document.getElementById("centerType").value;
		 var costCenterDetail=document.getElementById("departmentshop").value;
		 var sender=document.getElementById("sender").value;
		 var senderTel=document.getElementById("senderTel").value;//手机
		 var senderTel2=document.getElementById("senderTel2").value;//电话

		 
		 
		 if(centerType==''){
			 jumbo.showMsg("请选择成本中心类型");return;
		 }
		 if(costCenterDetail==''){
			 jumbo.showMsg("请选择部门/店铺");return;	 
		 }
		 if(sender==''){
			 jumbo.showMsg("请填写寄件人");return;	 
		 }
		 if(senderTel=='' && senderTel2==''){
			 jumbo.showMsg("寄件人手机和固定电话必填一个");return;	 
		 }
		 var myreg = /^(1+\d{10})$/;//验证手机号
		 if(senderTel!=''){
				 if(myreg.test(senderTel)){
				 }else{
					 jumbo.showMsg("寄件人手机不合法");return;
				 }
		 }
		 document.getElementById("div3").style.display="none";
		 document.getElementById("div4").style.display="block";
		 var owner=document.getElementById("departmentshop").value;
		 //获取保价范围
		 var rs2= loxia.syncXhrPost($j("body").attr("contextpath") + "/json/getPriceRange.do",{"owner":owner});
		 if(rs2["msg"] == 'success'){
			 var priceRange=rs2["BiChannel"]["minInsurance"]+"-"+rs2["BiChannel"]["maxInsurance"];
			$j("#priceRange").val(priceRange);
		 }
		 $j("#selectLpCode").focus();
	});
	//weightOutbound 称重出库
	var index = "1";
	$j("#weightOutbound").click(function(){
		 var staIds= new Array();
		 var staId=document.getElementsByName("staId");
		 var businessType=document.getElementById("businessType").value;
		 var centerType=document.getElementById("centerType").value;
		 var costCenterDetail=document.getElementById("departmentshop").value;
		 var sender=document.getElementById("sender").value;
		 var senderTel=document.getElementById("senderTel").value;//ji手机
		 var senderTel2=document.getElementById("senderTel2").value;//ji电话
		 var sAddress=document.getElementById("sAddress").value;
		 var transportatorCode=document.getElementById("selectLpCode").value;
		 var timeType=document.getElementById("timeType").value;
		 var insuranceAmount=document.getElementById("price").value;
		 var packageNum=document.getElementById("packageNum").value;
		 var receiver=document.getElementById("receiver").value;
		 var yrTel=document.getElementById("yrTel").value;//原始手机
		 var rTel=document.getElementById("rTel").value;//shou手机
		 var rTel2=document.getElementById("rTel2").value;//shou电话
		 var rCountry=document.getElementById("rCountry").value;
		 var rProvince=document.getElementById("rProvince").value;
		 var rCity=document.getElementById("rCity").value;
		 var rArea=document.getElementById("rArea").value;
		 var rAddress=document.getElementById("rAddress").value;
		 var remark=document.getElementById("remark").value;//备注
		 var isLandTrans=document.getElementById("isLandTrans").checked;
		 if(yrTel==''){//如果原始手机不为空，赋值到rTEl，
		 }else{
			 rTel=yrTel;
		 }
		 
		 if(transportatorCode==''){
			 jumbo.showMsg("请选择物流服务商");return;
		 }
		 if(insuranceAmount!=''){
			 if(!/^([1-9]\d{0,}|0)(\.\d{1,2})?$/.test(insuranceAmount)){
				 jumbo.showMsg("保价金额,不是一个合法的数字或精度要求不符合要求");return;
			 }
		 }
		 if(packageNum==''){
			 jumbo.showMsg("请填写包裹数量");return;
		 }
		 if(packageNum==''){
			 jumbo.showMsg("请填写包裹数量");return;
		 }
		 /////////////////////////////////////////////////////////////
		 //判断交接上限
		 var rs = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/getNum.json");
			if(rs.maxNum==null){
			}else{
				var maxNum=rs.maxNum;
				var num=Number(rs.num)+Number(packageNum);
				if(parseInt(num)>parseInt(maxNum)){
					 jumbo.showMsg("当前未交接运单数到达上限");return;
				}
			}
		/////////////////////////////////////////////////////////////
		 if(!/^[0-9]*$/.test(packageNum)){
             jumbo.showMsg("包裹数量,不是一个合法的数字或精度要求不符合要求");return;
            }
		 if(receiver==''){
			 jumbo.showMsg("请填写收货人");return;
		 }
		 if(rTel=='' && rTel2==''){
			 jumbo.showMsg("收货人手机和电话必须填一个");return;
		 }
		// var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;//验证手机号
		 var myreg = /^(1+\d{10})$/;//验证手机号
//		 var myreg2 = /^((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;//验证固话
		 if(rTel !=''){
				 if(myreg.test(rTel)){
				 }else{
					 jumbo.showMsg("收货人手机不合法");return;
				 }
		 }
		 
		 if(rCity=='' ||rProvince=='' || rArea=='' || rAddress=='' ){
			 jumbo.showMsg("请填写收货地址");return;
		 }
		 
		 if(isLandTrans){
			 isLandTrans="1";
		 }else{
			 isLandTrans="0";
		 }
		 var priceRange=document.getElementById("priceRange").value;
		 var pRs= new Array(); //定义一数组 
		 if(insuranceAmount !=null){
			 if(priceRange !=''){
				 pRs=priceRange.split("-");
				 var min=pRs[0];
				 var max=pRs[1];
				 if(parseFloat(insuranceAmount)<parseFloat(min) || parseFloat(insuranceAmount)>parseFloat(max) ){
					 	$j("#price").val('');
						jumbo.showMsg("请注意保价范围");return;
			      }
			 	}
		 }
		 if(packageNum==null){
			 jumbo.showMsg("请填写包裹数量");return;
		 }
		 var transOrder = {};
		 transOrder["transOrder.businessType"]=businessType;
		 transOrder["transOrder.costCenterType"]=centerType;
		 transOrder["transOrder.costCenterDetail"]=costCenterDetail;
		 transOrder["transOrder.sender"]=sender;
		 transOrder["transOrder.senderTel"]=senderTel;
		 transOrder["transOrder.senderTel2"]=senderTel2;//ji电话
		 transOrder["transOrder.senderAddress"]=sAddress;
		 transOrder["transOrder.transportatorCode"]=transportatorCode;
		 transOrder["transOrder.timeType"]=timeType;
		 transOrder["transOrder.insuranceAmount"]=insuranceAmount;
		 transOrder["transOrder.packageNum"]=packageNum;
		 transOrder["transOrder.receiver"]=receiver;
		 transOrder["transOrder.receiverTel"]=rTel;
		 transOrder["transOrder.receiverTel2"]=rTel2;//shou电话
		 transOrder["transOrder.receiverCountry"]=rCountry;
		 transOrder["transOrder.receiverProvince"]=rProvince;
		 transOrder["transOrder.receiverCity"]=rCity;
		 transOrder["transOrder.receiverArea"]=rArea;
		 transOrder["transOrder.receiverAddress"]=rAddress;
		 transOrder["transOrder.remark"]=remark;//备注
		 transOrder["transOrder.isnotLandTrans"]=isLandTrans;
		 if(staId.length>0){
				for(var i=0;i<staId.length;i++){
					staIds.push($j.trim(staId[i].value));
					}
				transOrder["staIds"]=staIds.join(",");
			}
//		 alert();
//		 return;
		 if(index == "1"){//防止重复提交
			 index = "0";
					 var rs= loxia.syncXhrPost($j("body").attr("contextpath") + "/insertTransOrder.json",transOrder);
					 if(rs["msg"] == 'success'){
						 $j("#orderId").val(rs["order"]["id"]);
						 $j("#tNum").val(rs["order"]["packageNum"]);
						 $j("#nowNum").val("1");
						 $j("#yNum").val(rs["order"]["packageNum"]+"-1");
						 //判断是电子面单还是纸质面单
						 if(rs["trans"] == 'no'){
							  document.getElementById("button2").style.display="none";
//							  $j("#zhiSubmit").val("提交");
							  document.getElementById('zhiSubmit').innerHTML ='确认提交';
						 }else{
							 var tr = document.getElementById("delNo");
							 tr.parentNode.removeChild(tr);
						 }
						 $j("#shopQueryDialog2").show();
						 $j("#shopQueryDialog2").dialog("open");
					 }else{
						 jumbo.showMsg("保存订单失败");
					 }
		
		 }
	});
	
	
///////////////////////////////////////////////////////////////////
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/findPlatformList.json");
	//加载物流信息 一键交接
	$j("#selTrans2").append("<option></option>");
	for(var idx in result){
		$j("#selTrans2").append("<option value='"+result[idx].code+"'>"+result[idx].name+"</option>");
	}
    $j("#selTrans2").flexselect();
    $j("#selTrans2").val("");
    
    $j("#selTrans2").change(function(){
    	if ($j("#selTrans2").val() != ""){
    		var showShopList = $j("#showShopList").html();
    		var postshopInput = $j("#postshopInput").val();
    		var postshopInputName = $j("#postshopInputName").val();
    		var shopLikeQuerys = $j("#shopLikeQuery").val(); //模糊查询下拉框key
    		var shopLikeQuery = $j("#selTrans2_flexselect").val(); //模糊查询下拉框value
    		var shoplist = "", postshop="",shoplistName="";
    		if (showShopList.indexOf(shopLikeQuery) < 0){
    			shoplist = shopLikeQuery + " | ";
				postshop = shopLikeQuerys+ "|";
				shoplistName = shopLikeQuery+ "|";
    		}  //不包含
    		$j("#showShopList").html(showShopList + shoplist);
    		$j("#postshopInput").val(postshopInput+ postshop);
    		$j("#postshopInputName").val(postshopInputName + shoplistName);
    	}
    });
    
    //设置未交接运单数和交接上限
	var rs = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/getNum.json");
	if(rs.maxNum==null){
		document.getElementById("maxNum").innerText ='未设置';
	}else{
		document.getElementById("maxNum").innerText =rs.maxNum;
	}
	document.getElementById("num").innerText =rs.num;
	//一键交接
	$j("#oneHandover").click(function(){
	  var lpcodes=$j("#postshopInputName").val();
	  if(lpcodes==null || lpcodes==''){
			 jumbo.showMsg("请选择物流服务商");return;
	  }
	  var postData = {};
	  postData["lpcode"] =lpcodes;
	  var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/createOneHandOverList.json",postData);
		 if(rs["result"] == 'success'){
			 if(rs["brand"]=='0'){
				 jumbo.showMsg("无一键交接满足条件");
			 }else{
				  getNum();
			 }
		 }else{
			     jumbo.showMsg("一键交接失败");
		 }
	});
	//一键交接并打印
	$j("#oneHandoverPrint").click(function(){
		  var lpcodes=$j("#postshopInputName").val();
		  if(lpcodes==null || lpcodes==''){
				 jumbo.showMsg("请选择物流服务商");return;
		  }
		  var postData = {};
		  postData["lpcode"] =lpcodes;
		  var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/createOneHandOverList.json",postData);
			 if(rs["result"] == 'success'){
				 if(rs["brand"]=='0'){
					 jumbo.showMsg("无一键交接满足条件");
				 }else{
					  getNum();
					  var hoIds=rs["hoIds"];
					  var url = $j("body").attr("contextpath") + "/json/printhandoverlist2.json?hoIds=" + hoIds;
					  printBZ(loxia.encodeUrl(url),true);
				 }
			 }else{
				     jumbo.showMsg("一键交接失败");
			 }
		});
	//获取未交接运单数 公共方法
	function getNum(){
		var rs = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/getNum.json");
		if(rs.maxNum==null){
			document.getElementById("maxNum").innerText ='未设置';
		}else{
			document.getElementById("maxNum").innerText =rs.maxNum;
		}
		document.getElementById("num").innerText =rs.num;
		document.getElementById("showShopList").innerText ='';
		 $j("#oneHandDiv input").val("");
	}
	
	/////////////////////////////////////////////////////////////////
	
	function checkSta(staTypes,staIds,brand,type){//驗證作業單
		
		var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/json/checkSta.do?staTypes="+staTypes.join(",")+"&staIds="+staIds.join(",")+"&brand="+brand);
		if(rs["msg"] == 'errorNull'){
			jumbo.showMsg("请填写，不能有空");return;
		}
		if(rs["msg"] == 'errorDouble'){
			jumbo.showMsg("作业指令"+rs["staCode"]+"重复");return;
		}
		if(rs["msg"] == 'errorCode'){
			jumbo.showMsg("作业指令"+rs["staCode"]+"不存在");return;
		}
		if(rs["msg"] == 'errorType'){
			jumbo.showMsg("作业指令"+rs["staCode"]+"的指令类型不正确");return;
		}
		if(rs["msg"] == 'errorChName'){
			jumbo.showMsg("作业指令有不同关联店铺");return;
		}
		if(rs["msg2"] == 'tipSuccess'){
			var str='';
			if(rs["codeList"]!=''){
				str="作业指令"+rs["codeList"]+"的状态已取消 ";
			}
			if(rs["codeList2"]!=''){
				str="作业指令"+rs["codeList2"]+"已使用 ";
			}
			if(rs["codeList3"]!=''){
				str="作业指令"+rs["codeList3"]+"取消未处理 ";
			}
			jumbo.showMsg(str);
		}
		if(rs["msg"] == 'success'){
			$j("#centerType").focus();//run
			document.getElementById("div2").style.display="none";
			document.getElementById("div3").style.display="block";
			document.getElementById("departmentshop").options.length = 0;  
			$j("#departmentshop").append("<option value=''>请选择</option>");
			initShopQuery("departmentshop","innerShopCode");
			jumbo.loadShopList("departmentshop");
			if(brand !=0){
			$j("#sender").val(rs["stockTrans"]["chName"]);//设置寄件人
			$j("#senderTel2").val(rs["stockTrans"]["chTel"]);//设置寄件人联系方式
//			$j("#sAddress").val(rs["stockTrans"]["chAddress"]);//设置寄件人详细地址
			$j("#centerType option").each(function (){//店铺选择
			    if($j(this).text()=='店铺'){
			        $j(this).attr('selected',true);
			    }
				});
			$j("#departmentshop option").each(function (){
			    if($j(this).text()==rs["stockTrans"]["chName"]){
			        $j(this).attr('selected',true);
			    }
				});
			if(type=='3'){//补发，设置收货地址相关信息
				$j("#rAddress").val(rs["stockTrans"]["reAddress"]);//设置收货地址
//				$j("#rCountry").val(rs["stockTrans"]["reCountry"]);//设置收货国家
				$j("#rProvince").val(rs["stockTrans"]["reProvince"]);//设置收货省
				$j("#rCity").val(rs["stockTrans"]["reCity"]);//设置收货市
				$j("#rArea").val(rs["stockTrans"]["reDistrict"]);//设置收货区
				
				$j("#receiver").val(rs["stockTrans"]["reReceiver"]);//设置收货人
				$j("#rTel").val(rs["stockTrans"]["reTelephone2"]);//设置手机号
				$j("#yrTel").val(rs["stockTrans"]["reTelephone"]);//设置原始手机号
				$j("#updateRel").show(); 
				document.getElementById("rTel").readOnly=true;
				}
			}
		}
	}
	
	
	$j("#updateRel").click(function() {//修改手机
		document.getElementById("rTel").readOnly=false;
		 $j("#rTel").val("");
		 $j("#yrTel").val("");
	});
	
	$j("#allCheckBox").click(function() {// 全选
		if ($j("#allCheckBox").is(":checked") == true) {
			$j("input[name='chkArr']").attr("checked", true);
		} else {
			$j("input[name='chkArr']").attr("checked", false);
		}
	});
	
});
	//添加行
 function addNewRow(){
	 var type=document.getElementById("businessType").value;
	 if(type==0){//0:请选择 1：转店，2：样品领用，3:补发，4:零担物流,5:公司件，6:其他
		 jumbo.showMsg("请选择业务类型");return;
	 }else  if(type==5){
		 jumbo.showMsg("请选择业务类型");return;
	 }
	 var tabObj=document.getElementById("myTab");//获取添加数据的表格
	 var rowsNum = tabObj.rows.length;
	 var tabObj=document.getElementById("myTab");//获取添加数据的表格
	 var rowsNum = tabObj.rows.length;//获取当前行数
	 var colsNum=tabObj.rows[rowsNum-1].cells.length;//获取行的列数
	 var myNewRow = tabObj.insertRow(rowsNum);//插入新行
	 var newTdObj1=myNewRow.insertCell(0);
	 newTdObj1.innerHTML="<input type='checkbox' name='chkArr' id='chkArr'"+rowsNum+"/>";
	 var newTdObj2=myNewRow.insertCell(1);
//	 newTdObj2.innerHTML="<select loxiaType='select' name='pickingList.status' style='width: 100px;'><option value='PACKING'>配货中</option><option value='FAILED_SEND'>快递无法送达</option></select>";
	 newTdObj2.innerHTML="<select loxiaType='select' id='typeList' name='staType' style='width: 150px;'><option value='0'>请选择</option><option value='11'>采购入库 </option><option value='12'>结算经销入库</option><option value='13'>自定义入库</option><option value='14'>代销入库</option><option value='15'>赠品入库 </option><option value='16'>移库入库 </option><option value='17'>货返入库 </option><option value='21'>销售出库</option><option value='22'>自定义出库</option><option value='25'>外部订单销售出库 </option><option value='31'>库内移动 </option><option value='32'>库间移动 </option><option value='41'>退换货申请-退货入库 </option><option value='42'>退换货申请-换货出库 </option><option value='45'>库存状态修改 </option><option value='50'>库存初始化 </option><option value='55'>GI 上架 </option><option value='61'>采购出库（采购退货出库） </option><option value='62'>结算经销出库 </option><option value='63'>包材领用出库</option><option value='64'>代销 出库</option><option value='81'>VMI移库入库 </option><option value='85'>VMI库存调整入库 </option><option value='86'>VMI库存调整出库 </option><option value='88'>转店 </option><option value='90'>同公司调拨 </option><option value='91'>不同公司调拨 </option> <option value='101'>VMI退大仓  </option><option value='102'>VMI转店退仓 </option><option value='110'>库存锁定 </option><option value='201'>福利领用 </option><option value='202'>固定资产领用 </option><option value='204'>报废出库 </option><option value='205'>促销领用 </option><option value='206'>低值易耗品 </option><option value='210'>样品领用出库 </option><option value='211'>样品领用入库 </option><option value='212'>商品置换出库 </option><option value='213'>商品置换入库 </option><option value='214'>送修出库 </option><option value='215'>送修入库 </option><option value='216'>串号拆分出库 </option><option value='217'>串号拆分入库 </option><option value='218'>串号组合出库 </option><option value='219'>串号组合入库 </option><option value='251'>盘点调整</option></select>";
	 var newTdObj3=myNewRow.insertCell(2);
	 newTdObj3.innerHTML="<input style='width: 100px;' loxiaType='input' name='staId' id='nodename'"+rowsNum+"/>";
 }
 	//窗口表格删除一行
 function removeRow(){
	var chkObj=document.getElementsByName("chkArr");
	var tabObj=document.getElementById("myTab");
	for(var k=0;k<chkObj.length;k++){
	if(chkObj[k].checked){
	tabObj.deleteRow(k+1);
	k=-1;
	}
	}
	$j("#allCheckBox").attr("checked", false);
 }
 	//点击业务类型表格行初始化
 function initRows(){
	 var tabObj=document.getElementById("myTab");
	 var staType=document.getElementsByName("staType");
	 if(staType.length>0){
		 for(var i=0;i<staType.length;i++){
			 tabObj.deleteRow(i+1);
				}
	 }
 }
 	//点击业务类型相关操作
 function businessType(){
	 initRows();//表格行初始化
	 document.getElementById("button").style.display="inline";
	 var type=document.getElementById("businessType").value; 
	if(type==1 || type==2){//1：转店，2：样品领用，3:补发，4:零担物流,5:公司件，6:其他
		document.getElementById("addNewRow").focus();
	}else if(type==5){
		$j("#centerType").focus();
		document.getElementById("div2").style.display="none";
		document.getElementById("div3").style.display="block";
		document.getElementById("departmentshop").options.length = 0;  
		$j("#departmentshop").append("<option value=''>请选择</option>");
//		initShopQuery("departmentshop","innerShopCode");
//		jumbo.loadShopList("departmentshop",null,true);
	}else if(type==3){
		 document.getElementById("button").style.display="none";
		 var tabObj=document.getElementById("myTab");//获取添加数据的表格
		 var rowsNum = tabObj.rows.length;
		 var tabObj=document.getElementById("myTab");//获取添加数据的表格
		 var rowsNum = tabObj.rows.length;//获取当前行数
		 var colsNum=tabObj.rows[rowsNum-1].cells.length;//获取行的列数
		 var myNewRow = tabObj.insertRow(rowsNum);//插入新行
		 var newTdObj1=myNewRow.insertCell(0);
		 newTdObj1.innerHTML="<input type='checkbox' name='chkArr' id='chkArr'"+rowsNum+"/>";
		 var newTdObj2=myNewRow.insertCell(1);
		 newTdObj2.innerHTML="<select loxiaType='select' id='typeList' name='staType' style='width: 150px;'><option value='21'>销售出库</option></select>";
		 var newTdObj3=myNewRow.insertCell(2);
		 newTdObj3.innerHTML="<input style='width: 100px;' loxiaType='input' name='staId' id='nodename'"+rowsNum+"/>";
	}
	
 }
 
 //选择店铺时，加载信息
 function departmentShop(){
		var type=document.getElementById("centerType").value;
		if(type==2){//店铺
			var shopCode=document.getElementById("departmentshop").value;
			var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/json/getChannelByCode.do",{"skuId":shopCode});
			if(rs["brand"]=='1'){
				$j("#sender").val(rs["channel"]["name"]);//设置寄件人
				$j("#senderTel2").val(rs["channel"]["telephone"]);//设置寄件人联系方式
			}
		}

 }
 
//中心类型
function centerType(){
	var type=document.getElementById("centerType").value; 
	if(type==1){//部门
		 $j("#sender").val('');
		 $j("#senderTel").val('');
		 document.getElementById("departmentshop").options.length = 0;  
		 $j("#departmentshop").append("<option value=''>请选择</option>");
		var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/json/getChooseOptionByCode.do",{"categoryCode":"departmentType"});
		for(var i=0;i<result.length;i++){
			$j("#departmentshop").append("<option value="+result[i].optionKey+">"+result[i].optionValue+"</option>");
		}
	}else if(type==2){//店铺
		document.getElementById("departmentshop").options.length = 0;  
		$j("#departmentshop").append("<option value=''>请选择</option>");
		initShopQuery("departmentshop","innerShopCode");
		jumbo.loadShopList("departmentshop");
	}
}
//选择物流商动态加载时效类型
function LpCode(){
	var type=document.getElementById("selectLpCode").value; 
	 document.getElementById("timeType").options.length = 0;  
	 $j("#timeType").append("<option value=''>请选择</option>");
	 if(type !=''){
		 var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/findTransServiceByCode.json?expCode="+type);
		 if(result["brand"]=="yes"){
			 $j("#timeType2").show(); 
			 $j("#timeType").show(); 
			 for(var i=0;i<result["list"].length;i++){
					$j("#timeType").append("<option value="+result["list"][i].timeTypeKey+">"+result["list"][i].timeTypes+"</option>");
					}
		 }else{
			 $j("#timeType2").hide(); 
			 document.getElementById("timeType").options.length = 0;  
			 $j("#timeType").append("<option value='0'></option>");
			 $j("#timeType").hide(); 
		 }
		}
}
 
 
