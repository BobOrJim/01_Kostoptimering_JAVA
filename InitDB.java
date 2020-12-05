package Xjobb;

//till kommunikation med databasen
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.io.File;
//import java.lang.Double.*;

//läser in all data från databasen och placerar detta i objekt
public class InitDB {
        String kolumn[] = {"livsmedel", "avfall", "energiJ", "energiK", "vatten", 
        "protein", "fett", "kolhydrater", "retinol", "karoten", "retinolekv", "Dvitamin",
        "aTokoferol", "tiamin", "askorbinsyra", "riboflavin", "niacin", "niacinekv", 
        "vitaminB6", "vitaminB12", "kalcium", "fosfor", "järn", "magnesium", "natrium",
        "kalium", "zink", "mättadeFettsyror", "enkelomättadeFettsyror", "fleromättadeFettsyror",
        "kolesterol", "monosackarider", "disackarider", "sackaros", "fibrer", "alkohol",
        "fettsyraC4C10", "fettsyraC120", "fettsyraC140", "fettsyraC160", "fettsyraC180",
        "fettsyraC200", "fettsyraC161", "fettsyraC181", "fettsyraC182", "fettsyraC183",
        "fettsyraC204", "fettsyraC205", "fettsyraC225", "fettsyraC226", "folat", "selen"};    

        Livsmedel[] livsmedel = new Livsmedel[2000]; //array av livsmedel
        Livsmedel tmpLivsmedel;
        int i=0, j=0, m=0;
    public InitDB(){
//Från exprimentering med relativa sökvägar till databasen, har misslyckats
//med detta.        
/*  File file = new File(".");
    System.out.println("absolute path = " + file.getAbsolutePath());        
    File f = new File("./tt.mdb");
    File filePref = new File("preferences.txt");
    System.out.println("preferences.txt path: " + filePref.getAbsolutePath());*/
//  String DRIVER = "jdbc:odbc:tt1";
        String url = "jdbc:odbc:DRIVER="+
        "{Microsoft Access Driver (*.mdb)};"+
//        "DBQ=c:\\tt.mdb";
        "DBQ="+ new File("c:/kost/tt.mdb");
//        "DBQ="+ f;
        
        String str2;
        String str3;
        try{
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");     
            Connection con = DriverManager.getConnection(url, "", "");
            Statement stmt = con.createStatement();
            ResultSet rs;
                for(int k=0; k<=51; k++){
                    str2 = "SELECT * FROM Blad1";
                    rs = stmt.executeQuery(str2);
                    while(rs.next()){
                        str3 = rs.getString(kolumn[k]);
                        insertInDB(str3);
                    }
                }
            con.close();
        } catch (Exception ex){
            System.out.println("Problem vid upprättande av anslutning:" + ex);
        }
    }

