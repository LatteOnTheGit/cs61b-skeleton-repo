package flik;
import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Test;

public class flikTest {
    @Test
    public void testSame(){
        int j = 129;
        for(int i=129; i<500 ; i++,j++){
            assertEquals(String.format("The i is %s the j is %s",i,j),true, Flik.isSameNumber(i,j));
        }
    }

    @Test
    public void testCertainNumber(){
        int i = 130, j = 130;
        assertEquals(String.format("The i is %s the j is %s",i,j),i==j, Flik.isSameNumber(i,j));
    }
}
