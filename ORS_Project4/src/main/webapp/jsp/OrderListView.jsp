<%@page import="java.util.Map"%>
<%@page import="com.rays.pro4.controller.OrderListCtl"%>
<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="com.rays.pro4.Bean.OrderBean"%>
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
<title>Order page</title>
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
	$(function() {
		$("#Udate").datepicker({
			changeMonth : true,
			changeYear : true,
			yearRange : '1980:2002',
		//dateFormat:'yy-mm-dd'
		});
	});

	function limitInputLength(input, maxLength) {
		if (input.value.length > maxLength) {
			input.value = input.value.slice(0, maxLength);
		}
	}
</script>
<body>
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.OrderBean"
		scope="request"></jsp:useBean>
	<%@include file="Header.jsp"%>
	<form action="<%=ORSView.ORDER_LIST_CTL%>" method="post">
		<center>

			<div align="center">
				<h1>Order List</h1>
				<h3>
					<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
					<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
				</h3>

			</div>
			<%
				Map map = (Map) request.getAttribute("prolist");

				int next = DataUtility.getInt(request.getAttribute("nextlist").toString());
			%>

			<%
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int index = ((pageNo - 1) * pageSize) + 1;

				List list = ServletUtility.getList(request);
				Iterator<OrderBean> it = list.iterator();
				if (list.size() != 0) {
			%>
			<table width="100%" align="center">
				<div>

					<td align="center"><label>ProductName</font> :
					</label> <input type="text" name="productName" 
						placeholder="Enter ProductName"
						oninput="handleLetterInput(this,'productNameError', 200)"
						onblur="validateLetterInput(this,'productNameError', 200)"
						value="<%=ServletUtility.getParameter("productName", request)%>">
						<br> <font color="red" id="productNameError"> <%=ServletUtility.getErrorMessage("productName", request)%></font>

					</td>
				</div>

				<td align="center"><label>Date</font> :
				</label> <input type="text" name="dob" id="Udate"
					placeholder="Enter Date of birth" readonly="readonly"
					value="<%=ServletUtility.getParameter("dob", request)%>">
					<div>

						<td align="center"><label>Quantity</font>:
						</label><input type="text" name="quantity" id="quantityInput"
							placeholder="Enter Quantity"
							oninput="handleIntegerInput(this, 'quantityError', 10)"
							onblur="validateIntegerInput(this, 'quantityError', 10)"
							value="<%=ServletUtility.getParameter("quantity", request)%>">
							<br> <font color="red" id="quantityError"> <%=ServletUtility.getErrorMessage("quantity", request)%></font>
						</td>

					</div>


					<div>
						<td><label>Customer</font> :
						</label> <%=HTMLUtility.getList2("customer", String.valueOf(bean.getCustomer()), map)%></td>

					</div> 	
				
					<td><input type="submit" name="operation"
						value="<%=OrderListCtl.OP_SEARCH%>"> 
					<input type="submit" name="operation"
						value="<%=OrderListCtl.OP_RESET%>"></td>
				

			</table>
			<br>

			<table border="1" width="100%" align="center" cellpadding=6px
				cellspacing=".2">


				<tr style="background: Yellow">
					<th><input type="checkbox" id="select_all" name="select">Select
						All</th>

					<th>S.No.</th>
					<th>Product Name</th>
					<th>Order date</th>
					<th>Quantity</th>
					<th>Customer</th>
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
					<td><%=bean.getProductName()%></td>
					<td><%=bean.getDob()%></td>
					<td><%=bean.getQuantity()%></td>
					<td><%=map.get(Integer.parseInt(bean.getCustomer()))%></td>
					<td><a href="OrderCtl?id=<%=bean.getId()%>">Edit</td>
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
							value="<%=OrderListCtl.OP_PREVIOUS%>"></td>
						<%
							} else {
						%>
						<td><input type="submit" name="operation"
							value="<%=OrderListCtl.OP_PREVIOUS%>"></td>
						<%
							}
						%>

						<td><input type="submit" name="operation"
							value="<%=OrderListCtl.OP_DELETE%>"></td>
						<td align="center"><input type="submit" name="operation"
							value="<%=OrderListCtl.OP_NEW%>"></td>

						<td align="right"><input type="submit" name="operation"
							value="<%=OrderListCtl.OP_NEXT%>"
							<%=(list.size() < pageSize || next == 0) ? "disabled" : ""%>></td>



					</tr>
				</table>
				<%
					}
					if (list.size() == 0) {
				%>
				<td align="center"><input type="submit" name="operation"
					value="<%=OrderListCtl.OP_BACK%>"></td>


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
