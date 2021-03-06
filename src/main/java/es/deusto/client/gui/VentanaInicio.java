package es.deusto.client.gui;

import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.rmi.RemoteException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import es.deusto.client.controllers.ControllerAlquiler;
import es.deusto.client.controllers.ControllerRegistro;
import es.deusto.server.dto.SocioDTO;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Clase de la ventana VentanaInicio.
 */
public class VentanaInicio extends JFrame {

	private static final long serialVersionUID = 1L;

	//	private HashMap<String, Socio> socios = new HashMap<String, Socio>();
	//	private ArrayList<Alquiler> alquileresPrueba = new ArrayList<Alquiler>();
	@SuppressWarnings("unused")
	private ControllerRegistro controllerRegistro;
	private ControllerAlquiler controllerAlquileres;
	private boolean registroCorrecto;
	private SocioDTO socio;


	//	public static void main(String[] args) {
	//		EventQueue.invokeLater(new Runnable() {
	//			public void run() {
	//				try {
	//					VentanaInicio frame = new VentanaInicio(null);
	//					frame.setVisible(true);
	//				} catch (Exception e) {
	//					e.printStackTrace();
	//				}
	//			}
	//		});
	//	}

	/**
	 * Constructor de la ventana VentanaInicio.
	 * @param controllerRegistro
	 */
	public VentanaInicio(ControllerRegistro controllerRegistro) {
		this.controllerRegistro = controllerRegistro;

		try {
			this.controllerAlquileres = new ControllerAlquiler();
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		this.setVisible(true);

		setTitle("Bienvenido al Videoclub");
		setResizable(false);

		//		cargarAlquileresPrueba(alquileresPrueba);

		//		Socio s = new Socio("a", "a", 100, "default-profile.png");
		//		s.setAlquileres(alquileresPrueba);
		//		s.setAlquileres(alquileresPrueba);
		//		socios.put(s.getNombre(), s);


		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 960, 540);

		getContentPane().setLayout(new BorderLayout());
		final JLabel background;
		ImageIcon img = new ImageIcon("."+File.separator+"src"+File.separator+"resources"+File.separator+"inicioSesion.png");
		Image im = img.getImage();
		im = getScaledImage(im, 960,540);
		ImageIcon finalImg= new ImageIcon(im);
		background = new JLabel("", finalImg, JLabel.CENTER);
		background.setBounds(100,100,450, 300);
		getContentPane().add(background);
		background.setLayout(null);

		final JTextField textfield = new JTextField(10);
		textfield.setBounds(400,100,250,40);
		background.add(textfield);

		final JPasswordField passwordField = new JPasswordField(10);
		passwordField.setBounds(400,200,250,40);
		background.add(passwordField);

		final JLabel candadoNegro;
		ImageIcon imagenCandadoN = new ImageIcon("."+File.separator+"src"+File.separator+"resources"+File.separator+"candadoNegro.png");
		im = imagenCandadoN.getImage();
		im = getScaledImage(im,200,200);
		imagenCandadoN = new ImageIcon(im);

		final JLabel candadoVerde;
		ImageIcon imagenCandadoV = new ImageIcon("."+File.separator+"src"+File.separator+"resources"+File.separator+"candadoVerde.png");
		im = imagenCandadoV.getImage();
		im = getScaledImage(im,200,200);
		imagenCandadoV = new ImageIcon(im);

		final JLabel candadoRojo;
		ImageIcon imagenCandadoR = new ImageIcon("."+File.separator+"src"+File.separator+"resources"+File.separator+"candadoRojo.png");
		im = imagenCandadoR.getImage();
		im = getScaledImage(im,200,200);
		imagenCandadoR = new ImageIcon(im);

		candadoNegro = new JLabel("", imagenCandadoN, JLabel.CENTER);
		candadoNegro.setBounds(25,220,450, 300);

		candadoVerde = new JLabel("", imagenCandadoV, JLabel.CENTER);
		candadoVerde.setBounds(25,220,450, 300);

		candadoRojo = new JLabel("", imagenCandadoR, JLabel.CENTER);
		candadoRojo.setBounds(25,220,450, 300);

		background.add(candadoNegro);
		background.add(candadoVerde);
		background.add(candadoRojo);
		candadoVerde.setVisible(false);
		candadoRojo.setVisible(false);

		ImageIcon botonInicioSesion = new ImageIcon("."+File.separator+"src"+File.separator+"resources"+File.separator+"botonLogin.png");
		im = botonInicioSesion.getImage();
		im = getScaledImage(im,208,62);
		botonInicioSesion = new ImageIcon(im);
		JButton boton = new JButton("Login");
		boton.setIcon(botonInicioSesion);
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String nombreUsuario = textfield.getText();
				String pass = String.valueOf(passwordField.getPassword());

				SocioDTO t = controllerAlquileres.selectSocio("Administrador");
				SocioDTO t1 = controllerAlquileres.selectSocio("Director");

				if((t.isBloquearMaquina() && !nombreUsuario.equals(t.getNombre()) && !pass.equals(t.getPassword())) || (t1.isBloquearMaquina() && !nombreUsuario.equals(t1.getNombre()) && !pass.equals(t1.getPassword())) ) {
					JOptionPane.showMessageDialog(null, "En estos momentos la maquina no esta activa debido a labores de mantenimiento, por favor vuelva a intentarlo en otro momento.", "Aviso", JOptionPane.WARNING_MESSAGE);
				} else {
					// Comprobación de si hay algún campo vacio
					if(nombreUsuario.equals("") || pass.equals("") || nombreUsuario == null || pass == null) {
						candadoRojo(candadoNegro, candadoRojo, candadoVerde);

						JOptionPane.showMessageDialog(null, "Alguno de los campos está vacio, por favor introduce un nombre de usuario y contraseña correctos.", "Aviso", JOptionPane.WARNING_MESSAGE);

						candadoNegro(candadoNegro, candadoRojo, candadoVerde);

					} else {
						socio = controllerRegistro.inicioSesion(nombreUsuario, pass);
						// Comprobación de si existe un socio registrado con el nombre introducido. En caso afirmativo se comprueba también si la contraseña es correcta para ese socio
						if(!socio.getNombre().equals("")) {	
							candadoVerde(candadoNegro, candadoRojo, candadoVerde);
							System.out.println("Has iniciado sesión correctamente, bienvenido!");
							textfield.setText(""); passwordField.setText("");

							JOptionPane.showMessageDialog(null, "Has iniciado sesión correctamente!!", "", JOptionPane.INFORMATION_MESSAGE);
							candadoNegro(candadoNegro, candadoRojo, candadoVerde);
							MenuSocio ms = new MenuSocio(VentanaInicio.this, socio);
							ms.setVisible(true);
							VentanaInicio.this.setVisible(false);

						} else {
							candadoRojo(candadoNegro, candadoRojo, candadoVerde);
							JOptionPane.showMessageDialog(null, "Nombre de usuario o contraseña incorrectos, vuelve a intentarlo.", "Login error", JOptionPane.ERROR_MESSAGE);
							candadoNegro(candadoNegro, candadoRojo, candadoVerde);
						}
					}
				}

			}
		});
		boton.setBounds(400,400,200,65);
		background.add(boton);


		ImageIcon botonRegistro = new ImageIcon("."+File.separator+"src"+File.separator+"resources"+File.separator+"botonRegister.png");
		im = botonRegistro.getImage();
		im = getScaledImage(im,230,63);
		botonRegistro = new ImageIcon(im);

		JButton boton2 = new JButton("Register");
		boton2.setIcon(botonRegistro);
		boton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String nombreUsuario = textfield.getText();
				String pass = String.valueOf(passwordField.getPassword());

				boolean mayus = false;

				// Comprobación de si la contraseña tiene al menos una mayúscula
				for(int i = 0; i < pass.length(); i++) {
					if(Character.isUpperCase(pass.charAt(i))) {
						mayus = true;
						break;
					}
				}

				// Comprobación de si algún campo está vacío
				if(nombreUsuario.equals("") || pass.equals("") || nombreUsuario == null || pass == null) {
					candadoRojo(candadoNegro, candadoRojo, candadoVerde);
					JOptionPane.showMessageDialog(null, "Alguno de los campos está vacio, por favor introduce un nombre de usuario y contraseña correctos.", "Aviso", JOptionPane.WARNING_MESSAGE);
					candadoNegro(candadoNegro, candadoRojo, candadoVerde);
				} else {
					// Comprobación de si los campos tienen el tamaño adecuado
					if((nombreUsuario.length() >= 4 && nombreUsuario.length() <= 20) && (pass.length() >= 8 && pass.length() <= 16)) {
						// Comprobación de si el nombre de usuario es el mismo que la contraseña
						if(!nombreUsuario.equals(pass)) {
							// Comprobación de si la contraseña tiene al menos una letra mayúscula
							if(mayus) {
								// Comprobación de si la contraseña introducida contiene tanto letras como números
								if(contieneLetrasYNumeros(pass)) {
									// Comprobación de si existe un socio con el nombre de usuario introducido en el JTextField
									if(!controllerRegistro.existeSocio(nombreUsuario)) {
										//										Socio cliente = new Socio(nombreUsuario, pass, 0, "default-profile.png");
										//										ArrayList<Alquiler> alquileresTemp = new ArrayList<Alquiler>();
										//										cliente.setAlquileres(alquileresTemp);
										registroCorrecto = controllerRegistro.registro(nombreUsuario, pass, 0);
										if(registroCorrecto) {
											//											socios.put(nombreUsuario, cliente);
											candadoVerde(candadoNegro, candadoRojo, candadoVerde);
											JOptionPane.showMessageDialog(null, "Te has registrado correctamente :)", "Registro", JOptionPane.INFORMATION_MESSAGE);
											candadoNegro(candadoNegro, candadoRojo, candadoVerde);
											textfield.setText(""); passwordField.setText("");
										}
									} else {
										candadoRojo(candadoNegro, candadoRojo, candadoVerde);
										JOptionPane.showMessageDialog(null, "El nombre de usuario introducido ya existe, por favor introduzca otro nuevo.", "Error", JOptionPane.ERROR_MESSAGE);
										candadoNegro(candadoNegro, candadoRojo, candadoVerde);
									}
								} else {
									candadoRojo(candadoNegro, candadoRojo, candadoVerde);
									JOptionPane.showMessageDialog(null, "La contraseña debe contener tanto letras como números.", "Aviso", JOptionPane.WARNING_MESSAGE);
									candadoNegro(candadoNegro, candadoRojo, candadoVerde);
								}
							} else {
								candadoRojo(candadoNegro, candadoRojo, candadoVerde);
								JOptionPane.showMessageDialog(null, "La contraseña debe contener al menos una letra mayúscula", "Aviso", JOptionPane.WARNING_MESSAGE);
								candadoNegro(candadoNegro, candadoRojo, candadoVerde);
							}
						} else {
							candadoRojo(candadoNegro, candadoRojo, candadoVerde);
							JOptionPane.showMessageDialog(null, "El nombre y la contraseña no pueden ser iguales, por favor introduce otro nombre o contraseña.", "Aviso", JOptionPane.WARNING_MESSAGE);
							candadoNegro(candadoNegro, candadoRojo, candadoVerde);
						}

					} else {
						candadoRojo(candadoNegro, candadoRojo, candadoVerde);
						JOptionPane.showMessageDialog(null, "El nombre de usuario tiene que tener entre 4 y 20 carácteres, y la contraseña entre 8 y 16.", "Aviso", JOptionPane.WARNING_MESSAGE);
						candadoNegro(candadoNegro, candadoRojo, candadoVerde);
					}

				}



			}
		});
		boton2.setBounds(630,403,220,55);
		background.add(boton2);

	}

	private Image getScaledImage(Image srcImg, int w, int h) {
		BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = resizedImg.createGraphics();

		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(srcImg, 0, 0, w, h, null);
		g2.dispose();

		return resizedImg;
	}

	private void candadoRojo(JLabel candadoNegro,JLabel candadoRojo,JLabel candadoVerde ) {
		candadoNegro.setVisible(false);
		candadoRojo.setVisible(true);
		candadoVerde.setVisible(false);
	}
	
	private void candadoVerde(JLabel candadoNegro,JLabel candadoRojo,JLabel candadoVerde ) {
		candadoNegro.setVisible(false);
		candadoRojo.setVisible(false);
		candadoVerde.setVisible(true);
	}
	
	private void candadoNegro(JLabel candadoNegro,JLabel candadoRojo,JLabel candadoVerde ) {
		candadoNegro.setVisible(true);
		candadoRojo.setVisible(false);
		candadoVerde.setVisible(false);
	}

	/**
	 * Comprueba si la cadena de caracteres contiene tanto letras como numeros.
	 * @param s
	 * @return true si contiene letras y numeros, false en caso negativo.
	 */
	public boolean contieneLetrasYNumeros(String s) {
		String n = ".*[0-9].*";
		String l = ".*[A-Z].*";
		return s.matches(n) && s.matches(l);
	}

	//	private void cargarAlquileresPrueba(ArrayList<Alquiler> alquileres) {
	//		Pelicula p1 = new Pelicula("Los vengadores", 5.5, "Descripcion de Los Vengadores", "Acción","20/09/2014", 9, "vengadores.jpg");
	//		Videojuego v1 = new Videojuego("Mario Bros", 4.75, "Descripcion de Mario", "Aventura","31/03/2008", 8.5, "mario.jpg");
	//		Videojuego v2 = new Videojuego("GTA V", 7, "Descripcion de GTA V", "Acción","10/07/2012", 6, "GTAV.jpg");
	//
	//
	//		Alquiler a1 = new Alquiler(p1, p1.getPrecio(), "20/3/2019", "30/3/2019", true);
	//		Alquiler a2 = new Alquiler(v1, v1.getPrecio(), "15/2/2019", "03/4/2019", false);
	//		Alquiler a3 = new Alquiler(v2, v2.getPrecio(), "1/31/2018", "12/31/2018", true);
	//
	//
	//		alquileres.add(a1);
	//		alquileres.add(a2);
	//		alquileres.add(a3);
	//
	//	}

}
