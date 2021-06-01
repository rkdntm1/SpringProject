<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- list.jsp파일이 post 안에 있으므로 ../-->
<%@include file="../includes/header.jsp" %>

                    <!-- DataTales Example -->
                    <div class="card shadow mb-4">
                        <div class="card-body">
                            
                           <form action="/post/registerPost" method="post" >
                           
                           <%@include file="./includes/postCommon.jsp"%>
	                            
								<button type="submit" class="btn btn-primary">등록</button>
								<button type="reset" class="btn btn-secondary">초기화</button>
								<input type="hidden" name="boardId" value="${boardId}">
                           </form>
                        </div>
                    </div>

                </div>
                <!-- /.container-fluid -->

            </div>
            <!-- End of Main Content -->

            <%@include file="../includes/footer.jsp" %>


<script type="text/javascript">
        	$(document).ready(function() {
        		controlInput('신규')
        	});
</script>