package Xjobb;

//Till "Explorer" trädet.
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
//Till jTable tabellerna och dess selection av rader.
import javax.swing.event.ListSelectionEvent; 
import javax.swing.event.ListSelectionListener;
import javax.swing.ListSelectionModel;
import javax.swing.JTable;
//till detection av förändringar i jTable
import javax.swing.event.TableModelListener;
import javax.swing.event.TableModelEvent;
import javax.swing.table.TableModel;
//till inställning av kolumnbredd hos tabeller
import javax.swing.table.TableColumn;
//till de länkade listorna.
import java.util.LinkedList;
//till hantering av bildinläsning samt utskrift av dessa
import java.awt.Toolkit;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Graphics;
import java.awt.Color;
//till JOptionPane/popup rutor
import javax.swing.JOptionPane;
//till diverse grafiska komponenter
import javax.swing.JPanel;
//för experimentation med look and feel
import javax.swing.UIManager;
//till styckning av strängar (för sparande av data till textfil)
import java.util.StringTokenizer;
//till formatering av antalet decimaler (klippa bort mkt långa decimalföljder)
import java.text.DecimalFormat;
//till att öppna och spara profiler
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;


//Detta är huvudklassen som handerar det grafiska fönstret, tar emot händelser
//från detta samt samordnar körningen av övriga klasser.
public class MyJFrame extends javax.swing.JFrame implements TableModelListener{
    Livsmedel[] livsmedel; //vektor som lagrar mina 1200 livsmedel
    DefaultMutableTreeNode root, parent, child; //träd noder
    JTree aTree; //träd
    LinkedList linkedList = new LinkedList(); //lagrar kylskål
    LinkedList linkedList2 = new LinkedList(); //lagrar planerad måltid
    Livsmedel removeLivsmedel; //lagrar livsmedel som valts att tas bort
    boolean removeFrom; //true = radera från initMaltid, false = radera från initKylskap
    String[][] initRDIdata = new String[50][3]; //RDI tabellens data
    Object[][] initMaltiddata = new Object[1][6]; //måltids tabellens data
    Object[][] initKylskapdata = new Object[0][0]; //kylskåpstabellens data
    
    String[] s = //för kommunikation med get/set metoder i Livsmedels klassen
    {"livsM", "avfall", "energiJ", "energiK", "vatten", "protein", "fett", 
    "kolhydrater", "retinol", "karoten", "retinolekv", "Dvitamin", 
    "aTokoferol", "tiamin", "askorbinsyra", "riboflavin", "niacin", 
    "niacinekv", "vitaminB6", "vitaminB12", "kalcium", "fosfor", "järn",
    "magnesium", "natrium", "kalium", "zink", "mättadeFettsyror",
    "enkelomättadeFettsyror", "fleromättadeFettsyror", "kolesterol",
    "monosackarider", "disackarider", "sackaros", "fibrer", "alkohol",
    "fettsyraC4C10", "fettsyraC120", "fettsyraC140", "fettsyraC160",
    "fettsyraC180", "fettsyraC200", "fettsyraC161", "fettsyraC181",
    "fettsyraC182", "fettsyraC183", "fettsyraC204", "fettsyraC205",
    "fettsyraC225", "fettsyraC226", "folat", "selen"};

    String[] s2 =  //Presentations namn i RDI tabellen
    {"Energi (kJ)", "Energi (kcal)", "Vatten (g)", "Protein (g)", "Fett (g)",
    "Kolhydrater (g)", "Retinol (mcg)", "Karoten (mcg)", "Retinolekv (mcg)",
    "D vitamin (mcg)", "a-Tokoferol (mg)", "Tiamin (mg)", "Askorbinsyra (mg)", 
    "Riboflavin (mg)", "Niacin (mg)", "Niacinekv (mg)", "Vitamin B6 (mg)",
    "Vitamin B12 (mcg)", "Kalcium (mg)", "Fosfor (mg)", "Järn (mg)",
    "Magnesium (mg)", "Natrium (mg)", "Kalium (mg)", "Zink (mg)",
    "Mättade fettsyror (g)", "Enkelomättade fettsyror (g)", 
    "Fleromättade fettsyror (g)", "Kolesterol (mg)", "Monosackarider (g)",
    "Disackarider (g)", "Sackaros (g)", "Fibrer (g)", "Alkohol (g)",
    "Fettsyra C4-C10 (g)", "Fettsyra C12:0 (g)", "Fettsyra C14:0 (g)",
    "Fettsyra C16:0 (g)", "Fettsyra C18:0 (g)", "Fettsyra C20:0 (g)",
    "Fettsyra C16:1 (g)", "Fettsyra C18:1 (g)", "Fettsyra C18:2 (g)",
    "Fettsyra C18:3 (g)", "Fettsyra C20:4 (g)", "Fettsyra C20:5 (g)",
    "Fettsyra C22:5 (g)", "Fettsyra C22:6 (g)", "Folat (mcg)", "Selen (mcg)"};

    public MyJFrame() {

//experimentation med look and feel för ett snyggt användargränssnitt        
    try {
        UIManager.setLookAndFeel(
        "javax.swing.plaf.metal.MetalLookAndFeel" );
        //"javax.swing.plaf.basic.BasicLookAndFeel");
        //"javax.swing.plaf.metal.DefaultMetalTheme");
        //"com.sun.java.swing.plaf.gtk.GTKLookAndFeel");//
        //"com.sun.java.swing.plaf.motif.MotifLookAndFeel" );//
        //"com.sun.java.swing.plaf.windows.WindowsLookAndFeel" );//
        ///UIManager.getSystemLookAndFeelClassName() );//
        //UIManager.getCrossPlatformLookAndFeelClassName());
    } catch (Exception e) { }
        
        initComponents(); //initierar grafiska komponenter
        InitDB initDB = new InitDB(); //initierar databasen
        livsmedel = initDB.getLivsmedel(); //hämtar data från databasen
        initMyTree(); //initierar träd till presentation av databasen
        initRDI("", 0, 0); //initierar RDI tabellen
        initBackgroundImage(); //initierar bakgrundsbild
        initGrafik(); //initierar övrig grafik
    }
    
