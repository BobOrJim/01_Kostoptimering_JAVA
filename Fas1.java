package Xjobb;

//Söker efter en baslösning
public class Fas1 {
    double[][] mat;
    int[] basLosning;
    boolean hittatBadLosning;
    void inputFas1(double[][] mat){
        this.mat = mat;
        System.out.println("FAS1 KÖRS");
//Baslösningens storlek är lika med antal ekv i ekvsys/matris.
//Blanda ej ihop med optimerings punktens kordinater        
        basLosning = new int [mat.length-1];
        sokBasLosning();
    }


    void sokBasLosning(){
        System.out.println("input matrix looks like this:");
        printMat(mat);
//RÄKNAR ETTOR OCH NOLLOR I VARJE KOLUMN FÖR ATT HITTA BASRADER, ALLA HITTADE BASRADER SPARAS
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
                System.out.println("Vi har hittat en basrad, den är: " +basLosning[hittadBasRad]);
            }
        }
//RÄKNAR ANTALET HITTADE BASRADER, VID FULT ANTAL, DVS OM VI HAR EN BASLÖSNING SÅ ÄR VI FÄRDIGA
        int tmp = 0; //räknar antalet basrader
        for(int i=0; i<basLosning.length; i++){
            if(basLosning[i] != 0){tmp++;}
        }
        if(tmp == basLosning.length){
            System.out.println("VI HAR EN BASLÖSNING MED UTSEENDE: ");
            printArray(basLosning);
            System.out.println("FAS1 IS NOW DONE, WE FOUND A BASLÖSNING");
            hittatBadLosning = true;
            return;
        }
        System.out.println("FAS1 IS NOW DONE, WE DID NOT FIND A BASLÖSNING");        
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
