<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="com.rays.pro4.Util.DataUtility"%>
<%@page import="com.rays.pro4.Util.ServletUtility"%>
<%@page import="com.rays.pro4.controller.StaffCtl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
<head>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16*16" />
<title>Staff Page</title>

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
		//dateFormat:'yy-mm-dd'
		});
	});
</script>
<!-- <script>
	function validateNumber(input) {
		const value = input.value;

		// Only retain the valid numeric characters
		const numericValue = value.replace(/[^0-9]/g, '');

		if (value !== numericValue) {
			input.value = numericValue; // Update input with only numeric values
		}
	}
</script> -->
<body>
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.StaffBean"
		scope="request"></jsp:useBean>
	<%@ include file="Header.jsp"%>

	<center>

		<form action="<%=ORSView.STAFF_CTL%>" method="post">

			<div align="center">
				<h1>

					<%
						if (bean != null && bean.getId() > 0) {
					%>
					<tr>
						<th><font size="5px"> Update Staff </font></th>
					</tr>
					<%
						} else {
					%>
					<tr>
						<th><font size="5px"> Add Staff </font></th>
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
				Map map = (Map) request.getAttribute("prolist");
			%>

			<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden"
				name="createdDatetime"
				value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
			<input type="hidden" name="modifiedDatetime"
				value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

			<table>
				<tr>
					<th align="left">FullName  <span style="color: red">*</span>
						:
					</th>
					<td><input type="text" name="fullName"
						placeholder="Enter fullName" size="25"
						oninput="handleLetterInput(this, 'fullNameError', 20)"
						onblur="validateLetterInput(this,'fullNameError', 20)"
						value="<%=DataUtility.getStringData(bean.getFullName())%>">
						<font color="red" id="fullNameError"> <%=ServletUtility.getErrorMessage("fullName", request)%></font>
					</td>
				</tr>

				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr>
					<th align="left">Joining Date <span style="color: red">*</span>

					</th>
					<td><input type="text" name="joiningDate" id="udatee"
						placeholder="Enter Joining Date" size="25" 
						value="<%=DataUtility.getDateString(bean.getJoiningDate())%>">
					<font color="red"> <%=ServletUtility.getErrorMessage("joiningDate", request)%></td>
					
				</tr>
				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr>
					<th align="left"> PreviousEmployer <span style="color: red">*</span>
						:
					</th>
					<td><input type="text" name="previousEmployer"
						placeholder="Enter Previous Employer" size="25"
						oninput="handleLetterInput(this, 'previousEmployerError', 20)"
						onblur="validateLetterInput(this,'previousEmployerError', 20)"
						value="<%=DataUtility.getStringData(bean.getPreviousEmployer())%>">
						<font color="red" id="previousEmployerError"> <%=ServletUtility.getErrorMessage("previousEmployer", request)%></font>
				</tr>

				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr>
					<th align="left">Division <span style="color: red">*</span> :
					</th>
					<td>
						<%
							String hlist = HTMLUtility.getList2("division", String.valueOf(bean.getDivision()), map);
						%> <%=hlist%>
					<font color="red"> <%=ServletUtility.getErrorMessage("division", request)%></td>
				</tr>

				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr>
					<th></th>
					<%
						if (bean.getId() > 0) {
					%>
					<td colspan="2">&nbsp; &emsp; <input type="submit"
						name="operation" value="<%=StaffCtl.OP_UPDATE%>"> &nbsp;
						&nbsp; <input type="submit" name="operation"
						value="<%=StaffCtl.OP_CANCEL%>"></td>

					<%
						} else {
					%>

					<td colspan="2">&nbsp; &emsp; <input type="submit"
						name="operation" value="<%=StaffCtl.OP_SAVE%>"> &nbsp;
						&nbsp; <input type="submit" name="operation"
						value="<%=StaffCtl.OP_RESET%>"></td>

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