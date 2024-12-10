<%@page import="java.util.Map"%>
<%@page import="com.rays.pro4.controller.TradeHistoryCtl"%>
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
<title>TradeHistory Page</title>

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
			dateformate : 'yy/mm/dd'
		});
	});

	$(function() {
		$("#udate").datepicker({
			changeMonth : true,
			changeYear : true,
			yearRange : '1980:2002',
			dateformate : 'yy/mm/dd'
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
	<%@ include file="Header.jsp"%>

	<center>

		<form action="<%=ORSView.TRADEHISTORY_CTL%>" method="post">

			<div align="center">
				<h1>

					<%
						if (bean != null && bean.getId() > 0) {
					%>
					<tr>
						<th><font size="5px"> Update TradeHistory </font></th>
					</tr>
					<%
						} else {
					%>
					<tr>
						<th><font size="5px"> Add TradeHistory </font></th>
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
					<th align="left">UserId <span style="color: red">*</span> :
					</th>

					<td><input type="text" name="userId"
						placeholder="Enter userId " size="25" maxlength="18"
						value="<%=DataUtility.getStringData(bean.getUserId())%>">
						<font color="red" id="userIdError"> <%=ServletUtility.getErrorMessage("userId", request)%></td>
				</tr>

				<tr>
					<th style="padding: 1px"></th>
				</tr>
				<tr>
					<th align="left">StartDate <span style="color: red">*</span>
						:
					</th>
					<td><input type="text" name="startDate"
						placeholder="Enter startDate  " size="25" id="udatee"
						readonly="readonly"
						value="<%=DataUtility.getDateString(bean.getStartDate())%>">
						<font color="red"> <%=ServletUtility.getErrorMessage("startDate", request)%></font></td>
				</tr>
				<tr>
					<th style="padding: 1px"></th>
				</tr>
				<tr>
					<th align="left">EndDate <span style="color: red">*</span>
						:
					</th>
					<td><input type="text" name="endDate"
						placeholder="Enter endDate  " size="25" id="udate"
						readonly="readonly"
						value="<%=DataUtility.getDateString(bean.getEndDate())%>">
						<font color="red"> <%=ServletUtility.getErrorMessage("endDate", request)%></font></td>
				</tr>
				<tr>
					<th style="padding: 1px"></th>
				</tr>

				<tr>
					<th align="left">TransctionType <span style="color: red">*</span> :
					</th>
					<td><%=HTMLUtility.getList2("transctionType", String.valueOf(bean.getTrasactionType()), map)%>

						<font color="red"> <%=ServletUtility.getErrorMessage("transctionType", request)%></font></br>

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
						name="operation" value="<%=TradeHistoryCtl.OP_UPDATE%>"> &nbsp;
						&nbsp; <input type="submit" name="operation"
						value="<%=TradeHistoryCtl.OP_CANCEL%>"></td>

					<%
						} else {
					%>

					<td colspan="2">&nbsp; &emsp; <input type="submit"
						name="operation" value="<%=TradeHistoryCtl.OP_SAVE%>"> &nbsp;
						&nbsp; <input type="submit" name="operation"
						value="<%=TradeHistoryCtl.OP_RESET%>"></td>

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