<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div style= "width: 100%;" >
	<div>
		<div class= "tabbable" >
			<ul id= "myTab_kd" class= "nav nav-tabs padding-8 tab-size-bigger tab-color-blue background-blue" style= "display: none;" >
				<li class= "active" ><a data-toggle= "tab" href= "#freight_ec" >运费</a></li>
				<li><a data-toggle= "tab" href= "#insuranceFee_ec" >保价费</a></li>
				<li><a data-toggle= "tab" href= "#specialServiceFee_ec" >特殊服务费</a></li>
				<li><a data-toggle= "tab" href= "#origDestList_ec" >始发地目的地参数列表</a></li>
			</ul>
			<ul id= "myTab_wl" class= "nav nav-tabs padding-8 tab-color-blue background-blue" style= "display: none;">
				<li class= "active"><a data-toggle= "tab" href= "#yf_tab" >运费</a></li>
				<li><a data-toggle= "tab" href= "#bjf_tab" >保价费</a></li>
				<li><a data-toggle= "tab" href= "#tsfwf_tab" >特殊服务费</a></li>
				<c:if test= "${expressContract.contract_type != 2 }" >
					<li><a data-toggle= "tab" href= "#glf_tab" >管理费</a></li>
				</c:if>
				<li><a data-toggle= "tab" href= "#wlkdgf_tab" >物流使用快递管理</a></li>
				<li><a data-toggle= "tab" href= "#address_tab" >始发地目的地参数列表</a></li>
			</ul>
			<div id= "kd_moudle_li" class= "tab-content col-xs-12" style= "display: none;" >
				<!-- 运费 -->
				<div id="freight_ec" class="tab-pane in active" align="center">
					<%@ include file="/lmis/express_contract/freight_ec.jsp" %>
				</div>
				
				<!-- 保价费 -->
				<div id="insuranceFee_ec" class="tab-pane" align="center">
					<%@ include file="/lmis/express_contract/insuranceFee_ec.jsp" %>
				</div>
				
				<!-- 特殊服务费 -->
				<div id="specialServiceFee_ec" class="tab-pane" align="center">
					<%@ include file="/lmis/express_contract/specialServiceFee_ec.jsp" %>
				</div>
				
				<!-- 始发地目的地参数列表 -->
				<div id="origDestList_ec" class="tab-pane" align="center">
					<%@ include file="/lmis/express_contract/origDestList_ec.jsp" %>
				</div>
			</div>
            <div id= "wl_moudle_li" class= "tab-content" align= "center" style= "display: none;" >
				<!-- 运费 -->
			  	<div id="yf_tab" class="tab-pane in active">
			    	<div class="moudle_dashed_border_show">
				 		<%@ include file="/lmis/trans_moudle/yf_moudle.jsp"%>
				  	</div>
                </div>
                <!-- 保价费 -->
                <div id="bjf_tab" class="tab-pane" align="center">
                	<%@ include file="/lmis/trans_moudle/bjf_moudle.jsp"%>
                </div>
                <!-- 特殊服务费 -->
	  			<div id="tsfwf_tab" class="tab-pane" align="center">
		  			<%@ include file="/lmis/trans_moudle/tsfwf_moudle.jsp"%>
		  		</div>
			  	<!-- 管理费 -->
                <div id="glf_tab" class="tab-pane" align="center">
                    <%@ include file="/lmis/trans_moudle/glf_moudle.jsp"%>
                </div>
                <!-- 起始地目的地参数 -->
	  			<div id="address_tab" class="tab-pane">
                	<%@ include file="/lmis/trans_moudle/address_moudle.jsp"%>
  				</div>
			  	<!-- 物流是使用快递管理 -->
                <div id="wlkdgf_tab" class="tab-pane" align="center">
                    <%@ include file="/lmis/trans_moudle/wlkdgl_moudle.jsp"%>
               	</div>
			</div>						
		</div>
	</div>
</div>
