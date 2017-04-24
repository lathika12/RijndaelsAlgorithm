package algorithm;

public class ShiftRow {

    /**
     * Performing a left shift on each row of the matrix.
     * Left shifts the nth row n-1 times.
     */
    public static int[][] shiftRow(int[][] state) {

        for (int i = 1; i < state.length; i++) {
            state[i] = leftrotate(state[i], i);
        }

        return state;
    }

    /**
     * Left rotates a given array. The size of the array is assumed to be 4.
     * If the number of times to rotate the array is divisible by 4, return the array
     * as it is.
     */

    public static int[] leftrotate(int[] state, int times) {

        assert(state.length == 4);
        if (times % 4 == 0) {
            return state;
        }
        while (times > 0) {
            int temp = state[0];
            for (int i = 0; i < state.length - 1; i++) {
                state[i] = state[i + 1];
            }
            state[state.length - 1] = temp;
            --times;
        }
        return state;
    }

}

