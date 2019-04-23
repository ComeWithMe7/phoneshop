<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

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

<form id="delete" method="post">
    <sec:csrfInput />

    <input type="hidden" name="_method" value="DELETE"/>
</form>

<div class="container">
    <sf:form method="PUT" modelAttribute="updateCartData">
        <p>PhoneShop
            Total:${cart.total}
        </p>
        <p><a href="<c:url value="/productList"/>">Back to product list</a></p>
        <c:if test="${empty cart.cartItems}">
            <p>Your cart is empty</p>
        </c:if>
        <c:if test="${not empty cart.cartItems}">
            <table border="1px" class="table">
                <thead>
                <tr>
                    <td>Image</td>
                    <td>Brand</td>
                    <td>Model</td>
                    <td>Color</td>
                    <td>Display size</td>
                    <td>Price</td>
                    <td>Quantity</td>
                    <td>Action</td>
                </tr>
                </thead>
                <c:forEach var="index" begin="0" varStatus="status" end="${cart.cartItems.size() - 1}">
                    <tr>
                        <td>
                            <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${cart.cartItems[status.index].phone.imageUrl}">
                        </td>
                        <td>
                                ${cart.cartItems[status.index].phone.brand}
                        </td>
                        <td>
                            <a href="<c:url value="productDetails/${cart.cartItems[status.index].phone.id}"/>">${cart.cartItems[status.index].phone.model}</a>
                        </td>
                        <td>
                            <c:forEach var="color" items="${cart.cartItems[status.index].phone.colors}"
                                       varStatus="colorIndex">
                                ${color.code} <br>
                            </c:forEach>
                        </td>
                        <td>
                                ${cart.cartItems[status.index].phone.displaySizeInches}"
                        </td>
                        <td>
                            <fmt:formatNumber value="${cart.cartItems[status.index].phone.price}" type="currency"
                                              currencyCode="USD"/>
                        </td>
                        <td>
                            <sf:hidden path="cartItems[${status.index}].id"/>
                            <sf:input path="cartItems[${status.index}].quantity"/><br>
                            <sf:errors path="cartItems[${status.index}].quantity"/>
                        </td>
                        <td>
                                <button form="delete" formaction="<c:url value = "/cart/delete/${cart.cartItems[status.index].phone.id}" />">
                                    Delete
                                </button>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <p>
                <input type="submit" value="Update">
            </p>
        </c:if>
    </sf:form>
    <c:if test="${not empty cart.cartItems}">
        <form>
            <p><input type="submit" value="Order" formmethod="get" formaction="<c:url value="/order"/>"></p>
        </form>
    </c:if>
</div>
</body>
</html>
