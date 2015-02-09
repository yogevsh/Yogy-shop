package com.example.ShopApp;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.*;

import com.example.ShopApp.DB.MySQLHelper;
import com.example.ShopApp.DB.configSQL;
import com.google.gson.Gson;

@SuppressWarnings("serial")
public class ProductsListServlet extends HttpServlet implements configSQL{
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		//Select items from table
		String sqlQuery = "SELECT * FROM "+ITEMS_TABLE;
		List<ItemType> itemsList = MySQLHelper.selectItemsFromSqlTable(sqlQuery);
		
		//Set the Encoding to support Hebrew
		resp.setCharacterEncoding("UTF-8");
		
		//return the items list as a Json String
		resp.getWriter().write(new Gson().toJson(itemsList));
		
	}
}
