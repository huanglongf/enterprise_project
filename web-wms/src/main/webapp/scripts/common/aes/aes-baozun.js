var aesKey="";

function initAesKey(){
	var result = loxia.syncXhrPost($j("body").attr("contextpath")+ "/json/getStarbucksAESKey.do");
	aesKey=result['aeskey'];
}

//aes加密 
function Encrypt(word){  
         var key = CryptoJS.enc.Utf8.parse(aesKey);   
         var srcs = CryptoJS.enc.Utf8.parse(word);  
         var encrypted = CryptoJS.AES.encrypt(srcs, key, {mode:CryptoJS.mode.ECB,padding: CryptoJS.pad.Pkcs7});  
         return encrypted.toString();  
}  
//aes解密
 function Decrypt(word){  
         var key = CryptoJS.enc.Utf8.parse(aesKey);   
         var decrypt = CryptoJS.AES.decrypt(word, key, {mode:CryptoJS.mode.ECB,padding: CryptoJS.pad.Pkcs7});  
         return CryptoJS.enc.Utf8.stringify(decrypt).toString();  
}  