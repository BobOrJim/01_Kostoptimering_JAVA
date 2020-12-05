package Xjobb;

//Skapar en baslösning med artificiella variabler
public class Fas1A {
    double[][] mat;
    int[] basLosning;
    void inputFas1a(double[][] mat, int[] basLosning){
        this.mat = mat;
        this.basLosning = basLosning;
        System.out.println("FAS1A KÖRS");
        //Baslösningens storlek är lika med antalet ekv i ekvsys/matris.
        //Blanda ej ihop med optimerings punktens kordinater        
        basLosning = new int [mat.length-1];
        insertAVar();
    }

    void insertAVar(){
        System.out.println("input matrix looks like this:");
        printMat(mat);
//SKAPAR STÖRRE MATRIS MED PLATS FÖR ARTIFICIELLA VARIABLER
        int increase=0; //räknar storleken som matrisen skall ökas
        double[][] matTmp; //temporär lagring av matris
        for(int i=0; i<basLosning.length; i++){ //för varje basrad som saknas måste vi införa en A variabel
            if(basLosning[i] == 0){increase++;}
        }
        matTmp = mat;
        mat = new double[matTmp.length][matTmp[0].length + increase];
        for(int i=0; i<matTmp[0].length - 1; i++){//flyttar över vänster sektionen av data till mat igen
            for(int j=0; j<matTmp.length; j++){
                mat[j][i] = matTmp[j][i];
            }
        }
        for(int i=0; i<mat.length; i++){//flyttar över höger kolumnen av data till mat igen
            mat[i][mat[0].length-1] = matTmp[i][matTmp[0].length-1];
        }
//STOPPAR IN ARTIFICIELLA VARIABLER & SKAPAR EN BASLÖSNING DÄR A VARIABLER INGÅR
        int[] tilldeladAVar; //håller reda på vilka ekv som fått en A variabel till sig
        tilldeladAVar = new int [increase];
        int tmp=0; //räknar fram ny pos till AvarPos
        for(int i=0; i<basLosning.length; i++){
            if(basLosning[i] == 0){
                mat[i+1][mat[0].length -1 -increase] = 1;
                basLosning[i] = mat[0].length -increase;
                System.out.println("Basrad " +i+" i baslösningen har värde " +(mat[0].length -increase));
                tilldeladAVar[tmp++] = i; //används till memorera raderna som kommer tillhöra W. tänkt W=a1+a2...
                increase--;
            }
        }
        //printMat(mat);
        System.out.print("VI HAR EN BASLÖSNING MED UTSEENDE: ");
        printArray(basLosning);
//BERÄKNAR W FUNKTIONEN SOM NY MINIMERINGS FUNKTION
        System.out.println("Antalet tilllagda A variablr är:" +tilldeladAVar.length);
        double[] W; //lagrar omega som är ny funktion att minimeras
        W = new double [mat[0].length];
//        printArray(tilldeladAVar);
        for(int i=0; i<(W.length - tilldeladAVar.length -1); i++){ //längden av W - antalet A variabler -1
            for(int j=0; j<tilldeladAVar.length; j++){//antalet A dvs antalet rader som skall adderas
                W[i] = W[i] + mat[tilldeladAVar[j]+1][i];
            }
        }
        for(int j=0; j<tilldeladAVar.length; j++){//fixar slut summan till höger om "likhetsteknet"
            W[W.length-1] = W[W.length-1] + mat[tilldeladAVar[j]+1][W.length-1];
        }
        System.out.println("W har utseende: ");
        printArray(W);
//BYTER PLATS PÅ W OCH ÖVERSTA RADEN I MAT
        double tmp2;
        for(int i=0; i<W.length; i++){
            tmp2 = W[i];
            W[i] = mat[0][i];
            mat[0][i] = tmp2;
        }
        //System.out.println("efter byte");
        //printMat(mat);
        //printArray(W);
//SKAPAR ETT FAS2 OBJEKT OCH SKICKAR IN MIN NYA MATRIS DÄR TILLSAMMANS MED MINA A BASLÖSNINGAR
        System.out.println("Anropar Fas2 och skickar med mat och basLosning");
        Fas2A fas2a = new Fas2A();
        fas2a.inputFas2a(mat, basLosning);
        mat = fas2a.getMat();
        basLosning = fas2a.getOptimalBasLosning(); //OBS denna baslösning har formen av en kordinat
        System.out.println("Jag har mottagit följande från fas2a");
        printMat(mat);
        System.out.println("Denna baslösning är i formen av en kordinat");
        printArray(basLosning);
//STOPPAR TILLBACKS W DVS "GAMMLA" ORGINAL FUNKTIONEN SOM SKALL MINIMERAS
        for(int i=0; i<W.length; i++){
            if(W[i] != 0){ //detta för att undvika -0 vilket kan ge problem senare
                mat[0][i] = -W[i];
            }
        }
        //printMat(mat);
//KLIPPER BORT OÖNSKADE RADER I MAT
        matTmp = mat; //temporär lagring av matris
        mat = new double[matTmp.length][matTmp[0].length- tilldeladAVar.length];
        //printMat(mat);
        for(int n=0; n<mat[0].length; n++){ //flyttar över vänster sektionen av data till mat igen
            for(int m=0; m<mat.length; m++){
                mat[m][n] = matTmp[m][n];
            }
        }
        for(int n=0; n<mat.length; n++){ //flyttar över höger kolumnen av data till mat igen
            mat[n][mat[0].length-1] = matTmp[n][matTmp[0].length-1];
        }
        System.out.println("Efter att jag tagit bort onödiga rader samt flyttat in orginal målfunktion");
        printMat(mat);
//PIVOTERAR MÅLFUNKTIONEN TILL RÄTT FORM
        for(int i=0; i<mat[0].length; i++){ //söker efter positionen hos basLosning som är != 0
            double konstant = 0;
            if(basLosning[i] != 0){ //vi har hittat denna position
                konstant = mat[0][i]; //läser in värdet som skall reduceras bort
                for(int j=0; j<mat.length; j++){ //söker efter raden som skall användas vid reduktionen
                    if(mat[j][i] == 1){ //vi har hittat raden
                        for(int k=0; k<mat[0].length; k++){ //reducear bort raden kolumn för kolumn
                            mat[0][k] = mat[0][k] - konstant * mat[j][k];
                        }
                    }
                }
            }
        }
        System.out.println("Efter pivotering av målfunktion till rätt form");
        printMat(mat);
//KLIPPER BORT OÖNSKADE RADER I BASLÖSNING
        int[] basLosningTmp = new int [basLosning.length];//temporär lagring av basLosning
        basLosningTmp = basLosning;
        basLosning = new int [basLosning.length - tilldeladAVar.length-1];
        for(int i=0; i<basLosning.length; i++){ //flyttar över baslösningen
            basLosning[i] = basLosningTmp[i];
        }
//SKRIVER UT SVAR
        printArray(basLosning);           
        System.out.println("FAS1A IS NOW DONE!!");
        return;
    }

    
    
    
    
    
    
    
    
    
    
    void printArray(int[] tmpArray){ //SKRIVER INT UT VEKTOR
        System.out.println();
        for(int n=0; n<tmpArray.length; n++){
            System.out.print(tmpArray[n] +" ");
        }  
        System.out.println();
    }    
    
    void printArray(double[] tmpArray){ //SKRIVER FLOAT UT VEKTOR
        System.out.println();
        for(int n=0; n<tmpArray.length; n++){
            System.out.print(tmpArray[n] +" ");
        }  
        System.out.println();
    }
    
    void printMat(double[][] matTmp3){ //SKRIVER UT MATRIS
        System.out.println();
        for(int q=0; q<matTmp3.length; q++){
            for(int r=0; r<matTmp3[0].length; r++){
                System.out.print(" " + matTmp3[q][r]);
            }
            System.out.println();
        }
    }//avslutar metod       
    
    double[][] getMat(){return mat;}
    int[] getBasLosning(){return basLosning;}
}