    private void initComponents() {//GEN-BEGIN:initComponents
        jFileChooser1 = new javax.swing.JFileChooser();
        jEditor = new javax.swing.JEditorPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        optimeraButton = new javax.swing.JButton();
        grafikButton = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jScrollPane5 = new javax.swing.JScrollPane();
        flyttaButton = new javax.swing.JButton();
        atButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        taBortButton = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        Arkiv = new javax.swing.JMenu();
        Open = new javax.swing.JMenuItem();
        Save = new javax.swing.JMenuItem();
        SaveAs = new javax.swing.JMenuItem();
        Avsluta = new javax.swing.JMenuItem();
        Help1 = new javax.swing.JMenu();
        Help = new javax.swing.JMenuItem();

        jFileChooser1.setCurrentDirectory(new java.io.File("C:\\kost"));

        getContentPane().setLayout(null);

        setTitle("Pauluns kostoptimerare - paulun.se");
        setBackground(new java.awt.Color(153, 255, 153));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setForeground(java.awt.Color.red);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        jPanel1.setLayout(null);

        jPanel1.setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(1, 1, 1, 1)));
        jPanel1.setForeground(new java.awt.Color(255, 102, 102));
        jPanel1.setPreferredSize(new java.awt.Dimension(1024, 768));
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(0, 0, 340, 490);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 340, 490);

        jScrollPane2.setForeground(new java.awt.Color(153, 244, 51));
        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane2.setAutoscrolls(true);
        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(350, 320, 670, 170);

        jScrollPane3.setForeground(new java.awt.Color(51, 0, 51));
        jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane3.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        getContentPane().add(jScrollPane3);
        jScrollPane3.setBounds(0, 500, 1024, 200);

        jLabel1.setText("Mitt Kylsk\u00e5p (100g):");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(350, 300, 290, 20);

        jLabel2.setText(" Verkligt och \u00f6nskat \u00e4mnes intag (RDI):");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(700, 0, 240, 20);

        optimeraButton.setBackground(new java.awt.Color(204, 201, 242));
        optimeraButton.setText("Optimera");
        optimeraButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optimeraButtonActionPerformed(evt);
            }
        });

        getContentPane().add(optimeraButton);
        optimeraButton.setBounds(350, 240, 160, 26);

        grafikButton.setBackground(new java.awt.Color(204, 201, 242));
        grafikButton.setText("Grafisk presentation");
        grafikButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                grafikButtonActionPerformed(evt);
            }
        });

        getContentPane().add(grafikButton);
        grafikButton.setBounds(860, 240, 160, 26);

        getContentPane().add(jScrollPane4);
        jScrollPane4.setBounds(690, 20, 330, 210);

        getContentPane().add(jScrollPane5);
        jScrollPane5.setBounds(350, 20, 330, 210);

        flyttaButton.setBackground(new java.awt.Color(204, 201, 242));
        flyttaButton.setText("Optimerat till Planerat");
        flyttaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                flyttaButtonActionPerformed(evt);
            }
        });

        getContentPane().add(flyttaButton);
        flyttaButton.setBounds(520, 240, 160, 26);

        atButton.setBackground(new java.awt.Color(204, 201, 242));
        atButton.setText("\u00c4t planerad m\u00e5ltid");
        atButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                atButtonActionPerformed(evt);
            }
        });

        getContentPane().add(atButton);
        atButton.setBounds(520, 270, 160, 26);

        jLabel3.setText("Planerad m\u00e5ltid (g):");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(350, 0, 130, 20);

        taBortButton.setBackground(new java.awt.Color(204, 201, 242));
        taBortButton.setText("Ta bort livsmedel");
        taBortButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                taBortButtonActionPerformed(evt);
            }
        });

        getContentPane().add(taBortButton);
        taBortButton.setBounds(350, 270, 160, 26);

        jMenuBar1.setBackground(new java.awt.Color(204, 201, 242));
        jMenuBar1.setBorder(null);
        Arkiv.setBackground(new java.awt.Color(204, 201, 242));
        Arkiv.setText("Arkiv");
        Open.setBackground(new java.awt.Color(204, 201, 242));
        Open.setText("\u00d6ppna profil");
        Open.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OpenActionPerformed(evt);
            }
        });

        Arkiv.add(Open);

        Save.setBackground(new java.awt.Color(204, 201, 242));
        Save.setText("Spara");
        Save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveActionPerformed(evt);
            }
        });

        Arkiv.add(Save);

        SaveAs.setBackground(new java.awt.Color(204, 201, 242));
        SaveAs.setText("Spara som");
        SaveAs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveAsActionPerformed(evt);
            }
        });

        Arkiv.add(SaveAs);

        Avsluta.setBackground(new java.awt.Color(204, 201, 242));
        Avsluta.setText("Avsluta");
        Avsluta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AvslutaActionPerformed(evt);
            }
        });

        Arkiv.add(Avsluta);

        jMenuBar1.add(Arkiv);

        Help1.setBackground(new java.awt.Color(204, 201, 242));
        Help1.setText("Hj\u00e4lp");
        Help.setBackground(new java.awt.Color(204, 201, 242));
        Help.setText("Hj\u00e4lp om Pauluns kostoptimerare");
        Help.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HelpActionPerformed(evt);
            }
        });

        Help1.add(Help);

        jMenuBar1.add(Help1);

        setJMenuBar(jMenuBar1);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-1028)/2, (screenSize.height-772)/2, 1028, 772);
    }//GEN-END:initComponents

//Hanterar borttagning av markerat livsmedel
    private void taBortButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_taBortButtonActionPerformed
        if(removeFrom == true && removeLivsmedel != null){//vill ta bort från initRDI
            linkedList2.remove(removeLivsmedel);
            removeLivsmedel = null;
            initMaltid(null, false, false, true);
        }
        else if(removeFrom == false && removeLivsmedel != null){//vill ta bort från initKylskap
            linkedList.remove(removeLivsmedel);
            removeLivsmedel = null;
            initKylskap();       
        }
        else{
            JOptionPane.showMessageDialog(null,"Det finns inget att ta bort, markera ett livsmedel"
            +"från ditt kylskåp eller från din planerade måltid", 
            "alert", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_taBortButtonActionPerformed

//Avslutar program
    private void AvslutaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AvslutaActionPerformed
        System.exit(0);
    }//GEN-LAST:event_AvslutaActionPerformed

//Hanterar spara som funktionen
//Denna sparar ned data från kylskåpet samt angivet RDI i en av användaren 
//namngiven textfil.    
    private void SaveAsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveAsActionPerformed
        try {
            int returnVal = jFileChooser1.showSaveDialog(jEditor);
            if(returnVal == jFileChooser1.APPROVE_OPTION) {
                System.out.println("You chose to save to this file: " + jFileChooser1.getSelectedFile().getName());
                FileWriter out = new FileWriter(jFileChooser1.getSelectedFile());
                for(int i=0; i < linkedList.size(); i++){
                    Livsmedel tmpLivsmedel = (Livsmedel) linkedList.get(i);
                    out.write(tmpLivsmedel.getLivsM()+";");
                }
                out.write("\n");
                for(int i=0; i < initRDIdata.length; i++){
                    out.write(initRDIdata[i][2]+";");
                }
                out.close();
            }
        }
        catch (java.io.IOException e1) {
            e1.printStackTrace();
        }
        catch (java.awt.HeadlessException e1) {
            e1.printStackTrace();
        }
    }//GEN-LAST:event_SaveAsActionPerformed

