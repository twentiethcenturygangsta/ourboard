const APICall = axios.create({
    timeout: 1000,
    headers: {'Content-Type': "application/json"}
})

const checkInputForm = (arr) => {
    arr.filter((instance) => {
        if (instance.val() === "") {
            instance.addClass("errorInput")
        } else {
            instance.removeClass("errorInput")
        }
    })
}