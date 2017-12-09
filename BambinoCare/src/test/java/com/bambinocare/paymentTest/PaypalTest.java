package com.bambinocare.paymentTest;

import java.util.ArrayList;
import java.util.List;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

public class PaypalTest {
	
	private static final String CLIENTID = "Aa4ywCxbtdHqDGcqyHRoOKjFxWl_XErYtCFuAmA6qaY00ay3zDahxHcquDFWWjvumCxBBXz2G_4D6MuX";
	private static final String CLIENTSECRET = "EC_1JAk8lZLHPzc5AOWPnrDv9JsFAe0e96tnArkNvWQGDiLywoAK3PQCFImTXd8tmotMHOM5AjCZtPVr";
	private static final String MODE = "sandbox";
	
	public static void main(String ... args) {
		
		
		Amount amount = new Amount();
		amount.setCurrency("MXN");
		amount.setTotal("1.00");
		
		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		
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
		
		try {
			APIContext context = new APIContext(CLIENTID, CLIENTSECRET, MODE);
			System.out.println("Se realizará creación de pago");
			Payment createdPayment = payment.create(context);
			//System.out.println(createdPayment);
		}catch (PayPalRESTException ppRestEx) {
			ppRestEx.printStackTrace();
		}
	}
	
}
