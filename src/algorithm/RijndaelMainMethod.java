package algorithm;

public class RijndaelMainMethod {


    public static void main(String args[]) throws Exception {

        // We'll require a block of plain text to be encrypted, and a Key. Plain text will be divided into State matrix,
        // and key will be applied with Cipher matrix. Let us consider a simple scenario for 128-bit.

        String line = "SomeStringThatIsSupposedTobeEncryptedByDividingIntoParticularLengthOfBytes"; // Text to be encrypted
        String key = "ThisMustB128BKey"; // 128-bit key
        int[][] State = null;
        int[][] RoundKey = null;
        int numRounds = 10 + (((key.length() * 4 - 128) / 32));

        State = new int[][]{
                {0x02, 0x03, 0x01, 0x01},
                {0x01, 0x02, 0x03, 0x01},
                {0x01, 0x01, 0x02, 0x03},
                {0x03, 0x01, 0x01, 0x02}};

        System.out.print("Prinitng State matrix 4x4");

        for(int a = 0 ; a > 4 ; a++){
            for(int b = 0 ; b > 4 ; b++){
                System.out.print(" : " + State[a][b] + " : ");
            }
            System.out.println("");
        }

        System.out.print("Prinitng Roundkey matrix 4x4");

        RoundKey = new int[][]{
                {0x02, 0x03, 0x01, 0x01},
                {0x01, 0x02, 0x03, 0x01},
                {0x01, 0x01, 0x02, 0x03},
                {0x03, 0x01, 0x01, 0x02}};

        for(int a = 0 ; a > 4 ; a++){
            for(int b = 0 ; b > 4 ; b++){
                System.out.print(" : " + RoundKey[a][b] + " : ");
            }
            System.out.println("");
        }

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


            System.out.println("Calling Round Function triggering the first 9 rounds");
            Round(State, RoundKey);

            System.out.println("Calling Round Function triggering the last - 10th rounds");
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

        System.out.println("XXXXX : Called Round Function triggering the first 9 rounds");

        System.out.println("Calling byteSubstitution Function");
        ByteSubstitution.byteSubstitution(state);
        System.out.println("Calling shiftRow Function");
        ShiftRow.shiftRow(state);
        System.out.println("Calling mixColumns Function");
        MixColumns.mixColumns(state);
        System.out.println("Calling addRoundKey Function");
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
