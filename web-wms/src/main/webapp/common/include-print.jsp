<%-- /* <script type="text/javascript">
	function print(url,flag){
		var result = document.printApplet.printReport(url,flag);
		console.log("print result"+result);
		return result;
	}
	
	/*
		print used defined printer
	*/
	function printDf(url,printerName){
		document.printApplet.printReport(url,false,printerName);
	}
	
	function preview(url){
		document.printApplet.previewReport(url);
	}
</script> */ --%>
<APPLET name="printApplet" CODE = "com.jumbo.util.comm.JasperPrintApplet.class" ARCHIVE = "<%=request.getContextPath() %>/common/jasperreports-3.5.2-applet.jar" WIDTH = "0" HEIGHT = "0">
</APPLET>
