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

public class EventArrayImplTests {

	private DateFormat dformat = null;
	private EventArrayImpl e;
	private EventArrayImpl es;
	
	private Date parseLocalDate(String spec) throws ParseException {
        return dformat.parse(spec);
	}

	public EventArrayImplTests() {
		
		dformat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	}
	
	@Before
	public void testBefore() throws Exception{
	    e = new EventArrayImpl("The Fabulous Five", parseLocalDate("24/02/2018 17:00:00"), 110);

	}
	
	@Test
	public void testEventoVacio() throws Exception {
		
	    Assert.assertTrue(e.getNumberOfAvailableSeats()==110);
	    Assert.assertEquals(e.getNumberOfAvailableSeats(), 110);
	    Assert.assertEquals(e.getNumberOfAttendingAdults(), 0);
	}
	
	@Test
	public void testSellSeat1Adult() throws Exception{
		
			
	    Assert.assertEquals(e.getNumberOfAttendingAdults(), 0);
		Assert.assertTrue(e.sellSeat(1, new Person("10203040A","Alice", 34),false));	//venta normal
	    Assert.assertEquals(e.getNumberOfAttendingAdults(), 1);  
	    Assert.assertEquals(e.getNumberOfNormalSaleSeats(), 1);
	  
	}
	

	
	@Test
	public void testgetCollection() throws Exception{
		Event  ep = new EventArrayImpl("The Fabulous Five", parseLocalDate("24/02/2018 17:00:00"), 4);
		Assert.assertEquals(ep.sellSeat(1, new Person("1010", "AA", 10), true),true);
		Assert.assertTrue(ep.getCollectionEvent()==75);					
		
	} //EL ROJO ES POR CULPA DE EL ULTIMO ASSERT TRUE
	
	
	// TODO EL RESTO DE MÃ‰TODOS DE TESTS NECESARIOS PARA LA COMPLETA COMPROBACIÃ“N DEL BUEN FUNCIONAMIENTO DE TODO EL CÃ“DIGO IMPLEMENTADO


	@Test
	public void testEventArrayImp() throws ParseException{ //ParseException hereda de Exception, de manera que ParseException es un caso como especifico de Exception que la lanza concreatamente ese parseDate y lo sabe, entonces al saberlo lo pongo especifico, igual que si yo creo una excepcion ya se cual lanzaria, la que yo creo y entonces la pongo, que puede lanzarla. Si como en este caso puede lanzar cualquiera que no se (nullpointer, out of bounds) simplemente pongo Exception la padre, de la que heredan todos 
		
		 es = new EventArrayImpl("Marcha Radetzky", parseLocalDate("24/02/2020 20:30:00"), 1400, 70.0, (byte) 5);
		 Assert.assertTrue(es.getName().equals("Marcha Radetzky"));
		 //hacer un equals despues de cada true
		 Assert.assertTrue(es.getNumberOfSeats() == 1400);
		 Assert.assertTrue(es.getPrice() == 70.0);
		 Assert.assertTrue(es.getDiscountAdvanceSale() == 5);
		 Assert.assertEquals(e.getDateEvent(),parseLocalDate("24/02/2018 17:00:00"));
		 
	}
	
	
	
	@Test
	public void testgetNumberOfSoldSeats() throws Exception{
		Assert.assertTrue(e.getNumberOfSoldSeats() == 0);
		Assert.assertEquals(e.getNumberOfSoldSeats(), 0);
		e.sellSeat(1, new Person("12741200", "Jose", 50), false); //vendo 1 asiento	
		e.sellSeat(2, new Person("12745970", "Luis", 50), true); //vendo otro de mi evento e
		Assert.assertTrue(e.getNumberOfSoldSeats() == 2);
		Assert.assertEquals(e.getNumberOfSoldSeats(), 2);
	}
	
	
	
	@Test //si ya lo comprueba uno anterior hay que volver a hacerlo
	public void testgetNumberOfNormalSaleSeats() throws Exception{
		Assert.assertTrue(e.getNumberOfNormalSaleSeats() == 0);
		Assert.assertEquals(e.getNumberOfNormalSaleSeats(), 0);
		e.sellSeat(1, new Person("10741200", "Juan", 54), false);
		Assert.assertTrue(e.getNumberOfNormalSaleSeats() == 1);
		Assert.assertEquals(e.getNumberOfNormalSaleSeats(), 1);
		e.sellSeat(6, new Person("10831200", "Pepe", 70), true); //le cuelo uno que no es normal
		Assert.assertTrue(e.getNumberOfNormalSaleSeats() == 1);
		Assert.assertEquals(e.getNumberOfNormalSaleSeats(), 1); //compruebo que efectivamente no lo cuenta
		
	}
	
	
	