//Samma som SaveAsActionPerformed metoden men utan valmöjlighet att välja fil namn
    private void SaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveActionPerformed
        try {
            System.out.println("You chose to save to this file: " +
            jFileChooser1.getSelectedFile().getName());
            FileWriter out = new FileWriter(jFileChooser1.getSelectedFile());
                for(int i=0; i < linkedList.size(); i++){
                    Livsmedel tmpLivsmedel = (Livsmedel) linkedList.get(i);
                    out.write(tmpLivsmedel.getLivsM()+";");
                }
                out.write("\n");
                for(int i=0; i < initRDIdata.length; i++){
                    out.write(initRDIdata[i][2]+";");
                }
                out.close();
        }
        catch (java.io.IOException ie) {
            ie.printStackTrace();
        }
        catch (java.awt.HeadlessException he) {
            he.printStackTrace();
        }
        catch (java.lang.NullPointerException npe) {
            showMsg("Ingen fil är vald att spara till, använda spara som");
            System.out.println("Du hittade ingen fil att spara till");
        }
    }//GEN-LAST:event_SaveActionPerformed

//Agerar som referens till den bifogade manualen    
    private void HelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HelpActionPerformed
 
JOptionPane.showMessageDialog(null, 
"Detta är en kortverison av den manual vilken medföljer programmet, den regogör"
+" kortfattat för programmets funktioner. \n\n\n"

+"Livsmedelsverkets databas: denna är placerad längst till vänster och dess "
+"syfte är att på ett bra sätt presentera de 1200 livsmedel som ingår. \n"
+"Genom att expandera respektive minimera mapparna vilka representerar olika "
+"mat grupper kan man navigera sig fram.\n\n\n"

+"Arkiv menyn: Här finns möjlighet att spara och öppna profiler. Detta finns "
+"för att flera användare skall kunna använda samma program. \n"
+"Alla filer sparas i form av text (*.txt) filer. För att spara en profil "
+"välj ”spara som”. Ange sedan plats där filen skall sparas och namnge den. \n"
+"För att öppna välj den sparade filen och dubbel klicka på den. \n"
+"Spara är en snabb version av spara som. \n"
+"Information som sparas är det önskade RDI och kyskåpet. Denna information är "
+"alltså personlig.\n\n\n"

+"Planerad måltid: I denna tabell visas den planerade måltiden eller planerat "
+"intag under tex en dag. \n"
+"Alla enheter är i gram och kolumnen längst till höger är den som flyttas "
+"över till RDI tabellen då ”Ät planerad måltid” klickas på.\n\n\n"

+"Verkligt och önskat ämnes intag (RDI): Denna tabell visar det planerade "
+"intaget utifrån ett angivet RDI. \n"
+"Genom att klicka på ”Presentera grafiskt” visas motsvarande information i "
+"form av 0-51 stapeldiagram längst ner. \n"
+"För att välja bort ett ämne från bevakningen ange ett bindestrecks "
+"(-) tecken.\n\n\n"

+"Mitt kylskåp: Denna tabell representerar profilens kylskåp. Syftet med "
+"detta är att livsmedelsverkets databas kan var något otymplig att \n"
+"leta i och därför sparas alla de livsmedels som är intressanta för profilen. \n"
+"Dessutom visas här detaljerad information för varje livsmedel. \n"
+"Om man tex behöver öka sitt intag av protein så flyttar man rullisten bort "
+"till protein kolumnen och kan då enkelt hitta ett proteinrikt \n"
+"livsmedel i sitt kylskåp.\n\n\n"

+"För att använda optimerings funktionerna vilka kan optimera kolhydrater,"
+"protein och fett så måste det önskade intaget av dessa vara \n"
+"angivet i RDI tabellen. \n"
+"Dessutom måste lämpligtvis ett antal livsmedel vara angivna i "
+"tabellen planerad måltid. \n"
+"Dessa livsmedel analyseras och söker efter mängden som gör att "
+"exakt intag av dessa ämnen uppnås.\n\n\n"


, "Hjälp", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_HelpActionPerformed

//Öppnar en fil där användaren har sparat sin profil.
//Denna består av kylskåpet samt önskat RDI    
    private void OpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OpenActionPerformed
        LinkedList tmpLinkedList = new LinkedList(); //lagrar kylskåps input från fil
        LinkedList tmpLinkedList2 = new LinkedList(); //lagrar RDI input från fil
        try{
            int returnVal = jFileChooser1.showOpenDialog(jEditor);
            if(returnVal == jFileChooser1.APPROVE_OPTION) {
                System.out.println("You chose to open this file: " + jFileChooser1.getSelectedFile().getName());
                BufferedReader br = new BufferedReader(new FileReader(jFileChooser1.getSelectedFile()));
                String rad;
                rad = br.readLine();  
                if (rad != null){
                    StringTokenizer st = new StringTokenizer(rad, ";");
                    while(st.hasMoreTokens()){
                        tmpLinkedList.add(st.nextToken());
                    }
                }
                rad = br.readLine();
                if (rad != null){
                    StringTokenizer st = new StringTokenizer(rad, ";");
                    while(st.hasMoreTokens()){
                        tmpLinkedList2.add(st.nextToken());
                    }
                }
                br.close();
            }
        }
        catch (java.io.IOException e1) {
            e1.printStackTrace();
        }
        catch (java.awt.HeadlessException e1) {
            e1.printStackTrace();
        }

//omvandlar tmpLinkedList till riktiga objekt i linkedlist.
        System.out.println(livsmedel.length);
        for(int i=0; i<tmpLinkedList.size(); i++){
            for(int j=0; j<livsmedel.length; j++){
                String a = livsmedel[j].getLivsM();
                String b = (String) tmpLinkedList.get(i);
                if(a.equals(b)){
                    System.out.println("we found a match:  "+a+"  equals   "+b);
                    linkedList.add(livsmedel[j]);
                    break;
                }
            }
        }
//omvandlar tmpLinkedList2 till riktiga strängar i initRDIdata.
        for(int i=0; i<tmpLinkedList2.size(); i++){
            initRDIdata[i][2] = (String) tmpLinkedList2.get(i);
        }
//initierar kylskåp och RDI tabelle efter det att data har lästs in
        initKylskap();
        initRDI("", 0, 0);
    }//GEN-LAST:event_OpenActionPerformed

