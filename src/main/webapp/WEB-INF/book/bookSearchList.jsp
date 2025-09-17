<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<title>CHAEKCHECK</title>
<meta charset="UTF-8">
<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.4/dist/jquery.js"></script>

<style>
	.container{
		display: flex;
		justify-content: center;
		align-items: center; /* 세로 가운데 정렬 */
	}
	.bookListContainer{
		margin: auto;
	}	
</style>
</head>
<body>
<%@ include file="../../common/cNav.jsp" %>

<div class="container">
	<div class="bookListContainer">
	<h2>도서 검색 결과</h2>
	
	<ul>
		<!-- DB 도서 -->
	    <c:forEach var="book" items="${dbBooks}">
	        <li>${book.title} / ${book.author} / ${book.publisher} (DB)</li>
	    </c:forEach>
		<!-- API 도서 -->
	    <c:forEach var="book" items="${apiBooks}">
	        <li><a href="${book.link}" target="_blank">${book.title}</a> (API)</li>
	    </c:forEach>
	</ul>
	
	</div>
</div>
</body>
</html>