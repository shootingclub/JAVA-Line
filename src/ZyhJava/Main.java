package ZyhJava;


import java.io.File;

public class Main {

    public static void main(String[] args) {
	// write your code here
        String pathname = "/Users/mac/Desktop/";
        File file = new File(pathname+"网络知识学习总结.rtf");
        System.out.println(file.getName());
    }
}
