import java.util.*;
class see1{
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        System.out.println("enter the string:");
        String so=sc.next();
        System.out.println("length" + so.length());
        char str[] = new char[so.length()];
        str = so.toCharArray();
       // System.out.println("input"+str[0]);
        int j = 0;
        char rev[] = new char[so.length()];
        for(int i = so.length()-1;i>=0;i--) {
        rev[j] = str[i];
        j++;
        }
    for(int i = 0;i<5;i++) {
          System.out.print(rev[i]);
    }

    }
}