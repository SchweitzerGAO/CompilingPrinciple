package com.cp.booleanstat;

import com.cp.pblc.BooleanElem;
import com.cp.pblc.QuadList;
import com.cp.pblc.QuadListNode;

import java.util.*;

/**
 * @author : sxg
 * @description :
 * @date : 2022-01-05 17:36
 **/
public class BooleanStatement {
    private Stack<BooleanElem> symbolStack;
    private Stack<Integer> stateStack;
    private QuadList quadList;
    private List<String> productionTable;
    private Map<String,BooleanElem> variables;
    private Map<String, List<Integer>> actionMap;
    private Map<String, List<Integer>> gotoMap;
    private static final int OFFSET = 1;

    public BooleanStatement() {
        symbolStack = new Stack<>();
        stateStack = new Stack<>();
        quadList = new QuadList();
        variables = new LinkedHashMap<>();
        productionTable = new ArrayList<>();
        symbolStack.push(new BooleanElem("#"));
        stateStack.push(0);
        productionTable.add("E->E or E");
        productionTable.add("E->E and E");
        productionTable.add("E->not E");
        productionTable.add("E->(E)");
        productionTable.add("E->id relop id");
        productionTable.add("E->id");
    }

    public List<String> getProductionTable() {
        return productionTable;
    }

    public void setProductionTable(List<String> productionTable) {
        this.productionTable = productionTable;
    }

    public Map<String, List<Integer>> getActionMap() {
        return actionMap;
    }

    public void setActionMap(Map<String, List<Integer>> actionMap) {
        this.actionMap = actionMap;
    }

    public Map<String, List<Integer>> getGotoMap() {
        return gotoMap;
    }

    public void setGotoMap(Map<String, List<Integer>> gotoMap) {
        this.gotoMap = gotoMap;
    }

    public Stack<BooleanElem> getSymbolStack() {
        return symbolStack;
    }

    public void setSymbolStack(Stack<BooleanElem> symbolStack) {
        this.symbolStack = symbolStack;
    }

    public Stack<Integer> getStateStack() {
        return stateStack;
    }

    public void setStateStack(Stack<Integer> stateStack) {
        this.stateStack = stateStack;
    }

    public QuadList getQuadList() {
        return quadList;
    }

    public void setQuadList(QuadList quadList) {
        this.quadList = quadList;
    }

    public void init() {
        symbolStack = new Stack<>();
        stateStack = new Stack<>();
        symbolStack.push(new BooleanElem("#"));
        stateStack.push(0);
        quadList = new QuadList();
    }

    public String getType(String str) {
        if(this.actionMap.get(str) != null){
            return str;
        }
        else if("<".equals(str) || "<=".equals(str) || "==".equals(str) || ">=".equals(str) || ">".equals(str)) {
            return "relop";
        }
        return "id";
    }

    public String getLeft(String str){
        return str.split("->")[0];
    }

    public void generateQuadList(String str) {
        String[] input = str.split(" ");
        int pointer = 0;
        while (true){
            int nextState = this.actionMap.get(getType(input[pointer])).get(this.stateStack.peek());
//            System.out.println("?????????"+this.stateStack.peek() + "??????" +
//                    getType(input[pointer]) +"??????" + (nextState>0?"??????":"??????"));
            //?????????
            if(nextState == 0){
                System.out.println("error!");
                return;
            }
            //??????
            else if (nextState == 999) {
                System.out.println("success!");
                return;
            }
            //????????????
            else if (nextState > 0 && nextState < 999) {
                BooleanElem tmp = new BooleanElem(input[pointer]);
                this.symbolStack.push(tmp);
                this.stateStack.push(nextState);
                pointer++;
            }
            //????????????
            else{
                //????????????????????????
                String production = this.productionTable.get(Math.abs(nextState) - OFFSET);
//                System.out.println("??????????????????" + production);
                if ("E->E or E".equals(production) || "E->E and E".equals(production)) {
                    parseOrAnd(production);
                } else if ("E->not E".equals(production)) {
                    parseNot(production);
                } else if ("E->(E)".equals(production)) {
                    parseBrackets(production);
                } else if ("E->id relop id".equals(production)) {
                    parseRelop(production);
                } else if ("E->id".equals(production)) {
                    parseID(production);
                }
            }
//            printNow();
        }
    }

    private void printNow(){
        System.out.print("??????????????????");
        for (BooleanElem booleanElem : this.symbolStack) {
            System.out.print(booleanElem.getName() + " ");
        }
        System.out.print("\n??????????????????");
        for (Integer integer : this.stateStack) {
            System.out.print(integer + " ");
        }
        System.out.print("\n\n");
    }

