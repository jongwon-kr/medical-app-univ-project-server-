<%@page import="com.db.Search"%>\
<%@ page import="java.io.*" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
   Search search_result = Search.getInstance();
   String returns;
   request.setCharacterEncoding("utf-8");
   response.setContentType("text/html; charset=UTF-8");

   String name = request.getParameter("NM");
   System.out.println("이름=" + name);

   // 데이터 삽입
   returns = search_result.connectionDB(name);
 
	try{
		 System.out.println("return ="+ returns);
		 out.println(returns);   


		}catch(IOException e){
			System.out.println("Search 전송완료");
		}
   
%>