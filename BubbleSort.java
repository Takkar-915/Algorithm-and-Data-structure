public class BubbleSort {
    
    public static void sort(int[] a){
        int n = a.length;

        for (int i = 0; i < n - 1;i++){
            for (int j = n - 1;j > i;j--){
                if (a[j-1] > a[j]){
                    int buf = a[j-1];
                    a[j-1] = a[j];
                    a[j] = buf;
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] num  = new int[4];
        
        num[0]=  11;
        num[1] = 4;
        num[2] = 5;
        num[3] = 14;


        sort(num);
        for (int i= 0; i < num.length; i++){
            System.out.println(num[i]);
        }
        
    }
}
