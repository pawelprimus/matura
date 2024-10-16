package pl.prim.y2023.Exercise_04;


import pl.prim.FileReader;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Exercise_4 {
    public static void main(String[] args) throws Exception {
        System.out.println("HELLO");


        String[] input = FileReader.readFileAsString("04", "brenna.txt").split("[\\n]");

        List<Measure> measures = new ArrayList<>();
        for (String str : input) {
            String[] splited = str.split("\\s+");
            String date = splited[0];
            String time = splited[1];
            double temeprature = Double.valueOf(splited[2].replaceAll(",", "."));
            double drop = Double.valueOf(splited[3].replaceAll(",", "."));

            String europeanDatePattern = "dd.MM.yyyy";
            DateTimeFormatter europeanDateFormatter = DateTimeFormatter.ofPattern(europeanDatePattern);
            LocalDate localDate = LocalDate.parse(date, europeanDateFormatter);
            LocalTime localTime = LocalTime.parse(time);
            LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
            //System.out.println(localDateTime);

            Measure measure = new Measure(localDateTime, temeprature, drop);
            measures.add(measure);
        }
        LocalDate loopDate = measures.getFirst().getLocalDateTime().toLocalDate();
        List<Measure> dayMeasures = new ArrayList<>();
        List<Day> days = new ArrayList<>();
        for (int i = 0; i < measures.size(); i++) {
            LocalDate currentDate = measures.get(i).getLocalDateTime().toLocalDate();

            if (currentDate.equals(loopDate)) {
                dayMeasures.add(measures.get(i));
            } else {
                Day day = new Day(dayMeasures);
                dayMeasures = new ArrayList<>();
                days.add(day);
                dayMeasures.add(measures.get(i));
                loopDate = currentDate;
            }
        }

        double maxAmplitude = Double.MIN_VALUE;
        Day dayOfMaxAplitude = days.getFirst();
        for (Day day : days) {
            if (day.getAmplitude() > maxAmplitude) {
                maxAmplitude = day.getAmplitude();
                dayOfMaxAplitude = day;
            }
        }

        // 4.1
        System.out.println("4.1");
        System.out.println("MAX amplitude day - " + dayOfMaxAplitude.getDay() + " " + maxAmplitude);

        // 4.2
        double[] temperaturesInHours = new double[24];
        int amountOfDays = measures.size() / 24;
        int iterator = 0;
        for (Measure measure : measures) {
            temperaturesInHours[iterator] = temperaturesInHours[iterator] + measure.getTemperature();
            if (iterator == 23) {
                iterator = 0;
            } else {
                iterator++;
            }
        }
        System.out.println();
        System.out.println("4.2");
        final DecimalFormat df = new DecimalFormat("0.00");
        for (int i = 0; i < 24; i++) {
            System.out.println(i + " " + df.format(temperaturesInHours[i] / amountOfDays));
        }

        //4.3
        List<Measure> longestRainList = new ArrayList<>();
        List<Measure> rainList = new ArrayList<>();

        for (Measure measure : measures) {
            if (measure.isRainy()) {
                rainList.add(measure);
            } else {
                if (rainList.size() > longestRainList.size()) {
                    longestRainList = new ArrayList<>(rainList);
                }
                rainList = new ArrayList<>();
            }
        }

        System.out.println();
        System.out.println("4.3");
        System.out.println(longestRainList.size());
        System.out.println("RAIN DURATION " + longestRainList.size());
        System.out.println("START DATE " + longestRainList.getFirst().getLocalDateTime());
        System.out.println("END DATE " + longestRainList.getLast().getLocalDateTime());
        System.out.println("DROP SUM " + longestRainList.stream().map(Measure::getOpad).mapToDouble(Double::doubleValue).sum());

        // 4.4
        List<Measure> snow = new ArrayList<>();
        int sumOfSnowplowIntervention = 0;
        Map<LocalDate, Integer> sumOfSnowplowInterventionMap = new TreeMap<>();
        for (Measure measure : measures) {

            double sumOfDrop = snow.stream().map(Measure::getOpad).mapToDouble(Double::doubleValue).sum();
            if (sumOfDrop > 4) {
                sumOfSnowplowIntervention++;

                LocalDate currentLocalDate = measure.getLocalDateTime().toLocalDate();
                int count = 0;
                if (sumOfSnowplowInterventionMap.containsKey(currentLocalDate)) {
                    count = sumOfSnowplowInterventionMap.get(currentLocalDate);
                }
                sumOfSnowplowInterventionMap.put(currentLocalDate, count + 1);

                snow = new ArrayList<>();
            } else {
                if (measure.isSnowy()) {
                    snow.add(measure);
                } else {
                    if (measure.isRainy()) {
                        snow = new ArrayList<>();
                    }
                }
            }
        }


        System.out.println();
        System.out.println("4.4");
        System.out.println("SUM OF SNOWPLOW INTERVENTION " + sumOfSnowplowIntervention);
        // 4.4 b) = 2019-02-22
        int sum = 0;
        for (Map.Entry<LocalDate, Integer> entry : sumOfSnowplowInterventionMap.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue().toString());
            sum += entry.getValue();
        }
        System.out.println(sum);

    }

}

class Measure {
    LocalDateTime localDateTime;
    double temperature;
    double opad;

    public Measure(LocalDateTime localDateTime, double temperature, double opad) {
        this.localDateTime = localDateTime;
        this.temperature = temperature;
        this.opad = opad;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getOpad() {
        return opad;
    }

    public boolean isRainy() {
        return temperature > 0 && opad > 0;
    }

    public boolean isSnowy() {
        return temperature <= 0 && opad > 0;
    }

    @Override
    public String toString() {
        return "Measure{" +
                "localDateTime=" + localDateTime +
                ", temperature=" + temperature +
                ", opad=" + opad +
                '}';
    }
}

class Day {
    List<Measure> measures;

    public Day(List<Measure> measures) {
        this.measures = measures;
    }

    public LocalDate getDay() {
        return measures.getFirst().getLocalDateTime().toLocalDate();
    }

    public double getAmplitude() {
        double max = measures.stream().map(Measure::getTemperature).mapToDouble(Double::doubleValue).max().getAsDouble();
        double min = measures.stream().map(Measure::getTemperature).mapToDouble(Double::doubleValue).min().getAsDouble();
        return max - min;
    }
}

