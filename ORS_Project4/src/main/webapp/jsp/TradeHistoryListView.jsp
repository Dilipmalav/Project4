<%@page import="java.util.Map"%>
<%@page import="com.rays.pro4.controller.TradeHistoryListCtl"%>
<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="com.rays.pro4.Bean.TradeHistoryBean"%>
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
<title>TradeHistory page</title>
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

	$(function() {
		$("#Udatee").datepicker({
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
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.TradeHistoryBean"
		scope="request"></jsp:useBean>
	<%@include file="Header.jsp"%>
	<form action="<%=ORSView.TRADEHISTORY_LIST_CTL%>" method="post">
		<center>

			<div align="center">
				<h1>TradeHistory List</h1>
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
				Iterator<TradeHistoryBean> it = list.iterator();
				if (list.size() != 0) {
			%>
			<table width="100%" align="center">
				<div>

					<td align="center"><label>UserId</font> :
					</label> <input type="text" name="userId"
						placeholder="Enter userId"
						oninput="validateAlphanumericInput(this, 'userIdError', 50)"
						onblur="validateLetterInput(this,'userIdError', 50)"
						value="<%=ServletUtility.getParameter("userId", request)%>">
						<br> <font color="red" id="userIdError"> <%=ServletUtility.getErrorMessage("userId", request)%></font>

					</td>
				</div>
				
                <div>
				<td align="center"><label>StartDate</font> :
				</label> <input type="text" name="startDate" id="Udate"
					placeholder="Enter startDate" readonly="readonly"
					value="<%=ServletUtility.getParameter("startDate", request)%>">
					</div>
					
					 <div>
				<td align="center"><label>EndDate</font> :
				</label> <input type="text" name="endDate" id="Udatee"
					placeholder="Enter endDate" readonly="readonly"
					value="<%=ServletUtility.getParameter("endDate", request)%>">
					</div>


					<div>
						<td><label>Illness</font> :
						</label> <%=HTMLUtility.getList2("transctionType", String.valueOf(bean.getTrasactionType()), map)%></td>

					</div>
				<td><input type="submit" name="operation"
					value="<%=TradeHistoryListCtl.OP_SEARCH%>"> <input type="submit"
					name="operation" value="<%=TradeHistoryListCtl.OP_RESET%>"></td>


			</table>
			<br>

		<table border ="1" width="100%" align="center" cellpadding=6px
				cellspacing=".2">


				<tr style="background: Yellow">
					<th><input type="checkbox" id="select_all" name="select">Select
						All</th>

					<th>S.No.</th>
					<th>UserId </th>
					<th>StartDate </th>
					<th>EndDate</th>
					<th>TransctionType</th>
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
					<td><%=bean.getUserId()%></td>
					<td><%=bean.getStartDate()%></td>
					<td><%=bean.getEndDate()%></td>
					<td><%=map.get(Integer.parseInt(bean.getTrasactionType()))%></td>
					<td><a href="TradeHistoryCtl?id=<%=bean.getId()%>">Edit</td>
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
							value="<%=TradeHistoryListCtl.OP_PREVIOUS%>"></td>
						<%
							} else {
						%>
						<td><input type="submit" name="operation"
							value="<%=TradeHistoryListCtl.OP_PREVIOUS%>"></td>
						<%
							}
						%>

						<td><input type="submit" name="operation"
							value="<%=TradeHistoryListCtl.OP_DELETE%>"></td>
						<td align="center"><input type="submit" name="operation"
							value="<%=TradeHistoryListCtl.OP_NEW%>"></td>

						<td align="right"><input type="submit" name="operation"
							value="<%=TradeHistoryListCtl.OP_NEXT%>"
							<%=(list.size() < pageSize || next == 0) ? "disabled" : ""%>></td>



					</tr>
				</table>
				<%
					}
					if (list.size() == 0) {
				%>
				<td align="center"><input type="submit" name="operation"
					value="<%=TradeHistoryListCtl.OP_BACK%>"></td>


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
