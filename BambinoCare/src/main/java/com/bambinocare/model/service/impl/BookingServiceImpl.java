package com.bambinocare.model.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.bambinocare.model.ValidationModel;
import com.bambinocare.model.entity.BookingEntity;
import com.bambinocare.model.entity.ClientEntity;
import com.bambinocare.model.entity.EmergencyContactEntity;
import com.bambinocare.model.entity.NannyEntity;
import com.bambinocare.model.entity.ParameterEntity;
import com.bambinocare.model.entity.UserEntity;
import com.bambinocare.model.repository.BookingRepository;
import com.bambinocare.model.service.BambinoService;
import com.bambinocare.model.service.BookingService;
import com.bambinocare.model.service.ClientService;
import com.bambinocare.model.service.EmergencyContactService;
import com.bambinocare.model.service.EventTypeService;
import com.bambinocare.model.service.ParameterService;

@Service("bookingService")
public class BookingServiceImpl implements BookingService {

	@Autowired
	@Qualifier("clientService")
	private ClientService clientService;

	@Autowired
	@Qualifier("bambinoService")
	private BambinoService bambinoService;

	@Autowired
	@Qualifier("emergencyContactService")
	private EmergencyContactService emergencyContactService;

	@Autowired
	@Qualifier("eventTypeService")
	private EventTypeService eventTypeService;

	@Autowired
	@Qualifier("bookingRepository")
	private BookingRepository bookingRepository;

	@Autowired
	@Qualifier("parameterService")
	private ParameterService parameterService;

	@Override
	public List<BookingEntity> findAllBookings() {
		return bookingRepository.findAll();
	}

	@Override
	public BookingEntity createBooking(BookingEntity booking) {
		return bookingRepository.save(booking);
	}

	@Override
	public List<BookingEntity> findByUser(UserEntity user) {
		return bookingRepository.findByClientUser(user);
	}

	@Override
	public BookingEntity findByBookingId(Integer bookingId) {
		return bookingRepository.findByBookingId(bookingId);
	}

	@Override
	public boolean bookingExist(BookingEntity booking) {

		if (findByBookingId(booking.getBookingId()) != null) {
			return true;
		}

		return false;
	}

	@Override
	public BookingEntity findByBookingIdAndUser(Integer bookingId, UserEntity user) {
		return bookingRepository.findByBookingIdAndClientUser(bookingId, user);
	}

	@Override
	public BookingEntity findByBookingIdAndUserAndBookingStatusBookingStatusDescNotIn(Integer bookingId,
			UserEntity user, String bookingStatusDesc) {
		return bookingRepository.findByBookingIdAndClientUserAndBookingStatusBookingStatusDescNotIn(bookingId, user,
				bookingStatusDesc);
	}

	@Override
	public BookingEntity findByBookingIdAndBookingStatusBookingStatusDescNotIn(Integer bookingId,
			String... bookingStatusDesc) {
		return bookingRepository.findByBookingIdAndBookingStatusBookingStatusDescNotIn(bookingId, bookingStatusDesc);
	}

	@Override
	public List<BookingEntity> findByNannyAndBookingStatusBookingStatusDesc(NannyEntity nanny,
			String bookingStatusDesc) {
		return bookingRepository.findByNannyAndBookingStatusBookingStatusDesc(nanny, bookingStatusDesc);
	}

	@Override
	public BookingEntity findByBookingIdAndBookingStatusBookingStatusDescAndNanny(Integer bookingId,
			String bookingStatusDesc, NannyEntity nanny) {
		return bookingRepository.findByBookingIdAndBookingStatusBookingStatusDescAndNanny(bookingId, bookingStatusDesc,
				nanny);
	}

	@Override
	public BookingEntity findByBookingIdAndBookingStatusBookingStatusDesc(Integer bookingId, String bookingStatusDesc) {
		return bookingRepository.findByBookingIdAndBookingStatusBookingStatusDesc(bookingId, bookingStatusDesc);
	}

	@Override
	public void delete(BookingEntity bookingEntity) {
		bookingRepository.delete(bookingEntity);
	}

