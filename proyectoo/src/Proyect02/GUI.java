package Proyect02;
import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import java.awt.Font;


public class GUI extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Scanner sc = new Scanner(System.in);
    RandomAccessFile fichero = null, entidades = null, atributos = null;
    private final String rutaBase = "src";
	private final String rutaEntidades = "entidades.dat";
	private final String rutaAtributos = "atributos.dat";
	private final int totalBytes = 83, bytesEntidad = 47, bytesAtributo = 43;
	private final static String formatoFecha = "dd/MM/yyyy";
	private JPanel contentPane;
	private JTable tbData;
	private DefaultTableModel tableModel;	
	static DateFormat format = new SimpleDateFormat(formatoFecha);
	private List<Entidad> listaEntidades = new ArrayList<>();
	public static void main1(String[] args) {
		EventQueue.invokeLater(new Runnable() {
		public void run() {
		try {
		GUI frame = new GUI();
		frame.setVisible(true);
		} catch (Exception e) {
		e.printStackTrace();
		}
		}
		});
	    }
		public static void main(String[] args) {
		GUI G = new GUI();
		if (G.validarDefinicion()) {
		G.menuDefinicion(true);
		} else {
		G.menuDefinicion(false);
		}
		System.exit(0); 
		}
		boolean validarDefinicion() {
		boolean res = false;
		try {
		entidades = new RandomAccessFile(rutaEntidades, "rw");
		atributos = new RandomAccessFile(rutaAtributos, "rw");
		long longitud = entidades.length();
		if (longitud <= 0) {
		System.out.println("NO HAY REGISTROS");
		res = false; 
		}
		if (longitud >= bytesEntidad) {
		entidades.seek(0);
		Entidad e;
		while (longitud >= bytesEntidad) {
		e = new Entidad();
		e.setIndice(entidades.readInt());
		byte[] bNombre = new byte[30]; 
		entidades.read(bNombre);
		e.setBytesNombre(bNombre);
		e.setCantidad(entidades.readInt());
		e.setBytes(entidades.readInt());
		e.setPosicion(entidades.readLong());
		entidades.readByte();
		longitud -= bytesEntidad;
		long longitudAtributos = atributos.length();
		     if (longitudAtributos <= 0) {
		       System.out.println("NO HAY REGISTROS");
		       res = false; 
		          break;
	       }
		atributos.seek(e.getPosicion());
		Atributo a;
	    longitudAtributos = e.getCantidad() * bytesAtributo;
	    
	    while (longitudAtributos >= bytesAtributo) {
		a = new Atributo();
		a.setIndice(atributos.readInt());
	    byte[] bNombreAtributo = new byte[30]; 
		atributos.read(bNombreAtributo);
		a.setBytesNombre(bNombreAtributo);
	    a.setValorTipoDato(atributos.readInt());
		a.setLongitud(atributos.readInt());
		a.setNombreTipoDato();
		atributos.readByte();
	    e.setAtributo(a);
		longitudAtributos -= bytesAtributo;
		}
		listaEntidades.add(e);
		}
		if (listaEntidades.size() > 0) {
		res = true;
		}
		}
		} catch (Exception e) {
		e.printStackTrace();
		}
		return res;
		}
		
		private void mostrarEntidad(Entidad entidad) {
		System.out.println("INDICE: " + entidad.getIndice());
		System.out.println("NOMBRE: " + entidad.getNombre());
		System.out.println("CANTIDAD: " + entidad.getCantidad());
		System.out.println("ATRIBUTOS:");
		int i = 1;
		for (Atributo atributo : entidad.getAtributos()) {
		System.out.println("\tNo. " + i);
		System.out.println("\tNOMBRE: " + atributo.getNombre());
		System.out.println("\tTIPO DE DATO: " + atributo.getNombreTipoDato());
		if (atributo.isRequiereLongitud()) {
		System.out.println("\tLONGITUD: " + atributo.getLongitud());
		tableModel.addRow(new Object[] {i, entidad.getNombre(),atributo.getNombre(),atributo.getTipoDato()});
		
		}
		i++;}
		}
		private boolean agregarEntidad() {
			boolean resultado = false;
			try {
			Entidad entidad = new Entidad();
			entidad.setIndice(listaEntidades.size() + 1);
			System.out.println("INGRESE ENTIDAD");
			String strNombre = "";
			int longitud = 0;
			do {
			strNombre = sc.nextLine();
			longitud = strNombre.length();
			if (longitud < 2 || longitud > 30) {
			System.out.println("LA LONG. DEL NOMBRE NO ES VALIDA [3 - 30]");
			} else {
			if (strNombre.contains(" ")) {
			System.out
			.println("EL NOMBRE NO TIENE QUE TENER ESPACIOS");
			longitud = 0;
			}
			}
			} while (longitud < 2 || longitud > 30);
			entidad.setNombre(strNombre);
			System.out.println("ATRIBUTOS DE LA ENTIDAD");
			int bndDetener = 0;
			do {
			Atributo atributo = new Atributo();
			atributo.setIndice(entidad.getIndice());
			longitud = 0;
			System.out.println("ESCRABA EL NOMBRE DEL ATRIBUTO 2 " + (entidad.getCantidad() + 1));
			do {
			strNombre = sc.nextLine();
			longitud = strNombre.length();
			if (longitud < 2 || longitud > 30) {
			System.out.println("LA LON. NO ES VALIDA [3 - 30]");
			} else {
			if (strNombre.contains(" ")) {
			System.out.println(
			"EL NOMBRE NO TIENE QUE TENER ESPACIOS ");
			longitud = 0;
			}
			}
			} while (longitud < 2 || longitud > 30);
			atributo.setNombre(strNombre);
			System.out.println("Seleccione el tipo de dato");
			System.out.println(TipoDato.INT.getValue() + " .......... " + TipoDato.INT.name());
			System.out.println(TipoDato.LONG.getValue() + " .......... " + TipoDato.LONG.name());
			System.out.println(TipoDato.STRING.getValue() + " .......... " + TipoDato.STRING.name());
			System.out.println(TipoDato.DOUBLE.getValue() + " .......... " + TipoDato.DOUBLE.name());
			System.out.println(TipoDato.FLOAT.getValue() + " .......... " + TipoDato.FLOAT.name());
			System.out.println(TipoDato.DATE.getValue() + " .......... " + TipoDato.DATE.name());
			System.out.println(TipoDato.CHAR.getValue() + " .......... " + TipoDato.CHAR.name());
			atributo.setValorTipoDato(sc.nextInt());
			if (atributo.isRequiereLongitud()) {
			System.out.println("INGRESE LA LONGITUD");
			atributo.setLongitud(sc.nextInt());
			} else {
			atributo.setLongitud(0);
			}
			atributo.setNombreTipoDato();
			entidad.setAtributo(atributo);
			System.out.println("Desea agregar otro atributo presione cualquier numero, de lo contrario 0");
			bndDetener = sc.nextInt();
		    } while (bndDetener != 0);
		    System.out.println("Los datos a registrar son: ");
		    mostrarEntidad(entidad);
		    System.out.println("Presione 1 para guardar 0 para cancelar");
		    longitud = sc.nextInt();
			if (longitud == 1) {
			entidad.setPosicion(atributos.length());
			atributos.seek(atributos.length());
			for (Atributo atributo : entidad.getAtributos()) {
			atributos.writeInt(atributo.getIndice());
			atributos.write(atributo.getBytesNombre());
			atributos.writeInt(atributo.getValorTipoDato());
			atributos.writeInt(atributo.getLongitud());
			atributos.write("\t".getBytes()); 
			}
			entidades.writeInt(entidad.getIndice());
			entidades.write(entidad.getBytesNombre());
			entidades.writeInt(entidad.getCantidad());
			entidades.writeInt(entidad.getBytes());
			entidades.writeLong(entidad.getPosicion());
			entidades.write("\t".getBytes());
			listaEntidades.add(entidad);
			resultado = true;
		    } else {
			System.out.println("No se guardo la entidad debido a que el usuario decidio cancelarlo");
			resultado = false;
		    }
		    System.out.println("Presione una tecla para continuar...");
		    System.in.read();
			} catch (Exception e) {
			resultado = false;
		    e.printStackTrace();
		    }
		    return resultado;
		    }
		
		    private void modificarEntidad() {
			try {
		    int indice = 0;
		    while (indice < 1 || indice > listaEntidades.size()) {
			for (Entidad entidad : listaEntidades) {
			System.out.println(entidad.getIndice() + " ...... " + entidad.getNombre());
			}
			System.out.println("Seleccione la entidad que desea modificar");
			indice = sc.nextInt();
			}
			Entidad entidad = null;
			for (Entidad e : listaEntidades) {
			if (indice == e.getIndice()) {
			entidad = e;
			break;
			}
			}
			String nombreFichero = formarNombreFichero(entidad.getNombre());
			fichero = new RandomAccessFile(rutaBase + nombreFichero, "rw");
			long longitudDatos = fichero.length();
			fichero.close();
			if (longitudDatos > 0) {
			System.out.println("No es posible modificar la entidad debido a que ya tiene datos asociados");
			} else {
			boolean bndEncontrado = false, bndModificado = false;
			entidades.seek(0);
			long longitud = entidades.length();
			int registros = 0, salir = 0, i;
			Entidad e;
			byte[] tmpBytes;
			while (longitud > totalBytes) {
			e = new Entidad();
			e.setIndice(entidades.readInt());
			tmpBytes = new byte[30];
			entidades.read(tmpBytes);
			e.setBytesNombre(tmpBytes);
			e.setCantidad(entidades.readInt());
			e.setBytes(entidades.readInt());
			e.setPosicion(entidades.readLong());
			if (entidad.getIndice() == e.getIndice()) {
			System.out.println("Si no desea modificar el campo presione enter");
			System.out.println("Ingrese el nombre");
			String tmpStr = "";
			int len = 0;
			long posicion;
			do {
			tmpStr = sc.nextLine();
			len = tmpStr.length();
			if (len == 1 || len > 30) {
			System.out.println("La longitud del nombre no es valida [2 - 30]");
			}
			} while (len == 1 || len > 30);
			if (len > 0) {
			e.setNombre(tmpStr);
			posicion = registros * totalBytes;
			fichero.seek(posicion);
			byte[]tmpArrayByte;
			String linea ="";
			for (Proyect02.Atributo atributo: entidad.getAtributos()) {
				linea+= atributo.getNombre().toString().trim()+"\t\t";
				tableModel = new DefaultTableModel();
				tableModel.addColumn(linea);	
			}
			fichero.skipBytes(4); 
			fichero.write(e.getBytesNombre());
			bndModificado = true;
			}
			i = 1;
			for (Atributo a : entidad.getAtributos()) {
			System.out.println("Modificando atributo 1");
			System.out.println(a.getNombre().trim());
			}
			break;
			}
			registros++;
			longitud -= totalBytes;
			}
		    }
		    } catch (Exception e) {
		    System.out.println("Error: " + e.getMessage());
			}
		    }
		    void menuDefinicion(boolean mostrarAgregarRegistro) {
		    int opcion = 1;
		    while (opcion != 0) {
			System.out.println("Elija su opcion");
			System.out.println("1 ........ Agregar entidad");
			System.out.println("2 ........ Modificar entidad");
			if (mostrarAgregarRegistro) {
			System.out.println("3 ........ Listar entidades");
			}else {	
			}
			if (mostrarAgregarRegistro) {
			System.out.println("4 ........ Agregar registros");
			}
			System.out.println("5 ........ Borrar bases de datos");
			System.out.println("0 ........ Salir");
			opcion = sc.nextInt();
			switch (opcion) {
			case 0:
			JOptionPane.showConfirmDialog(null, "GRACIAS, VUELVA PRONTO");
			break;
			case 1:
			if (agregarEntidad()) {
			JOptionPane.showConfirmDialog(null, "SE AGREGO EXITOSAMENTE");
			mostrarAgregarRegistro = true;
			}
			break;
			case 2:
				modificarEntidad();
				break;
				case 3:	
					if (listaEntidades.size() > 0) {
					int tmpInt = 0;
					while(tableModel.getRowCount() > 0) {
						tableModel.removeRow(0);
					}
					System.out.println("Desea imprimir los detalles. Si, presione 1. No, presione 0?");
					tmpInt = sc.nextInt();
			        if (tmpInt == 1) {
			        	for (Entidad entidad : listaEntidades) {
			        		mostrarEntidad(entidad);
			        		}
			        	} else {
			        		for (Entidad entidad : listaEntidades) {
			        			System.out.println("Indice: " + entidad.getIndice());
			        			System.out.println("Nombre: " + entidad.getNombre());
			        			System.out.println("Cantidad de atributos: " + entidad.getCantidad());				
			        			}
			        		}
			        } else {
			        	System.out.println("No hay entidades registradas");
			        	}
					break;
					case 4:
						int indice = 0;
						while (indice < 1 || indice > listaEntidades.size()) {
							for (Entidad entidad : listaEntidades) {
								System.out.println(entidad.getIndice() + " ...... " + entidad.getNombre());
								}
							System.out.println("Seleccione la entidad que desea trabajar");
							indice = sc.nextInt();
							}
						iniciar(indice);
						break;
						case 5:
							
							int confirmar = 0;
							System.out.println("QUIERE ELIMINAR DATOS");
							confirmar = sc.nextInt();
							if (confirmar == 1) {
								cerrarArchivos();
								if (borrarArchivos()) {
									listaEntidades = null;
									listaEntidades = new ArrayList<>();
									mostrarAgregarRegistro = false;
									System.out.println("Archivos borrados");
									}
								
								}
							break;
							default:
								System.out.println("Opcion no valida");
								break;
								}
			}
		    }
		    private void cerrarArchivos() {
		    	if (fichero != null) {
		    		try {
		    			fichero.close();
		    			} catch (IOException e) {
		    				e.printStackTrace();
		    				}
		    		}
		    	if (atributos != null) {
		    		try {
		    			atributos.close();
		    			} catch (IOException e) {
		    				e.printStackTrace();
		    				}
		    		}
		    	if (entidades != null) {
		    		try {
		    			entidades.close();
		    			} catch (IOException e) {
		    				e.printStackTrace();
		    				}
		    		}
		    	}
		    private boolean borrarArchivos() {
		    	boolean res = false;
		    	try {
		    		File file;
		    		for (Entidad entidad : listaEntidades) {
		    			file = new File(rutaBase + entidad.getNombre().trim() + ".dat");
		    			if (file.exists()) {
		    				file.delete();
		    				}
		    			file = null;
		    			}
		    		file = new File(rutaAtributos);
		    		if (file.exists()) {
		    			file.delete();
		    			}
		    		file = null;
		    		file = new File(rutaEntidades);
		    		if (file.exists()) {
		    			file.delete();
		    			}
		    		file = null;
		    		res = true;
		    		} catch (Exception e) {
		    			e.printStackTrace();
		    			}
		    	return res;
		    	}
		    private String formarNombreFichero(String nombre) {
		    	return nombre.trim() + ".dat";
		    	}
		    private void iniciar(int indice) {
		    	int opcion = 0;
		    	String nombreFichero = "";
		    	try {
		    		Entidad entidad = null;
		    		for (Entidad e : listaEntidades) {
		    			if (indice == e.getIndice()) {
		    				nombreFichero = formarNombreFichero(e.getNombre());
		    				entidad = e;
		    				break;
		    				}
		    			}
		    		fichero = new RandomAccessFile(rutaBase + nombreFichero, "rw");
		    		System.out.println("Bienvenido (a)");
		    		Atributo a = entidad.getAtributos().get(0);
		    		do {
		    			try {
		    				System.out.println("Seleccione su opcion");
		    				System.out.println("1.\t\tAgregar");
		    				System.out.println("2.\t\tListar");
		    				System.out.println("3.\t\tBuscar");
		    				System.out.println("4.\t\tModificar");
		    				System.out.println("0.\t\tRegresar al menu anterior");
		    				opcion = sc.nextInt();
		    				switch (opcion) {
		    				case 0:
		    					System.out.println("");
		    					break;
		    					case 1:
		    						grabarRegistro(entidad);
		    						break;
		    						case 2:
		    							listarRegistros(entidad);
		    							GUI G =new GUI();
		    							break;
		    							case 3:
		    								System.out.println("Se hara la busqueda en la primera columna ");
		    								System.out.println("Ingrese " + a.getNombre().trim() + " a buscar");
		    								break;
		    								case 4:
		    									System.out.println("Ingrese el carne a modificar: ");
		    									break;
		    									default:
		    										System.out.println("Opcion no valida");
		    										break;
		    				}
		    						    				
		    			} catch (Exception e) { 
		    				System.out.println("Error: " + e.getMessage());
		    				}
		    			} while (opcion != 0);
		    		fichero.close();
		    		} catch (Exception e) { 
		    			System.out.println("Error: " + e.getMessage());
	   
		    		}
		    	}
		
		    private boolean grabarRegistro(Entidad entidad) {
		    	boolean resultado = false;
		    	try {
		    		fichero.seek(fichero.length());
		    		boolean valido;
		    		byte[] bytesString;
		    		String tmpString = "";
		    		for (Atributo atributo : entidad.getAtributos()) {
		    			valido = false;
		    			System.out.println("Ingrese " + atributo.getNombre().trim());
		    			while (!valido) {
		    				try {
		    					switch (atributo.getTipoDato()) {
		    					case INT:
		    						int tmpInt = sc.nextInt();
		    						fichero.writeInt(tmpInt);
		    						sc.nextLine();
		    						break;
		    						case LONG:
		    							long tmpLong = sc.nextLong();
		    							fichero.writeLong(tmpLong);
		    							break;
		    							case STRING:
		    								int longitud = 0;
		    								do {
		    									tmpString = sc.nextLine();
		    									longitud = tmpString.length();
		    									if (longitud <= 1 || longitud > atributo.getLongitud()) {
		    										System.out.println("La longitud de " + atributo.getNombre().trim()
		    												+ " no es valida [1 - " + atributo.getLongitud() + "]");
		    										}
		    									} while (longitud <= 0 || longitud > atributo.getLongitud());
		    								bytesString = new byte[atributo.getLongitud()];
		    								for (int i = 0; i < tmpString.length(); i++) {
		    									bytesString[i] = (byte) tmpString.charAt(i);
		    									}
		    								fichero.write(bytesString);
		    								break;
		    								case DOUBLE:
		    									double tmpDouble = sc.nextDouble();
		    									fichero.writeDouble(tmpDouble);
		    									break;
		    									case FLOAT:
		    										float tmpFloat = sc.nextFloat();
		    										fichero.writeFloat(tmpFloat);
		    										break;
		    										case DATE:
		    											Date date = null;
		    											tmpString = "";
		    											while (date == null) {
		    												System.out.println("Formato de fecha: " + formatoFecha);
		    												tmpString = sc.nextLine();date = strintToDate(tmpString);
		    												}
		    											bytesString = new byte[atributo.getBytes()];
		    											for (int i = 0; i < tmpString.length(); i++) {
		    												bytesString[i] = (byte) tmpString.charAt(i);
		    												}
		    											fichero.write(bytesString);
		    											break;
		    											case CHAR:
		    												do {
		    													tmpString = sc.nextLine();
		    													longitud = tmpString.length();
		    													if (longitud < 1 || longitud > 1) {
		    														System.out.println("Solo se permite un caracter");
		    														}
		    													} while (longitud < 1 || longitud > 1);
		    												byte caracter = (byte) tmpString.charAt(0);
		    												fichero.writeByte(caracter);
		    												break;
		    												}
		    					valido = true;
		    					} catch (Exception e) {
		    						System.out.println(
		    								"Error " + e.getMessage() + " VUELVA A INGRESAR EL VALOR: ");
				sc.nextLine();
				}
				}
				} 
				fichero.write("\n".getBytes()); 
				resultado = true;
			     } catch (Exception e) {
				resultado = false;
				System.out.println("ERROR AL AGREGAR " + e.getMessage());
			   }
			   return resultado;
		       }
		        public void listarRegistros(Entidad entidad) {
			    try {
				long longitud = fichero.length();
				if (longitud <= 0) {
				System.out.println("NO HAY REGISTROS");
				return; 
				}
				fichero.seek(0);
				byte[] tmpArrayByte;
				String linea = "";
				for(Atributo atributo : entidad.getAtributos()) {
					linea += atributo.getNombre().toString().trim()+ "\t\t";
					tableModel = new DefaultTableModel();
					tableModel.addColumn(linea);
				}
				for (Atributo atributo : entidad.getAtributos()) {
				linea += atributo.getNombre().toString().trim() + "\t\t";
				
				}
				System.out.println(linea);
				while (longitud >= entidad.getBytes()) {
				linea = "";
				for (Atributo atributo : entidad.getAtributos()) {
				switch (atributo.getTipoDato()) {
				case INT:
				int tmpInt = fichero.readInt();
				linea += String.valueOf(tmpInt) + "\t\t";
				break;
				case LONG:
				long tmpLong = fichero.readLong();
				linea += String.valueOf(tmpLong) + "\t\t";
				break;
				case STRING:
				tmpArrayByte = new byte[atributo.getLongitud()];
				fichero.read(tmpArrayByte);
				String tmpString = new String(tmpArrayByte);
				linea += tmpString.trim() + "\t\t";
				break;
			    case DOUBLE:
				double tmpDouble = fichero.readDouble();
				linea += String.valueOf(tmpDouble) + "\t\t";
				break;
				case FLOAT:
					float tmpFloat = fichero.readFloat();
					linea += String.valueOf(tmpFloat) + "\t\t";
					break;
					case DATE:
						tmpArrayByte = new byte[atributo.getBytes()];
						fichero.read(tmpArrayByte);
			            tmpString = new String(tmpArrayByte);
				        linea += tmpString.trim() + "\t\t";
				        break;
				        case CHAR:
				        	char tmpChar = (char) fichero.readByte();
			             	linea += tmpChar + "\t\t";
			             	break;
			             	}
			             	}
				
				fichero.readByte();
				tableModel.addRow(new Object[] {linea});
				longitud -= entidad.getBytes();
				System.out.println(linea +""+ longitud);
				}
			    } catch (Exception e) {
			    System.out.println("Error: " + e.getMessage());
			    }
		        }
		        public Date strintToDate(String strFecha) {
		        Date date = null;
		        try {
		        date = format.parse(strFecha);
		        } catch (Exception e) {
		        date = null;
		        System.out.println("Error en fecha: " + e.getMessage());
		        } 
		        return date;
		        }
		        public String dateToString(Date date) {
		        String strFecha;
		        strFecha = format.format(date);
		        return strFecha;
		        }	
		      
		        public GUI() {
		        	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		    		setBounds(100, 100, 561, 344);
		    		contentPane = new JPanel();
		    		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		    		setContentPane(contentPane);
		    		contentPane.setLayout(null);
		    		
		    		tableModel = new DefaultTableModel();
		    		tableModel.addColumn("INDICE");
		    		tableModel.addColumn("ENTIDAD");
		    		tableModel.addColumn("NOMBRE");
		    		tableModel.addColumn("Dato");
		    		tableModel.addColumn("LONGITUD");
		    		tbData = new JTable();
		    		tbData.setModel(tableModel);
		    		JScrollPane scrollPane = new JScrollPane(tbData);
		    		scrollPane.setBounds(10, 81, 525, 213);
		    		contentPane.add(scrollPane);
		    		
		    		JTextPane textPane = new JTextPane();
		    		textPane.setBounds(196, 46, 6, 20);
		    		contentPane.add(textPane);
		    		
		    		JLabel lblNewLabel = new JLabel("Datos Ingresados");
		    		lblNewLabel.setFont(new Font("Times New Roman", Font.ITALIC, 39));
		    		lblNewLabel.setBounds(143, 11, 293, 55);
		    		contentPane.add(lblNewLabel);
		    	}
}
