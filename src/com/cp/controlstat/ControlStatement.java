package com.cp.controlstat;

import com.cp.pblc.QuadList;
import com.cp.pblc.BooleanElem;

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

    public ControlStatement(){
        quadList = new QuadList();
        booleanElemList = new ArrayList<>();
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
    /**
     * 控制语句解析
     */
    void parseControl()
    {
//        SymbolListElem elem = find(label);
//        if(elem != null)
//        {
//            if(elem.isDefined())
//            {
//                quadList.emit("j","-","-",elem.getAddress());
//            }
//            else
//            {
//                int q = elem.getAddress();
//                elem.setAddress(quadList.getNextQuad());
//                quadList.emit("j","-","-",q);
//            }
//        }
//        else
//        {
//            symbolList.add(new SymbolListElem(label,"label",true,quadList.getNextQuad()));
//            quadList.emit("j","-","-",0);
//        }
    }
}
