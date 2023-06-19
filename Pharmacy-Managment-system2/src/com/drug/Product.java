package com.drug;

import java.sql.Date;

public class Product {
	
	    private int pid;
	    private String pname;
	    private String manufacturer;
	    private Date mfg;
	    private Date exp;
	    private double price;
	    private int quantity;
	    // getters and setters
		
		public void setManufacturer(String manufacturer) {
			this.manufacturer=manufacturer;
			
		}
		public void setMfg(Date mfg) {
			this.mfg=mfg;
			
		}
		
		public void setExp(Date exp) {
			this.exp=exp;
			
		}
		public void setPrice(double price) {
			
			this.price=price;
		}
		public void setQuantity(int quantity) {
			
			this.quantity=quantity;
		}
		public void setPname(String pname) {
			this.pname=pname;
			
		}
		public void setPid(int pid) {
			this.pid=pid;
			
		}
	
		
	
	
	
	
	
	
	
}
