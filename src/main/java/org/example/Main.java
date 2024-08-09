package org.example;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Main {
    static char getDelimiter(String str){
        char delimiter = ',';
        str = str.trim();
        if(str.startsWith("//")){
            delimiter = str.charAt(2);
        }
        return delimiter;
    }

    static String cleanString(String str){
        str = str.trim();
        if(str.startsWith("//")){
            str = str.substring(4,str.length());
        }
        return str;
    }

    static int add(String numbers){
        if(numbers == null || numbers.trim().isEmpty()){
            return 0;
        }
        char delimiter = getDelimiter(numbers);
        String str  = cleanString(numbers);
        String pattern = "\\r?\\n|\\r|" + delimiter;
        String[] nums = str.split(pattern);
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
        return Stream.of(nums).mapToInt(Integer::parseInt).sum();
    }
    public static void main(String[] args) {
        String numbers = "//;\n1;2\n3;4;5;6\n7;8;9";
        try {
            int sum = add(numbers);
            System.out.println("The sum of " + numbers + " is " + sum);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}