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
			<form id="frmMember" method="post" action="/party/joinMember">
			
				<div class="form-group">
					<label>아이디</label> <input id="userId" name="userId" placeholder="유일성있게 해봐" class="form-control">
					<button id="idCheck">아이디중복검사</button>
				</div>
				
				<div id="PwdCheck" class="form-group">
					<label>비번</label> <input id="userPwdOrgin" name="userPwd" type="password" class="form-control">
					<label>비번확인</label> <input id="userPwdCheck" name="userPwd" type="password" class="form-control">
					<p id="pwCheckMsg"></p>
				</div>
				
				<div class="form-group">
					<label>회원닉네임</label> <input name="name" placeholder="재밌게 써봐요" class="form-control">
				</div>
				<!-- 가변성에 좋다 -> 유지보수성 긋  -->
				<c:forEach items="${listCpType}" var="contactPointType" varStatus="status">
					<div class="form-group">
						<label>${contactPointType.description}</label> 
						<input type="hidden" name="listContactPoint[${status.index}].contactPointType" value="${contactPointType.cpType}" class="form-control" readonly>
						<input name="listContactPoint[${status.index}].info" class="form-control">
					</div>
				</c:forEach>
								
				<input type='hidden' name='${_csrf.parameterName}' value='${_csrf.token}'>
				<button id="btnJoin" type="submit" class="btn btn-primary">회원가입</button>
				<button type="reset" class="btn btn-secondary">초기화</button>
			</form>
		</div>
	</div>
</div>
<!-- /.container-fluid -->

<%@include file="../common/attachFileManagement.jsp"%>

</div>
<!-- End of Main Content -->

<%@include file="../includes/footer.jsp"%>
<script>
$(document).ready(function(){
	var csrfHN = "${_csrf.headerName}";
	var csrfTV = "${_csrf.token}";
	
	var frmMember = $("#frmMember");
	
	$(document).ajaxSend(
		function (e, xhr) {
			xhr.setRequestHeader(csrfHN, csrfTV);
		}
	);
	
	//회원 ID가 유일한가를 Ajax로 검사하고, 그렇지 못할 때는 Focus를 다시 받아야합니다.
	$("#idCheck").on("click", function(){
		id = $("#userId").val();
		
		$.ajax({	
		    url: '/party/idCheck',
		    type: 'POST',
		    dataType: 'text', //서버로부터 내가 받는 데이터의 타입
		    contentType : 'text/plain; charset=utf-8;',//내가 서버로 보내는 데이터의 타입
		    data: id,

		    success: function(data){
		    	console.log(data);
		         if(data == 0){
		         console.log("아이디 없음");
		         alert("사용하실 수 있는 아이디입니다.");
		         }else{
		         	console.log("아이디 있음");
		         	alert("중복된 아이디가 존재합니다.");
		         }
		    },
		    error: function (){        
		    }
		  });
	});
	
	$('#PwdCheck').keyup(function(){
		if($('#userPwdOrgin').val()!=$('#userPwdCheck').val()){
			$('#pwCheckMsg').text('');
	  		$('#pwCheckMsg').html("<font color='#FF3333'>패스워드 확인이 불일치 합니다. </font>");
	 	}else{
		  	$('#pwCheckMsg').text('');
		  	$('#pwCheckMsg').html("<font color='#70AD47'>패스워드 확인이 일치 합니다.</font>");
	 	}
	});
	
	
	$("#btnRegisterPost").on("click", function(e) {
	});
});
</script>