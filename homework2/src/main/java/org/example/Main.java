package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.util.ArrayDeque;
import java.util.Scanner;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static String URI_PATH = "https://api.weather.yandex.ru/v2/forecast?";
    private static String HEADER_KEY = "X-Yandex-Weather-Key";
    private static String HEADER_VALUE = "5168e424-d1e8-4f4f-a673-29797756cbb2";

    public static void main(String[] args) throws ParseException {

        Scanner scanner = new Scanner(System.in);
        HttpClient httpClient = HttpClient.newHttpClient();
        JSONParser parser = new JSONParser();
        System.out.println("Input Coordinates");
        float lat = scanner.nextFloat();
        float lon = scanner.nextFloat();

        String parameterised_URI_PATH = URI_PATH + "lat=" + lat + "&" + "lon=" + lon;
        System.out.println("URL will be: " + parameterised_URI_PATH);

        JSONObject jsonObject = getJSONFromResponse(httpClient, parameterised_URI_PATH, parser);

        System.out.println("JSON response from Yandex API: ");
        System.out.println(jsonObject.toString());
        System.out.println("Fact Temperature: ");
        System.out.println(parseFactTemperature(jsonObject));

        System.out.println("Input Period");
        int limit = scanner.nextInt();
        parameterised_URI_PATH = parameterised_URI_PATH + "&" + "limit=" + limit;
        System.out.println("URL will be: " + parameterised_URI_PATH);

        jsonObject = getJSONFromResponse(httpClient, parameterised_URI_PATH, parser);
        System.out.println("AVG Period Temperature: ");
        System.out.println(getAVGPeriodTemperature(jsonObject));

    }

    private static JSONObject getJSONFromResponse(HttpClient httpClient, String URL, JSONParser parser) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(URI_PATH)).headers(HEADER_KEY, HEADER_VALUE).GET().build();
        HttpResponse response = null;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            System.err.println("Error making HTTP request: " + e.getMessage());
        }
        System.out.println("Response Code:\n" + response);
        System.out.println("Response Code: " + response.statusCode());
        System.out.println(response.body());

        JSONObject jsObject = null;
        try {
            jsObject = (JSONObject) parser.parse(response.body().toString());
        } catch (ParseException e) {
            System.err.println("Error making HTTP request: " + e.getMessage());
        }

        return jsObject;
    }


    private static long parseFactTemperature(JSONObject jsObject) {
        JSONObject factObject = (JSONObject) jsObject.get("fact");
        Long factTemperature = (Long) factObject.get("temp");
        return factTemperature;
    }

    private static float getAVGPeriodTemperature(JSONObject jsObject) {
        JSONArray jsonArray = (JSONArray) jsObject.get("forecasts");
        long temperatureSumm = 0;

        for (Object object : jsonArray) {
            JSONObject partsObject = (JSONObject) ((JSONObject) object).get("parts");
            JSONObject dayObject = (JSONObject) partsObject.get("day");
            System.out.println(dayObject.get("temp_avg"));
            temperatureSumm += (Long) dayObject.get("temp_avg");
        }
        return (float) temperatureSumm / jsonArray.size();

    }
}

