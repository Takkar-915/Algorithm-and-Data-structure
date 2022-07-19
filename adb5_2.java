import java.io.*;
     
class Instruction {
    int cmd;
    String optkey;
    String optdata;
}
 
class KeyAndData {
    String key;
    String data;
    public KeyAndData(String key, String data) {
        this.key = key;
        this.data = data;
    }
}
 
class Node {
    Node left;
    Node right;
    Node parent;
    KeyAndData keyAndData;
 
    public Node(String key, String data) {
        this.keyAndData = new KeyAndData(key,data);
        this.left = null;
        this.right = null;
        this.parent = null;
    }
    static void printTxt(String s) {
        System.out.print(s);
    }
    void printTree() {
        printTxt("[");
        if( left != null ) { left.printTree(); }
        printTxt(" ");
        printThisNode();
        printTxt(" ");
        if( right != null ) { right.printTree(); }
        printTxt("]");
    }
    void printThisNode() {
        if( keyAndData == null ) {
            printTxt("(null)");
        } else {
            String key_i;
            String data_i;
            if( keyAndData.key != null ) {
                if( keyAndData.key.length() > 10 ) {
                    key_i = keyAndData.key.substring(0,10);
                } else {
                    key_i = keyAndData.key;
                }
            } else {
                key_i = "(null)";
            }
            if( keyAndData.data != null ) {
                if( keyAndData.data.length() > 10 ) {
                    data_i = keyAndData.data.substring(0,10);
                } else {
                    data_i = keyAndData.data;
                }
            } else {
                data_i = "(null)";
            }
            printTxt("(" + key_i + ":" + data_i + ")");
        }
    }
    void printThisNodeDetails() {
        printTxt("node.parent= ");
        if( parent != null ) { parent.printThisNode(); }
        printTxt("\n");
        printTxt("node       = ");
        printThisNode(); printTxt("\n");
        printTxt("node.left  = ");
        if( left != null ) { left.printThisNode(); }
        printTxt("\n");
        printTxt("node.right = ");
        if( right != null ) { right.printThisNode(); }
        printTxt("\n");
    }
}
 
class adb5_2 {
    /*
     * switch-caseの部分でわかりやすくなるように定義
     */
    static final int NOP = 0;
    static final int HALT = 1;
    static final int SHOW = 2;
    static final int INS = 3;
    static final int DEL = 4;
    static final int FIND = 5;
    static final int UP = 6;
    static final int L = 7;
    static final int R = 8;
    static final int ROOT = 9;
    static final int FNDR = 10;
    static final int DMIN = 11;
    static final int DMAX = 12;
    
    
    Node rootnode = null;
    Node currentnode = null;
    //java.util.Vector findrangebuf = new java.util.Vector();
    java.util.Vector<Node> findrangebuf = new java.util.Vector<Node>();
    
    public static void main(String args[]) {
        (new adb5_2()).run_interpreter(); 
    }
    
    public static void printTxt(String s) {
        System.out.print(s);
    }
    
