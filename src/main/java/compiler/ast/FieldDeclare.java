package compiler.ast;

import compiler.enums.Tag;

/**
 * 类字段声明
 */
public class FieldDeclare extends Node {
    /**
     * Tag 访问权限
     */
    private final Tag access;
    /**
     * 类型声明
     */
    private final Expr typeExpr;
    /**
     * 变量名
     */
    private final String varName;
    /**
     * 如果声明带有初始值
     */
    private final Expr initValue;

    public FieldDeclare(Tag access, Expr typeExpr, String varName, Expr initValue) {
        this.access = access;
        this.typeExpr = typeExpr;
        this.varName = varName;
        this.initValue = initValue;
    }
}
