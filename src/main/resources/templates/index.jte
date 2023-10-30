<!DOCTYPE html>
<html lang="es-CO">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>simple page</title>
</head>
<body>
    <form id="form">
        <input type="email" id="email" value="cc@na.com">
        <input type="password" id="password" value="12345678">
        <button type="submit">enviar</button>
    </form>
    @raw
    <script>
    
        let form = document.querySelector('#form')
        let email = document.querySelector('#email')
        let password = document.querySelector('#password')

        form.addEventListener('submit', async event =>{
            event.preventDefault()
            try{
                console.log(`${email.value} - ${password.value}`)
                const myData = await fetch("http://localhost:8081/api/v1/auth/login",{
                    method: 'POST',
                    headers: {
                               "Content-type": "application/json",
                             },
                    body: JSON.stringify({
                         email: email.value,
                         password: password.value
                    })
                }) 
            }catch(error){console.error(error)}

           
            
        })

    </script>
    @endraw
</body>
</html>