import java.util.Random;

/**
 * Created by hug.
 */
public class ExperimentHelper {

    /** Returns the internal path length for an optimum binary search tree of
     *  size N. Examples:
     *  N = 1, OIPL: 0
     *  N = 2, OIPL: 1
     *  N = 3, OIPL: 2
     *  N = 4, OIPL: 4
     *  N = 5, OIPL: 6
     *  N = 6, OIPL: 8
     *  N = 7, OIPL: 10
     *  N = 8, OIPL: 13
     */
    private static double log(int N, int base) {
        return Math.log(N) / Math.log(base);
    }

    /**
     * if 2^x <= N < 2^(x + 1) then the depth of the node in this layer should be x
     * and there will be 2^x nodes in this layer
     * @param N
     * @return
     */
    public static int optimalIPL(int N) {
        int ans = 0;
        int layer = 0;
        double numOfLayer = Math.pow(2, layer);
        int depthOfLayer = layer;
        int cumulativeNum = 1;
        while (cumulativeNum < N) {
            ans += numOfLayer * depthOfLayer;
            layer++;
            numOfLayer = Math.pow(2, layer);
            depthOfLayer = layer;
            cumulativeNum += numOfLayer;
        }
        cumulativeNum -= numOfLayer;
        int residualNum = N - (cumulativeNum);
        ans += residualNum * layer;
        return ans;

    }

    /** Returns the average depth for nodes in an optimal BST of
     *  size N.
     *  Examples:
     *  N = 1, OAD: 0
     *  N = 5, OAD: 1.2
     *  N = 8, OAD: 1.625
     * @return
     */
    public static double optimalAverageDepth(int N) {
        double totalDepth = optimalIPL(N);
        return totalDepth / N;
    }


    public static double randomDeleteAndInsert(BST<Integer> integerBST, int bound, boolean isRandom) {
        Random random = new Random();
        int val = random.nextInt(bound);
        if (isRandom) {
            integerBST.deleteTakingRandom(integerBST.getRandomKey());
        } else {
            integerBST.deleteTakingSuccessor(integerBST.getRandomKey());
        }
        int size = integerBST.size();
        while (size == integerBST.size()) {
            val = random.nextInt(bound);
            integerBST.add(val);
        }
        return integerBST.calAvgDepth();
    }



}
