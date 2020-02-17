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
	// TODO Auto-generated method stub
	return 0;
}


@Override
public List<Integer> getAvailableSeatsList() {
	// TODO Auto-generated method stub
	return null;
}


@Override
public List<Integer> getAdvanceSaleSeatsList() {
	// TODO Auto-generated method stub
	return null;
}


@Override
public int getMaxNumberConsecutiveSeats() {
	// TODO Auto-generated method stub
	return 0;
}


@Override
public Double getPrice(Seat seat) {
	// TODO Auto-generated method stub
	return null;
}


@Override
public Double getCollectionEvent() {
	// TODO Auto-generated method stub
	return null;
}


@Override
public int getPosPerson(Person p) {
	// TODO Auto-generated method stub
	return 0;
}


@Override
public boolean isAdvanceSale(Person p) {
	// TODO Auto-generated method stub
	return false;
}
   


}	