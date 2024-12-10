package com.rays.pro4.Model;

import java.sql.Connection;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.rays.pro4.Bean.PortfolioManagementBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DatabaseException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Util.JDBCDataSource;

public class PortfolioManagementModel {

	public int nextPK() throws DatabaseException {

		String sql = "SELECT MAX(ID) FROM st_portfoliomanagement";
		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
		} catch (Exception e) {

			throw new DatabaseException("Exception : Exception in getting PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk + 1;

	}

	public long add(PortfolioManagementBean bean) throws ApplicationException, DuplicateRecordException {

		String sql = "INSERT INTO st_portfoliomanagement VALUES(?,?,?,?,?)";

		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();

			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getPortfolioName());
			pstmt.setLong(3, bean.getInitialInvestmentAmount());
			pstmt.setString(4, bean.getRiskToleranceLevel());
			pstmt.setString(5, bean.getInvestmentStartegy());

			int a = pstmt.executeUpdate();
			System.out.println("ho gyua re" + a);
			conn.commit();
			pstmt.close();

		} catch (Exception e) {

			try {
				e.printStackTrace();
				conn.rollback();

			} catch (Exception e2) {
				e2.printStackTrace();

				throw new ApplicationException("Exception : add rollback exceptionn" + e2.getMessage());
			}
		}

		finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;

	}

	public void delete(PortfolioManagementBean bean) throws ApplicationException {

		String sql = "DELETE FROM st_portfoliomanagement WHERE ID=?";
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, bean.getId());
			int i = pstmt.executeUpdate();
			System.out.println(i + "data deleted");
			conn.commit();
			pstmt.close();

		} catch (Exception e) {

			try {
				conn.rollback();
			} catch (Exception e2) {
				throw new ApplicationException("Exception: Delete rollback Exception" + e2.getMessage());
			}
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

	}

	public void update(PortfolioManagementBean bean) throws ApplicationException, DuplicateRecordException {

		String sql = "UPDATE st_portfoliomanagement SET PORTFOLIONAME=?, INITIALINVESTMENTAMOUNT=?, RISKTOLERANCELEVEL=?, INVESTMENTSTRATEGY=? WHERE ID=?";
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			
			pstmt.setString(1, bean.getPortfolioName());
			pstmt.setLong(2, bean.getInitialInvestmentAmount());
			pstmt.setString(3, bean.getRiskToleranceLevel());
			pstmt.setString(4, bean.getInvestmentStartegy());
			pstmt.setLong(5, bean.getId());
			
			pstmt.executeUpdate();
			int i = pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();

			try {
				conn.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
				throw new ApplicationException("Exception : Update Rollback Exception " + e2.getMessage());
			}
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

	}

	/*
	 * public List search(PortfolioManagementBean bean) throws ApplicationException { return
	 * search(bean); }
	 */

	public List search(PortfolioManagementBean bean, int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("SELECT *FROM st_portfoliomanagement WHERE 1=1");
		if (bean != null) {
			
			if (bean.getPortfolioName() != null && bean.getPortfolioName().length() > 0) {
				sql.append(" AND PORTFOLIONAME like '" + bean.getPortfolioName() + "%'");
			}
			if (bean.getInitialInvestmentAmount() > 0) {
				sql.append(" AND INITIALINVESTMENTAMOUNT = " + bean.getInitialInvestmentAmount());
			}
			
			if (bean.getRiskToleranceLevel() != null && bean.getRiskToleranceLevel().length() > 0) {
				sql.append(" AND RISKTOLERANCELEVEL like '" + bean.getRiskToleranceLevel() + "%'");
			}

			/*
			 * if (bean.getImportance() != null && bean.getImportance().length() > 0) {
			 * sql.append(" AND PORTFOLIOMANAGEMENT like '" + bean.getImportance()+ "%'"); }
			 */
			
			if (bean.getInvestmentStartegy() != null && bean.getInvestmentStartegy().length() > 0) {
				sql.append(" AND INVESTMENTSTRATEGY like '" + bean.getInvestmentStartegy()+ "%'");
			}

			if (pageSize > 0) {

				pageNo = (pageNo - 1) * pageSize;

				sql.append(" Limit " + pageNo + ", " + pageSize);

			}
		}
		System.out.println("sql>>>>>>>>>> " + sql.toString());

		List list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new PortfolioManagementBean();
				bean.setId(rs.getLong(1));
				bean.setPortfolioName(rs.getString(2));
				bean.setInitialInvestmentAmount(rs.getLong(3));
				bean.setRiskToleranceLevel(rs.getString(4));
				bean.setInvestmentStartegy(rs.getString(5));


				list.add(bean);

			}
			rs.close();
		} catch (Exception e) {

			throw new ApplicationException("Exception: Exception in Search User");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;

	}

	public PortfolioManagementBean findByPK(long pk) throws ApplicationException {

		String sql = "SELECT * FROM st_portfoliomanagement WHERE ID=?";
		PortfolioManagementBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new PortfolioManagementBean();
				
				bean.setId(rs.getLong(1));
				bean.setPortfolioName(rs.getString(2));
				bean.setInitialInvestmentAmount(rs.getLong(3));
				bean.setRiskToleranceLevel(rs.getString(4));
				bean.setInvestmentStartegy(rs.getString(5));

			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();

			throw new ApplicationException("Exception : Exception in getting Payment by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	public List list() throws ApplicationException {
		return list(0, 0);
	}

	public List list(int pageNo, int pageSize) throws ApplicationException {

		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from st_portfoliomanagement");

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				PortfolioManagementBean bean = new PortfolioManagementBean();
				bean.setId(rs.getLong(1));
				bean.setPortfolioName(rs.getString(2));
				bean.setInitialInvestmentAmount(rs.getLong(3));
				bean.setRiskToleranceLevel(rs.getString(4));
				bean.setInvestmentStartegy(rs.getString(5));

				list.add(bean);

			}
			rs.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting list of users");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}
}
