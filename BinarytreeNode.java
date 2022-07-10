/*
 * 二分木をなぞるプログラム
 */

class BinarytreeNode {
    String label;
    BinarytreeNode left;
    BinarytreeNode right;
    

    BinarytreeNode(String label, BinarytreeNode left, BinarytreeNode right){
        this.label = label;
        this.left = left;
        this.right = right;
    }

    /*
     * 行きがけ順でなぞる
     */
    
    static void traversePreorder(BinarytreeNode p){
        if (p == null){
            return;
        }
        System.out.println(p.label + "に立ち寄りました");
        traversePreorder(p.left);
        traversePreorder(p.right);
    }

    /*
     * 通りがけでなぞる
     */
    
     static void traverseInorder(BinarytreeNode p){
         if(p == null){
             return;
         }
         traverseInorder(p.left);
         System.out.println(p.label + "に立ち寄りました");
         traverseInorder(p.right);
     }
     
     /*
      * 帰りがけ順でなぞる
      */
      
      static void traversePostorder(BinarytreeNode p ){
        if(p == null){
            return;
        }
        traversePostorder(p.left);
        traversePostorder(p.right);
        System.out.println(p.label + "に立ち寄りました");
}

public static void main(String[] args) {
    BinarytreeNode tree = new BinarytreeNode("a", new BinarytreeNode("b", new BinarytreeNode("c", null, null), null), new BinarytreeNode("d", null, null));

    System.out.println("行きがけ順");
    traversePreorder(tree);
    System.out.println("通りがけ順");
    traverseInorder(tree);
    System.out.println("帰りがけ順");
    traversePostorder(tree);
}

}
