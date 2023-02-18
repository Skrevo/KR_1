package main;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        Main main = new Main();
        //main.run();
        String fileNameSouvenirs = "souvenirs.txt";
        String fileNameManufactures = "manufactures.txt";
        List<Souvenir> souvenirList = Arrays.asList(
                new Souvenir("cup", new Manufacture("Zbroia", "Ukraine"),"2018",44),
                new Souvenir("hat", new Manufacture("Zbroia", "Ukraine"),"2015",35.5),
                new Souvenir("hat", new Manufacture("Ukrsuvenir", "Ukraine"),"2015",35.5),
                new Souvenir("pen", new Manufacture("Ukrsuvenir","Ukraine"),"2018",55));
        Set<Manufacture> manufactureSet = Set.of(
                new Manufacture("Zbroia", "Ukraine"),
                new Manufacture("Ukrsuvenir", "Ukraine"));
        Scanner sc = new Scanner(System.in);
        while (!sc.nextLine().equals("q")) {
            System.out.println("""
                                Please select action what you want to do:
                                type s - to show all souvenirs
                                type m - to show all manufactures
                                type add - to add souvenirs or manufactures
                                type ds - to delete souvenirs
                                type dm - to delete manufacture
                                type sm - to show souvenirs by manufacture
                                type sc - to show souvenirs by country
                                type sp - to show souvenirs by price
                                type sms - to show manufactures and his souvenirs
                                type smsy - to show manufactures of souvenir and year
                                type sy - to show souvenirs by year
                                type dms - to delete manufacture with his souvenirs""");
            String choose = sc.nextLine();
            if (choose.equals("s"))
                souvenirList.forEach(System.out::println);
            if (choose.equals("m"))
                manufactureSet.forEach(System.out::println);
            if (choose.equals("add")){
                Object object;
                object = edit();
                if (object.getClass() == Manufacture.class) {
                    manufactureSet.add((Manufacture) object);
                    write(fileNameManufactures, manufactureSet.stream().toList());
                }
                if (object.getClass() == Souvenir.class) {
                souvenirList.add((Souvenir) object);
                write(fileNameSouvenirs, souvenirList);
                manufactureSet.add(((Souvenir) object).getManufacture());
                write(fileNameManufactures,manufactureSet.stream().toList());
                }
            }
            if (choose.equals("ds")){
                System.out.println("write a name of souvenir: ");
                String souvenir = sc.next();
                souvenirList = souvenirList.stream().filter(s -> !s.getName().equals(souvenir)).collect(Collectors.toList());
                write(fileNameSouvenirs, souvenirList);
            }
            if (choose.equals("dm")) {
                System.out.println("write a name of manufacture: ");
                String manufacture = sc.nextLine();
                manufactureSet = manufactureSet.stream().filter(m -> !m.getName().equals(manufacture)).collect(Collectors.toSet());
                write(fileNameManufactures, manufactureSet.stream().toList());
            }
            if (choose.equals("sm")) {
                System.out.println("write a name of manufacture to show souvenirs: ");
                String manufacture = sc.next();
                souvenirList.stream().filter(s -> s.getManufacture().getName().equals(manufacture)).forEach(System.out::println);
            }
            if (choose.equals("sc")) {
                System.out.println("write a name of country to show souvenirs: ");
                String country = sc.next();
                souvenirList.stream().filter(s -> s.getManufacture().getCountry().equals(country)).forEach(System.out::println);
            }
            if (choose.equals("sp")) {
                System.out.println("write price to find souvenirs with price lower: ");
                double price = sc.nextDouble();
                souvenirList.stream().filter(s -> s.getPrice() < price).forEach(System.out::println);
            }
            if (choose.equals("sms")) {
                souvenirList.stream().map(s -> s.getManufacture());//not relize
            }
            if (choose.equals("smsy")) {
                System.out.println("write a name of souvenir and year of produce split by ',': ");
                String[] split = sc.nextLine().split(",");
                String souvenir = split[0];
                String year = split[1];
                souvenirList.stream().filter(s -> s.getName().equals(souvenir)).filter(s -> s.getDateOfProduce().equals(year)).map(Souvenir::getManufacture).forEach(System.out::println);
            }
            if (choose.equals("sy")) {
                System.out.println("write a year to find souvenirs produced in this year: ");
                String year = sc.next();
                souvenirList.stream().filter(s -> s.getDateOfProduce().equals(year)).forEach(System.out::println);
            }
            if (choose.equals("dms")) {
                System.out.println("write a name of manufacture to delete: ");
                String manufacture = sc.next();
                souvenirList = souvenirList.stream().filter(s -> !s.getManufacture().getName().equals(manufacture)).collect(Collectors.toList());
                manufactureSet = manufactureSet.stream().filter(m -> !m.getName().equals(manufacture)).collect(Collectors.toSet());
                write(fileNameSouvenirs, souvenirList);
                write(fileNameManufactures, manufactureSet.stream().toList());
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
            System.out.println("Write price for it: ");
            double price = scanner.nextDouble();
            s.setPrice(price);
            return s;
    }

    private static void write(String fileName, List list) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (Object o : list) {
                bw.write(String.valueOf(o));
                bw.newLine();
            }
        }
    }

    private static List read(String fileName) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            List list = new ArrayList<>();
            //long lines = 0;
            String line;
            while ((line = br.readLine()) != null) {
                //lines++;
                //System.out.println(lines + " " + line);
                list.add(line);
            }
            return list;
        }
    }
}