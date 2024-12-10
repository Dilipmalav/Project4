<%@page import="java.util.Map"%>
<%@page import="com.rays.pro4.controller.PatientCtl"%>
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
<title>Patient Page</title>

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
		
	
    });
	});
	function limitInputLength(input, maxLength) {
		if (input.value.length > maxLength) {
			input.value = input.value.slice(0, maxLength);
		}
	}
</script>
<body>
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.PatientBean"
		scope="request"></jsp:useBean>
	<%@ include file="Header.jsp"%>

	<center>

		<form action="<%=ORSView.PATIENT_CTL%>" method="post">

			<div align="center">
				<h1>

					<%
						if (bean != null && bean.getId() > 0) {
					%>
					<tr>
						<th><font size="5px"> Update Patient </font></th>
					</tr>
					<%
						} else {
					%>
					<tr>
						<th><font size="5px"> Add Patient </font></th>
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
					<th align="left">Name <span style="color: red">*</span> :
					</th>

					<td><input type="text" name="name"
						placeholder="Enter name " size="25"
						oninput="handleLetterInput(this, 'nameError', 20)"
						onblur="validateLetterInput(this, 'nameError', 20)"
						value="<%=DataUtility.getStringData(bean.getName())%>">
						<font color="red" id="nameError"> <%=ServletUtility.getErrorMessage("name", request)%></td>
				</tr>

				<tr>
					<th style="padding: 1px"></th>
				</tr>
				<tr>
					<th align="left">DateOfVisit <span style="color: red">*</span>
						:
					</th>
					<td><input type="text" name="dateOfVisit"
						placeholder="Enter dateOfVisit"dateOfVisit"  " size="25" id="datepicker"
						readonly="readonly"
						value="<%=DataUtility.getDateString(bean.getDateOfVisit())%>">
						<font color="red"> <%=ServletUtility.getErrorMessage("dateOfVisit", request)%></font></td>
				</tr>
				<tr>
					<th style="padding: 1px"></th>
				</tr>
				<tr>
					<th align="left">Mobile <span style="color: red">*</span> :
					</th>

					<td><input type="text" name="mobile"
						placeholder="Enter mobile" size="25"
						oninput="handleMobileNumberInput(this, 'mobileError', 10)"
						onblur="validateIntegerInput(this, 'mobileError', 10)"
							value="<%=DataUtility.getStringData(bean.getMobile()).equals("0") ? ""
					: DataUtility.getStringData(bean.getMobile())%>">
						<font color="red" id="mobileError"> <%=ServletUtility.getErrorMessage("mobile", request)%></td>
				</tr>

				<th style="padding: 1px"></th>
				</tr>

				<tr>
					<th align="left">Decease <span style="color: red">*</span> :
					</th>
					<td><%=HTMLUtility.getList2("decease", String.valueOf(bean.getDecease()), map)%>

						<font color="red"> <%=ServletUtility.getErrorMessage("decease", request)%></font></br>

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
						name="operation" value="<%=PatientCtl.OP_UPDATE%>"> &nbsp;
						&nbsp; <input type="submit" name="operation"
						value="<%=PatientCtl.OP_CANCEL%>"></td>

					<%
						} else {
					%>

					<td colspan="2">&nbsp; &emsp; <input type="submit"
						name="operation" value="<%=PatientCtl.OP_SAVE%>"> &nbsp;
						&nbsp; <input type="submit" name="operation"
						value="<%=PatientCtl.OP_RESET%>"></td>

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