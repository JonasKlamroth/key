package org.key_project.monkey.product.ui.test.testcase;

import java.io.File;
import java.io.IOException;
import java.util.List;

import junit.framework.TestCase;

import org.key_project.monkey.product.ui.batch.MonKeYBatchModeParameters;
import org.key_project.util.java.CollectionUtil;
import org.key_project.util.java.IOUtil;

/**
 * Tests for {@link MonKeYBatchModeParameters}
 * @author Martin Hentschel
 */
public class MonKeYBatchApplicationParametersTest extends TestCase {
   /**
    * Tests {@link MonKeYBatchModeParameters#analyze(String[])}, 
    * in particular the locations together with a boot class path.
    */
   public void testAnalyse_outputLocationAndRounds() throws IOException {
      // Create temporary directories
      File outDir = IOUtil.createTempDirectory("MonKeYBatchApplicationParametersTest", "out");
      File path = IOUtil.createTempDirectory("MonKeYBatchApplicationParametersTest", "path");
      try {
         // Test valid combination and valid rounds
         String[] array = {MonKeYBatchModeParameters.PARAM_ROUNDS, "42", MonKeYBatchModeParameters.PARAM_OUTPUT_PATH, outDir.toString(), path.toString()};
         MonKeYBatchModeParameters parameters = MonKeYBatchModeParameters.analyze(array);
         assertParameters(parameters, true, false, false, false, false, false, false, false, null, outDir.toString(), CollectionUtil.toList(path.toString()), 42);
         // Test valid combination and invalid rounds (negative)
         array = new String[] {MonKeYBatchModeParameters.PARAM_ROUNDS, "-5", MonKeYBatchModeParameters.PARAM_OUTPUT_PATH, outDir.toString(), path.toString()};
         parameters = MonKeYBatchModeParameters.analyze(array);
         assertParameters(parameters, false, false, false, false, false, false, false, false, null, outDir.toString(), CollectionUtil.toList(path.toString()), -5);
         // Test valid combination and invalid rounds (no number)
         array = new String[] {MonKeYBatchModeParameters.PARAM_ROUNDS, "asdf", MonKeYBatchModeParameters.PARAM_OUTPUT_PATH, outDir.toString(), path.toString()};
         parameters = MonKeYBatchModeParameters.analyze(array);
         assertParameters(parameters, false, false, false, false, false, false, false, false, null, outDir.toString(), CollectionUtil.toList(path.toString()), 1);
      }
      finally {
         IOUtil.delete(outDir);
         IOUtil.delete(path);
      }
   }
   
   /**
    * Tests {@link MonKeYBatchModeParameters#analyze(String[])}, 
    * in particular the locations together with a boot class path.
    */
   public void testAnalyse_outputLocationAndBootClassPath() throws IOException {
      // Create temporary directories
      File outDir = IOUtil.createTempDirectory("MonKeYBatchApplicationParametersTest", "out");
      File path = IOUtil.createTempDirectory("MonKeYBatchApplicationParametersTest", "path");
      File boot = IOUtil.createTempDirectory("MonKeYBatchApplicationParametersTest", "boot");
      try {
         // Test valid combination, no boot class path
         String[] array = {MonKeYBatchModeParameters.PARAM_OUTPUT_PATH, outDir.toString(), path.toString()};
         MonKeYBatchModeParameters parameters = MonKeYBatchModeParameters.analyze(array);
         assertParameters(parameters, true, false, false, false, false, false, false, false, null, outDir.toString(), CollectionUtil.toList(path.toString()), 1);
         // Test valid combination, existing boot class path
         array = new String[] {MonKeYBatchModeParameters.PARAM_BOOT_CLASS_PATH, boot.toString(), MonKeYBatchModeParameters.PARAM_OUTPUT_PATH, outDir.toString(), path.toString()};
         parameters = MonKeYBatchModeParameters.analyze(array);
         assertParameters(parameters, true, false, false, false, false, false, false, false, boot.toString(), outDir.toString(), CollectionUtil.toList(path.toString()), 1);
         // Test invalid combination, not existing boot class path
         array = new String[] {MonKeYBatchModeParameters.PARAM_BOOT_CLASS_PATH, boot.toString() + "INVALID", MonKeYBatchModeParameters.PARAM_OUTPUT_PATH, outDir.toString(), path.toString()};
         parameters = MonKeYBatchModeParameters.analyze(array);
         assertParameters(parameters, false, false, false, false, false, false, false, false, boot.toString() + "INVALID", outDir.toString(), CollectionUtil.toList(path.toString()), 1);
      }
      finally {
         IOUtil.delete(outDir);
         IOUtil.delete(path);
         IOUtil.delete(boot);
      }
   }
   
