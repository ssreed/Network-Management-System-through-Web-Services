$(document).ready(function(){
	
	
	
	
	$('#commands').change(function () {
            if ($('select#commands').val() === '' || $('select#commands').val() === 'montiorSNMPStatus'
                || $('select#commands').val() === 'showrmonalarm' || $('select#commands').val() === 'showrmonevent') {
                $('#optionalParameters').html('');
            }

            if ($('select#commands').val() === 'createsec2group') {
                $('#optionalParameters').html('');
                var form =
                    '<label id="groupnameLabel">Group Name</label>'
                    + '<input name = "groupname" type = "text" id = "groupname"/>'
                    + '<label id="securitynameLabel">Security Name</label>'
                    + '<input name = "securityname" type = "text" id = "securityname"/>';

                $('#optionalParameters').append(form);
            }

            if ($('select#commands').val() === 'deletesec2group') {
                $('#optionalParameters').html('');
                var form =
                    '<label id="securitynameLabel">Security Name</label>'
                    + '<input name = "securityname" type = "text" id = "securityname"/>';
                $('#optionalParameters').append(form);
            }

            if ($('select#commands').val() === 'createview') {
                $('#optionalParameters').html('');
                var form =
                    '<label id="viewNameLabel">View Name</label>'
                    + '<input name = "viewname" type = "text" id = "viewname"/>'
                    + '<label id="oidTreeLabel">OID Tree</label>'
                    + '<input name = "oid" type = "text" id="oid"/>'
                    + '<select name="option">'
                    + '<option value="included">included</option>'
                    + '<option value="excluded">excluded</option>'
                    + '</select>';
                $('#optionalParameters').append(form);
            }

            if ($('select#commands').val() === 'deleteview') {
                $('#optionalParameters').html('');
                var form =
                    '<label id="viewNameLabel">View Name</label>'
                    + '<input name = "viewname" type = "text" id = "viewname"/>'
                    + '<label id="oidTreeLabel">OID Tree</label>'
                    + '<input name = "oid" type = "text" id="oid"/>';
                $('#optionalParameters').append(form);
            }

            if ($('select#commands').val() === 'createaccess') {
                $('#optionalParameters').html('');
                form =
                    '<label id="groupname">Group Name</label>'
                    + '<input name = "groupname" type = "text" id = "groupname"/>'
                    + '<label id="readviewnameLabel">Read View Name</label>'
                    + '<input name = "readviewname" type = "text" id = "readviewname"/>'
                    + '<label id="writeviewnameLabel">Write View Name</label>'
                    + '<input name = "writeviewname" type = "text" id = "writeviewname"/>'
                    + '<label id="notifyviewnameLabel">Notify View Name</label>'
                    + '<input name = "notifyviewname" type = "text" id = "notifyviewname"/>';
                $('#optionalParameters').append(form);
            }

            if ($('select#commands').val() === 'deleteaccess') {
                $('#optionalParameters').html('');
                form =
                    '<label id="groupname">Group Name</label>'
                    + '<input name = "groupname" type = "text" id = "groupname"/>';
                $('#optionalParameters').append(form);
            }

            if ($('select#commands').val() === 'set') {
                $('#optionalParameters').html('');
                form =
                     '<select name="oid" id="oid">'
                    + '<option value="1.3.6.1.2.1.1.4.0">contact</option>'
                    + '<option value="1.3.6.1.2.1.1.6.0">location</option>'
                    + '<option value="1.3.6.1.2.1.1.5.0">name</option>'
                    + '</select>'
                    + '<input name = "value" type = "text" id = "option"/>';
                $('#optionalParameters').append(form);
            }
            if ($('select#commands').val() === 'enablesnmpagent') {
                $('#optionalParameters').html('');
                form =
                     '<select name="option" id="option">'
                    + '<option value="enable">enable</option>'
                    + '<option value="disable">disable</option>'
                    + '</select>';

                $('#optionalParameters').append(form);
            }
            if ($('select#commands').val() === 'enablermon') {
                $('#optionalParameters').html('');
                form =
                     '<select name="option" id="option">'
                    + '<option value="native">native</option>'
                    + '<option value="promisciuos">promisciuos</option>'
                    + '</select>';

                $('#optionalParameters').append(form);
            }
            if ($('select#commands').val() === 'setrmonqueue') {
                $('#optionalParameters').html('');
                form =
                    '<label id="queueRMONSizeLabel">Queue size</label>'
                    + '<input name = "value" type = "text" id = "value"/>';

                $('#optionalParameters').append(form);
            }
            if ($('select#commands').val() === 'setrmonalarm') {
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
            if ($('select#commands').val() === 'setrmonevent') {
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
	$('#input_form').submit(function () {

            var lURL = "https://" + $('#txtServerHost').val() + ":8181/CS158B_WEBSERVICES/rest/snmpoperation/" + $('select#commands').val() + "?" + $(this).serialize();
            //var result = $('#result').html(lURL);
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
			$('#optionalParameters').html(''); //clear
			//$('select#commands').val('Choose a command');
            return false;
        });
	
    
    ajaxCallFailed = function (pJQXHR, pTextStatus) {
        alert("Error:" + pJQXHR + " Status:" + pTextStatus);
    };
    
	
	
	
	
    /*Danil's functions*/
	
	    parseResponse = function (data) {
        $("#node_list").empty();

        if ($('select#commands').val() == 'showrmonalarm' ||
        $('select#commands').val() == 'setrmonalarm') {
            table = '<table class="table table-hover table-striped">'
                + '<tr><td>alarm index</td>'
                + '<td>alarm variable</td>'
                + '<td>alarm interval</td>'
                + '<td>alarm interval option</td>'
                + '<td>alarm rising threshold</td>'
                + '<td>alarm rising event index </td>'
                + '<td>alarm falling threshold</td>'
                + '<td>alarm falling event index</td>'
                + '<td>alarm status</td>'
                + '<td>alarm owner</td></tr>';

            $.each(data, function (pIndex, pElement) {
                table = table
                + '<tr><td>' + pElement.alarmindex + '</td>'
                + '<td>' + pElement.alarmvariable + '</td>'
                + '<td>' + pElement.alarminterval + '</td>'
                + '<td>' + pElement.alarmintervaloption + '</td>'
                + '<td>' + pElement.alarmrisingthreshold + '</td>'
                + '<td>' + pElement.alarmrisingeventindex + '</td>'
                + '<td>' + pElement.alarmfallingthreshold +'</td>'
                + '<td>' + pElement.alarmfallingeventindex + '</td>'
                + '<td>' + pElement.alarmstatus + '</td>'
                + '<td>' + pElement.alarmowner + '</td></tr>';
            });
            table = table + '</table>';
            $("#result").append(table);
        }  // if
        else if ($('select#commands').val() === 'showrmonevent' ||
         $('select#commands').val() == 'setrmonevent')  {
            table = '<table class="table table-hover table-striped">'
                + '<tr><td>event index</td>'
                + '<td>event trap </td>'
                + '<td>event community</td>'
                + '<td>event last timesent</td>'
                + '<td>event description </td>'
                + '<td>event status</td>'
                + '<td>event owner</td></tr>';

            $.each(data, function (pIndex, pElement) {
                table = table + '<tr><td>' + pElement.eventindex + '</td>'
                + '<td>' + pElement.eventtrap + '</td>'
                + '<td>' + pElement.eventcommunity + '</td>'
                + '<td>' + pElement.eventlasttimesent + '</td>'
                + '<td>' + pElement.eventdescription + '</td>'
                + '<td>' + pElement.eventstatus + '</td>'
                + '<td>' + pElement.eventowner + '</td></tr>';
            });
            table = table + '</table>';
            $("#result").append(table);
        }  
		 else if ($('select#commands').val() === 'status')  {
			 $.each(data, function (pIndex, pElement) {
                var lName = { text: pElement.name };
                // if
                if (lName.text == "STATUS") {
                    var lValue = { text: pElement.value };

                    var lLI = document.createElement("li");
                    $("#result").append(lLI);
                    $(lLI).append($("<p>", lValue));
                }  // if
                // else
            });
		 }
		// else if
        else {
		$.each(data, function (pIndex, pElement) {
			var result  = '<p>' + pElement.name + ':' + pElement.value + '</p>';
			$("#result").append(result);
		});
		/*
            $.each(data, function (pIndex, pElement) {
                var lName = { text: pElement.name };
                if (lName.text == "OID Name") {
                    var lValue = { text: pElement.value };

                    var lLI = document.createElement("li");
                    $("#node_list").append(lLI);
                    $(lLI).append($("<p>", lValue));
                }  // if
                else if (lName.text == "NETSTAT" || lName.text == "STATUS" || lName.text == "TRANSLATE") {
                    var lValue = { text: pElement.value };

                    var lLI = document.createElement("li");
                    $("#node_list").append(lLI);
                    $(lLI).append($("<p>", lValue));
                }  // if
                else {
                    var lValue = { text: pElement.value };
                    var lLI = document.createElement("li");
                    $("#node_list").append(lLI);
                    $(lLI).append($("<p>", lName));
                    $(lLI).append($("<p>", lValue));
                }  // else
            });
		*/
        }  // else
    }  // parseResponse

	
});