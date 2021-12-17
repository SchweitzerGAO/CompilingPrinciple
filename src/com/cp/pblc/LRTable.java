package com.cp.pblc;


import java.util.List;
import java.util.Map;

public class LRTable {
    // every key-value pair stores a column in the LR table as ZSY shows in txt files
    Map<String, List<Integer>> action;
    Map<String, List<Integer>> Goto;

    public Map<String, List<Integer>> getAction() {
        return action;
    }

    public void setAction(Map<String, List<Integer>> action) {
        this.action = action;
    }

    public Map<String, List<Integer>> getGoto() {
        return Goto;
    }

    public void setGoto(Map<String, List<Integer>> aGoto) {
        Goto = aGoto;
    }
}