	@Test
	public void testgetNumberOfAdvanceSaleSeats() throws Exception{
		Assert.assertTrue(e.getNumberOfAdvanceSaleSeats() == 0);
		Assert.assertEquals(e.getNumberOfAdvanceSaleSeats(), 0);
		e.sellSeat(1, new Person("10741200", "Juan", 54), false);//le cuelo uno normal
		Assert.assertTrue(e.getNumberOfAdvanceSaleSeats() == 0); //compruebo que efectivamente no lo cuenta
		Assert.assertEquals(e.getNumberOfAdvanceSaleSeats(), 0);
		e.sellSeat(6, new Person("10831200", "Pepe", 70), true); 
		Assert.assertTrue(e.getNumberOfAdvanceSaleSeats() == 1);
		Assert.assertEquals(e.getNumberOfAdvanceSaleSeats(), 1); 
		
	}
	
	
	
	@Test
	public void testgetNumberOfAvailableSeats() throws Exception{
		Assert.assertTrue(e.getNumberOfAvailableSeats() == 110);
		Assert.assertEquals(e.getNumberOfAvailableSeats(), 110);
		e.sellSeat(1, new Person("10741200", "Juan", 54), false);//le cuelo uno normal
		Assert.assertTrue(e.getNumberOfAvailableSeats() == 109); //compruebo que efectivamente no lo cuenta
		Assert.assertEquals(e.getNumberOfAvailableSeats(), 109);
		
	}


	
	@Test
	public void testgetSeat() throws Exception{
		//si no esta ocupada
		Assert.assertTrue(e.getSeat(1) == null);
		Assert.assertEquals(e.getSeat(1), null);		
		
		//ocupada
		e.sellSeat(1, new Person("10741200", "Juan", 54), false);
		Assert.assertTrue(e.getSeat(1) == e.getSeat(1));
		Assert.assertEquals(e.getSeat(1), e.getSeat(1)); //SINSENTIDO	
		
		//out of bounds
		Assert.assertTrue(e.getSeat(300) == null);
		Assert.assertEquals(e.getSeat(300), null);
		Assert.assertTrue(e.getSeat(-7) == null);
		Assert.assertEquals(e.getSeat(-7), null);
	}
	
	
	
	@Test
	public void testrefundSeat() throws Exception{ //QUEDA EL OUT OF BOUNDS
		//vendo una butaca
		Person holder = new Person("10741200", "Juan", 54);
		e.sellSeat(1, holder, false);//le cuelo uno normal
		Assert.assertTrue(e.getSeat(1) != null);
		Assert.assertNotEquals(e.getSeat(1), null);		
		
		//la devuelvo
		e.refundSeat(1);		
	/*
		//pruebo el retorno de la funcion (ACCEDO A UNA POSICION QUE APUNTA A NULL)
		Assert.assertTrue(e.refundSeat(1).equals(holder));
		Assert.assertEquals(e.refundSeat(1), holder);
	*/	
		//compruebo que se ha devuelto y quedo vacia
		Assert.assertTrue(e.getSeat(1) == null);
		Assert.assertEquals(e.getSeat(1),null);
		
		//devolver una butaca vacia da null
		e.refundSeat(3);
		Assert.assertTrue(e.refundSeat(3) == null);
		Assert.assertEquals(e.refundSeat(3), null);
		
		//trato de vender una butaca out of bounds
		e.refundSeat(500);	
		e.refundSeat(-5);
					
	}
	
	
	
