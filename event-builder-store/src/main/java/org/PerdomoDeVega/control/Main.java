package org.PerdomoDeVega.control;

import org.PerdomoDeVega.exception.ReceiverException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EventStoreController eventStoreController = new EventStoreController(new FileBuilderStore("C:\\Users\\Usuario\\Downloads\\WeatherDataLake"), new WeatherEventReceiver("tcp://localhost:61616", "Weather.prediction.test"));
        List<String> events = null;
        try {
            events = eventStoreController.getEventReceiver().ReceiveEvent();
        } catch (ReceiverException e) {
            System.out.println(e.getMessage());
        }

        assert events != null;
        for (String event : events) {
            eventStoreController.getBuilderStore().StoreWeather(event);
            System.out.println(event);
        }
    }
}
