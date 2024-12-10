<%@page import="java.util.Map"%>
<%@page import="com.rays.pro4.controller.CompensationListCtl"%>
<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="com.rays.pro4.Bean.CompensationBean"%>
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
<title>Compensation page</title>
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
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.CompensationBean"
		scope="request"></jsp:useBean>
	<%@include file="Header.jsp"%>
	<form action="<%=ORSView.COMPENSATION_LIST_CTL%>" method="post">
		<center>

			<div align="center">
				<h1>Compensation List</h1>
				<h3>
					<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
					<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
				</h3>

			</div>
			
			<%
				Map map1 = (Map) request.getAttribute("stafflist");
			
			     int next = DataUtility.getInt(request.getAttribute("nextlist").toString());
			%>
			
			<%
				Map map2 = (Map) request.getAttribute("statelist");
			%>

			<%
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int index = ((pageNo - 1) * pageSize) + 1;

				List list = ServletUtility.getList(request);
				Iterator<CompensationBean> it = list.iterator();
				if (list.size() != 0) {
			%>
			<table width="100%" align="center">
				<div>
						<td><label>StaffMember</font> :
						</label> <%=HTMLUtility.getList1("staffMember", String.valueOf(bean.getStaffMember()), map1)%></td>

					</div> 	

					<div>

						<td align="center"><label>PaymentAmount</font>:
						</label><input type="text" name="paymentAmount" id="paymentAmountInput"
							placeholder="Enter paymentAmount"
							oninput="handleIntegerInput(this, 'paymentAmountError', 10)"
							onblur="validateIntegerInput(this, 'paymentAmountError', 10)"
							value="<%=ServletUtility.getParameter("paymentAmount", request)%>">
							<br> <font color="red" id="paymentAmountError"> <%=ServletUtility.getErrorMessage("paymentAmount", request)%></font>
						</td>

					</div>
					
					<td align="center"><label>DateApplied</font> :
				</label> <input type="text" name="dateApplied" id="Udate"
					placeholder="Enter dateApplied" readonly="readonly"
					value="<%=ServletUtility.getParameter("dateApplied", request)%>">


					<div>
						<td><label>State</font> :
						</label> <%=HTMLUtility.getList2("state", String.valueOf(bean.getState()), map2)%></td>

					</div> 	
				
					<td><input type="submit" name="operation"
						value="<%=CompensationListCtl.OP_SEARCH%>"> 
					<input type="submit" name="operation"
						value="<%=CompensationListCtl.OP_RESET%>"></td>
				

			</table>
			<br>

			<table bcompensation="1" width="100%" align="center" cellpadding=6px
				cellspacing=".2">


				<tr style="background: Yellow">
					<th><input type="checkbox" id="select_all" name="select">Select
						All</th>

					<th>S.No.</th>
					<th>Staff Memeber</th>
					<th>Payment Amount</th>
					<th>Applied Date</th>
					<th>State</th>
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
					<td><%=map1.get(Integer.parseInt(bean.getStaffMember()))%></td>
					<td><%=bean.getPaymemtAmount()%></td>
					<td><%=bean.getDateApplied()%></td>
					<td><%=map2.get(Integer.parseInt(bean.getState()))%></td>
					<td><a href="CompensationCtl?id=<%=bean.getId()%>">Edit</td>
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
							value="<%=CompensationListCtl.OP_PREVIOUS%>"></td>
						<%
							} else {
						%>
						<td><input type="submit" name="operation"
							value="<%=CompensationListCtl.OP_PREVIOUS%>"></td>
						<%
							}
						%>

						<td><input type="submit" name="operation"
							value="<%=CompensationListCtl.OP_DELETE%>"></td>
						<td align="center"><input type="submit" name="operation"
							value="<%=CompensationListCtl.OP_NEW%>"></td>

						<td align="right"><input type="submit" name="operation"
							value="<%=CompensationListCtl.OP_NEXT%>"
							<%=(list.size() < pageSize || next == 0) ? "disabled" : ""%>></td>



					</tr>
				</table>
				<%
					}
					if (list.size() == 0) {
				%>
				<td align="center"><input type="submit" name="operation"
					value="<%=CompensationListCtl.OP_BACK%>"></td>


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
