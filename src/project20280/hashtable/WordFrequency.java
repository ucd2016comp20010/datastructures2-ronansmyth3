package project20280.hashtable;

import project20280.interfaces.Entry;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class WordFrequency {
    public static void main(String[] args) throws FileNotFoundException
    {
        File f = new File("src/project20280/hashtable/sample_text.txt");
        ChainHashMap<String, Integer> counter = new ChainHashMap<>();

        Scanner scanner = new Scanner(f);

        // read file
        while (scanner.hasNext()) {
            String word = scanner.next().toLowerCase();

            // skip blanks
            if (word.equals("")) continue;

            Integer count = counter.get(word);

            if (count == null) {
                counter.put(word, 1);
            } else {
                counter.put(word, count + 1);
            }
        }

        // Convert to list for sorting
        ArrayList<Entry<String, Integer>> list = new ArrayList<>();
        for (Entry<String, Integer> e : counter.entrySet()) {
            list.add(e);
        }

        // Sort by frequency (descending)
        list.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        // Print top 10
        System.out.println("Top 10 words:");
        for (int i = 0; i < Math.min(10, list.size()); i++) {
            Entry<String, Integer> e = list.get(i);
            System.out.println(e.getKey() + ": " + e.getValue());
        }
    }
}