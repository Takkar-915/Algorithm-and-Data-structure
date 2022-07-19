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
    Node left;      //左の木の部分
    Node right;     //右の木の部分
    Node parent;    //親
    KeyAndData keyAndData;
 
    /*
     * コンストラクタの定義
     * keyとdataを引数にノードを初期化
     */
    public Node(String key, String data) {
        this.keyAndData = new KeyAndData(key,data);
        this.left = null;
        this.right = null;
        this.parent = null;
    }

    /*
     * クラスメソッドで定義。これ以下のprint〜のメソッドで呼び出される。
     */
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
                    key_i = keyAndData.key.substring(0,10);     //substring(0,10)で1〜10までのkeyを抜き出す
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
}
 
class adb5_0 {
    static final int NOP = 0;
    static final int HALT = 1;
    static final int SHOW = 2;
    static final int INS = 3;
    static final int DEL = 4;
    static final int FIND = 5;
    
    Node rootnode = null;
    
    public static void main(String args[]) {
        (new adb5_0()).run_interpreter(); /* don't forget to change here! */
    }
    
    public static void printTxt(String s) {
        System.out.print(s);
    }
    
    void run_interpreter() {
        Node nd;
        Instruction inst;
        
        do{
            inst = read_instruction();  //キーボードからコマンドの入力まち。下のread_instruction()メソッドへ
            switch(inst.cmd) {
            case NOP:
                break;
            case SHOW:
                if(rootnode!=null) {rootnode.printTree(); } printTxt("\n");
                break;
            case INS:
                insertNode(new Node(inst.optkey,inst.optdata),rootnode);
                break;
            case DEL:
                nd = deleteNodeByKey(inst.optkey,rootnode);
                if( nd != null ) {
                    printTxt(" |Node:");
                    nd.printThisNode(); printTxt(" deleted.\n");
                } else {
                    printTxt(" |Key not found.\n");
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
    
    /*
     * 処理内容の記述
     */
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
            } else if( buf.equals("find") ) {
                inst.cmd = FIND;
                System.err.print("key:");
                buf = br.readLine();
                inst.optkey = buf;
            }
        } catch( Exception e) {
            System.err.println(e);
            inst.cmd = NOP;
        }
        return inst;
    }
}