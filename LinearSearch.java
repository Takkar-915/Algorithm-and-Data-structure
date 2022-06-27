public class LinearSearch{
    /*
     * データの登録
     */
    
    static private class Entry{
        int key;
        Object data;

    /*
    *コンストラクタの定義 
    */

    private Entry(int key, Object data){
        this.key = key;
        this.data =data;
    }
}

    final static int max = 100;         //データの最大個数
    Entry [] table = new Entry[max];    //データを格納する配列
    int n;                              //テーブルに登録されているデータの個数

    
    public void add(int key, Object data){
        if (n >= max){
            System.out.println("データの個数オーバーです");
        }
        table[n++] = new Entry(key, data);
    }

    public Object Seach(int key){
        int i = 0;
        while(i < n){
            if (table[i].key == key){
                return(table[i].data);
            }
            i++;
        }
        return null;        
    }

    public static void main(String[] args) {
        LinearSearch mmjum = new LinearSearch();
        //LinearSearchのインスタンスであるmmjum1はデータを格納する配列tableを100個有している

        mmjum.add(1,"a");  //配列table[1]
        mmjum.add(2,"i");      //配列table[2]
        mmjum.add(3, "u");
        mmjum.add(4, "e");

        String ans;
        ans = (String)mmjum.Seach(2);

        if (ans != null){
            System.out.println(ans);
        }else{
            System.out.println("みつかりません…");
        }

    }
}