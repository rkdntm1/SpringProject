<!-- 게시글 상세 화면(readPost.jsp)에서 이 모든 것이 들어가 있으면 너무 복잡해집니다.
따라서 분할 정복으로 복잡도를 관리. 이는 유지보수성 향상 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!-- 댓글 목록 출력 부분 -->
<div class="card-footer">
	<div class="card-header">
		댓글			
		<button id="btnOpenReplyModalForNew" class="btnOpenReplyModalForNew btn btn-primary btn-xs pull-right">댓글달기</button>
	</div>
	<div class="card-body">
		<!-- 원글에 달린 댓글 목록 페이징으로 출력하기 -->
		<ul id="ulReply">
		</ul>
	</div>
	
	<!-- 페이징 처리 -->
	<div id="replyPaging" class='fa-pull-right'>
	</div>
</div>

<!-- 댓글 입력 용도 modal 창 -->
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
					<label>작성자</label> 
					<input class="form-control" name='replyer' value=''>
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

<script src="\resources\js\post\reply.js"></script>
<script src="\resources\js\util\dateFormat.js"></script>

<script>
	var ulReply = $("#ulReply");
	var postId = "${post.id}";
	var currentPageNum = 1;
	var replyPaging = $("#replyPaging");
	
	showReplyList(1);
	
	function showReplyList(pageNum) {
		replyService.getReplyList(
			{
				orgId:postId,
				page:pageNum || 1
			},
			function(listReplyWithCount) {
				var criteria = listReplyWithCount.first;
				var listReply = listReplyWithCount.second;
				
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
					if (listReply[i].replyCnt > 0) {
						strLiTags += '		<i>[' + listReply[i].replyCnt + ']</i>';
					}
					strLiTags += '			<strong>' + listReply[i].writer.name + '</strong>';
					strLiTags += '			<small>' + dateFormatService.getWhenPosted(listReply[i].updateDate) + '</small>';
					strLiTags += '			<button class="btn btn-primary btn-xs pull-right">대댓</button>';
					strLiTags += '		</div>';
					strLiTags += '		<p>' + listReply[i].content + '</p>';
					strLiTags += '	</div>';
									/* 대댓글 전체목록 삽입 장소
									그 구성 형식은 댓글 깊이에 따라서 다음과 같음
									<ul>
										<li>
											<ul>
												<li></li>
												<li></li>
											</ul>
										</li>
									</ul>
									*/
					strLiTags += '</li>';
				}
				ulReply.html(strLiTags);
				
				replyPaging.html(criteria.pagingDiv);
			},
			function(errMsg) {
				alert("댓글 등록 오류 : " + errMsg);
			}
		);			
	}
	
	var modalReply = $("#modalReply");
	var inputReplyContent = modalReply.find("input[name='replyContent']");
	var inputReplyer = modalReply.find("input[name='replyer']");
	var inputReplyDate = modalReply.find("input[name='replyDate']");
	
	var btnRegisterReply = $("#btnRegisterReply");
	var btnModifyReply = $("#btnModifyReply");
	var btnRemoveReply = $("#btnRemoveReply");
	
	//댓글에서 대댓글 전체 조회하기
	ulReply.on("click", "li i", function(e) {
		e.preventDefault(); //이벤트가 타고타고 들어가서 발생될 수 있는 문제를 막아줌.
		
		var clickedLi = $(this).closest("li");
		var reply_id = clickedLi.data("reply_id");
		
		replyService.getReplyListOfReply(
				reply_id,
				function(listReply) {
					//댓글이 있으면 li로 만들어서 ul에 담을 것
					strLiTags = clickedLi.html();// li의 원래내용
					strLiTags += printReplyOfReplyByRecursion(listReply, 0, true);
					clickedLi.html(strLiTags);
				},
				function(errMsg) {
					alert("댓글 등록 오류 : " + errMsg);
				} 
			);	
	});
	
	/* 내것이 앞에 것 보다 깊으면 재귀 호출하고 앞의 것의 닫기 tag를 완성해줌
	<ul>
		<li>3댓글</li>
		<li>3댓글
	 		<ul>
	  			<li>4대댓글</li>
	  			<li>4대댓글</li>
	  		</ul>
	  	</li>
		<li>3댓글</li>
	</ul>
	*/
	function printReplyOfReplyByRecursion(listReply, startIdxm, startWithUl) {
		var strLiTags = "";
		
		if (startWithUl) {
			strLiTags = "<ul>";	
		}
		
		var prev = null;
		
		for (var i = startIdx, len = listReply.length; i < len; i++) {
			if (prev == null) {
				strLiTags += '<li class="clearfix" data-reply_id = "' + listReply[i].id + '">';
				prev = listReply[i];
			} else if (prev.depth == listReply[i].depth) {
				strLiTags += '</li>';
				strLiTags += '<li class="clearfix" data-reply_id = "' + listReply[i].id + '">';
				prev = listReply[i];
			} else if (prev.depth < listReply[i].depth) {
				strLiTags += printReplyOfReplyByRecursion(listReply, i, true);
			} else if (prev.depth > listReply[i].depth) {
				strLiTags += printReplyOfReplyByRecursion(listReply, i, false)
			}
		}
		strLiTags += "</li>";
		strLiTags += "</ul>";
		return strLiTags; 
	}
	
	//댓글 신규 용도의 모달 창 열기
	$("#btnOpenReplyModalForNew").on("click", function(e) {
		modalReply.data("original_id", postId); // 창 띄울때 어떤 글에 달아줘야할지 기억시켜줌
		showModalForCreate();
	});
	
	//대댓글 신규 용도 모달 창 열기. (자손 결합자 이용)
	ulReply.on("click", "li button", function(e) {
		e.preventDefault(); //이벤트가 타고타고 들어가서 발생될 수 있는 문제를 막아줌.
		
		var clickedLi = $(this).closest("li");
		modalReply.data("original_id", clickedLi.data("reply_id")); // 대댓글 달때는 댓글의 아이디를 기억시킴
		showModalForCreate();
	});
	
	function showModalForCreate() {
		//모달에 들어 있는 모든 내용 청소
		modalReply.find("input").val("");
		//신규 댓글 달기 시에는 등록일자는 Default 처리. 따라서 보여줄 필요가 없음. 
		inputReplyDate.closest("div").hide();
		
		btnModifyReply.hide();
		btnRemoveReply.hide();
		btnRegisterReply.show();
		
		modalReply.modal("show");
	}
	
	$("#btnCloseModal").on("click", function(e) {
			modalReply.find("input").val("");
			modalReply.modal("hide");
	});
	//목록에서 특정 댓글을 클릭하면 해당 댓글의 상세정보를 Ajax-rest로 읽고 이를 모달창에 보여준다.
	//특정 댓글은 동적으로 추가된 것이기에 이벤트 위임 방식을 활용하여야한다.(p424)
	ulReply.on("click", "li p", function(e) {
		var clickedLi = $(this).closest("li");
		
		replyService.getReply(
			clickedLi.data("reply_id"),
			function(replyObj) {
				//수정 또는 삭제 시 댓글의 id가 필요함.
				modalReply.data("reply_id", replyObj.id);
				inputReplyContent.val(replyObj.content);
				inputReplyer.val(replyObj.writer.name);
				inputReplyDate.val(dateFormatService.getWhenPosted(replyObj.updateDate));
				
				inputReplyer.attr("readonly", "readonly");
				inputReplyDate.attr("readonly", "readonly");
				
				btnModifyReply.show();
				btnRemoveReply.show();
				btnRegisterReply.hide();
				
				modalReply.modal("show");
			},
			function(errMsg) {
				alert("댓글 조회 오류 : " + errMsg);
			}
		);
	});
	
	//모달창에서 사용자가 입력한 다음에 등록 버튼을 누르면 댓글로 등록하고 목록을 1쪽부터 다시보여준다.
	btnRegisterReply.on("click", function(e) {
		var reply =	{
			content : inputReplyContent.val()
		};
		
		replyService.addReply(
			modalReply.data("original_id"), 
			reply,
			function(newReplyId) {
				modalReply.find("input").val(""); //들어 있는 값 청소
				modalReply.modal("hide");
				//목록을 1쪽부터 다시보여준다.
				showReplyList(1);
			},
			function(errMsg) {
				alert("댓글 등록 오류 : " + errMsg);
			}
		);
	});
	
	//댓글 상세 내용이 모달창에 나타났으며 작성자가 그 내용을 수정하고 수정버튼을 누르면 DB에 내용을 반영하고
	//모달창 닫고 목록으로 돌아온다.
	btnModifyReply.on("click", function(e) {
		replyService.updateReply(
			{
				id : modalReply.data("reply_id"),
				content : inputReplyContent.val()
			},
			function(resultMsg) {
				modalReply.modal("hide");
				//목록을 1쪽부터 다시보여준다.
				showReplyList(currentPageNum);
			},
			function(errMsg) {
				alert("댓글 수정 오류 : " + errMsg);
			}
		);
	});
	
	//댓글 상세 내용이 모달창에 나타났으며 작성자가 삭제버튼을 누르면 DB에 내용을 반영하고
	//모달창 닫고 목록으로 돌아온다.
	btnRemoveReply.on("click", function(e) {
		replyService.removeReply(
			modalReply.data("reply_id"),
			function(delResult) {
				modalReply.modal("hide");
				//목록을 1쪽부터 다시보여준다.
				showReplyList(currentPageNum);
			},
			function(errMsg) {
				alert("댓글 삭제 오류 : " + errMsg);
			}
		);
	});
	
	//페이징 중 하나를 선택하면 해당 쪽의 댓글 목록을 조회하여 갱신한다.
	replyPaging.on("click", "li a", function(e) {
		e.preventDefault(); //이벤트가 타고타고 들어가서 발생될 수 있는 문제를 막아줌.
		
		var clickAnchor = $(this);
		currentPageNum = clickAnchor.attr("href");
		  
		showReplyList(currentPageNum); 		
	});
</script>