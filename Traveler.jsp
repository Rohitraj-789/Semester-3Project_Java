<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>Book Flight</title>
</head>
<body>

<h2>Book Flight</h2>

<form action="booking" method="post">

    Origin:
    <input type="text" name="origin" required><br><br>

    Destination:
    <input type="text" name="destination" required><br><br>

    Travel Date:
    <input type="date" name="travelDate" required><br><br>

    <input type="submit" value="Book Flight">

</form>

</body>
</html>
