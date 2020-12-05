package Xjobb;

//S�ker efter en basl�sning
public class Fas1 {
    double[][] mat;
    int[] basLosning;
    boolean hittatBadLosning;
    void inputFas1(double[][] mat){
        this.mat = mat;
        System.out.println("FAS1 K�RS");
//Basl�sningens storlek �r lika med antal ekv i ekvsys/matris.
//Blanda ej ihop med optimerings punktens kordinater        
        basLosning = new int [mat.length-1];
        sokBasLosning();
    }


    void sokBasLosning(){
        System.out.println("input matrix looks like this:");
        printMat(mat);
//R�KNAR ETTOR OCH NOLLOR I VARJE KOLUMN F�R ATT HITTA BASRADER, ALLA HITTADE BASRADER SPARAS
        for(int i=0; i<mat[0].length-1; i++){
            int hittadBasRad = 0;
            int ettor = 0, nollor = 0;
            for(int j=1; j<mat.length; j++){
                if(mat[j][i] == 1){
                    ettor++;
                    hittadBasRad = j-1;
                }
            }
            for(int j=1; j<mat.length; j++){
                if(mat[j][i] == 0){
                    nollor++;
                }
            }
            //System.out.println("matris antal rader = " +(mat.length-1));
            //System.out.println("ettor = " +ettor);
            //System.out.println("nollor = " +nollor);
            if(ettor == 1 && mat.length -1 - nollor == 1 && basLosning[hittadBasRad] == 0){
                basLosning[hittadBasRad] = i+1;
                System.out.println("Vi har hittat en basrad, den �r: " +basLosning[hittadBasRad]);
            }
        }
//R�KNAR ANTALET HITTADE BASRADER, VID FULT ANTAL, DVS OM VI HAR EN BASL�SNING S� �R VI F�RDIGA
        int tmp = 0; //r�knar antalet basrader
        for(int i=0; i<basLosning.length; i++){
            if(basLosning[i] != 0){tmp++;}
        }
        if(tmp == basLosning.length){
            System.out.println("VI HAR EN BASL�SNING MED UTSEENDE: ");
            printArray(basLosning);
            System.out.println("FAS1 IS NOW DONE, WE FOUND A BASL�SNING");
            hittatBadLosning = true;
            return;
        }
        System.out.println("FAS1 IS NOW DONE, WE DID NOT FIND A BASL�SNING");        
        hittatBadLosning = false;
        return;
    }

    
    void printArray(int[] tmpArray){ //SKRIVER INT UT VEKTOR
        System.out.println();
        for(int n=0; n<tmpArray.length; n++){
            System.out.print(tmpArray[n] +" ");
        }  
        System.out.println();
    }    
    
    void printArray(float[] tmpArray){ //SKRIVER FLOAT UT VEKTOR
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
    }

    boolean hittatBadLosning(){return hittatBadLosning;}
    double[][] getMat(){return mat;}
    int[] getBasLosning(){return basLosning;}
}
