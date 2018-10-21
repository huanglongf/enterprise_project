/**
 * 解决部分js注入的问题
 * @param {Object} str
 * @return {TypeName} 
 */
function replaceJs(str){
	 if(str==null||str==''){
		 return;
	 }
	 var  s;  
     s   =   str.replace(/</g, "&lt"); 
     s   =   s.replace(/>/g, "&gt"); 
	return s;
}

/**
 * 去掉字符的前后空格
 * @param {Object} m
 * @return {TypeName} 
 */
function  ztrim(str)   {
	return  str.replace(/(^\s*)|(\s*$)/g, "");
  //return m;   
}  

  function checkLogName(name){
	var reg=/[a-z0-9_@]*/i;
	if(name.length<6||name.length>50||!(reg.test(name))){
		alert("输入登入名称不合法，登入名，支持字母，数字，下划线和@。 不区分大小写，长度[6，50]");
		return true;
	}
   }



