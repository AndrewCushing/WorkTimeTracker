function checkCredentials(){
    validCredentials = false;
    let email = sessionStorage.getItem('email');
    let hashedPass = sessionStorage.getItem('pass');
    let xhr = new XMLHttpRequest();
    xhr.open('PUT', 'http://localhost:3000/api/login', true);
    xhr.responseType = 'text';
    xhr.onload = function(){
        if(xhr.status === 200){
            console.log(xhr.responseText);
            if(xhr.responseText==='Access granted.'){
                validCredentials = true;
            } else if (xhr.responseText==="User does not exist.") {
                alert('User does not exist');
                window.location = 'Login.html';
            } else if (xhr.responseText==="Incorrect password.") {
                alert('Incorrect password');
                window.location = 'Login.html';
            }
        }
    };
    xhr.send(email + ':' + hashedPass);
}

var validCredentials;
checkCredentials();

function submitEntry(){
    if(validCredentials){
        let email = sessionStorage.getItem('email');
        let project = document.getElementById("project").value;
        let description = document.getElementById("description").value;
        let date = document.getElementById("date").value;
        let hours = document.getElementById("amountOfTime").value;
        let xhr = new XMLHttpRequest();
        xhr.open('PUT', 'http://localhost:3000/api/login', true);
        xhr.responseType = 'text';
        xhr.onload = function(){
            if(xhr.status === 200){
                console.log(xhr.responseText);
                if(xhr.responseText==='Access granted.'){
                    goToMyAccount();
                } else if (xhr.responseText==="User does not exist.") {
                    alert('User does not exist');
                    validation = false;
                } else if (xhr.responseText==="Incorrect password.") {
                    alert('Incorrect password');
                    validation = false;
                }
            }
        };
        xhr.send(email + ':' + hashedPass);
    }
}