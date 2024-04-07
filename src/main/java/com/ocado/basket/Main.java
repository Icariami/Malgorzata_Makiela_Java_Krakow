package com.ocado.basket;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        BasketSplitter bs = new BasketSplitter("config.json");

        List<String> basket1 = getBasket("basket-1.json");
        System.out.println("First basket: " + basket1 + "\n");

        Map<String, List<String>> solution1 = bs.split(basket1);
        System.out.println("First basket split into delivery options: ");
        print(solution1);

        List<String> basket2 = getBasket("basket-2.json");
        System.out.println("Second basket: " + basket2 + "\n");

        Map<String, List<String>> solution2 = bs.split(basket2);
        System.out.println("Second basket split into delivery options: ");
        print(solution2);
    }

    public static List<String> getBasket(String file) {
        List<String> basket = new ArrayList<>();

        try (InputStream stream = BasketSplitter.class.getClassLoader().getResourceAsStream(file)) {
            if (stream == null)
                throw new NullPointerException("Error occurred while loading the file stream");

            try (Reader reader = new InputStreamReader(stream)) {
                JSONParser parser = new JSONParser();
                JSONArray products = (JSONArray) parser.parse(reader);
                if (products.isEmpty()) {
                    System.out.println("Basket is empty!");
                }
                for (Object product : products) {
                    basket.add((String) product);
                }

            } catch (IOException | ParseException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return basket;
    }

    public static void print(Map<String, List<String>> map) {
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        System.out.println();
    }
}
