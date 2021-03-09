package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;

public class Item {
	
	public Connection connect()
	{
	 Connection con = null;
	 
	 try
	 {
			 //Class.forName("com.mysql.jdbc.Driver");
			 //con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3307/itemdb","root","ravinduc3303");
			 
			 Class.forName("com.mysql.cj.jdbc.Driver");
			 con = DriverManager.getConnection("jdbc:mysql://localhost:3306/itemdb","root","ravinduc3303");
			 
			 //For testing
			 System.out.print("Successfully connected");
	 }
	 catch(Exception e)
	 {
		 System.out.println("Some error in DB");
		 e.printStackTrace();
	 }

	 return con;
	}
	
	public String insertItem(String code, String name, String price, String desc)
	{
				String output = "";
				
				PreparedStatement ps = null;
				
				try
				{
					Connection con = connect();
					if (con == null)
					{
							return "Error while connecting to the database";
					}
	 
					// create a prepared statement
					//String query = " insert into items(`itemID`,`itemCode`,`itemName`,`itemPrice`,`itemDesc`)"+ " values (?, ?, ?, ?, ?)";
					
					String query = " insert into items(itemID,itemCode,itemName,itemPrice,itemDesc)"
							 + " values (?, ?, ?, ?, ?)"; 
					
					ps = con.prepareStatement(query);
					
					// binding values
						ps.setInt(1, 0);
						ps.setString(2, code);
						ps.setString(3, name);
						ps.setDouble(4, Double.parseDouble(price));
						ps.setString(5, desc); 
						
					//execute the statement
						ps.execute();
						con.close();
						output = "Inserted successfully";
				}
				catch (Exception e)
				{
					output = "Error while inserting";
					System.err.println(e.getMessage());
				}
				return output;
	}
	
	public String readItems()
	{
			String output = "";
			try
			{
	 
				Connection con = connect();
	 
				if (con == null)
				{
					return "Error while connecting to the database for reading.";
				}
				// Prepare the html table to be displayed
				output = "<table border='1'><tr><th>Item Code</th>"
						+"<th>Item Name</th><th>Item Price</th>"
						+ "<th>Item Description</th>"
						+ "<th>Update</th><th>Remove</th></tr>";
	 
				String query = "select * from items";
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);
	 
				// iterate through the rows in the result set
				while (rs.next())
				{
					String itemID = Integer.toString(rs.getInt("itemID"));
					String itemCode = rs.getString("itemCode");
					String itemName = rs.getString("itemName");
					String itemPrice = Double.toString(rs.getDouble("itemPrice"));
					String itemDesc = rs.getString("itemDesc");
	 
					// Add a row into the html table
					output += "<tr><td>" + itemCode + "</td>";
					output += "<td>" + itemName + "</td>";
					output += "<td>" + itemPrice + "</td>";
					output += "<td>" + itemDesc + "</td>";
	 
					// buttons
					output += "<td><input name='btnUpdate' "
							+ " type='button' value='Update'></td>"
							+ "<td><form method='post' action='items.jsp'>"
							+ "<input name='btnRemove' "
							+ " type='submit' value='Remove'>"
							+ "<input name='itemID' type='hidden' "
							+ " value='" + itemID + "'>" + "</form></td></tr>";
				}
				con.close();
	 
				// Complete the html table
				output += "</table>";
	 }
	catch (Exception e)
	 {
		output = "Error while reading the items.";
		System.err.println(e.getMessage());
	 }
	return output;
	}


}
