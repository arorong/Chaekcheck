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
		display : flex;
		justify-content: center;
		align-items: center; /* 세로 가운데 정렬 */
	}
	
	.findPwContainer{
		margin : auto;
	}
	
	a{
		text-decoration : none;
		color : inherit;
	}
	

</style>

</head>
<body>
	<div class="container">
		<div class="findPwContainer">
			<div class="logo">
				<h2><a href="main.log">CHAEKCHECK</a></h2>
			</div>
			<div>
				<h2>비밀번호 찾기</h2>
				<p>가입한 이메일을 입력해 주세요.<br>이메일을 통해 비밀번호 변경 링크가 전송됩니다</p>
			</div>
			<form class="findPwEmail" id="pwFindForm" novalidate>
				<div>
					<input type="email" name="u_email" placeholder="이메일">
				</div>
				<div class="error-message" id="emailNoneMessage" style="display:none; color:red;"><small>이메일을 입력해주세요.</small></div>
				<div class="error-message" id="emailErrorMessage" style="display:none; color:red;"><small>이메일을 올바른 형식으로 입력해주세요.</small></div>
				<div class="error-message" id="emailNotFoundMessage" style="display:none; color:red;"><small>등록되지 않은 이메일입니다.</small></div>
				<div class="error-message" id="serverErrorMessage" style="display:none; color:red;"><small>오류가 발생했습니다. 다시 시도해주세요.</small></div>
				<button type="submit">변경 링크 전송하기</button>
			</form>
		</div>
	</div>
	
	<script>
	$(document).ready(function() {
	    // 모든 에러/성공 메시지 숨기기
	    function hideAllMessages() {
	        $('.error-message, .success-message').hide();
	    }
	    
	 	// 이메일 형식 체크 함수
	    function validateEmail(email) {
	        var emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
	        return emailPattern.test(email);
	    }
	    
	    //form 제출
	    $('#pwFindForm').on('submit', function(e) {
	        e.preventDefault(); // 기본 폼 제출 방지
	        hideAllMessages(); // 이전 메시지들 숨김
	        
	        var email = $('input[name="u_email"]').val().trim();
	        
	        // 이메일 입력 체크
	        if (!email) {
	            $('#emailNoneMessage').show();
	            return;
	        }
	        
	        // 이메일 형식 체크
	        if (!validateEmail(email)) {
	            $('#emailErrorMessage').show();
	            return;
	        }
	        
	        // AJAX 요청
	        $.ajax({
	            url: 'sendFindPwLink.log',
	            type: 'POST',
	            data: {u_email: email},
	            success: function(data) {
	                if (data === 'success') {
	                    alert('비밀번호 변경 링크가 이메일로 전송되었습니다.');
	                    $('#successMessage').show();
	                    window.location.href = "main.log";
	                } else {
	                    $('#emailNotFoundMessage').show();
	                }
	            },
	            error: function() {
	                $('#serverErrorMessage').show();
	            }
	        });
	    });
	    
	    // 입력 필드에 포커스가 갔을 때 메시지 숨기기
	    $('input[name="u_email"]').on('focus', function() {
	        hideAllMessages();
	    });
	});
	</script>
	
	
	
	
</body>
</html>