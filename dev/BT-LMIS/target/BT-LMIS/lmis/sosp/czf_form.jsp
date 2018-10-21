<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<script type="text/javascript">
	$(document).ready(function() {
		show1($("#id").val());
		show2($("#id").val());
		show3($("#id").val());
		show4($("#id").val());
		show5($("#id").val());
		show6($("#id").val());
		show7($("#id").val());
		show36($("#id").val());
		show37($("#id").val());
	});
	//操作费－[计算公式]展示
	function showCZF() {
		var divId = $("#CZF").val();
		var divObj = $("div[id^='CZF']");
		for (i = 0; i < divObj.length; i++) {
			if (i == divId) {
				$("#CZF" + i).css("display", "block");
			} else {
				$("#CZF" + i).css("display", "none");
			}
		}
	};
	//操作费固定费用[阶梯]展示
	function showGD() {
		var divId = $("#GD").val();
		var divObj2 = $("div[id^='GD']");
		for (i = 1; i <= divObj2.length; i++) {
			if (i == divId) {
				$("#GD" + i).css("display", "block");
			} else {
				$("#GD" + i).css("display", "none");
			}
		}
	};
	//入库操作费
	function showRKCZF() {
		var divId = $("#RKCZF").val();
		var divObj2 = $("div[id^='RKCZF']");
		for (i = 1; i <= divObj2.length; i++) {
			if (i == divId) {
				$("#RKCZF" + i).css("display", "block");
			} else {
				$("#RKCZF" + i).css("display", "none");
			}
		}
	};
	//B2C出库操作费
	function showB2CCKCZF() {
		var divId = $("#B2CCKCZF").val();
		var divObj2 = $("div[id^='B2CCKCZF']");
		for (i = 1; i <= divObj2.length; i++) {
			if (i == divId) {
				$("#B2CCKCZF" + i).css("display", "block");
			} else {
				$("#B2CCKCZF" + i).css("display", "none");
			}
		}
	};
	//B2C出库操作费[阶梯]展示
	function showB2CCKCZFJT() {
		var divId = $("#B2CCKCZFJT").val();
		var divObj2 = $("div[id^='B2CCKCZFJT']");
		for (i = 1; i <= divObj2.length; i++) {
			if (i == divId) {
				$("#B2CCKCZFJT" + i).css("display", "block");
			} else {
				$("#B2CCKCZFJT" + i).css("display", "none");
			}
		}
	};
	function showB2CCKCZFJTs() {
		var divId = $("#B2CCKCZFJTs").val();
		var divObj2 = $("div[id^='B2CCKCZFJTs']");
		for (i = 1; i <= divObj2.length; i++) {
			if (i == divId) {
				$("#B2CCKCZFJTs" + i).css("display", "block");
			} else {
				$("#B2CCKCZFJTs" + i).css("display", "none");
			}
		}
	};

	//B2C是否转B2B
	function showIFB2B() {
		var divId = $("#IFB2B").val();
		var divObj2 = $("div[id^='IFB2B']");
		for (i = 1; i <= divObj2.length; i++) {
			if (i == divId) {
				$("#IFB2B" + i).css("display", "block");
			} else {
				$("#IFB2B" + i).css("display", "none");
			}
		}
	};
	//B2C出库操作费[阶梯]展示
	function showB2CADJTCK() {
		var divId = $("#B2CADJTCK").val();
		var divObj2 = $("div[id^='B2CADJTCK']");
		for (i = 1; i <= divObj2.length; i++) {
			if (i == divId) {
				$("#B2CADJTCK" + i).css("display", "block");
			} else {
				$("#B2CADJTCK" + i).css("display", "none");
			}
		}
	};
	//B2B出库操作费
	function showB2BCKCZF() {
		var divId = $("#B2BCKCZF").val();
		var divObj2 = $("div[id^='B2BCKCZF']");
		for (i = 1; i <= divObj2.length; i++) {
			if (i == divId) {
				$("#B2BCKCZF" + i).css("display", "block");
			} else {
				$("#B2BCKCZF" + i).css("display", "none");
			}
		}
	};
	//B2B出库操作费
	function showB2BTCCZF() {
		var divId = $("#B2BTCCZF").val();
		var divObj2 = $("div[id^='B2BTCCZF']");
		for (i = 1; i <= divObj2.length; i++) {
			if (i == divId) {
				$("#B2BTCCZF" + i).css("display", "block");
			} else {
				$("#B2BTCCZF" + i).css("display", "none");
			}
		}
	};
	//B2C退换货入库费
	function showB2CTHHRKF() {
		var divId = $("#B2CTHHRKF").val();
		var divObj2 = $("div[id^='B2CTHHRKF']");
		for (i = 1; i <= divObj2.length; i++) {
			if (i == divId) {
				$("#B2CTHHRKF" + i).css("display", "block");
			} else {
				$("#B2CTHHRKF" + i).css("display", "none");
			}
		}
	};
	//B2C退换货入库费[阶梯]展示
	function showB2CTHHRKFJT() {
		var divId = $("#B2CTHHRKFJT").val();
		var divObj2 = $("div[id^='B2CTHHRKFJT']");
		for (i = 1; i <= divObj2.length; i++) {
			if (i == divId) {
				$("#B2CTHHRKFJT" + i).css("display", "block");
			} else {
				$("#B2CTHHRKFJT" + i).css("display", "none");
			}
		}
	};
	//操作费保存按钮
	function saveOperation() {
		var id = $("#id").val(); //合同ID
		var params = $("#tosrForm").serialize();
		$
				.ajax({
					url : "${root}control/operatingCostController/saveMain.do?id="
							+ id,
					type : "POST",
					data : params,
					dataType : "json",
					success : function(data) {
						alert(data.info);
						show1($("#id").val());
						show2($("#id").val());
					},
					error : function(XMLHttpRequest) {
						alert("服务器异常Error: " + XMLHttpRequest.responseText);
					}
				});
	}
	function show1(id) {
		var url = root+ "/control/operatingCostController/get_show_tab.do?tbid=" + id;
		var htm_td = "";
		$.ajax({ype : "POST",url : url,data : "",dataType : "json",success : function(data) {var text = "<table class='with-border' border='1'><tr class='title'><td>序号</td><td>件数区间</td><td>单价</td><td>操作</td></tr>";
						if (data.status == 'y') {for (i = 0; i < data.list.length; i++) {
								htm_td = htm_td + "<tr><td>" + (i + 1)
										+ "</td><td>"
										+ data.list[i].tosf_section
										+ "</td><td>" + data.list[i].tosf_price
										+ "元/件</td><td><a onclick='delete1("
										+ data.list[i].tosf_id
										+ ");'>删除</a></td></tr>";
							}
							$("table1").html(text + htm_td + "</table>");
						} else {$("table1").html(text + "</table>");}}});};
	function delete1(id) {
		if(!confirm("是否删除以下所选数据?")){
		  	return;
		  	
		}
		$.ajax({
					type : "POST",
					url : root
							+ "/control/operatingCostController/delete1.do?id="
							+ id,
					data : null,
					dataType : "json",
					success : function(data) {
						if (data.status == 'y') {
							alert(data.info);
							show1($("#id").val());
						} else {
							alert(data.info);
						}
					}
				});
	};

	//退换货入库费按件数计算 有阶梯保存
	function saveB2CTHHRKFJT() {
		var id = $("#id").val(); //合同ID
		var params = $("#tosrForm").serialize();
		$
				.ajax({
					url : "${root}control/operatingCostController/saveB2CTHHRKFJT.do?cbid="
							+ id,
					type : "POST",
					data : params,
					dataType : "json",
					success : function(data) {
						alert(data.info);
						show2($("#id").val());
					},
					error : function(XMLHttpRequest) {
						alert("服务器异常Error: " + XMLHttpRequest.responseText);
					}
				});
	}

	function show2(id) {
		var url = root
				+ "/control/operatingCostController/get_show_tab2.do?tbid="
				+ id;
		var htm_td = "";
		$
				.ajax({
					type : "POST",
					url : url,
					data : "",
					dataType : "json",
					success : function(data) {
						var text = "<table class='with-border' border='1'><tr class='title'><td>序号</td><td>百分比区间</td><td>单价</td><td>操作</td></tr>";
						if (data.status == 'y') {
							for (i = 0; i < data.list.length; i++) {
								htm_td = htm_td + "<tr><td>" + (i + 1)
										+ "</td><td>"
										+ data.list[i].btcrti_interval
										+ "</td><td>"
										+ data.list[i].btcrt_price
										+ "元/件</td><td><a onclick='delete2("
										+ data.list[i].id
										+ ");'>删除</a></td></tr>";
							}
							$("table2").html(text + htm_td + "</table>");
						} else {
							$("table2").html(text + "</table>");
						}
					}
				});
	};
	function delete2(id) {
		if(!confirm("是否删除以下所选数据?")){
		  	return;
		  	
		}
		$
				.ajax({
					type : "POST",
					url : root
							+ "/control/operatingCostController/delete2.do?id="
							+ id,
					data : null,
					dataType : "json",
					success : function(data) {
						if (data.status == 'y') {
							alert(data.info);
							show2($("#id").val());
						} else {
							alert(data.info);
						}
					}
				});
	};

	function saveB2CCKCZF2() {
		var id = $("#id").val(); //合同ID
		var params = $("#tosrForm").serialize();
		$
				.ajax({
					url : "${root}control/operatingCostController/saveB2CCKCZF2.do?cbid="
							+ id,
					type : "POST",
					data : params,
					dataType : "json",
					success : function(data) {
						alert(data.info);
						show3($("#id").val());
					},
					error : function(XMLHttpRequest) {
						alert("服务器异常Error: " + XMLHttpRequest.responseText);
					}
				});
	}
	function show3(id) {
		var url = root
				+ "/control/operatingCostController/get_show_tab3.do?tbid="
				+ id;
		var htm_td = "";
		$
				.ajax({
					type : "POST",
					url : url,
					data : "",
					dataType : "json",
					success : function(data) {
						var text = "<table class='with-border' border='1'><tr class='title'><td>序号</td><td>区间</td><td>收费</td><td>操作</td></tr>";
						if (data.status == 'y') {
							for (i = 0; i < data.list.length; i++) {
								htm_td = htm_td + "<tr><td>" + (i + 1)
										+ "</td><td>"
										+ data.list[i].bnt_interval
										+ "</td><td>"
										+ data.list[i].bnt_discount
										+ "元/件</td><td><a onclick='delete3("
										+ data.list[i].id
										+ ");'>删除</a></td></tr>";
							}
							$("table3").html(text + htm_td + "</table>");
						} else {
							$("table3").html(text + "</table>");
						}
					}
				});
	};
	function delete3(id) {
		if(!confirm("是否删除以下所选数据?")){
		  	return;
		  	
		}
		$
				.ajax({
					type : "POST",
					url : root
							+ "/control/operatingCostController/delete3.do?id="
							+ id,
					data : null,
					dataType : "json",
					success : function(data) {
						if (data.status == 'y') {
							alert(data.info);
							show3($("#id").val());
						} else {
							alert(data.info);
						}
					}
				});
	};

	function saveB2CCKCZF2s() {
		var id = $("#id").val(); //合同ID
		var params = $("#tosrForm").serialize();
		$
				.ajax({
					url : "${root}control/operatingCostController/saveB2CCKCZF2s.do?cbid="
							+ id,
					type : "POST",
					data : params,
					dataType : "json",
					success : function(data) {
						alert(data.info);
						show36($("#id").val());
					},
					error : function(XMLHttpRequest) {
						alert("服务器异常Error: " + XMLHttpRequest.responseText);
					}
				});
	}
	function show36(id) {
		var url = root
				+ "/control/operatingCostController/get_show_tab36.do?tbid="
				+ id;
		var htm_td = "";
		$
				.ajax({
					type : "POST",
					url : url,
					data : "",
					dataType : "json",
					success : function(data) {
						var text = "<table class='with-border' border='1'><tr class='title'><td>序号</td><td>区间</td><td>收费</td><td>操作</td></tr>";
						if (data.status == 'y') {
							for (i = 0; i < data.list.length; i++) {
								htm_td = htm_td + "<tr><td>" + (i + 1)
										+ "</td><td>"
										+ data.list[i].bnts_interval
										+ "</td><td>"
										+ data.list[i].bnts_discount
										+ "元/单</td><td><a onclick='delete36("
										+ data.list[i].id
										+ ");'>删除</a></td></tr>";
							}
							$("table36").html(text + htm_td + "</table>");
						} else {
							$("table36").html(text + "</table>");
						}
					}
				});
	};
	function delete36(id) {
		$.ajax({
			type : "POST",
			url : root + "/control/operatingCostController/delete36.do?id="
					+ id,
			data : null,
			dataType : "json",
			success : function(data) {
				if (data.status == 'y') {
					alert(data.info);
					show36($("#id").val());
				} else {
					alert(data.info);
				}
			}
		});
	};

	function show37(id) {
		var url = root
				+ "/control/operatingCostController/get_show_tab37.do?tbid="
				+ id;
		var htm_td = "";
		$
				.ajax({
					type : "POST",
					url : url,
					data : "",
					dataType : "json",
					success : function(data) {
						var text = "<table class='with-border' border='1'><tr class='title'><td>序号</td><td>区间</td><td>收费</td><td>折扣</td><td>操作</td></tr>";
						if (data.status == 'y') {
							for (i = 0; i < data.list.length; i++) {
								htm_td = htm_td + "<tr><td>" + (i + 1)
										+ "</td><td>"
										+ data.list[i].obts_interval
										+ "</td><td>"+data.list[i].obts_price
										+ "</td><td>"
										+ data.list[i].obts_discount
										+ "元/单</td><td><a onclick='delete37("
										+ data.list[i].id
										+ ");'>删除</a></td></tr>";
							}
							$("table37").html(text + htm_td + "</table>");
						} else {
							$("table37").html(text + "</table>");
						}
					}
				});
	};
	function delete37(id) {
		if(!confirm("是否删除以下所选数据?")){
		  	return;
		  	
		}
		$.ajax({
			type : "POST",
			url : root + "/control/operatingCostController/delete37.do?id="
					+ id,
			data : null,
			dataType : "json",
			success : function(data) {
				if (data.status == 'y') {
					alert(data.info);
					show37($("#id").val());
				} else {
					alert(data.info);
				}
			}
		});
	};

	function saveB2CCKCZFJT3s() {
		var id = $("#id").val(); //合同ID
		var params = $("#tosrForm").serialize();
		$
				.ajax({
					url : "${root}control/operatingCostController/saveB2CCKCZFJT3s.do?cbid="
							+ id,
					type : "POST",
					data : params,
					dataType : "json",
					success : function(data) {
						alert(data.info);
						show37($("#id").val());
					},
					error : function(XMLHttpRequest) {
						alert("服务器异常Error: " + XMLHttpRequest.responseText);
					}
				});
	};
	function saveB2CCKCZFJT3() {
		var id = $("#id").val(); //合同ID
		var params = $("#tosrForm").serialize();
		$
				.ajax({
					url : "${root}control/operatingCostController/saveB2CCKCZFJT3.do?cbid="
							+ id,
					type : "POST",
					data : params,
					dataType : "json",
					success : function(data) {
						alert(data.info);
						show4($("#id").val());
					},
					error : function(XMLHttpRequest) {
						alert("服务器异常Error: " + XMLHttpRequest.responseText);
					}
				});
	};
	function show4(id) {
		var url = root
				+ "/control/operatingCostController/get_show_tab4.do?tbid="
				+ id;
		var htm_td = "";
		$
				.ajax({
					type : "POST",
					url : url,
					data : "",
					dataType : "json",
					success : function(data) {
						var text = "<table class='with-border' border='1'><tr class='title'><td>序号</td><td>区间</td><td>收费</td><td>折扣</td><td>操作</td></tr>";
						if (data.status == 'y') {
							for (i = 0; i < data.list.length; i++) {
								htm_td = htm_td + "<tr><td>" + (i + 1)
										+ "</td><td>"
										+ data.list[i].obt_interval
										+ "</td><td>" + data.list[i].obt_price
										+ "元/件</td><td>"
										+ data.list[i].obt_discount
										+ "</td><td><a onclick='delete4("
										+ data.list[i].id
										+ ");'>删除</a></td></tr>";
							}
							$("table4").html(text + htm_td + "</table>");
						} else {
							$("table4").html(text + "</table>");
						}
					}
				});
	};
	function delete4(id) {
		if(!confirm("是否删除以下所选数据?")){
		  	return;
		  	
		}
		$
				.ajax({
					type : "POST",
					url : root
							+ "/control/operatingCostController/delete4.do?id="
							+ id,
					data : null,
					dataType : "json",
					success : function(data) {
						if (data.status == 'y') {
							alert(data.info);
							show4($("#id").val());
						} else {
							alert(data.info);
						}
					}
				});
	};

	function saveTBBT() {
		var id = $("#id").val(); //合同ID
		var params = $("#tosrForm").serialize();
		$.ajax({
			url : "${root}control/operatingCostController/saveTBBT.do?cbid="
					+ id,
			type : "POST",
			data : params,
			dataType : "json",
			success : function(data) {
				alert(data.info);
				show5($("#id").val());
			},
			error : function(XMLHttpRequest) {
				alert("服务器异常Error: " + XMLHttpRequest.responseText);
			}
		});
	};
	function show5(id) {
		var url = root
				+ "/control/operatingCostController/get_show_tab5.do?tbid="
				+ id;
		var htm_td = "";
		$
				.ajax({
					type : "POST",
					url : url,
					data : "",
					dataType : "json",
					success : function(data) {
						var text = "<table class='with-border' border='1'><tr class='title'><td>序号</td><td>区间</td><td>每单单价</td><td>每件单价</td><td>操作</td></tr>";
						if (data.status == 'y') {
							for (i = 0; i < data.list.length; i++) {
								htm_td = htm_td + "<tr><td>" + (i + 1)
										+ "</td><td>"
										+ data.list[i].bbt_interval
										+ "</td><td>"
										+ data.list[i].bbt_bill_price
										+ "元</td><td>"
										+ data.list[i].bbt_piece_price
										+ "元</td><td><a onclick='delete5("
										+ data.list[i].id
										+ ");'>删除</a></td></tr>";
							}
							$("table5").html(text + htm_td + "</table>");
						} else {
							$("table5").html(text + "</table>");
						}
					}
				});
	};
	function delete5(id) {
		$
				.ajax({
					type : "POST",
					url : root
							+ "/control/operatingCostController/delete5.do?id="
							+ id,
					data : null,
					dataType : "json",
					success : function(data) {
						if (data.status == 'y') {
							alert(data.info);
							show5($("#id").val());
						} else {
							alert(data.info);
						}
					}
				});
	};

	function save6() {
		var id = $("#id").val(); //合同ID
		var params = $("#tosrForm").serialize();
		$.ajax({
			url : "${root}control/operatingCostController/save6.do?cbid=" + id,
			type : "POST",
			data : params,
			dataType : "json",
			success : function(data) {
				alert(data.info);
				show6($("#id").val());
			},
			error : function(XMLHttpRequest) {
				alert("服务器异常Error: " + XMLHttpRequest.responseText);
			}
		});
	};
	function show6(id) {
		var url = root + "/control/operatingCostController/show6.do?tbid=" + id;
		var htm_td = "";
		$
				.ajax({
					type : "POST",
					url : url,
					data : "",
					dataType : "json",
					success : function(data) {
						var text = "<table class='with-border' border='1'><tr class='title'><td>序号</td><td>区间</td><td>折扣</td><td>操作</td></tr>";
						if (data.status == 'y') {
							for (i = 0; i < data.list.length; i++) {
								htm_td = htm_td + "<tr><td>" + (i + 1)
										+ "</td><td>"
										+ data.list[i].btcbd_interval
										+ "</td><td>"
										+ data.list[i].btcbd_price
										+ "</td><td><a onclick='delete6("
										+ data.list[i].id
										+ ");'>删除</a></td></tr>";
							}
							$("table6").html(text + htm_td + "</table>");
						} else {
							$("table6").html(text + "</table>");
						}
					}
				});
	};
	function delete6(id) {
		$
				.ajax({
					type : "POST",
					url : root
							+ "/control/operatingCostController/delete6.do?id="
							+ id,
					data : null,
					dataType : "json",
					success : function(data) {
						if (data.status == 'y') {
							alert(data.info);
							show6($("#id").val());
						} else {
							alert(data.info);
						}
					}
				});
	};

	function save7() {
		var id = $("#id").val(); //合同ID
		var params = $("#tosrForm").serialize();
		$.ajax({
			url : "${root}control/operatingCostController/save7.do?cbid=" + id,
			type : "POST",
			data : params,
			dataType : "json",
			success : function(data) {
				alert(data.info);
				show7($("#id").val());
			},
			error : function(XMLHttpRequest) {
				alert("服务器异常Error: " + XMLHttpRequest.responseText);
			}
		});
	};
	function show7(id) {
		var url = root + "/control/operatingCostController/show7.do?tbid=" + id;
		var htm_td = "";
		$
				.ajax({
					type : "POST",
					url : url,
					data : "",
					dataType : "json",
					success : function(data) {
						var text = "<table class='with-border' border='1'><tr class='title'><td>序号</td><td>区间</td><td>折扣</td><td>操作</td></tr>";
						if (data.status == 'y') {
							for (i = 0; i < data.list.length; i++) {
								htm_td = htm_td + "<tr><td>" + (i + 1)
										+ "</td><td>"
										+ data.list[i].btcbd2_interval
										+ "</td><td>"
										+ data.list[i].btcbd2_price
										+ "</td><td><a onclick='delete7("
										+ data.list[i].id
										+ ");'>删除</a></td></tr>";
							}
							$("table7").html(text + htm_td + "</table>");
						} else {
							$("table7").html(text + "</table>");
						}
					}
				});
	};
	function delete7(id) {
		
		if(!confirm("是否删除以下所选数据?")){
		  	return;
		  	
		}
		$
				.ajax({
					type : "POST",
					url : root
							+ "/control/operatingCostController/delete7.do?id="
							+ id,
					data : null,
					dataType : "json",
					success : function(data) {
						if (data.status == 'y') {
							alert(data.info);
							show7($("#id").val());
						} else {
							alert(data.info);
						}
					}
				});
	};
