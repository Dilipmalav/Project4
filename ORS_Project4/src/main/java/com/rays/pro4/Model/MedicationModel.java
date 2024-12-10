package com.rays.pro4.Model;

import java.sql.Connection;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.rays.pro4.Bean.MedicationBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DatabaseException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Util.JDBCDataSource;

public class MedicationModel {

	public int nextPK() throws DatabaseException {

		String sql = "SELECT MAX(ID) FROM st_medication";
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

	public long add(MedicationBean bean) throws ApplicationException, DuplicateRecordException {

		String sql = "INSERT INTO st_medication VALUES(?,?,?,?,?)";

		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();

			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getFullName());
			pstmt.setString(3, bean.getIllness());
			pstmt.setDate(4, new java.sql.Date(bean.getPrecriptionDate().getTime()));
			pstmt.setInt(5, bean.getDosage());

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

	public void delete(MedicationBean bean) throws ApplicationException {

		String sql = "DELETE FROM st_medication WHERE ID=?";
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

	public void update(MedicationBean bean) throws ApplicationException, DuplicateRecordException {

		String sql = "UPDATE st_medication SET FULLNAME=?, ILLNESS=?, PRESCRIPTIONDATE =?, DOSAGE=? WHERE ID=?";
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			
			pstmt.setString(1, bean.getFullName());
			pstmt.setString(2, bean.getIllness());
			pstmt.setDate(3, new java.sql.Date(bean.getPrecriptionDate().getTime()));
			pstmt.setInt(4, bean.getDosage());
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
	 * public List search(MedicationBean bean) throws ApplicationException { return
	 * search(bean); }
	 */

	public List search(MedicationBean bean, int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("SELECT *FROM st_medication WHERE 1=1");
		if (bean != null) {
			/*
			 * if (bean != null && bean.getId() > 0) {
			 * 
			 * sql.append(" AND id = " + bean.getId());
			 * 
			 * }
			 */
			if (bean.getFullName() != null && bean.getFullName().length() > 0) {
				sql.append(" AND FULLNAME like '" + bean.getFullName() + "%'");
			}
			if (bean.getIllness() != null && bean.getIllness().length() > 0) {
				sql.append(" AND ILLNESS like '" + bean.getIllness() + "%'");
			}
			if (bean.getDosage() > 0) {
				sql.append(" AND DOSAGE  = " + bean.getDosage());
			}

			if (bean.getPrecriptionDate() != null && bean.getPrecriptionDate().getTime() > 0) {
				Date d = new Date(bean.getPrecriptionDate().getDate());
				sql.append(" AND PRESCRIPTIONDATE like '" + new java.sql.Date(bean.getPrecriptionDate().getTime()) + "%'");
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
				bean = new MedicationBean();
				bean.setId(rs.getLong(1));
				bean.setFullName(rs.getString(2));
				bean.setIllness(rs.getString(3));
				bean.setPrecriptionDate(rs.getDate(4));
				bean.setDosage(rs.getInt(5));


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

	public MedicationBean findByPK(long pk) throws ApplicationException {

		String sql = "SELECT * FROM st_medication WHERE ID=?";
		MedicationBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new MedicationBean();
				
				bean.setId(rs.getLong(1));
				bean.setFullName(rs.getString(2));
				bean.setIllness(rs.getString(3));
				bean.setPrecriptionDate(rs.getDate(4));
				bean.setDosage(rs.getInt(5));

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
		StringBuffer sql = new StringBuffer("select * from st_medication");

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
				MedicationBean bean = new MedicationBean();
				bean.setId(rs.getLong(1));
				bean.setFullName(rs.getString(2));
				bean.setIllness(rs.getString(3));
				bean.setPrecriptionDate(rs.getDate(4));
				bean.setDosage(rs.getInt(5));

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
