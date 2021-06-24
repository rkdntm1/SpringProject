<!-- 분할 정복으로 첨부 파일 관리 복잡도를 관리. 이는 유지보수성 향상 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

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

<!-- 첨부 파일 목록 출력 부분 -->
<div class="container-fluid">
	<div class="card shadow mb-4">
		<div class="card-body">
			<div class="card-header">첨부파일</div>			
			<div class="card-body" id="uploadDiv">
				<input id="inFiles" type="file" name="upLoadFile" multiple>
				<button id="btnUpload">파일올리기</button>
			</div>
			
			<div class="card-body" id="uploadResult">
				<ul></ul>
			</div>
			
			<div class="bigWrapper">
				<div class="bigNested">
				</div>
			</div>
		</div>
	</div>
</div>
<!-- End of 첨부 파일 목록 -->
<script src="\resources\js\util\utf8.js"></script>

<script>
$(document).ready(function() {
	//업로드 파일에 대한 확장자 제한하는 정규식
	var uploadConstraintByExt = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
	//업로드 파일에 대한 최대크기를 제한
	var uploadMaxSize = 1036870912;
	
	//화면이 맨 처음 로드 시 들어 있는 깨끗한 상태 기억
	var initClearStatus = $("#uploadDiv").clone();
	
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
			success : function (result) {
				showUploadedFile(result);
				//업로드이후 청소
				$("#uploadDiv").html(initClearStatus.html());
			}
		});
	});

	// IE11까지 고려한 보여 준 이후에 클릭하면 사라지게합니다.
	$(".bigWrapper").on("click", function() {
		$(".bigNested").animate({width:'0%', height:'0%'}, 1000);
		setTimeout(function() {
			$(".bigWrapper").hide();
		}, 1000);
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
	
	// 이미지클릭시 자동재생 및 원본파일재생
	$("#uploadResult").on("click", "a", function(){
		var attachVo = $(this).closest("li").data("attach_info");
		attachVo = JSON.parse(decodeURL(attachVo));
		
		$(".bigWrapper").css("display", "flex").show();
		if (attachVo.multimediaType === "audio") {
			$(".bigNested").html("<audio src='/uploadFiles/display?fileName=" +  encodeURIComponent(attachVo.originalFileCallPath) + "' autoplay>"
					).animate({width:'100%', height:'100%'}, 1000);
		} else if (attachVo.multimediaType === "image") {
			$(".bigNested").html("<img src='/uploadFiles/display?fileName=" +  encodeURIComponent(attachVo.originalFileCallPath) + "'>"
					).animate({width:'100%', height:'100%'}, 1000);
		} else if (attachVo.multimediaType === "video") {
			$(".bigNested").html("<video src='/uploadFiles/display?fileName=" + encodeURIComponent(attachVo.originalFileCallPath) + "' autoplay>"
					).animate({width:'100%', height:'100%'}, 1000);	
		}
	});
	
	//첨부 취소하기
	$("#uploadResult").on("click", "span", function(){
		var targetLi = $(this).closest("li");
		var attachVo = targetLi.data("attach_info");
		attachVo = JSON.parse(decodeURL(attachVo));
		
		$.ajax({
			url : "/uploadFiles/deleteFile",
			data : attachVo,
			type : 'post',
			dataType : 'text',
			success : function (result) {
				targetLi.remove();
			}
		});
	});
});

function showUploadedFile(result) {
	var liTags = "";
	$(result).each(function (i, attachVoInJson) {
		var attachVo = JSON.parse(decodeURL(attachVoInJson));
		
		if (attachVo.multimediaType === "others") {
			liTags += "<li data-attach_info=" + attachVoInJson + "><a href='/uploadFiles/download?fileName=" 
					+ encodeURIComponent(attachVo.originalFileCallPath) + "'><img src='/resources/img/attachFileIcon.png'>" 
					+ attachVo.pureFileName + "</a><span>X</span></li>";
		} else {
			if (attachVo.multimediaType === "audio") {
				liTags += "<li data-attach_info=" + attachVoInJson + ">"
						+ "<a>"
						+ "<img src='/resources/img/audioThumbnail.png'>" 
						+ attachVo.pureFileName + "</a>" 
						+ "<span>X</span>" 
						+ "</li>";
			} else if (attachVo.multimediaType === "image" || attachVo.multimediaType === "video") {
				liTags += "<li data-attach_info=" + attachVoInJson + ">"
						+ "<a>"
						+ "<img src='/uploadFiles/display?fileName=" 
						+ encodeURIComponent(attachVo.fileCallPath) + "'>" + attachVo.pureFileName + "</a>" 
						+ "<span>X</span>"
						+ "</li>";
			} 	  
		}
	});
	$("#uploadResult ul").append(liTags);  //append 쓴이유  > 업로드 또하면
}

/**
 * 첨부 파일 기능은 여러 화면에서 재사용될 가능성이 높다.
 이를 각 화면에서 중복 개발하기 보다는 이곳에서 통합적으로 서비스 할 수 있도록 모듈화함
 */
function addAttachInfo(frmContainer, varName) {
	var inputAttaches = "";
	$("#uploadResult ul li").each(function(i, attchLi){
		var jobObj = $(attchLi);
		
		var attachVo = jobObj.data("attach_info");
		attachVo = JSON.parse(decodeURL(attachVo));
		
		inputAttaches += "<input type='hidden' name='" + varName +  "[" + i + "].uuid' value=" + attachVo.uuid + ">";
		inputAttaches += "<input type='hidden' name='" + varName +  "[" + i + "].savedFolderPath' value=" + attachVo.savedFolderPath + ">";
		inputAttaches += "<input type='hidden' name='" + varName +  "[" + i + "].pureFileName' value=" + attachVo.pureFileName + ">";
		inputAttaches += "<input type='hidden' name='" + varName +  "[" + i + "].multimediaType' value=" + attachVo.multimediaType + ">";
	});
	
	frmContainer.append(inputAttaches);
}
</script>