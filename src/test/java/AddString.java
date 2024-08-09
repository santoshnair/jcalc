import org.junit.Assert;
import org.junit.Test;
import java.util.stream.Stream;

public class AddString {
    String str = "1,2,3,4,5,6,7,9";
    @Test
    public void processStringAddition(){
        int sum = 37;
        Assert.assertEquals(sum,this.getStringSum());
    }
    private int getStringSum(){
        return  Stream.of(this.str.split(",")).mapToInt(Integer::parseInt).sum();
    }
}
