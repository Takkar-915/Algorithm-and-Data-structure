public class BinarySearch {
    
    static class Entry{
        int key;
        Object data;

        /*
         * コンストラクタの定義
         */


         private Entry(int key, Object data){
            this.key = key;
            this.data = data;
         } 
     }
         
    final static int max = 100;     //データの最大個数
    Entry[] table = new Entry[max]; //データを格納する配列
    int n = 0;                      //tableに登録されているデータの数

    public Object search(int key){
        int low = 0;
        int high = n-1;

        while(low <= high){
            int middle = (low + high) / 2;
            if(key == table[middle].key){
                return table[middle].data;
            }
            else if(key < table[middle].key){
                high = middle-1;
            }
            else if(key > table[middle].key){
                low = middle + 1;
            }
        }
        return null;
    }

    

    
    
}
