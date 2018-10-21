$j.extend(loxia.regional['zh-CN'],{
	SURE_CANCEL : "您确定要作废？"
	
});

function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}

function checkNumber(number){
	var regExp = /^\d+$/; 
	return regExp.test(number);
}

$j(document).ready(function (){
	loxia.asyncXhrPost(window.parent.$j("body").attr("contextpath")+"/json/queryWorkLineNo.json",{},
	{
		success:function (data) {
			if(data&&data.msg){
				loxia.unlockPage();
				jumbo.showMsg(data.msg);	
			}else{
				loxia.unlockPage();
				initInfo(data);
			}
		}, 
		error:function(data){
			loxia.unlockPage();
			jumbo.showMsg(i18("MSG_FAILURE"));	
		}			   
	}); 	
	
	$j("#save").click(function (){
		var workLineNo = {};
		var tempCode = [];
		var error = 0;
		$j.each($j("#tab_tbody tr"),function(i,bian){
			var code = $j(bian).find("td input[name='code']").val();
			var lineNo = $j(bian).find("td input[name='lineNo']").val();
			if(code == ""){
				jumbo.showMsg("编码不能为空!");	
				error += 1;
				return;
			}
			if(!checkNumber(code)){
				jumbo.showMsg("编码只能是数字!");	
				error += 1;
				return;
			}
			if(lineNo == ""){
				jumbo.showMsg("开票机器编码不能为空!");	
				error += 1;
				return;
			}
			for(var j = 0; j < i; j++){
				if(code == tempCode[j]){
					jumbo.showMsg("编码["+code+"]有重复!");	
					error += 1;
					return;
				}
			};
			tempCode[i] = code;
			workLineNo["workLineNo["+i+"].code"]=code;
			workLineNo["workLineNo["+i+"].lineNo"]=lineNo;
			i++;
		});
		if(error){
			return ;
		}
		loxia.asyncXhrPost(window.parent.$j("body").attr("contextpath")+ "/json/updateLineNo.json",
				workLineNo,
				{
				success:function(data){
					if(data){
						if(data.result=="success"){
							jumbo.showMsg("操作成功");
						}else if(data.result=="error"){
							jumbo.showMsg(data.message);
						}
					} else {
						jumbo.showMsg("数据操作异常");
					}
				},
				error:function(){jumbo.showMsg("数据操作异常");}//操作数据异常
		});
		
	});
});

function deleteTr(index){
	$j("#tab_tbody tr[index='("+index+")']").remove();
}

function initInfo(data){
	var html=[];
	$j("#stvlineListtable tr:gt(0)").remove();
	$j.each(data,function(i, line) {
		html.push(getTrInfo(line,i));
	});
	$j("#tab_tbody").prepend(html.join(""));
	loxia.initContext($j("#tab_info"));
}


function getTrInfo(data,i){
	var html =	'<tr class="add even" index="('+(i)+')" id="dateTr('+(i)+')">'+
		'<td><input type="checkbox" id="chock('+(i)+'")/></td>'+
		'<td><input loxiatype="number" mandatory="true" name="code" class="code" value="'+data.code+'"/></td>'+
		'<td><input loxiatype="input" mandatory="true" name="lineNo" class="name" value="'+data.lineNo+'"/></td>'+
		'<td width="100"><button type="button" loxiaType="button" id="delete('+(i)+')" onclick="deleteTr('+(i)+')" class="confirm">删除</button></td>'+
		'</tr>';
	return html;
}