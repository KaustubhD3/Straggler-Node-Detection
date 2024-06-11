package com.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class Dbconn {
	public static ArrayList<String> ResList = new ArrayList<String>();
	public static ArrayList<String> filetitle = new ArrayList<String>();
	public static ArrayList<String> offline2list = new ArrayList<String>();
	public static int hitCount;
	public static String data = "";
	 public static String mainInputfile = "C:\\Users\\HARSHAL\\Documents\\FinalProject\\OutputDataset\\";

	public static String mainfile = "C:\\Users\\HARSHAL\\Documents\\FinalProject\\TextFile";
    public static String trainarff_file = mainfile + "\\Training.arff";
    public static String testarff_file = mainfile + "\\Testing.arff";
    public static String traintext_file = mainfile + "\\Training.txt";
    public static String testtext_file = mainfile + "\\Testing.txt";
	public static String filepath = "C:/Users/HARSHAL/Documents/FinalProject/Data/";

	public static String filechunk = filepath+"split/";


	public static String Output = filepath+"Output/";
	public Dbconn() throws SQLException {
		super();
	}

	public static Connection conn() throws SQLException, ClassNotFoundException {
		Connection con;

		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection(
				"jdbc:mysql://localhost:3307/scheduling_application", "root",
				"admin");
		return (con);
	}

	public static Connection conn1(String DbName) throws SQLException,
			ClassNotFoundException {

		String Db = "dynamicResource";

		if (DbName.equals("server1")) {
			Db = "server1";
		} else if (DbName.equals("server2")) {
			Db = "server2";
		} else if (DbName.equals("server3")) {
			Db = "server3";
		} else if (DbName.equals("server4")) {
			Db = "server4";
		}
		//System.out.println("Server Selected " + Db);
		Connection con;
		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection("jdbc:mysql://localhost:3307/" + Db,
				"root", "admin");

		return (con);

	}

}
