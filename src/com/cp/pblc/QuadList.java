package com.cp.pblc;

public class QuadList {
    private QuadListNode head;
    private int nextQuad;

    public QuadList()
    {
        this.head = null;
        this.nextQuad = 100;
    }

    public QuadList(QuadListNode head) {
        this.head = head;
        nextQuad = 100;
    }

    public QuadListNode getHead() {
        return head;
    }

    public void setHead(QuadListNode head) {
        this.head = head;
    }
    public int getNextQuad() {
        return nextQuad;
    }

    public void setNextQuad(int nextQuad) {
        this.nextQuad = nextQuad;
    }


    public QuadList merge(QuadList newList)
    {
        QuadListNode temp = head;
        if(temp == null)
        {
            return null;
        }
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
        while(temp != null)
        {
            temp.getData().setNextHop(t);
            temp = temp.getNext();
        }
    }

    public void emit(String op,String arg1,String arg2,int nextHop)
    {
        if(head == null)
        {
            head = new QuadListNode(new Quad(nextQuad,op,arg1,arg2,nextHop),null);
            nextQuad++;
            return;
        }
        QuadListNode temp = head;
        while(temp.getNext() != null)
        {
            temp = temp.getNext();
        }
        temp.setNext(new QuadListNode(new Quad(nextQuad,op,arg1,arg2,nextHop),null));
        nextQuad++;
    }

}
