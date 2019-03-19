<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
              integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
                integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
                crossorigin="anonymous"></script>

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

        <script type="text/javascript" src="<spring:url value="/resources/js/ajaxUpdateCart.js"/> "></script>

        <!-- JQuerry library -->
    </head>
    <body>
        <p>
            <a href="<c:url value="/cart"/>">Cart</a>
        </p>
        <p>PhoneShop
            Total:
        <div id="total"><label>${cartTotal}</label></div>
        </p>
        <p><a href="<c:url value="/productList"/>">Back to product list</a></p>
        <h1>${phone.model}</h1>
        <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}">
        <p>${phone.description}</p>
        <h3>Display</h3>
        <table border="1px">
            <thead>
            <tr>
                <td>Size</td>
                <td>Resolution</td>
                <td>Technology</td>
                <td>Pixel density</td>
            </tr>
            </thead>
            <tr>
                <td>${phone.displaySizeInches}"</td>
                <td>${phone.displayResolution}</td>
                <td>${phone.displayTechnology}</td>
                <td>${phone.pixelDensity}</td>
            </tr>
        </table>

        <h3>Dimensions & weight</h3>
        <table border="1px">
            <thead>
            <tr>
                <td>Length</td>
                <td>Width</td>
                <td>Weight</td>
            </tr>
            </thead>
            <tr>
                <td>${phone.lengthMm}mm"</td>
                <td>${phone.widthMm}mm</td>
                <td>${phone.weightGr}</td>
            </tr>
        </table>

        <h3>Camera</h3>
        <table border="1px">
            <thead>
            <tr>
                <td>Front</td>
                <td>Back</td>
            </tr>
            </thead>
            <tr>
                <td>${phone.frontCameraMegapixels} megapixels</td>
                <td>${phone.backCameraMegapixels} megapixels</td>
            </tr>
        </table>

        <h3>Battery</h3>
        <table border="1px">
            <thead>
            <tr>
                <td>TalkTime</td>
                <td>Stand by time</td>
                <td>Battery capacity</td>
            </tr>
            </thead>
            <tr>
                <td>${phone.talkTimeHours} hours"</td>
                <td>${phone.standByTimeHours} hours</td>
                <td>${phone.batteryCapacityMah}mAh</td>
            </tr>
        </table>

        <h3>Other</h3>
        <table border="1px">
            <thead>
            <tr>
                <td>Colors</td>
                <td>Device type</td>
                <td>Bluetooth</td>
            </tr>
            </thead>
            <tr>
                <td>
                    <c:forEach var="color" items="${phone.colors}">
                        ${color.code} <br>
                    </c:forEach>
                </td>
                <td>${phone.deviceType}</td>
                <td>${phone.bluetooth}</td>
            </tr>
        </table>
        <h2>Price: ${phone.price}</h2>
        <form>
            <input id="quantity" value="1"/>
            <div id="quantityError"></div>
            <button type="button" onclick="myFunction(${phone.id})">Add to</button>
        </form>
    </body>
</html>