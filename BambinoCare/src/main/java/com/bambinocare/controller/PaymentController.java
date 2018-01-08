package com.bambinocare.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import com.bambinocare.constant.PaypalConstants;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Details;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

@Controller
@RequestMapping("/payments")
@SessionAttributes({ "paymentId", "bookingId" })
public class PaymentController {

	@PostMapping("/")
	public ModelAndView createPayment(@ModelAttribute(name = "bookingId") Integer bookingId,
			@ModelAttribute(name = "cost") String cost, BindingResult bindingResult, Model model,
			HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();

		Payment createdPayment = null;

		APIContext apiContext = new APIContext(PaypalConstants.CLIENTID, PaypalConstants.CLIENTSECRET,
				PaypalConstants.MODE);

		// Generamos los detalles de la venta, seteando el subtotal a partir del costo
		// del servicio
		Details details = new Details();
		details.setSubtotal(cost);

		// Generamos el Amount seteando la moneda, los details generados arriba y el
		// total de la venta a partir del costo del servicio.
		Amount amount = new Amount();
		amount.setCurrency("MXN");
		amount.setTotal(cost);
		amount.setDetails(details);

		// Generamos la transacción seteando el Amount generado arriba, una descripción
		// y los items vendidos
		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		transaction.setDescription("BambinoCare");
		// Generamos los items seteando el nombre del item, la cantidad, la moneda y el
		// precio a partir del costo, para despues agregarlos en una lista de items y
		// setearselos a la transaction
		Item item = new Item();
		item.setName("Servicio BambinoCare").setQuantity("1").setCurrency("MXN").setPrice(cost);
		ItemList itemList = new ItemList();
		List<Item> items = new ArrayList<>();
		items.add(item);
		itemList.setItems(items);
		transaction.setItemList(itemList);

		// Agregamos la transaction a una lista de transactions para poder pasarsela al
		// Payment posteriormente
		List<Transaction> transactions = new ArrayList<>();
		transactions.add(transaction);

		// Generamos un nuevo payer seteando el metodo de pago, en este caso paypal
		Payer payer = new Payer();
		payer.setPaymentMethod("paypal");

		// Generamos las URLs a redireccionar (solo aplica para pagos con paypal),
		// cuando se inicie sesión en Paypal o cuando se cancele el pago con paypal
		RedirectUrls redirectUrls = new RedirectUrls();
		redirectUrls.setCancelUrl("http://localhost:8080/users/showbookings");
		redirectUrls.setReturnUrl("http://localhost:8080/payments/execute");

		// Generamos el Payment, seteando el tipo de intent, para este caso "sale"
		// (venta directa)
		// , seteamos el payer, la lista de transactions y las URLs a redireccionar
		Payment payment = new Payment();
		payment.setIntent("sale");
		payment.setPayer(payer);
		payment.setTransactions(transactions);
		payment.setRedirectUrls(redirectUrls);

		try {
			// Se crea el pago en Paypal
			createdPayment = payment.create(apiContext);
			Iterator<Links> links = createdPayment.getLinks().iterator();

			// Se recupera la URL para aprobación de pago en paypal
			String redirectUrl = null;
			while (links.hasNext()) {
				Links link = links.next();
				if (link.getRel().equalsIgnoreCase("approval_url")) {
					redirectUrl = link.getHref();
				}
			}

			// Se guarda el PaymentID en sesión para utilizarlo despues de que el usuario
			// inicie sesión en Paypal
			model.addAttribute("paymentId", setPaymentId(createdPayment.getId()));
			model.addAttribute("bookingId", setBookingId(bookingId));

			// Se redirige a la url para que el usuario inicie la sesión en paypal y
			// autorice el pago.
			mav.setViewName("redirect:" + redirectUrl);

		} catch (PayPalRESTException e) {
			e.printStackTrace();
		}

		return mav;
	}

	@ModelAttribute("paymentId")
	private String setPaymentId(String paymentId) {
		return paymentId;
	}

	@ModelAttribute("bookingId")
	private Integer setBookingId(Integer bookingId) {
		return bookingId;
	}

	@GetMapping("/execute")
	public ModelAndView executePayment(@RequestParam("paymentId") String paymentId, @ModelAttribute("bookingId") Integer bookingId,
			@RequestParam("PayerID") String payerId, @RequestParam("token") String token, Model model,
			HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		String result;

		// Eliminar la variable de sesión
		model.addAttribute("paymentId", setPaymentId(""));

		APIContext apiContext = new APIContext(PaypalConstants.CLIENTID, PaypalConstants.CLIENTSECRET,
				PaypalConstants.MODE);

		if (paymentId == null || paymentId.equals("") || token == null || token.equals("") || payerId == null
				|| payerId.equals("")) {
			request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
			ModelMap modelmap = mav.getModelMap();
			modelmap.addAttribute("result", "No se ha podido procesar el pago, intente nuevamente");
			modelmap.addAttribute("bookingId", bookingId);
			mav = new ModelAndView("redirect:/users/completebooking", modelmap);
			return mav;
		}

		try {
			Payment payment = Payment.get(apiContext, paymentId);

			// Generamos un PaymentExecution y seteamos su payerId
			PaymentExecution paymentExecution = new PaymentExecution();
			paymentExecution.setPayerId(payerId);

			// Ejecutamos el pago
			Payment paymentExecuted = payment.execute(apiContext, paymentExecution);

			if (!paymentExecuted.getState().equalsIgnoreCase("approved")) {
				request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
				ModelMap modelmap = mav.getModelMap();
				modelmap.addAttribute("error", "No se ha podido procesar el pago, intente nuevamente");
				modelmap.addAttribute("bookingId", bookingId);
				mav = new ModelAndView("redirect:/users/completebooking", modelmap);
				return mav;
			}

		} catch (PayPalRESTException e) {
			e.printStackTrace();
		}

		result = "El pago se ejecuto correctamente";
		request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
		ModelMap modelmap = mav.getModelMap();
		modelmap.addAttribute("result", "El pago se ha realizado exitosamente");
		modelmap.addAttribute("bookingId", bookingId);
		mav.addObject("result",result);
		mav.addObject("bookingId",bookingId);
		mav = new ModelAndView("forward:/users/completebooking", modelmap);
		return mav;
	}

}
