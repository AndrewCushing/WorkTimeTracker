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
                createProjectSelector(projects);
            } else {
                selectionForm.innerHTML = "<span>You haven't added any entries yet</span>";
            }
        }
        xhr.send(email);
    }  
}

function createProjectSelector(projectList){
    let selectionForm = document.getElementById("resultsGoHere");
    let selectString = '<span>Project: </span><br><br><form id="searchForm" onsubmit="return findProjectSummary(this)">';
    selectString += '<select name="Projects">';
    for (let project in projectList){
        selectString += '<option>' + projectList[project] + '</option>';
    }
    selectString += '</select><br><br>';
    selectString += '<button>Get Summary</button>';
    selectString += '</form><br><button onclick="getAllEntries()">Get all entries</button><br><br><br>';
    selectString += '<div id="summarySpace"></div>';
    selectionForm.innerHTML += selectString;
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
            let summarySpace = document.getElementById("summarySpace");
            if(xhr.status === 200){
                let summaryInfo = xhr.responseText.split(' :');
                let summaryString = '<table style="width:50%"><tr><th>Description</th><th>Total time</th></tr>';
                for (let i = 0 ; i < summaryInfo.length ; i+=2){
                    summaryString += "<tr><td>" + summaryInfo[i] + "</td><td>" + summaryInfo[i+1] + "</td></tr>";
                }
                summaryString += '</table><br><br>';
                summarySpace.innerHTML = summaryString;
            }
        }
        xhr.send(email + ":" + projectSelection);
    }
    return false;
}

function getAllEntries(){
    let projectSelection = document.getElementById("searchForm").Projects.value;
    if(validCredentials){
        let email = sessionStorage.getItem('email');
        let xhr = new XMLHttpRequest();
        xhr.open('PUT', 'http://localhost:3000/api/getAllEntries', true);
        xhr.responseType = 'text';
        xhr.onload = function(){
            let summarySpace = document.getElementById("summarySpace");
            if(xhr.status === 200){
                let response = xhr.responseText;
                console.log(response);
                let summaryInfo = response.split(' :');
                console.log(summaryInfo);
                let summaryString = '<table style="width:80%"><tr><th>Description</th><th>Date</th><th>Hours</th><th>Edit</th>' + 
                    '<th>Delete</th></tr>';
                for (let i = 0 ; i < summaryInfo.length ; i+=4){
                    summaryString += "<tr><td>" + summaryInfo[i] + "</td><td>" + summaryInfo[i+1] + "</td><td>" + summaryInfo[i+2] + 
                        '</td><td><button class="' + summaryInfo[i+3] + '">Edit</button></td><td><button class="' + summaryInfo[i+3] + 
                        '">Delete</button></td></tr>';
                }
                summaryString += '</table><br><br>';
                summarySpace.innerHTML = summaryString;
            }
        }
        xhr.send(email + ":" + projectSelection);
    }
    return false;
}

function goToMyAccount(){
    window.location = 'MyAccount.html';
}