   /**
    * Tests {@link MonKeYBatchModeParameters#analyze(String[])}, 
    * in particular the locations.
    */
   public void testAnalyse_outputAndLocation() throws IOException {
      // Create temporary directories
      File outDir = IOUtil.createTempDirectory("MonKeYBatchApplicationParametersTest", "out");
      File path1 = IOUtil.createTempDirectory("MonKeYBatchApplicationParametersTest", "path1");
      File path2 = IOUtil.createTempDirectory("MonKeYBatchApplicationParametersTest", "path2");
      File path3 = IOUtil.createTempDirectory("MonKeYBatchApplicationParametersTest", "path3");
      try {
         // Test one location
         String[] array = {MonKeYBatchModeParameters.PARAM_OUTPUT_PATH, outDir.toString(), path1.toString()};
         MonKeYBatchModeParameters parameters = MonKeYBatchModeParameters.analyze(array);
         assertParameters(parameters, true, false, false, false, false, false, false, false, null, outDir.toString(), CollectionUtil.toList(path1.toString()), 1);
         // Test two locations
         array = new String[] {path1.toString(), MonKeYBatchModeParameters.PARAM_OUTPUT_PATH, outDir.toString(), path2.toString()};
         parameters = MonKeYBatchModeParameters.analyze(array);
         assertParameters(parameters, true, false, false, false, false, false, false, false, null, outDir.toString(), CollectionUtil.toList(path1.toString(), path2.toString()), 1);
         // Test three locations
         array = new String[] {path1.toString(), path2.toString(), path3.toString(), MonKeYBatchModeParameters.PARAM_OUTPUT_PATH, outDir.toString()};
         parameters = MonKeYBatchModeParameters.analyze(array);
         assertParameters(parameters, true, false, false, false, false, false, false, false, null, outDir.toString(), CollectionUtil.toList(path1.toString(), path2.toString(), path3.toString()), 1);
         // Test not existing output path
         array = new String[] {MonKeYBatchModeParameters.PARAM_OUTPUT_PATH, outDir.toString() + "INVALID", path1.toString()};
         parameters = MonKeYBatchModeParameters.analyze(array);
         assertParameters(parameters, false, false, false, false, false, false, false, false, null, outDir.toString() + "INVALID", CollectionUtil.toList(path1.toString()), 1);
         // Test not existing location
         array = new String[] {path1.toString(), path2.toString() + "INVALID", path3.toString(), MonKeYBatchModeParameters.PARAM_OUTPUT_PATH, outDir.toString()};
         parameters = MonKeYBatchModeParameters.analyze(array);
         assertParameters(parameters, false, false, false, false, false, false, false, false, null, outDir.toString(), CollectionUtil.toList(path1.toString(), path2.toString() + "INVALID", path3.toString()), 1);
      }
      finally {
         IOUtil.delete(outDir);
         IOUtil.delete(path1);
         IOUtil.delete(path2);
         IOUtil.delete(path3);
      }
   }
   
   /**
    * Tests {@link MonKeYBatchModeParameters#analyze(String[])}, 
    * in particular the locations.
    */
   public void testAnalyse_locations() {
      // Test one location
      String[] array = {"path1"};
      MonKeYBatchModeParameters parameters = MonKeYBatchModeParameters.analyze(array);
      assertParameters(parameters, false, false, false, false, false, false, false, false, null, null, CollectionUtil.toList("path1"), 1);
      // Test two locations
      array = new String[] {"path1", "path2"};
      parameters = MonKeYBatchModeParameters.analyze(array);
      assertParameters(parameters, false, false, false, false, false, false, false, false, null, null, CollectionUtil.toList("path1", "path2"), 1);
      // Test three locations
      array = new String[] {"path1", "path2", "path3"};
      parameters = MonKeYBatchModeParameters.analyze(array);
      assertParameters(parameters, false, false, false, false, false, false, false, false, null, null, CollectionUtil.toList("path1", "path2", "path3"), 1);
   }
   
