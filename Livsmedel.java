package Xjobb;

//Objekt av denna klass varje livsmedel samt erbjuder get/set metoder.
public class Livsmedel {
    String livsM;
    double energiJ, energiK, vatten, retinol, karoten, retinolekv, Dvitamin, 
           aTokoferol, tiamin, askorbinsyra, riboflavin, niacin,
           niacinekv, vitaminB6, vitaminB12, kalcium, fosfor, järn, magnesium, natrium,
           kalium, zink, mättadeFettsyror, enkelomättadeFettsyror, fleromättadeFettsyror,
           kolesterol, monosackarider, disackarider, sackaros, fibrer, alkohol, fettsyraC4C10,
           fettsyraC120, fettsyraC140, fettsyraC160, fettsyraC180, fettsyraC200,
           fettsyraC161, fettsyraC181, fettsyraC182, fettsyraC183, fettsyraC204,
           fettsyraC205, fettsyraC225, fettsyraC226, folat, selen, avfall,
           protein, fett, kolhydrater;
    
    void setLivsM(String s){livsM = s;}
    String getLivsM(){return livsM;}

    void setAvfall(double d){avfall = d;}
    double getAvfall(){return avfall;}

    void setEnergiJ(double d){energiJ = d;}
    double getEnergiJ(){return energiJ;}

    void setEnergiK(double d){energiK = d;}
    double getEnergiK(){return energiK;}
        
    void setVatten(double d){vatten = d;}
    double getVatten(){return vatten;}
    
    void setProtein(double d){protein = d;}
    double getProtein(){return protein;}

    void setFett(double d){fett = d;}
    double getFett(){return fett;}

    void setKolhydrater(double d){kolhydrater = d;}
    double getKolhydrater(){return kolhydrater;}

    void setRetinol(double d){retinol = d;}
    double getRetinol(){return retinol;}

    void setKaroten(double d){karoten = d;}
    double getKaroten(){return karoten;}
    
    void setRetinolekv(double d){retinolekv = d;}
    double getRetinolekv(){return retinolekv;}
  
    void setDvitamin(double d){Dvitamin = d;}
    double getDvitamin(){return Dvitamin;}

    void setaTokoferol(double d){aTokoferol = d;}
    double getaTokoferol(){return aTokoferol;}

    void setTiamin(double d){tiamin = d;}
    double getTiamin(){return tiamin;}
    
    void setAskorbinsyra(double d){askorbinsyra = d;}
    double getAskorbinsyra(){return askorbinsyra;}
    
    void setRiboflavin(double d){riboflavin = d;}
    double getRiboflavin(){return riboflavin;}
    
    void setNiacin(double d){niacin = d;}
    double getNiacin(){return niacin;}

    void setNiacinekv(double d){niacinekv = d;}
    double getNiacinekv(){return niacinekv;}

    void setVitaminB6(double d){vitaminB6 = d;}
    double getVitaminB6(){return vitaminB6;}
    
    void setVitaminB12(double d){vitaminB12 = d;}
    double getVitaminB12(){return vitaminB12;}
    
    void setKalcium(double d){kalcium = d;}
    double getKalcium(){return kalcium;}
    
    void setFosfor(double d){fosfor = d;}
    double getFosfor(){return fosfor;}
    
    void setJärn(double d){järn = d;}
    double getJärn(){return järn;}
    
    void setMagnesium(double d){magnesium = d;}
    double getMagnesium(){return magnesium;}

    void setNatrium(double d){natrium = d;}
    double getNatrium(){return natrium;}

    void setKalium(double d){kalium = d;}
    double getKalium(){return kalium;}
    
    void setZink(double d){zink = d;}
    double getZink(){return zink;}
    
    void setMättadeFettsyror(double d){mättadeFettsyror = d;}
    double getMättadeFettsyror(){return mättadeFettsyror;}

    void setEnkelomättadeFettsyror(double d){enkelomättadeFettsyror = d;}
    double getEnkelomättadeFettsyror(){return enkelomättadeFettsyror;}
    
    void setFleromättadeFettsyror(double d){fleromättadeFettsyror = d;}
    double getFleromättadeFettsyror(){return fleromättadeFettsyror;}

    void setKolesterol(double d){kolesterol = d;}
    double getKolesterol(){return kolesterol;}

    void setMonosackarider(double d){monosackarider = d;}
    double getMonosackarider(){return monosackarider;}

    void setDisackarider(double d){disackarider = d;}
    double getDisackarider(){return disackarider;}
    
    void setSackaros(double d){sackaros = d;}
    double getSackaros(){return sackaros;}

    void setFibrer(double d){fibrer = d;}
    double getFibrer(){return fibrer;}
    
    void setAlkohol(double d){alkohol = d;}
    double getAlkohol(){return alkohol;}
    
    void setFettsyraC4C10(double d){fettsyraC4C10 = d;}
    double getFettsyraC4C10(){return fettsyraC4C10;}
    
    void setFettsyraC120(double d){fettsyraC120 = d;}
    double getFettsyraC120(){return fettsyraC120;}
    
    void setFettsyraC140(double d){fettsyraC140 = d;}
    double getFettsyraC140(){return fettsyraC140;}

    void setFettsyraC160(double d){fettsyraC160 = d;}
    double getFettsyraC160(){return fettsyraC160;}
    
    void setFettsyraC180(double d){fettsyraC180 = d;}
    double getFettsyraC180(){return fettsyraC180;}

    void setFettsyraC200(double d){fettsyraC200 = d;}
    double getFettsyraC200(){return fettsyraC200;}
    
    void setFettsyraC161(double d){fettsyraC161 = d;}
    double getFettsyraC161(){return fettsyraC161;}
    
    void setFettsyraC181(double d){fettsyraC181 = d;}
    double getFettsyraC181(){return fettsyraC181;}

    void setFettsyraC182(double d){fettsyraC182 = d;}
    double getFettsyraC182(){return fettsyraC182;}

    void setFettsyraC183(double d){fettsyraC183 = d;}
    double getFettsyraC183(){return fettsyraC183;}

    void setFettsyraC204(double d){fettsyraC204 = d;}
    double getFettsyraC204(){return fettsyraC204;}

    void setFettsyraC205(double d){fettsyraC205 = d;}
    double getFettsyraC205(){return fettsyraC205;}
    
    void setFettsyraC225(double d){fettsyraC225 = d;}
    double getFettsyraC225(){return fettsyraC225;}

    void setFettsyraC226(double d){fettsyraC226 = d;}
    double getFettsyraC226(){return fettsyraC226;}
    
    void setFolat(double d){folat = d;}
    double getFolat(){return folat;}

    void setSelen(double d){selen = d;}
    double getSelen(){return selen;}
}