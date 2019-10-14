/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eight.puzzle;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mahmed27
 * Node data structure for each node of the search tree
 */
public class SearchTreeNode<T> implements Comparable<SearchTreeNode<T>> {
    
    private SearchTreeNode<T> parent = null;
    
    private T state = null;
    private boolean isVisited = false;
    private int depthGn = 0;         //G(n)
    private int heuristicCostHn = 0; //h(n)
    
    private List<SearchTreeNode> children = new ArrayList<>();

    public SearchTreeNode(T value) {
        this.state = value;
    }
    
    public T getState() {
        return this.state;
    }
    
    public boolean getIsVisited() {
        return this.isVisited;
    }
    
    public void setIsVisited(boolean isVisited) {
        this.isVisited = isVisited;
    }
    
    public int getDepthGn() {
        return this.depthGn;
    }
    
    public void setDepthGn(int depth) {
        this.depthGn = depth;
    }
    
    public int getHeuristicCostHn() {
        return this.heuristicCostHn;
    }
    
    public void setHeuristicCostHn(int heuristicCost) {
        this.heuristicCostHn = heuristicCost;
    }
    
    public void addChildToTree(SearchTreeNode child) {
        child.setParent(this);
        this.children.add(child);
    }

    public void setParent(SearchTreeNode parent) {
        this.parent = parent;
    }

    public SearchTreeNode getParent() {
        return this.parent;
    }

    public List<SearchTreeNode> getChildren() {
        return this.children;
    }
    
    /*
    * This method will be used to sort the node in open list based on G(n) + h(n) 
    */
    @Override
    public int compareTo(SearchTreeNode<T> o) {
        if((this.depthGn + this.heuristicCostHn)  == (o.getDepthGn() - o.getHeuristicCostHn())) {
            if(this.depthGn == o.getDepthGn()) {
                return this.heuristicCostHn - o.getHeuristicCostHn();
            } else {
                return this.depthGn - o.getDepthGn();
            }
        } else {
            return this.depthGn + this.heuristicCostHn - o.getDepthGn() - o.getHeuristicCostHn();
        }
        
    }
}
