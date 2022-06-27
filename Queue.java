public class Queue {
    int[] data;
    int queueSize;
    int front;
    int rear;

    static final int default_queue_size = 100;

    public Queue(){
        this.data = new int[default_queue_size];
        this.queueSize = default_queue_size;
    }


    public int next(int a){
        return  (a + 1) % queueSize;

    }

    /*
     * キューにデータを入れる
     */

    public void enqueue(int x){
        if (next(rear) == front){
            System.out.println("これ以上はデータを入れられません。");
        }
        data[rear] = x;
        rear = next(rear);
    }

    /*
     * キューからデータを取り出す
     */

     public int dequeue(){
         if (front == rear){
             System.out.println("キュー内がからなので何も取り出せません。");
         }
         int x = data[front];
         data[front] = 0;
         front = next(front);
         return x;
        }
        /*
         * キューが空か確認している。これにより、待ち行列が空っぽじか満たんか区別できる。
         */
        public boolean isEmpty(){
            return front == rear;
        }


        public static void main(String[] args) {
            Queue queue = new Queue();
            queue.enqueue(1);
            queue.enqueue(2);
            queue.enqueue(3);

            System.out.println(queue.dequeue());
        }
     }
