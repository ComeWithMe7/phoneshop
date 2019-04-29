<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

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
    <h2>Order number: ${order.id}</h2>
    <h2>${order.status}</h2>
    <table border="1px" class="table">
        <thead>
        <tr>
            <td>Brand</td>
            <td>Model</td>
            <td>Color</td>
            <td>Display size</td>
            <td>Price</td>
            <td>Quantity</td>
            <td>Price</td>
        </tr>
        </thead>
        <c:forEach var="index" begin="0" varStatus="status" end="${order.orderItems.size() - 1}">
            <tr>
                <td>
                        ${order.orderItems[status.index].phone.brand}
                </td>
                <td>
                        ${order.orderItems[status.index].phone.model}
                </td>
                <td>
                    <c:forEach var="color" items="${order.orderItems[status.index].phone.colors}" varStatus="colorIndex">
                        ${color.code} <br>
                    </c:forEach>
                </td>
                <td>
                        ${order.orderItems[status.index].phone.displaySizeInches}"
                </td>
                <td>
                    <fmt:formatNumber value="${order.orderItems[status.index].phone.price}" type="currency"
                                      currencyCode="USD"/>
                </td>
                <td>
                        ${order.orderItems[status.index].quantity}
                </td>
                <td>
                        ${order.orderItems[status.index].phone.price}$
                </td>
            </tr>
        </c:forEach>
    </table>
    <table border="1px" class="table">
        <thead>
        <tr>
            <td>Subtotal</td>
            <td>Delivery</td>
            <td>Total</td>
        </tr>
        </thead>
        <tr>
            <td>${order.subtotal}$</td>
            <td>${order.deliveryPrice}$</td>
            <td>${order.totalPrice}$</td>
        </tr>
    </table>

    First name:   ${order.firstName}<br>
    Last name:   ${order.lastName}<br>
    Address:   ${order.deliveryAddress}<br>
    Phone:   ${order.contactPhoneNo}<br>
    Additional information:   ${order.information}<br>

    <form id="delivered"  method="post">
        <sec:csrfInput />

        <input type="hidden" name="status" id="status" value="DELIVERED">
        <input type="hidden" name="_method" value="PUT"/>
        <p><input type="submit" value="Delivered" formaction="<c:url value="/admin/orders/${order.id}"/>"></p>
    </form>
    <form id="rejected" method="post">
        <sec:csrfInput />
        <input type="hidden" name="status" value="REJECTED">
        <input type="hidden" name="_method" value="PUT"/>
        <p><input type="submit" value="Rejected" formaction="<c:url value="/admin/orders/${order.id}"/>"></p>
    </form>
</div>
</body>
</html>
