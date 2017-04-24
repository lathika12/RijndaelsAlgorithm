import algorithm.ByteSubstitution;
import algorithm.KeySchedule;
import algorithm.MixColumns;
import algorithm.ShiftRow;

public class RijndaelMainMethod {


    public static void main(String args[]) throws Exception {

        // We'll require a block of plain text to be encrypted, and a Key. Plain text will be divided into State matrix,
        // and key will be applied with Cipher matrix. Let us consider a simple scenario for 128-bit.

        String line = "SomeStringThatIsSupposedTobeEncryptedByDividingIntoParticularLengthOfBytes";
        String key = "ThisMustB128BKey";
        int[][] State = null;
        int[][] RoundKey = null;
        int numRounds = 10 + (((key.length() * 4 - 128) / 32));

        State = new int[][]{
                {0x02, 0x03, 0x01, 0x01},
                {0x01, 0x02, 0x03, 0x01},
                {0x01, 0x01, 0x02, 0x03},
                {0x03, 0x01, 0x01, 0x02}};


        RoundKey = new int[][]{
                {0x02, 0x03, 0x01, 0x01},
                {0x01, 0x02, 0x03, 0x01},
                {0x01, 0x01, 0x02, 0x03},
                {0x03, 0x01, 0x01, 0x02}};

        System.out.println("Line is : " + line);

        if (line.matches("[0-9A-F]+")) {

            if (line.length() < 32) {
                line = String.format("%032x", Integer.parseInt(line, 16));
                System.out.println("Line is XXXX : " + line);
            }
            State = new int[4][4];

            for (int i = 0; i < 4; i++) //Parses line into a matrix
            {
                for (int j = 0; j < 4; j++) {
                    State[j][i] = Integer.parseInt(line.substring((8 * i) + (2 * j), (8 * i) + (2 * j + 2)), 16);
                    System.out.println("State[" + j + "][" + i + "]: " + State[j][i]);
                }
            }

        }

        System.out.println( " State [0] [0] is : " + State[0][0]);

        //Functions that are to be performed for number of Rounds - 10 in this case for 128-bit.

        try {
//            addRoundKey(State, KeySchedule.subKey(RoundKey, 0));


            Round(State, RoundKey);


            FinalRound(State, RoundKey);



        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("AES failed to work.");
        }

    }

    private static void FinalRound(int[][] state, int[][] roundKey) {

        ByteSubstitution.byteSubstitution(state);
        ShiftRow.shiftRow(state);
        addRoundKey(state, roundKey);

    }
    private static void Round(int[][] state, int[][] roundKey) {

        ByteSubstitution.byteSubstitution(state);
        ShiftRow.shiftRow(state);
        MixColumns.mixColumns(state);
        addRoundKey(state, roundKey);

    }

    /**
     * In the AddRoundKey step, the subkey is combined with the state.
     * For each round, a chunk of the key scheduled is pulled; each subkey is the same size as the state.
     * Each element in the byte matrix is XOR'd with each element in the chunk of the expanded key.
     */

    public static int[][] addRoundKey(int[][] state, int[][] roundkey){

        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[0].length; j++) {
                state[j][i] ^= roundkey[j][i];
            }
        }

        System.out.println( " State [0] [0] is : " + state[0][0]);

        return state;
    }

}
