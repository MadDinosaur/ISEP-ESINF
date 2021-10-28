/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PQExamples;

import Priority_queue.Entry;
import Priority_queue.HeapPriorityQueue;

public class PrintQueue {
    
    //------------ Static nested Document class ------------    
    public static class Document {
        private final Integer id;
        private final Integer npages;
        
        public Document (Integer i, Integer np) {id=i; npages=np;}
        
        public Integer getId() {return id;}        
        Integer getNpages() {return npages;}
        
        @Override
        public boolean equals(Object otherObj) {
            if (this == otherObj) return true;            
            if (otherObj == null || this.getClass() != otherObj.getClass()) return false;

            Document otherDoc = (Document) otherObj;        
            return this.id.equals(otherDoc.id);
        }        
    }
    //------------ end of Static nested Room class ------------

    private final HeapPriorityQueue<Integer,Document> prn;  

    public PrintQueue() {
        prn = new HeapPriorityQueue<>();
    }

    /*
    * add a Document to the printing queue
    */
    public boolean addDoc2Queue(Integer p, Document doc) {
     throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /*
    * send a Document to printer, removing it from the queue
    */
    public Entry<Integer,Document> send2Printer()  {
        throw new UnsupportedOperationException("Not supported yet.");
    } 
    
    /*
    * returns the next Document in line to be printed
    */
    public Document nextDoc2Print()  {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /*
    * returns the estimated time before the printing of a specific document starts,
    * considering that the printer takes in average 2 seconds to print each page
    */
    public Double time2print(Document doc, Double timeslot) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}