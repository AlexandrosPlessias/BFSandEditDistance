/*
 * System Specs: 
 * Intel(R) 4-Core i5-2500K CPU @ 3.30GHz
 * 8GB RAM
 * NetBeans IDE 8.0.1 (Default Comliper)
 * Windows 10 Pro Insider Preview (Java recognizes as Windows 8.1)
 */
package acp2015.pkg;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

/*
* Implemetention of GUI with listeners (use card layout)
*/
public class MainFrame extends JFrame {

    // Used for card layout manage.
    private final static String startscreen = "startscreen";
    private final static String bfs = "bfs";
    private final static String editDistance = "editDistance";

    // Used for BFS screen/panel.
    private JButton bfsbutton = new JButton("BFS");
    private JButton filechoose = new JButton("Input File");
    private static JTextField filename = new JTextField(40);
    private CardLayout cardlayout;
    private JPanel cardpanel;
    private static JTextPane bfsinfo = new JTextPane();
    private JButton menuscreenbut = new JButton("Menu");
    
    // Used for Edit Distance screen/panel.
    // In this class "ed" mean " edit distnce".
    private JButton editDistanceButton = new JButton("Edit Distance");
    private JTextArea str1TextArea = new JTextArea(4, 30);
    private JTextArea str2TextArea = new JTextArea(4, 30);
    private JLabel edNumberLabel = new JLabel("");
    private JProgressBar similarityBar = new JProgressBar(0);
    private JButton edButton = new JButton("Edit Distance");
    private JButton menuscreenbtn2 = new JButton("Menu");

