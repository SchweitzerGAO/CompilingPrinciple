package com.cp.controlstat;

import com.cp.pblc.QuadList;
import com.cp.pblc.SymbolListElem;

/**
 * @description:
 * @author: LuBixing
 * @date: 2022/1/2 15:23
 */
public class ControlStatement {
    private QuadList quadList;

    public ControlStatement(){
        quadList = new QuadList();
    }

    public QuadList getQuadList() {
        return quadList;
    }

    public void setQuadList(QuadList quadList) {
        this.quadList = quadList;
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
