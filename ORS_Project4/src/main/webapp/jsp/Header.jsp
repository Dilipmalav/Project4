<!DOCTYPE html> 
<%@page import="com.rays.pro4.Bean.UserBean"%>
<%@page import="com.rays.pro4.Bean.RoleBean"%>
<%@ page import ="com.rays.pro4.controller.LoginCtl" %>
<%@page import="com.rays.pro4.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>

				
<body >
	<%
    UserBean userBean = (UserBean)session.getAttribute("user");
    boolean userLoggedIn = userBean != null;
    String welcomeMsg = "Hi, ";
    if (userLoggedIn) {
        String role = (String)session.getAttribute("role");
        welcomeMsg += userBean.getFirstName() + " (" + role + ")";
    } else {
        welcomeMsg += "Guest";
    }
	%>

<table >
    <tr ><th></th>
       <td width="90%" >
      
        <a href="<%=ORSView.WELCOME_CTL%>">Welcome</b></a> |
      
            	<%
            		if (userLoggedIn) {
      		 	 %> 
       <a href=" <%=ORSView.LOGIN_CTL%>?operation=<%=LoginCtl.OP_LOG_OUT%>">Logout</b></a> 

            <%
                } else {
            %> 
            <a href="<%=ORSView.LOGIN_CTL%>">Login</b></a> 
            <%
  			   } 
			 %>
 		</td>
        <td rowspan="2" >
            <h1 align="right" >
                <img src= "<%=ORSView.APP_CONTEXT %>/img/customLogo.jpg"  width="175" height="50">
            </h1>
        </td>
    </tr>

    <tr><th></th>
      <td>
        <h3><%=welcomeMsg%></h3>
      </td>
    </tr>
   
    <%
        if (userLoggedIn) {
    %>

    <tr><th></th>
        <td colspan="2" >     
        <font style="font-size: 18px">
       	 
        <a href="<%=ORSView.MY_PROFILE_CTL%>">MyProfile</b></a> |       
         <a href="<%=ORSView.CHANGE_PASSWORD_CTL%>">Change Password</b></a> |
       	 <a href="<%=ORSView.GET_MARKSHEET_CTL%>">Get Marksheet</b></a> |              
       	 <a href="<%=ORSView.MARKSHEET_MERIT_LIST_CTL%>">Marksheet MeritList</b></a> |
       	<%--  <a href="<%=ORSView.PAYMENT_CTL%>">Add Payment</a> |
       	  <a href="<%=ORSView.PAYMENT_LIST_CTL%>"> Payment List</a> --%> |
       	 
       	 
        
        <%
            if (userBean.getRoleId() == RoleBean.ADMIN) {
        %> 
       
        <a href="<%=ORSView.MARKSHEET_CTL%>">Add Marksheet</b></a> |     
        <a href="<%=ORSView.MARKSHEET_LIST_CTL%>">Marksheet List</b></a> |
        <a href="<%=ORSView.USER_CTL%>">Add User</b></a> | 
        <a href="<%=ORSView.USER_LIST_CTL%>">User List</b></a> |         
        <a href="<%=ORSView.COLLEGE_CTL%>">Add College</b></a> |        
        <a href="<%=ORSView.COLLEGE_LIST_CTL%>">College List</b></a> |
        <a href="<%=ORSView.ROLE_CTL%>">Add Role</b></a> |        
        <a href="<%=ORSView.ROLE_LIST_CTL%>">Role List</b></a> |
        <br>
        <a href="<%=ORSView.STUDENT_CTL%>">Add Student</b></a> |
        <a href="<%=ORSView.STUDENT_LIST_CTL%>">Student List</b></a> |
        <a href="<%=ORSView.COURSE_CTL %>" >Add Course</b></a> |       
        <a href="<%=ORSView.COURSE_LIST_CTL %>">Course List</b></a> |       
        <a href="<%=ORSView.SUBJECT_CTL %>" >Add Subject</b></a> |       
        <a href="<%=ORSView.SUBJECT_LIST_CTL %>" >Subject List</b></a> |          
        <a href="<%=ORSView.FACULTY_CTL %>" >Add Faculty</b></a> |       
        <a href="<%=ORSView.FACULTY_LIST_CTL %>">Faculty List</b></a> |
        <a href="<%=ORSView.TIMETABLE_CTL %>" >Add TimeTable</b></a> |       
        <a href="<%=ORSView.TIMETABLE_LIST_CTL %>">TimeTable List</b></a> |
        <a href="<%=ORSView.VEHICLE_CTL%>">Add Vehicle</b></a> |
        <a href="<%=ORSView.VEHICLE_LIST_CTL%>">Vehicle List</b></a> |
        <br>
        <a href="<%=ORSView.ORDER_CTL%>">Add Order</b></a> |
        <a href="<%=ORSView.ORDER_LIST_CTL%>">Order List</b></a> |
        
        <a href="<%=ORSView.CUSTOMER_CTL%>">Add Customer</b></a> |
        <a href="<%=ORSView.CUSTOMER_LIST_CTL%>">Customer List</b></a> |
        
         <a href="<%=ORSView.PHYSICIAN_CTL%>">Add Physician</b></a> |
        <a href="<%=ORSView.PHYSICIAN_LIST_CTL%>">Physician List</b></a> |
        
         <a href="<%=ORSView.STAFF_CTL%>">Add Staff</b></a> |
        <a href="<%=ORSView.STAFF_LIST_CTL%>">Staff List</b></a> |
        
         <a href="<%=ORSView.CLIENT_CTL%>">Add Client</b></a> |
        <a href="<%=ORSView.CLIENT_LIST_CTL%>">Client List</b></a> |
        
          <a href="<%=ORSView.COMPENSATION_CTL%>">Add Compensation</b></a> |
        <a href="<%=ORSView.COMPENSATION_LIST_CTL%>">Compensation List</b></a> |
           
           <br>
          <a href="<%=ORSView.MEDICATION_CTL%>">Add Medication</b></a> |
        <a href="<%=ORSView.MEDICATION_LIST_CTL%>">Medication List</b></a> |
        
         <a href="<%=ORSView.TRADEHISTORY_CTL%>">Add TradeHistory</b></a> |
        <a href="<%=ORSView.TRADEHISTORY_LIST_CTL%>">TradeHistory List</b></a> |
        
           <a href="<%=ORSView.STOCKPURCHASE_CTL%>">Add StockPurchase</b></a> |
        <a href="<%=ORSView.STOCKPURCHASE_LIST_CTL%>">StockPurchase List</b></a> | 
       
         <a href="<%=ORSView.PORTFOLIOMANAGEMENT_CTL%>">Add PortfolioManagement</b></a> |
        <a href="<%=ORSView.PORTFOLIOMANAGEMENT_LIST_CTL%>">PortfolioManagement List</b></a> |
        
        <br>
         <a href="<%=ORSView.PATIENT_CTL%>">Add Patient</b></a> |
        <a href="<%=ORSView.PATIENT_LIST_CTL%>">Patient List</b></a> |
        
         <a href="<%=ORSView.INVENTORY_CTL%>">Add Inventory</b></a> |
        <a href="<%=ORSView.INVENTORY_LIST_CTL%>">Add Inventory List</b></a> |
        
         <a href="<%=ORSView.ITEMINFORMATION_CTL %>" >Add ItemInformation</b></a> |       
        <a href="<%=ORSView.ITEMINFORMATION_LIST_CTL %>">ItemInformation List</b></a> |  
        
        <a target="blank" href="<%=ORSView.JAVA_DOC_VIEW%>">Java Doc</b></a> |
       <%
     		}
 		%>
 		 <%
            if (userBean.getRoleId() == RoleBean.STUDENT) {
        %> 
       
        <a href="<%=ORSView.COLLEGE_LIST_CTL%>">College List</b></a> |
        <a href="<%=ORSView.STUDENT_LIST_CTL%>">Student List</b></a> |
        <a href="<%=ORSView.COURSE_LIST_CTL %>">Course List</b></a> |       
        <a href="<%=ORSView.SUBJECT_LIST_CTL %>">Subject List</b></a> |       
        <a href="<%=ORSView.FACULTY_LIST_CTL %>">Faculty List</b></a> |
        <a href="<%=ORSView.TIMETABLE_LIST_CTL %>">TimeTable List</b></a> |
       <%
     		}
 		%>
		
 		<%
            if (userBean.getRoleId() == RoleBean.KIOSK) {
        %> 
        <a href="<%=ORSView.COLLEGE_LIST_CTL%>">College List</b></a> |
        <a href="<%=ORSView.TIMETABLE_LIST_CTL %>">TimeTable List</b></a> |
        <a href="<%=ORSView.COURSE_LIST_CTL %>">Course List</b></a> |       
       <%
     		}
 		%>
 		 <%
            if (userBean.getRoleId() == RoleBean.FACULTY) {
           // System.out.println("======>><><>"+userBean.getRoleId());	
        %> 
       
        <a href="<%=ORSView.MARKSHEET_CTL%>">Add Marksheet</b></a> |       
        <a href="<%=ORSView.MARKSHEET_LIST_CTL%>">Marksheet List</b></a> |
        <a href="<%=ORSView.COLLEGE_LIST_CTL%>">College List</b></a> |
        <a href="<%=ORSView.STUDENT_CTL%>">Add Student</b></a> |
        <a href="<%=ORSView.STUDENT_LIST_CTL%>">Student List</b></a> |
        <a href="<%=ORSView.COURSE_LIST_CTL %>">Course List</b></a> |       
        <a href="<%=ORSView.SUBJECT_CTL %>" >Add Subject</b></a> |   
        <br>    
        <a href="<%=ORSView.SUBJECT_LIST_CTL%>">Subject List</b></a> |       
        <a href="<%=ORSView.TIMETABLE_CTL %>" >Add TimeTable</b></a> |       
        <a href="<%=ORSView.TIMETABLE_LIST_CTL %>">TimeTable List</b></a> |
       <%
     		}
 		%>
 		 <%
            if (userBean.getRoleId() == RoleBean.COLLEGE) {
       //    System.out.println("======>><><>"+userBean.getRoleId());	
          %> 
       
        <a href="<%=ORSView.MARKSHEET_CTL%>">Add Marksheet</b></a> |       
        <a href="<%=ORSView.MARKSHEET_LIST_CTL%>">Marksheet List</b></a> |
        <a href="<%=ORSView.STUDENT_CTL%>">Add Student</b></a> |
        <a href="<%=ORSView.STUDENT_LIST_CTL%>">Student List</b></a> |
        <a href="<%=ORSView.FACULTY_LIST_CTL %>">Faculty List</b></a> |
        <a href="<%=ORSView.TIMETABLE_LIST_CTL %>">TimeTable List</b></a> |
        <a href="<%=ORSView.COURSE_LIST_CTL %>">Course List</b></a> |       
       <%
     		}
 		%>
 		
 		</font>
 		</td>
    </tr>
    	<%
        	}
   		 %>
</table>
<hr>
</body>	
</html>