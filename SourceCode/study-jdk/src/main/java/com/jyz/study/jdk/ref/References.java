package com.jyz.study.jdk.ref;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

public class References {

	 public static void main(String[] args) {  
		     Object weakObj, phantomObj;  
		     weakObj    = new String("Weak Reference");  
		     phantomObj = new String("Phantom Reference");  
		     
		     Reference ref;  
		     
		     ReferenceQueue weakQueue, phantomQueue;  
		     weakQueue    = new ReferenceQueue();  
		     phantomQueue = new ReferenceQueue();  
		     
		     
		     WeakReference weakRef;  
		     weakRef    = new WeakReference(weakObj, weakQueue);  
		     
		     PhantomReference phantomRef;  
		     phantomRef = new PhantomReference(phantomObj, phantomQueue);  
		  
		     // Print referents to prove they exist.  Phantom referents  
		     // are inaccessible so we should see a null value.  
		     System.out.println("Weak Reference: " + weakRef.get());  
		     System.out.println("Phantom Reference: " + phantomRef.get());  
		  
		     // Clear all strong references  
		     weakObj    = null;  
		     phantomObj = null;  
		  
		     // Invoke garbage collector in hopes that references  
		     // will be queued  
		     System.gc();  
		  
		     // See if the garbage collector has queued the references  
		     System.out.println("Weak Queued: " + weakRef.isEnqueued());  
		     // Try to finalize the phantom references if not already  
		     if(!phantomRef.isEnqueued()) {  
		       System.out.println("Requestion finalization.");  
		       System.runFinalization();  
		     }  
		     System.out.println("Phantom Queued: " + phantomRef.isEnqueued());  
		  
		     // Wait until the weak reference is on the queue and remove it  
		     try {  
		       ref = weakQueue.remove();  
		       // The referent should be null  
		       System.out.println("Weak Reference: " + ref.get());  
		       // Wait until the phantom reference is on the queue and remove it  
		       ref = phantomQueue.remove();  
		       System.out.println("Phantom Reference: " + ref.get());  
		       // We have to clear the phantom referent even though  
		       // get() returns null  
		       ref.clear();  
		     } catch(InterruptedException e) {  
		       e.printStackTrace();  
		       return;  
		     }  
		     
		   } 
	 
}
