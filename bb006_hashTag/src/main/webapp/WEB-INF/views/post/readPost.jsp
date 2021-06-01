<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!-- list.jsp파일이 post 안에 있으므로 ../-->
<%@include file="../includes/header.jsp"%>

<!-- DataTales Example -->
<div class="card shadow mb-4">
	<div class="card-body">
		<%@include file="./includes/postCommon.jsp"%>


		<button data-oper="modify" class="btn btn-primary">수정</button>
		<button data-oper="list" class="btn btn-secondary">목록</button>


		<form id="frmOper" action="/post/modifyPost" method="get">
			<input type="hidden" id="boardId" name="boardId" value="${boardId}">
			<input type="hidden" id="postId" name="postId" value="${post.id}">
			<input type="hidden" name="pageNumber"
				value="${pagenation.pageNumber}"> <input type="hidden"
				name="amount" value="${pagenation.amount}">
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

		$("button[data-oper='modify']").on("click", function() {
			$("#frmOper").submit();
		});

		$("button[data-oper='list']").on("click", function() {
			$("#frmOper").find("#postId").remove();
			$("#frmOper").attr("action", "/post/list").submit();
		});

	});
</script>

