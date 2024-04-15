<%@page import="com.db.ConnectDB"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
   ConnectDB connectDB = ConnectDB.getInstance();

	request.setCharacterEncoding("utf-8");
	response.setContentType("text/html; charset=UTF-8");

   String id = request.getParameter("id");
   String pw = request.getParameter("pw");
   String nick = request.getParameter("nick");
   String spn = request.getParameter("spn");
   String ans = request.getParameter("ans");
   
   
   String returns = connectDB.connectionDB(id, pw, nick, spn, ans);

   System.out.println("id="+id+"pw="+pw+"nick="+nick+"spn="+spn+"ans="+ans);
   System.out.println(returns);

   // 안드로이드로 전송
   out.println(returns);
   out.close();
%>