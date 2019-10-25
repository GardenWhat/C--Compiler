import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class TopDownParser {

    private static String token;

    static void start() throws IOException {
        Scanner tokenScanner = new Scanner(new File("tokens.txt"));
        token = tokenScanner.nextLine();
        program(tokenScanner);
        if(token.equals('$')) {
            System.out.println("ACCEPT");
        }
        else {
            System.out.println("REJECT");
        }
        tokenScanner.close();
    }

    static void program(Scanner tokenScanner) {
        declaration_list(tokenScanner);
    }

    static void declaration_list(Scanner tokenScanner) {
        declaration(tokenScanner);
        DLPrime(tokenScanner);
    }

    static void DLPrime(Scanner tokenScanner) {
        if(token.equals("int")
        || token.equals("void")) {
            declaration(tokenScanner);
            DLPrime(tokenScanner);
        }
    }

    static void declaration(Scanner tokenScanner) {
        type_specifier(tokenScanner);
        if (token.equals("ID")) {
            token = nextToken(tokenScanner);
            DPrime(tokenScanner);
        } else {
            System.out.println("REJECT");
            System.exit(0);
        }

    }

    static void DPrime(Scanner tokenScanner) {
        if (token.equals("(")) {
            token = nextToken(tokenScanner);
            params(tokenScanner);
            if (token.equals(")")) {
                token = nextToken(tokenScanner);
                compound_stmt(tokenScanner);
            } else {
                System.out.println("REJECT");
                System.exit(0);
            }
        } else {
            VDPrime(tokenScanner);
        }
    }

    static void var_declaration(Scanner tokenScanner) {
        type_specifier(tokenScanner);
        if (token.equals("ID")) {
            token = nextToken(tokenScanner);
            VDPrime(tokenScanner);
        } else {
            System.out.println("REJECT");
            System.exit(0);
        }
    }

    static void VDPrime(Scanner tokenScanner) {
        if (token.equals(";")) {
            token = nextToken(tokenScanner);
        } else {
            if (token.equals("[")) {
                token = nextToken(tokenScanner);
                if (token.equals("NUM")) {
                    token = nextToken(tokenScanner);
                    if (token.equals("]")) {
                        token = nextToken(tokenScanner);
                        if (token.equals(";")) {
                            token = nextToken(tokenScanner);
                        } else {
                            System.out.println("REJECT");
                            System.exit(0);
                        }
                    } else {
                        System.out.println("REJECT");
                        System.exit(0);
                    }
                } else {
                    System.out.println("REJECT");
                    System.exit(0);
                }
            } else {
                System.out.println("REJECT");
                System.exit(0);
            }
        }
    }

    static void type_specifier(Scanner tokenScanner) {
        if (token.equals("int")) {
            token = nextToken(tokenScanner);
        }
        else if (token.equals("void")) {
            token = nextToken(tokenScanner);
        }
        else {
            System.out.println("REJECT");
            System.exit(0);
        }
    }

    static void params(Scanner tokenScanner) {
        if (token.equals("int")) {
            token = nextToken(tokenScanner);
            if (token.equals("ID")) {
                token = nextToken(tokenScanner);
                paramPrime(tokenScanner);
                param_listPrime(tokenScanner);
            } else {
                System.out.println("REJECT");
                System.exit(0);
            }
        } else if (token.equals("void")) {
            token = nextToken(tokenScanner);
            paramsPrime(tokenScanner);
        } else {
            System.out.println("REJECT");
            System.exit(0);
        }
    }

    static void paramsPrime(Scanner tokenScanner) {
        if (token.equals("ID")) {
            token = nextToken(tokenScanner);
            paramPrime(tokenScanner);
            param_listPrime(tokenScanner);
        }
    }

    static void param_listPrime(Scanner tokenScanner) {
        if (token.equals(",")) {
            token = nextToken(tokenScanner);
            type_specifier(tokenScanner);
            if (token.equals("ID")) {
                token = nextToken(tokenScanner);
                paramPrime(tokenScanner);
                param_listPrime(tokenScanner);
            } else {
                System.out.println("REJECT");
                System.exit(0);
            }
        }
    }

    static void paramPrime(Scanner tokenScanner) {
        if (token.equals("[")) {
            token = nextToken(tokenScanner);
            if (token.equals("]")) {
                token = nextToken(tokenScanner);
            } else{
                System.out.println("REJECT");
                System.exit(0);
            }
        }
    }

    static void compound_stmt(Scanner tokenScanner) {
        if (token.equals("{")) {
            token = nextToken(tokenScanner);
            local_declaration(tokenScanner);
            statement_list(tokenScanner);
            if (token.equals("}")) {
                token = nextToken(tokenScanner);
            } else {
                System.out.println("REJECT");
                System.exit(0);
            }
        } else {
            System.out.println("REJECT");
            System.exit(0);
        }
    }

    static void local_declaration(Scanner tokenScanner) {
        if(token.equals("int")
        || token.equals("void")) {
            var_declaration(tokenScanner);
            local_declaration(tokenScanner);
        }
    }

    static void statement_list(Scanner tokenScanner) {
        if(token.equals("{")
        || token.equals("if")
        || token.equals("while")
        || token.equals("return")
        || token.equals(";")
        || token.equals("ID")
        || token.equals("NUM")
        || token.equals("(")) {
            statement(tokenScanner);
            statement_list(tokenScanner);
        }
    }

    static void statement(Scanner tokenScanner) {
        if (token.equals("{")) {
            compound_stmt(tokenScanner);
        } else if (token.equals("if")) {
            selection_stmt(tokenScanner);
        } else if (token.equals("while")) {
            iteration_stmt(tokenScanner);
        } else if (token.equals("return")) {
            return_stmt(tokenScanner);
        } else if (token.equals(";")
                || token.equals("ID")
                || token.equals("NUM")
                || token.equals("(")){
            expression_stmt(tokenScanner);
        }
        else {
            System.out.println("REJECT");
            System.exit(0);
        }
    }

    static void expression_stmt(Scanner tokenScanner) {
        if (token.equals(";")) {
            token = nextToken(tokenScanner);
        }
        else if(token.equals("ID")
             || token.equals("NUM")
             || token.equals("(")){
            expression(tokenScanner);
            if (token.equals(";")) {
                token = nextToken(tokenScanner);
            } else {
                System.out.println("REJECT");
                System.exit(0);
            }
        }
        else {
            System.out.println("REJECT");
            System.exit(0);
        }
    }

    static void selection_stmt(Scanner tokenScanner) {
        if (token.equals("if")) {
            token = nextToken(tokenScanner);
            if (token.equals("(")) {
                token = nextToken(tokenScanner);
                expression(tokenScanner);
                if (token.equals(")")) {
                    token = nextToken(tokenScanner);
                    statement(tokenScanner);
                    SSPrime(tokenScanner);
                } else {
                    System.out.println("REJECT");
                    System.exit(0);
                }
            } else {
                System.out.println("REJECT");
                System.exit(0);
            }
        } else {
            System.out.println("REJECT");
            System.exit(0);
        }
    }

    static void SSPrime(Scanner tokenScanner) {
        if (token.equals("else")) {
            token = nextToken(tokenScanner);
            statement(tokenScanner);
        }
    }

    static void iteration_stmt(Scanner tokenScanner) {
        if (token.equals("while")) {
            token = nextToken(tokenScanner);
            if (token.equals("(")) {
                token = nextToken(tokenScanner);
                expression(tokenScanner);
                if (token.equals(")")) {
                    token = nextToken(tokenScanner);
                    statement(tokenScanner);
                } else {
                    System.out.println("REJECT");
                    System.exit(0);
                }
            } else {
                System.out.println("REJECT");
                System.exit(0);
            }
        } else {
            System.out.println("REJECT");
            System.exit(0);
        }
    }

    static void return_stmt(Scanner tokenScanner) {
        if (token.equals("return")) {
            token = nextToken(tokenScanner);
            RSPrime(tokenScanner);
        } else {
            System.out.println("REJECT");
            System.exit(0);
        }
    }

    static void RSPrime(Scanner tokenScanner) {
        if (token.equals(";")) {
            token = nextToken(tokenScanner);
        } else if(token.equals("ID")
                || token.equals("NUM")
                || token.equals("(")){
            expression(tokenScanner);
            if (token.equals(";")) {
                token = nextToken(tokenScanner);
            } else {
                System.out.println("REJECT");
                System.exit(0);
            }
        } else {
            System.out.println("REJECT");
            System.exit(0);
        }
    }

    static void expression(Scanner tokenScanner) {
        if (token.equals("ID")) {
            token = nextToken(tokenScanner);
            EP(tokenScanner);
        } else if (token.equals("NUM")) {
            token = nextToken(tokenScanner);
            TPrime(tokenScanner);
            AEPrime(tokenScanner);
            SEPrime(tokenScanner);
        } else if (token.equals("(")) {
            token = nextToken(tokenScanner);
            expression(tokenScanner);
            if (token.equals(")")) {
                token = nextToken(tokenScanner);
                TPrime(tokenScanner);
                AEPrime(tokenScanner);
                SEPrime(tokenScanner);
            } else {
                System.out.println("REJECT");
                System.exit(0);
            }
        } else {
            System.out.println("REJECT");
            System.exit(0);
        }
    }

    static void EP(Scanner tokenScanner) {
        if (token.equals("=")) {
            token = nextToken(tokenScanner);
            expression(tokenScanner);
        } else if (token.equals("[")) {
            token = nextToken(tokenScanner);
            expression(tokenScanner);
            if (token.equals("]")) {
                token = nextToken(tokenScanner);
                EPP(tokenScanner);
            } else {
                System.out.println("REJECT");
                System.exit(0);
            }
        } else if (token.equals("(")) {
            token = nextToken(tokenScanner);
            args(tokenScanner);
            if (token.equals(")")) {
                token = nextToken(tokenScanner);
                TPrime(tokenScanner);
                AEPrime(tokenScanner);
                SEPrime(tokenScanner);
            } else {
                System.out.println("REJECT");
                System.exit(0);
            }
        }
        else {
            TPrime(tokenScanner);
            AEPrime(tokenScanner);
            SEPrime(tokenScanner);
        }
    }

    static void EPP(Scanner tokenScanner) {
        if (token.equals("=")) {
            token = nextToken(tokenScanner);
            expression(tokenScanner);
        } else {
            TPrime(tokenScanner);
            AEPrime(tokenScanner);
            SEPrime(tokenScanner);
        }
    }

    static void varPrime(Scanner tokenScanner) {
        if (token.equals("[")) {
            token = nextToken(tokenScanner);
            expression(tokenScanner);
            if (token.equals("]")) {
                token = nextToken(tokenScanner);
            } else {
                System.out.println("REJECT");
                System.exit(0);
            }
        }
    }

    static void SEPrime(Scanner tokenScanner) {
        if(token.equals("<=")
        || token.equals("<")
        || token.equals(">")
        || token.equals(">=")
        || token.equals("==")
        || token.equals("!=")) {
            relop(tokenScanner);
            additive_expression(tokenScanner);
        }
    }

    static void relop(Scanner tokenScanner) {
        if (token.equals("<=")) {
            token = nextToken(tokenScanner);
        } else if (token.equals("<")) {
            token = nextToken(tokenScanner);
        } else if (token.equals(">")) {
            token = nextToken(tokenScanner);
        } else if (token.equals(">=")) {
            token = nextToken(tokenScanner);
        } else if (token.equals("==")) {
            token = nextToken(tokenScanner);
        } else if (token.equals("!=")) {
            token = nextToken(tokenScanner);
        } else {
            System.out.println("REJECT");
            System.exit(0);
        }
    }

    static void additive_expression(Scanner tokenScanner) {
        term(tokenScanner);
        AEPrime(tokenScanner);
    }

    static void AEPrime(Scanner tokenScanner) {
        if(token.equals("+")
        || token.equals("-")) {
            addop(tokenScanner);
            term(tokenScanner);
            AEPrime(tokenScanner);
        }
    }

    static void addop(Scanner tokenScanner) {
        if (token.equals("+")) {
            token = nextToken(tokenScanner);
        } else if (token.equals("-")) {
            token = nextToken(tokenScanner);
        } else {
            System.out.println("REJECT");
            System.exit(0);
        }
    }

    static void term(Scanner tokenScanner) {
        factor(tokenScanner);
        TPrime(tokenScanner);
    }

    static void TPrime(Scanner tokenScanner) {
        if(token.equals("*")
        || token.equals("/")) {
            mulop(tokenScanner);
            factor(tokenScanner);
            TPrime(tokenScanner);
        }
    }

    static void mulop(Scanner tokenScanner) {
        if (token.equals("*")) {
            token = nextToken(tokenScanner);
        } else if (token.equals("/")) {
            token = nextToken(tokenScanner);
        } else {
            System.out.println("REJECT");
            System.exit(0);
        }
    }

    static void factor(Scanner tokenScanner) {
        if (token.equals("(")) {
            token = nextToken(tokenScanner);
            expression(tokenScanner);
            if (token.equals(")")) {
                token = nextToken(tokenScanner);
            } else {
                System.out.println("REJECT");
                System.exit(0);
            }
        } else if (token.equals("ID")) {
            token = nextToken(tokenScanner);
            FPrime(tokenScanner);
        } else if (token.equals("NUM")) {
            token = nextToken(tokenScanner);
        }
    }

    static void FPrime(Scanner tokenScanner) {
        if (token.equals("[")) {
            token = nextToken(tokenScanner);
            expression(tokenScanner);
            if (token.equals("]")) {
                token = nextToken(tokenScanner);
            } else {
                System.out.println("REJECT");
                System.exit(0);
            }
        } else if (token.equals("(")) {
            token = nextToken(tokenScanner);
            args(tokenScanner);
            if (token.equals(")")) {
                token = nextToken(tokenScanner);
            } else{
                System.out.println("REJECT");
                System.exit(0);
            }
        }
    }

    static void args(Scanner tokenScanner) {
        if(token.equals("ID")
            || token.equals("NUM")
            || token.equals("("))
            arg_list(tokenScanner);
    }

    static void arg_list(Scanner tokenScanner) {
        expression(tokenScanner);
        ALPrime(tokenScanner);
    }

    static void ALPrime(Scanner tokenScanner) {
        if (token.equals(",")) {
            token = nextToken(tokenScanner);
            expression(tokenScanner);
            ALPrime(tokenScanner);
        }
    }

    static String nextToken(Scanner scanner) {
        String temp = "";
        if (scanner.hasNextLine()) {
            temp = scanner.nextLine();
            return temp;
        }
        else{
            System.out.println("REJECT");
            System.exit(0);
        }
        return temp;
    }
}
	
