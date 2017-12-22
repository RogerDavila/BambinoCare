(function($){
  $(function(){


    $('.button-collapse').sideNav({
        closeOnClick: true, // Closes side-nav on <a> clicks, useful for Angular/Meteor
      }
    );

    $('.parallax').parallax();

    $('.pushpin-demo-nav').pushpin({
      top: 30,
      offset: 0
    });

    $('select').material_select();

    // /User Section
    $("#save").hide();
    $("#cancel").hide();
    $("#editPerfil").click(function(){
      $(":disabled").prop("disabled", false);
      $("#save").show();
      $("#cancel").show();
    });
    //$("#save").click(function(){
    	//$("#save").prop("disabled", true);
    	//$("#cancel").prop("disabled", true);
    //});

    $('.datepicker').pickadate({
      selectMonths: true, // Creates a dropdown to control month
      selectYears: 15, // Creates a dropdown of 15 years to control year,
      today: 'Today',
      clear: 'Clear',
      close: 'Ok',
      closeOnSelect: false, // Close upon selecting a date,
      format: 'yyyy-mm-dd'
    });
    $('.timepicker').pickatime({
      default: 'now', // Set default time: 'now', '1:30AM', '16:30'
      fromnow: 0,       // set default time to * milliseconds from now (using
						// with default = 'now')
      twelvehour: false, // Use AM/PM or 24-hour format
      donetext: 'OK', // text for done-button
      cleartext: 'Clear', // text for clear-button
      canceltext: 'Cancel', // Text for cancel-button
      autoclose: false, // automatic close timepicker
      ampmclickable: true, // make AM PM clickable
      aftershow: function(){} // Function for after opening timepicker
    });

    // Reservacion
    $("#Tutorias").hide();
    $("#Evento").hide();
    $('#tipoServicio').change(function(){
    	if($(this).val() == 2){
        $("#Tutorias").hide();
        $("#Evento").show();
    	}
    	if($(this).val() == 3){
        $("#Evento").hide();
        $("#Tutorias").show();
    	}
	});

    $('#contacto')
    .click(function() {
    	console.log("Redirigiendo...");
      window.location.href = "http://localhost:8080/#Footer";
    });

    $('#servicios')
    .click(function() {
    	console.log("Redirigiendo...");
      window.location.href = "http://localhost:8080/#Servicios";
    });

    $('#inicio, #logo-container')
    .click(function() {
    	console.log("Redirigiendo...");
      window.location.href = "http://localhost:8080/#";
    });

    $('#nosotros')
    .click(function() {
    	console.log("Redirigiendo...");
      window.location.href = "http://localhost:8080/#Nosotros";
    });

    $('#contactoMobile')
    .click(function() {
    	console.log("Redirigiendo...");
      window.location.href = "http://localhost:8080/#Footer";
    });

    $('#serviciosMobile')
    .click(function() {
    	console.log("Redirigiendo...");
      window.location.href = "http://localhost:8080/#Servicios";
    });

    $('#inicioMobile')
    .click(function() {
    	console.log("Redirigiendo...");
      window.location.href = "http://localhost:8080/#";
    });

    $('#nosotrosMobile')
    .click(function() {
    	console.log("Redirigiendo...");
      window.location.href = "http://localhost:8080/#Nosotros";
    });
    
    $('#paymentmodal').modal({
        dismissible: true, // Modal can be dismissed by clicking outside of the modal
        opacity: .5, // Opacity of modal background
        //inDuration: 300, // Transition in duration
        //outDuration: 200, // Transition out duration
        startingTop: '4%', // Starting top style attribute
        endingTop: '10%', // Ending top style attribute
        ready: function(modal, trigger) { // Callback for Modal open. Modal and trigger parameters available.
          console.log(modal);
          console.log(trigger);
        },
        complete: function() { 
        	document.forms['bookingform'].submit();
        }
      }
    );
    
    $('#bambinomodal').modal({
        dismissible: true, // Modal can be dismissed by clicking outside of the modal
        opacity: .5, // Opacity of modal background
        inDuration: 300, // Transition in duration
        outDuration: 200, // Transition out duration
        startingTop: '4%', // Starting top style attribute
        endingTop: '10%', // Ending top style attribute
        ready: function(modal, trigger) { // Callback for Modal open. Modal and trigger parameters available.
        },
        complete: function() { 
        }
      }
    );

  });
})(jQuery);
