/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataClass;

/**
 *
 * @author LCJ
 */
public class Edge {
    int from;
    int to;
    String value;
    
    public Edge(int _from,int _to, String _value) 
    {        
        this.from  = _from;
        this.to    = _to;
        this.value = _value;
    }
}
