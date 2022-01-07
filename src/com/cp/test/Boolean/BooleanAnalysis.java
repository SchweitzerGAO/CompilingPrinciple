package com.cp.test.Boolean;

import com.cp.booleanstat.BooleanStatement;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class BooleanAnalysis {

    String fileAction = "src/com/cp/test/Boolean/files/action.txt";
    String fileGoto="src/com/cp/test/Boolean/files/goto.txt";
    String testCase="src/com/cp/test/Boolean/files/input.txt";
    String wenfa="src/com/cp/test/Boolean/files/wenfa.txt";

    Map<String, List<Integer>> action=new LinkedHashMap<>();
    Map<String, List<Integer>> gotoMap=new LinkedHashMap<>();
    List<String> wenfaList=new ArrayList<>();
    List<StringBuilder> list = new ArrayList<>();
    BooleanStatement booleanStatement = new BooleanStatement();

    @Test
    public void myAnalysis() throws FileNotFoundException {
        try {
            //生成action分析表
            loadAction();
            //生成goto表
            loadGoto();

            booleanStatement.setGotoMap(gotoMap);
            booleanStatement.setActionMap(action);
        }
        catch (IOException e) {
            System.out.println("构造LR分析表失败！");
            e.printStackTrace();
            return;
        }

        //加载测试样例
        loadInput();

        for (StringBuilder sb : list) {
            booleanStatement.init();
            booleanStatement.generateQuadList(sb.toString());
            booleanStatement.printQuadList();
        }

        //输出四元式
//        booleanStatement.printQuadList();
    }

    //加载Action表
    private void loadAction() throws IOException {
        Scanner sc = new Scanner(new FileReader(fileAction));
        List<String> lines = new ArrayList<>();
        while (sc.hasNext()) {
            lines.add(sc.nextLine());
        }
        String[] vt = lines.get(0).split(",");
        for (int i = 0; i < vt.length; i++) {
            this.action.put(vt[i],new ArrayList<>());
            for (int j = 1; j <lines.size() - 1; j++) {
                this.action.get(vt[i]).add(Integer.valueOf(lines.get(j).split(",")[i]));
            }
        }
//        System.out.println(action);
    }

    //加载Goto表
    private void loadGoto() throws IOException {
        Scanner sc = new Scanner(new FileReader(fileGoto));
        List<String> lines = new ArrayList<>();
        while (sc.hasNext()) {
            lines.add(sc.nextLine());
        }
        String[] vt = lines.get(0).split(",");
        for (int i = 0; i < vt.length; i++) {
            this.gotoMap.put(vt[i],new ArrayList<>());
            for (int j = 1; j <lines.size() - 1; j++) {
                this.gotoMap.get(vt[i]).add(Integer.valueOf(lines.get(j).split(",")[i]));
            }
        }
//        System.out.println(gotoMap);
    }

    //加载文法
    private void loadWenfa() throws FileNotFoundException {
        try (Scanner sc = new Scanner(new FileReader(wenfa))) {
            //分隔符
            sc.useDelimiter("\n");
            //按分隔符读取字符串
            while (sc.hasNext()) {
                String str = sc.next();
                this.wenfaList.add(str);
            }
        }
        System.out.println("==========");
    }

    //加载测试样例
    private void loadInput() throws FileNotFoundException {
        try (Scanner sc = new Scanner(new FileReader(testCase))) {
            sc.useDelimiter("\r\n");
            //按分隔符读取字符串
            while (sc.hasNext()) {
                StringBuilder sb = new StringBuilder();
                String str = sc.next();
                if (str.contains("//")) {
                    String[] split = str.split("//");
                    sb.append(split[0]);
                } else {
                    sb.append(str);
                }
                sb.append(" #");
                list.add(sb);
            }
            System.out.println("list:"+list);

//            //先看要补成哪种类型
//            boolean thenFlag = false;
//            boolean elseFlag = false;
//            for(StringBuilder sb :list){
//                if(sb.toString().contains("then")){
//                    thenFlag = true;
//                }
//                if(sb.toString().contains("else")){
//                    elseFlag = true;
//                }
//            }

//            int jj=0;
////            String symbol = "";
//            for (StringBuilder sb : list) {
//                jj++;
//                System.out.println("("+jj+") 测试输入：" + sb.toString());
//                int symbol = 0;
//                if(sb.toString().contains("then")){
//                    symbol = 1;
//                }
//                else if(sb.toString().contains("else")){
//                    symbol = 2;
//                }
//                //说明要补成if E then m S n else m S
//                if(thenFlag && elseFlag){
//                    if(sb.toString().contains("then")){
//                        int index=sb.indexOf("then")+5;
//                        sb.insert(index,"m ");
//                        index=sb.indexOf("then")+9;
//                        sb.insert(index," n");
//                    }
//                    if(sb.toString().contains("else")){
//                        int index=sb.indexOf("else")+5;
//                        sb.insert(index,"m ");
//                    }
//                }else{  //说明要补成if E then m S
//                    if(sb.toString().contains("then")){
//                        int index=sb.indexOf("then")+5;
//                        sb.insert(index,"m ");
//                    }
//                }
////                System.out.println(sb.toString());
//                //按照空格分隔字符串，获取单个元素
//                String[] s = sb.toString().split(" ");
//
//                //对栈进行初始化
//                Stack<Integer> stackState = new Stack<>();
//                Stack<String> stack1 = new Stack<>();
//                Stack<String> stack2 = new Stack<>();
//                stack1.push("#");
//                stack2.push("#");
//                stackState.push(0);
//                for (int i = s.length - 1; i >= 0; i--) {
//                    stack2.push(s[i]);
//                }
//                System.out.println("LR分析表扫描结果：");
//                do {
////                System.out.println(stackState);
//                    String input = stack2.peek();
//                    System.out.println("输入："+input);
//
//                    Integer peek = stackState.peek();
//                    System.out.println("状态栈顶："+peek);
//                    //除了保留字之外，其他的终结符（a1，a2）统一为a
//                    if(!input.equals("if") && !input.equals("E") && !input.equals("then")&&
//                            !input.equals("else")&& !input.equals("m")&&!input.equals("n")&&!input.equals("#")) {
//                        input = "a";
//                    }
//                    Integer param = action.get(input).get(peek);
//                    System.out.println(action.get(input));
//                    System.out.println("加入："+param);
//                    //表示移进
//                    if (param > 0 && param < 999) {
//                        stackState.push(param);
//                        if (!stack2.peek().equals("#"))
//                            stack2.pop();
//                        System.out.println("移入：" + input);
//                        if (!input.equals("#"))
//                            stack1.push(input);
//                    }
//                    //表示规约
//                    else if (param < 0) {
//                        String wf = wenfaList.get(-param);
//                        System.out.println("选择规约的项目为："+wf);
//                        String[] frontAndEnd = wf.split("#");
//                        //记录要pop出去的数目
//                        int endLength = frontAndEnd[1].trim().length();
//                        switch (param) {
//                            case -1:{
//                                //S#ietMS
////                                controlStatement.parseIf();
//                                break;
//                            }
//                            case -2: {
//                                //S#ietMSNsMS
////                                controlStatement.parseIfElse();
//                                break;
//                            }
//                            case -3: {
//                                //M#m
////                                controlStatement.parseM(symbol);
//                                break;
//                            }
//                            case -4: {
//                                //N#n
////                                controlStatement.parseN();
//                                break;
//                            }
//                            case -5:{
//                                //S#a
////                                controlStatement.parseTerminal();
//                                break;
//                            }
//                        }
//                        for (int j = 0; j < endLength; j++) {
//                            stackState.pop();
//                            stack1.pop();
//                        }
////                    System.out.println(frontAndEnd[0]);
////                    System.out.println(stackState.size());
//                        stack1.push(frontAndEnd[0]);
//                        Integer f1 = gotoMap.get(stack1.peek()).get(stackState.peek());
////                    System.out.println(f1);
//                        stackState.push(f1);
//
//                        System.out.println("规约：" + frontAndEnd[0] + "->" + frontAndEnd[1]);
////                    System.out.println(stackState.peek());
//                    }
//                    //表示成功
//                    else if (param == 999) {
//                        System.out.println("accept!!!");
//                        break;
//                    }
//                    //表示失败
//                    else {
//                        System.out.println("error!!!");
//                        break;
//                    }
//                } while (!stack1.peek().equals("#"));
//                System.out.println("==========");
//            }
        }
    }
}