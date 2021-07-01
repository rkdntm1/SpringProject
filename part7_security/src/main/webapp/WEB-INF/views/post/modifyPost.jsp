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
			<form id='frmPost' action="/post/modifyPost" method="post">
				<%@include file="./includes/postCommon.jsp"%>
				
				<%@include file="../common/attachFileManagement.jsp"%>
				
				<!-- data-oper: data(button 엘리먼트에게 oper라는 변수명에 modify라는 값을 추가해줄거다) -->
				<sec:authentication property="principal" var="customerUser"/>
				<sec:authorize access="isAuthenticated()">
					<c:if test="${customerUser.curUser.userId eq post.writer.userId}">
						<button type="submit" data-oper='modify' class="btn btn-primary">수정</button>
						<button type="submit" data-oper='remove' class="btn btn-danger">삭제</button>							
					</c:if>
				</sec:authorize>
				<button type="submit" data-oper='list' class="btn btn-secondary">목록</button>	
				
				<input id="boardId" type="hidden" name="boardId" value="${boardId}">
				<input type="hidden" name="postId" value="${post.id}">
				<input type="hidden" name="pageNumber" value="${pagenation.pageNumber}">
				<input type="hidden" name="amount" value="${pagenation.amount}">
				<input type="hidden" name="searching" value="${pagenation.searching}">
				
				<input type='hidden' name='${_csrf.parameterName}' value='${_csrf.token}'>
			</form>
		</div>
	</div>
</div>
<!-- /.container-fluid -->

</div>
<!-- End of Main Content -->

<%@include file="../includes/footer.jsp"%>

<script>
$(document).ready(function(){
	controlInput('수정');
	adjustCRUDAtAttach('수정');
	
	<c:forEach var="attachVoInStr" items="${post.attachListInGson}">
	appendUploadUl('<c:out value="${attachVoInStr}"/>');
	</c:forEach>
	
	var frmPost = $("#frmPost");
	//처리 우선 순위가 있고 script가 html에 등록 되어 있는 것 보다 앞선다.
	$("button").on("click", function(eventInfo){
		//이벤트 처리의 전파를 막아서 어떤 미리 개발되어있는 이벤트 처리를 막는 함수
		eventInfo.preventDefault();  // 브라우저의 기본동작 방지
		
		var oper = $(this).data("oper");  //this - 이벤트가 일어난 객체
		if (oper === 'remove') { //form에 들어있는 내용 바꿔서 
			frmPost.attr("action", "/post/removePost");	
		} else if (oper === 'list') {
			// empty하기전에 list로 돌아갈때 필요한 속성들을 저장해놓음
			var boardIdInput = frmPost.find("#boardId");  
			var pageNumberInput = $('input[name="pageNumber"]');
			var amountInput = $('input[name="amount"]');
			
			var searching = $('input[name="searching"]');
			
			frmPost.attr("method", "get");
			frmPost.attr("action", "/post/listBySearch");
			//empty : form에 담겨이쓴 모든 하위 요소를 없애라(필요없는 속성값들을 없애줌)
			frmPost.empty(); 
			//목록으로 갈때 보던 쪽수로 가기위해서  boardIdInput pageNumberInput amountInput가 필요함
			frmPost.append(boardIdInput); 
			frmPost.append(pageNumberInput); 
			frmPost.append(amountInput); 
			
			frmPost.append(searching);
		} else if (oper === 'modify') {
			addAttachInfo(frmPost, "listAttachInStringFormat");
		}
		frmPost.submit();
	});
});
	
</script>