<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
		<%@ taglib prefix="fns" uri="http://java.sun.com/jsp/jstl/functions" %>
		
		<script type="text/javascript">
			$(function(){
				hideYScroll("#follow_up_content", $("#follow_up_title").width());
				$(window).resize(function(){
					hideYScroll("#follow_up_content", $("#follow_up_title").width());
			    });
				// 内容宽度，隐藏scroll
			    $("#follow_up_title").resize(function(){
			    	hideYScroll("#follow_up_content", $("#follow_up_title").width());
			    }) 
			})
		</script>
	</head>
	<body>
		<div class= "modal-dialog modal-lg" style="width: 1200px;" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true" >×</span></button>
					<h4 id= "formLabel" class= "modal-title" >跟进</h4>
				</div>
				<div class="modal-body">
					<div class="row form-group">
	            		<div class="col-md-1" align="right">
	            			<label class="blue" for="processInfo">跟进记录&nbsp;:</label>
            			</div>
						<div class="col-xs-10">
							<div class="table-border">
								<div class="table-title" id="follow_up_title">	
									<table class="table table-bordered no-margin table-fixed">
								   		<thead>
									  		<tr>
									  			<th class="text-center table-text col-lg" title="跟进时间">跟进时间</th>
									  			<th class="text-center table-text col-lg" title="所在部门">所在部门</th>
									  			<th class="text-center table-text col-lg" title="所在组别">所在组别</th>
									  			<th class="text-center table-text col-lg" title="跟进人">跟进人</th>
									  			<th class="text-center table-text col-lg" title="跟进记录">跟进记录</th>
									  		</tr>
									  	</thead>
									</table>
								</div>
								<div class="table-content-parent">
									<div class="table-content table-content-fix" id="follow_up_content" onscroll="syntable($('#follow_up_title'),$('#follow_up_content'))">
										<table class="table table-striped table-bordered table-hover table-fixed no-margin" data-page-size="5">
											<tbody>
												<c:forEach  items="${list}" var="power" varStatus='status'>
											  		<tr>
											  			<td class="text-center table-text col-lg" title="${power.create_time }" ><fmt:formatDate value="${power.create_time}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
											  			<td class="text-center table-text col-lg" title="${power.create_by_department_display }">${power.create_by_department_display }</td>
											  			<td class="text-center table-text col-lg" title="${power.create_by_group_display }">${power.create_by_group_display }</td>
											  			<td class="text-center table-text col-lg" title="${power.create_by }">${power.create_by }</td>
											  			<td class="text-center table-text col-lg" title="${power.follow_up_record }">
											  				${power.follow_up_record }
											  			</td>
											  		</tr>
										  		</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="row form-group">
	            		<div class="col-md-1" align="right">
	            			<label class="blue" for="processInfo">跟进说明&nbsp;:</label>
            			</div>
	            		<div class="col-xs-10">
							<textarea class="form-control" id="followuprecord" name="followuprecord" rows="10"></textarea>
						</div>
		            </div>
				</div>
				
				<div class="modal-footer">
	            	<button type="button" class="btn btn-sm btn-white btn-default btn-bold btn-round btn-width" onclick="addFollowUpRecord();" >
						<i class="ace-icon fa fa-pencil-square-o yellow">
							确认跟进
						</i>
					</button>
	            </div>
			</div>
		</div>
	</body>
</html>
