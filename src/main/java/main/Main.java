package main;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    final String FILE_NAME_SOUVENIR = "souvenirs.txt";
    final String FILE_NAME_MANUFACTURE = "manufactures.txt";
    private Scanner sc;
    private List<Souvenir> souvenirList;
    private Set<Manufacture> manufactureSet;
    public static void main(String[] args) throws IOException {
        Main main = new Main();
        main.run();
    }
    private void run() throws IOException {
        init();

        int c;
        while ((c = menu()) != 0) {
            switch (c) {
                case 1 -> souvenirList.forEach(System.out::println);
                case 2 -> manufactureSet.forEach(System.out::println);
                case 3 -> {
                    Object object;
                    object = edit();
                    if (object.getClass() == Manufacture.class) {
                        manufactureSet.add((Manufacture) object);
                        write(FILE_NAME_MANUFACTURE, manufactureSet.stream().toList());
                    }
                    if (object.getClass() == Souvenir.class) {
                        souvenirList.add((Souvenir) object);
                        write(FILE_NAME_SOUVENIR, souvenirList);
                        manufactureSet.add(((Souvenir) object).getManufacture());
                        write(FILE_NAME_MANUFACTURE,manufactureSet.stream().toList());
                    }
                }
                case 4 -> {
                    System.out.println("write a name of souvenir: ");
                    String souvenir = sc.next();
                    souvenirList = souvenirList.stream().filter(s -> !s.getName().equals(souvenir)).collect(Collectors.toList());
                    write(FILE_NAME_SOUVENIR, souvenirList);
                }
                case 5 -> {
                    System.out.println("write a name of manufacture: ");
                    String manufacture = sc.nextLine();
                    manufactureSet = manufactureSet.stream().filter(m -> !m.getName().equals(manufacture)).collect(Collectors.toSet());
                    write(FILE_NAME_MANUFACTURE, manufactureSet.stream().toList());
                }
                case 6 -> {
                    System.out.println("write a name of manufacture to show souvenirs: ");
                    String manufacture = sc.next();
                    souvenirList.stream().filter(s -> s.getManufacture().getName().equals(manufacture)).forEach(System.out::println);
                }
                case 7 -> {
                    System.out.println("write a name of country to show souvenirs: ");
                    String country = sc.next();
                    souvenirList.stream().filter(s -> s.getManufacture().getCountry().equals(country)).forEach(System.out::println);
                }
                case 8 -> {
                    System.out.println("write price to find souvenirs with price lower: ");
                    double price = sc.nextDouble();
                    souvenirList.stream().filter(s -> s.getPrice() < price).forEach(System.out::println);
                }
                case 9 -> {
                    for (int i = 0; i < manufactureSet.size(); i++) {
                        String manufacture = manufactureSet.stream().toList().get(i).getName();
                        System.out.println("The souvenirs of" + manufacture);
                        souvenirList.stream().filter(s -> s.getManufacture().getName().equals(manufacture)).forEach(System.out::println);
                    }
                }
                case 10 -> {
                    System.out.println("write a name of souvenir and year of produce split by ',': ");
                    String[] split = sc.nextLine().split(",");
                    String souvenir = split[0];
                    String year = split[1];
                    souvenirList.stream().filter(s -> s.getName().equals(souvenir)).filter(s -> s.getDateOfProduce().equals(year)).map(Souvenir::getManufacture).forEach(System.out::println);
                }
                case 11 -> {
                    System.out.println("write a year to find souvenirs produced in this year: ");
                    String year = sc.next();
                    souvenirList.stream().filter(s -> s.getDateOfProduce().equals(year)).forEach(System.out::println);
                }
                case 12 -> {
                    System.out.println("write a name of manufacture to delete: ");
                    String manufacture = sc.next();
                    souvenirList = souvenirList.stream().filter(s -> !s.getManufacture().getName().equals(manufacture)).collect(Collectors.toList());
                    manufactureSet = manufactureSet.stream().filter(m -> !m.getName().equals(manufacture)).collect(Collectors.toSet());
                    write(FILE_NAME_SOUVENIR, souvenirList);
                    write(FILE_NAME_MANUFACTURE, manufactureSet.stream().toList());
                }
            }
        }
    }

    private int menu() {
        System.out.println("""
                                Please select action:
                                1. Show all souvenirs
                                2. Show all manufactures
                                3. Add souvenirs or manufactures
                                4. Delete souvenirs
                                5. Delete manufacture
                                6. Show souvenirs by manufacture
                                7. Show souvenirs by country
                                8. Show souvenirs by price
                                9. Show manufactures and it souvenirs
                                10. Show manufactures of souvenir and year
                                11. Show souvenirs by year
                                12. Delete manufacture with his souvenirs
                                0. Exit""");
        int choice = sc.nextInt();
        sc.nextLine();
        return choice;
    }

    private void init() throws IOException {
        manufactureSet = new HashSet<>();
        souvenirList = new ArrayList<>();
        read(FILE_NAME_SOUVENIR);
        read(FILE_NAME_MANUFACTURE);
        sc = new Scanner(System.in);
    }

    private Object edit() {
        System.out.println("Write manufacture or souvenir: ");
        String line = sc.nextLine();
        String[] split = line.split(",");
        if (split.length == 2) {
            return new Manufacture(split[0],split[1]);
        }
            Souvenir s = new Souvenir(split[0],new Manufacture(split[1],split[2]),split[3],0);
            System.out.println("Write price for it: ");
            double price = sc.nextDouble();
            s.setPrice(price);
            return s;
    }

    private void write(String fileName, List list) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (Object o : list) {
                bw.write(String.valueOf(o));
                bw.newLine();
            }
        }
    }

    private void read(String fileName) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            if (fileName.equals(FILE_NAME_MANUFACTURE)) {
                while ((line = br.readLine()) != null) {
                    String[] strings = line.split(",");
                    String s1 = strings[0].substring(17);
                    String s2 = strings[1].substring(9, strings[1].length()-1);
                    Manufacture manufacture = new Manufacture(s1, s2);
                    manufactureSet.add(manufacture);
                }
            }
                while ((line = br.readLine()) != null) {
                    String[] strings = line.split(",");
                    String s1 = strings[0].substring(14);
                    String s2 = strings[1].substring(30);
                    String s3 = strings[2].substring(9);
                    String s4 = strings[3].substring(15);
                    String s5 = strings[4].substring(7,strings[4].length()-1);
                    Souvenir souvenir = new Souvenir(s1,new Manufacture(s2,s3),s4,Double.parseDouble(s5));
                    souvenirList.add(souvenir);
                }
        }
    }
}