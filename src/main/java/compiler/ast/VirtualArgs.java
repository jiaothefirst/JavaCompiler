package compiler.ast;

import java.util.ArrayList;
import java.util.List;

/**
 * 函数形参列表
 */
public class VirtualArgs extends Node {
    private final List<VirtualArg> virtualArgs;

    public VirtualArgs(List<VirtualArg> virtualArgs) {
        this.virtualArgs = virtualArgs;
    }
    public VirtualArgs(){
        this.virtualArgs = new ArrayList<>();
    }

    public List<VirtualArg> getVirtualArgs() {
        return virtualArgs;
    }
}
