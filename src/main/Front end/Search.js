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
            let selectionForm = document.getElementById("resultsGoHere");
            if(xhr.status === 200){
                let projects = xhr.responseText.split(' :');
                let selectString = '<span>Project: </span><br><br><form onsubmit="return findProjectSummary(this)">';
                selectString += '<select name="Projects">';
                for (let project in projects){
                    selectString += '<option>' + projects[project] + '</option>';
                }
                selectString += '</select><br><br>';
                selectString += '<button>Get Summary</button>';
                selectString += '</form><br><br>';
                selectString += '<div id="summarySpace"></div>';
                selectionForm.innerHTML += selectString;
            } else {
                selectionForm.innerHTML = "<span>You haven't added any entries yet</span>";
            }
        }
        xhr.send(email);
    }  
}
checkCredentials();

function findProjectSummary(formData){
    let projectSelection = formData.Projects.value;
    if(validCredentials){
        let email = sessionStorage.getItem('email');
        let xhr = new XMLHttpRequest();
        xhr.open('PUT', 'http://localhost:3000/api/summaryByProject', true);
        xhr.responseType = 'text';
        xhr.onload = function(){
            let selectionForm = document.getElementById("summarySpace");
            if(xhr.status === 200){
                let summaryInfo = xhr.responseText.split(' :');
                let summaryString = '<table style="width:50%"><tr><th>Description</th><th>Total time</th></tr>';
                for (let i = 0 ; i < summaryInfo.length ; i+=2){
                    summaryString += "</tr><tr><td>" + summaryInfo[i] + "</td><td>" + summaryInfo[i+1] + "</td></tr>";
                }
                summaryString += '</table><br><br>';
                selectionForm.innerHTML = summaryString;
            }
        }
        xhr.send(email + ":" + projectSelection);
    }
    return false;
}

function goToMyAccount(){
    window.location = 'MyAccount.html';
}