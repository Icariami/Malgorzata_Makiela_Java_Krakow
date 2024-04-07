
package com.ocado.basket;
import java.io.*;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class BasketSplitter {
    private final Map<String, List<String>> deliveries = new HashMap<>();
    public BasketSplitter(String absolutePathToConfigFile) {

        try (InputStream stream = BasketSplitter.class.getClassLoader().getResourceAsStream(absolutePathToConfigFile)) {
            if (stream == null)
                throw new NullPointerException("Error occurred while loading the file stream");

            try (Reader reader = new InputStreamReader(stream)) {
                JSONParser parser = new JSONParser();
                JSONObject mainObject = (JSONObject) parser.parse(reader);

                for (Object key : mainObject.keySet()) {
                    String product = (String) key;
                    JSONArray delivery = (JSONArray) mainObject.get(product);
                    List<String> methods = new ArrayList<>();
                    for (Object method : delivery) {
                        methods.add((String) method);
                    }
                    deliveries.put(product, methods);
                }

            } catch (IOException | ParseException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, List<String>> split(List<String> items) {
        Map<String, List<String>> result = new HashMap<>();
        Map<String, List<String>> deliveryIndices = new HashMap<>();
        List<String> productsLeft = new ArrayList<>(items);

        for (String item : items) {
            for (String option : deliveries.get(item)) {
                List<String> indices = deliveryIndices.computeIfAbsent(option, k -> new ArrayList<>());
                indices.add(item);
            }
        }

        while(!productsLeft.isEmpty()) {
            productsLeft = getBiggestGroup(deliveryIndices, productsLeft, result, items);
            Map<String, List<String>> next = new HashMap<>();
            for (String item : productsLeft) {
                for (String option : deliveries.get(item)) {
                    List<String> indices = next.computeIfAbsent(option, k -> new ArrayList<>());
                    indices.add(item);
                }
            }
            deliveryIndices = next;
        }

        return result;
    }

    public List<String> getBiggestGroup (Map<String, List<String>> deliveryIndices, List<String> productsLeft, Map<String, List<String>> result, List<String> items) {
        int maxProducts = 0;
        String biggestGroup = null;
        for (String option : deliveryIndices.keySet()) {
            int numberOfProducts = deliveryIndices.get(option).size();
            if (numberOfProducts > maxProducts) {
                maxProducts = numberOfProducts;
                biggestGroup = option;
            }
        }

        result.put(biggestGroup, deliveryIndices.get(biggestGroup));

        List<String> productsToRemove = new ArrayList<>();
        for (String item : items) {
            if (deliveryIndices.get(biggestGroup).contains(item))
                productsToRemove.add(item);
        }
        productsLeft.removeAll(productsToRemove);

        return productsLeft;
    }
}