	@Test
	public void testsellSeat() throws Exception{ //QUEDA POR TESTAR EL OUT OF BOUNDS
		//miro que esta sin vender
		Assert.assertTrue(e.getSeat(50) == null);
		Assert.assertEquals(e.getSeat(50),null);
		 //miro uno del otro tipo
		Assert.assertTrue(e.getSeat(55) == null);
		Assert.assertEquals(e.getSeat(55),null);
		
		//los vendo
		boolean returnValue = e.sellSeat(50, new Person("13641800", "Ana", 30), true);
		
		Assert.assertTrue(returnValue == true);
		Assert.assertEquals(returnValue, true);
		
		//miro que no puedo venderlo dos veces
		returnValue = e.sellSeat(50, new Person("19641800", "Pepa", 40), true); //le actualizo el valor
		
		Assert.assertTrue(returnValue == false);
		Assert.assertEquals(returnValue, false);
		 	//vendo el tipo 2
		returnValue = e.sellSeat(55, new Person("10641800", "Luisa", 31), false); //le actualizo el valor
		
		Assert.assertTrue(returnValue == true);
		Assert.assertEquals(returnValue, true);
		
		//miro que no lo puedo vender dos veces
		returnValue = e.sellSeat(55, new Person("10841000", "Pepi", 60), false);
		
		Assert.assertTrue(returnValue == false);
		Assert.assertEquals(returnValue, false);
		
		
		//veo que estan vendidos
		Assert.assertTrue(e.getSeat(50) != null);
		Assert.assertNotEquals(e.getSeat(50), null);
		Assert.assertTrue(e.getSeat(55) != null);
		Assert.assertNotEquals(e.getSeat(55), null);
		
		//vendo un test fuera de bounds
		returnValue = e.sellSeat(500, new Person("00000001", "Maria Luisa", 23), true);
		
		Assert.assertTrue(returnValue == false);
		Assert.assertEquals(returnValue, false);
		
		returnValue = e.sellSeat(-5, new Person("00000001", "Maria Luisa", 23), true);
		
		Assert.assertTrue(returnValue == false);
		Assert.assertEquals(returnValue, false);

		
				
	}
	
	
	
	@Test //aniado un adulto y un elderly y veo que no los cuenta¿?
	public void testgetNumberOfAttendingChildren() throws Exception{
		//veo los niños que hay inicialmente que seran 0
		Assert.assertTrue(e.getNumberOfAttendingChildren() == 0);
		Assert.assertEquals(e.getNumberOfAttendingChildren(), 0);
		
		//aniado un ninio
		e.sellSeat(1, new Person("71812230", "Daniel", 3), false);
		
		//veo que hay un ninio ahora
		Assert.assertTrue(e.getNumberOfAttendingChildren() == 1);
		Assert.assertEquals(e.getNumberOfAttendingChildren(), 1);
		
		//aniado un adulto
		e.sellSeat(2, new Person("10812230", "Rocio", 58), false);

		//veo que no se le cuenta como ninio
		Assert.assertTrue(e.getNumberOfAttendingChildren() == 1);
		Assert.assertEquals(e.getNumberOfAttendingChildren(), 1);
		
		
		
	}


	
	@Test //se deben cubrir las posibilidades del && todas
	public void testgetNumberOfAttendingAdults() throws Exception{
		//veo los adultos que hay inicialmente que seran 0
		Assert.assertTrue(e.getNumberOfAttendingAdults() == 0);
		Assert.assertEquals(e.getNumberOfAttendingAdults(), 0);
		
		//aniado un adulto
		e.sellSeat(2, new Person("10812230", "Rocio", 58), false);
		
		
		//veo que hay un adulto ahora
		Assert.assertTrue(e.getNumberOfAttendingAdults() == 1);
		Assert.assertEquals(e.getNumberOfAttendingAdults(), 1);
		
		//aniado un elderly
		e.sellSeat(1, new Person("08812230", "Serafin", 88), false);
		
		//veo que no lo cuenta
		Assert.assertTrue(e.getNumberOfAttendingAdults() == 1);
		Assert.assertEquals(e.getNumberOfAttendingAdults(), 1);
		
		//aniado un child
		e.sellSeat(3, new Person("71812230", "Daniel", 3), false);

		
		//veo que no lo cuenta
		Assert.assertTrue(e.getNumberOfAttendingAdults() == 1);
		Assert.assertEquals(e.getNumberOfAttendingAdults(), 1);
		
		
	}
	
	
	
	@Test
	public void testgetNumberOfAttendingElderlyPeople() throws Exception{
		//veo los seniorines que hay inicialmente que seran 0
			Assert.assertTrue(e.getNumberOfAttendingElderlyPeople() == 0);
			Assert.assertEquals(e.getNumberOfAttendingElderlyPeople(), 0);
		
		//aniado un seniorin
			e.sellSeat(1, new Person("08812230", "Serafin", 88), false);
				
		//veo que no lo cuenta
			Assert.assertTrue(e.getNumberOfAttendingElderlyPeople() == 1);
			Assert.assertEquals(e.getNumberOfAttendingElderlyPeople(), 1);
			
		//aniado un child
			e.sellSeat(3, new Person("71812230", "Daniel", 3), false);

			
		//veo que no lo cuenta
			Assert.assertTrue(e.getNumberOfAttendingElderlyPeople() == 1);
			Assert.assertEquals(e.getNumberOfAttendingElderlyPeople(), 1);
		
		//testeo el integerMaxValue
			e.sellSeat(101, new Person("00000000", "Prueba", Integer.MAX_VALUE), false);
			Assert.assertTrue(e.getNumberOfAttendingElderlyPeople() == 1);
			Assert.assertEquals(e.getNumberOfAttendingElderlyPeople(), 1);
			
			
	}
	
	
	
