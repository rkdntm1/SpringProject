<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:useBean id="tablePrinter" class="www.dream.com.framework.printer.TablePrinter"/>
	
<div class="form-group">
	<label>아이디</label> <input name="id" value="${post.id}" class="form-control" readonly>
</div>

<div class="form-group">
	<label>제목</label> <input id="title" name="title" value="${post.title}" class="form-control" readonly>
</div>

<div class="form-group">
	<label>내용</label> <textarea id="txaContent" name="content" class="form-control" row="5" readonly>${post.content}</textarea>
</div>

<div class="form-group">
	<label>작성자</label>
	<input value="${post.writer.name}" class="form-control" readonly>
</div>

<div class="form-group">
	<label>조회수</label> <input value="${post.readCnt}" readonly>
	<label>좋아요</label> <input value="${post.likeCnt}" readonly>
	<label>조회수</label>	<input value="${post.dislikeCnt}" readonly>
</div>

<div class="form-group">
	<label>등록시점 : </label> 
	<fmt:formatDate pattern="yyyy-MM-dd" value="${post.registrationDate}" />
	<label>, 수정시점 : </label>
	<fmt:formatDate pattern="yyyy-MM-dd" value="${post.updateDate}" />
</div>

<script type="text/javascript">
	<!-- 수정 처리 시 title, content에는 readonly는 없어야 함 -->
	<!-- 신규 처리 시 title, content에는 value가 없고 readonly가 없어야 함 -->
	//자바스크립트에서는 $(document).ready 로 감싼 함수를 불러오지 못함. 
	function controlInput(includer) {
		if (includer === '수정' || includer === '신규') {
			$('#title').attr("readonly", false);
			//id 값으로 'content'를 쓰는 것은 적용되지않을 수 있어서 안쓰는것이 좋다.
			//$('#txaContent').attr("readonly", false);
			document.getElementById("txaContent").readOnly = false;
		}
	}
</script>
