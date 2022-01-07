package com.cp.booleanstat;

import com.cp.pblc.BooleanElem;
import com.cp.pblc.QuadList;

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
    }

    public String getType(String str) {
        if(this.actionMap.get(str) != null){
            return str;
        }
        else if(this.variables.get(str) != null) {
            return this.variables.get(str).getType();
        }
        else if("<".equals(str) || "<=".equals(str) || "==".equals(str) || ">=".equals(str) || ">".equals(str)) {
            return "relop";
        }
        return "id";
    }

    public String getLeft(String str){
        return str.split("->")[0];
    }

    public String getRight(String str){
        return str.split("->")[1];
    }

    public void generateQuadList(String str) {
        String[] input = str.split(" ");
        int pointer = 0;
        while (true){
            System.out.println("第" + (pointer+1) +"个：" + input[pointer]);
            if (getType(input[pointer]) == null) {
                //报错
                System.out.println("error!");
                return;
            }
            else {
                int nextState = this.actionMap.get(getType(input[pointer])).get(this.stateStack.peek());
                System.out.println("由状态"+this.stateStack.peek() + "接收" + getType(input[pointer]) +"跳转到状态" +nextState);
                if (nextState == 999) {
                    //TODO 成了，咋办
                    System.out.println("success!");
                    return;
                }
                //移进项目
                else if (nextState > 0 && nextState < 999) {
                    BooleanElem tmp = new BooleanElem(input[pointer]);
                    tmp.setType(getType(input[pointer]));
                    this.symbolStack.push(tmp);
                    this.stateStack.push(nextState);
                    pointer++;
                    printNow();
                }
                //规约项目
                else if (nextState < 0) {
                    //规约使用的产生式
                    String production = this.productionTable.get(Math.abs(nextState) - OFFSET);
                    if ("E->E or E".equals(production) || "E->E and E".equals(production)) {
                        parseOrAnd(production);
                        printNow();
                    } else if ("E->not E".equals(production)) {
                        parseNot(production);
                        printNow();
                    } else if ("E->(E)".equals(production)) {
                        parseBrackets(production);
                        printNow();
                    } else if ("E->id relop id".equals(production)) {
                        parseRelop(production);
                        printNow();
                    } else if ("E->id".equals(production)) {
                        parseID(production);
                        printNow();
                    }
                }
                //action表项为0
                else{
                    System.out.println("error!");
                    return;
                }
            }
        }
    }

    private void printNow(){
        System.out.print("当前符号：");
        for (BooleanElem booleanElem : this.symbolStack) {
            System.out.print(booleanElem.getName() + " ");
        }
        System.out.print("\n当前状态：");
        for (Integer integer : this.stateStack) {
            System.out.print(integer + " ");
        }
        System.out.print("\n");
    }

    private void parseID(String production) {
        //E->id，弹出1个，压入1个
        BooleanElem id = this.symbolStack.peek();
        this.symbolStack.pop();
        this.stateStack.pop();

        BooleanElem E = new BooleanElem(getLeft(production));
        E.setType(getLeft(production));

        //压入新的
        this.symbolStack.push(new BooleanElem(getLeft(production)));
        this.stateStack.push(gotoMap.get(getLeft(production)).get(this.stateStack.peek()));
    }

    private void parseRelop(String production) {
        //E->id relop id2，弹出3个，压入1个
        BooleanElem id1, relop, id2;
        id1 = this.symbolStack.peek();
        this.symbolStack.pop();
        this.stateStack.pop();
        relop = this.symbolStack.peek();
        this.symbolStack.pop();
        this.stateStack.pop();
        id2 = this.symbolStack.peek();
        this.symbolStack.pop();
        this.stateStack.pop();

        BooleanElem E = newTemp();
        E.setType(getLeft(production));
        System.out.println(relop.getName()+ "," + id1.getName() + "," + id2.getName() + "," + E.getName());
        System.out.println(":=,"+"#0," +"-," + E.getName());
        // System.out.println("j," + "-," +"-," + quadList.getNextQuad());
        System.out.println("j," + "-," +"-," + "下一条地址");
        System.out.println(":=,"+"#1," +"-," + E.getName());
        //                        quadList.emit("or",E1.getName(),E2.getName(),E.getName());

        //压入新的
        this.symbolStack.push(new BooleanElem(getLeft(production)));
        this.stateStack.push(gotoMap.get(getLeft(production)).get(this.stateStack.peek()));
    }

    private void parseBrackets(String production) {
        //E->not E，弹出3个，压入1个
        BooleanElem E1;
        E1 = this.symbolStack.peek();
        this.symbolStack.pop();
        this.stateStack.pop();
        this.symbolStack.pop();
        this.stateStack.pop();
        this.symbolStack.pop();
        this.stateStack.pop();

        BooleanElem E = newTemp();
        E.setType(getLeft(production));
        System.out.println(":=" + E1.getName() + ",-," + E.getName());
//                        quadList.emit("or",E1.getName(),E2.getName(),E.getName());

        //压入新的
        this.symbolStack.push(new BooleanElem(getLeft(production)));
        this.stateStack.push(gotoMap.get(getLeft(production)).get(this.stateStack.peek()));
    }

    private void parseNot(String production) {
        //E->not E，弹出2个，压入1个
        BooleanElem E1;
        E1 = this.symbolStack.peek();
        this.symbolStack.pop();
        this.stateStack.pop();
        this.symbolStack.pop();
        this.stateStack.pop();

        BooleanElem E = newTemp();
        E.setType(getLeft(production));
        System.out.println("not," + E1.getName() + ",-," + E.getName());
//                        quadList.emit("or",E1.getName(),E2.getName(),E.getName());

        //压入新的
        this.symbolStack.push(new BooleanElem(getLeft(production)));
        this.stateStack.push(gotoMap.get(getLeft(production)).get(this.stateStack.peek()));
    }

    private void parseOrAnd(String production) {
        //E->E1 or E2 ，弹出3个，压入1个
        BooleanElem E1, E2;
        E2 = this.symbolStack.peek();
        this.symbolStack.pop();
        this.stateStack.pop();
        this.symbolStack.pop();
        this.stateStack.pop();
        E1 = this.symbolStack.peek();
        this.symbolStack.pop();
        this.stateStack.pop();

        BooleanElem E = newTemp();
        E.setType(getLeft(production));

        if("E->E1 or E2".equals(production)) {
            System.out.println("or," + E1.getName() + "," + E2.getName()+"," + E.getName());
//                            quadList.emit("or",E1.getName(),E2.getName(),E.getName());
        }
        else{
            System.out.println("and," + E1.getName() + "," + E2.getName() + "," + E.getName());
//                            quadList.emit("and",E1.getName(),E2.getName(),E.getName());
        }

        //压入新的
        this.symbolStack.push(new BooleanElem(getLeft(production)));
        this.stateStack.push(gotoMap.get(getLeft(production)).get(this.stateStack.peek()));
    }

    private BooleanElem newTemp() {
        variables.put("T"+variables.size(),new BooleanElem("T"+variables.size()));
        return variables.get("T"+(variables.size()-1));
    }

    //输出
    public void printQuadList() {

    }
}
