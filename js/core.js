/*
$('li').click(function(){this.element.find('ul').first().toggle()}) //work in progress for hiding
*/

$(document).ready(function(){ 
	$('ul#iso ').find('ul').hide();
	
	
	$('ul').click(function (e) {
		e.preventDefault();
		e.stopPropagation();
		$(this).children('li').children('ul').toggle();
		console.log(this);
	});
    
    $('#iso').click(function () {
        //$('#org').toggle(250);
        document.getElementById('oid').value = "1";
    });

    $('#org').click(function () {
        //$('#dod').toggle();
        document.getElementById('oid').value = "1.3";
    });

    $('#dod').click(function () {
        //$('#internet').toggle();
        document.getElementById('oid').value = "1.3.6";
    });
    
    /*$('#iso').click(function(){
        $('.info').append(result);
    });

    $('#org').click(function(){
        $('.info').append("hello");
    });*/
    
    /* create URL */
    $('#input_form').submit(function() {
      var querystring = "http://localhost:8080/CS158B_WEBSERVICES/REST/SNMPOPERATION/" + $(this).serialize();
      var result = $('#result').html(querystring);
      return false;
    });

});