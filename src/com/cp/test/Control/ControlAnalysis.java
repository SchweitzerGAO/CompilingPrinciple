package com.cp.test.Control;

import com.cp.controlstat.ControlStatement;
import com.cp.pblc.LRTable;
import org.junit.Test;
//import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

/**
 * @description:
 * @author: LuBixing
 * @date: 2022/1/5 21:18
 */
public class ControlAnalysis {
    //读入数据
    String fileAction = "src/com/cp/test/Control/files/action.txt";
    String fileGoto="src/com/cp/test/Control/files/goto.txt";
    String testCase="src/com/cp/test/Control/files/input.txt";
    String wenfa="src/com/cp/test/Control/files/wenfa.txt";

    Map<String, List<Integer>> action=new LinkedHashMap<>();
    Map<String, List<Integer>> gotoMap=new LinkedHashMap<>();
    List<String> wenfaList=new ArrayList<>();
    List<StringBuilder> list=new ArrayList<>();
    ControlStatement controlStatement = new ControlStatement();

    @Test
    public void myAnalysis() throws FileNotFoundException {
        //生成LR分析表
        LRTable lrTable=new LRTable();
        loadAction(action);
        loadGoto(gotoMap);
        lrTable.setAction(action);
        lrTable.setAction(gotoMap);

        //加载文法
        loadWenfa(wenfaList);

        //加载测试样例
        loadInput();

        //输出四元式
        controlStatement.printQuadList();
    }

    //加载Action表
    private void loadAction(Map<String, List<Integer>> action) throws FileNotFoundException {
        try (Scanner sc = new Scanner(new FileReader(fileAction))) {
            sc.useDelimiter(",");  //分隔符
            boolean flag=false;
            int t=0;
            while (sc.hasNext()) {   //按分隔符读取字符串
                String str = sc.next();
//                System.out.print(str);
                if(str.contains("\n")){
                    flag=true;
                    t=0;
                }
                if(!flag){
                    List<Integer> list=new ArrayList<>();
                    action.put(str,list);
//                    System.out.println(action);
                }else{
                    int k=0;
                    for (String s : action.keySet()) {
//                        System.out.println("this:"+s);
                        if(k==t){
                            action.get(s).add(Integer.valueOf(str.trim()));
                        }
                        k++;
                    }
                    t++;
                }
            }
        }
//        System.out.println("action表："+action);
//        System.out.println("=========");
    }

    //加载Goto表
    private void loadGoto(Map<String,List<Integer>>gotoMap) throws FileNotFoundException{
        try (Scanner sc = new Scanner(new FileReader(fileGoto))) {
            sc.useDelimiter(",");  //分隔符
            boolean flag=false;
            int t=0;
            while (sc.hasNext()) {   //按分隔符读取字符串
                String str = sc.next();
//                System.out.println(str);
                if(str.contains("\n")){
                    flag=true;
                    t=0;
                }
                if(!flag){
                    List<Integer> list=new ArrayList<>();
                    gotoMap.put(str,list);
                }else{
                    int k=0;
                    for (String s : gotoMap.keySet()) {
                        if(k==t){
                            gotoMap.get(s).add(Integer.valueOf(str.trim()));
                        }
                        k++;
                    }
                    t++;
                }
            }
        }
//        System.out.println("goto表："+gotoMap);
//        System.out.println("==========");
    }

    //加载文法
    private void loadWenfa(List<String> wenfaList) throws FileNotFoundException {
        try (Scanner sc = new Scanner(new FileReader(wenfa))) {
            sc.useDelimiter("\n");  //分隔符
//            System.out.println("文法各项目：");
            while (sc.hasNext()) {   //按分隔符读取字符串
                String str = sc.next();
//                System.out.println(str);
                wenfaList.add(str);
            }
        }
//        System.out.println("==========");
    }

