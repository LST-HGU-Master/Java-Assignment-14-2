import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
/**
 * @version (20220703)
 */
public class ProgE2Test {
    InputStream originalIn;
    PrintStream originalOut;
    ByteArrayOutputStream bos;
    StandardInputStream in;
    
    @BeforeEach
    void before() {
        //back up binding
        originalIn  = System.in;
        originalOut = System.out;
        //modify binding
        bos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(bos));
        in = new StandardInputStream();
        System.setIn(in);
    }

    @AfterEach
    void after() {
       System.setOut(originalOut);
       System.setIn(originalIn);
    }

    @Test
    public void testReadResult1()
    {
        String filename = "textfile1.txt";
        // prepairing expected texts from the sourcefile
        String line;
        ArrayList<String> expected = new ArrayList<String>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            while ( (line = br.readLine()) != null){
                expected.add(line);
            }
        } catch( IOException e) {
            after();
            fail("IOException occurred!（" + filename + "を用意していますか？）");
        }

        // action
        ProgE2.main(new String[]{filename}); // 実行時引数をテストする場合
        // undo the binding in System
        after(); // redundant?
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
    public void testReadResult2()
    {
        // preparing an original textfile
        String filename = "text1.csv";
        String [] dt = {"1.apple\n", "2.orange\n", "3.cucumber"};
        try (FileWriter fw = new FileWriter(filename) ) {
            for(int i=0; i<dt.length; i++) {
                fw.write(dt[i]);
            }
        } catch ( Exception e) {
            fail("Exception has occured during preparing test data!");
        }

        // action
        ProgE2.main(new String[]{filename}); // expecting args[0] is applied
        
        // recover to the condition before this test
        after(); // redundant?
        File f = new File(filename); f.delete();
        
        String [] expected = dt;
        String [] print = bos.toString().split("\r\n|\n", -1);
        // assertion
        int count = 0;
        for(String expStr : expected) { 
            assertTrue(expStr.contains(print[count++]),"ファイルの内容("+ count +"行目)とprintしたものが一致しません! "); // expStr
        }
        // assertion about more LFs than expected (print.length-1 supports the code using BufferedReader.readLine())
        assertTrue(expected.length == print.length -1 || expected.length == print.length,"読み込みファイルの行数(" + expected.length +"よりも多くの改行があります!");
    }

    @Test
    public void testCatchException()
    {
        // assertion
        assertDoesNotThrow(
            () -> ProgE2.main(new String[]{"src/main/java/abcdefg.txt"}), 
            "例外処理をthrowしてはいけません!"
        );
        // undo the binding in System
        after(); // redundant?
        
        assertTrue(bos.toString().contains("ファイル読み込みで例外が発生しました。処理を中断します！"),
            "読み込みで例が発生したことを示すメッセージ表示がない、またはその内容が指示と異なります!" );
    }
}
