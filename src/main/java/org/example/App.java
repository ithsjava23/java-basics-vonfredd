package org.example;

import java.util.Locale;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Locale swedishLocale = new Locale("sv", "SE");
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
            int choise;
            inputFromUser = sc.nextLine();

            if(inputFromUser.equals("e")|| inputFromUser.equals("E")){
                break;
            }
                   try {
                       choise = Integer.parseInt(inputFromUser);
                   }catch (Exception e){
                       System.out.println("Fel val!");
                       continue;
                   }
                switch (choise) {
                    case 1 -> priceInput(priceOfElectricity, copyOfPriceArray, sc);
                    case 2 -> minMaxMedel(priceOfElectricity, hourArray);
                    case 3 -> sortedPrice(copyOfPriceArray, copyOfHourArray);
                    case 4 -> bestTimeToLoad(priceOfElectricity, hourArray);
                    case 5 -> diagram(priceOfElectricity, hourArray);
                }
            }
        }

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
        double sum = 0;
        int maxValue = 0;
        String hour = "";

        for (int i = 0; i < arr.length; i++){
            sum += arr[i];
        }
        double doubleSum = (sum / 24);

        for (int i = 0; i < arr.length; i++){
            if (maxValue < arr[i]) {
                maxValue = arr[i];
                hour = arrayHours[i];
            }
        }

        int minValue = Integer.MAX_VALUE;
        String hourHere = "";

        for (int i = 0; i < arr.length; i++){
            if (minValue > arr[i]) {
                minValue = arr[i];
                hourHere = arrayHours[i];
            }
        }
        String response = """
                Lägsta pris: %s, %d öre/kWh
                Högsta pris: %s, %d öre/kWh
                Medelpris: %.2f öre/kWh
                """;


String formatted = String.format(response,hourHere,minValue,hour,maxValue,doubleSum);
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
    public static void bestTimeToLoad(int[] arrPrice, String[] arrHours){
        double sum = Double.MAX_VALUE;

        String firstHour = "";
        double price;

        for (int i = 0; i < arrPrice.length-4; i++) {
            if (sum > (arrPrice[i] + arrPrice[i+1] + arrPrice[i+2] +arrPrice[i+3] )){
                sum = (arrPrice[i] + arrPrice[i+1] + arrPrice[i+2] +arrPrice[i+3] );

                firstHour = arrHours[i].substring(0,2);
            }
        }

        price = sum / 4;
        
        String response = """
                Påbörja laddning klockan %s
                Medelpris 4h: %.1f öre/kWh
                """;
        String formattedString = String.format(response,firstHour,price);
        System.out.print("\n" + formattedString);
    }

    public static void diagram(int[] arr, String[] arrayHours) {

        int maxValue = 0;
        int valueUnderMax;
        int minValue = Integer.MAX_VALUE;
        int mediumValue;
        int valueOverMin;
        int rowValue;
        int valueOverMedium;

        String one = "";
        String two = "";
        String three = "";
        String four = "";
        String five = "";
        String six = "";
        String seven = "";
        String eight = "";


        for (int i = 0; i < arr.length; i++) {
            if (minValue > arr[i]) {
                minValue = arr[i];
            }
            if (maxValue < arr[i]) {
                maxValue = arr[i];
            }
        }

        rowValue = (maxValue - minValue) /5;

        valueOverMin = minValue + rowValue;
        mediumValue = (valueOverMin + rowValue);
        valueOverMedium = (mediumValue + rowValue);
        valueUnderMax = valueOverMedium + rowValue;

        for (int i = 0; i < 8; i++) {
            if(i == 0){
                 one += String.format("%3d|%2s",maxValue,"");
            }
            else if(i == 5)
                six += String.format("%3d|%2s",minValue,"");
            else if(i == 6) {
                seven = String.format("%3s|------------------------------------------------------------------------","");
            }
            else if (i == 7)
                break;
            else
                switch (i){
                case 1 -> two += String.format("%3s|%2s","","");
                case 2 -> three += String.format("%3s|%2s","","");
                case 3 -> four += String.format("%3s|%2s","","");
                case 4 -> five += String.format("%3s|%2s","","");
                }

            for (int j = 0; j < arr.length; j++) {
                if (i == 0 && arr[j] == maxValue) {
                        one += String.format("x%2s","");
                } else if (i == 1 && arr[j] >= valueUnderMax) {
                    two += String.format("x%2s","");
                }else if (i == 2 && arr[j] >= valueOverMedium) {
                    three += String.format("x%2s","");
                }else if (i == 3 && arr[j] >= mediumValue) {
                        four += String.format("x%2s","");
                }else if (i == 4 && arr[j] >= valueOverMin) {
                        five += String.format("x%2s","");
                }else if (i == 5 && arr[j] >= minValue) {
                        six += String.format("x%2s","");
                }else
                    switch(i){
                    case 0 -> one += String.format(" %2s","");
                    case 1 -> two += String.format(" %2s","");
                    case 2 -> three += String.format(" %2s","");
                    case 3 -> four += String.format(" %2s","");
                    case 4 -> five += String.format(" %2s","");
                    case 5 -> six += String.format(" %2s","");
                    }

            }
            if(i == 6){
                for (int j = 0; j < arr.length; j++) {
                    if (j == 0) {
                        eight += String.format("%3s|%1s00%1s","","","");
                    }else if(j == arr.length-1){
                        eight += arrayHours[j].substring(0, 2);
                    }
                    else {
                        eight += arrayHours[j].substring(0, 2) + " ";
                    }
                }
            }
        }

        one = one.substring(0,one.length()-2);
        two = two.substring(0,two.length()-2);
        three = three.substring(0,three.length()-2);
        four = four.substring(0,four.length()-2);
        five = five.substring(0,five.length()-2);
        six = six.substring(0,six.length()-2);

        String svar = """
                %s
                %s
                %s
                %s
                %s
                %s
                %s
                %s
                """;

        String formatted = String.format(svar,one,two,three,four,five,six,seven,eight);
        System.out.println("FORMAT : \n" +formatted);
    }
}

