/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphqueryprocess;

import DataClass.Graph;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author LCJ
 */
public class DeleteTestCase {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InstantiationException, SQLException {
        
        
        
        
        /*
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
            return;
        } 
        */
        //System.out.println("MySQL JDBC Driver Registered!");
        Connection connection = null;

        String dbinfo = "jdbc:mysql://localhost/testG";
        String dbID = "root";
        String dbPW = "apmsetup";

        try {
            connection = java.sql.DriverManager.getConnection(dbinfo, dbID, dbPW);

        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
        }

        if (connection != null) {
            System.out.println("You made it, take control your database now!");
        } else {
            System.out.println("Failed to make connection!");
        }

        QueryProcess process = new QueryProcess();
       // ResultSet restemp = process.Process(connection, "SELECT egid FROM edges WHERE 1->2->3 AND 4->5 AND 6->7->8");
        String query = process.Process("DELETE FROM edges WHERE 1->2");
        
        Statement st = connection.createStatement();
        st.executeUpdate("DELETE FROM graphs");
        st.executeUpdate("DELETE FROM edges");
        st.executeUpdate("DELETE FROM vertices");
        
        //Vector<Graph> Graphs = new Vector<Graph>();
        for(int i = 0; i<10; i++)
        {
            st.addBatch("INSERT INTO graphs(gid) VALUES (" + i + ")");
            st.addBatch("INSERT INTO vertices(vgid,vid) VALUES(" + i + ",1)");
            st.addBatch("INSERT INTO vertices(vgid,vid) VALUES(" + i + ",2)");
            st.addBatch("INSERT INTO vertices(vgid,vid) VALUES(" + i + ",3)");
            st.addBatch("INSERT INTO edges(egid,efrom,eto) VALUES(" + i + ",1,2)");
            st.addBatch("INSERT INTO edges(egid,efrom,eto) VALUES(" + i + ",2,3)");
        }
        st.executeBatch();
        
        System.out.println(query);
        st.executeUpdate(query);
        
    }

}

