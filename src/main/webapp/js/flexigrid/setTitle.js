function setTitleFlexigrid(){
	$("#flex tr > td").each(function(){
		var html = $(this).html().toUpperCase();
		if(html.indexOf("<A") != -1 && html.indexOf("<A")>html.indexOf(">") && html.indexOf("</A>") != -1){
			//$(this).attr("title","");
			//暂时不处理
		}else{
			var text = $(this).text();
			if(text != ""){
				$(this).attr("title",text);
			}
		}
	});
}