//Skickar RDI tabellen tillMyCanvas klassen vilken skapar den grafiska
//presentationen av näringsämnena i den planerade måltiden
//i relation till önskat RDI    
    private void grafikButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_grafikButtonActionPerformed
        MyCanvas canvas = new MyCanvas(initRDIdata);
        jScrollPane3.setViewportView(canvas);
    }//GEN-LAST:event_grafikButtonActionPerformed

    
//Laddar in bakgrundsbild med druva    
    void initGrafik(){
        Toolkit tk = Toolkit.getDefaultToolkit();
        final Image image = tk.createImage("c:/kost/t2.jpg");
        MediaTracker tracker = new MediaTracker(this);//håller tråden som ska använda bilden tills det att bilden är inladdad
        tracker.addImage(image, 0);  
        try {  
            tracker.waitForAll();
        }catch (InterruptedException e) {
            System.out.println("fel vid inladdning av bild");
        }
        JPanel panel4 = new JPanel(){
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                g.drawImage(image, 0, 0, 1024, 200, Color.white, null);
            }
        };
        jScrollPane3.setViewportView(panel4);
        final Image image2 = tk.createImage("c:/kost/druva.jpg");
        MediaTracker tracker2 = new MediaTracker(this);//håller tråden som ska använda bilden tills det att bilden är inladdad
        tracker.addImage(image2, 0);  
        try {  
            tracker.waitForAll();
        }catch (InterruptedException e) {
            System.out.println("fel vid inladdning av bild");
        }
        JPanel panel5 = new JPanel(){
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                g.drawImage(image2, 0, 0, 330, 210, Color.white, null);
            }
        };
        jScrollPane5.setViewportView(panel5);        
    }
    
    
//räknar ut samtliga näringsämnen från får planerade måltid
//och flyttar över dessa till verkligt intag i RDI tabellen    
    private void atButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_atButtonActionPerformed
        try{
            double d1;
            double d2;
            double svar=0;
            String s;
            for(int j=0; j<s2.length; j++){
                for(int i=0; i < linkedList2.size(); i++){
                    Livsmedel tmpLivsmedel = (Livsmedel) linkedList2.get(i);
//här behövs det en algoritm som hittar k. k är raden i initKylskapsdata där 
//livsmedlet jag just nu räknar på befiner sig. Detta måste jag veta då jag 
//vill ta fram mängden av ämne X som detta livsmedlet innehåller.
//Ej helt idealisk lösning men för att byta ut denna skulle get/set metoder
//användas istället vilket skulle kräva ca 500rader kod. Därav tilllåts
//bristen att en planerad måltid ej kan beräknas om dess livsmedel
//ej finns kvar i kylskåpet
                    int k2=0;
                    for(int k=0; k<initKylskapdata.length; k++){
                        String a = (String) initKylskapdata[k][0];
                        String b = tmpLivsmedel.getLivsM();
                        if(a.equals(b)){
                            k2=k;
                            break;
                        }
                    }
                    s = (String) initKylskapdata[k2][2+j];
                    d1 = Double.parseDouble(s.trim());
                    s = (String) initMaltiddata[i][2];
                    d2 = Double.parseDouble(s.trim());
//skalar ner svaret till g (gram) för att få ökad presition till optimeringen
                    svar = svar + d1*d2/100;
                }
                initRDIdata[j][1] = Double.toString(svar);
                svar = 0;
            }

//går igenom hela initRDIdata och ta bort alla mkt långa decimalutvecklingar
            String pattern = "###.###";
            double d3;
            for(int i=0; i<50; i++){
                DecimalFormat myFormatter = new DecimalFormat(pattern);
//omvandlar objekt-->string-->double
                d3 = Double.parseDouble(((String) initRDIdata[i][1]).trim());
                String output = myFormatter.format(d3);
                initRDIdata[i][1] = output;
            }
            initRDI("", 0, 0);
        }
        catch (java.lang.NullPointerException npe){
            showMsg("Du måste ange en planerad måltid och antalet gram för att"
            +"kunna visa dess näringsinehåll");
        }
        catch (java.lang.ArrayIndexOutOfBoundsException AIOOBE){
            showMsg("Du kan inte äta en måtid som du inte har i ditt kykskåp");
        }
        catch(java.lang.NumberFormatException nfe){
            showMsg("Du måste ha råkat ange ogiltliga tecken hos varorna i ditt"
            +"kylskåp eller måltid, ta bort dessa");
        }
    }//GEN-LAST:event_atButtonActionPerformed

