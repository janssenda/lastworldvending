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

                    <form action="${pageContext.request.contextPath}/jsp/toggleRealism" method="post">
                        <input type="hidden" name="totalCash" value="${totalCash}">
                        <input type="hidden" name="selectedItem" value="${selectedItem}">
                        <input type="hidden" name="displayID" value="${displayID}">
                        <input type="hidden" name="boxmsg" value="${boxmsg}">



                        <c:if test="${realism == true}">
                            Realism is
                            <button id="rmodeOn" name="realismMode" class="rounded" type="submit">
                                <span style="color:lawngreen; font-weight: bold">ON:</span>
                            </button>
                            <span style="color:teal"> ${realismType} </span>
                        </c:if>
                        <c:if test="${realism == false}">
                            Realism is
                            <button id="rmodeOff" name="realismMode" class="rounded" type="submit">
                                <span style="color:grey; font-weight: bold">OFF</span>
                            </button>
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

                <c:if test="${productList != null}">
                    <div id="showResults" class="rounded">
                        <p class="resultP">
                                ${messages} <br/>

                            <c:forEach items="${productList}" var="product" varStatus="loop">

                        <div class="row">
                            <div class="col-sm-8 productDesc text-left">
                                Product: ${product.productName} <br/>
                                Best by: ${product.bestByStr}<br/>
                                Name: ${product.message}<br/><br/>
                            </div>
                            <div class="col-sm-4">
                                <img class="productImage"
                                     src="${pageContext.request.contextPath}/images/Output/${product.productName}.png"/>
                            </div>
                        </div>
                        <br/>

                        </c:forEach>
                        <form action="${pageContext.request.contextPath}/jsp/processRealRequest" method="post">
                            <input type="hidden" name="totalCash" value="${totalCash}">
                            <input type="hidden" name="selectedItem" value="${selectedItem}">
                            <input type="hidden" name="displayID" value="${displayID}">
                            <input type="hidden" name="boxmsg" value="${boxmsg}">
                            <button class="screenButton rounded" name="realAction" value="noaction">Okay</button>
                        </form>
                        </p>
                    </div>
                </c:if>

                <c:if test="${stuckItemList != null}">
                    <div id="showResults" class="rounded">
                        <p class="resultP">
                                ${messages}
                        <div class="col-sm-8 productDesc text-left">
                            <c:forEach items="${stuckItemList}" var="product">
                                ${product.key}: ${product.value} <br/>
                            </c:forEach>
                        </div>
                        <br/><br/>

                        <form action="${pageContext.request.contextPath}/jsp/processRealRequest" method="post">
                            <input type="hidden" name="totalCash" value="${totalCash}">
                            <input type="hidden" name="selectedItem" value="${selectedItem}">
                            <input type="hidden" name="displayID" value="${displayID}">
                            <input type="hidden" name="boxmsg" value="${boxmsg}">

                            <button class="screenButton rounded" name="realAction" value="noaction">Okay</button>
                            <button class="screenButton rounded" name="realAction" value="shake">Shake Machine</button>
                        </form>


                        </p>
                    </div>


                </c:if>

                <c:if test="${messageBox == true}">
                    <div id="showResults" class="rounded">
                        <p class="resultP">
                                ${messages}
                        <form action="${pageContext.request.contextPath}/jsp/processRealRequest" method="post">
                            <input type="hidden" name="totalCash" value="${totalCash}">
                            <input type="hidden" name="selectedItem" value="${selectedItem}">
                            <input type="hidden" name="displayID" value="${displayID}">
                            <input type="hidden" name="boxmsg" value="${boxmsg}">
                            <button class="screenButton rounded" name="realAction"
                                    value="noaction">${buttonText}</button>
                        </form>
                        </p>
                    </div>
                </c:if>


                <form action="${pageContext.request.contextPath}/jsp/loadItem" method="post">
                    <input type="hidden" name="totalCash" value="${totalCash}">
                    <input type="hidden" name="selectedItem" value="${selectedItem}">

                    <c:set var="rowLetters" value="${['A','B','C','D','E','F','G','H','I','J','K']}"/>
                    <c:set var="rowLetter" scope="session" value="${rowLetters[0]}"/>
                    <c:set var="colNumber" scope="session" value="1"/>
                    <c:set var="rowIndex" scope="session" value="0"/>

                    <div class='row text-center'>

                        <c:forEach items="${plist}" var="product" varStatus="loop">

                            <c:if test="${colNumber > 3}">
                                <c:set var="rowIndex" scope="session" value="${rowIndex + 1}"/>
                                <c:set var="colNumber" scope="session" value="1"/>
                                <c:set var="rowLetter" scope="session" value="${rowLetters[rowIndex]}"/>
                            </c:if>

                            <div class='col-sm-4 item-container'>
                                <button class='itemBox' name="selectedItemButton"
                                        value="${product.productName},${rowLetter}${colNumber}">
                                    <div class='item rounded'>
                                        <div class='itemInner'>
                                            <div class='itemDisplayNumber'>${rowLetter}${colNumber}</div>
                                            <div class='itemName'>${product.productName}</div>
                                            <div class='itemPrice'>${product.productPrice}</div>
                                            <div class='itemQuantity'>Remaining: ${product.productQty}</div>
                                        </div>
                                    </div>
                                </button>
                            </div>

                            <c:set var="colNumber" scope="session" value="${colNumber+1}"/>
                        </c:forEach>

                    </div>


                </form>
            </div>


            <div id="controlPanelMainDiv" class="col-sm-3 rounded">
                <div class="row text-center">
                    <div class="form-group text-center">
                        <div class="container vbshrink">

                            <label for="totalMoney" class="col-12 col-form-label title">
                                <span class="title">Welcome</span>
                            </label>

                            <form action="${pageContext.request.contextPath}/jsp/addMoney" method="post">
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
                                    <form action="${pageContext.request.contextPath}/jsp/vendItem" method="post">
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
                                <form action="${pageContext.request.contextPath}/jsp/getChange" method="post">
                                    <input type="hidden" name="totalCash" value="${totalCash}">
                                    <button type="submit" id="changeButton" name="change">Coin Return</button>
                                </form>
                                <c:if test="${realism == true}">
                                    <form action="${pageContext.request.contextPath}/jsp/processRealRequest" method="post">
                                        <input type="hidden" name="totalCash" value="${totalCash}">
                                        <input type="hidden" name="selectedItem" value="${selectedItem}">
                                        <input type="hidden" name="displayID" value="${displayID}">
                                        <input type="hidden" name="boxmsg" value="${boxmsg}">
                                        <button type="submit" id="viewButton" name="realAction" value="getItems">View
                                            Jammed
                                        </button>
                                    </form>
                                </c:if>
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

