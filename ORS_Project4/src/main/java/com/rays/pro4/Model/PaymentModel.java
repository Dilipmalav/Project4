package com.rays.pro4.Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.rays.pro4.Bean.PaymentBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DatabaseException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.JDBCDataSource;

	public class PaymentModel {
		
		public int nextPK() throws DatabaseException {

			
			String sql = "SELECT MAX(ID) FROM st_bank";
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

		public long add(PaymentBean bean) throws ApplicationException, DuplicateRecordException {
			

			String sql = "INSERT INTO st_bank VALUES(?,?,?)";

			Connection conn = null;
			int pk = 0;



			try {
				conn = JDBCDataSource.getConnection();
				pk = nextPK();

				conn.setAutoCommit(false);
				PreparedStatement pstmt = conn.prepareStatement(sql);

				pstmt.setInt(1, pk);
				pstmt.setString(2, bean.getC_Name());
				pstmt.setString(3, bean.getAccount());

				int a = pstmt.executeUpdate();
				System.out.println(a);
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

		public void delete(PaymentBean bean) throws ApplicationException {
			
			String sql = "DELETE FROM st_bank WHERE ID=?";
			Connection conn = null;
			try {
				conn = JDBCDataSource.getConnection();
				conn.setAutoCommit(false);
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setLong(1, bean.getId());
				int i=pstmt.executeUpdate();
				System.out.println(i+"data deleted");
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

		
		

		public void update(PaymentBean bean) throws ApplicationException, DuplicateRecordException {
			
			String sql = "UPDATE st_bank SET c_name=?,account=? WHERE ID=?";
			Connection conn = null;
	
		
			try {
				conn = JDBCDataSource.getConnection();
				conn.setAutoCommit(false);
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, bean.getC_Name());
				pstmt.setString(2, bean.getAccount());
				pstmt.setLong(3, bean.getId());
				pstmt.executeUpdate();
				int i=pstmt.executeUpdate();
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

		public List search(PaymentBean bean) throws ApplicationException {
			return search(bean, 0, 0);
		}

		public List search(PaymentBean bean, int pageNo, int pageSize) throws ApplicationException {
			
			StringBuffer sql = new StringBuffer("SELECT * FROM st_bank WHERE 1=1");
			if (bean != null) {
				if (bean.getC_Name() != null && bean.getC_Name().length() > 0) {
					sql.append(" AND c_name like '" + bean.getC_Name() + "%'");
				}
			}
				
			if (pageSize > 0) {
				
				pageNo = (pageNo - 1) * pageSize;

				sql.append(" Limit " + pageNo + ", " + pageSize);
				
			}

			
			List list = new ArrayList();
			Connection conn = null;
			try {
				conn = JDBCDataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql.toString());
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					bean = new PaymentBean();
					bean.setId(rs.getLong(1));
					bean.setC_Name(rs.getString(2));
					bean.setAccount(rs.getString(3));
				
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
		public PaymentBean findByPK(long pk) throws ApplicationException {
			
			String sql = "SELECT * FROM st_bank WHERE ID=?";
			PaymentBean bean = null;
			Connection conn = null;
			try {
				conn = JDBCDataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setLong(1, pk);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					bean = new PaymentBean();
					bean.setId(rs.getLong(1));
					bean.setC_Name(rs.getString(2));
					bean.setAccount(rs.getString(3));
					
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
		
	

}
