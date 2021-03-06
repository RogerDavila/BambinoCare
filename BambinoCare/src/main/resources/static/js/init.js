(function($){
  $(function(){

    $('.button-collapse').sideNav({
        closeOnClick: true, // Closes side-nav on <a> clicks, useful for Angular/Meteor
      }
    );

    $('.parallax').parallax();
    
    $('.slider').slider();
    
    $('.scrollspy').scrollSpy();

    $('.pushpin-demo-nav').pushpin({
      top: 30,
      offset: 0
    });

    $('select').material_select();

    // /User Section
    $("#save").hide();
    $("#cancel").hide();
    $("#editPerfil").click(function(){
      $("#MiPerfil input").prop("disabled", false);
      $("#save").show();
      $("#cancel").show();
    });

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
      donetext: 'Aceptar', // text for done-button
      cleartext: 'Limpiar', // text for clear-button
      canceltext: 'Cancelar', // Text for cancel-button
      autoclose: false, // automatic close timepicker
      ampmclickable: false, // make AM PM clickable
      aftershow: function(){} // Function for after opening timepicker
    });

    $('#contacto')
    .click(function() {
      window.location.href = "http://localhost:8080/#Footer";
    });

    $('#servicios')
    .click(function() {
      window.location.href = "http://localhost:8080/#Servicios";
    });

    $('#inicio, #logo-container')
    .click(function() {
      window.location.href = "http://localhost:8080/#";
    });

    $('#nosotros')
    .click(function() {
      window.location.href = "http://localhost:8080/#Nosotros";
    });

    $('#contactoMobile')
    .click(function() {
      window.location.href = "http://localhost:8080/#Footer";
    });

    $('#serviciosMobile')
    .click(function() {
      window.location.href = "http://localhost:8080/#Servicios";
    });

    $('#inicioMobile')
    .click(function() {
      window.location.href = "http://localhost:8080/#";
    });

    $('#nosotrosMobile')
    .click(function() {
      window.location.href = "http://localhost:8080/#Nosotros";
    });
    
    $('#paymentmodal, #cashmodal').modal({
        dismissible: true, // Modal can be dismissed by clicking outside of the modal
        opacity: .5, // Opacity of modal background
        startingTop: '4%', // Starting top style attribute
        endingTop: '10%', // Ending top style attribute
        ready: function(modal, trigger) { // Callback for Modal open. Modal and trigger parameters available.
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
