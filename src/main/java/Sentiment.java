import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Chenyu Wang on 17/04/2018.
 */
public class Sentiment {
    private String encode;
    private Set<String> posWordSet, negWordSet, negVodSet, vod1Set, vod2Set, vod3Set, vod4Set, vod5Set, vod6Set;
    private StringBuilder sensTxt;
    //intensity of every polarity word
    private float tolSens = 0;
    //intensity of every sentence
    private float docSens = 0;


    public void readDoc(String path, String senPath, String encode) throws IOException {
        this.encode = encode;
        File doc = new File(path);
        String singleDoc;
        String[] stringList;
        dic();
        StringBuilder readParse, ParseSen;
        BufferedReader docBf = new BufferedReader(new InputStreamReader(new FileInputStream(doc), this.encode));

        while ((singleDoc = docBf.readLine()) != null) {
            readParse = new StringBuilder();
            ParseSen = new StringBuilder();
            readParse.append("<");
            ParseSen.append("<");

            stringList = singleDoc.split(" ");
            for (String item : stringList) {
                System.out.print(item + "");
                if (posWordSet.contains(item) || negWordSet.contains(item) || negVodSet.contains(item)
                        || vod1Set.contains(item) || vod2Set.contains(item) || vod3Set.contains(item)
                        || vod4Set.contains(item) || vod5Set.contains(item) || vod6Set.contains(item)) {

                    if (readParse.lastIndexOf(">") > readParse.lastIndexOf("<")) {
                        readParse.append("<");
                    }
                    if (ParseSen.lastIndexOf(">") > ParseSen.lastIndexOf("<")) {
                        ParseSen.append("<");
                    }
                    if (negVodSet.contains(item)) {
                        System.out.print("NA");
                        readParse.append("NA");
                        ParseSen.append("-0.8,");
                    }
                    if (vod1Set.contains(item)) {
                        System.out.print("DA");
                        readParse.append("DA");
                        ParseSen.append("0.9,");
                    }
                    if (vod2Set.contains(item)) {
                        System.out.print("DA");
                        readParse.append("DA");
                        ParseSen.append("0.9,");
                    }
                    if (vod3Set.contains(item)) {
                        System.out.print("DA");
                        readParse.append("DA");
                        ParseSen.append("0.7,");
                    }
                    if (vod4Set.contains(item)) {
                        System.out.print("DA");
                        readParse.append("DA");
                        ParseSen.append("0.5,");
                    }
                    if (vod5Set.contains(item)) {
                        System.out.print("DA");
                        readParse.append("DA");
                        ParseSen.append("0.3,");
                    }
                    if (vod6Set.contains(item)) {
                        System.out.print("DA");
                        readParse.append("DA");
                        ParseSen.append("-0.5,");
                    }
                    if (posWordSet.contains(item)) {
                        System.out.print("PW");
                        readParse.append("PW>");
                        ParseSen.append("0.8>");
                    }
                    if (negWordSet.contains(item)) {
                        System.out.print("PW");
                        readParse.append("PW>");
                        ParseSen.append("-0.8>");
                    }
                }
            }
            System.out.print("\r\n");
            System.out.println(readParse.toString() + " " + ParseSen.toString());

            System.out.println("总情感值为：" + computeSen(readParse.toString(), ParseSen.toString(), senPath));  // judge tag
        }
    }

    private void dic() throws IOException {
        BufferedReader posWord = new BufferedReader(new InputStreamReader(
                new FileInputStream(new File("./data/Dictionary/PosWords/PosWords(0.8).txt")), this.encode));
        BufferedReader negWord = new BufferedReader(new InputStreamReader(
                new FileInputStream(new File("./data/Dictionary/NegWords/NegWords(-0.8).txt")), this.encode));
        BufferedReader negVod = new BufferedReader(new InputStreamReader(
                new FileInputStream(new File("./data/Dictionary/NegAdverbs/Neg(-0.8).txt")), this.encode));
        BufferedReader vod1 = new BufferedReader(new InputStreamReader(
                new FileInputStream(new File("./data/Dictionary/DegreeWords/Most(0.9).txt")), this.encode));  // 最
        BufferedReader vod2 = new BufferedReader(new InputStreamReader(
                new FileInputStream(new File("./data/Dictionary/DegreeWords/Super(0.9).txt")), this.encode));  // 超
        BufferedReader vod3 = new BufferedReader(new InputStreamReader(
                new FileInputStream(new File("./data/Dictionary/DegreeWords/Very(0.7).txt")), this.encode));  // 很
        BufferedReader vod4 = new BufferedReader(new InputStreamReader(
                new FileInputStream(new File("./data/Dictionary/DegreeWords/More(0.5).txt")), this.encode));  // 较
        BufferedReader vod5 = new BufferedReader(new InputStreamReader(
                new FileInputStream(new File("./data/Dictionary/DegreeWords/Little(0.3).txt")), this.encode));  // 稍
        BufferedReader vod6 = new BufferedReader(new InputStreamReader(
                new FileInputStream(new File("./data/Dictionary/DegreeWords/Less(-0.5).txt")), this.encode));  // 欠

        posWordSet = new HashSet<String>();
        negWordSet = new HashSet<String>();
        negVodSet = new HashSet<String>();
        vod1Set = new HashSet<String>();
        vod2Set = new HashSet<String>();
        vod3Set = new HashSet<String>();
        vod4Set = new HashSet<String>();
        vod5Set = new HashSet<String>();
        vod6Set = new HashSet<String>();

        String str;
        while ((str = posWord.readLine()) != null) {
            posWordSet.add(str);
        }
        while ((str = negWord.readLine()) != null) {
            negWordSet.add(str);
        }
        while ((str = negVod.readLine()) != null) {
            negVodSet.add(str);
        }
        while ((str = vod1.readLine()) != null) {
            vod1Set.add(str);
        }
        while ((str = vod2.readLine()) != null) {
            vod2Set.add(str);
        }
        while ((str = vod3.readLine()) != null) {
            vod3Set.add(str);
        }
        while ((str = vod4.readLine()) != null) {
            vod4Set.add(str);
        }
        while ((str = vod5.readLine()) != null) {
            vod5Set.add(str);
        }
        while ((str = vod6.readLine()) != null) {
            vod6Set.add(str);
        }

        posWord.close();
        negWord.close();
        negVod.close();
        vod1.close();
        vod2.close();
        vod3.close();
        vod4.close();
        vod5.close();
        vod6.close();
    }

