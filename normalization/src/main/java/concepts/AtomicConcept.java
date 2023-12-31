package concepts;


import java.util.*;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.semanticweb.owlapi.model.OWLClass;



public class AtomicConcept extends ConceptExpression implements Comparable<AtomicConcept> {

	private static int definer_index = 1;

	public AtomicConcept() {
		super();
	}

	public boolean neg = false;

    public static void definer_indexInit(){
		definer_index = 0;
	}
	public AtomicConcept(String str) {
		super(str);
		this.c_sig = new LinkedHashSet<>();
		this.r_sig = new LinkedHashSet<>();
		this.neg = false;
		this.set_c_sig(this);
	}

	public void setNeg(){
    	this.neg = true;
	}
	public static void main(String[] args) {	
		AtomicConcept ac = new AtomicConcept("A");
		System.out.println("ac.c_sig = " + ac.c_sig);
		System.out.println("ac.r_sig = " + ac.r_sig);
		
	}

//	public String getName(){
//    	if(this.getText().contains("\u00AC")) return this.getText().substring(1);
//    	return this.getText();
//	}

	@Override
	public String toString() {
		return this.getText();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(this.getText()).toHashCode();
	}

	@Override
	public int compareTo(AtomicConcept concept) {
		int i = this.getText().compareTo(concept.getText());
		return i;
	}

	public static int getDefiner_index() {
		return definer_index;
	}

	public static void setDefiner_index(int definer_index) {
		AtomicConcept.definer_index = definer_index;
	}

}
