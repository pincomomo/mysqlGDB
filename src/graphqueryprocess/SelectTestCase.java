/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphqueryprocess;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author LCJ
 */
public class SelectTestCase {
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
        String query = process.Process ("SELECT egid FROM edges WHERE 1->2->3 AND 4->5 AND 6->7->8");
                
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
            st.addBatch("INSERT INTO vertices(vgid,vid) VALUES(" + i + ",4)");
            st.addBatch("INSERT INTO vertices(vgid,vid) VALUES(" + i + ",5)");
            st.addBatch("INSERT INTO vertices(vgid,vid) VALUES(" + i + ",6)");
            st.addBatch("INSERT INTO vertices(vgid,vid) VALUES(" + i + ",7)");
            st.addBatch("INSERT INTO vertices(vgid,vid) VALUES(" + i + ",8)");
            
            st.addBatch("INSERT INTO edges(egid,efrom,eto) VALUES(" + i + ",1,2)");
            st.addBatch("INSERT INTO edges(egid,efrom,eto) VALUES(" + i + ",2,3)");
            st.addBatch("INSERT INTO edges(egid,efrom,eto) VALUES(" + i + ",4,5)");
            st.addBatch("INSERT INTO edges(egid,efrom,eto) VALUES(" + i + ",6,7)");
            st.addBatch("INSERT INTO edges(egid,efrom,eto) VALUES(" + i + ",7,8)");
        }
        st.executeBatch();
        
        ResultSet rs = st.executeQuery(query);
        rs.next();
        ResultSetMetaData rsmd = rs.getMetaData();
        
        for(int i=0; i<10; i++)
        {
            System.out.println(rs.getInt("egid"));
            rs.next();
        }
        
        
        
    }
}
