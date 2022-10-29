
package net.htlgkr.KohlbauerS190178.hue5;

/**
 * @author bmayr
 */

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * Class wrapping methods for implementing reciprocal array sum in parallel.
 */
public final class ReciprocalArraySum {

    /**
     * Default constructor.
     */
    private ReciprocalArraySum() {
    }

    /**
     * Sequentially compute the sum of the reciprocal values for a given array.
     *
     * @param input Input array
     * @return The sum of the reciprocals of the array input
     */
    protected static double seqArraySum(final double[] input) {
        double sum = 0;

        for (double d : input) {
            if (d != 0)
                sum += ((double)1 / d);
        }

        return sum;
        // ToDo: Compute sum of reciprocals of array elements
    }


    /**
     * This class stub can be filled in to implement the body of each task
     * created to perform reciprocal array sum in parallel.
     */
    private static class ReciprocalArraySumTask extends RecursiveAction {
        /**
         * Starting index for traversal done by this task.
         */
        private final int startIndexInclusive;
        /**
         * Ending index for traversal done by this task.
         */
        private final int endIndexExclusive;
        /**
         * Input array to reciprocal sum.
         */
        private final double[] input;
        /**
         * Intermediate value produced by this task.
         */
        private double value;

        private static int SEQUENTIAL_THRESHOLD = 50000;

        /**
         * Constructor.
         *
         * @param setStartIndexInclusive Set the starting index to begin
         *                               parallel traversal at.
         * @param setEndIndexExclusive   Set ending index for parallel traversal.
         * @param setInput               Input values
         */
        ReciprocalArraySumTask(final int setStartIndexInclusive,
                               final int setEndIndexExclusive, final double[] setInput) {
            this.startIndexInclusive = setStartIndexInclusive;
            this.endIndexExclusive = setEndIndexExclusive;
            this.input = setInput;
        }

        /**
         * Getter for the value produced by this task.
         *
         * @return Value produced by this task
         */
        public double getValue() {
            return value;
        }

        @Override
        protected void compute() {
            // TODO: Implement Thread forking on Threshold value. (If size of
            // array smaller than threshold: compute sequentially else, fork 
            // 2 new threads

            int mid = startIndexInclusive + (endIndexExclusive - startIndexInclusive) / 2;

            if (endIndexExclusive - startIndexInclusive < SEQUENTIAL_THRESHOLD) {
                value += seqArraySum(Arrays.copyOfRange(input, startIndexInclusive,endIndexExclusive));
            } else {

                ReciprocalArraySumTask left = new ReciprocalArraySumTask(startIndexInclusive, mid, input);
                ReciprocalArraySumTask right = new ReciprocalArraySumTask(mid, endIndexExclusive, input);

                invokeAll(left, right);

                value += left.value+ right.value;
            }
        }
    }


    /**
     * TODO: Extend the work you did to implement parArraySum to use a set
     * number of tasks to compute the reciprocal array sum.
     *
     * @param input    Input array
     * @param numTasks The number of tasks to create
     * @return The sum of the reciprocals of the array input
     */
    protected static double parManyTaskArraySum(final double[] input, final int numTasks) {

        // ToDo: Start Calculation with help of ForkJoinPool

        ForkJoinPool pool = new ForkJoinPool(numTasks);
        ReciprocalArraySumTask task = new ReciprocalArraySumTask(0, input.length, input);
        pool.execute(task);
        task.join();
        pool.shutdown();
        return task.value;
    }
}

