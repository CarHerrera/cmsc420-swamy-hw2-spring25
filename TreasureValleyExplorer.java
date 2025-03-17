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
    private Node LinkLeft, LinkRight, TreeLeft, TreeRight, parent;
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
        this.parent = null;
    }
    public Node (){
        this.isNil = true;
        this.color = Color.BLACK;
        this.TreeLeft = this;
        this.TreeRight = this;
    }
    public void setValue(int n){this.value = n;}
    public void setHeight(int n){this.height = n;}
    public void setDepth(int n){this.depth = n;}
    public void setBack(Node left){this.LinkLeft = left;}
    public void setNext(Node n){this.LinkRight = n;}
    public void setDownLeft(Node n){this.TreeLeft = n;}
    public void setDownRight(Node n){this.TreeRight = n;}
    public void setPeak(boolean b){this.isPeak = b;}
    public void setValley(boolean b){this.isValley = b;}
    public void setColor(Color c){this.color = c;}
    public void setParent(Node n){this.parent = n;}
    public Node getBack(){return this.LinkLeft;}
    public Node getNext(){return this.LinkRight;}
    public Node getDownLeft(){return this.TreeLeft;}
    public Node getDownRight(){return this.TreeRight;}
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
    final Node NIL = new Node();
    public TreasureTree(Node n){
        this.root = n;
        this.count = 1;
        this.root.setColor(Color.BLACK);
        this.root.setDownLeft(NIL);
        this.root.setDownRight(NIL);
    }

    public void addValley(Node n){ 
        n.setColor(Color.RED);
        n.setParent(null);
        if(n.getDepth() == 10){
            int i = 0;
        }
        addLeaf(n);
        this.count++;
        
    }
    public void addLeaf(Node n){
        n.setDownLeft(NIL);
        n.setDownRight(NIL);
        Node curr = this.root;
        Node y = null;
        while (!curr.isNil()){
            y = curr;
            if(n.getValue() < curr.getValue()){
                curr = curr.getDownLeft();
            } else {
                curr = curr.getDownRight();
            }
        }
        n.setParent(y);
        if (y == null){
            this.root = n;
        } else if (n.getValue() < y.getValue()){
            y.setDownLeft(n);
        } else {
            y.setDownRight(n);
        }

        if(n.getParent() == null){
            n.setColor(Color.BLACK);
            return;
        } 
        if(n.getParent().getParent() == null) { return;}
        insertFix(n);
    }
    public void insertFix(Node n){
        Node unc;
        while(n.getParent().getColor() == Color.RED){
            if(n.getParent() == n.getParent().getParent().getDownRight()){
                unc = n.getParent().getParent().getDownLeft();
                if(unc.getColor() == Color.RED){
                    unc.setColor(Color.BLACK);
                    n.getParent().setColor(Color.BLACK);
                    n.getParent().getParent().setColor(Color.RED);
                    n = n.getParent().getParent();
                } else {
                    if(n.getParent().getDownLeft() == n){
                        n = n.getParent();
                        rotateRight(n);
                    }
                    n.getParent().setColor(Color.BLACK);
                    n.getParent().getParent().setColor(Color.RED);
                    rotateLeft(n.getParent().getParent());
                }
            } else {
                unc = n.getParent().getParent().getDownRight();
                if(unc.getColor() == Color.RED){
                    unc.setColor(Color.BLACK);
                    n.getParent().setColor(Color.BLACK);
                    n.getParent().getParent().setColor(Color.RED);
                    n = n.getParent().getParent();
                } else {
                    if(n.getParent().getDownRight() == n){
                        n = n.getParent();
                        rotateLeft(n);
                    }
                    n.getParent().setColor(Color.BLACK);
                    n.getParent().getParent().setColor(Color.RED);
                    rotateRight(n.getParent().getParent());
                }
            }
            if(n == this.root) { break;}

        }
        this.root.setColor(Color.BLACK);
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
            if(min.getParent() == n){
                x.setParent(min);
            } else {
                transplant(min, min.getDownRight());
                min.setDownRight(n.getDownRight());
                min.getDownRight().setParent(min);
            }
            transplant(n, min);
            min.setDownLeft(n.getDownLeft());
            min.getDownLeft().setParent(min);
            min.setColor(n.getColor());
            
        }

        if (org == Color.BLACK){
            delete_fixup(x);
        }
        n.setDownLeft(NIL);
        n.setDownRight(NIL);
        n.setParent(null);
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
                    rotateRight(parent);
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


    public void rotateLeft(Node x){
        Node y = x.getDownRight();
        x.setDownRight(y.getDownLeft());
        if(!y.getDownLeft().isNil()){
            y.getDownLeft().setParent(x);
        }
        y.setParent(x.getParent());

        if (x.getParent() == null){
            this.root = y;
        } else if (x == x.getParent().getDownLeft()){
            x.getParent().setDownLeft(y);
        } else {
            x.getParent().setDownRight(y);
        }

        y.setDownLeft(x);
        x.setParent(y);
    }
    public void rotateRight(Node x){
        Node y = x.getDownLeft();
        x.setDownLeft(y.getDownRight());

        if (!y.getDownRight().isNil()){
            y.getDownRight().setParent(x);
        }

        y.setParent(x.getParent());

        if (x.getParent() == null){
            this.root = y;
        } else if (x == x.getParent().getDownRight()){
            x.getParent().setDownRight(y);
        } else {
            x.getParent().setDownLeft(y);
        }

        y.setDownRight(x);;
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
            } else if(oldNode.isValley()){
                // Begin Ascent, depth already init to 0
                newNode.setPeak(true);
                addToTree(oldDepth, oldNode);
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
        int oldDepth = valley.getDepth();
        if(this.count == 1){
            this.head = newNode;
            this.tail = valley;
            this.head.setNext(this.tail);
            this.tail.setBack(this.head);
            if(newNode.getHeight() > valley.getHeight()){
                // newNode is larger
                removeFromTree(oldDepth, valley);
                valley.setDepth(1);
                addToTree(1, valley);
                this.tail.setPeak(false);
                
                newNode.setPeak(true);
            } else{
                // newNode becomes valley. And since its haed, depth=0
                newNode.setDepth(0);
                removeFromTree(oldDepth, valley);
                addToTree(0, newNode);
                newNode.setValley(true);
                // Since count is one, valley had been both a peak and a valley
                valley.setValley(false);
            }
        } else if (valley == this.head){
            this.head = newNode;
            this.head.setNext(valley);
            valley.setBack(newNode);
            if(newNode.getHeight() > valley.getHeight()){
                // newNode is larger
                removeFromTree(0, valley);
                valley.setDepth(1);
                addToTree(1, valley);
                newNode.setPeak(true);
            } else{
                // newNode becomes valley. And since its haed, depth=0
                newNode.setDepth(0);
                removeFromTree(oldDepth, valley);
                addToTree(0, newNode);
                newNode.setValley(true);
                // Since count is one, valley had been both a peak and a valley
                valley.setValley(false);
            }
        } else{
            // Connect the newnode to the list
            Node back = valley.getBack();
            back.setNext(newNode);
            newNode.setBack(back);
            newNode.setNext(valley);
            valley.setBack(newNode);
            if(valley.getHeight() < newNode.getHeight()){
                // Valley is smaller than newNode. So it remains a Valley 
                if(back.getHeight() < newNode.getHeight()){
                    // newNode is a peak
                    newNode.setPeak(true);
                    removeFromTree(oldDepth, valley);
                    valley.setDepth(1);
                    addToTree(1, valley);
                    if(back.isPeak()){
                        // Just Ascending
                        back.setDepth(0);
                        back.setPeak(false);
                        if(back == this.head){
                            addToTree(0, back);
                            back.setValley(true);
                        }
                    } else {
                        // Becomes a new valley
                        back.setValley(true);
                        addToTree(back.getDepth(), back);
                    }
                } else {
                    // Bigger than valley but smaller than oldBack, so we are descending
                    // Takes its old spot
                    removeFromTree(oldDepth, valley);
                    newNode.setDepth(oldDepth);
                    valley.setDepth((oldDepth+1));
                    addToTree((oldDepth+1), valley);
                }

            } else {
                // Valley is larger than the newNode
                if(valley == this.tail){
                    this.tail.setPeak(true);
                }
                newNode.setValley(true);
                newNode.setDepth(oldDepth);
                removeFromTree(oldDepth, valley);
                addToTree(oldDepth, newNode);
                valley.setValley(false);
                valley.setDepth(0);
            }
        } 
        this.count++;
        
        
    }
    public void remove(Node valley){
        removeFromTree(valley.getDepth(), valley);
        if(this.count == 1){
            this.head = this.tail = null;
        } else {
            if (this.head == valley){
                Node next = valley.getNext();
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
                    addToTree(0, this.head);
                } else{
                    if(!this.head.isPeak()){
                        // Previously was part of asecnding sequence
                        this.head.setDepth(0);
                        this.head.setValley(true);
                        addToTree(0, this.head);
                    }
                }
                    
            } else if (this.tail == valley){
                Node back = this.tail.getBack();
                // removeFromTree(this.tail.getDepth(), this.tail);
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
                if(back.isPeak() && next.isPeak()){
                    // Both are peaks so depths are already 0
                    if (back.getHeight() < next.getHeight()){
                        // No need to fix depths
                        back.setPeak(false);
                    } else {
                        next.setPeak(false);
                        adjustDepths(back);
                    }
                } else if (back.isPeak() && !next.isPeak()){
                    if (back.getHeight() < next.getHeight()){
                        // No need to fix depths
                        // This is ascending
                        back.setPeak(false);
                    } else {
                        // Since next is not a peak, its known thats its smaller than the right. Now its a valley
                        next.setDepth(1);
                        next.setValley(true);
                        addToTree(1, next);
                    }
                } else if (!back.isPeak() && next.isPeak()){
                    if (back.getHeight() < next.getHeight()){
                        // Smaller than left, and right so valley
                        back.setValley(true);
                        addToTree(back.getDepth(), back);
                    } else {
                        // next is smaller, so its a descending sequence
                        next.setPeak(false);
                        adjustDepths(back);
                    }
                } else {
                    // Neither are peaks, so one of them becomes a valley
                    if (back.getHeight() < next.getHeight()){
                        addToTree(back.getDepth(), back);
                        back.setValley(true);
                    } else {
                        // Next is a valley
                        addToTree(back.getDepth()+1, next);
                        next.setDepth(back.getDepth()+1);
                        next.setValley(true);
                    }
                }
                
            }
        }
        
        
        this.count--;
    }
    public void adjustDepths(Node n){
        Node x = n;
        int depths = n.getDepth();
        while(x.getNext() != null && x.getHeight() > x.getNext().getHeight()){
            x = x.getNext();
            if (x.isValley()){
                removeFromTree(x.getDepth(), x);
                addToTree((depths+1), x);
            }
            depths++;
            x.setDepth(depths);
        }
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
        TreasureTree t = treasureMap.getTreasuresAtDepth(depth);
        if(t == null) {return false;}
        if(t.getCount() == 0) {return false;}
        Node max = t.findMax();
        Node toIns = new Node (height, value);
        treasureMap.insertAt(max, toIns);
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
        TreasureTree t = treasureMap.getTreasuresAtDepth(depth);
        if(t == null) {return false;}
        if(t.getCount() == 0) {return false;}
        Node min = t.findMin();
        Node toIns = new Node (height, value);
        treasureMap.insertAt(min, toIns);
        return true;
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
        if(t == null) {return null;}
        if(t.getCount() == 0) {return null;}
        Node curr = t.findMax();
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
        if(t == null) {return null;}
        if(t.getCount() == 0) {return null;}
        Node curr = t.findMin();
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
        if(t == null) {return null;}
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
        if(t == null) {return null;}
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