    public MainFrame() {
        super("AC Programming Exercise");
        setSize(395, 160);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        cardpanel = new JPanel(new CardLayout());
        cardlayout = (CardLayout) (cardpanel.getLayout());
        JPanel mainscreenpanel = new JPanel(new BorderLayout());
        cardpanel.add(mainscreenpanel, startscreen);
        JPanel flowpanel = new JPanel(new FlowLayout());
        mainscreenpanel.add(flowpanel, BorderLayout.NORTH);
        flowpanel.add(new JLabel("Please choose a problem :"));
        JPanel flowpanel2 = new JPanel(new FlowLayout(00, 60, 50));
        mainscreenpanel.add(flowpanel2, BorderLayout.CENTER);

        bfsbutton.setPreferredSize(editDistanceButton.getPreferredSize());

        flowpanel2.add(bfsbutton);
        flowpanel2.add(editDistanceButton);
        add(cardpanel, BorderLayout.CENTER); //cardpanel ->center
        bfsbutton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setSize(600, 650);
                cardlayout.previous(cardpanel);
                cardlayout.show(cardpanel, bfs);
                setLocationRelativeTo(null);
            }
        });
        editDistanceButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setSize(600, 650);
                cardlayout.previous(cardpanel);
                cardlayout.show(cardpanel, editDistance);
                setLocationRelativeTo(null);
            }
        });

        /**
         * ********************BFS  Screen/Panel********************
         */
        JPanel bfspanel = new JPanel(new BorderLayout());
        JPanel flowbut = new JPanel(new FlowLayout());
        bfspanel.add(flowbut, BorderLayout.SOUTH);
        flowbut.add(menuscreenbut);
        cardpanel.add(bfspanel, bfs);
        JPanel openfilepanel = new JPanel(new FlowLayout());
        bfspanel.add(openfilepanel, BorderLayout.NORTH);
        openfilepanel.add(filename);
        filename.setEditable(false);
        openfilepanel.add(filechoose);
        JScrollPane bfsinfopanel = new JScrollPane(bfsinfo, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        bfsinfo.requestFocus();
        bfsinfo.setEditable(false);
        bfspanel.add(bfsinfopanel, BorderLayout.CENTER);

        /**
         * *******************Edit Distance Screen/Panel*******************
         */ 
        // Edit distance panel for GUI.
        JPanel edpanel = new JPanel(new BorderLayout());
        
        // Basic panel for show string's labels, textAreas, 
        // "submit button" & results presentation.
        JPanel basicPanel = new JPanel(new GridLayout(5, 1, 0, 0));
        edpanel.add(basicPanel, BorderLayout.PAGE_START);
        cardpanel.add(edpanel, editDistance);

        JPanel msgPanel = new JPanel(new GridLayout(3, 1));
        JLabel msgLine1Label = new JLabel("+++ Edit Distance Calculator +++", SwingConstants.CENTER);
        JLabel msgLine2Label = new JLabel("1. Support more than 100 chars per String.", SwingConstants.CENTER);
        JLabel msgLine3Label = new JLabel("2. Uppercase and Lowercase chars treated as equally.", SwingConstants.CENTER);
        
        msgPanel.add(msgLine1Label);
        msgPanel.add(msgLine2Label);
        msgPanel.add(msgLine3Label);
        basicPanel.add(msgPanel);

        JPanel str1Panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        str1Panel.setBackground(Color.white);
        JLabel str1Label = new JLabel("String A:");

        str1TextArea.setLineWrap(true);
        str1TextArea.setWrapStyleWord(true);
        JScrollPane scrollPaneA = new JScrollPane(str1TextArea);
        scrollPaneA.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        str1Panel.add(str1Label);
        str1Panel.add(scrollPaneA);
        basicPanel.add(str1Panel);

        JPanel str2Panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        str2Panel.setBackground(Color.white);
        JLabel str2Label = new JLabel("String B:");

        str2TextArea.setLineWrap(true);
        str2TextArea.setWrapStyleWord(true);
        JScrollPane scrollPaneB = new JScrollPane(str2TextArea);
        scrollPaneB.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        str2Panel.setBackground(Color.white);
        str2Panel.add(str2Label);
        str2Panel.add(scrollPaneB);
        basicPanel.add(str2Panel);

        JPanel edButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        edButtonPanel.setBackground(Color.white);
        edButtonPanel.add(edButton);
        basicPanel.add(edButtonPanel);

        JLabel edMsgLabel = new JLabel("The Edit Distance between  String  A and B is: ");
        JPanel edMsgPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        edMsgPanel.setBackground(Color.white);
        edMsgPanel.add(edMsgLabel);
        edMsgPanel.add(edNumberLabel);
        basicPanel.add(edMsgPanel);

        JLabel similarityLabel = new JLabel("Similarity between String  A and B is:");
        JPanel similarityPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        similarityPanel.setBackground(Color.white);
        // Initialization of bar. 
        similarityBar.setValue(0);
        similarityBar.setStringPainted(true);
        similarityBar.setVisible(false);
        similarityPanel.add(similarityLabel);
        similarityPanel.add(similarityBar);
        edpanel.add(similarityPanel);

        JPanel menuButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        menuButtonPanel.add(menuscreenbtn2);
        edpanel.add(menuButtonPanel, BorderLayout.PAGE_END);

        /**
         * *******************GUI for open file*******************
         */
        
        filechoose.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int rVal;
                bfsinfo.setText("");
                filename.setHorizontalAlignment(JTextField.CENTER);
                JFileChooser openfile = new JFileChooser();
                // Demonstrate "Open" dialog:
                rVal = openfile.showOpenDialog(rootPane);
                if (rVal == JFileChooser.APPROVE_OPTION) { // if select file    

                    if (System.getProperty("os.name").equals("Linux")) { //if os is Linux
                        filename.setText((openfile.getCurrentDirectory().toString()) + File.separator + openfile.getSelectedFile().getName()); //show full path
                    } else if (System.getProperty("os.name").equals("Windows")) { //if os is Windows
                        filename.setText((openfile.getCurrentDirectory().toString()) + File.separator + openfile.getSelectedFile().getName()); //show full path
                    } else if (System.getProperty("os.name").equals("Windows 8.1")) { //if os is Windows 8.1.
                        filename.setText((openfile.getCurrentDirectory().toString()) + File.separator + openfile.getSelectedFile().getName()); //show full path
                    } else if (System.getProperty("os.name").equals("Windows 10")) { //if os is Windows 10.
                        filename.setText((openfile.getCurrentDirectory().toString()) + File.separator + openfile.getSelectedFile().getName()); //show full path
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Os not Supported , System Exit!", "Error Message", JOptionPane.ERROR_MESSAGE);
                        System.exit(0);
                    }
                    try {
                        new BFS();
                    } catch (BadLocationException eq) {
                        System.out.println(eq.toString());
                    }
                }

                if (rVal == JFileChooser.CANCEL_OPTION) { //if cancel file selection
                    filename.setText("You cancel it!");
                }
            }
        });

        filechoose.addMouseListener(new MouseListener() {
            boolean press = false;

            @Override
            public void mouseClicked(MouseEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mousePressed(MouseEvent e) {
                filechoose.setBackground(Color.yellow);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                press = true;
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                filechoose.setBackground(Color.GREEN);
                if (bfsinfo.getText() == null || bfsinfo.getText().trim().equals("")) {
                    filename.setText("");
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                filechoose.setBackground(null);
                if (press) {
                    filechoose.setBackground(Color.red);
                    press = false;
                }
            }
        });

        
        /**
         * *******************Listeners*******************
         */   
        
        // For mouseover fade effect.
        bfsbutton.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                bfsbutton.setBackground(Color.GREEN);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                bfsbutton.setBackground(null);
            }
        });
        
        // For mouseover fade effect.
        editDistanceButton.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                editDistanceButton.setBackground(Color.GREEN);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                editDistanceButton.setBackground(null);
            }
        });
      
        // For go to menu interface from bfs screen.
        menuscreenbut.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setSize(395, 160);
                cardlayout.previous(cardpanel);
                cardlayout.show(cardpanel, startscreen);
                setLocationRelativeTo(null);
                bfsinfo.setText("");
                filename.setText("");
                filechoose.setBackground(null);
                
            }
        });

        // For mouseover fade effect.
        menuscreenbut.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                menuscreenbut.setBackground(Color.GREEN);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                menuscreenbut.setBackground(null);
            }
        });
   
        // For go to menu interface from edit distance screen. 
        // Also, clear fields from old results and hide some.
        menuscreenbtn2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                str1TextArea.setText("");
                str2TextArea.setText("");
                edNumberLabel.setText("");
                similarityBar.setVisible(false);
                similarityBar.setValue(0);
                setSize(395, 160);
                cardlayout.previous(cardpanel);
                cardlayout.show(cardpanel, startscreen);
                setLocationRelativeTo(null);
                
            }
        });
      
        // For mouseover fade effect.
        menuscreenbtn2.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                menuscreenbtn2.setBackground(Color.GREEN);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                menuscreenbtn2.setBackground(null);
            }
        });

        // Check if strings textAreas are filled, create a EditDistance
        // object and show the results.
        edButton.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent me) {
                String strA = str1TextArea.getText();
                String strB = str2TextArea.getText();

                if (strA.isEmpty() || strB.isEmpty()) {

                    JOptionPane.showMessageDialog(null, "Please, try to fill both fields\n next time.", "Error Message", JOptionPane.ERROR_MESSAGE);
                    similarityBar.setVisible(false);
                    similarityBar.setValue(0);
                    edNumberLabel.setText("");

                } else {
                    EditDistance ed = new EditDistance();
                    
                    edNumberLabel.setText("");
                    edNumberLabel.setText(" "+ed.getEditDistance(strA, strB));

                    // Show and Update similarityBar's percent.
                    similarityBar.setVisible(true);
                    similarityBar.setValue((int) ed.similarityScore(strA, strB));

                }

            }

            @Override
            public void mousePressed(MouseEvent me) {
            }

            @Override
            public void mouseReleased(MouseEvent me) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                edButton.setBackground(Color.GREEN);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                edButton.setBackground(null);
            }

        });
    
    }//end con

    public static String getFileName() {
        return filename.getText();
    }

    public static StyledDocument getBfsinfo() {
        StyledDocument doc = bfsinfo.getStyledDocument();
        return doc;
    }

}
