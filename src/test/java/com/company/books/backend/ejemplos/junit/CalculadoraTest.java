package com.company.books.backend.ejemplos.junit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CalculadoraTest {
	
	@BeforeAll
	public static void primero() {
		System.out.println("primero");
	}
	@AfterAll
	public static void ultimo() {
		System.out.println("ultimo");
	}

	@Test
	public void calculadoraAssertEqualTest() {
		Calculadora calc = new Calculadora();
		
		assertEquals(2, calc.sumar(1, 1));
		assertEquals(3, calc.restar(4, 1));
		assertEquals(56, calc.multiplicar(7, 8));
		assertEquals(9, calc.dividir(27,3));
		System.out.println("calculadoraAssertEqualTest");
	}
	
	@Test
	public void calculadoraTrueFalse() {
		Calculadora calc = new Calculadora();
		
		assertTrue(calc.sumar(1, 1) == 2);
		assertTrue(calc.restar(4, 1) == 3);
		assertFalse(calc.multiplicar(7, 8) == 9);
		assertFalse(calc.dividir(4,2) == 1);
		System.out.println("calculadoraTrueFalse");
	}
}
