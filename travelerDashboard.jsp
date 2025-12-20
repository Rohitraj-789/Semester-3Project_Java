<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>Traveler Dashboard</title>

<style>
body {
    font-family: Arial;
    background: #f2f2f2;
}
header {
    background: #2c3e50;
    color: white;
    padding: 15px;
    text-align: center;
}
.container {
    padding: 20px;
}
.card {
    background: white;
    padding: 15px;
    margin-bottom: 20px;
    border-radius: 6px;
}
button {
    width: 100%;
    padding: 10px;
    background: #3498db;
    color: white;
    border: none;
}
input, select {
    width: 100%;
    padding: 6px;
    margin-top: 5px;
}
</style>
</head>

<body>

<header>
    <h2>Travel Management System</h2>
</header>

<div class="container">

<form action="book" method="post">

<!-- Traveler Identity -->
<div class="card">
    <h3>Traveler Details</h3>
    Name:
    <input type="text" name="name" required>

    Email:
    <input type="email" name="email" required>

    Phone:
    <input type="text" name="phone" required>
</div>

<!-- Booking Type -->
<div class="card">
    <h3>Select Booking Type</h3>
    <select name="type" required>
        <option value="">--Select--</option>
        <option value="Flight">Flight</option>
        <option value="Hotel">Hotel</option>
        <option value="Car">Car</option>
    </select>
</div>

<!-- Booking Details -->
<div class="card">
    From:
    <input name="from">

    To / City / Location:
    <input name="to">

    Date:
    <input type="date" name="date">
</div>

<button type="submit">Confirm Booking</button>

</form>

</div>
</body>
</html>