    void run_interpreter() {
        Node nd;
        Instruction inst;
        
        do{
            inst = read_instruction();
            switch(inst.cmd) {
            case NOP:
                break;
            case SHOW:
                if(rootnode!=null) {rootnode.printTree(); } printTxt("\n");
                break;
            case INS:
                nd = new Node(inst.optkey,inst.optdata);
                insertNode(nd,rootnode);
                printTxt(" |Node:");
                nd.printThisNode(); printTxt(" inserted.\n");
                currentnode = rootnode;
                break;
            case DEL:
                nd = deleteNodeByKey(inst.optkey,rootnode);
                if( nd != null ) {
                    printTxt(" |Node:");
                    nd.printThisNode(); printTxt(" deleted.\n");
                    if( currentnode == nd ) {
                        currentnode = rootnode;
                    }
                } else {
                    printTxt(" |Key not found.\n");
                }
                break;
            case DMIN:
                nd = deleteMinNode(rootnode);
                if( nd != null ) {
                    printTxt(" |Node:");
                    nd.printThisNode(); printTxt(" deleted.\n");
                    if( currentnode == nd ) {
                        currentnode = rootnode;
                    }
                } else {
                    printTxt(" |no node.\n");
                }
                break;
            case DMAX:
                nd = deleteMaxNode(rootnode);
                if( nd != null ) {
                    printTxt(" |Node:");
                    nd.printThisNode(); printTxt(" deleted.\n");
                    if( currentnode == nd ) {
                        currentnode = rootnode;
                    }
                } else {
                    printTxt(" |no node.\n");
                }
                break;
            case FIND:
                nd = findNodeByKey(inst.optkey,rootnode);
                if( nd != null ) {
                    printTxt(" |found:");
                    nd.printThisNode(); printTxt(" \n");
                } else {
                    printTxt(" |Key not found.\n");
                }
                break;
            case FNDR:
                initNodeBuf();
                findNodeByRange(inst.optkey,inst.optdata,rootnode);
                printAllInBuf();
                break;
            case UP:
                if( currentnode != null && currentnode.parent != null ) {
                    currentnode = currentnode.parent;
                } else {
                    printTxt("no parent node.\n");
                }
                if( currentnode != null ) {
                    currentnode.printThisNodeDetails(); printTxt("\n");
                }
                break;
            case L:
                if( currentnode != null && currentnode.left != null ) {
                    currentnode = currentnode.left;
                } else {
                    printTxt("no left node.\n");
                }
                if( currentnode != null ) {
                    currentnode.printThisNodeDetails(); printTxt("\n");
                }
                break;
            case R:
                if( currentnode != null && currentnode.right != null ) {
                    currentnode = currentnode.right;
                } else {
                    printTxt("no right node.\n");
                }
                if( currentnode != null ) {
                    currentnode.printThisNodeDetails(); printTxt("\n");
                }
                break;
            case ROOT:
                currentnode = rootnode;
                if( currentnode != null ) {
                    currentnode.printThisNodeDetails(); printTxt("\n");
                }
                break;
            }
        } while( inst.cmd != HALT );
        return;
    }
    
    int compare(KeyAndData one, KeyAndData two) {
        if( one == null || two == null ) { return(-1);}
        return(compareByKey( one.key, two ));
    }
 
    int compareByKey(String onekey, KeyAndData two ) {
        if( onekey == null || two == null || two.key == null ) { return(-1);}
        return( onekey.compareTo(two.key) );
    }
 
    void insertNode(Node newnode, Node node) {
        int compareflag;
        if( newnode == null ) {return;}
        
        if( node == null ) {
            rootnode = newnode;
            newnode.parent = null;
        } else {
            compareflag = compare(newnode.keyAndData,node.keyAndData);
            if( compareflag < 0 ) {
                if( node.left != null ) {
                    insertNode(newnode,node.left);
                } else {
                    node.left = newnode;
                    newnode.parent = node;
                }
            } else {
                if( node.right != null ) {
                    insertNode(newnode,node.right);
                } else {
                    node.right = newnode;
                    newnode.parent = node;
                }
            }
        }
        return;
    }
    
    Node deleteMinNode(Node node) {
        if( node == null ){return(null);}
        if( node.left == null ) {
            if( node.parent == null ) {
                rootnode = node.right;
            } else if( node.parent.left == node ) {
                node.parent.left = node.right;
            } else {
                node.parent.right = node.right;
            }
            if( node.right != null ) {
                node.right.parent = node.parent;
            }
            node.right = null;
            return(node);
        } else {
            return(deleteMinNode(node.left));
        }
    }

    Node deleteMaxNode(Node node) {
        if( node == null ){return(null);}
        if( node.left == null ) {
            if( node.parent == null ) {
                rootnode = node.right;
            } else if( node.parent.left == node ) {
                node.parent.left = node.right;
            } else {
                node.parent.right = node.right;
            }
            if( node.right != null ) {
                node.right.parent = node.parent;
            }
            node.right = null;
            return(node);
        } else {
            return(deleteMaxNode(node.left));
        }
    }
    
    Node deleteNodeByKey(String key, Node node) {
        int compareflag;
        Node nd;
        if( node == null ){ return null;}
        
        compareflag = compareByKey(key,node.keyAndData);
        
        node.printThisNode();  
        
        if( compareflag == 0 ) {
            if( node.right != null ) {
                nd = deleteMinNode(node.right);
                
                printTxt(" (deleted:"); nd.printThisNode(); printTxt(" ");
                node.printThisNode(); printTxt(" will be replaced with ");
                nd.printThisNode(); printTxt(") ");
                
                if( node.parent == null ) {
                    rootnode = nd;
                } else if( node.parent.left == node ) {
                    node.parent.left = nd;
                } else {
                    node.parent.right = nd;
                }
                nd.parent = node.parent;
                nd.left = node.left;
                nd.right = node.right;
                if( node.left != null ) {
                    node.left.parent = nd;
                }
                if( node.right != null ) {
                    node.right.parent = nd;
                }
                return(node);
            } else {
                if( node.parent == null ) {
                    rootnode = node.left;
                } else if( node.parent.left == node ) {
                    node.parent.left = node.left;
                } else {
                    node.parent.right = node.left;
                }
                if( node.left != null ) {
                    node.left.parent = node.parent;
                }
                return(node);
            }
        } else if( compareflag < 0 ) {
            return( deleteNodeByKey(key,node.left) );
        } else {
            return( deleteNodeByKey(key,node.right) );
        }
    }
    
