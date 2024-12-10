package com.rays.pro4.controller;

/**
 * @author Dilip Malav
 *
 */
public interface ORSView {

	public String APP_CONTEXT = "/ORS_Project4";
	public String LAYOUT_VIEW = "/BaseLayout.jsp";
	public static String PAGE_FOLDER = "/jsp";
	public String JAVA_DOC_VIEW = APP_CONTEXT + "/doc/index.html";

	public String PAYMENT_LIST_VIEW = PAGE_FOLDER + "/PaymentListView.jsp";
	public String PAYMENT_VIEW = PAGE_FOLDER + "/PaymentView.jsp";

	public String VEHICLE_LIST_VIEW = PAGE_FOLDER + "/VehicleListView.jsp";
	public String VEHICLE_VIEW = PAGE_FOLDER + "/VehicleView.jsp";

	public String ORDER_LIST_VIEW = PAGE_FOLDER + "/OrderListView.jsp";
	public String ORDER_VIEW = PAGE_FOLDER + "/OrderView.jsp";

	public String CUSTOMER_LIST_VIEW = PAGE_FOLDER + "/CustomerListView.jsp";
	public String CUSTOMER_VIEW = PAGE_FOLDER + "/CustomerView.jsp";

	public String CLIENT_LIST_VIEW = PAGE_FOLDER + "/ClientListView.jsp";
	public String CLIENT_VIEW = PAGE_FOLDER + "/ClientView.jsp";

	public String PHYSICIAN_LIST_VIEW = PAGE_FOLDER + "/PhysicianListView.jsp";
	public String PHYSICIAN_VIEW = PAGE_FOLDER + "/PhysicianView.jsp";

	public String STAFF_LIST_VIEW = PAGE_FOLDER + "/StaffListView.jsp";
	public String STAFF_VIEW = PAGE_FOLDER + "/StaffView.jsp";
	
	public String COMPENSATION_LIST_VIEW = PAGE_FOLDER + "/CompensationListView.jsp";
	public String COMPENSATION_VIEW = PAGE_FOLDER + "/CompensationView.jsp";
	
	public String MEDICATION_LIST_VIEW = PAGE_FOLDER + "/MedicationListView.jsp";
	public String MEDICATION_VIEW = PAGE_FOLDER + "/MedicationView.jsp";
	
	public String TRADEHISTORY_LIST_VIEW = PAGE_FOLDER + "/TradeHistoryListView.jsp";
	public String TRADEHISTORY_VIEW = PAGE_FOLDER + "/TradeHistoryView.jsp";
	
	public String STOCKPURCHASE_LIST_VIEW = PAGE_FOLDER + "/StockPurchaseListView.jsp";
	public String STOCKPURCHASE_VIEW = PAGE_FOLDER + "/StockPurchaseView.jsp";
	
	public String PORTFOLIOMANAGEMENT_LIST_VIEW = PAGE_FOLDER + "/PortfolioManagementListView.jsp";
	public String PORTFOLIOMANAGEMENT_VIEW = PAGE_FOLDER + "/PortfolioManagementView.jsp";
  
	public String PATIENT_LIST_VIEW = PAGE_FOLDER + "/PatientListView.jsp";
	public String PATIENT_VIEW = PAGE_FOLDER + "/PatientView.jsp";
	
	public String INVENTORY_LIST_VIEW = PAGE_FOLDER + "/InventoryListView.jsp";
	public String INVENTORY_VIEW = PAGE_FOLDER + "/InventoryView.jsp";
	
	public String ITEMINFORMATION_VIEW = PAGE_FOLDER + "/ItemInformationView.jsp";
    public String ITEMINFORMATION_LIST_VIEW = PAGE_FOLDER + "/ItemInformationListView.jsp";

