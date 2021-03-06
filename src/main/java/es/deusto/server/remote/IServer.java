package es.deusto.server.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import es.deusto.client.data.Alquiler;
import es.deusto.client.data.Articulo;
import es.deusto.client.data.Pelicula;
import es.deusto.server.dto.AlquilerDTO;
import es.deusto.client.data.Socio;
import es.deusto.server.dto.ArticuloDTO;
import es.deusto.server.dto.SocioDTO;

public interface IServer extends Remote {

	String sayHello() throws RemoteException;
	public boolean registro(String nombre, String pass, double monedero) throws RemoteException;
	public boolean existeSocio(String nombreSocio) throws RemoteException;
	public SocioDTO inicioSesion(String nombreSocio, String password) throws RemoteException;

	public boolean insertarAlquiler(String nombre, double precio, String sinopsis, String genero, String fecha_estr, double puntuacion, String caratula, double coste, String nombreUsuario, boolean pv, String fechaFin, String fechaInicio, double descuento) throws RemoteException;
	public List<AlquilerDTO> historialAlquileres(String nombreSocio) throws RemoteException;
	
	public List<ArticuloDTO> listadoArticulos() throws RemoteException;
	public SocioDTO selectSocio(String nombreUsuario) throws RemoteException;
	public boolean updateMonedero(String nombreUsuario, double monedero) throws RemoteException;
	

	public boolean updateDescuento(String nombreArticulo, double descuento) throws RemoteException;
	public boolean updateDatosSocio(String nombreSocio, String datosNuevos) throws RemoteException;

	public boolean insertarPelicula(String nombre, double precio, String sinopsis, String genero, String fecha_estr, double puntuacion, String caratula, double descuento) throws RemoteException;
	public boolean insertarVideojuego(String nombre, double precio, String descripcion, String categoria, String fecha_lanz, double puntuacion, String caratula, double descuento) throws RemoteException;

	
	public boolean updatePrecio(String nombreArticulo, double precio) throws RemoteException;
	public boolean devolverAlquiler(String nombreUsuario, String nombreArticulo, int valoracion) throws RemoteException;
	
	public boolean bloquearMaquina(String nombreAdmin) throws RemoteException;
	
	public boolean pagarPaypal(String nombre, String password, double cantidad) throws RemoteException;
}
