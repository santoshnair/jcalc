package org.example;
import java.util.stream.Stream;

public class Main {
    static int add(String numbers){
        if(numbers.trim().length()==0){
            return 0;
        }
        return  Stream.of(numbers.split(",")).mapToInt(Integer::parseInt).sum();
    }
    public static void main(String[] args) {
        String numbers = "1,2,3,4,5,6,7,8,9";
        int sum = add(numbers);
        System.out.println("The sum of "+numbers+" is "+sum);
    }
}