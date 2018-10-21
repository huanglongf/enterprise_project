<%-- <script type="text/javascript">
function setRS232Value(weightVal){
	if(weightVal){
		var weight=document.getElementById('autoWeigth');
		weight.value=weightVal;
	}	
}

function appletStop(){
	/*
	if(document.weightApplet){
		document.weightApplet.destroy();
	}
	*/
}

function appletStart(){
	/*
	if(document.weightApplet){
		document.weightApplet.init();
		document.weightApplet.start();
	}
	*/
}

function restart(){
	if(document.weightApplet){
		if(typeof document.weightApplet.closeSerialPort != 'undefined'){
			document.weightApplet.closeSerialPort();
		}
		if(typeof document.weightApplet.initSerialPort != 'undefined'){
			document.weightApplet.initSerialPort();
		}
	}
}
</script>
--%>

<APPLET name="weightApplet" CODE = "com.jumbo.util.comm.CommSR232Applet.class" width="0" height="0"
	ARCHIVE = "<%=request.getContextPath() %>/common/comapplet.jar" type="applet">
	<PARAM name="separate_jvm" value="true">
</APPLET>