    private void saveSens(String path, String word) {
        File file = new File(path);
        try {
            if (!file.exists()) {
                System.out.println("Create file: " + file.createNewFile());
            }
            FileWriter fWriter = new FileWriter(path, true);
            fWriter.write(word);
            fWriter.flush();
            fWriter.close();
        } catch (Exception e) {
            System.out.print("exist IO error when saving data");
            e.printStackTrace();
        }
    }

//    public void accuracy(String pre, String result) throws IOException {
//        File preFile = new File(pre);
//        File resultFile = new File(result);
//        if (!preFile.exists() || !resultFile.exists()) {
//            throw new IllegalArgumentException(("File for tesing accuracy is not existing!"));
//        }
//        BufferedReader preBf = new BufferedReader(
//                new InputStreamReader(new FileInputStream(preFile), this.encode));
//        BufferedReader resultBf = new BufferedReader(
//                new InputStreamReader(new FileInputStream(resultFile), this.encode));
//
//        double a = 0;
//        double b = 0;
//        String presb = null;
//        String resulesb = null;
//
//        while ((presb = preBf.readLine()) != null && (resulesb = resultBf.readLine()) != null) {
//            if (presb.equals(resulesb)) {
//                a = a + 1;
//                b = b + 1;
//            } else {
//                b = b + 1;
//            }
//        }
//        preBf.close();
//        resultBf.close();
//        System.out.println("accuracy is :" + a / b);
//    }

    private double computeSen(String doc, String value, String senPath) {
        String regx1 = "<(.+?)>";
        Pattern p = Pattern.compile(regx1);
        Matcher m1 = p.matcher(doc);
        Matcher m2 = p.matcher(value);

        ArrayList<String> doclist = new ArrayList<String>();
        ArrayList<String> valuelist = new ArrayList<String>();
        while (m2.find()) {
            valuelist.add(m2.group(1));
        }
        while (m1.find()) {
            doclist.add(m1.group(1));
        }
        Iterator<String> dociter = doclist.iterator();
        Iterator<String> valueiter = valuelist.iterator();

        while (dociter.hasNext()) {
            String sentence;
            System.out.print(sentence = dociter.next());
            String[] cpValues = valueiter.next().split(",");
            for (String cpValue : cpValues) {
                System.out.print(cpValue);
            }
            if (sentence.equals("PW")) {
                tolSens = Float.parseFloat(cpValues[0]);
            } else if (sentence.equals("NAPW")) {
                tolSens = Float.parseFloat(cpValues[0]) * Float.parseFloat(cpValues[1]);
            } else if (sentence.equals("NANAPW")) {
                tolSens = Float.parseFloat(cpValues[2]);
            } else if (sentence.equals("DAPW")) {
                if (Float.parseFloat(cpValues[1]) > 0) {
                    tolSens = Float.parseFloat(cpValues[1]) + (1 - Float.parseFloat(cpValues[1])) * Float.parseFloat(cpValues[0]);
                } else {
                    tolSens = Float.parseFloat(cpValues[1]) + (-1 - Float.parseFloat(cpValues[1])) * Float.parseFloat(cpValues[0]);
                }
            } else if (sentence.equals("DADAPW")) {
                tolSens = Float.parseFloat(cpValues[2]) +
                        (1 - Float.parseFloat(cpValues[2])) * Float.parseFloat(cpValues[0]) +
                        (1 - Float.parseFloat(cpValues[2]) - (1 - Float.parseFloat(cpValues[2])) * Float.parseFloat(cpValues[0])) * Float.parseFloat(cpValues[1]);
            } else if (sentence.equals("NADAPW")) {
                tolSens = Float.parseFloat(cpValues[2]) + (1 - Float.parseFloat(cpValues[0])) * (Float.parseFloat(cpValues[1]) - 2);
            } else if (sentence.equals("DANAPW")) {
                tolSens = Float.parseFloat(cpValues[2]) * Float.parseFloat(cpValues[1]) +
                        (-1 - Float.parseFloat(cpValues[2])) * Float.parseFloat(cpValues[1]) * Float.parseFloat(cpValues[1]);
            }

            System.out.print("(" + tolSens + ")");
            docSens = tolSens + docSens;
        }

        sensTxt = new StringBuilder();
        if (docSens > 0) {
            sensTxt.append("pos" + "\r\n");
            System.out.println("总情感极性值是：" + docSens + "判断为 ：积极");
        } else if (docSens < 0) {
            sensTxt.append("neg" + "\r\n");
            System.out.println("总情感极值是：" + docSens + " 判断为：消极 ");
        } else {
            sensTxt.append("neutral" + "\r\n");
            System.out.println("总情感极值是：" + docSens + " 判断为：中性 ");
        }

        saveSens(senPath, sensTxt.toString());

        tolSens = 0;
        docSens = 0;
        return tolSens;
    }
}
