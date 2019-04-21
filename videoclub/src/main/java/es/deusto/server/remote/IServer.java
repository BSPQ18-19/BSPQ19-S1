package es.deusto.server.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import es.deusto.client.data.Articulo;
import es.deusto.server.dto.AlquilerDTO;
import es.deusto.server.dto.ArticuloDTO;
import es.deusto.server.dto.SocioDTO;

public interface IServer extends Remote {

	String sayHello() throws RemoteException;
	public boolean registro(String nombre, String pass, double monedero) throws RemoteException;
	public boolean existeSocio(String nombreSocio) throws RemoteException;
	public SocioDTO inicioSesion(String nombreSocio, String password) throws RemoteException;

	boolean insertarAlquiler(Articulo articulo, double coste, String nombreUsuario) throws RemoteException;
	public List<AlquilerDTO> historialAlquileres(String nombreSocio) throws RemoteException;
	
	public List<ArticuloDTO> listadoArticulos() throws RemoteException;

}
