<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>
#uploadResult {	width: 100%; background-color: gray}
#uploadResult ul{ display:flex; flex-flow: row; justify-content: center; align-items: center;}
#uploadResult ul li {list-style:none; padding: 10px; align-content: center; text-align: center;}
#uploadResult ul li img{ width: 60px;}
#uploadResult ul li span{color: white;}
.bigWrapper { position: absolute; display: none; justify-content: center;
			align-items: center; top: 0%; width: 100%; height: 100%; background-color: gray;
			z-index: 100; background:rgba(255,255,255,0.5);}
.bigNested { position: relative; display:flex; justify-content: center; align-items:center;}
.bigNested img {width: 600px;}
.bigNested video {width: 600px;}
</style>
<title>Insert title here</title>
</head>
<body>
	<div id="uploadDiv">
		<input id="inFiles" type="file" name="upLoadFile" multiple>
	</div>
	<button id="btnUpload">파일올리기</button>
	
	<div id="uploadResult">
		<ul></ul>
	</div>
</body>
	<script  src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	//업로드 파일에 대한 확장자 제한하는 정규식
	var uploadConstraintByExt = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
	//업로드 파일에 대한 최대크기를 제한
	var uploadMaxSize = 1036870912;
	
	//화면이 맨 처음 로드 시 들어 있는 깨끗한 상태 기억
	var initClearStatus = $("#uploadDiv").clone();
	var resultUl = $("#uploadResult ul");
	
	$("#btnUpload").on("click", function(e) {
		var formData = new FormData();
		var files = $("#inFiles")[0].files;
		
		for (var i = 0; i < files.length; i++) {
			if (! checkFileConstraints(files[i].name, files[i].size))
				return false;
			formData.append("uploadFile", files[i]);
		}
		
		$.ajax({
			url : "/uploadFiles/upload",
			processData : false,
			contentType : false,
			data : formData,
			type : 'post',
			success : function(result) {
				var liTags = "";
				$(result).each(function (i, attachVo) {
					if (attachVo.multimediaType === "others") {
						liTags += "<li><img src='/resources/img/attachFileIcon.png'>" + attachVo.pureFileName + "</li>";
					} else if (attachVo.multimediaType === "audio") {
						liTags += "<li><img src='/resources/img/audioThumbnail.png'>" + attachVo.pureFileName + "</li>";
					} else if (attachVo.multimediaType === "image" || attachVo.multimediaType === "video") {
						liTags += "<li><img src='/uploadFiles/display" + attachVo.fileCallPath + "'>" + attachVo.pureFileName + "</li>";
					} else {
						liTags += "<li>" + attachVo.pureFileName + "</li>";	
					}				  
				});
				resultUl.append(liTags); //append 쓴이유  > 업로드 또하면 
				//업로드이후 청소
				$("#uploadDiv").html(initClearStatus.html());
			}
		});
	});
	
	//업로드 파일에 대한 제약 사항을 미리 검사해줍니다.
	function checkFileConstraints(fileName, fileSize) {
		//크기 검사
		if (fileSize > uploadMaxSize) {
			return false;
		}
		//종류 검사
		if (uploadConstraintByExt.test(fileName)) {
			return false;
		}
		
		return true;
	}
	
	
});
</script>
</html>