package main;

import org.apache.commons.cli.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        Options options = new Options();
        OptionsBuilder opt = new OptionsBuilder();
        opt.addOptions(options);

        HelpFormatter formatter = new HelpFormatter();
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;

        try {

            cmd = parser.parse(options, args);
            String inputFilePath = cmd.getOptionValue("input");

            FileReader fileReader = new FileReader(inputFilePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            for (String arg : args )
                System.out.println("Argument : " + arg);


            StructBuilder structBuilder = new StructBuilder();

            String textLine = bufferedReader.readLine();
            do {
                TextParser textParser = new TextParser();
                textParser.createPattern();
                textParser.checkInLine(textLine);
                textParser.isInLine();
                structBuilder.writeInfo(textParser.getMatches(), textLine);

            //    System.out.println(textLine);
                textLine = bufferedReader.readLine();
            } while(textLine != null);


         TextPrinter textPrinter = new TextPrinter();
         String mode = cmd.getOptionValue("mode");
        if (mode.equals("c")){
            System.out.println("wybrałeś opcję wyświetlania spisu treści");
            textPrinter.printTableOfContent(structBuilder);
        } else if (mode.equals("b")){
            System.out.println("wybrałeś opcję wyświetlania body");

            String chapter = cmd.getOptionValue("chapter");
            String article = cmd.getOptionValue("article");
            String[] articles = cmd.getOptionValues("articles");
            String paragraph = cmd.getOptionValue("paragraph");
            String point = cmd.getOptionValue("point");

            if(chapter != null){
                System.out.println("chcesz wyświetlić rozdział " + chapter);
            //    textPrinter.printChapter(structBuilder, "Rozdział "+chapter);
            } else if (articles != null){
                System.out.println("chcesz wyświetlić zakres artykułów");
                textPrinter.printArticles(structBuilder, Integer.parseInt(articles[0]), Integer.parseInt(articles[1]));
            } else if (article != null){
                System.out.println("chcesz wyświtlić artykuł i może coś więcej");

                if(paragraph != null && point != null){
                    System.out.println("chcesz wyświtlić artykuł , paragraf, punkt");
//                    textPrinter.printPoint(structBuilder, point, paragraph, Integer.parseInt(article));
                } else if ( paragraph != null){
                    System.out.println("chcesz wyświtlić artykuł , paragraf");
//                    textPrinter.printParagraph(structBuilder, paragraph, Integer.parseInt(article));
                }else if ( point != null){
                    System.out.println("chcesz wyświtlić artykuł , punkt");

                    ArrayList<IPart> parts = new ArrayList<>();
                    parts.add(new Point());

                    ArrayList<String> names = new ArrayList<>();
                    names.add(point);

                    System.out.println("zrobiłem listy");

                    textPrinter.printPart(parts, names, structBuilder.articles.get(Integer.parseInt(article)-1));

                } else {
                    System.out.println("chcesz wyświtlić artykuł");
                    textPrinter.printArticle(structBuilder, Integer.parseInt(article));
                }
            }
            else {
                System.out.println("nie podałeś specyfikacji więc wyświetlę całą konstytucję");
                textPrinter.printAll(structBuilder);

            }

        } else {
            System.out.println("podałeś zły argument dla opcji mode");
        }

         bufferedReader.close();

        } catch (ParseException ex){
            System.out.println("Unexpected ParseException: " + ex.getMessage());
            formatter.printHelp( " ", options );
        } catch (IOException ex){
            System.out.println("Unexpected IOException: " + ex.getMessage());
        } catch (TestException ex){
            System.out.println("TestException: " + ex.getMessage());
        }
    }
}


//        textPrinter.printSection(textInfos, "III");

//textPrinter.printDown(textPrinter.findNextPart(new Point(), "1)", textInfos.articles.get(241).getDown()));

/*

              ArrayList<IPart> parts = new ArrayList<>();
            ArrayList<String> names = new ArrayList<>();

            textPrinter.printArticle(structBuilder, 81);

             parts.add(new Paragraph());
            names.add("3a.");
            textPrinter.printPart (parts, names, structBuilder.articles.get(81-1));

           parts.add(new Point());
            names.add("1)");
            textPrinter.printPart(parts, names, structBuilder.articles.get(81-1));

            parts.add(new Letter());
            names.add("b)");
            textPrinter.printPart(parts, names, structBuilder.articles.get(81-1));

            textPrinter.printArticleWithLetter(structBuilder, 94, "b");

            PartFinder finder = new PartFinder();
            IPart partToWrite = finder.findArticleWithLetter(structBuilder.articles.get(99-1), "b");
            ArrayList<IPart> parts1 = new ArrayList<>();
            ArrayList<String> names2 = new ArrayList<>();
            parts1.add(new Paragraph());
            names2.add("2.");
            textPrinter.printPart (parts1, names2, partToWrite);


  textPrinter.printAll(textInfos);
         textPrinter.printTableOfContent(textInfos);
         textPrinter.printArticle(textInfos, 186);
         textPrinter.printArticles(textInfos, 15, 29);

         Integer num =  textPrinter.findChapter(textInfos, "Rozdział IX");
         System.out.println("Rozdział IX zaczyna się z artykułem " + num);

         Integer num1 = textPrinter.findNextChapter(textInfos, num);
         System.out.println("Następny rozdział X zaczyna się z artykułem " + num1);

         textPrinter.printChapter(textInfos, "Rozdział IX");
         textPrinter.printParagraph(textInfos, "2.", 162);
         textPrinter.printParagraph(textInfos, "1.", 214);
         textPrinter.printPoint(textInfos, "2)","2.", 162);

         String[] articleNumbers = cmd.getOptionValues("articles");
         for (String num3 : articleNumbers )
                System.out.println("Article Number : " + num3);
         System.out.println("\n");
         textPrinter.printArticles(textInfos, Integer.parseInt(articleNumbers[0]), Integer.parseInt(articleNumbers[1]));
*/

 /*           ArrayList<IPart> parts = new ArrayList<>();
            parts.add(new Paragraph());


            ArrayList<String> names = new ArrayList<>();
            names.add("2.");


            System.out.println("jestem");
            textPrinter.printArticle(textInfos, 2);
            textPrinter.printPart(1, parts, names, textInfos.articles.get(2-1));

            parts.add(new Point());
            names.add("1)");
            textPrinter.printPart(2, parts, names, textInfos.articles.get(1));
textPrinter.printPart(3, parts, names, structBuilder.articles.get(1));


            textPrinter.printChapterOrSection(structBuilder, "VII", new Chapter());
            textPrinter.printArticle(structBuilder, 55);
            textPrinter.printArticles(structBuilder, 89,123);
            textPrinter.printTableOfContentOfChapterOrSection(structBuilder, "II", new Chapter());


            parts.add(new Letter());
            names.add("b)");
         //   textPrinter.printPart(3, parts, names, textInfos.articles.get(2));

*/