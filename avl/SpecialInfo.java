package alg;

/**
 * Created by ekishigo on 28.4.16.
 */
public class SpecialInfo {
    public int nodesOnLevel[];

    public SpecialInfo() {
        nodesOnLevel = new int[18];
        nodesOnLevel[0] = 1;
    }

    public double getAvg() {
        double n = 0;
        double sum = 0;
        for (int i = 0; i < nodesOnLevel.length; i++) {
            int a = nodesOnLevel[i];
            int s = i * a;
            n += a;
            sum += s;
        }
        return sum / n;
    }
}
