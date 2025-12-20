<%@ page contentType="text/html; charset=UTF-8" %>
<%
    if (session.getAttribute("email") == null) {
        response.sendRedirect("login.jsp");
    }
%>

<!DOCTYPE html>
<html>
<head>
<title>Booking</title>
</head>

<body>

<h2>Book Travel</h2>

<form action="book" method="post">

Name:
<input name="name" value="${sessionScope.name}" readonly>

Email:
<input name="email" value="${sessionScope.email}" readonly>

Phone:
<input name="phone" required>

Booking Type:
<select name="type" required>
    <option>Flight</option>
    <option>Hotel</option>
    <option>Car</option>
</select>

From:
<input name="from">

To / City:
<input name="to">

Date:
<input type="date" name="date">

<button type="submit">Confirm</button>

</form>

</body>
</html>
