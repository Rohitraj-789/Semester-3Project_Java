<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Book Rental Car</title>

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

<h2>Car Rental Booking</h2>

<form action="booking" method="post">

    <input type="hidden" name="type" value="Car">

    Car Type:
    <input type="text" name="carType" required><br><br>

    Pickup Location:
    <input type="text" name="pickupLocation" required><br><br>

    Pickup Date:
    <input type="date" name="pickupDate" required><br><br>

    Return Date:
    <input type="date" name="returnDate" required><br><br>

    <input type="submit" value="Book Car">

</form>

</body>
</html>
