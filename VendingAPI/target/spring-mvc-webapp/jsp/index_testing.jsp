<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Vending Monster</title>
    <!-- Bootstrap core CSS -->
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/testPage.css" rel="stylesheet">


</head>
<body>
<div class="container">
    <h1>Vending Landing JSP</h1>
    ${time}
    <hr/>

    <div class="row">
        <form action="/vendingAPI/jsp/addMoney" method="post">
            <div class="row">
                <div class="col-lg-12">
                    <label>totalCash: </label>
                    <input type="hidden" name="selectedItem" value="${selectedItem}">
                    <input id="#moneytotal" type="number" name="totalCash"
                           placeholder="0.00" value="${totalCash}" readonly/>
                    <span>&nbsp &nbsp</span>
                    <button class="mbtn" type="submit" name="mbutton" value="dollar">1.00</button>
                    <button class="mbtn" type="submit" name="mbutton" value="quarter">0.25</button>
                    <button class="mbtn" type="submit" name="mbutton" value="dime">0.10</button>
                    <button class="mbtn" type="submit" name="mbutton" value="nickel">0.05</button>

                </div>
            </div>
        </form>
        <hr/>
    </div>

    <div class="row">
        <div class="col-sm-5">

            <form action="/vendingAPI/jsp/vendItem" method="post">
                <input id="loadBox" type="text" readonly value="${selectedItem}"><br/>
                <input type="hidden" name="totalCash" value="${totalCash}">
                <input type="hidden" name="selectedItem" value="${selectedItem}">
                <button type="submit" name="purchase">Make Purchase</button>
            </form>

            <br/>
            <div id="changebox" class="img-rounded">

                <c:choose>
                    <c:when test="${boxmsg} == null || ${boxmsg}  == ''">
                        Welcome...
                    </c:when>
                    <c:otherwise>
                        ${boxmsg}<br/>
                        ${change.quarters}<br/>
                        ${change.dimes}<br/>
                        ${change.nickels}<br/>
                        ${change.pennies}<br/>


                    </c:otherwise>
                </c:choose>


            </div>
            <form action="/vendingAPI/jsp/getChange" method="post">
                <input type="hidden" name="totalCash" value="${totalCash}">
                <button type="submit" name="change">Get Change</button>
            </form>
        </div>
        <div class="col-sm-5" style="border: 2px solid black; padding: 5px;">

            <form action="/vendingAPI/jsp/loadItem" method="post">
                <input type="hidden" name="totalCash" value="${totalCash}">
                <input type="hidden" name="selectedItem" value="${selectedItem}">

                <c:forEach items="${plist}" var="product">
                    <button type="submit" name="selectedItemButton"
                            value="${product.productName}">${product.productName}</button>
                    : ${product.productPrice}, ${product.productQty} remaining <br/>
                </c:forEach>


            </form>

        </div>

    </div>
</div>
<!-- Placed at the end of the document so the pages load faster -->
<script src="${pageContext.request.contextPath}/js/jquery-3.2.1.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

</body>
</html>

