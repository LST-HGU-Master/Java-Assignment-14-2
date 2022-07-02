import org.junit.jupiter.api.Test;
import java.io.*;

import static org.junit.jupiter.api.Assertions.*;
/**
 * @version (20220616)
 */
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
        // undo the binding in System
        System.setOut(originalOut);
        
        // assertion
        String expected = "1.読み込み確認用ファイル\n" +
                "2.ここは2行目です。\n" +
                "3.";
        assertTrue(bos.toString().contains(expected),"ファイルから読み込んだ内容とprintした内容が一致しません!");
    }

    @Test
    public void testCatchException()
    {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream bosOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(bosOut));

        StandardInputStream in = new StandardInputStream();
        System.setIn(in);

        // assertion
        assertDoesNotThrow(
            () -> ProgE2.main(new String[]{"src/main/java/abcdefg.txt"}), 
            "例外処理をthrowしてはいけません！"
        );

        // undo the binding in System
        System.setOut(originalOut);
        
        assertTrue(bosOut.toString().contains("ファイル読み込みで例外が発生しました。処理を中断します！"),
            "読み込みで例が発生したことを示すメッセージ表示がない、またはその内容が指示と異なります!" );
    }
}
