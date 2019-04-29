<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
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
    <sec:csrfMetaTags />

    <!-- JQuerry library -->
</head>
<body>
<div class="container">
    <p>PhoneShop</p>
    <p><a href="<c:url value="/productList"/>">Back to product list</a></p>
    <sf:form method="PUT" modelAttribute="productView">

        <sf:input path="model"/>
        <sf:errors path="model"/>
        <sf:input path="description" />
        <sf:errors path="description"/>
    <h3>Display</h3>
    <table border="1px" class="table">
        <thead>
        <tr>
            <td>Size</td>
            <td>Resolution</td>
            <td>Technology</td>
            <td>Pixel density</td>
        </tr>
        </thead>
        <tr>
            <td><sf:input path="displaySizeInches" />
                <sf:errors path="displaySizeInches"/>
            </td>

            <td><sf:input path="displayResolution" />
                <sf:errors path="displayResolution"/>
            </td>

            <td><sf:input path="displayTechnology" />
                <sf:errors path="displayTechnology"/>
            </td>

            <td><sf:input path="pixelDensity" />
                <sf:errors path="pixelDensity"/>
            </td>

        </tr>
    </table>

    <h3>Dimensions & weight</h3>
    <table border="1px" class="table">
        <thead>
        <tr>
            <td>Length</td>
            <td>Width</td>
            <td>Weight</td>
        </tr>
        </thead>
        <tr>
            <td><sf:input path="lengthMm" />
                <sf:errors path="lengthMm"/>mm"</td>
            <td><sf:input path="widthMm" />
                <sf:errors path="widthMm"/>mm</td>
            <td><sf:input path="weightGr" />
                <sf:errors path="weightGr"/></td>
        </tr>
    </table>

    <h3>Camera</h3>
    <table border="1px" class="table">
        <thead>
        <tr>
            <td>Front</td>
            <td>Back</td>
        </tr>
        </thead>
        <tr>
            <td><sf:input path="frontCameraMegapixels" />
                <sf:errors path="frontCameraMegapixels"/> megapixels</td>
            <td><sf:input path="backCameraMegapixels" />
                <sf:errors path="backCameraMegapixels"/> megapixels</td>
        </tr>
    </table>

    <h3>Battery</h3>
    <table border="1px" class="table">
        <thead>
        <tr>
            <td>TalkTime</td>
            <td>Stand by time</td>
            <td>Battery capacity</td>
        </tr>
        </thead>
        <tr>
            <td><sf:input path="talkTimeHours" />
                <sf:errors path="talkTimeHours"/> hours"</td>
            <td><sf:input path="standByTimeHours" />
                <sf:errors path="standByTimeHours"/> hours</td>
            <td><sf:input path="batteryCapacityMah" />
                <sf:errors path="batteryCapacityMah"/>mAh</td>
        </tr>
    </table>

    <h3>Other</h3>
    <table border="1px" class="table">
        <thead>
        <tr>
            <td>Device type</td>
            <td>Bluetooth</td>
        </tr>
        </thead>
        <tr>
            <td><sf:input path="deviceType" />
                <sf:errors path="deviceType"/></td>
            <td><sf:input path="bluetooth" />
                <sf:errors path="bluetooth"/></td>
        </tr>
    </table>
    <h2>Price: <fmt:formatNumber value="${phone.price}" type="currency"
                                 currencyCode="USD"/></h2>

        <sf:hidden path="id"/>
        <sf:hidden path="brand"/>
        <sf:hidden path="imageUrl"/>

        <p>
            <input type="submit" value="Update">
        </p>
    </sf:form>
</div>
</body>
</html>