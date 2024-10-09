<%@page import="java.util.Map"%>
<%@page import="com.rays.pro4.controller.CustomerCtl"%>
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
<title>Customer Page</title>

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
 <script type="text/javascript">
        function validatephone(event) {
            const input = event.target;
            // Remove any non-digit characters (except period)
            input.value = input.value.replace(/[^0-9.]/g, '');
            
            // Check if the input starts with a digit between 1 and 5
            if (input.value.length > 0 && input.value[0] >= '1' && input.value[0] <= '5') {
                // Clear the input field
                input.value = '';
                // Display an error message in Hinglish
                messageElement.textContent = 'Phone number should start with 6 to 9. Please enter a valid number.';
            } else {
                // Clear the error message if the input is valid
                messageElement.textContent = '';
            }
        }
    </script>
<body>
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.CustomerBean"
		scope="request"></jsp:useBean>
	<%@ include file="Header.jsp"%>

	<center>

		<form action="<%=ORSView.CUSTOMER_CTL%>" method="post">

			<div align="center">
				<h1>

					<%
						if (bean != null && bean.getId() > 0) {
					%>
					<tr>
						<th><font size="5px"> Update Customer </font></th>
					</tr>
					<%
						} else {
					%>
					<tr>
						<th><font size="5px"> Add Customer </font></th>
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

			<input type="hidden" name="id" value="<%=bean.getId()%>">

			<table>
				<tr>
					<th align="left">Client Name <span style="color: red">*</span>
						:
					</th>
					<td><input type="text" name="clientName"
						placeholder="Enter clientName" size="25"
						oninput="handleLetterInput(this, 'clientNameError', 20)"
						onblur="validateLetterInput(this,'clientNameError', 20)"
						value="<%=DataUtility.getStringData(bean.getClientName())%>">
						<font color="red"id="clientNameError"> <%=ServletUtility.getErrorMessage("clientName", request)%></font>
					</td>
				</tr>

				<tr>
					<th style="padding: 1px"></th>
				</tr>
				<tr>
					<th align="left"> Location <span style="color: red">*</span>
						:
					</th>
					<td><input type="text" name="location"
						placeholder="Enter Customer location " size="25" 
						value="<%=DataUtility.getStringData(bean.getLocation())%>"> <font
						color="red"> <%=ServletUtility.getErrorMessage("location", request)%></font></td>
				</tr>
				<tr>
					<th style="padding: 1px"></th>
				</tr>
				<tr>
					<th align="left">ContactNumber<span style="color: red">*</span> :
					</th>
					<td><input type="text" name="contactNumber" 
						placeholder="Enter contactNumber" size="25"
						onclick="validatephone(event)" 
						oninput="handleIntegerInput(this, 'contactNumberError', 10)"
						onblur="validateIntegerInput(this,'contactNumberError', 10)"
						value="<%=DataUtility.getStringData(bean.getContactNumber()).equals("0") ? ""
					: DataUtility.getStringData(bean.getContactNumber())%>">
						<font color="red" id="contactNumberError"> <%=ServletUtility.getErrorMessage("contactNumber", request)%></font></br>

						</font></td>
				</tr>
				<tr>
					<th style="padding: 1px"></th>
				</tr>

				<tr>
					<th align="left"> importance <span style="color: red">*</span> :
					</th>
					<td><%=HTMLUtility.getList2("importance", String.valueOf(bean.getImportance()), map)%>

						<font color="red"> <%=ServletUtility.getErrorMessage("importance", request)%></font></br>

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
						name="operation" value="<%=CustomerCtl.OP_UPDATE%>"> &nbsp;
						&nbsp; <input type="submit" name="operation"
						value="<%=CustomerCtl.OP_CANCEL%>"></td>

					<%
						} else {
					%>

					<td colspan="2">&nbsp; &emsp; <input type="submit"
						name="operation" value="<%=CustomerCtl.OP_SAVE%>"> &nbsp;
						&nbsp; <input type="submit" name="operation"
						value="<%=CustomerCtl.OP_RESET%>"></td>

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
