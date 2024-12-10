<%@page import="java.util.Map"%>
<%@page import="com.rays.pro4.controller.ClientListCtl"%>
<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="com.rays.pro4.Bean.ClientBean"%>
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
<title>Client page</title>
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
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.ClientBean"
		scope="request"></jsp:useBean>
	<%@include file="Header.jsp"%>
	<form action="<%=ORSView.CLIENT_LIST_CTL%>" method="post">
		<center>

			<div align="center">
				<h1>Client List</h1>
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
				Iterator<ClientBean> it = list.iterator();
				if (list.size() != 0) {
			%>
			<table width="100%" align="center">
				<div>

					<td align="center"><label>FullName</font> :
					</label> <input type="text" name="fullName"
						placeholder="Enter fullName"
						oninput="handleLetterInput(this,'fullNameError', 50)"
						onblur="validateLetterInput(this,'fullNameError', 50)"
						value="<%=ServletUtility.getParameter("fullName", request)%>">
						<br> <font color="red" id="fullNameError"> <%=ServletUtility.getErrorMessage("fullName", request)%></font>

					</td>
				</div>

				<td align="center"><label>AppointmentDate</font> :
				</label> <input type="text" name="appointmentDate" id="Udate"
					placeholder="Enter appointmentDate" readonly="readonly"
					value="<%=ServletUtility.getParameter("appointmentDate", request)%>">
					<div>

						<td align="center"><label>Phone</font>:
						</label><input type="text" name="phone" id="phoneInput"
							placeholder="Enter phone"
							oninput="handleIntegerInput(this, 'phoneError', 10)"
							onblur="validateIntegerInput(this, 'phoneError', 10)"
							value="<%=ServletUtility.getParameter("phone", request)%>">
							<br> <font color="red" id="phoneError"> <%=ServletUtility.getErrorMessage("phone", request)%></font>
						</td>

					</div>


					<div>
						<td><label>Illness</font> :
						</label> <%=HTMLUtility.getList2("illness", String.valueOf(bean.getIllness()), map)%></td>

					</div>
				<td><input type="submit" name="operation"
					value="<%=ClientListCtl.OP_SEARCH%>"> <input type="submit"
					name="operation" value="<%=ClientListCtl.OP_RESET%>"></td>


			</table>
			<br>

		<table border ="1" width="100%" align="center" cellpadding=6px
				cellspacing=".2">


				<tr style="background: Yellow">
					<th><input type="checkbox" id="select_all" name="select">Select
						All</th>

					<th>S.No.</th>
					<th>FullName </th>
					<th>AppointmentDate </th>
					<th>Phone</th>
					<th>Illness</th>
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
					<td><%=bean.getFullName()%></td>
					<td><%=DataUtility.getDateString(bean.getAppointmentDate())%></td>
					<td><%=bean.getPhone()%></td>
					<td><%=map.get(Integer.parseInt(bean.getIllness()))%></td>
					<td><a href="ClientCtl?id=<%=bean.getId()%>">Edit</td>
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
							value="<%=ClientListCtl.OP_PREVIOUS%>"></td>
						<%
							} else {
						%>
						<td><input type="submit" name="operation"
							value="<%=ClientListCtl.OP_PREVIOUS%>"></td>
						<%
							}
						%>

						<td><input type="submit" name="operation"
							value="<%=ClientListCtl.OP_DELETE%>"></td>
						<td align="center"><input type="submit" name="operation"
							value="<%=ClientListCtl.OP_NEW%>"></td>

						<td align="right"><input type="submit" name="operation"
							value="<%=ClientListCtl.OP_NEXT%>"
							<%=(list.size() < pageSize || next == 0) ? "disabled" : ""%>></td>



					</tr>
				</table>
				<%
					}
					if (list.size() == 0) {
				%>
				<td align="center"><input type="submit" name="operation"
					value="<%=ClientListCtl.OP_BACK%>"></td>


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
