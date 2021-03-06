package es.deusto.client.gui;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import es.deusto.client.controllers.ControllerAlquiler;
import es.deusto.client.controllers.ControllerListadoArticulos;
import es.deusto.client.controllers.ControllerMenuSocio;
import es.deusto.server.dto.AlquilerDTO;
import es.deusto.server.dto.ArticuloDTO;
import es.deusto.server.dto.SocioDTO;

import java.awt.Color;
import java.awt.Font;


/**
 * Clase de la ventana MenuSocio.
 */
public class MenuSocio extends JFrame {

	private static final long serialVersionUID = 1L;
	// private JFrame frame;
	private JFrame ventanaInicio;
	private ControllerListadoArticulos controllerListadoArticulos;
	private ControllerAlquiler controllerAlquileres;
	private ControllerMenuSocio controllerMenuSocio;
	private List<ArticuloDTO> articulos = new ArrayList<ArticuloDTO>();
	private List<AlquilerDTO> alquileres = new ArrayList<AlquilerDTO>();
	private boolean bloquearMaquina;

	
	//	public static void main(String[] args) {
	//		EventQueue.invokeLater(new Runnable() {
	//			public void run() {
	//				try {
	//					MenuSocio frame = new MenuSocio();
	//					frame.setVisible(true);
	//				} catch (Exception e) {
	//					e.printStackTrace();
	//				}
	//			}
	//		});
	//	}

	
	/**
	 * Constructor de la ventana MenuSocio.
	 * @param ventanaAnterior
	 * @param iniciado
	 */
	public MenuSocio(JFrame ventanaAnterior, final SocioDTO iniciado) {

		//cargarArticulosPrueba();

		try {
			this.controllerListadoArticulos = new ControllerListadoArticulos();
			this.controllerAlquileres = new ControllerAlquiler();
			this.controllerMenuSocio = new ControllerMenuSocio();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		setTitle("Menú principal");
		ventanaInicio = ventanaAnterior;
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 960, 540);
		final JLabel background;
		ImageIcon img = new ImageIcon("."+File.separator+"src"+File.separator+"resources"+File.separator+"menuprincipal.png");
		Image im = img.getImage();
		im = getScaledImage(im, 960,540);
		ImageIcon finalImg= new ImageIcon(im);
		getContentPane().setLayout(null);
		background = new JLabel("", finalImg, JLabel.CENTER);
		background.setBounds(0,0,960, 518);
		getContentPane().add(background);
		background.setLayout(null);

		final JLabel lblSaldo = new JLabel("Tu saldo actual es de "+iniciado.getMonedero()+" €");
		lblSaldo.setFont(new Font("AppleGothic", Font.PLAIN, 18));
		lblSaldo.setForeground(Color.WHITE);
		lblSaldo.setBounds(125, 200, 300, 60);
		background.add(lblSaldo);

		articulos = controllerListadoArticulos.listadoArticulos();

		JButton btnNewButton = new JButton("");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Alquilar");
				articulos = controllerListadoArticulos.listadoArticulos();
				List<ArticuloDTO> articulos2 = new ArrayList<ArticuloDTO>();
				ListadoArticulos ls = new ListadoArticulos(MenuSocio.this, iniciado, lblSaldo, articulos, articulos2);
				ls.setVisible(true);
				MenuSocio.this.setVisible(false);
			}
		});
		btnNewButton.setBounds(490, 159, 170, 50);
		background.add(btnNewButton);
		btnNewButton.setOpaque(false);
		btnNewButton.setContentAreaFilled(false);
		btnNewButton.setBorderPainted(false);

		JButton btnConsultar = new JButton("");
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Consultar alquileres");
				alquileres = controllerAlquileres.historialAlquileres(iniciado.getNombre());
				if(!alquileres.isEmpty()) {
					VentanaAlquileres va = new VentanaAlquileres(MenuSocio.this, iniciado, alquileres);
					va.setVisible(true);
					MenuSocio.this.setVisible(false);	
				} else {
					JOptionPane.showMessageDialog(null, "No tienes ningún alquiler en esta cuenta.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		background.add(btnConsultar);
		btnConsultar.setBounds(730, 159, 170, 50);
		btnConsultar.setOpaque(false);
		btnConsultar.setContentAreaFilled(false);
		btnConsultar.setBorderPainted(false);

		JButton btnRecargar = new JButton("");
		btnRecargar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				System.out.println("Recargar saldo");
				VentanaRecargarSaldo saldo = new VentanaRecargarSaldo(MenuSocio.this, iniciado.getNombre(), lblSaldo);
				saldo.setVisible(true);
				MenuSocio.this.setVisible(false);

			}
		});
		btnRecargar.setBounds(735, 240, 170, 50);
		background.add(btnRecargar);
		btnRecargar.setOpaque(false);
		btnRecargar.setContentAreaFilled(false);
		btnRecargar.setBorderPainted(false);

		JButton btnDevolver = new JButton("");
		btnDevolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Devolver artículos");
				alquileres = controllerAlquileres.historialAlquileres(iniciado.getNombre());
				boolean temp = false;
				for(int i = 0; i < alquileres.size(); i++) {
					if(alquileres.get(i).isEnCurso()) {
						temp = true;
					}
				}
				if(temp) {
					VentanaDevolucion vd = new VentanaDevolucion(MenuSocio.this, iniciado.getNombre(), alquileres);
					vd.setVisible(true);
					MenuSocio.this.setVisible(false);	
				} else {
					JOptionPane.showMessageDialog(null, "No tienes ningún alquiler en esta cuenta.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnDevolver.setBounds(490, 240, 170, 50);
		background.add(btnDevolver);
		btnDevolver.setOpaque(false);
		btnDevolver.setContentAreaFilled(false);
		btnDevolver.setBorderPainted(false);
		JButton btnSalir = new JButton("");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Salir");
				ventanaInicio.setVisible(true);
				MenuSocio.this.dispose();
			}
		});
		
		JButton btnGuia = new JButton("?");
