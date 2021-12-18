

package compiler.lexer;

import compiler.enums.Tag;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author
 * @date 2021/12/18
 **/

public class Lexer {
    /**
     * 代码行数
     */
    public static int line = 1;

    /**
     * 缓存不变的 Token
     */
    private static Map<String,Token> cacheToken = new HashMap<String, Token>(32);
    static {
        for(Tag tag : Tag.values()){
            if(tag.getStr() != null){
                cacheToken.put(tag.getStr(),new Token(tag));
            } else if(tag.getCh() != null){
                cacheToken.put(tag.getCh(),new Token(tag));
            }
        }
    }

    /**
     * 当前字符
     */
    char peek = ' ';
    /**
     * 字符流
     */
    private final Reader reader;
    public Lexer(){
        reader = new InputStreamReader(System.in);
    }
    public Lexer(InputStream inputStream){
        reader = new InputStreamReader(inputStream);
    }

    /**
     * 读取一个字符
     */
    private void readch() throws IOException {
        peek = (char) reader.read();
    }
    /**
     * 读取指定的字符
     */
    private boolean readch(char c) throws IOException {
        readch();
        if (peek != c) {
            return false;
        }
        peek = ' ';
        return true;
    }

    /**
     * 生成 Token
     */
    public Token scan() throws IOException{
        //处理换行
        for (; ; readch()) {
            if (peek == ' ' || peek == '\t') {
            } else if (peek == '\n') {
                line = line + 1;
            } else {
                break;
            }
        }
        //处理双符号运算符
        switch (peek) {
            case '&':
                if (readch(Tag.BITAND.ch)) {
                    return new Token(Tag.AND);
                } else {
                    return new Token(Tag.BITAND);
                }
            case '|':
                if (readch(Tag.BITOR.ch)) {
                    return new Token(Tag.OR);
                } else {
                    return new Token(Tag.BITOR);
                }
            case '=':
                if (readch(Tag.ASSIGN.ch)) {
                    return new Token(Tag.EQ);
                } else {
                    return new Token(Tag.ASSIGN);
                }
            case '!':
                if (readch(Tag.ASSIGN.ch)) {
                    return new Token(Tag.NE);
                } else {
                    return new Token(Tag.NOT);
                }
            case '<':
                readch();
                if(peek == Tag.ASSIGN.ch){
                    return new Token(Tag.LE);
                } else if(peek == Tag.LI.ch){
                    return new Token(Tag.LSHIFT);
                } else{
                    return new Token(Tag.LI);
                }
            case '>':
                readch();
                if(peek == Tag.ASSIGN.ch){
                    return new Token(Tag.GE);
                } else if(peek == Tag.GI.ch){
                    return new Token(Tag.RSHIFT);
                } else{
                    return new Token(Tag.GI);
                }
            case '+':
                if(readch(Tag.ADD.ch)){
                    return new Token(Tag.ADDADD);
                } else{
                    return new Token(Tag.ADD);
                }
            case '-':
                if(readch(Tag.SUB.ch)){
                    return new Token(Tag.SUBSUB);
                } else{
                    return new Token(Tag.SUB);
                }
            default:break;
        }
        if(cacheToken.containsKey(String.valueOf(peek))){
            char tempPeek = peek;
            readch();
            return cacheToken.get(String.valueOf(tempPeek));
        }
       //处理数字
        if (Character.isDigit(peek) || peek == Tag.POINT.ch) {
            int v = 0;
            if (peek != Tag.POINT.ch) {
                do {
                    v = 10 * v + Character.digit(peek, 10);
                    readch();
                } while (Character.isDigit(peek));
            }
            if (peek != Tag.POINT.ch) {
                return new Num(v);
            }
            float x = v, d = 10;
            for (; ; ) {
                readch();
                if (!Character.isDigit(peek)) break;
                x = x + Character.digit(peek, 10) / d;
                d = d * 10;
            }
            return new Real(x);
        }
        if (Character.isLetter(peek) || peek == Tag.UNDER_LINE.ch) {
            StringBuilder b = new StringBuilder();
            do {
                b.append(peek);
                readch();
            } while (Character.isLetterOrDigit(peek) || peek == Tag.UNDER_LINE.ch);
            String result = b.toString();
            if(cacheToken.containsKey(result)){
                return cacheToken.get(result);
            } else{
                return new Word(result);
            }
        }
        peek = ' ';
        return new Token(null);
    }

}
