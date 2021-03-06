package com.bambinocare;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeTest {
	
	public static void main(String[] args) {
		//getFinalHour("18:30", 10.5);
		/***************************************************/
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	        String dateInString = "26/12/2017";
	        String hour = "23:30";
			Date date = formatter.parse(dateInString);
			System.out.println(date);
			System.out.println(getBookingDateTime(date,hour , 4.0, false));
			System.out.println(getBookingDateTime(date, hour, 12.350000000000001, true));
			//System.out.println(isValideDate(date,"21:36"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static String getFinalHour(String initialTime, Double duration) {
		//Calcular horario
		String[] initialHourAux = initialTime.split(":");

		Integer hoursToAdd = duration.intValue();
		Integer minutesToAdd = (int) ((duration - hoursToAdd) * 60);
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(2017, 12, 12, Integer.parseInt(initialHourAux[0]), Integer.parseInt(initialHourAux[1]));
		
		calendar.add(Calendar.HOUR, hoursToAdd);
		calendar.add(Calendar.MINUTE, minutesToAdd);
		
		String finalHour = String.valueOf(calendar.get(Calendar.HOUR));
		String finalMinute = String.valueOf(calendar.get(Calendar.MINUTE));
		
		if(finalMinute.equals("0")) {
			finalMinute = "00";
		}
		
		String finalTime = finalHour + ":" + finalMinute;
		
		System.out.println(finalTime);
		
		return finalTime;
	}
	
	public Date getDate(Date date, int days) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, days);

		return calendar.getTime();
	}
	
	public static boolean isValideDate (Date date, String hour) {
		
		boolean isValideDate = false;
		
		String[] initialHourAux = hour.split(":");
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(initialHourAux[0]));
		calendar.set(Calendar.MINUTE, Integer.parseInt(initialHourAux[1]));

		System.out.println(calendar.getTime());
		
		Calendar newCalendar = Calendar.getInstance();
		newCalendar.add(Calendar.HOUR_OF_DAY, 24);
		
		System.out.println(newCalendar.getTime());
		
		if(calendar.after(newCalendar)) {
			isValideDate = true;
		}
		
		return isValideDate;
		
	}
	
	public static Date getBookingDateTime(Date bookingDate, String hour, Double duration, boolean isFinalDate) {
		
		Calendar date = Calendar.getInstance();
		
		Calendar originDate = Calendar.getInstance();
		originDate.setTime(bookingDate);
		String[] originTimeArr = hour.split(":");
		
		date.set(originDate.get(Calendar.YEAR), originDate.get(Calendar.MONTH),
				originDate.get(Calendar.DAY_OF_MONTH), Integer.parseInt(originTimeArr[0]),
				Integer.parseInt(originTimeArr[1]),0);
		
		if(isFinalDate){
			Integer hours = duration.intValue();
			Double minutesAux = (duration - hours) * 60;
			Integer minutes = minutesAux.intValue();
			date.add(Calendar.HOUR_OF_DAY, hours);
			date.add(Calendar.MINUTE, minutes);
		}
		
		return date.getTime();
	}
}
