<%@ page import="model.Booking" %>
<%
Booking b = (Booking) request.getAttribute("booking");
%>

<!DOCTYPE html>
<html>
<head>
<title>Booking Receipt</title>
<style>
body { font-family: Arial; background: #f4f4f4; }
.card {
    background: white;
    padding: 20px;
    width: 400px;
    margin: 50px auto;
    border-radius: 8px;
}
</style>
</head>

<body>
<div class="card">
<h2>Booking Receipt</h2>

<p><b>Type:</b> <%= b.getBookingType() %></p>

<% if ("flight".equals(b.getBookingType())) { %>
<p>From: <%= b.getFrom() %></p>
<p>To: <%= b.getTo() %></p>
<p>Date: <%= b.getTravelDate() %></p>
<% } %>

<% if ("hotel".equals(b.getBookingType())) { %>
<p>City: <%= b.getCity() %></p>
<p>Check-in: <%= b.getCheckin() %></p>
<p>Check-out: <%= b.getCheckout() %></p>
<% } %>

<% if ("car".equals(b.getBookingType())) { %>
<p>Location: <%= b.getCity() %></p>
<p>Date: <%= b.getTravelDate() %></p>
<% } %>

<p>Status: <b><%= b.getStatus() %></b></p>

<a href="travelerDashboard.jsp">Back to Dashboard</a>
</div>
</body>
</html>
