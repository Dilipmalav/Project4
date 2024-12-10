package com.rays.pro4.Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

//import org.apache.log4j.Logger;

import com.rays.pro4.Bean.VehicleBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DatabaseException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Exception.RecordNotFoundException;
import com.rays.pro4.Util.EmailBuilder;
import com.rays.pro4.Util.EmailMessage;
import com.rays.pro4.Util.EmailUtility;
import com.rays.pro4.Util.JDBCDataSource;

/**
 * JDBC Implementation of VehicleModel.
 * 
 * @author Dilip Malav
 *
 */

public class VehicleModel {
	private static Logger log = Logger.getLogger(VehicleModel.class);

	public int nextPK() throws DatabaseException {

		log.debug("Model nextPK Started");

		String sql = "SELECT MAX(ID) FROM st_vehicle";
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
		log.debug("Model nextPK Started");
		return pk + 1;

	}

	public long add(VehicleBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("Model add Started");

		String sql = "INSERT INTO st_vehicle VALUES(?,?,?,?,?)";

		Connection conn = null;
		int pk = 0;


		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();

			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getNumber());
			pstmt.setDate(3, new java.sql.Date(bean.getPurchaseDate().getTime()));
			pstmt.setInt(4, bean.getInsuranceAmount());
			pstmt.setString(5, bean.getColour());


			int a = pstmt.executeUpdate();

			conn.commit();
			pstmt.close();

		} catch (Exception e) {
			log.error("Database Exception ...", e);
			try {
				e.printStackTrace();
				conn.rollback();

			} catch (Exception e2) {
				e2.printStackTrace();
				// application exception
				throw new ApplicationException("Exception : add rollback exceptionn" + e2.getMessage());
			}
		}

		finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model Add End");
		return pk;

	}

	public void delete(VehicleBean bean) throws ApplicationException {
		log.debug("Model delete start");
		String sql = "DELETE FROM st_vehicle WHERE ID=?";
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			log.error("DataBase Exception", e);
			try {
				conn.rollback();
			} catch (Exception e2) {
				throw new ApplicationException("Exception: Delete rollback Exception" + e2.getMessage());
			}
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model Delete End");
	}

	public VehicleBean findByPK(long pk) throws ApplicationException {
		log.debug("Model findBy PK start");
		String sql = "SELECT * FROM st_vehicle WHERE ID=?";
		VehicleBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				
				bean = new VehicleBean();
				bean.setId(rs.getLong(1));
				bean.setNumber(rs.getString(2));
				bean.setPurchaseDate(rs.getDate(3));
				bean.setInsuranceAmount(rs.getInt(4));
				bean.setColour(rs.getString(5));
				
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("DataBase Exception ", e);
			throw new ApplicationException("Exception : Exception in getting Vehicle by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Method Find By PK end");
		return bean;
	}

	public void update(VehicleBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("Model Update Start");
		String sql = "UPDATE st_vehicle SET NUMBER=?, PURCHASEDATE=?, INSURANCEAMOUNT=?, COLOUR=?  WHERE ID=?";
		Connection conn = null;
		
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bean.getNumber());
			pstmt.setDate(2, new java.sql.Date(bean.getPurchaseDate().getTime()));
			pstmt.setInt(3, bean.getInsuranceAmount());
			pstmt.setString(4, bean.getColour());
			pstmt.setLong(5, bean.getId());
			
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("DataBase Exception", e);
			try {
				conn.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
				throw new ApplicationException("Exception : Update Rollback Exception " + e2.getMessage());
			}
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model Update End ");
	}

	public List search(VehicleBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}

	public List search(VehicleBean bean, int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model Search Start");
		StringBuffer sql = new StringBuffer("SELECT * FROM st_vehicle where 1=1 ");
		if (bean != null) {
		//	System.out.println("pppppppppppppppppppppppppppppppp");
			System.out.println(bean.getInsuranceAmount());
			System.out.println(bean.getNumber());
			System.out.println(bean.getPurchaseDate());
			System.out.println(bean.getId());
			System.out.println(bean.getColour());
			
			if (bean.getNumber() != null && bean.getNumber().length() > 0) {
				sql.append(" AND NUMBER like '" + bean.getNumber() + "%'");
				System.out.println(bean.getNumber());
			}
			
			if (bean.getPurchaseDate() != null && bean.getPurchaseDate().getTime() > 0) {
				Date d = new java.sql.Date(bean.getPurchaseDate().getTime());
				sql.append(" AND PURCHASEDATE like '" + d + "%'");
			}
			
			if (bean.getInsuranceAmount() > 0) {
				sql.append(" AND INSURANCEAMOUNT = " + bean.getInsuranceAmount());
			}
			if (bean.getColour() != null && bean.getColour().length() > 0) {
				sql.append(" AND COLOUR like '" + bean.getColour() + "%'");
			}
		}
			
		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + ", " + pageSize);
			// sql.append(" limit " + pageNo + "," + pageSize);
		}

		System.out.println(sql);
		List list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new VehicleBean();
				bean.setId(rs.getLong(1));
				bean.setNumber(rs.getString(2));
				bean.setPurchaseDate(rs.getDate(3));
				bean.setInsuranceAmount(rs.getInt(4));
				bean.setColour(rs.getString(5));
				
				list.add(bean);
				//System.out.println("yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyysssssssssssss");

			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception", e);
			throw new ApplicationException("Exception: Exception in Sssssearch Vehicle");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model Search end");
		
		return list;
		
	}

	public List list() throws ApplicationException {
		return list(0, 0);
	}

	public List list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model list Started");
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from st_vehicle");

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		System.out.println("preload........" + sql);
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				VehicleBean bean = new VehicleBean();
				bean.setId(rs.getLong(1));
				bean.setNumber(rs.getString(2));
				bean.setPurchaseDate(rs.getDate(3));
				bean.setInsuranceAmount(rs.getInt(4));
				bean.setColour(rs.getString(5));
				
				list.add(bean);

			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception...", e);
			throw new ApplicationException("Exception : Exception in getting list of Vehicles");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model list End");
		return list;
	}

	
		

	

	
}
