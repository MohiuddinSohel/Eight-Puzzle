/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eight.puzzle;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author mahmed27
 */
public class Utility {
    public static int numberOfRow = 3;
    
    /*
    * check whether current node is a goal node or not
    */
    public static boolean isGoalState(SearchTreeNode<String> node, String goalState) {
        return goalState.contentEquals(node.getState());
    }
    
    /*
    * Check whether a node is already visited or not
    */
    public static boolean isAlreadyVisited(ArrayList<SearchTreeNode<String>> openStateList, ArrayList<String> closedStateList, String currentState) {
        if(closedStateList.size() <= 0) return false;
        for(int i = 0; i < closedStateList.size(); i++) {
            if(currentState.contentEquals(closedStateList.get(i))) return true;
        }
        for(int i = 0; i < openStateList.size(); i++) {
            if(currentState.contentEquals(openStateList.get(i).getState())) return true;
        }
        return false;
    }
    
    /*
    * Print the path from the endNode to root node
    */
    public static void getPath(SearchTreeNode<String> endNode) {
        if(endNode.getParent() == null) {
            System.out.print("Path(State sequence in order) :" + endNode.getState() + "(f(n)= " + (endNode.getDepthGn() + endNode.getHeuristicCostHn()) +") ");
            return;
        }
        Utility.getPath(endNode.getParent());
        System.out.print(endNode.getState() + "(f(n)= " + (endNode.getDepthGn() + endNode.getHeuristicCostHn()) +") ");
    }
    
    public static void printGeneratedState(ArrayList<SearchTreeNode<String>> openNodeList, ArrayList<String> closedNodeList) {
        System.out.println("Number of Generated node(OpenList + closeList) ::: (Generated, but not yet visited Nodes: " + openNodeList.size() + " + Visited(Expanded): " + closedNodeList.size()
                + "). Total: " + (openNodeList.size() + closedNodeList.size()));
         System.out.println("\n");
        String visitedState = "";
        for(int i = 0; i < closedNodeList.size(); i++) {
            visitedState += closedNodeList.get(i);
            if(i != (closedNodeList.size() - 1)) {
                visitedState += ", ";
            }
        }
        String notvisitedState = "";
        for(int i = 0; i < openNodeList.size(); i++) {
            notvisitedState += openNodeList.get(i).getState();
            if(i != (openNodeList.size() - 1)) {
                notvisitedState += ", ";
            }
        }
        System.out.println("Visited Nodes: " + visitedState + "\n");
        System.out.println("Generated, but not yet visited Nodes: " + notvisitedState + "\n");
    }
    
    /*
    * A* Search Algorithm
    */
    public static SearchTreeNode<String> aStarSearch(SearchTreeNode<String> startingNode, String goalState) {
        ArrayList<SearchTreeNode<String>> openNodeList = new ArrayList();
        ArrayList<String> closedStateList = new ArrayList();
        openNodeList.add(startingNode); // Add initial state to open List
        
        while(!openNodeList.isEmpty()) {
            if(isGoalState(openNodeList.get(0), goalState)) {
                openNodeList.remove(0);
                closedStateList.add(goalState);
                Utility.printGeneratedState(openNodeList, closedStateList);
                return openNodeList.get(0); // Goal node found
            }
            
            // Generate all children of a node 
            ArrayList<SearchTreeNode<String>> generatedNode = Utility.generateNewState(openNodeList.get(0)
                    , goalState, Utility.numberOfRow);
            
            // Add all of the generated children to the openList(node to be expanded), if it is not already visited
            for(int i = 0; i < generatedNode.size(); i++) {
                if(!Utility.isAlreadyVisited(openNodeList, closedStateList, generatedNode.get(i).getState())) {
                    openNodeList.add(generatedNode.get(i));
                    System.out.println(generatedNode.get(i).getState());
                }
            }
            
            // Add the current node to closedList(list of visited node)
            openNodeList.get(0).setIsVisited(true);
            closedStateList.add(openNodeList.get(0).getState());
            
            //Remove the current node from openList
            openNodeList.remove(0);
            
            // Sort the openList in ascending order based on compareTo() method of each SearchTreeNode which uses g(n)+h(n) to compare
            if(!openNodeList.isEmpty()) {
                Collections.sort(openNodeList);
            }
        }
       return null; 
    }
    
