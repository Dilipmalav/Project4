package com.rays.pro4.Model;

import java.sql.Connection;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.rays.pro4.Bean.PhysicianBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DatabaseException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Util.JDBCDataSource;

public class PhysicianModel {

	public int nextPK() throws DatabaseException {

		String sql = "SELECT MAX(ID) FROM ST_PHYSICIAN";
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

	public long add(PhysicianBean bean) throws ApplicationException, DuplicateRecordException {

		String sql = "INSERT INTO ST_PHYSICIAN VALUES(?,?,?,?,?)";

		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();

			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getFullName());
			pstmt.setDate(3, new java.sql.Date(bean.getBirthDate().getTime()));
			pstmt.setLong(4, bean.getPhone());
			pstmt.setString(5, bean.getSpecialization());

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

	public void delete(PhysicianBean bean) throws ApplicationException {

		String sql = "DELETE FROM ST_PHYSICIAN WHERE ID=?";
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

	public void update(PhysicianBean bean) throws ApplicationException, DuplicateRecordException {

		String sql = "UPDATE ST_PHYSICIAN SET FULLNAME=?,BIRTHDATE=?,PHONE=?,SPECIALIZATION=? WHERE ID=?";
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, bean.getFullName());
			pstmt.setDate(2, new java.sql.Date(bean.getBirthDate().getTime()));
			pstmt.setLong(3, bean.getPhone());
			pstmt.setString(4, bean.getSpecialization());
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
	 * public List search(PhysicianBean bean) throws ApplicationException { return
	 * search(bean); }
	 */

	public List search(PhysicianBean bean, int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("SELECT *FROM ST_PHYSICIAN WHERE 1=1");
		if (bean != null) {
			if (bean != null && bean.getId() > 0) {

				sql.append(" AND id = " + bean.getId());

			}
			if (bean.getFullName() != null && bean.getFullName().length() > 0) {
				sql.append(" AND FULLNAME like '" + bean.getFullName() + "%'");
			}
			if (bean.getBirthDate()!= null && bean.getBirthDate().getTime() > 0) {
				Date d = new Date(bean.getBirthDate().getDate());
				sql.append(" AND BIRTHDATE like '" + new java.sql.Date(bean.getBirthDate().getTime()) + "%'");
			}
			if (bean.getPhone() > 0) {
				sql.append(" AND PHONE = " + bean.getPhone());
			}

			if (bean.getSpecialization() != null && bean.getSpecialization().length() > 0) {
				sql.append(" AND SPECIALIZATION like '" + bean.getSpecialization() + "%'");
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
				bean = new PhysicianBean();
				bean.setId(rs.getLong(1));
				bean.setFullName(rs.getString(2));
				bean.setBirthDate(rs.getDate(3));
				bean.setPhone(rs.getLong(4));
				bean.setSpecialization(rs.getString(5));

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

	public PhysicianBean findByPK(long pk) throws ApplicationException {

		String sql = "SELECT * FROM ST_PHYSICIAN WHERE ID=?";
		PhysicianBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new PhysicianBean();
				bean.setId(rs.getLong(1));
				bean.setFullName(rs.getString(2));
				bean.setBirthDate(rs.getDate(3));
				bean.setPhone(rs.getLong(4));
				bean.setSpecialization(rs.getString(5));

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
		StringBuffer sql = new StringBuffer("select * from ST_PHYSICIAN");

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
				PhysicianBean bean = new PhysicianBean();
				bean.setId(rs.getLong(1));
				bean.setFullName(rs.getString(2));
				bean.setBirthDate(rs.getDate(3));
				bean.setPhone(rs.getLong(4));
				bean.setSpecialization(rs.getString(5));

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