   /**
    * Tests {@link MonKeYBatchModeParameters#analyze(String[])}, 
    * in particular the output path option.
    */
   public void testAnalyse_outputPath() {
      // Test valid parameter pair
      String[] array = {MonKeYBatchModeParameters.PARAM_OUTPUT_PATH, "path"};
      MonKeYBatchModeParameters parameters = MonKeYBatchModeParameters.analyze(array);
      assertParameters(parameters, false, false, false, false, false, false, false, false, null, "path", null, 1);
      // Test missing path definition
      array = new String[] {MonKeYBatchModeParameters.PARAM_OUTPUT_PATH};
      parameters = MonKeYBatchModeParameters.analyze(array);
      assertParameters(parameters, false, false, false, false, false, false, false, false, null, null, null, 1);
   }
   
   /**
    * Tests {@link MonKeYBatchModeParameters#analyze(String[])}, 
    * in particular the rounds option.
    */
   public void testAnalyse_rounds() {
      // Test valid parameter pair
      String[] array = {MonKeYBatchModeParameters.PARAM_ROUNDS, "42"};
      MonKeYBatchModeParameters parameters = MonKeYBatchModeParameters.analyze(array);
      assertParameters(parameters, false, false, false, false, false, false, false, false, null, null, null, 42);
      // Test missing path definition
      array = new String[] {MonKeYBatchModeParameters.PARAM_ROUNDS};
      parameters = MonKeYBatchModeParameters.analyze(array);
      assertParameters(parameters, false, false, false, false, false, false, false, false, null, null, null, 1);
   }
   
   /**
    * Tests {@link MonKeYBatchModeParameters#analyze(String[])}, 
    * in particular the boot class path option.
    */
   public void testAnalyse_bootClassPath() {
      // Test valid parameter pair
      String[] array = {MonKeYBatchModeParameters.PARAM_BOOT_CLASS_PATH, "path"};
      MonKeYBatchModeParameters parameters = MonKeYBatchModeParameters.analyze(array);
      assertParameters(parameters, false, false, false, false, false, false, false, false, "path", null, null, 1);
      // Test missing path definition
      array = new String[] {MonKeYBatchModeParameters.PARAM_BOOT_CLASS_PATH};
      parameters = MonKeYBatchModeParameters.analyze(array);
      assertParameters(parameters, false, false, false, false, false, false, false, false, null, null, null, 1);
   }
   
   /**
    * Tests {@link MonKeYBatchModeParameters#analyze(String[])}, 
    * in particular the dummy load option.
    */
   public void testAnalyse_dummyLoad() {
      String[] array = {MonKeYBatchModeParameters.PARAM_DUMMY_LOAD_OFF};
      MonKeYBatchModeParameters parameters = MonKeYBatchModeParameters.analyze(array);
      assertParameters(parameters, false, false, false, false, false, false, false, true, null, null, null, 1);
   }
   
   /**
    * Tests {@link MonKeYBatchModeParameters#analyze(String[])}, 
    * in particular the main window option.
    */
   public void testAnalyse_mainWindow() {
      String[] array = {MonKeYBatchModeParameters.PARAM_MAIN_WINDOW_OFF};
      MonKeYBatchModeParameters parameters = MonKeYBatchModeParameters.analyze(array);
      assertParameters(parameters, false, false, false, false, false, false, true, false, null, null, null, 1);
   }
   
   /**
    * Tests {@link MonKeYBatchModeParameters#analyze(String[])}, 
    * in particular the query treatment option.
    */
   public void testAnalyse_queryTreatment() {
      String[] array = {MonKeYBatchModeParameters.PARAM_QUERY_TREATMENT_OFF};
      MonKeYBatchModeParameters parameters = MonKeYBatchModeParameters.analyze(array);
      assertParameters(parameters, false, false, false, false, false, true, false, false, null, null, null, 1);
   }
   
   /**
    * Tests {@link MonKeYBatchModeParameters#analyze(String[])}, 
    * in particular the method treatment option.
    */
   public void testAnalyse_methodTreatment() {
      String[] array = {MonKeYBatchModeParameters.PARAM_METHOD_TREATMENT_CONTRACT};
      MonKeYBatchModeParameters parameters = MonKeYBatchModeParameters.analyze(array);
      assertParameters(parameters, false, false, false, false, true, false, false, false, null, null, null, 1);
   }
   
   /**
    * Tests {@link MonKeYBatchModeParameters#analyze(String[])}, 
    * in particular the dependency contracts option.
    */
   public void testAnalyse_dependencyContracts() {
      String[] array = {MonKeYBatchModeParameters.PARAM_DEPENDENCY_CONTRACTS_OFF};
      MonKeYBatchModeParameters parameters = MonKeYBatchModeParameters.analyze(array);
      assertParameters(parameters, false, false, false, true, false, false, false, false, null, null, null, 1);
   }
   
