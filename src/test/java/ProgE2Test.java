import org.junit.jupiter.api.Test;
import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class ProgE2Test {

    @Test
    public void testReadResult()
    {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(bos));

        StandardInputStream in = new StandardInputStream();
        System.setIn(in);

        // action
        ProgE2.main(new String[]{"textfile1.txt"}); // 実行時引数をテストする場合

        // assertion
        String expected = "1.読み込み確認用ファイル\n" +
                "2.ここは2行目です。\n" +
                "3.\n";
        assertTrue(bos.toString().contains(expected));

        // undo the binding in System
        System.setOut(originalOut);
    }

    @Test
    public void testCatchException()
    {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(bos));

        StandardInputStream in = new StandardInputStream();
        System.setIn(in);

        // assertion
        assertDoesNotThrow(() -> ProgE2.main(new String[]{"src/main/java/abcdefg.txt"}));
    }

}
