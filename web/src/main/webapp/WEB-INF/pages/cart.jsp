<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>


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
    <sf:form method="post" commandName="cartView">
        <p>PhoneShop
            Total:${cartView.total}
        </p>
        <p><a href="<c:url value="/productList"/>">Back to product list</a></p>
        <c:if test="${empty cartView.cartItems}">
            <p>Your cart is empty</p>
        </c:if>
        <c:if test="${not empty cartView.cartItems}">
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
                <c:forEach var="index" begin="0" varStatus="status" end="${cartView.cartItems.size() - 1}">
                    <tr>
                        <td>
                            <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${cartView.cartItems[status.index].phone.imageUrl}">
                        </td>
                        <td>
                                ${cartView.cartItems[status.index].phone.brand}
                        </td>
                        <td>
                            <a href="<c:url value="productDetails/${cartView.cartItems[status.index].phone.id}"/>">${cartView.cartItems[status.index].phone.model}</a>
                        </td>
                        <td>
                            <c:forEach var="color" items="${cartView.cartItems[status.index].phone.colors}"
                                       varStatus="colorIndex">
                                ${color.code} <br>
                            </c:forEach>
                        </td>
                        <td>
                                ${cartView.cartItems[status.index].phone.displaySizeInches}"
                        </td>
                        <td>
                            $ ${cartView.cartItems[status.index].phone.price}
                        </td>
                        <td>
                            <sf:hidden path="cartItems[${status.index}].phone.id"/>
                            <sf:input path="cartItems[${status.index}].quantity"/><br>
                            <sf:errors path="cartItems[${status.index}].quantity"/>
                        </td>
                        <td>
                            <button formaction="<c:url value = "/cart/delete/${cartView.cartItems[status.index].phone.id}" />">
                                Delete
                            </button>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <p><input type="submit" value="Update"></p>
        </c:if>
    </sf:form>
    <c:if test="${not empty cartView.cartItems}">
        <form>
            <p><input type="submit" value="Order" formmethod="get" formaction="<c:url value="/order"/>"></p>
        </form>
    </c:if>
</div>
</body>
</html>
