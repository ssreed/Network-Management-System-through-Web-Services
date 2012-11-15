/*
$('li').click(function(){this.element.find('ul').first().toggle()}) //work in progress for hiding
*/

$(document).ready(function(){ 
	/*generate tree*/
    var jsonArray = [
        {'name' : 'iso(1)','parent' : '', 'oid' : '1'},
        {'name' : 'org(3)','parent' : 'iso\\(1\\)', 'oid' : '1.3'},
        {'name' : 'dod(6)',  'parent' : 'org\\(3\\)', 'oid' : '1.3.6'},
        {'name' : 'internet(1)',  'parent' : 'dod\\(6\\)', 'oid' : '1.3.6.1'},
        {'name' : 'mgmt(2)',  'parent' : 'internet\\(1\\)', 'oid' : '1.3.6.1.2'},
        {'name' : 'mib(2)',  'parent' : 'mgmt\\(2\\)', 'oid' : '1.3.6.1.2.2'},
        {'name' : 'system(1)',  'parent' : 'mib\\(2\\)', 'oid' : '1.3.6.1.2.2.1'},
        {'name' : 'interface(2)',  'parent' : 'mib\\(2\\)', 'oid' : '1.3.6.1.2.2.2'}];
        
        for(var i = 0; i < jsonArray.length; i++) 
        {
            if(i == 0)
            {
                var nodes = '<li>';
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
    }); 

    /* create URL */
    $('#input_form').submit(function() {
      var querystring = "http://localhost:8080/CS158B_WEBSERVICES/REST/SNMPOPERATION/" + $(this).serialize();
      var result = $('#result').html(querystring);
      return false;
    });

});