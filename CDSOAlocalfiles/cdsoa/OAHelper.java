package com.roche.rapid.test.APIAutomation.CustomTests.Analytics.cdsoa;
import com.roche.rapid.test.APIAutomation.Helpers.APILogger;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OAHelper {


    public static String organization_uuid = null;
    public static void validatePatientInDatabase(Connection connection, SoftAssert softAssert, String tableName, String fieldName, String param) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            String query = "SELECT * FROM " + tableName + " WHERE " + fieldName + " = '" + param + "'";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String patient_uuid = resultSet.getString("patientid");
                String patient_blob = resultSet.getString("patientblob");
                APILogger.info("patient_uuid is present in database: " + patient_uuid);
                APILogger.info("patient_blob Json is present in database: " + patient_blob);
//           softAssert.assertEquals(resultSet.getString(1), 0, "failed to delete " + fieldName + " from " + tableName);
            } } catch (SQLException e) {
            Assert.fail(e.getMessage());
        } finally {
            try {
                resultSet.close();
            } catch (Exception e) { /* ignored */ }
            try {
                statement.close();
            } catch (Exception e) { /* ignored */ }
        }
    }
    public static List<String> getIndividualOrganizationDatabase(Connection connection, SoftAssert softAssert, String tableName, String fieldName, String param) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
//        List<String> organization_uuid = Arrays.asList();
        List<String> organization_uuid_list = new ArrayList<>();
        try {
            String query = "SELECT organization_uuid  FROM " + tableName + " limit "+param;
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                organization_uuid = resultSet.getString("organization_uuid");
                APILogger.info("organization_uuid: " + organization_uuid);
                organization_uuid_list.add(organization_uuid);


//            softAssert.assertEquals(Integer.parseInt(resultSet.getString(1)), 0, "failed to delete " + fieldName + " from " + tableName);
            } } catch (SQLException e) {
            Assert.fail(e.getMessage());
        } finally {
            try {
                resultSet.close();
            } catch (Exception e) { /* ignored */ }
            try {
                statement.close();
            } catch (Exception e) { /* ignored */ }
        }

        return organization_uuid_list;
    }
    public static List<String> validateOrganizationdestination(Connection connection, SoftAssert softAssert, String tableName, String fieldName, String param) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<String> organization_uuid_list = new ArrayList<>();
        try {
            String query = "SELECT datname FROM " + tableName + " WHERE " + fieldName +" = '" + param + "'"+" AND NOT datname IN " +"('postgres','analytics_db')";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                organization_uuid = resultSet.getString(1);

//                APILogger.info("Organization database is created in destination database: " + organization_uuid_list);
                organization_uuid_list.add(organization_uuid);

//            softAssert.assertEquals(Integer.parseInt(resultSet.getString(1)), 0, "failed to delete " + fieldName + " from " + tableName);

            } } catch (SQLException e) {
            Assert.fail(e.getMessage());
        } finally {
            try {
                resultSet.close();
            } catch (Exception e) { /* ignored */ }
            try {
                statement.close();
            } catch (Exception e) { /* ignored */ }
        }
        return organization_uuid_list;
    }
    public static String getOrganizationDatabase(Connection connection, SoftAssert softAssert, String tableName, String fieldName, String param) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
//        String organization_uuid = Arrays.asList();

        String organization_uuid_list = null;
        try {
            String query = "SELECT organization_uuid  FROM " + tableName + " limit " + param;
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            organization_uuid_list = null;
            while (resultSet.next()) {
                organization_uuid = resultSet.getString("organization_uuid");
                APILogger.info("organization_uuid: " + organization_uuid);


//            softAssert.assertEquals(Integer.parseInt(resultSet.getString(1)), 0, "failed to delete " + fieldName + " from " + tableName);
            }
        } catch (SQLException e) {
            Assert.fail(e.getMessage());
        } finally {
            try {
                resultSet.close();
            } catch (Exception e) { /* ignored */ }
            try {
                statement.close();
            } catch (Exception e) { /* ignored */ }
        }

        return organization_uuid;
    }
    public static String getOrganizationCount(Connection connection, SoftAssert softAssert, String tableName, String fieldName, String param) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;


        String organization_uuid_list = null;
        String organization_count = null;
        try {
            String query = "SELECT count(organization_uuid)  FROM " + tableName + " limit " + param;
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            organization_count = null;
            while (resultSet.next()) {
                organization_count = resultSet.getString(1);
                APILogger.info("organization count is: " + organization_count);


//            softAssert.assertEquals(Integer.parseInt(resultSet.getString(1)), 0, "failed to delete " + fieldName + " from " + tableName);
            }
        } catch (SQLException e) {
            Assert.fail(e.getMessage());
        } finally {
            try {
                resultSet.close();
            } catch (Exception e) { /* ignored */ }
            try {
                statement.close();
            } catch (Exception e) { /* ignored */ }
        }

        return organization_count;
    }
    public static String validateOrganizationCountDestination(Connection connection, SoftAssert softAssert, String tableName, String fieldName, String param) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
       String organization_count = null;
        try {
            String query = "SELECT count(datname) FROM " + tableName + " WHERE " + fieldName +" = '" + param + "'"+" AND NOT datname IN " +"('postgres','analytics_db')";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                organization_count = resultSet.getString(1);

               APILogger.info("Organization count in destination: " + organization_count);


//            softAssert.assertEquals(Integer.parseInt(resultSet.getString(1)), 0, "failed to delete " + fieldName + " from " + tableName);

            } } catch (SQLException e) {
            Assert.fail(e.getMessage());
        } finally {
            try {
                resultSet.close();
            } catch (Exception e) { /* ignored */ }
            try {
                statement.close();
            } catch (Exception e) { /* ignored */ }
        }
        return organization_count;
    }
}
