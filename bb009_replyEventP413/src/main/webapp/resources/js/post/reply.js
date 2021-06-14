/** 모듈 패턴	
 * 이벤트, DOM, Ajax 처리 등을 하나의 jsp에 다 넣기에는 복잡해짐 유지보수성 저하
 * 이에 javascript를 별도 file로 만들어 분할 정복
 */

/** 함수 정의 영역 
CallBack 함수 : 특정 이벤트시에 이를 불러 준다.
*/
var replyService = (function() {
	function getList(orgIdAndPage, successCallBack, errorCallBack) {
			var page = orgIdAndPage.page || 1;
			//https://api.jquery.com/jquery.getjson/
			$.getJSON(
				"/replies/pages/" + orgIdAndPage.orgId + "/" + page,
				function(listReply) {
					if (successCallBack) {
						successCallBack(listReply);
					}
				}
			).fail(
				function(xhr, status, errMsg){
					if (errorCallBack) {
						errorCallBack(errMsg);
					}
				}
			);
		}

	function get(replyId, successCallBack, errorCallBack) {
		$.getJSON(
				"/replies/" + replyId + ".json",
				function(replyObj) {
					if (successCallBack) {
						successCallBack(replyObj);
					}
				}
			).fail(
				function(xhr, status, errMsg){
					if (errorCallBack) {
						errorCallBack(errMsg);
					}
				}
		);
	}

	function add(originalId, reply, successCallBack, errorCallBack) {
		//https://api.jquery.com/jquery.ajax/
		$.ajax({
			type : 'post', //method alias
			url : '/replies/new/' + originalId,
			data : JSON.stringify(reply), //Data or 객체를 json 문자열로 출력. 
			contentType :'application/json; charset=UTF-8',
			success : function(resObj, status, xhr) {
				if (successCallBack) {
					successCallBack(resObj);
				}
			},			
			error : function(xhr, status, errMsg) {
				if (errorCallBack) {
					errorCallBack(errMsg);
				}
			}
		});
	}
	
	function update(reply, successCallBack, errorCallBack) {
		$.ajax({
			type : 'put', //method alias
			url : '/replies/',
			data : JSON.stringify(reply), //Data or 객체를 json 문자열로 출력. 
			contentType :'application/json; charset=UTF-8',
			success : function(resultMsg, status, xhr) {
				if (successCallBack) {
					successCallBack(resultMsg);
				}
			},			
			error : function(xhr, status, errMsg) {
				if (errorCallBack) {
					errorCallBack(errMsg);
				}
			}
		});
	}
		
	function remove(replyId, successCallBack, errorCallBack) {
		$.ajax({
			type : 'delete', //method alias
			url : '/replies/' + replyId,
			success : function(delResult, status, xhr) {
				if (successCallBack) {
					successCallBack(delResult);
				}
			},			
			error : function(xhr, status, errMsg) {
				if (errorCallBack) {
					errorCallBack(errMsg);
				}
			}
		});
	}
	
	//정의된 함수를 알려 주는 곳
	return {
		getReplyList:getList,
		getReply:get,
		addReply:add,
		updateReply:update,
		removeReply:remove
		};
})();