	public String MARKSHEET_VIEW = PAGE_FOLDER + "/MarksheetView.jsp";
	public String MARKSHEET_LIST_VIEW = PAGE_FOLDER + "/MarksheetListView.jsp";
	public String GET_MARKSHEET_VIEW = PAGE_FOLDER + "/GetMarksheetView.jsp";
	public String USER_VIEW = PAGE_FOLDER + "/UserView.jsp";
	public String USER_LIST_VIEW = PAGE_FOLDER + "/UserListView.jsp";
	public String COLLEGE_VIEW = PAGE_FOLDER + "/CollegeView.jsp";
	public String COLLEGE_LIST_VIEW = PAGE_FOLDER + "/CollegeListView.jsp";
	public String STUDENT_VIEW = PAGE_FOLDER + "/StudentView.jsp";
	public String STUDENT_LIST_VIEW = PAGE_FOLDER + "/StudentListView.jsp";
	public String ROLE_VIEW = PAGE_FOLDER + "/RoleView.jsp";
	public String ROLE_LIST_VIEW = PAGE_FOLDER + "/RoleListView.jsp";
	public String USER_REGISTRATION_VIEW = PAGE_FOLDER + "/UserRegistrationView.jsp";
	public String LOGIN_VIEW = PAGE_FOLDER + "/LoginView.jsp";
	public static String WELCOME_VIEW = PAGE_FOLDER + "/Welcome.jsp";
	public String CHANGE_PASSWORD_VIEW = PAGE_FOLDER + "/ChangePasswordView.jsp";
	public String MY_PROFILE_VIEW = PAGE_FOLDER + "/MyProfileView.jsp";
	public String FORGET_PASSWORD_VIEW = PAGE_FOLDER + "/ForgetPasswordView.jsp";
	public String MARKSHEET_MERIT_LIST_VIEW = PAGE_FOLDER + "/MarksheetMeritListView.jsp";
	public String COURSE_VIEW = PAGE_FOLDER + "/CourseView.jsp";
	public String COURSE_LIST_VIEW = PAGE_FOLDER + "/CourseListView.jsp";
	public String FACULTY_VIEW = PAGE_FOLDER + "/FacultyView.jsp";
	public String FACULTY_LIST_VIEW = PAGE_FOLDER + "/FacultyListView.jsp";
	public String SUBJECT_VIEW = PAGE_FOLDER + "/SubjectView.jsp";
	public String SUBJECT_LIST_VIEW = PAGE_FOLDER + "/SubjectListView.jsp";
	public String TIMETABLE_VIEW = PAGE_FOLDER + "/TimeTableView.jsp";
	public String TIMETABLE_LIST_VIEW = PAGE_FOLDER + "/TimeTableListView.jsp";
	public String ERROR_VIEW = PAGE_FOLDER + "/ErrorView.jsp";
	// public String ERROR_VIEW5 = PAGE_FOLDER + "/ErrorView5.jsp";

	public String PAYMENT_LIST_CTL = APP_CONTEXT + "/ctl/PaymentListCtl";
	public String PAYMENT_CTL = APP_CONTEXT + "/ctl/PaymentCtl";

	public String VEHICLE_LIST_CTL = APP_CONTEXT + "/ctl/VehicleListCtl";
	public String VEHICLE_CTL = APP_CONTEXT + "/ctl/VehicleCtl";

	public String ORDER_LIST_CTL = APP_CONTEXT + "/ctl/OrderListCtl";
	public String ORDER_CTL = APP_CONTEXT + "/ctl/OrderCtl";

	public String CUSTOMER_LIST_CTL = APP_CONTEXT + "/ctl/CustomerListCtl";
	public String CUSTOMER_CTL = APP_CONTEXT + "/ctl/CustomerCtl";

	public String STAFF_LIST_CTL = APP_CONTEXT + "/ctl/StaffListCtl";
	public String STAFF_CTL = APP_CONTEXT + "/ctl/StaffCtl";

	public String CLIENT_CTL = APP_CONTEXT + "/ctl/ClientCtl";
	public String CLIENT_LIST_CTL = APP_CONTEXT + "/ctl/ClientListCtl";
	
	public String PHYSICIAN_CTL = APP_CONTEXT + "/ctl/PhysicianCtl";
	public String PHYSICIAN_LIST_CTL = APP_CONTEXT + "/ctl/PhysicianListCtl";
	
	public String COMPENSATION_CTL = APP_CONTEXT + "/ctl/CompensationCtl";
	public String COMPENSATION_LIST_CTL = APP_CONTEXT + "/ctl/CompensationListCtl";

	public String MEDICATION_CTL = APP_CONTEXT + "/ctl/MedicationCtl";
	public String MEDICATION_LIST_CTL = APP_CONTEXT + "/ctl/MedicationListCtl";
	
