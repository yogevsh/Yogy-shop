package com.example.ShopApp.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.ShopApp.ItemType;


public class MySQLHelper implements configSQL{
	
	public static boolean createSQLDatabase(String DBName){
		return executeSQLUpdate(DB_BASE_URL, "CREATE DATABASE "+DBName);
	}
	
	public static boolean createSQLTable(String tableSql){
		return executeSQLUpdate(DB_URL, tableSql);
	}
	
	/**
	 * 
	 * @param DBUrl
	 * @param sqlQuery
	 * @return true if succeeded, otherwise false
	 */
	public static boolean executeSQLUpdate(String DBUrl, String sqlQuery){
		Connection conn = null;
		Statement stmt = null;
		try{
	      //Register JDBC driver
	      Class.forName(JDBC_DRIVER);

	      //Open a connection
	      conn = DriverManager.getConnection(DBUrl, USER, PASS);

	      // Execute SQL query
	      stmt = conn.createStatement();
	      
	      stmt.executeUpdate(sqlQuery);
	      return true;
	   }catch(SQLException se){
	      //Handle errors for JDBC
	      se.printStackTrace();
	   }catch(Exception e){
	      //Handle errors for Class.forName
	      e.printStackTrace();
	   }finally{
	      //finally block used to close resources
	      try{
	         if(stmt!=null)
	            stmt.close();
	      }catch(SQLException se2){
	      }// nothing we can do
	      try{
	         if(conn!=null)
	            conn.close();
	      }catch(SQLException se){
	         se.printStackTrace();
	      }//end finally try
	   }//end try
		
		return false;
	}
	
	/**
	 * Insert items to the items table
	 * @param insertSql
	 * @return
	 */
	public static boolean insertItemsListToTableSQL(List<ItemType> itemsList){
		
		Connection conn = null;
		Statement stmt = null;
		try{
	      //Register JDBC driver
	      Class.forName(JDBC_DRIVER);
	
	      //Open a connection
	      conn = DriverManager.getConnection(DB_URL, USER, PASS);
	
	      // Execute SQL query
	      stmt = conn.createStatement();
	      
	      for( ItemType item : itemsList){
	    	 String insertSql = "INSERT INTO " + ITEMS_TABLE + 
	                   " VALUES('" + item.getName() + "', " + item.getPrice() + ")";
	    	  stmt.executeUpdate(insertSql);
	      }
	      
	      
	      return true;
	   }catch(SQLException se){
	      //Handle errors for JDBC
	      se.printStackTrace();
	   }catch(Exception e){
	      //Handle errors for Class.forName
	      e.printStackTrace();
	   }finally{
	      //finally block used to close resources
	      try{
	         if(stmt!=null)
	            stmt.close();
	      }catch(SQLException se2){
	      }// nothing we can do
	      try{
	         if(conn!=null)
	            conn.close();
	      }catch(SQLException se){
	         se.printStackTrace();
	      }//end finally try
	   }//end try
		
		return false;
	}

	/**
	 * Execute Select query and return the result
	 * @param sqlQuery
	 * @return list with items which return from the select query
	 */
	public static List<ItemType> selectItemsFromSqlTable(String sqlQuery){
		
		List<ItemType> itemsList = new ArrayList<>();
		Connection conn = null;
		Statement stmt = null;
		
		try{
	         // Register JDBC driver
	         Class.forName(JDBC_DRIVER);

	         // Open a connection
	         conn = DriverManager.getConnection(DB_URL,USER,PASS);

	         // Execute SQL query
	         stmt = conn.createStatement();
	         
	         ResultSet rs = stmt.executeQuery(sqlQuery);
	         
	         // Extract data from result set
	         while(rs.next()){
	        	 
	            //add item to the items list
		        itemsList.add(new ItemType(rs.getString(NAME_COL), rs.getFloat(PRICE_COL)));
	         }
	           
	         // Clean-up environment
	         rs.close();
	         stmt.close();
	         conn.close();
	         
	         return itemsList;
	         
	      }catch(SQLException se){
	         //Handle errors for JDBC
	         se.printStackTrace();
	      }catch(Exception e){
	         //Handle errors for Class.forName
	         e.printStackTrace();
	      }finally{
	         //finally block used to close resources
	         try{
	            if(stmt!=null)
	               stmt.close();
	         }catch(SQLException se2){
	         }// nothing we can do
	         try{
	            if(conn!=null)
	            conn.close();
	         }catch(SQLException se){
	            se.printStackTrace();
	         }//end finally try
	      } //end try
		
		return null;
	}
}
