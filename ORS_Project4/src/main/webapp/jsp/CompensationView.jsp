<%@page import="java.util.Map"%>
<%@page import="com.rays.pro4.controller.CompensationCtl"%>
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
<title>Compensation Page</title>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="<%=ORSView.APP_CONTEXT%>/js/utilities.js"></script>
<script>
	$(function() {
		$("#udatee").datepicker({
			changeMonth : true,
			changeYear : true,
			yearRange : '1980:2002',
		});
	});
	function limitInputLength(input, maxLength) {
		if (input.value.length > maxLength) {
			input.value = input.value.slice(0, maxLength);
		}
	}
</script>
<body>
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.CompensationBean"
		scope="request"></jsp:useBean>
	<%@ include file="Header.jsp"%>

	<center>

		<form action="<%=ORSView.COMPENSATION_CTL%>" method="post">

			<div align="center">
				<h1>

					<%
						if (bean != null && bean.getId() > 0) {
					%>
					<tr>
						<th><font size="5px"> Update Compensation </font></th>
					</tr>
					<%
						} else {
					%>
					<tr>
						<th><font size="5px"> Add Compensation </font></th>
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
				Map map1 = (Map) request.getAttribute("stafflist");
			%>
			
			<%
				Map map2 = (Map) request.getAttribute("statelist");
			%>

			<input type="hidden" name="id" value="<%=bean.getId()%>">

			<table>
				<tr>
					<th style="padding: 1px"></th>
				</tr>

				<tr>
					<th align="left">StaffMember <span style="color: red">*</span> :
					</th>
					<td><%=HTMLUtility.getList1("staffMember", String.valueOf(bean.getStaffMember()), map1)%>

						<font color="red"> <%=ServletUtility.getErrorMessage("staffMember", request)%></font></br>

					</td>
				</tr>
				
				<tr>
					<th align="left">Payment amount<span style="color: red">*</span> :
					</th>
					<td><input type="text" name="paymentAmount" id="paymentAmountInput"
						placeholder="Enter Quantity" style="width: 198px"
						oninput="handleIntegerInput(this, 'paymentAmountError', 5)"
						onblur="validateIntegerInput(this,'paymentAmountError', 5)"
						value="<%=DataUtility.getStringData(bean.getPaymemtAmount()).equals("0") ? ""
					: DataUtility.getStringData(bean.getPaymemtAmount())%>">

						<font color="red" id="paymentAmountError"> <%=ServletUtility.getErrorMessage("paymentAmount", request)%></font></br>

						</font></td>
				</tr>
				<tr>
					<th style="padding: 1px"></th>
				</tr>
				<tr>
					<th align="left">Applied Date <span style="color: red">*</span>
						:
					</th>
					<td><input type="text" name="dateApplied"
						placeholder="Enter dateApplied" size="25" id="udatee"
						value="<%=DataUtility.getDateString(bean.getDateApplied())%>"> 
					<font 	color="red"> <%=ServletUtility.getErrorMessage("dateApplied", request)%></font></td>
				</tr>
				<tr>
					<th style="padding: 1px"></th>
				</tr>

				<tr>
					<th align="left">State <span style="color: red">*</span> :
					</th>
					<td><%=HTMLUtility.getList2("state", String.valueOf(bean.getState()), map2)%>

						<font color="red"> <%=ServletUtility.getErrorMessage("state", request)%></font></br>

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
						name="operation" value="<%=CompensationCtl.OP_UPDATE%>"> &nbsp;
						&nbsp; <input type="submit" name="operation"
						value="<%=CompensationCtl.OP_CANCEL%>"></td>

					<%
						} else {
					%>

					<td colspan="2">&nbsp; &emsp; <input type="submit"
						name="operation" value="<%=CompensationCtl.OP_SAVE%>"> &nbsp;
						&nbsp; <input type="submit" name="operation"
						value="<%=CompensationCtl.OP_RESET%>"></td>

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
