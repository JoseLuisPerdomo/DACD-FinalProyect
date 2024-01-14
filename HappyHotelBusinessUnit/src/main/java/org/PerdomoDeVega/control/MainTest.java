package org.PerdomoDeVega.control;

import org.PerdomoDeVega.exception.ReceiverException;
import org.PerdomoDeVega.view.HappyHotelTerminal;
import org.apache.activemq.ActiveMQConnectionFactory;

import java.util.ArrayList;
import java.util.List;

public class MainTest {
    public static void main(String[] args) {
        List<String> topicsNames = new ArrayList<>();
        topicsNames.add("Weather.Predictions");
        topicsNames.add("Hotel.Rates");
        SensorsReceiver sensorsReceiver = new SensorsReceiver(ActiveMQConnectionFactory.DEFAULT_BROKER_URL, topicsNames);
        List<List<String>> events;

        try {
            events = sensorsReceiver.ReceiveEvent();
        } catch (ReceiverException e) {
            throw new RuntimeException(e);
        }

        SensorsStore sensorsStore = new SensorsStore(args[0]);

        for (int i = 0; i < events.get(0).size(); i++) {
            sensorsStore.StoreWeather(events.get(0).get(i));
        }


        for (int i = 0; i < events.get(1).size(); i++) {
            sensorsStore.StoreHotelRates(events.get(1).get(i));
        }


        HappyHotelTerminal happyHotelTerminal = new HappyHotelTerminal(args[0]);
        happyHotelTerminal.executeTerminal();
    }
}
