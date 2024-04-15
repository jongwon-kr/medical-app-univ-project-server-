<%@page import="com.db.myinfo"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
myinfo info = myinfo.getInstance();

   request.setCharacterEncoding("utf-8");
   response.setContentType("text/html; charset=UTF-8");

   String nick = request.getParameter("nick");
 
   System.out.println("닉네임=" + nick);

   // 데이터 삽입
   String returns = info.connectionDB(nick);
   
   System.out.println(returns);
   
   out.print(returns);
	out.close();
%>