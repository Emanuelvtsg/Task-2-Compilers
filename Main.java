package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Stack;

public class Main {

    public static void main(String[] args){

        StringBuilder sb = new StringBuilder();
        // caminho do artigo
        Path path = Paths.get("Calc1.stk");

        String line;
        Stack stack = new Stack();
        int firstNum;
        int secndNum;
        ArrayList<Token> tokenList = new ArrayList<>();
        Boolean error = false;

        try (BufferedReader br = Files.newBufferedReader(path)) {

            // lendo linha por linha do arquivo
            while ((line = br.readLine()) != null) {
                if (isInt(line)){
                    TokenType token = TokenType.NUM;
                    Token tokenNum = new Token( token, line);
                    tokenList.add(tokenNum); // adiciona o token do numero na lista de tokens
                }
                else {
                    // não é número

                    // se entrar nesse if é porque não é número nem operando, printa erro
                    if (!line.equals("*") && !line.equals("+") && !line.equals("-") && !line.equals("/")){
                        error = true;
                        System.out.println("Error: unexpected character: " + line);

                    }
                    else {

                        // verifica qual o operando para adicionar na lista de token
                        if (line.equals("*")){
                            TokenType token = TokenType.STAR;
                            Token tokenOp = new Token( token, line);
                            tokenList.add(tokenOp);
                        }
                        else if (line.equals("+")){
                            TokenType token = TokenType.PLUS;
                            Token tokenOp = new Token( token, line);
                            tokenList.add(tokenOp);
                        }
                        else if (line.equals("-")){
                            TokenType token = TokenType.MINUS;
                            Token tokenOp = new Token( token, line);
                            tokenList.add(tokenOp);
                        }
                        else if (line.equals("/")){
                            TokenType token = TokenType.SLASH;
                            Token tokenOp = new Token( token, line);
                            tokenList.add(tokenOp);
                        }
                    }

                }
            }

        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }

        // se ocorreu erro, no caso, leu um caractere inesperado, nao precisa ler a tokenList para realizar as operaçoes na pilha
        if (!error) {
            for (Token token : tokenList){
                if (token.type.equals(TokenType.NUM)){
                    // se for numero, apenas adiciona na pilha
                    stack.push(token.lexeme);
                }
                else {
                    // se não for número é operando, verifica qual é para fazer a operaçao
                    secndNum = Integer.parseInt((String) stack.pop()); // segundo operando
                    firstNum = Integer.parseInt((String) stack.pop()); // primeiro operando

                    // realizará a conta entre secndNum e firstNum de acordo com o sinal que leu
                    if (token.lexeme.equals("*")){
                        stack.push((firstNum*secndNum)+"");
                    }
                    else if (token.lexeme.equals("+")){
                        stack.push((firstNum+secndNum)+"");
                    }
                    else if (token.lexeme.equals("-")){
                        stack.push((firstNum-secndNum)+"");
                    }
                    else if (token.lexeme.equals("/")){
                        stack.push((firstNum/secndNum)+"");
                    }
                }
                System.out.println(token.toString());
            }


            // ao fim de tudo, o único elemento na pilha será o resultado final
            System.out.println(stack.pop());
        }
    }

    public static boolean isInt(String line){
        // utilizada para receber uma linha do arquivo e dizer se é um número ou não (após as devidas conversões)
        try{
            Integer.parseInt(line);
            return true;
        }
        catch (NumberFormatException ex){
            return false;
        }
    }

}