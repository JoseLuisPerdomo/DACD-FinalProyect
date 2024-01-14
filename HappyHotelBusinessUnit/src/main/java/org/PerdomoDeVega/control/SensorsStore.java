package org.PerdomoDeVega.control;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;

public class SensorsStore implements EventStore{

    private static String path;
    private static String fileName;

    public SensorsStore(String path) {
        SensorsStore.path = path;
        path = path + "DataMart";
        File directory = new File(path);
        directory.mkdirs();
        File subdirectory = new File(path + "\\currentEvents");
        subdirectory.mkdirs();
        File subdirectoryprediction = new File(path + "\\currentEvents\\Weather.Predictions");
        subdirectoryprediction.mkdirs();
        File subdirectoryX = new File(path + "\\currentEvents\\Hotel.Rates");
        subdirectoryX.mkdirs();
    }

    @Override
    public void StoreWeather(String message) {
        LocalDateTime localDateTime = LocalDateTime.now();
        String today = localDateTime.toString();
        today = today.substring(0, 10).replace("-", "");
        createFile("\\currentEvents\\Weather.Predictions" + "\\" + today + ".events");
        message = message + "\n";

        try {
            Files.write(Paths.get(path + "\\" + fileName), message.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void StoreHotelRates(String hotelRates) {
        LocalDateTime localDateTime = LocalDateTime.now();
        String today = localDateTime.toString();
        today = today.substring(0, 10).replace("-", "");
        createFile("\\currentEvents\\Hotel.Rates" + "\\" + today + ".events");
        hotelRates = hotelRates + "\n";

        try {
            Files.write(Paths.get(path + "\\" + fileName), hotelRates.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void createFile(String fileName) {
        File file = new File(path + "\\" + fileName);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        SensorsStore.fileName = fileName;
    }

}
