import benchmark.BenchmarkRunner;

public class Main {

    public static void main(String[] args) {
        try {
            BenchmarkRunner runner = new BenchmarkRunner(args[0]);
            runner.run();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Please provide input file in csv.");
        }
    }
}
