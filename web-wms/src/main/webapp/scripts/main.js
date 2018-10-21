var BARCODE_CONFIRM = 'OK'; //确认条码

(function($){
	var _this = this;
	if(typeof this["jumbo"] === "undefined"){
		this.jumbo = {
		defaultPageSize:20,
		defaultPageSizeList:[20,40,100,200],
		defaultHeight:'auto',
		getPageSize:function (){
			return this.defaultPageSize;
		},
		getPageSizeList: function(){
			return this.defaultPageSizeList;
		},
		getHeight:function(){
			return this.defaultHeight;
		},
		_constructHidden: function(name,value){
			name = name||"",value = value||"";
			return "<input type=hidden name=\"" + name + "\" value=\"" + value + "\" />";
		},
		bindTableExportBtn: function(table,optionCols,url,postData,postCheck){
			var $t = loxia.isString(table)?$j("#" + table):$j(table),
				url = url || $t.jqGrid("getGridParam","url"),
				$c = $t.parents("div.ui-jqgrid");
			var html = [];
			html.push('<a class="ui-jqgrid-titlebar-export HeaderButton"' +  
					' href="javascript:;" title="导出" role="link" style="float:left;">' + 
					'<span class="ui-icon ui-icon-comment"><!-- --></span></a>');
			html.push("<form action="+url+" type='post'>");
			html.push(this._constructHidden("isExcel","true"));
			html.push(this._constructHidden("page","1"));
			html.push(this._constructHidden("rows","-1"));
			html.push(this._constructHidden("sidx",""));
			html.push(this._constructHidden("sord",""));
			html.push(this._constructHidden("caption",$t.jqGrid("getGridParam","caption")));
			var cm=$t.jqGrid("getGridParam","colModel"),cn=$t.jqGrid("getGridParam","colNames"),index=-1;
			var filter={"id":"id","rn":"","subgrid":"","cb":""};
			$j.each(cm,function(i,e){
				if(e.hidden==false&&!(e.name in filter)){
					index++;
					html.push(jumbo._constructHidden("colModel["+index+"]",e.name));
					html.push(jumbo._constructHidden("colNames["+index+"]",cn[i]));
				}
			});
			if(optionCols){
				for(var k in optionCols){
					html.push(this._constructHidden("columnOption." + k,optionCols[k]));
				}
			}
			if(postData){
				for(var k in postData){
					html.push(this._constructHidden(k,postData[k]));
				}
			}
			html.push("</form>");
			if($c.find(".ui-jqgrid-titlebar-export").length>0){
				$c.find(".ui-jqgrid-titlebar-export").remove();
				$c.find("form").remove();
			}
			$c.find(".ui-jqgrid-titlebar .ui-jqgrid-titlebar-close").after(html.join(""));
			$c.find(".ui-jqgrid-titlebar-export").hover(function(){
				$j(this).addClass("ui-state-hover");
			},function(){
				$j(this).removeClass("ui-state-hover");
			});
			$c.find(".ui-jqgrid-titlebar-export").click(function(){
				var num;
				var text = $c.find("div.ui-jqgrid-pager>div>table>tbody>tr>td:eq(2)>div").text();
				if(text == "" || text == "无数据显示"){
					alert("没有要导出的数据!");
					return;
				}
				if(text==""){
					var num1 = $c.find("div.ui-jqgrid-pager>div>table>tbody>tr>td:eq(1)>table>tbody>tr>td:eq(3)>span").text().replace(" ","");
					var num2 = $c.find("div.ui-jqgrid-pager>div>table>tbody>tr>td:eq(1)>table>tbody>tr>td:eq(7)>select").val();
					num = parseInt(num1)*parseInt(num2);
				}else{
					var d = text.indexOf("共");
					if(d>0){
						var num=text.substring(d+2,text.length-2);
						num=num.replace(new RegExp(" ", 'g'), "");
					}
				}
				if(num>20000){
					alert("系统设定最多导出20000条数据!");
					return;
				}
				if(typeof(postCheck)==="function"){
					if(postCheck()== false){
						return false;
					}
				}
				$c.find(".ui-jqgrid-titlebar input[name='sidx']").val($t.jqGrid("getGridParam","sortname"));
				$c.find(".ui-jqgrid-titlebar input[name='sord']").val($t.jqGrid("getGridParam","sortorder"));
				$c.find(".ui-jqgrid-titlebar form")[0].submit();
				return false;
			});
		},
		bindExcelButton:function(tblId,columnOption,url){
			url=url||$j("#"+tblId).jqGrid("getGridParam","url");
			var html=[];
			html.push("<input type='button' value='Excel导出' id='export' class='ui-button ui-widget ui-corner-all'/>");
			html.push("<form action="+url+" type='post'>");
			html.push("<input type='hidden' name='isExcel' value=true />");
			html.push("<input type='hidden' name='page' value=1 />");
			html.push("<input type='hidden' name='rows' value=-1 />");
			html.push("<input type='hidden' name='sidx' value="+ $j("#"+tblId).jqGrid("getGridParam","sortname")+" />");
			html.push("<input type='hidden' name='sord' value="+ $j("#"+tblId).jqGrid("getGridParam","sortorder")+" />");
			html.push("<input type='hidden' name='caption' value="+ $j("#"+tblId).jqGrid("getGridParam","caption")+" />");
			var cm=$j("#"+tblId).jqGrid("getGridParam","colModel"),cn=$j("#"+tblId).jqGrid("getGridParam","colNames"),index=-1;
			var filter={"rn":"","subgrid":""};
			$j.each(cm,function(i,e){
					if(e.hidden==false&&!(e.name in filter)){
						index++;
						html.push("<input type='hidden' name='colModel["+index+"]' value="+ e.name+" />");
						html.push("<input type='hidden' name='colNames["+index+"]' value="+ cn[i]+" />");
					}
				});
			if(columnOption){
				for(var i in columnOption){
					html.push("<input type='hidden' name='columnOption."+i+"' value="+ columnOption[i]+" />");
				}
			}
			html.push("</form>");
			$j("#t_"+tblId).append(html.join(""));
			$j("#export","#t_"+tblId).click(function(){
				loxia.submitForm($j(this).next());
			});
		},
		/**
		 * EMS打印
		 * @param plid 配货清单ID
		 * @param staCodes 所有需要打印免单的作业单号
		 * @return
		 */
		emsPrint:function emsPrint(staCodes,plid){
			var rs =loxia.syncXhr($j("body").attr("contextpath")+"/json/findEmsTrandInfo.do?pickingListId="+plid);
			if(rs && rs.result != 'success'){
				jumbo.showMsg("EMS帐号信息不存在！");
				return ;
			}else{
				var emsObject = document.getElementById("emsObject");
				try{
					var rtn = emsObject.CheckID(rs.ems.account + '#%'+ rs.ems.password);
					if(rtn.substring(0,2) == '1#'){
						//验证成功处理
						var errorMsg = '面单打印失败，作业单号：';
						var isError = false;
						for(var i = 0 ; i < staCodes.length ; i++){
							var tmp = rs.ems.account + '#%' + staCodes[i] ;
							var rtnPrint = emsObject.PrtHotBillAndGetBillNo(tmp);
							if(rtnPrint.substring(0,2) == '0#'){
								//打印失败，尝试重新打印接口
								rtnPrint = emsObject.RePrtHotBill(tmp);
							}
							if(rtnPrint.substring(0,2) == '1#'){
								//打印成功,更新快递单号
								var t = rtnPrint.split('#%')
								var transNo = t[1];
								//更新快递单号
								loxia.asyncXhrPost($j("body").attr("contextpath") + "/updateStaTransNo.json",
										{"staCode":staCodes[i],"transNo":transNo},{
									success:function (data) {
											if(data.result=="error"){
												loxia.asyncXhrPost($j("body").attr("contextpath") + "/updateStaTransNo.json",
														{"staCode":staCodes[i],"transNo":transNo},{
													success:function (data1) {
															if(data1.result=="error"){
																loxia.asyncXhrPost($j("body").attr("contextpath") + "/updateStaTransNo.json",
																		{"staCode":staCodes[i],"transNo":transNo},{
																	success:function (data2) {
																			if(data2.result=="error"){
																				errorMsg+="作业单："+staCodes[i]+"快递单号"+transNo+"更新失败!<br/>";
																				isError=true;
																			}
																   }
																});
															}
												   }
												});
											}
								   }
								});
							}else{
								var t = rtnPrint.split('#%')
								var emsMsg = t[1];
								errorMsg += staCodes[i] + "-" + emsMsg + "<br/>";
								isError = true;
							}
						}
						if(isError){
							jumbo.showMsg(errorMsg);
						}
					}else{
						jumbo.showMsg("EMS帐号信息错误：" + rtn.substring(3,rtn.length-1));
					}
				}catch(e){
					jumbo.showMsg("打印EMS面单请使用IE核心浏览器！");
				}
			}
		},
		showMsg: function(content, timeout, msgType, target){
				msgType = msgType || "info";
				if(target == undefined){
					if(window.parent && window.parent.jQuery && window.parent.jQuery("#msg").length > 0)
						target = window.parent;
					else
						target = _this;
				}		
				var $msg = target.jQuery("#msg");
				if(content || content === ""){			
					if(content){
						target.jQuery("#msg span.ui-icon").attr("class","ui-icon ui-icon-" + msgType);
						var contentStr = "";
						if(loxia.isString(content)){
							contentStr = content + "\r\n";
						}else if($.isArray(content)){
							for(var i=0, c;(c=content[i]);i++)
								contentStr += (c + "\r\n");
						}
						target.jQuery("#msg textarea.content").val(contentStr);
					}
					
					var base = target.jQuery("div.ui-tabs-panel:not(.ui-tabs-hide) .frame-control");
					if(base.length > 0){
						var pos = base.offset(),
						h = base.height(),
						w = base.width();
						
						$msg.css({
							position: "absolute",
							left: pos.left + (w - $msg.width())/2,
							top: pos.top + h,
							zIndex: 9999
						});
					}
					$msg.addClass("shown").show("drop",{direction:"up"},500);	
					if(timeout == undefined) timeout = 5000;
					if(timeout)
						setTimeout(function(){$msg.removeClass("shown").hide("highlight",{},500);},timeout);
				}else
					$msg.removeClass("shown").hide("highlight",{},500);
			},
			max:function(o,name,empty,max){
				var obj=loxia.isString(o)?jQuery("#"+o):o;
				var max=$j(obj).attr("max")||max;
				if(empty&&$j.trim($j(obj).val()).length==0){
					this.showMsg(name+"不能为空，并且最长只能"+max+"个字符！");
					return false;
				}else if(max&&$j.trim($j(obj).val()).length>max){
					this.showMsg(name+"最长只能"+max+"个字符！");
					return false;
				}
				return true;
			},
			openFrame:function(frameId, frameName, a, target) {
				if(target == undefined){
					if(window.parent && window.parent.jQuery && window.parent.jQuery("#functionlist").length > 0)
						target = window.parent;
					else
						target = _this;
				}		
				var container = target.jQuery("#frame-container");
												
				if(loxia.isString(a)){
					var $a = target.jQuery("#functionlist").find("a[href='" + a +"']");
					if($a.length ==0){
						target.jQuery("#functionlist").append("<a frameId='"+ frameId +"' href='" + a +"'>" + frameName + "</a>");
					}else{
						$a.attr("frameId",frameId);
					}						
				}else
					target.jQuery(a).attr("frameId",frameId);
				container.tabs("add","#" + frameId, frameName);
				target.jQuery("#" + frameId + " iframe").height(container.height() - target.jQuery("> ul.ui-tabs-nav", container).height() - 30);
				
				container.tabs("select", container.tabs("length")-1);
				//container.find("li:eq(" + idx + ")").attr("frameId", frameId);
			},

			/***
			 * 店铺列表
			 * @param divShopid		加载到页面的div，id
			 * @param selectValue	下拉列表框的选项。[innerShopCode,id,shopId,null]
			 * @param isAllShop		是否列出所有公司的所有店铺，[true, false,null]
			 * 
			 * @return
			 */ 
			loadShopList : function loadShopList(divShopid, selectValue, isAllShop){
				var baseUrl = window.$j("body").attr("contextpath");
				if(isAllShop == undefined || isAllShop == null || !isAllShop){
					var result = loxia.syncXhrPost(loxia.getTimeUrl(baseUrl + "/findshoplistbycompanyid.json"));					
				} else if (isAllShop){
					var result = loxia.syncXhrPost(loxia.getTimeUrl(baseUrl + "/findallshoplist.json"));
				}else {
					var result = loxia.syncXhrPost(loxia.getTimeUrl(baseUrl + "/findshoplistbycompanyid.json"));
				}
				//平台类型1：淘宝；2：乐酷天
				if (selectValue == undefined || selectValue == null || selectValue == 'innerShopCode'){
					for(var idx in result){
						$j('<option value="' + result[idx].code + '">'+ result[idx].name +'</option>').appendTo($j("#"+divShopid));
					}
				}else if (selectValue == 'id'){
					for(var idx in result){
						$j("<option value='" + result[idx].id + "'>"+ result[idx].name +'</option>').appendTo($j("#"+divShopid));
					}
				}else if (selectValue == 'shopId'){
					for(var idx in result){
						$j('<option value="' + result[idx].code + '">' + result[idx].name +'</option>').appendTo($j("#"+divShopid));
					}
				}else{
					for(var idx in result){
						$j('<option value="' + result[idx].code + '">' + result[idx].name +'</option>').appendTo($j("#"+divShopid));
					}
				}
			},
			loadShopPlatform : function loadShopPlatform(){
				var status=loxia.syncXhr($j("body").attr("contextpath") + "/formateroptions.json",{"categoryCode":"whSTAStatus"});
				return status;
			},
			
			//获取流水线号，如果没有则显示对话框输入
			getAssemblyLineNo :function (){
				var no = $j(window.parent.document.body).attr("AssemblyLineNo");
				if(no){
					return no;
				}else{
					var aln = $j(window.parent.document.body).find("#DIV_AssemblyLineNo");
					if(aln.length == 0){
						$j(document.body).append("<div id='DIV_AssemblyLineNo'>" +
								"<table><tr><td style='font-size: 21px;'>请输入流水线号</td>" +
								"<td><input style='height: 28px; line-height:  28px; width:  50px;font-size: 21px;' loxiaType='input' type='text' id='TXT_AssemblyLineNo'></td>" +
								"</tr><tr><td><button loxiaType='button' class='confirm' id='BTN_AssemblyLineNo'>确认</button> 确认条码</td>" +
								"<td><input id='TXT_AssemblyLineNo_CF' style='width:  50px;' type='input' loxiaType='input'></td></tr></table></div>");
						loxia.initContext($j("#DIV_AssemblyLineNo"));
						$j("#BTN_AssemblyLineNo").click(function(){
							$j(window.parent.document.body).attr("AssemblyLineNo",$j("#TXT_AssemblyLineNo").val());
							var tag = $j(window.parent.document.body).find("#infobar").find("#DIV_AssemblyLineNo_SHOW");
							if(tag.length == 0){
								$j(window.parent.document.body).find("#infobar").append("<span id='DIV_AssemblyLineNo_SHOW'>当前流水线："+$j("#TXT_AssemblyLineNo").val()+"</span>");
							}else{
								$j("#DIV_AssemblyLineNo_SHOW").html("当前流水线："+$j("#TXT_AssemblyLineNo").val());
							}
							$j("#DIV_AssemblyLineNo").dialog("close");
						});
						$j("#TXT_AssemblyLineNo").keydown(function(evt){
							if(evt.keyCode === 13){
								$j("#TXT_AssemblyLineNo_CF").focus();
							}
						});
						$j("#TXT_AssemblyLineNo_CF").keydown(function(evt){
							if(evt.keyCode === 13){
								$j("#BTN_AssemblyLineNo").trigger("click");
							}
						});
						$j("#TXT_AssemblyLineNo").focus();
						$j("#DIV_AssemblyLineNo").dialog({title: "流水线号", modal:true, autoOpen: false, width: 400,closeOnEscape: false,open: function(event, ui){$j(".ui-dialog-titlebar-close").hide();}});
					}else{
					}
					$j("#DIV_AssemblyLineNo").dialog("open");
					return null;
				}
			},
			
			removeAssemblyLineNo :function (){
				var no = $j(window.parent.document.body).attr("AssemblyLineNo");
				if(no){
					$j(window.parent.document.body).find("#DIV_AssemblyLineNo_SHOW").remove();
					$j(document.body).find("#DIV_AssemblyLineNo").remove();
					$j(window.parent.document.body).attr("AssemblyLineNo","");
				}
			},
			
			/**
			 * select加载所有物流
			 */
			loadTransportator : function(selTag){
				var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/getAllTransportator.json");
				if(result){
					for(var idx in result){
						$j("<option value='" + result[idx].id + "'>"+ result[idx].fullName +"</option>").appendTo($j("#"+selTag));
					}
				}
			}
		};
	}
	 
	function constructButton(cellvalue, btnoption){
		var btnstr = "<button loxiaType='button'";
		var btnlabel = btnoption.label||"确定";
		if(btnoption.cssClass)
			btnstr += " class='" + btnoption.cssClass + "'";
		if(btnoption.onclick){
			btnstr +=" onclick='" + btnoption.onclick + "'";
		}
		btnstr += " value='" + cellvalue + "'>" + btnlabel + "</button>";
		return btnstr;
	}

	$.extend($.fn.fmatter, {
	    buttonFmatter: function (cellvalue, options) {
	    	if(options.colModel.formatoptions.buttons != undefined){
	    		if($.isArray(options.colModel.formatoptions.buttons)){
	    			var btnlist = "";
	    			for(var i=0,b;(b=options.colModel.formatoptions.buttons[i]);i++)
	    				btnlist += constructButton(cellvalue, b);
	    			return btnlist;
	    		}else
	    			return constructButton(cellvalue, options.colModel.formatoptions.buttons);
	    	}else
	    		return $.fn.fmatter.defaultFormat(cellvalue, options); 
	    },
	    boolButtonFmatter: function (cellvalue, options) {
	    	if(!cellvalue||cellvalue==0)return "";
	    	if(options.colModel.formatoptions.buttons != undefined){
	    		if($.isArray(options.colModel.formatoptions.buttons)){
	    			var btnlist = "";
	    			for(var i=0,b;(b=options.colModel.formatoptions.buttons[i]);i++)
	    				btnlist += constructButton(cellvalue, b);
	    			return btnlist;
	    		}else
	    			return constructButton(cellvalue, options.colModel.formatoptions.buttons);
	    	}else
	    		return $.fn.fmatter.defaultFormat(cellvalue, options); 
	    },
	    linkFmatter: function(cellvalue, options) {
	    	if(options.colModel.formatoptions.onclick != undefined){
	    		return "<a href='javascript:;' onclick='" + options.colModel.formatoptions.onclick + "(this); return false;'>" + cellvalue + "</a>";
	    	}else
	    		return $.fn.fmatter.defaultFormat(cellvalue, options); 
	    },
	    loxiaInputFmatter: function(cellvalue, options){
	    	var loxiaType = options.colModel.formatoptions.loxiaType || "input";	    	
	    	var name = options.colModel.name || "";
	    	var editObj = "<input rowId='" + options.rowId + "' loxiaType='" + loxiaType +"' value='" + (cellvalue===undefined||cellvalue==null?'':cellvalue) + "'";
	    	if(name) editObj += " name='" + name + "'";
	    	if(options.colModel.formatoptions != undefined){	    		
	    		var o = options.colModel.formatoptions;
	    		if(o.mandatory) editObj += " mandatory='" + o.mandatory + "'";
	    		if(o.decimal) editObj += " decimal='" +o.decimal +"'";
	    		if(o.min) editObj += " min='" + o.min + "'";
	    		if(o.max) editObj += " max='"+ o.max +"'";
	    		if(o.checkmaster) editObj += " checkmaster='" + o.checkmaster +"'";
	    	}
	    	editObj += "/>";
	    	return editObj;
	    },
	    loxiaSelectFmatter: function(cellvalue, options){
	    	if(options.colModel.formatoptions.templateSelect != undefined){
	    		var selectObj = "<select loxiaType='select' rowId='" + options.rowId + "'";
	    		
	    		var o = options.colModel.formatoptions;
	    		if(o.mandatory) editObj += " mandatory='" + o.mandatory + "'";
	    		if(o.checkmaster) editObj += " checkmaster='" + o.checkmaster +"'";
	    		
	    		var name = options.colModel.name || "";
	    		if(name) selectObj += " name='" + name + "'>";
	    		selectObj += $("#" + options.colModel.formatoptions.templateSelect).html();
	    		selectObj += "</select>";
	    		selectObj = selectObj.replace(new RegExp("value=\"" + cellvalue + "\""),"value=\"" + cellvalue + "\" selected");
	    		return selectObj;
	    	}else
	    		return $.fn.fmatter.defaultFormat(cellvalue, options); 
	    },
	    inputFmatter: function(cellvalue, options){
	    	var name = options.colModel.name || "";
	    	var editObj = "<input rowId='" + options.rowId + "' value='" + cellvalue + "'";
	    	if(name) editObj += " name='" + name + "'";	    	
	    	editObj += "/>";
	    	return editObj;
	    },
	    selectFmatter: function(cellvalue, options){
	    	if(options.colModel.formatoptions.templateSelect != undefined){
	    		var selectObj = "<select rowId='" + options.rowId + "'";
	    		var name = options.colModel.name || "";
	    		if(name) selectObj += " name='" + name + "'>";
	    		selectObj += $("#" + options.colModel.formatoptions.templateSelect).html();
	    		selectObj += "</select>";
	    		selectObj = selectObj.replace(new RegExp("value=\"" + cellvalue + "\""),"value=\"" + cellvalue + "\" selected");
	    		return selectObj;
	    	}else
	    		return $.fn.fmatter.defaultFormat(cellvalue, options); 
	    },
	    selectKVFmatter: function(cellvalue, options){
	    	if(options.colModel.formatoptions.templateSelect != undefined){
	    		var value="";
	    		$.each($("#" + options.colModel.formatoptions.templateSelect).find("option"),function(i,e){
	    			if($(e).val()==cellvalue){
	    				value=$(e).text();
	    				return false;
	    			} 
	    		});
	    		return value;
	    	}else
	    		return $.fn.fmatter.defaultFormat(cellvalue, options); 
	    }
	});
	
	$.extend($.fn.fmatter.loxiaInputFmatter, {
        unformat: function (celltext, options, cell) {
            var inputWidget = loxia.byCss("input", cell)[0];
            return inputWidget.val();
        }
	});
	
	$.extend($.fn.fmatter.buttonFmatter, {
        unformat: function (celltext, options, cell) {
            var $btn = $("button",cell);
            return $btn.attr("value");
        }
	});
	
	$.extend($.fn.fmatter.loxiaSelectFmatter, {
        unformat: function (celltext, options, cell) {
        	var inputWidget = loxia.byCss("select", cell)[0];
            return inputWidget.val();
        }
	});
	
	$.extend($.fn.fmatter.inputFmatter, {
        unformat: function (celltext, options, cell) {
            var $edit = $("input",cell);
            return $edit.val();
        }
	});
	
	$.extend($.fn.fmatter.selectFmatter, {
        unformat: function (celltext, options, cell) {
            var $edit = $("select",cell);
            return $edit.val();
        }
	});
	
	
	
})(jQuery);


