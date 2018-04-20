public class Main {
    public static void main(String[] args) throws Exception {
        String encode = "utf8";
//        WordSegment jd = new WordSegment("./data/jdHuaweiP8Comment.txt", "./data/jdHuaweiP8FC.txt", encode);
//        jd.getSegmentedWord();
        // start sentiment
        Sentiment text = new Sentiment();
        text.readDoc("./data/jdHuaweiP8FC.txt", "./data/JDPrediction.txt", encode);
    }
}