    //加载测试样例
    private void loadInput() throws FileNotFoundException {
        try (Scanner sc = new Scanner(new FileReader(testCase))) {
            sc.useDelimiter("\r\n");
            while (sc.hasNext()) {   //按分隔符读取字符串
                StringBuilder sb = new StringBuilder();
                String str = sc.next();
                if (str.contains("//")) {
                    String[] split = str.split("//");
                    sb.append(split[0]);
                } else {
                    sb.append(str);
                }
                list.add(sb);
            }
            System.out.println("list:"+list);

            //先看要补成哪种类型
            boolean thenFlag = false;
            boolean elseFlag = false;
            for(StringBuilder sb :list){
                if(sb.toString().contains("then")){
                    thenFlag = true;
                }
                if(sb.toString().contains("else")){
                    elseFlag = true;
                }
            }

            int jj=0;
//            String symbol = "";
            for (StringBuilder sb : list) {
                jj++;
                System.out.println("("+jj+") 测试输入：" + sb.toString());
//                if(sb.toString().contains("then")){
//                    symbol = 1;
//                }
//                else if(sb.toString().contains("else")){
//                    symbol = 2;
//                }
                //说明要补成if E then m S n else m S///////////////////////////
                if(thenFlag && elseFlag){
                    if(sb.toString().contains("then")){
                        int index=sb.indexOf("then")+5;
                        sb.insert(index,"o ");
                        index=sb.indexOf("then")+9;
                        sb.insert(index," o");
                    }
                    if(sb.toString().contains("else")){
                        int index=sb.indexOf("else")+5;
                        sb.insert(index,"o ");
                    }
                }else{  //说明要补成if E then m S
                    if(sb.toString().contains("then")){
                        int index=sb.indexOf("then")+5;
                        sb.insert(index,"o ");
                    }
                }
//                System.out.println(sb.toString());
                //按照空格分隔字符串，获取单个元素
                String[] s = sb.toString().split(" ");

                //对栈进行初始化
                Stack<Integer> stackState = new Stack<>();
                Stack<String> stack1 = new Stack<>();
                Stack<String> stack2 = new Stack<>();
                stack1.push("#");
                stack2.push("#");
                stackState.push(0);
                for (int i = s.length - 1; i >= 0; i--) {
                    stack2.push(s[i]);
                }
                System.out.println("LR分析表扫描结果：");
                do {
//                System.out.println(stackState);
                    String input = stack2.peek();
                    System.out.println("输入："+input);

                    Integer peek = stackState.peek();
                    System.out.println("状态栈顶："+peek);
                    //除了保留字之外，其他的终结符（a1，a2）统一为a
                    if(!input.equals("if") && !input.equals("e") && !input.equals("then")&&
                            !input.equals("else")&& !input.equals("o")&&!input.equals("#")) {
                        input = "a";
                    }
                    Integer param = action.get(input).get(peek);
                    System.out.println("加入："+param);
                    System.out.println(action.get(input));
                    System.out.println("-------");
                    //表示移进
                    if (param > 0 && param < 999) {
                        stackState.push(param);
                        if (!stack2.peek().equals("#"))
                            stack2.pop();
                        System.out.println("移入：" + input);
                        if (!input.equals("#"))
                            stack1.push(input);
                    }
                    //表示规约
                    else if (param < 0) {
                        String wf = wenfaList.get(-param);
                        System.out.println("选择规约的项目为："+wf);
                        String[] frontAndEnd = wf.split("#");
                        //记录要pop出去的数目
                        int endLength = frontAndEnd[1].trim().length();
                        switch (param) {
                            case -1:{
                                //S#ietMS
                                controlStatement.parseIf();
                                break;
                            }
                            case -2: {
                                //S#ietMSNsMS
                                controlStatement.parseIfElse();
                                break;
                            }
                            case -3:{
                                //E#e
                                controlStatement.parseE();
                                break;
                            }
                            case -4: {
                                //M#m
                                controlStatement.parseM();
                                break;
                            }
                            case -5: {
                                //N#n
                                controlStatement.parseN();
                                break;
                            }
                            case -6:{
                                //S#a
                                controlStatement.parseTerminal();
                                break;
                            }
                        }
                        for (int j = 0; j < endLength; j++) {
                            stackState.pop();
                            stack1.pop();
                        }
//                    System.out.println(frontAndEnd[0]);
//                    System.out.println(stackState.size());
                        stack1.push(frontAndEnd[0]);
                        Integer f1 = gotoMap.get(stack1.peek()).get(stackState.peek());
//                    System.out.println(f1);
                        stackState.push(f1);

                        System.out.println("规约：" + frontAndEnd[0] + "->" + frontAndEnd[1]);
//                    System.out.println(stackState.peek());
                    }
                    //表示成功
                    else if (param == 999) {
                        System.out.println("accept!!!");
                        break;
                    }
                    //表示失败
                    else{
                        System.out.println("error!!!");
                        break;
                    }
                } while (!stack1.peek().equals("#"));
                System.out.println("==========");
            }
        }
    }
}