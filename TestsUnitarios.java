package test;

import java.util.Calendar;
import java.util.Date;

import modelo.Cuenta;
import modelo.Debito;
import modelo.Credito;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestsUnitarios {

	Cuenta cuenta, cuenta2;
	Debito tarjeta_d, tarjeta_d2;

	@BeforeEach
	public void setUp() throws Exception {
		cuenta = new Cuenta("14658976-21", "Fraiz Franco");
		cuenta.ingresar(150000.0);
		tarjeta_d = new Debito("457896", "Lopez Ariel", new Date(2021, 9, 23));
		tarjeta_d.setCuenta(cuenta);
		
		cuenta2 = new Cuenta("14658976-32", "Arias Facundo");
		cuenta2.ingresar(1000.0);	
		
		tarjeta_d2 = new Debito("238995", "Arias Facundo",new Date(2018, 03, 26));
		tarjeta_d2.setCuenta(cuenta2);
				
	}
	
	
//Testeos	
	
	@Test
	public void testPermitePagarConSaldoSuficiente() {
		double saldoAnterior = cuenta.getSaldo();
		try {
			tarjeta_d.pagoEnEstablecimiento("establecimiento1", 50000.0);
			assertTrue(saldoAnterior!=cuenta.getSaldo(),"Fallo - No permitio pagar el monto habiendo saldo suficiente");
		} catch (Exception e) {
			fail ("No permitio hacer el pago");
		}
	}

	
	@Test 
	public void testActualizarMontoPostExtraccion() {
		double saldoAnterior = cuenta.getSaldo();	
		try {
			tarjeta_d.retirar(2000.0);	
			assertTrue(saldoAnterior!=cuenta.getSaldo(), "Fallo - No resto el monto extraido al saldo de la cuenta");
		} catch (Exception e) {			
			fail ("No permitio hacer el retiro");
		}		
	}	
	
	@Test
	public void testNoPermitePagoNegativo() {
		double saldoAnterior = cuenta.getSaldo();
		try {
			tarjeta_d.pagoEnEstablecimiento("establecimiento1", -1800.0);
		} catch (Exception e) {
			assertTrue(saldoAnterior==cuenta.getSaldo(),"Fallo - Permitio pagar un monto negativo");
		}
	}
	
		  
	@Test 
	public void testNoPermiteIngresarMonto0() {
		double saldoAIngresar = 0;	
		try {
			cuenta.ingresar(saldoAIngresar);	
		} catch (Exception e) {
			assertTrue(saldoAIngresar == 0,"Fallo - Permitio ingresar importe 0");		
		}
	}	
	 
	
	@Test 
	public void testPermiteIngresarSaldo() {
		double saldoAnterior = cuenta.getSaldo();
		try {
			cuenta.ingresar(2500);
		} catch (Exception e) {
			assertTrue(saldoAnterior == cuenta.getSaldo(),"Fallo - No permitio ingresar saldo");
		}
	}
		 
}
