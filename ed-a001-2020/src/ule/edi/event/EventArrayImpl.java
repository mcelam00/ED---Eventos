package ule.edi.event;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import ule.edi.model.*;
import ule.edi.model.Configuration.Type;


public class EventArrayImpl implements Event { //Esta es como si dijeramos la clase evento, porque evento per se es una interfaz con métodos sin implementar. Han de implementarse aqui porque "implements la interface"
	
	private String name;
	private Date eventDate;
	private int nSeats;
	
	private Double price;    // precio de entradas 
	private Byte discountAdvanceSale;   // descuento en venta anticipada (0..100)
   	
	private Seat[] seats;
		
	
	
   public EventArrayImpl(String name, Date date, int nSeats){
	 // utiliza los precios por defecto: DEFAULT_PRICE y DEFAULT_DISCOUNT definidos en Configuration.java   
	 // Debe crear el array de butacas
	   
	   //Se asignan los parámetros a los atributos
	   this.name = name;
	   this.eventDate = date;
	   this.nSeats = nSeats;
	   
	   //Se utilizan los valores por defecto como se indica 
	   this.price = Configuration.DEFAULT_PRICE;
	   this.discountAdvanceSale = Configuration.DEFAULT_DISCOUNT;

	   //Crea el array de Butacas
	   seats = new Seat[nSeats];
		   
   }
   
   
   public EventArrayImpl(String name, Date date, int nSeats, Double price, Byte discount){
	   //Constructor igual que el anterior pero este permite el paso de descuento y precio establecido 
	   // Debe crear el array de butacas
	  
	   //Se asignan los parámetros a los atributos
	   this.name = name;
	   this.eventDate = date;
	   this.nSeats = nSeats;
	   
	   //Se utilizan los valores por defecto como se indica 
	   this.price = price;
	   this.discountAdvanceSale = discount;

	   //Crea el array de Butacas
	   seats = new Seat[nSeats];
	   
	   
   }


@Override
public String getName() {
	
	return this.name;
}


@Override
public Date getDateEvent() {
	
	return this.eventDate;	
}


@Override
public Double getPrice() {

	return this.price;
}


@Override
public Byte getDiscountAdvanceSale() {
	
	return this.discountAdvanceSale;
}


@Override
public int getNumberOfSoldSeats() {
	//Calcula el numero de asientos totales vendidos del evento
	
	int numSoldSeats = 0;
	
	for(int i = 0; i < this.nSeats; i++) {
		
		if(this.seats[i] != null) {
			
			numSoldSeats++;
			
		}
		
	}
	
	return numSoldSeats;
	
}


@Override
public int getNumberOfNormalSaleSeats() {

	int numNormalSaleSeats = 0;
	
	for(int i = 0; i < this.nSeats; i++) {
		
		if(this.seats[i] != null) { //si esta ocupado voy a cotillear que tipo de venta tuvo
			
			Type normalSaleType = Configuration.Type.NORMAL;
			
			if(seats[i].getType().equals(normalSaleType)) {
				
				numNormalSaleSeats++;
			}
			
		}
		
	}
	
	return numNormalSaleSeats;
	
	
}


@Override
public int getNumberOfAdvanceSaleSeats() {

	int numAdvanceSaleSeats = 0;
	
	for(int i = 0; i < this.nSeats; i++) {
		
		if(this.seats[i] != null) { //si esta ocupado voy a cotillear que tipo de venta tuvo
			
			Type AdvanceSaleType = Configuration.Type.ADVANCE_SALE;
			
			if(seats[i].getType().equals(AdvanceSaleType)) {
				
				numAdvanceSaleSeats++;
			}
			
		}
		
	}
	
	return numAdvanceSaleSeats;
}


@Override
public int getNumberOfSeats() {
	
	return this.nSeats;
	
}


@Override
public int getNumberOfAvailableSeats() {
	
int numAvailableSeats = 0;
	
	for(int i = 0; i < this.nSeats; i++) {
		
		if(this.seats[i] == null) {
			
			numAvailableSeats++;
			
		}
		
	}
	
	return numAvailableSeats;
	
	
}


@Override
public Seat getSeat(int pos) {

	Seat seatRequested;
	
	if(seats[pos-1] != null) { //esta ocupada
	
		seatRequested = seats[pos-1];
	
	}else { //esta libre
	
		seatRequested = null;
		
	}
	
	return seatRequested;
	
}


@Override
public Person refundSeat(int pos) {
	
	Person holder;
	
	if(seats[pos-1] != null) { //esta ocupada
		
		holder = seats[pos-1].getHolder();  //retorno al ocupante
		
		seats[pos-1] = null; //se libera
		
	}else { //esta libre
		
		holder = null;
		
	}
	
	return holder;
	
}


@Override
public boolean sellSeat(int pos, Person p, boolean advanceSale) {

	boolean transactionComplete = false;
	
	//check de los parametros de entrada
	
	if(pos-1 < 0 & pos-1 >= nSeats) { //out of bounds
		
		System.out.println("No es posible acceder a esa butaca, excede el aforo del evento");
	
	}else if(seats[pos-1] == null) { //butaca libre
		
		
		transactionComplete = true;
		
		//se vende
		
		if(advanceSale == false) {
			
			Type type = Configuration.Type.NORMAL; //es como si fuera una clase el enum
			
			Seat seatToSell = new Seat(this, pos, type, p);
			
			seats[pos-1] = seatToSell;
			
			
		}else if(advanceSale == true){
			
			Type type = Configuration.Type.ADVANCE_SALE;
			
			Seat seatToSell = new Seat(this, pos, type, p);
			
			seats[pos-1] = seatToSell;
			
		}
			
		
	}
	
	return transactionComplete;
	
}


@Override
public int getNumberOfAttendingChildren() {

	int numOfChilds = 0;
	
	for(int i = 0; i < nSeats; i++) {
		
		if((seats[i] != null) & (seats[i].getHolder().getAge() < Configuration.CHILDREN_EXMAX_AGE)) {  //esta ocupada y por un niño
			
			numOfChilds++;
			
		}
				
	}
	
	return numOfChilds;
	
}


@Override
public int getNumberOfAttendingAdults() {

	int numOfAdults = 0;
	
	for(int i = 0; i < nSeats; i++) {
		
		if((seats[i] != null)) {  //esta ocupada 
			
			if((seats[i].getHolder().getAge() >= Configuration.CHILDREN_EXMAX_AGE) & (seats[i].getHolder().getAge() < Configuration.ELDERLY_PERSON_INMIN_AGE)) {
			
				numOfAdults++;
				
			}
									
		}
				
	}
	
	return numOfAdults;	
	
}


@Override
public int getNumberOfAttendingElderlyPeople() {
	
	int numOfElderlys = 0;
	
	for(int i = 0; i < nSeats; i++) {
		
		if((seats[i] != null)) {  //esta ocupada 
			
			if((seats[i].getHolder().getAge() >= Configuration.ELDERLY_PERSON_INMIN_AGE) & (seats[i].getHolder().getAge() < Integer.MAX_VALUE)) {
			
				numOfElderlys++;
				
			}
									
		}
				
	}
	
	return numOfElderlys;	
}


@Override
public List<Integer> getAvailableSeatsList() {
	
	ArrayList<Integer> availableSeats = new ArrayList<Integer>();
	
	for(int i = 0; i < nSeats; i++) {
		
		if(seats[i] == null) {
			
			availableSeats.add(i+1);
			
		}
		
	}
	
	return availableSeats;
	
}


@Override
public List<Integer> getAdvanceSaleSeatsList() {

	ArrayList<Integer> advanceSaleSeats = new ArrayList<Integer>();
	Type type = Configuration.Type.ADVANCE_SALE;
	
	for(int i = 0; i < nSeats; i++) {
		
		if((seats[i] != null) & (seats[i].getType().equals(type))) {
			
			advanceSaleSeats.add(i+1);
			
		}
		
	}
	
	return advanceSaleSeats;	

}


@Override
public int getMaxNumberConsecutiveSeats() {
	
	int maximunStored = 0;
	int currentMaximun = 0;
	
	for(int i = 0; i < nSeats; i++) {
	
		if(seats[i] != null || i == nSeats-1) { //si esta ocupada, se resetea el parcial. Si es al ultima iteracion quiero que compruebe tambien porque si llevo 6 al final y tengo 4 se pierde porque la ultima posicion del array es vacante y no compararia el 6 con el 4 almacenado
			
			//antes de resetear el parcial se compara con el almacenado a ver si hemos conseguido una cifra mayor de vacantes consecutivos
			if(currentMaximun > maximunStored) {
				
				maximunStored = currentMaximun;
			
			}
					
			currentMaximun = 0;
			
		}else {
		
			currentMaximun++;
		}
		
		
	}
	
	
	if(currentMaximun == nSeats) { //si todo el array esta vacante se retorna el tamaño
	
		maximunStored = nSeats;
		
	}
	
	
	return maximunStored;
	
	
}


@Override
public Double getPrice(Seat seat) {
	
	Double seatPrice = 0.0;
	
	Type advanceSale = Configuration.Type.ADVANCE_SALE; 
	Type normalSale = Configuration.Type.NORMAL; 
	
	
	if(seat.getType().equals(advanceSale)) {
		
		Byte discount = this.discountAdvanceSale; //por defecto o si se le ha pasado uno al constructor, ese
		
		float partsPerUnit = 1 - (discount/100); //en tanto por uno (25% descuento equivale a 0.75)
		
		seatPrice = this.price*partsPerUnit;
		
		
	}else if(seat.getType().equals(normalSale)) {
		
		seatPrice = this.price;
		
	}
	
	return seatPrice;
	
}


@Override
public Double getCollectionEvent() {
	
	Double totalCollected = 0.0;
	
	Type advanceSale = Configuration.Type.ADVANCE_SALE; 
	Type normalSale = Configuration.Type.NORMAL; 
		
	
	for(int i = 0; i < nSeats; i++) {
		
		
		if(seats[i].getType().equals(advanceSale)) {
			
			Byte discount = this.discountAdvanceSale; //por defecto o si se le ha pasado uno al constructor, ese
			
			float partsPerUnit = 1 - (discount/100); //en tanto por uno (25% descuento equivale a 0.75)
			
			totalCollected = totalCollected + (this.price*partsPerUnit);
			
			
		}else if(seats[i].getType().equals(normalSale)) {
			
			totalCollected = totalCollected + this.price;
			
		}
		
		
	}
	
	return totalCollected;
	
	
}


@Override
public int getPosPerson(Person p) {
	
	int posicion = -1;
	int i = 0;
	
	while(posicion == -1) { //mientras no se encuentre sigue buscando y si no está queda el valor por defecto que es -1
		
		if(seats[i].getHolder().equals(p)) {
			
			posicion = i+1; 
			
		}
		
		i++;
	}
	
	
	return posicion;
	
	
}


@Override
public boolean isAdvanceSale(Person p) {
	
	boolean isAdvanceSale = false;
	
	int position = this.getPosPerson(p);
	
	Type typeOfSale = Configuration.Type.ADVANCE_SALE;
	
	
	if(seats[position-1].getType().equals(typeOfSale)) {
		
		isAdvanceSale = true;
		
	}
	
	return isAdvanceSale;
	
}
   


}	