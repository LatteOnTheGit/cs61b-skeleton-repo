package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeSLList {
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
        timeGetLast();
    }

    public static void timeGetLast() {
        // TODO: YOUR CODE HERE
        int ops_number = 1000;
        int[] n_list={1000,2000,4000,8000,16000,32000,64000};
        AList N = new AList<Integer>();
        AList times = new AList<Double>();
        AList ops = new AList<Integer>();
        for(int i:n_list){
            int count = 0;
            SLList test_case = new SLList<Integer>();
            while (i>count){
                test_case.addLast(1);
                count+=1;
            }
            int count2 = 0;
            Stopwatch sw = new Stopwatch();
            while (count2 < ops_number){
                test_case.getLast();
                count2 += 1;
            }
            double timeInSeconds = sw.elapsedTime();
            N.addLast(i);
            ops.addLast(ops_number);
            times.addLast(timeInSeconds);
        }
        printTimingTable(N,times,ops);
    }

}
