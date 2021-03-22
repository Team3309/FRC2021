package friarLib2.math;

public class LinearRegression {

    private double m; //slope
    private double b; //y-intercept

    /**
     * Compute m and b.
     * 
     * I (Mark) copied this code from https://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/LinearRegression.java.html
     * and don't understand any of it, but it seems to work great!
     * 
     * @param data a 2D array of x and y coordinates
     */
    public LinearRegression (double[][] data) {
        int n = data.length;

        // first pass
        double sumx = 0.0, sumy = 0.0, sumx2 = 0.0;
        for (int i = 0; i < n; i++) {
            sumx  += data[i][0];
            sumx2 += data[i][0]*data[i][0];
            sumy  += data[i][1];
        }
        double xbar = sumx / n;
        double ybar = sumy / n;

        // second pass: compute summary statistics
        double xxbar = 0.0, yybar = 0.0, xybar = 0.0;
        for (int i = 0; i < n; i++) {
            xxbar += (data[i][0] - xbar) * (data[i][0] - xbar);
            yybar += (data[i][1] - ybar) * (data[i][1] - ybar);
            xybar += (data[i][0] - xbar) * (data[i][1] - ybar);
        }
        m = xybar / xxbar;
        b = ybar - m * xbar;
    }

    public double evaluate (double x) {
        return m*x + b;
    }

    public double getSlope () {
        return m;
    }

    public double getIntercept () {
        return b;
    }
}
