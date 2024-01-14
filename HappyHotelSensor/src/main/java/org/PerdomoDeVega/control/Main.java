package org.PerdomoDeVega.control;

import org.PerdomoDeVega.exception.StoreException;
import org.PerdomoDeVega.model.HotelRates;
import org.PerdomoDeVega.model.HotelRatesEvent;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
    public static void main(String[] args) {

        HotelRatesController hotelRatesController = new HotelRatesController(new XoteloProvider(), new XoteloEventStore("Hotel.Rates"));

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                LocalDateTime localDateTime = LocalDateTime.now().plusDays(1);
                String checkIn = localDateTime.toString().substring(0, 10);
                String checkOut = localDateTime.plusDays(5).toString().substring(0, 10);


                List<List<String>> requestParameters = getRequestParameters(args[0]);
                assert requestParameters != null;
                for(List<String> hotelParameters :requestParameters)

                {
                    for (int i = 1; i + 1 < hotelParameters.size(); i += 2) {
                        String island = hotelParameters.get(0);
                        String hotelName = hotelParameters.get(i);
                        String hotelKey = hotelParameters.get(i + 1);
                        HotelRates hotelRates = hotelRatesController.getHotelProvider().getHotelRates(hotelName, hotelKey, checkIn, checkOut, "1", "1", "0", "EUR", island);
                        try {
                            hotelRatesController.getHotelEventStore().StoreHotelRates(new HotelRatesEvent(hotelRates));
                        } catch (StoreException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
                System.out.println("Hotel Rates stored correctly. Executing every six hours...");
            }


        };
        long delay = 0;
        long period = 6 * 60 * 1000;
        timer.scheduleAtFixedRate(task, delay, period);
    }

    public static List<List<String>> getRequestParameters(String path){
        //String path = "C:\\Users\\Usuario\\Desktop\\islandsHotels.txt";
        List<List<String>> hotelKeys = new ArrayList<>();
        hotelKeys.add(new ArrayList<>());
        hotelKeys.add(new ArrayList<>());
        hotelKeys.add(new ArrayList<>());
        hotelKeys.add(new ArrayList<>());
        hotelKeys.add(new ArrayList<>());
        hotelKeys.add(new ArrayList<>());
        hotelKeys.add(new ArrayList<>());
        hotelKeys.add(new ArrayList<>());
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            int j = 0;
            while ((line = br.readLine()) != null) {

                String[] parts = line.split(",");


                String name = parts[0].trim();
                hotelKeys.get(j).add(name);

                String hotelSet;
                for (int i = 1; i < parts.length; i++) {
                    hotelSet = parts[i].trim();
                    String[] keyValue = hotelSet.split(":");
                    hotelKeys.get(j).add(keyValue[0].trim());
                    hotelKeys.get(j).add(keyValue[1].trim());

                }
                j++;
            }
            return hotelKeys;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

}
