import sun.misc.IOUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Trigraph {

    private String path = "C:/Users/Andre/Desktop/file.txt";
    private String text = "One fish, two fish, red fish, blue fish.";
    private String regex = "[\\s,.!:;=/<>+*\"-]+";
    List<String> words;
    List<String> trigraphs;
    Map<String, Integer> mapTrigraphs;

    public Trigraph(){
        words = new ArrayList<>();
        trigraphs = new ArrayList<>();
        mapTrigraphs = new LinkedHashMap<>();
    }

    public String getPath() {
        return path;
    }

    public String getText() {
        return text;
    }

    public String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    public void splitTextOnWords(String text){
        for (String currentWord: text.split(regex)) {
            words.add(currentWord);
        }
    }

    public void findTrigraphs() {
        String subString;
        for (String currentWord : words) {
            if (currentWord.length() == 3)
                trigraphs.add(currentWord);
            else if (currentWord.length() < 3) continue;
            else if (currentWord.length() > 3) {
                for (int i = 0; i < currentWord.length(); i++) {
                    for (int j = 1; j < currentWord.length() - i; j++) {
                        subString = currentWord.substring(i, i + j + 1);
                        if (subString.length() == 3) trigraphs.add(subString) ;
                    }
                }
            }

        }
        //System.out.println(trigraphs);
    }

    public void sortTrigraphsByAlphabeticalOrder(){
        Comparator<String> alphabeticalOrder = new Comparator<String>() {
            public int compare(String str1, String str2) {
                int res = String.CASE_INSENSITIVE_ORDER.compare(str1, str2);
                if (res == 0) {
                    res = str1.compareTo(str2);
                }
                return res;
            }
        };
        Collections.sort(trigraphs, alphabeticalOrder);
        //System.out.println(trigraphs);
    }

    public void countTrigraphs(){
        Integer value;
        for (String currentTrigraph : trigraphs) {
            value = mapTrigraphs.get(currentTrigraph);
            mapTrigraphs.put(currentTrigraph, value == null ? 1 : value + 1);
        }
        //System.out.println(mapTrigraphs);
    }

    public void coutResult(){
        Set set;
        set = mapTrigraphs.entrySet();
        for (Object o: set){
            System.out.println(o);
        }
    }

    public static void main(String[] args) {
        Trigraph trigraph = new Trigraph();
        String text2 = null;
        try {
           text2 = trigraph.readFile(trigraph.getPath(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //trigraph.splitTextOnWords(trigraph.getText());
        trigraph.splitTextOnWords(text2);
        trigraph.findTrigraphs();
        trigraph.sortTrigraphsByAlphabeticalOrder();
        trigraph.countTrigraphs();
        trigraph.coutResult();
    }
}
