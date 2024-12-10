package com.rays.pro4.Model;

import java.sql.Connection;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.rays.pro4.Bean.TradeHistoryBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DatabaseException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Util.JDBCDataSource;

public class TradeHistoryModel{

	public int nextPK() throws DatabaseException {

		String sql = "SELECT MAX(ID) FROM st_tradehistory";
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

	public long add(TradeHistoryBean bean) throws ApplicationException, DuplicateRecordException {

		String sql = "INSERT INTO st_tradehistory VALUES(?,?,?,?,?)";

		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();

			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getUserId());
			pstmt.setDate(3, new java.sql.Date(bean.getStartDate().getTime()));
			pstmt.setDate(4, new java.sql.Date(bean.getEndDate().getTime()));
			pstmt.setString(5, bean.getTrasactionType());

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

	public void delete(TradeHistoryBean bean) throws ApplicationException {

		String sql = "DELETE FROM st_tradehistory WHERE ID=?";
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

	public void update(TradeHistoryBean bean) throws ApplicationException, DuplicateRecordException {

		String sql = "UPDATE st_tradehistory SET USERID=?, STARTDATE=?, ENDDATE=?, TRANSACTIONTYPE=? WHERE ID=?";
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			
			pstmt.setString(1, bean.getUserId());
			pstmt.setDate(2, new java.sql.Date(bean.getStartDate().getTime()));
			pstmt.setDate(3, new java.sql.Date(bean.getEndDate().getTime()));
			pstmt.setString(4, bean.getTrasactionType());
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
	 * public List search(TradeHistoryBean bean) throws ApplicationException { return
	 * search(bean); }
	 */

	public List search(TradeHistoryBean bean, int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("SELECT *FROM st_tradehistory WHERE 1=1");
		if (bean != null) {
			if (bean != null && bean.getId() > 0) {

				sql.append(" AND id = " + bean.getId());

			}
			if (bean.getUserId() != null && bean.getUserId().length() > 0) {
				sql.append(" AND USERID like '" + bean.getUserId() + "%'");
			}
			if (bean.getStartDate() != null && bean.getStartDate().getTime() > 0) {
				Date d = new Date(bean.getStartDate().getDate());
				sql.append(" AND STARTDATE like '" + new java.sql.Date(bean.getStartDate().getTime()) + "%'");
			}
			if (bean.getEndDate() != null && bean.getEndDate().getTime() > 0) {
				Date d = new Date(bean.getEndDate().getDate());
				sql.append(" AND ENDDATE like '" + new java.sql.Date(bean.getEndDate().getTime()) + "%'");
			}
			if (bean.getTrasactionType() != null && bean.getTrasactionType().length() > 0) {
				sql.append(" AND TRANSACTIONTYPE like '" + bean.getTrasactionType() + "%'");
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
				bean = new TradeHistoryBean();
				bean.setId(rs.getLong(1));
				bean.setUserId(rs.getString(2));
				bean.setStartDate(rs.getDate(3));
				bean.setEndDate(rs.getDate(4));
				bean.setTrasactionType(rs.getString(5));

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

	public TradeHistoryBean findByPK(long pk) throws ApplicationException {

		String sql = "SELECT * FROM st_tradehistory WHERE ID=?";
		TradeHistoryBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new TradeHistoryBean();
				
				bean.setId(rs.getLong(1));
				bean.setUserId(rs.getString(2));
				bean.setStartDate(rs.getDate(3));
				bean.setEndDate(rs.getDate(4));
				bean.setTrasactionType(rs.getString(5));

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
		StringBuffer sql = new StringBuffer("select * from st_tradehistory");

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
				TradeHistoryBean bean = new TradeHistoryBean();
				
				bean.setId(rs.getLong(1));
				bean.setUserId(rs.getString(2));
				bean.setStartDate(rs.getDate(3));
				bean.setEndDate(rs.getDate(4));
				bean.setTrasactionType(rs.getString(5));
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
