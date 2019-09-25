package com.colsubsidio.salud.transversal.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.TimeZone;
import org.springframework.stereotype.Component;

/**
 *
 * @author Usuario
 */
@Component
public class HandleDate {

	/**
	 *
	 * @return
	 */
	public Date getFechaHora() {
		Calendar calendar = Calendar.getInstance();
		return calendar.getTime();
	}

	/**
	 *
	 * @param cadena
	 * @return
	 */
	public Calendar retornaCalendar(String cadena) {
		Calendar cal = null;
		try {
			DateFormat formatter;
			Date date;
			formatter = new SimpleDateFormat("dd-MM-yyyy");
			date = (Date) formatter.parse(cadena);
			cal = Calendar.getInstance();
			cal.setTime(date);
		} catch (ParseException e) {

		}
		return cal;
	}

	/**
	 *
	 * @param cadena
	 * @return
	 */
	public Calendar retornaCalendarHora(String cadena) {
		Calendar cal = null;
		try {
			DateFormat formatter;
			Date date;
			formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			date = (Date) formatter.parse(cadena + ":00");
			cal = Calendar.getInstance();
			cal.setTime(date);
		} catch (ParseException e) {
		}
		return cal;
	}

	/**
	 *
	 * @param cadena
	 * @return
	 */
	public Date retornaDate(String cadena) {
		Date date = null;
		try {
			DateFormat formatter;

			formatter = new SimpleDateFormat("dd-mm-yyyy");
			date = (Date) formatter.parse(cadena);
		} catch (ParseException e) {
		}
		return date;
	}

	/**
	 *
	 * @param cadena
	 * @return
	 */
	public Timestamp retornaTimeStamp(String cadena) {
		Calendar cal = this.retornaCalendar(cadena);
		long date = cal.getTime().getTime();
		Timestamp timeStamp = new Timestamp(date);
		return timeStamp;
	}

	/**
	 *
	 * @return
	 */
	public Timestamp getFechaHoraTimeStamp(int dias) {
		TimeZone timeZone = TimeZone.getTimeZone("America/Bogota");
		Calendar calendar = Calendar.getInstance(timeZone);
		calendar.add(Calendar.DAY_OF_YEAR, dias != 0 ? (-dias) : 0);
		long date = calendar.getTime().getTime();
		Timestamp timeStamp = new Timestamp(date);
		return timeStamp;
	}

	/**
	 *
	 * @param t
	 * @return
	 */
	public String DevuelveFormato(Timestamp t) {
		String s = "";
		// if(t.toString().compareTo("") != 0){
		if (t != null) {
			s = new SimpleDateFormat("dd-MM-yyyy").format(t);
		}
		return s;
	}

	/**
	 *
	 * @param t
	 * @return
	 */
	public String DevuelveFormatoHora(Timestamp t) {
		String s = "";
		// if(t.toString().compareTo("") != 0){
		if (t != null) {
			s = new SimpleDateFormat("HH:mm:ss").format(t);
		}
		return s;
	}

	/**
	 *
	 * @param FechaHora
	 * @return
	 * @throws ParseException
	 */
	public Timestamp FechaHoraToTimestamp(String FechaHora) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
		Date parsedTimeStamp = dateFormat.parse(FechaHora);
		Timestamp timestamp = new Timestamp(parsedTimeStamp.getTime());
		return timestamp;
	}

	/**
	 *
	 * @param t
	 * @return
	 */
	public String DevuelveAnio(Timestamp t) {
		String s = "";
		// if(t.toString().compareTo("") != 0){
		if (t != null) {
			s = new SimpleDateFormat("yyyy").format(t);
		}
		return s;
	}

	/**
	 *
	 * @param t
	 * @return
	 */
	public String FechaLetras(Timestamp t) {
		String s = "";
		// if(t.toString().compareTo("") != 0){
		if (t != null) {
			s = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy").format(t);
		}
		return s;
	}

	/**
	 *
	 * @param cadena
	 * @return
	 */
	public Timestamp StringToTimeStamp(String cadena) {
		Calendar cal = this.retornaCalendar(cadena);
		long date = cal.getTime().getTime();
		Timestamp timeStamp = new Timestamp(date);
		return timeStamp;
	}

	/**
	 *
	 * @param cadena
	 * @return
	 */
	public Timestamp StringHoraToTimeStamp(String cadena) {
		Calendar cal = this.retornaCalendarHora(cadena);
		long date = cal.getTime().getTime();
		Timestamp timeStamp = new Timestamp(date);
		return timeStamp;
	}

	/**
	 *
	 * @param t
	 * @return
	 */
	public String FechaLetras(String t) {
		String s = "";
		// if(t.toString().compareTo("") != 0){
		if (t != null) {
			s = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy").format(StringToTimeStamp(t));
		}
		return s;
	}

	/**
	 *
	 * @return
	 */
	public String getCadenaArchivo(int dias) {
		String nombre = this.getFechaHoraTimeStamp(dias).toString().replace("-", "").replace(":", "").replace(".", "")
				.replace(" ", "");
		nombre = nombre.substring(0, 12);
		return nombre;
	}

	/**
	 *
	 * @return
	 */
	public String geCadena(int dias) {
		String nombre = this.getFechaHoraTimeStamp(dias).toString().replace("-", "").replace(":", "").replace(".", "")
				.replace(" ", "");
		return nombre;
	}

	/**
	 *
	 * @param t1
	 * @param t2
	 * @return
	 */
	public int compare(Timestamp t1, Timestamp t2) {

		long l1 = t1.getTime();
		long l2 = t2.getTime();
		if (l2 > l1) {
			return 1;
		} else if (l1 > l2) {
			return -1;
		} else {
			return 0;
		}
	}

	/**
	 *
	 * @param fecha_nac
	 * @return
	 */
	public String calcularEdad(String fecha_nac) {
		Date fechaActual = new Date();
		SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
		String hoy = formato.format(fechaActual);
		String[] dat1 = fecha_nac.split("-");
		String[] dat2 = hoy.split("-");
		int anos = Integer.parseInt(dat2[2]) - Integer.parseInt(dat1[2]);
		int mes = Integer.parseInt(dat2[1]) - Integer.parseInt(dat1[1]);
		if (mes < 0) {
			anos = anos - 1;
		} else if (mes == 0) {
			int dia = Integer.parseInt(dat1[0]) - Integer.parseInt(dat2[0]);
			if (dia > 0) {
				anos = anos - 1;
			}
		}
		return String.valueOf(anos);
	}

	public Calendar retornaFechaY_M_D(String cadena) {
		Calendar cal = null;
		try {
			DateFormat formatter;
			Date date;
			formatter = new SimpleDateFormat("yyyy-MM-dd");
			date = (Date) formatter.parse(cadena);
			cal = Calendar.getInstance();
			cal.setTime(date);
		} catch (ParseException e) {

		}
		return cal;
	}

	public Timestamp retornaTimeStampY_M_d(String cadena) {
		Calendar cal = this.retornaFechaY_M_D(cadena);
		long date = cal.getTime().getTime();
		Timestamp timeStamp = new Timestamp(date);
		return timeStamp;
	}
}