//		Font fuente = new Font("AppleGothic", 3, 10);
//		btnGuia.setFont(fuente);
		btnGuia.setBounds(5, 460, 45, 35);
		background.add(btnGuia);
		btnGuia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaGuia ventanaGuia;
				try {
					ventanaGuia = new VentanaGuia(MenuSocio.this);
					ventanaGuia.setVisible(true);
					MenuSocio.this.setVisible(false);
				} catch (FileNotFoundException e1) {
					System.out.println("Error");
					e1.printStackTrace();
				}
			}
		});
		
		if (iniciado.getNombre().equals("Administrador") || iniciado.getNombre().equals("Director")) {
			JButton btnDescuentos = new JButton("Descuentos");
			btnDescuentos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					articulos = controllerListadoArticulos.listadoArticulos();
					VentanaDescuento descuentos = new VentanaDescuento(MenuSocio.this, articulos);
					descuentos.setVisible(true);
					MenuSocio.this.setVisible(false);


				}
			});
			btnDescuentos.setBounds(765, 300, 130, 50);
			background.add(btnDescuentos);

			JButton btnNuevoArticulo = new JButton("Añadir artículo");
			btnNuevoArticulo.setBounds(625, 300, 130, 50);
			background.add(btnNuevoArticulo);

			btnNuevoArticulo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					NuevoArticulo nuevoArticulo = new NuevoArticulo(MenuSocio.this);
					nuevoArticulo.setVisible(true);
					//	MenuSocio.this.setVisible(false);
				}
			});

			JButton btnStock = new JButton("Gestionar stock");
			btnStock.setBounds(490, 300, 130, 50);
			background.add(btnStock);

			btnStock.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VentanaStock ventanaStock = new VentanaStock(MenuSocio.this, articulos);
					ventanaStock.setVisible(true);
					MenuSocio.this.setVisible(false);
				}
			});
			
			
			bloquearMaquina = iniciado.isBloquearMaquina();

			JLabel lblBloquear = new JLabel();
			lblBloquear.setFont(new Font("AppleGothic", Font.PLAIN, 12));
			lblBloquear.setForeground(Color.WHITE);
			lblBloquear.setBounds(700, 45, 300, 60);
			if(bloquearMaquina) {
				lblBloquear.setText("Estado de la maquina cliente: Bloqueada");
			} else {
				lblBloquear.setText("Estado de la maquina cliente: Activa");
			}
			background.add(lblBloquear);

			JButton btnBloquear = new JButton("B/D");
			btnBloquear.setBounds(850, 25, 55, 25);
			background.add(btnBloquear);

			btnBloquear.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					boolean correcto = controllerMenuSocio.updateBloquearMaquina(iniciado.getNombre());
					
					SocioDTO t = controllerAlquileres.selectSocio(iniciado.getNombre());
					if(correcto) {
						if(t.isBloquearMaquina()) {
							JOptionPane.showMessageDialog(null, "Se ha bloqueado la maquina cliente correctamente", "Bloquear maquina", JOptionPane.INFORMATION_MESSAGE);
							lblBloquear.setText("Estado de la maquina cliente: Bloqueada");
						} else {
							JOptionPane.showMessageDialog(null, "Se ha desbloqueado la maquina cliente correctamente", "Desbloquear maquina", JOptionPane.INFORMATION_MESSAGE);
							lblBloquear.setText("Estado de la maquina cliente: Activa");
						}
					} else {
						JOptionPane.showMessageDialog(null, "No se ha podido realizar la accion correctamente, intentalo de nuevo", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			});
			
			JButton btnEstadisticas = new JButton("Estadisticas");
			btnEstadisticas.setBounds(490, 365, 130, 50);
			background.add(btnEstadisticas);
			
			btnEstadisticas.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					VentanaEstadisticas ventanaEstadistica = new VentanaEstadisticas(MenuSocio.this, alquileres);
					ventanaEstadistica.setVisible(true);
					MenuSocio.this.setVisible(false);
					
				}
				
				
				
			});

		}


		//btnDescuentos.setOpaque(false);
		//btnDescuentos.setContentAreaFilled(false);
		//btnDescuentos.setBorderPainted(false);


		btnSalir.setBounds(590, 430, 180, 55);
		background.add(btnSalir);
		btnSalir.setOpaque(false);
		btnSalir.setContentAreaFilled(false);
		btnSalir.setBorderPainted(false);

		JButton btnEditarDatosSocio = new JButton("Modificar mis datos");
		btnEditarDatosSocio.setBounds(625, 365, 200, 50);
		background.add(btnEditarDatosSocio);

		btnEditarDatosSocio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaEditarDatosSocio veds = new VentanaEditarDatosSocio(MenuSocio.this, iniciado.getNombre(), controllerAlquileres);
				veds.setVisible(true);
				MenuSocio.this.setVisible(false);
			}
		});

		final JLabel fotoPerfil;
		ImageIcon defaultProfile = new ImageIcon("."+File.separator+"src"+File.separator+"resources"+File.separator+"default-profile.png");
		im = defaultProfile.getImage();
		im = getScaledImage(im,200,200);
		defaultProfile = new ImageIcon(im);

		fotoPerfil = new JLabel("", defaultProfile, JLabel.CENTER);
		fotoPerfil.setBounds(25,220,450, 300);
		background.add(fotoPerfil);

		JLabel lblHola = new JLabel("¡Hola "+iniciado.getNombre()+"!");
		lblHola.setFont(new Font("AppleGothic", Font.PLAIN, 22));
		lblHola.setForeground(Color.WHITE);
		lblHola.setBounds(170, 159, 180, 60);
		background.add(lblHola);


	}

	private Image getScaledImage(Image srcImg, int w, int h){
		BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = resizedImg.createGraphics();

		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(srcImg, 0, 0, w, h, null);
		g2.dispose();

		return resizedImg;
	}

	//	private void cargarArticulosPrueba() {
	//		Videojuego v1 = new Videojuego("Sonic", 5, "Descripcion de Sonic", "Plataformas","10/02/2004", 7, "sonic.JPG");
	//		Videojuego v2 = new Videojuego("Mario Bros", 4.75, "Descripcion de Mario", "Aventura","31/03/2008", 8.5, "mario.jpg");
	//		Videojuego v3 = new Videojuego("GTA V", 7, "Descripcion de GTA V", "Acción","20/03/2015", 9, "GTAV.jpg");
	//
	//		Pelicula p1 = new Pelicula("Los vengadores", 5.5, "Descripcion de Los Vengadores", "Acción","20/09/2014", 9, "vengadores.jpg");
	//		Pelicula p2 = new Pelicula("Harry Potter",5, "Descripcion de Harry Potter", "Acción","29/01/2009", 9, "harryPotter.jpg");
	//		Pelicula p3 = new Pelicula("Star Wars I", 6.25, "Descripcion de Star Wars I", "Ciencia ficción","13/06/2010", 9, "starWars.jpg");
	//
	//		articulos.add(v1);
	//		articulos.add(v2);
	//		articulos.add(v3);
	//		articulos.add(p1);
	//		articulos.add(p2);
	//		articulos.add(p3);
	//
	//	}

	//	private boolean alquileresEnCurso(final SocioDTO iniciado) {
	//		boolean result = false;
	//
	//		for(int i = 0; i < iniciado.getAlquileres().size(); i++) {
	//			if(iniciado.getAlquileres().get(i).isEnCurso()) {
	//				result = true;
	//			} 
	//		}
	//
	//
	//		return result;
	//
	//	}

}
