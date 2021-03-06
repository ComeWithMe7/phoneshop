<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>

<jsp:useBean id="cart" type="com.es.core.cart.Cart" scope="request"/>

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
<sf:form method="post" commandName="orderView">
    <p>PhoneShop</p>
    <p>
        <input type="submit" value="Back to cart" formmethod="get" formaction="<c:url value="/cart"/>">
    </p>
    <h2>Order</h2>
    <table border="1px" class="table">
        <thead>
        <tr>
            <td>Brand</td>
            <td>Model</td>
            <td>Color</td>
            <td>Display size</td>
            <td>Quantity</td>
            <td>Price</td>
        </tr>
        </thead>
        <c:forEach var="index" begin="0" varStatus="status" end="${cart.cartItems.size() - 1}">
            <tr>
                <td>
                    ${cart.cartItems[status.index].phone.brand}
                </td>
                <td>
                    <a href="<c:url value="productDetails/${cart.cartItems[status.index].phone.id}"/>">${cart.cartItems[status.index].phone.model}</a>
                </td>
                <td>
                    <c:forEach var="color" items="${cart.cartItems[status.index].phone.colors}" varStatus="colorIndex">
                        ${color.code} <br>
                    </c:forEach>
                </td>
                <td>
                        ${cart.cartItems[status.index].phone.displaySizeInches}"
                </td>
                <td>
                    <sf:hidden path="cartItemUpdates[${status.index}].id"/>
                    <sf:hidden path="cartItemUpdates[${status.index}].quantity"/>
                    <sf:errors path="cartItemUpdates[${status.index}].quantity"/><br>
                    ${cart.cartItems[status.index].quantity}
                </td>
                <td>
                    ${cart.cartItems[status.index].phone.price}$
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
            <td>${orderView.subtotal}$</td>
            <td>${orderView.delivery}$</td>
            <td>${orderView.total}$</td>
        </tr>
    </table>

    First name*   <sf:input path="firstName"/><br>
    <sf:errors path="firstName"/><br>
    Last name*   <sf:input path="lastName"/><br>
    <sf:errors path="lastName"/><br>
    Address*   <sf:input path="address"/><br>
    <sf:errors path="address"/><br>
    Phone*   <sf:input path="phoneNumber"/><br>
    <sf:errors path="phoneNumber"/><br>
    Additional information   <sf:textarea path="information"/><br>
    <p><input type="submit" value="Order"></p>
</sf:form>
</div>
</body>
</html>
