<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Book Hotel</title>

<style>
    body {
        font-family: Arial;
        background-color: #f4f6f8;
        text-align: center;
    }
    form {
        background: white;
        width: 350px;
        margin: auto;
        padding: 20px;
        border-radius: 5px;
    }
    input {
        width: 90%;
        padding: 8px;
    }
</style>

</head>
<body>

<h2>Hotel Booking</h2>

<form action="booking" method="post">

    <input type="hidden" name="type" value="Hotel">

    Hotel Name:
    <input type="text" name="hotelName" required><br><br>

    Location:
    <input type="text" name="location" required><br><br>

    Check-in Date:
    <input type="date" name="checkIn" required><br><br>

    Check-out Date:
    <input type="date" name="checkOut" required><br><br>

    <input type="submit" value="Book Hotel">

</form>

</body>
</html>
