package com.cp.pblc;


import java.util.List;
import java.util.Map;

public class LRTable {
    Map<String, List<String>> action; // every key-value pair stores a column in the LR table
    Map<String, List<String>> Goto;

    public Map<String, List<String>> getAction() {
        return action;
    }

    public void setAction(Map<String, List<String>> action) {
        this.action = action;
    }

    public Map<String, List<String>> getGoto() {
        return Goto;
    }

    public void setGoto(Map<String, List<String>> aGoto) {
        Goto = aGoto;
    }
}