    Node findNodeByKey(String key, Node node) {
        int compareflag;
        if( node == null ){return(null);}
        
        node.printThisNode();
        
        compareflag = compareByKey(key,node.keyAndData);
        
        if( compareflag == 0 ) {
            return(node);
        } else if( compareflag < 0 ) {
            return(findNodeByKey(key,node.left));
        } else {
            return(findNodeByKey(key,node.right));
        }
    }
    
    void findNodeByRange(String key0, String key1, Node node) {
        int compareflag0, compareflag1;
        if( node == null ){return;}
        
        node.printThisNode();
        
        compareflag0 = compareByKey(key0,node.keyAndData);
        compareflag1 = compareByKey(key1,node.keyAndData);
        
        if( compareflag0 < 0 && node.left != null ) { 
            findNodeByRange(key0,key1,node.left);
            node.printThisNode();
        }
  
        if( compareflag0 <= 0 && compareflag1 >= 0 ) {
            addNodeToBuf(node);
        }
  
        if( compareflag1 > 0 && node.right != null) {
            findNodeByRange(key0,key1,node.right);
            node.printThisNode();
        }
    }
    void printAllInBuf() {
        int i;
        printTxt("\n |Found:");
        for(i = 0; i < findrangebuf.size(); i++ ) {
            Node nd = (Node)findrangebuf.elementAt(i);
            nd.printThisNode();
        }
        printTxt("\n");
    }
    void addNodeToBuf(Node node) {
        findrangebuf.add(node); /* ignore warning if no use of java-generics */
    }
    void initNodeBuf() {
        findrangebuf.clear();
    }
    
    Instruction read_instruction() {
        Instruction inst = new Instruction();
        
        try{
            BufferedReader br
                = new BufferedReader(new InputStreamReader(System.in));
            String buf;
            System.err.print("cmd:");
            buf = br.readLine();
            if( buf.equals("nop") ) {
                inst.cmd = NOP;
            } else if( buf.equals("halt") ) {
                inst.cmd = HALT;
            } else if( buf.equals("show") ) {
                inst.cmd = SHOW;
            } else if( buf.equals("ins") ) {
                inst.cmd = INS;
                System.err.print("key:");
                buf = br.readLine();
                inst.optkey = buf;
                System.err.print("data:");
                buf = br.readLine();
                inst.optdata = buf;
            } else if( buf.equals("del") ) {
                inst.cmd = DEL;
                System.err.print("key:");
                buf = br.readLine();
                inst.optkey = buf;
            } else if( buf.equals("delmin") ) {
                inst.cmd = DMIN;
            } else if( buf.equals("delmax") ) {
                inst.cmd = DMAX;
            } else if( buf.equals("find") ) {
                inst.cmd = FIND;
                System.err.print("key:");
                buf = br.readLine();
                inst.optkey = buf;
            } else if( buf.equals("findrange") ) {
                inst.cmd = FNDR;
                System.err.print("min:");
                buf = br.readLine();
                inst.optkey = buf;
                System.err.print("max:");
                buf = br.readLine();
                inst.optdata = buf;
            } else if( buf.equals("up") ) {
                inst.cmd = UP;
            } else if( buf.equals("parent") ) {
                inst.cmd = UP;
            } else if( buf.equals("l") ) {
                inst.cmd = L;
            } else if( buf.equals("left") ) {
                inst.cmd = L;
            } else if( buf.equals("r") ) {
                inst.cmd = R;
            } else if( buf.equals("right") ) {
                inst.cmd = R;
            } else if( buf.equals("root") ) {
                inst.cmd = ROOT;
            }
        } catch( Exception e) {
            System.err.println(e);
            inst.cmd = NOP;
        }
        return inst;
    }
} 