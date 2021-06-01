<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!-- list.jsp파일이 post 안에 있으므로 ../-->
<%@include file="../includes/header.jsp"%>

<!-- DataTales Example -->
<div class="card shadow mb-4">
	<div class="card-body">

		<form id='frmPost' action="/post/modifyPost" method="post">
			<%@include file="./includes/postCommon.jsp"%>

			<button type="submit" data-oper="modify" class="btn btn-primary">수정</button>
			<button type="submit" data-oper="remove" class="btn btn-danger">삭제</button>
			<button type="submit" data-oper="list" class="btn btn-secondary">목록</button>

			<input id="boardId" type="hidden" name="boardId" value="${boardId}">
			<input type="hidden" name="postId" value="${post.id}"> 
			<input type="hidden" name="pageNumber" value="${pagenation.pageNumber}">
			<input type="hidden" name="amount" value="${pagenation.amount}">
			
		</form>
	</div>
</div>

</div>
<!-- /.container-fluid -->

</div>
<!-- End of Main Content -->

<%@include file="../includes/footer.jsp"%>


<script>
	$(document).ready(function() {

		controlInput('수정');

		var frmPost = $('#frmPost');

		//처리 우선 순위가 있고 script가 html에 등록 되어 있는 것 보다 앞선다.
		$("button").on("click", function(eventInfo) {
			//이벤트 처리의 전파를 (주고 주고 주고 주고 ) 막아서 미리 개발되어있는 이벤트를 처리를 막는 함수 
			eventInfo.preventDefault(); // 브라우저의 기본동작 방지

			var oper = $(this).data("oper"); //this - 이벤트가 일어난 객체

			if (oper === 'remove') { //form에 들어있는 내용 바꿔서 
				frmPost.attr("action", "/post/removePost");
			} else if (oper === 'list') {

				/* 속성 선택자: 특정한 속성을 가지는 요소를 선택한다.
					p[class=“example”] { color: blue; }*/

				var boardIdInput = frmPost.find("#boardId");
				var pageNumber = $('input[name="pageNumber"]');
				var amount = $('input[name="amount"]');
				frmPost.attr("action", "/post/list").attr("method", "get") //체인기능 
				frmPost.empty();
				frmPost.append(boardIdInput);
				frmPost.append(pageNumber);
				frmPost.append(amount);
			}
			frmPost.submit();
		});
	});
</script>


