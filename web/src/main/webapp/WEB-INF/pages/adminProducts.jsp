<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<header>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
            integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
            crossorigin="anonymous"></script>
    <script type="text/javascript" src="<spring:url value="/resources/js/ajaxUpdateCart.js"/> "></script>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <sec:csrfMetaTags />

    <!-- JQuerry library -->
</header>

<body>

<div class="container">


    <p>PhoneShop</p>
    <form method="get">
        <p>
            <input type="search" name="searchLine" placeholder="Site search"
                   value="<c:out value="${searchLineAttrib}"/>">
            <input type="hidden" name="sortingParameter" value="${sortParam}"/>
            <input type="hidden" name="gradation" value="${gradation}"/>
            <input type="submit" value="Search">
        </p>
    </form>
    <p>
        Hello from product list!
    </p>
    <p>
        Found
        <c:out value="${countPhones}"/> phones.
    <table border="1px" class="table">
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
        </tr>
        </thead>
        <c:forEach var="phone" items="${phones}">
            <tr>
                <td>
                    <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}">
                </td>
                <td>${phone.brand}</td>
                <td><a href="<c:url value="/admin/productDetails/${phone.id}"/>">${phone.model}</a></td>
                <td>
                    <c:forEach var="color" items="${phone.colors}">
                        ${color.code} <br>
                    </c:forEach>
                </td>
                <td>${phone.displaySizeInches}"</td>
                <td><fmt:formatNumber value="${phone.price}" type="currency"
                                      currencyCode="USD"/></td>
            </tr>
        </c:forEach>
    </table>
    </p>
    <nav aria-label="Page navigation example">
        <ul class="pagination">
            <li class="page-item">
                <a class="page-link"
                   href="<c:url value="/productList"><c:param name="searchLine" value="${searchLineAttrib}"/><c:param name="sortingParameter" value="${sortParam}"/><c:param name="gradation" value="${gradation}"/><c:param name="pageNumber" value="1"/></c:url>"
                   aria-label="Previous">
                    <span aria-hidden="true">&laquo;</span>
                    <span class="sr-only">Previous</span>
                </a>
            </li>
            <c:forEach var="page" begin="${startPage}" end="${pageLimit}">
                <c:if test="${page == currentPage}">
                    <li class="page-item active"><a class="page-link"
                                                    href="<c:url value="/productList"><c:param name="searchLine" value="${searchLineAttrib}"/><c:param name="sortingParameter" value="${sortParam}"/><c:param name="gradation" value="${gradation}"/><c:param name="pageNumber" value="${page}"/></c:url>">${page}</a>
                    </li>
                </c:if>
                <c:if test="${page != currentPage}">
                    <li class="page-item"><a class="page-link"
                                             href="<c:url value="/productList"><c:param name="searchLine" value="${searchLineAttrib}"/><c:param name="sortingParameter" value="${sortParam}"/><c:param name="gradation" value="${gradation}"/><c:param name="pageNumber" value="${page}"/></c:url>">${page}</a>
                    </li>
                </c:if>
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
</div>
</body>