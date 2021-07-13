<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri= "http://www.springframework.org/security/tags" prefix="sec" %>

<jsp:useBean id="tablePrinter" class="www.dream.com.framework.printer.TablePrinter"/>


<div class="form-group">
	<label>아이디</label> <input name="id" value="${post.id}" class="form-control" readonly>
</div>

<div class="form-group">
	<label>제목</label> <input id="title" name="title" value="${post.title}" class="form-control" readonly>
</div>

<div class="form-group">
	<label>내용</label>
	<!-- textarea id="content" 이렇게는 오작동. 원인 파악 후 버그 레포팅 -->
	<textarea id="txaContent" name="content" class="form-control" rows="3" readonly>${post.content}</textarea>
</div>

<div class="form-group">
	<c:choose>
	    <c:when test="${empty post}">
			 <input value= '<sec:authentication property="principal.curUser.name"/>' class="form-control" readonly>
	    </c:when>
	    <c:otherwise>
	        <input value="${post.writer.name}" class="form-control" readonly>
	    </c:otherwise>
	</c:choose>	
</div>

<div class="form-group">
	<label>조회수</label> <input value="${post.readCnt}" readonly>
	<label>좋아요</label> <input value="${post.likeCnt}" readonly>
	<label>싫어요</label> <input value="${post.dislikeCnt}" readonly>
</div>

<div class="form-group">
	<label>등록시점 : </label> 
	<fmt:formatDate pattern="yyyy-MM-dd" value="${post.registrationDate}"/>
	<label>, 수정시점 : </label> 
	<fmt:formatDate pattern="yyyy-MM-dd" value="${post.updateDate}"/>
</div>
<input type='hidden' name='${_csrf.parameterName}' value='${_csrf.token}'>

<script type="text/javascript">
	<!-- 수정 처리 시 title, content에는 readonly는 없어야 함 -->
	<!-- 신규 처리 시 title, content에는 value가 없고 readonly는 없어야 함 -->
	function controlInput(includer) {
		if (includer === '수정' || includer === '신규') {
			$('#title').attr("readonly", false);
			$('#txaContent').attr("readonly", false);
		}
	}
</script>



				
	
