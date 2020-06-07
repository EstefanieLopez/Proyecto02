package Proyect02;
import java.util.Date;
public class Alumno {
		private int id;
		private String nombre;
		private String telefono;
		private String correo;
		public Alumno() {
		}
		public Alumno(int id, String carne, String nombre, String telefono, String correo) {
			this.id = id;
			this.nombre = nombre;
			this.telefono = telefono;
			this.correo = correo;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getNombre() {
			return nombre;
		}
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
		public String getTelefono() {
			return telefono;
		}
		public void setTelefono(String telefono) {
			this.telefono = telefono;
		}
		public String getCorreo() {
		return correo;
		}
		public void setCorreo(String correo) {
		this.correo = correo;
		}
		public String toString() {
			return "Persona [id=" + id + ", carne: " +  ", nombre = " + nombre.trim() + ", telefono = " + telefono.trim() + ", correo = " + correo.trim() + "]";
		}
		public void setFechaNacimiento(Date date) {
		}
		public byte[] getBytesFechaNacimiento() {
		return null;
		}
		public Date getFechaNacimiento() {
			return null;
		}

		public void setBytesFechaNacimiento(byte[] bFecha) {
			
		}

		public void setBytesNombre(byte[] bNombre) {
			
		}

		public void setCarne(int readInt) {
		
		}

	}

