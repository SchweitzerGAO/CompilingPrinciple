package com.cp.controlstat;

import com.cp.pblc.QuadList;
import com.cp.pblc.BooleanElem;
import com.cp.pblc.QuadListNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: LuBixing
 * @date: 2022/1/2 15:23
 */
public class ControlStatement {
    //布尔表达式E，里面有两个综合属性E.truelist和E.falselist
    private List<BooleanElem> booleanElemList;
    private QuadList quadList;
    private boolean hasError;

    public ControlStatement(){
        quadList = new QuadList();
        booleanElemList = new ArrayList<>();
        hasError = false;
    }
    public QuadList getQuadList() {
        return quadList;
    }
    public void setQuadList(QuadList quadList) {
        this.quadList = quadList;
    }
    public List<BooleanElem> getBooleanElemList(){
        return booleanElemList;
    }
    public void setBooleanElemList(List<BooleanElem> booleanElemList){
        this.booleanElemList = booleanElemList;
    }



    public void printQuadList() {
        if(hasError)
        {
            System.err.println("COMPILE ERROR");
            return;
        }
        QuadListNode temp = this.quadList.getHead();
        while(temp != null)
        {
            temp.getData().print();
            temp = temp.getNext();
        }
    }
}
