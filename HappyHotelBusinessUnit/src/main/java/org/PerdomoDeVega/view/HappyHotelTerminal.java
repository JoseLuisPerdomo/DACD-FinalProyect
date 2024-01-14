package org.PerdomoDeVega.view;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HappyHotelTerminal implements BusinessTerminal {

    private List<String> places = new ArrayList<>();
    private final String weatherPath;

    public HappyHotelTerminal(String weatherPath) {
        places.add("Gran Canaria");
        places.add("Tenerife");
        places.add("La Palma");
        places.add("El Hierro");
        places.add("La Gomera");
        places.add("Fuerteventura");
        places.add("Lanzarote");
        places.add("La Graciosa");
        this.weatherPath = weatherPath;
    }

    @Override
    public void executeTerminal() {

        System.out.println("Please, try to write every command with the spaces between words and using the capital letters when it´s neccesary...");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }


        System.out.println("Happy Hotel Asistant is waking up...");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        Scanner myObj = new Scanner(System.in);
        System.out.println("How may I call you? :)");
        String command = myObj.nextLine();
        System.out.println("Hi, " + command);
        //System.out.println("If you want to close the app just write \"Thanks for all\"");
        System.out.println("First of all, I can help you finding the best hotels with the best weathers conditions available in Canary Islands. What island do you want to go?");
        System.out.println(places.toString());
        //TODO Select the best weather opction for the client, ask how many people will go and improve shell

        Scanner commandScann = new Scanner(System.in);
        command = commandScann.nextLine();
        String island = command;

        System.out.println("Let´s trip to " + command);
        System.out.println(command + "have these weathers conditions.");

        System.out.println(showIslandWeatherConditions(command, weatherPath));

        System.out.println("If you want to see the available hotels for this island, write \"Hotels\".");

        command = commandScann.nextLine();

        if (command.equals("Hotels")){

            System.out.println("We got the info of these hotels.");

            System.out.println(showIslandHotelRates(island, weatherPath));

        }

        System.out.println("That´s all the info I can provide you at this moment, if you want to see the information from another island, re-run my app. Thanks for everything.");
        //System.out.println("If you a specific hotel from this list, write it´s name exactly the way you are seen it.");
        //System.out.println("Otherwise, you can search the \"Cheapest Hotel\", the \"The Most Expensive Hotel\"");

        }




    private List<String> showIslandWeatherConditions(String island, String weatherPath) {

        LocalDateTime localDateTime = LocalDateTime.now();
        String today = localDateTime.toString();
        today = today.substring(0, 10).replace("-", "");
        String yesterday = localDateTime.minusDays(1).toString();
        yesterday = yesterday.substring(0, 10).replace("-", "");

        List<String> weatherConditions = new ArrayList<>();

        BufferedReader br = null;
        try {
            String path = weatherPath + "\\currentEvents\\Weather.Predictions" + today + ".events";
            File file = new File(path);
            if (file.exists()){
                br = new BufferedReader(new FileReader(weatherPath + "\\currentEvents\\Weather.Predictions" + today + ".events"));
            } else {
                br = new BufferedReader(new FileReader(weatherPath + "\\currentEvents\\Weather.Predictions" + yesterday + ".events"));
            }

            String line = br.readLine();
            while (line != null) {
                if (line.contains(island)){
                    weatherConditions.add(line);
                }
                line = br.readLine();
            }

        } catch (IOException e){
            System.out.println(e.getMessage());
        }

        return weatherConditions;
    }


    private List<String> showIslandHotelRates(String island, String ratesPath) {

        LocalDateTime localDateTime = LocalDateTime.now();
        String today = localDateTime.toString();
        today = today.substring(0, 10).replace("-", "");
        String yesterday = localDateTime.minusDays(1).toString();
        yesterday = yesterday.substring(0, 10).replace("-", "");

        List<String> hotelRates = new ArrayList<>();

        BufferedReader br = null;
        try {
            String path = weatherPath + "\\currentEvents\\Hotel.Rates" + today + ".events";
            File file = new File(path);
            if (file.exists()){
                br = new BufferedReader(new FileReader(weatherPath + "\\currentEvents\\Hotel.Rates" + today + ".events"));
            } else {
                br = new BufferedReader(new FileReader(weatherPath + "\\currentEvents\\Hotel.Rates" + yesterday + ".events"));
            }

            String line = br.readLine();
            while (line != null) {
                if (line.contains(island)){
                    hotelRates.add(line);
                }
                line = br.readLine();
            }

        } catch (IOException e){
            System.out.println(e.getMessage());
        }

        return hotelRates;
    }




}
