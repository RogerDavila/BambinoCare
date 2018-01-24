var showMessage = 0;
var options = {
    now: "00:00",
    twentyFour: false,
    title: "Seleccione duración",
    twentyFour: true
};

$(function () {
	if(perfil != 'nanny'){
		totalCost = totalCost ? totalCost : 0.00;
		document.getElementById('cost').innerHTML = "<h5>Total: $" + totalCost + "</h5>";
	}
    calculateCost();
    
    $('#bookingform').bind('submit', function (e) {
        var sendbutton = $('#sendbutton');
        var cancelbutton = $('#cancelbutton');
        sendbutton.prop('disabled', true);
        cancelbutton.addClass('disabled');
    });
    
    $("#bookingform").submit(function (event) {
        let durationValue = getDurationValue();
        $("#duration").val(durationValue);

        var paymenttype = $('#paymenttype option:selected').text();

        if (paymenttype == 'Pago en Oxxo o a cuenta Bancaria') {
            $('#paymentmodal').modal('open');
            event.preventDefault();
        } else if (paymenttype == 'Pago en efectivo') {
            $('#cashmodal').modal('open');
            event.preventDefault();
        }

    });

    $("#btn-confirm").click(function () {
        $('#paymentmodal').modal('close');
    });

    $("#duration, input[type='checkbox'], #bookingtype, #bambinosqty")
        .change(function () {
            calculateCostByBookingType();
            totalCost = totalCost ? totalCost : 0.00;
            document.getElementById('cost').innerHTML = "<h5>Total: $" + totalCost + "</h5>";
        });

    function calculateCostByBookingType() {
        var bookingtype = $('#bookingtype option:selected').text();
        if (bookingtype == "Bambino Events") {
            calculateCostBambinoEvents(costsBambinoEvents);
        } else if (bookingtype == "Bambino Tutory") {
            calculateCost(1, costsBambinoTutory);
        } else if (bookingtype == "Bambino Care") {
            calculateCost(null, costsBambinoCare);
        } else if (bookingtype == "Bambino ASAP") {
            calculateCost(null, costsBambinoASAP);
        }
    }

    function calculateCostBambinoEvents(costs) {
        let bambinoQuantity = $("#bambinosqty").val();

        if (!costs) {
            costs = [];
        }

        /* <![CDATA[ */
        if (!bambinoQuantity) {
            totalCost = 0;
        } else {
            let costsCurrent;
            for (let i = 0; i < costs.length; i++) {

                if (i == (costs.length - 1)) {
                    costsCurrent = costs[i];
                    break;
                }

                if (bambinoQuantity < costs[i + 1].bambinoQuantity) {
                    costsCurrent = costs[i];
                    break;
                }
            }
            let durationValue = getDurationValue();
            if (durationValue != "" && !isNaN(durationValue) && durationValue != 0) {
                if (durationValue > costsCurrent.hourQuantity) {
                    let normalCostDuration = costsCurrent.hourQuantity;
                    let extraCostDuration = durationValue - normalCostDuration;

                    let extraCost = extraCostDuration * costsCurrent.costExtraHour;
                    let normalCost = normalCostDuration * costsCurrent.cost;

                    totalCost = extraCost + normalCost;

                } else {
                    totalCost = costsCurrent.cost * durationValue;
                }
            }

            /* ]]> */
        }
        return totalCost;
    }

    // Calculo de costos para BambinoCare, Bambino ASAP y Bambino Tutory
    function calculateCost(bambinoQuantity, costs) {
        var sum = 0;

        if (!costs) {
            costs = [];
        }
        if (!bambinoQuantity) {
            totalCost = 0;
            $("input[type='checkbox']").each(function (checkbox) {
                if (this.checked) {
                    sum += 1;
                }
            });
        } else {
            totalCost = 0;
            sum = bambinoQuantity;
        }

        /* <![CDATA[ */
        if (sum > 3) {
            if (showMessage < 1) {
                $('#bambinomodal').modal('open');
            }
            showMessage += 1;
            sum -= 3;
            totalCost += calculateCost(sum, costs);
            sum = 3;
        }

        let costsCurrent = [];
        for (let cost of costs) {
            if (cost.bambinoQuantity == sum) {
                costsCurrent.push(cost);
            }
        }

        for (let i = 0; i < costsCurrent.length; i++) {
            let durationValue = getDurationValue();
            if (durationValue != "" && !isNaN(durationValue) && durationValue != 0) {
                if (durationValue > costsCurrent[i].hourQuantity) {
                    if (i < costsCurrent.length - 1)
                        continue;
                    else {
                        totalCost += costsCurrent[i].cost * durationValue;
                        break;
                    }
                } else {

                    if (durationValue == costsCurrent[i].hourQuantity) {
                        totalCost += costsCurrent[i].cost * durationValue;
                    } else {
                        totalCost += costsCurrent[i - 1].cost * durationValue;
                    }
                    break;
                }
            }
        }/* ]]> */
        console.log(totalCost);
        return totalCost;
    }

    function getDurationValue() {
        let duration = $("#duration");
        let durationValue = duration[0].value.split(":");
        let durationHour = parseFloat(durationValue[0]);
        let durationMinute = parseFloat(durationValue[1] / 60);
        durationValue = durationHour + durationMinute;

        return durationValue;
    }
    
    $("#tutory").hide();
    $("#event").hide();
    
    if($('#bookingtype').val() == 2){
    	$("#tutory").show();
    	$("#infobambinos").hide();
    }else if($('#bookingtype').val() == 3){
    	$("#event").show();
    	$("#infobambinos").hide();
    }
    
    $('#bookingtype').change(function(){
    	var bookingtype = $('#bookingtype option:selected').text();
    	if(bookingtype == "Bambino Events"){
    		$("#tutory").hide();
    		$("#event").show();
    		$("#infobambinos").hide();
    	}else if(bookingtype == "Bambino Tutory"){
    		$("#event").hide();
    		$("#tutory").show();
    		$("#infobambinos").hide();
    	}else{
    		$("#event").hide();
    		$("#tutory").hide();
    		$("#infobambinos").show();
    	}
	});

});