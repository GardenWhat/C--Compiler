import java.io.*;
import java.lang.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        Scanner inputFileScanner = new Scanner(new File(args[0]));
        
        File tokens = new File("tokens.txt");


        if (tokens.exists()) {
            tokens.delete();
        }
        tokens.createNewFile();
        
        FileWriter tokenwriter = new FileWriter(tokens);
        PrintWriter tokenWriter = new PrintWriter(tokenwriter);

        String lineInFile;
        Boolean commentMode = false;

        while(inputFileScanner.hasNext()){
            lineInFile = inputFileScanner.nextLine();
            if(lineInFile.trim().length() != 0){
            commentMode = LineParser.parse(lineInFile, commentMode, tokenWriter);
            }
        }
        tokenWriter.println("$");
        tokenwriter.close();
        tokenWriter.close();
        TopDownParser.start();
    }
}
