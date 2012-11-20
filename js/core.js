$(document).ready(function(){ 
	/*dummy data*/
    var jsonArray = [
        {'name' : 'iso(1)','parent' : '', 'oid' : '1'},
        {'name' : 'org(3)','parent' : 'iso\\(1\\)', 'oid' : '1.3'},
        {'name' : 'dod(6)',  'parent' : 'org\\(3\\)', 'oid' : '1.3.6'},
        {'name' : 'internet(1)',  'parent' : 'dod\\(6\\)', 'oid' : '1.3.6.1'},
        {'name' : 'mgmt(2)',  'parent' : 'internet\\(1\\)', 'oid' : '1.3.6.1.2'},
        {'name' : 'mib(2)',  'parent' : 'mgmt\\(2\\)', 'oid' : '1.3.6.1.2.2'},
        {'name' : 'system(1)',  'parent' : 'mib\\(2\\)', 'oid' : '1.3.6.1.2.2.1'},
        {'name' : 'interface(2)',  'parent' : 'mib\\(2\\)', 'oid' : '1.3.6.1.2.2.2'}
    ];
    
    /*generate tree*/
    for(var i = 0; i < jsonArray.length; i++) 
    {
        var nodes = "";
        if(i === 0)
        {
            nodes = '<li>';
            nodes += "<a id=" + jsonArray[i].oid + ">";
            nodes += jsonArray[i].name; 
            nodes += '</a>';
            nodes += '</li>';  
            $("#iso\\(1\\)").append(nodes); 
        }
        else 
        {
            nodes = "<ul class='node' id=" + jsonArray[i].name + ">";
            nodes += '<li>';
            nodes += "<a id=" + jsonArray[i].oid + ">";
            nodes += jsonArray[i].name; 
            nodes += '</a>';
            nodes += '</li>';
            nodes += '</ul>'; 
            var hashVar = "#" + jsonArray[i].parent; 
            $(hashVar).children('li').append(nodes);
        }
    }

    /*hide tree*/   
    $('#iso\\(1\\)').find('ul').hide();

    /*toggle tree nodes*/
	$('.node').find('a').click(function (e) {
        e.preventDefault();
        e.stopPropagation();
        var id = $(this).attr('id');
        $('#oid').attr({value:id});
		$(this).parent('li').children('ul').toggle();
		//Translate
		var oid = $('#oid').attr('value');
		
		if (oid != '1')
		{
			var n = oid.split(".");
			var oidString = n[0];
			var text = $('#' + n[0]).text();
			var index = text.indexOf("(");
			var newString = text.substring(0,index);
			var oidName = newString;
			
			for (i = 1; i < n.length; i++)
			{
				oidString += "\\." + n[i]; 
				
				text = $('#' + oidString).text();
				
				index = text.indexOf("(");
				newString = text.substring(0,index);
				
				oidName += "." +  newString;
			}
		}
		else
		{
				var text = $('#' + oid).text();
				var index = text.indexOf("(");
				var newString = text.substring(0,index);
				var oidName = newString;
		}
		console.log(oidName);
		/*
		var innerText = $('#' + oid).text();
		var n = innerText.indexOf("(");
		var newString = innerText.substring(0,n);
		alert(newString);
		*/
		
		
		
    }); 
	
	// Click Submit
		$('#send_button').click(function (e) {
			alert("Submit");
			performSNMP();
		});
	
	
    /*show all*/
    $("#showAll").click(function () {
         $('#iso\\(1\\)').find('ul').show();
    });

    /* create URL */
    $('#input_form').submit(function() {
      var querystring = "http://localhost:8080/CS158B_WEBSERVICES/REST/SNMPOPERATION/" + $(this).serialize();
      var result = $('#result').html(querystring);
      return false;
    });
	
	ajaxCallFailed = function (pJQXHR, pTextStatus) {
        alert("Error:" + pJQXHR + " Status:" + pTextStatus);
    }
	
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
            else
            { 
                var lValue = { text: pElement.value };
                var lLI = document.createElement("li");
                $(".info").append(lLI);
                $(lLI).append($("<p>", lName));
                $(lLI).append($("<p>", lValue));
            }  // else
        });
    };  // parseResponse

    function performSNMP() {

        var community = document.getElementById("community").value;
        var host = document.getElementById("host").value;
        var oid = document.getElementById("oid").value;
        var commands = document.getElementById("commands").value;
        var lURL;
        var lServerHost = document.getElementById("txtServerHost").value;
		
        if (commands == "set") {
            var value = prompt("Please enter the new value for the entry", "");
            if (value !== null && value !== "") {
                lURL = "http://" + lServerHost + ":8080/CS158B_WEBSERVICES/rest/snmpoperation/" + commands + "?host=" + host + "&community=" + community + "&oid=" + oid + "&value=" + value;
            }  // if
            else if (value === "") {
                alert("The operation is canceled due to an invalid input. The input cannot be empty.");
                return;
            }  // else
            else {
                alert("The operation is canceled by the user");
                return;
            }  // else
        }   // if
		else if (commands == "Translate")
		{
			/*translate */
			/*
			var test = "iso(1)";
			
			
			var n = test.indexOf("(");
			var newString = test.substring(0,n);
			
			alert(newString);
			*/
			var oid = $('#oid').attr('value');
			alert(oid);
		}
        else {
            lURL = "http://" + lServerHost + ":8080/CS158B_WEBSERVICES/rest/snmpoperation/" + commands + "?host=" + host + "&community=" + community + "&oid=" + oid;
        }  // else
        $.ajax({
            cache: true,
            url: "http://24.7.122.241:8080/CS158B_WEBSERVICES/rest/snmpoperation/host=130.65.111.60&community=public&oid=1.3.6.1.2.1.1.3.0&commands=get",
            data: {},
            datatype: "GET",
            contentType: "application/javascript",
            dataType: "jsonp",
            success: parseResponse,
            error: ajaxCallFailed
        });  // ajax
    }  // function performSNMP

});