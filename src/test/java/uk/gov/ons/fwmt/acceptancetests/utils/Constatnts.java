package uk.gov.ons.fwmt.acceptancetests.utils;

import lombok.Data;

@Data
public class Constatnts {

  public static final String LMS_RESPONSE = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><soap:Body><request xmlns=\"http://schemas.consiliumtechnologies.com/services/mobile/2009/03/messaging\"><Id>4ae43740-c9c8-4258-8052-49fcc4d5fb39</Id><Content>&lt;![CDATA[&lt;?xml version=\"1.0\" encoding=\"UTF-8\"?&gt;\n"
      + "&lt;element xmlns:ns1=\"http://ons.gov.uk/fwmt/NonContactDetail\" xmlns:ns2=\"http://ons.gov.uk/fwmt/PropertyDetails\" xmlns:ns3=\"http://ons.gov.uk/fwmt/AdditionalProperties\" xmlns:ns7=\"http://schemas.consiliumtechnologies.com/mobile/2009/07/FormsTypes.xsd\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"\"&gt;\n"
      + "    &lt;eventDate&gt;2018-10-12T16:41:42.22&lt;/eventDate&gt;\n"
      + "    &lt;jobIdentity&gt;IDENTITY;/jobIdentity&gt;\n"
      + "    &lt;nonContactDetail/&gt;\n"
      + "    &lt;propertyDetails&gt;\n"
      + "        &lt;description&gt;DESCRIPTION&lt;/description&gt;\n"
      + "    &lt;/propertyDetails&gt;\n"
      + "    &lt;username&gt;USERNAME&lt;/username&gt;\n"
      + "    &lt;additionalProperties&gt;\n"
      + "        &lt;name&gt;NAME&lt;/name&gt;\n"
      + "    &lt;/additionalProperties&gt;\n"
      + "&lt;/element&gt;]]&gt;</Content><Format>fwmtOHSJobStatusNotification</Format></request></soap:Body></soap:Envelope>";


}
