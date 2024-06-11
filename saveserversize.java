package com.algo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;

import com.connection.*;

public class saveserversize {
	 public static DecimalFormat df2 = new DecimalFormat(".##");
	public static void serversize(String ServerName,long Incomingload)
	{
		try
		{
		Connection con = Dbconn.conn();
		double  VMUsage = 0.0, Remainingload = 0.0, VMavailableuse = 0.0;
		double dbVMUsage = 0.0, dbRemainingload = 0.0;
		double finalIncomingload = 0.0;
		double totalload = 1024.0;
		double dbIncomingload = 0.0;
		Statement stRegister = con.createStatement();
		Statement sts = con.createStatement();
		ResultSet rsLogin;
		rsLogin = stRegister
				.executeQuery("select * from serverinfo where ServerName='"+ServerName+"'");
		if (rsLogin.next()) {
			totalload = Double.valueOf(rsLogin.getString("Totalload"));
			dbIncomingload = Double.valueOf(rsLogin
					.getString("Incomingload"));
			dbVMUsage = Double.valueOf(rsLogin.getString("VMUsage"));
			dbRemainingload = Double.valueOf(rsLogin
					.getString("Remainingload"));				
		}
		if (dbRemainingload > 0) {
			finalIncomingload = dbIncomingload + Incomingload;
			VMUsage = (finalIncomingload / totalload * 100)*100;
			Remainingload = totalload - finalIncomingload;
			VMavailableuse = Remainingload / totalload * 100;
		} else {
			finalIncomingload = dbVMUsage + Incomingload;
			VMUsage = (finalIncomingload / totalload * 100)*100;
			Remainingload = totalload - finalIncomingload;
			VMavailableuse = Remainingload / totalload * 100;
		}
		String str = "UPDATE serverinfo SET Incomingload='"
				+df2.format(finalIncomingload) + "',VMUsage='" +df2.format(VMUsage)
				+ "',Remainingload='" + df2.format(Remainingload)
				+ "',VMavailableuse='" +df2.format(VMavailableuse)
				+ "' WHERE ServerName='"+ServerName+"'";
		sts.executeUpdate(str);
		}
		catch (Exception e) {

		}

	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		serversize("Server1",4869);
	}

}
