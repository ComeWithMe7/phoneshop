function updateCart(phoneId) {
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
            $("#count").html("");
            $("#quantityError" + phoneId).html("");
            var text = document.createTextNode(receivedObject.total);
            var domElement1 = document.getElementById('total');
            domElement1.appendChild(text);
            var text3 = document.createTextNode(receivedObject.count);
            var domElement3 = document.getElementById('count');
            domElement3.appendChild(text3);
        },
        error: function (data) {
            console.log(data);
            var receivedObject1 = data.responseJSON;
            $("#quantityError" + phoneId).html("");
            var text2 = document.createTextNode(receivedObject1.quantityError);
            var domElement2 = document.getElementById('quantityError' + phoneId);
            domElement2.appendChild(text2);
        }
    });
}
