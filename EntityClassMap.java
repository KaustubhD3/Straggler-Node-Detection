/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ML;
import com.connection.Dbconn;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static javafx.scene.input.KeyCode.N;

/**
 *
 * This class contains constants to map the entity type/class for each NER tool. The types/classes
 * are used in FOX are {@link #L}, {@link #O}, {@link #P}, {@link #M} and {@link #N}.
 *
 * @author rspeck
 *
 */
public class EntityClassMap {

  public static final String A = "1";
  public static final String B = "0"; 
  
  public static final List<String> entityClasses = Arrays.asList(A,B);

  protected static final Map<String, String> entityClassesOracel = new HashMap<>();
  static {
    entityClassesOracel.put("1", A);
    entityClassesOracel.put("0", B); 
  }

  protected static final Map<String, String> entityClassesNEEL = new HashMap<>();
  static {
    entityClassesNEEL.put("1", A);
    entityClassesNEEL.put("0", B); 
  }

  protected static final Map<String, String> entityClassesILLINOIS = new HashMap<>();
  static {
   
      entityClassesILLINOIS.put("1", A);
    entityClassesILLINOIS.put("0", B); 
  }

  /**
   * Gets the entity class for a oracel entity type/class.
   */
  public static String oracel(final String tag) {
    String t = entityClassesOracel.get(tag);
    if (t == null) {
      t = getNullCategory();
    }
    return t;
  }

  /**
   * Gets the entity class for a NEEL challenge entity type/class.
   */
  public static String neel(final String tag) {
    String t = entityClassesNEEL.get(tag);
    if (t == null) {
      t = getNullCategory();
    }
    return t;
  }

  /**
   * Gets the entity class for a illinois entity type/class.
   */
  public static String illinois(final String illinoisTag) {
    String t = entityClassesILLINOIS.get(illinoisTag);
    if (t == null) {
      t = getNullCategory();
    }
    return t;
  }

  /**
   * Gets the null type/class.
   */
  public static String getNullCategory() {
    return "";
  }
}