/**
 * 打印
 * @param url
 * @param flag
 * @returns
 */
function printBZ(url,flag){
	var result = window.top.document.printApplet.printReport(url,flag);
	console.log("print result"+result);
	return result;
}

function getPrintName(){
	try {
		var printers =window.top.document.printApplet.getAllPrinterNames();
		console.log("print result"+printers);
		return printers;
	} catch (e) {
		if (window["console"]){
			console.log("getAllPrinterNames error!");	
		}
		return null;
	}
}

/*
	print used defined printer
*/
function printDf(url,printerName){
	window.top.document.printApplet.printReport(url,false,printerName);
}

function preview(url){
	window.top.document.printApplet.previewReport(url);
}

/**
 * 电子秤
 * @param weightVal
 */

function setRS232Value(weightVal){
	if(weightVal){
		var weight=document.getElementById('autoWeigth');
		weight.value=weightVal;
	}	
}
function appletStop(){}
function appletStart(){}

function restart(){
	if(document.weightApplet){
		if(typeof document.weightApplet.closeSerialPort != 'undefined'){
			window.top.document.weightApplet.closeSerialPort();
		}
		if(typeof document.weightApplet.initSerialPort != 'undefined'){
			window.top.document.weightApplet.initSerialPort();
		}
	}
}

//定时获取重量
function autoGetElWeight(){
	if(window.top && window.top.document && window.top.document.getElementById("autoWeigth")){
		var weight = window.top.document.getElementById("autoWeigth").value;
		$j("#autoWeigth").val(weight);
	}
}

/**
 * 星巴克读卡POS机回调方法
 * @param data
 */
function checkCard(data){
	//alert(data + "--");
	$j(".ui-tabs-panel").each(function(){
		  if(!$j(this).is('.ui-tabs-hide')){
			//TODO 测试调用页面JS方法,(测试页面：作业单查询)
		   // $j(this).find("iframe").contents().find("#c1").val("2017-01-01 00:00:00");
		   // $j(this).find("iframe").contents().find("#e1").val("2017-03-01 00:00:00");
			$j("#starbucksSnCode").val(data);
		    $j(this).find("iframe").contents().find("#getstarbucksSncode").trigger('click');
		  }
		});
}

$(document).ready(function(){
	if($j("#mainFrame").length == 0){
		window.setInterval(autoGetElWeight, 300);
	}
});

