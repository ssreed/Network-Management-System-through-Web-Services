$(document).ready(function(){
	/*
    // Click Submit
    $('#send_button').click(function (e) {
        performSNMP();
        //var querystring = "http://localhost:8080/CS158B_WEBSERVICES/REST/SNMPOPERATION/" + $(this).serialize();
        //var result = $('#result').html(querystring);
    });
	*/
    $('#commands').change(function() {
		if($('select#commands').val() === '' || $('select#commands').val() === 'montiorSNMPStatus' 
			|| $('select#commands').val() === 'showRmonAlarm' || $('select#commands').val() === 'showRmonEvent')
		{
			$('#optionalParameters').html('');
		}
        if($('select#commands').val() === 'createSNMPView')
        {
            $('#optionalParameters').html('');
            var form = 
                '<label id="viewNameLabel">View Name</label>' 
                + '<input name = "viewName" type = "text" id = "viewName"/>'
                + '<label id="oidTreeLabel">OID Tree</label>'
                + '<input name = "oidTree" type = "text" id="oidTree"/>'
                + '<select name="snmpViewOption">'
                + '<option value="include">include</option>'
                + '<option value="exclude">exclude</option>'
                + '</select>';
            $('#optionalParameters').append(form); 
        }

        if($('select#commands').val() === 'createAccessControl')
        {
            $('#optionalParameters').html('');
            form = 
                '<label id="modifyCommunityLabel">Modify Community</label>' 
                + '<input name = "modifyCommunity" type = "text" id = "modifyCommunity"/>'
                + '<label id="viewNameLabel">View Name</label>'
                + '<input name = "viewName" type = "text" id = "viewName"/>'
                + '<select name="permissionOption">'
                + '<option value="rocommunity">rocommunity</option>'
                + '<option value="rwcommunity">rwcommunity</option>'
                + '</select>'
                + '<label id="accessListLabel">Access List</label>' 
                + '<input name = "accessList" type = "text" id = "accessList"/>';
            $('#optionalParameters').append(form); 
        }
		if($('select#commands').val() === 'establishContactLocationName')
        {
            $('#optionalParameters').html('');
            form = 
                 '<select name="establishOption" id="establishOption">'
                + '<option value="contact">contact</option>'
                + '<option value="location">location</option>'
				+ '<option value="name">name</option>'
                + '</select>'
                + '<input name = "valueOfEstablishOption" type = "text" id = "valueOfEstablishOption"/>';
            $('#optionalParameters').append(form); 
        }
		if($('select#commands').val() === 'enableSNMPAgent')
        {
            $('#optionalParameters').html('');
            form = 
                 '<select name="enableSNMPOption" id="enableOption">'
                + '<option value="enable">enable</option>'
                + '<option value="disable">disable</option>'
                + '</select>';
                
            $('#optionalParameters').append(form); 
        }
		if($('select#commands').val() === 'enableRmon')
        {
            $('#optionalParameters').html('');
            form = 
                 '<select name="enableRMONOption" id="enableRMONOption">'
                + '<option value="native">native</option>'
                + '<option value="promisciuos">promisciuos</option>'
                + '</select>';
                
            $('#optionalParameters').append(form); 
        }
		if($('select#commands').val() === 'enableRmon')
        {
            $('#optionalParameters').html('');
            form = 
                 '<select name="enableRMONOption" id="enableRMONOption">'
                + '<option value="native">native</option>'
                + '<option value="promisciuos">promisciuos</option>'
                + '</select>';
                
            $('#optionalParameters').append(form); 
        }
		if($('select#commands').val() === 'setRmonQueue')
        {
            $('#optionalParameters').html('');
            form = 
                '<label id="queueRMONSizeLabel">Queue size</label>' 
                + '<input name = "queueRMONSize" type = "text" id = "queueRMONSize"/>';
                
            $('#optionalParameters').append(form); 
        }
		if($('select#commands').val() === 'setRmonAlarm')
        {
            $('#optionalParameters').html('');
            form = 
                '<label id="alarmIndexLabel">index</label>' 
                + '<input name = "alarmIndex" type = "text" id = "alarmIndex"/>'
				+ '<label id="alarmIntervalLabel">interval</label>'
                + '<input name = "alarmInterval" type = "text" id = "alarmInterval"/>'
				+ '<label id="alarmVariableLabel">variable</label>'
                + '<input name = "alarmVariable" type = "text" id = "alarmVariable"/>'
				+ '<label id="alarmSampleTypeLabel">sample type</label>'
                + '<input name = "alarmSampleType" type = "text" id = "alarmSampleType"/>'
				+ '<label id="alarmStartupAlarmLabel">start up alarm</label>'
                + '<input name = "alarmStartupAlarm" type = "text" id = "alarmStartupAlarm"/>'
				+ '<label id="alarmRisingThresholdLabel">rising threshold</label>'
                + '<input name = "alarmRisingThreshold" type = "text" id = "alarmRisingThreshold"/>'
				+ '<label id="alarmFallingThresholdLabel">falling threshold</label>'
                + '<input name = "alarmFallingThreshold" type = "text" id = "alarmFallingThreshold"/>'
				+ '<label id="alarmRisingEventIndexLabel">rising event index</label>'
                + '<input name = "alarmRisingEventIndex" type = "text" id = "alarmRisingEventIndex"/>'
				+ '<label id="alarmFallingEventIndexLabel">rising event index</label>'
                + '<input name = "alarmFallingEventIndex" type = "text" id = "alarmFallingEventIndex"/>'
				+ '<label id="alarmStatusLabel">status</label>'
                + '<input name = "alarmStatus" type = "text" id = "alarmStatus"/>';
				
				
                
            $('#optionalParameters').append(form); 
        }
		if($('select#commands').val() === 'setRmonEvent')
        {
            $('#optionalParameters').html('');
            form = 
                '<label id="eventIndexLabel">index</label>' 
                + '<input name = "eventIndex" type = "text" id = "eventIndex"/>'
				+ '<label id="eventDescriptionLabel">description</label>'
                + '<input name = "eventDescription" type = "text" id = "eventDescription"/>'
				+ '<label id="eventTypeLabel">type</label>'
                + '<input name = "eventType" type = "text" id = "eventType"/>'
				+ '<label id="eventCommunityLabel">community</label>'
                + '<input name = "eventCommunity" type = "text" id = "eventCommunity"/>'
				+ '<label id="eventLastTimeSentLabel">last time sent</label>'
                + '<input name = "eventLastTimeSent" type = "text" id = "eventLastTimeSent"/>'
				+ '<label id="eventOwnerLabel">owner</label>'
                + '<input name = "eventOwner" type = "text" id = "eventOwner"/>'
				+ '<label id="eventStatusLabel">status</label>'
                + '<input name = "eventStatus" type = "text" id = "eventStatus"/>';
                
            $('#optionalParameters').append(form); 
        }
    });
	
    /* create URL. */
    $('#input_form').submit(function() {
      var querystring = "http://localhost:8080/CS158B_WEBSERVICES/REST/SNMPOPERATION/" + $(this).serialize();
      var result = $('#result').html(querystring);
	  $.ajax({
            cache: true,
            url: querystring,
            data: {},
            datatype: "GET",
            contentType: "application/javascript",
            dataType: "jsonp",
            success: parseResponse,
            error: ajaxCallFailed
        });  // ajax
      
      $('#optionalParameters').html(''); //clear
      
      return false;
    });
    
    ajaxCallFailed = function (pJQXHR, pTextStatus) {
        alert("Error:" + pJQXHR + " Status:" + pTextStatus);
    };
    
	
	/*
	// create establishOption
	$('#establishOption').change(function(){
		if($('select#establishOption').val() == 'contact')
		{	
			var form = '<input name = "contact" type = "text" id = "contact"/>';
			$('#establishOption').append(form);
		}
		if($('select#establishOption').val() == 'location')
		{
			var form = '<input name = "location" type = "text" id = "location"/>';
			$('#establishOption').append(form);
		}
		if($('select#establishOption').val() == 'name')
		{
			var form = '<input name = "name" type = "text" id = "name"/>';
			$('#establishOption').append(form);
		}
	});
	*/
	
	
    /*Danil's functions*/
   parseResponse = function (data) {
        $(".info").empty();
        $.each(data, function (pIndex, pElement) {
            var lName = { text: pElement.name };
            if (lName.text == "OID Name") {
                var lValue = { text: pElement.value };

                var lLI = document.createElement("li");
                $(".info").append(lLI);
                $(lLI).append($("<p>", lValue));
            }  // if
            else if (lName.text == "NETSTAT" || lName.text == "STATUS" || lName.text == "TRANSLATE") {
                var lValue = { text: pElement.value };

                var lLI = document.createElement("li");
                $(".info").append(lLI);
                $(lLI).append($("<p>", lValue));
            }  // if
            else {
                var lValue = { text: pElement.value };
                var lLI = document.createElement("li");
                $(".info").append(lLI);
                $(lLI).append($("<p>", lName));
                $(lLI).append($("<p>", lValue));
            }  // else
        });
    };// parseResponse

	/*
    function performSNMP() {
        var lCommunity = document.getElementById("community").value;
        var lHost = document.getElementById("host").value;
        var lOID = document.getElementById("oid").value;
        var lOperation = document.getElementById("commands").value.toLowerCase();
    
        var lURL;
        var lServerHost = document.getElementById("txtServerHost").value;
        if (lOperation == "set") {
            var lValue = prompt("Please enter the new value for the entry", "");
            if (lValue !== null && lValue !== "") {
                lURL = "http://" + lServerHost + ":8080/CS158B_WEBSERVICES/rest/snmpoperation/" + lOperation + "?host=" + lHost + "&community=" + lCommunity + "&oid=" + lOID + "&value=" + lValue;
            }  // if
            else if (lValue === "") {
                alert("The operation is canceled due to an invalid input. The input cannot be empty.");
                return;
            }  // else
            else {
                alert("The operation is canceled by the user");
                return;
            }  // else
        }   // if
        else if (lOperation == "test") {
            var lResultValue = "";
            do {
                var lValue = prompt("Please enter the test variable", "");
                if (lValue !== null && lValue !== "") {
                    lResultValue = lResultValue + lValue + ";";
                }  // if
                else {
                    break;
                }  // else
            } while (true);

            if (lResultValue === "") {
                alert("The operation is canceled by the user");
                return;
            }  // if

            lURL = "http://" + lServerHost + ":8080/CS158B_WEBSERVICES/rest/snmpoperation/" + lOperation + "?host=" + lHost + "&community=" + lCommunity + "&oids=" + lResultValue;
        }   // if
        else if (lOperation == "translate") {
            var lResultValue = "";
            do {
                var lValue = prompt("Please enter the translate numeric oid", "");
                if (lValue !== null && lValue !== "") {
                    lResultValue = lResultValue + lValue + ";";
                }  // if
                else {
                    break;
                }  // else
            } while (true);

            if (lResultValue === "") {
                alert("The operation is canceled by the user");
                return;
            }  // if

            lURL = "http://" + lServerHost + ":8080/CS158B_WEBSERVICES/rest/snmpoperation/" + lOperation + "?oids=" + lResultValue;
        }   // if
        else if (lOperation == "status" || lOperation == "netstat") {
            lURL = "http://" + lServerHost + ":8080/CS158B_WEBSERVICES/rest/snmpoperation/" + lOperation + "?host=" + lHost + "&community=" + lCommunity;
        }  // else
        else {
            lURL = "http://" + lServerHost + ":8080/CS158B_WEBSERVICES/rest/snmpoperation/" + lOperation + "?host=" + lHost + "&community=" + lCommunity + "&oid=" + lOID;
        }  // else
        $.ajax({
            cache: true,
            url: lURL,
            data: {},
            datatype: "GET",
            contentType: "application/javascript",
            dataType: "jsonp",
            success: parseResponse,
            error: ajaxCallFailed
        });  // ajax
    }  // function performSNMP
*/
});