package com.cp.pblc;

/**
 * @description: 普通非终结符S，其属性nextlist指向下一地址
 * @author: LuBixing
 * @date: 2022/1/5 18:32
 */
public class StatementElem {
    private int nextlist;

    public StatementElem(){
        nextlist = 0;
    }

    public StatementElem(int nextlist){
        this.nextlist = nextlist;
    }

    public int getNextlist(){
        return nextlist;
    }

    public void setNextlist(int nextlist){
        this.nextlist = nextlist;
    }
}
