public class RandomNumberGenerator {
    public static void main(String[] args) {
        for (int i = 0; i < 12; ++i) {
            System.out.println((i + 1) + " " + (int)(Math.random() * 10000));
        }
    }
}
/*
1 5091
2 4009
3 1538
4 8486
5 7266
6 1289
7 6669
8 4076
9 9415
10 5854
11 4411
12 653
 */
