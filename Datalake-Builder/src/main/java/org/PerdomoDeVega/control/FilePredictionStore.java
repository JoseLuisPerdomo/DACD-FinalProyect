package org.PerdomoDeVega.control;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;

public class FilePredictionStore implements BuilderStore{
    private static String path;
    private static String fileName;

    public FilePredictionStore(String path) {
        FilePredictionStore.path = path;
        path = path + "DataLake";
        File directory = new File(path);
        directory.mkdirs();
        File subdirectory = new File(path + "\\eventstore");
        subdirectory.mkdirs();
        File subdirectoryprediction = new File(path + "\\eventstore\\Weather.Prediction");
        subdirectoryprediction.mkdirs();
        File subdirectoryX = new File(path + "\\eventstore\\Hotel.Rates");
        subdirectoryX.mkdirs();
        File subsubdirectoryX = new File(path + "\\eventstore\\Hotel.Rates\\HappyHotelSensor");
        subsubdirectoryX.mkdirs();
        File subsubdirectoryWeather = new File(path + "\\eventstore\\Weather.Prediction\\Prediction-Provider");
        subsubdirectoryWeather.mkdirs();
    }

    @Override
    public void StoreWeather(String message) {
        LocalDateTime localDateTime = LocalDateTime.now();
        String today = localDateTime.toString();
        today = today.substring(0, 10).replace("-", "");
        createFile("\\eventstore\\Weather.Prediction\\Prediction-Provider" + "\\" + today + ".events");
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
        createFile("\\eventstore\\Hotel.Rates\\HappyHotelSensor" + "\\" + today + ".events");
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
        FilePredictionStore.fileName = fileName;
    }
}

