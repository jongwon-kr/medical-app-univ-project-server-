<%@page import="com.db.findInfo"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>

<%
   findInfo find = findInfo.getInstance();
	request.setCharacterEncoding("utf-8");
	response.setContentType("text/html; charset=UTF-8");
   String info = request.getParameter("info");
   String spn = request.getParameter("spn");
   String ans = request.getParameter("ans");
   String check = request.getParameter("check");
   
   
   String returns = find.connectionDB(info, spn, ans,check);
   System.out.println("info="+info+"spn="+spn+"ans="+ans+"check="+check);
   System.out.println(returns);
   // 안드로이드로 전송
   out.println(returns);
%>