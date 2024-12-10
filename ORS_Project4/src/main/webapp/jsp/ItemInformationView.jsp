<%@page import="java.util.Map"%>
<%@page import="com.rays.pro4.controller.ItemInformationCtl"%>
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
<title>ItemInformation Page</title>

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
<body>
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.ItemInformationBean"
		scope="request"></jsp:useBean>
	<%@ include file="Header.jsp"%>

	<center>

		<form action="<%=ORSView.ITEMINFORMATION_CTL%>" method="post">

			<div align="center">
				<h1>

					<%
						if (bean != null && bean.getId() > 0) {
					%>
					<tr>
						<th><font size="5px"> Update ItemInformation </font></th>
					</tr>
					<%
						} else {
					%>
					<tr>
						<th><font size="5px"> Add ItemInformation </font></th>
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
					<th align="left"> Title <span style="color: red">*</span> :
					</th>

					<td><input type="text" name="title"
						placeholder="Enter title " size="25"
						oninput="handleLetterInput(this, 'titleError', 20)"
						onblur="validateLetterInput(this, 'titleError', 20)"
						value="<%=DataUtility.getStringData(bean.getTitle())%>">
						<font color="red" id="titleError"> <%=ServletUtility.getErrorMessage("title", request)%></td>
				</tr>

				    <tr>
                  <th align="left">OverView <span style="color: red">*</span> :</th>
              <td>
        <textarea name="overView" placeholder="Enter overView" rows="5" cols="25"
            oninput="handleLetterInput(this, 'overViewError', 250)"><%=DataUtility.getStringData(bean.getOverView())%></textarea>
          <font color="red" id="overViewError"> <%=ServletUtility.getErrorMessage("overView", request)%></td>
          
               </td>
               
                 </tr>

				<tr>
					<th style="padding: 1px"></th>
				</tr>
				
				<tr>
					<th align="left">Cost <span style="color: red">*</span> :
					</th>

					<td><input type="text" name="cost"
						placeholder="Enter cost" size="25"
						oninput=" handleIntegerInput(this, 'costError', 20)"
						onblur="validateIntegerInput(this, 'costError', 20)"
							value="<%=DataUtility.getStringData(bean.getCost()).equals("0") ? ""
					: DataUtility.getStringData(bean.getCost())%>">
						<font color="red" id="costError"> <%=ServletUtility.getErrorMessage("cost", request)%></td>
				</tr>

				<tr>
					<th style="padding: 1px"></th>
				</tr>
				
				<tr>
					<th align="left">PurchaseDate <span style="color: red">*</span>:
					</th>
					
					<td><input type="text" name="purchaseDate"
						placeholder="Enter purchaseDate"dateOfVisit"  " size="25" id="datepicker"
						readonly="readonly"
						value="<%=DataUtility.getDateString(bean.getPurchaseDate())%>">
						<font color="red"> <%=ServletUtility.getErrorMessage("purchaseDate", request)%></font></td>
				</tr>
				
                 <tr>
				<th style="padding: 1px"></th>
				</tr>

				<tr>
					<th align="left">Category <span style="color: red">*</span> :
					</th>
					<td><%=HTMLUtility.getList2("category", String.valueOf(bean.getCategory()), map)%>

						<font color="red"> <%=ServletUtility.getErrorMessage("category", request)%></font></br>

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
						name="operation" value="<%=ItemInformationCtl.OP_UPDATE%>"> &nbsp;
						&nbsp; <input type="submit" name="operation"
						value="<%=ItemInformationCtl.OP_CANCEL%>"></td>

					<%
						} else {
					%>

					<td colspan="2">&nbsp; &emsp; <input type="submit"
						name="operation" value="<%=ItemInformationCtl.OP_SAVE%>"> &nbsp;
						&nbsp; <input type="submit" name="operation"
						value="<%=ItemInformationCtl.OP_RESET%>"></td>

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