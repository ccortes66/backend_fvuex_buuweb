<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>me content</title>
</head>
<body>
    <h1 id="titleMe"></h1>
    <div>
        <p>Email: <span id="email"></span> </p>
    </div>
    <div>
        <p>Role:  <span id="role"></span></p>
    </div>
    <div>
      <button id="logout">Cerrar Session</button>
    </div>

    @raw
    <script>
        
        const logout = document.querySelector('#logout')

        logout.addEventListener('click',async() =>{
                console.log("click me enter")
                try
                {
                   const result = await fetch('http://localhost:8081/api/v1/auth/logout',{
                   method: 'GET',
                  })
                }catch(error){console.error(error)} 
        })

        document.addEventListener('DOMContentLoaded',async event => {
            event.preventDefault();
            try
            {
                const result = await fetch('http://localhost:8081/api/v1/auth/refesh',{
                 method: 'GET',
                 credentials: 'include'
                })

                const { token } = await result.json()
                const resultServer = await fetch('http://localhost:8081/api/v1/user/me',{
                 method: 'GET',
                 headers:{
                        "Content-type": "application/json",
                        'Authorization':  `Bearer ${token}`, 
                   }
                })
                
                const { email, role } = await resultServer.json()
                document.querySelector('#email').innerHTML = email
                document.querySelector('#role').innerHTML = role
                document.querySelector('#titleMe').innerHTML = `Hola ${email.split('@')[0]}`
                
            }catch(error){console.error(error)}
     })
    </script>
     @endraw
</body>
</html>