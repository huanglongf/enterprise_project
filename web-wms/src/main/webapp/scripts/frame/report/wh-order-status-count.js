$j.extend(loxia.regional['zh-CN'], {});

var TIME_LINE = '16:30:00';

function initTransTable() {
	var baseUrl = $j("body").attr("contextpath");
	var startDate = $j("#startDate").val().replace(/:/g, "").replace(/ /g, "")
			.replace(/-/g, "");
	var endDate = $j("#endDate").val().replace(/:/g, "").replace(/ /g, "")
			.replace(/-/g, "");
	var postData = {
		startDate : startDate,
		endDate : endDate
	};
	var rs = loxia.syncXhrPost(loxia.getTimeUrl(baseUrl
			+ "/findTransOrderStatusReporyByOu.json"), postData);
	if (rs && rs.result) {
		$j("#tbl_trans_tby tr").remove();
		for (var i = 0; i < rs.result.length; i++) {
			var detial = $j('<tr><th>' + rs.result[i].lpcode + '</th><td>'
					+ rs.result[i].totalQty + '</td><td>'
					+ rs.result[i].statusNewQty + '</td><td>'
					+ rs.result[i].statusOcpQty + '</td><td>'
					+ rs.result[i].statusBatchQty + '</td><td>'
					+ rs.result[i].statusCheckedQty + '</td><td>'
					+ rs.result[i].statusOutboundQty + '</td><td>'
					+ rs.result[i].statusFinishedQty + '</td><td>'
					+ rs.result[i].statusCaneclQty + '</td></tr>');
			$j("#tbl_trans_tby").append(detial);
		}
	}
}

function intiOrderTable() {
	var baseUrl = $j("body").attr("contextpath");
	var startDate = $j("#startDate").val().replace(/:/g, "").replace(/ /g, "")
			.replace(/-/g, "");
	var endDate = $j("#endDate").val().replace(/:/g, "").replace(/ /g, "")
			.replace(/-/g, "");
	var postData = {
		startDate : startDate,
		endDate : endDate
	};
	var rs = loxia.syncXhrPost(loxia.getTimeUrl(baseUrl
			+ "/findOrderStatusReporyByOu.json"), postData);
	if (rs && rs.result) {
		$j("#tbl_order_tby tr").remove();
		// 构建表格
		var statusNewQty = 0;
		var statusOcpQty = 0;
		var statusBatchQty = 0;
		var statusCheckedQty = 0;
		var statusOutboundQty = 0;
		var statusFinishedQty = 0;
		var statusCaneclQty = 0;
		for (var i = 0; i < rs.result.length; i++) {
			statusNewQty += rs.result[i].statusNewQty;
			statusOcpQty += rs.result[i].statusOcpQty;
			statusBatchQty += rs.result[i].statusBatchQty;
			statusCheckedQty += rs.result[i].statusCheckedQty;
			statusOutboundQty += rs.result[i].statusOutboundQty;
			statusFinishedQty += rs.result[i].statusFinishedQty;
			statusCaneclQty += rs.result[i].statusCaneclQty;
			var detial = $j('<tr><th>' + rs.result[i].whName + '</th><td>'
					+ rs.result[i].totalQty + '</td><td>'
					+ rs.result[i].statusNewQty + '</td><td>'
					+ rs.result[i].statusOcpQty + '</td><td>'
					+ rs.result[i].statusBatchQty + '</td><td>'
					+ rs.result[i].statusCheckedQty + '</td><td>'
					+ rs.result[i].statusOutboundQty + '</td><td>'
					+ rs.result[i].statusFinishedQty + '</td><td>'
					+ rs.result[i].statusCaneclQty + '</td></tr>');
			$j("#tbl_order_tby").append(detial);
		}
		var pieData = [ {
			value : statusNewQty,
			color : "#CD0000",
			highlight : "#FF0000",
			label : "新建"
		}, {
			value : statusOcpQty,
			color : "#68228B",
			highlight : "#6959CD",
			label : "占用"
		}, {
			value : statusBatchQty,
			color : "#104E8B",
			highlight : "#1874CD",
			label : "建批"
		}, {
			value : statusCheckedQty,
			color : "#00EEEE",
			highlight : "#00FFFF",
			label : "核对"
		}, {
			value : statusOutboundQty,
			color : "#FFC125",
			highlight : "#FFD700",
			label : "出库"
		}, {
			value : statusFinishedQty,
			color : "#00CD00",
			highlight : "#00EE00",
			label : "完成"
		}, {
			value : statusCaneclQty,
			color : "#3D3D3D",
			highlight : "#080808",
			label : "取消"
		} ];
		$j("#canvas-holder canvas").remove();
		var canvs = $j('<canvas id="chart-area" width="300" height="300" />');
		$j("#canvas-holder").append(canvs);
		var ctx = $j("#chart-area").get(0).getContext("2d");
		pieChart = new Chart(ctx);
		pieChart.Pie(pieData);
	}
}

function initDefaultDate() {
	var yestdate = new Date();
	yestdate.setDate(yestdate.getDate() - 1);
	var startDate = formatDate(yestdate) + TIME_LINE;
	$j("#startDate").val(startDate);
	var endDate = formatDate(new Date()) + TIME_LINE;
	$j("#endDate").val(endDate);
}

/**
 * 格式化时间
 * 
 * @param date
 * @returns {String}
 */
function formatDate(date) {
	var seperator1 = "-";
	var seperator2 = ":";
	var year = date.getFullYear();
	var month = date.getMonth() + 1;
	var strDate = date.getDate();
	if (month >= 1 && month <= 9) {
		month = "0" + month;
	}
	if (strDate >= 0 && strDate <= 9) {
		strDate = "0" + strDate;
	}
	var currentdate = year + seperator1 + month + seperator1 + strDate + " ";
	return currentdate;
}

$j(document).ready(function() {
	$j("#tabs").tabs();
	initDefaultDate();
	$j("#reflush").click(function() {
		intiOrderTable();
		initTransTable();
	});

	intiOrderTable();
	initTransTable();

	setInterval(function() {
		intiOrderTable();
		initTransTable();
	}, 1000 * 60 * 10);
});
