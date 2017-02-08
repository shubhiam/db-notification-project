package hello;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleStatement;
import oracle.jdbc.dcn.DatabaseChangeRegistration;

public class DBChangeNotify {
  String URL = "jdbc:oracle:thin:SYSTEM/Oracle123@localhost:1521/orcl";
  Properties prop;

//  public static void main(String[] argv) {
//
//    DBChangeNotify dcn = new DBChangeNotify();
//    try {
//      dcn.prop = new Properties();
//      dcn.run();
//    }
//    catch(Exception e) {
//      e.printStackTrace();
//    }
//  }

  void run() throws SQLException {
    OracleConnection conn = (OracleConnection)DriverManager.getConnection(URL,prop);

    Properties prop = new Properties();
    prop.setProperty(OracleConnection.DCN_NOTIFY_ROWIDS,"true");
    DatabaseChangeRegistration dcr = conn.registerDatabaseChangeNotification(prop);

    try {
      DCNListener list = new DCNListener(this);
      dcr.addListener(list);

      Statement stmt = conn.createStatement();
      ((OracleStatement)stmt).setDatabaseChangeRegistration(dcr);
      ResultSet rs = stmt.executeQuery("select * from employee");
      rs.close();
      stmt.close();
    }
    catch(Exception e) {
      //clean up our registration
      if(conn != null)
        conn.unregisterDatabaseChangeNotification(dcr);
      e.printStackTrace();
    }
    finally {
      try {
        conn.close();
      }
      catch(Exception e){
        e.printStackTrace();
      }
    }

    try {
      Thread.currentThread().join();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    finally {
      OracleConnection conn3 = (OracleConnection)DriverManager.getConnection(URL,prop);
      conn3.unregisterDatabaseChangeNotification(dcr);
      conn3.close();
    }
  }
}