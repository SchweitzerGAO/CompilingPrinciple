package com.cp.pblc;

public class SymbolListElem {
    private String name;
    private String type;
    private boolean defined;
    private int address;

    public SymbolListElem(String name, String type, boolean defined, int address) {
        this.name = name;
        this.type = type;
        this.defined = defined;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isDefined() {
        return defined;
    }

    public void setDefined(boolean defined) {
        this.defined = defined;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }
}
