<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="www.dream.com.bulletinBoard.model.PostVO" %>

<%@include file="../includes/header.jsp"%>

<!-- Begin Page Content -->
<div class="container-fluid">

	<!-- DataTales Example -->
	<div class="card shadow mb-4">
		<div class="card-header py-3">
			<h6 class="m-0 font-weight-bold text-primary">${boardName} 글 목록</h6>
		</div>
		<div class="card-body">
		<!-- Paging 이벤트에서 서버로 요청보낼 인자들을 관리합니다. -->
			<form id='frmSearching' action='/post/listBySearch' method='get'>
				<input type="text" name="searching" value="${pagenation.searching}">
				<button id="btnSearch" class='btn btn-default'>검색</button>
				<button id="btnRegisterPost">글쓰기</button>	
				
				<input type="hidden" name="boardId" value="${boardId}">
				<input type="hidden" name="pageNumber" value="${pagenation.pageNumber}">
				<input type="hidden" name="amount" value="${pagenation.amount}">
				
			</form>
			
			<div class="table-responsive">
				<table class="table table-bordered" id="dataTable" width="100%"
					cellspacing="0">
					<thead>
						<tr>
							<th>제목</th>
							<th>작성자</th>
							<th>조회수</th>
							<th>수정일</th>
						</tr>
					</thead>
					
					<tbody>
						<c:forEach items="${listPost}" var="post">
							<tr>
								<td>
									<!-- id는 1개만 class는 여러개의 객체 가능 -->
									<a class="anchor4post" href="${post.id}">${post.title}</a></td>
								<td>${post.writer.name}</td>
								<td>${post.readCnt}</td>
								<td><fmt:formatDate pattern="yyyy-MM-dd" value="${post.updateDate}"/>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				
				<!-- 페이징 처리 -->
				<div class='fa-pull-right'>
					<ul id='ulPagination' class='pagination'>
						<c:if test="${pagenation.prev}">
							<li class="page-item previous">
								<a class='page-link' href="${pagenation.startPage - 1}">&lt;&lt;</a>
							</li>
						</c:if>
						<c:forEach var="num" begin="${pagenation.startPage}" end="${pagenation.endPage}">
							<li class='page-item ${pagenation.pageNumber == num ? "active" : ""}'>
								<a class='page-link' href="${num}">${num}</a>
							</li>
						</c:forEach>
						<c:if test="${pagenation.next}">
							<li class="page-item next">
								<a class='page-link' href="${pagenation.endPage + 1}">&gt;&gt;</a>
							</li>
						</c:if>
					</ul>
				</div>

				
				<!-- Modal -->
				<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
					aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-hidden="true">&times;</button>
								<h4 class="modal-title" id="myModalLabel">Modal title</h4>
							</div>
							<div class="modal-body">처리가 완료 되었습니다.</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default"
									data-dismiss="modal">Close</button>
								<button type="button" class="btn btn-primary">Save
									changes</button>
							</div>
						</div>
						<!-- /.modal-content -->
					</div>
					<!-- /.modal-dialog -->
				</div>
				<!-- /.modal -->
				
			</div>
		</div>
	</div>

</div>
<!-- /.container-fluid -->

</div>
<!-- End of Main Content -->

<%@include file="../includes/footer.jsp"%>

<script>
$(document).ready(function(){
	var frmSearching = $('#frmSearching');
	
	//var boardId = '<c:out value="${boardId}"/>';
	$("#btnRegisterPost").on("click", function() {
		frmSearching.attr('action', '/post/registerPost');
		frmSearching.submit();
	});
	
	var result = '<c:out value="${result}"/>';
	
	checkModal(result); //함수를 호출
	
	history.replaceState({}, null, null);
	
	function checkModal(result) {
		if (result === '' || history.state) {
			return;
		}
		if (result.length == ${PostVO.ID_LENGTH}) { // DB에 게시글 ID 가 5자리라서 상수로 정의하여 유지보수 업
			$(".modal-body").html("게시글 " + result + "번이 등록되었습니다.");
		} else {
			$(".modal-body").html("게시글에 대한 "+ result + "하였습니다");
		}
		
		$("#myModal").modal("show");
	}
	

	$('#btnSearch').on('click', function(eInfo){
		eInfo.preventDefault();
		
		//공백으로 검색했을시(파괴자) 오류발생하여 trim을 통하여 해결
		if ($('input[name="searching"]').val().trim() === '') { // 검색어 입력 없이 검색 눌렀을시 메세지 호출
			alert('검색어를 입력하세요');
			return;
		}
		//신규 조회이므로 1쪽을 보여줘야합니다.
		$("input[name='pageNumber']").val("1"); // 검색했을때 1페이지로 이동시키기
		
		frmSearching.submit();
	});
	
	/* 페이징 처리에서 특정 쪽 번호를 클릭 하였을 때 해당 페이지의 정보를 조회하여 목록을 재 출력해 줍니다.*/
	$('.page-item a').on('click',function(eInfo){
		eInfo.preventDefault();
		$("input[name='pageNumber']").val($(this).attr('href'));
		frmSearching.submit();
	});
	
	/* 특정 게시물에 대한 상세 조회 처리 */
	$('.anchor4post').on('click',function(e) {
		e.preventDefault();
		var postId = $(this).attr('href');
		frmSearching.append("<input name='postId' type='hidden' value='" + postId + "'>");
		frmSearching.attr('action', '/post/readPost');
		frmSearching.attr('method', 'get');
		frmSearching.submit();
	});
});
</script>