	@Override
	public String getFinalHour(String initialTime, Double duration) {
		// Calcular horario
		String[] initialHourAux = initialTime.split(":");

		Integer hoursToAdd = duration.intValue();
		Integer minutesToAdd = (int) ((duration - hoursToAdd) * 60);

		Calendar calendar = Calendar.getInstance();
		calendar.set(2017, 12, 12, Integer.parseInt(initialHourAux[0]), Integer.parseInt(initialHourAux[1]));
		calendar.add(Calendar.HOUR, hoursToAdd);
		calendar.add(Calendar.MINUTE, minutesToAdd);

		String finalHour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
		String finalMinute = String.valueOf(calendar.get(Calendar.MINUTE));

		if (finalMinute.equals("0")) {
			finalMinute = "00";
		}

		String finalTime = finalHour + ":" + finalMinute;
		return finalTime;
	}

	@Override
	public Date getDate(Date date, int days) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, days);

		return calendar.getTime();
	}

	@Override
	public boolean isValideDate(Date date, String hour) {
		boolean isValideDate = false;

		String[] initialHourAux = hour.split(":");

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(getDate(date, 1));
		calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(initialHourAux[0]));
		calendar.set(Calendar.MINUTE, Integer.parseInt(initialHourAux[1]));

		Calendar newCalendar = Calendar.getInstance();
		newCalendar.add(Calendar.HOUR_OF_DAY, 24);

		if (calendar.after(newCalendar)) {
			isValideDate = true;
		}

		return isValideDate;

	}

	@Override
	public boolean isValideHour(String hour) {

		boolean isValideHour = false;

		if (hour == null || hour.equals("")) {
			return isValideHour;
		}

		ParameterEntity parameterOpeningHour = parameterService.findByParameterKey("Hora Apertura");
		ParameterEntity parameterClosingHour = parameterService.findByParameterKey("Hora Cierre");

		if (parameterOpeningHour.getParameterValue().equals("")) {
			parameterOpeningHour.setParameterValue("00:00");
		}

		if (parameterClosingHour.getParameterValue().equals("")) {
			parameterClosingHour.setParameterValue("24:00");
		}

		String serviceHour = hour.split(":")[0];
		String openingHour = parameterOpeningHour.getParameterValue().split(":")[0];
		String closingHour = parameterClosingHour.getParameterValue().split(":")[0];

		if (Integer.parseInt(serviceHour) >= Integer.parseInt(openingHour)
				&& Integer.parseInt(serviceHour) <= Integer.parseInt(closingHour)) {
			isValideHour = true;
		}

		return isValideHour;

	}

	@Override
	public ValidationModel validateBookingForm(BookingEntity booking, User user) {

		String result = null;
		boolean requireOtherView = false;
		String otherView = null;

		if (booking.getDuration() == null || booking.getDuration() == 0) {
			result = "Favor de verificar el campo Duraci%C3%B3n";
			return new ValidationModel(result, requireOtherView, otherView);
		}
		if (booking.getDate() == null) {
			result = "Favor de verificar el campo Fecha";
			return new ValidationModel(result, requireOtherView, otherView);
		}
		if (booking.getHour() == null || booking.getHour().equals("")) {
			result = "Favor de verificar el campo Hora";
			return new ValidationModel(result, requireOtherView, otherView);
		} else if (!isValideDate(booking.getDate(), booking.getHour())) {
			result = "La reservaci%C3%B3n debe realizarse al menos 24 horas antes de la fecha solictada, le sugerimos revisar el servicio Bambino ASAP";
			return new ValidationModel(result, requireOtherView, otherView);
		} else if (!isValideHour(booking.getHour())) {
			String serviceHour = parameterService.findByParameterKey("Hora Apertura").getParameterValue();
			result = "Por el momento el horario para el servicio es a partir de las " + serviceHour + " hrs";
			return new ValidationModel(result, requireOtherView, otherView);
		}
		if (booking.getPaymentType() == null) {
			result = "Favor de verificar la forma de pago";
			return new ValidationModel(result, requireOtherView, otherView);
		}

		ClientEntity client = clientService.findByUserEmail(user.getUsername());
		booking.setClient(client);
		if (bambinoService.findByClient(client).isEmpty() && booking.getBookingType().getBookingTypeId() != 3) {
			result = "Favor de dar de alta a sus bambinos";
			requireOtherView = true;
			otherView = "redirect:/users/createbambinoform";
			return new ValidationModel(result, requireOtherView, otherView);
		}

		List<EmergencyContactEntity> emergencyContacts = emergencyContactService.findByClient(client);
		if ((emergencyContacts.isEmpty() || emergencyContacts.size() < 2)
				&& booking.getBookingType().getBookingTypeId() != 3) {
			result = "Favor de dar de alta al menos 2 contactos de emergencia";
			requireOtherView = true;
			otherView = "redirect:/users/createcontactform";
			return new ValidationModel(result, requireOtherView, otherView);
		}

		// Validaciones para BambinoTutory
		if (booking.getBookingType().getBookingTypeId() == 2) {
			if (booking.getTutory() == null) {
				result = "Ocurri%C3%B3 un error al intentar generar el servicio seleccionado. Por favor intente de nuevo";
				return new ValidationModel(result, requireOtherView, otherView);
			}
			if (booking.getTutory().getCourse() == null
					|| booking.getTutory().getCourse().trim().equalsIgnoreCase("")) {
				result = "Favor de especificar una materia";
				return new ValidationModel(result, requireOtherView, otherView);
			}
			if (booking.getTutory().getTopic() == null || booking.getTutory().getTopic().trim().equalsIgnoreCase("")) {
				result = "Favor de especificar un tema";
				return new ValidationModel(result, requireOtherView, otherView);
			}
		}

		// Validaciones para Bambino Events
		if (booking.getBookingType().getBookingTypeId() == 3) {

			if (booking.getEvent() == null) {
				result = "Ocurri%C3%B3 un error al intentar generar el servicio seleccionado. Por favor intente de nuevo";
				return new ValidationModel(result, requireOtherView, otherView);
			}

			if (booking.getEvent().getEventType() == null) {
				result = "Favor de especificar un tipo de Evento";
				return new ValidationModel(result, requireOtherView, otherView);
			}
			if (booking.getEvent().getBambinosQty() == null || booking.getEvent().getBambinosQty() <= 0) {
				result = "Favor de especificar el número de Bambinos";
				return new ValidationModel(result, requireOtherView, otherView);
			}
			if (booking.getEvent().getAge() == null || booking.getEvent().getAge().trim().equalsIgnoreCase("")) {
				result = "Favor de especificar un rango de edades de los Bambinos";
				return new ValidationModel(result, requireOtherView, otherView);
			}
			if (booking.getEvent().getStreet() == null || booking.getEvent().getStreet().trim().equalsIgnoreCase("")) {
				result = "Favor de especificar la calle y número del evento";
				return new ValidationModel(result, requireOtherView, otherView);
			}
			if (booking.getEvent().getNeighborhood() == null
					|| booking.getEvent().getNeighborhood().trim().equalsIgnoreCase("")) {
				result = "Favor de especificar la colonia del evento";
				return new ValidationModel(result, requireOtherView, otherView);
			}
			if (booking.getEvent().getCity() == null || booking.getEvent().getCity().trim().equalsIgnoreCase("")) {
				result = "Favor de especificar el municipio del evento";
				return new ValidationModel(result, requireOtherView, otherView);
			}
			if (booking.getEvent().getState() == null || booking.getEvent().getState().trim().equalsIgnoreCase("")) {
				result = "Favor de especificar el estado del evento";
				return new ValidationModel(result, requireOtherView, otherView);
			}
		}

		return new ValidationModel(result, requireOtherView, otherView);
	}

	@Override
	public Date getBookingDateTime(Date bookingDate, String hour, Double duration, boolean isFinalDate) {

		Calendar date = Calendar.getInstance();

		Calendar originDate = Calendar.getInstance();
		originDate.setTime(bookingDate);
		String[] originTimeArr = hour.split(":");

		date.set(originDate.get(Calendar.YEAR), originDate.get(Calendar.MONTH), originDate.get(Calendar.DAY_OF_MONTH),
				Integer.parseInt(originTimeArr[0]), Integer.parseInt(originTimeArr[1]), 0);

		if (isFinalDate) {
			Integer hours = duration.intValue();
			Double minutesAux = (duration - hours) * 60;
			Integer minutes = minutesAux.intValue();
			date.add(Calendar.HOUR_OF_DAY, hours);
			date.add(Calendar.MINUTE, minutes);
			date.set(Calendar.MILLISECOND, 0);
		}

		return date.getTime();
	}

	@Override
	public List<BookingEntity> findByNanny(NannyEntity nanny) {
		
		if(nanny == null) {
			return null;
		}
		
		return bookingRepository.findByNanny(nanny);
	}

}
