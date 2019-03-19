function myFunction(phoneId) {
    var quantityx = "quantity" + phoneId;
    $.ajax({
        type: "POST",
        url: "/phoneshop-web/ajaxCart",
        datatype: 'json',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify({
            "id": parseInt(phoneId),
            "quantity": parseInt($('#' + "quantity" + phoneId).val())
        }),
        success: function (data) {
            var receivedObject = data;
            $("#total").html("");
            $("#quantityError" + phoneId).html("");
            console.log(data);
            var text = document.createTextNode(receivedObject.total);
            var text2 = document.createTextNode(receivedObject.quantityError);
            var domElement1 = document.getElementById('total');
            domElement1.appendChild(text);
            var domElement2 = document.getElementById('quantityError' + phoneId);
            domElement2.appendChild(text2);
        },
        error: function (result) {
            console.log("ERROR: ", result);
        }
    });
}
