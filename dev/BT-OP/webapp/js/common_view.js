/**
 * 弹出遮罩层
 */
function showDialogPage(height,url) {   
   if($("#bg").attr("id")!=undefined){
	 $("#bg").remove();
	}
   if($("#show").attr("id")!=undefined){
	 $("#show").remove();
	}	
   var body = document.body;
   //背景元素
   var div = document.createElement("div");
   div.id = "bg";
   body.appendChild(div);
   //对话窗元素
   var div2 = document.createElement("div");
   div2.id = "show";
   body.appendChild(div2);
   //添加样式
   $("#bg").addClass("dialog-bg");
   $("#show").addClass("dialog-show");
   //指定坐标
   var a_height=window.screen.availHeight;   
   $("#bg").css("height",a_height);
   $("#show").css("height",height);  
   $("#show").css("top",'10');
   $("#bg").css("display","block");
   $("#show").css("display",'block');   
   //页面禁止滚动
   $(document.body).css({"overflow-x":"hidden","overflow-y":"hidden"});
   //加载页面
//   if(type=="json"){
//	   show_dialog_page_retrun_json("show",url);
//   }
   //if(type=="html"){
	   show_dialog_page("show",url);	   
  // }

}

function showDialogPage1(height,url) {   
	   if($("#bg").attr("id")!=undefined){
		 $("#bg").remove();
		}
	   if($("#show").attr("id")!=undefined){
		 $("#show").remove();
		}	
	   var body = document.body;
	   //背景元素
	   var div = document.createElement("div");
	   div.id = "bg";
	   body.appendChild(div);
	   //对话窗元素
	   var div2 = document.createElement("div");
	   div2.id = "show";
	   body.appendChild(div2);
	   //添加样式
	   $("#bg").addClass("dialog-bg");
	   $("#show").addClass("dialog-show");
	   //指定坐标
	   var a_height=document.body.clientWidth; 
	   var oShowH=$("#show").height()/2;
	   var oShowW=$("#show").width();
	   $("#bg").css("height",a_height);
	   $("#show").css("height",height);  
	   $("#show").css({"top":'50%',"margin-top":-oShowH,"position":"fixed"});
	   $("#bg").css("display","block");
	   $("#show").css("display",'block');   
	   //页面禁止滚动
	   $(document.body).css({"overflow-x":"hidden","overflow-y":"hidden"});
	   //加载页面
	//   if(type=="json"){
//		   show_dialog_page_retrun_json("show",url);
	//   }
	   //if(type=="html"){
		   show_dialog_page("show",url);	   
	  // }
	}
function show_dialog_text(text,height,width) {   
	   if($("#bg").attr("id")!=undefined){
		 $("#bg").remove();
		}
	   if($("#show").attr("id")!=undefined){
		 $("#show").remove();
		}	
	   var body = document.body;
	   //背景元素
	   var div = document.createElement("div");
	   div.id = "bg";
	   body.appendChild(div);
	   //对话窗元素
	   var div2 = document.createElement("div");
	   div2.id = "show";
	   body.appendChild(div2);
	   //添加样式
	   $("#bg").addClass("dialog-bg");
	   $("#show").addClass("dialog-show");
	  // $("#bg").css({"-moz-opacity":"0.9","opacity":"90","filter": "alpha(opacity=90)"});
	   //指定坐标
	   var a_height=window.screen.availHeight;   
	   var a_width=window.screen.availWidth;
	   $("#bg").css("height",a_height);
	   $("#show").css("height",height);  
	   $("#show").css("width",width);  
	   $("#show").css("top",'10');
	   $("#show").css("left",(a_width-width)/2);
	   $("#bg").css("display","block");
	   $("#show").css("display",'block');   
	   //页面禁止滚动
	   $(document.body).css({"overflow-x":"hidden","overflow-y":"hidden"});
	   //加载页面
	  $("#show").html("<div>" +
	  		           "<h5>"+text+"</h5>"+
	  		          "<div>"+
	  		          "<div>" +
	  		           "<button class='btn btn-xs btn-primary' id='' onclick='set_ok()'>"+
	  		           "<i></i><span id='but_text_1'>确定</span>"+
	  		           "</button>"+
	  		           "&nbsp;&nbsp;&nbsp;&nbsp;"+
	  		           "<button class='btn btn-xs btn-grey' id='' onclick='hidediv()'>"+
	  		           "<i></i><span id='but_text_1'>取消</span>"+
	  		           "</button>"+	  		           
	  		           "</div>"
	  		           )
	}


	


function show_dialog_page(div,url){
	   $.ajax({
			type : "POST",
			url: url,  
			data:"",
			dataType: "html",  
			success : function(jsonHtml) {
//				alert(div);
				$("#"+div).html(jsonHtml);
			}
		});
}

function show_dialog_page_retrun_json(div,url){
	var result="";
	   $.ajax({
			type : "POST",
			url: url,  
			data:"",
			dataType: "html",  
			async:false,
			success : function(jsonStr) {
				result=jsonStr;
			}
		});	
	   return result;
}


/**
 * 隐藏遮罩层
 */
function hidediv() {
   document.getElementById("bg").style.display ='none';
   document.getElementById("show").style.display ='none';
   $(document.body).css({"overflow-x":"auto","overflow-y":"auto"});
}

function set_ok(){
	return true;
}

/**
 * 设置固定表头
 */
function init_td(titleId,contendId){
 $("#"+titleId).scrollLeft($("#"+contendId).scrollLeft());
}
