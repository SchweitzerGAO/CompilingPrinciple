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

    public void backpatch(int p,int t) {
        //从头开始，先查找到地址为p的节点
        QuadListNode temp = head;
        while(temp != null) {
            if(temp.getData().getAddress() == p){
                break;
            }
            temp = temp.getNext();
        }
        if(temp == null){
            System.out.println("backpatch出错，不存在地址为"+p+"的四元式");
        }else{
            //0表示回填结束了
            int next;
            while(true){
                //先记录原本该回填的下一条
                next = temp.getData().getNextHop();
                //0表示回填结束了
                if(next==0){
                    break;
                }
                temp.getData().setNextHop(t);
                //跳过与回填的下一条不符的四元式
                while(temp.getData().getAddress()!=next){
                    temp = temp.getNext();
                }
            }
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
