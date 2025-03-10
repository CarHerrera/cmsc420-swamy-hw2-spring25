import java.util.HashMap;
// import java.util.List;
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
enum Color {
    RED, BLACK
}
class Node{
    private Node left, right, downLeft, downRight, parent;
    private int value, height, depth; 
    private boolean isPeak, isValley, isNil; 
    private Color color;
    public Node(int height, int value){
        this.value = value;
        this.height = height;
        this.depth = 0;
        this.isPeak = false;
        this.isValley = false;
        this.color = Color.RED;
        this.isNil = false;
        this.downLeft = this.downRight = new Node();
        // this.parent = null;ws
    }
    public Node (){
        this.isNil = true;
        this.color = Color.BLACK;
    }
    public void setValue(int n){this.value = n;}
    public void setHeight(int n){this.height = n;}
    public void setDepth(int n){this.depth = n;}
    public void setBack(Node left){this.left = left;}
    public void setNext(Node n){this.right = n;}
    public void setDownLeft(Node n){this.downLeft = n;}
    public void setDownRight(Node n){this.downRight = n;}
    public void setPeak(boolean b){this.isPeak = b;}
    public void setValley(boolean b){this.isValley = b;}
    public void setColor(Color c){this.color = c;}
    public void setParent(Node n){this.parent = n;}
    public Node getLeft(){return this.left;}
    public Node getRight(){return this.right;}
    public Node getDownLeft(){return this.downLeft;}
    public Node getDownRight(){return this.downRight;}
    public Node getParent(){return this.parent;}
    public boolean isNil(){return this.isNil;}
    public boolean isPeak(){return this.isPeak;}
    public boolean isValley(){return this.isValley;}
    public Color getColor(){return this.color;}
    public int getValue(){ return this.value;}
    public int getHeight(){ return this.height;}
    public int getDepth(){return this.depth;}
}
class TreasureTree{
    Node root;
    public TreasureTree(Node n){
        this.root = n;
        this.root.setColor(Color.BLACK);
    }

    public void addValley(Node n){ 
        addLeaf(this.root, n);
        insertFix(n);
    }
    public Node addLeaf(Node last, Node n){
        if (last.isNil()){
            return n;
        } else {
            if(last.getValue() < n.getValue()){
                // New Node has a larger value so shuold go right
                Node inserted = addLeaf(last.getDownRight(), n);
                last.setDownRight(inserted);
                inserted.setParent(last);
            } else {
                Node inserted = addLeaf(last.getDownLeft(), n);
                last.setDownLeft(inserted);
                inserted.setParent(last);
            }
            return last;
        }
    }
    public void insertFix(Node n){
        Node next = n;
        while(next.getParent().getColor() == Color.RED){
            Node parent = next.getParent();
            Node gp = parent.getParent();
            if(gp.getDownLeft() == parent){
                // Parent is the Left Child
                if (gp.getDownRight().getColor() == Color.RED){
                    gp.getDownRight().setColor(Color.BLACK);
                    parent.setColor(Color.BLACK);
                    gp.setColor(Color.RED);
                    next = gp;
                } else if (parent.getDownRight() == next && gp.getDownLeft().getColor()==Color.BLACK){
                    // Triangle Case
                    next = parent;
                    rotateLeft(parent);
                } else 
                // Line Case 
                if(parent.getDownLeft() == next&& gp.getDownLeft().getColor()==Color.BLACK){
                    rotateRight(gp);
                    parent.setColor(Color.BLACK);
                    gp.setColor(Color.RED);
                    next = gp;
                } 
            } else {
                // Parent is the Right child
                if (gp.getDownLeft().getColor() == Color.RED){
                    gp.getDownLeft().setColor(Color.BLACK);
                    parent.setColor(Color.BLACK);
                    gp.setColor(Color.RED);
                    next = gp;
                } else if (gp.getDownLeft().getColor() == Color.BLACK && parent.getDownRight() == next){
                    // Line Case    
                    rotateLeft(gp);
                    parent.setColor(Color.BLACK);
                    gp.setColor(Color.BLACK);
                    next = gp;
                } else if (gp.getDownLeft().getColor() == Color.BLACK && parent.getDownLeft() == next){
                    // Triangle Case
                    next = parent;
                    rotateRight(parent);
                }
            }
            if(next == this.root){
                break;
            }
        }
        this.root.setColor(Color.BLACK);
    }

    public void rotateLeft(Node x){
        Node y = x.getDownRight();
        x.setDownRight(y.getDownLeft());
        if(x.getParent()==null){
            this.root = y;
        } else {
            Node p = x.getParent();
            if (p.getLeft() == x){
                p.setDownLeft(y);
            } else{
                p.setDownRight(y);
            }
            y.setParent(p);
        }
        x.setParent(y);
        y.setDownLeft(x);
    }
    public void rotateRight(Node x){
        Node y = x.getDownLeft();
        x.setDownLeft(y.getDownRight());
        if(x.getParent()== null){
            this.root = y;
            x.setParent(y);
        } else {
            Node p = x.getParent();
            if (p.getRight() == x){
                p.setDownRight(y);
            } else {
                p.setDownLeft(y);
            }
            y.setParent(p);
        }
        y.setDownRight(x);
        x.setParent(y);
    }
}
class TreeGraph{
    Node head, tail;
    int count;
    // ArrayList<TreasureTree> valley = new ArrayList<TreasureTree>();
    HashMap<Integer, TreasureTree> valleyTracker = new HashMap<Integer, TreasureTree>();
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
            oldNode.setNext(newNode);
            newNode.setBack(oldNode);
            if(oldNode.isPeak()){
                // Ascending Already
                oldNode.setPeak(false);
                newNode.setPeak(true);
            } else if(oldNode.isValley()){
                // Begin Ascent, depth already init to 0
                newNode.setPeak(true);
                if(valleyTracker.get(oldDepth) == null){
                    valleyTracker.put(oldDepth, new TreasureTree(oldNode));
                } else {
                    valleyTracker.get(oldDepth).addValley(oldNode);
                }
                // valleyTracker.put(oldDepth, oldNode)
            }   
        } else {
            // Old Node larger
            oldNode.setNext(newNode);
            newNode.setBack(oldNode);
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
    public TreasureTree getTreasuresAtDepth(int i) {return this.valleyTracker.get(i);}
    public int getCount(){return this.count;}
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
    // Create instance variables here.
    private TreeGraph treasureMap;
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
        return treasureMap.getCount() == 0;
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
        TreasureTree t = treasureMap.getTreasuresAtDepth(depth);
        Node curr = t.root;
        while(!curr.getDownRight().isNil()){
            curr = curr.getDownRight();
        }
        return new IntPair(curr.getHeight(), curr.getValue());
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