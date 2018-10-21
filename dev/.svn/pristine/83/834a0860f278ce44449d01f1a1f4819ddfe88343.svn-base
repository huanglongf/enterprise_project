// 单个表单元素校验
function checkValue(currentNode) {
	// 显示校验信息节点
	var checkInfoNode;
	if(typeof(currentNode.attr("data-edit-select")) == "undefined") {
		checkInfoNode= currentNode.parent().next();
		
	} else {
		checkInfoNode = currentNode.parent().parent().next();
		
	}
	//
	checkInfoNode.empty();
	// 元素值
	var value= currentNode.val();
	// 校验项
	var checkType= eval('(' + currentNode.attr("checkType") + ')');
	// 校验值
	var param= eval('(' + currentNode.attr("validate-param") + ')');
	// 校验信息
	var info = "";
	if("NOTNULL" in checkType && value == "") {
		// 非空校验
		info = checkType["NOTNULL"];
		
	}
	if(info == "" && "LENGTH" in checkType) {
		// 长度校验
		if("MAX" in param && value.length > param["MAX"]) {
			info = "超出长度上限";
			
		}
		if(info == "" && "MIN" in param && value.length < param["MIN"]) {
			info = "不足长度下限";
			
		}
		
	}
	if(info == "" && "NUM" in checkType && isNaN(value)) {
		// 非数字
		info= checkType["NUM"];
		
	}
	if(info == "" && "PHONE" in checkType && value.match(/^1(3|4|5|7|8)\d{9}$/) == null && value.match(/^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$/) == null) {
		// 座机/手机格式不合法
		info= checkType["PHONE"];
		
	}
	if(info == "" && "yyyy-MM-dd hh:mm:ss" in checkType && value != "" && value.match(/^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-8]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))(\s(([01]\d{1})|(2[0123])):([0-5]\d):([0-5]\d))?$/) == null) {
		// 年月日时分秒格式不合法
		info= checkType["yyyy-MM-dd hh:mm:ss"];
		
	}
	if(info == "") {
		// 通过校验
		checkInfoNode.append("<span class='block input-icon input-icon-left'><i class='icon-ok-sign' style='color: green;'></i></span>");
		// 校验标识更新
		if(checkInfoNode.attr("validation") == "0") {
			checkInfoNode.attr("validation", "1");
			
		}
		return true;
		
	} else {
		// 异常
		checkInfoNode.append("<label class= 'no-padding-right red' style= 'white-space: nowrap;' >" + info + "</label>");
		if(checkInfoNode.attr("validation") == "1") {
			checkInfoNode.attr("validation", "0");
			
		}
		return false;
		
	}
	
}

//清空校验信息
function initCheckInfo(selector) {
	var checkInfoNode;
	if(selector.indexOf("#") == 0) {
		var currentNode= $(selector);
		if(typeof(currentNode.attr("data-edit-select")) == "undefined") {
			checkInfoNode= currentNode.parent().next();
			
		} else {
			checkInfoNode= currentNode.parent().parent().next();
			
		}
		
	}
	if(selector.indexOf(".") == 0) {
		checkInfoNode= $(selector)
		
	}
	checkInfoNode.empty();
	checkInfoNode.attr("validation", "0");
	
}

// 校验只是表单所有需要提交的数据
function checkValues(formId) {
	var flag= 0;
	$("#" + formId + " [checkType]").each(
		function() {
			// 当前节点
			// 校验信息节点
			var checkInfoNode;
			if(typeof($(this).attr("data-edit-select")) == "undefined") {
				checkInfoNode= $(this).parent().next();
				
			} else {
				checkInfoNode= $(this).parent().parent().next();
				
			}
			if((checkInfoNode.attr("validation") == 0) && !checkValue($(this))) {
				flag++;
				
			}
			
		}
	
	);
	if(flag == 0) {
		return true;
		
	} else {
		return false;
		
	}
	
}