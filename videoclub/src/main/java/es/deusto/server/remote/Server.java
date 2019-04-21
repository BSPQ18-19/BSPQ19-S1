package es.deusto.server.remote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import es.deusto.client.data.Articulo;
import es.deusto.server.dto.AlquilerDTO;
import es.deusto.server.dto.ArticuloDTO;
import es.deusto.server.dto.SocioDTO;
import es.deusto.server.services.AppServiceDB;

public class Server extends UnicastRemoteObject implements IServer {

	private static final long serialVersionUID = 1L;
	
	private static Server INSTANCE;
	private AppServiceDB appService;
	

	private Server() throws RemoteException {
		super();
		appService = new AppServiceDB();
	}

	public static Server getInstance() {
		synchronized(Server.class) {
			if (INSTANCE == null) {
				try {
					INSTANCE = new Server();
				} catch (Exception ex) {
					System.err.println("# Error creating Server: " + ex);
				}
			}

			return INSTANCE;
		}

	}

	public String sayHello() throws RemoteException {
		return "Hello World!";
	}

	@Override
	public boolean registro(String nombre, String pass, double monedero) throws RemoteException {
		try {
			System.out.println("###Server: AppServiceDB.insertarSocio###");
			return appService.insertarSocio(nombre, pass, monedero);
		} catch (Exception e) {
			System.err.println("$ Error al registrarse " + e.getMessage());
			return false;
		}
	}

	@Override
	public boolean existeSocio(String nombreSocio) throws RemoteException {
		try {
			return appService.existeSocio(nombreSocio);
		} catch (Exception e) {
			System.err.println("$ Error al comprobar si existe socio " + e.getMessage());
			return false;
		}
		
	}

	@Override
	public SocioDTO inicioSesion(String nombreSocio, String password) throws RemoteException {
		try {
			return appService.inicioSesion(nombreSocio, password);
		} catch (Exception e) {
			System.err.println("$ Error al iniciar sesion " + e.getMessage());
			return null;
		}
	}
	
	@Override
	public boolean insertarAlquiler(Articulo articulo, double coste, String nombreUsuario) throws RemoteException
	{
		try
		{
			return appService.insertarAlquiler(articulo, coste, nombreUsuario);
		}catch(Exception e)
		{
			System.err.println("$ Error al insertar alquiler "+ e.getMessage());
			return false;
		}
	}

	@Override
	public List<ArticuloDTO> listadoArticulos() throws RemoteException {
		try {
			return appService.listadoArticulos();
		} catch(Exception e) {
			System.err.println("$ Error al obtener listado de articulos " + e.getMessage());
			return null;
		}
	}

	@Override
	public List<AlquilerDTO> historialAlquileres(String nombreSocio) throws RemoteException {
		try {
			return appService.historialAlquileres(nombreSocio);
		} catch(Exception e) {
			System.err.println("$ Error al obtener historial de alquileres " + e.getMessage());
			return null;
		}
	}

}
