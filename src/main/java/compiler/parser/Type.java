package compiler.parser;

import compiler.enums.Tag;

public class Type {
    /**
     * 类名
     */
    private String type;
    /**
     * 基本数据类型
     */
    private Tag tag;
    public Type(String type){
        this.type = type;
    }
    public Type(Tag tag){
        this.tag = tag;
    }
}
