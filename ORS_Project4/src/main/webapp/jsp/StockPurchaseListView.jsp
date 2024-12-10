<%@page import="java.util.Map"%>
<%@page import="com.rays.pro4.controller.StockPurchaseListCtl"%>
<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="com.rays.pro4.Bean.StockPurchaseBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="com.rays.pro4.Util.DataUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.rays.pro4.Util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>StockPurchase page</title>
<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script src="<%=ORSView.APP_CONTEXT%>/js/Checkbox11.js"></script>

</head>
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="<%=ORSView.APP_CONTEXT%>/js/utilities.js"></script>
<script>
$( function() {
    $( "#datepicker" ).datepicker({
      changeMonth: true,
      changeYear: true,
     
		yearRange : '1980:2002',
		dateFormat : 'yy/mm/dd',
	
    });
	});

	function limitInputLength(input, maxLength) {
		if (input.value.length > maxLength) {
			input.value = input.value.slice(0, maxLength);
		}
	}
</script>
<body>
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.StockPurchaseBean"
		scope="request"></jsp:useBean>
	<%@include file="Header.jsp"%>
	<form action="<%=ORSView.STOCKPURCHASE_LIST_CTL%>" method="post">
		<center>

			<div align="center">
				<h1>StockPurchase List</h1>
				<h3>
					<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
					<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
				</h3>

			</div>
			<%
				Map map = (Map) request.getAttribute("ilnes");

				int next = DataUtility.getInt(request.getAttribute("nextlist").toString());
			%>

			<%
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int index = ((pageNo - 1) * pageSize) + 1;

				List list = ServletUtility.getList(request);
				Iterator<StockPurchaseBean> it = list.iterator();
				if (list.size() != 0) {
			%>
			<table width="100%" align="center">
				<div>

					<td align="center"><label>Quantity</font> :
					</label> <input type="text" name="quantity" placeholder="Enter quantity"
					    oninput="handleIntegerInput(this, 'quantityError', 20)"
						onblur="validateIntegerInput(this, 'quantityError', 20)"
						value="<%=ServletUtility.getParameter("quantity", request)%>">
						<br> <font color="red" id="quantityError"> <%=ServletUtility.getErrorMessage("quantity", request)%></font>

					</td>

					<td align="center"><label>PurchasePrice</font>:
					</label><input type="text" name="purchasePrice" 
					    oninput="handleIntegerInput(this, 'purchasePriceError', 20)"
						onblur="validateIntegerInput(this, 'purchasePriceError', 20)"
						placeholder="Enter purchasePrice"
						value="<%=ServletUtility.getParameter("purchasePrice", request)%>">
						<br> <font color="red" id="purchasePriceError"> <%=ServletUtility.getErrorMessage("purchasePrice", request)%></font>
					</td>

				</div>

				<td align="center"><label>PurchaseDate</font> :
				</label> <input type="text" name="purchaseDate" id="datepicker"
					placeholder="Enter purchaseDate" readonly="readonly"
					value="<%=ServletUtility.getParameter("purchaseDate", request)%>">
					</div>


					<div>
						<td><label>OrderType</font> :
						</label> <%=HTMLUtility.getList2("orderType", String.valueOf(bean.getOrderType()), map)%></td>

					</div>
				<td><input type="submit" name="operation"
					value="<%=StockPurchaseListCtl.OP_SEARCH%>"> <input
					type="submit" name="operation"
					value="<%=StockPurchaseListCtl.OP_RESET%>"></td>


			</table>
			<br>

			<table border="1" width="100%" align="center" cellpadding=6px
				cellspacing=".2">


				<tr style="background: Yellow">
					<th><input type="checkbox" id="select_all" name="select">Select
						All</th>

					<th>S.No.</th>
					<th>Quantity</th>
					<th>PurchasePrice</th>
					<th>PurchaseDate</th>
					<th>OrderType</th>
					<th>Edit</th>
				</tr>
				<%
					while (it.hasNext()) {
							bean = it.next();
				%>
				<tr align="center">
					<td><input type="checkbox" class="checkbox" name="ids"
						value="<%=bean.getId()%>"></td>

					<td><%=index++%></td>
					<td><%=bean.getQuantity()%></td>
					<td><%=bean.getPurchasePrice()%></td>
					<td><%=bean.getPurchaseDate()%></td>
					<td><%=map.get(Integer.parseInt(bean.getOrderType()))%></td>
					<td><a href="StockPurchaseCtl?id=<%=bean.getId()%>">Edit</td>
				</tr>
				<%
					}
				%>

				<table width="100%">

					<tr>
						<th></th>
						<%
							if (pageNo == 1) {
						%>
						<td><input type="submit" name="operation" disabled="disabled"
							value="<%=StockPurchaseListCtl.OP_PREVIOUS%>"></td>
						<%
							} else {
						%>
						<td><input type="submit" name="operation"
							value="<%=StockPurchaseListCtl.OP_PREVIOUS%>"></td>
						<%
							}
						%>

						<td><input type="submit" name="operation"
							value="<%=StockPurchaseListCtl.OP_DELETE%>"></td>
						<td align="center"><input type="submit" name="operation"
							value="<%=StockPurchaseListCtl.OP_NEW%>"></td>

						<td align="right"><input type="submit" name="operation"
							value="<%=StockPurchaseListCtl.OP_NEXT%>"
							<%=(list.size() < pageSize || next == 0) ? "disabled" : ""%>></td>



					</tr>
				</table>
				<%
					}
					if (list.size() == 0) {
				%>
				<td align="center"><input type="submit" name="operation"
					value="<%=StockPurchaseListCtl.OP_BACK%>"></td>


				<%
					}
				%>

				<input type="hidden" name="pageNo" value="<%=pageNo%>">
				<input type="hidden" name="pageSize" value="<%=pageSize%>">

				</form>
				</br>

				</br>
				</br>
				</br>
				</br>
				</br>
				</br>

				</center>
				<%@include file="Footer.jsp"%>
</body>
</html>
