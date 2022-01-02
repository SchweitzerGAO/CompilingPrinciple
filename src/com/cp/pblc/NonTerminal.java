package com.cp.pblc;

public class NonTerminal {
    private int truelist;
    private int falselist;

    public NonTerminal(){ }

    public NonTerminal(int truelist,int falselist){
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
