package Xjobb;

//Hittar en optimall�sning, anv�nds n�r inte artificiella variabler anv�nts
public class Fas2 {
    int[] optimalBasLosning;
    double[][] mat;
    void inputFas2(double[][] mat, int[] baslosning){
        this.mat = mat;
        System.out.println("FAS2 K�RS");
        optimalBasLosning = new int [mat[0].length];
        printMat(mat);
//BER�KNAR INVARIABEL
        double in = mat[0][0]; //rad:kolumn
        int inVar = 0;
        for (int i=0; i<mat[0].length-1; i++){
            if (mat[0][i] > in){
                in = mat[0][i];
                inVar = i;
            }
        }
        System.out.print("invariabel blir d� kolumn " +(inVar+1)+ " med varde "+in);
        System.out.println(" dvs x" +(inVar+1));
//KOLLAR AVSLUTNINGS VILKOR & S�KNING SAMT LAGRING/UTSKRIFT AV OPTIMAL BASL�SNING
        if(in <= 0.001){
            boolean output = false;
            int raknare = 0;
            System.out.println("Funktionen �r nu maximerad och kordinaten �r d�:");
                for(int n=1; n<mat[0].length; n++){
                    for(int m=0; m<baslosning.length; m++){
                        if(baslosning[m] == n && output != true){
                            optimalBasLosning[raknare] = (int) mat[m+1][mat[0].length-1];
                            raknare++;
                            output = true;
                        }
                    }
                    if (output == false){
                        optimalBasLosning[raknare] = 0;
                        raknare++;
                    }
                    output = false;
                }
            printArray(optimalBasLosning);
            printMat(mat);
            System.out.println("Fas2 IS NOW DONE!!");
            return;
        }
//BER�KNAR UTVARIABEL
        double ut = 10000; //fuling l�sning
        int utVar = 0;
        for (int i=1; i<mat.length; i++){
            if (mat[i][inVar] > 0){
                if (mat[i][mat[0].length-1]/mat[i][inVar] < ut){
                    ut = mat[i][mat[0].length-1]/mat[i][inVar];
                    utVar = i;
                }
            }
        }
        System.out.print("utvariabel blir d� rad " +utVar+" med varde " +ut);
        System.out.println(" dvs x" +baslosning[utVar-1]);
//SKRIVER UT RADER ATT PIVOTERA FR�N OCH TILL
        System.out.print("Jag �nskar d�rf�r pivotera x" +(inVar+1)+ " kolumnen s� att denna ");
        System.out.println(" tar utseendet av x" + baslosning[utVar-1] + " kolumnen");
        double[] pivTo, pivFrom;
        pivTo = new double[mat.length]; //detta �r enhetsvektorn, dvs den jag skall pivotera min blandrad till
        pivFrom = new double[mat.length];
        for(int k=1; k<mat.length; k++){ //l�ser in raderna som skall anv�ndas vid pivotering
            pivTo[k] = mat[k][baslosning[utVar-1]-1];
            pivFrom[k] = mat[k][inVar];
        }
        baslosning[utVar-1] = inVar+1; //laddar om baslosning till n�sta g�ng itterationen ovan skall k�ras
        System.out.print("pivTo/utvariabeln har utseende: ");
        printArray(pivTo);
        System.out.print("pivFrom/invariabel har utseende ");
        printArray(pivFrom); //OBS ger fel vid utskrift, ett off by one eller f�ljer med, algoritmen verkar dock fungera
//SKAPAR EN 1:A SOM KONSTANT TILL RADEN SOM SKALL ANV�NDAS TILL PIVOTERINGEN
        double tmp2;
        int rattRad=0;
        for(int i=1; i<mat.length; i++){
            if(pivTo[i] == 1){ //hittar pivoterings raden
                rattRad = i;
                tmp2 = 1 / pivFrom[i]; //hittar multiplikator konstant
                for(int j=0; j<mat[0].length; j++){
                    mat[i][j] = tmp2 * mat[i][j];
                }
            }
        }
        System.out.println();
        System.out.println("Efter fixad konstant:");
        printMat(mat);
//UTF�R PIVOTERINGEN
        for(int n=1; n<mat.length; n++){
            if(mat[n][inVar] != 0 && n != rattRad){
                double multKonst = mat[n][inVar];
                for(int p=0; p<=mat[0].length-1; p++){
                    mat[n][p] = mat[n][p] - multKonst*mat[rattRad][p];
                }
            }
        }
        //System.out.println("Efter pivotering:");
//ANPASAR MINIMERINGS FUNKTIONEN
        for(int q=0; q<=mat[0].length-1; q++){
            mat[0][q] = mat[0][q] - in * mat[rattRad][q];
        }
        System.out.println();
        System.out.println("Efter fixad maximeringsfunktion:");
        printMat(mat);
        System.out.println("Avslutar en itteration");
        inputFas2(mat, baslosning);
    }//avslutar metod
    

    void printArray(int[] tmpArray){ //SKRIVER INT UT VEKTOR
        System.out.println();
        for(int n=0; n<tmpArray.length; n++){
            System.out.print(tmpArray[n] +" ");
        }  
        System.out.println();
    }     
    
    void printArray(double[] tmpArray){ //SKRIVER INT UT VEKTOR
        System.out.println();
        for(int n=0; n<tmpArray.length; n++){
            System.out.print(tmpArray[n] +" ");
        }  
        System.out.println();
    } 
    
    
    void printMat(double[][] mat){ //SKRIVER UT MATRIS
        System.out.println();
        for(int q=0; q<mat.length; q++){
            for(int r=0; r<mat[0].length; r++){
                System.out.print(" " + mat[q][r]);
            }
            System.out.println();
        }
    }
    
    double[][] getMat(){return mat;}
    
    int[] getOptimalBasLosning(){
        return optimalBasLosning;
    }
    
}
