import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


public class TestAddString {
    String str = "1\n2,3,4\n5,6,7,8,9";
    String emptyString = "";
    String nullString = null;
    String strDelimiterChanged = "//:\n1:2:3:4:5:6:7:8:9";
    String strSingleNegative = "1\n2,3,4\n5,6,7,8,-9";
    String strMultipleNegative = "1\n2,-3,4\n5,-6,7,8,-9";

    @Test
    public void testStringAddition(){
        int sum = 45;
        Assert.assertEquals(sum,this.getStringSum());
    }

    @Test
    public void testEmptyString(){
        int sum = 0;
        Assert.assertEquals(sum,this.getEmptyStringSum());
    }

    @Test
    public void testNullString(){
        int sum = 0;
        Assert.assertEquals(sum,this.getNullEmptyStringSum());
    }

    @Test
    public void testDelimeterChangedString(){
        int sum = 45;
        Assert.assertEquals(sum,this.getDelimeterChangedStringSum());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeNumberSingle(){
        String pattern = "\\r?\\n|\\r|,";
        String numbers[] = this.strSingleNegative.split(pattern);
        try {
            validateNumbers(numbers);
        }catch (Exception e){
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMultipleNumberSingle(){
        String pattern = "\\r?\\n|\\r|,";
        String numbers[] = this.strMultipleNegative.split(pattern);
        try {
            validateNumbers(numbers);
        }catch (Exception e){
            System.out.println(e.getMessage());
            throw e;
        }
    }

    private char getDelimiter(String str){
        char delimiter = ',';
        str = str.trim();
        if(str.startsWith("//")){
            delimiter = str.charAt(2);
        }
        return delimiter;
    }

    private String cleanString(String str){
        str = str.trim();
        if(str.startsWith("//")){
            str = str.substring(4,str.length());
        }
        return str;
    }

    private int getStringSum(){
        String pattern = "\\r?\\n|\\r|,";
        return Stream.of(this.str.split(pattern)).mapToInt(Integer::parseInt).sum();
    }

    private int getDelimeterChangedStringSum(){
        char delimiter = getDelimiter(this.strDelimiterChanged);
        String str  = cleanString(this.strDelimiterChanged);
        String pattern = "\\r?\\n|\\r|" + delimiter;
        return Stream.of(str.split(pattern)).mapToInt(Integer::parseInt).sum();
    }

    private int getEmptyStringSum(){
        if(this.emptyString.trim().isEmpty()){
            return 0;
        }
        char delimiter = getDelimiter(this.str);
        String str  = cleanString(this.str);
        String pattern = "\\r?\\n|\\r|" + delimiter;
        return Stream.of(str.split(pattern)).mapToInt(Integer::parseInt).sum();
    }

    private int getNullEmptyStringSum(){
        if(this.nullString == null || this.nullString.trim().isEmpty()){
            return 0;
        }
        char delimiter = getDelimiter(this.str);
        String str  = cleanString(this.str);
        String pattern = "\\r?\\n|\\r|" + delimiter;
        return Stream.of(str.split(pattern)).mapToInt(Integer::parseInt).sum();
    }

    private void validateNumbers(String[] nums){
        List<String> lstNonNumeric = new ArrayList<>();
        for(String s:nums){
            if(!s.matches("\\d+(\\.\\d+)?")){
                lstNonNumeric.add(s);
            }
        }
        if(lstNonNumeric.size() == 1){
            if(Long.parseLong(lstNonNumeric.get(0)) < 0){
                throw new IllegalArgumentException("negative numbers not allowed " + lstNonNumeric.get(0));
            }else{
                throw new IllegalArgumentException("Invalid number " + lstNonNumeric.get(0));
            }
        }
        if(lstNonNumeric.size() > 1){
            String nonNumeric = "";
            String invalidNumbers = "";
            int negativeCount = 0;
            for (String s : lstNonNumeric) {
                if(Long.parseLong(s) < 0){
                    ++negativeCount;
                    nonNumeric += (!nonNumeric.isEmpty())?","+s:s;
                }else{
                    invalidNumbers += (!invalidNumbers.isEmpty())?","+s:s;
                }
            }
            if(negativeCount > 1){
                throw new IllegalArgumentException("negative numbers not allowed " + nonNumeric);
            }else{
                throw new IllegalArgumentException("Invalid number " + invalidNumbers);
            }
        }
    }
}
