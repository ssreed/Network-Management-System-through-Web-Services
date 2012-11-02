/*
$('li').click(function(){this.element.find('ul').first().toggle()}) //work in progress for hiding
*/

$(document).ready(function(){ 

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

    $('#input_form').submit(function() {
      var querystring = $(this).serialize();
      var result = $('#result').html(querystring);
      return false;
    });

});