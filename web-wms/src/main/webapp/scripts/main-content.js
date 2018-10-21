var main={};
main.pushA=function(html,node){
	if(node.menuId=='M-AUTH-USERPASSWORD-MODIFY'){
		var url=node.entryURL==null?"javascript:void(0);":node.entryURL;
		html.push('<a href="#" target="_blank" onclick ="yhmm(\''+url+'\')" acode="'+node.name+'">'+node.name+'</a>');
	}else{
		var url=node.entryURL==null?"javascript:void(0);":$j("body").attr("contextpath")+node.entryURL;
		html.push('<a href="'+url+'" acode="'+node.name+'">'+node.name+'</a>');
	}
}
function yhmm(url){
	window.open(url); 
}
main.createLeftTree=function(id,data){
	var html=[];
	html.push("<div id=\""+id+"\">");
	for(var d,i=0;d=data[i];i++){
		html.push(main.createLeftMenu(d));
	}
	html.push("</div>");
	$j("#"+id).replaceWith(html.join(""));
}
main.createLeftMenu=function(data){
	if(data.isGroup&&data.children.length>0){
		var html=[];
	html.push("<h3>");
	main.pushA(html,data);
	html.push("</h3>");
	html.push("<div>");
	html.push(main.createNode(data.children));
	html.push("</div>");
	return html.join("");
	}else{
		return "";
	}
}
main.createNode=function(children){
	var html=[];
	html.push("<ul>");
	if(children){
		for(var i in children){
			html.push("<li" + (children[i].isGroup?" class='group'":"") + ">");
//			html.push('<a href="'+$j("body").attr("contextpath")+children[i].entryURL+'">'+children[i].name+'</a>');
			main.pushA(html,children[i]);
			if(children[i].isGroup){
				html.push(main.createNode(children[i].children));
			}
			html.push("</li>");
		}
	}
	html.push("</ul>");
	return html.join("");
}
main.leftMenuPost=function(targetId){
	$j("#"+targetId).accordion({fillSpace: true});
		var n="javascript:void(0);";
		$j("#left-menu li > a[href!='"+n+"']").click(function(evt){
			evt.preventDefault();
			openFrame(this);		
			return false;
		});
		$j("#left-menu li ul").hide();
		$j("#left-menu li > a[href='"+n+"']").click(function(evt){
			evt.preventDefault();
			$j(this).parent().find("ul").toggle();
			if($j(this).parent().hasClass("expended")){
				$j(this).parent().removeClass("expended");
			}else{
				$j(this).parent().addClass("expended");
			}
			return false;
		});
}

main.createTopTree=function(id,data){
	var html = "";
	for(var d,i=0;d=data[i];i++){
		if(d.isGroup&&d.children.length>0){
			html += "<a tabindex=\"0\" class=\"fg-button fg-button-icon-right ui-widget ui-state-default\">";
			html += "<span class=\"ui-icon ui-icon-triangle-1-s\"></span>"+d.name+"</a>";
			html += "<div style=\"display: none;\">";
			html += main.createNode(d.children);
			html += "</div>";
		}
	}
	$j("#" + id).html(html);
}
main.topMenuPost=function(){
	$j('.fg-button').hover(
			function(){ $j(this).removeClass('ui-state-default').addClass('ui-state-focus'); },
			function(){ $j(this).removeClass('ui-state-focus').addClass('ui-state-default'); }
		);
	
	$j("a.fg-button").each(function(){
		$j(this).menu({ 
			content: $j(this).next().html(), // grab content from this page
			showSpeed: 400,
			flyOut: true
		});
	});
}
main.loadMenuTree=function(param){
	loxia.asyncXhrPost($j("body").attr("contextpath") + "/menulist.json",param,{
		success:function (data) {
			$j("#left-menu").show();
			main.createLeftTree("left-menu",data);
			main.leftMenuPost("left-menu");
			main.createTopTree("top-menu",data);
			main.topMenuPost();
		   }
		});
}

$j(document).ready(function (){
	$j("#main-container").css({"height":(loxia.getViewport().height - $j("#top").height() - $j("#bottom").height()) + "px"});

	var mainLayout = $j("#main-container").layout({		
		south__spacing_open: 0,
		south__spacing_closed: 8,
		south__closable: false,
		south__resizable: false,
		west__size: 250,
		west__spacing_open: 8,
		west__spacing_closed: 22,
		west__togglerLength_closed: 140,
		//west__initClosed: true,
		west__togglerAlign_closed: "top",
		west__togglerContent_closed: "M<BR>E<BR>N<BR>U",
		west__togglerTip_closed: "Open & Pin Menu",
		west__sliderTip: "Slide Open Menu",
		west__slideTrigger_open: "mouseover"
	});	
	$j(".frame-control button[icons='ui-icon-refresh']").live("click",function(evt){
		evt.preventDefault();
		var frameDoc, currFrame = $j(this).parents(".ui-tabs-panel").find("iframe")[0];
		if(currFrame.contentDocument)
			frameDoc = currFrame.contentDocument;
		else if(currFrame.contentWindow)
			frameDoc = currFrame.contentWindow;
		else
			frameDoc = currFrame.document;
		if(frameDoc)
			frameDoc.location.reload(true);
		return false;
	});
	
	$j("#top").load(loxia.getTimeUrl($j("body").attr("contextpath") + "/top.do"), function(){
		loxia.initContext($j("#top"));
		loxia.initContext($j("#dialog-chgrole"));
		
		main.loadMenuTree({});
		mainLayout.open("west");
		mainLayout.close("west");
	});
//	$j(window).wresize(function(){ mainLayout.resizeAll();}); 
	 setInterval(extendsSessionTime, 3600000);
});
function extendsSessionTime(){
	loxia.asyncXhrPost(
			"/wms/json/getNull.do",
			{},
			{}
	);
}