    void insertInDB(String str){
        m = i/1970;
//skulle kunna lösas med modulus (%) men jag vill hålla nere antalet beräkningar
        if(m == 0){ 
            tmpLivsmedel = new Livsmedel();
            tmpLivsmedel.setLivsM(str);
            livsmedel[i] = tmpLivsmedel;
        }
        if(m == 1){
            j=i%1970;
            tmpLivsmedel = livsmedel[j];
            tmpLivsmedel.setAvfall(stringToFloat(str));
            livsmedel[j] = tmpLivsmedel;
        }
        if(m == 2){
            j=i%1970;
            tmpLivsmedel = livsmedel[j];
            tmpLivsmedel.setEnergiJ(stringToFloat(str));
            livsmedel[j] = tmpLivsmedel;
        }
        if(m == 3){
            j=i%1970;
            tmpLivsmedel = livsmedel[j];
            tmpLivsmedel.setEnergiK(stringToFloat(str));
            livsmedel[j] = tmpLivsmedel;
        }

        if(m == 4){
            j=i%1970;
            tmpLivsmedel = livsmedel[j];
            tmpLivsmedel.setVatten(stringToFloat(str));
            livsmedel[j] = tmpLivsmedel;
        }
        
        if(m == 5){
            j=i%1970;
            tmpLivsmedel = livsmedel[j];
            tmpLivsmedel.setProtein(stringToFloat(str));
            livsmedel[j] = tmpLivsmedel;
        }        
        
        if(m == 6){
            j=i%1970;
            tmpLivsmedel = livsmedel[j];
            tmpLivsmedel.setFett(stringToFloat(str));
            livsmedel[j] = tmpLivsmedel;
        }        
        
        if(m == 7){
            j=i%1970;
            tmpLivsmedel = livsmedel[j];
            tmpLivsmedel.setKolhydrater(stringToFloat(str));
            livsmedel[j] = tmpLivsmedel;
        }        
        
        if(m == 8){
            j=i%1970;
            tmpLivsmedel = livsmedel[j];
            tmpLivsmedel.setRetinol(stringToFloat(str));
            livsmedel[j] = tmpLivsmedel;
        }        
        
        if(m == 9){
            j=i%1970;
            tmpLivsmedel = livsmedel[j];
            tmpLivsmedel.setKaroten(stringToFloat(str));
            livsmedel[j] = tmpLivsmedel;
        }        
        
        if(m == 10){
            j=i%1970;
            tmpLivsmedel = livsmedel[j];
            tmpLivsmedel.setRetinolekv(stringToFloat(str));
            livsmedel[j] = tmpLivsmedel;
        }        
        
        if(m == 11){
            j=i%1970;
            tmpLivsmedel = livsmedel[j];
            tmpLivsmedel.setDvitamin(stringToFloat(str));
            livsmedel[j] = tmpLivsmedel;
        }        

        if(m == 12){
            j=i%1970;
            tmpLivsmedel = livsmedel[j];
            tmpLivsmedel.setaTokoferol(stringToFloat(str));
            livsmedel[j] = tmpLivsmedel;
        }        
        
        if(m == 13){
            j=i%1970;
            tmpLivsmedel = livsmedel[j];
            tmpLivsmedel.setTiamin(stringToFloat(str));
            livsmedel[j] = tmpLivsmedel;
        }        
        
        if(m == 14){
            j=i%1970;
            tmpLivsmedel = livsmedel[j];
            tmpLivsmedel.setAskorbinsyra(stringToFloat(str));
            livsmedel[j] = tmpLivsmedel;
        }        

        if(m == 15){
            j=i%1970;
            tmpLivsmedel = livsmedel[j];
            tmpLivsmedel.setRiboflavin(stringToFloat(str));
            livsmedel[j] = tmpLivsmedel;
        }        
        
        if(m == 16){
            j=i%1970;
            tmpLivsmedel = livsmedel[j];
            tmpLivsmedel.setNiacin(stringToFloat(str));
            livsmedel[j] = tmpLivsmedel;
        }        
        
        if(m == 17){
            j=i%1970;
            tmpLivsmedel = livsmedel[j];
            tmpLivsmedel.setNiacinekv(stringToFloat(str));
            livsmedel[j] = tmpLivsmedel;
        }        
        
        if(m == 18){
            j=i%1970;
            tmpLivsmedel = livsmedel[j];
            tmpLivsmedel.setVitaminB6(stringToFloat(str));
            livsmedel[j] = tmpLivsmedel;
        }

        if(m == 19){
            j=i%1970;
            tmpLivsmedel = livsmedel[j];
            tmpLivsmedel.setVitaminB12(stringToFloat(str));
            livsmedel[j] = tmpLivsmedel;
        }        
        
        if(m == 20){
            j=i%1970;
            tmpLivsmedel = livsmedel[j];
            tmpLivsmedel.setKalcium(stringToFloat(str));
            livsmedel[j] = tmpLivsmedel;
        }
        
        if(m == 21){
            j=i%1970;
            tmpLivsmedel = livsmedel[j];
            tmpLivsmedel.setFosfor(stringToFloat(str));
            livsmedel[j] = tmpLivsmedel;
        }
        
        if(m == 22){
            j=i%1970;
            tmpLivsmedel = livsmedel[j];
            tmpLivsmedel.setJärn(stringToFloat(str));
            livsmedel[j] = tmpLivsmedel;
        }        
        
        if(m == 23){
            j=i%1970;
            tmpLivsmedel = livsmedel[j];
            tmpLivsmedel.setMagnesium(stringToFloat(str));
            livsmedel[j] = tmpLivsmedel;
        }        
        
        if(m == 24){
            j=i%1970;
            tmpLivsmedel = livsmedel[j];
            tmpLivsmedel.setNatrium(stringToFloat(str));
            livsmedel[j] = tmpLivsmedel;
        }        

        if(m == 25){
            j=i%1970;
            tmpLivsmedel = livsmedel[j];
            tmpLivsmedel.setKalium(stringToFloat(str));
            livsmedel[j] = tmpLivsmedel;
        }        

        if(m == 26){
            j=i%1970;
            tmpLivsmedel = livsmedel[j];
            tmpLivsmedel.setZink(stringToFloat(str));
            livsmedel[j] = tmpLivsmedel;
        }        
        
        if(m == 27){
            j=i%1970;
            tmpLivsmedel = livsmedel[j];
            tmpLivsmedel.setMättadeFettsyror(stringToFloat(str));
            livsmedel[j] = tmpLivsmedel;
        }        
        
        if(m == 28){
            j=i%1970;
            tmpLivsmedel = livsmedel[j];
            tmpLivsmedel.setEnkelomättadeFettsyror(stringToFloat(str));
            livsmedel[j] = tmpLivsmedel;
        }

        if(m == 29){
            j=i%1970;
            tmpLivsmedel = livsmedel[j];
            tmpLivsmedel.setFleromättadeFettsyror(stringToFloat(str));
            livsmedel[j] = tmpLivsmedel;
        }        
        
        if(m == 30){
            j=i%1970;
            tmpLivsmedel = livsmedel[j];
            tmpLivsmedel.setKolesterol(stringToFloat(str));
            livsmedel[j] = tmpLivsmedel;
        }
        
        if(m == 31){
            j=i%1970;
            tmpLivsmedel = livsmedel[j];
            tmpLivsmedel.setMonosackarider(stringToFloat(str));
            livsmedel[j] = tmpLivsmedel;
        }        
        
        if(m == 32){
            j=i%1970;
            tmpLivsmedel = livsmedel[j];
            tmpLivsmedel.setDisackarider(stringToFloat(str));
            livsmedel[j] = tmpLivsmedel;
        }        
        
        if(m == 33){
            j=i%1970;
            tmpLivsmedel = livsmedel[j];
            tmpLivsmedel.setSackaros(stringToFloat(str));
            livsmedel[j] = tmpLivsmedel;
        }        
        
        if(m == 34){
            j=i%1970;
            tmpLivsmedel = livsmedel[j];
            tmpLivsmedel.setFibrer(stringToFloat(str));
            livsmedel[j] = tmpLivsmedel;
        }        
        
        if(m == 35){
            j=i%1970;
            tmpLivsmedel = livsmedel[j];
            tmpLivsmedel.setAlkohol(stringToFloat(str));
            livsmedel[j] = tmpLivsmedel;
        }        

        if(m == 36){
            j=i%1970;
            tmpLivsmedel = livsmedel[j];
            tmpLivsmedel.setFettsyraC4C10(stringToFloat(str));
            livsmedel[j] = tmpLivsmedel;
        }        

        if(m == 37){
            j=i%1970;
            tmpLivsmedel = livsmedel[j];
            tmpLivsmedel.setFettsyraC120(stringToFloat(str));
            livsmedel[j] = tmpLivsmedel;
        }        
        
        if(m == 38){
            j=i%1970;
            tmpLivsmedel = livsmedel[j];
            tmpLivsmedel.setFettsyraC140(stringToFloat(str));
            livsmedel[j] = tmpLivsmedel;
        }        
      
        if(m == 39){
            j=i%1970;
            tmpLivsmedel = livsmedel[j];
            tmpLivsmedel.setFettsyraC160(stringToFloat(str));
            livsmedel[j] = tmpLivsmedel;
        }        

        if(m == 40){
            j=i%1970;
            tmpLivsmedel = livsmedel[j];
            tmpLivsmedel.setFettsyraC180(stringToFloat(str));
            livsmedel[j] = tmpLivsmedel;
        }        
        
        if(m == 41){
            j=i%1970;
            tmpLivsmedel = livsmedel[j];
            tmpLivsmedel.setFettsyraC200(stringToFloat(str));
            livsmedel[j] = tmpLivsmedel;
        }        

        if(m == 42){
            j=i%1970;
            tmpLivsmedel = livsmedel[j];
            tmpLivsmedel.setFettsyraC161(stringToFloat(str));
            livsmedel[j] = tmpLivsmedel;
        }        

        if(m == 43){
            j=i%1970;
            tmpLivsmedel = livsmedel[j];
            tmpLivsmedel.setFettsyraC181(stringToFloat(str));
            livsmedel[j] = tmpLivsmedel;
        }        
        
        if(m == 44){
            j=i%1970;
            tmpLivsmedel = livsmedel[j];
            tmpLivsmedel.setFettsyraC182(stringToFloat(str));
            livsmedel[j] = tmpLivsmedel;
        }        

        if(m == 45){
            j=i%1970;
            tmpLivsmedel = livsmedel[j];
            tmpLivsmedel.setFettsyraC183(stringToFloat(str));
            livsmedel[j] = tmpLivsmedel;
        }        
        
        if(m == 46){
            j=i%1970;
            tmpLivsmedel = livsmedel[j];
            tmpLivsmedel.setFettsyraC204(stringToFloat(str));
            livsmedel[j] = tmpLivsmedel;
        }        

        if(m == 47){
            j=i%1970;
            tmpLivsmedel = livsmedel[j];
            tmpLivsmedel.setFettsyraC205(stringToFloat(str));
            livsmedel[j] = tmpLivsmedel;
        }        

        if(m == 48){
            j=i%1970;
            tmpLivsmedel = livsmedel[j];
            tmpLivsmedel.setFettsyraC225(stringToFloat(str));
            livsmedel[j] = tmpLivsmedel;
        }        

        if(m == 49){
            j=i%1970;
            tmpLivsmedel = livsmedel[j];
            tmpLivsmedel.setFettsyraC226(stringToFloat(str));
            livsmedel[j] = tmpLivsmedel;
        }        

        if(m == 50){
            j=i%1970;
            tmpLivsmedel = livsmedel[j];
            tmpLivsmedel.setFolat(stringToFloat(str));
            livsmedel[j] = tmpLivsmedel;
        }        
        
        if(m == 51){
            j=i%1970;
            tmpLivsmedel = livsmedel[j];
            tmpLivsmedel.setSelen(stringToFloat(str));
            livsmedel[j] = tmpLivsmedel;
        }        
        
        i++;
    }

    Livsmedel[] getLivsmedel(){
        return livsmedel;
    }

//byter alla decimal komma mot decimalpunkt för att kunna använda
//mina data till beräkningar    
    double stringToFloat(String str){
        String str2 = str.replace( ',', '.' );
        double d=0;
        try{
            d = Double.parseDouble(str2);
        }
        catch (NumberFormatException e){
            System.out.println("Någon har fingrat med databasen");
        }
        return d;
    }
}







