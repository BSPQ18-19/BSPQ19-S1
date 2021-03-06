package es.deusto.client.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.rmi.RemoteException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import es.deusto.client.data.Articulo;
import es.deusto.client.data.Pelicula;
import es.deusto.client.remote.ServiceLocator;
import es.deusto.server.dto.SocioDTO;
import es.deusto.server.remote.IServer;

public class ControllerArticulosTest {

	private ControllerArticulos cArt;
	private Articulo pelicula;

	@Mock
	private ServiceLocator rsl;
	@Mock
	private IServer server;
	
	private String nombre;
	private double precio;
	private String sinopsis;
	private String genero;
	private String fecha_estr;
	private double puntuacion;
	private String caratula;
	private double descuento;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		pelicula = new Pelicula("PeliculaTest", 3.5, "Sinopsis", "Genero", "01/05/2000", 5, "caratula.png", 0);
		try {
			cArt = new ControllerArticulos(rsl);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void constructorTest() {
		try {
			assertNotNull(new ControllerArticulos());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
//	
//	@Test
//	public void insertarPeliculaTest() {
//		try {
//			cArt = new ControllerArticulos(rsl);
//			
//			when(rsl.getService()).thenReturn(server);
//			when(rsl.getService().insertarPelicula(nombre, precio, sinopsis, genero, fecha_estr, puntuacion, caratula, descuento));
//			
//			assertTrue(cArt.insertarPelicula(nombre, precio, sinopsis, genero, fecha_estr, puntuacion, caratula, descuento));
//		} catch (RemoteException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@Test
//	public void insertarVideojuegoTest() {
//		try {
//			cArt = new ControllerArticulos(rsl);
//			
//			when(rsl.getService()).thenReturn(server);
//			when(rsl.getService().insertarVideojuego(nombre, precio, sinopsis, genero, fecha_estr, puntuacion, caratula, descuento));
//			
//			assertTrue(cArt.insertarVideojuego(nombre, precio, sinopsis, genero, fecha_estr, puntuacion, caratula, descuento));
//		} catch (RemoteException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@Test
//	public void updatePrecioTest() {
//		try {
//			cArt = new ControllerArticulos(rsl);
//			
//			when(rsl.getService()).thenReturn(server);
//			when(rsl.getService().updatePrecio(nombre, precio));
//			assertTrue(cArt.updatePrecio(nombre, precio));
//		} catch (RemoteException e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void updateDescuentoTest() {
//		try {
//			cArt = new ControllerArticulos(rsl);
//			
//			when(rsl.getService()).thenReturn(server);
//			when(rsl.getService().updateDescuento(nombre, descuento));
//			assertTrue(cArt.updateDescuento(nombre, descuento));
//		} catch (RemoteException e) {
//			e.printStackTrace();
//		}
//	}
	
	
}
