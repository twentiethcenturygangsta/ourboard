const APICall = axios.create({
    timeout: 1000,
    headers: {'Content-Type': "application/json"}
})