//Flyttar den optimerade måltiden till planerad kolumn
    private void flyttaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_flyttaButtonActionPerformed
        initMaltid(null, false, true, false);
    }//GEN-LAST:event_flyttaButtonActionPerformed

    
    
    private void optimeraButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optimeraButtonActionPerformed
    try{
        double[][] mat = new double[4][linkedList2.size()+1];
        int col = 0;
        for (int i=0; i<linkedList2.size(); i++ ){
            Livsmedel tmpLivsmedel = (Livsmedel) linkedList2.get(i);
            col++; //skapar en frirad för målfunktionen
            mat[col++][i] = tmpLivsmedel.getKolhydrater();
            mat[col++][i] = tmpLivsmedel.getProtein();
            mat[col++][i] = tmpLivsmedel.getFett();
            col = 0;
        }
        int[] tkn = {2, 2, 2}; //visar tecknet för matrisen, 1="<=", 2="=", 3=">="

//BERÄKNAR MINIMERINGS FUNKTION
    double tmp =0;
        for(int i=0; i<mat[0].length-1; i++){
            for (int j=0; j<mat.length; j++){
                tmp = tmp + mat[j][i];
            }
            mat[0][i] = 1;// experimentation med ändrad minimeringsfunktion
//            mat[0][i] = tmp;
            tmp = 0;
        }
//LÄGGER IN ÖNSKAT RDI
        mat[1][mat[0].length-1] = 100 * Double.parseDouble(initRDIdata[5][2].trim()); //kolhyd
        mat[2][mat[0].length-1] = 100 * Double.parseDouble(initRDIdata[3][2].trim()); //prot
        mat[3][mat[0].length-1] = 100 * Double.parseDouble(initRDIdata[4][2].trim()); //fett

        int[] basLosning;

//anropar förfasen
        PreFas preFas = new PreFas();
        preFas.inputPreFas(mat, tkn);
        mat = preFas.getMat();
//anropar fas1
        Fas1 fas1 = new Fas1();
        fas1.inputFas1(mat);
        basLosning = fas1.getBasLosning();
        mat = fas1.getMat();
//om vi hittar en baslösning anropas fas2
        if(fas1.hittatBadLosning() == true){
            Fas2 fas2 = new Fas2();
            fas2.inputFas2(mat, basLosning);
            basLosning = fas2.getOptimalBasLosning();
            mat = fas2.getMat();
        }
//om vi inte hittar en baslösning anropas Fas1A som angriper problemet med
//artificiella variabler        
        else{
            Fas1A fas1a = new Fas1A();
            fas1a.inputFas1a(mat, basLosning);
            basLosning = fas1a.getBasLosning();
            mat = fas1a.getMat();
        }

        System.out.println("JAg är nu i optimerabutton och skriver här ut optimallösningen");
        for(int i=0; i<basLosning.length; i++){
            System.out.print(basLosning[i] +" ");
        }
//initierar måltiden med den optimala måltiden
        initMaltid(basLosning, true, false, false);
    }
    catch (java.lang.NullPointerException npe){
        showMsg("du måste ange ditt RDI för kolhydrater, protein och fett "
        +"samt bör ange ett par livsmedel i den planerade måltid innan du kan "
        +"optimera");
    }

    catch (java.lang.NumberFormatException nfe){
        showMsg("Optimera funktionen kan inte användas om något av kolhydrater,"
        +"protein, fett har falts att inte bavakas");
    }

    
//avslutar program    
    }//GEN-LAST:event_optimeraButtonActionPerformed
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        System.exit(0);
    }//GEN-LAST:event_exitForm

