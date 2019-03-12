function myFunction(phoneId, url) {
    var quantityx = "quantity" + phoneId;
    $.ajax({
        type: "POST",
        url: url,
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
            var html = document.createElement('div');
            var text = document.createTextNode(receivedObject.total);
            html.appendChild(text);
            var html2 = document.createElement('div');
            var text2 = document.createTextNode(receivedObject.quantityError);
            html2.appendChild(text2);
            var domElement1 = document.getElementById('total');
            domElement1.appendChild(html);
            var domElement2 = document.getElementById('quantityError' + phoneId);
            domElement2.appendChild(html2);
        },
        error: function (result) {
            console.log("ERROR: ", result);
        }
    });
}