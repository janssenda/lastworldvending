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
    <link href="${pageContext.request.contextPath}/css/mycss.css" rel="stylesheet">

</head>
<body>
<div id="overallContainer" class="container">


    <div class="row text-center">
        <div class="col-12">
            <hr/>
            <div class="row">
                <div class="tDiv"><img src="${pageContext.request.contextPath}/images/tube.png" id="tubeLeft"/></div>
                <div class="tDiv" id="titleText">
                    <h2 id="pageTitle" class="text-center">Vintage Tube Vending </h2>
                    Feed your desire...<br/>

                    <form action="/vendingAPI/jsp/toggleRealism" method="post">
                        <input type="hidden" name="totalCash" value="${totalCash}">
                        <input type="hidden" name="selectedItem" value="${selectedItem}">
                        <input type="hidden" name="displayID" value="${displayID}">
                        <input type="hidden" name="boxmsg" value="${boxmsg}">

                        <button id="rmode" name="realismMode" class="rounded-circle" type="submit"
                                style="border: 2px solid black;">+
                        </button>

                        <c:if test="${realism == true}">
                            Realism is <span style="color:plum; font-weight: bold">ON:</span>
                            <span style="color:teal"> ${realismType} </span>
                        </c:if>
                        <c:if test="${realism == false}">
                            Realism is <span style="color:blue; font-weight: bold">OFF</span>
                        </c:if>

                    </form>
                </div>
                <div class="tDiv"><img src="${pageContext.request.contextPath}/images/tube.png" id="tubeRight"/></div>
                <div style="clear: both"></div>
            </div>
            <hr/>

            <c:if test="${error == true}">
                <div id="serverError" class="alert alert-danger">
                    <strong>Error: </strong> ${errMsg}
                </div>
            </c:if>

        </div>
    </div>


    <div id="contentArea" class="rounded">
        <div class="row text-center">
            <div id="itemAreaMainDiv" class="col-sm-9">


                <div id="showResults" class="rounded">
                    <p id="resultP">
                        Congratulations!! You got... <br/>

                        <c:forEach items="${productList}" var="product" varStatus="loop">

                    <div class="row">
                        <div class="col-sm-8 productDesc text-left">
                            Product: ${product.productName} <br/>
                            Best by: ${product.bestByStr}<br/>
                            Name: ${product.message}<br/><br/>
                        </div>
                        <div class="col-sm-4">
                            <img class="productImage" src="${pageContext.request.contextPath}/images/Output/${product.productName}.png"/>
                        </div>
                    </div><br/>

                    </c:forEach>



                    </p>
                </div>


            </div>


            <div id="controlPanelMainDiv" class="col-sm-3 rounded">
                <div class="row text-center">
                    <div class="form-group text-center">
                        <div class="container vbshrink">

                            <label for="totalMoney" class="col-12 col-form-label title">
                                <span class="title">Welcome</span>
                            </label>

                            <form action="/vendingAPI/jsp/addMoney" method="post">
                                <input type="hidden" name="selectedItem" value="${selectedItem}">
                                <input type="hidden" name="displayID" value="${displayID}">

                                <div class="row">
                                    <div class="col-12">
                                        <input id="totalMoney" class="rounded" type="text" readonly="readonly"
                                               name="totalCash" value="${totalCash}" placeholder="0.00">
                                    </div>
                                </div>


                                <div class="row">
                                    <button type="submit" class="moneyButton" value="dollar"
                                            name="mbutton" id="addDollarButton">$1.00
                                    </button>
                                    <button type="submit" class="moneyButton" value="quarter"
                                            name="mbutton" id="addQuarterButton">$0.25
                                    </button>
                                </div>
                                <div class="row">
                                    <button type="submit" class="moneyButton" value="dime"
                                            name="mbutton" id="addDimeButton">$0.10
                                    </button>
                                    <button type="submit" class="moneyButton" value="nickel"
                                            name="mbutton" id="addNickelButton">$0.05
                                    </button>
                                </div>
                            </form>

                        </div>
                    </div>
                </div>
                <hr/>
                <div class="row text-center vtshrink">

                    <div class="container text-center">
                        <div class="row"><span class="title">Messages</span></div>
                        <div class="row text-center">
                            <div id="messages" class="col-8 text-center rounded">
                                <c:choose>
                                    <c:when test="${boxmsg} == null || ${boxmsg}  == ''">
                                        Welcome...
                                    </c:when>
                                    <c:otherwise>
                                        ${boxmsg}
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                    <div class="form-group text-center">
                        <div class="container vbshrink">
                            <div class="row">
                                <div class="col-12">
                                    <form action="/vendingAPI/jsp/vendItem" method="post">
                                        <label for="itemDisplay" class="itemTitle">Item: </label>
                                        <input id="itemDisplay" class="rounded" type="text" readonly
                                               value="${displayID}" name="displayID"><br/>
                                        <input type="hidden" name="totalCash" value="${totalCash}">
                                        <input type="hidden" name="selectedItem" value="${selectedItem}">
                                        <button type="submit" id="purchaseButton" name="purchase">Make Purchase</button>
                                    </form>

                                </div>
                            </div>
                        </div>
                    </div>
                    <hr/>
                </div>
                <div class="row text-center vtshrink" style="padding-bottom: 10px">
                    <div class="container text-center">
                        <div class="row"><span class="title">Change</span></div>
                        <div class="row text-center">
                            <div id="changeDisplay" class="col-8 text-center rounded">
                                ${change.quarters} &nbsp
                                ${change.dimes}<br/>
                                ${change.nickels}&nbsp
                                ${change.pennies}<br/>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-12">
                                <form action="/vendingAPI/jsp/getChange" method="post">
                                    <input type="hidden" name="totalCash" value="${totalCash}">
                                    <button type="submit" id="changeButton" name="change">Coin Return</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Placed at the end of the document so the pages load faster -->
<script src="${pageContext.request.contextPath}/js/jquery-3.2.1.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

</body>
</html>

