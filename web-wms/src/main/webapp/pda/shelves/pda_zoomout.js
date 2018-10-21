			var size = 1.0; 
			
			function zoomoutPda() {  
			 size = size + 3.5;  
			 setPda(); 
			}  
			
			function setPda() {  
			document.body.style.zoom = size;
			document.body.style.cssText += '; -moz-transform: scale(' + size + ');-moz-transform-origin: 0 0; '; 
			} 
			
			
			$(document).ready(function(){
				////////////////////////////////////////////////////适应屏幕大小
				var height=$(window).height();
		        //alert(height);
				if(height==664 || height==1384){
					zoomoutPda();
				} 
				///////////////////////////////////////////////////////////////////////////////////////////////
			});
