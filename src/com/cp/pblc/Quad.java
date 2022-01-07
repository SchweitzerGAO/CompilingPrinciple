package com.cp.pblc;  // public classes: put all public used classes here


public class Quad {
    private int address;  // address starting from 100
    private String op;    // operator
    private String arg1;  // argument 1
    private String arg2;  // argument 2
    private int nextHop;  // the next quad to jump

    //nexthop不仅指下一步跳到哪条,也指merge的时候合并的下一串,但merge的不一定被回填了
    private Boolean finished;  //在回填的时候,改变这个节点的bool值

    public Quad() {
    }

    public Quad(int address, String op, String arg1, String arg2, int nextHop) {
        this.address = address;
        this.op = op;
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.nextHop = nextHop;
        this.finished=false;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getArg1() {
        return arg1;
    }

    public void setArg1(String arg1) {
        this.arg1 = arg1;
    }

    public String getArg2() {
        return arg2;
    }

    public void setArg2(String arg2) {
        this.arg2 = arg2;
    }

    public int getNextHop() {
        return nextHop;
    }

    public void setNextHop(int nextHop) {
        this.nextHop = nextHop;
    }

    public boolean getFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public void print()
    {
        System.out.println(address+": ("+op+", "+arg1+", "+arg2+", "+ (finished? nextHop: "0") + ')');
    }

    public void printGoto()
    {
        System.out.println(address+": ("+op+", "+arg1+", "+arg2+", "+ nextHop + ')');
    }


}
