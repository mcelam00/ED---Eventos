package ule.edi.event;

import java.security.Permissions;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.*;

import ule.edi.model.*;
import ule.edi.model.Configuration.Type;


public class main {
	public static void main(String[] args) {
	    EventArrayImpl e = null;
	

	    
			e = new EventArrayImpl("The Fabulous Five", new Date(), 110);

			 Type anticipada = Configuration.Type.ADVANCE_SALE;
				Person holder = new Person("Fulanito","46317899",20);

				Seat butacaAnticipada = new Seat(e, 2, anticipada, holder);
			    
			    
				System.out.println(getPrice(butacaAnticipada));
				
	
	    
	   
		
		
		

	}
	
	public static Double getPrice(Seat seat) {
		
		Double seatPrice = 0.0;
		
		Type advanceSale = Configuration.Type.ADVANCE_SALE; 
		Type normalSale = Configuration.Type.NORMAL; 
		
		
		if(seat.getType() == advanceSale) {
			
			Byte discount = 25; //por defecto o si se le ha pasado uno al constructor, ese
						System.out.println(discount);
			double partsPerUnit = 1 - ((double)discount/100); //en tanto por uno (25% descuento equivale a 0.75)

			System.out.println(partsPerUnit);
			seatPrice = 100.0*partsPerUnit;
			
			
		}else { //si es venta normal
			
			seatPrice = 100.0;
			
		}
		
		return seatPrice;
		
	}
}
