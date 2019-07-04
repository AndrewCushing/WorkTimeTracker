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
    selectString += '</select></form>';
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
                let summaryString = '<table style="width:50%" class="table table-bordered" id="entryTable"><tr><th scope="col">Description</th><th scope="col">Total time</th></tr>';
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
                displayEntries(xhr.responseText);                
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
    summarySpace.innerHTML = '<table style="width:75%" class="table table-bordered"><form onsubmit="return editEntry(this,' + 
        recordID + ')"><tr><th>Field</th><th>New value</th></tr><tr><td>' + 
        '<span>Project: </span></td><td><input type="text" required id="project" value="' + project + '"></td></tr>'+
        '<tr><td><span>Description: </span></td><td><input type="text" required id="description" value="' + description + '"></td></tr>'+
        '<tr><td><span>Date: </span></td><td><input type="date" required id="date" value ="' + date + '"></td></tr>'+
        '<tr><td><span>No. of hours: </span></td><td><input type="number" required id="amountOfTime" value="' + hours + '"></td></tr>'+
        '<tr><td><button>Submit</button></table></td></tr>'+
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
    alert("Feature not yet implemented. This will be included in a future release.");
}

function getFilteredEntries(){
    let startDate = document.getElementById("startDate").value;
    let endDate = document.getElementById("endDate").value;
    let project = document.getElementById("searchForm").Projects.value
    let email = sessionStorage.getItem('email');
    let hashedPass = sessionStorage.getItem('pass');
    let xhr = makeXHR('PUT', 'http://localhost:3000/api/getFilteredEntries');
    xhr.onload = function(){
        if (xhr.responseText==='No entries found'){
            document.getElementById("summarySpace").innerHTML = '<h3>No entries found</h3>';
            return;
        }
        displayEntries(xhr.responseText);
    }
    xhr.send(email+":"+hashedPass+":"+startDate+":"+endDate+":"+project);
}

function displayEntries(entriesAsString){
    let summarySpace = document.getElementById("summarySpace");
    let entryInfo = entriesAsString.split(':');
    let summaryString = '<table style="width:70%" class="table table-bordered" id="entryTable"><tr><th scope="col">Description</th><th scope="col">Date</th><th scope="col">Hours</th><th scope="col">Edit</th>' + 
        '<th>Delete</th></tr>';
    for (let i = 0 ; i < entryInfo.length ; i+=4){
        summaryString += '<tr id="' + entryInfo[i+3] + '"><td>' + entryInfo[i] + "</td><td>" + entryInfo[i+1] + "</td><td>" + entryInfo[i+2] + 
            '</td><td><button onclick="editRecord(' + entryInfo[i+3] + ')">Edit</button></td><td><button' + 
            ' onclick="deleteRecord(' + entryInfo[i+3] + ')">Delete</button></td></tr>';
    }
    summaryString += '</table><br><br>';
    summarySpace.innerHTML = summaryString;
}