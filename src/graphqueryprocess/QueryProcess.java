/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphqueryprocess;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

/**
 *
 * @author LCJ
 */
public class QueryProcess {
    
    public String Process(String gql) throws SQLException {
        ResultSet res = null;
        if (gql.contains("SELECT")) {
            String mysqlQuery = selectquery(gql);      
            return mysqlQuery;
        } else if (gql.contains("DELETE")) {
            String mysqlQuery = deletequery(gql);
            return mysqlQuery;
        }
        //String[] splitedGQL = gql.split(" ");

      return "failed";
    }

    public void processArrow(String arrowStatement, Vector<String[]> _values)
    {
        String subQuery = "";
        int cnt = 0;
        int idx1 = 0;
        int idx2 = 0;
        String arr[][] = null;
        
        Vector<String> values = new Vector<String>();  
        
        while(arrowStatement.indexOf("->",idx1) != -1)
        {
            cnt++;
            idx1 = arrowStatement.indexOf("->",idx1) + 2;
        }
        
        arr = new String[cnt][2];
        
        idx1 = 0;
        idx2 = arrowStatement.indexOf("->");

        for (int i = 0; i < cnt; i++) {
            subQuery = arrowStatement.substring(idx1, idx2);
            values.add(subQuery);
            
            idx1 = idx2 + 2;
            idx2 = arrowStatement.indexOf("->",idx1);
        }
        
        subQuery = arrowStatement.substring(idx1, arrowStatement.length());
        values.add(subQuery);
           
        idx1 = 0;
        
        for(int i = 0; i < cnt; i++) {
            arr[i][0] = values.get(idx1);
            arr[i][1] = values.get(idx1 + 1);
            idx1++;
            
            _values.add(arr[i]);
        }
        
        
    }
   
    public void savevalue(String query, Vector<String[]> _values)
    {
        String subQuery = "";
        int idx1 = 0;
        int idx2 = 0;
        int cnt = 0;
        
        while(query.indexOf("AND",idx1) != -1)
        {
            cnt++;
            idx1 = query.indexOf("AND",idx1) + 3;
        }
        
        idx1 = 0;
        idx2 = query.indexOf("AND");

        for (int i = 0; i < cnt; i++) {
            subQuery = query.substring(idx1, idx2);
            processArrow(subQuery, _values);
            
            idx1 = idx2 + 3;
            idx2 = query.indexOf("AND",idx1);
        }
        
        subQuery = query.substring(idx1, query.length());
        processArrow(subQuery, _values);
        
    }
    
    public String removerow(Vector<String[]> _values, String _from) throws SQLException
    {
        String subquery = "";
        
        for(int i = 0; i < _values.size() + 1; i++)
        {
            if(_from.equals("vertices"))
            {
                if(i == 0)
                {
                    subquery = "DELETE FROM vertices WHERE vid = " +  _values.get(i)[0];
                }
                else if(0 < i && i < _values.size())
                {
                    subquery += " OR vid = " +  _values.get(i)[0];
                }
                else if(i == _values.size())
                {
                    subquery += " OR vid = " +  _values.get(i - 1)[1];
                }
            }
            else if(_from.equals("edges"))
            {
                if(i == 0)
                {
                    subquery = "DELETE FROM edges WHERE efrom = " +  _values.get(i)[0] + " OR eto = " + _values.get(i)[0];
                }
                else if(0 < i && i < _values.size())
                {
                    subquery += " OR efrom = " +  _values.get(i)[0] + " OR eto = " + _values.get(i)[0];
                }/*
                else if(i == _values.size())
                {
                    subquery +=*/
            }
        }
        
        return subquery;
    }
    
    public String selectquery(String query) {
        Vector<String[]> values = new Vector<String[]>();
        String select = "";
        String from = "";
        String where = "";
        String squery = "";
        int cnt = 0;

        if (query.contains("->")) {

            select = query.substring(query.indexOf("SELECT") + 6, query.indexOf("FROM"));
            select = select.replaceAll("\\s+", "");

            if (query.contains("WHERE")) {
                from = query.substring(query.indexOf("FROM") + 4, query.indexOf("WHERE"));
                from = from.replaceAll("\\s+", "");
            } else {
                from = query.substring(query.indexOf("FROM") + 4, query.length());
                from = from.replaceAll("\\s+", "");
            }

            where = query.substring(query.indexOf("WHERE") + 5, query.length());
            where = where.replaceAll("\\s+", "");

            savevalue(where, values);

            cnt = values.size();

            if (where != "") {
                if (cnt == 1) {
                    squery = "SELECT " + select + " FROM " + from + " WHERE efrom = " + values.get(0)[0] + " AND eto = " + values.get(0)[1];
                } else {
                    int i = 0;
                    squery = "SELECT jt1." + select + " FROM " + from + " jt1 ";

                    for (i = 0; i < cnt - 1; i++) {
                        squery += "INNER JOIN " + from + " jt" + (i + 2) + " ON jt1." + select + " = jt" + (i + 2) + "." + select + " ";
                    }

                    for (i = 0; i < cnt; i++) {
                        squery += "AND jt" + (i + 1) + ".efrom = " + values.get(i)[0] + " AND jt" + (i + 1) + ".eto = " + values.get(i)[1] + " ";
                    }

                    System.out.println(squery);
                }
            } else {
                squery = query;
            }

            return squery;
        } else {
            return query;
        }

    }

    public String deletequery(String query) throws SQLException {
        Vector<String[]> values = new Vector<String[]>();
        String from = "";
        String where = "";
        String dquery = "";
        int cnt = 0;

        if (query.contains("->")) {
            from = query.substring(query.indexOf("FROM") + 4, query.indexOf("WHERE"));
            from = from.replaceAll("\\s+", "");
            
            where = query.substring(query.indexOf("WHERE") + 5, query.length());
            where = where.replaceAll("\\s+", "");

            savevalue(where, values);
            
            return removerow(values, from);
            
        } else {
            return query;
        }
    }
    /*
    public String insertquery(String query) {
        if(query.contains("->")) {
            
        }else {
            return query;
        }
    }*/
    
}
