package pa5;

import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer;

class Node {
    String word;String Class;String meaning;
    Node left;Node right;Node parent;

    public Node(String word, String Class, String meaning) {
        this.word = word;
        this.Class = Class;
        this.meaning = meaning;
        left = null;right = null;
        parent = null;
    }
}
     class BinaryTree {
         Node root;
         int size = 0;
         public BinaryTree() {
             root = null;
         }
         public BinaryTree(Node root) {
             this.root = root;
         }
         public static boolean compareTo(String str1, String str2) {
             if (str1.compareTo(str2) > 0)  //str1이 str2보다 큰경우
                 return true;
             else
                 return false;    //str1이 str2보다 작은경우
         }
         public static Node treeSearch(Node x, String k) {
             if (x == null || k.equals(x.word))
                 return x;
             if (compareTo(x.word, k))     //찾을려는 k 왼쪽에있는경우
                 return treeSearch(x.left, k);
             else
                 return treeSearch(x.right, k);
         }
         public static Node treeMinimum(Node x) {
             while (x.left != null)
                 x = x.left;
             return x;
         }
         public static Node treeSuccessor(Node x) {
             Node y;
             if (x.right != null)
                 return treeMinimum(x.right);
             y = x.parent;
             while (y != null && x == y.right) {
                 x = y;
                 y = y.parent;
             }
             return y;
         }
         public static void treeInsert(BinaryTree t, Node z) {
             Node y = null;
             Node x;
             x = t.root;
             while (x != null) {
                 y = x;
                 if (compareTo(x.word, z.word))    //x.word is big z.word
                     x = x.left;
                 else
                     x = x.right;
             }
             z.parent = y;
             if (y == null)
                 t.root = z;
             else if (compareTo(y.word, z.word))
                 y.left = z;
             else
                 y.right = z;
         }
         public static void treeDelete(BinaryTree t, Node z) {
             Node y;
             Node x;
             if (z.left == null || z.right == null)
                 y = z;
             else
                 y = treeSuccessor(z);
             if (y.left != null)
                 x = y.left;
             else
                 x = y.right;
             if (x != null)
                 x.parent = y.parent;
             if (y.parent == null)
                 t.root = x;
             else if (y == y.parent.left)
                 y.parent.left = x;
             else
                 y.parent.right = x;
             if (y != z)
                 z.word = y.word;
         }
         public static void readFile(BinaryTree t) {
             try (BufferedReader br = new BufferedReader(new FileReader
                     ("C:\\Users\\hagji\\Desktop\\shuffled_dict.txt"))) {
                 String str;
                 while(true) {
                     str = br.readLine();
                     if(str == null)
                         break;
                     splitDataAdd(t,str);
                 }
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }
         public static void splitDataAdd(BinaryTree t,String data) {
             int index1 = data.indexOf("(");
             int index2 = data.indexOf(")");
             String word = data.substring(0,index1-1);  // 단어
             if(data.charAt(index1 + 1) == ')'){  //품사자리에 빈칸인 경우
                 if(data.length() - 1 == index2)
                    add(t,word, " "," "); //품사자리도 빈칸이고 뜻도 빈칸인경우
                 else { //품사자리만 빈칸인경우
                     String meaning = data.substring(index2 + 2,data.length() -1 );
                     add(t, word, " ", meaning);
                 }
             }
             else{
                 String Class = data.substring(index1 + 1, index2 - 1);  // 품사자리에 빈칸이 아닌경우
                 if(data.length() - 1 == index2) //품사자리에 빈칸이 아니고 뜻만 빈칸인경우
                     add(t, word, Class, " ");
                 else {  //빈칸이 없는경우
                     String meaning = data.substring(index2 + 2,data.length() -1 );
                     add(t, word, Class, meaning);
                 }
             }
         }
         public static void readCommand(BinaryTree t) {
             Scanner sc = new Scanner(System.in);
             while (true) {
                 String command = sc.nextLine();
                 StringTokenizer cmd = new StringTokenizer(command, " ");
                 command = cmd.nextToken();
                 if (command.equals("size")) {
                     System.out.println(getSize(t));
                 } else if (command.equals("find")) {
                     String word;
                     word = cmd.nextToken();
                     find(t, word); //찾고자 하는 단어 넘겨줌
                 } else if (command.equals("add")) {
                     System.out.print("word: ");String word = sc.nextLine();
                     System.out.print("class: ");String Class = sc.nextLine();
                     System.out.print("meaning: ");String meaning = sc.nextLine();
                     add(t,word,Class,meaning);
                 } else if (command.equals("delete")) {
                     String name;
                         name = cmd.nextToken();
                     delete(t, name);   //삭제하고자 하는 이름 넘겨줌
                 } else if (command.equals("exit")) {
                     break;
                 } else if (command.equals("deleteall")) {
                     String dName;
                     dName = cmd.nextToken();
                     deleteAll(t,dName);
                 }
             }
         }
         public static int getSize(BinaryTree t) {
             return t.size;
         }
         public static void find(BinaryTree t, String word) {
             Node x = treeSearch(t.root, word);  //찾고자 하는 단어 검색
             if(x == null)
                 System.out.println(word + " is No exist");
             else
                System.out.println("meaning: " + x.meaning);
         }
         public static void add(BinaryTree t,String word,String Class,String meaning) {
             Node newNode = new Node(word, Class, meaning);
             treeInsert(t, newNode); t.size++;
         }
         public static void delete(BinaryTree t, String name) {
             Node x = treeSearch(t.root, name);
             treeDelete(t, x); t.size--;
             if (x == null) System.out.println("Not found");  //if x is null no exist word
             else System.out.println("Delete successfully");
         }
         public static void deleteAll(BinaryTree t,String fileName) {
             int count = 0;
             try (BufferedReader br = new BufferedReader(new FileReader
                     ("C:\\Users\\hagji\\Desktop\\"+fileName))) {
                 String str;
                 while(true){
                     str = br.readLine();
                     if(str == null)
                         break;
                     int index1 = str.indexOf("(");
                     String name = str.substring(0,index1-1);
                     delete(t,name);count++;
                 }
             } catch (IOException e) {
                 e.printStackTrace();
             }
             System.out.println(count + " words were deleted successfully");
         }
         public static void main(String[] args) {
             BinaryTree tree = new BinaryTree();
             readFile(tree);
             readCommand(tree);
         }
     }
