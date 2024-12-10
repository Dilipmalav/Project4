<%@page import="java.util.Map"%>
<%@page import="com.rays.pro4.Bean.StaffBean"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.rays.pro4.Model.StaffModel"%>
<%@page import="com.rays.pro4.Model.StaffModel"%>
<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="com.rays.pro4.Util.DataUtility"%>
<%@page import="com.rays.pro4.Util.ServletUtility"%>
<%@page import="com.rays.pro4.controller.StaffListCtl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
<title>Staff List</title>
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script src="<%=ORSView.APP_CONTEXT%>/js/Checkbox11.js"></script>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
	$(function() {
		$("#udatee").datepicker({
			changeMonth : true,
			changeYear : true,
			yearRange : '1980:2002'
		});
	});
</script>
</head>
<body>
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.StaffBean"
		scope="request" />
	<%@include file="Header.jsp"%>

	<form action="<%=ORSView.STAFF_LIST_CTL%>" method="post">
		<center>
			<div align="center">
				<h1>Staff List</h1>
				<h3>
					<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
					<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
				</h3>
			</div>
			
			<%
				Map map = (Map) request.getAttribute("prolist");
			%>

			<%
				int next = DataUtility.getInt(request.getAttribute("nextlist").toString());
			
				List ulist = (List) request.getAttribute("vlist");
				
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				
				int index = ((pageNo - 1) * pageSize) + 1;
				List<StaffBean> list = ServletUtility.getList(request);
				Iterator<StaffBean> it = list.iterator();

				if (list.size() != 0) {
			%>
			<table width="100%" align="center">
				<tr>
					<td align="center"><label>Full Name:</label> <input type="text"
						name="fullName" placeholder="Enter Full Name"
						value="<%=ServletUtility.getParameter("fullName", request)%>">
						
						<label>Joining Date:</label> <input type="text" id="udatee"
						name="joiningDate" placeholder="Enter Joining Date"
						value="<%=ServletUtility.getParameter("joiningDate", request)%>">
						
						<label>Previous Employer:</label> <input type="text"
						name="previousEmployer" placeholder="Enter "
						value="<%=ServletUtility.getParameter("previousEmployer", request)%>">
						
						<label>Division:</label> <%
 	

 		String colourList = HTMLUtility.getList2("division", String.valueOf(bean.getDivision()), map);
 %> <%=colourList%> <input type="submit" name="operation"
						value="<%=StaffListCtl.OP_SEARCH%>"> <input type="submit"
						name="operation" value="<%=StaffListCtl.OP_RESET%>"></td>
				</tr>
			</table>
			<br>

			<table border="1" width="100%" align="center" cellpadding="6"
				cellspacing="0">
				<tr style="background: orange">
					<th><input type="checkbox" id="select_all" name="select">
						Select All</th>
					<th>S.No.</th>
					<th>Full Name</th>
					<th>Joining Date</th>
					<th>Previous Employer </th>
					<th>Division</th>
					<th>Edit</th>
				</tr>

				<%
					while (it.hasNext()) {
							bean = it.next();
							/* StaffModel m = new 
							RoleBean rolebean = new RoleBean();
							rolebean = model.findByPK(bean.getRoleId()); */
				%>
				<tr align="center">
					<td><input type="checkbox" class="checkbox" name="ids"
						value="<%=bean.getId()%>"></td>
					<td><%=index++%></td>
					<td><%=bean.getFullName()%></td>
					<td><%=bean.getJoiningDate()%></td>
					<td><%=bean.getPreviousEmployer()%></td>
					<td><%=map.get(Integer.parseInt(bean.getDivision()))%></td>
					<td><a href="StaffCtl?id=<%=bean.getId()%>">Edit</a></td>
				</tr>
				<%
					}
				%>
			</table>

			<table width="100%">
				<tr>
					<th></th>
					<td><input type="submit" name="operation"
						value="<%=StaffListCtl.OP_PREVIOUS%>"
						<%=(pageNo == 1) ? "disabled" : ""%>></td>
					<td><input type="submit" name="operation"
						value="<%=StaffListCtl.OP_DELETE%>"></td>
					<td><input type="submit" name="operation"
						value="<%=StaffListCtl.OP_NEW%>"></td>
					<td align="right"><input type="submit" name="operation"
						value="<%=StaffListCtl.OP_NEXT%>"
						<%=(list.size() < pageSize || next == 0) ? "disabled" : ""%>>
					</td>
				</tr>
			</table>
			<%
				} else {
			%>
			<table width="100%">
				<tr>
					<td align="center"><input type="submit" name="operation"
						value="<%=StaffListCtl.OP_BACK%>"></td>
				</tr>
			</table>
			<%
				}
			%>

			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">
		</center>
	</form>
	<%@include file="Footer.jsp"%>
</body>
</html>
