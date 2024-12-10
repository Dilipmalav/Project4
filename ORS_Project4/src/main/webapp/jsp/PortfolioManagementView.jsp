<%@page import="java.util.Map"%>
<%@page import="com.rays.pro4.controller.PortfolioManagementCtl"%>
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
<title>PortfolioManagement Page</title>

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
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.PortfolioManagementBean"
		scope="request"></jsp:useBean>
	<%@ include file="Header.jsp"%>

	<center>

		<form action="<%=ORSView.PORTFOLIOMANAGEMENT_CTL%>" method="post">

			<div align="center">
				<h1>

					<%
						if (bean != null && bean.getId() > 0) {
					%>
					<tr>
						<th><font size="5px"> Update PortfolioManagement </font></th>
					</tr>
					<%
						} else {
					%>
					<tr>
						<th><font size="5px"> Add PortfolioManagement </font></th>
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
					<th align="left">Portfolio Name <span style="color: red">*</span>
						:
					</th>
					<td><input type="text" name="portfolioName"
						placeholder="Enter portfolioName" size="25"
						oninput="handleLetterInput(this, 'portfolioNameError', 35)"
						onblur="validateLetterInput(this,'portfolioNameError', 35)"
						value="<%=DataUtility.getStringData(bean.getPortfolioName())%>">
						<font color="red"id="portfolioNameError"> <%=ServletUtility.getErrorMessage("portfolioName", request)%></font>
					</td>
				</tr>
                     <tr>
					<th style="padding: 1px"></th>
				</tr>
				<tr>
					<th align="left"> InitialInvestmentAmount <span style="color: red">*</span> :
					</th>
					<td><input type="text" name="initialInvestmentAmount" 
						placeholder="Enter initialInvestmentAmount" size="25"
						oninput="handleIntegerInput(this, 'initialInvestmentAmountError', 20)"
						onblur="validateIntegerInput(this,'initialInvestmentAmountError', 20)"
						value="<%=DataUtility.getStringData(bean.getInitialInvestmentAmount()).equals("0") ? ""
					: DataUtility.getStringData(bean.getInitialInvestmentAmount())%>">
						<font color="red" id="initialInvestmentAmountError"> <%=ServletUtility.getErrorMessage("initialInvestmentAmount", request)%></font></br>

						</font></td>
				</tr>
				
				
				<tr>
					<th style="padding: 1px"></th>
				</tr>

				<tr>
					<th align="left"> RiskToleranceLevel <span style="color: red">*</span> :
					</th>
					<td><%=HTMLUtility.getList1("riskToleranceLevel", String.valueOf(bean.getRiskToleranceLevel()), map)%>

						<font color="red"> <%=ServletUtility.getErrorMessage("riskToleranceLevel", request)%></font></br>

					</td>
				</tr>
				
				<tr>
					<th style="padding: 1px"></th>
				</tr>
				<tr>
					<th align="left"> InvestmentStartegy <span style="color: red">*</span>
						:
					</th>
					<td><input type="text" name="investmentStartegy"
						placeholder="Enter investmentStartegy " size="25" 
						oninput="handleLetterInput(this, 'investmentStartegyError', 20)"
						onblur="validateLetterInput(this,'investmentStartegyError', 20)"
						value="<%=DataUtility.getStringData(bean.getInvestmentStartegy())%>"> <font
						color="red"> <%=ServletUtility.getErrorMessage("investmentStartegy", request)%></font></td>
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
						name="operation" value="<%=PortfolioManagementCtl.OP_UPDATE%>"> &nbsp;
						&nbsp; <input type="submit" name="operation"
						value="<%=PortfolioManagementCtl.OP_CANCEL%>"></td>

					<%
						} else {
					%>

					<td colspan="2">&nbsp; &emsp; <input type="submit"
						name="operation" value="<%=PortfolioManagementCtl.OP_SAVE%>"> &nbsp;
						&nbsp; <input type="submit" name="operation"
						value="<%=PortfolioManagementCtl.OP_RESET%>"></td>

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
