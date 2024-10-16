package pl.prim.y2023.Exercise_03;


import pl.prim.FileReader;

public class Exercise_3 {
    public static void main(String[] args) throws Exception {
        System.out.println("HELLO");

        // 3.1
        System.out.println(calculate(3, 3, 11));
        // 2 = 5
        int x = 1;
        while (calculate(5, x, 31) != 25) {
            x++;
        }
        // 3 = 2
        System.out.println(x);

        x = 1;
        while (calculate(2, x, 59) != 5) {
            x++;
        }
        //4 = 6
        System.out.println(x);

        System.out.println(calculate(9, 2, 80)); // 5 = 1

        String[] input = FileReader.readFileAsString("03", "liczby.txt").split("[\\n]");


        int primes = 0;
        int wzgledniePierwsze = 0;
        int isExists = 0;
        for (String str : input) {
            String[] splited = str.split(" ");
            if (isPrime(splited[0])) {
                primes++;
            }
            if (wzgledniePierwse(splited[0], splited[1])) {
                wzgledniePierwsze++;
            }
            // M a b
            if (czyIstniejeX(Integer.valueOf(splited[0]), Integer.valueOf(splited[1]), Integer.valueOf(splited[2]))) {
                isExists++;
            }

            System.out.println(splited[0]);
            System.out.println(splited[1]);
            System.out.println(splited[2]);
        }

        System.out.println("3.3 PRIMES " + primes);
        System.out.println("3.4 wzgledniePierwsze " + wzgledniePierwsze);
        System.out.println("3.5 isExistsX " + isExists); // 764
    }

    public static boolean isPrime(String num) {
        int number = Integer.valueOf(num);

        for (int i = 2; i < number; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean wzgledniePierwse(String num1, String num2) {
        int M = Integer.valueOf(num1);
        int a = Integer.valueOf(num2);

        int max = Math.max(M, a);

        for (int i = 2; i < max; i++) {
            if (M % i == 0 && a % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isExistsX(int M, int a, double b) {

        for (int x = 0; x < M - 1; x++) {
            if (calculate(a, x, M) == b) {
                return true;
            }
        }
        return false;
    }


    public static double calculate(int a, int x, int M) {
        System.out.println(Math.pow(a, x));
        return Math.pow(a, x) % M;
    }

    private static boolean czyIstniejeX(int M, int a, int b) {
        int wynik = 1;
        for (int x = 0; x < M; x++) {
            if (wynik % M == b) {
                return true;
            }
            wynik = (wynik * a) % M;
        }
        return false;
    }

}
