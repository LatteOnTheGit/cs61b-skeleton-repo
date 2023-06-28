package timingtest;
import edu.princeton.cs.algs4.Stopwatch;
import org.checkerframework.checker.units.qual.A;

/**
 * Created by hug.
 */
public class TimeAList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeAListConstruction();
    }

    public static void timeAListConstruction() {
        // TODO: YOUR CODE HERE
        int[] n_list={1000,2000,4000,8000,16000,32000,64000,128000,1000000};
        AList N = new AList<Integer>();
        AList times = new AList<Double>();
        for(int i : n_list){
            int count = 0;
            AList test_case = new AList<Integer>();
            Stopwatch sw = new Stopwatch();
            while (i>count){
                test_case.addLast(1);
                count+=1;
            }
            double timeInSeconds = sw.elapsedTime();
            N.addLast(i);
            times.addLast(timeInSeconds);
        }
        printTimingTable(N,times,N);
    }
}
