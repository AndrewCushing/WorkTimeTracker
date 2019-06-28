var validCredentials;

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
                getProjects();
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

function getProjects(){
    if(validCredentials){
        let email = sessionStorage.getItem('email');
        let xhr = new XMLHttpRequest();
        xhr.open('PUT', 'http://localhost:3000/api/getProjects', true);
        xhr.responseType = 'text';
        xhr.onload = function(){
            if(xhr.status === 200){
                let projects = xhr.responseText.split(' :');
                let selectionForm = document.getElementById("resultsGoHere");
                let selectString = '<select name="Projects">';
                for (let project in projects){
                    selectString += '<option>' + projects[project] + '</option>';
                }
                selectString += '</select>';
                selectString += '</select>';
                selectionForm.innerHTML += selectString;
            }
        }
        xhr.send(email);
    }  
}
checkCredentials();