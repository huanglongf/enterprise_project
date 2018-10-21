$j(document).ready(function (){
	$j("#search").click(function(){
		toGetShippingCollectionBoard();
	});
});
function toGetShippingCollectionBoard() {
	var fullTime=$j("#fullTime").val();
	var notFullTime=$j("#notFullTime").val();
	if(fullTime==null||fullTime==''){
		$j("#tbl-detail-list tr").remove();
		jumbo.showMsg("请设置集满超时预警时间");
		return;
	}
	if(notFullTime==null||notFullTime==''){
		$j("#tbl-detail-list tr").remove();
		jumbo.showMsg("请设置未集满超时预警时间");
		return;
	}
	if(isNaN(fullTime)||isNaN(notFullTime)){
		jumbo.showMsg("时间非法");
		$j("#tbl-detail-list tr").remove();
		return;
	}
	var map=loxia.syncXhr($j("body").attr("contextpath") + "/findshippingCollectionBoard.json",{"fullTime":fullTime,"notFullTime":notFullTime});
	if(null!=map&&null!=map["value"]){
		$j("#tbl-detail-list tr").remove();
		var array={};
		var s=map["value"].substring(0,map["value"].length-1);
		array=s.split(";");
		var str="";
		for(var i=0;i<array.length;i++){
			if(null!=array[i]&&""!=array[i]){
				var msg={};
				msg=array[i].split(",");
				str+="<td id='"+i+"' >"+(i+1)+"通道"+"</td>";
				for(var j=0;j<msg.length;j++){
					if(msg[j].indexOf("白色")>=0){
						str+="<td   style='background-color:white' width='80'>"+msg[j].replace("-白色","")+"</td>";
					}else if(msg[j].indexOf("蓝色")>=0){
						str+="<td   style='background-color:blue' width='80'>"+msg[j].replace("-蓝色","")+"</td>";
					}else if(msg[j].indexOf("绿色")>=0){
						str+="<td   style='background-color:green' width='80'>"+msg[j].replace("-绿色","")+"</td>";
					}else if(msg[j].indexOf("红色")>=0){
						str+="<td   style='background-color:red' width='80'>"+msg[j].replace("-红色","")+"</td>";
					}else if(msg[j].indexOf("黄色")>=0){
						str+="<td  style='background-color:yellow' width='80'>"+msg[j].replace("-黄色","")+"</td>";
					}
				}
			}
			if(null!=array[i]&&""!=array[i]){
				$j("<tr >"+str+"</tr>").appendTo($j("#tbl-detail-list"));
			}
			str="";
		}
	}
}
setInterval(toGetShippingCollectionBoard, 60000);//定时刷新，1分钟一次