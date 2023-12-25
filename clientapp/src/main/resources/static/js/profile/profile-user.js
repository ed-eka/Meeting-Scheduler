$(document).ready(function(){
    let isEditMode = false;
    $("#profile-edit").hide();

    $("#edit-button").click(function toggleEditMode(){
        isEditMode = !isEditMode;
        
        if (isEditMode) {
            $("#profile-view").hide();
            $("#profile-edit").show();
            $("#edit-button").text("Save Profile").addClass("btn-info").removeClass("btn-primary");
        } else {
            // Save update
            
            $("#profile-edit").hide();
            $("#profile-view").show();
            $("#edit-button").text("Edit Profile").addClass("btn-primary").removeClass("btn-info");
        }
    }
    );


    
    
});


function beforeUpdate(){}

function update(){
    let usernameVal = $('#account-username-upd').val();
    let passwordVal = $('#account-password-upd').val();

    let emailVal = $('#person-email-upd').val();
    let addressVal = $('#person-address-upd').val();
    let phoneNumberVal = $('#person-phoneNumber-upd').val();

    $.ajax({
        method: "PUT",
        url: "api/profile/",
        beforeSend: addCsrfToken(),
        dataType: 'json',
        data: JSON.stringify({
            password: passwordVal
        }),
        contentType: 'application/json',
        success: result => {
            
        }
    })
}