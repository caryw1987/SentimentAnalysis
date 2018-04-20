import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Chenyu Wang on 17/04/2018.
 */

public class WordSegment {
    // import stopword dictionary
    private String stopWordTable = "./data/stopWordDic/stopWord.txt";
    private String jdComment, path, encode;

    WordSegment(String comment, String path, String encode) {
        this.jdComment = comment;
        this.path = path;
        this.encode = encode;
    }

    public void getSegmentedWord() throws IOException {
        BufferedReader StopWordFileBr = new BufferedReader(new InputStreamReader(
                new FileInputStream(new File(stopWordTable)), this.encode));  // read stopword file
        Set<String> stopWordSet = new HashSet<String>(); // store stopword set
        //initialize stopWord set
        String stopWord = null;
        for (; (stopWord = StopWordFileBr.readLine()) != null; ) {
            stopWordSet.add(stopWord);
        }

        BufferedReader jdComFileBr = new BufferedReader(new InputStreamReader(
                new FileInputStream(new File(jdComment)), this.encode));  //import jdComment

        //create word Segmenter object
        String text;
        StringBuilder sb = new StringBuilder();
        try {
            while ((text = jdComFileBr.readLine().toString()) != null) {
                StringReader sr = new StringReader(text);
                IKSegmenter ik = new IKSegmenter(sr, true);
                Lexeme lex;

                //word segment
                while ((lex = ik.next()) != null) {
                    //remove stop word
                    if (stopWordSet.contains(lex.getLexemeText())) {
                        continue;
                    }
                    System.out.print(lex.getLexemeText() + "\t");
                    sb.append(lex.getLexemeText()).append(" ");
                }
                sb.append("\r\n");
            }
            StopWordFileBr.close();
            jdComFileBr.close();

        } catch (NullPointerException e) {
            saveSegmentedWord(sb.toString());
            System.out.println("\r\n" + "word segment successfully!(^.^)");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //save segmented word
    private void saveSegmentedWord(String word) {
        File file = new File(this.path);
        try {
            if (!file.exists()) {
                System.out.println("Create file:" + file.createNewFile());
            }
            FileOutputStream fos = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fos, this.encode);
            osw.write(word);
            osw.flush();
        } catch (Exception e) {
            System.out.print("Exist IO error when segmenting word");
            e.printStackTrace();
        }
    }
}