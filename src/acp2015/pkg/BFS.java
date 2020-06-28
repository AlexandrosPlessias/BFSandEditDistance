/*
 * System Specs: 
 * Intel(R) 4-Core i5-2500K CPU @ 3.30GHz
 * 8GB RAM
 * NetBeans IDE 8.0.1 (Default Comliper)
 * Windows 10 Pro Insider Preview (Java recognizes as Windows 8.1)
 */

package acp2015.pkg;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

/*
* Problem 1.
* Implemetention of BFS algorithm with 4 sub-problems. 
*  Time Complexity is O(m+n) where n=nodes & m=edges.
*/
public class BFS {

    private static HashMap<Integer, List<Integer>> graphinfo = new HashMap<Integer, List<Integer>>(); // HashMap for graph info key (Node) -> neighbors (values)
    private static HashMap<Integer, Boolean> visited = new HashMap<Integer, Boolean>(); // node-> viseted -> true or false
    private static LinkedList<Integer> myqueue = new LinkedList<Integer>(); //queue for bfs 
    private static HashMap<Integer, Integer> parent = new HashMap<Integer, Integer>(); // node parent
    private static HashMap<Integer, Integer> nodelevel = new HashMap<Integer, Integer>(); //key node , value level
    private static int levels = 0, lastnode = 0;
    private static SimpleAttributeSet style = new SimpleAttributeSet(); //style for info
    private static SimpleAttributeSet style1 = new SimpleAttributeSet(); //style for title
    private Scanner scanner = null;

