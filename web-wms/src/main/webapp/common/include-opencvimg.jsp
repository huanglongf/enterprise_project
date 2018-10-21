<script type="text/javascript">
function cameraPaintJs(url){
		document.openCvImgApplet.cameraImg(url);
}
</script>


<APPLET name="openCvImgApplet" CODE = "com.jumbo.util.comm.OpenCvImgApplet.class"  width="320" height="240"
	ARCHIVE = "<%=request.getContextPath() %>/common/opencvimgapplet.jar" type="applet" >
</APPLET>