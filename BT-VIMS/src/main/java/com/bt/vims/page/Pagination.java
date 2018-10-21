package com.bt.vims.page;

/*
 * 通用分页封装类
 */
public class Pagination {
	
	public static int getLastPageSize(int pageIndex){
		if(pageIndex-1 < 1){
			return 1;
		}else{
			return pageIndex-1;
		}
	}
	public static int getNextPageSize(int pageIndex,int totleSize,int pageSize){
		if(pageIndex>totleSize / pageSize){
			return totleSize / pageSize;
		}else{
			return pageIndex+1;
		}	
	}
	// 计算出总共多少页
	public static int getTotlePage(int totleSize,int totlePage,int pageSize) {
		if(totleSize==0){
			totlePage=1;
		}else{
			totlePage = totleSize / pageSize;
			if (totleSize % pageSize != 0) {
				totlePage++;
	
			}
		}
		return totlePage;
	}

	/** 
	* @Title: getPageView 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param pageIndex	每页大小
	* @param @param totleSize	总共多少条
	* @param @param totlePage	总共多少页
	* @param @param pageIndex	当前第几页
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	public static final String getPageView(int pageSize,int totleSize,int totlePage,int pageIndex) {
		StringBuffer sb = new StringBuffer();
		StringBuffer result = new StringBuffer();
		sb.append("<ul class='pagination pull-right no-margin'>");
        if(pageIndex<=1){
	    	sb.append("<li class='prev disabled'>");
	    	sb.append("	<a href='javaScript:void(0);'>");
			sb.append("	<i class='icon-double-angle-left'></i>");
			sb.append("	</a>");
			sb.append("</li>");
        }else{
	    	sb.append("<li>");
	    	sb.append("	<a href='javaScript:void(0);' onClick='setData(\"last\")' >");
			sb.append("	<i class='icon-double-angle-left'></i>");
			sb.append("	</a>");
			sb.append("</li>");
        }
		if(getTotlePage(totleSize, totlePage, pageSize)<=10){
			for(int i=1;i<=totlePage;i++){		
				if(i==pageIndex){
					sb.append("<li id='page_"+i+"' class='active' >");
					sb.append("	<a href='javaScript:;' onclick='jumpToPage(\""+i+"\")'>"+i+"</a>");
					sb.append("</li>");
				}else{
					sb.append("<li id='page_"+i+"' >");	
					sb.append("	<a href='javaScript:;' onclick='jumpToPage(\""+i+"\")'>"+i+"</a>");
					sb.append("</li>");
				}	
			}
		}
		if(getTotlePage(totleSize, totlePage, pageSize)>=11){
			 if(pageIndex>=4){
				 sb.append("<li id='page_0'>");
				 sb.append("	<a href='javaScript:;' onclick='jumpToPage(1)'>首页</a>");
				 sb.append("</li>");					 
			 }
			 if(1==pageIndex){
				 sb.append("<li class='active' >");
				 sb.append("<a href='javaScript:;' onclick='jumpToPage(\"1\")'>1</a>");
				 sb.append("</li>");
			 }else{
				 sb.append("<li>");	
				 sb.append("<a href='javaScript:;' onclick='jumpToPage(\"1\")'>1</a>");
				 sb.append("</li>");
			 }
			 if(2==pageIndex){
				 sb.append("<li class='active' >");
				 sb.append("<a href='javaScript:;' onclick='jumpToPage(\"2\")'>2</a>");
				 sb.append("</li>");
			 }else{
				 sb.append("<li>");	
				 sb.append("<a href='javaScript:;' onclick='jumpToPage(\"2\")'>2</a>");
				 sb.append("</li>");
			 }
			 if(3==pageIndex){
				 sb.append("<li class='active' >");
				 sb.append("<a href='javaScript:;' onclick='jumpToPage(\"3\")'>3</a>");
				 sb.append("</li>");	
			 }else{
				 sb.append("<li>");	
				 sb.append("<a href='javaScript:;' onclick='jumpToPage(\"3\")'>3</a>");
				 sb.append("</li>");	
			 }
			 if(pageIndex>3&&pageIndex<totlePage-3){
				 sb.append("<li>");
				 sb.append("<a href='#'>....</a>");
				 sb.append("</li>");						
				 sb.append("<li id='page_"+pageIndex+"' class='active'>");
				 sb.append("<a href='javaScript:;' onclick='jumpToPage("+(pageIndex)+")'>"+(pageIndex)+"</a>");
				 sb.append("</li>");
				 sb.append("<li>");
				 sb.append("<a href='#'>....</a>");	
				 sb.append("</li>");	
			 }else{
				 sb.append("<li>");
				 sb.append("<a href='#'>....</a>");
				 sb.append("</li>");						
			 }
			 if((totlePage-2)==pageIndex){
				 sb.append("<li  class='active' >");
				 sb.append("	<a href='javaScript:;' onclick='jumpToPage("+(totlePage-2)+")'>"+(getTotlePage(totleSize, totlePage, pageSize)-2)+"</a>");
				 sb.append("</li>");
			 }else{
				 sb.append("<li>");	
				 sb.append("	<a href='javaScript:;' onclick='jumpToPage("+(totlePage-2)+")'>"+(getTotlePage(totleSize, totlePage, pageSize)-2)+"</a>");
				 sb.append("</li>");
			 }
			 if((totlePage-1)==pageIndex){
				 sb.append("<li  class='active' >");
				 sb.append("<a href='javaScript:;' onclick='jumpToPage("+(totlePage-1)+")'>"+(getTotlePage(totleSize, totlePage, pageSize)-1)+"</a>");
				 sb.append("</li>");
			 }else{
				 sb.append("<li>");
				 sb.append("<a href='javaScript:;' onclick='jumpToPage("+(totlePage-1)+")'>"+(getTotlePage(totleSize, totlePage, pageSize)-1)+"</a>");
				 sb.append("</li>");
			 }
			 if((totlePage)==pageIndex){
				 sb.append("<li  class='active' >");
				 sb.append("<a href='javaScript:;' onclick='jumpToPage("+totlePage+")'>"+getTotlePage(totleSize, totlePage, pageSize)+"</a>");
				 sb.append("</li>");	
			 }else{
				 sb.append("<li>");	
				 sb.append("<a href='javaScript:;' onclick='jumpToPage("+totlePage+")'>"+getTotlePage(totleSize, totlePage, pageSize)+"</a>");
				 sb.append("</li>");	
			 }
		}
		if(pageIndex>=getTotlePage(totleSize, totlePage, pageSize)){
			sb.append("<li class='prev disabled'>");
			sb.append("<a href='javaScript:void(0);'>");
			sb.append("<i class='icon-double-angle-right'></i>");
			sb.append("</a>");
			sb.append("</li>");
		}else{
			sb.append("<li class='next'>");	
			sb.append("<a href='javaScript:void(0);' onclick='setData(\"next\")'>");
			sb.append("<i class='icon-double-angle-right'></i>");
			sb.append("</a>");	
			sb.append("</li>");		
		}
		sb.append("<li>");
//		sb.append("<select id='jumIndex' style='height:32px;' onchange='KeyDown()'>");
//		for(int i=1;i<=getTotlePage(totleSize, totlePage, pageSize);i++){
//			if(pageIndex==i){
//				sb.append("<option selected='selected'>"+i+"</option>");
//			}else{
//				sb.append("<option>"+i+"</option>");
//			}
//		}
//		sb.append("</select>");
//		sb.append("<select id='jumpPageSize' onchange='KeyDown()' style='height:32px;'>");
//		if(pageSize==10){
//			sb.append("<option selected=selected>10</option>");
//		}else{
//			sb.append("<option>10</option>");
//		}
//		if(pageSize==20){
//			sb.append("<option selected=selected>20</option>");
//		}else{
//			sb.append("<option>20</option>");
//		}
//		if(pageSize==30){
//			sb.append("<option selected=selected>30</option>");
//		}else{
//			sb.append("<option>30</option>");
//		}
//		if(pageSize==40){
//			sb.append("<option selected=selected>40</option>");
//		}else{
//			sb.append("<option>40</option>");
//		}
//		if(pageSize==50){
//			sb.append("<option selected=selected>50</option>");
//		}else{
//			sb.append("<option>50</option>");
//		}		
//		sb.append("</select>");
		sb.append("</li>");	
		sb.append("</ul>");
		sb.append("<input type='hidden' value='"+pageIndex+"' id='pageIndex'/>");
		sb.append("<input type='hidden' value='' id='startRow'/>");
		sb.append("<input type='hidden' value='' id='endRow'/>");
		sb.append("<input type='hidden' value='' id='pageIndex'/>");
		sb.append("<input type='hidden' value='"+pageSize+"' id='pageSize'/>");
		sb=getScript(sb, pageIndex, pageSize, totleSize);
		sb.append("");
		result.append("<div><div style='float:right'>");
		result.append(sb);
		result.append("</div><div style='float:right;line-height:30px;'><span>共"+totleSize+"条记录"+getTotlePage(totleSize, totlePage, pageSize)+"页</span>&nbsp;&nbsp;</div></div>");
		return result.toString();

	}
	public static StringBuffer getScript(StringBuffer sb,int pageIndex,int pageSize,int totleSize){
		sb.append("<script type='text/javascript'>");
		sb.append("function setData(type){");
		sb.append("if(type=='last'){");
		if(pageIndex-1 >=0){
			sb.append("$(\"#startRow\").val("+(pageIndex-2)*pageSize+");");
			sb.append("$(\"#endRow\").val("+pageSize+");");
			sb.append("$(\"#pageIndex\").val("+getLastPageSize(pageIndex)+");");
			sb.append("$(\"#pageSize\").val("+pageSize+");");
		}else{
			sb.append("$(\"#startRow\").val(0);");
			sb.append("$(\"#endRow\").val("+pageSize+");");
			sb.append("$(\"#pageIndex\").val("+getLastPageSize(pageIndex)+");");
			sb.append("$(\"#pageSize\").val("+pageSize+");");
		}
		sb.append("}");
		sb.append("if(type=='next'){");
		sb.append("$(\"#startRow\").val("+(pageIndex)*pageSize+");");
		sb.append("$(\"#endRow\").val("+pageSize+");");
		sb.append("$(\"#pageIndex\").val("+getNextPageSize(pageIndex, totleSize, pageSize)+");");
		sb.append("$(\"#pageSize\").val("+pageSize+");");
		sb.append("}");
		sb.append("pageJump();");
		sb.append("}");
		

		sb.append("function jumpToPage(index){");
		sb.append("$(\"#startRow\").val(parseInt(index-1)*"+pageSize+");");
		sb.append("$(\"#pageIndex\").val(index);");
		sb.append("$(\"#pageSize\").val("+pageSize+");");
		sb.append("pageJump();");
		sb.append("}");
		
		sb.append("function KeyDown(){");
		sb.append("var toaPageSize=parseInt("+totleSize+")/parseInt($('#jumpPageSize').val());");
		sb.append("if(parseInt("+totleSize+")%parseInt($('#jumpPageSize').val())!=0){");
		sb.append("toaPageSize=toaPageSize+1;");
		sb.append("}");
		sb.append("if(parseInt($('#jumIndex').val())>toaPageSize){");
		sb.append("$(\"#startRow\").val(0);");
		sb.append("$('#pageIndex').val(1);");
		
		sb.append("}else{");
		//sb.append("$(\"#startRow\").val(parseInt($('#jumIndex').val()-1)*parseInt($('#jumpPageSize').val()));");
		sb.append("$(\"#startRow\").val(parseInt($('#jumIndex').val()-1)*"+pageSize+");");
		sb.append("$('#pageIndex').val($('#jumIndex').val());");
		sb.append("}");
		sb.append("$(\"#pageSize\").val($('#jumpPageSize').val());");
		sb.append("pageJump();");
		sb.append("}");
		sb.append("</script>");
		return sb;
	}
	
}