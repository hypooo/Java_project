package db;
import java.sql.*;

public class db {
    private Connection dbConn;
    private Statement stateMent;
    public db(){
        //SQL Sever 2012 JDBC 驱动
        String driverName =  "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        //URL
        String dbURL = "jdbc:sqlserver://localhost:1433;DatabaseName=pay";
        String userName = "sa";
        String userPwd = "123456";

        try{
            Class.forName(driverName);
            dbConn = DriverManager.getConnection(dbURL,userName,userPwd);
            //连接成功信息
            System.out.println("连接成功");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public int excuteUpdate(String sql) throws SQLException{
        stateMent = dbConn.createStatement();
        return stateMent.executeUpdate(sql);
    }

    public ResultSet excuteQuery(String sql) throws SQLException{
        stateMent = dbConn.createStatement();
        return stateMent.executeQuery(sql);
    }

    public void closeConn() throws SQLException{
        stateMent.close();
        dbConn.close();
    }

    public PreparedStatement PreparedStatement(String sql) throws SQLException{
        return dbConn.prepareStatement(sql);
    }
}
