package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Souvenir s1 = new Souvenir("cup", new Manufacture("Zbroia","Ukraine"),"10-07-2020",40);
        String fileNameSouvenirs = "souvenirs.txt";
        List<Souvenir> souvenirList = new ArrayList<>();
        String fileNameManufactures = "manufactures.txt";
        Scanner sc = new Scanner(System.in);
        while (!sc.nextLine().equals("q")) {
            System.out.println("Please select action what you want to do:" + '\'' +
                                "type s - to show all souvenirs" + '\'' +
                                "type m - to show all manufactures" + '\'' +
                                "type es - to edit souvenirs" + '\'' +
                                "type em - to edit manufactures");
            String choose = sc.nextLine();
            if (choose.equals("s")) {
                showAllSouvenirs(fileNameSouvenirs);
            }
            if (choose.equals("es")){
                //edit();
                souvenirList.add((Souvenir) edit());
                write(fileNameSouvenirs, souvenirList);
            }
        }
        System.out.println(souvenirList);
    }

    private static Object edit() {
        System.out.println("Write manufacture or souvenir: ");
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        String[] split = line.split(",");
        if (split.length == 2) {
            Manufacture m = new Manufacture(split[0],split[1]);
            return m;
        }
            Souvenir s = new Souvenir(split[0],new Manufacture(split[1],split[2]),split[3],0);
            System.out.println("Write price for it");
            double price = scanner.nextDouble();
            s.setPrice(price);
            return s;
    }

    private static void write(String fileName, List list) throws IOException {
        System.out.println(list);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
        for(int i = 0; i<list.size();i++) {
                bw.write(String.valueOf(list.get(i)));
                bw.newLine();
            }
        }
    }

    private static void showAllSouvenirs(String fileName) throws IOException {
        List<String> list = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        for (int i = 0; i <list.size(); i++) {
            br.skip(i);
            String line = br.readLine();
            list.add(line);
        }

    }
}