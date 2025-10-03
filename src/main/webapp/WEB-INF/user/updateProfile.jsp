<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>CHAEKCHECK</title>
<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.4/dist/jquery.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.js"></script>
<script src="${pageContext.request.contextPath}/resources/validation.js"></script>

<style>
	.container{
	    display: flex;
	    flex-direction: column;
	    align-items: center;
	    width: 100%;
	    min-height: 100vh;
	    box-sizing: border-box;
	}
	
	.updateMain{
		margin: 0;
	    padding: 0;
	    border: 0;
	    width : 100%;
	    font-size: 100%;
	    font: inherit;
	    vertical-align: baseline;
	    box-sizing: border-box;
	}
	
	button{
		border: none;
		background-color: transparent;
		color: grey;
	}
	
	.modal-header{
		border : none;
	}
	
	.content{
	    margin-top: 0;
	    margin-bottom: 0;
	}
	
	.identifyMem{
		margin-top: 50px;
	}
	
	
</style>



</head>
<body>
<%@ include file="../../common/cNav.jsp" %>
	<div class="container">
		<div class="updateMain">
			프로필 사진 수정<br>
			닉네임 수정<br>
			비밀번호 변경<br>
			<div class="delAccount">
				<button type="button" data-toggle="modal" data-target="#delModal" onclick="delAccountModal()">회원 탈퇴 ></button>
			</div>
		</div>
	</div>
	
	<!-- 계정 탈퇴 모달 -->
	<div class="modal fade" id="delModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h4 class="modal-title">탈퇴 전 안내사항</h4>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
<!-- 	      <h3 class="delAcInform" style="text-align:left; margin: 20px 0 0 10px;">탈퇴 전 안내사항</h3> -->
	      <div class="modal-body">
	      	<div class="delInform">
	      		<div class="delInform1">
	      			<p class="content">1. 계정 탈퇴 시 계정과 관련된 권한이 사라지며 복구할 수 없습니다.</p>
	      			<p class="content">2. 탈퇴 시 작성한 콘텐츠는 삭제되지 않으며, 만일 삭제를 원하시면 탈퇴 이전에 직접 삭제해주셔야 합니다.</p>
	      			<p class="content">3. 탈퇴 후 동일한 메일로 재가입이 가능하나, 탈퇴한 계정과 연동되지 않습니다.</p>
	      			<p class="content">4. 아래 본인 확인 후 탈퇴하기를 누르시면 위 내용에 동의하는 것으로 간주됩니다.</p>
	      		</div>
	      		<div class="identifyMem">
			        <form class="delAccount-modal" id="delAccount" action="deleteMember.log" novalidate>
			          <div>
			      	    <h6>본인 확인</h6>
			          	<p>본인 확인을 위해 비밀번호를 입력해주세요</p>
			      	  </div>
			      	  <input type="hidden" name="u_email" value="${u_email}">
			          <div class="delAccount-group" onclick="pwInput()">
			            <input class="password" type="password" id="u_pw" name="u_pw" placeholder="비밀번호" >
			          </div>
			          <div class="error-message" id="pwErrorMessage" style="display: none; color: red;">
						  <small>✓ 비밀번호를 다시 확인해주세요.</small>
					  </div>
			          <div class="modal-footer" style="border:none;">
			            <button type="submit">탈퇴하기</button>
			          </div>
			        </form>
		        </div>
	      	</div>
	      </div>
	    </div>
	  	</div>
	</div>	
</body>
<script>
	document.addEventListener('DOMContentLoaded', function(){
		//특정 입력 필드 가져오기
		var inputField = document.getElementById("delAccount");
		if (!inputField) return; //요소값이 없으면 바로 종료
		
		//입력 필드에 포커스 될 때 엔터키 이벤트 처리
		inputField.addEventListener("keydown", function(e){
			if(e.key === "Enter"){
				e.preventDefault(); //기본 submit 막기(중복 방지)
				document.getElementByID(delAccount).submit();
			}
		})
	});
		
</script>






</html>