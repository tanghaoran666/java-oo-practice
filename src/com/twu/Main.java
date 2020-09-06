package com.twu;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Map<String, Integer> hotSearchMap = new HashMap<>();
        Scanner sc = new Scanner(System.in);
        boolean systemMenuStatus = true;
        while(systemMenuStatus){
            System.out.println("欢迎来到热搜排行榜，你是？");
            System.out.println("1.用户");
            System.out.println("2.管理员");
            System.out.println("3.退出");
            int choice = sc.nextInt();
            switch (choice){
                case 1:
                    System.out.println("请输入您的昵称");
                    String name = sc.next();
                    User customer = new User(name);
                    boolean customerMenuStatus = true;
                    while(customerMenuStatus){
                        System.out.println("你好"+customer.name +",你可以：" );
                        System.out.println("1.查看热搜排行榜");
                        System.out.println("2.给热搜事件投票");
                        System.out.println("3.购买热搜");
                        System.out.println("4.添加热搜");
                        System.out.println("5.退出");
                        int menuChoice = sc.nextInt();
                        switch (menuChoice){
                            case 1:
                                checkHotSearch(hotSearchMap);
                                break;
                            case 2:
                                System.out.println("请输入你要投票的热搜名称");
                                String voteName = sc.next();
                                System.out.println("请输入你要投票的热搜票数： （你目前还有："+customer.vote + "票）");
                                int voteNumber = sc.nextInt();
                                if(voteNumber > customer.vote){
                                    System.out.println("投票失败");
                                    break;
                                }
                                customer.vote -= voteNumber;
                                voteHotSearch(hotSearchMap,voteName,voteNumber);
                                System.out.println("投票成功");
                                break;
                            case 4:
                                System.out.println("请输入你要添加的热搜事件的名字");
                                String addSearchName = sc.next();
                                addHotSearch(hotSearchMap,addSearchName);
                                System.out.println("添加成功");
                                break;
                            case 5:
                                customerMenuStatus = false;
                        }
                    }
                    break;
                case 2:
                    System.out.println("请输入您的昵称");

                    break;
                case 3:
                    systemMenuStatus = false;
                    break;
            }
        }

    }

    private static void addHotSearch(Map<String, Integer> hotSearchMap, String hotSearchToAdd) {
        hotSearchMap.put(hotSearchToAdd,0);
    }

    private static void checkHotSearch(Map<String, Integer> unsortSearchMap) {
        Map<String, Integer> sortedSearchMap = new LinkedHashMap<>();
        unsortSearchMap.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEachOrdered(x -> sortedSearchMap.put(x.getKey(), x.getValue()));
        List<HotSearch> listResult = HotSearch.mapToList(sortedSearchMap);
        HotSearch insert = new HotSearch();
        for (int i = 0; i < listResult.size(); i++) {
            System.out.println((i+1)+". "+ listResult.get(i).getKeyword() + " " + listResult.get(i).getHotValue());
        }
    }

    private static void voteHotSearch(Map<String, Integer> hotSearchMap, String keyWord, int voteHot) {
        int oldHot = hotSearchMap.remove(keyWord);
        int newHot = oldHot + voteHot;
        hotSearchMap.put(keyWord,newHot);
    }


}
