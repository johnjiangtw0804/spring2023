import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

class Pair {
    public Character c;
    public Double i;

    public Pair(Character c, Double i) {
        this.c = c;
        this.i = i;
    }
}

public class FrequencyAnalysis {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java FrequencyAnalysis <filename>");
            System.exit(1);
        }
        String cipherTextFileName = args[0];
        File cipherTextFile = new File(cipherTextFileName);

        try (Scanner myReader = new Scanner(cipherTextFile)) {
            Double[] freqs = new Double[26];
            Arrays.fill(freqs, 0.0);
            while (myReader.hasNextLine()) {
                String text = myReader.nextLine();
                letterFrequenciesCounter(text, freqs);
            }
            Pair[] charToInstances = new Pair[26];
            for (int i = 0; i < freqs.length; i++) {
                char c = (char) (i + 'a');
                charToInstances[i] = new Pair(c, freqs[i]);
            }
            Arrays.sort(charToInstances, new Comparator<Pair>() {
                @Override
                public int compare(Pair o1, Pair o2) {
                    return o2.i.compareTo(o1.i);
                }
            });

            Pair[] relativeFrequencies = new Pair[] { new Pair('a', 0.08167),
                    new Pair('b', 0.01492), new Pair('c', 0.02782),
                    new Pair('d', 0.04253), new Pair('e', 0.12702),
                    new Pair('f', 0.02228), new Pair('g', 0.02015),
                    new Pair('h', 0.06094), new Pair('i', 0.06966),
                    new Pair('j', 0.00153), new Pair('k', 0.00772),
                    new Pair('l', 0.04025), new Pair('m', 0.02406),
                    new Pair('n', 0.06749), new Pair('o', 0.07507),
                    new Pair('p', 0.01929), new Pair('q', 0.00095),
                    new Pair('r', 0.05987), new Pair('s', 0.06327),
                    new Pair('t', 0.09056), new Pair('u', 0.02758),
                    new Pair('v', 0.00978), new Pair('w', 0.02360),
                    new Pair('x', 0.00150), new Pair('y', 0.01974),
                    new Pair('z', 0.00074) };
            Arrays.sort(relativeFrequencies, new Comparator<Pair>() {
                @Override
                public int compare(Pair o1, Pair o2) {
                    return o2.i.compareTo(o1.i);
                }
            });

            Character[] potentialKey = new Character[26];
            for (int i = 0; i < charToInstances.length; i++) {
                potentialKey[charToInstances[i].c - 'a'] = relativeFrequencies[i].c;
            }
            System.out.println("The potential key is: ");
            for (int i = 0; i < charToInstances.length; i++) {
                System.out.print(potentialKey[i]);
            }
        } catch (

        Exception e) {
            System.out.println("Exceptions: " + e);
            System.exit(1);
        }
    }

    public static void letterFrequenciesCounter(String text, Double[] freqs) {
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (Character.isLetter(c)) {
                int index = Character.toLowerCase(c) - 'a';
                freqs[index]++;
            }
        }
    }
}