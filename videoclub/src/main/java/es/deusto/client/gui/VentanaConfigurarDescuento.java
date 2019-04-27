package es.deusto.client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import es.deusto.client.controllers.ControllerAlquiler;
import es.deusto.client.data.Alquiler;
import es.deusto.client.data.Articulo;
import es.deusto.client.data.Pelicula;
import es.deusto.client.data.Videojuego;
import es.deusto.server.dto.ArticuloDTO;
import es.deusto.server.dto.SocioDTO;

public class VentanaConfigurarDescuento extends JFrame {
	
	private JPanel contentPane;
	private JFrame ventanaQueMeLlama;
	private ControllerAlquiler controllerAlquiler;
	
	private Image getScaledImage(Image srcImg, int w, int h){
		BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = resizedImg.createGraphics();

		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(srcImg, 0, 0, w, h, null);
		g2.dispose();

		return resizedImg;
	}
	
	public VentanaConfigurarDescuento(final JFrame MenuSocio, JFrame ventanaAnterior, final Alquiler a,Articulo a1) {
		ventanaQueMeLlama = ventanaAnterior;

		try {
			controllerAlquiler = new ControllerAlquiler();
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
		
		setResizable(false);
		setTitle("Confirmación de descuento");


		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 962, 540);

		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		final JLabel background;
		ImageIcon img = new ImageIcon("."+File.separator+"src"+File.separator+"resources"+File.separator+"confirmarAlquiler.png");
		Image im = img.getImage();
		im = getScaledImage(im, 960, 540);
		ImageIcon finalImg= new ImageIcon(im);
		background = new JLabel("", finalImg, JLabel.CENTER);
		background.setBounds(100,100,450, 300);



		JLabel imagen;
		ImageIcon imge = new ImageIcon("."+File.separator+"src"+File.separator+"resources"+File.separator+a.getAlquilado().getCaratula());
		Image imgen = imge.getImage();
		imgen = getScaledImage(imgen, 200, 270);
		ImageIcon finalImgen = new ImageIcon(imgen);
		imagen = new JLabel("", finalImgen, JLabel.CENTER);
		imagen.setBounds(150, 125, 200, 270);
		background.add(imagen);



		JLabel lblArtculo = new JLabel("Artículo: ");
		lblArtculo.setBounds(450,70,150,150);
		lblArtculo.setFont(new Font("Times New Roman", Font.BOLD, 22));
		lblArtculo.setForeground(Color.WHITE);;
		background.add(lblArtculo);

		JLabel label = new JLabel("" + a.getAlquilado().getNombre());
		label.setBounds(540,70,500,150);
		label.setFont(new Font("Times New Roman", Font.BOLD, 22));
		label.setForeground(Color.WHITE);
		background.add(label);

		JLabel lblPrecio = new JLabel("Precio: ");
		lblPrecio.setBounds(450,120,150,150);
		lblPrecio.setFont(new Font("Times New Roman", Font.BOLD, 22));
		lblPrecio.setForeground(Color.WHITE);
		background.add(lblPrecio);

		JLabel label_1 = new JLabel("" + a.getCoste() + "€");
		label_1.setBounds(530,120,500,150);
		label_1.setFont(new Font("Times New Roman", Font.BOLD, 22));
		label_1.setForeground(Color.WHITE);
		background.add(label_1);

		JLabel lblFechaDevolucin = new JLabel("Fecha devolución: ");
		lblFechaDevolucin.setBounds(450,170,300,150);
		lblFechaDevolucin.setFont(new Font("Times New Roman", Font.BOLD, 22));
		lblFechaDevolucin.setForeground(Color.WHITE);;
		background.add(lblFechaDevolucin);

		JComboBox comboBox = new JComboBox<String>();
		comboBox.setBounds(630,170,150,150);
		background.add(comboBox);

		Date date = new Date();
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		String fechaInicio = localDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));


		for (int i = 0;i<8;i++) {

			LocalDate newDate = localDate.plusDays(i+1);
			String str = newDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
			comboBox.addItem(str);

		}

		//String itemSeleccionado = null;



		//	TextField tf = null;
		//itemSeleccionado = (String)comboBox.getSelectedItem();

		/*JLabel label_2 = new JLabel("" + a.getFecha_fin());
		label_2.setBounds(630,170,500,150);
		label_2.setFont(new Font("Times New Roman", Font.BOLD, 22));
		label_2.setForeground(Color.WHITE);;
		background.add(label_2);*/

		JLabel lblDescuento = new JLabel("Descuento: ");
		lblDescuento.setBounds(450,220,300,150);
		lblDescuento.setFont(new Font("Times New Roman", Font.BOLD, 22));
		lblDescuento.setForeground(Color.WHITE);
		background.add(lblDescuento);

		JTextField txtAñadirDescuento = new JTextField("Añada aqui el porcentaje de descuento");
		txtAñadirDescuento.setBounds(560,220,500,150);
		txtAñadirDescuento.setFont(new Font("Times New Roman", Font.BOLD, 22));
		txtAñadirDescuento.setForeground(Color.WHITE);;
		background.add(txtAñadirDescuento);


		JLabel lblSaldo = new JLabel("Precio tras el descuento: ");
		lblSaldo.setBounds(450,270,300,150);
		lblSaldo.setFont(new Font("Times New Roman", Font.BOLD, 22));
		lblSaldo.setForeground(Color.WHITE);
		background.add(lblSaldo);		


		JLabel label_4 = new JLabel("" +  (a.getCoste() - ((a.getCoste()/100) * Integer.parseInt(txtAñadirDescuento.getText()))) + "€" );
		label_4.setBounds(600,270,500,150);
		label_4.setFont(new Font("Times New Roman", Font.BOLD, 22));
		label_4.setForeground(Color.WHITE);;
		background.add(label_4);


		JButton bConfirmar = new JButton("");
		bConfirmar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String fechaFin = (String) comboBox.getSelectedItem();
				if(a1.getDescuento() > 100) {
					JOptionPane.showMessageDialog(null, "El descuento sobrepasa los limites",
							"Descuento superior al 100%", JOptionPane.ERROR_MESSAGE);
					MenuSocio.setVisible(true);
					VentanaConfigurarDescuento.this.dispose();
				} else {
					int eleccion = JOptionPane.showConfirmDialog(null, "¿Esta seguro?");
					if(eleccion == 0) {
						//update monedero
						System.out.println("updateMonedero");
						//controllerAlquiler.updateMonedero(nombreUsuario, controllerAlquiler.selectSocio(nombreUsuario).getMonedero()-a.getCoste());
						//s1.setMonedero(s1.getMonedero() - a.getCoste());
						System.out.println("insertarAlquiler");
						Videojuego v;
						Pelicula p;
						boolean pv;
						//String nombre, double precio, String sinopsis, String genero, String fecha_estr, double puntuacion, String caratula, double coste, String nombreUsuario, boolean pv
						if(a1.getClassName().equals("Videojuego")) {
							v = (Videojuego)a1;
							pv = false;
							System.out.println(pv);
							//controllerAlquiler.insertarAlquiler(v.getNombre(),v.getPrecio(),v.getDescripcion(), v.getCategoria(),v.getFecha_lan(), v.getPuntuacion(),v.getCaratula(), a.getCoste(), s1.getNombre(), pv, fechaFin, fechaInicio);

						}else if(a1.getClassName().equals("Pelicula"))
						{	
							p = (Pelicula)a1;
							pv = true;
							System.out.println(pv);
							//System.out.println(p.getNombre() + "-" +p.getPrecio() + "-" +p.getSinopsis() + "-" +p.getGenero() + "-" +p.getFecha_estr() + "-" +p.getPuntuacion() + "-" +p.getCaratula() + "-" + a.getCoste() + "-" + s1.getNombre() + "-" + pv);
							//controllerAlquiler.insertarAlquiler(p.getNombre(),p.getPrecio(),p.getSinopsis(), p.getGenero(),p.getFecha_estr(), p.getPuntuacion(),p.getCaratula(), a.getCoste(), s1.getNombre(), pv, fechaFin, fechaInicio);

						}
						JOptionPane.showMessageDialog(null, "Aplicado descuento correctamente!", "Descuento aplicado con exito", JOptionPane.INFORMATION_MESSAGE);
						MenuSocio.setVisible(true);
						VentanaConfigurarDescuento.this.dispose();
					} 
				}
				repaint();

			}
		});
		bConfirmar.setBounds(735,452,142,50);
		bConfirmar.setFont(new Font("AppleGothic", Font.PLAIN, 22));
		bConfirmar.setForeground(Color.WHITE);
		bConfirmar.setOpaque(false);
		bConfirmar.setContentAreaFilled(false);
		bConfirmar.setBorderPainted(false);
		background.add(bConfirmar);

		JButton bCancelar = new JButton("");

		bCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ventanaQueMeLlama.setVisible(true);
				VentanaConfigurarDescuento.this.dispose();
			}
		});
		bCancelar.setBounds(80,452,142,50);
		bCancelar.setFont(new Font("AppleGothic", Font.PLAIN, 22));
		bCancelar.setForeground(Color.WHITE);
		bCancelar.setOpaque(false);
		bCancelar.setContentAreaFilled(false);
		bCancelar.setBorderPainted(false);
		background.add(bCancelar);

		getContentPane().add(background);
		background.setBorder(new EmptyBorder(5, 5, 5, 5));
		background.setLayout(new BorderLayout(0, 0));

	}

}