	package es.deusto.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaInicio extends JFrame {

	private JPanel contentPane;
	private HashMap<String, String> registro = new HashMap<String, String>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaInicio frame = new VentanaInicio();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	private Image getScaledImage(Image srcImg, int w, int h){
	    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2 = resizedImg.createGraphics();

	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(srcImg, 0, 0, w, h, null);
	    g2.dispose();

	    return resizedImg;
	}
	/**
	 * Create the frame.
	 */
	public VentanaInicio() {
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
				candadoNegro.setVisible(false);
				candadoRojo.setVisible(false);
				candadoVerde.setVisible(true);
				background.revalidate();
				background.repaint();
				
				String nombreUsuario = textfield.getText();
				String pass = String.valueOf(passwordField.getPassword());
				// Comprobaciónd de si hay algún campo vacio
				if(nombreUsuario.equals("") || pass.equals("") || nombreUsuario == null || pass == null) {
					JOptionPane.showMessageDialog(null, "Alguno de los campos está vacio, por favor introduce un nombre de usuario y contraseña correctos.", "Aviso", JOptionPane.WARNING_MESSAGE);
				} else {
					// Comprobación de si existe un usuario registrado con el nombre introducido
					if(registro.containsKey(nombreUsuario)) {
						// Comprobación de si la contraseña coincide con la del nombre de usuario registrado
						if(registro.get(nombreUsuario).equals(pass)) {
							System.out.println("Has iniciado sesión correctamente, bienvenido!");
							textfield.setText(""); passwordField.setText("");
						} else {
							JOptionPane.showMessageDialog(null, "Contraseña incorrecta, vuelve a intentarlo.", "Error", JOptionPane.ERROR_MESSAGE);
						}
					} else {
						JOptionPane.showMessageDialog(null, "No existe ningún usuario con ese nombre, por favor prueba con otro.", "Error", JOptionPane.ERROR_MESSAGE);
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
				candadoNegro.setVisible(false);
				candadoRojo.setVisible(true);
				candadoVerde.setVisible(false);
				background.revalidate();
				background.repaint();
				
				
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

				// Comprobación de si algún campo está vacio
				if(nombreUsuario.equals("") || pass.equals("") || nombreUsuario == null || pass == null) {
					JOptionPane.showMessageDialog(null, "Alguno de los campos está vacio, por favor introduce un nombre de usuario y contraseña correctos.", "Aviso", JOptionPane.WARNING_MESSAGE);
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
									if(!registro.containsKey(nombreUsuario)) {
										registro.put(nombreUsuario, pass);
										JOptionPane.showMessageDialog(null, "Te has registrado correctamente :)", "Registro", JOptionPane.INFORMATION_MESSAGE);
										textfield.setText(""); passwordField.setText("");
									} else {
										JOptionPane.showMessageDialog(null, "El nombre de usuario introducido ya existe, por favor introduzca otro nuevo.", "Error", JOptionPane.ERROR_MESSAGE);
									}
								} else {
									JOptionPane.showMessageDialog(null, "La contraseña debe contener tanto letras como números.", "Aviso", JOptionPane.WARNING_MESSAGE);
								}
							} else {
								JOptionPane.showMessageDialog(null, "La contraseña debe contener al menos una letra mayúscula", "Aviso", JOptionPane.WARNING_MESSAGE);
							}
						} else {
							JOptionPane.showMessageDialog(null, "El nombre y la contraseña no pueden ser iguales, por favor introduce otro nombre o contraseña.", "Aviso", JOptionPane.WARNING_MESSAGE);
						}

					} else {
						JOptionPane.showMessageDialog(null, "El nombre de usuario tiene que tener entre 4 y 20 carácteres, y la contraseña entre 8 y 16.", "Aviso", JOptionPane.WARNING_MESSAGE);
					}

				}
				
				
			}
		});
		boton2.setBounds(630,403,220,55);
		background.add(boton2);
		
		
		
	}
	public boolean contieneLetrasYNumeros(String s) {
		String n = ".*[0-9].*";
		String l = ".*[A-Z].*";
		return s.matches(n) && s.matches(l);
	}
	
}
