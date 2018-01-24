package com.bambinocare;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

public class PayPalTest {

	private static final String CLIENTID = "";
	private static final String CLIENTSECRET = "";
	private static final String MODE = "sandbox";

	public static void main(String[] args) throws PayPalRESTException {
		createPayment();
		executePayment();
	}

	public static Payment createPayment() throws PayPalRESTException {
		Payment createdPayment = null;

		APIContext apiContext = new APIContext(CLIENTID, CLIENTSECRET, MODE);

		Details details = new Details();
		details.setSubtotal("20.0");
		details.setTax(String.valueOf(Double.parseDouble(details.getSubtotal()) * 0.15));

		Amount amount = new Amount();
		amount.setCurrency("MXN");
		amount.setTotal(
				String.valueOf(Double.parseDouble(details.getSubtotal()) + Double.parseDouble(details.getTax())));
		amount.setDetails(details);

		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		transaction.setDescription("BambinoCare");

		Item item = new Item();
		item.setName("Bambino 10 horas").setQuantity("1").setCurrency("MXN").setPrice("20.0");
		ItemList itemList = new ItemList();
		List<Item> items = new ArrayList<>();
		items.add(item);
		itemList.setItems(items);

		transaction.setItemList(itemList);

		List<Transaction> transactions = new ArrayList<>();
		transactions.add(transaction);

		Payer payer = new Payer();
		payer.setPaymentMethod("paypal");
		
		Payment payment = new Payment();
		payment.setIntent("sale");
		payment.setPayer(payer);
		payment.setTransactions(transactions);

		RedirectUrls redirectUrls = new RedirectUrls();
		redirectUrls.setCancelUrl("http://www.bambinocare.com.mx");
		redirectUrls.setReturnUrl("http://www.bambinocare.com.mx");

		payment.setRedirectUrls(redirectUrls);

		createdPayment = payment.create(apiContext);
		System.out.println(
				"se creo el pago con el id: " + createdPayment.getId() + " y estatus= " + createdPayment.getState());
		
		Iterator<Links> links = createdPayment.getLinks().iterator();
		while(links.hasNext()) {
			Links link = links.next();
			if(link.getRel().equalsIgnoreCase("approval_url")) {
				System.out.println(link.getHref());
			}
		}
		
		System.out.println("Pago con paypal: " + Payment.getLastRequest() + " --- " + Payment.getLastResponse());
		
		return createdPayment;
	}
	
	public static Payment executePayment() throws PayPalRESTException {
		Payment executedPayment = null;
		
		APIContext apiContext = new APIContext(CLIENTID, CLIENTSECRET, MODE);
		
		Payment paymentToExecute = new Payment();
		//Este imbecil sale de la petición a la url generada arriba (approval_url)
		paymentToExecute.setId("PAY-43Y12505DW234801TLIYLVJQ"); 
		
		PaymentExecution paymentExecution = new PaymentExecution();
		
		//Este imbecil sale de la petición a la url generada arriba (approval_url)
		String payerID = "UNDA2EA58T4M6";
		
		System.out.println("PAYERID: " + payerID);

		
		paymentExecution.setPayerId(payerID);
		
		executedPayment = paymentToExecute.execute(apiContext, paymentExecution);
		
		System.out.println("PAGO REALIZADO EN PAYPAL : " + Payment.getLastRequest() + " --- " + Payment.getLastResponse());
		
		return executedPayment;
	} 
}
