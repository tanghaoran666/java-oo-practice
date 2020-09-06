package com.twu;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Map<String, Integer> hotSearchMap = new HashMap<>();
        Map<Integer,String> searchWithRankMap = new HashMap<>();
        List<Integer> moneyForRankList = new ArrayList<>();
        List<String> superHotSearch = new ArrayList<>();
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
                    Customer customer = new Customer(name);
                    boolean customerMenuStatus = true;
                    while(customerMenuStatus){
                        System.out.println("你好"+customer.getName() +",你可以：" );
                        System.out.println("1.查看热搜排行榜");
                        System.out.println("2.给热搜事件投票");
                        System.out.println("3.购买热搜");
                        System.out.println("4.添加热搜");
                        System.out.println("5.退出");
                        int menuChoice = sc.nextInt();
                        switch (menuChoice){
                            case 1:
                                checkHotSearch(hotSearchMap,searchWithRankMap);
                                System.out.println("竞价表：" + moneyForRankList);
                                break;
                            case 2:
                                System.out.println("请输入你要投票的热搜名称");
                                String voteName = sc.next();
                                if(!hotSearchMap.containsKey(voteName)){
                                    System.out.println("排行榜中暂未此条热搜，请重新选择或者添加热搜");
                                    break;
                                }
                                System.out.println("请输入你要投票的热搜票数： （你目前还有："+customer.vote + "票）");
                                int voteNumber = sc.nextInt();
                                if(voteNumber > customer.vote){
                                    System.out.println("投票失败");
                                    break;
                                }
                                customer.vote -= voteNumber;
                                if(superHotSearch.contains(voteName)) voteNumber *= 2;
                                voteHotSearch(hotSearchMap,voteName,voteNumber);
                                System.out.println("投票成功");
                                break;
                            case 3:
                                System.out.println("请输入你要购买的热搜名称");
                                String moneySearchName = sc.next();
                                System.out.println("请输入你要购买的热搜排名");
                                int rank = sc.nextInt();
                                System.out.println("请输入你要购买的金额");
                                int money = sc.nextInt();
                                if(money <= moneyForRankList.get(rank-1)) {
                                    System.out.println("竞价失败，请调整金额或者重选排名");
                                    break;
                                }
                                hotSearchMap.remove(moneySearchName);
                                searchWithRankMap.put(rank,moneySearchName);
                                moneyForRankList.set(rank-1,money);
                                break;
                            case 4:
                                System.out.println("请输入你要添加的热搜事件的名字");
                                String addSearchName = sc.next();
                                addHotSearch(hotSearchMap,addSearchName);
                                moneyForRankList.add(0);
                                System.out.println("添加成功");
                                break;
                            case 5:
                                customerMenuStatus = false;
                        }
                    }
                    break;
                case 2:
                    System.out.println("请输入您的昵称");
                    String adminName = sc.next();
                    System.out.println("请输入你的密码");
                    String adminPassword = sc.next();
                    Administrator administrator = new Administrator();
                    if(!adminName.equals(administrator.getName()) || !adminPassword.equals(administrator.getPassword())) {
                        System.out.println("用户名或密码不对！");
                        break;
                    }
                    boolean adminSystemStatus = true;
                    while(adminSystemStatus){
                        System.out.println("你好管理员" +administrator.getName() + ",你可以：" );
                        System.out.println("1.查看热搜排行榜");
                        System.out.println("2.添加热搜");
                        System.out.println("3.添加超级热搜");
                        System.out.println("4.退出");
                        int adminchoice = sc.nextInt();
                        switch (adminchoice){
                            case 1:
                                checkHotSearch(hotSearchMap,searchWithRankMap);
                                break;
                            case 2:
                                System.out.println("请输入你要添加的热搜事件的名字");
                                String addSearchName = sc.next();
                                addHotSearch(hotSearchMap,addSearchName);
                                System.out.println("添加成功");
                                break;
                            case 3:
                                System.out.println("请输入你要添加的超级热搜事件的名字");
                                String addSuperSearchName = sc.next();
                                superHotSearch.add(addSuperSearchName);
                                addHotSearch(hotSearchMap,addSuperSearchName);
                                System.out.println("添加成功");
                                break;
                            case 4:
                                adminSystemStatus = false;
                        }
                    }
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

    private static void checkHotSearch(Map<String, Integer> unsortSearchMap,Map<Integer,String> moneySearchMap) {
        Map<String, Integer> sortedSearchMap = new LinkedHashMap<>();
        unsortSearchMap.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEachOrdered(x -> sortedSearchMap.put(x.getKey(), x.getValue()));

        List<HotSearch> listResult = HotSearch.mapToList(sortedSearchMap);
        for(Map.Entry<Integer,String> entry : moneySearchMap.entrySet()) {
            HotSearch moneySearch = new HotSearch(entry.getValue(),0);
            listResult.add(entry.getKey()-1,moneySearch);
        }

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
