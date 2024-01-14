package org.PerdomoDeVega.control;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.PerdomoDeVega.model.HotelRates;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XoteloProvider implements HotelProvider{

    public HotelRates getHotelRates(String hotelName, String hotelKey, String checkIn, String checkOut, String rooms, String adults, String childrens, String currency, String island) {
        String apiRequest = String.format("https://data.xotelo.com/api/rates?hotel_key=%s&chk_in=%s&chk_out=%s&rooms=%s&adults=%s&childrens=%s&currency=%s", hotelKey, checkIn, checkOut, rooms, adults, childrens, currency);
        HotelRates hotelRates;
        try {
            Connection.Response response = Jsoup.connect(apiRequest)
                    .ignoreContentType(true)
                    .execute();

            String jsonResponse = response.body();
            JsonObject hotelJson = JsonParser.parseString(jsonResponse).getAsJsonObject();
            if (hotelJson == null){
                System.out.println("Json == null");
            }
            hotelRates = fromJsonToHotelVisit(hotelJson, adults, childrens, rooms, hotelName, island);
        } catch (IOException e) {
            e.getCause();
            return null;
        }
        return hotelRates;
    }

    private HotelRates fromJsonToHotelVisit(JsonObject hotelJson, String adults, String childrens, String rooms, String hotelName, String island){
        String checkIn = hotelJson.getAsJsonObject("result").get("chk_in").getAsString();
        String checkOut = hotelJson.getAsJsonObject("result").get("chk_out").getAsString();
        String currency = hotelJson.getAsJsonObject("result").get("currency").getAsString();
        JsonArray JsonArray = hotelJson.getAsJsonObject("result").getAsJsonArray("rates");
        List<String> hotelOption = new ArrayList<>();
        List<Integer> hotelPrices = new ArrayList<>();
        for (int i = 0; i < JsonArray.size(); i++) {
            hotelOption.add(JsonArray.get(i).getAsJsonObject().get("name").getAsString());
            hotelPrices.add(JsonArray.get(i).getAsJsonObject().get("rate").getAsInt());
        }
        List<String> checkInCheckOut = new ArrayList<>();
        checkInCheckOut.add(checkIn);
        checkInCheckOut.add(checkOut);
        return new HotelRates(hotelName, hotelOption, hotelPrices, checkInCheckOut , currency, adults, childrens, rooms, island);
    }
}
