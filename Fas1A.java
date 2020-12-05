package Xjobb;

//Skapar en basl�sning med artificiella variabler
public class Fas1A {
    double[][] mat;
    int[] basLosning;
    void inputFas1a(double[][] mat, int[] basLosning){
        this.mat = mat;
        this.basLosning = basLosning;
        System.out.println("FAS1A K�RS");
        //Basl�sningens storlek �r lika med antalet ekv i ekvsys/matris.
        //Blanda ej ihop med optimerings punktens kordinater        
        basLosning = new int [mat.length-1];
        insertAVar();
    }

    void insertAVar(){
        System.out.println("input matrix looks like this:");
        printMat(mat);
//SKAPAR ST�RRE MATRIS MED PLATS F�R ARTIFICIELLA VARIABLER
        int increase=0; //r�knar storleken som matrisen skall �kas
        double[][] matTmp; //tempor�r lagring av matris
        for(int i=0; i<basLosning.length; i++){ //f�r varje basrad som saknas m�ste vi inf�ra en A variabel
            if(basLosning[i] == 0){increase++;}
        }
        matTmp = mat;
        mat = new double[matTmp.length][matTmp[0].length + increase];
        for(int i=0; i<matTmp[0].length - 1; i++){//flyttar �ver v�nster sektionen av data till mat igen
            for(int j=0; j<matTmp.length; j++){
                mat[j][i] = matTmp[j][i];
            }
        }
        for(int i=0; i<mat.length; i++){//flyttar �ver h�ger kolumnen av data till mat igen
            mat[i][mat[0].length-1] = matTmp[i][matTmp[0].length-1];
        }
//STOPPAR IN ARTIFICIELLA VARIABLER & SKAPAR EN BASL�SNING D�R A VARIABLER ING�R
        int[] tilldeladAVar; //h�ller reda p� vilka ekv som f�tt en A variabel till sig
        tilldeladAVar = new int [increase];
        int tmp=0; //r�knar fram ny pos till AvarPos
        for(int i=0; i<basLosning.length; i++){
            if(basLosning[i] == 0){
                mat[i+1][mat[0].length -1 -increase] = 1;
                basLosning[i] = mat[0].length -increase;
                System.out.println("Basrad " +i+" i basl�sningen har v�rde " +(mat[0].length -increase));
                tilldeladAVar[tmp++] = i; //anv�nds till memorera raderna som kommer tillh�ra W. t�nkt W=a1+a2...
                increase--;
            }
        }
        //printMat(mat);
        System.out.print("VI HAR EN BASL�SNING MED UTSEENDE: ");
        printArray(basLosning);
//BER�KNAR W FUNKTIONEN SOM NY MINIMERINGS FUNKTION
        System.out.println("Antalet tilllagda A variablr �r:" +tilldeladAVar.length);
        double[] W; //lagrar omega som �r ny funktion att minimeras
        W = new double [mat[0].length];
//        printArray(tilldeladAVar);
        for(int i=0; i<(W.length - tilldeladAVar.length -1); i++){ //l�ngden av W - antalet A variabler -1
            for(int j=0; j<tilldeladAVar.length; j++){//antalet A dvs antalet rader som skall adderas
                W[i] = W[i] + mat[tilldeladAVar[j]+1][i];
            }
        }
        for(int j=0; j<tilldeladAVar.length; j++){//fixar slut summan till h�ger om "likhetsteknet"
            W[W.length-1] = W[W.length-1] + mat[tilldeladAVar[j]+1][W.length-1];
        }
        System.out.println("W har utseende: ");
        printArray(W);
//BYTER PLATS P� W OCH �VERSTA RADEN I MAT
        double tmp2;
        for(int i=0; i<W.length; i++){
            tmp2 = W[i];
            W[i] = mat[0][i];
            mat[0][i] = tmp2;
        }
        //System.out.println("efter byte");
        //printMat(mat);
        //printArray(W);
//SKAPAR ETT FAS2 OBJEKT OCH SKICKAR IN MIN NYA MATRIS D�R TILLSAMMANS MED MINA A BASL�SNINGAR
        System.out.println("Anropar Fas2 och skickar med mat och basLosning");
        Fas2A fas2a = new Fas2A();
        fas2a.inputFas2a(mat, basLosning);
        mat = fas2a.getMat();
        basLosning = fas2a.getOptimalBasLosning(); //OBS denna basl�sning har formen av en kordinat
        System.out.println("Jag har mottagit f�ljande fr�n fas2a");
        printMat(mat);
        System.out.println("Denna basl�sning �r i formen av en kordinat");
        printArray(basLosning);
//STOPPAR TILLBACKS W DVS "GAMMLA" ORGINAL FUNKTIONEN SOM SKALL MINIMERAS
        for(int i=0; i<W.length; i++){
            if(W[i] != 0){ //detta f�r att undvika -0 vilket kan ge problem senare
                mat[0][i] = -W[i];
            }
        }
        //printMat(mat);
//KLIPPER BORT O�NSKADE RADER I MAT
        matTmp = mat; //tempor�r lagring av matris
        mat = new double[matTmp.length][matTmp[0].length- tilldeladAVar.length];
        //printMat(mat);
        for(int n=0; n<mat[0].length; n++){ //flyttar �ver v�nster sektionen av data till mat igen
            for(int m=0; m<mat.length; m++){
                mat[m][n] = matTmp[m][n];
            }
        }
        for(int n=0; n<mat.length; n++){ //flyttar �ver h�ger kolumnen av data till mat igen
            mat[n][mat[0].length-1] = matTmp[n][matTmp[0].length-1];
        }
        System.out.println("Efter att jag tagit bort on�diga rader samt flyttat in orginal m�lfunktion");
        printMat(mat);
//PIVOTERAR M�LFUNKTIONEN TILL R�TT FORM
        for(int i=0; i<mat[0].length; i++){ //s�ker efter positionen hos basLosning som �r != 0
            double konstant = 0;
            if(basLosning[i] != 0){ //vi har hittat denna position
                konstant = mat[0][i]; //l�ser in v�rdet som skall reduceras bort
                for(int j=0; j<mat.length; j++){ //s�ker efter raden som skall anv�ndas vid reduktionen
                    if(mat[j][i] == 1){ //vi har hittat raden
                        for(int k=0; k<mat[0].length; k++){ //reducear bort raden kolumn f�r kolumn
                            mat[0][k] = mat[0][k] - konstant * mat[j][k];
                        }
                    }
                }
            }
        }
        System.out.println("Efter pivotering av m�lfunktion till r�tt form");
        printMat(mat);
//KLIPPER BORT O�NSKADE RADER I BASL�SNING
        int[] basLosningTmp = new int [basLosning.length];//tempor�r lagring av basLosning
        basLosningTmp = basLosning;
        basLosning = new int [basLosning.length - tilldeladAVar.length-1];
        for(int i=0; i<basLosning.length; i++){ //flyttar �ver basl�sningen
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
