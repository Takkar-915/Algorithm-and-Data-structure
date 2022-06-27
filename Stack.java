public class Stack {
    int[] data;
    int stackSize;
    int sp;

    static final int default_stack_size = 100;

    public Stack(){
        this.data = new int[default_stack_size];
        this.stackSize = default_stack_size;
        this.sp = 0;
    }

    public void push(int x){
        if(sp >= stackSize){
            System.out.println("stack overflow");
        }
        data[sp] = x;
        sp++;
        
    }

    public int pop(){
        if(sp <= 0){
            System.out.println("empty stack");
        }
        sp--;
        return data[sp];
    }

    public static void main(String[] args) {
        Stack stack = new Stack();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        System.out.println(stack.pop());
    }

    

}
