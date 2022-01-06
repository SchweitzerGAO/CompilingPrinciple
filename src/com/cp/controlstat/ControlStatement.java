package com.cp.controlstat;

import com.cp.pblc.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: LuBixing
 * @date: 2022/1/2 15:23
 */
public class ControlStatement {
    //布尔表达式E，里面有两个综合属性E.truelist和E.falselist
    private BooleanElem E;      //布尔表达式
    private List<StatementElem> statementElemList;  //语句S
    private StatementElem N;    //N
    private AddressElem[] M;    //M
    private QuadList quadList;  //四元式

    public ControlStatement(){
        quadList = new QuadList();
        E = new BooleanElem(0,0);
        statementElemList = new ArrayList<>();
        M = new AddressElem[2];
    }
    public QuadList getQuadList() {
        return quadList;
    }
    public void setQuadList(QuadList quadList) {
        this.quadList = quadList;
    }
    public BooleanElem getE(){
        return E;
    }
    public void setE(BooleanElem E){
        this.E = E;
    }
    public List<StatementElem> getStatementElemList(){
        return statementElemList;
    }
    public void setStatementElemList(List<StatementElem> statementElemList){
        this.statementElemList = statementElemList;
    }

    //S#ietMS
    public void parseIf(){
        //quadList.backpatch(E.truelist,M.quad);
        ////S.nextlist = merge(E.falselist,S1.nextlist)
    }
    //S#ietMSNsMS
    public void parseIfElse(){
        //quadList.backpatch();
        //quadList.backpatch(E.truelist,M1.quad);
        //quadList.backpatch(E.falselist,M2.quad);
        //S.nextlist = merge(S1.nextlist,N.nextlist,S2.nextlist);
    }
    //E#e
    public void parseE(){
        //truelist，等待回填
        quadList.emit("jnz","e","-",0);
        //falselist,等待回填
        quadList.emit("j","-","-",0);
    }
    //M#m
    public void parseM(int symbol){
        //symbol对应位置1和2，索引对应0和1
        M[symbol - 1] = new AddressElem(quadList.getNextQuad());
    }
    //N#n
    public void parseN(){
        N = new StatementElem(quadList.getNextQuad());
        quadList.emit("j","-","-",0);
    }
    //S->a
    public void parseTerminal(){
        quadList.emit("-","-","-",0);
    }

    public void printQuadList() {
        QuadListNode temp = this.quadList.getHead();
        while(temp != null)
        {
            temp.getData().print();
            temp = temp.getNext();
        }
    }
}
