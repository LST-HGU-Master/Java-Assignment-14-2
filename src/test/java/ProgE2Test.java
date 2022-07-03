import org.junit.jupiter.api.Test;
import java.io.*;
import java.io.BufferedReader;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
/**
 * @version (20220703)
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

        String filename = "textfile1.txt";
        // prepairing texts from the sourcefile
        String line;
        ArrayList<String> expected = new ArrayList<String>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            while ( (line = br.readLine()) != null){
                expected.add(line);
            }
        } catch( IOException e) {
            System.setOut(originalOut);
            fail("IOException occurred!（" + filename + "を用意していますか？）");
        }

        // action
        ProgE2.main(new String[]{filename}); // 実行時引数をテストする場合
        // undo the binding in System
        System.setOut(originalOut);
        // assertion
        String [] print = bos.toString().split("\r\n|\n", -1);
        int count = 0;
        for(String expStr : expected) { 
            assertTrue(print[count++].equals(expStr),"ファイルの内容("+ count +"行目)とprintしたものが一致しません! "); // expStr
        }
        // assertion about more LFs than expected (print.length-1 supports the code using BufferedReader.readLine())
        assertTrue(expected.size() == print.length -1 || expected.size() == print.length,"読み込みファイルの行数(" + expected.size() +"よりも多くの改行があります!");
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
            "例外処理をthrowしてはいけません!"
        );
        // undo the binding in System
        System.setOut(originalOut);
        
        assertTrue(bosOut.toString().contains("ファイル読み込みで例外が発生しました。処理を中断します！"),
            "読み込みで例が発生したことを示すメッセージ表示がない、またはその内容が指示と異なります!" );
    }
}
