function checkAll() {
    if (loginForm.id.value=="") {
        alert("id 입력하세요.");
        return false;
    }
    if (loginForm.password.value=="") {
        alert("password 입력하세요.");
        return false;
    }
    return true;
}