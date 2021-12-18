package com.cp.pblc;

public class QuadListNode {
    private Quad data;
    private QuadListNode next;

    public QuadListNode() {
    }

    public QuadListNode(Quad data, QuadListNode next) {
        this.data = data;
        this.next = next;
    }

    public Quad getData() {
        return data;
    }

    public void setData(Quad data) {
        this.data = data;
    }
    public QuadListNode getNext() {
        return next;
    }

    public void setNext(QuadListNode next) {
        this.next = next;
    }
}
