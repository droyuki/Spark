import org.apache.spark.mllib.classification.SVMModel;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.classification.LogisticRegressionModel;
import org.apache.spark.mllib.classification.LogisticRegressionWithSGD;
/**
 * Created by wan-en-fu on 2014/11/19.
 */
public class JsonLRModelConvertor {
    private Double intercept;
    private double[] weights;
    private Double max;
    private Double min;
    public LogisticRegressionModel getModelFromCurrentData() {
        Vector v = Vectors.dense(weights);
//        return new LogisticRegressionModel(v, intercept);
        return new LogisticRegressionModel(v, intercept).setThreshold(min+0.9*(max-min));
    }
    public void setIntercept(Double intercept) {
        this.intercept = intercept;
    }
    public void setWeights(double[] weights) {
        this.weights = weights;
    }
    public void setMax(Double max) {
        this.max = max;
    }
    public void setMin(Double min) {
        this.min = min;
    }
    public Double getIntercept() {
        return intercept;
    }
    public double[] getWeights() {
        return weights;
    }
    public Double getMax() {
        return max;
    }
    public Double getMin() {
        return min;
    }
}