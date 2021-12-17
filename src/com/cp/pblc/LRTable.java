package com.cp.pblc;


import java.util.Map;

public class LRTable {
    Map<String,Integer> action; // every key-value pair stores a column in the LR table
    Map<String, Integer> Goto;

    public Map<String, Integer> getAction() {
        return action;
    }

    public void setAction(Map<String, Integer> action) {
        this.action = action;
    }

    public Map<String, Integer> getGoto() {
        return Goto;
    }

    public void setGoto(Map<String, Integer> Goto) {
        this.Goto = Goto;
    }
}
