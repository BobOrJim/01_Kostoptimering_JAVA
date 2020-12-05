package Xjobb;

//för varningsruta vid felinmatning i RDI tabell
import javax.swing.JOptionPane;
//till hantering av grafik och färger
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

//tar emot data från RDI tabellen och ritar ut denna grafiskt
public class MyCanvas extends Canvas{
    Color color;
    String[][] initRDIdata;
    MyCanvas(String[][] initRDIdata){
        this.initRDIdata = initRDIdata;
        System.out.println("nu körs initMyCanvas konstruktorn");
    }
    
    public void paint(Graphics g){
String[] ss = new String[]{ //vektor av vektorer, för utskrift på tabeller
   "Energi J", "Energi K", "Vatten", "Protein", "Fett", "Kolhydrater", "Retinol",
   "Karoten", "Retinolekv", "D vitamin", "A-Tokoferol", "Tiamin", 
   "Askorbinsyra", "Riboflavin", "Niacin", "Niacinekv", "Vitamin B6", 
   "Vitamin B12", "Kalcium", "Fosfor", "Järn", "Magnesium", "Natrium",
   "Kalium", "Zink", "Mättade fettsyror", "Enkelom. fettsyror", 
   "Flerom. fettsyror", "Kolesterol", "Monosackarider", "Disackarider", "Sackaros",
   "Fibrer", "Alkohol", "Fettsyra C4-C10", "Fettsyra C12:0", "Fettsyra C14:0",
   "Fettsyra C16:0", "Fettsyra C18:0", "Fettsyra C20:0", "Fettsyra C16:1", "Fettsyra C18:1",
   "Fettsyra C18:2", "Fettsyra C18:3", "Fettsyra C20:4", "Fettsyra C20:5", "Fettsyra C22:5",
   "Fettsyra C22:6", "Folat", "Selen"};

for(int p=0; p<ss.length; p++){ //byter "-" mot null och löser även några null problem
    String a2 = (String) initRDIdata[p][2];
    String b2 = (String) initRDIdata[p][1];
    String a3 = "-";
    if(a2 == null || a2 == "" || a2.equals(a3)){
        initRDIdata[p][2] = "null";
    }
    if(b2 == null || b2 == "" || b2.equals(a3)){
        initRDIdata[p][1] = "null";
    }
}


        
//ritar ut staplar
        for(int k=0; k<50; k++){
            String a = (String) initRDIdata[k][2];
            String b = (String) initRDIdata[k][1];
            String c = "null";
            if(!a.equals(c) && !b.equals(c)){
                try{
                    double d1 = Double.parseDouble(initRDIdata[k][1].trim());
                    double d2 = Double.parseDouble(initRDIdata[k][2].trim());

                    double d3 = d1/d2;
                    g.setColor(new java.awt.Color(125, 190, 0));
                    if(d1 <= d2){
                        d3=200-d3*100;
                        int i1 = (int) d3;
                        g.fill3DRect((10+19*k), i1, 10, 200, true);
                    }
                    if(d1 > d2 && d1 <= 2*d2){
                        d3=150 - d3*50;
                        int i1 = (int) d3;
                        g.fill3DRect((10+19*k), i1, 10, 200, true);
                    }
                    if(d1 > 2*d2 && d1 <= 4*d2){
                        d3=75 - d3*12.5;
                        int i1 = (int) d3;
                        g.fill3DRect((10+19*k), i1, 10, 200, true);
                    }
                    if(d1 > 4*d2 && d1 <= 8*d2){
                        d3=50 - d3*6.25;
                        int i1 = (int) d3;
                        g.fill3DRect((10+19*k), i1, 10, 200, true);
                    }
                    if(d1 > 8*d2){
                        g.fill3DRect((10+19*k), 0, 10, 200, true);
                    }
                }
                catch (java.lang.NumberFormatException n){
                    JOptionPane.showMessageDialog(null, "Använd endast siffror, med decimalpunkt", "Varning", JOptionPane.WARNING_MESSAGE);
                    break;
                }
            }
        }
         
//RDI text
        g.setColor(new java.awt.Color(170, 170, 255));
        g.drawString("R", 958, 10);
        g.drawString("D", 968, 10);
        g.drawString("I", 978, 10);
        g.drawString(":", 983, 10);
//800% linjen

        int j=5;
        while(j<978){
            g.drawLine(j, 0, j+10, 0);
            j = j + 20;
        }
//400% linjen
        j=5;
        while(j<978){
            g.drawLine(j, 25, j+10, 25);
            j = j + 20;
        }
//200% linjen
        j=5;
        while(j<978){
            g.drawLine(j, 50, j+10, 50);
            j = j + 20;
        }
//100% linjen
        j=5;
        while(j<978){
            g.drawLine(j, 100, j+10, 100);
            j = j + 20;
        }
//50% linjen
        j=5;
        while(j<978){
            g.drawLine(j, 150, j+10, 150);
            j = j + 20;
        }
//25% linjen
        j=5;
        while(j<978){
            g.drawLine(j, 175, j+10, 175);
            j = j + 20;
        }
//Skriver ut texten till linjerna
        g.setColor(new java.awt.Color(80, 80, 200));        
        g.drawString("8", 989, 10);
        g.drawString("0", 996, 10);
        g.drawString("0", 1003, 10);
        g.drawString("%", 1010, 10);
        g.drawString("4", 989, 30);
        g.drawString("0", 996, 30);
        g.drawString("0", 1003, 30);
        g.drawString("%", 1010, 30);
        g.drawString("2", 989, 55);
        g.drawString("0", 996, 55);
        g.drawString("0", 1003, 55);
        g.drawString("%", 1010, 55);
        g.drawString("1", 989, 104);
        g.drawString("0", 996, 104);
        g.drawString("0", 1003, 104);
        g.drawString("%", 1010, 104);
        g.drawString("5", 996, 155);
        g.drawString("0", 1003, 155);
        g.drawString("%", 1010, 155);
        g.drawString("2", 996, 178);
        g.drawString("5", 1003, 178);
        g.drawString("%", 1010, 178);
        
//skriver livsmedels text   
        g.setColor(Color.black);        
        for(int m=0; m < ss.length; m++){
            String a1 = (String) initRDIdata[m][2];
            String b1 = (String) initRDIdata[m][1];
            String c1 = "null";
            if(!a1.equals(c1) && !b1.equals(c1)){
                for(int n=0; n < ss[m].length(); n++){
                    g.drawString(ss[m].substring(n, n+1), 11+19*m, 10+10*n);
                }
            }
        }                
        
//Stoppar tillbacks "-"
            for(int i=0; i< initRDIdata.length; i++){
            for(int k=1; k <= 2; k++){
                String s3 = (String) initRDIdata[i][k];
                String s = "null";
                String s2 = "";
                if(s3 == null || s3.equals(s2) || s3 == "" || s3.equals(s)){
                    initRDIdata[i][k] = "-";
                }
            }
        }
    }
}