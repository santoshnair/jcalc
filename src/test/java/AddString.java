import org.junit.Assert;
import org.junit.Test;
import java.util.stream.Stream;

public class AddString {
    String str = "1,2,3,4,5,6,7,8,9";
    String emptyString = "";

    @Test
    public void processStringAddition(){
        int sum = 45;
        Assert.assertEquals(sum,this.getStringSum());
    }

    @Test
    public void processEmptyString(){
        int sum = 0;
        Assert.assertEquals(sum,this.getEmptyStringSum());
    }

    private int getStringSum(){
        return Stream.of(this.str.trim().split(",")).mapToInt(Integer::parseInt).sum();
    }

    private int getEmptyStringSum(){
        if(this.emptyString.trim().isEmpty()){
            return 0;
        }
        return Stream.of(this.emptyString.split(",")).mapToInt(Integer::parseInt).sum();
    }
}
