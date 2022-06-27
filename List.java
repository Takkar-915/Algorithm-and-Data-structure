/*
 * 整数が昇順に並ぶようにした連結リスト
 */
class Cell{
    Cell next;      //次のセルへのリンク
    Object data;    //セルが持つデータ

    Cell(Object data){
        next = null;
        this.data = data;
    }
}

public class List {
    
    Cell header;    //リストの先頭のリンク

    public List(){
        header = new Cell("List Head"); //リストの先頭を作成
    }

    /*
     * 挿入する位置を探索
     */
    public void insert(int num){
        Cell p = header.next;
        Cell q = header;
        while(p != null && num > (Integer)p.data){
            q = p;
            p = p.next;
        }
        
        Cell newCell = new Cell(num);
        newCell.next = p;
        q.next = newCell;
    }

    public String toString(){
        String s = "[";
        for (Cell p = header.next; p != null;p = p.next){
            s += p.data + "";
        } 
        s += "]";
        return s;
    }


public static void main(String[] args) {
    List list = new List();
    System.out.println(list);
    list.insert(2);
    System.out.println(list);
    list.insert(3);
    System.out.println(list);
    list.insert(8);
    System.out.println(list);

}

}


