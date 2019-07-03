var validCredentials;

function checkCredentials(){
    validCredentials = false;
    let email = sessionStorage.getItem('email');
    let hashedPass = sessionStorage.getItem('pass');
    let xhr = makeXHR('PUT', 'http://localhost:3000/api/login');
    xhr.onload = function(){
        if(xhr.status === 200){
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
        let xhr = makeXHR('PUT', 'http://localhost:3000/api/getProjects');
        xhr.onload = function(){
            if(xhr.status === 200){
                let projects = xhr.responseText.split(':');
                createProjectSelector(projects);
            } else {
                document.getElementById("resultsGoHere").innerHTML = "<span>You haven't added any entries yet</span>";
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
    selectString += '</select></form><br><br>';
    selectionForm.innerHTML += selectString;
}
checkCredentials();

function findProjectSummary(){
    let projectSelection = document.getElementById("searchForm").Projects.value;
    if(validCredentials){
        let email = sessionStorage.getItem('email');
        let xhr = makeXHR('PUT', 'http://localhost:3000/api/summaryByProject');
        xhr.onload = function(){
            let summarySpace = document.getElementById("summarySpace");
            if(xhr.status === 200){
                let summaryInfo = xhr.responseText.split(':');
                let summaryString = '<table style="width:50%"><tr><th style="text-align:center">Description</th><th style="text-align:center">Total time</th></tr>';
                for (let i = 0 ; i < summaryInfo.length ; i+=2){
                    summaryString += '<tr><td>' + summaryInfo[i] + '</td><td>' + summaryInfo[i+1] + "</td></tr>";
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
        let xhr = makeXHR('PUT', 'http://localhost:3000/api/getAllEntries');
        xhr.onload = function(){
            let summarySpace = document.getElementById("summarySpace");
            if(xhr.status === 200){
                let response = xhr.responseText;
                let summaryInfo = response.split(':');
                let summaryString = '<table id="entryTable" style="width:80%"><tr><th>Description</th><th>Date</th><th>Hours</th><th>Edit</th>' + 
                    '<th>Delete</th></tr>';
                for (let i = 0 ; i < summaryInfo.length ; i+=4){
                    summaryString += '<tr id="' + summaryInfo[i+3] + '"><td>' + summaryInfo[i] + "</td><td>" + summaryInfo[i+1] + "</td><td>" + summaryInfo[i+2] + 
                        '</td><td><button onclick="editRecord(' + summaryInfo[i+3] + ')">Edit</button></td><td><button' + 
                        ' onclick="deleteRecord(' + summaryInfo[i+3] + ')">Delete</button></td></tr>';
                }
                summaryString += '</table><br><br>';
                summarySpace.innerHTML = summaryString;
            }
        }
        xhr.send(email + ":" + projectSelection);
    }
    return false;
}

function editRecord(recordID){
    let rowToEdit = document.getElementById(recordID);
    let description = rowToEdit.children[0].innerHTML;
    let date = rowToEdit.children[1].innerHTML;
    let hours = rowToEdit.children[2].innerHTML;
    let project = document.forms[0][0].value;
    let summarySpace = document.getElementById("summarySpace");
    summarySpace.innerHTML = '<form onsubmit="return editEntry(this,' + recordID + ')">'+
        '<span>Project: </span><input type="text" required id="project" value="' + project + '"><br><br>'+
        '<span>Description: </span><input type="text" required id="description" value="' + description + '"><br><br>'+
        '<span>Date: </span><input type="date" required id="date" value ="' + date + '"><br><br>'+
        '<span>No. of hours: </span><input type="number" required id="amountOfTime" value="' + hours + '"><br><br>'+
        '<button>Submit</button><br>'+
        '</form><div id="updateResult"></div>';
}

function editEntry(formData, recordID){
    let email = sessionStorage.getItem('email');
    let hashedPass = sessionStorage.getItem('pass');
    let project = document.getElementById("project").value;
    let description = document.getElementById("description").value;
    let date = document.getElementById("date").value;
    let hours = document.getElementById("amountOfTime").value;
    let xhr = makeXHR('PUT', 'http://localhost:3000/api/updateEntry');
    xhr.onload = function(){
        if (xhr.responseText==="Entry updated successfully"){
            getAllEntries();
        } else {
            document.getElementById("summarySpace").innerHTML = xhr.responseText;
        }
    };
    xhr.send(recordID+':'+email+':'+hashedPass+":"+project+":"+description+":"+date+":"+hours);
    return false;
}

function deleteRecord(entryID){
    let email = sessionStorage.getItem('email');
    let hashedPass = sessionStorage.getItem('pass');
    let resultDiv = document.getElementById("responseSpace");
    let responses = ['','Entry deleted successully','Error when trying to remove from database','Your credentials could not be verified. Please log in and try again.'];
    let xhr = makeXHR('PUT', 'http://localhost:3000/api/deleteEntry');
    xhr.onload = function(){
        resultDiv.innerHTML = '<h4>' + responses[Number(xhr.responseText)] + '</h4>';
        if (xhr.responseText==='1'){
            let rowToBeDeleted = document.getElementById(entryID);
            console.log(rowToBeDeleted);
            rowToBeDeleted.parentNode.removeChild(rowToBeDeleted);
        }
    }
    xhr.send(email + ':' + hashedPass + ":" + entryID);
}

function logout(){
    sessionStorage.clear();
    window.location = "Login.html";
}

function goToMyAccount(){
    window.location = 'AddEntry.html';
}

function makeXHR(requestHeader, requestPath){
    let xhr = new XMLHttpRequest();
    xhr.open(requestHeader, requestPath, true);
    xhr.responseType = 'text';
    return xhr;
}

function findFilteredProjectSummary(){
    let startDate = document.getElementById("startDate").value;
    let endDate = document.getElementById("endDate").value;
    
}

function getFilteredEntries(){

}