<%@page import="com.db.rankingInfo"%>
<%@page import="java.io.*"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
	rankingInfo rank= rankingInfo.getInstance();

	request.setCharacterEncoding("utf-8");
	response.setContentType("text/html; charset=UTF-8");

   String start = request.getParameter("start");

   
   String returns = rank.connectionDB();

	System.out.println(returns);

	try{
		out.println(returns);
	}catch(IOException e){
		System.out.println("ranking 전송완료");
	}catch(IllegalStateException e){
		System.out.println("ranking 전송완료");
	}
%>