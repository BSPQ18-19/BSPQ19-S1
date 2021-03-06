package es.deusto.client.controllers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.rmi.RemoteException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import es.deusto.client.remote.ServiceLocator;
import es.deusto.server.dto.SocioDTO;
import es.deusto.server.remote.IServer;

@RunWith(MockitoJUnitRunner.class)
public class ControllerMenuSocioTest {
	
	private ControllerMenuSocio cms;
	private SocioDTO s;
	
	@Mock
	private ServiceLocator rsl;
	@Mock
	private IServer server;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		s = new SocioDTO("Pepe", "1111111A", "Pepe", "Apellidos", "Direccion de Pepe", 0.0, "imagenTest.jpg");
		try {
			cms = new ControllerMenuSocio(rsl);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void constructorTest() {
		try {
			assertNotNull(new ControllerMenuSocio());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void updateBloquearMaquinaBienTest() {
		try {
			when(rsl.getService()).thenReturn(server);
			when(rsl.getService().bloquearMaquina(s.getNombre())).thenReturn(true);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		assertTrue(cms.updateBloquearMaquina(s.getNombre()));
	}
	
	public void updateBloquearMaquinaMalTest() {
		try {
			when(rsl.getService()).thenReturn(server);
			when(rsl.getService().bloquearMaquina(s.getNombre())).thenThrow(RemoteException.class);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		assertFalse(cms.updateBloquearMaquina(s.getNombre()));
	}
	
}
