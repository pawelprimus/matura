package pl.prim.y2023.Exercise_05;


import pl.prim.FileReader;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Exercise_5 {
    public static void main(String[] args) throws Exception {


        String[] ships = FileReader.readFileAsString("05", "statki.txt").split("[\\n]");
        String[] codes = FileReader.readFileAsString("05", "sklep_internetowy.txt").split("[\\n]");
        String[] arrivals = FileReader.readFileAsString("05", "przybycia.txt").split("[\\n]");

        List<Ship> shipList = new ArrayList<>();
        for (int i = 1; i < ships.length; i++) {
            shipList.add(new Ship(ships[i]));
        }

        List<Code> codeList = new ArrayList<>();
        for (int i = 1; i < codes.length; i++) {
            codeList.add(new Code(codes[i]));
        }

        List<Arrival> arrivalList = new ArrayList<>();
        for (int i = 1; i < arrivals.length; i++) {
            arrivalList.add(new Arrival(arrivals[i]));
        }

        int arrivalOf2016 = arrivalList.stream().map(i -> i.getArrivalDate().getYear()).filter(i -> i == 2016).toList().size();
        int arrivalOf2017 = arrivalList.stream().map(i -> i.getArrivalDate().getYear()).filter(i -> i == 2017).toList().size();
        int arrivalOf2018 = arrivalList.stream().map(i -> i.getArrivalDate().getYear()).filter(i -> i == 2018).toList().size();
        int arrivalOf2019 = arrivalList.stream().map(i -> i.getArrivalDate().getYear()).filter(i -> i == 2019).toList().size();
        System.out.println("5.1");
        System.out.println("2016 " + arrivalOf2016);
        System.out.println("2017 " + arrivalOf2017);
        System.out.println("2018 " + arrivalOf2018);
        System.out.println("2019 " + arrivalOf2019);


        for (Arrival arrival : arrivalList) {
            for (Ship ship : shipList) {
                if (arrival.getNumber().equals(ship.getNumber())) {
                    arrival.setShip(ship);
                    break;
                }
            }
        }

        List<Wharf> wharves = new ArrayList<>();
        for (Arrival arrival : arrivalList) {
            String strWharf = arrival.getWharf();


            Optional<Wharf> optionalWharf = wharves.stream().filter(i -> i.getWarf().equals(strWharf)).findAny();
            Code code = codeList.stream().filter(i -> i.getFlag().equals(arrival.getFlag())).findAny().get();
            Ship ship = arrival.getShip();

            if (optionalWharf.isPresent()) {
                optionalWharf.get().addShip(ship);
                optionalWharf.get().addCode(code);
            } else {
                Wharf wharf = new Wharf(strWharf);
                wharf.addShip(ship);
                wharves.add(wharf);
                wharf.addCode(code);
            }
        }

        wharves.sort(Comparator.comparing(o -> o.warf));
        System.out.println();
        System.out.println("5.2 ");
        for (Wharf wharf : wharves) {
            System.out.println(wharf);
        }

        System.out.println();
        System.out.println("5.3 ");
        for (Wharf wharf : wharves) {
            if(wharf.notEurupe()){
                System.out.println(wharf.getWarf());
            }
        }
//        for (Ship ship : shipList) {
//            for (Arrival arrival : arrivalList) {
//                if (ship.getNumber().equals(arrival.getNumber())) {
//                    ship.addArrival(arrival);
//                }
//            }
//        }


    }

}

class Ship {
    String number;
    String name;
    int load;
    List<Arrival> arrivals = new ArrayList<>();

    public Ship(String line) {
        String[] splited = line.split(";");
        this.number = splited[0];
        this.name = splited[1];
        this.load = Integer.valueOf(splited[2].trim());
    }

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public int getLoad() {
        return load;
    }

    public List<Arrival> getArrivals() {
        return arrivals;
    }

    public void addArrival(Arrival arrival) {
        arrivals.add(arrival);
    }

    public String toPrint() {
        return name + " " + load;
    }
}

class Code {
    String flag;
    String country;
    String continent;

    public Code(String line) {
        String[] splited = line.split(";");
        this.flag = splited[0];
        this.country = splited[1].trim();
        this.continent = splited[2].trim();
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }
}

class Arrival {
    String lp;
    LocalDate arrivalDate;
    String number;
    String flag;
    String wharf;
    Ship ship;

    public Arrival(String line) {
        String[] splited = line.split(";");
        String europeanDatePattern = "dd.MM.yyyy";
        DateTimeFormatter europeanDateFormatter = DateTimeFormatter.ofPattern(europeanDatePattern);
        LocalDate localDate = LocalDate.parse(splited[1], europeanDateFormatter);
        this.lp = splited[0];
        this.arrivalDate = localDate;
        this.number = splited[2];
        this.flag = splited[3];
        this.wharf = splited[4].trim();
    }

    public String getLp() {
        return lp;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public String getNumber() {
        return number;
    }

    public String getFlag() {
        return flag;
    }

    public String getWharf() {
        return wharf;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }
}

class Wharf {
    String warf;
    List<Ship> maxLoadShips = new ArrayList<>();
    private int maxLoad = 0;
    private List<Code> codes = new ArrayList<>();

    public Wharf(String warf) {
        this.warf = warf;
    }

    public void addCode(Code code) {
        if (!codes.contains(code)) {
            codes.add(code);
        }
    }

    public boolean notEurupe() {
        for (Code code : codes) {
            if (code.continent.equals("EUROPA")) {
                return false;
            }
        }
        return true;
    }

    public void addShip(Ship ship) {
        if (ship.getLoad() >= maxLoad) {
            if (ship.getLoad() == maxLoad) {
                if (!maxLoadShips.contains(ship)) {
                    maxLoadShips.add(ship);
                }
            } else {
                maxLoadShips = new ArrayList<>();
                maxLoadShips.add(ship);
                maxLoad = ship.getLoad();
            }
        }
    }


    public String getWarf() {
        return warf;
    }

    @Override
    public String toString() {
        return warf + " | " + maxLoadShips.stream().map(Ship::toPrint).collect(Collectors.joining(" "));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wharf wharf = (Wharf) o;
        return Objects.equals(warf, wharf.warf);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(warf);
    }
}
