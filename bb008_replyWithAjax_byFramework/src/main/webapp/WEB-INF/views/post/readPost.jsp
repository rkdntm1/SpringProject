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
	</div>
</div>
<!-- /.container-fluid -->

</div>
<!-- End of Main Content -->

<%@include file="../includes/footer.jsp"%>

<script src="\resources\js\post\reply.js"/>

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
	});
</script>