(function($){
	openFrame = function(menuItem) {		
		if($(menuItem).attr("href") != "#"){
			var frameId="frm-" + (new Date()).getTime().toString(), frameName = $(menuItem).text();
			
			jumbo.openFrame(frameId,frameName,menuItem);		
			return frameId;
		}
		return null;
	};
	
	$(document).ready(function (){
		//init default frame container
		var $tabs = $("#frame-container");		
		$tabs.tabs({
			tabTemplate: "<li><a href='#{href}'>#{label}</a> <span class='ui-icon ui-icon-close'>关闭</span></li>",
			add: function( event, ui ) {
				$j("#rarDownload").addClass("hidden");
				var frameId = $(ui.panel).attr("id");		
				var url = $("a[frameId='" + frameId + "']").attr("href");
				$(ui.panel).append("<div class='frame-control'><button type='button' loxiaType='button' icons='ui-icon-refresh' showText='false'>刷新</button>"
						+ "<button type='button' loxiaType='button' icons='ui-icon-print' showText='false'>打印</button>" 
						+ "<button type='button' loxiaType='button' icons='ui-icon-comment' showText='false'>显示信息</button></div>" + 
						"<iframe src='" + url + "' scrolling=auto frameborder='0'></iframe>");	
				loxia.initContext($(ui.panel));
			}
		});
		
		$( "#frame-container span.ui-icon-close" ).live( "click", function() {
			var $titleContainer = $tabs.find("> ul.ui-tabs-nav");
			var index = $( "li", $titleContainer ).index( $( this ).parent() );
			$tabs.tabs( "remove", index );
		});
		
		$( "#frame-container button[icons='ui-icon-print']" ).live( "click", function() {
			var $frame = $(this).parent("div").next("iframe");
			$frame[0].contentWindow.focus();
			$frame[0].contentWindow.print();
		});
		
		$( "#frame-container button[icons='ui-icon-comment']" ).live( "click", function() {
			if($("#msg").hasClass("shown"))
				jumbo.showMsg();
			else
				jumbo.showMsg("",0);
		});
	});
})(jQuery);

