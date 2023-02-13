package org.example;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Souvenir s1 = new Souvenir("cup", new Manufacture("Zbroia","Ukraine"),"10-07-2020",40);
        String fileNameSouvenirs = "souvenirs.txt";
        List<Souvenir> souvenirList = new ArrayList<>();
        Set<Manufacture> manufactureList = new HashSet<>();
        String fileNameManufactures = "manufactures.txt";
        Scanner sc = new Scanner(System.in);
        while (!sc.nextLine().equals("q")) {
            System.out.println("""
                                Please select action what you want to do:
                                type s - to show all souvenirs
                                type m - to show all manufactures
                                type add - to add souvenirs or manufactures
                                type em - to edit manufactures
                                type es - to edit souvenirs""");
            String choose = sc.nextLine();
            if (choose.equals("s")) {
                showAllSouvenirs(fileNameSouvenirs, souvenirList);
            }
            if (choose.equals("add")){
                souvenirList.add((Souvenir) edit());
                manufactureList.add(((Souvenir) edit()).getManufacture());
                write(fileNameSouvenirs, souvenirList);
                write(fileNameManufactures,manufactureList.stream().toList());
            }
        }
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
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
        for(int i = 0; i<list.size();i++) {
                bw.write(String.valueOf(list.get(i)));
                bw.newLine();
            }
        }
    }

    private static void showAllSouvenirs(String fileName, List list) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            for (int i = 0; i < list.size(); i++) {
                String line = br.readLine();
                System.out.println(line);
                list.add(line);
            }
        }
        System.out.println(list);
    }
}