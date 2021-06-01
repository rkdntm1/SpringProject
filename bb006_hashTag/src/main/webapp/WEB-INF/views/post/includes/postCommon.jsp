<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!-- 수정처리 시 title, content는 readonly는 없어야 한다. -->
<!-- 신규처리 시 title, content는 value가 없고 readonly는없어야 한다. -->

<div class="form-group">
	<label>아이디</label> <input name="id" value="${post.id}"
		class="form-control" readonly>
</div>

<div class="form-group">
	<label>제목</label> <input id="title" name="title" value="${post.title}" class="form-control" readonly>
</div>

<div class="form-group">
	<label>내용</label>
	<textarea id="txaContent" name="content" rows="3" class="form-control" readonly>${post.content}</textarea>
</div>


<div class="form-group">
	<label>작성자</label> <input class="form-control" value="${post.writer.name}" readonly>
</div>


<div class="form-group">
	<p>
		<label>조회수</label> 
		<b> ${post.readCnt} </b> 
		<label>좋아요</label>
		 <i>${post.likeCnt} </i> 
			<label>싫어요</label> 
			<strong> ${post.dislikeCnt} </strong>
	</p>
</div>

<div class="form-group">
	<label>등록시점 :</label>
	<fmt:formatDate pattern="yyyy-MM-dd" value="${post.registrationDate}" />
	<label>, 수정시점 :</label>
	<fmt:formatDate pattern="yyyy-MM-dd" value="${post.updateDate}" />
</div>


<script type="text/javascript">

/* 공통화페이지 에서는  $(document).ready(function() {})으로 감싸면 가시성이 떨어진다. 주의할것 */ 


<!-- 수정 처리 시 title, content는 readonly는 없어야 한다. -->
<!-- 신규 처리 시 title, content는 value가 없고 readonly는없어야 한다. -->

function controlInput(includer) {
	if (includer === '수정' || includer === '신규'){
		$('#title').attr("readonly", false);
		$('#txaContent').attr("readonly", false);
		//document.getElementById("#txaContent").readOnly = false; 
	}
}

</script>