    public BFS() throws BadLocationException {

        graphinfo.clear();
        visited.clear();
        myqueue.clear();
        parent.clear();
        nodelevel.clear();
        /*for show style*/
        StyleConstants.setForeground(style, Color.black);
        StyleConstants.setBackground(style, Color.white);
        StyleConstants.setBold(style1, true);
        StyleConstants.setFontSize(style1, 13);
        StyleConstants.setItalic(style1, true);
        File file = new File(MainFrame.getFileName()); //input file name
        if (!MainFrame.getFileName().substring(Math.max(MainFrame.getFileName().length() - 2, 0)).equals("in")) {
            JOptionPane.showMessageDialog(null, "File must ending to .in  !", "Error Message", JOptionPane.ERROR_MESSAGE);
        }
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Failed to open the file!", "Error Message", JOptionPane.ERROR_MESSAGE);
        }
        try {
            int nodes = Integer.parseInt(scanner.nextLine()); // read first line (number of nodes)
            for (int i = 0; i < nodes; i++) {   // for each node(line) read the neighbors
                String line = scanner.nextLine();
                String[] patterns = line.split(" "); //split the neighbors of the node
                for (int j = 1; j < patterns.length; j++) {
                    fillmap(i + 1, Integer.parseInt(patterns[j])); // fill the map : key is node , values are the neighbors (create adjusting list )
                }
            }// end out for
            MainFrame.getBfsinfo().insertString(MainFrame.getBfsinfo().getLength(), "\t\t\tAdjusting List : \n\n", style1);
            //print adjusting list
            for (Integer key : graphinfo.keySet()) {
                MainFrame.getBfsinfo().insertString(MainFrame.getBfsinfo().getLength(), "\t\t\t" + key + " -> " + graphinfo.get(key) + "\n", style);
            }
            MainFrame.getBfsinfo().insertString(MainFrame.getBfsinfo().getLength(), "\n\t\t\tInformations About Crosses: \n", style1);
            bfsalgorith();
            printifo();
        }//end try
        catch (Exception e) { // catch exception for bad input file 
            if (MainFrame.getFileName().substring(Math.max(MainFrame.getFileName().length() - 2, 0)).equals("in")) { // correnct ending , bad format  
                JOptionPane.showMessageDialog(null, "Bad file Format !", "Error Message", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//end constructor 

    private static void fillmap(int key, int value) { // the  value of the map is a list
        List<Integer> mylist = graphinfo.get(key); // get the key of map
        visited.put(key, false);
        if (mylist == null) {
            mylist = new ArrayList<Integer>();
            graphinfo.put(key, mylist); // put the list as value of the map
        }
        mylist.add(value); //put value to list
    }

    private static void bfsalgorith() throws BadLocationException {
        myqueue.add(1); // always start from 1
        boolean levelnode = false, startlevel = false;
        Set nodesetcross = new TreeSet();//for crosess
        while (!myqueue.isEmpty()) { // while i have nodes continue 
            nodelevel.put(1, 0);  //node 1 level 0           
            int key = myqueue.remove();//dequeue
            if (myqueue.isEmpty()) {
                lastnode = key;
            }
            visited.put(key, true); //key (node) is visited
            for (int i = 0; i < graphinfo.get(key).size(); i++) { // for every neighbors
                if (!parent.containsKey(graphinfo.get(key).get(i)) && graphinfo.get(key).get(i) != 1) { // if not parent   
                    parent.put(graphinfo.get(key).get(i), key);
                } // parent (key) -> graphinfo.get(key).get(i)
                if (!visited.get(graphinfo.get(key).get(i))) { // if not visited put it in queue
                    visited.put(graphinfo.get(key).get(i), true);
                    if (!startlevel) {// level start firt time
                        if (levelnode == startlevel && nodelevel.get(parent.get(graphinfo.get(key).get(i))) == levels) { //if parent level is level level++
                            levels++;
                            levelnode = true;
                        }
                        if (nodelevel.get(parent.get(graphinfo.get(key).get(i))) != levels) { // if parent level is not = parent, make the new node parent level +1
                            nodelevel.put(graphinfo.get(key).get(i), levels); // key -> node , value->level, levelnode=true;
                        } else {
                            nodelevel.put(graphinfo.get(key).get(i), nodelevel.get(parent.get(graphinfo.get(key).get(i))) + 1);
                        }
                    } // end if startlevels
                    else {
                        if (levelnode == startlevel) {
                            levels++;
                            levelnode = true;
                        }
                    }
                    myqueue.add(graphinfo.get(key).get(i));
                } else { //if is visited  , cross edge
                    if (graphinfo.get(key).get(i) != parent.get(key)) { // if the node has different parent                     
                        if (!nodesetcross.contains(key)) {// not put double crosses
  /* horizontal cross*/ if (nodelevel.get(graphinfo.get(key).get(i)) == nodelevel.get(key)) { // if are in the same level is horizontal cross
                                MainFrame.getBfsinfo().insertString(MainFrame.getBfsinfo().getLength(), "\n\t\t\tHorizontal Cross Edge " + key + " -> " + graphinfo.get(key).get(i) + "\n", style);
                            } else {
                                /*simple cross*/ MainFrame.getBfsinfo().insertString(MainFrame.getBfsinfo().getLength(), "\n\t\t\tSimple Cross Edge : " + key + " ->  " + graphinfo.get(key).get(i) + "\n", style);

                            }
                            nodesetcross.add(new Integer(graphinfo.get(key).get(i))); // add cross to set 
                        }
                    }
                }

            }  //end for
            levelnode = false;
        }//end while          
    }//end method

    private static void printifo() throws BadLocationException {
        // nodes levels
        Map<Integer, Integer> sortedmap = SortMap(nodelevel);
        MainFrame.getBfsinfo().insertString(MainFrame.getBfsinfo().getLength(), "\n\t\t\tSum of levels : ", style1);
        MainFrame.getBfsinfo().insertString(MainFrame.getBfsinfo().getLength(), "" + levels + "\n", style);
        int curlevel = 0;
        MainFrame.getBfsinfo().insertString(MainFrame.getBfsinfo().getLength(), "\n\t\t\tNodes for each level :\n\n", style1);
        MainFrame.getBfsinfo().insertString(MainFrame.getBfsinfo().getLength(), "\t\t\tLevel 0 nodes : ", style);
        for (Map.Entry<Integer, Integer> entry : sortedmap.entrySet()) {
            if (curlevel == entry.getValue()) { //nodes in curlevel
                MainFrame.getBfsinfo().insertString(MainFrame.getBfsinfo().getLength(), entry.getKey() + "  ", style);
            } else {   // we have new level
                MainFrame.getBfsinfo().insertString(MainFrame.getBfsinfo().getLength(), "\n\t\t\tLevel  " + entry.getValue() + " nodes : ", style);
                MainFrame.getBfsinfo().insertString(MainFrame.getBfsinfo().getLength(), +entry.getKey() + "  ", style);
            }
            curlevel = entry.getValue();
        }

        ArrayList path = new ArrayList();
        path.clear();
        path.add(lastnode);
        MainFrame.getBfsinfo().insertString(MainFrame.getBfsinfo().getLength(), "\n\n\t\t\tSortest Path is : \n\n\t\t\t", style1);
        for (int i = 1; i < levels + 1; i++) {
            path.add(parent.get(path.get(i - 1)));
        }
        Collections.reverse(path);
        for (Object pathsort : path) {
            MainFrame.getBfsinfo().insertString(MainFrame.getBfsinfo().getLength(), pathsort + "  ", style);
        }
        levels = 0;
    }//end print info

    private static Map<Integer, Integer> SortMap(Map<Integer, Integer> unsortMap) {

        // Convert Map to List
        List<Map.Entry<Integer, Integer>> list
                = new LinkedList<Map.Entry<Integer, Integer>>(unsortMap.entrySet());

        // Sort list with comparator, to compare the Map values
        Collections.sort(list, new Comparator<Map.Entry<Integer, Integer>>() {
            public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }

        });

        // Convert sorted map back to a Map
        Map<Integer, Integer> sortedMap = new LinkedHashMap<Integer, Integer>();
        for (Iterator<Map.Entry<Integer, Integer>> it = list.iterator(); it.hasNext();) {
            Map.Entry<Integer, Integer> entry = it.next();
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }
}// end class
