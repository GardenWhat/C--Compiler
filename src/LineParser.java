import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class LineParser {
    static private String token = "";
    static boolean parse(String line, boolean commentMode, PrintWriter tokenWriter) throws IOException {
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == '/') { //Checks for comments

                if (i != line.length() - 1 && line.charAt(i + 1) == '/' && !commentMode) { // case for "//"
                    break;
                } else if (i != line.length() - 1 && line.charAt((i + 1)) == '*' && !commentMode) { // case for "/*"
                    commentMode = true;
                } else if (i != 0 && line.charAt(i - 1) == '*' && commentMode) { // case for "*/"
                    commentMode = false;
                } else if (!commentMode) printTokenToFile(Character.toString(line.charAt(i)), tokenWriter); // if its not a comment

            }//End of checking for comments
            // Checks for symbols
            else if (!commentMode
                    && (line.charAt(i) == '*'
                    || line.charAt(i) == '+'
                    || line.charAt(i) == '-'
                    || line.charAt(i) == ';'
                    || line.charAt(i) == ','
                    || line.charAt(i) == '('
                    || line.charAt(i) == ')'
                    || line.charAt(i) == '['
                    || line.charAt(i) == ']'
                    || line.charAt(i) == '{'
                    || line.charAt(i) == '}')) {
                printTokenToFile(Character.toString(line.charAt(i)), tokenWriter);
            } else if (line.charAt(i) == '<' && !commentMode) {
                if (i != line.length() - 1 && line.charAt((i + 1)) == '=') {
                    printTokenToFile("<=", tokenWriter);
                    i++;
                } else printTokenToFile(Character.toString(line.charAt(i)), tokenWriter); // <
            } else if (line.charAt(i) == '>' && !commentMode) {
                if (i != line.length() - 1 && line.charAt((i + 1)) == '=') {
                    printTokenToFile(">=", tokenWriter);
                    i++;
                } else printTokenToFile(Character.toString(line.charAt(i)), tokenWriter); // >
            } else if (line.charAt(i) == '=' && !commentMode) {
                if (i != line.length() - 1 && line.charAt((i + 1)) == '=') {
                    printTokenToFile("==", tokenWriter);
                    i++;
                } else printTokenToFile(Character.toString(line.charAt(i)), tokenWriter); // =
            } else if (line.charAt(i) == '!' && !commentMode) {
                if (i != line.length() - 1 && line.charAt((i + 1)) == '=') {
                    printTokenToFile("!=", tokenWriter);
                    i++;
                } //else System.out.println("Error: " + line.charAt(i)); // !                   CHECK HERE
            }   //End of checking for symbols

            //Checks for keywords
            else if (line.charAt(i) == 'e' && !commentMode) {  //else
                if (i < line.length() - 3
                        && line.charAt(i + 1) == 'l'
                        && line.charAt(i + 2) == 's'
                        && line.charAt(i + 3) == 'e') {
                    i += 3;
                    i = printIdOrKeyword(i, 3, line, "else", tokenWriter);
                } else {
                    i = isID(i, line, tokenWriter);
                }
            } else if (line.charAt(i) == 'i' && !commentMode) {  //if
                if (i < line.length() - 1
                        && line.charAt(i + 1) == 'f') {
                    i++;
                    i = printIdOrKeyword(i, 1, line, "if", tokenWriter);
                } else if (i < line.length() - 2
                        && line.charAt(i + 1) == 'n'
                        && line.charAt(i + 2) == 't') {
                    i += 2;
                    i = printIdOrKeyword(i, 2, line, "int", tokenWriter);
                } else {
                    i = isID(i, line, tokenWriter);
                }
            } else if (line.charAt(i) == 'r' && !commentMode) {  //return
                if (i < line.length() - 5
                        && line.charAt(i + 1) == 'e'
                        && line.charAt(i + 2) == 't'
                        && line.charAt(i + 3) == 'u'
                        && line.charAt(i + 4) == 'r'
                        && line.charAt(i + 5) == 'n') {
                    i += 5;
                    i = printIdOrKeyword(i, 5, line, "return", tokenWriter);
                } else {
                    i = isID(i, line, tokenWriter);
                }
            } else if (line.charAt(i) == 'v' && !commentMode) {  //void
                if (i < line.length() - 3
                        && line.charAt(i + 1) == 'o'
                        && line.charAt(i + 2) == 'i'
                        && line.charAt(i + 3) == 'd') {
                    i += 3;
                    i = printIdOrKeyword(i, 3, line, "void", tokenWriter);
                } else {
                    i = isID(i, line, tokenWriter);
                }
            } else if (line.charAt(i) == 'w' && !commentMode) {  //while
                if (i < line.length() - 4
                        && line.charAt(i + 1) == 'h'
                        && line.charAt(i + 2) == 'i'
                        && line.charAt(i + 3) == 'l'
                        && line.charAt(i + 4) == 'e' ){
                    i += 4;
                    i = printIdOrKeyword(i, 4, line, "while", tokenWriter);
                } else {
                    i = isID(i, line, tokenWriter);
                }
            } // end of keywords
            else if (isLetter((Character.toString(line.charAt(i)))) && !commentMode) {
                i = isID(i, line, tokenWriter);
            } else if (isDigit((Character.toString(line.charAt(i)))) && !commentMode) {
                i = isINT(i, line, tokenWriter);

            } else if (!commentMode && isSymbolOrSpace(i, line) && !(Character.isLetter(line.charAt(i))) && !(Character.isDigit(line.charAt(i)))) { // CHECK HERE
                //System.out.print("Error: ");
                if (i == line.length() - 1 && line.length() == 1) System.out.println(line.charAt(i));
                else if (i < line.length() - 1) {
                    while (i < line.length() - 1 && isSymbolOrSpace(i, line)) {
                        //System.out.print(line.charAt(i));
                        i++;
                    }
                    if (i == line.length() - 1 && isSymbolOrSpace(i, line)) System.out.print(line.charAt(i));
                    i--;
                   // System.out.print("\n");
                }
            }
        } // end of for loop
        return commentMode;
    } // end of parse function

    private static boolean isLetter(String c) {
        return c.matches("[a-zA-Z]");
    }

    private static boolean isDigit(String c) {
        return c.matches("[0-9]");
    }

    private static int isID(int i, String line, PrintWriter tokenWriter) throws IOException{
        if (isLetter(Character.toString(line.charAt(i))) && i == line.length() - 1 && line.length() == 1)
            token = "ID";// + line.charAt(i);
        else if (isLetter((Character.toString(line.charAt(i)))) && i < line.length() - 1) {
            token = "ID";
            while (isLetter((Character.toString(line.charAt(i)))) && i < line.length() - 1) {
                //token += line.charAt(i);
                i++;
            }
            if (i == line.length() - 1 && isLetter(Character.toString(line.charAt(i)))) {
                //token += line.charAt(i);
            }
            i--;
            printTokenToFile(token, tokenWriter);
            token = "";
        }
        return i;
    }

    private static int isINT(int i, String line, PrintWriter tokenWriter) throws IOException{
        if (isDigit(Character.toString(line.charAt(i))) && i == line.length() - 1 && line.length() == 1)
            printTokenToFile("NUM", tokenWriter);// + line.charAt(i), tokenWriter);
        else if (isDigit((Character.toString(line.charAt(i)))) && i < line.length() - 1) {
            token = "NUM";
            while (isDigit((Character.toString(line.charAt(i)))) && i < line.length() - 1) {
                //token += line.charAt(i);
                i++;
            }
            if (i == line.length() - 1 && isDigit(Character.toString(line.charAt(i)))) {
                //token += line.charAt(i);
            }
            i--;
            printTokenToFile(token, tokenWriter);
            token = "";
        }
        return i;
    }

    private static boolean isSymbolOrSpace(int i, String line) {
        return (line.charAt(i) != ' '
                && line.charAt(i) != '*'
                && line.charAt(i) != '+'
                && line.charAt(i) != '-'
                && line.charAt(i) != ';'
                && line.charAt(i) != ','
                && line.charAt(i) != '('
                && line.charAt(i) != ')'
                && line.charAt(i) != '['
                && line.charAt(i) != ']'
                && line.charAt(i) != '{'
                && line.charAt(i) != '}'
                && line.charAt(i) != '/'
                && line.charAt(i) != '='
                && line.charAt(i) != '<'
                && line.charAt(i) != '>'
                && line.charAt(i) != '!');
    }

    private static int printIdOrKeyword(int i, int lengthOfKeyword, String line, String keyword, PrintWriter tokenWriter)throws IOException{
        if(i == line.length() - 1)
            printTokenToFile(keyword, tokenWriter);
        else if(i < line.length() - 1 && !isSymbolOrSpace(i + 1, line))
            printTokenToFile(keyword, tokenWriter);
        else {
            i = isID(i - lengthOfKeyword, line, tokenWriter);
        }
        return i;
    }

    private static void printTokenToFile(String token, PrintWriter tokenWriter) throws IOException{
    
        tokenWriter.println(token);
       
    }
}
