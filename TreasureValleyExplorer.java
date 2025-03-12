import java.security.DrbgParameters.NextBytes;
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
    public Node getBack(){return this.left;}
    public Node getNext(){return this.right;}
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
    int count;
    public TreasureTree(Node n){
        this.root = n;
        this.count = 1;
        this.root.setColor(Color.BLACK);
    }

    public void addValley(Node n){ 
        addLeaf(this.root, n);
        insertFix(n);
        this.count++;
    }
    public void removeValley(Node n){
        Color org = n.getColor();
        Node x;
        // Left null
        if(n.getDownLeft().isNil()){
            x = n.getDownRight();
            transplant(n, x);
        } else if (n.getDownRight().isNil()){ 
            x = n.getDownLeft();
            transplant(n, x);
        } else {
            Node min = n.getDownRight();
            while (!min.getDownLeft().isNil()){
                min = min.getDownLeft();
            }
            org = min.getColor();
            x = min.getDownRight();
            transplant(min, x);
            min.setDownRight(x.getParent());
            x.getParent().setParent(min);;
            transplant(n,min);
            
        }
        if (org == Color.BLACK){
            delete_fixup(x);
        }
        this.count--;
    }
    public void delete_fixup(Node x){
        while(x.getColor() == Color.BLACK &&  x!= this.root){
            Node parent = x.getParent();
            if (parent.getDownLeft() == x){
                Node sibling = parent.getDownRight();
                if (sibling.getColor() == Color.RED){
                    parent.setColor(Color.RED);
                    sibling.setColor(Color.BLACK);
                    rotateLeft(parent);
                    sibling = parent.getDownRight();
                }

                if (sibling.getDownLeft().getColor() == Color.BLACK &&
                 sibling.getDownRight().getColor() == Color.BLACK ){
                    sibling.setColor(Color.RED);
                    x = parent;
                 } else {
                    if(sibling.getDownRight().getColor() == Color.BLACK){
                        sibling.getDownLeft().setColor(Color.BLACK);
                        sibling.setColor(Color.RED);
                        rotateRight(sibling);
                        sibling = parent.getDownRight();
                    }
                    sibling.setColor(parent.getColor());
                    parent.setColor(Color.BLACK);
                    sibling.getDownRight().setColor(Color.BLACK);
                    rotateLeft(parent);
                    x = root;
                 }
            } else {
                Node sibling = parent.getDownLeft();
                if(sibling.getColor() == Color.RED){
                    sibling.setColor(Color.BLACK);
                    parent.setColor(Color.RED);
                    rotateRight(parent);
                    sibling = parent.getDownLeft();
                }

                if(sibling.getDownRight().getColor() == Color.BLACK &&
                 sibling.getDownLeft().getColor() == Color.BLACK){
                    sibling.setColor(Color.RED);
                    x = parent;
                } else {
                    if (sibling.getDownLeft().getColor() == Color.BLACK){
                        sibling.getDownRight().setColor(Color.BLACK);
                        sibling.setColor(Color.RED);
                        rotateLeft(sibling);
                        sibling = parent.getDownLeft();
                    }
                    sibling.setColor(parent.getColor());
                    parent.setColor(Color.BLACK);
                    sibling.getDownLeft().setColor(Color.BLACK);
                    rotateLeft(parent);
                    x = root;
                }
            }
        }
        x.setColor(Color.BLACK);
    }

    public void transplant(Node u, Node v){
        if(u.getParent() == null){
            this.root = v;
        } else if (u.getParent().getDownLeft() == u){
            u.getParent().setDownLeft(v);
        } else {
            u.getParent().setDownRight(v);
        }
        v.setParent(u.getParent());
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
            if (p.getBack() == x){
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
            if (p.getNext() == x){
                p.setDownRight(y);
            } else {
                p.setDownLeft(y);
            }
            y.setParent(p);
        }
        y.setDownRight(x);
        x.setParent(y);
    }

    public Node findMin(){
        Node curr = this.root;
        while(!curr.getDownLeft().isNil()){
            curr = curr.getDownLeft();
        }
        return curr;
    }

    public Node findMax(){
        Node curr = this.root;
        while(!curr.getDownRight().isNil()){
            curr = curr.getDownRight();
        }
        return curr;
    }
    public int getCount(){
        return this.count;
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
        addToTree(0, h);
        this.count= 1;
    }
    public Node initializeLandform(Node oldNode, Node newNode){
        int oldDepth = oldNode.getDepth();
        if(oldNode.getHeight() < newNode.getHeight()){
            // Old Node is smaller
            oldNode.setNext(newNode);
            newNode.setBack(oldNode);
            if(oldNode.isPeak()){
                // Ascending Already
                oldNode.setPeak(false);
                newNode.setPeak(true);
                // Case for when there's only two
                
                
            } else if(oldNode.isValley()){
                // Begin Ascent, depth already init to 0
                newNode.setPeak(true);
                addToTree(oldDepth, oldNode);
                // valleyTracker.put(oldDepth, oldNode)
            }   
        } else {
            // Old Node larger
            oldNode.setNext(newNode);
            newNode.setBack(oldNode);
            if (oldNode.isValley()){
                // Descending
                oldNode.setValley(false);
                removeFromTree(oldDepth, oldNode);
                newNode.setValley(true);
                newNode.setDepth(oldDepth+1);
                addToTree(oldDepth+1, newNode);
            } else if(oldNode.isPeak()){
                // Begin to descend
                newNode.setValley(true);
                addToTree(1, newNode);
                newNode.setDepth(1);
            }
            
        }
        return newNode;
    }

    public void add(Node n){
        if (this.tail == this.head){
            this.tail = initializeLandform(this.head, n);
            this.head.setDepth(0);
        } else{
            this.tail = initializeLandform(this.tail, n);
        }
        this.count++;
    }
    public void addToTree(int depth, Node n){
        if(valleyTracker.get(depth) == null){
            valleyTracker.put(depth, new TreasureTree(n));
        } else {
            valleyTracker.get(depth).addValley(n);
        }
    }

    public void removeFromTree(int depth, Node n){
        valleyTracker.get(depth).removeValley(n);
    }
    public void insertAt(Node valley, Node newNode){
        if(this.count == 1){
            this.head = newNode;
            this.tail = valley;
            if(newNode.getHeight() > valley.getHeight()){
                removeFromTree(0, valley);
                valley.setDepth(1);
                addToTree(1, valley);
                this.tail.setPeak(false);
                newNode.setPeak(true);
            } else{
                newNode.setDepth(0);
                removeFromTree(0, valley);
                addToTree(0, newNode);
                newNode.setValley(true);
                valley.setPeak(false);
            }
        } else {
            Node back = valley.getBack();
            valley.setBack(newNode);
            back.setNext(newNode);
            newNode.setBack(back);
            newNode.setNext(valley);
            if(valley == this.head){
                this.head = newNode;
                if(newNode.getHeight() > valley.getHeight()){
                    newNode.setPeak(true);
                    newNode.setDepth(0);
                    valley.setDepth(1);
                    removeFromTree(0, valley);
                    addToTree(1, valley);
                } else{
                    removeFromTree(0, valley);
                    addToTree(0, newNode);
                    newNode.setValley(true);
                    valley.setValley(false);
                }
            } else{
                if(newNode.getHeight() > valley.getHeight()){
                    // Right remains valley, are we a peak?
                    if(back.getHeight() > newNode.getHeight()){
                        newNode.setDepth(back.getDepth()+1);
                        this.tail.setDepth(newNode.getDepth()+1);
                    } else {
                        // Greater than left
                        newNode.setPeak(true);
                        newNode.getNext().setDepth(1);
                    }
                }
            }

            
        }
        
        this.count++;
    }
    public void remove(Node valley){
        if(this.count == 1){
            this.head = this.tail = null;
        } else {
            if (this.head == valley){
                Node next = valley.getNext();
                removeFromTree(this.head.getDepth(), this.head);
                this.head.setNext(null);
                next.setBack(null);
                // New Head Set
                this.head = next;
                // Next of New Head
                next = this.head.getNext();
                    if(next == null){
                        // Only node left in the list
                        this.tail = this.head;
                        this.head.setValley(true);
                        this.head.setPeak(true);
                        this.head.setDepth(0);
                    } else{
                        if(next.getHeight() > this.head.getHeight()){
                            this.head.setValley(true);
                            this.head.setDepth(0);
                            addToTree(0, this.head);
                        } else {
                            this.head.setPeak(true);
                            this.head.setDepth(0);
                            int depth = 0;
                            // Maybe could skip if head was already a peak before replacement
                            while(next.getNext() != null ){
                                //  if we are descending
                                depth++;
                                if(next.getNext().getHeight() < next.getHeight()){
                                    next.getNext().setDepth(depth);
                                } else {
                                    // Found something larger than us to the right
                                    // Depths been configured
                                    break;
                                }
                                next = next.getNext();
                            }
                            // Because it could not be a valley idk
                            // next.setValley(true);
                        }
                    }
                    
            } else if (this.tail == valley){
                Node back = this.tail.getBack();
                removeFromTree(this.tail.getDepth(), this.tail);
                this.tail.setBack(null);
                back.setNext(null);
                this.tail = back;
                back = this.tail.getBack();
                if(back == null){
                    this.head = this.tail;
                    this.head.setPeak(true);
                    this.head.setValley(true);
                    this.head.setDepth(0);
                } else {
                    if(this.tail.getHeight() < back.getHeight()){
                        this.tail.setValley(true);
                    } else {
                        this.tail.setPeak(true);
                        this.tail.setDepth(0);
                    }
                }
                
            } else {
                // Restablishing the Link
                Node back = valley.getBack();
                Node next = valley.getNext();
                back.setNext(next);
                next.setBack(back);
                Node left = back;
                while(left.getNext() != null){
                    // Descending
                    Node right = left.getNext();
                    int depth = left.getDepth();
                    if (right.getHeight() < left.getHeight()){
                        if (right.isValley()){
                            removeFromTree(right.getDepth(), right);
                            addToTree(right.getDepth()+1, right);
                        } else if(right == this.tail){
                            this.tail.setValley(true);
                            this.tail.setPeak(false);
                            this.tail.setDepth(depth+1);
                            addToTree(depth+1,right);
                            break;
                        }
                        right.setDepth(depth+1);
                    } else {
                        // Detected that the right is taller than left
                        // Extra check to ensure that whatever is to the left of left is taller than us
                        if(left == head){
                            this.head.setDepth(0);
                            this.head.setValley(true);
                            addToTree(0, this.head);
                        } else {
                            if(left.getBack().getHeight() > left.getHeight()){
                                left.setValley(true);
                                addToTree(left.getDepth(), left);
                            }
                            
                        }
                        break;
                    }
                    left = right;
                }
            }
        }
        
        
        this.count--;
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
        TreasureTree t = treasureMap.getTreasuresAtDepth(depth);
        if(t.getCount() == 0) {return false;}
        Node max = t.findMax();

        return true;
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
        TreasureTree t = treasureMap.getTreasuresAtDepth(depth);
        if(t.getCount() == 0) {return null;}
        Node curr = t.findMax();
        t.removeValley(curr);
        treasureMap.remove(curr);
        return new IntPair(curr.getHeight(), curr.getValue());
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
        TreasureTree t = treasureMap.getTreasuresAtDepth(depth);
        if(t.getCount() == 0) {return null;}
        Node curr = t.findMin();
        t.removeValley(curr);
        treasureMap.remove(curr);
        return new IntPair(curr.getHeight(), curr.getValue());
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
        TreasureTree t = treasureMap.getTreasuresAtDepth(depth);
        if(t.getCount() == 0) {return null;}
        Node curr = t.findMax();
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
        TreasureTree t = treasureMap.getTreasuresAtDepth(depth);
        if(t.getCount() == 0) {return null;}
        Node curr = t.findMin();
        return new IntPair(curr.getHeight(), curr.getValue());
    }

    /**
     * A method to get the number of valleys of a given depth
     *
     * @param depth The depth that we want to count valleys for
     *
     * @return The number of valleys of the specified depth
     */
    public int getValleyCount(int depth) {
        return treasureMap.getTreasuresAtDepth(depth).getCount();
    }
}