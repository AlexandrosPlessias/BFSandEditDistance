/*
 * System Specs: 
 * Intel(R) 4-Core i5-2500K CPU @ 3.30GHz
 * 8GB RAM
 * NetBeans IDE 8.0.1 (Default Comliper)
 * Windows 10 Pro Insider Preview (Java recognizes as Windows 8.1)
 */
package acp2015.pkg;

/*
 * Problem 2.
 * Implemetention of Edit Distance algorithm.
 * Time Complexity is O(nm) where n=str1Length & m=str2Length.
 */
public class EditDistance {

    /**
     * Function to get Edit Distance between 2 strings implementation of
     * Wagner-Fischer algorithm.
     * https://en.wikipedia.org/wiki/Wagner%E2%80%93Fischer_algorithm
     *
     * @param str1 The first string.
     * @param str2 The second string.
     * @return The edit distance.
     */
    public int getEditDistance(String str1, String str2) {

        // The strings we want to exam.
        // Uppercase and Lowercase chars treated as equally.
        String str1ToLower = str1.toLowerCase();
        String str2ToLower = str2.toLowerCase();

        // Get strings lengths.
        int str1Length = str1ToLower.length();
        int str2Length = str2ToLower.length();

        /* Create table for store distances between distances i,j  
         * and use +1 for driver row & column.    
         */
        int[][] distances = new int[str1Length + 1][str2Length + 1];

        /* Initialize the "driver row & column".
         * ex.   ε a l e x a n d e r 
         *     ε 0 1 2 2 4 5 6 7 8 9
         *     a 0
         *     l 2
         *     e 3
         *     x 4
         */
        for (int i = 0; i <= str1Length; i++) {
            distances[i][0] = i;
        }
        for (int i = 1; i <= str2Length; i++) {
            distances[0][i] = i;
        }

        // Loop for scanning strings.
        for (int i = 1; i <= str1Length; i++) {
            for (int j = 1; j <= str2Length; j++) {

                // Is 0 if have match else 1.
                // The cost is given by exercise.
                int cost;

                // Check if have match.
                if ((str1ToLower.charAt(i - 1) == str2ToLower.charAt(j - 1))) {
                    distances[i][j] = distances[i - 1][j - 1];
                } else {
                    cost = 1;

                    int deletion = distances[i - 1][j] + cost;
                    int insertion = distances[i][j - 1] + cost;
                    int substitution = distances[i - 1][j - 1] + cost;

                    // Select the smallest of 3.
                    distances[i][j] = Math.min(deletion, Math.min(insertion, substitution));
                }
            }
        }

//        Feel free to Uncomment and check array results in Output console.
        
//        Print array appropriate.
//        for (int j = 0; j <= str2Length; j++) {
//            if (j == 0) {
//                System.out.print("  | ε | ");
//            } else {
//                System.out.print(str2.charAt(j - 1) + " | ");
//            }
//        }
//        System.out.println();
//
//        for (int i = 0; i <= str1Length; i++) {
//            if (i == 0) {
//                System.out.print("ε | ");
//            } else {
//                System.out.print(str1.charAt(i - 1) + " | ");
//            }
//
//            for (int j = 0; j <= str2Length; j++) {
//                System.out.print(distances[i][j] + " | ");
//            }
//            System.out.println();
//        }
//        System.out.println();

        // The requested edit distance is always in this cell.
        return distances[str1Length][str2Length];

    }

    /**
     * A bonus simple Function.
     * Function to get Similarity Score between 2 strings implementation
     * formula: (1 - ( edit_distance/max(str1.length,str2.length) ) * 100 .
     *
     * @param str1 The first string.
     * @param str2 The second string.
     * @return percent of Similarity.
     */
    public float similarityScore(String str1, String str2) {

        float similarityScore;

        // Implementation of formula.
        if (str1.length() == str2.length()) {
            similarityScore = (float) ((1 - ((float) getEditDistance(str1, str2) / str1.length())) * 100);
        } else if (str1.length() > str2.length()) {
            similarityScore = (float) ((1 - ((float) getEditDistance(str1, str2) / str1.length())) * 100);
        } else { // str1.length() < str2.length()
            similarityScore = (float) ((1 - ((float) getEditDistance(str1, str2) / str2.length())) * 100);
        }

        return similarityScore;
    }
}
