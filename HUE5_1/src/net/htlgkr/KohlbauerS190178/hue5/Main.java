package net.htlgkr.KohlbauerS190178.hue5;

public class Main {

   static int AMOUNT= 80000;

    public static void main(String[] args) {

        Main main = new Main();
        main.beispiel1();
    }

    public void beispiel1(){

        System.out.println("---Beispiel 1---");

        double[] arr = new double[AMOUNT];

        for (int i = 0; i < AMOUNT; i++) {
            arr[i] = i;
        }

        double sum = ReciprocalArraySum.parManyTaskArraySum(arr, 2);
        System.out.println("sum: " + sum);




        /*double test =0;
        for (int i = 0; i < AMOUNT; i++) {

            if(i!=0)
            test+= (double)1/(double)i;
        }
        System.out.println(test);*/
    }


}
