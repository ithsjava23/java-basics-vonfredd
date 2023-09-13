package org.example;

import java.util.Locale;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Locale swedishLocale = Locale.of("sv", "SE");
        Locale.setDefault(swedishLocale);
        Scanner sc = new Scanner(System.in);
        String inputFromUser;
        int[] priceOfElectricity = new int[24];
        String[] hourArray = new String[]{
                "00-01", "01-02", "02-03", "03-04", "04-05", "05-06",
                "06-07", "07-08", "08-09", "09-10", "10-11", "11-12",
                "12-13", "13-14", "14-15", "15-16", "16-17", "17-18",
                "18-19", "19-20", "20-21", "21-22", "22-23", "23-24"
        };

        int[] copyOfPriceArray = new int[priceOfElectricity.length];
        String[] copyOfHourArray = new String[]{
                "00-01", "01-02", "02-03", "03-04", "04-05", "05-06",
                "06-07", "07-08", "08-09", "09-10", "10-11", "11-12",
                "12-13", "13-14", "14-15", "15-16", "16-17", "17-18",
                "18-19", "19-20", "20-21", "21-22", "22-23", "23-24"
        };

        while (true) {
            menu();
            int choice;
            inputFromUser = sc.nextLine();

            if(inputFromUser.equals("e")|| inputFromUser.equals("E")){
                break;
            }
                   try {
                       choice = Integer.parseInt(inputFromUser);
                   }catch (Exception e){
                       System.out.println("Fel val!");
                       continue;
                   }
                switch (choice) {
                    case 1 -> priceInput(priceOfElectricity, copyOfPriceArray, sc);
                    case 2 -> minMaxMedel(priceOfElectricity, hourArray);
                    case 3 -> sortedPrice(copyOfPriceArray, copyOfHourArray);
                    case 4 -> bestTimeToLoad(priceOfElectricity, hourArray);
                    case 5 -> diagram(priceOfElectricity, hourArray);
                }
            }
        }
         /*
            Takes input from user to fill an array with the price of each hour of the day.
            Array is copied to a copy array made for sorting so that we do not affect the original array
         */
    public static void priceInput(int[] arr, int[] copyArr, Scanner sc){
        System.out.print("Lägg till priser för varje timme:\n");
        int number;
        for (int i = 0; i < arr.length; i++) {
            if (i <= 9){
                System.out.println("Lägg till priset för timma 0" + i);
            }else
                System.out.println("Lägg till priset för timma " + i);
            number = Integer.parseInt(sc.nextLine());
            arr[i] = number;
        }
        System.arraycopy(arr, 0, copyArr, 0, arr.length);
    }
    public static void minMaxMedel(int[]arr, String[] arrayHours){
        String response = """
                %s
                %s
                %s
                """;
        String formatted = String.format(response,min(arr,arrayHours),max(arr,arrayHours),mediumValue(arr));
        System.out.print(formatted);
}
public static void menu(){
String menuvg = """
                Elpriser
                ========
                1. Inmatning
                2. Min, Max och Medel
                3. Sortera
                4. Bästa Laddningstid (4h)
                5. Visualisering
                e. Avsluta
                """;
        System.out.print(menuvg);
    }
    public static void sortedPrice(int[] arrPrice, String[] arrHour){
        int temporaryPrice;
        String tempString;
        for (int i = 0; i < arrPrice.length-1; i++) {
            for (int j = 0; j < arrPrice.length - i -1; j++) {
                {
                    if(arrPrice[j] < arrPrice[j+1]){
                        temporaryPrice = arrPrice[j];
                        arrPrice[j] = arrPrice[j+1];
                        arrPrice[j+1] = temporaryPrice;

                        tempString = arrHour[j];
                        arrHour[j] = arrHour[j+1];
                        arrHour[j+1] = tempString;
                    }
                }
            }
        }
        for (int i = 0; i < arrPrice.length; i++) {
            System.out.print(arrHour[i] + " " + arrPrice[i] + " öre\n");
        }
    }
     /*
        This method shows the best price of 4 hours after each other
     */
    public static void bestTimeToLoad(int[] arrPrice, String[] arrHours){
        double sum = Double.MAX_VALUE;
        String firstHour = "";
        double price;
         /*
            Loops through the price array with -4 in length so that we don't go out of bounds.
            If the sum of all 4 following numbers combined are smaller than the value of sum, the value sum
            will be replaced and the first hour of the first element in that streak is saved.
         */
        for (int i = 0; i < arrPrice.length-4; i++) {
            if (sum > (arrPrice[i] + arrPrice[i+1] + arrPrice[i+2] +arrPrice[i+3] )){
                sum = (arrPrice[i] + arrPrice[i+1] + arrPrice[i+2] +arrPrice[i+3] );

                firstHour = arrHours[i].substring(0,2);
            }
        }
        price = sum / 4; //Calculates the mean value of the 4 hours
        String response = """
                Påbörja laddning klockan %s
                Medelpris 4h: %.1f öre/kWh
                """;
        String formattedString = String.format(response,firstHour,price);
        System.out.print("\n" + formattedString);
    }
     /*
        This method returns a visual diagram on 6 levels which shows
        the volatility of the price through the day
     */
    public static void diagram(int[] arr, String[] arrayHours) {
        int maxValue = 0; // Maxvalue as a roof
        int minValue = Integer.MAX_VALUE; // Minvalue as a floor
        double valueUnderMax; //2nd level
        double mediumValue; //4th level
        double valueOverMin; //5th level
        double valueOverMedium; //3rd level
        double rowValue; //The amount to raise every level with
         /*
          Declares the largest and smallest number
         */
        for (int i = 0; i < arr.length; i++) {
            if (minValue > arr[i]) {
                minValue = arr[i];
            }
            if (maxValue < arr[i]) {
                maxValue = arr[i];
            }
        }
        rowValue = (maxValue - minValue) / 5f; // Calculates what value to be added to each level
        valueOverMin =  (minValue + rowValue);
        int overMin = (int)valueOverMin;
        mediumValue =  (valueOverMin + rowValue);
        int medium = (int)mediumValue;
        valueOverMedium = (mediumValue + rowValue);
        int overMed = (int)valueOverMedium;
        valueUnderMax =  (int)(valueOverMedium + rowValue);
        int underMax = (int)valueUnderMax;

        double[] levelNums = {maxValue,underMax,overMed,medium,overMin,minValue};
        StringBuilder endingLines = new StringBuilder();
        StringBuilder hours = new StringBuilder();

        for (int i = 0; i < levelNums.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                if(i == 0 && j == 0)
                    System.out.printf("%3d|",maxValue);
                if(i == 5 && j == 0)
                    System.out.printf("%3d|",minValue);
                if(j == 0 && i != 0 && i != 5)
                    System.out.printf("%3s|","");
                if(arr[j] >= levelNums[i])
                    System.out.printf("%2sx", "");
                else
                    System.out.printf("%3s", "");
                if (i == 0 ) {
                    if (j == 0) {
                        endingLines.append(String.format("%3s|", ""));
                        hours.append(String.format("%3s|", ""));
                    }
                    endingLines.append("---");
                    hours.append(String.format("%1s"+(arrayHours[j].substring(0,2)),""));
                }
            }
            System.out.print("\n");
        }
        System.out.print(endingLines+"\n"+hours+"\n");
    }
    public static String max(int[] arr, String[] arrayHours){
        int maxValue = 0;
        String hour = "";
        for (int i = 0; i < arr.length; i++){
            if (maxValue < arr[i]) {
                maxValue = arr[i];
                hour = arrayHours[i];
            }
        }
        return String.format("Högsta pris: %s, %d öre/kWh",hour,maxValue);
    }
     /*
        This method calculates the lowest price of the price array,
        then returns a formatted string to its caller together with the hour
        which the price occurs at.
     */
    public static String min(int[] arr, String[] arrayHours){
        int minValue = Integer.MAX_VALUE;
        String hour = "";
        for (int i = 0; i < arr.length; i++){
            if (minValue > arr[i]) {
                minValue = arr[i];
                hour = arrayHours[i];
            }
        }
        return String.format("Lägsta pris: %s, %d öre/kWh",hour,minValue);
    }
     /*
        This method calculates the medium price of the price array,
        then returns a formatted string to its caller
     */
    public static String mediumValue(int[] arr){
        double sum = 0;
        for (int j : arr) {
            sum += j;
        }
        double mediumSum = (sum / 24);
        return String.format("Medelpris: %.2f öre/kWh",mediumSum);
    }
}