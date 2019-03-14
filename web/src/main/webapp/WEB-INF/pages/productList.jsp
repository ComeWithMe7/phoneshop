<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>

<%--<script type="text/javascript" src="${pageContext.request.contextPath}/js/ajaxCartUpdate.js"></script>--%>
<header>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
            integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
            crossorigin="anonymous"></script>
    <%--<script type="text/javascript" src="ajaxCartUpdate.js"></script>--%>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <%--<script type="text/javascript" src="/webapp/WEB-INF/js/ajaxCartUpdate.js"--%>
    <script type="text/javascript">
        var url = "<c:url value="/ajaxCart"></c:url>";

        function myFunction(phoneId) {
            var quantityx = "quantity" + phoneId;
            $.ajax({
                type: "POST",
                url: url,
                datatype: 'json',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify({
                    "id": parseInt(phoneId),
                    "quantity": parseInt($('#' + "quantity" + phoneId).val())
                }),
                success: function (data) {
                    var receivedObject = data;
                    $("#total").html("");
                    $("#quantityError" + phoneId).html("");
                    console.log(data);
                    var text = document.createTextNode(receivedObject.total);
                    var text2 = document.createTextNode(receivedObject.quantityError);
                    var domElement1 = document.getElementById('total');
                    domElement1.appendChild(text);
                    var domElement2 = document.getElementById('quantityError' + phoneId);
                    domElement2.appendChild(text2);
                },
                error: function (result) {
                    console.log("ERROR: ", result);
                }
            });
        }
    </script>
    <!-- JQuerry library -->
</header>

<body>
<p>PhoneShop
    Total:<div id="total"><label>${cartTotal}</label></div>
</p>
<form method="get">
    <p>
        <input type="search" name="searchLine" placeholder="Site search" value="<c:out value="${searchLineAttrib}"/>">
        <input type="submit" value="Search"></p>
    </p>
</form>
<p>
    Hello from product list!
</p>
<p>
    Found
    <c:out value="${countPhones}"/> phones.
<table border="1px" width="100%">
    <thead>
    <tr>
        <td>Image</td>
        <td>Brand<a
                href="<c:url value="/productList"><c:param name="searchLine" value="${searchLineAttrib}"/><c:param name="sortingParameter" value="brand"/><c:param name="gradation" value="up"/></c:url>">up</a>
            <a href="<c:url value="/productList"><c:param name="searchLine" value="${searchLineAttrib}"/><c:param name="sortingParameter" value="brand"/><c:param name="gradation" value="down"/></c:url>">down</a>
        </td>
        <td>Model<a
                href="<c:url value="/productList"><c:param name="searchLine" value="${searchLineAttrib}"/><c:param name="sortingParameter" value="model"/><c:param name="gradation" value="up"/></c:url>">up</a>
            <a href="<c:url value="/productList"><c:param name="searchLine" value="${searchLineAttrib}"/><c:param name="sortingParameter" value="model"/><c:param name="gradation" value="down"/></c:url>">down</a>
        </td>
        <td>Color</td>
        <td>Display size<a
                href="<c:url value="/productList"><c:param name="searchLine" value="${searchLineAttrib}"/><c:param name="sortingParameter" value="displaySize"/><c:param name="gradation" value="up"/></c:url>">up</a>
            <a href="<c:url value="/productList"><c:param name="searchLine" value="${searchLineAttrib}"/><c:param name="sortingParameter" value="displaySize"/><c:param name="gradation" value="down"/></c:url>">down</a>
        </td>
        <td>Price</td>
        <td>Quantity</td>
        <td>Action</td>
    </tr>
    </thead>
    <c:forEach var="phone" items="${phones}">
        <tr>
            <td>
                <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}">
            </td>
            <td>${phone.brand}</td>
            <td>${phone.model}</td>
            <td>
                <c:forEach var="color" items="${phone.colors}">
                    ${color.code} <br>
                </c:forEach>
            </td>
            <td>${phone.displaySizeInches}"</td>
            <td>$ ${phone.price}</td>
            <form>
                <td>
                    <input type="hidden" id="id" value="${phone.id}"/>
                    <input id="quantity${phone.id}" value="1"/>
                    <div id="quantityError${phone.id}"></div>
                </td>
                <td>
                    <button type="button" onclick="myFunction(${phone.id})">Add to</button>
                </td>
            </form>
        </tr>
    </c:forEach>
</table>
</p>
<nav aria-label="Page navigation example">
    <ul class="pagination">
        <li class="page-item">
            <a class="page-link"
               href="<c:url value="/productList"><c:param name="searchLine" value="${searchLineAttrib}"/><c:param name="${sortParam}" value="brand"/><c:param name="gradation" value="${gradation}"/><c:param name="pageNumber" value="1"/></c:url>"
               aria-label="Previous">
                <span aria-hidden="true">&laquo;</span>
                <span class="sr-only">Previous</span>
            </a>
        </li>
        <c:forEach var="page" begin="${startPage}" end="${pageLimit}">
            <li class="page-item"><a class="page-link"
                                     href="<c:url value="/productList"><c:param name="searchLine" value="${searchLineAttrib}"/><c:param name="sortingParameter" value="${sortParam}"/><c:param name="gradation" value="${gradation}"/><c:param name="pageNumber" value="${page}"/></c:url>">${page}</a>
            </li>
        </c:forEach>
        <li class="page-item">
            <a class="page-link"
               href="<c:url value="/productList"><c:param name="searchLine" value="${searchLineAttrib}"/><c:param name="sortingParameter" value="${sortParam}"/><c:param name="gradation" value="${gradation}"/><c:param name="pageNumber" value="${finalPage}"/></c:url>"
               aria-label="Next">
                <span aria-hidden="true">&raquo;</span>
                <span class="sr-only">Next</span>
            </a>
        </li>
    </ul>
</nav>
</body>