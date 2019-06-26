var xhr = new XMLHttpRequest();

xhr.open('GET', 'http://127.0.0.1:8000/api/hello', true);
xhr.responseType = 'text';

xhr.onreadystatechange = function(){
    console.log(xhr.readyState);
}

xhr.onload = function(){
    if(xhr.status === 200){
        document.getElementById("body").innerHTML += xhr.responseText;
    }
};

xhr.send();