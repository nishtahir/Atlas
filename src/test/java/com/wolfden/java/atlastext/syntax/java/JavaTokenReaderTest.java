package com.wolfden.java.atlastext.syntax.java;

import com.wolfden.java.atlastext.syntax.StyledTokenReader;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.eclipse.swt.custom.StyleRange;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 */
public class JavaTokenReaderTest {
    private static final String sampleJavaCode =
            "/**\n" +
            "* Sample java code\n" +
            "*/\n" +
            "public class SampleClass {\n" +
            "    public static void main(String[] args){\n" +
            "        System.out.println(\"Hello world!\");\n" +
            "    }\n" +
            "}\n";

    private CommonTokenStream tokenStream;
    private StyledTokenReader styledTokenReader;

    @Before
    public void setUp(){
        ANTLRInputStream antlrInputStream = new ANTLRInputStream(sampleJavaCode);
        com.wolfden.java.atlastext.syntax.java.JavaLexer javaLexer = new com.wolfden.java.atlastext.syntax.java.JavaLexer(antlrInputStream);

        tokenStream = new CommonTokenStream(javaLexer);
        assertNotNull(tokenStream);

        styledTokenReader = new JavaTokenReader();
        assertNotNull(styledTokenReader);
    }

    @Test
    public void testGetStyles_WithNullTokens_ReturnsZeroStyleRange(){
        StyleRange[] styleRange = styledTokenReader.getStyles(null, null);
        assertArrayEquals(new StyleRange[0], styleRange);
    }

    @Test
    public void testGetStyles_WithSampleTokens_ReturnsCorrectLength(){
        StyleRange[] styleRange = styledTokenReader.getStyles(tokenStream, null);
        assertEquals(7, styleRange.length);
    }
}