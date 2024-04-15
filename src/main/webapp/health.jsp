<%@page import="com.db.healthInfo"%>
<%@ page import="java.io.*" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
   healthInfo health = healthInfo.getInstance();
	String returns;
	request.setCharacterEncoding("utf-8");
	response.setContentType("text/html; charset=UTF-8");
	
   String part = request.getParameter("part");
   
   returns = health.connectionDB(part);
	
	
   System.out.println("part="+part);

   
	try{
	 System.out.println(returns);
	 System.out.println("=======");
	 out.println(returns);   


	}catch(IOException e){
		System.out.println("healthInfo 전송완료");
	}
	
%>