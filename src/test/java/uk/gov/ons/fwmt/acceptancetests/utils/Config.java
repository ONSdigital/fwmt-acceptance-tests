package uk.gov.ons.fwmt.acceptancetests.utils;

public class Config {

  public static final String JOB_SVC_URL = System.getenv("JOB_SVC_URL");

  public static final String TM_RESPONSE_ENDPOINT = "jobs/ws/GenericOutgoingWs.wsdl";

  public static final String BASIC_AUTH_USER = System.getenv("BASIC_AUTH_USER");

  public static final String BASIC_AUTH_PASSWORD = System.getenv("BASIC_AUTH_PASSWORD");

  //Smoke Tests
  public static final String RMA_URL = System.getenv("RMA_URL");

  public static final String RMA_RABBIT_URL = System.getenv("RMA_RABBIT_URL");

  public static final String JS_USERNAME = System.getenv("JS_USERNAME");

  public static final String JS_PASSWORD = System.getenv("JS_PASSWORD");

  public static final String JS_URL = System.getenv("JS_URL");

  public static final String JS_RABBIT_URL =  System.getenv("JS_RABBIT_URL");

  public static final String TM_USERNAME = System.getenv("TM_USERNAME");

  public static final String TM_PASSWORD = System.getenv("TM_PASSWORD");

  public static final String TM_URL = System.getenv("TM_URL");
}
