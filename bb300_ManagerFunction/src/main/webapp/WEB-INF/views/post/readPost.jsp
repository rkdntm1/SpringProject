<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@include file="../includes/header.jsp"%>
<!-- Begin Page Content -->
<div class="container-fluid">
	<div class="card shadow mb-4">
		<div class="card-body">
			<%@include file="./includes/postCommon.jsp"%>
			<sec:authentication property="principal" var="customUser"/>
				<sec:authorize access="isAuthenticated()">
					<c:if test="${customUser.curUser.userId eq post.writer.userId}">
						<button data-oper='modify' class="btn btn-primary">수정</button>
					</c:if>
				</sec:authorize>
		
			
			
			<button data-oper='list' class="btn btn-secondary">목록</button>

			<form id='frmOper' action="/post/modifyPost" method="get">
				<input type="hidden" name="boardId" value="${boardId}">
				<input type="hidden" id="postId" name="postId" value="${post.id}">
				<input type="hidden" name="pageNumber" value="${pagenation.pageNumber}">
				<input type="hidden" name="amount" value="${pagenation.amount}">
				<input type="hidden" name="searching" value='${pagenation.searching}'>
			</form>
			<%@include file="../common/attachFileManagement.jsp"%>
		</div>

		<%@include file="./includes/replyManagement.jsp"%>
		
	</div>
</div>
<!-- /.container-fluid -->


</div>
<!-- End of Main Content -->

<%@include file="../includes/footer.jsp"%>

<script type="text/javascript">
$(document).ready(function() {
	adjustCRUDAtAttach('조회');
	//딸라{post.attachList}
	<c:forEach var="attachVOInStr" items="${post.attachListInGson}">
      appendUploadUl('<c:out value="${attachVOInStr}" />');
   </c:forEach>
	
	
	$("button[data-oper='modify']").on("click", function() {
		$("#frmOper").submit();
	});

	$("button[data-oper='list']").on("click", function() {
		$("#frmOper").find("#postId").remove();
		$("#frmOper").attr("action", "/post/listBySearch").submit();
	});
	
});
</script>

