package uk.gov.ons.fwmt.acceptancetests.utils;

public class Config {

  public static final String JOB_SVC_URL = System.getenv("JOB_SVC_URL");

  public static final String TM_RESPONSE_ENDPOINT = "jobs/ws/GenericOutgoingWs.wsdl";

  public static final String BASIC_AUTH_USER = System.getenv("BASIC_AUTH_USER");

  public static final String BASIC_AUTH_PASSWORD = System.getenv("BASIC_AUTH_PASSWORD");
}