   /**
    * Tests {@link MonKeYBatchModeParameters#analyze(String[])}, 
    * in particular the arithmetic treatment option.
    */
   public void testAnalyse_arithmeticTreatment() {
      String[] array = {MonKeYBatchModeParameters.PARAM_ARITHMETIC_TREATMENT_BASE};
      MonKeYBatchModeParameters parameters = MonKeYBatchModeParameters.analyze(array);
      assertParameters(parameters, false, false, true, false, false, false, false, false, null, null, null, 1);
   }
   
   /**
    * Tests {@link MonKeYBatchModeParameters#analyze(String[])}, 
    * in particular the help option.
    */
   public void testAnalyse_Help() {
      String[] array = {MonKeYBatchModeParameters.PARAM_SHOW_HELP};
      MonKeYBatchModeParameters parameters = MonKeYBatchModeParameters.analyze(array);
      assertParameters(parameters, true, true, false, false, false, false, false, false, null, null, null, 1);
   }
   
   /**
    * Tests {@link MonKeYBatchModeParameters#analyze(String[])}, 
    * in particular the help option with an empty parameter list.
    */
   public void testAnalyse_Empty() {
      String[] array = {};
      MonKeYBatchModeParameters parameters = MonKeYBatchModeParameters.analyze(array);
      assertParameters(parameters, true, true, false, false, false, false, false, false, null, null, null, 1);
   }
   
   /**
    * Makes sure that a correct {@link MonKeYBatchModeParameters} instance was created.
    * @param current The created {@link MonKeYBatchModeParameters}.
    * @param expectedValid The expected valid state.
    * @param expectedShowHelp The expected parameter value.
    * @param expectedArithmeticTreatmentBase The expected parameter value.
    * @param expectedDependencyContractsOff The expected parameter value.
    * @param expectedMethodTreatmentContract The expected parameter value.
    * @param expectedQueryTreatmentOff The expected parameter value.
    * @param expectedMainWindowOff The expected parameter value.
    * @param expectedDummyLoadOff The expected parameter value.
    * @param expectedBootClassPath The expected parameter value.
    * @param expectedOutputPath The expected parameter value.
    * @param expectedLocations The expected parameter value.
    * @param expectedNumberOfRounds The expected parameter value.
    */
   protected static void assertParameters(MonKeYBatchModeParameters current,
                                          boolean expectedValid,
                                          boolean expectedShowHelp,
                                          boolean expectedArithmeticTreatmentBase,
                                          boolean expectedDependencyContractsOff,
                                          boolean expectedMethodTreatmentContract,
                                          boolean expectedQueryTreatmentOff,
                                          boolean expectedMainWindowOff,
                                          boolean expectedDummyLoadOff,
                                          String expectedBootClassPath,
                                          String expectedOutputPath,
                                          List<String> expectedLocations,
                                          int expectedNumberOfRounds) {
      assertEquals(expectedArithmeticTreatmentBase, current.isArithmeticTreatmentBase());
      assertEquals(expectedDependencyContractsOff, current.isDependencyContractsOff());
      assertEquals(expectedMainWindowOff, current.isMainWindowOff());
      assertEquals(expectedMethodTreatmentContract, current.isMethodTreatmentContract());
      assertEquals(expectedQueryTreatmentOff, current.isQueryTreatmentOff());
      assertEquals(expectedShowHelp, current.isShowHelp());
      assertEquals(expectedDummyLoadOff, current.isDummyLoadOff());
      assertEquals(expectedBootClassPath, current.getBootClassPath());
      assertEquals(expectedOutputPath, current.getOutputPath());
      assertNotNull(current.getLocations());
      if (expectedLocations != null) {
         assertEquals(expectedLocations, current.getLocations());
      }
      else {
         assertTrue(current.getLocations().isEmpty());
      }
      try {
         assertEquals(expectedNumberOfRounds, current.getNumberOfRounds());
      }
      catch (Exception e) {
         if (expectedValid) {
            fail("Can't parse round numbers.");
         }
      }
      assertEquals(current.getErrorMessage(), expectedValid, current.isValid());
      if (expectedValid) {
         assertNull(current.getErrorMessage());
      }
      else {
         assertNotNull(current.getErrorMessage());
      }
   }
}