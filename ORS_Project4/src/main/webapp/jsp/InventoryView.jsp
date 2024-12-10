<%@page import="java.util.Map"%>
<%@page import="com.rays.pro4.controller.InventoryCtl"%>
<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.rays.pro4.Util.DataUtility"%>
<%@page import="com.rays.pro4.Util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16*16" />
<title>Inventory Page</title>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
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
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.InventoryBean"
		scope="request"></jsp:useBean>
	<%@ include file="Header.jsp"%>

	<center>

		<form action="<%=ORSView.INVENTORY_CTL%>" method="post">

			<div align="center">
				<h1>

					<%
						if (bean != null && bean.getId() > 0) {
					%>
					<tr>
						<th><font size="5px"> Update Inventory </font></th>
					</tr>
					<%
						} else {
					%>
					<tr>
						<th><font size="5px"> Add Inventory </font></th>
					</tr>
					<%
						}
					%>
				</h1>
				<h3>
					<font color="red"> <%=ServletUtility.getErrorMessage(request)%></font>
					<font color="green"> <%=ServletUtility.getSuccessMessage(request)%></font>
				</h3>

			</div>

			<%
				Map map = (Map) request.getAttribute("ilnes");
			%>

			<input type="hidden" name="id" value="<%=bean.getId()%>">

			<table>
				<tr>
					<th align="left"> SupplierName <span style="color: red">*</span> :
					</th>

					<td><input type="text" name="supplierName"
						placeholder="Enter supplierName " size="25"
						oninput="handleLetterInput(this, 'supplierNameError', 20)"
						onblur="validateLetterInput(this, 'supplierNameError', 20)"
						value="<%=DataUtility.getStringData(bean.getSupplierName())%>">
						<font color="red" id="supplierNameError"> <%=ServletUtility.getErrorMessage("supplierName", request)%></td>
				</tr>

				<tr>
					<th style="padding: 1px"></th>
				</tr>
				<tr>
					<th align="left">LastUpdatedDate <span style="color: red">*</span>
						:
					</th>
					<td><input type="text" name="lastUpdatedDate"
						placeholder="Enter lastUpdatedDate"dateOfVisit"  " size="25" id="datepicker"
						readonly="readonly"
						value="<%=DataUtility.getDateString(bean.getLastUpdatedDate())%>">
						<font color="red"> <%=ServletUtility.getErrorMessage("lastUpdatedDate", request)%></font></td>
				</tr>
				<tr>
					<th style="padding: 1px"></th>
				</tr>
				<tr>
					<th align="left">Quantity <span style="color: red">*</span> :
					</th>

					<td><input type="text" name="quantity"
						placeholder="Enter quantity" size="25"
						oninput=" handleIntegerInput(this, 'quantityError', 10)"
						onblur="validateIntegerInput(this, 'quantityError', 10)"
							value="<%=DataUtility.getStringData(bean.getQuantity()).equals("0") ? ""
					: DataUtility.getStringData(bean.getQuantity())%>">
						<font color="red" id="quantityError"> <%=ServletUtility.getErrorMessage("quantity", request)%></td>
				</tr>

				<th style="padding: 1px"></th>
				</tr>

				<tr>
					<th align="left">Product <span style="color: red">*</span> :
					</th>
					<td><%=HTMLUtility.getList2("product", String.valueOf(bean.getProduct()), map)%>

						<font color="red"> <%=ServletUtility.getErrorMessage("product", request)%></font></br>

					</td>
				</tr>

				<tr>
					<th style="padding: 1px"></th>
				</tr>

				<tr>
					<th></th>
					<%
						if (bean.getId() > 0) {
					%>
					<td colspan="2">&nbsp; &emsp; <input type="submit"
						name="operation" value="<%=InventoryCtl.OP_UPDATE%>"> &nbsp;
						&nbsp; <input type="submit" name="operation"
						value="<%=InventoryCtl.OP_CANCEL%>"></td>

					<%
						} else {
					%>

					<td colspan="2">&nbsp; &emsp; <input type="submit"
						name="operation" value="<%=InventoryCtl.OP_SAVE%>"> &nbsp;
						&nbsp; <input type="submit" name="operation"
						value="<%=InventoryCtl.OP_RESET%>"></td>

					<%
						}
					%>
				</tr>
			</table>
		</form>
	</center>

	<%@ include file="Footer.jsp"%>
</body>
</html>