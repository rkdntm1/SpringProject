<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div id="uploadDiv">
		<input id="inFiles" type="file" name="upLoadFile" multiple>
	</div>
	<button id="btnUpload">파일올리기</button>
</body>
	<script  src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	$("#btnUpload").on("click", function(e) {
		var formData = new FormData();
		var asd = $("#inFiles")[0].files;
		
		for (var i = 0; i < asd.length; i++) {
			formData.append("uploadFile", asd[i]);
		}
		
		$.ajax({
			url : "/uploadFiles/upload",
			processData : false,
			contentType : false,
			data : formData,
			type : 'post',
			success : function(result) {
				alert("업.로.드.성.공");
			}
		});
	});
});
</script>
</html>