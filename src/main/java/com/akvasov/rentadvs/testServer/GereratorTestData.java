package com.akvasov.rentadvs.testServer;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * Created by akvasov on 30.07.14.
 */
public class GereratorTestData {

    static Random rnd = new Random();
    //
    static Integer frndAvr = 4;
    static Integer peopleCount; // 1 + 10 * 10
    static ArrayList<Set<Integer>> userFriend = new ArrayList<>();

    static Integer[][] sum;

    static List<Integer> zoneLength = new ArrayList<>();

    public static void createMan(int ind){
        userFriend.add(ind, new TreeSet<>());
    }

    public static void makeFriend(int i, int j){
        userFriend.get(i).add(j);
        userFriend.get(j).add(i);
    }

    public static void makeFriendLevel(int i, List<Integer> lst, int percentage){
        Set<Integer> iFriends = userFriend.get(i);
        for(Integer ind: lst){
            if (rnd.nextInt(100) < percentage){
                iFriends.add(ind);
                userFriend.get(ind).add(i);
            }
        }
    }

    public static void printMatrix(Integer m[][], Integer pad){
        for (int i = 0; i < m.length; ++i){
            //if (i < 10) System.out.print(" ");
            //System.out.print(i + ": ");

            for (int j = 0; j < m.length; ++j){
                System.out.print(StringUtils.leftPad(m[i][j].toString(), pad) + " ");
            }
            System.out.println();
        }
    }

    public static Integer randomFriendsCount(){
        int d = frndAvr / 2;
        return d + rnd.nextInt(frndAvr - d) + 1;
    }

    public static void main(String[] args) {

        //Level 0
        createMan(0);
        peopleCount = 1; //First user
        zoneLength.add(1);

        //Level 1
        Integer fLevelFrndCnt = randomFriendsCount();
        zoneLength.add(fLevelFrndCnt);
        List<Integer> fLevelFriends = new ArrayList<>();
        List<Integer> allPeople = new ArrayList<>();
        //allPeople.add(0);

        for (int i = 1; i <= fLevelFrndCnt; ++i){
            createMan(peopleCount);
            makeFriend(0, peopleCount);
            makeFriendLevel(peopleCount, fLevelFriends, 70);

            fLevelFriends.add(peopleCount);
            allPeople.add(peopleCount);
            peopleCount++;
        }

        //Level 2
        for (Integer ind: fLevelFriends){
            Integer sLevelFrndCnt = randomFriendsCount();
            zoneLength.add(sLevelFrndCnt);

            List<Integer> sLevelFriends = new ArrayList<>();
            for (int i = 1; i <= sLevelFrndCnt; ++i){
                createMan(peopleCount);
                makeFriend(ind, peopleCount);
                makeFriendLevel(peopleCount, sLevelFriends, 50);
                makeFriendLevel(peopleCount, allPeople, 20);

                sLevelFriends.add(peopleCount);
                allPeople.add(peopleCount);
                peopleCount++;
            }
        }

        System.out.println(zoneLength);

        System.out.println(userFriend);
        System.out.println(peopleCount);

        Integer[][] adj = new Integer[peopleCount][peopleCount];

        for (Integer i = 0; i < peopleCount; ++i){
            Arrays.fill(adj[i], 0);
            for(Integer ind: userFriend.get(i)){
                adj[i][ind] = 1;
                adj[ind][i] = 1;
            }
        }

        System.out.println();
        printMatrix(adj, 1);

        sum = new Integer[peopleCount][peopleCount];
        for (int i = 0; i < peopleCount; ++i){
            for (int j = 0; j < peopleCount; ++j){
                sum[i][j] = adj[i][j];
                if (j > 0) sum[i][j] += sum[i][j - 1];
                if (i > 0) sum[i][j] += sum[i - 1][j];
                if (i > 0 && j > 0) sum[i][j] -= sum[i - 1][j - 1];
            }
        }


        System.out.println();
        printMatrix(sum, 3);

        int sum1 = 0;
        for (Integer[] s: adj){
            sum1 += Arrays.stream(s).mapToInt(b->((Integer)b).intValue()).sum();
        }
        System.out.println(sum1);

        List<Integer> zoneSum = new ArrayList<>();
        int t = 0;
        for (Integer i: zoneLength){
            t += i;
            zoneSum.add(t);
        }

        System.out.println(zoneSum);

        System.out.println();
        System.out.println();
        System.out.println();

        System.out.println(zoneLength);

        Integer[][] per = new Integer[zoneLength.size()][zoneLength.size()];
        for (Integer[] i: per){
            Arrays.fill(i, 0);
        }
        for (int i = 0; i < zoneLength.size(); ++i){
            for(int j = i; j < zoneLength.size(); ++j){
                per[i][j] = sum[zoneSum.get(i) - 1][zoneSum.get(j) - 1];
                if (i > 0) per[i][j] -= sum[zoneSum.get(i - 1) - 1][zoneSum.get(j) - 1];
                if (j > 0) per[i][j] -= sum[zoneSum.get(i) - 1][zoneSum.get(j - 1) - 1];
                if (i > 0 && j > 0) per[i][j] += sum[zoneSum.get(i - 1) - 1][zoneSum.get(j - 1) - 1];
                //per[i][j] *= 100;
                //per[i][j] /= zoneLength.get(i) * zoneLength.get(j);
            }
        }
        printMatrix(per, 3);
        System.out.println();

        for (int i = 0; i < zoneLength.size(); ++i){
            for(int j = i; j < zoneLength.size(); ++j){
                per[i][j] = sum[zoneSum.get(i) - 1][zoneSum.get(j) - 1];
                if (i > 0) per[i][j] -= sum[zoneSum.get(i - 1) - 1][zoneSum.get(j) - 1];
                if (j > 0) per[i][j] -= sum[zoneSum.get(i) - 1][zoneSum.get(j - 1) - 1];
                if (i > 0 && j > 0) per[i][j] += sum[zoneSum.get(i - 1) - 1][zoneSum.get(j - 1) - 1];
                per[i][j] *= 100;
                per[i][j] /= zoneLength.get(i) * zoneLength.get(j);
            }
        }
        printMatrix(per, 3);
        System.out.println();

        //

        List<List<String>> rent = new ArrayList<>();
        for (int i = 0; i < peopleCount; ++i){
            rent.add(new ArrayList<String>());
        }

        //fill himadv
        for (int i = 1; i < peopleCount; ++i){
            if (rnd.nextInt(3) == 0){
                rent.get(i).add("user " + i);
            }

            if (rnd.nextInt(8) == 0){
                Integer x = rnd.nextInt(400) + peopleCount;
                rent.get(i).add("user " + x);
            }

            System.out.println(i + ": " +  rent.get(i));
        }

        System.out.println("Debug end");

        System.out.println(peopleCount);
        printMatrix(adj, 2);
        for (int i = 0; i < peopleCount; ++i){
            System.out.println(rent.get(i));
        }

    }

}
