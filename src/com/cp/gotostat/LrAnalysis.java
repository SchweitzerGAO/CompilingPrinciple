package com.cp.gotostat;

import com.cp.pblc.LRTable;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

/**
 * @author: Gu HungJou
 * @date: 2022/1/2 15:52
 * Describe:
 * 2022.1.3 03:00备注
 * 文件路径自行改动
 * 测试样例为ppt“自上而下语法分析”第112页、本项目“标号和goto”代码
 */
public class LrAnalysis {
//    String fileAction = "src/com/cp/gotostat/action.txt";
    String fileAction = "Z:\\test\\action.txt";
//    String fileGoto="src/com/cp/gotostat/goto.txt";
    String fileGoto="Z:\\test\\goto.txt";
    String testCase="Z:\\test\\test-case\\goto.txt";
//    String testCase="src/com/cp/gotostat/例题输入ppt112页.txt";
    String wenfa="Z:\\test\\wenfa.txt";
//    String wenfa="src/com/cp/gotostat/wenfa.txt";
    @Test
   public void myAnalysis() throws FileNotFoundException {
        LRTable lrTable=new LRTable();
        Map<String, List<Integer>> action=new LinkedHashMap<>();
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
        System.out.println("action表："+action);
        System.out.println("=========");
        Map<String, List<Integer>> gotoMap=new LinkedHashMap<>();
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
                if(flag==false){
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
        System.out.println("goto表："+gotoMap);
        System.out.println("==========");
        lrTable.setAction(action);
        lrTable.setAction(gotoMap);

        List<String> wenfaList=new ArrayList<>();
        try (Scanner sc = new Scanner(new FileReader(wenfa))) {
            sc.useDelimiter("\n");  //分隔符
            System.out.println("文法各项目：");
            while (sc.hasNext()) {   //按分隔符读取字符串
                String str = sc.next();
                System.out.println(str);
                wenfaList.add(str);
            }
        }
        System.out.println("==========");
        List<StringBuilder> list=new ArrayList<>();
        try (Scanner sc = new Scanner(new FileReader(testCase))) {
//            StringBuilder sb=new StringBuilder("");
            sc.useDelimiter("\r\n");
//            Integer ii=1;
            while (sc.hasNext()) {   //按分隔符读取字符串
                StringBuilder sb = new StringBuilder();
                String str = sc.next();
//                System.out.print(str);

//                if(str.contains("")){
//                    continue;
//                }
                if (str.contains("//")) {
                    String[] split = str.split("//");
                    sb.append(split[0]);
//                    System.out.println(sb);
                } else {
                    sb.append(str);
                }
                list.add(sb);
//                System.out.println("!11"+sb);
//                System.out.println(ii.toString()+sb);
//                ii++;
            }
            int jj=0;
            for (StringBuilder sb : list) {
                jj++;
                System.out.println("("+jj+") 测试输入：" + sb.toString());
                String[] s = sb.toString().split(" ");
//            System.out.println(s[3]);
                Stack<Integer> stackState = new Stack<>();
                Stack<String> stack1 = new Stack<>();
                Stack<String> stack2 = new Stack<>();
//            Map<String, List<Integer>> act = lrTable.getAction();
//            Map<String, List<Integer>> go = lrTable.getGoto();
                stack1.push("#");
                stack2.push("#");
                stackState.push(0);
                for (int i = s.length - 1; i >= 0; i--) {
                    stack2.push(s[i]);
                }
                System.out.println("==========");
//            System.out.println("a的5为："+action.get("a").get(5));
                System.out.println("LR分析表扫描结果：");

                do {
//                System.out.println(stackState);
                    String input = stack2.peek();
//                System.out.println("输入："+input);

                    Integer peek = stackState.peek();
//                System.out.println("状态栈顶："+peek);
                    Integer canshu = action.get(input).get(peek);
//                System.out.println(action.get(input));
//                System.out.println("加入："+canshu);
                    if (canshu > 0 && canshu < 999) {
                        stackState.push(canshu);
                        if (!stack2.peek().equals("#"))
                            stack2.pop();
                        System.out.println("移入：" + input);
                        if (!input.equals("#"))
                            stack1.push(input);
                    } else if (canshu < 0) {
                        String wf = wenfaList.get(-canshu);
//                    System.out.println("选择规约的项目为："+wf);
                        String[] frontAndEnd = wf.split("#");
                        int endLength = frontAndEnd[1].trim().length();
//                    System.out.println(endLength);
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
                    } else if (canshu == 999) {
                        System.out.println("accept!!!");
                        break;
                    } else {
                        System.out.println("error!!!");
                        break;
                    }
                } while (!stack1.peek().equals("#"));
                System.out.println("==========");
            }
        }
    }
}
