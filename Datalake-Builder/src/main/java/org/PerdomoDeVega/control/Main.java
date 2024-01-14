package org.PerdomoDeVega.control;

import org.PerdomoDeVega.exception.ReceiverException;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> topicsName = new ArrayList<>();
        topicsName.add("Weather.Prediction");
        topicsName.add("Hotel.Rates");
        EventStoreController eventStoreController = new EventStoreController(new FilePredictionStore(args[0]), new WeatherHotelEventReceiver("tcp://localhost:61616", topicsName));
            List<List<String>> events = null;
            try {
                events = eventStoreController.getEventReceiver().ReceiveEvent();
            } catch (ReceiverException e) {
                System.out.println(e.getMessage());
            }

            assert events != null;
        for (int i = 0; i < 2; i++) {
            for (String event : events.get(i)) {
                if (i == 0) {
                    eventStoreController.getBuilderStore().StoreWeather(event);
                }
                else{
                    eventStoreController.getBuilderStore().StoreHotelRates(event);
                }
            }
        }
    }
}
