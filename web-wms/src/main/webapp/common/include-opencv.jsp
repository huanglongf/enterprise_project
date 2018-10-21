<script type="text/javascript">
function openCvPhotoImgJs(){
		return document.openCvApplet.openCvPhoto();		
}

function cameraPhotoJs(fileName,photoName){
		document.openCvApplet.cameraPhoto(fileName,photoName);
}
function creatZipJs(url,zipName){
	document.openCvApplet.creatZip(url,zipName);
}
function updateHttpJs(url,zipName){
	document.openCvApplet.updateHttp(url,zipName);
}
function closeGrabberJs(){
	document.openCvApplet.closeGrabber();
}
</script>


<APPLET name="openCvApplet" CODE = "com.jumbo.util.comm.OpenCvApplet.class" width="0" height="0"
	ARCHIVE = "<%=request.getContextPath() %>/common/opencvapplet.jar" type="applet">
</APPLET>
