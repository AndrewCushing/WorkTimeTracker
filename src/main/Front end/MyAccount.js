var xhr = new XMLHttpRequest();

xhr.open('GET', 'http://localhost.:8000/api/hello', true);
xhr.responseType = 'text';


xhr.onload = function(){
    console.log(xhr.responseText);
    if(xhr.status === 200){
        document.getElementById("body").innerHTML += xhr.responseText;
    }
};

xhr.send();