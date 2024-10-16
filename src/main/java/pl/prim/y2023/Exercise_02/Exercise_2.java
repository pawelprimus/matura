package pl.prim.y2023.Exercise_02;


public class Exercise_2 {
    public static void main(String[] args) throws Exception {
        System.out.println("HELLO");


        System.out.println("2 - 1 " + game(new int[]{1, 2, 3}, 5));
        System.out.println("2 - 2 " + game(new int[]{1, 2, 5, 10}, 14));
        System.out.println("2 - 3 " + game(new int[]{13, 5, 5, 2, 7}, 17));
        System.out.println("2 - 4 " + game(new int[]{7, 6, 5, 4, 3, 2, 1}, 25));

        // 1 = TRUE
        // 2 = FALSE
        // 3 = TRUE
        // 4 = TRUE

        int[] A = new int[20];
        for (int i = 0; i < A.length; i++) {
            A[i] = (i + 1) * 5;
        }
        System.out.println("2.2 = " + gameCount(A, 500));
        System.out.println();
        System.out.println(all200AreTrue(new int[]{}));

    }

    public static boolean all200AreTrue(int A[]) {
        for (int i = 1; i <= 200; i++) {
            if (!game(A, i)) {
                return false;
            }
        }
        return true;
    }

    public static boolean game(int A[], int s) {
        int n = A.length;

        int[] B = new int[s + 1];
        B[0] = -1;

        for (int k = 1; k <= n; k++) {
            for (int i = s; i >= 0; i--) {
                if (i - A[k - 1] >= 0) {
                    if ((B[i - A[k - 1]] == -1) && B[i] == 0) {
                        B[i] = -1;
                    }
                }
            }
        }

        return B[s] == -1;
    }

    public static int gameCount(int A[], int s) {
        int n = A.length;

        int[] B = new int[s + 1];
        B[0] = -1;

        int sum = 0;
        for (int k = 1; k <= n; k++) {
            for (int i = s; i >= 0; i--) {
                if (i - A[k - 1] >= 0) {
                    if ((B[i - A[k - 1]] == -1) && B[i] == 0) {
                        B[i] = -1;
                    }
                }
            }
        }

        for (int i = 0; i < B.length; i++) {
            if (B[i] == -1) {
                sum++;
            }
        }

        return sum;
    }

    public static void print(int[] matrix) {

        for (int i = 0; i < matrix.length; i++) {
            System.out.println(matrix[i]);
        }
    }

}
