public class InsertionSort {
    

    public static void sort(int[] a){
        int n = a.length;   //配列の要素数

        for(int i = 1; i < n;i++){
            int j = i;
            while(j >= 1 && a[j-1] > a[j]){
                int buf = a[j];
                a[j] = a[j-1];
                a[j-1] = buf;
                j--;
            }
        }
    }
}
