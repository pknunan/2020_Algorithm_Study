package algorithm_2019;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Pa_5_1 {

    static class WordNode {
        String word;
        String mean;
        WordNode leftNode = null;
        WordNode rightNode = null;
        WordNode superNode;

        public WordNode(String word, String mean, WordNode superNode) {
            this.word = word;
            this.mean = mean;
            this.superNode = superNode;
        }

    }

    static class WordTree {
        WordNode root = null;
        int size = 0;

        WordNode find(String target) {
            WordNode curr = this.root;
            while(curr != null) {
                if(curr.word.compareTo(target)==0) {
                    return curr;
                } else if (curr.word.compareTo(target) < 0) {
                    curr = curr.rightNode;
                } else
                    curr = curr.leftNode;
            }
            return null;
        }

        public int getSize() {
            return size;
        }

        void add(String word, String mean) {
            WordNode curr = this.root;
            WordNode prev = null;

            while(curr!=null) {
                prev = curr;
                if(curr.word.compareTo(word) > 0)
                    curr = curr.leftNode;
                else
                    curr = curr.rightNode;
            }

            if(prev == null) {
                this.root = new WordNode(word, mean, null);
            } else if (prev.word.compareTo(word) > 0) {
                prev.leftNode = new WordNode(word, mean, prev);
            } else
                prev.rightNode = new WordNode(word, mean, prev);

            this.size++;
        }

        void delete(String target) {
            WordNode targetNode = find(target);
            WordNode deleteNode, subNode;
            if (targetNode.leftNode == null || targetNode.rightNode == null)  { // 1개 이하
                deleteNode = targetNode;
            } else {
                deleteNode = successor(targetNode); // 변경 해야 할 노드
            }
            if(deleteNode.leftNode != null) {
                subNode = deleteNode.leftNode;
            } else {
                subNode = deleteNode.rightNode;
            }
            if(subNode != null) {
                subNode.superNode = deleteNode.superNode;
            }
            if(deleteNode.superNode == null) {
                root = subNode;
            } else if (deleteNode == deleteNode.superNode.leftNode) {
                deleteNode.superNode.leftNode=subNode;
            } else {
                deleteNode.superNode.rightNode = subNode;
            }
            if(deleteNode != targetNode) {
                targetNode.word = deleteNode.word;
                targetNode.mean = deleteNode.mean;
            }
        }

        private WordNode successor(WordNode curr) {
            if(curr.rightNode != null) {
                WordNode tmp = curr.rightNode;
                WordNode tmp2 = curr;
                while(tmp != null) {
                    tmp2 = tmp;
                    tmp = tmp.leftNode;
                }
                return tmp2;
            }
            WordNode y = curr.superNode;
            while(y!=null && curr == y.rightNode) {
                curr = y;
                y = y.superNode;
            }
            return y;
        }

        void deleteAll(String fileName) throws NoSuchElementException, FileNotFoundException {
            String path = System.getProperty("user.dir");
            path += "\\algorithm\\src\\algorithm_2019\\resource\\";
            path += fileName;
            Scanner fileScanner = new Scanner(new File(path));
            int cnt = 0;
            String word;

            while(fileScanner.hasNext()) {
                word = fileScanner.next();
                delete(word);
                cnt++;
            }
            System.out.println(cnt + " words were deleted successfully.");
        }
    }

    public static void main(String[] args) {
        WordTree wordTree = new WordTree();
        try {
            initDictionary(wordTree);
        } catch (NoSuchElementException ignore) {
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
        }

        Scanner sc = new Scanner(System.in);
        String command, arg;

        while(true) {
            System.out.print("$ ");
            command = sc.next();
            if(command.equals("size")) {
                System.out.println(wordTree.size);
            } else if (command.equals("find")) {
                arg = sc.next();
                WordNode target = wordTree.find(arg);
                if (target != null) {
                    System.out.println(target.mean);
                } else
                    System.out.println("There's not exist such word.");
            } else if (command.equals("add")) {
                System.out.print("word: ");
                String word = sc.next();
                System.out.print("class: ");
                String mean = "(";
                sc.nextLine();
                mean = mean + sc.nextLine();
                if(mean.equals("("))
                    mean += ") ";
                else
                    mean += ".) ";
                System.out.print("meaning: ");
                mean += sc.nextLine();
                wordTree.add(word, mean);
            } else if (command.equals("delete")) {
                arg = sc.next();
                wordTree.delete(arg);
            } else if (command.equals("deleteall")) {
                arg = sc.next();
                try {
                    wordTree.deleteAll(arg);
                }catch (NoSuchElementException e1) {
                } catch (FileNotFoundException e2) {
                    e2.printStackTrace();
                }
            } else if (command.equals("exit"))
                break;
            else{
                System.out.println("Wrong Command.");
            }
        }
    }

    private static void initDictionary(WordTree wordTree) throws NoSuchElementException, FileNotFoundException {
        String path = System.getProperty("user.dir");
        path += "\\algorithm\\src\\algorithm_2019\\resource\\shuffled_dict.txt";
        Scanner fileScanner = new Scanner(new File(path));

        String word, mean;
        while(true) {
            word = fileScanner.next();
            mean = fileScanner.nextLine();
            wordTree.add(word,mean);
        }
    }

}
