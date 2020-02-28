package ule.edi.model;


public class Person {

	private String name;
	private String nif;
	private int age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Person(String name, String nif, int edad) {
        this.nif=nif;
		this.name = name;
		this.age = edad;
		
	}


	@Override
	public String toString() {
		return "{ NIF: "+ nif + "  Name : " + name + ", Age:" + age + "}";
	}
	
    @Override
	public boolean equals(Object obj) {
		// Dos personas son iguales si son iguales sus nifs
    	
		boolean flag = true; //por defecto, los supongo iguales
		
		if(obj == this) { //comparar direccines con == siempre
			
			flag = true; //si el objeto que se nos pasa no es de tipo person y no se 
		
		}else {
			
			
			if(obj instanceof Person) {
			
				Person personParam = (Person)obj;
				
				if(personParam.getNif().compareTo(this.nif) != 0) {
					//si no son iguales lexicogr�ficamente los dos nifs cambio el testigo
					
					flag = false;
					
				}
			}else {
				
				flag = false;
				
			}
		
		}
			
			
    	return flag;
	}
    
    
    
	
}
