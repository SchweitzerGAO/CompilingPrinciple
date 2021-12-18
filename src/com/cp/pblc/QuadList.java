package com.cp.pblc;

public class QuadList {
    private QuadListNode head;
    public QuadList(){}

    public QuadList(QuadListNode head) {
        this.head = head;
    }

    public QuadListNode getHead() {
        return head;
    }

    public void setHead(QuadListNode head) {
        this.head = head;
    }

    public QuadList makeList()
    {
        head = new QuadListNode(new Quad(0,"-","-","-",0),null);
        return this;
    }

    public QuadList merge(QuadList newList)
    {
        QuadListNode temp = this.head;
        while(temp.getNext() != null)
        {
            temp = temp.getNext();
        }
        temp.setNext(newList.head);
        return this;
    }

    public void backpatch(int t)
    {
        QuadListNode temp = head;
        while(temp.getNext() != null)
        {
            temp.getData().setNextHop(t);
            temp = temp.getNext();
        }
    }

}
