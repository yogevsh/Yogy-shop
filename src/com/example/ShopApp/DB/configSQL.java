package com.example.ShopApp.DB;

public interface configSQL {
	
	 // JDBC driver name and database URL
    static final String JDBC_DRIVER="com.mysql.jdbc.Driver";  
    static final String DB_BASE_URL="jdbc:mysql://localhost/";
    static final String DB_NAME = "shopdb";
    static final String DB_URL = DB_BASE_URL+DB_NAME;

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "12345";
    
    //Items Table
    static final String ITEMS_TABLE = "Items";
    static final String NAME_COL = "name";
    static final String PRICE_COL = "price";

}
