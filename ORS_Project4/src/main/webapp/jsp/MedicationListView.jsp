<%@page import="java.util.Map"%>
<%@page import="com.rays.pro4.controller.MedicationListCtl"%>
<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="com.rays.pro4.Bean.MedicationBean"%>
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
<title>Medication page</title>
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
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.MedicationBean"
		scope="request"></jsp:useBean>
	<%@include file="Header.jsp"%>
	<form action="<%=ORSView.MEDICATION_LIST_CTL%>" method="post">
		<center>

			<div align="center">
				<h1>Medication List</h1>
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
				Iterator<MedicationBean> it = list.iterator();
				if (list.size() != 0) {
			%>
			<table width="100%" align="center">
				<div>

					<td align="center"><label>FullName</font> :
					</label> <input type="text" name="fullName" 
						placeholder="Enter fullName"
						oninput="handleLetterInput(this,'fullNameError', 200)"
						onblur="validateLetterInput(this,'fullNameError', 200)"
						value="<%=ServletUtility.getParameter("productName", request)%>">
						<br> <font color="red" id="fullNameError"> <%=ServletUtility.getErrorMessage("fullName", request)%></font>

					</td>
				</div>
				
				<div>
						<td><label>Illness</font> :
						</label> <%=HTMLUtility.getList2("illness", String.valueOf(bean.getIllness()), map)%></td>

					</div> 	

				<td align="center"><label>PrescriptionDate</font> :
				</label> <input type="text" name="prescriptionDate" id="Udate"
					placeholder="Enter prescriptionDate" readonly="readonly"
					value="<%=ServletUtility.getParameter("prescriptionDate", request)%>">
					<div>

						<td align="center"><label>Dosage (mg)</font>:
						</label><input type="text" name="dosage" id="dosageInput"
							placeholder="Enter dosage"
							oninput="handleIntegerInput(this, 'dosageError', 10)"
							onblur="validateIntegerInput(this, 'dosageError', 10)"
							value="<%=ServletUtility.getParameter("dosage", request)%>">
							<br> <font color="red" id="dosageError"> <%=ServletUtility.getErrorMessage("dosage", request)%></font>
						</td>

					</div>


					
				
					<td><input type="submit" name="operation"
						value="<%=MedicationListCtl.OP_SEARCH%>"> 
					<input type="submit" name="operation"
						value="<%=MedicationListCtl.OP_RESET%>"></td>
				

			</table>
			<br>

			<table bmedication="1" width="100%" align="center" cellpadding=6px
				cellspacing=".2">


				<tr style="background: Yellow">
					<th><input type="checkbox" id="select_all" name="select">Select
						All</th>

					<th>S.No.</th>
					<th>Full Name</th>
					<th>Illness</th>
					<th>PrescriptionDate</th>
					<th>Dosage(mg)</th>
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
					<td><%=map.get(Integer.parseInt(bean.getIllness()))%></td>
					<td><%=bean.getPrecriptionDate()%></td>
					<td><%=bean.getDosage()%></td>
					<td><a href="MedicationCtl?id=<%=bean.getId()%>">Edit</td>
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
							value="<%=MedicationListCtl.OP_PREVIOUS%>"></td>
						<%
							} else {
						%>
						<td><input type="submit" name="operation"
							value="<%=MedicationListCtl.OP_PREVIOUS%>"></td>
						<%
							}
						%>

						<td><input type="submit" name="operation"
							value="<%=MedicationListCtl.OP_DELETE%>"></td>
						<td align="center"><input type="submit" name="operation"
							value="<%=MedicationListCtl.OP_NEW%>"></td>

						<td align="right"><input type="submit" name="operation"
							value="<%=MedicationListCtl.OP_NEXT%>"
							<%=(list.size() < pageSize || next == 0) ? "disabled" : ""%>></td>



					</tr>
				</table>
				<%
					}
					if (list.size() == 0) {
				%>
				<td align="center"><input type="submit" name="operation"
					value="<%=MedicationListCtl.OP_BACK%>"></td>


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
