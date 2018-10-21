<script type="text/javascript">
function cameraPaintMultiJs(url){
		document.openCvImgMultiApplet.cameraImg(url);
}

</script>


<APPLET name="openCvImgMultiApplet" CODE = "com.jumbo.util.comm.OpenCvImgApplet.class"  width="320" height="240"
	ARCHIVE = "<%=request.getContextPath() %>/common/opencvimgapplet.jar" type="applet" >
</APPLET>