/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eight.puzzle;

import java.util.Scanner;

/**
 *
 * @author mahmed27
 */
public class EightPuzzle {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
/*
Input the initial and goal state separated by one empty new line. You can test all of your initial and goal state in a single run.
However, two different test case should be separated by one empty new line.
2 8 3
1 6 4
7   5

1 2 3
8 6 4
7 5   

5 4     
6 1 8
7 3 2

1 2 3
4   5
6 7 8

7 2 4
5   6
8 3 1

  1 2
3 4 5
6 7 8
*/
        Scanner in = new Scanner(System.in);
        String initial = "";
        String goal = "";
        boolean isInitial = true , isGoal =false;
        int i = 0;
        System.out.println("Input the initial and goal state separated by one empty new line. You can test all of your initial and goal state in a single run.\n" +
            "However, two different test case should be separated by one empty new line.");
        while(true) {
            String line = in.nextLine();
            if(line.isEmpty()) {
                isGoal = !isGoal;
                isInitial = !isInitial;
                i = 0;
                continue;
            }
            
            if(line.length() < 5) {
                System.out.println("Invalid Output");
                return;
            }
            if(isInitial) {
                initial += Character.toString(line.charAt(0)) + Character.toString(line.charAt(2)) 
                        + Character.toString(line.charAt(4));
                i++;
            } else if(isGoal) {
                goal += Character.toString(line.charAt(0)) + Character.toString(line.charAt(2)) 
                        + Character.toString(line.charAt(4));
                i++;
            }
            
            //Execute the A* search for Each test case
            if(i == 3 && isGoal) {
                String initialState = initial.replaceAll(" ", "0");
                String goalState = goal.replaceAll(" ", "0");
                
                
                System.out.println("Initial State: " + initialState);
                System.out.println("Goal State:    " + goalState);
                
                SearchTreeNode<String> initialNode = new SearchTreeNode<> (initialState);
                SearchTreeNode<String> goalNode = Utility.aStarSearch(initialNode, goalState);
                if(goalNode != null) {
                    System.out.println("Initial State: " + initialState);
                    System.out.println("Goal State:    " + goalState);
                    System.out.println("Total State in Optimal path(including initial and goal node) : " + (goalNode.getDepthGn() + 1));
                    Utility.getPath(goalNode);
                    System.out.println("\n");
                } else {
                    System.out.println("No path found. Search Stuck");
                }
                
                //Reset the input variable to take new test case input
                initial = "";
                goal = "";
            }
        }
    }
    
}