	public String TRADEHISTORY_CTL = APP_CONTEXT + "/ctl/TradeHistoryCtl";
	public String TRADEHISTORY_LIST_CTL = APP_CONTEXT + "/ctl/TradeHistoryListCtl";
	
	public String STOCKPURCHASE_CTL = APP_CONTEXT + "/ctl/StockPurchaseCtl";
	public String STOCKPURCHASE_LIST_CTL = APP_CONTEXT + "/ctl/StockPurchaseListCtl";
	
	public String PORTFOLIOMANAGEMENT_CTL = APP_CONTEXT + "/ctl/PortfolioManagementCtl";
	public String PORTFOLIOMANAGEMENT_LIST_CTL = APP_CONTEXT + "/ctl/PortfolioManagementListCtl";

	public String PATIENT_LIST_CTL = APP_CONTEXT + "/ctl/PatientListCtl";
	public String PATIENT_CTL = APP_CONTEXT + "/ctl/PatientCtl"; 
	
	public String INVENTORY_LIST_CTL = APP_CONTEXT + "/ctl/InventoryListCtl";
	public String INVENTORY_CTL = APP_CONTEXT + "/ctl/InventoryCtl"; 
	
	 public String ITEMINFORMATION_CTL = APP_CONTEXT + "/ctl/ItemInformationCtl";
	 public String ITEMINFORMATION_LIST_CTL = APP_CONTEXT + "/ctl/ItemInformationListCtl";
	
	public String MARKSHEET_CTL = APP_CONTEXT + "/ctl/MarksheetCtl";
	public String MARKSHEET_LIST_CTL = APP_CONTEXT + "/ctl/MarksheetListCtl";
	public String USER_CTL = APP_CONTEXT + "/ctl/UserCtl";
	public String USER_LIST_CTL = APP_CONTEXT + "/ctl/UserListCtl";
	public String COLLEGE_CTL = APP_CONTEXT + "/ctl/CollegeCtl";
	public String COLLEGE_LIST_CTL = APP_CONTEXT + "/ctl/CollegeListCtl";
	public String STUDENT_CTL = APP_CONTEXT + "/ctl/StudentCtl";
	public String STUDENT_LIST_CTL = APP_CONTEXT + "/ctl/StudentListCtl";
	public String ROLE_CTL = APP_CONTEXT + "/ctl/RoleCtl";
	public String ROLE_LIST_CTL = APP_CONTEXT + "/ctl/RoleListCtl";
	public String USER_REGISTRATION_CTL = APP_CONTEXT + "/UserRegistrationCtl";
	public String LOGIN_CTL = APP_CONTEXT + "/LoginCtl";
	public String WELCOME_CTL = APP_CONTEXT + "/WelcomeCtl";
	public String LOGOUT_CTL = APP_CONTEXT + "/LoginCtl";
	public String GET_MARKSHEET_CTL = APP_CONTEXT + "/ctl/GetMarksheetCtl";
	public String CHANGE_PASSWORD_CTL = APP_CONTEXT + "/ctl/ChangePasswordCtl";
	public String MY_PROFILE_CTL = APP_CONTEXT + "/ctl/MyProfileCtl";
	public String FORGET_PASSWORD_CTL = APP_CONTEXT + "/ForgetPasswordCtl";
	public String MARKSHEET_MERIT_LIST_CTL = APP_CONTEXT + "/ctl/MarksheetMeritListCtl";
	public String COURSE_CTL = APP_CONTEXT + "/ctl/CourseCtl";
	public String COURSE_LIST_CTL = APP_CONTEXT + "/ctl/CourseListCtl";
	public String FACULTY_CTL = APP_CONTEXT + "/ctl/FacultyCtl";
	public String FACULTY_LIST_CTL = APP_CONTEXT + "/ctl/FacultyListCtl";
	public String SUBJECT_CTL = APP_CONTEXT + "/ctl/SubjectCtl";
	public String SUBJECT_LIST_CTL = APP_CONTEXT + "/ctl/SubjectListCtl";
	public String TIMETABLE_CTL = APP_CONTEXT + "/ctl/TimeTableCtl";
	public String TIMETABLE_LIST_CTL = APP_CONTEXT + "/ctl/TimeTableListCtl";
	public String ERROR_CTL = APP_CONTEXT + "/ErrorCtl";

	
}
