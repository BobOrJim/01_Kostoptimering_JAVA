package Xjobb;

//Denna klass tar emot matrisen med kost ekvationssystemet och lägger till
//slacker variabler tills det att endast likhetstecken finns.
//dvs skriver LP problemet på standardform
public class PreFas{ 
    private double[][] mat, matTmp2;
    int[] tkn;
 
    void inputPreFas(double[][] mat, int[] tkn){
        this.mat = mat;
        this.tkn = tkn;
        System.out.println("PREFAS KÖRS");
        System.out.println("input matrix looks like this:");
        printMat(mat);
        initNewMatrix();
        fillNewMatrix();
        System.out.println("PreFas IS NOW DONE!!");
    }


//SKAPAR NY MATRIS MED PLATS FÖR SLACKVARIABLER OCH STOPPAR IN GAMMAL MATRIS
    void initNewMatrix(){
        int increase=0;
        for(int n=0; n<tkn.length; n++){
            if(tkn[n] == 1){increase++;} //ökar mat till slackvariabel pga <= tecken 
            if(tkn[n] == 2){}
            if(tkn[n] == 3){increase++;} //ökar mat till slackvariabel pga >= tecken
        }
        matTmp2 = mat;
        mat = new double[matTmp2.length][matTmp2[0].length + increase];
//flyttar över vänster sektionen av data till mat igen
        for(int n=0; n<matTmp2[0].length - 1; n++){
            for(int m=0; m<matTmp2.length; m++){
                mat[m][n] = matTmp2[m][n];
            }
        }
//flyttar över höger kolumnen av data till mat igen
        for(int n=0; n<mat.length; n++){ 
            //System.out.println(mat.length);
            //System.out.println(matTmp2[0].length-1);
            mat[n][mat[0].length-1] = matTmp2[n][matTmp2[0].length-1];
        }
    }

//FYLLER MATRISEN MED SLACKVARIABLER    
    void fillNewMatrix(){ 
        int offset=0;
        for(int n=0; n<tkn.length; n++){
            if(tkn[n] == 1){ //adderar en slack variabel
                //System.out.println(matTmp2[0].length);
                mat[n+1][matTmp2[0].length+offset-1] = 1;
                offset++;
            }
            if(tkn[n] == 2){} //gör inget
            if(tkn[n] == 3){ //subtrahera en slack variabel
                //System.out.println(matTmp2[0].length);
                mat[n+1][matTmp2[0].length+offset-1] = -1;
                offset++;
            }
        }
    }
    
    double[][] getMat(){return mat;}
    
    void printMat(double[][] matTmp3){ //SKRIVER UT MATRIS
        System.out.println();
        for(int q=0; q<matTmp3.length; q++){
            for(int r=0; r<matTmp3[0].length; r++){
                System.out.print(" " + matTmp3[q][r]);
            }
            System.out.println();
        }
    }
}
