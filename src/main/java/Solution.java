public class Solution {
    private double[][] x0;
    private double[][] gradX; //f(x)
    private double[][] H;
    private double[][] X;
    private static double e = 0.000001;

    public Solution(double[] xi) {
        x0 = new double[xi.length][1];
        for (int i = 0; i < x0.length; i++) {
            x0[i][0] = xi[i];
        }
        gradX = new double[xi.length][1];
        H = new double[xi.length][xi.length];
        X = new double[xi.length][1];
        X[0][0] = x0[0][0];
        X[1][0] = x0[1][0];

    }

    private double[][] zFunction(double x, double y) {
        double part1 = Math.pow(Math.E, (-x - y));
        double dx = part1 * (2 * x * x - 4 * x + 3 * y * y);
        double dy = part1 * (2 * x * x - 6 * y + 3 * y * y);

        return new double[][]{{dx},{dy}};
    }

    /*private double[][] zFunction2(double x, double y) {
        double[][] result = new double[2][1];
        double part2 = Math.pow(x + 2 * y - 1, 3);
        double part1 = Math.pow((2 * x + y + 1), 3);
        double dx = 8 * part1 + 0.4 * part2;
        double dy = 4 * part1 + 0.8 * part2;
        result[0][0] = dx;
        result[1][0] = dy;
        return result;
    }*/

    private double[][] hessianMatrix(double x, double y) {

        double part = (2 * x * x + 3 * y * y) * Math.exp(-x - y);
        double dx = 8 * x * Math.exp(-x - y) - part - 4 * Math.exp(-x - y);
        double dy = 12 * y * Math.exp(-x - y) - part - 6 * Math.exp(-x - y);
        double dxDy = 4 * x * Math.exp(-x - y) + 6 * y * Math.exp(-x - y) -
                part;

        return new double[][]{{dx, dxDy}, {dxDy, dy}};
    }

    /*private double[][] hessianMatrix2(double x, double y) {
        double part = Math.pow(2 * x + y + 1, 2);
        double dx = 48 * part + 1.2 * part;
        double dy = 12 * part + 4.8 * part;
        double dxDy = 24 * part + 2.4 * part;
        return new double[][]{{dx, dxDy}, {dxDy, dy}};
    }*/

    private double modGrad(double[][] deltaF) {
        return Math.sqrt(Math.pow(deltaF[0][0], 2) + Math.pow(deltaF[1][0], 2));
    }

    public double[][] method() {

        double[][] s;
        double gradMod;
        int iter = 0;
        double[][] invertH;

        gradX = zFunction(X[0][0], X[1][0]);
        gradMod = modGrad(gradX);

        while (!(gradMod < e)) {
            iter++;
            System.out.printf("\n----------Iter: %d --------------\n", iter);
            System.out.printf("F(X%d)", iter - 1);
            Matrix.printMartix(gradX);

            H = hessianMatrix(X[0][0], X[1][0]);
            System.out.print("\nH: ");
            Matrix.printMartix(H);

            invertH = Matrix.invert(H);
            System.out.print("\nInvert H: ");
            Matrix.printMartix(H);

            s = Matrix.prodMatrix(invertH, gradX);
            X = Matrix.subMatrix(X, s);
            System.out.printf("\nX%d: ", iter);
            Matrix.printMartix(X);

            gradX = zFunction(X[0][0], X[1][0]);
            gradMod = modGrad(gradX);
        }

        return X;
    }
}