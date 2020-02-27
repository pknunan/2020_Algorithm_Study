package algorithm_2019;

import org.w3c.dom.Node;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Pa_6_1 {
    final static WordNode NIL = new WordNode(null, null, null);

    public static void main(String[] args) {
        RBTree wordTree = new RBTree();
        try {
            initDictionary(wordTree);
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
        }

        Scanner sc = new Scanner(System.in);
        String command, arg;

        label:
        while (true) {
            System.out.print("$ ");
            command = sc.next();
            switch (command) {
                case "size":
                    System.out.println(wordTree.size);
                    break;
                case "find":
                    arg = sc.next();
                    WordNode target = wordTree.find(arg);
                    if (target != NIL) {
                        System.out.println(target.mean);
                    } else
                        System.out.println("There's not exist such word.");
                    break;
                case "add":
                    System.out.print("word: ");
                    String word = sc.next();
                    System.out.print("class: ");
                    String mean = "(";
                    sc.nextLine();
                    mean = mean + sc.nextLine();
                    if (mean.equals("("))
                        mean += ") ";
                    else
                        mean += ".) ";
                    System.out.print("meaning: ");
                    mean += sc.nextLine();
                    wordTree.add(word, mean);
                    break;
                case "delete":
                    arg = sc.next();
                    wordTree.delete(arg);
                    break;
                case "deleteall":
                    arg = sc.next();
                    try {
                        wordTree.deleteAll(arg);
                    } catch (FileNotFoundException e2) {
                        e2.printStackTrace();
                    }
                    break;
                case "exit":
                    break label;
                default:
                    System.out.println("Wrong Command.");
                    break;
            }
        }
    }

    private static void initDictionary(RBTree wordTree) throws NoSuchElementException, FileNotFoundException {
        String path = System.getProperty("user.dir");
        path += "\\algorithm\\src\\algorithm_2019\\resource\\shuffled_dict.txt";
        Scanner fileScanner = new Scanner(new File(path));

        String word, mean;
        while (fileScanner.hasNext()) {
            word = fileScanner.next();
            mean = fileScanner.nextLine();
            wordTree.add(word, mean);
        }
    }

    static class WordNode {
        String word;
        String mean;
        WordNode leftNode = NIL;
        WordNode rightNode = NIL;
        WordNode superNode;
        String color = "black";

        public WordNode(String word, String mean, WordNode superNode) {
            this.word = word;
            this.mean = mean;
            this.superNode = superNode;
        }

    }

    static class RBTree {
        private WordNode root = NIL;
        private int size = 0;

        public int getSize() {
            return size;
        }

        public WordNode find(String target) {
            WordNode curr = this.root;
            while (curr != NIL) {
                if (curr.word.compareTo(target) == 0) {
                    return curr;
                } else if (curr.word.compareTo(target) < 0) {
                    curr = curr.rightNode;
                } else
                    curr = curr.leftNode;
            }
            return NIL;
        }

        public void add(String word, String mean) {
            WordNode prev = NIL;
            WordNode curr = this.root;

            while (curr != NIL) {
                prev = curr;
                if (curr.word.compareTo(word) > 0)
                    curr = curr.leftNode;
                else
                    curr = curr.rightNode;
            }

            WordNode z = new WordNode(word, mean, prev);
            if (prev == NIL) {
                this.root = z;
            } else if (prev.word.compareTo(word) > 0) {
                prev.leftNode = z;
            } else
                prev.rightNode = z;
            this.size++;

            z.color = "red";
            insertFixUp(z);
        }

        private void insertFixUp(WordNode z) {
            while(z.superNode != NIL && z.superNode.color.equals("red")) {
                //case 1,2,3: p[z] == p[p[z]].leftNode
                if(z.superNode == z.superNode.superNode.leftNode) {
                    WordNode y = z.superNode.superNode.rightNode; // y = uncle node
                    //case 1: z의 삼촌이 red
                    if(y != NIL && y.color.equals("red")) {
                        z.superNode.color = "black";
                        y.color = "black";
                        z.superNode.superNode.color = "red";
                        z = z.superNode.superNode;
                        //case 2, 3: z의 삼촌이 black
                    } else {
                        if(z == z.superNode.rightNode) {
                            z = z.superNode;
                            leftRotate(z);
                        }
                        z.superNode.color = "black";
                        z.superNode.superNode.color = "red";
                        rightRotate(z.superNode.superNode);
                    }
                    //case 4,5,6: p[z] == p[p[z]].rightNode
                } else {
                    WordNode y = z.superNode.superNode.leftNode; // y = uncle node
                    if(y != NIL && y.color.equals("red")) {
                        z.superNode.color = "black";
                        y.color = "black";
                        z.superNode.superNode.color = "red";
                    } else {
                        if(z == z.superNode.leftNode) {
                            z = z.superNode;
                            rightRotate(z);
                        }
                        z.superNode.color = "black";
                        z.superNode.superNode.color = "red";
                        leftRotate(z.superNode.superNode);
                    }
                }
            }
            root.color = "black";
        }

        public void delete(String target) {
            WordNode targetNode = find(target);
            WordNode deleteNode, subNode;
            if (targetNode.leftNode == NIL || targetNode.rightNode == NIL) { // 1개 이하
                deleteNode = targetNode;
            } else {
                deleteNode = successor(targetNode); // 변경 해야 할 노드
            }
            if (deleteNode.leftNode != NIL) {
                subNode = deleteNode.leftNode;
            } else {
                subNode = deleteNode.rightNode;
            }
            if (subNode != NIL) {
                subNode.superNode = deleteNode.superNode;
            }
            if (deleteNode.superNode == NIL) {
                root = subNode;
            } else if (deleteNode == deleteNode.superNode.leftNode) {
                deleteNode.superNode.leftNode = subNode;
            } else {
                deleteNode.superNode.rightNode = subNode;
            }
            if (deleteNode != targetNode) {
                targetNode.word = deleteNode.word;
                targetNode.mean = deleteNode.mean;
            }
            size--;
            if(deleteNode.color.equals("black")) {
                WordNode x = deleteNode.leftNode;
                if(x == NIL)
                    x = deleteNode.rightNode;
                deleteFixUp(x);
            }
        }

        private void deleteFixUp(WordNode x) {
            while(x != root && x.color.equals("black")) {
                if( x == x.superNode.leftNode) {
                    WordNode w = x.superNode.rightNode;
                    if(w.color.equals("red")) {
                        w.color = "black";
                        x.superNode.color = "red";
                        leftRotate(x.superNode);
                        w = x.superNode.rightNode;
                    }
                    if(w.leftNode.color.equals("black") && w.rightNode.color.equals("black")) {
                        w.color = "red";
                        x = x.superNode;
                    } else {
                        if (w.rightNode.color.equals("black")) {
                            w.leftNode.color = "black";
                            w.color = "red";
                            rightRotate(w);
                            w = x.superNode.rightNode;
                        }
                        w.color = x.superNode.color;
                        x.superNode.color = "black";
                        w.rightNode.color = "black";
                        leftRotate(x.superNode);
                        x = root;
                    }
                } else if (x == x.superNode.rightNode){
                    WordNode w = x.superNode.leftNode;
                    if(w.color.equals("red")) {
                        w.color = "black";
                        x.superNode.color = "red";
                        rightRotate(x.superNode);
                        w = x.superNode.leftNode;
                    }
                    if(w.rightNode.color.equals("black") && w.leftNode.color.equals("black")) {
                        w.color = "red";
                        x = x.superNode;
                    } else {
                        if (w.leftNode.color.equals("black")) {
                            w.rightNode.color = "black";
                            w.color = "red";
                            leftRotate(w);
                            w = x.superNode.leftNode;
                        }
                        w.color = x.superNode.color;
                        x.superNode.color = "black";
                        w.leftNode.color = "black";
                        rightRotate(x.superNode);
                        x = root;
                    }
                }
            }
            x.color = "black";
        }

        private WordNode successor(WordNode curr) {
            if (curr.rightNode != NIL) {
                WordNode tmp = curr.rightNode;
                WordNode tmp2 = curr;
                while (tmp != NIL) {
                    tmp2 = tmp;
                    tmp = tmp.leftNode;
                }
                return tmp2;
            }
            WordNode y = curr.superNode;
            while (y != NIL && curr == y.rightNode) {
                curr = y;
                y = y.superNode;
            }
            return y;
        }

        public void deleteAll(String fileName) throws FileNotFoundException {
            String path = System.getProperty("user.dir");
            path += "\\algorithm\\src\\algorithm_2019\\resource\\";
            path += fileName;
            Scanner fileScanner = new Scanner(new File(path));
            int cnt = 0;
            String word;

            while (fileScanner.hasNext()) {
                word = fileScanner.next();
                delete(word);
                cnt++;
            }
            System.out.println(cnt + " words were deleted successfully.");
        }

        private void leftRotate(WordNode x) {
            WordNode y = x.rightNode;
            if (y != NIL) {
                x.rightNode = y.leftNode;
                y.leftNode.superNode = x;
                y.superNode = x.superNode;
                if (x.superNode == NIL) { // x == root
                    root = y;
                } else if (x == x.superNode.leftNode) {
                    x.superNode.leftNode = y;
                } else {
                    x.superNode.rightNode = y;
                }
                y.leftNode = x;
                x.superNode = y;
            }
        }

        private void rightRotate(WordNode x) {
            WordNode y = x.leftNode;
            if (y != NIL) {
                x.leftNode = y.rightNode;
                y.rightNode.superNode = x;
                y.superNode = x.superNode;
                if (x.superNode == NIL) { // x == root
                    root = y;
                } else if (x == x.superNode.leftNode) {
                    x.superNode.leftNode = y;
                } else {
                    x.superNode.rightNode = y;
                }
                y.rightNode = x;
                x.superNode = y;
            }
        }
    }
}