//main metod    
    public static void main(String args[]) {
        new MyJFrame().show();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu Arkiv;
    private javax.swing.JMenuItem Avsluta;
    private javax.swing.JMenuItem Help;
    private javax.swing.JMenu Help1;
    private javax.swing.JMenuItem Open;
    private javax.swing.JMenuItem Save;
    private javax.swing.JMenuItem SaveAs;
    private javax.swing.JButton atButton;
    private javax.swing.JButton flyttaButton;
    private javax.swing.JButton grafikButton;
    private javax.swing.JEditorPane jEditor;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JButton optimeraButton;
    private javax.swing.JButton taBortButton;
    // End of variables declaration//GEN-END:variables

//initierar trädet
    private void initMyTree(){
        root = new DefaultMutableTreeNode("Livsmedelsverkets Databas");
        aTree = new JTree(root);
        parent = root;
        child = new DefaultMutableTreeNode("Grönsaker");
        parent.add(child);
        for(int i=0; i<140; i++){
            child.add(new DefaultMutableTreeNode(livsmedel[i].getLivsM()));
        }
        
        child = new DefaultMutableTreeNode("Frukt och bär");
        parent.add(child);
        for(int i=140; i<256; i++){
            child.add(new DefaultMutableTreeNode(livsmedel[i].getLivsM()));
        }
        
        child = new DefaultMutableTreeNode("Rotfrukter och potatis");
        parent.add(child);
        for(int i=256; i<284; i++){
            child.add(new DefaultMutableTreeNode(livsmedel[i].getLivsM()));
        }

        child = new DefaultMutableTreeNode("Glass, mjölk, ost och ägg");
        parent.add(child);
        for(int i=284; i<400; i++){
            child.add(new DefaultMutableTreeNode(livsmedel[i].getLivsM()));
        }

        child = new DefaultMutableTreeNode("Fisk, skaldjur, fågel, kött och chark");
        parent.add(child);
        for(int i=400; i<706; i++){
            child.add(new DefaultMutableTreeNode(livsmedel[i].getLivsM()));
        }
        
        child = new DefaultMutableTreeNode("Bröd, kaffebröd, mjöl, flingor och pasta");
        parent.add(child);
        for(int i=706; i<915; i++){
            child.add(new DefaultMutableTreeNode(livsmedel[i].getLivsM()));
        }
        
        child = new DefaultMutableTreeNode("Matfett och olja");
        parent.add(child);
        for(int i=915; i<959; i++){
            child.add(new DefaultMutableTreeNode(livsmedel[i].getLivsM()));
        }
        
        child = new DefaultMutableTreeNode("Drycker, läsk, godis, sötsaker");
        parent.add(child);
        for(int i=959; i<1114; i++){
            child.add(new DefaultMutableTreeNode(livsmedel[i].getLivsM()));
        }
        
        child = new DefaultMutableTreeNode("Alkohol relaterade drycker");
        parent.add(child);
        for(int i=1114; i<1157; i++){
            child.add(new DefaultMutableTreeNode(livsmedel[i].getLivsM()));
        }
        
        aTree.putClientProperty("JTree.lineStyle", "Angled"); //snyggar till trädet
        jScrollPane1.setViewportView(aTree);
        aTree.expandRow(0); //expanderar första raden
        aTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        aTree.addTreeSelectionListener(new TreeSelectionListener(){
            public void valueChanged(TreeSelectionEvent e){
                DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                aTree.getLastSelectedPathComponent();
                if (node == null){
                    return;
                }
                Object nodeInfo = node.getUserObject();
                String p = (String) nodeInfo;
                for(int r=0; r<=1969; r++){
                    if(livsmedel[r].getLivsM() == p){
//kollar fall livsmedlet redan finns med i kylskåpet, i så fall medelas detta och inget görs
                        boolean finnsRedan=false;
                        for(int s=0; s<linkedList.size(); s++){
                            if(linkedList.get(s) == livsmedel[r]){
                                showMsg("livsmedlet finns redan tilllagt");
                                finnsRedan = true;
                                break;
                            }
                            finnsRedan = false;
                        }
                        if(finnsRedan == false){
                            linkedList.add(livsmedel[r]);
                            initKylskap();
                            break;
                        }
                    }
                }
            }
        });
    }
    
    
//initierar kylskåpet
    void initKylskap(){
        initKylskapdata = new Object[linkedList.size()][52];
        int col = 0;
        for(int i=0; i < linkedList.size(); i++){
            Livsmedel tmpLivsmedel = (Livsmedel) linkedList.get(i);
            initKylskapdata[i][col++] = tmpLivsmedel.getLivsM();
            initKylskapdata[i][col++] = Double.toString(tmpLivsmedel.getAvfall());
            initKylskapdata[i][col++] = Double.toString(tmpLivsmedel.getEnergiJ());
            initKylskapdata[i][col++] = Double.toString(tmpLivsmedel.getEnergiK());
            initKylskapdata[i][col++] = Double.toString(tmpLivsmedel.getVatten());
            initKylskapdata[i][col++] = Double.toString(tmpLivsmedel.getProtein());
            initKylskapdata[i][col++] = Double.toString(tmpLivsmedel.getFett());
            initKylskapdata[i][col++] = Double.toString(tmpLivsmedel.getKolhydrater());
            initKylskapdata[i][col++] = Double.toString(tmpLivsmedel.getRetinol());
            initKylskapdata[i][col++] = Double.toString(tmpLivsmedel.getKaroten());
            initKylskapdata[i][col++] = Double.toString(tmpLivsmedel.getRetinolekv());
            initKylskapdata[i][col++] = Double.toString(tmpLivsmedel.getDvitamin());
            initKylskapdata[i][col++] = Double.toString(tmpLivsmedel.getaTokoferol());
            initKylskapdata[i][col++] = Double.toString(tmpLivsmedel.getTiamin());
            initKylskapdata[i][col++] = Double.toString(tmpLivsmedel.getAskorbinsyra());
            initKylskapdata[i][col++] = Double.toString(tmpLivsmedel.getRiboflavin());
            initKylskapdata[i][col++] = Double.toString(tmpLivsmedel.getNiacin());
            initKylskapdata[i][col++] = Double.toString(tmpLivsmedel.getNiacinekv());
            initKylskapdata[i][col++] = Double.toString(tmpLivsmedel.getVitaminB6());
            initKylskapdata[i][col++] = Double.toString(tmpLivsmedel.getVitaminB12());
            initKylskapdata[i][col++] = Double.toString(tmpLivsmedel.getKalcium());
            initKylskapdata[i][col++] = Double.toString(tmpLivsmedel.getFosfor());
            initKylskapdata[i][col++] = Double.toString(tmpLivsmedel.getJärn());
            initKylskapdata[i][col++] = Double.toString(tmpLivsmedel.getMagnesium());
            initKylskapdata[i][col++] = Double.toString(tmpLivsmedel.getNatrium());
            initKylskapdata[i][col++] = Double.toString(tmpLivsmedel.getKalium());
            initKylskapdata[i][col++] = Double.toString(tmpLivsmedel.getZink());
            initKylskapdata[i][col++] = Double.toString(tmpLivsmedel.getMättadeFettsyror());
            initKylskapdata[i][col++] = Double.toString(tmpLivsmedel.getEnkelomättadeFettsyror());
            initKylskapdata[i][col++] = Double.toString(tmpLivsmedel.getFleromättadeFettsyror());
            initKylskapdata[i][col++] = Double.toString(tmpLivsmedel.getKolesterol());
            initKylskapdata[i][col++] = Double.toString(tmpLivsmedel.getMonosackarider());
            initKylskapdata[i][col++] = Double.toString(tmpLivsmedel.getDisackarider());
            initKylskapdata[i][col++] = Double.toString(tmpLivsmedel.getSackaros());
            initKylskapdata[i][col++] = Double.toString(tmpLivsmedel.getFibrer());
            initKylskapdata[i][col++] = Double.toString(tmpLivsmedel.getAlkohol());
            initKylskapdata[i][col++] = Double.toString(tmpLivsmedel.getFettsyraC4C10());
            initKylskapdata[i][col++] = Double.toString(tmpLivsmedel.getFettsyraC120());
            initKylskapdata[i][col++] = Double.toString(tmpLivsmedel.getFettsyraC140());
            initKylskapdata[i][col++] = Double.toString(tmpLivsmedel.getFettsyraC160());
            initKylskapdata[i][col++] = Double.toString(tmpLivsmedel.getFettsyraC180());
            initKylskapdata[i][col++] = Double.toString(tmpLivsmedel.getFettsyraC200());
            initKylskapdata[i][col++] = Double.toString(tmpLivsmedel.getFettsyraC161());
            initKylskapdata[i][col++] = Double.toString(tmpLivsmedel.getFettsyraC181());
            initKylskapdata[i][col++] = Double.toString(tmpLivsmedel.getFettsyraC182());
            initKylskapdata[i][col++] = Double.toString(tmpLivsmedel.getFettsyraC183());
            initKylskapdata[i][col++] = Double.toString(tmpLivsmedel.getFettsyraC204());
            initKylskapdata[i][col++] = Double.toString(tmpLivsmedel.getFettsyraC205());
            initKylskapdata[i][col++] = Double.toString(tmpLivsmedel.getFettsyraC225());
            initKylskapdata[i][col++] = Double.toString(tmpLivsmedel.getFettsyraC226());
            initKylskapdata[i][col++] = Double.toString(tmpLivsmedel.getFolat());
            initKylskapdata[i][col++] = Double.toString(tmpLivsmedel.getSelen());
            col = 0;
        }
//ny version för presentation av ämnen hos livsmedel i kylskåpet
        String[] columnNames =
    {"Livsmedel", "Avfall (%)", "Energi (kJ)", "Energi (kcal)", "Vatten (g)", 
    "Protein (g)", "Fett (g)", "Kolhydrater (g)", "Retinol (mcg)", 
    "Karoten (mcg)", "Retinolekv (mcg)", "D vitamin (mcg)", "a-Tokoferol (mg)",
    "Tiamin (mg)", "Askorbinsyra (mg)", "Riboflavin (mg)", "Niacin (mg)",
    "Niacinekv (mg)", "Vitamin B6 (mg)", "Vitamin B12 (mcg)", "Kalcium (mg)", 
    "Fosfor (mg)", "Järn (mg)", "Magnesium (mg)", "Natrium (mg)", "Kalium (mg)",
    "Zink (mg)", "Mättade fettsyror (g)", "Enkelomättade fettsyror (g)",
    "Fleromättade fettsyror (g)", "Kolesterol (mg)", "Monosackarider (g)",
    "Disackarider (g)", "Sackaros (g)", "Fibrer (g)", "Alkohol (g)",
    "Fettsyra C4-C10 (g)", "Fettsyra C12:0 (g)", "Fettsyra C14:0 (g)", 
    "Fettsyra C16:0 (g)", "Fettsyra C18:0 (g)", "Fettsyra C20:0 (g)",
    "Fettsyra C16:1 (g)", "Fettsyra C18:1 (g)", "Fettsyra C18:2 (g)",
    "Fettsyra C18:3 (g)", "Fettsyra C20:4 (g)", "Fettsyra C20:5 (g)",
    "Fettsyra C22:5 (g)", "Fettsyra C22:6 (g)", "Folat (mcg)", "Selen (mcg)"};

        JTable aTable = new JTable(initKylskapdata, columnNames);
        aTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF );
        jScrollPane2.setViewportView(aTable);
        
//initierar listen selection i kylskåpet
        aTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ListSelectionModel rowSM = aTable.getSelectionModel();
        rowSM.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()){
                }
                else{
                    ListSelectionModel lsm = (ListSelectionModel)e.getSource();
                    int selectedRow = lsm.getMinSelectionIndex();
                    //System.out.println("och du klickade på" +selectedRow);
                    Livsmedel tmpLivsmedel = (Livsmedel) linkedList.get(selectedRow);
                        boolean finnsRedan=false;
                        for(int s=0; s<linkedList2.size(); s++){
                            if(linkedList2.get(s) == tmpLivsmedel){
                                //System.out.println("livsmedlet finns redan tilllagt");
                                finnsRedan = true;
                                showMsg("livsmedlet finns redan tilllagt");
                                break;
                            }
                            finnsRedan = false;
                        }
                        if(finnsRedan == false){ //om det inte finns läggs det till
                            linkedList2.add(tmpLivsmedel);
                        }                    
                    initMaltid(null, false, false, false);
                    removeLivsmedel = (Livsmedel) linkedList.get(selectedRow);
                    removeFrom = false;                    
                }
            }
        });
