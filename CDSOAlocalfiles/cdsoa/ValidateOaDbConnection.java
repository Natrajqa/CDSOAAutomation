package com.roche.rapid.test.APIAutomation.CustomTests.Analytics.cdsoa;

import com.roche.rapid.test.APIAutomation.CustomTests.CustomTestHelper;
import com.roche.rapid.test.APIAutomation.CustomTests.DBConnector;
import com.roche.rapid.test.APIAutomation.Helpers.APILogger;
import com.roche.rapid.test.APIAutomation.reporting.ExtentTestNGReportBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.apache.commons.configuration.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.sql.*;
import java.util.List;
import java.util.UUID;

import static com.roche.rapid.test.APIAutomation.CustomTests.Analytics.cdsoa.OAHelper.*;
import static com.roche.rapid.test.APIAutomation.CustomTests.CustomTestHelper.*;
import static io.restassured.RestAssured.given;

public class ValidateOaDbConnection extends ExtentTestNGReportBuilder {
    private DBConnector dbConnector;
    private Logger LOGGER = LoggerFactory.getLogger(ValidateOaDbConnection.class);
    private Connection connection;
    final static String url = "http://localhost:8181/report/";
    final static String patienturl = "http://localhost:14008/create/patient";
    private SoftAssert softAssert = new SoftAssert();
    private static String payload = "{   \"firstName\":\"natraj1new\",   \"lastName\":\"sdf\",   \"dateOfBirth\":\"1994-06-07T00:00:00-07:00\",   \"facility\":\"02bc2464-0cd4-45f5-87f2-7243614986e1\",   \"gender\":\"M\",   \"patientIdentifiers\":[      {         \"facility\":\"02bc2464-0cd4-45f5-87f2-7243614986e1\",         \"system\":\"http://dip.roche.com/context-identifier/impacted-patient-identifier/assigner-UHC/UHC\",         \"value\":\"53543\"      },      {         \"facility\":\"02bc2464-0cd4-45f5-87f2-7243614986e1\",         \"system\":\"http://dip.roche.com/context-identifier/impacted-patient-identifier/assigner-UHC/UEI\",         \"value\":\"53543\"      }   ],   \"contacts\":null,   \"versionId\":null 	}";
    private static String organizationuuid;
    @BeforeClass
    private void setup() throws ConfigurationException {
        dbConnector = new DBConnector();
        connection = dbConnector.getlocal1DBConnection();

      organizationuuid = getOrganizationDatabase(connection, softAssert, "organization", "report_id", "1");


    }

  @Test
    public void runReportQuery() throws ConfigurationException {
//        validateOrganizationDatabase(connection, softAssert, "organization", "report_id", "5");

      Response patientresponse = given().contentType(ContentType.JSON).body(payload).post(patienturl).then().statusCode(200).extract()
              .response();
      String patientUUID = patientresponse.jsonPath().get("uuid");
      APILogger.info("New patient is created and patientUUID is: "+patientUUID);
      Response Orgresponse = given().pathParam("reportId", 1).when().get(url + "{reportId}/start").then().assertThat().statusCode(200).extract().response();
      ResponseBody jobresponse = Orgresponse.getBody();
      APILogger.info(jobresponse.asString());


      try {
          Thread.sleep(5000);
      } catch (InterruptedException e) {
          LOGGER.error(e.getMessage());
      }
      connection = dbConnector.getlocalDBConnection(organizationuuid);
      validateOrganizationdestination(connection, softAssert, "pg_database", "datname", organizationuuid);
      validatePatientInDatabase(connection, softAssert, "patient", "patientid", patientUUID.toString());

  }

    @AfterClass
    private void cleanupAfterTest() {
        dbConnector.cleanup();
    }

}

