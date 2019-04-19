<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>

<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
            integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
            crossorigin="anonymous"></script>
</head>
<body>
<div class="container">
    <p>PhoneShop</p>
    <c:if test="${empty orders}">
        <h2>Order list is empty</h2>
    </c:if>
    <c:if test="${not empty orders}">
        <h2>Orders</h2>
        <table border="1px" class="table">
            <thead>
            <tr>
                <td>Order number</td>
                <td>Customer</td>
                <td>Phone</td>
                <td>Address</td>
                <td>Total price</td>
                <td>Status</td>
            </tr>
            </thead>
            <c:forEach var="order" items="${orders}">
                <tr>
                    <td><a href="<c:url value="/admin/orders/"/>${order.id}">${order.id}</a></td>
                    <td>${order.firstName} ${order.lastName}</td>
                    <td>${order.contactPhoneNo}</td>
                    <td>${order.deliveryAddress}</td>
                    <td>${order.totalPrice}$</td>
                    <td>${order.status}</td>
                </tr>
            </c:forEach>
        </table>
    </c:if>

</div>
</body>
</html>