//ställer in kolumn storlekarna då vissa namn är längre än andra
        TableColumn column = null;
        for (int i = 0; i < 52; i++) {
            column = aTable.getColumnModel().getColumn(i);
            if (i == 0) { //livsmedelsnamn
                column.setPreferredWidth(250);
            }
            else if (i == 7){ //kolhydrater
                column.setPreferredWidth(95);
            }
            else if (i == 14){ //askorbinsyra
                column.setPreferredWidth(115);
            }
            else if (i == 19){ //vitamin b12
                column.setPreferredWidth(115);
            }
            else if (i == 23){ //magnesium
                column.setPreferredWidth(115);
            }
            else if (i == 27){ //mättat fet
                column.setPreferredWidth(135);
            }
            else if (i == 28){ //enkelmättat fet
                column.setPreferredWidth(155);
            }
            else if (i == 29){ //fleromättat fet
                column.setPreferredWidth(155);
            }
            else if (i == 31){ //monosakarider
                column.setPreferredWidth(115);
            }
            else if (i == 32){ //disakarider
                column.setPreferredWidth(115);
            }
            else if (i >= 36 && i <= 49){ //disakarider
                column.setPreferredWidth(105);
            }
            else {
                column.setPreferredWidth(100);
            }
        }
    }

//initierar samt hanterar måltids tabellen
    void initMaltid(int[] basLosning, boolean basLosningResived, boolean optimeraTillAt, boolean remove){
        if(linkedList2.size() != initMaltiddata.length){
            if(remove == false){//ett element skall läggas till, modifierad spara sifferdata algoritm körs
                Object[] tmp = {"0"};
                if(linkedList2.size() == 0){
                    tmp = new Object[0][4];
                }
                else{
                    tmp = new Object[initMaltiddata.length];
                    for(int i=0; i<initMaltiddata.length; i++){
                        tmp[i] = initMaltiddata[i][2];
                        //System.out.println(tmp[i]);
                    }
                    initMaltiddata = new Object[linkedList2.size()][4];
                    for(int i=0; i<tmp.length; i++){
                        initMaltiddata[i][2] = tmp[i];
                    }
                }
            }
            
            if(remove == true){//ett element har tagits bort, modifierad spara siferdata algoritm körs
                Object[] tmp = {"0"};
                if(linkedList2.size() == 0){
                    tmp = new Object[0][4];
                }
                else{
                    tmp = new Object[initMaltiddata.length];
                    int j=0;
                    for(int i=0; i<linkedList2.size(); i++){
                        Livsmedel tmpLivsmedel = (Livsmedel) linkedList2.get(i);
                        String a = tmpLivsmedel.getLivsM();
                        String b = (String) initMaltiddata[j][0];
                        if(a.equals(b)){//stoppar tillbacks allt utom det borttagna elementet
                            //System.out.println("we have a match");
                            tmp[i] = initMaltiddata[j][2];
                            j++;
                        }
                        else{
                            //System.out.println("We dont have a match");
                            j++;
                            tmp[i] = initMaltiddata[j][2];
                            j++;
                        }
                    }
                }
                initMaltiddata = new Object[linkedList2.size()][4];
                for(int i=0; i<linkedList2.size(); i++){
                    initMaltiddata[i][2] = tmp[i];
                }
            }
        }

        for(int i=0; i < linkedList2.size(); i++){
            Livsmedel tmpLivsmedel = (Livsmedel) linkedList2.get(i);
            initMaltiddata[i][0] = tmpLivsmedel.getLivsM();
        }
        if(basLosningResived == true){
            for(int i=0; i<basLosning.length; i++){
                initMaltiddata[i][1] = Integer.toString(basLosning[i]);
            }
        }
        if(optimeraTillAt == true){
            for(int i=0; i<linkedList2.size(); i++){
                initMaltiddata[i][2] = initMaltiddata[i][1];
            }
        }
        String[] columnNames = {"Livsmedel", "Optimalt", "Planerat"};
        JTable MALTIDTable = new JTable(initMaltiddata, columnNames);
        //fixar kolumn storleken
/*        MALTIDTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF );
        TableColumn column1 = null;
        for (int i = 0; i < 3; i++) {
            column1 = MALTIDTable.getColumnModel().getColumn(i);
            if (i == 0) {
                column1.setPreferredWidth(117);
            }
            if (i == 1) {
                column1.setPreferredWidth(75);
            }
            if (i == 2) {
                column1.setPreferredWidth(70);
            }
        }*/
//hanterar selection i maltidtabellen
        MALTIDTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ListSelectionModel rowSM = MALTIDTable.getSelectionModel();
        rowSM.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()){
                    return;
                }
                else{
                    ListSelectionModel lsm = (ListSelectionModel)e.getSource();
                    int selectedRow = lsm.getMinSelectionIndex();
                    //System.out.println("och du klickade på" +selectedRow);
                    removeLivsmedel = (Livsmedel) linkedList2.get(selectedRow);
                    removeFrom = true;
                }
            }
        });
        jScrollPane5.setViewportView(MALTIDTable);
    }

    
    
