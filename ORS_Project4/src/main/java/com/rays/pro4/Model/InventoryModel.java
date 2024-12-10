package com.rays.pro4.Model;

import java.sql.Connection;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.rays.pro4.Bean.InventoryBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DatabaseException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Util.JDBCDataSource;

public class InventoryModel{

	public int nextPK() throws DatabaseException {

		String sql = "SELECT MAX(ID) FROM st_inventory";
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

	public long add(InventoryBean bean) throws ApplicationException, DuplicateRecordException {

		String sql = "INSERT INTO st_inventory VALUES(?,?,?,?,?)";

		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();

			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getSupplierName());
			pstmt.setDate(3, new java.sql.Date(bean.getLastUpdatedDate().getTime()));
			pstmt.setLong(4, bean.getQuantity());
			pstmt.setString(5, bean.getProduct());

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

	public void delete(InventoryBean bean) throws ApplicationException {

		String sql = "DELETE FROM st_inventory WHERE ID=?";
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

	public void update(InventoryBean bean) throws ApplicationException, DuplicateRecordException {

		String sql = "UPDATE st_inventory SET SUPPLIERNAME=?, LASTUPDATEDDATE=?, QUANTITY=?, PRODUCT=? WHERE ID=?";
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bean.getSupplierName());
			pstmt.setDate(2, new java.sql.Date(bean.getLastUpdatedDate().getTime()));
			pstmt.setLong(3, bean.getQuantity());
			pstmt.setString(4, bean.getProduct());
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
	 * public List search(InventoryBean bean) throws ApplicationException { return
	 * search(bean); }
	 */

	public List search(InventoryBean bean, int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("SELECT *FROM st_inventory WHERE 1=1");
		if (bean != null) {
			
			if (bean.getSupplierName() != null && bean.getSupplierName().length() > 0) {
				sql.append(" AND SUPPLIERNAME like '" + bean.getSupplierName() + "%'");
			}
			
			if (bean.getLastUpdatedDate() != null && bean.getLastUpdatedDate().getTime() > 0) {
				Date d = new Date(bean.getLastUpdatedDate().getDate());
				sql.append(" AND LASTUPDATEDDATE like '" + new java.sql.Date(bean.getLastUpdatedDate().getTime()) + "%'");
			}
			
			if (bean.getQuantity() > 0) {
				sql.append(" AND QUANTITY = " + bean.getQuantity());
			}
			

			if (bean.getProduct() != null && bean.getProduct().length() > 0) {
				sql.append(" AND PRODUCT like '" + bean.getProduct() + "%'");
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
				bean = new InventoryBean();
				bean.setId(rs.getLong(1));
				bean.setSupplierName(rs.getString(2));
				bean.setLastUpdatedDate(rs.getDate(3));
				bean.setQuantity(rs.getLong(4));
				bean.setProduct(rs.getString(5));

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

	public InventoryBean findByPK(long pk) throws ApplicationException {

		String sql = "SELECT * FROM st_inventory WHERE ID=?";
		InventoryBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new InventoryBean();
				bean.setId(rs.getLong(1));
				bean.setSupplierName(rs.getString(2));
				bean.setLastUpdatedDate(rs.getDate(3));
				bean.setQuantity(rs.getLong(4));
				bean.setProduct(rs.getString(5));

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
		StringBuffer sql = new StringBuffer("select * from st_inventory");

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
				InventoryBean bean = new InventoryBean();
				bean.setId(rs.getLong(1));
				bean.setSupplierName(rs.getString(2));
				bean.setLastUpdatedDate(rs.getDate(3));
				bean.setQuantity(rs.getLong(4));
				bean.setProduct(rs.getString(5));
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