	@Test
	public void testgetAvailableSeatsList() throws Exception{
		List<Integer> availableSeats, availableSeatsAfter;
		//sin aniadir sitios comprobamos que la lista esta llena (size 110)
		availableSeats = e.getAvailableSeatsList();
		Assert.assertTrue(availableSeats.size() == 110);
		Assert.assertEquals(availableSeats.size(), 110);
		
		//añadimos un sitio y entonces hay uno menos disponible, con lo cual si ocupo la butaca 50 la posicion 50 de la lista sera la 51
		e.sellSeat(50, new Person("71812230", "Daniel", 3), false);
		availableSeatsAfter = e.getAvailableSeatsList();
		Assert.assertTrue(availableSeats.get(50) == 51); 
		Assert.assertEquals(availableSeats.get(50), Integer.valueOf(51)); 
		
		 
	}
	
	
	
	@Test
	public void testgetAdvanceSaleSeatsList() throws Exception{
		List<Integer> advanceSaleSeats, advanceSaleSeatsAfter;
		//sin aniadir sitios comprobamos que la lista esta vacia (size 0)
		advanceSaleSeats = e.getAdvanceSaleSeatsList();
		Assert.assertTrue(advanceSaleSeats.size() == 0);
		Assert.assertEquals(advanceSaleSeats.size(), 0);
		
		//añadimos un sitio y entonces hay uno de este tipo, con lo cual la posicion 1 del arraylist sera la butaca con ese tipo
		e.sellSeat(50, new Person("71812230", "Daniel", 3), true);	
		advanceSaleSeatsAfter = e.getAdvanceSaleSeatsList();
		Assert.assertTrue(advanceSaleSeatsAfter.get(0) == 50);
		
		//añadimos uno del otro tipo y comprobamos que ese no se guarda en la lista -> size == 1
		e.sellSeat(1, new Person("71812230", "Daniel", 3), false);	
		advanceSaleSeatsAfter = e.getAdvanceSaleSeatsList();
		Assert.assertTrue(advanceSaleSeatsAfter.size() == 1);
		Assert.assertEquals(advanceSaleSeatsAfter.size(), 1);
		
	}
	
	
	
	@Test
	public void testgetMaxNumberConsecutiveSeats() throws Exception{
		//comprobamos que con el evento vacio, el tamaño de la lista es 110
		Assert.assertTrue(e.getMaxNumberConsecutiveSeats() == 110);
		Assert.assertEquals(e.getMaxNumberConsecutiveSeats(), 110);
		
		//añadimos 2 sitios al final y 2 en la 3 y 4 y comprobamos que quedan 104 consecutivos
		e.sellSeat(109, new Person("10836722", "Raquel", 54), true);	
		e.sellSeat(110, new Person("12756981", "Juan", 53), true);	

		e.sellSeat(3, new Person("85039711", "Diego", 33), false);	
		e.sellSeat(4, new Person("00368822", "Asun", 63), false);	

		Assert.assertTrue(e.getMaxNumberConsecutiveSeats() == 104);
		Assert.assertEquals(e.getMaxNumberConsecutiveSeats(), 104);
		
				
	}
	
	
	
	@Test
	public void testgetPrice() throws Exception{
		//creacion butaca con tipo normal
		Type normal = Configuration.Type.NORMAL;
		Person holder = new Person("Fulanito","46317899",20);
		
		Seat butacaNomal = new Seat(e, 1, normal, holder);
		
		//verificamos que el precio es el precio del evento
		Assert.assertTrue(e.getPrice(butacaNomal) == 100.0);
		Assert.assertEquals(e.getPrice(butacaNomal), 100.0, 0.001);
		
		//creacion butaca con tipo anticipado
		Type anticipada = Configuration.Type.ADVANCE_SALE;
		Seat butacaAnticipada = new Seat(e, 2, anticipada, holder);
		
		//verificamos que el precio es el del evento pero descontandole el descuento
		Assert.assertTrue(e.getPrice(butacaAnticipada) == 75.0);
		Assert.assertEquals(e.getPrice(butacaAnticipada), 75.0, 0.001); //debo indicar la tolerancia del double
	}
	
	
	
