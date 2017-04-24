package algorithm;

public class MixColumns {

    /**
     * Galois table used for mixColumns
     */
    public static final int[][] galois = {
            {0x02, 0x03, 0x01, 0x01},
            {0x01, 0x02, 0x03, 0x01},
            {0x01, 0x01, 0x02, 0x03},
            {0x03, 0x01, 0x01, 0x02}};

  /*   * Performed by mapping each element in the current matrix with the value
     * returned by its helper function.*/

    public static int[][] mixColumns(int[][] state){

        int[][] tarr = new int[4][4];
        for(int i = 0; i < 4; i++)
        {
            System.arraycopy(state[i], 0, tarr[i], 0, 4);
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                state[i][j] = mcHelper(tarr, galois, i, j);
            }
        }

    return state;
    }

    /**
     * Helper method of mixColumns in which compute the mixColumn formula on each element.
     * @param arr passed in current matrix
     * @param g the galois field
     * @param i the row position
     * @param j the column position
     * @return the computed mixColumns value
     */

    private static int mcHelper(int[][] arr, int[][] g, int i, int j)
    {
        int mcsum = 0;
        for (int k = 0; k < 4; k++) {
            int a = g[i][k];
            int b = arr[k][j];
            mcsum ^= mcCalc(a, b);
        }
        return mcsum;
    }

    private static int mcCalc(int a, int b) //Helper method for mcHelper
    {
        if (a == 1) {
            return b;
        } else if (a == 2) {
            return MCTables.mc2[b / 16][b % 16];
        } else if (a == 3) {
            return MCTables.mc3[b / 16][b % 16];
        }
        return 0;
    }



}
