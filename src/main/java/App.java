public class App {
    private static double e = 0.000001;

    public static void main(String[] args) {
        double[] xi = {2.5, 2.5};
        Solution s1 = new Solution(xi);
        double[][] result = s1.method();
        System.out.println("\n-------Result--------");
        Matrix.printMartix(result);
    }
}