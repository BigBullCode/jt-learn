<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
	<h1>文件上传入门案例</h1>
	<form action="http://localhost:8091/file" method="post" encType="multipart/form-data">
		<input type="file" name="file"></input>
		<input type="submit" name="提交"></input>
	</form>
</body>
</html>
<%-- <form action="http://localhost:8091/file" method="post" encType="multipart/form-data></form> --%>