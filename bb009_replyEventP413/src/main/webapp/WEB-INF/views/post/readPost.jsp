<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@include file="../includes/header.jsp"%>
<!-- Begin Page Content -->
<div class="container-fluid">
	<!-- DataTales Example -->
	<div class="card shadow mb-4">
		<div class="card-body">
			<%@include file="./includes/postCommon.jsp"%>

			<!-- data-oper: data(button 엘리먼트에게 oper라는 변수명에 modify라는 값을 추가해줄거다) -->
			<button data-oper='modify' class="btn btn-primary">수정</button>
			<button data-oper='list' class="btn btn-secondary">목록</button>

			<form id="frmOper" action="/post/modifyPost" method="get">
				<input type="hidden" name="boardId" value="${boardId}">
				<input type="hidden" id="postId" name="postId" value="${post.id}">
				<input type="hidden" name="pageNumber"	value="${pagenation.pageNumber}">
				<input type="hidden" name="amount" value="${pagenation.amount}">
				<input type="hidden" name="searching" value="${pagenation.searching}">		
			</form>
		</div>
		
		<div class="card-footer">
			<div class="card-header">
				댓글			
				<button id="btnOpenReplyModalForNew" class="btn btn-primary btn-xs pull-right">댓글달기</button>
			</div>
			<div class="card-body">
				<ul id="ulReply">
				</ul>
			</div>
		</div>		
	</div>
</div>
<!-- /.container-fluid -->

<!-- 댓글용도 modal 창 -->
<div id="modalReply" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        		<h4 class="modal-title" id="myModalLabel">REPLY MODAL</h4>
			</div>	<!-- End of modal-header -->
			<div class="modal-body">
				<div class="form-group">
					<label>Reply</label> 
					<input class="form-control" name='replyContent' value='New Reply!!!!'>
				</div>
				<div class="form-group">
					<label>Reply Date</label> 
					<input class="form-control" name='replyDate' value=''>
				</div>
			</div>	<!-- End of modal-body -->
			<div class="modal-footer">
				<button id='btnModifyReply' type="button" class="btn btn-warning">Modify</button>
				<button id='btnRemoveReply' type="button" class="btn btn-danger">Remove</button>
				<button id='btnRegisterReply' type="button" class="btn btn-primary">Register</button>
				<button id='btnCloseModal' type="button" class="btn btn-default">Close</button>
			</div>	<!-- End of modal-footer -->
		</div>	<!-- End of modal-content -->
	</div>	<!-- End of modal-dialog -->
</div>
<!-- End of 댓글 입력 모달창 -->

</div>
<!-- End of Main Content -->

<%@include file="../includes/footer.jsp"%>

<script src="\resources\js\post\reply.js"></script>
<script src="\resources\js\util\dateFormat.js"></script>

<script>
	$(document).ready(function() {
		//속성선택자를 통해서 button의 data-oper 속성 'modify'를 가져와서 클릭 이벤트 처리
		//*-전체선택자, .-클래스선택자, # - 아이디선택자, [] - 속성선택자, :- 의사선택자
		$("button[data-oper='modify']").on("click", function() {
			$("#frmOper").submit();
		});

		//속성선택자를 통해서 button의 data-oper 속성 'list'를 가져와서 클릭 이벤트 처리
		$("button[data-oper='list']").on("click", function() {
			$("#frmOper").find("#postId").remove(); //list로 갈때는 postId속성은 필요없으므로 삭제
			$("#frmOper").attr("action", "/post/listBySearch").submit(); //action 속성을 "/post/list"로 변경 
		});
		
		
		var ulReply = $("#ulReply");
		var originalId = "${post.id}";
		showReplyList(1);
		
		function showReplyList(pageNum) {
			replyService.getReplyList(
				{orgId:originalId, page:pageNum || 1},
				function(listReply) {
					if (listReply == null || listReply.length == 0) {
						//정보가 없을 시 UL에 담긴 내용 청소
						ulReply.html("");
						return;
					}
					//댓글이 있으면 li로 만들어서 ul에 담을 것
					strLiTags = "";
					for (var i = 0, len = listReply.length || 0; i < len; i++) {
						strLiTags += '<li class="clearfix" data-reply_id = "' + listReply[i].id + '">';
						strLiTags += '	<div>';
						strLiTags += '		<div>';
						strLiTags += '			<strong>' + listReply[i].writer.name + '</strong>';
						strLiTags += '			<small>' + dateFormatService.getWhenPosted(listReply[i].updateDate) + '</small>';
						strLiTags += '		</div>';
						strLiTags += '		<p>' + listReply[i].content + '</p>';
						strLiTags += '	</div>';
						strLiTags += '</li>';
					}
					ulReply.html(strLiTags);
				},
				function(errMsg) {
					alert("댓글 등록 오류 : " + errMsg);
				}
			);			
		}
		
		//replyService 모듈
		
		replyService.getReply(
			"0000400005",
			function(replyObj) {
				alert("댓글 상세: " + replyObj);
			},
			function(errMsg) {
				alert("댓글 조회 오류 : " + errMsg);
			}
		);
		
		var modalReply = $("#modalReply");
		var inputReplyContent = modalReply.find("input[name='replyContent']");
		var inputReplyDate = modalReply.find("input[name='replyDate']");
		
		var btnModifyReply = $("#btnModifyReply");
		var btnRemoveReply = $("#btnRemoveReply");
		var btnRegisterReply = $("#btnRegisterReply");

		//댓글 신규 용도의 모달 창 열기
		$("#btnOpenReplyModalForNew").on("click", function(e) {
			//모달에 들어 있는 모든 내용 청소
			modalReply.find("input").val("");
			//신규 댓글 달기 시에는 등록일자는 Default 처리. 따라서 보여줄 필요가 없음. 
			inputReplyDate.closest("div").hide();
			
			btnModifyReply.hide();
			btnRemoveReply.hide();
			btnRegisterReply.show();
			
			modalReply.modal("show");
		});
		
		replyService.addReply(
			originalId,
			{content:"테스트 용도로 넣는 댓글"},
			function(newReplyId) {
				alert("신규 댓글 id: " + newReplyId);
			},
			function(errMsg) {
				alert("댓글 등록 오류 : " + errMsg);
			}
		);
		
		replyService.updateReply(
			{
				id:"0000400005",
				content:"수정했다. 됐나?"
			},
			function(resultMsg) {
				alert("댓글 수정 성공 : " + resultMsg);
			},
			function(errMsg) {
				alert("댓글 수정 오류 : " + errMsg);
			}
		);
		
		replyService.removeReply(
			"20000J",
			function(delResult) {
				alert("댓글 삭제 성공 " + delResult);
			},
			function(errMsg) {
				alert("댓글 삭제 오류 : " + errMsg);
			}
		);
		
	});
</script>