    /*
    * Find heuristic, h(n) 
    */
    public static int findMissPlacedTile(String goalState, String currentState) {
        int missPlacedTile = 0;
        for(int i = 0; i < goalState.trim().length(); i++) {
            if(/*goalState.trim().charAt(i) != '0' &&*/ goalState.trim().charAt(i) != currentState.trim().charAt(i)) {
                missPlacedTile++;
            }
        }
        return missPlacedTile;
    }
    
    /*
    * Generate all of the children of a node, and set the  parent, g(n), h(n) of each children
    */
    public static ArrayList<SearchTreeNode<String>> generateNewState(SearchTreeNode<String> currentNode
            , String goalState, int numberOfTileInARow) {
        
        String currentState = currentNode.getState();
        ArrayList<SearchTreeNode<String>> newNodeList = new ArrayList();
        int index = currentState.indexOf('0');
        
        if((index + 1 + numberOfTileInARow) <= currentState.length()) { // move done if not in last row
            char[] arrState = currentState.toCharArray();
            arrState[index] ^= arrState[index + 1 + numberOfTileInARow - 1] ^ (arrState[index + 1 + numberOfTileInARow - 1] = arrState[index]); //swap
            SearchTreeNode<String> node = new SearchTreeNode(new String(arrState));
            node.setDepthGn(currentNode.getDepthGn() + 1); //set g(n)
            node.setHeuristicCostHn(Utility.findMissPlacedTile(goalState, new String(arrState))); //set h(n)
            node.setParent(currentNode);
            newNodeList.add(node);
        }
        if((index + 1 - numberOfTileInARow) > 0) { // move up if not in first row
            char[] arrState = currentState.toCharArray();
            arrState[index] ^= arrState[index + 1 - numberOfTileInARow - 1] ^ (arrState[index + 1 - numberOfTileInARow - 1] = arrState[index]); //swap
            SearchTreeNode<String> node = new SearchTreeNode(new String(arrState));
            node.setDepthGn(currentNode.getDepthGn() + 1); //set g(n)
            node.setHeuristicCostHn(Utility.findMissPlacedTile(goalState, new String(arrState))); //set h(n)
            node.setParent(currentNode);
            newNodeList.add(node);
        }
        if(((index + 1 + 1) <= currentState.length()) && !(0 == ((index + 1) % numberOfTileInARow))) { // move right if not in last column
            char[] arrState = currentState.toCharArray();
            arrState[index] ^= arrState[index + 1 + 1 - 1] ^ (arrState[index + 1 + 1 - 1] = arrState[index]); //swap
            SearchTreeNode<String> node = new SearchTreeNode(new String(arrState));
            node.setDepthGn(currentNode.getDepthGn() + 1); //set g(n)
            node.setHeuristicCostHn(Utility.findMissPlacedTile(goalState, new String(arrState))); //set h(n)
            node.setParent(currentNode);
            newNodeList.add(node);
        }
        if(((index + 1 - 1) > 0) && !(1 == ((index + 1) % numberOfTileInARow))) { // move left if not in first column
            char[] arrState = currentState.toCharArray();
            arrState[index] ^= arrState[index + 1 - 1 - 1] ^ ( arrState[index + 1 - 1 - 1] = arrState[index]); //swap
            SearchTreeNode<String> node = new SearchTreeNode(new String(arrState));
            node.setDepthGn(currentNode.getDepthGn() + 1); //set g(n)
            node.setHeuristicCostHn(Utility.findMissPlacedTile(goalState, new String(arrState))); //set h(n)
            node.setParent(currentNode);
            newNodeList.add(node);
        }
        return newNodeList;
    }
    
}
