<%@page import="java.util.Map"%>
<%@page import="com.rays.pro4.controller.ClientCtl"%>
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
<title>Client Page</title>

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
     
		yearRange : '1980:2025',
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
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.ClientBean"
		scope="request"></jsp:useBean>
	<%@ include file="Header.jsp"%>

	<center>

		<form action="<%=ORSView.CLIENT_CTL%>" method="post">

			<div align="center">
				<h1>

					<%
						if (bean != null && bean.getId() > 0) {
					%>
					<tr>
						<th><font size="5px"> Update Client </font></th>
					</tr>
					<%
						} else {
					%>
					<tr>
						<th><font size="5px"> Add Client </font></th>
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
					<th align="left">FullName <span style="color: red">*</span> :
					</th>

					<td><input type="text" name="fullName"
						placeholder="Enter fullName " size="25"
						oninput="handleLetterInput(this, 'fullNameError', 20)"
						onblur="validateLetterInput(this, 'fullNameError', 20)"
						value="<%=DataUtility.getStringData(bean.getFullName())%>">
						<font color="red" id="fullNameError"> <%=ServletUtility.getErrorMessage("fullName", request)%></td>
				</tr>

				<tr>
					<th style="padding: 1px"></th>
				</tr>
				<tr>
					<th align="left">AppointmentDate <span style="color: red">*</span>
						:
					</th>
					<td><input type="text" name="appointmentDate"
						placeholder="Enter appointmentDate  " size="25" id="datepicker"
						readonly="readonly"
						value="<%=DataUtility.getDateString(bean.getAppointmentDate())%>">
						<font color="red"> <%=ServletUtility.getErrorMessage("appointmentDate", request)%></font></td>
				</tr>
				<tr>
					<th style="padding: 1px"></th>
				</tr>
				<tr>
					<th align="left">Phone <span style="color: red">*</span> :
					</th>

					<td><input type="text" name="phone"
						placeholder="Enter Phone No" size="25"
						oninput="handleMobileNumberInput(this, 'phoneError', 10)"
						onblur="validateIntegerInput(this, 'phoneError', 10)"
							value="<%=DataUtility.getStringData(bean.getPhone()).equals("0") ? ""
					: DataUtility.getStringData(bean.getPhone())%>">
						<font color="red" id="phoneError"> <%=ServletUtility.getErrorMessage("phone", request)%></td>
				</tr>

				<th style="padding: 1px"></th>
				</tr>

				<tr>
					<th align="left">Illness <span style="color: red">*</span> :
					</th>
					<td><%=HTMLUtility.getList2("illness", String.valueOf(bean.getIllness()), map)%>

						<font color="red"> <%=ServletUtility.getErrorMessage("illness", request)%></font></br>

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
						name="operation" value="<%=ClientCtl.OP_UPDATE%>"> &nbsp;
						&nbsp; <input type="submit" name="operation"
						value="<%=ClientCtl.OP_CANCEL%>"></td>

					<%
						} else {
					%>

					<td colspan="2">&nbsp; &emsp; <input type="submit"
						name="operation" value="<%=ClientCtl.OP_SAVE%>"> &nbsp;
						&nbsp; <input type="submit" name="operation"
						value="<%=ClientCtl.OP_RESET%>"></td>

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