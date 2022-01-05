package com.cp.gotostat;

import com.cp.pblc.QuadList;
import com.cp.pblc.QuadListNode;
import com.cp.pblc.SymbolListElem;

import java.util.ArrayList;
import java.util.List;

public class GotoStatement {
    private List<SymbolListElem> symbolList;
    private QuadList quadList;
    private boolean hasError = false;

    public GotoStatement() {
        symbolList = new ArrayList<>();
        quadList = new QuadList();
    }

    public List<SymbolListElem> getSymbolList() {
        return symbolList;
    }

    public void setSymbolList(List<SymbolListElem> symbolList) {
        this.symbolList = symbolList;
    }

    public QuadList getQuadList() {
        return quadList;
    }

    public void setQuadList(QuadList quadList) {
        this.quadList = quadList;
    }

    private SymbolListElem find(String label)
    {
        SymbolListElem elem = null;
        for (SymbolListElem element:symbolList)
        {
            if(element.getName().equals(label))
            {
                elem = element;
                break;
            }
        }
        return elem;
    }


    /**
     * 标号语句解析
     * @param label 标号
     */
    public void parseLabel(String label)
    {
        SymbolListElem elem = find(label);
        if(elem != null)  // 若标号存在
        {
            if(elem.isDefined() || !elem.getType().equals("label"))
            {
                hasError = true;
                return;
            }
            elem.setDefined(true);
            elem.setAddress(quadList.getNextQuad());
            quadList.backpatch(quadList.getNextQuad());
        }
        else  // 若不存在
        {
            symbolList.add(new SymbolListElem(label,"label",true,quadList.getNextQuad()));
        }
    }

    /**
     * goto语句解析
     * @param label goto的标号
     */
   public void parseGoto(String label)
    {
        SymbolListElem elem = find(label);
        if(elem != null)
        {
            if(elem.isDefined())
            {
                quadList.emit("j","-","-",elem.getAddress());
            }
            else
            {
                int q = elem.getAddress();
                elem.setAddress(quadList.getNextQuad());
                quadList.emit("j","-","-",q);
            }
        }
        else
        {
            symbolList.add(new SymbolListElem(label,"label",false,quadList.getNextQuad()));
            quadList.emit("j","-","-",0);
        }
    }

    public void parseTerminal()
    {
        this.quadList.emit("-","-","-",0);
    }

    public void printQuadList()
    {
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
