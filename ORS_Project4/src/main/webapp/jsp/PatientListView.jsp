<%@page import="java.util.Map"%>
<%@page import="com.rays.pro4.controller.PatientListCtl"%>
<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="com.rays.pro4.Bean.PatientBean"%>
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
<title>Patient page</title>
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
	<%@include file="Header.jsp"%>
	<form action="<%=ORSView.PATIENT_LIST_CTL%>" method="post">
		<center>

			<div align="center">
				<h1>Patient List</h1>
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
				Iterator<PatientBean> it = list.iterator();
				if (list.size() != 0) {
			%>
			<table width="100%" align="center">
				<div>

					<td align="center"><label>Name</font> :
					</label> <input type="text" name="name"
						placeholder="Enter name"
						oninput="handleLetterInput(this,'nameError', 50)"
						onblur="validateLetterInput(this,'nameError', 50)"
						value="<%=ServletUtility.getParameter("name", request)%>">
						<br> <font color="red" id="nameError"> <%=ServletUtility.getErrorMessage("name", request)%></font>

					</td>
				</div>

				<td align="center"><label>DateOfVisit</font> :
				</label> <input type="text" name="dateOfVisit" id="datepicker"
					placeholder="Enter dateOfVisit" readonly="readonly"
					value="<%=ServletUtility.getParameter("dateOfVisit", request)%>">
					<div>

						<td align="center"><label>Mobile</font>:
						</label><input type="text" name="mobile" id="mobileInput"
							placeholder="Enter mobile"
							oninput="handleIntegerInput(this, 'mobileError', 10)"
							onblur="validateIntegerInput(this, 'mobileError', 10)"
							value="<%=ServletUtility.getParameter("mobile", request)%>">
							<br> <font color="red" id="mobileError"> <%=ServletUtility.getErrorMessage("mobile", request)%></font>
						</td>

					</div>


					<div>
						<td><label>Decease</font> :
						</label> <%=HTMLUtility.getList2("decease", String.valueOf(bean.getDecease()), map)%></td>

					</div>
				<td><input type="submit" name="operation"
					value="<%=PatientListCtl.OP_SEARCH%>"> <input type="submit"
					name="operation" value="<%=PatientListCtl.OP_RESET%>"></td>


			</table>
			<br>

		<table border ="1" width="100%" align="center" cellpadding=6px
				cellspacing=".2">


				<tr style="background: Yellow">
					<th><input type="checkbox" id="select_all" name="select">Select
						All</th>

					<th>S.No.</th>
					<th>Name </th>
					<th>DateOfVisit </th>
					<th>Mobile</th>
					<th>Decease</th>
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
					<td><%=bean.getName()%></td>
					<td><%=DataUtility.getDateString(bean.getDateOfVisit())%></td>
					<td><%=bean.getMobile()%></td>
					<td><%=map.get(Integer.parseInt(bean.getDecease()))%></td>
					<td><a href="PatientCtl?id=<%=bean.getId()%>">Edit</td>
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
							value="<%=PatientListCtl.OP_PREVIOUS%>"></td>
						<%
							} else {
						%>
						<td><input type="submit" name="operation"
							value="<%=PatientListCtl.OP_PREVIOUS%>"></td>
						<%
							}
						%>

						<td><input type="submit" name="operation"
							value="<%=PatientListCtl.OP_DELETE%>"></td>
						<td align="center"><input type="submit" name="operation"
							value="<%=PatientListCtl.OP_NEW%>"></td>

						<td align="right"><input type="submit" name="operation"
							value="<%=PatientListCtl.OP_NEXT%>"
							<%=(list.size() < pageSize || next == 0) ? "disabled" : ""%>></td>



					</tr>
				</table>
				<%
					}
					if (list.size() == 0) {
				%>
				<td align="center"><input type="submit" name="operation"
					value="<%=PatientListCtl.OP_BACK%>"></td>


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