    private void parseID(String production) {
        //E->id?????????1????????????1???
        BooleanElem id = this.symbolStack.peek();
        this.symbolStack.pop();
        this.stateStack.pop();

        BooleanElem E = new BooleanElem(getLeft(production));
        E.setQuad(quadList.getNextQuad());
        E.setTruelist(quadList.getNextQuad());
        E.setFalselist(quadList.getNextQuad()+1);
        quadList.emit("jnz",id.getName(),"-",0);
        quadList.emit("j","-","-",0);

        //????????????
        this.symbolStack.push(E);
        this.stateStack.push(gotoMap.get(getLeft(production)).get(this.stateStack.peek()));
    }

    private void parseRelop(String production) {
        //E->id relop id2?????????3????????????1???
        BooleanElem id1, relop, id2;
        id2 = this.symbolStack.peek();
        this.symbolStack.pop();
        this.stateStack.pop();
        relop = this.symbolStack.peek();
        this.symbolStack.pop();
        this.stateStack.pop();
        id1 = this.symbolStack.peek();
        this.symbolStack.pop();
        this.stateStack.pop();

        BooleanElem E = new BooleanElem(getLeft(production));
        E.setQuad(quadList.getNextQuad());
        E.setTruelist(quadList.getNextQuad());
        E.setFalselist(quadList.getNextQuad()+1);
        quadList.emit("j"+relop.getName(),id1.getName(),id2.getName(),0);
        quadList.emit("j","-","-",0);

        //????????????
        this.symbolStack.push(E);
        this.stateStack.push(gotoMap.get(getLeft(production)).get(this.stateStack.peek()));
    }

    private void parseBrackets(String production) {
        //E->(E)?????????3????????????1???
        BooleanElem E1;
        this.symbolStack.pop();
        this.stateStack.pop();
        E1 = this.symbolStack.peek();
        this.symbolStack.pop();
        this.stateStack.pop();
        this.symbolStack.pop();
        this.stateStack.pop();

        BooleanElem E = new BooleanElem(getLeft(production));
        E.setQuad(E1.getQuad());
        E.setTruelist(E1.getTruelist());
        E.setFalselist(E1.getFalselist());

        //????????????
        this.symbolStack.push(E);
        this.stateStack.push(gotoMap.get(getLeft(production)).get(this.stateStack.peek()));
    }

    private void parseNot(String production) {
        //E->not E?????????2????????????1???
        BooleanElem E1;
        E1 = this.symbolStack.peek();
        this.symbolStack.pop();
        this.stateStack.pop();
        this.symbolStack.pop();
        this.stateStack.pop();

        //??????
        BooleanElem E = new BooleanElem(getLeft(production));
        E.setQuad(E1.getQuad());
        E.setTruelist(E1.getFalselist());
        E.setFalselist(E1.getTruelist());

        //????????????
        this.symbolStack.push(E);
        this.stateStack.push(gotoMap.get(getLeft(production)).get(this.stateStack.peek()));
    }

    private void parseOrAnd(String production) {
        //E->E1 or E2 ?????????3????????????1???
        BooleanElem E1, E2;
        E2 = this.symbolStack.peek();
        this.symbolStack.pop();
        this.stateStack.pop();
        this.symbolStack.pop();
        this.stateStack.pop();
        E1 = this.symbolStack.peek();
        this.symbolStack.pop();
        this.stateStack.pop();

        BooleanElem E = new BooleanElem(getLeft(production));
        E.setQuad(E1.getQuad());

        if("E->E or E".equals(production)) {
            //E2????????????????????????E1???falseList
            quadList.backpatch(E1.getFalselist(), E2.getQuad());
            //E1???E2???trueList??????
            E.setTruelist(quadList.merge(E1.getTruelist(),E2.getTruelist()));
            E.setFalselist(E2.getFalselist());
        }
        else{
            //TODO ?????????E2?????????????????????
            quadList.backpatch(E1.getTruelist(), E2.getQuad());
            E.setFalselist(quadList.merge(E1.getFalselist(),E2.getFalselist()));
            E.setTruelist(E2.getTruelist());
        }

        //????????????
        this.symbolStack.push(E);
        this.stateStack.push(gotoMap.get(getLeft(production)).get(this.stateStack.peek()));
    }

    private BooleanElem newTemp() {
        variables.put("T"+variables.size(),new BooleanElem("T"+variables.size()));
        return variables.get("T"+(variables.size()-1));
    }

    //??????
    public void printQuadList() {
        QuadListNode temp = this.quadList.getHead();
        while(temp != null)
        {
            temp.getData().print();
            temp = temp.getNext();
        }
    }
}
