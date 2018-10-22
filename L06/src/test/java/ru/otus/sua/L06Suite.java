package ru.otus.sua;

import ru.otus.sua.client.L06Test;
import com.google.gwt.junit.tools.GWTTestSuite;
import junit.framework.Test;
import junit.framework.TestSuite;

public class L06Suite extends GWTTestSuite {
  public static Test suite() {
    TestSuite suite = new TestSuite("Tests for L06");
    suite.addTestSuite(L06Test.class);
    return suite;
  }
}
