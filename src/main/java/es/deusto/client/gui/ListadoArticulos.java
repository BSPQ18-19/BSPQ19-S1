package es.deusto.client.gui;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import es.deusto.client.data.Alquiler;
import es.deusto.client.data.Articulo;
import es.deusto.client.data.Pelicula;
import es.deusto.client.data.Videojuego;
import es.deusto.server.dto.ArticuloDTO;
import es.deusto.server.dto.PeliculaDTO;
import es.deusto.server.dto.SocioDTO;
import es.deusto.server.dto.VideojuegoDTO;

/**
 * Clase de la ventana ListadoArticulos.
 */
public class ListadoArticulos extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JFrame MenuSocio;

	//	public static void main(String[] args) {
	//		EventQueue.invokeLater(new Runnable() {
	//			public void run() {
	//				try {
	//					ListadoArticulos window = new ListadoArticulos(null, null, null, null);
	//					window.setVisible(true);
	//				} catch (Exception e) {
	//					e.printStackTrace();
	//				}
	//			}
	//		});
	//	}

	
	/**
	 * Crea tantos botones como articulos haya en la lista y se añaden al background.
	 * @param background
	 * @param articulos
	 * @param VentanaAnterior
	 * @param iniciado
	 * @param lblSaldo
	 */
	private void crearBotones(JLabel background, List<ArticuloDTO> articulos, JFrame VentanaAnterior, SocioDTO iniciado, JLabel lblSaldo)
	{
		int distancia = 128;
		for (int i = 0; i < articulos.size(); i++) {

			JButton btnJuego = new JButton();
			final Articulo a1 = getArticuloDeDTO(articulos.get(i));
			btnJuego.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					
					Calendar fecha = new GregorianCalendar();
					fecha.add(Calendar.MONTH, 1);
					
					int año = fecha.get(Calendar.YEAR);
			        int mes = fecha.get(Calendar.MONTH);
			        int dia = fecha.get(Calendar.DAY_OF_MONTH);
			        
			        String fechaIni = "" + dia +"/" + mes + "/"+ año +"";
			        
			        fecha.add(Calendar.DAY_OF_YEAR, 10);
			        
			        int añoF = fecha.get(Calendar.YEAR);
			        int mesF = fecha.get(Calendar.MONTH);
			        int diaF = fecha.get(Calendar.DAY_OF_MONTH);
			        String fechaFin = "" + diaF +"/" + mesF + "/"+ añoF +"";
					Alquiler a = new Alquiler(a1, a1.getPrecio() , fechaIni ,  fechaFin, false, a1.getNombre());

					VentanaConfirmacion confirm = new VentanaConfirmacion(VentanaAnterior , ListadoArticulos.this, iniciado.getNombre(), a,a1, lblSaldo);
					confirm.setVisible(true);
					setVisible(false);
				}
			});
			btnJuego.setBounds(distancia, 200, 87, 120);
			background.add(btnJuego);
			ImageIcon img1 = new ImageIcon("."+File.separator+"src"+File.separator+"resources"+File.separator+articulos.get(i).getCaratula());

			Image image = img1.getImage();
			image = getScaledImage(image, 87, 120);
			ImageIcon finalImage = new ImageIcon(image);

			btnJuego.setIcon(finalImage);
			distancia = distancia+120;


		}
	}
	
	/**
	 * Constructor de la ventana ListadoArticulos
	 * @param VentanaAnterior
	 * @param iniciado
	 * @param lblSaldo
	 * @param articulos
	 * @param articulos2
	 */
	public ListadoArticulos(final JFrame VentanaAnterior, final SocioDTO iniciado, final JLabel lblSaldo, List<ArticuloDTO> articulos, List<ArticuloDTO> articulos2) {
		MenuSocio = VentanaAnterior;
		
		setTitle("Artículos disponibles para alquilar");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 960, 540);
		final JLabel background;
		ImageIcon img = new ImageIcon("."+File.separator+"src"+File.separator+"resources"+File.separator+"articulos.png");
		Image im = img.getImage();
		im = getScaledImage(im, 960,540);
		ImageIcon finalImg= new ImageIcon(im);
		getContentPane().setLayout(null);
		background = new JLabel("", finalImg, JLabel.CENTER);
		background.setBounds(0, 0, 960, 518);
		getContentPane().add(background);
		background.setLayout(null);

		JButton btnVolver = new JButton("");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Volver");
				MenuSocio.setVisible(true);
				articulos.clear();
				ListadoArticulos.this.dispose();
			}
		});
		btnVolver.setBounds(780, 455, 140, 50);
		background.add(btnVolver);
		btnVolver.setOpaque(false);
		btnVolver.setContentAreaFilled(false);
		btnVolver.setBorderPainted(false);

		JComboBox<String> comboBox = new JComboBox<String>();
		background.add(comboBox);
		comboBox.setBounds(750,50,100,30);

		comboBox.addItem("Todo");
		comboBox.addItem("Videojuegos");
		comboBox.addItem("Películas");
		
		if(articulos2.size()==0)
		{
			crearBotones(background, articulos, VentanaAnterior,iniciado,lblSaldo);
		}else
		{
			crearBotones(background, articulos2, VentanaAnterior, iniciado, lblSaldo);
		}
		articulos2.clear();
		comboBox.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.out.println((String)comboBox.getSelectedItem());
				if(((String)comboBox.getSelectedItem()).equals("Películas"))
				{
					for(ArticuloDTO a: articulos)
					{
						System.out.println(a.getClassName());
						if(a.getClassName().equals("PeliculaDTO"))
						{
							System.out.println("añadido");
							articulos2.add(a);
						}
					}
					
					ListadoArticulos ls = new ListadoArticulos(MenuSocio, iniciado, lblSaldo, articulos, articulos2);
					ListadoArticulos.this.dispose();
					ls.setVisible(true);
				}else if(((String)comboBox.getSelectedItem()).equals("Videojuegos"))
				{
					for(ArticuloDTO a : articulos)
					{
						if(a.getClassName().equals("VideojuegoDTO"))
						{
							articulos2.add(a);
						}
					}
					ListadoArticulos ls = new ListadoArticulos(MenuSocio, iniciado, lblSaldo, articulos, articulos2);
					ListadoArticulos.this.dispose();
					ls.setVisible(true);
				}else
				{
					for(ArticuloDTO a : articulos)
					{
						articulos2.add(a);
					}
					ListadoArticulos ls = new ListadoArticulos(MenuSocio, iniciado, lblSaldo, articulos, articulos2);
					ListadoArticulos.this.dispose();
					ls.setVisible(true);
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
	 * Devuelve un nuevo Articulo creado con los atributos del ArticuloDTO introducido por parametro.
	 * @param artDTO
	 * @return Articulo
	 */
	public Articulo getArticuloDeDTO(ArticuloDTO artDTO) {
		if(artDTO.getClassName().equals("VideojuegoDTO")) {
			VideojuegoDTO juego = (VideojuegoDTO) artDTO;
			return new Videojuego(juego.getNombre(), juego.getPrecio(), juego.getDescripcion(),
					juego.getCategoria(), juego.getFecha_lan(), juego.getPuntuacion(), juego.getCaratula(), juego.getDescuento());
		} else {
			PeliculaDTO peli = (PeliculaDTO) artDTO;
			return new Pelicula(peli.getNombre(), peli.getPrecio(), peli.getSinopsis(), peli.getGenero(), 
					peli.getFecha_estr(), peli.getPuntuacion(), peli.getCaratula(),peli.getDescuento());
		}
	}
}
