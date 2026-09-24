
$(function () {

	$(document).on('click', '.delete', function (e) {

	    e.preventDefault();

	    var menuId = $(this).attr('data-id');

	    console.log("Delete clicked, menuId = " + menuId);

	    if (!menuId) {
	        alert("Menu ID is missing.");
	        return;
	    }

	    if (!confirm('Do you want to delete menu ' + menuId + ' ?')) {
	        return;
	    }

	    $.ajax({
	        url: '/deleteMenu',
	        type: 'POST',
	        data: {
	            menuId: menuId
	        },

	        success: function (data) {

	            console.log("Server response = " + data);

	            if ($.trim(data) === 'success') {

	                alert('Record is successfully Deleted');

	            } else {

	                alert(
	                    'Record can not be Deleted as it is referenced by other submenus'
	                );
	            }

	            window.location.href = '/mainmenu';
	        },

	        error: function (xhr, status, error) {

	            console.log("Status = " + status);
	            console.log("Error = " + error);
	            console.log("Response = " + xhr.responseText);

	            alert('Error while deleting menu.');
	        }
	    });
	});

});

