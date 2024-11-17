package dev.fuzzit.examplejava;

import org.junit.runner.RunWith;
import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;

@RunWith(JQF.class)
public class AddNumbersTest {

    @Fuzz
    public void fuzz(int a, int b) {
        AddNumbers.add(a, b);
    }
}