</script>

<div id="czf_form">
	<form id="tosrForm" name="tosrForm">
		<div>
			结算方式 : <select style="width: 280px;" id="CZF" name="CZF"
				onchange="showCZF();">
				<option>请选择结算方式</option>
				<option ${osr.osr_setttle_method == 0? "selected= 'selected'": "" } value= "0" >固定费用结算</option>
				<option ${osr.osr_setttle_method == 1? "selected= 'selected'": "" } value= "1" >按销售额的百分比结算</option>
				<option ${osr.osr_setttle_method == 2? "selected= 'selected'": "" } value= "2" >按实际发生费用结算</option>
			</select>
		</div>
		<br>
		<div>
			折扣率：<input type="text" id="allzk" name="allzk" value="${osr.allzk}" />
		</div>
		<br>
		<!-- 固定费用结算 -->
		<div id="CZF0"
			<c:if test="${osr.osr_setttle_method==0}">style="display: block;"</c:if>
			<c:if test="${osr.osr_setttle_method!=0}">style="display: none;"</c:if>
			class="moudle_dashed_border">
			<div class="div_margin">
				<span style="color: red">操作费固定费用结算规则 : 操作费 = 固定费用 * 月份数</span>
			</div>
			<div class="form-group" style="margin: 10px;" align="center">
				<select onchange="showGD();" id="GD" name="GD" style="width: 30%;">
					<option value="0">请选择</option>
					<option value="1">无阶梯</option>
					<option value="2">超过部分阶梯</option>
				</select>
				<!-- 无阶梯 -->
				<div id="GD1" style="display: none;" align="center">
					<div class="div_margin">
						固定费用： <input type="text" id="osr_fixed_price"
							name="osr_fixed_price" value="${osr.osr_fixed_price}" /> <select
							id="osr_fixed_price_unit" name="osr_fixed_price_unit">
							<option value="0">元/月</option>
						</select>
					</div>
					<div class="div_margin">
						<a data-toggle="tab" href="#tab_add">
							<button class="btn btn-xs btn-yellow" onclick="saveOperation();">
								<i class="icon-hdd"></i>保存
							</button>
						</a>
					</div>
				</div>
				<!-- 超过部分阶梯 -->
				<div id="GD2" style="display: none;" align="center">
					<div class="div_margin">
						固定费用： <input type="text" id="osr_fixed_price1"
							name="osr_fixed_price1" value="${osr.osr_fixed_price}" /> <select
							id="osr_fixed_price_unit1" name="osr_fixed_price_unit1">
							<option value="0">元/月</option>
						</select>
					</div>
					<div class="div_margin">
						<span>---已维护的阶梯----</span>
					</div>
					<div>
						<table1></table1>
					</div>
					<div align="left" align="center">
						<div class="add_button">
							<a data-toggle="tab" href="#tab_add">
								<button class="btn btn-xs btn-yellow"
									onclick="changeShow('GdCGBF')">
									<i class="icon-hdd"></i>新增
								</button>
							</a>
						</div>
						<div id="GdCGBF" style="display: none;" align="center">
							<span style="color: red">超过部分阶梯价： 固定费用+（总件数-维护的条件件数）*单价</span><br />
							<span style="color: red">公式说明：阶梯区间，需要在下方设置</span><br />
							<div class="div_margin">
								<span>---阶梯设置---</span>
							</div>
							<div class="div_margin">
								条件 <select id="osr_mark1" name="osr_mark1">
									<option value=">">></option>
									<option value=">=">>=</option>
								</select> 入库件数: <input type="text" id="osr_mark4" name="osr_mark4" /> <select
									id="" name=""><option value="0">/件</option></select>
							</div>
							<div class="div_margin">
								组合方式: <select id="osr_mark2" name="osr_mark2">
									<option value="1">并且</option>
								</select>
							</div>
							<div class="div_margin">
								条件 <select id="osr_mark3" name="osr_mark3">
									<option value="<"><</option>
									<option value="<="><=</option>
								</select> 入库件数: <input type="text" id="osr_mark5" name="osr_mark5" /> <select
									id="" name=""><option value="0">/件</option></select>
							</div>
							<div class="div_margin">
								单价:<input type="text" id="tosf_price" name="tosf_price" />&nbsp;
								<select id="tosf_price_unit" name="tosf_price_unit"><option
										value="0">元/件</option></select>
							</div>
							<div class="div_margin">
								<a data-toggle="tab" href="#tab_add">
									<button class="btn btn-xs btn-yellow"
										onclick="saveOperation();">
										<i class="icon-hdd"></i>保存
									</button>
								</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- 按销售额的百分比结算 -->
		<div id="CZF1"
			<c:if test="${osr.osr_setttle_method==1}">style="display: block;"</c:if>
			<c:if test="${osr.osr_setttle_method!=1}">style="display: none;"</c:if>
			align="center" class="moudle_dashed_border">
			<div class="div_margin">
				<span style="color: red">操作费按销售额的百分比结算规则： (当月销售额 * 百分比) * 税点</span>
			</div>
			<div class="div_margin">
				百分比: <input type="text" id="osr_sales_percent"
					name="osr_sales_percent" value="${osr.osr_sales_percent}" /> <select><option
						value="0"></option></select>
			</div>
			<div class="div_margin">
				税点: <input type="text" id="osr_tax_point" name="osr_tax_point"
					value="${osr.osr_tax_point}" /> <select><option value="0"></option></select>
			</div>
			<div class="div_margin">
				<a data-toggle="tab" href="#tab_add">
					<button class="btn btn-xs btn-yellow" onclick="saveOperation();">
						<i class="icon-hdd"></i>保存
					</button>
				</a>
			</div>
		</div>
		<!-- 按实际发生费用结算 -->
		<div id="CZF2"
			<c:if test="${osr.osr_setttle_method==2}">style="display: block;"</c:if>
			<c:if test="${osr.osr_setttle_method!=2}">style="display: none;"</c:if>
			class="moudle_dashed_border">
			<div class="div_margin">
				<h1>操作费按实际发生费用收取</h1>
			</div>
			<hr>
			<div class="form-group" style="margin: 10px;" align="center">
				入库操作费: <select onchange="showRKCZF();" id="RKCZF" name="RKCZF"
					style="width: 30%;">
					<option value="0">请选择</option>
					<option ${osr.osr_ibop_fee == 1? "selected= 'selected'": "" } value= "1" >按SKU类型收取</option>
					<option ${osr.osr_ibop_fee == 2? "selected= 'selected'": "" } value= "2" >按入库数量收取</option>
				</select>
				<!-- 按SKU类型收取 -->
				<div id="RKCZF1"
					<c:if test="${osr.osr_ibop_fee==1}">style="display: block;"</c:if>
					<c:if test="${osr.osr_ibop_fee!=1}">style="display: none;"</c:if>
					class="moudle_dashed_border" align="center">
					<div class="div_margin">
						<span style="color: red">入库操作费按SKU类型：
							收货SKU数量*SKU单价+收货商品件数*商品单价</span>
					</div>
					<div class="div_margin">
						SKU单价: <input type="text" id="osr_ibop_skuprice"
							name="osr_ibop_skuprice" value="${osr.osr_ibop_skuprice}" /> <select
							id="osr_ibop_skuprice_unit" name="osr_ibop_skuprice_unit"><option
								value="0">元/SKU/天</option></select>
					</div>
					<div class="div_margin">
						商品单价: <input type="text" id="osr_ibop_itemprice"
							name="osr_ibop_itemprice" value="${osr.osr_ibop_itemprice}" /> <select
							id="osr_ibop_itemprice_unit" name="osr_ibop_itemprice_unit"><option
								value="0">元/件/天</option></select>
					</div>
				</div>
				<!-- 按入库数量收取 -->
				<div id="RKCZF2"
					<c:if test="${osr.osr_ibop_fee==2}">style="display: block;"</c:if>
					<c:if test="${osr.osr_ibop_fee!=2}">style="display: none;"</c:if>
					class="moudle_dashed_border" align="center">
					<div class="div_margin">
						<span style="color: red">操作费按收货数量收取： 收货上架数量*单价</span>
					</div>
					<div class="div_margin">
						单价: <input type="text" id="osr_ibop_qtyprice1"
							name="osr_ibop_qtyprice1" value="${osr.osr_ibop_qtyprice}" /> <select
							id="osr_ibop_qtyprice_unit" name="osr_ibop_qtyprice_unit"><option
								value="0">元/件/天</option></select>
					</div>
				</div>
				<hr>
				B2C出库操作费: <select onchange="showB2CCKCZF();" id="B2CCKCZF"
					name="B2CCKCZF" style="width: 30%;">
					<option value="0">请选择</option>
					<option ${osr.osr_btcobop_fee == 1? "selected= 'selected'": "" } value= "1" >按件出库</option>
					<option ${osr.osr_btcobop_fee == 2? "selected= 'selected'": "" } value= "2" >按单内件数阶梯计费</option>
					<option ${osr.osr_btcobop_fee == 3? "selected= 'selected'": "" } value= "3" >按单阶梯计费</option>
				</select>
				<div id="B2CCKCZF1" ${osr.osr_btcobop_fee == 1? "style= 'display: block;'": "style= 'display: none;'" } align= "center" class= "moudle_dashed_border" >
					B2C出库按件收费:
					<select id= "B2CCKCZFJT" name= "B2CCKCZFJT" style= "width: 30%;" onchange= "showB2CCKCZFJT();" >
						<option value="0">请选择</option>
						<option ${osr.osr_btcobop_numfee == 1? "selected= 'selected'": "" } value= "1" >无阶梯</option>
						<option ${osr.osr_btcobop_numfee == 2? "selected= 'selected'": "" } value= "2" >超过部分阶梯价</option>
						<option ${osr.osr_btcobop_numfee == 3? "selected= 'selected'": "" } value= "3" >总件数阶梯价</option>
					</select>
					<div id="B2CCKCZFJT1"
						<c:if test="${osr.osr_btcobop_numfee==1}">style="display: block;"</c:if>
						<c:if test="${osr.osr_btcobop_numfee!=1}">style="display: none;"</c:if>
						align="center">
						<div class="div_margin">
							<span style="color: red">B2C出库按件收费 B2C出库件数*单价*折扣</span>
						</div>
						<div class="div_margin">
							件数单价: <input type="text" id="osr_btcobop_numprice"
								name="osr_btcobop_numprice" value="${osr.osr_btcobop_numprice }" />
							<select id="" name=""><option value="0">元/件/天</option></select>
						</div>
						<div class="div_margin">
							折扣: <input type="text" id="osr_btcobop_numdc"
								name="osr_btcobop_numdc" value="${osr.osr_btcobop_numdc }" /> <select
								id="" name=""><option value="0"></option></select>
						</div>
					</div>
					<div id="B2CCKCZFJT2"
						<c:if test="${osr.osr_btcobop_numfee==2}">style="display: block;"</c:if>
						<c:if test="${osr.osr_btcobop_numfee!=2}">style="display: none;"</c:if>
						align="center">
						<div class="div_margin">
							<table3></table3>
							<div class="div_margin">
								<span style="color: red">超过部分折扣阶梯价 : B2C出库总件数*单价</span><br /> <span
									style="color: red">公式说明：折扣为阶梯区间，需要在下方设置</span><br />
							</div>
							<div class="div_margin">
								<span>---阶梯设置---</span>
							</div>
							<div class="div_margin">
								条件 <select id="bnt_mark1" name="bnt_mark1">
									<option value=">">></option>
									<option value=">=">>=</option>
								</select> 件数<input type="text" id="bnt_mark4" name="bnt_mark4" /> <select
									id="" name=""><option value="0">件</option></select>
							</div>
							<div class="div_margin">
								组合方式:<select id="bnt_mark2" name="bnt_mark2">
									<option value="1">并且</option>
								</select>
							</div>
							<div class="div_margin">
								条件 <select id="bnt_mark3" name="bnt_mark3">
									<option value="<"><</option>
									<option value="<="><=</option>
								</select> 件数<input type="text" id="bnt_mark5" name="bnt_mark5" /> <select
									id="" name=""><option value="0">件</option></select>
							</div>
							<!-- <div class="div_margin">
								 每单单价<input type="text" id="bnt_price" name="bnt_price" value=""/>
								 <select id="" name=""><option value="0">元</option></select>
						 	</div> -->
							<div class="div_margin">
								每件单价<input type="text" id="bnt_discount" name="bnt_discount" />
								<select id="" name=""><option value="0">元</option></select>
							</div>
							<div class="div_margin">
								<a data-toggle="tab" href="#tab_add">
									<button class="btn btn-xs btn-yellow"
										onclick="saveB2CCKCZF2();">
										<i class="icon-hdd"></i>保存
									</button>
								</a>
							</div>
						</div>
					</div>
					<div id="B2CCKCZFJT3"
						<c:if test="${osr.osr_btcobop_numfee==3}">style="display: block;"</c:if>
						<c:if test="${osr.osr_btcobop_numfee!=3}">style="display: none;"</c:if>
						align="center">
						<div class="div_margin">
							<table4></table4>
						</div>
						<span style="color: red">B2C出库按件收费 : B2C出库件数*单价*折扣</span><br /> <span
							style="color: red">公式说明：折扣为阶梯区间，需要在下方设置</span><br />
						<div class="div_margin">
							<span>---阶梯设置---</span>
						</div>
						<div class="div_margin">
							条件 <select id="obt_mark1" name="obt_mark1">
								<option value=">">></option>
								<option value=">=">>=</option>
							</select> 件数<input type="text" id="obt_mark4" name="obt_mark4" /> <select
								id="" name=""><option value="0">件</option></select>
						</div>
						<div class="div_margin">
							组合方式:<select id="obt_mark2" name="obt_mark2">
								<option value="1">并且</option>
							</select>
						</div>
						<div class="div_margin">
							条件 <select id="obt_mark3" name="obt_mark3">
								<option value="<"><</option>
								<option value="<="><=</option>
							</select> 件数<input type="text" id="obt_mark5" name="obt_mark5" /> <select
								id="" name=""><option value="0">件</option></select>
						</div>
						<div class="div_margin">
							单价:<input type="text" id="obt_price" name="obt_price" />&nbsp;<select
								id="" name=""><option value="0">元／件</option></select>
						</div>
						<div class="div_margin">
							折扣:<input type="text" id="obt_discount" name="obt_discount" />&nbsp;
							<select id="" name=""><option value="0"></option></select>
						</div>
						<div class="div_margin">
							<a data-toggle="tab" href="#tab_add">
								<button class="btn btn-xs btn-yellow"
									onclick="saveB2CCKCZFJT3();">
									<i class="icon-hdd"></i>保存
								</button>
							</a>
						</div>
					</div>
				</div>
				<div id="B2CCKCZF2" ${osr.osr_btcobop_fee == 2? "style= 'display: block;'": "style= 'display: none;'" } align= "center" class= "moudle_dashed_border" >
					<div class="div_margin">
						<span>是否转B2B:</span> <input id="osr_tobtb" name="osr_tobtb"
							type="checkbox"
							<c:if test="${osr.osr_tobtb==1}">checked="checked"</c:if>
							class="ace ace-switch ace-switch-5"
							onchange="changeShow('IFB2B1')" /> <span class="lbl"></span>
						<!-- <select onchange="showIFB2B();" id="IFB2B" style="width: 30%;">
							<option value="0">否</option>
							<option value="1">是</option>
						</select> -->
						<div id="IFB2B1"
							<c:if test="${osr.osr_tobtb==1}">style="display: block;"</c:if>
							<c:if test="${osr.osr_tobtb!=1}">style="display: none;"</c:if>
							align="center">
							<div class="div_margin">
								条件 <select id="" name="">
									<!-- <option value=">">></option> -->
									<option value=">=">>=</option>
								</select> 件数:<input type="text" id="osr_btcobopbill_tobtb_js"
									name="osr_btcobopbill_tobtb_js"
									value="${osr.osr_btcobopbill_tobtb_js}" /> <select id=""
									name=""><option value="0">件</option></select>
							</div>
							<div class="div_margin">
								<a data-toggle="tab" href="#tab_add">
									<button class="btn btn-xs btn-yellow"
										onclick="saveOperation();">
										<i class="icon-hdd"></i>保存
									</button>
								</a>
							</div>
						</div>
					</div>
					<hr>
					<div class="div_margin">
						B2C按单出库操作费：
						<div class="div_margin">
							<table5></table5>
						</div>
						<span style="color: red">B2C按单出库操作费 :
							(每单未超过3件的单数*单量单价)+单个订单件数超过3件的单数 * [每单单价+（B2C出库件数-维护条件件数）*单件单价]</span><br />
						<span style="color: red">公式说明：折扣为阶梯区间，需要在下方设置</span><br />
						<div class="div_margin">
							<span>---阶梯设置---</span>
						</div>
						<div class="div_margin">
							条件 <select id="tbbt_mark1" name="tbbt_mark1">
								<option value=">">></option>
								<option value=">=">>=</option>
							</select> 件数<input type="text" id="tbbt_mark4" name="tbbt_mark4" />
					<select id="" name=""><option value="0">件</option></select>
						</div>
						<div class="div_margin">
							组合方式:<select id="tbbt_mark2" name="tbbt_mark2">
								<option value="1">并且</option>
							</select>
						</div>
						<div class="div_margin">
							条件 <select id="tbbt_mark3" name="tbbt_mark3">
								<option value="<"><</option>
								<option value="<="><=</option>
							</select> 件数<input type="text" id="tbbt_mark5" name="tbbt_mark5" /> <select
								id="" name=""><option value="0">件</option></select>
						</div>
						<div class="div_margin">
							每单单价<input type="text" id="bbt_bill_price" name="bbt_bill_price"
								value="" /> <select id="" name=""><option value="0">元</option></select>
						</div>
						<div class="div_margin">
							每件单价<input type="text" id="bbt_piece_price"
								name="bbt_piece_price" /> <select id="" name=""><option
									value="0">元</option></select>
						</div>
						<div class="div_margin">
							<a data-toggle="tab" href="#tab_add">
								<button class="btn btn-xs btn-yellow" onclick="saveTBBT();">
									<i class="icon-hdd"></i>保存
								</button>
							</a>
						</div>
						<hr>
						阶梯折扣结算规则:
						<select id= "B2CADJTCK" name= "B2CADJTCK" style= "width: 30%;" onchange= "showB2CADJTCK();" >
							<option value= "0" >请选择</option>
							<option ${osr.osr_btcobopbill_discount_type == 1? "selected= 'selected'": "" } value= "1" >阶梯折扣按件结算</option>
							<option ${osr.osr_btcobopbill_discount_type == 2? "selected= 'selected'": "" } value= "2" >阶梯折扣按单结算</option>
						</select>
						<div id="B2CADJTCK1" ${osr.osr_btcobopbill_discount_type == 1? "style= 'display: block;'": "style= 'display: none;'" } align= "center" >
							<div class="div_margin">
								<table6></table6>
							</div>
							<div class="div_margin">
								条件<select id="btcbd_mark1" name="btcbd_mark1">
									<option value=">">></option>
									<option value=">=">>=</option>
								</select> 件数<input type="text" id="btcbd_mark4" name="btcbd_mark4" /> <select
									id="" name=""><option value="0">件</option></select>
							</div>
							<div class="div_margin">
								组合方式: <select id="btcbd_mark2" name="btcbd_mark2">
									<option value="1">并且</option>
								</select>
							</div>
							<div class="div_margin">
								条件<select id="btcbd_mark3" name="btcbd_mark3">
									<option value="<"><</option>
									<option value="<="><=</option>
								</select> 件数<input type="text" id="btcbd_mark5" name="btcbd_mark5" /> <select
									id="" name=""><option value="0">件</option></select>
							</div>
							<div class="div_margin">
								折扣<input type="text" id="btcbd_price" name="btcbd_price" /> <select
									id="" name=""><option value="0"></option></select>
							</div>
							<div class="div_margin">
								<a data-toggle="tab" href="#tab_add">
									<button class="btn btn-xs btn-yellow" onclick="save6();">
										<i class="icon-hdd"></i>保存
									</button>
								</a>
							</div>
						</div>
						<div id="B2CADJTCK2"
							<c:if test="${osr.osr_btcobopbill_discount_type==2}">style="display: block;"</c:if>
							<c:if test="${osr.osr_btcobopbill_discount_type!=2}">style="display: none;"</c:if>
							align="center">
							<div class="div_margin">
								<table7></table7>
							</div>
							<div class="div_margin">
								条件<select id="btcbd2_mark1" name="btcbd2_mark1">
									<option value=">">></option>
									<option value=">=">>=</option>
								</select> 单数<input type="text" id="btcbd2_mark4" name="btcbd2_mark4" /> <select
									id="" name=""><option value="0">单</option></select>
							</div>
							<div class="div_margin">
								组合方式: <select id="btcbd2_mark2" name="btcbd2_mark2">
									<option value="1">并且</option>
								</select>
							</div>
							<div class="div_margin">
								条件 <select id="btcbd2_mark3" name="btcbd2_mark3">
									<option value="<"><</option>
									<option value="<="><=</option>
								</select> 单数<input type="text" id="btcbd2_mark5" name="btcbd2_mark5" /> <select
									id="" name=""><option value="0">单</option></select>
							</div>
							<div class="div_margin">
								折扣<input type="text" id="btcbd2_price" name="btcbd2_price" /> <select
									id="" name=""><option value="0"></option></select>
							</div>
							<div class="div_margin">
								<a data-toggle="tab" href="#tab_add">
									<button class="btn btn-xs btn-yellow" onclick="save7();">
										<i class="icon-hdd"></i>保存
									</button>
								</a>
							</div>
						</div>
					</div>
				</div>
				<div id="B2CCKCZF3" ${osr.osr_btcobop_fee == 3? "style= 'display: block;'": "style= 'display: none;'" } align= "center" class= "moudle_dashed_border" >
					B2C出库按单收费:
					<select id= "B2CCKCZFJTs" name= "B2CCKCZFJTs" style= "width: 30%;" onchange= "showB2CCKCZFJTs();" >
						<option value= "0" >请选择</option>
						<option ${osr.osr_btcobop_numfees == 1? "selected= 'selected'": "" } value= "1" >无阶梯</option>
						<option ${osr.osr_btcobop_numfees == 2? "selected= 'selected'": "" } value= "2" >超过部分阶梯价</option>
						<option ${osr.osr_btcobop_numfees == 3? "selected= 'selected'": "" } value= "3" >总单数阶梯价</option>
					</select>
					<div id="B2CCKCZFJTs1"
						<c:if test="${osr.osr_btcobop_numfees==1}">style="display: block;"</c:if>
						<c:if test="${osr.osr_btcobop_numfees!=1}">style="display: none;"</c:if>
						align="center">
						<div class="div_margin">
							<span style="color: red">B2C出库按件收费 B2C出库单数*单价*折扣</span>
						</div>
						<div class="div_margin">
							单数单价: <input type="text" id="osr_btcobop_numprices"
								name="osr_btcobop_numprices"
								value="${osr.osr_btcobop_numprices }" /> <select id="" name=""><option
									value="0">元/件/天</option></select>
						</div>
						<div class="div_margin">
							折扣: <input type="text" id="osr_btcobop_numdcs"
								name="osr_btcobop_numdcs" value="${osr.osr_btcobop_numdcs }" />
						</div>
					</div>
					<div id="B2CCKCZFJTs2"
						<c:if test="${osr.osr_btcobop_numfees==2}">style="display: block;"</c:if>
						<c:if test="${osr.osr_btcobop_numfees!=2}">style="display: none;"</c:if>
						align="center">
						<div class="div_margin">
							<table36></table36>
							<div class="div_margin">
								<span style="color: red">超过部分折扣阶梯价 : B2C出库总单数*单价</span><br /> <span
									style="color: red">公式说明：折扣为阶梯区间，需要在下方设置</span><br />
							</div>
							<div class="div_margin">
								<span>---阶梯设置---</span>
							</div>
							<div class="div_margin">
								条件 <select id="bnts_mark1" name="bnts_mark1">
									<option value=">">></option>
									<option value=">=">>=</option>
								</select> 单数<input type="text" id="bnts_mark4" name="bnts_mark4" /> <select
									id="" name=""><option value="0">件</option></select>
							</div>
							<div class="div_margin">
								组合方式:<select id="bnts_mark2" name="bnts_mark2">
									<option value="1">并且</option>
								</select>
							</div>
							<div class="div_margin">
								条件 <select id="bnts_mark3" name="bnts_mark3">
									<option value="<"><</option>
									<option value="<="><=</option>
								</select> 单数<input type="text" id="bnts_mark5" name="bnts_mark5" /> <select
									id="" name=""><option value="0">件</option></select>
							</div>
							<!-- <div class="div_margin">
								 每单单价<input type="text" id="bnt_price" name="bnt_price" value=""/>
								 <select id="" name=""><option value="0">元</option></select>
						 	</div> -->
							<div class="div_margin">
								每单单价<input type="text" id="bnts_discount" name="bnts_discount" />
								<select id="" name=""><option value="0">元</option></select>
							</div>
							<div class="div_margin">
								<a data-toggle="tab" href="#tab_add">
									<button class="btn btn-xs btn-yellow"
										onclick="saveB2CCKCZF2s();">
										<i class="icon-hdd"></i>保存
									</button>
								</a>
							</div>
						</div>
					</div>
					<div id="B2CCKCZFJTs3"
						<c:if test="${osr.osr_btcobop_numfees==3}">style="display: block;"</c:if>
						<c:if test="${osr.osr_btcobop_numfees!=3}">style="display: none;"</c:if>
						align="center">
						<div class="div_margin">
							<table37></table37>
						</div>
						<span style="color: red">B2C出库按件收费 : B2C出库单数*单价*折扣</span><br /> <span
							style="color: red">公式说明：折扣为阶梯区间，需要在下方设置</span><br />
						<div class="div_margin">
							<span>---阶梯设置---</span>
						</div>
						<div class="div_margin">
							条件 <select id="obts_mark1" name="obts_mark1">
								<option value=">">></option>
								<option value=">=">>=</option>
							</select> 单数<input type="text" id="obts_mark4" name="obts_mark4" /> <select
								id="" name=""><option value="0">单</option></select>
						</div>
						<div class="div_margin">
							组合方式:<select id="obts_mark2" name="obts_mark2">
								<option value="1">并且</option>
							</select>
						</div>
						<div class="div_margin">
							条件 <select id="obts_mark3" name="obts_mark3">
								<option value="<"><</option>
								<option value="<="><=</option>
							</select> 单数<input type="text" id="obts_mark5" name="obts_mark5" /> <select
								id="" name=""><option value="0">单</option></select>
						</div>
						<div class="div_margin">
							单价:<input type="text" id="obts_price" name="obts_price" />&nbsp;<select
								id="" name=""><option value="0">元／单</option></select>
						</div>
						<div class="div_margin">
							折扣:<input type="text" id="obts_discount" name="obts_discount" />&nbsp;
							<select id="" name=""><option value="0"></option></select>
						</div>
						<div class="div_margin">
							<a data-toggle="tab" href="#tab_add">
								<button class="btn btn-xs btn-yellow"
									onclick="saveB2CCKCZFJT3s();">
									<i class="icon-hdd"></i>保存
								</button>
							</a>
						</div>
					</div>
				</div>
				<hr>
				B2B出库操作费 
				<select id= "B2BCKCZF" name= "B2BCKCZF" style= "width: 30%;" onchange= "showB2BCKCZF();" >
					<option value= "0" >请选择</option>
					<option ${osr.osr_btbobop_fee == 1? "selected= 'selected'": "" } value= "1" >按件出库</option>
					<option ${osr.osr_btbobop_fee == 2? "selected= 'selected'": "" } value= "2" >按SKU类型收取</option>
				</select>
				<div id="B2BCKCZF1"
					<c:if test="${osr.osr_btbobop_fee==1}">style="display: block;"</c:if>
					<c:if test="${osr.osr_btbobop_fee!=1}">style="display: none;"</c:if>
					align="center" class="moudle_dashed_border">
					<div class="div_margin">
						<span style="color: red">B2B按件数出库操作费 : B2B出库件数*单价</span>
					</div>
					<div class="div_margin">
						件数单价:<input type="text" id="osr_btbobop_billprice"
							name="osr_btbobop_billprice"
							value="${osr.osr_btbobop_billprice }" /> <select
							id="osr_btbobop_billprice_unit" name="osr_btbobop_billprice_unit"><option
								value="0">元/件/天</option></select>
					</div>
				</div>
				<div id="B2BCKCZF2"
					<c:if test="${osr.osr_btbobop_fee==2}">style="display: block;"</c:if>
					<c:if test="${osr.osr_btbobop_fee!=2}">style="display: none;"</c:if>
					align="center" class="moudle_dashed_border">
					<div class="div_margin">
						<span style="color: red">B2B按SKU类型收取操作费 :
							SKU数量*SKU单价+商品数量*商品单价</span>
					</div>
					<div class="div_margin">
						SKU单价:<input type="text" id="osr_btbobop_skuprice"
							name="osr_btbobop_skuprice" value="${osr.osr_btbobop_skuprice }" />
						<select id="osr_btbobop_skuprice_unit"
							name="osr_btbobop_skuprice_unit"><option value="0">元/SKU/天</option></select>
					</div>
					<div class="div_margin">
						商品单价:<input type="text" id="osr_btbobop_itemprice"
							name="osr_btbobop_itemprice"
							value="${osr.osr_btbobop_itemprice }" /> <select
							id="osr_btbobop_itemprice_unit" name="osr_btbobop_itemprice_unit"><option
								value="0">元/SKU/天</option></select>
					</div>
				</div>
				<hr>
				B2B退仓操作费
			 	<select id= "B2BTCCZF" name= "B2BTCCZF" style= "width: 30%;" onchange= "showB2BTCCZF();" >
					<option value= "0" >请选择</option>
					<option ${osr.osr_btbrtop_fee == 1? "selected= 'selected'": "" } value= "1" >按SKU计算</option>
					<option ${osr.osr_btbrtop_fee == 2? "selected= 'selected'": "" } value= "2" >按件数计算</option>
				</select>
				<div id="B2BTCCZF1"
					<c:if test="${osr.osr_btbrtop_fee==1}">style="display: block;"</c:if>
					<c:if test="${osr.osr_btbrtop_fee!=1}">style="display: none;"</c:if>
					align="center" class="moudle_dashed_border">
					<div class="div_margin">
						<span style="color: red">B2B退仓按SKU类型出库操作费 :
							退仓SKU数量*单价+退仓件数*单价</span>
					</div>
					<div class="div_margin">
						SKU类型单价 :<input type="text" id="osr_btbrtop_sku_skuprice"
							name="osr_btbrtop_sku_skuprice"
							value="${osr.osr_btbrtop_sku_skuprice }" /> <select
							id="osr_btbrtop_sku_skuprice_unit"
							name="osr_btbrtop_sku_skuprice_unit"><option value="0">元/SKU/天</option></select>
					</div>
					<div class="div_margin">
						件数单价 :<input type="text" id="osr_btbrtop_sku_billprice"
							name="osr_btbrtop_sku_billprice"
							value="${osr.osr_btbrtop_sku_billprice }" /> <select
							id="osr_btbrtop_sku_billprice_unit"
							name="osr_btbrtop_sku_billprice_unit"><option value="0">元/件/天</option></select>
					</div>
				</div>
				<div id="B2BTCCZF2"
					<c:if test="${osr.osr_btbrtop_fee==2}">style="display: block;"</c:if>
					<c:if test="${osr.osr_btbrtop_fee!=2}">style="display: none;"</c:if>
					align="center" class="moudle_dashed_border">
					<div class="div_margin">
						<span style="color: red">B2B按件数出库操作费 : B2B出库件数*单价</span>
					</div>
					<div class="div_margin">
						件数单价:<input type="text" id="osr_btbrtop_bill_billprice"
							name="osr_btbrtop_bill_billprice"
							value="${osr.osr_btbrtop_bill_billprice }" /> <select
							id="osr_btbrtop_bill_billprice_unit"
							name="osr_btbrtop_bill_billprice_unit"><option value="0">元/件/天</option></select>
					</div>
				</div>
				<hr>
				B2C退换货入库费 
				<select id= "B2CTHHRKF" name= "B2CTHHRKF" style= "width: 30%;" onchange= "showB2CTHHRKF();" >
					<option value= "0" >请选择</option>
					<option ${osr.osr_btcrtib_fee == 1? "selected= 'selected'": "" } value= "1" >按单计算</option>
					<option ${osr.osr_btcrtib_fee == 2? "selected= 'selected'": "" } value= "2" >按件计算</option>
				</select>
				<div id="B2CTHHRKF1"
					<c:if test="${osr.osr_btcrtib_fee==1}">style="display: block;"</c:if>
					<c:if test="${osr.osr_btcrtib_fee!=1}">style="display: none;"</c:if>
					align="center" class="moudle_dashed_border">
					<div class="div_margin">
						<span style="color: red">B2C退换货入库费=退换货入库单量*单量单价</span>
					</div>
					<div class="div_margin">
						单量单价:<input type="text" id="osr_btcrtib_bill_billprice"
							name="osr_btcrtib_bill_billprice"
							value="${osr.osr_btcrtib_bill_billprice }" /> <select
							id="osr_btcrtib_bill_billprice_unit"
							name="osr_btcrtib_bill_billprice_unit"><option value="0">元/单/天</option></select>
					</div>
				</div>
				<div id= "B2CTHHRKF2" ${osr.osr_btcrtib_fee == 2? "style= 'display: block;'": "style= 'display: none;'" } align= "center" class= "moudle_dashed_border" >
					退换货入库费按件数计算
					<select id= "B2CTHHRKFJT" name= "B2CTHHRKFJT" style= "width: 30%;" onchange= "showB2CTHHRKFJT();" >
						<option value= "0" >请选择</option>
						<option ${osr.osr_btcrtib_piece == 1? "selected= 'selected'": "" } value= "1" >无阶梯</option>
						<option ${osr.osr_btcrtib_piece == 2? "selected= 'selected'": "" } value= "2" >有阶梯</option>
					</select>
					<div id="B2CTHHRKFJT1"
						<c:if test="${osr.osr_btcrtib_piece==1}">style="display: block;"</c:if>
						<c:if test="${osr.osr_btcrtib_piece!=1}">style="display: none;"</c:if>
						align="center">
						<div class="div_margin">
							<span style="color: red">B2C退换货入库费=退换货入库件数*件数单价</span>
						</div>
						<div class="div_margin">
							件数单价:<input type="text" id="osr_btcrtib_piece_pieceprice"
								name="osr_btcrtib_piece_pieceprice"
								value="${osr.osr_btcrtib_piece_pieceprice }" /> <select
								id="osr_btcrtib_piece_pieceprice_unit"
								name="osr_btcrtib_piece_pieceprice_unit"><option
									value="0">元/单/天</option></select>
						</div>
					</div>
					<div id="B2CTHHRKFJT2"
						<c:if test="${osr.osr_btcrtib_piece==2}">style="display: block;"</c:if>
						<c:if test="${osr.osr_btcrtib_piece!=2}">style="display: none;"</c:if>
						align="center">
						<div class="div_margin">
							<span style="color: red">B2C退换货入库费=退换货入库件数*件数单价</span>
							<table2></table2>
							<div class="div_margin">
								条件<select id="btcrti_mark1" name="btcrti_mark1">
									<option value=">">></option>
									<option value=">=">>=</option>
								</select> 退换货入库数量/销售出库数量<input type="text" id="btcrti_mark4"
									name="btcrti_mark4" /> <select id="" name=""><option
										value="0"></option></select>
							</div>
							<div class="div_margin">
								组合方式: <select id="btcrti_mark2" name="btcrti_mark2">
									<option value="1">并且</option>
								</select>
							</div>
							<div class="div_margin">
								条件<select id="btcrti_mark3" name="btcrti_mark3">
									<option value="<"><</option>
									<option value="<="><=</option>
								</select> 退换货入库数量/销售出库数量<input type="text" id="btcrti_mark5"
									name="btcrti_mark5" /> <select id="" name=""><option
										value="0"></option></select>
							</div>
							<div class="div_margin">
								件数单价<input type="text" id="btcrt_price" name="btcrt_price" /> <select
									id="btcrt_price_unit" name="btcrt_price_unit"><option
										value="0">元/件</option></select>
							</div>
							<div class="div_margin">
								<a data-toggle="tab" href="#tab_add">
									<button class="btn btn-xs btn-yellow"
										onclick="saveB2CTHHRKFJT();">
										<i class="icon-hdd"></i>保存
									</button>
								</a>
							</div>
						</div>
					</div>
				</div>
				<div class="div_margin">
					<a data-toggle="tab" href="#tab_add">
						<button class="btn btn-xs btn-yellow" onclick="saveOperation();">
							<i class="icon-hdd"></i>保存
						</button>
					</a>
				</div>
			</div>
			</div>
		
		<div class="moudle_dashed_border" style="display: block;">
			<div class="div_margin">
				<label class="control-label blue">
					管理费
				</label>
				<input 
				id="cb_managementFee_other_czf" 
				type="checkbox" 
				class="ace ace-switch ace-switch-5" ${czfFeeFlag == true ? 'checked="checked"' : '' } 
				onchange="managementOtherShow('czf');"/>
				<span class="lbl"></span>
			</div>
			<!-- 管理费 -->
			<div id="managementFeeOther_czf" class="moudle_dashed_border" style="width:100%;border:0px;${czfFeeFlag == true ? 'display:block;' : '' }">
				<iframe id="iframe_czf" class="with-border" style="overflow: visible; border:0px;margin:0px;padding:0px;background: rgba(0,0,0,0);width: 100%;" src="${root }control/expressContractController/toManagementFeeOther.do?management_fee_type=czf&cb_id=${cb.id }"></iframe>
			</div>
		</div>
	</form>
</div>