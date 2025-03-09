import java.util.List;
/**
 * A convenient class that stores a pair of integers.
 * DO NOT MODIFY THIS CLASS.
 */

class IntPair {
    // Make the fields final to ensure they cannot be changed after initialization
    public final int first;
    public final int second;

    public IntPair(int first, int second) {
        this.first = first;
        this.second = second;
    }

    public String toString() {
        return "(" + first + "," + second + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        IntPair other = (IntPair) obj;
        return first == other.first && second == other.second;
    }

    @Override
    public int hashCode() {
        return 31 * first + second;
    }
}
class Node{
    private Node upLeft, upRight, downLeft, downRight;
    private int value, height, depth; 
    boolean isPeak, isValley; 
    public Node(int height, int value){
        this.value = value;
        this.height = height;
        this.depth = 0;
        this.isPeak = false;
        this.isValley = false;
    }
    public void setValue(int n){this.value = n;}
    public void setHeight(int n){this.height = n;}
    public void setDepth(int n){this.depth = n;}
    public void setUpLeft(Node left){this.upLeft = left;}
    public void setUpRight(Node n){this.upRight = n;}
    public void setDownLeft(Node n){this.downLeft = n;}
    public void setDownRight(Node n){this.downRight = n;}
    public void setPeak(boolean b){this.isPeak = b;}
    public void setValley(boolean b){this.isValley = b;}
    public Node getUpLeft(){return this.upLeft;}
    public Node getUpRight(){return this.upRight;}
    public Node getDownLeft(){return this.downLeft;}
    public Node getDownRight(){return this.downRight;}
    public boolean isPeak(){return this.isPeak;}
    public boolean isValley(){return this.isValley;}
    public int getValue(){ return this.value;}
    public int getHeight(){ return this.height;}
    public int getDepth(){return this.depth;}
}
class TreeGraph{
    Node head, tail;
    int count;
    public TreeGraph(Node h){
        this.head = h;
        this.tail = h;
        this.head.setValley(true);
        this.head.setPeak(true);
        this.count= 1;
    }
    public Node addLandform(Node oldNode, Node newNode){
        int oldDepth = oldNode.getDepth();
        if(oldNode.getHeight() < newNode.getHeight()){
            // Old Node is smaller
            oldNode.setUpRight(newNode);
            newNode.setDownLeft(oldNode);
            if(oldNode.isPeak()){
                // Ascending Already
                oldNode.setPeak(false);
                newNode.setPeak(true);
            } else if(oldNode.isValley()){
                // Begin Ascent
                newNode.setPeak(true);
            }
            
            
        } else {
            // Old Node larger
            oldNode.setDownRight(newNode);
            newNode.setUpLeft(oldNode);
            if (oldNode.isValley()){
                // Descending
                oldNode.setValley(false);
                newNode.setValley(true);
                newNode.setDepth(oldDepth+1);
            } else if(oldNode.isPeak()){
                // Begin to descend
                newNode.setValley(true);
                newNode.setDepth(1);
            }
            
        }
        return newNode;
    }

    public void add(Node n){
        if (this.tail == this.head){
            this.tail = addLandform(this.head, n);
            this.head.setDepth(0);
        } else{
            this.tail = addLandform(this.tail, n);
        }
        this.count++;
    }
}
/**
 * TreasureValleyExplorer class operates on a landscape of Numerica,
 * selectively modifying the most and least valuable valleys of a specified
 * depth.
 * 
 * DO NOT MODIFY THE SIGNATURE OF THE METHODS PROVIDED IN THIS CLASS.
 * You are encouraged to add methods and variables in the class as needed.
 *
 * @author Carlos Herrera
 */
public class TreasureValleyExplorer {
    private TreeGraph treasureMap;
    // Create instance variables here.

    /**
     * Constructor to initialize the TreasureValleyExplorer with the given heights
     * and values
     * of points in Numerica.
     *
     * @param heights An array of distinct integers representing the heights of
     *                points in the landscape.
     * @param values  An array of distinct integers representing the treasure value
     *                of points in the landscape.
     */
    public TreasureValleyExplorer(int[] heights, int[] values) {
        // TODO: Implement the constructor.
        if (heights.length >= 1){
            Node root = new Node(heights[0], values[0]);
            this.treasureMap = new TreeGraph(root);
            for(int i =1; i < heights.length; i++){
                treasureMap.add(new Node(heights[i], values[i]));
            }
        }
    }

    /**
     * Checks if the entire landscape is excavated (i.e., there are no points
     * left).
     *
     * @return true if the landscape is empty, false otherwise.
     */
    public boolean isEmpty() {
        // TODO: Implement the isEmpty method.
        return false;
    }

    /**
     * A method to insert a new landform prior to the most valuable valley of the
     * specified depth
     *
     * @param height The height of the new landform
     * @param value  The treasure value of the new landform
     * @param depth  The depth of the valley we wish to insert at
     *
     * @return true if the insertion is successful, false otherwise
     */
    public boolean insertAtMostValuableValley(int height, int value, int depth) {
        // TODO: Implement the insertAtMostValuableValley method
        return false;
    }

    /**
     * A method to insert a new landform prior to the least valuable valley of the
     * specified depth
     *
     * @param height The height of the new landform
     * @param value  The treasure value of the new landform
     * @param depth  The depth of the valley we wish to insert at
     *
     * @return true if the insertion is successful, false otherwise
     */
    public boolean insertAtLeastValuableValley(int height, int value, int depth) {
        // TODO: Implement the insertAtLeastValuableValley method
        return false;
    }

    /**
     * A method to remove the most valuable valley of the specified depth
     *
     * @param depth The depth of the valley we wish to remove
     *
     * @return An IntPair where the first field is the height and the second field
     *         is the treasure value of the removed valley
     * @return null if no valleys of the specified depth exist
     */
    public IntPair removeMostValuableValley(int depth) {
        // TODO: Implement the removeMostValuableValley method
        return null;
    }

    /**
     * A method to remove the least valuable valley of the specified depth
     *
     * @param depth The depth of the valley we wish to remove
     *
     * @return An IntPair where the first field is the height and the second field
     *         is the treasure value of the removed valley
     * @return null if no valleys of the specified depth exist
     */
    public IntPair removeLeastValuableValley(int depth) {
        // TODO: Implement the removeLeastValuableValley method
        return null;
    }

    /**
     * A method to get the treasure value of the most valuable valley of the
     * specified depth
     *
     * @param depth The depth of the valley we wish to find the treasure value of
     *
     * @return An IntPair where the first field is the height and the second field
     *         is the treasure value of the found valley
     * @return null if no valleys of the specified depth exist
     */
    public IntPair getMostValuableValley(int depth) {
        // TODO: Implement the getMostValuableValleyValue method
        return null;
    }

    /**
     * A method to get the treasure value of the least valuable valley of the
     * specified depth
     *
     * @param depth The depth of the valley we wish to find the treasure value of
     *
     * @return An IntPair where the first field is the height and the second field
     *         is the treasure value of the found valley
     * @return null if no valleys of the specified depth exist
     */
    public IntPair getLeastValuableValley(int depth) {
        // TODO: Implement the getLeastValuableValleyValue method
        return null;
    }

    /**
     * A method to get the number of valleys of a given depth
     *
     * @param depth The depth that we want to count valleys for
     *
     * @return The number of valleys of the specified depth
     */
    public int getValleyCount(int depth) {
        // TODO: Implement the getValleyCount method
        return 0;
    }
}