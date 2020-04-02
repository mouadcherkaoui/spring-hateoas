
package com.nlayersapp.demo.services;

import manifold.ext.api.*;
import java.lang.reflect.*;

import java.sql.*;
// import java.sql.CallableStatement;
// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.PreparedStatement;
// import java.sql.ResultSet;
// import java.sql.SQLException;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

public class DataAccessService {
    public static void main(final String[] args){        
                // Create datasource.
                final SQLServerDataSource ds = new SQLServerDataSource();
                ds.setUser("<user>");
                ds.setPassword("<password>");
                ds.setServerName("<server>");
                ds.setPortNumber(Integer.parseInt("<port>"));
                ds.setDatabaseName("AdventureWorks");
        
                try (Connection con = ds.getConnection();
                        CallableStatement cstmt = con.prepareCall("{call dbo.uspGetEmployeeManagers(?)}");) {
                    // Execute a stored procedure that returns some data.
                    cstmt.setInt(1, 50);
                    final ResultSet rs = cstmt.executeQuery();
        
                    // Iterate through the data in the result set and display it.
                    while (rs.next()) {
                        System.out.println("EMPLOYEE: " + rs.getString("LastName") + ", " + rs.getString("FirstName"));
                        System.out.println("MANAGER: " + rs.getString("ManagerLastName") + ", " + rs.getString("ManagerFirstName"));
                        System.out.println();
                    }
                }
                // Handle any errors that may have occurred.
                catch (final SQLException e) {
                    e.printStackTrace();
                }
    }

    @Extension
    public static Connection Connection(@This final Connection source, final String connectionString) throws SQLException{
        return DriverManager.getConnection(connectionString);
    }
    
    @Extension
    public static PreparedStatement Select(@This final Connection source, final String tableName, final String[] columns) throws SQLException{
        String query = "SELECT"; 
        for (final String column : columns) {
            query += String.format(" %s,", column);
        }
        query = String.format("%s From %s", query.substring(0, query.lastIndexOf(",")), tableName);

        return source.prepareStatement(query);
    }

    @Extension
    public static <TItem> Statement Select(@This final Connection source) throws SQLException {
        final String[] columns;      
        final TItem item = null;
        final Type _class = item.getClass();    

        String query = "SELECT"; 
        for (Method method : _class.getClass().getMethods()) {
            if(method.getName().startsWith("get_")){
                String columnName = method.getName().substring(3);
                query += String.format(" %s,", columnName);
            }
        }
        query = String.format("%s From %s", query.substring(0, query.lastIndexOf(",")), _class.getTypeName());

        Statement statement = source.prepareStatement(query);
        return statement;
    }

    // private static Class<?> TypeOf<T>(){
    //     return (Class<?>)T;
    // }

}

class TypeOf {
    public static Class<?> methodParameterfinal (Object obj, final String methodName,
            final int parameterNumber) {
        final Class<?> objClass = obj.getClass();
        for (Method objMethod : objClass.getDeclaredMethods()) {
            if (objMethod.getName().equals(methodName)) {
                final Class<?>[] objMethodParameters = objMethod.getParameterTypes();
                if (objMethodParameters.length >= parameterNumber) {
                    return objMethodParameters[parameterNumber - 1];
                }
            }
        }
        throw new IllegalArgumentException(
                "Unknown method or invalid parameter number");
    }
}