//initierar och hanterar RDI tabellen    
    void initRDI(String str, int row, int column){
        if(row != 0 && column != 0){
            System.out.println("tog emot något i initRDI från initMaltiddata som inte var noll");
            initRDIdata[row][column] = str;
        }
        for(int i=0; i < 50; i++){
            initRDIdata[i][0] = s2[i];
        }
        String[] columnNames = {"Näringsämne", "Verkligt intag", "Önskat intag"};
        
//byter alla null mot "-" vilket ger ett mer tilltalade grafiskt utryck
        for(int i=0; i< initRDIdata.length; i++){
            for(int j=1; j <= 2; j++){
                String s3 = (String) initRDIdata[i][j];
                String s = "null";
                String s2 = "";
                if(s3 == null || s3.equals(s2) || s3 == "" || s3.equals(s)){
                    initRDIdata[i][j] = "-";
                }
            }
        }
        
        JTable RDITable = new JTable(initRDIdata, columnNames);
        jScrollPane4.setViewportView(RDITable);

        //fixar storlek på kategorier i tabellen
        RDITable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF );
        TableColumn column1 = null;
        for (int i = 0; i < 3; i++) {
            column1 = RDITable.getColumnModel().getColumn(i);
            if (i == 0) {
                column1.setPreferredWidth(152);
            }
            if (i == 1) {
                column1.setPreferredWidth(80);
            }
            if (i == 2) {
                column1.setPreferredWidth(80);
            }
        }
        RDITable.getModel().addTableModelListener(this);
    }

//läser in de data användaren anger när denna fyller i sitt RDI    
    public void tableChanged(TableModelEvent e) {
        int row = e.getFirstRow();
        int column = e.getColumn();
        TableModel model = (TableModel) e.getSource();
        String columnName = model.getColumnName(column);
        String tmpData = (String) model.getValueAt(row, column);
        System.out.println("tableChanged körs");
        initRDI(tmpData, row, column);
        //initMaltiddata[2][row] = tmpData;
    }


    void printMat(Object[][] mat){ //SKRIVER UT MATRIS, till felsökning
        System.out.println();
        for(int q=0; q<mat.length; q++){
            for(int r=0; r<mat[0].length; r++){
                System.out.print(" " + mat[q][r]);
            }
            System.out.println();
        }
    }
    
//Läser in och visar bakgrundsbild
    void initBackgroundImage(){
        Toolkit tk = Toolkit.getDefaultToolkit();
        final Image image = tk.createImage("c:/kost/de.jpg");
//håller tråden som ska använda bilden tills det att bilden är inladdad
        MediaTracker tracker = new MediaTracker(this);
        tracker.addImage(image, 0);  
        try {  
            tracker.waitForAll();
        } catch (InterruptedException e){
            System.out.println("fel vid inladdning av bild");
        }  
        JPanel panel4 = new JPanel(){
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                g.drawImage(image, 0, 0, 1024, 768, Color.white, null);
            }
        };
        getContentPane().add(panel4);
        panel4.setBounds(0, 0, 1024,  768);
        panel4.setOpaque( false );        
    }

//visar popup ruta där denna visar mottagen text    
    void showMsg(String msg){
        JOptionPane.showMessageDialog(null, msg, "Information", JOptionPane.INFORMATION_MESSAGE);
    }
        
}//avslutar klass





//testdata som används till att testköra simplexmetoden med
/*        float[][] mat =  {{  30,   20,    0   },//grund exempel max
                          {   2,    1,    100 },
                          {   1,    1,    80  },
                          {   1,    0,    40  }};
          int[] tkn = {1, 1, 1};
*/          

/*        float[][] mat =  {{   5,    2,    1,    0   },//uppgift 4.5 max
                          {   4,    2,    1,    8   },
                          {   3,    2,    2,    10  },
                          {   1,    1,    1,    4   }};
        int[] tkn = {1, 1, 1};
*/
/*        float[][] mat =  {{   2,    1,    1,    0   },//exempel med A variabler min
                          {   2,   -1,   -1,    1   },
                          {   2,    1,    0,    5   },
                          {   0,   -1,    1,    1   }};
        int[] tkn = {3, 1, 2}; //visar likhetsvilkoret för matrisen, 1="<=", 2="=", 3=">="
*/

/*        float[][] mat =  {{ -12,   -6,  -10,    0   },//exemple från tavlan
                          {  10,    3,    2,  200   },
                          {   2,    3,    8,  100   }};
        int[] tkn = {2, 2}; //visar likhetsvilkoret för matrisen, 1="<=", 2="=", 3=">="
*/
/*        double[][] mat =  {{ -22,  -17,  -32,    0   },//test1
                          {    4,   60,  0.9,  400   },
                          {    3,    3,   24,  300   },
                          {   15,    8,    8,  200   }};
        int[] tkn = {2, 2, 2}; //visar likhetsvilkoret för matrisen, 1="<=", 2="=", 3=">="
*/