	@Test
	public void testgetCollectionEventAdvance() throws Exception{
		//igual que el de abajo pero con la salvedad de que es venta anticipada y lleva descuento
		Event  en = new EventArrayImpl("The Fabulous Five", parseLocalDate("24/02/2018 17:00:00"), 4);
		Assert.assertEquals(en.sellSeat(1, new Person("74563322", "Perico", 8), true),true);
		Assert.assertEquals(en.sellSeat(2, new Person("78562322", "Pepa", 90), true),true);
		Assert.assertTrue(en.getCollectionEvent()==150.0);
		Assert.assertEquals(en.getCollectionEvent(), 150.0, 0.001);

	}
	
	
	
	@Test
	public void testgetCollectionNormal() throws Exception{
		Event  en = new EventArrayImpl("The Fabulous Five", parseLocalDate("24/02/2018 17:00:00"), 4);
		Assert.assertEquals(en.sellSeat(1, new Person("74563322", "Perico", 8), false),true);
		Assert.assertEquals(en.sellSeat(2, new Person("78562322", "Pepa", 90), false),true);
		Assert.assertTrue(en.getCollectionEvent()==200.0);
		Assert.assertEquals(en.getCollectionEvent(), 200.0, 0.001);

	}
	
	
	
	@Test
	public void testgetPosPerson() throws Exception{
		//Se crea a una persona
		Person person = new Person("Maria", "61234788", 25);
		
		//se le vende una buataca
		e.sellSeat(23, person,true);

		
		//se comprueba que pasandola devuelve la posicion en la que compro la butaca
		Assert.assertTrue(e.getPosPerson(person) == 23);
		Assert.assertEquals(e.getPosPerson(person), 23);

		//se pasa un objeto persona que no esta, no tiene butaca
		Person fakePerson = new Person("Elisa", "01234788", 27);
		
		//se comprueba que la salida es -1
		Assert.assertTrue(e.getPosPerson(fakePerson) == -1);
		Assert.assertEquals(e.getPosPerson(fakePerson), -1);
		
		
		
	}
	
	
	
	@Test
	public void testisAdvanceSale() throws Exception{
		//se crea una persona e hizo la compra con venta anticipada
		Person personAdvancedSale = new Person("Maria", "61234788", 25);
		e.sellSeat(23, personAdvancedSale,true);
		
		//se verifica que pasandosela devuelve true
		Assert.assertTrue(e.isAdvanceSale(personAdvancedSale) == true);
		Assert.assertEquals(e.isAdvanceSale(personAdvancedSale), true);
		
		
		//se crea otra persona que hizo la compra en venta normal
		Person personNormalSale = new Person("Mario", "48952200", 24);
		e.sellSeat(26, personNormalSale,false);
		
		
		//se comprueba que pasandosela al metodo retorna false
		Assert.assertTrue(e.isAdvanceSale(personNormalSale) == false);
		Assert.assertEquals(e.isAdvanceSale(personNormalSale), false);
		
	
	} 
	//SUPONGO QUE 2 NIF IGUALES NO PUEDEN SER : si 2 objetos persona iguales en distinta posicion del array no va por culpa del equals
	//la clse arrayImp debe de estar al 100 y hacer para que el equals se cumplan todos los caminos


	@Test
	public void testequalsDiferentInstance() throws Exception{
		//creo objeto persona para instanciar la clase
		Person persone = new Person("Maria", "61234788", 25);
		
		//creo un objeto que no es persona para comprobar el if de que es de otra clase
		Event  en = new EventArrayImpl("The Fabulous Five", parseLocalDate("24/02/2018 17:00:00"), 4);

		Assert.assertTrue(persone.equals(en) == false);
		Assert.assertEquals(persone.equals(en), false);
		
		//compruebo con un objeto igual exactamente
		
		Assert.assertTrue(persone.equals(persone) == true);
		Assert.assertEquals(persone.equals(persone), true);
		
		//compuebo con otra persona que sea de la misma instancia (clase) pero con diferente NIF (mas grande)
		Person person = new Person("Mario", "98234788", 35);
		
		Assert.assertTrue(persone.equals(person) == false);
		Assert.assertEquals(persone.equals(person), false);
		
		//compuebo con otra persona que sea de la misma instancia (clase) pero con diferente NIF (mas pequeño)
		Person person1 = new Person("Luis", "00005566", 27);
				
		Assert.assertTrue(persone.equals(person1) == false);
		Assert.assertEquals(persone.equals(person1), false);
		
		
		//compuebo con otra persona que sea de la misma instancia (clase) pero igual nif
		Person person2 = new Person("Carmen", "61234788", 30);
				
		Assert.assertTrue(persone.equals(person2) == true);
		Assert.assertEquals(persone.equals(person2), true);
	}












}
