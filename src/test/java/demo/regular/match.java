package demo.regular;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class match {

    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("_.*_");
        Matcher matcher = pattern.matcher("_activateRange_");
        matcher.find();
        String s=matcher.group(0);
        s=s.substring(1,s.length()-1);
        System.out.println(s);
    }

}
