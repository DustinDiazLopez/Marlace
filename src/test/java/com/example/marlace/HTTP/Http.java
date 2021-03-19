package com.example.marlace.HTTP;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;

public class Http {

    public static String put(String url, Map<String, String> header, String body, int expectedResponseCode)
            throws IOException {
        return request(url, "PUT", header, body, expectedResponseCode);
    }

    public static String delete(String url, Map<String, String> header, String body, int expectedResponseCode)
            throws IOException {
        return request(url, "DELETE", header, body, expectedResponseCode);
    }

    public static String get(String url, Map<String, String> header, String body, int expectedResponseCode)
            throws IOException {
        return request(url, "GET", header, body, expectedResponseCode);
    }

    public static String post(String url, Map<String, String> header, String body, int expectedResponseCode)
            throws IOException {
        return request(url, "POST", header, body, expectedResponseCode);
    }

    public static String request(String url, String requestMethod, Map<String, String> header, String body,
                                 int expectedResponseCode) throws IOException {

        URL UrlObj = new URL(url);

        HttpURLConnection connection = (HttpURLConnection) UrlObj.openConnection();
        connection.setRequestMethod(requestMethod);
        connection.setDoOutput(true);

        for (Map.Entry<String, String> entry : header.entrySet()) {
            connection.setRequestProperty(entry.getKey(), entry.getValue());
        }

        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
        outputStream.writeBytes(body);
        outputStream.flush();
        outputStream.close();

        if (connection.getResponseCode() == expectedResponseCode) {
            BufferedReader inputReader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream())
            );

            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = inputReader.readLine()) != null) {
                response.append(inputLine);
            }

            inputReader.close();
            return response.toString();
        }

        return null;
    }
}
