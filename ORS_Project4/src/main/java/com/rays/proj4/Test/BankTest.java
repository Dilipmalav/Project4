package com.rays.proj4.Test;

import com.rays.pro4.Bean.BankBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Model.BankModel;

public class BankTest {

	public static void main(String[] args) throws Exception {
		//testAdd();
		//testUpdate();
		testDelete();
		

	}

	private static void testDelete() throws Exception {
		BankBean bean=new BankBean();
		bean.setId(5);
		BankModel model=new BankModel();
		//model.delete(bean);
		
		
	}

	private static void testUpdate() throws Exception, Exception {
		BankBean bean=new BankBean();
		bean.setId(4);
		bean.setC_Name("ppppp");
		bean.setAccount("7859674567");
		BankModel model=new BankModel();
		model.update(bean);
		
	}

	private static void testAdd() throws ApplicationException, DuplicateRecordException {
		BankBean bean=new BankBean();
		bean.setId(4);
		bean.setC_Name("rahul");
		bean.setAccount("7859674567");
		BankModel model=new BankModel();
		model.add(bean);
		
	}
	

}
