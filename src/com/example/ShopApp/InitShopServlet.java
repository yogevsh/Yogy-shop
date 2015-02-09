package com.example.ShopApp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.ShopApp.DB.MySQLHelper;
import com.example.ShopApp.DB.configSQL;

@SuppressWarnings("serial")
public class InitShopServlet extends HttpServlet implements configSQL{
 
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		
		//Create Database
		if(!MySQLHelper.createSQLDatabase(DB_NAME)){
			
			resp.getWriter().println("Database creation is failed!");
			return;
		}
			
		
		//Create Table
		String tableSql = "CREATE TABLE " + ITEMS_TABLE + 
                " (" + NAME_COL + " VARCHAR(255), " + 
                PRICE_COL + " FLOAT)";
                 
		if(!MySQLHelper.createSQLTable(tableSql)){
			
			resp.getWriter().println("Table creation is failed!");
			return;
		}
		
		//Insert Items to the table
		List<ItemType> itemsList = new ArrayList<>();
		itemsList.add(new ItemType("Shirt", 30));
		itemsList.add(new ItemType("Socks", 50));
		itemsList.add(new ItemType("Hat", 40));
		itemsList.add(new ItemType("Jeans", 100));
		itemsList.add(new ItemType("Dress", 80));
		itemsList.add(new ItemType("Skirt", 65));
		itemsList.add(new ItemType("Shoes", 150));
		
		if(!MySQLHelper.insertItemsListToTableSQL(itemsList)){
			resp.getWriter().println("Items insertion is failed!");
			return;
		}
		
		
		resp.setContentType("text/plain");
		resp.getWriter().println("Init shop succeeded");
	}
	
}

