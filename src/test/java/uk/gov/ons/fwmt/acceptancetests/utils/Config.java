package uk.gov.ons.fwmt.acceptancetests.utils;

public class Config {

  public static final String JOB_SVC_URL = System.getenv("JOB_SVC_URL");

  public static final String TM_RESPONSE_ENDPOINT = "jobs/ws/GenericOutgoingWs.wsdl";

  public static final String BASIC_AUTH_USER = System.getenv("BASIC_AUTH_USER");

  public static final String BASIC_AUTH_PASSWORD = System.getenv("BASIC_AUTH_PASSWORD");

  //TM Credentials
  public static final String TM_USERNAME = System.getenv("TM_USERNAME");

  public static final String TM_PASSWORD = System.getenv("TM_PASSWORD");

  public static final String TM_URL = System.getenv("TM_URL");


  //Legacy Smoke Tests
  public static final String RS_USERNAME = System.getenv("RS_USERNAME");
  public static final String RS_PASSWORD = System.getenv("RS_PASSWORD");
  public static final String RS_URL = System.getenv("RS_URL");

  public static final String LS_USERNAME = System.getenv("LS_USERNAME");
  public static final String LS_PASSWORD = System.getenv("LS_PASSWORD");
  public static final String LS_URL = System.getenv("LS_URL");
  
  public static final String SS_USERNAME = System.getenv("SS_USERNAME");
  public static final String SS_PASSWORD = System.getenv("SS_PASSWORD");
  public static final String SS_URL = System.getenv("SS_URL");

}
