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
	// TODO Auto-generated method stub
	return null;
}


@Override
public Person refundSeat(int pos) {
	// TODO Auto-generated method stub
	return null;
}


@Override
public boolean sellSeat(int pos, Person p, boolean advanceSale) {
	// TODO Auto-generated method stub
	return false;
}


@Override
public int getNumberOfAttendingChildren() {
	// TODO Auto-generated method stub
	return 0;
}


@Override
public int getNumberOfAttendingAdults() {
	// TODO Auto-generated method stub
	return 0;
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