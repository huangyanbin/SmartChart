package com.daivd.chart.provider.component.line;

import android.graphics.Path;

import java.util.LinkedList;
import java.util.List;

/**曲线内容绘制
 * Created by huang on 2017/9/27.
 */

public class CurveLineModel  implements ILineModel {
    private final int STEP = 12;

    @Override
    public Path getLinePath(List<Float> pointX, List<Float> pointY) {
        List<Cubic> calculate_x = calculate(pointX);
        List<Cubic> calculate_y = calculate(pointY);
        Path path = new Path();
        if (null != calculate_x && null != calculate_y && calculate_y.size() >= calculate_x.size()) {
            path.moveTo(calculate_x.get(0).eval(0), calculate_y.get(0).eval(0));
            for (int i = 0; i < calculate_x.size(); i++) {
                for (int j = 1; j <= STEP; j++) {
                    float u = j / (float) STEP;
                    path.lineTo(calculate_x.get(i).eval(u), calculate_y.get(i).eval(u));
                }
            }
        }
        return path;
    }



    /**
     * 计算曲线.
     * @param x
     * @return
     */
    private List<Cubic> calculate(List<Float> x) {
        if (null != x && x.size() > 0) {
            int n = x.size() - 1;
            float[] gamma = new float[n + 1];
            float[] delta = new float[n + 1];
            float[] D = new float[n + 1];
            int i;
            gamma[0] = 1.0f / 2.0f;
            for (i = 1; i < n; i++) {
                gamma[i] = 1 / (4 - gamma[i - 1]);
            }
            gamma[n] = 1 / (2 - gamma[n - 1]);

            delta[0] = 3 * (x.get(1) - x.get(0)) * gamma[0];
            for (i = 1; i < n; i++) {
                delta[i] = (3 * (x.get(i + 1) - x.get(i - 1)) - delta[i - 1]) * gamma[i];
            }
            delta[n] = (3 * (x.get(n) - x.get(n - 1)) - delta[n - 1]) * gamma[n];

            D[n] = delta[n];
            for (i = n - 1; i >= 0; i--) {
                D[i] = delta[i] - gamma[i] * D[i + 1];
            }

            List<Cubic> cubicList = new LinkedList<>();
            for (i = 0; i < n; i++) {
                Cubic c = new Cubic(x.get(i), D[i], 3 * (x.get(i + 1) - x.get(i)) - 2 * D[i] - D[i + 1], 2 * (x.get(i) - x.get(i + 1)) + D[i] + D[i + 1]);
                cubicList.add(c);
            }
            return cubicList;
        }
        return null;
    }




    private class Cubic {

        float a, b, c, d; /* a + b*u + c*u^2 +d*u^3 */

        Cubic(float a, float b, float c, float d) {
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
        }

        /** evaluate cubic */
        float eval(float u) {
            return (((d * u) + c) * u + b) * u + a;
        }
    }
}
