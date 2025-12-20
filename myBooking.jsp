<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="java.util.*" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>My Bookings</title>

<style>
    body {
        font-family: Arial;
        background-color: #f4f6f8;
        text-align: center;
    }
    table {
        margin: auto;
        border-collapse: collapse;
        width: 60%;
        background: white;
    }
    th, td {
        padding: 10px;
        border: 1px solid #ccc;
    }
    th {
        background-color: #007BFF;
        color: white;
    }
</style>

</head>
<body>

<h2>My Bookings</h2>

<table>
    <tr>
        <th>#</th>
        <th>Booking Details</th>
    </tr>

<%
    List<String> bookings = (List<String>) session.getAttribute("bookings");

    if (bookings == null || bookings.isEmpty()) {
%>
    <tr>
        <td colspan="2">No bookings found</td>
    </tr>
<%
    } else {
        int i = 1;
        for (String b : bookings) {
%>
    <tr>
        <td><%= i++ %></td>
        <td><%= b %></td>
    </tr>
<%
        }
    }
%>

</table>

<br>
<a href="travelerDashboard.jsp">Back to Dashboard</a>

</body>
</html>
