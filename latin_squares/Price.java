package alg;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ekishigo on 10.3.16.
 */
public class Price implements Comparable<Price>{
    public final int value;
    public final int X;
    public final int Y;
    public final int type;

    public Price(int value, int x, int y, int type) {
        this.value = value;
        X = x;
        Y = y;
        this.type = type;
    }

    public int compareTo(Price o) {
        return this.value - o.value;
    }

    @Override
    public String toString() {
        return "Price{" +
                "value=" + value +
                ", X=" + X +
                ", Y=" + Y +
                ", type=" + type +
                '}';
    }

}
