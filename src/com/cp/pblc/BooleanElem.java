package com.cp.pblc;
/*
 * @description:非终结符E代表布尔语句，其综合属性E.truelist和E.falselist记录E所应的四元式中需回填真假出口的四元式标号所构成的链表
 * @author: LuBixing
 * @date: 2022/1/5 18:24
 */
public class BooleanElem {
    private int truelist;
    private int falselist;

    public BooleanElem(){ }

    public BooleanElem(int truelist, int falselist){
        this.truelist = truelist;
        this.falselist = falselist;
    }

    public int getTruelist(){
        return truelist;
    }
    public void setTruelist(int truelist){
        this.truelist = truelist;
    }

    public int getFalselist(){
        return falselist;
    }
    public void setFalselist(int falselist){
        this.falselist = falselist;
    }
}
