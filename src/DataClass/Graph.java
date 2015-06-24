/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataClass;

import java.util.Vector;

/**
 *
 * @author LCJ
 */
public class Graph {
    int gid;
    String gName;
    Vector<Vertex> Vertices = new Vector<Vertex>();
    Vector<Edge>   Edges = new Vector<Edge>();
    
    public Graph(int _gid, String _gName)
    {
        this.gid = _gid;
        this.gName = _gName;
    }
    
    public void addVertex(Vertex newV)
    {
        Vertices.add(newV);
    }
    
    public void addEdges(Edge newE)
    {
        Edges.add(newE);
    }
}
