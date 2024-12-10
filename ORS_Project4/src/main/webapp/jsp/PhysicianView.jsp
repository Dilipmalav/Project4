<%@page import="java.util.Map"%>
<%@page import="com.rays.pro4.controller.PhysicianCtl"%>
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
<title>Physician Page</title>

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
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.PhysicianBean"
		scope="request"></jsp:useBean>
	<%@ include file="Header.jsp"%>

	<center>

		<form action="<%=ORSView.PHYSICIAN_CTL%>" method="post">

			<div align="center">
				<h1>

					<%
						if (bean != null && bean.getId() > 0) {
					%>
					<tr>
						<th><font size="5px"> Update Physician </font></th>
					</tr>
					<%
						} else {
					%>
					<tr>
						<th><font size="5px"> Add Physician </font></th>
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
				Map map = (Map) request.getAttribute("splztion");
			%>

			<input type="hidden" name="id" value="<%=bean.getId()%>">

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
					<th style="padding: 1px"></th>
				</tr>
				<tr>
					<th align="left">BirthDate  <span style="color: red">*</span>
						:
					</th>
					<td><input type="text" name="birthDate"
						placeholder="Enter  birthDate " size="25" id="udatee"
						readonly="readonly"
						value="<%=DataUtility.getDateString(bean.getBirthDate())%>"> <font
						color="red"> <%=ServletUtility.getErrorMessage("birthDate", request)%></font></td>
				</tr>
				<tr>
					<th style="padding: 1px"></th>
				</tr>
				<tr>
					<th align="left">Phone<span style="color: red">*</span> :
					</th>
					<td><input type="text" name="phone" id="phoneInput"
						placeholder="Enter phone" style="width: 198px"
						oninput="handleIntegerInput(this, 'phoneError', 10)"
						onblur="validateIntegerInput(this,'phoneError', 10)"
						value="<%=DataUtility.getStringData(bean.getPhone()).equals("0") ? ""
					: DataUtility.getStringData(bean.getPhone())%>">

						<font color="red" id="phoneError"> <%=ServletUtility.getErrorMessage("phone", request)%></font></br>

						</font></td>
				</tr>
				<tr>
					<th style="padding: 1px"></th>
				</tr>

				<tr>
					<th align="left">Specialization <span style="color: red">*</span> :
					</th>
					<td><%=HTMLUtility.getList2("specialization", String.valueOf(bean.getSpecialization()), map)%>

						<font color="red"> <%=ServletUtility.getErrorMessage("specialization", request)%></font></br>

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
						name="operation" value="<%=PhysicianCtl.OP_UPDATE%>"> &nbsp;
						&nbsp; <input type="submit" name="operation"
						value="<%=PhysicianCtl.OP_CANCEL%>"></td>

					<%
						} else {
					%>

					<td colspan="2">&nbsp; &emsp; <input type="submit"
						name="operation" value="<%=PhysicianCtl.OP_SAVE%>"> &nbsp;
						&nbsp; <input type="submit" name="operation"
						value="<%=PhysicianCtl.OP_RESET%>"></td>

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
