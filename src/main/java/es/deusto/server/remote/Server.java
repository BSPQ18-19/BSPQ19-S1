package es.deusto.server.remote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import es.deusto.server.dto.AlquilerDTO;
import es.deusto.server.dto.ArticuloDTO;
import es.deusto.server.dto.SocioDTO;
import es.deusto.server.services.AppServiceDB;

public class Server extends UnicastRemoteObject implements IServer {

	private static final long serialVersionUID = 1L;
	
	private static Server INSTANCE;
	private AppServiceDB appService;
	

	private Server(String[] args) throws RemoteException {
		appService = new AppServiceDB(args);
	}

	public static Server getInstance(String[] args) {
		synchronized(Server.class) {
			if (INSTANCE == null) {
				try {
					INSTANCE = new Server(args);
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
	public boolean insertarAlquiler(String nombre, double precio, String sinopsis, String genero, String fecha_estr, double puntuacion, String caratula, double coste, String nombreUsuario, boolean pv, String fechaFin, String fechaInicio, double descuento) throws RemoteException
	{
		System.out.println("He pasado al Server.java");
		try
		{
			return appService.insertarAlquiler(nombre, precio, sinopsis, genero, fecha_estr,puntuacion, caratula, coste, nombreUsuario,pv, fechaFin, fechaInicio, descuento);
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

	public SocioDTO selectSocio(String nombreUsuario) throws RemoteException {
		try {
			return appService.selectSocio(nombreUsuario);
		}catch(Exception e) {
			System.err.println("$ Error al seleccionar el socio " + e.getMessage());
			return null;
		}
	
	}

	@Override
	public boolean updateMonedero(String nombreUsuario, double monedero) throws RemoteException {
		try {
			return appService.updateMonedero(nombreUsuario, monedero);
		}catch(Exception e) {
			System.err.println("$ Error al actualizar el monedero " + e.getMessage());
		}
		return false;
	}
	
	@Override
	public boolean updateDescuento(String nombreArticulo, double descuento) throws RemoteException {
		try {
			return appService.updateDescuento(nombreArticulo, descuento);
		}catch(Exception e) {
			System.err.println("$ Error al actualizar el monedero " + e.getMessage());
		}
		return false;
	}
	
	@Override
	public boolean updateDatosSocio(String nombreSocio, String datosNuevos) throws RemoteException {
		try {
			return appService.updateDatosSocio(nombreSocio, datosNuevos);
		} catch(Exception e) {
			System.err.println("$ Error al actualizar los datos de socio " + e.getMessage());
			return false;
		}
	}

	@Override
	public boolean insertarPelicula(String nombre, double precio, String sinopsis, String genero, String fecha_estr, double puntuacion, String caratula, double descuento) throws RemoteException {
		try
		{
			return appService.insertarPelicula(nombre, precio, sinopsis, genero, fecha_estr, puntuacion, caratula, descuento);
		}catch(Exception e)
		{
			System.err.println("$ Error al insertar película "+ e.getMessage());
			
		}
		return false;
	}
	
	@Override
	public boolean insertarVideojuego(String nombre, double precio, String descripcion, String categoria, String fecha_lanz, double puntuacion, String caratula, double descuento) throws RemoteException {
		try
		{
			return appService.insertarVideojuego(nombre, precio, descripcion, categoria, fecha_lanz, puntuacion, caratula, descuento);
		}catch(Exception e)
		{
			System.err.println("$ Error al insertar videojuego "+ e.getMessage());
			
		}
		return false;
	}

	@Override
	public boolean updatePrecio(String nombreArticulo, double precio) throws RemoteException {
		try {
			return appService.updatePrecio(nombreArticulo, precio);
		}catch(Exception e) {
			System.err.println("$ Error al actualizar precio " + e.getMessage());
		}
		return false;
	}

	@Override
	public boolean devolverAlquiler(String nombreUsuario, String nombreArticulo, int valoracion) throws RemoteException {
		try {
			return appService.devolverAlquiler(nombreUsuario, nombreArticulo, valoracion);
		}catch (Exception e) {
			System.err.println("$ Error al devolver articulo "+ e.getMessage());
		}
		return false;
	}

	@Override
	public boolean bloquearMaquina(String nombreAdmin) throws RemoteException {
		try {
			return appService.bloquearMaquina(nombreAdmin);
		}catch (Exception e) {
			System.err.println("$ Error al bloquear maquina "+ e.getMessage());
			return false;
		}
	}

	@Override
	public boolean pagarPaypal(String nombre, String password, double cantidad) throws RemoteException {
		try {
			return appService.pagarPaypal(nombre, password, cantidad);
		} catch (Exception e) {
			System.err.println("$ Error al realizar el pago mediante PayPal "+ e.getMessage());
			return false;
		}
	}

}
